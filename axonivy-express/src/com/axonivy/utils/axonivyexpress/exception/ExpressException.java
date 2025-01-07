package com.axonivy.utils.axonivyexpress.exception;

public class ExpressException extends RuntimeException {

  private static final long serialVersionUID = 8480668185478659714L;

  public ExpressException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExpressException(String message) {
    super(message);
  }

  public ExpressException(Throwable cause) {
    super(cause);
  }

}