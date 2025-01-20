package com.axonivy.utils.axonivyexpress.utils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import ch.ivyteam.ivy.application.ActivityState;
import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.application.IProcessModel;
import ch.ivyteam.ivy.application.IProcessModelVersion;
import ch.ivyteam.ivy.application.app.IApplicationRepository;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.ISecurityContext;
import ch.ivyteam.ivy.security.exec.Sudo;
import ch.ivyteam.ivy.workflow.IProcessStart;
import ch.ivyteam.ivy.workflow.IWorkflowProcessModelVersion;

public class ProcessStartUtils {
  /**
   * Find start link from friendly request path
   * 
   * @param friendlyRequestPath friendly path e.g "Start
   *                            Processes/UserExampleGuide/userExampleGuide.ivp"
   * @return start link or empty string
   */
  public static String findRelativeUrlByProcessStartFriendlyRequestPath(
      String friendlyRequestPath) {
    IProcessStart processStart = ProcessStartUtils
        .findProcessStartByUserFriendlyRequestPath(friendlyRequestPath);
    return processStart != null ? processStart.getLink().getRelative()
        : StringUtils.EMPTY;
  }

  public static IProcessStart findProcessStartByUserFriendlyRequestPath(
      String requestPath) {
    return Sudo.get(() -> {
      IProcessStart processStart = findProcessStartByUserFriendlyRequestPathAndPmv(
          requestPath, Ivy.request().getProcessModelVersion());
      if (processStart != null) {
        return processStart;
      }

      List<IApplication> applicationsInSecurityContext = IApplicationRepository
          .instance().allOf(ISecurityContext.current());

      List<IProcessModel> processModels = applicationsInSecurityContext.stream()
          .map(IApplication::getProcessModelsSortedByName).flatMap(List::stream)
          .collect(Collectors.toList());

      for (IProcessModel processModel : processModels) {
        Optional<IProcessStart> processStartOptional = Optional.of(processModel)
            .filter(pm -> isActive(pm))
            .map(IProcessModel::getReleasedProcessModelVersion)
            .filter(pmv -> isActive(pmv))
            .map(p -> findProcessStartByUserFriendlyRequestPathAndPmv(
                requestPath, p))
            .filter(Objects::nonNull);
        if (processStartOptional.isPresent()) {
          return processStartOptional.get();
        }
      }
      return processStart;
    });
  }

  private static IProcessStart findProcessStartByUserFriendlyRequestPathAndPmv(
      String requestPath, IProcessModelVersion processModelVersion) {
    return IWorkflowProcessModelVersion.of(processModelVersion)
        .findStartElementByUserFriendlyRequestPath(requestPath);
  }

  private static boolean isActive(IProcessModel processModel) {
    return processModel.getActivityState() == ActivityState.ACTIVE;
  }

  private static boolean isActive(IProcessModelVersion processModelVersion) {
    return processModelVersion != null
        && processModelVersion.getActivityState() == ActivityState.ACTIVE;
  }

}
