/**
 * @Title: UserController
 * @Auther: zhang
 * @Version: 1.0
 * @create: 2022/6/16 16:19
 */
package com.how2java.tmall.web;

import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * http://127.0.0.1:8080/tmall_springboot/admin_user_list
     * 1. admin_user_list 导致 listUser.html 传递给浏览器
     * 2. html 里的js 通过 axios.js 访问 users路径  list:function(start){}
     * 3. UserController 映射 users, 返回 user 集合对应的json 数组
     * 4. vue拿到json数组，通过v-for 遍历到视图上 <tr v-for="bean in beans ">
     */

    @GetMapping("/users")
    public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                     @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {

        start = start < 0 ? 0 : start;
        Page4Navigator<User> page = userService.list(start, size, 5);
        return page;

    }
}
