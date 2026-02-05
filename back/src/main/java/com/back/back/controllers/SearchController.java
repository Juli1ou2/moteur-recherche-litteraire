package com.back.back.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @GetMapping("")
    public String index() {
        return "Hello World !";
    }

    @PostMapping("")
    public String regex(@RequestBody Map<String, String> body) {
        String var = body.get("regex");
        return "Hello World !";
    }

}
