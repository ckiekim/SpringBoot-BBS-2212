package com.mulcam.bbs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Servlet Filter implementation class MenuFilter
 */
@Component
public class BbsFilter extends HttpFilter implements Filter {
	private final Logger log = LoggerFactory.getLogger(getClass());

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpResponse = (HttpServletResponse) res;
		HttpSession session = httpRequest.getSession();
		session.setMaxInactiveInterval(3600*10); 		// 10 hours
		
		String uri = httpRequest.getRequestURI();
		
		if (uri.contains("board"))
			session.setAttribute("menu", "board");
		else if (uri.contains("user"))
			session.setAttribute("menu", "user");
		else if (uri.contains("api"))
			session.setAttribute("menu", "api");
		else if (uri.contains("crawling"))
			session.setAttribute("menu", "crawling");
		else if (uri.contains("python"))
			session.setAttribute("menu", "python");
		else if (uri.contains("schedule"))
			session.setAttribute("menu", "schedule");
		else
			session.setAttribute("menu", "");
		
		String[] urlPatterns = {"/board", "/user/list", "/user/update", "/user/delete",
								"/api", "/crawling", "/python", "/schedule", "/aside"};
		for (String routing: urlPatterns) {
			if (uri.contains(routing)) {
				String ipAddr = httpRequest.getRemoteAddr();
				if (!uri.contains("/aside"))
					log.info("{} is called from {}", uri, ipAddr);
				String uid = (String) session.getAttribute("uid");
				if (uid == null || uid.equals("")) {
					httpResponse.sendRedirect("/bbs/user/login");
					return;
				}
				break;
			}
		}
		
		chain.doFilter(req, res);
	}

}
