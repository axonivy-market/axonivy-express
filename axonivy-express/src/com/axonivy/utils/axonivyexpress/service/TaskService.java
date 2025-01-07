package com.axonivy.utils.axonivyexpress.service;

import java.util.List;

import com.axonivy.utils.axonivyexpress.ExpressConstants;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.ISecurityContext;
import ch.ivyteam.ivy.workflow.ITask;
import ch.ivyteam.ivy.workflow.query.CaseQuery;
import ch.ivyteam.ivy.workflow.query.TaskQuery;
import ch.ivyteam.ivy.workflow.query.TaskQuery.FilterLink;

public class TaskService {

  private TaskService() {
  }

  public static TaskService newInstance() {
    return new TaskService();
  }

  public List<ITask> findAllExpressTask() {
    TaskQuery query = TaskQuery.create();
    query.where().cases(getCaseQueryForExpress()).and(queryExcludeSystemTasks())
        .and(queryInvolvedTasks()).and(queryExpressTasks());
    return Ivy.wf().getTaskQueryExecutor().getResults(query);
  }

  private CaseQuery getCaseQueryForExpress() {
    CaseQuery caseQuery = CaseQuery.create();
    caseQuery.where().customField()
        .stringField(ExpressConstants.IS_EXPRESS_PROCESS).isEqual("true");
    return caseQuery;
  }

  private TaskQuery queryExcludeSystemTasks() {
    return TaskQuery.create().where().workerId().isNotEqual(
        ISecurityContext.current().users().system().getSecurityMemberId());
  }

  private TaskQuery queryInvolvedTasks() {
    FilterLink currentUserIsInvolved = TaskQuery.create().where().and()
        .currentUserOrHisRolesAreInvolved();
    return currentUserIsInvolved;
  }

  private TaskQuery queryExpressTasks() {
    FilterLink hasGroupId = TaskQuery.create().where().and()
        .customField().stringField(ExpressConstants.IS_EXPRESS_TASK)
        .isEqual("true");
    return hasGroupId;
  }
}
