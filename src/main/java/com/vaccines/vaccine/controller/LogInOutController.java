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
import org.springframework.web.servlet.ModelAndView;

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
        }else {
            session.setAttribute("message", "Nep");
            response.sendRedirect(bURL + "log");
            return;
        }

        session.setAttribute("message", "");
        response.sendRedirect(bURL);
    }

    @GetMapping(value = "/odjava")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.setAttribute("user", null);
        session.setAttribute("role", null);
        session.setAttribute("message", "");
        response.sendRedirect(bURL);
    }

    @GetMapping(value = "")
    public ModelAndView login() {
        ModelAndView rez = new ModelAndView("login");
        return rez;
    }

    @GetMapping(value = "/reg")
    public ModelAndView reg() {
        ModelAndView rez = new ModelAndView("registracija");
        return rez;
    }
}
