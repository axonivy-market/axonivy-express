package com.axonivy.utils.axonivyexpress.utils;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.virusscan.VirusException;
import org.primefaces.virusscan.VirusScanner;
import org.primefaces.virusscan.VirusScannerService;

import com.axonivy.portal.components.document.DocumentDetector;
import com.axonivy.portal.components.document.DocumentDetectorFactory;

import ch.ivyteam.ivy.environment.Ivy;

public final class UploadDocumentUtils {

  private static final String ENABLE_VIRUS_SCANNER_FOR_UPLOADED_DOCUMENT = "Portal.Document.EnableVirusScanner";
  private static final String ENABLE_SCRIPT_CHECKING_FOR_UPLOADED_DOCUMENT = "Portal.Document.EnableScriptChecking";

  public static boolean enableVirusScannerForUploadedDocument() {
    return Boolean.parseBoolean(
        Ivy.var().get(ENABLE_VIRUS_SCANNER_FOR_UPLOADED_DOCUMENT));
  }

  public static boolean enableScriptCheckingForUploadedDocument() {
    return Boolean.parseBoolean(
        Ivy.var().get(ENABLE_SCRIPT_CHECKING_FOR_UPLOADED_DOCUMENT));
  }

  public static String validateUploadedFile(UploadedFile importFile) {
    String validateMessage = "";
    if (importFile == null || importFile.getSize() == 0) {
      validateMessage = Ivy.cms()
          .co("/Labels/Messages/FileEmptyMessage");
    } else if (enableVirusScannerForUploadedDocument()
        && isDocumentTypeHasVirus(importFile)) {
      validateMessage = Ivy.cms()
          .co("/Dialogs/com/axonivy/portal/components/DocumentTable/FileContainVirus");
    } else if (enableScriptCheckingForUploadedDocument()
        && !isDocumentSafe(importFile)) {
      validateMessage = Ivy.cms()
          .co("/Dialogs/com/axonivy/portal/components/DocumentTable/FileContainScript");
    } else if (!FilenameUtils.isExtension(importFile.getFileName(), "json")) {
      validateMessage = Ivy.cms()
          .co("/Dialogs/com/axonivy/portal/components/DocumentTable/InvalidFileMessage");
    }
    return validateMessage;
  }

  public static boolean isDocumentTypeHasVirus(UploadedFile uploadedFile) {
    VirusScannerService service = new VirusScannerService(
        VirusScanner.class.getClassLoader());
    try {
      service.performVirusScan(uploadedFile);
    } catch (VirusException e) {
      Ivy.log().error(e);
      return true;
    }
    return false;
  }

  public static boolean isDocumentSafe(UploadedFile uploadedFile) {
    if (uploadedFile != null) {
      DocumentDetectorFactory documentDetectorFactory = new DocumentDetectorFactory();
      DocumentDetector documentDetector = documentDetectorFactory
          .getDocumentDetector(FilenameUtils
              .getExtension(StringUtils.lowerCase(uploadedFile.getFileName())));
      if (documentDetector != null) {
        try {
          return documentDetector.isSafe(uploadedFile.getInputStream());
        } catch (IOException e) {
          Ivy.log().error(e);
          return false;
        }
      }
      // File type doesn't support for scanning inside script
      else
        return true;
    }
    return false;
  }
}
