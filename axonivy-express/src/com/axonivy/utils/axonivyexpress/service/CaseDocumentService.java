package com.axonivy.utils.axonivyexpress.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ch.ivyteam.ivy.environment.Ivy;

public class CaseDocumentService {

  private static final String UPLOAD_DOCUMENT_WHITELIST_EXTENSION = "Portal.Document.WhitelistExtension";

  public static List<String> getAllowedUploadFileType() {
    String whileListVariable = Ivy.var()
        .get(UPLOAD_DOCUMENT_WHITELIST_EXTENSION);

    if (StringUtils.isBlank(whileListVariable)) {
      return new ArrayList<>();
    } else {
      String[] supportedFileTypeArr = whileListVariable.toLowerCase()
          .split("\\s*,[,\\s]*");
      return Arrays.asList(supportedFileTypeArr);
    }
  }
}
