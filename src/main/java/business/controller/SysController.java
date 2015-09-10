package business.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import business.sys.entity.User;
import business.sys.service.UserService;
import core.base.CommonRepository;
import core.base.mvc.ReplyMessage;
import core.base.utils.StringUtil;

@Controller
@RequestMapping("/sys")
public class SysController {

    @Resource
    CommonRepository commonRepository;

    @Resource
    UserService userService;

    /**
     * Login service
     * 
     * @param userName
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "user/login", method = RequestMethod.POST)
    public ReplyMessage login(String userName, String password) {
        if (StringUtil.isNullOrEmpty(userName, password)) return ReplyMessage.fail().message("登录失败");
        return ReplyMessage.success().returObject(userService.login(userName, password));
    }

    /**
     * Logout user ,exit account;
     * 
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "user/{userId}", method = RequestMethod.PUT)
    public ReplyMessage logout(Long userId) {
        userService.logout(userId);
        return ReplyMessage.success().message("注销成功");
    }

    /**
     * User registering.
     * 
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public ReplyMessage register(User user) {
        return ReplyMessage.success().message("注册成功").returObject(userService.register(user));
    }

    /**
     * Get user's detail informations
     * 
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "user/{userId}", method = RequestMethod.GET)
    public ReplyMessage userDetail(Long userId) {
        return ReplyMessage.success().returObject(commonRepository.get(User.class, userId));
    }
}
