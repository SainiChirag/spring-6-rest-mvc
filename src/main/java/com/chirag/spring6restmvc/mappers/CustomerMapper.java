package com.chirag.spring6restmvc.mappers;

import com.chirag.spring6restmvc.entity.Customer;
import com.chirag.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customertoCustomerDto(Customer customer);
}
