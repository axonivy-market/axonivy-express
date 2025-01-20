package com.axonivy.utils.axonivyexpress.test.page;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.openqa.selenium.NoSuchElementException;

import com.axonivy.utils.axonivyexpress.test.common.FileHelper;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class ExpressManagementPage extends BasePage {

  @Override
  protected String getLoadedLocator() {
    return "form[id='express-form']";
  }

  public void uploadExpressJsonFile(String fileName) {
    openImportDialog();
    selectJSONFile(FileHelper.getAbsolutePathToTestFile(fileName));
    clickOnDeployExpress();
    closeImportDialog();
  }

  private void selectJSONFile(String pathToFile) {
    $("*[id$=':express-process-upload_input']").sendKeys(pathToFile);
    $$(".ui-fileupload-upload").shouldBe(CollectionCondition.size(1),
        DEFAULT_TIMEOUT);
  }

  private void openImportDialog() {
    $("[id='express-form:import-button']")
        .shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
    $("[id='import-express-dialog']").shouldBe(Condition.visible,
        DEFAULT_TIMEOUT);
  }

  private void closeImportDialog() {
    $("[id='import-express-dialog']").find("[id='close-import-express']")
        .shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
    $("[id='import-express-dialog']").shouldBe(Condition.disappear,
        DEFAULT_TIMEOUT);
  }


  public void clickOnDeployExpress() {
    $("[id='import-express-dialog']").$(".ui-fileupload-upload")
        .shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
    $("[id='import-express-dialog']").find("pre.express-import-result")
        .shouldBe(Condition.appear, DEFAULT_TIMEOUT);
  }

  public boolean hasExpressProcessWithName(String name) {
    $("[id='express-form:express-process-table']").shouldBe(Condition.visible,
        DEFAULT_TIMEOUT);
    try {
      SelenideElement processNameElem = $(
          "[id='express-form:express-process-table']")
          .findAll("tr.ui-widget-content .express-name").asDynamicIterable()
          .stream().filter(cell -> cell.getText().contentEquals(name))
          .findFirst().get();
      return processNameElem != null;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private SelenideElement openMenuByIndex(int index) {
    String menuQuery = String.format(
        "[id='express-form:express-process-table:%d:action-button']", index);
    $("[id='express-form:express-process-table']")
        .shouldBe(Condition.visible, DEFAULT_TIMEOUT).find(menuQuery)
        .shouldBe(getClickableCondition(), DEFAULT_TIMEOUT)
        .click();

    String menuPanelQuery = String.format(
        "[id='express-form:express-process-table:%d:action-menu']", index);
    return $(menuPanelQuery).shouldBe(Condition.appear, DEFAULT_TIMEOUT);
  }

  public void delete(int index) {
    openMenuByIndex(index).find("a[id$=':delete']")
        .shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();

    $("[id='express-form:remove-button']")
        .shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();

    $("[id='express-form:remove-button']")
        .shouldBe(Condition.disappear, DEFAULT_TIMEOUT);
  }

  public int countRows() {
    $("[id='express-form:express-process-table']").shouldBe(Condition.visible,
        DEFAULT_TIMEOUT);
    return $("[id='express-form:express-process-table']")
        .findAll("tr.ui-widget-content .express-name").size();
  }

  public WorkflowDefinitionPage edit(int index) {
    openMenuByIndex(index).find("a[id$=':edit']")
        .shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();

    return new WorkflowDefinitionPage();
  }

  public WorkflowDefinitionPage create() {
    $("[id='express-form:create-button'")
        .shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
    return new WorkflowDefinitionPage();
  }
}
