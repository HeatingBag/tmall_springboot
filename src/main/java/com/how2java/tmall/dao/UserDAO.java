/**
 * @Title: UserDAO
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/16 16:16
 */
package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {

    User findByName(String name);

    User getByNameAndPassword(String name, String password);
}
