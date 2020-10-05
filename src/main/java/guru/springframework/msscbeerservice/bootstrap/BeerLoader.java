package guru.springframework.msscbeerservice.bootstrap;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;

//@Component
public class BeerLoader implements CommandLineRunner{
	public static final String BEER_1_UPC="0766564332";
	public static final String BEER_2_UPC="0766564336";
	public static final String BEER_3_UPC="0766564334";
	public static final UUID BEER_1_UUID = UUID.fromString("55f1ef01-07c7-4839-a811-579e4e0c6e2e");
	public static final UUID BEER_2_UUID = UUID.fromString("584edfd5-a541-49b2-8e2f-0a6ce899e370");
	public static final UUID BEER_3_UUID = UUID.fromString("52534feb-9a00-4a9a-b9f4-0d04a36a67a2");

	private final BeerRepository beerRepository;
	
	public BeerLoader(BeerRepository beerRepository) {
		super();
		this.beerRepository = beerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadBeerObjects();
	}
	
	public void loadBeerObjects() {
		if(beerRepository.count() == 0) {
			beerRepository.save(Beer.builder()
							.beerName("Mango bobs")
							.beerStyle("IPA")
							.quantityToBrew(200)
							.upc(BEER_1_UPC)
							.price(new BigDecimal("12.34"))
							.minOnHand(12)
							.build());
			
			beerRepository.save(Beer.builder()
					.beerName("Galaxy cat")
					.beerStyle("PALE_ALE")
					.quantityToBrew(200)
					.upc(BEER_2_UPC)
					.price(new BigDecimal("11.34"))
					.minOnHand(12)
					.build());
			
			beerRepository.save(Beer.builder()
					.beerName("No Hammer On The Bar")
					.beerStyle("PALE_ALE")
					.quantityToBrew(200)
					.upc(BEER_3_UPC)
					.price(new BigDecimal("11.34"))
					.minOnHand(12)
					.build());
		}
		System.out.println("Loading beers: " + beerRepository.count());
	}
	
}
