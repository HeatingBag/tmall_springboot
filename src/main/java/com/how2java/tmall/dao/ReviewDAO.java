/**
 * @Title: ReviewDAO
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/17 10:47
 */
package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDAO extends JpaRepository<Review, Integer> {

    List<Review> findByProductOrderByIdDesc(Product product);

    int countByProduct(Product product);
}
