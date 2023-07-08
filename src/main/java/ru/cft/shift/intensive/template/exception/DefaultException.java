package ru.cft.shift.intensive.template.exception;

public class DefaultException extends RuntimeException{
  public DefaultException() {
    super("api.server.error");
  }
}
