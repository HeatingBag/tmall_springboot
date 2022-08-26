/**
 * @Title: OrderDAO
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/16 16:36
 */
package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Integer> {

    public List<Order> findByUserAndStatusNotOrderByIdDesc(User user, String status);

}
