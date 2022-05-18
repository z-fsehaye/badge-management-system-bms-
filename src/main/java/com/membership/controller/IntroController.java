package com.membership.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/intro")
public class IntroController 
{

    @GetMapping("/allusers")
    public String allUsers() 
    {
        return "This content is an introduction information for all users of the Badge&Membership System";
    }
    @GetMapping("/publics")
    public String publics() 
    {
        return "This content is a public introduction information about Badge&Membership System ";
    }
    @GetMapping("/staff")
    public String staff() 
    {
        return "This content is an introduction information for all staff of Badge&Membership System ";
    }
}