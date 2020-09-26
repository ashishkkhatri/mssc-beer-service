package guru.springframework.msscbeerservice.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;

@Component
public class BeerLoader implements CommandLineRunner{

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
							.upc(32442344243L)
							.price(new BigDecimal("12.34"))
							.minOnHand(12)
							.build());
			
			beerRepository.save(Beer.builder()
					.beerName("Galaxy cat")
					.beerStyle("PALE_ALE")
					.quantityToBrew(200)
					.upc(32442344283L)
					.price(new BigDecimal("11.34"))
					.minOnHand(12)
					.build());
		}
		System.out.println("Loading beers: " + beerRepository.count());
	}
	
}
