package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.entity.Customer;
import com.chirag.spring6restmvc.exception.NotFoundException;
import com.chirag.spring6restmvc.model.CustomerDTO;
import com.chirag.spring6restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("customerId") UUID customerId) {
        CustomerDTO customerDTO = customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customerDTO);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    // udpate
    @PutMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO,
                                                      @PathVariable(name = "customerId") UUID customerId) {
        customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
        customerService.updateCustomerById(customerId, customerDTO);
        return new ResponseEntity<>(HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }


    // delete

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable(name = "customerId") UUID customerId) {
        customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }


}
