package com.axonivy.utils.axonivyexpress.enums;

import ch.ivyteam.ivy.environment.Ivy;

public enum ProcessType {
  AD_HOC("AMWF"), REPEAT("AHWF");

  private final String value;

  ProcessType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public String getLabel() {
    return Ivy.cms().co("/Labels/Enums/ProcessType/" + name());
  }
}