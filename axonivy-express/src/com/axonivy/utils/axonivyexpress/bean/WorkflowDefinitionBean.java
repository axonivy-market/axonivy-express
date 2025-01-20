package com.axonivy.utils.axonivyexpress.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.utils.axonivyexpress.enums.ProcessType;
import com.axonivy.utils.axonivyexpress.enums.TaskType;
import com.axonivy.utils.axonivyexpress.navigator.ExpressNavigator;

import ch.ivyteam.ivy.environment.Ivy;
import gawfs.TaskDef;

@ManagedBean
@RequestScoped
public class WorkflowDefinitionBean implements Serializable {

  private static final long serialVersionUID = 8119703742579630358L;
  private static final String SYSTEM = "SYSTEM";

  private List<String> excludedRoleNames;

  @PostConstruct
  public void init() {
    initExcludedRoleNames();
  }

  private void initExcludedRoleNames() {
    excludedRoleNames = new ArrayList<>();
    excludedRoleNames.add(SYSTEM);
  }

  public TaskType[] getTaskTypesForFirstWorkflowTask() {
    return new TaskType[] { TaskType.USER_TASK, TaskType.USER_TASK_WITH_EMAIL,
        TaskType.EMAIL };
  }

  /**
   * Get array of all task types
   * 
   * @param taskDefList
   * @param currentTaskIndex
   * @return Array of all task types
   */
  public TaskType[] getTaskTypes(List<TaskDef> taskDefList,
      int currentTaskIndex) {
    List<TaskDef> previousTasks = taskDefList.subList(0, currentTaskIndex);

    for (TaskDef previousTask : previousTasks) {
      if (previousTask.getTaskType() == TaskType.USER_TASK
          || previousTask.getTaskType() == TaskType.USER_TASK_WITH_EMAIL) {
        return new TaskType[] { TaskType.USER_TASK,
            TaskType.USER_TASK_WITH_EMAIL, TaskType.EMAIL, TaskType.APPROVAL };
      }
    }

    return new TaskType[] { TaskType.USER_TASK, TaskType.USER_TASK_WITH_EMAIL,
        TaskType.EMAIL };
  }

  /**
   * Get array of all process types
   * 
   * @return Array of process types
   */
  public ProcessType[] getProcessTypes() {
    return ProcessType.values();
  }

  public List<String> getExcludedRoleNames() {
    return excludedRoleNames;
  }

  public void setExcludedRoleNames(List<String> excludedRoleNames) {
    this.excludedRoleNames = excludedRoleNames;
  }

  public String getTitle(String processName) {
    String stepName = Ivy.cms().co(
        "/Dialogs/com/axonivy/utils/axonivyexpress/WorkflowDefinition/WorkflowPropertiesStep");

    if (StringUtils.isNotBlank(processName)) {
      return processName + " - " + stepName;
    }

    return stepName;
  }

  public void navigateToExpressManagementPage() {
    ExpressNavigator.navigateToManagementPage();
  }
}