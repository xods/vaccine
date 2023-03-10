package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.OrderDTO;
import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.Order;
import com.vaccines.vaccine.entity.User;
import com.vaccines.vaccine.entity.Vakcina;
import com.vaccines.vaccine.service.OrderService;
import com.vaccines.vaccine.service.UserService;
import com.vaccines.vaccine.service.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    VakcinaService vakcinaService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/adminOrders")
    public ResponseEntity<List<OrderDTO>> getAllAdmin(){
        List<Order> orders = orderService.findByStatusOrStatus(EStatus.CREATED, EStatus.CHANGED);

        List<OrderDTO> ordersDTO = new ArrayList<>();
        for(Order o : orders){
            ordersDTO.add(new OrderDTO(o));
        }

        return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
    }

    /*
    @GetMapping(value = "/staffOrders")
    public ResponseEntity<List<OrderDTO>> getAllStaff(){
        List<Order> orders = orderService.findByStatusOrStatus(EStatus.CREATED, EStatus.RETURNED);

        List<OrderDTO> ordersDTO = new ArrayList<>();
        for(Order o : orders){
            ordersDTO.add(new OrderDTO(o));
        }

        return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
    }*/

    @PostMapping(consumes = "application/json")
    public ResponseEntity<OrderDTO> add(@RequestBody OrderDTO orderDTO) {
        Order order = new Order();

        order.setKolicina(orderDTO.getKolicina());
        order.setRazlog(orderDTO.getRazlog());
        order.setDatumKreiranja(new Date());
        order.setStatus(EStatus.CREATED);

        Vakcina vakcina = vakcinaService.findById(orderDTO.getVakcina().getId()).orElse(new Vakcina());
        order.setVakcina(vakcina);

        User user = userService.getReferenceById(orderDTO.getKreator().getId());
        order.setKreator(user);

        order = orderService.save(order);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json", value = "/{id}")
    public ResponseEntity<OrderDTO> update(@RequestBody OrderDTO orderDTO, @PathVariable("id") Long id){
        Order order = orderService.findById(id).orElse(new Order());

        order.setStatus(orderDTO.getStatus());
        order.setNapomena(orderDTO.getNapomena());

        if (orderDTO.getStatus().equals(EStatus.APPROVED)){
            Vakcina vakcina = vakcinaService.findById(orderDTO.getVakcina().getId()).orElse(new Vakcina());
            vakcina.setKolicina(vakcina.getKolicina() + orderDTO.getKolicina());
            vakcinaService.save(vakcina);
        }
        orderService.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
