package com.livngroup.gds.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.livngroup.gds.service.WexPingService;

@Component("wex")
public class WexHealthIndicator implements HealthIndicator {

	@Autowired
	private WexPingService wexPingService;
	
	@Override
	public Health health() {
		Health.Builder healthBuilder = new Health.Builder();
		try {
			wexPingService.ping();
			healthBuilder.up();
		} catch (Throwable exc) {
			healthBuilder.outOfService().withDetail("error", exc.getMessage());
		}
		return healthBuilder.build();
	}

}
