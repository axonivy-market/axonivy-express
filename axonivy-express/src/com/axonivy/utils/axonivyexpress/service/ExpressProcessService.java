package com.axonivy.utils.axonivyexpress.service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.utils.axonivyexpress.entity.ExpressProcess;
import com.axonivy.utils.axonivyexpress.utils.ExpressManagementUtils;
import com.axonivy.utils.axonivyexpress.utils.ProcessStartUtils;

import ch.ivyteam.ivy.workflow.IProcessStart;

public class ExpressProcessService
    extends JsonConfigurationService<ExpressProcess> {

  private static final String EXPRESS_PROCESSES = "Portal.Processes.ExpressProcesses";
  private static final String EXPRESS_CREATE_FRIENDLY_REQUEST_PATH = "Start Processes/CreateWorkflow/AxonIvyExpressWF.ivp";
  private static final String EXPRESS_ADHOC_WF_FRIENDLY_REQUEST_PATH = "Start Processes/CreateWorkflow/AxonIvyExpressAdhocWF.ivp";
  private static final String EXPRESS_WORKFLOW_FRIENDLY_REQUEST_PATH = "Start Processes/GenericPredefinedWorkflowStart/GenericPredefinedProcessStart.ivp";
  private static final String EXPRESS_WORKFLOW_EDIT_REQUEST_PATH = "Start Processes/GenericPredefinedWorkflowStart/GenericEditProcessStart.ivp";
  private static final String EXPRESS_BUSINESS_VIEW_REQUEST_PATH = "Start Processes/ExpressStart/startExpressBusinessView.ivp";
  private static ExpressProcessService instance;

  public static ExpressProcessService getInstance() {
    if (instance == null) {
      instance = new ExpressProcessService();
    }
    return instance;
  }

  public List<ExpressProcess> findReadyToExecuteProcessOrderByName() {
    List<ExpressProcess> expressProcesses = findAll();
    return expressProcesses.stream().filter(ExpressProcess::isReadyToExecute)
        .sorted(Comparator.comparing(ExpressProcess::getProcessName))
        .collect(Collectors.toList());
  }

  public ExpressProcess findReadyToExecuteProcessByName(String processName) {
    List<ExpressProcess> readyToExecuteProcesses = findReadyToExecuteProcessOrderByName();
    return readyToExecuteProcesses.stream().filter(filterByName(processName))
        .findFirst().orElse(null);
  }

  private Predicate<? super ExpressProcess> filterByName(String processName) {
    return process -> process.getProcessName().equals(processName);
  }

  public ExpressProcess findExpressProcessByName(String processName) {
    return findAll().stream().filter(filterByName(processName)).findFirst()
        .orElse(null);
  }

  public List<ExpressProcess> findReadyToExecuteProcessByProcessType(
      String processType) {
    List<ExpressProcess> readyToExecuteProcesses = findReadyToExecuteProcessOrderByName();
    return readyToExecuteProcesses.stream()
        .filter(process -> process.getProcessType().equals(processType))
        .collect(Collectors.toList());
  }

  public List<ExpressProcess> findAllByProcessType(String processType) {
    return findAll().stream()
        .filter(process -> process.getProcessType().equals(processType))
        .sorted(Comparator.comparing(ExpressProcess::getProcessName))
        .collect(Collectors.toList());
  }

  public ExpressProcess findExpressProcessById(String id) {
    ExpressProcess result = findById(id);
    if (result != null) {
      result.setProcessOwner(
          ExpressManagementUtils.getValidMemberName(result.getProcessOwner()));
      result.setProcessCoOwners(ExpressManagementUtils
          .getValidMemberNames(result.getProcessCoOwners()));
      result.setProcessPermissions(ExpressManagementUtils
          .getValidMemberNames(result.getProcessPermissions()));
    }
    return result;
  }

  @Override
  public Class<ExpressProcess> getType() {
    return ExpressProcess.class;
  }

  @Override
  public String getConfigKey() {
    return EXPRESS_PROCESSES;
  }

  public String findExpressAdhocWFLink() {
    return ProcessStartUtils.findRelativeUrlByProcessStartFriendlyRequestPath(
        EXPRESS_ADHOC_WF_FRIENDLY_REQUEST_PATH);
  }

  public String findExpressWorkflowStartLink() {
    return ProcessStartUtils.findRelativeUrlByProcessStartFriendlyRequestPath(
        EXPRESS_WORKFLOW_FRIENDLY_REQUEST_PATH);
  }

  public String findExpressBusinessViewStartLink() {
    return ProcessStartUtils.findRelativeUrlByProcessStartFriendlyRequestPath(
        EXPRESS_BUSINESS_VIEW_REQUEST_PATH);
  }

  public IProcessStart findExpressCreationProcess() {
    return ProcessStartUtils.findProcessStartByUserFriendlyRequestPath(
        EXPRESS_CREATE_FRIENDLY_REQUEST_PATH);
  }

  public String findExpressWorkflowEditLink(String workflowId) {
    String url = ProcessStartUtils
        .findRelativeUrlByProcessStartFriendlyRequestPath(
            EXPRESS_WORKFLOW_EDIT_REQUEST_PATH);
    if (StringUtils.isNotBlank(url)) {
      return String.format("%s?workflowID=%s", url, workflowId);
    }
    return StringUtils.EMPTY;
  }
}