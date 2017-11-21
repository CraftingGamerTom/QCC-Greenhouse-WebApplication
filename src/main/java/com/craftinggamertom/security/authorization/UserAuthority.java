package com.craftinggamertom.security.authorization;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.security.core.context.SecurityContextHolder;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.database.MongoDatabaseConnection;
import com.craftinggamertom.security.authentication.AppUser;
import com.craftinggamertom.security.authentication.UserInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class UserAuthority extends Authority {

	private AppUser theUser;

	protected MongoDatabase database;
	protected String currentTime = "";

	/**
	 * The user who is attempting to access a page will be looked up in spring and
	 * then their information will be retrieved from the database
	 */
	public UserAuthority() {
		this.theUser = getUserFromDatabase(authorizeUser()); // Gets full user information
		setAuthorityLevel(theUser.getAuthority_key()); // Sets the Authority level (based on key)
	}

	/**
	 * Given a UserInfo the UserAuthority will be created and the Authority Level
	 * will be set based on the user's credentials
	 * 
	 * @param userInfo
	 */
	public UserAuthority(UserInfo userInfo) {
		this.theUser = getUserFromDatabase(userInfo); // Gets full user information
		setAuthorityLevel(theUser.getAuthority_key()); // Sets the Authority level (based on key)
	}

	/**
	 * Gets the user information from the database and puts it into the User object.
	 * If the user does not exist in the database (based on Id) then It puts the
	 * user into the database.
	 * 
	 * @param userInfo
	 *            the userInfo object Spring creates
	 * @return a User Object with the data from the database
	 */
	private AppUser getUserFromDatabase(UserInfo userInfo) {
		makeConnectionToDatabase();

		Bson idFilter = Filters.eq("id", userInfo.getId());

		MongoCollection<Document> collection = null;
		collection = database.getCollection(ConfigurationReaderSingleton.getAppUserCollection());

		FindIterable<Document> documents = collection.find(idFilter);
		Document theUsersDoc = documents.first();

		if (theUsersDoc == null) {
			return putUserInDB(collection, userInfo);
		} else {
			return getUserInformation(theUsersDoc, userInfo);
		}
	}

	private void makeConnectionToDatabase() {
		// Collects and sets the current Time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		currentTime = dtf.format(now); // 2016/11/16 12:08:43

		try {
			database = MongoDatabaseConnection.getInstance(); // Singleton
		} catch (Exception e) {
			System.out.println(" ***** ERROR CONNECTING TO MONGO DB ***** ");
			System.out.println("ERROR WHEN GETTING DATABASE. (PageBuilder.java) STACKTRACE:");
			e.printStackTrace();
			System.out.println(" ***** END ERROR ***** ");
		}
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
				// System.out.print("(UserAuthority.java) User Object: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());

				userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
	 * Gets the user's information from the database
	 * @param collection to get the information from
	 * @return
	 */
	private AppUser getUserInformation(Document theUsersDoc, UserInfo userInfo) {
		ObjectMapper mapper = new ObjectMapper();
		 
        /**
         * Read JSON and convert into a Map
         */
        try {
            HashMap<String, String> userMap = mapper.readValue(theUsersDoc.toJson(), new TypeReference<Map<String, Object>>() {
            });
            AppUser user = new AppUser(userInfo, userMap);
            return user;
 
        } catch (Exception e) {
        	System.out.println("Trouble reading the user's JSON (UserAuthority.java STACKTRACE:");
            e.printStackTrace();
        }
        return null; // Will cause more problems if this is reached.
	}
	
	/**
	 * Called when the user does not exist. This method populates a collection
	 * within the database with standard information that we know about the user as
	 * well ass place markers for unknown information.
	 * 
	 * @param the collection to add the user to
	 * @param the userInfo object created by spring
	 * 
	 * @return the AppUser object for a the new user
	 */
	private AppUser putUserInDB(MongoCollection<Document> collection, UserInfo userInfo) {

		// Creates the AppUser
		HashMap<String, String> infoMap = new HashMap<String, String>();
		infoMap.put("authority_key", "unverified");
		infoMap.put("join_date", currentTime);
		infoMap.put("num_of_observations", "0");
		infoMap.put("num_of_updates", "0");
		infoMap.put("last_seen", currentTime);
		infoMap.put("nickname", userInfo.getGivenName());
		infoMap.put("email_address", "Enter Your Email");
		infoMap.put("cell_phone", "(000)000-0000");

		AppUser newUser = new AppUser(userInfo, infoMap);
		
		//Add the user to the collection
		Document document = new Document(); 
		document.putAll(newUser.getAllInformation());
		collection.insertOne(document);
		
		return newUser; // Instead of then getting it from the database the user is just returned.
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
