package com.axonivy.utils.axonivyexpress.utils;

import java.util.Arrays;

import ch.ivyteam.ivy.security.exec.Sudo;
import ch.ivyteam.ivy.workflow.ITask;
import ch.ivyteam.ivy.workflow.TaskState;

public final class TaskUtils {

  private TaskUtils() {
  }

  public static void resetTask(final ITask task) {
    Sudo.get(() -> {
      if (Arrays
          .asList(TaskState.RESUMED, TaskState.CREATED, TaskState.PARKED,
              TaskState.READY_FOR_JOIN, TaskState.FAILED)
          .contains(task.getState())) {
        task.reset();
      }
      return Void.class;
    });
  }
}
