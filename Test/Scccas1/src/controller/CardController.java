package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import model.Card;
import model.MyUser;
import service.CardService;

@Controller
@RequestMapping("/card")
public class CardController {
	@Autowired
	private CardService cardService;
	/**
	 * 处理用户登录
	 */
	@ModelAttribute
	public void checkLogin(HttpSession session) throws NoLoginException{
		if(session.getAttribute("userLogin") == null) {
			throw new NoLoginException();
		}
	}
	/**
	 * 跳转到分页显示所有卡片信息页面
	 */
	@RequestMapping("/selectAllCardsByPage")
	public String selectAllCardsByPage(Model model, int currentPage,  HttpSession session) {
		return cardService.selectAllCardsByPage(model, currentPage,  session);
	}
	/**
	 * 跳转到添加卡片页面
	 */
	@RequestMapping("/toAddCard")
	public String toAddCard(@ModelAttribute Card card) {
		return "addCard";
	}
	/**
	 * 处理添加卡片请求
	 */
	@RequestMapping("/addCard")
	public String addCard(@ModelAttribute Card card, HttpServletRequest  request, String act, HttpSession session) throws IllegalStateException, IOException {
		return cardService.addCard(card, request, act, session);
	}
	/**
	 * 显示卡片详细信息
	 */
	@RequestMapping("/detail")
	public String detail(Model model, int id, String act) {
		
		return cardService.detail(model, id, act);
	}
	/**
	 * 删除卡片
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(int id) {
		return cardService.delete(id);
	}
	/**
	 * 用户注销
	 */
	@RequestMapping("/loginOut")
	public String loginOut(Model model, HttpSession session) {
		return cardService.loginOut(model, session);
	}
	/**
	 * 跳转到修改密码页面
	 */
	@RequestMapping("/toUpdatePwd")
	public String toUpdatePwd(Model model, HttpSession session) {
		return cardService.toUpdatePwd(model, session);
	}
	/**
	 * 修改密码
	 */
	@RequestMapping("/updatePwd")
	public String updatePwd(@ModelAttribute MyUser myuser) {
		return cardService.updatePwd(myuser);
	}
}
