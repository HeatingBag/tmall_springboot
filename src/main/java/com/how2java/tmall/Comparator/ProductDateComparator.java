/**
 * @Title: ProductDateComparator
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/17 15:59
 */
package com.how2java.tmall.Comparator;

import com.how2java.tmall.pojo.Product;

import java.util.Comparator;

public class ProductDateComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p1.getCreateDate().compareTo(p2.getCreateDate());
    }
}
