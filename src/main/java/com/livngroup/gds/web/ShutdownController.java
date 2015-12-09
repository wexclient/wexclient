package com.livngroup.gds.web;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.livngroup.gds.LivnDemoApplication;

@Controller 
public class ShutdownController {

	@RequestMapping(value = "/admin/shutdown", method = RequestMethod.GET)
	public void shutdown() {
	  ExitCodeGenerator exitCodeGenerator = new ExitCodeGenerator() {
	    @Override
	    public int getExitCode() {
	      return 0;
	    }
	  };
	  SpringApplication.exit(LivnDemoApplication.APPLICATION_CONTEXT, exitCodeGenerator); 
	}
	 	
	
	
}
