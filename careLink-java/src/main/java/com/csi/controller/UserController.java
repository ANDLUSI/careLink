package com.csi.controller;

import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import com.csi.domain.Resident;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.csi.domain.Result;
import com.csi.domain.User;
import com.csi.mapper.UserMapper;
import com.csi.service.UserService;
import com.csi.utils.AI;
import com.csi.utils.JwtUtil;
import com.csi.utils.SmsUtils;

import com.github.pagehelper.PageInfo;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("UserController")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/loginP")
    public Object loginP(@RequestParam("contact") String contact,
                         @RequestParam("password") String password) {
        User user = userService.loginP(contact, password);
        Result<User> result = new Result<>();
        if (user != null) {
            result.setCode(101);
            result.setMessage("登录成功！");
            result.setToken(JwtUtil.getToken("user"));
            result.setData(user);
        } else {
            result.setCode(102);
            result.setMessage("密码错误！");
        }
        return result;
    }

    @PostMapping("/loginC")
    public Object loginC(@RequestParam("contact") String contact,
                         @RequestParam("code") String code) {
        User user = userService.loginC(contact);
        Result<User> result = new Result<>();
        if (user != null) {
            if (code.equals(redisTemplate.opsForValue().get(contact))) {
                result.setCode(101);
                result.setMessage("登录成功");
                result.setToken(JwtUtil.getToken("user"));
                result.setData(user);
            } else {
                result.setCode(102);
                result.setMessage("验证码错误");
            }
        } else {
            result.setCode(103);
            result.setMessage("用户不存在");
        }
        return result;
    }


    @PostMapping("/sendCode")
    public void sendCode(@RequestParam("contact") String contact) {
        try {
            String co = SmsUtils.sendSms(contact);
            redisTemplate.opsForValue().set(contact, co, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/register")
    public Object register(@RequestParam("contact") String contact,
                           @RequestParam("password") String password,
                           @RequestParam("code") String code) {
        Result<User> result = new Result<>();
        if (code.equals(redisTemplate.opsForValue().get(contact))) {
            User user = new User();
            user.setContact(contact);
            user.setPassword(password);
            int ret = userService.register(user);
            if (ret == 1) {
                result.setCode(101);
                result.setMessage("注册成功");
            } else {
                result.setCode(102);
                result.setMessage("该手机号已被注册");
            }
        } else {
            result.setCode(103);
            result.setMessage("验证码错误");
        }
        return result;
    }

    @PostMapping("/message")
    public Object message(@RequestParam("message") String message) {
        Result<String> result = new Result<>();
        GenerationResult rs = null;
        try {
            rs = AI.callWithMessage(message);
        } catch (NoApiKeyException | InputRequiredException ignored) {
        }

        if (rs != null) {
            result.setCode(101);
            result.setMessage("成功");
            result.setData(rs.getOutput().getChoices().get(0).getMessage().getContent());
        } else {
            result.setCode(102);
            result.setMessage("失败");
            result.setData("抱歉，暂时无法识别。");
        }
        return result;
    }


    @PostMapping("/modify")
    public Object modify(User user) {
        Result<User> result = new Result<>();
        int ret = userService.modifyUser(user);
        if (ret == 1) {
            result.setCode(101);
            result.setMessage("成功");
        } else {
            result.setCode(102);
            result.setMessage("失败");
        }
        return result;
    }


    /**
     * 修改用户状态
     * @param userStatus
     * @param id
     * @return
     */
    @PostMapping("/modifyStatus")
    public Object modifyStatus(@RequestParam("status")int userStatus,@RequestParam("userId") Integer id ){
        int i = userService.modifyStatus(userStatus, id);
        Result result=new Result();
        if (i==1){
            result.setCode(2001);
            result.setMessage("修改成功");
        }else {
            result.setCode(4001);
            result.setMessage("修改失败");
        }
         return  result;

    }



    /**
     * 分页查询全部用户 | 用户名字模糊查询
     * @param userName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Object list(@RequestParam(value = "userName", required = false) String userName, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        Result<PageInfo<User>> result = new Result<>();
        List<User> list = userService.list(userName, pageNo, pageSize);
        if (list != null) {
            result.setCode(2001);
            result.setMessage("查询成功");
            PageInfo<User> pageInfo = new PageInfo<>(list);
            result.setData(pageInfo);
        } else {
            result.setCode(4001);
            result.setMessage("查询失败");
        }
        return result;

    }

    /**
     * 修改用户密码
     *
     * @param newPassword
     * @param userId
     * @return
     */
    @PostMapping("/modifyPassword")
    public Object modifyPassword(@RequestParam("newPassword") String newPassword, @RequestParam("userId") Integer userId) {
        Result result = new Result<>();
        int i = userService.modifyPassword(newPassword, userId);
        if (i == 1) {
            result.setCode(2001);
            result.setMessage("成功");
        } else {
            result.setCode(4001);
            result.setMessage("失败");
        }
        return result;

    }

    /**
     * 修改用户头像
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/changeHeadPhoto")
    public Object headPhotoUpload(@RequestParam("newHeadPhoto") MultipartFile file) throws IOException {
        //获取图片上传name
        String filename = file.getOriginalFilename();

        //获取服务器的绝对路径
        String realPath = servletContext.getRealPath("/");
        //在该路径下创建一个用于存储头像的文件夹
        File f = new File(realPath + "/headPhoto");
        if (!f.exists()) {
            f.mkdirs();
        }
        //通过接收到文件名字截取获取扩展名
        String ext = filename.substring(filename.lastIndexOf("."));

        //使用UUID.randomUUID()生成新名字与扩展名进行拼接生成最终存储的名字
        String newName = UUID.randomUUID().toString().replace("-", "") + ext;

        //将文件的内容移动到指定的目标文件
        file.transferTo(new File(f, newName));
        Result<String> result = new Result();
        result.setData(newName);
        return result;
    }


    /**
     * 查询用户信息以及所关联的老人信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/findResidentsById")
    public Object findResidentsById(@RequestParam("userId") Integer userId) {
        System.out.println("========================");
        List<Resident> list = userService.findResidentsById(userId);
        User user = userService.findById(userId);
        Result<User> result = new Result<>();
        if (user != null) {
            result.setCode(1001);
            result.setMessage("查询成功");
            if (list != null) {
                user.setResidents(list);
                result.setData(user);
            } else {
                result.setData(user);
                result.setMessage("该用户下没有老人");
            }
        } else {
            result.setCode(4001);
            result.setMessage("查询失败");
        }

        return result;
    }


    /**
     * 注销用户下关联的老人
     *
     * @param userId
     * @param residentId
     * @return
     */
    @PostMapping("/cancelOldPeople")
    public Object cancelOldPeople(@RequestParam("userId") Integer userId, @RequestParam("residentId") Integer residentId) {
        int i = userService.cancelOldPeople(userId, residentId);
        Result result = new Result();
        if (i == 1) {
            result.setCode(1001);
            result.setMessage("注销成功");
        } else {
            result.setCode(4001);
            result.setMessage("注销失败");
        }
        return result;
    }

    /**
     * 用户绑定对应老人
     *
     * @param userId
     * @param residentId
     * @return
     */
    @PostMapping("/addOldPeople")
    public Object addOldPeople(@RequestParam("userId") Integer userId, @RequestParam("residentId") Integer residentId) {
        //查询用户下的老人
        List<Resident> list = userService.findResidentsById(userId);
        Result result = new Result();

        //验证该用户是否已经绑定过该老人
        boolean flag = true;
        if (list != null && !list.isEmpty()) {
            for (Resident resident : list) {
                if (resident != null) {
                    if (resident.getId() == residentId) {
                        flag = false;
                        break;
                    }
                }

            }
        }
        if (flag) {
            int i = userService.addOldPeople(userId, residentId);
            if (i == 1) {
                result.setCode(1001);
                result.setMessage("绑定成功");
            } else {
                result.setCode(4001);
                result.setMessage("绑定失败");
            }
        } else {
            result.setCode(4001);
            result.setMessage("该老人已绑定");
        }
        return result;
    }


}
