package com.syuez.springbootwebsocketstomp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/websocket")
public class IndexController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/world")
    public String world() {
        return "world";
    }
}
