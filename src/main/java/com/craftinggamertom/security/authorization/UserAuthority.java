package com.craftinggamertom.security.authorization;

import org.springframework.security.core.context.SecurityContextHolder;

import com.craftinggamertom.security.authentication.AppUser;
import com.craftinggamertom.security.authentication.UserInfo;

public class UserAuthority extends Authority {

	private AppUser theUser;

	/**
	 * The user who is attempting to access a page will be looked up in spring and
	 * then their information will be retrieved from the database
	 */
	public UserAuthority() {
		this.theUser = getUserFromDatabase(authorizeUser()); // Gets full user information
		setAuthorityLevel(theUser.getAuthorityKey()); // Sets the Authority level (based on key)
	}

	/**
	 * Given a UserInfo the UserAuthority will be created and the Authority Level
	 * will be set based on the user's credentials
	 * 
	 * @param userInfo
	 */
	public UserAuthority(UserInfo userInfo) {
		this.theUser = getUserFromDatabase(userInfo); // Gets full user information
		setAuthorityLevel(theUser.getAuthorityKey()); // Sets the Authority level (based on key)
	}

	private AppUser getUserFromDatabase(UserInfo userInfo) {
		// TODO WRITE CODE TO GET THE INFORMATION FROM THE DATABASE
		// Make sure to incorporate the UserInfo variables as well
		return null;
	}

	/**
	 * Checks to see if someone is signed in and gets their basic information as a
	 * UserInfo object or creates a UserInfo object with values representing an
	 * anonymous user
	 * 
	 * @return
	 */
	public static UserInfo authorizeUser() {
		// Creates anonymous credentials
		UserInfo userInfo = new UserInfo("anonymousUser_Id", "anonymousUser_name", "anonymousUser_givenName",
				"anonymousUser_familyName", "anonymousUser_gender", "anonymousUser_picture", "anonymousUser_link");

		try {
			if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) { // Sets
																													// signed
																													// in
																													// credentials

				System.out.print(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

				userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				UserAuthority ua = new UserAuthority(userInfo);

			}
		} catch (NullPointerException e) {
			// DO NOTHING - just move on (Super ugly but this is a work around for
			// unprotected pages)
			/*
			 * By default spring authentication is "anonymousUser" Essentially the above
			 * makes sure the user is not "anonymousUser" and gets their context If a Null
			 * is thrown (and caught here) that means the person was not signed in because
			 * Spring set the user to Null when you sign out rather then setting back to
			 * "anonymousUser"
			 * 
			 * Therefore, by doing nothing the user keeps the anonymous userInfo object
			 * created at the top of the class. - That is how this works.
			 */
			// TODO make amore secure method
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userInfo;
	}

	/**
	 * Gets the user object. It contains all the stored information on user
	 * including the UserInfo variables
	 * 
	 * @return a User object
	 */
	public AppUser getUser() {
		return theUser;
	}

	/*
	 * FOR TRYING TO GET THE EMAIL - DOES NOT WORK BUT IS TO BE IMPLEMENTED IN
	 * OpenIDConnectAuthenticion.java
	 * 
	 * public void sendGet() throws Exception {
	 * 
	 * String MY_SIMPLE_API_KEY = ""; // API KEY GOES HERE String url =
	 * "https://www.googleapis.com/plus/v1/people/me?access_token=" +
	 * user.getToken() + "&key=" + MY_SIMPLE_API_KEY;
	 * 
	 * URL obj = new URL(url); HttpURLConnection con = (HttpURLConnection)
	 * obj.openConnection();
	 * 
	 * // optional default is GET con.setRequestMethod("GET");
	 * con.setRequestProperty("Authorization", "OAuth="+user.getToken());
	 * 
	 * int responseCode = con.getResponseCode();
	 * System.out.println("\nSending 'GET' request to URL : " + url);
	 * System.out.println("Response Code : " + responseCode);
	 * 
	 * BufferedReader in = new BufferedReader( new
	 * InputStreamReader(con.getInputStream())); String inputLine; StringBuffer
	 * response = new StringBuffer();
	 * 
	 * while ((inputLine = in.readLine()) != null) { response.append(inputLine); }
	 * in.close();
	 * 
	 * //print result System.out.println("(UserAuthority)REMOVE: Should be email: "
	 * + response.toString()); }
	 */

}
