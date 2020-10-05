package guru.springframework.msscbeerservice.services.inventory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import guru.springframework.msscbeerservice.bootstrap.BeerLoader;
import org.springframework.boot.test.context.SpringBootTest;

//@Disabled
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

	@Autowired
	BeerInventoryService beerInventoryService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void getOnhandInventory() {
		Integer qoh = beerInventoryService.getOnhandInventory(BeerLoader.BEER_1_UUID);
		
		System.out.println(qoh);
	}

}
