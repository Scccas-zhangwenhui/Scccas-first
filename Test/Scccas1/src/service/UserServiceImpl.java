package service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import dao.UserMapper;
import model.MyUser;
import po.MyUserTable;
import util.MD5Util;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	/**
     * 检查用户名是否已存在
     * @param myUser 包含用户名的MyUser对象
     * @return "no" 如果用户名已存在，"ok" 如果用户名不存在
     */
	@Override
	public String checkUname(MyUser myUser) {
		List<MyUserTable> userList = userMapper.selectByUname(myUser);
		if(userList.size() > 0)
			return "no";
		return "ok";
	}
	/**
     * 实现用户注册功能
     * @param myUser 包含用户信息的MyUser对象
     * @return "login" 注册成功，重定向到登录页面，"register" 注册失败，返回注册页面
     */
	@Override
	public String register(MyUser myUser) {
		// 对用户密码进行MD5加密
		myUser.setUpwd(MD5Util.MD5(myUser.getUpwd()));
		if(userMapper.register(myUser) > 0)
			return "login";
		return "register";
	}
	 /**
     * 实现用户登录功能
     * @param myUser 包含用户名和密码的MyUser对象
     * @param model 模型对象，用于向视图传递数据
     * @param session HTTP会话对象，用于存储用户信息
     * @return 登录成功重定向到卡片列表页面，失败返回登录页面
     */
	@Override
	public String login(MyUser myUser, Model model, HttpSession session) {
		 // 从会话中获取之前生成的验证码
		String code = (String)session.getAttribute("rand");
		if(!code.equalsIgnoreCase(myUser.getCode())) {
			model.addAttribute("errorMessage", "验证码错误，请重新输入");
			return "login";
		}else {
			// 对用户密码进行MD5加密
			myUser.setUpwd(MD5Util.MD5(myUser.getUpwd()));
			List<MyUserTable> list = userMapper.login(myUser);
			if(list.size() > 0){
				session.setAttribute("userLogin", list.get(0));
				return "redirect:/card/selectAllCardsByPage?currentPage=1";
			}else {
				model.addAttribute("errorMessage", "用户名或密码错误，请重新输入");
				return "login";
			}
		}
	}
}
