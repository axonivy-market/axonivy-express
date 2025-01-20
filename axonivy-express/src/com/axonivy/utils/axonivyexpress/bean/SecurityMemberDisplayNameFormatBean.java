package com.axonivy.utils.axonivyexpress.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.axonivy.portal.components.dto.UserDTO;
import com.axonivy.utils.axonivyexpress.utils.SecurityMemberDisplayNameUtils;

import ch.ivyteam.ivy.security.ISecurityMember;

@ManagedBean
@ViewScoped
/**
 * This bean provide some methods to generate display name for
 * {@link ISecurityMember} and {@link UserDTO}
 */
public class SecurityMemberDisplayNameFormatBean implements Serializable {

  private static final long serialVersionUID = 3349035984172731613L;

  public String generateBriefDisplayNameForSecurityMember(
      ISecurityMember member, String securityMemberName) {
    return SecurityMemberDisplayNameUtils
        .generateBriefDisplayNameForSecurityMember(member, securityMemberName);
  }

  public String generateFullDisplayNameForUserDTO(UserDTO user) {
    return SecurityMemberDisplayNameUtils
        .generateFullDisplayNameForUserDTO(user);
  }
}