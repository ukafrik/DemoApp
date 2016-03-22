package com.demo.controller;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
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
		
		try {
			this.readData();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
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
    
//    private void readData() throws IOException {
//    	Resource resource = new ClassPathResource("mydata.dat");
//    	InputStream in = resource.getInputStream();   
//    	BufferedReader bufRdr = new BufferedReader(new InputStreamReader(in));
//    	while (true) {
//    		String line = bufRdr.readLine();
//    		if (line == null) {
//    			break;
//    		}
//    		System.out.println("Line: " + line);
//    		
//    	}
//    }

    private void readData() {
    	ExecutorService executorService = Executors.newSingleThreadExecutor();
    	//Map<String, Callable<String>> callables = new HashMap<String, Callable<String>>();
    	Set<Callable<String>> callables = new HashSet<Callable<String>>();

    	System.out.println("myProperties: " + myProperties);
    	for (Entry<String, String> entry : myProperties.entrySet()) {
    		System.out.println("Key: " + entry.getKey());
    		System.out.println("Value:" + entry.getValue());
    		
    		final String jobKey = entry.getKey();
    		callables.add(new Callable<String>() {

				public String call() throws Exception {
			        URL oracle = new URL("http://localhost:7001/app");
			        URLConnection yc = oracle.openConnection();
			        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			        
			        String inputLine;
			        while ((inputLine = in.readLine()) != null) {
			            System.out.println(inputLine);
			        }
			        in.close();
			        
					return jobKey;
				}
    		});
    	}
    	
    	List<Future<String>> futures;
		try {
			futures = executorService.invokeAll(callables);
			
	    	for(Future<String> future : futures){
	    	    try {
					System.out.println("future.get = " + future.get());
				} 
	    	    catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	executorService.shutdown();
    }
    
    private void readData1() {
    	System.out.println("myProperties: " + myProperties);
    	for (Entry<String, String> entry : myProperties.entrySet()) {
    		System.out.println("Key: " + entry.getKey());
    		System.out.println("Value:" + entry.getValue());
    	}
    }
    
    @Bean(name = "myProperties")
    public static PropertiesFactoryBean mapper() {
            PropertiesFactoryBean bean = new PropertiesFactoryBean();
            bean.setLocation(new ClassPathResource("mydata.dat"));
            return bean;
    }	
    
    
    @Resource(name = "myProperties")
    private Map<String, String> myProperties;    
    ///////////
	
	
}
