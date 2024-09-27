package com.cy.store.service;

import com.cy.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {
    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("15556669999");
        address.setName("哈哈哈");
        addressService.addNewAddress(7,"管理员",address);

    }

    @Test
    public void setDefault() {
        addressService.setDefault(11,9,"admin01");

    }

    @Test
    public void delete(){
        addressService.delete(7,9,"name01");
    }
}
