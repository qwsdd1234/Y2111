package com.cn.qq.login.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

@Controller
@RequestMapping("qq")
public class LoginController {
		@RequestMapping("do_login.chm")
	public void doLogin(HttpServletRequest request,HttpServletResponse response) throws IOException, QQConnectException {
		//��ȡ�û���Ȩҳ��
		response.sendRedirect(new Oauth().getAuthorizeURL(request));
		
		
	}
		@RequestMapping("login")
		@ResponseBody
		public void login(HttpServletRequest request,HttpServletResponse response) throws QQConnectException, IOException{
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			//����Oauth��ȡToken
			AccessToken accessToken=new Oauth()
					.getAccessTokenByQueryString
					(request.getQueryString(), request.getParameter("state"));
			//��request�л�ȡ��Code����ȥToken
			String token=null;
			//��Ч��
			long expireIn;
			//��ȡ����Token
			
			token=accessToken.getAccessToken();
			//��ȡ
			expireIn=accessToken.getExpireIn();
			//׼����ȡopen Id
			OpenID openID=new OpenID(token);
			//��ȡOPenid
			String openid=openID.getUserOpenID();
			
			//��ȡQQ�ռ���Ϣ
			UserInfo qzone=new UserInfo(token, openid);
			//��ȡ�û�����
			UserInfoBean qzoneUser=qzone.getUserInfo();
			response.getWriter().write(qzoneUser.toString());
		}
}
