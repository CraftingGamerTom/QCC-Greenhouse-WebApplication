package com.craftinggamertom.pageBuilders;


import org.springframework.ui.Model;

public class LiveDataBuilder extends PageBuilder {
	
	public LiveDataBuilder() {
		super();
	}
	
	
	/**
	 * Handles everything that is required to return all the needed
	 * components of the live data module
	 * 
	 * @return Model containing all the variables
	 */
	public Model buildPage() {


		return addPageAttributes();

	}

	/**
	 * Models all variables to be given to the Front end
	 * 
	 * @return Model to be returned back to the front end
	 */
	private Model addPageAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
}
