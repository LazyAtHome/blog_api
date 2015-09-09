package core.base.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Capture the exceptions from controller layer and resolve it into http response;
 * @author Jason
 *
 * 2015年9月9日
 */
public class HttpStatusHandlerExceptionResolver implements HandlerExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(HttpStatusHandlerExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		try {
			if (ex instanceof HttpRequestMethodNotSupportedException) {
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
		} catch (Throwable throwable) {
			logger.warn("处理响应状态发生异常", throwable);
		}
		return new ModelAndView();
	}

	/**
	 * Distinguish the type of property that get error and transfer to Chinese;
	 * @param clazz
	 * @return
	 */
	private String convertClassName(Class<?> clazz) {
		if (clazz.equals(Integer.class)) {
			return "整数";
		} else if (clazz.equals(Float.class)) {
			return "浮点数";
		} else if (clazz.equals(Double.class)) {
			return "双精度浮点数";
		} else if (clazz.equals(Boolean.class)) {
			return "布尔型";
		} else {
			return clazz.getSimpleName();
		}
	}

}
