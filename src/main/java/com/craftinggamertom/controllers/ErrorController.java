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
                errorMsg = "Http Error Code: 404. Resource not found";
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
}
