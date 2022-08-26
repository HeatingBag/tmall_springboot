/**
 * @Title: ProductPriceComparator
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/17 16:03
 */
package com.how2java.tmall.Comparator;

import com.how2java.tmall.pojo.Product;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p1.getPromotePrice() - p2.getPromotePrice());
    }
}
