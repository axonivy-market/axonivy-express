package com.axonivy.utils.axonivyexpress.utils;

import ch.ivyteam.ivy.security.ISecurityContext;
import ch.ivyteam.ivy.security.IUser;
import ch.ivyteam.ivy.security.exec.Sudo;

public class UserUtils {

  public static IUser findUser(String username) {
    return Sudo.get(() -> {
      return ISecurityContext.current().users().find(username);
    });
  }

  public static String stripSharpCharacterFromSecurityMemberName(
      String username) {
    if (username == null) {
      return "";
    }
    return username.startsWith("#") ? username.substring(1) : username;
  }
}
