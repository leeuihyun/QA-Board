package com.example.board.dto;

public class BoardResponseMessage<T> extends ResponseMessage {

  private T data;

  public BoardResponseMessage(String message, boolean success) {
    super(message, success);
  }

  public BoardResponseMessage(String message, T data, boolean success) {
    super(message, success);
    this.data = data;
  }
}
