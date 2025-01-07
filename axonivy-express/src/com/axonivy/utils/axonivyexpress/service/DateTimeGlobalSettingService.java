package com.axonivy.utils.axonivyexpress.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ch.ivyteam.ivy.environment.Ivy;

public class DateTimeGlobalSettingService {

  private final String SPACE_CHARACTER = " ";
  private final String COMMA_CHARACTER = ",";
  private final String YEAR_PATTERN = "\\W?[Yy]+\\W?";
  private static final String HIDE_TIME = "Portal.DateTimeFormat.HideTime";
  private static final String HIDE_YEAR = "Portal.DateTimeFormat.HideYear";
  private static final String DATE_FILTER_WITH_TIME = "Portal.DateTimeFormat.DateFilterWithTime";

  private static DateTimeGlobalSettingService instance;

  public static DateTimeGlobalSettingService getInstance() {
    if (instance == null) {
      instance = new DateTimeGlobalSettingService();
    }
    return instance;
  }

  public String getGlobalDateTimePattern() {
    return isTimeHidden() ? getDatePattern() : getDateTimePattern();
  }

  public boolean isTimeHidden() {
    String dateTimeGlobalSetting = Ivy.var().get(HIDE_TIME);
    return Boolean.valueOf(dateTimeGlobalSetting);
  }

  public String getDatePattern() {
    return getDatePatternByYearSetting();
  }

  public String getDateTimePattern() {
    return getDatePatternByYearSetting() + SPACE_CHARACTER
        + Ivy.cms().co("/patterns/timePattern");
  }

  private String getDatePatternByYearSetting() {
    return isYearHidden() ? getDateWithoutYearPattern(getDefaultDatePattern())
        : getDefaultDatePattern();
  }

  private String getDateWithoutYearPattern(String pattern) {
    String expectedPattern = pattern.replaceAll(YEAR_PATTERN, "").trim();
    return expectedPattern.endsWith(COMMA_CHARACTER)
        ? expectedPattern.substring(0, expectedPattern.length() - 1)
        : expectedPattern;
  }

  private boolean isYearHidden() {
    return Boolean.parseBoolean(Ivy.var().get(HIDE_YEAR));
  }

  private String getDefaultDatePattern() {
    return ((SimpleDateFormat) getDefaultDateFormatter()).toPattern();
  }

  public DateFormat getDefaultDateFormatter() {
    return DateFormat.getDateInstance(DateFormat.MEDIUM,
        Ivy.session().getFormattingLocale());
  }

  public boolean isDateFilterWithTime() {
    String dateFilterGlobalSetting = Ivy.var().get(DATE_FILTER_WITH_TIME);
    return Boolean.valueOf(dateFilterGlobalSetting);
  }
}
