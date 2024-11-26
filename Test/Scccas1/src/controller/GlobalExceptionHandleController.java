package controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandleController {
	/**
     * 异常处理方法
     * @param e 异常对象
     * @param model 模型对象，用于向视图传递数据
     * @return 返回视图名称，用于显示错误信息
     */
	@ExceptionHandler(value=Exception.class)
	public String exceptionHandler(Exception e, Model model) {
		String message = "";
		if (e instanceof NoLoginException) {
			message = "noLogin";
		} else {
			message =  "noError";
		}
		model.addAttribute("mymessage",message);
		return "error";
	}
}
