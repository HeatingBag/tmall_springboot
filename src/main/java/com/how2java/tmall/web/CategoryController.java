/**
 * @Title: CategoryController
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/14 10:26
 */
package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page4Navigator;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 这个就是专门用来提供 RESTFUL 服务器控制器了
 *
 * @RestController 表示这是一个控制器，并且对每个方法的返回值都会直接转换为 json 数据格式。
 * @Autowired CategoryService categoryService; 自动装配 CategoryService
 * @GetMapping("/categories"),这里对应 uri: 'categories',
 * 对于categories 访问，会获取所有的 Category对象集合，并返回这个集合，
 * 因为是声明为 @RestController， 所以这个集合，又会被自动转换为 JSON数组抛给浏览器。
 */

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                         @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {

        start = start < 0 ? 0 : start;

        //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        Page4Navigator<Category> page = categoryService.list(start, size, 5);

        return page;
    }

    @PostMapping("/categories")
    public Object add(Category bean, MultipartFile image, HttpServletRequest request) throws Exception {
        categoryService.add(bean);
        saveOrUpdateImageFile(bean, image, request);
        return bean;
    }

    private void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request) throws IOException {

        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, bean.getId() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    /**
     * @DeleteMapping("/categories/{id}")对应ListCategory.html的 deleteBean: function (id) {}的规范 var url = this.uri+"/"+id;
     * 1. 首先根据id 删除数据库里的数据
     * 2. 删除对应的文件
     * 3. 返回 null, 会被RESTController 转换为空字符串。
     */
    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws Exception {

        categoryService.delete(id);
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();
        return null;
    }

    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id) throws Exception {
        Category bean = categoryService.get(id);
        return bean;
    }


    /**
     * 这里获取参数用的是 request.getParameter("name"),根据 RESTFUL 标准 。PUT 方式注入不了。。。 只能用这种方式取参数
     */
    @PutMapping("/categories/{id}")
    public Object update(Category bean, MultipartFile image, HttpServletRequest request) throws Exception {

        String name = request.getParameter("name");
        bean.setName(name);
        categoryService.update(bean);

        if (image != null) {
            saveOrUpdateImageFile(bean, image, request);
        }
        return bean;
    }
}
