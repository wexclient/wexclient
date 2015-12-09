package com.livngroup.gds;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.livngroup.gds.LivnDemoApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LivnDemoApplication.class)
@WebAppConfiguration
public class LivnDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
