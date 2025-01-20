package com.axonivy.utils.axonivyexpress.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.axonivy.utils.axonivyexpress.enums.TaskType;

import ch.ivyteam.ivy.environment.Ivy;

@ManagedBean
@RequestScoped
public class ApprovalFormBean implements Serializable {

  private static final long serialVersionUID = 5226396640717407908L;
  
  public String getTitle() {
    return String.format("%s - %s %s", Ivy.wfCase().names().current(), TaskType.APPROVAL.getLabel(), Ivy.wfTask().names().current());
  }

}
