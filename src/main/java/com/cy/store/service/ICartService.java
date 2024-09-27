package com.cy.store.service;

import com.cy.store.vo.CartVo;

import java.util.List;

public interface ICartService {

    void addToCart(Integer uid,Integer pid,Integer amount,String username);

    List<CartVo> getVOByUid(Integer uid);

    Integer addNum(Integer cid,Integer uid,String username);

    Integer subNum(Integer cid,Integer uid,String username);

    List<CartVo> getVoByCid(Integer uid, Integer[] cids);

    void deleteByCid(Integer cid,Integer uid,String username);

   Integer deleteAllCid(Integer uid,Integer[] cidss);


}
