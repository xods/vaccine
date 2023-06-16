package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.UserDTO;
import com.vaccines.vaccine.entity.ERole;
import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.User;
import com.vaccines.vaccine.entity.VakcinaPacijenta;
import com.vaccines.vaccine.service.UserService;
import com.vaccines.vaccine.service.VakcinaPacijentaService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController implements ServletContextAware {

    @Autowired
    UserService userService;
    @Autowired
    VakcinaPacijentaService vakcinaPacijentaService;
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

    @GetMapping()
    public ModelAndView getUser(HttpSession session, HttpServletResponse response) throws IOException {

        if(session.getAttribute("user") == null){
            session.setAttribute("message", "Nod");
            response.sendRedirect(bURL);
        }

        ModelAndView rez = new ModelAndView("profil");

        User user = (User) session.getAttribute("user");
        rez.addObject("kor", user);

        List<VakcinaPacijenta> primljene = vakcinaPacijentaService.findByUser_IdAndStatusOrderByDatumVakcinacijeAsc(user.getId(), EStatus.APPROVED);
        rez.addObject("primljene", primljene);

        List<VakcinaPacijenta> aktivne = vakcinaPacijentaService.findByUser_IdAndStatusOrderByDatumVakcinacijeAsc(user.getId(), EStatus.CREATED);
        rez.addObject("aktivne", aktivne);

        session.setAttribute("message", "");
        return rez;
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
            session.setAttribute("message", "Nep");
            response.sendRedirect(bURL+"log/reg");
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

        session.setAttribute("message", "");
        response.sendRedirect(bURL);
    }

    @PostMapping("/{email}")
    public void edit(@PathVariable("email") String email,
                     @RequestParam String password,
                     @RequestParam String password2,
                     @RequestParam String ime,
                     @RequestParam String prezime,
                     @RequestParam String JMBG,
                     @RequestParam String adresa,
                     @RequestParam String datumRodjenja,
                     HttpSession session, HttpServletResponse response) throws IOException, ParseException {

        User user = (User) session.getAttribute("user");

        if (user == null || !password.equals(password2)){
            session.setAttribute("message", "Nep");
            response.sendRedirect(bURL+"registracija.html");
            return;
        }

        if(!password.equals("")){
            user.setPassword(password);
        }

        user.setIme(ime);
        user.setPrezime(prezime);
        user.setDatumRodjenja(new SimpleDateFormat("yyyy-dd-MM").parse(datumRodjenja));
        user.setJMBG(JMBG);
        user.setAdresa(adresa);
        user.setDatumReg(new Date());

        user = userService.save(user);

        if(user != null){
            session.setAttribute("user", user);
            session.setAttribute("role", user.getUloga().toString());
            session.setAttribute("message", "success");
        }

        session.setAttribute("message", "");
        response.sendRedirect(bURL);
    }
}
