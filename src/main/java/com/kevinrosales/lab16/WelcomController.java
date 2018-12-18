package com.kevinrosales.lab16;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomController {

    //  setting the default route to not be more welcoming
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(){
//  this will display the string below on the homepage route
        return "Welcome to the home page!";
    }
}
