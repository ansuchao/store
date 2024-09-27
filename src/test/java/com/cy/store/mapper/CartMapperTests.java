package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTests {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert1(){
        Cart cart = new Cart();
        cart.setUid(9);
        cart.setPid(10000002);
        cart.setNum(2);
        cart.setPrice(1000L);
        cartMapper.insert1(cart);
    }
    @Test
    public void updateNumByCid(){
        cartMapper.updateNumByCid(1,4,"anime",new Date());
    }

    @Test
    public void findByUidAndPid(){
        Cart cart = cartMapper.findByUidAndPid(9,10000002);
        System.out.println(cart);
    }

    @Test
    public void findByUid(){
        System.out.println(cartMapper.findVoByUid(9));
    }

    @Test
    public void findByCid() {
        Integer cid = 6;
        Cart result = cartMapper.findByCid(cid);
        System.out.println(result);
    }

    @Test
    public void findVOByCids() {
        Integer[] cids = {1, 2, 6, 7, 8, 9, 10};
        List<CartVo> list = cartMapper.findVoByCid(cids);
        System.out.println("count=" + list.size());
        for (CartVo item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void deleteByCid() {
        Integer cid = 6;
        Integer rows = cartMapper.deleteByCid(cid);
        System.out.println(rows);
    }

    @Test
    public void deleteAllCid() {
        Integer[] list = {2,3};
        cartMapper.deleteAllCid(9, list);

    }
}
