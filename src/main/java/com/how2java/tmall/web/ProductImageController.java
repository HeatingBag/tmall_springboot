/**
 * @Title: ProductImageController
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/16 10:19
 */
package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductImageController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    CategoryService categoryService;

    /**
     * 1. 首先访问路径，admin_productImage_list?pid=2， 通过AdminPageController 中的映射，返回 listProductImage.html
     *
     * @GetMapping(value = "/admin_productImage_list") -->admin/listProductImage
     * 2. 在listProductImage.html 加载后就获取了参数上的 pid  listSingles: function (start) {...}
     * 3. 然后自动调用 listSingles 函数
     * var url = "products/" + pid + "/" + this.uri + "?type=single";
     * 4. listSingles 函数用过 axios.js 调用 products/2/productImages?type=single 这个地址
     * 5. 步骤4的地址，导致 ProductImageController 的 list 方法被调用
     * 6. list 方法里根据参数调用 ProductService的 listSingleProductImages 方法，返回当前产品的单个图片集合
     * 7. 这个图片集合返回 json数组
     * 8. axios拿到这个json数组就放在 vue的singleProductImages对象上
     * 9. vue 把 singleProductImages 通过v-for 遍历在视图上
     */

    @GetMapping("/products/{pid}/productImages")
    public List<ProductImage> list(@RequestParam("type") String type, @PathVariable("pid") int pid) throws Exception {

        Product product = productService.get(pid);

        if (ProductImageService.type_single.equals(type)) {
            List<ProductImage> singles = productImageService.listSingleProductImages(product);
            return singles;
        } else if (ProductImageService.type_detail.equals(type)) {
            List<ProductImage> details = productImageService.listDetailProductImages(product);
            return details;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 增加产品图片分单个和详情两种，其区别在于增加所提交的type类型不一样。
     * 这里就对单个的进行讲解，详情图片的处理同理。
     * 1. 选中图片后，会导致 getSingleFile 函数被调用，vue就拿到了文件  getSingleFile: function (event) {}
     * 2. 点击提交按钮会导致addSingle函数被调用 @click="addSingle"  addSingle: function () {}
     * 3. 与分类上传图片类似， axios,js 上传图片要用 FormData 的方式   var url = this.uri + "?type=single&pid=" + pid;
     * 4. 上传到路径 productImages，并带上type和pid参数
     * 5. ProductImageController 的add方法被调用
     * 6. 首先根据pid 和 type 创建 ProductImage 对象，并插入数据库
     * 7. 然后根据类型指定保存文件的路径：productSingle
     * 8. 接着根据产品图片对象的id，作为文件名，把图片保存到对应的位置
     * 9. 像分类上传图片一样，要通过 ImageUtil.change2jpg进行类型强制转换,以确保一定是jpg图片
     * 10. 如果是单个图片，还要创建 small 和 middle 两种不同大小的图片，用的是 ImageUtil.resizeImage 函数
     */

    @PostMapping("/productImages")
    public Object add(@RequestParam("pid") int pid, @RequestParam("type") String type, MultipartFile image, HttpServletRequest request) throws Exception {

        ProductImage bean = new ProductImage();
        Product product = productService.get(pid);
        bean.setProduct(product);
        bean.setType(type);

        productImageService.add(bean);
        String folder = "img/";
        if (ProductImageService.type_single.equals(bean.getType())) {
            folder += "productSingle";
        } else {
            folder += "productDetail";
        }
        File imageFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder, bean.getId() + ".jpg");
        String fileName = file.getName();
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ProductImageService.type_single.equals(bean.getType())) {
            String imageFolder_small = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }
        return bean;
    }

    /**
     * 1. 点击删除超链
     * 2. vue 上的 deleteBean 函数被调用，访问 productImages/id 路径
     * 3. ProductImageController的delete方法被调用   deleteBean: function (id) {}
     * 4. 根据id获取ProductImage 对象    var url = this.uri + "/" + id;
     * 5. 借助productImageService，删除数据
     * 6. 如果是单个图片，那么删除3张正常，中等，小号图片
     * 7. 如果是详情图片，那么删除一张图片
     */

    @DeleteMapping("/productImages/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws Exception {

        ProductImage bean = productImageService.get(id);
        productImageService.delete(id);

        String folder = "img/";
        if (ProductImageService.type_single.equals(bean.getType()))
            folder += "productSingle";
        else
            folder += "productDetail";

        File imageFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder, bean.getId() + ".jpg");
        String fileName = file.getName();
        file.delete();
        if (ProductImageService.type_single.equals(bean.getType())) {
            String imageFolder_small = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();

        }
        return null;
    }
}
