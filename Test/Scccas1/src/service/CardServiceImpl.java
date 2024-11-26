package service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import util.MD5Util;
import util.MyUtil;

import dao.CardMapper;
import model.Card;
import model.MyUser;
import po.CardTable;
import po.MyUserTable;
@Service
public class CardServiceImpl implements CardService{
	@Autowired
	private CardMapper cardMapper;
	/**
     * 查询所有卡片并按页显示
     * @param model 模型对象，用于向视图传递数据
     * @param currentPage 当前页码
     * @param session HTTP会话对象
     * @return 返回视图名称
     */
	@Override
	public String selectAllCardsByPage(Model model, int currentPage,  HttpSession session) {
		// 从会话中获取当前登录的用户信息
        MyUserTable mut = (MyUserTable) session.getAttribute("userLogin");
        // 根据用户ID查询该用户的所有卡片信息
        List<Map<String, Object>> allUser = cardMapper.selectAllCards(mut.getId());
        // 计算总卡片数量
        int totalCount = allUser.size();
        // 设置每页显示的卡片数量
        int pageSize = 5;
        // 计算总页数
        int totalPage = (int) Math.ceil(totalCount * 1.0 / pageSize);
        // 根据当前页码和每页数量查询卡片信息
        List<Map<String, Object>> cardsByPage = cardMapper.selectAllCardsByPage((currentPage - 1) * pageSize, pageSize, mut.getId());
        // 将查询到的卡片信息添加到模型中
        model.addAttribute("allCards", cardsByPage);
        // 将总页数添加到模型中
        model.addAttribute("totalPage", totalPage);
        // 将当前页码添加到模型中
        model.addAttribute("currentPage", currentPage);
        // 返回主页面视图名称
        return "main";
	}
	/**
     * 添加或更新卡片
     * @param card 卡片对象
     * @param request HTTP请求对象
     * @param act 操作类型（add或update）
     * @param session HTTP会话对象
     * @return 返回视图名称
     * @throws IllegalStateException 状态异常
     * @throws IOException IO异常
     */
	@Override
	public String addCard(Card card, HttpServletRequest  request, String act, HttpSession session) throws IllegalStateException, IOException {
		// 获取卡片对象中的Logo文件
        MultipartFile myfile = card.getLogo();
        
        // 如果Logo文件不为空，则处理文件上传
        if (!myfile.isEmpty()) {
            // 获取服务器上用于存储图片的路径
            String path = request.getServletContext().getRealPath("/static/images/");
            // 获取上传文件的原始文件名
            String fileName = myfile.getOriginalFilename();
            // 生成一个新的文件名，防止文件名冲突
            String fileNewName = MyUtil.getNewFileName(fileName);
            // 创建文件路径对象
            File filePath = new File(path + File.separator + fileNewName);
            
            // 如果文件路径的父目录不存在，则创建目录
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            
            // 将上传的文件保存到服务器上
            myfile.transferTo(filePath);
            
            // 设置卡片对象的Logo文件名
            card.setLogoName(fileNewName);
        }
        // 根据操作类型执行添加或更新操作
        if ("add".equals(act)) {
            // 从会话中获取当前登录的用户信息
            MyUserTable mut = (MyUserTable) session.getAttribute("userLogin");
            // 设置卡片对象的创建者ID
            card.setUser_id(mut.getId());
            // 调用Mapper接口添加卡片
            int n = cardMapper.addCard(card);
            // 如果添加成功，则重定向到卡片列表页面
            if (n > 0)
                return "redirect:/card/selectAllCardsByPage?currentPage=1";
            
            // 如果添加失败，则返回添加卡片页面视图名称
            return "addCard";
        } else {
            // 调用Mapper接口更新卡片
            int n = cardMapper.updateCard(card);
            // 如果更新成功，则重定向到卡片列表页面
            if (n > 0)
                return "redirect:/card/selectAllCardsByPage?currentPage=1";
            
            // 如果更新失败，则返回更新卡片页面视图名称
            return "updateCard";
        }
	}
	/**
     * 查看卡片详细信息
     * @param model 模型对象，用于向视图传递数据
     * @param id 卡片ID
     * @param act 操作类型（detail或update）
     * @return 返回视图名称
     */
	@Override
	public String detail(Model model, int id, String act) {
		 // 根据卡片ID查询卡片详细信息
        CardTable ct = cardMapper.selectACard(id);
        // 将卡片详细信息添加到模型中
        model.addAttribute("card", ct);
        // 根据操作类型返回不同的视图名称
        if ("detail".equals(act)) {
            // 返回卡片详细信息页面视图名称
            return "cardDetail";
        } else {
            // 返回更新卡片页面视图名称
            return "updateCard";
        }
	}
	 /**
     * 删除卡片
     * @param id 卡片ID
     * @return 返回视图名称
     */
	@Override
	public String delete(int id) {
		// 调用Mapper接口删除卡片
        cardMapper.deleteACard(id);
        // 重定向到卡片列表页面
        return "/card/selectAllCardsByPage?currentPage=1";
	}
	/**
     * 用户注销
     * @param model 模型对象，用于向视图传递数据
     * @param session HTTP会话对象
     * @return 返回视图名称
     */
	@Override
	public String loginOut(Model model, HttpSession session) {
		// 销毁HTTP会话
        session.invalidate();
        // 将新的用户对象添加到模型中，以便在登录页面显示
        model.addAttribute("myUser", new MyUser());
        // 返回登录页面视图名称
        return "login";
	}
	/**
     * 跳转到修改密码页面
     * @param model 模型对象，用于向视图传递数据
     * @param session HTTP会话对象
     * @return 返回视图名称
     */
	@Override
	public String toUpdatePwd(Model model, HttpSession session) {
		 // 从会话中获取当前登录的用户信息
        MyUserTable mut = (MyUserTable) session.getAttribute("userLogin");
        // 将用户信息添加到模型中
        model.addAttribute("myuser", mut);
        // 返回修改密码页面视图名称
        return "updatePwd";
	}
	 /**
     * 修改密码
     * @param myuser 用户对象
     * @return 返回登录页面视图名称
     */
	@Override
	public String updatePwd(MyUser myuser) {
		// 对用户密码进行MD5加密
        myuser.setUpwd(MD5Util.MD5(myuser.getUpwd()));
        // 调用Mapper接口更新用户密码
        cardMapper.updatePwd(myuser);
        // 返回登录页面视图名称
        return "login";
	}
}
