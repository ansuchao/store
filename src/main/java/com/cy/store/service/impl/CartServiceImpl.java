package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.ex.*;
import com.cy.store.vo.CartVo;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;


    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        // 根据参数pid和uid查询购物车中的数据
        Cart result = cartMapper.findByUidAndPid(uid,pid);
        Date date = new Date();
        // 判断查询结果是否为null
        if (result  == null){
            // 是：表示该用户并未将该商品添加到购物车
            // 创建Cart对象
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            //补全价格:
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());

            cart.setCreatedTime(date);
            cart.setCreatedUser(username);
            cart.setModifiedTime(date);
            cart.setModifiedUser(username);

            Integer rows = cartMapper.insert1(cart);
            if (rows != 1){
                throw new InsertException("插入商品数据时出现未知错误，请联系系统管理员");
            }
        }else {
            // 否：表示该用户的购物车中已有该商品
            // 从查询结果中获取购物车数据的id
           Integer num = result.getNum() + amount;
           Integer rows = cartMapper.updateNumByCid(result.getCid(),num,username,date);
           if (rows != 1){
               throw new UpdateException("修改商品数量时出现未知错误，请联系系统管理员");
           }
        }
    }

    @Override
    public List<CartVo> getVOByUid(Integer uid) {
        return cartMapper.findVoByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null){
            throw new CartNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid,num,username,new Date());
        if (rows != 1){
            throw new UpdateException("更新数据失败");
        }
        return num;
    }

    @Override
    public Integer subNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null){
            throw new CartNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum() - 1;
        Integer rows = cartMapper.updateNumByCid(cid,num,username,new Date());
        if (rows != 1){
            throw new UpdateException("更新数据失败");
        }
        return num;
    }

    @Override
    public List<CartVo> getVoByCid(Integer uid, Integer[] cids) {
        List<CartVo> list = cartMapper.findVoByCid(cids);
        Iterator<CartVo> it = list.iterator();
        while (it.hasNext()){
            CartVo cartVo = it.next();
            if (!cartVo.getUid().equals(uid)){
                list.remove(cartVo);
            }
        }
        return list;
    }

    @Override
    public void deleteByCid(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null){
            throw new CartNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }


        Integer row = cartMapper.deleteByCid(cid);
        if (row != 1){
            throw new DeleteException("删除数据产生未知的异常");
        }
    }

    @Override
    public Integer deleteAllCid(Integer uid,Integer[] cidss) {
        Integer row = cartMapper.deleteAllCid(uid,cidss);
        if (row == 0){
            throw new CartNotFoundException("所选数据不存在");
        }
        if (StringUtils.isNotBlank(String.valueOf(uid))){
            throw new AccessDeniedException("数据非法访问");
        }
        
        return row;
    }


}
