package com.axonivy.utils.axonivyexpress.enums;

import ch.ivyteam.ivy.environment.Ivy;

public enum ExpressMessageType {
  INFO("INFO"), WARNING("WARNING"), ERROR("WARNING"), FAILED("FAILED"),
  IMPORT_STATUS("import_status"),
  IMPORT_EXPRESS_PROCESSES("import_express_processes"),
  IMPORT_RESULT("import_result"), SUCCESSFUL("SUCCESSFUL");

  private final String value;

  ExpressMessageType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public String getLabel() {
    return Ivy.cms().co("/Labels/Enums/ExpressMessageType/" + name());
  }
}