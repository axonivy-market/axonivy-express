package com.axonivy.utils.axonivyexpress.test.page;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;

import java.time.Duration;

import org.openqa.selenium.By;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;

public abstract class BasePage {
  protected static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);
  public static final String CLASS_PROPERTY = "class";

  protected BasePage() {
    waitPageLoaded();
  }

  public void waitPageLoaded() {
    $(getLoadedLocator()).shouldBe(appear, DEFAULT_TIMEOUT);
  }

  /**
   * This abstract method is used to determine identity of a page.
   * 
   * @return A unique CSS selector for the particular page.
   */
  protected abstract String getLoadedLocator();

  protected WebElementCondition getClickableCondition() {
    return Condition.and("should be clickable", Condition.visible, Condition.exist);
  }

  public SelenideElement findElementById(String selector) {
    return $(String.format("[id$='%s']", selector)).shouldBe(appear,
        DEFAULT_TIMEOUT);
  }

  public void waitForElementClickableThenClick(SelenideElement element) {
    element.shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
  }

  public void waitForElementClickableThenClick(By by) {
    $(by).shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
  }

  public void waitForElementDisplayed(By element, boolean expected) {
    if (expected) {
      $(element).shouldBe(appear, DEFAULT_TIMEOUT);
    } else {
      $(element).shouldBe(disappear, DEFAULT_TIMEOUT);
    }
  }

  public boolean isElementPresent(By element) {
    return $(element).is(Condition.visible);
  }
}
