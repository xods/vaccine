package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.entity.User;
import com.vaccines.vaccine.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import java.io.IOException;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/log")
public class LogInOutController implements ServletContextAware {

    @Autowired
    UserService service;
    @Autowired
    ServletContext servletContext;
    private  String bURL;

    @PostConstruct
    public void init() {
        bURL = servletContext.getContextPath()+"/";
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @PostMapping(value = "/prijava")
    public void logIn(@RequestParam String email, @RequestParam String password, HttpSession session, HttpServletResponse response) throws IOException {
        User user = service.findByEmailAndPassword(email, password);

        if(user != null){
            session.setAttribute("user", user);
            session.setAttribute("role", user.getUloga().toString());
        }

        response.sendRedirect(bURL);
    }

    @GetMapping(value = "/odjava")
    public String logout(HttpSession session){
        session.setAttribute("user", null);
        return "index";
    }
}
