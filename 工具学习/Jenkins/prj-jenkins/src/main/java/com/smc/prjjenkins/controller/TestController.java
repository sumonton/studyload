package com.smc.prjjenkins.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2025/5/2
 * @Author smc
 * @Description:
 */
@RestController
@RequestMapping("/api")
public class TestController {

 @GetMapping("/hello")
 public String hello() {
  return "Hello, World!";
 }
}