package com.craftinggamertom.security.authorization;

import org.springframework.security.core.context.SecurityContextHolder;

import com.craftinggamertom.security.authentication.UserInfo;

public class AppAuthorizer {

	public static UserInfo authorizeUser() {
		// Creates anonymous credentials
		UserInfo userInfo = new UserInfo("anonymousUser_Id",
                "anonymousUser_name",
                "anonymousUser_givenName",
                "anonymousUser_familyName",
                "anonymousUser_gender",
                "anonymousUser_picture",
                "anonymousUser_link");
	
		try {
		if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) { // Sets signed in credentials
			
			userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		}catch(NullPointerException e) {
			// DO NOTHING - just move on (Super ugly but this is a work around for unprotected pages)
			// TODO make amore secure method
		}
		return userInfo;
	}
}
