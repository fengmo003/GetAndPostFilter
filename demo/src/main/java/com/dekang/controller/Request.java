package com.dekang.controller;


import com.dekang.domain.Info;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class Request {
    @RequestMapping("/test")
    public Info getRquest(Info info) {
        Info info1 = new Info();
        info1.setName("hhhh");
        info1.setSex("1");
        System.out.println(info.toString());
        System.out.println(info1.toString());
        return info1;
    }

    @PostMapping("/test")
    public Info getRquest2(@RequestBody Info info) {
        Info info1 = new Info();
        info1.setName("hhhh");
        info1.setSex("1");
        System.out.println(info.toString());
        System.out.println(info1.toString());
        return info1;
    }

}
