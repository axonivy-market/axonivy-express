package com.axonivy.utils.axonivyexpress.enums;

import ch.ivyteam.ivy.environment.Ivy;

public enum FormElementType {
  INPUT_TEXT("InputFieldText"), INPUT_DATE("InputFieldDate"),
  INPUT_NUMBER("InputFieldNumber"), INPUT_TEXT_AREA("InputTextArea"),
  CHECKBOX("ManyCheckbox"), RADIO_BUTTON("OneRadio"), FILE_UPLOAD("FileUpload");

  private final String value;

  FormElementType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public String getLabel() {
    return Ivy.cms().co("/Labels/Enums/FormElementType/" + name());
  }
}