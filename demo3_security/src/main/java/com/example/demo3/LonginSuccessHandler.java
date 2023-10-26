package com.example.demo3;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

//인증성공시 처리되는 곳
public class LonginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static Logger logger = LoggerFactory.getLogger(LonginSuccessHandler.class);
	
	public LonginSuccessHandler(String defaultTargetUrl) {
		setDefaultTargetUrl(defaultTargetUrl);
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws ServletException, IOException {
		logger.debug("onAuthenticationSuccess : ");
		
		HttpSession session= req.getSession();
		if(null != session) {
			String redirectUrl = (String) session.getAttribute("prevPage");
			logger.info("onAuthenticationSuccess : redirect[{}]", redirectUrl);
			if (null != redirectUrl) {
				session.removeAttribute("prevPage");
//				getRedirectStrategy().sendRedirect(req, res, redirectUrl);
//				res.sendRedirect(redirectUrl);
			}
		}
		
		super.onAuthenticationSuccess(req, res, auth);
	}
}
