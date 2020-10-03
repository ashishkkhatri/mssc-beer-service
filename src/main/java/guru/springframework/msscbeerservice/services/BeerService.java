package guru.springframework.msscbeerservice.services;

import java.util.UUID;

import javax.validation.Valid;

import guru.springframework.msscbeerservice.web.model.BeerDto;

public interface BeerService {

	BeerDto getById(UUID beerId);

	BeerDto saveNewBeer(@Valid BeerDto beerDto);

	BeerDto updateBeer(UUID beerId, @Valid BeerDto beerDto);

}
