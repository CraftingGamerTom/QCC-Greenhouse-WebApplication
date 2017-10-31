package com.craftinggamertom.security.authorization;

import org.springframework.security.core.context.SecurityContextHolder;

import com.craftinggamertom.security.authentication.UserInfo;

public class AppAuthorizer {

	public static UserInfo authorizeUser() {
		UserInfo userInfo = new UserInfo("anonymousUser_ID",
                "anonymousUser_name",
                "anonymousUser_givenName",
                "anonymousUser_familyName",
                "anonymousUser_gender",
                "anonymousUser_picture",
                "anonymousUser_link");
		
		if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) { // Sets signed in credentials
			
			userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return userInfo;
	}
}
