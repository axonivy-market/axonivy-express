package com.axonivy.utils.axonivyexpress.enums;

import ch.ivyteam.ivy.environment.Ivy;

public enum TaskType {
  USER_TASK, USER_TASK_WITH_EMAIL, APPROVAL, EMAIL, FINAL_REVIEW;

  public String getLabel() {
    return Ivy.cms().co("/Labels/Enums/TaskType/" + name());
  }
}