package com.axonivy.utils.axonivyexpress.test.common;

import org.openqa.selenium.Dimension;

import com.codeborne.selenide.WebDriverRunner;

public class ResizeUtils {

  public static void resizeBrowser(Dimension size) {
    WebDriverRunner.getWebDriver().manage().window().setSize(size);
  }
}
