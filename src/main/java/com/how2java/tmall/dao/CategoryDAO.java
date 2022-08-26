/**
 * @Title: CategoryDAO
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/14 10:20
 */
package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoryDAO 类继承了 JpaRepository
 * 就提供了CRUD和分页 的各种常见功能。 这就是采用 JPA 方便的地方~
 * */

public interface CategoryDAO extends JpaRepository<Category, Integer> {
}
