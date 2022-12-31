package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.OrderDTO;
import com.vaccines.vaccine.dto.UserDTO;
import com.vaccines.vaccine.dto.VakcinaDTO;
import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.Order;
import com.vaccines.vaccine.entity.User;
import com.vaccines.vaccine.entity.Vakcina;
import com.vaccines.vaccine.repository.OrderRepository;
import com.vaccines.vaccine.repository.UserRepository;
import com.vaccines.vaccine.repository.VakcinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    VakcinaRepository vakcinaRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/")
    public ResponseEntity<List<OrderDTO>> getAllAdmin(){
        List<Order> orders = orderRepository.findByStatusOrStatus(EStatus.CREATED, EStatus.CHANGED);

        List<OrderDTO> ordersDTO = new ArrayList<>();
        for(Order o : orders){
            ordersDTO.add(new OrderDTO(o));
        }

        return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
    }

    /*
    @GetMapping(value = "/")
    public ResponseEntity<List<OrderDTO>> getAllStaff(){
        List<Order> orders = orderRepository.findByStatusOrStatus(EStatus.CREATED, EStatus.RETURNED);

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

        Vakcina vakcina = vakcinaRepository.findById(orderDTO.getVakcina().getId()).get();
        order.setVakcina(vakcina);

        User user = userRepository.getReferenceById(orderDTO.getKreator().getId());
        order.setKreator(user);

        order = orderRepository.save(order);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.CREATED);
    }

    @PostMapping(consumes = "application/json", value = "/{id}")
    public ResponseEntity<OrderDTO> update(@RequestBody OrderDTO orderDTO, @PathVariable("id") Long id){
        Order order = orderRepository.findById(id).get();

        order.setStatus(orderDTO.getStatus());
        order.setNapomena(orderDTO.getNapomena());

        if (orderDTO.getStatus().equals(EStatus.APPROVED)){
            Vakcina vakcina = vakcinaRepository.findById(orderDTO.getVakcina().getId()).get();
            vakcina.setKolicina(vakcina.getKolicina() + orderDTO.getKolicina());
            vakcinaRepository.save(vakcina);
        }
        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
