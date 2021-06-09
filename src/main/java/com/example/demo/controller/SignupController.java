package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;


    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(){
        return "signup";

    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, RedirectAttributes redirectAttributes){
        String signupError = null;
        if(!userService.isUserAvailable(user.getUsername())){
            signupError = "The user already exists";

        }
        if(signupError == null){
            int rowAdded = userService.createUser(user);
            if(rowAdded < 0){
                signupError = "There was a error during signup process. Please try again!";
            }

            if(signupError == null){

                redirectAttributes.addFlashAttribute("signupSuccess", true);
                redirectAttributes.addFlashAttribute("successMessage", "Account created properly.");
                return "redirect:/login";
            }
        }
        redirectAttributes.addFlashAttribute("signupError",signupError);
        return "redirect:/signup";

    }
}
