package guru.springframework.msscbeerservice.services;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;

public interface BeerService {

	BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

	BeerDto saveNewBeer(@Valid BeerDto beerDto);

	BeerDto updateBeer(UUID beerId, @Valid BeerDto beerDto);

	BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageReuest, Boolean showInventoryOnHand);

}
