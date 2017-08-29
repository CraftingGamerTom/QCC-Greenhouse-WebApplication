package com.craftinggamertom.view;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.pageBuilders.DataGraphBuilder;
import com.craftinggamertom.pageBuilders.LiveDataBuilder;
import com.mongodb.MongoSocketOpenException;

@Controller
@RequestMapping("view")
public class ViewController {

	/**
	 * Landing page for view data
	 * @return The index page for viewing data
	 */
	@RequestMapping(value = "")
	public String handleViewRequest() {
		return "view/index";
	}
	
	/**
	 * Handles the request to view the sensor data graph UI
	 * 
	 * @param sensorID The ID as defined by the raspberry pi and held in the database
	 * @param timing The timing to gather data from the appropriate table
	 * @param startDate The first date for the data to be shown
	 * @param model
	 * @return the page containing loaded data
	 */
	@RequestMapping(value = "sensor-data")
    public ModelAndView handleSensorDataRequest (
              @RequestParam(value = "c-sensor", defaultValue="indoor-temperature") String cSensor,
              @RequestParam(value = "c-timing", defaultValue="h") String cTiming,
              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
              @RequestParam(value = "start-date", defaultValue="2017-08-25") String cDate,
              Model model) {

		model.addAttribute("c-sensor", "\"" + cSensor + "\""); // Must use these quote insertions for the UI to understand
		model.addAttribute("c-timing", "\"" + cTiming + "\""); // Must use these quote insertions
		model.addAttribute("c-date", "\"" + cDate + "\""); // Must use these quote insertions
		
		
		try {
			DataGraphBuilder response = new DataGraphBuilder();
			model = response.buildPage(cSensor, cTiming, cDate, model);
		}catch(MongoSocketOpenException e) {
			// Currently not catching the exception when the mongoDB is unaccessible 
			System.out.println(" ***** ERROR CONNECTING TO MONGO DB ***** ");
			e.printStackTrace();
			System.out.println("**************** END ERROR ***************");
		}catch(Exception e){
			System.out.println("Exception: ");
			e.printStackTrace();
		}
		
		
        return new ModelAndView("view/sensor-data");
    }
	
	/**
	 * Handles the request to view the sensor data graph UI
	 * 
	 * @param model for the variables
	 * @return the page containing loaded data
	 */
	@RequestMapping(value = "live-data")
    public ModelAndView handleLiveDataRequest(Model model) {
			
		try {
			LiveDataBuilder response = new LiveDataBuilder();
			model = response.buildPage();
		}catch(MongoSocketOpenException e) {
			System.out.println(" ***** ERROR CONNECTING TO MONGO DB ***** ");
			e.printStackTrace();
			System.out.println("**************** END ERROR ***************");
		}catch(Exception e){
			System.out.println("Exception: ");
			e.printStackTrace();
		}
		
		
        return new ModelAndView("view/live-data");
    }
	
	/**
	 * 
	 * @param sensorID The ID as defined by the raspberry pi and held in the database
	 * @param timing The timing to gather data from the appropriate table
	 * @param startDate The first date for the data to be shown
	 * @param model
	 * @return the page containing loaded data
	 */
	@RequestMapping(value = "test")
    public ModelAndView handleTestRequest (
              @RequestParam(value = "sensorID", defaultValue="rp1-01") String sensorID,
              @RequestParam(value = "timing", defaultValue="hourly") String timing,
              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
              @RequestParam(value = "startDate", defaultValue="2000-10-31") String startDate,
              Model model) {

        model.addAttribute("sensor report test with ID, timing, and start-time : " +
                                                             sensorID + ", " + timing + 
                                                             ", " + startDate );
        return new ModelAndView("view/test", "sensorID", sensorID);
    }
	
}
