package guru.springframework.msscbeerservice.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;

@Component
public class BeerLoader implements CommandLineRunner{
	public static final String BEER_1_UPC="0766564332";
	public static final String BEER_2_UPC="0766564336";
	public static final String BEER_3_UPC="0766564334";

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
