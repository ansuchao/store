package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CartMapper {

    Integer insert1(Cart cart);

    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);
    //查询当前这个购物
    Cart findByUidAndPid(Integer uid, Integer pid);

    Integer deleteByCid(Integer cid);

    Integer deleteAllCid(@Param("uid")Integer uid, @Param("cidList")Integer[] cidss);

    List<CartVo> findVoByUid(Integer uid);

    Cart findByCid(Integer uid);

    List<CartVo> findVoByCid(Integer[] cids);

    void alterCid(Integer uid);

}
