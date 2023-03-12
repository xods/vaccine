package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.OrderDTO;
import com.vaccines.vaccine.dto.VakcinaDTO;
import com.vaccines.vaccine.entity.*;
import com.vaccines.vaccine.service.OrderService;
import com.vaccines.vaccine.service.UserService;
import com.vaccines.vaccine.service.VakcinaService;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController implements ServletContextAware {

    @Autowired
    OrderService orderService;
    @Autowired
    VakcinaService vakcinaService;
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

    @GetMapping(value = "/")
    public ModelAndView addOrder(HttpSession session, HttpServletResponse response) throws IOException {

        if(session.getAttribute("user") == null || session.getAttribute("role") != ERole.STAFF.toString()){
            response.sendRedirect(bURL);
        }

        ModelAndView rezultat = new ModelAndView("orderAdd");

        List<Vakcina> vakcine = vakcinaService.findAll();
        List<VakcinaDTO> vakcineDTO = new ArrayList<>();
        for (Vakcina v : vakcine) {
            vakcineDTO.add(new VakcinaDTO(v));
        }

        rezultat.addObject("vakcine", vakcineDTO);

        return rezultat;
    }

    @GetMapping(value = "/svi")
    public void redirekt(HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") == ERole.PATIENTS.toString()){
            response.sendRedirect(bURL);
        }

        if (session.getAttribute("role") == ERole.ADMIN.toString()){
            response.sendRedirect(bURL + "order/adminOrders");
        }else {
            response.sendRedirect(bURL + "order/staffOrders");
        }
    }

    @GetMapping(value = "/adminOrders")
    public ModelAndView getAllAdmin(HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") != ERole.ADMIN.toString()){
            response.sendRedirect(bURL);
        }

        List<Order> ordersCr = orderService.findByStatusOrStatus(EStatus.CREATED, EStatus.CHANGED);

        ModelAndView rez = new ModelAndView("orderi");

        List<OrderDTO> ordersDTOCr = new ArrayList<>();
        for(Order o : ordersCr){
            ordersDTOCr.add(new OrderDTO(o));
        }
        rez.addObject("ordersCr", ordersDTOCr);


        return rez;
    }

    @GetMapping(value = "/staffOrders")
    public ModelAndView getAllStaff(HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") != ERole.STAFF.toString()){
            response.sendRedirect(bURL);
        }
        User user = (User) session.getAttribute("user");

        List<Order> ordersCr = orderService.findByKreator_IdAndStatusOrStatus(user.getId(), EStatus.CREATED, EStatus.CHANGED);
        List<Order> ordersRe = orderService.findByKreator_IdAndStatus(user.getId(), EStatus.RETURNED);
        List<Order> ordersRj = orderService.findByKreator_IdAndStatus(user.getId(), EStatus.REJECTED);

        ModelAndView rez = new ModelAndView("orderi");

        List<OrderDTO> ordersDTOCr = new ArrayList<>();
        for(Order o : ordersCr){
            ordersDTOCr.add(new OrderDTO(o));
        }
        rez.addObject("ordersCr", ordersDTOCr);

        List<OrderDTO> ordersDTORe = new ArrayList<>();
        for(Order o : ordersRe){
            ordersDTORe.add(new OrderDTO(o));
        }
        rez.addObject("ordersRe", ordersDTORe);

        List<OrderDTO> ordersDTORj = new ArrayList<>();
        for(Order o : ordersRj){
            ordersDTORj.add(new OrderDTO(o));
        }
        rez.addObject("ordersRj", ordersDTORj);

        return rez;
    }

    @PostMapping(value = "/")
    public void add(@RequestParam String vId,
                                        @RequestParam String kolicina,
                                        @RequestParam String razlog,
                                        HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") != ERole.STAFF.toString()){
            response.sendRedirect(bURL);
        }
        Order order = new Order();

        order.setKolicina(Integer.valueOf(kolicina));
        order.setRazlog(razlog);
        order.setDatumKreiranja(new Date());
        order.setStatus(EStatus.CREATED);

        Vakcina vakcina = vakcinaService.findById(Long.valueOf(vId)).orElse(new Vakcina());
        order.setVakcina(vakcina);

        User user = (User) session.getAttribute("user");
        user = userService.getReferenceById(user.getId());
        order.setKreator(user);

        orderService.save(order);

        response.sendRedirect(bURL + "order/svi");
    }

    @GetMapping(value = "/{id}")
    public ModelAndView update(@PathVariable("id") String id, HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") == ERole.PATIENTS.toString()){
            response.sendRedirect(bURL);
        }
        ModelAndView rez = new ModelAndView("order");

        Optional<Order> order = orderService.findById(Long.valueOf(id));
        if (order.isEmpty()){
            response.sendRedirect(bURL + "order/svi");
        }
        rez.addObject("order", new OrderDTO(order.get()));

        return rez;
    }
    @PostMapping(value = "/{id}")
    public void update(@PathVariable("id") String id,
                       @RequestParam @Nullable String kolicina,
                       @RequestParam @Nullable String status,
                       @RequestParam @Nullable String napomena,
                       HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") == ERole.PATIENTS.toString()){
            response.sendRedirect(bURL);
        }

        Order order = orderService.findById(Long.valueOf(id)).orElse(new Order());

        if(status != null){
            order.setStatus(EStatus.valueOf(status));
        }

        if (kolicina != null){
            order.setKolicina(Integer.valueOf(kolicina));
        }

        if(napomena != null){
            order.setNapomena(napomena);
        }

        if (EStatus.APPROVED.toString().equals(status)){
            Vakcina vakcina = vakcinaService.findById(order.getVakcina().getId()).orElse(new Vakcina());
            vakcina.setKolicina(vakcina.getKolicina() + order.getKolicina());
            vakcinaService.save(vakcina);
        }
        if (session.getAttribute("role") == ERole.STAFF.toString()){
            order.setStatus(EStatus.CHANGED);
        }
        orderService.save(order);

        response.sendRedirect(bURL + "order/svi");
    }
}
