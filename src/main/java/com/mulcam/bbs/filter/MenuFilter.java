package com.mulcam.bbs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

/**
 * Servlet Filter implementation class MenuFilter
 */
@Component
@WebFilter("/*")
public class MenuFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		
		String uri = httpRequest.getRequestURI();
		if (uri.contains("board"))
			session.setAttribute("menu", "board");
		else if (uri.contains("user"))
			session.setAttribute("menu", "user");
		else
			session.setAttribute("menu", "");
		
		chain.doFilter(request, response);
	}

}
