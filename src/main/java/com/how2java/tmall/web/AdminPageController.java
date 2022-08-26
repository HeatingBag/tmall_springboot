/**
 * @Title: AdminPageController
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/14 10:24
 */
package com.how2java.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 后台管理页面跳转专用控制器。
 * 因为是做前后端分离，所以数据是通过 RESTFUL接口来取的，而在业务上，除了 RESTFUL 服务要提供，还要提供页面跳转服务
 * 所以所有的后台页面跳转都放在 AdminPageController 这个控制器里，而RSTFUL 专门放在 Category 对应的控制器 CategoryController.java 里面。
 *
 * @Controller 表示这是一个控制器，会让return "admin/listCategory"自动去找相应的html
 * @GetMapping(value="/admin") 访问地址 admin,就会客户端跳转redirect到 admin_category_list去。
 * @GetMapping(value="/admin_category_list") 访问地址 admin_category_list 就会访问 admin/listCategory.html 文件。
 */

@Controller
public class AdminPageController {

    @GetMapping(value = "/admin")
    public String admin() {
        return "redirect:admin_category_list";
    }

    @GetMapping(value = "/admin_category_list")
    public String listCategory() {
        return "admin/listCategory";
    }

    @GetMapping(value = "/admin_category_edit")
    public String editCategory() {
        return "admin/editCategory";

    }

    @GetMapping(value = "/admin_order_list")
    public String listOrder() {
        return "admin/listOrder";

    }

    @GetMapping(value = "/admin_product_list")
    public String listProduct() {
        return "admin/listProduct";

    }

    @GetMapping(value = "/admin_product_edit")
    public String editProduct() {
        return "admin/editProduct";

    }

    @GetMapping(value = "/admin_productImage_list")
    public String listProductImage() {
        return "admin/listProductImage";

    }

    @GetMapping(value = "/admin_property_list")
    public String listProperty() {
        return "admin/listProperty";

    }

    @GetMapping(value = "/admin_property_edit")
    public String editProperty() {
        return "admin/editProperty";

    }

    @GetMapping(value = "/admin_propertyValue_edit")
    public String editPropertyValue() {
        return "admin/editPropertyValue";

    }

    @GetMapping(value = "/admin_user_list")
    public String listUser() {
        return "admin/listUser";

    }
}
