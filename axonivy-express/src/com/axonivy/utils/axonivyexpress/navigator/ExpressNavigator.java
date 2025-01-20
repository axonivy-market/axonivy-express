package com.axonivy.utils.axonivyexpress.navigator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.axonivy.portal.components.generic.navigation.BaseNavigator;
import com.axonivy.portal.components.publicapi.PortalNavigatorInFrameAPI;
import com.axonivy.portal.components.util.ProcessStartUtils;
import com.axonivy.utils.axonivyexpress.entity.ExpressProcess;
import com.axonivy.utils.axonivyexpress.exception.ExpressException;

public class ExpressNavigator extends BaseNavigator {

  private static final String MANAGEMENT_REQUEST_PATH = "Start Processes/ExpressStart/expressManagement.ivp";
  private static final String CREATE_WORKFLOW_REQUEST_PATH = "Start Processes/CreateWorkflow/AxonIvyExpressWF.ivp";
  private static final String EDIT_WORKFLOW_REQUEST_PATH = "Start Processes/GenericPredefinedWorkflowStart/GenericEditProcessStart.ivp";
  private static final String START_EXPRESS_WORKFLOW_FRIENDLY_REQUEST_PATH = "Start Processes/GenericPredefinedWorkflowStart/GenericPredefinedProcessStart.ivp";

  public static void navigateToCreateWorkflow() {
    try {
      ProcessStartUtils.redirect(PortalNavigatorInFrameAPI
          .findAbsoluteUrlByProcessStartFriendlyRequestPath(
              CREATE_WORKFLOW_REQUEST_PATH));
    } catch (IOException e) {
      throw new ExpressException(e);
    }
  }

  public static void navigateToEditWorkflow(ExpressProcess process) {
    try {
      Map<String, String> params = new HashMap<>();
      params.put("workflowID", process.getId());
      ProcessStartUtils.redirect(buildAbsoluteUrl(
          EDIT_WORKFLOW_REQUEST_PATH, params));
    } catch (IOException e) {
      throw new ExpressException(e);
    }
  }

  public static void navigateToManagementPage() {
    try {
      ProcessStartUtils.redirect(PortalNavigatorInFrameAPI
        .findAbsoluteUrlByProcessStartFriendlyRequestPath(
            MANAGEMENT_REQUEST_PATH));
    } catch (IOException e) {
      throw new ExpressException(e);
    }
  }

  public static String getExpressStartLink(ExpressProcess process) {
    Map<String, String> params = new HashMap<>();
    params.put("embedInFrame", "true");

    if (process == null) {
      return buildAbsoluteUrl(START_EXPRESS_WORKFLOW_FRIENDLY_REQUEST_PATH,
          params);
    }

    params.put("actualStepIndex", "0");
    params.put("workflowID", process.getId());
    return buildAbsoluteUrl(START_EXPRESS_WORKFLOW_FRIENDLY_REQUEST_PATH,
        params);
  }
}
