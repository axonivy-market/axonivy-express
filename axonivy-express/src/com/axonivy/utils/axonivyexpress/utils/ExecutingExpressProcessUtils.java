package com.axonivy.utils.axonivyexpress.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.axonivy.utils.axonivyexpress.ExpressConstants;
import com.axonivy.utils.axonivyexpress.exception.ExpressException;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.persistence.PersistencyException;
import ch.ivyteam.ivy.security.exec.Sudo;
import ch.ivyteam.ivy.workflow.ICase;
import ch.ivyteam.ivy.workflow.ITask;
import ch.ivyteam.ivy.workflow.query.CaseQuery;
import ch.ivyteam.ivy.workflow.query.TaskQuery;

public final class ExecutingExpressProcessUtils {
  private ExecutingExpressProcessUtils() {
  }

  @SuppressWarnings("unchecked")
  public static <T> List<T> getAttributesOfTasks(String groupId,
      String attribute) {
    return Sudo.get(() -> {
      TaskQuery query = TaskQuery.create().where().caseId()
          .isEqual(Ivy.wfCase().getId()).and().customField()
          .textField(ExpressConstants.TASK_GROUP_ID_KEY).isEqual(groupId)
          .orderBy()
          .endTimestamp();
      List<ITask> tasks = Ivy.wf().getTaskQueryExecutor().getResults(query);
      List<T> result = new ArrayList<>();
      for (ITask task : tasks) {
        try {
          result.add((T) task.getEndProcessData().get(attribute));
        } catch (PersistencyException | NoSuchFieldException e) {
          throw new ExpressException(e);
        }
      }
      return result;
    });
  }

  @SuppressWarnings("unchecked")
  public static <T> List<T> getExpressTaskEndProcessData(Long caseId,
      String parentCategoryName) {
    return Sudo.get(() -> {
      TaskQuery query = buildExpressTaskQuery(caseId, parentCategoryName);
      query.orderBy().endTimestamp();
      List<ITask> tasks = Ivy.wf().getTaskQueryExecutor().getResults(query);
      List<T> result = new ArrayList<>();
      for (ITask task : tasks) {
        try {
          if (task.getEndProcessData() != null) {
            result.add((T) task.getEndProcessData());
          }
        } catch (PersistencyException e) {
          throw new ExpressException(e);
        }
      }
      return result;
    });
  }

  private static TaskQuery buildExpressTaskQuery(Long caseId,
      String parentCategoryName) {
    String startingWithCategory = String.format("%s%%", parentCategoryName);
    TaskQuery query = TaskQuery.create().where().businessCaseId()
        .isEqual(caseId).and().category().isLike(startingWithCategory);
    return query;
  }

  public static ICase getExpressCase(long caseId) {
    return Sudo.get(() -> {
      CaseQuery query = CaseQuery.businessCases().where().caseId()
          .isEqual(caseId).and().customField()
          .stringField(ExpressConstants.IS_EXPRESS_PROCESS).isEqual("true");
      List<ICase> result = Ivy.wf().getCaseQueryExecutor().getResults(query);
      return CollectionUtils.isEmpty(result) ? null : result.get(0);
    });
  }

  public static boolean isExpressCase(ICase caze) {
    String isExpress = caze.customFields()
        .stringField(ExpressConstants.IS_EXPRESS_PROCESS).getOrDefault("false");
    return Boolean.parseBoolean(isExpress);
  }

  public static void setIsExpressTask(ITask task) {
    task.customFields().stringField(ExpressConstants.IS_EXPRESS_TASK)
        .set("true");
  }
}