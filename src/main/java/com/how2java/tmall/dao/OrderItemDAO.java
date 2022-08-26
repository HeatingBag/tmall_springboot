/**
 * @Title: OrderItemDAO
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/16 16:34
 */
package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {

    /*通过订单查询的方法,这种方式在命名里提供OrderByIdDesc，就进行到排序了*/
    List<OrderItem> findByOrderOrderByIdDesc(Order order);

    /*根据产品获取OrderItem的方法*/
    List<OrderItem> findByProduct(Product product);

    List<OrderItem> findByUserAndOrderIsNull(User user);

}
