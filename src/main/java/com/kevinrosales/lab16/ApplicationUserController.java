package com.kevinrosales.lab16;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException{
}

@Controller
public class ApplicationUserController {
    @Autowired
    public ApplicationUserRepository appUserRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showSplash(){
        return "index";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String showSignUp() { return "signUp"; }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getProfiles(Model m){
        m.addAttribute("profiles", appUserRepo.findAll());
        return "profiles";
    }

    @RequestMapping(value = "/users/{profileId}", method = RequestMethod.GET)
    public String getProfiles(@PathVariable long profileId, Model m){
        Optional<ApplicationUser> u =appUserRepo.findById(profileId);
        if (u.isPresent()){
            m.addAttribute("profile", u.get());
            return "individualProfile";
        }else{
            throw new ResourceNotFoundException();
        }
    }


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public RedirectView addUsers(@RequestParam String username, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String dateOfBirth, @RequestParam String bio) {

        String hashedPassword = bCryptPasswordEncoder.encode(password);
        ApplicationUser newUser = new ApplicationUser(username, hashedPassword, firstName, lastName, dateOfBirth, bio);

        appUserRepo.save(newUser);
        return new RedirectView("/users");
    }
}
