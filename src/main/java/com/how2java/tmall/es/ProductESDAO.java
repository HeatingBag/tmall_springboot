/**
 * @Title: ProductESDAO
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/19 19:35
 */
package com.how2java.tmall.es;

import com.how2java.tmall.pojo.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductESDAO extends ElasticsearchRepository<Product, Integer> {
}
