package com.axonivy.utils.axonivyexpress.mail;

import com.axonivy.utils.axonivyexpress.entity.ExpressUserEmail;

import ch.ivyteam.ivy.environment.Ivy;

public class InformationMailSender {

  public void send(ExpressUserEmail mail) {
    try {
      ExpressMailClient.send(mail);
    } catch (Exception e) {
      Ivy.log().error(e);
    }
  }

}