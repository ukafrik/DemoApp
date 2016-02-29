package com.demo.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class DemoApp {

	@RequestMapping(method = RequestMethod.GET)
	public String sayHello(ModelMap model) {
		model.addAttribute("msg", "Hello from spring");
		return "demo";
	}
	
	@RequestMapping(value = "/greetings", method = RequestMethod.GET)
	public String greetings(ModelMap model) {
		boolean isAlive = isServerReachable();
		
		model.addAttribute("greeting", "This time, it is greetins!");
		model.addAttribute("isAlive", isAlive);
		
		return "demo";
	}
	
    // To check if server is reachable
	private boolean isServerReachable() {
        try {
            InetAddress.getByName("www.google.com").isReachable(3000); //Replace with your name
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	///////////
	@RequestMapping(value = "/pageStatus", method = RequestMethod.GET)
	public String getPageStatus() {
		 
        String[] hostList = { "http://crunchify.com", "http://yahoo.com",
                "http://www.ebay.com", "http://google.com",
                "http://www.example.co", "https://paypal.com",
                "http://bing.com/", "http://techcrunch.com/",
                "http://mashable.com/", "http://thenextweb.com/",
                "http://wordpress.com/", "http://wordpress.org/",
                "http://example.com/", "http://sjsu.edu/",
                "http://ebay.co.uk/", "http://google.co.uk/",
                "http://www.wikipedia.org/",
                "http://en.wikipedia.org/wiki/Main_Page" };
 
        for (int i = 0; i < hostList.length; i++) {
 
        	String status = "";
            String url = hostList[i];
            try {
            	status = getStatus(url);
            }
            catch (IOException ioe) {
            	ioe.printStackTrace();
            }
            System.out.println(url + "\t\tStatus:" + status);
        }
        
        return "demo";
 
    }
 
    private String getStatus(String url) throws IOException {
 
        String result = "";
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
 
            int code = connection.getResponseCode();
            if (code == 200) {
                result = "Green";
            }
        } catch (Exception e) {
            result = "->Red<-";
        }
        return result;
    }
 
	///////////
	
	
}
