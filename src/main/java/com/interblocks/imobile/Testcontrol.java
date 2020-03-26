package com.interblocks.imobile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class Testcontrol {
    @RequestMapping(method = RequestMethod.GET,path = "/t")
    public String test(){
        return "pp";
    }
}
