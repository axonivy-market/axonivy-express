package com.axonivy.utils.axonivyexpress.bean;

import java.io.Serializable;
import java.util.Optional;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.vars.Variable;

@ManagedBean
@ViewScoped
public class VisibilityBean implements Serializable {

  private static final long serialVersionUID = 3545423773483684349L;
  private static final String SHOW_BUTTON_ICON = "Portal.ShowButtonIcon";

  public boolean isShowButtonIcon() {
    String valueStr = Ivy.var().get(SHOW_BUTTON_ICON);
    valueStr = StringUtils.isEmpty(valueStr)
        ? Optional.ofNullable(Ivy.var().variable(SHOW_BUTTON_ICON))
            .map(Variable::defaultValue).orElse("true")
        : valueStr;

    return Boolean.valueOf(valueStr);
  }

  public String generateButtonIcon(String iconClass) {
    return isShowButtonIcon() ? iconClass : StringUtils.EMPTY;
  }
}