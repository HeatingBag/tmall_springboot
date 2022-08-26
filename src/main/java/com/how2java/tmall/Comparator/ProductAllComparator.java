/**
 * @Title: ProductAllComparator
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/17 15:58
 */
package com.how2java.tmall.Comparator;

import com.how2java.tmall.pojo.Product;

import java.util.Comparator;

public class ProductAllComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount();
    }
}
