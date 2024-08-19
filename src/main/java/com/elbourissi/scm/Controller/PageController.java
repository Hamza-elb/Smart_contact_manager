package com.elbourissi.scm.Controller;

import com.elbourissi.scm.Entity.User;
import com.elbourissi.scm.Forms.UserForm;
import com.elbourissi.scm.Helper.MessageHelper;
import com.elbourissi.scm.Helper.MessageType;
import com.elbourissi.scm.Service.Impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
public class PageController {
    private final UserServiceImpl userService;

    public PageController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage(Model model) {
        return "services";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    // Register Processing
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processingRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, HttpSession session) {
        System.out.println(userForm);
        // fetch form data
        // userForm
        // validate form data
        if (bindingResult.hasErrors()) {
            return "register";
        }
        //save to database
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic(
                "https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75");
        User savedUser = userService.save(user);
        log.info("Saved user: {}", savedUser);
        // message "Register success"
        MessageHelper message = MessageHelper.builder()
                .content("Registered Successfully")
                .type(MessageType.green)
                .build();
        session.setAttribute("message", message);
        // redirect login page
        return "redirect:/register";
    }
}
