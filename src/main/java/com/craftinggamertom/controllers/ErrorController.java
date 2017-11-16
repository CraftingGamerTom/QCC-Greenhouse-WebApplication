package com.craftinggamertom.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.pageBuilders.PageBuilder;

@Controller
public class ErrorController {
	@RequestMapping(value = "/errors", method = RequestMethod.GET)
	public ModelAndView goToErrorPage(HttpServletRequest httpRequest, Model model) {
        try {
		PageBuilder pageBuilder = new PageBuilder();
        pageBuilder.buildPage(model); //Proper navigation bar
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        
        //Everything below for printing the error on web page
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
        
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = get404ErrorMessage(); // Gets the html for the page
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
        }
        model.addAttribute("error-message", errorMsg);
        
        
        return new ModelAndView("errorPage");
    }
     
	/**
	 * gets the error code for the web page
	 * @param httpRequest
	 * @return
	 */
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }
    
    /**
     * To keep the class organized the strings can be accessed through methods. This is for the 404 page
     * @return
     */
    private String get404ErrorMessage() {
    	String message = "";
    	message +="\r\n" + 
    			"    <div class=\"middle-box text-center animated fadeInDown\">\r\n" + 
    			"        <h1>404</h1>\r\n" + 
    			"        <h3 class=\"font-bold\">Page Not Found</h3>\r\n" + 
    			"        <h5>Http Error Code: 404. Resource not found</h5>" +
    			"\r\n" + 
    			"        <div class=\"error-desc\">\r\n" + 
    			"            Sorry, but the page you are looking for has note been found. Try checking the URL for error, then hit the refresh button on your browser or try found something else in our app.\r\n" + 
    			"            <br/><a href=\"/\" class=\"btn btn-primary m-t\">Home</a>" + 
    			"        </div>\r\n" + 
    			"    </div>\r\n" + 
    			"";
    	
    	return message;
    }
}
