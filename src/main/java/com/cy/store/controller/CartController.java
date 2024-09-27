package com.cy.store.controller;

import com.cy.store.service.ICartService;
import com.cy.store.service.ex.CartNotFoundException;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartVo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController{
    @Autowired
    private ICartService cartService;

    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session){
        cartService.addToCart(getuidFromSession(session),pid,amount,getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping({"","/"})
    public JsonResult<List<CartVo>> getVoByUid(HttpSession session){
        List<CartVo> data = cartService.getVOByUid(getuidFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid,HttpSession session){
        Integer data = cartService.addNum(cid,getuidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("{cid}/num/sub")
    public JsonResult<Integer> subNum(@PathVariable("cid") Integer cid,HttpSession session){
        Integer data = cartService.subNum(cid,getuidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("{cid}/delete")
    public JsonResult<Void> deleteByCid(@PathVariable("cid") Integer cid,HttpSession session){
        cartService.deleteByCid(cid,getuidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

   @RequestMapping("{cid}/deleteAll")
    public JsonResult<Integer> deleteAllCid(@PathVariable("cid") Integer[] cidss,HttpSession session){
        Integer data = cartService.deleteAllCid(getuidFromSession(session),cidss);
        if (data == 0){
            throw new CartNotFoundException("所选数据不存在");
        }
        return new JsonResult<>(OK,data);
    }


    @RequestMapping ("list")
    public JsonResult<List<CartVo>> getVOByCids(Integer[] cids, HttpSession session) {
        // 调用业务对象执行查询数据
        List<CartVo> data = cartService.getVoByCid(getuidFromSession(session),cids);
        // 返回成功与数据
        return new JsonResult<>(OK, data);
    }
}
