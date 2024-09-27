package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(7);
        address.setPhone("1555666");
        address.setName("啊哈哈哈");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid(){
       Integer count = addressMapper.countByUid(7);
        System.out.println(count);

    }

    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(9);
        System.out.println(list);
    }

    @Test
    public void findByAid(){
        System.out.println(addressMapper.findByAid(11));
    }
    @Test
    public void updateNonDefault(){
          addressMapper.updateNonDefault(9);
    }
    @Test
    public void updateDefaultByAid(){
         addressMapper.updateDefaultByAid(11,"超哥",new Date());
    }

    @Test
    public void deleteByAid(){
        addressMapper.deleteByAid(4);
    }
    @Test
    public void findLastModified(){
        System.out.println(addressMapper.findLastModified(9));
    }

}
