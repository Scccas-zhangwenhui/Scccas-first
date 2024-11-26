package controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * ȫ���쳣������
 */
@ControllerAdvice
public class GlobalExceptionHandleController {
	/**
     * �쳣������
     * @param e �쳣����
     * @param model ģ�Ͷ�����������ͼ��������
     * @return ������ͼ���ƣ�������ʾ������Ϣ
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
