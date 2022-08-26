/**
 * @Title: ProductImageDAO
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/16 10:05
 */
package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageDAO extends JpaRepository<ProductImage, Integer> {

    public List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);
}
