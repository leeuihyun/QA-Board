package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

  @GetMapping("/admin")
  public String adminP() {
    System.out.println("admin");
    return "AdminController";
  }
}
