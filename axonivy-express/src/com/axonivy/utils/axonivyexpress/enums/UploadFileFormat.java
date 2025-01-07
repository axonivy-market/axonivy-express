package com.axonivy.utils.axonivyexpress.enums;

import ch.ivyteam.ivy.environment.Ivy;

public enum UploadFileFormat {
  PDF, WORD, EXCEL, OTHERS;

  public String getLabel() {
    return Ivy.cms().co("/Labels/Enums/UploadFileFormat/" + name());
  }
}