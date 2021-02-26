package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVO;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 用户分页&多条件组合查询
    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVO userVO) {
        PageInfo<User> pageInfo = userService.findAllUserByPage(userVO);
        return new ResponseResult(true, 200, "分页&多条件查询成功", pageInfo);
    }

    // 用户状态设置
    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(Integer id, String status) {

        if ("ENABLE".equalsIgnoreCase(status)) {
            status = "DISABLE";
        } else {
            status = "ENABLE";
        }
        userService.updateUserStatus(id, status);
        return new ResponseResult(true, 200, "用户状态设置成功", status);
    }

    // 用户登录
    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {
        User user1 = userService.login(user);

        if (user1 != null) {
            // 保存用户id及access_token保存到session中;
            // 下次请求时通过对比请求头与session中的access_token来判断是否为同一用户且在登录状态
            HttpSession session = request.getSession();
            // 通过UUID生成随机token
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("access_token",access_token);
            session.setAttribute("user_id", user1.getId());

            Map<String, Object> map = new HashMap<>();
            map.put("access_token", access_token);
            map.put("user_id", user1.getId());
            // 将查询结果也存入map中, 以便前端做登出功能
            map.put("user", user1);

            return new ResponseResult(true, 1, "用户登录成功", map);

        } else {
            return new ResponseResult(true, 400, "用户名或密码错误", null);
        }
    }

    // 根据用户id查询关联的角色信息
    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRoleById(Integer id) {
        List<Role> list = userService.findUserRoleById(id);
        return new ResponseResult(true, 200, "分配角色回显成功", list);
    }

    // 分配角色
    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVO userVO) {
        userService.userContextRole(userVO);
        return new ResponseResult(true, 200, "分配角色成功", null);
    }

    // 获取用户权限, 进行动态菜单展示(get请求, 无请求参数)
    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request) {
        // 1.获取请求头中的token
        String header_token = request.getHeader("Authorization");
        // 2.获取session中的token
        String session_token = (String) request.getSession().getAttribute("access_token");
        // 3.判断token是否一致
        if (header_token.equals(session_token)) {
            // 用户处于登录状态, 获取用户id, 查询权限信息(动态菜单)
            Integer user_id = (Integer) request.getSession().getAttribute("user_id");
            return userService.getUserPermissions(user_id);
        } else {
            // 返回错误信息
            return new ResponseResult(false, 400, "获取菜单信息失败", null);
        }
    }
}
