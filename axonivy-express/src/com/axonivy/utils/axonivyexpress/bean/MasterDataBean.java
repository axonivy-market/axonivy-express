package com.axonivy.utils.axonivyexpress.bean;

import java.io.Serializable;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.io.FileUtils;

import com.axonivy.portal.components.enums.BasicDocumentType;
import com.axonivy.portal.components.enums.DocumentType;
import com.axonivy.utils.axonivyexpress.entity.MasterData;
import com.axonivy.utils.axonivyexpress.enums.AwesomeIcon;

import ch.ivyteam.ivy.environment.Ivy;

@ManagedBean
@SessionScoped
public class MasterDataBean implements Serializable {

  private static final long serialVersionUID = 6543050278853332982L;

  public AwesomeIcon[] getAwesomeIcons() {
    return AwesomeIcon.values();
  }

  public DocumentType[] getDocumentTypes() {
    return BasicDocumentType.values();
  }

  public Long getFileUploadSizeLimit() {
    return MasterData.getFileUploadSizeLimit();
  }

  public String getFileUploadInvalidSizeMessage() {
    return Ivy.cms().co(
        "/Dialogs/com/axonivy/portal/components/DocumentTable/ErrorFileUploadSize",
        Arrays.asList(
            FileUtils.byteCountToDisplaySize(getFileUploadSizeLimit())));
  }
}
