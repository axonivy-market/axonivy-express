package com.axonivy.utils.axonivyexpress.test.common;

public class FileHelper {
  public static String getAbsolutePathToTestFile(String fileName) {
    return System.getProperty("user.dir") + "\\resources\\testFile\\"
        + fileName;
  }
}
