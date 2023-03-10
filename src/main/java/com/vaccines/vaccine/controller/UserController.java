package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.UserDTO;
import com.vaccines.vaccine.entity.ERole;
import com.vaccines.vaccine.entity.User;
import com.vaccines.vaccine.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController implements ServletContextAware {

    @Autowired
    UserService userService;
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

    @PostMapping()
    public void registration(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String password2,
                             @RequestParam String ime,
                             @RequestParam String prezime,
                             @RequestParam String JMBG,
                             @RequestParam String adresa,
                             @RequestParam String datumRodjenja,
                             HttpSession session, HttpServletResponse response) throws IOException, ParseException {

        User user;

        user = userService.findByEmail(email);

        if (user != null || !password.equals(password2)){
            session.setAttribute("message", "failure");
            response.sendRedirect(bURL+"registracija.html");
            return;
        }

        user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setIme(ime);
        user.setPrezime(prezime);
        user.setDatumRodjenja(new SimpleDateFormat("yyyy-dd-MM").parse(datumRodjenja));
        user.setJMBG(JMBG);
        user.setAdresa(adresa);
        user.setDatumReg(new Date());
        user.setUloga(ERole.PATIENTS);

        user = userService.save(user);

        if(user != null){
            session.setAttribute("user", user);
            session.setAttribute("role", user.getUloga().toString());
            session.setAttribute("message", "success");
        }

        response.sendRedirect(bURL);
    }
}
