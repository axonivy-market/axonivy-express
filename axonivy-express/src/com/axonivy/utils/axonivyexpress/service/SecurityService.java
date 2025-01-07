package com.axonivy.utils.axonivyexpress.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.axonivy.utils.axonivyexpress.entity.RoleDTO;
import com.axonivy.utils.axonivyexpress.entity.SecurityMemberDTO;
import com.axonivy.utils.axonivyexpress.entity.UserDTO;
import com.axonivy.utils.axonivyexpress.transformer.SecurityMemberDTOMapper;
import com.axonivy.utils.axonivyexpress.utils.UserUtils;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.IRole;
import ch.ivyteam.ivy.security.ISecurityContext;
import ch.ivyteam.ivy.security.IUser;
import ch.ivyteam.ivy.security.exec.Sudo;
import ch.ivyteam.ivy.security.query.UserQuery;
import ch.ivyteam.ivy.security.query.UserQuery.IFilterQuery;

public class SecurityService {
  private SecurityService() {
  }

  public static SecurityService newInstance() {
    return new SecurityService();
  }

  public List<SecurityMemberDTO> findSecurityMembers(String query,
      int startIndex, int count) {
    return Sudo.get(() -> {
      List<RoleDTO> roles = findAllRoleDTO().stream()
          .filter(role -> doesNameContainQuery(query, role))
          .sorted(getRoleDTOComparator()).collect(Collectors.toList());

      List<UserDTO> users = queryUsers(query, startIndex, count, null, null);

      List<SecurityMemberDTO> members = SecurityMemberDTOMapper
          .mapFromUserDTOs(users);
      members.addAll(SecurityMemberDTOMapper.mapFromRoleDTOs(roles));
      int size = count;
      if (count <= 0) {
        size = members.size();
      }

      return members.subList(startIndex, Math.min(size, members.size()));
    });
  }

  public List<RoleDTO> findAllRoleDTO() {
    return Sudo.get(() -> {
      return CollectionUtils
          .emptyIfNull(ISecurityContext.current().roles().all()).stream()
          .filter(role -> role.getProperty("HIDE") == null)
          .map(role -> new RoleDTO(role)).collect(Collectors.toList());
    });
  }

  public SecurityMemberDTO findSecurityMemberByName(
      String securityMemberName) {
    if (securityMemberName.startsWith("#")) {
      return findSecurityUserByName(securityMemberName.replace("#", ""));
    }
    return findSecurityRoleByName(securityMemberName);
  }

  private SecurityMemberDTO findSecurityUserByName(
      String securityMemberName) {
    IUser findUser = UserUtils.findUser(securityMemberName);
    return findUser == null ? null : new SecurityMemberDTO(findUser);
  }

  private boolean doesNameContainQuery(String query, RoleDTO role) {
    return StringUtils.containsIgnoreCase(role.getDisplayName(), query)
        || StringUtils.containsIgnoreCase(role.getName(), query);
  }

  private Comparator<? super RoleDTO> getRoleDTOComparator() {
    return (u1, u2) -> StringUtils.compareIgnoreCase(u1.getDisplayName(),
        u2.getDisplayName());
  }

  /**
   * Query users in specific application
   * @param query
   * @param startIndex
   * @param count
   * @param fromRoles
   * @param excludedUsernames
   * @return {@link List}
   */
  private List<UserDTO> queryUsers(String query, int startIndex, int count, List<String> fromRoles, List<String> excludedUsernames) {
    UserQuery userQuery = ISecurityContext.current().users().query();

    IFilterQuery filterQuery = createFilterQuery(query, userQuery);

    if (CollectionUtils.isNotEmpty(fromRoles)) {
      UserQuery hasRolesQuery = queryHasRoles(fromRoles);
      filterQuery.andOverall(hasRolesQuery);
    }
    excludeUsername(excludedUsernames, filterQuery);

    List<IUser> users = userQuery
        .orderBy().fullName().name()
        .executor().results(startIndex, count);
    return users.stream().map(UserDTO::new).collect(Collectors.toList());
  }

  private IFilterQuery createFilterQuery(String query, UserQuery userQuery) {
    String containingQuery = "%" + Objects.toString(query, StringUtils.EMPTY)
        + "%";
    IFilterQuery filterQuery = userQuery.where();
    filterQuery.fullName().isLikeIgnoreCase(containingQuery).or().name()
        .isLikeIgnoreCase(containingQuery);
    return filterQuery;
  }

  private UserQuery queryHasRoles(List<String> fromRoles) {
    List<IRole> roles = new ArrayList<>();
    for (String roleName : fromRoles) {
      IRole iRole = ISecurityContext.current().roles().find(roleName);
      if (Objects.nonNull(iRole)) {
        roles.add(iRole);
      } else {
        Ivy.log().warn("Cannot find role name: {0}", roleName);
      }
    }

    UserQuery hasRolesQuery = UserQuery.create();
    IFilterQuery hasRolesFilter = hasRolesQuery.where();
    for (IRole role : roles) {
      hasRolesFilter.or().hasRole(role);
    }
    return hasRolesQuery;
  }

  private void excludeUsername(List<String> excludedUsernames,
      IFilterQuery filterQuery) {
    if (CollectionUtils.isNotEmpty(excludedUsernames)) {
      UserQuery excludeUsernameQuery = queryExcludeUsernames(excludedUsernames);
      filterQuery.andOverall(excludeUsernameQuery);
    }
  }

  private UserQuery queryExcludeUsernames(List<String> excludedUsernames) {
    UserQuery excludeUsernameQuery = UserQuery.create();
    IFilterQuery excludeUsernameFilter = excludeUsernameQuery.where();
    for (String username : excludedUsernames) {
      excludeUsernameFilter.and().name().isNotEqual(username);
    }
    return excludeUsernameQuery;
  }

  private SecurityMemberDTO findSecurityRoleByName(
      String securityMemberName) {
    List<RoleDTO> roles = findAllRoleDTO().stream().filter(role -> StringUtils
        .equalsIgnoreCase(role.getName(), securityMemberName))
        .collect(Collectors.toList());
    return SecurityMemberDTOMapper.mapFromRoleDTOs(roles).stream().findFirst()
        .orElse(null);
  }
}
