package com.github.felixbyrjall.vehiclelookup;

import com.github.felixbyrjall.vehiclelookup.service.VehicleLookupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VehicleLookupApplicationTests {
	@Autowired
	private VehicleLookupService vehicleLookupService;

	@Test
	void contextLoads() {
	}

	@Test
	void testPublishVehicleSearchedEvent() {
		vehicleLookupService.publishVehicleSearchedEvent("VIN123456", "1");
	}

}
