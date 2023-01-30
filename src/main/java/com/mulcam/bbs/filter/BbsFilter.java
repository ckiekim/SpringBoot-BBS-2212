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

import org.springframework.stereotype.Component;

/**
 * Servlet Filter implementation class MenuFilter
 */
@Component
public class BbsFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		
		String uri = httpRequest.getRequestURI();
//		System.out.println("BbsFilter.uri:" + uri);
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
		else
			session.setAttribute("menu", "");
		
		String[] urlPatterns = {"/board", "/user/list", "/user/update", "/user/delete"};
		for (String routing: urlPatterns) {
			if (uri.contains(routing)) {
				String uid = (String) session.getAttribute("uid");
				if (uid == null || uid.equals("")) {
					httpResponse.sendRedirect("/bbs/user/login");
					return;
				}
				break;
			}
		}
		
		chain.doFilter(request, response);
	}

}
