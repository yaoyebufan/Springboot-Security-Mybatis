package com.example.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class indexController {
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    private String index() {
        return "index";
    }

    /*表单提交(POST请求，没有写这个控制层，security帮我们自动完成)得是login不然会跳到该处理，导致无法验证*/
    @RequestMapping(value = "toLogin", method = RequestMethod.GET)
    private String login() {
        return "login";
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    private String user() {
        return "user";
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    private String admin() {
        return "admin";
    }
}
