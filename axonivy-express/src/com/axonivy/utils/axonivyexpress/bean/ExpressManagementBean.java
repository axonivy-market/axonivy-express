package com.axonivy.utils.axonivyexpress.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.axonivy.portal.components.publicapi.PortalNavigatorInFrameAPI;
import com.axonivy.portal.components.util.FacesMessageUtils;
import com.axonivy.portal.components.util.ProcessStartUtils;
import com.axonivy.utils.axonivyexpress.entity.ExpressProcess;
import com.axonivy.utils.axonivyexpress.entity.SecurityMemberDTO;
import com.axonivy.utils.axonivyexpress.enums.ExpressMessageType;
import com.axonivy.utils.axonivyexpress.navigator.ExpressNavigator;
import com.axonivy.utils.axonivyexpress.service.ExpressProcessService;
import com.axonivy.utils.axonivyexpress.service.SecurityService;
import com.axonivy.utils.axonivyexpress.utils.ExpressManagementUtils;
import com.axonivy.utils.axonivyexpress.utils.PermissionUtils;
import com.axonivy.utils.axonivyexpress.utils.UploadDocumentUtils;
import com.axonivy.utils.axonivyexpress.utils.UserUtils;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.IUser;
import ch.ivyteam.ivy.security.exec.Sudo;

@ManagedBean
@ViewScoped
public class ExpressManagementBean implements Serializable {

  private static final long serialVersionUID = 8650690997206742678L;

  private List<ExpressProcess> processes;
  private ExpressProcess selectedProcess;
  private UploadedFile importExpressFile;
  private String importOutput;
  private String importStatus;
  private FacesMessage validateMessage;

  private List<SecurityMemberDTO> activeMemberList;

  public void init() {
    processes = Sudo.get(() -> {
      return ExpressManagementUtils.findExpressProcesses();
    });

    activeMemberList = SecurityService.newInstance().findSecurityMembers("", 0,
        -1);
  }

  public List<ExpressProcess> getProcesses() {
    return processes;
  }

  public void setProcesses(List<ExpressProcess> processes) {
    this.processes = processes;
  }

  public ExpressProcess getSelectedProcess() {
    return selectedProcess;
  }

  public void setSelectedProcess(ExpressProcess selectedProcess) {
    this.selectedProcess = selectedProcess;
  }

  public void navigateToExpressProcessModificationPage(ExpressProcess process) {
    ExpressNavigator.navigateToEditWorkflow(process);
  }

  public void navigateToCreateExpressWorkflow() {
    ExpressNavigator.navigateToCreateWorkflow();
  }

  public void startExpressProcessWithinPortal(ExpressProcess process)
      throws IOException {
    String startLink = ExpressNavigator.getExpressStartLink(process);
    PortalNavigatorInFrameAPI.navigateToUrl(startLink);
  }

  public void startExpressProcess(ExpressProcess process) throws IOException {
    String startLink = ExpressNavigator.getExpressStartLink(process);
    ProcessStartUtils.redirect(startLink);
  }

  public void deleteExpressProcess() {
    ExpressProcessService.getInstance().delete(selectedProcess.getId());
    processes.remove(selectedProcess);
    selectedProcess = null;
  }

  public void onChooseExpressProcess(ExpressProcess process) {
    this.selectedProcess = process;
  }

  public StreamedContent exportToJsonFile(ExpressProcess process) {
    return ExpressManagementUtils
        .exportExpressProcess(Arrays.asList(process));
  }

  public void importExpress(FileUploadEvent event) {
    importExpressFile = event.getFile();
    String validateStr = UploadDocumentUtils
        .validateUploadedFile(importExpressFile);
    if (StringUtils.isNotEmpty(validateStr)) {
      setValidateMessage(FacesMessageUtils
          .sanitizedMessage(FacesMessage.SEVERITY_ERROR, validateStr, null));
      displayedMessage();
    } else {
      importExpressProcesses();
    }
  }

  private void displayedMessage() {
    FacesContext.getCurrentInstance().addMessage(
        "import-express-form:import-express-dialog-message", validateMessage);
    importStatus = ExpressMessageType.FAILED.getLabel();
  }

  @SuppressWarnings("unchecked")
  private void importExpressProcesses() {
    Map<ExpressMessageType, Object> results = ExpressManagementUtils
        .importExpressProcesses(importExpressFile);
    try {
      importStatus = results.get(ExpressMessageType.IMPORT_STATUS).toString();
      importOutput = results.get(ExpressMessageType.IMPORT_RESULT).toString();
      if (!importStatus
          .equalsIgnoreCase(ExpressMessageType.FAILED.getLabel())) {
        processes.addAll((List<ExpressProcess>) results
            .get(ExpressMessageType.IMPORT_EXPRESS_PROCESSES));
      }
    } catch (Exception e) {
      importStatus = ExpressMessageType.FAILED.getLabel();
      importOutput = e.getMessage();
    }
  }

  /**
   * Get a display name by activator name
   *
   * @param activatorName
   * @return display name
   */
  public String getUserDisplayName(String activatorName) {
    if (StringUtils.isBlank(activatorName)) {
      return Ivy.cms().co("/Labels/NotAvailable");
    }

    String displayName = activatorName;
    if (CollectionUtils.isNotEmpty(activeMemberList)) {
      Optional<SecurityMemberDTO> activeUser = activeMemberList.stream()
          .filter(user -> user.getMemberName().equalsIgnoreCase(activatorName))
          .findFirst();
      if (activeUser.isPresent()) {
        displayName = StringUtils.isBlank(activeUser.get().getDisplayName())
            ? activeUser.get().getName()
            : activeUser.get().getDisplayName();
      } else {
        displayName = Ivy.cms()
            .co("/Labels/NotAvailable");
      }
    } else {
      IUser user = UserUtils.findUser(activatorName);
      displayName = StringUtils.isBlank(user.getDisplayName()) ? user.getName()
          : user.getDisplayName();
    }
    return displayName;
  }

  public boolean canStartProcess(ExpressProcess process) {
    return canEditProcess(process) && process.isReadyToExecute();
  }

  public boolean canEditProcess(ExpressProcess process) {
    return PermissionUtils
        .checkAbleToStartAndAbleToEditExpressWorkflow(process);
  }

  public UploadedFile getImportExpressFile() {
    return importExpressFile;
  }

  public void setImportExpressFile(UploadedFile importExpressFile) {
    this.importExpressFile = importExpressFile;
  }

  public String getImportOutput() {
    return importOutput;
  }

  public void setImportOutput(String importOutput) {
    this.importOutput = importOutput;
  }

  public String getImportStatus() {
    return importStatus;
  }

  public void setImportStatus(String importStatus) {
    this.importStatus = importStatus;
  }

  public FacesMessage getValidateMessage() {
    return validateMessage;
  }

  public void setValidateMessage(FacesMessage validateMessage) {
    this.validateMessage = validateMessage;
  }
}
