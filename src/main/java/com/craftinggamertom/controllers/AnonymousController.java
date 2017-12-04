package com.craftinggamertom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.pageBuilders.FeedBuilder;

@Controller
public class AnonymousController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage() {
		String redirectUrl = "feed";
		return "redirect:" + redirectUrl;
	}

	@RequestMapping(value = "/feed", method = RequestMethod.GET)
	public ModelAndView feedPage(Model model) {

		try {

			FeedBuilder response = new FeedBuilder();
			model = response.buildPage(model);

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView("anonymous/feed");
	}

}