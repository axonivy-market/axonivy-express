package com.axonivy.utils.axonivyexpress.utils;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.axonivy.utils.axonivyexpress.ExpressConstants;
import com.axonivy.utils.axonivyexpress.entity.ExpressProcess;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.IRole;
import ch.ivyteam.ivy.security.ISecurityContext;
import ch.ivyteam.ivy.security.ISecurityMember;
import ch.ivyteam.ivy.security.IUser;

public class PermissionUtils {

  private static final String EXTERNAL_ID_PREFIX = " externalId:";

  /**
   * Check if user can start an Express workflow and set permission if user able
   * to edit/delete express WF
   *
   * @param workflow
   * @return True: has permission to start Express workflow, False: Do not have
   *         permission to start Express workflow
   */
  public static boolean checkAbleToStartAndAbleToEditExpressWorkflow(
      ExpressProcess workflow) {
    String validProcessOwnerName = ExpressManagementUtils
        .getValidMemberName(workflow.getProcessOwner());
    boolean isWorkflowOwner = StringUtils.isNotBlank(validProcessOwnerName)
        ? Ivy.session()
            .canActAsUser(ISecurityContext.current().users()
                .find(validProcessOwnerName.substring(1)))
        : false;
    boolean hasAdminRole = isSessionUserHasAdminRole();

    if (isWorkflowOwner || hasAdminRole) {
      workflow.setAbleToEdit(true);
      return true;
    }
    Collection<String> ableToStartResponsibles = CollectionUtils
        .emptyIfNull(workflow.getProcessPermissions());
    Collection<String> processOwners = CollectionUtils
        .emptyIfNull(workflow.getProcessCoOwners());

    for (String memberName : processOwners) {
      if (isSessionUserBelongsToPermissionGroup(memberName)) {
        workflow.setAbleToEdit(true);
        return true;
      }
    }

    for (String memberName : ableToStartResponsibles) {
      if (isSessionUserBelongsToPermissionGroup(memberName)) {
        return true;
      }
    }

    return false;
  }

  public static boolean isSessionUserHasAdminRole() {
    return Ivy.session()
        .hasRole(ISecurityContext.current().roles()
            .find(ExpressConstants.ADMIN_ROLE), false);
  }

  private static boolean isSessionUserBelongsToPermissionGroup(
      String memberName) {
    if (memberName == null) {
      return false;
    }

    String memberNameWithoutExternalId = getMemberNameWithoutExternalId(
        memberName);
    ISecurityMember member = ISecurityContext.current().members()
        .find(memberNameWithoutExternalId);
    if (member != null) {
      boolean isAssignedUser = member.isUser()
          && Ivy.session().canActAsUser((IUser) member);
      boolean hasAssignedRole = !member.isUser()
          && Ivy.session().hasRole((IRole) member, false);
      return isAssignedUser || hasAssignedRole;
    }
    return false;
  }

  private static String getMemberNameWithoutExternalId(String memberName) {
    int indexOfExternalId = memberName.indexOf(EXTERNAL_ID_PREFIX);
    return indexOfExternalId > -1 ? memberName.substring(0, indexOfExternalId)
        : memberName;
  }
}
