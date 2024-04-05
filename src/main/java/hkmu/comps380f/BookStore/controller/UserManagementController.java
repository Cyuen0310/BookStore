package hkmu.comps380f.BookStore.controller;

import hkmu.comps380f.BookStore.dao.BookUserRepository;
import hkmu.comps380f.BookStore.dao.BookUserService;
import hkmu.comps380f.BookStore.dao.UserManagementService;
import hkmu.comps380f.BookStore.model.BookUser;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserManagementController {

    public static class Form {
        private String username;
        private String password;
        private String[] roles;

        // getters and setters for all properties
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }

    @Resource
    UserManagementService umService;

    @Resource
    BookUserService buService;

    @Resource
    BookUserRepository uRepo;

    @GetMapping({"", "/", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("bookUsers", umService.getBookUsers());
        return "listuser";
    }



    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "BookUser", new Form());
    }

    @PostMapping("/create")
    public String create(Form form) throws IOException {
        umService.createBookUser(form.getUsername(), form.getPassword(), form.getRoles());
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        umService.delete(username);
        return "redirect:/user/list";
    }

    //TASK 7
    @GetMapping("/edit/{username}")
    public ModelAndView userEditForm(@PathVariable("username") String username, Principal principal, HttpServletRequest request) {
        BookUser user = umService.getBookUser(username);
        if (user == null || !(request.isUserInRole("ROLE_ADMIN")) && !principal.getName().equals(user.getUsername())){
            return new ModelAndView( new RedirectView("/book/list",true) );
        }
        ModelAndView modelAndView = new ModelAndView("editUser");
        modelAndView.addObject("user",user);
        Form UserForm = new Form();
        UserForm.setUsername(user.getUsername());
        UserForm.setPassword(user.getPassword());
        modelAndView.addObject("userForm",UserForm);
        return modelAndView;
    }

    //TASK 7
    @PostMapping("/edit/{username}")
    public String editUser(@PathVariable("username") String username, Form form, Principal principal, HttpServletRequest request){
        BookUser user = umService.getBookUser(username);
        if (user == null || !(request.isUserInRole("ROLE_ADMIN")) && !principal.getName().equals(user.getUsername())){
            return "redirect:/user";
        }
        umService.updateBookUser(username,form.getUsername(), form.getPassword());
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/user/list";
        }
        return "redirect:/book";


    }

}