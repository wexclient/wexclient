package com.livngroup.gds.web;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.livngroup.gds.LivnDemoApplication;
import com.livngroup.gds.response.GeneralResponse;

@Controller
public class ShutdownController {

	@RequestMapping(value = "/admin/shutdown", method = RequestMethod.POST)
	public @ResponseBody GeneralResponse shutdown() {
	  ExitCodeGenerator exitCodeGenerator = new ExitCodeGenerator() {
	    @Override
	    public int getExitCode() {
	      return 0;
	    }
	  };
	  SpringApplication.exit(LivnDemoApplication.APPLICATION_CONTEXT, exitCodeGenerator); 
	  return new GeneralResponse(true, "Shutting down, bye...");
	}
	 	
	
	
}
