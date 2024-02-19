package com.example.dbankingbackend.mappers;

import com.example.dbankingbackend.dto.CustomerDTO;
import com.example.dbankingbackend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
//MapStruct: autre outils pour faire le mapping
@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO); // il transformer de customer vers cutomerDTO il rempilier les attribue existe dans cutomerDTO
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return  customer;
    }
}
