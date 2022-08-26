/**
 * @Title: PropertyController
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/16 8:20
 */
package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    /**
     * 1. 查询地址admin_property_list 映射 AdminPageController  @GetMapping(value = "/admin_product_list") 拿到 listProperty.html 文件
     * 2. listProperty.html 通过 aixos 异步访问 list:function(start){...} categories/cid/properties?start=0 拿到数据。
     * var url =  "categories/"+cid+"/"+this.uri+"?start="+start;
     * 这个是通过分类拿到子类数据的 RESTFULL规范 cid 是分类的id,实际运行的时候是真实的值   var cid = getUrlParms("cid");
     * 3. categories/cid/properties 地址对应 PropertyController的list方法，在这个方法里通过 propertyService 获取分页数据
     * 4. 拿到数据之后，通过 vue.js 的 v-for 遍历到 table中  <tr v-for="bean in beans ">
     */

    @GetMapping("/categories/{cid}/properties")
    public Page4Navigator<Property> list(@PathVariable("cid") int cid, @RequestParam(value = "start", defaultValue = "0") int start,
                                         @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {

        start = start < 0 ? 0 : start;
        Page4Navigator<Property> page = propertyService.list(cid, start, size, 5);
        return page;

    }

    /**
     * 1. 访问地址admin_property_edit 导致 editProperty.html 被访问
     * @GetMapping(value="/admin_property_edit")
     * 2. editProperty.html 加载后调用 vue 的 get方法
     * get:function(){。。var url = this.uri+"/"+id;}
     * 3. vue的get方法通过 axios 访问 properties/id 地址
     * 4. PropertyController 映射该地址，并通过 propertyService 获取数据
     * 5. html 拿到数据后，通过Vue 显示出来
     */

    @GetMapping("/properties/{id}")
    public Property get(@PathVariable("id") int id) throws Exception {
        Property bean = propertyService.get(id);
        return bean;
    }

    /**
     * 1. 在listProperty.html页面提交数据会调用vue的add方法
     * add: function () {...}
     * var url = this.uri;    this.bean.category.id = cid; 这里的this是指data4Vue
     * 2. add 方法里 把property数据通过 axios 提交到 properties路径   axios.post(url,this.bean)
     * 3. PropertyController 的 add 方法接受数据，并调用 PropertyService 插入数据库
     */

    @PostMapping("/properties")
    public Object add(@RequestBody Property bean) throws Exception {
        propertyService.add(bean);
        return bean;
    }

    /**
     * 1. 删除的时候调用 deleteBean 函数，访问 properties/id 路径
     * deleteBean: function (id) {... var url = this.uri+"/"+id;  axios.delete(url)}
     * 2. PropertyController 的delete 方法被调用，调用propertiService 进行数据删除
     * */

    @DeleteMapping("/properties/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        propertyService.delete(id);
        return null;
    }

    /**
     * 1. editProperty.html 中通过 axois 提交修改后的数据 到 properties地址
     * update:function () {... axios.put(url,vue.bean)}
     * 2. PropertyController 的update 接受到数据后，通过 propertyService 把数据更新到数据库
     * */

    @PutMapping("/properties")
    public Object update(@RequestBody Property bean) throws Exception {
        propertyService.update(bean);
        return bean;
    }
}
