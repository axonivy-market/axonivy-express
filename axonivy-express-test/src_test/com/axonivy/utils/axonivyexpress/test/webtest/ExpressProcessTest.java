package com.axonivy.utils.axonivyexpress.test.webtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.WebAppFixture;
import com.axonivy.utils.axonivyexpress.test.common.ExpressResponsible;
import com.axonivy.utils.axonivyexpress.test.page.ExpressManagementPage;
import com.axonivy.utils.axonivyexpress.test.page.ExpressTaskPage;
import com.axonivy.utils.axonivyexpress.test.page.FormDefinitionPage;
import com.axonivy.utils.axonivyexpress.test.page.WorkflowDefinitionPage;

@IvyWebTest
public class ExpressProcessTest extends BaseTest {
  private static final int USER_TASK_INDEX = 0;
  private static final int APPROVAL_INDEX = 3;

  private static final int INPUT_TEXT_TYPE_INDEX = 0;
  private static final int INPUT_NUMBER_TYPE_INDEX = 1;

  @BeforeEach
  public void setup(WebAppFixture fixture) {
    fixture.login("express", "express");
    navigateToExpressManagementPage();
  }

  @Test
  public void testOneTimeWorkflow() {
    ExpressManagementPage page = new ExpressManagementPage();
    WorkflowDefinitionPage expressProcessPage = page.create();
    expressProcessPage.fillProcessProperties(true, true, "Test approval",
        "Test description");
    FormDefinitionPage formDefinition = configureExpressProcessWhenMultiApproval(
        expressProcessPage);
    formDefinition.executeWorkflow();
    executeExpressProcessWhenMultiApproval();
    page = navigateToExpressManagementPage();
    assertEquals(0, page.countRows());
  }

  @Test
  public void testCreateThenExecuteWorkflow() {
    ExpressManagementPage page = new ExpressManagementPage();
    WorkflowDefinitionPage expressProcessPage = page.create();
    expressProcessPage.fillProcessProperties(false, true, "Test approval",
        "Test description");
    FormDefinitionPage formDefinition = configureExpressProcessWhenMultiApproval(
        expressProcessPage);
    formDefinition.finishWorkflow();
    page = new ExpressManagementPage();
    assertTrue(page.hasExpressProcessWithName("Test approval"));
  }

  public ExpressResponsible setExpressResponsible(String userName,
      boolean isGroup) {
    ExpressResponsible user = new ExpressResponsible();
    user.setResponsibleName(userName);
    user.setIsGroup(isGroup);
    return user;
  }

  private FormDefinitionPage configureExpressProcessWhenMultiApproval(
      WorkflowDefinitionPage expressProcessPage) {
    ExpressResponsible responsible1 = setExpressResponsible(
        "express", false);
    ExpressResponsible responsible2 = setExpressResponsible(
        "testUser", false);

    expressProcessPage.createTask(0, USER_TASK_INDEX, "Task 1",
        "Task 1 description", Arrays.asList(responsible1, responsible2));

    expressProcessPage.addNewTask(0);
    expressProcessPage.createTask(1, APPROVAL_INDEX, "Task 2",
        "Task 2 description", Arrays.asList(responsible2));

    expressProcessPage.addNewTask(1);
    expressProcessPage.createTask(2, APPROVAL_INDEX, "Task 3",
        "Task 3 description", Arrays.asList(responsible1, responsible2));
    FormDefinitionPage formDefinition = expressProcessPage
        .goToFormDefinition();
    formDefinition.createTextInputField("Input Text 1", INPUT_TEXT_TYPE_INDEX,
        false);
    formDefinition.createTextInputField("Input Number 2",
        INPUT_NUMBER_TYPE_INDEX, false);
    formDefinition.countElementPrepareToDrag(2);
    formDefinition.moveAllElementToDragAndDrogPanel();
    formDefinition.countElementPrepareToDrag(0);
    return formDefinition;
  }

  private void executeExpressProcessWhenMultiApproval() {
    ExpressTaskPage expressTaskPage = new ExpressTaskPage();
    expressTaskPage.finish();
  }
}
