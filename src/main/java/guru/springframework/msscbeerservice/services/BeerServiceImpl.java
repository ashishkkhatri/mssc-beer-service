package guru.springframework.msscbeerservice.services;

import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService{
	
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;
	
	// condition parameter in the cacheable annotation shows that if showInventoryOnHand is false then cache should not be enabled.
	// In case we do not pass the key here ehcache generates its own key according to the parameters value.
	// this cacheName should match the alias name in the ehcache.xml file.
	@Cacheable(cacheNames = "beerCache", key="#beerId", condition = "#showInventoryOnHand == false")
	@Override
	public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
		System.out.println("I was called");
		if(showInventoryOnHand) {
			return beerMapper.beerToBeerDtoWithInventory(
					beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
					);
		} else {
			return beerMapper.beerToBeerDto(
					beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
					);
		}
	}

	@Override
	public BeerDto saveNewBeer(BeerDto beerDto) {
		return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
	}

	@Override
	public BeerDto updateBeer(UUID beerId, @Valid BeerDto beerDto) {
		Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
		
		beer.setBeerName(beerDto.getBeername());
		beer.setBeerStyle(beerDto.getBeerStyle().name());
		beer.setPrice(beerDto.getPrice());
		beer.setUpc(beerDto.getUpc());
		return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}

	// condition parameter in the cacheable annotation shows that if showInventoryOnHand is false then cache should not be enabled.
	@Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
	@Override
	public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
		System.out.println("I was called");
		
		BeerPagedList beerPagedlist;
		Page<Beer> beerPage;
		
		if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
		} else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
		} else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
		} else {
			beerPage = beerRepository.findAll(pageRequest);
		}
		if(showInventoryOnHand) {
			beerPagedlist = new BeerPagedList(beerPage
					.getContent()
					.stream()
					.map(beerMapper::beerToBeerDtoWithInventory)
					.collect(Collectors.toList()),
					pageRequest.of(beerPage.getPageable().getPageNumber(),
									beerPage.getPageable().getPageSize()),
									beerPage.getTotalElements()
					);
		} else {
			beerPagedlist = new BeerPagedList(beerPage
					.getContent()
					.stream()
					.map(beerMapper::beerToBeerDto)
					.collect(Collectors.toList()),
					pageRequest.of(beerPage.getPageable().getPageNumber(),
									beerPage.getPageable().getPageSize()),
									beerPage.getTotalElements()
					);
		}
		
		
		
		return beerPagedlist;
	}
	
	@Cacheable(cacheNames = "beerUpcCache")
	@Override
	public BeerDto getByUpc(String upc) {
		return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
	}

}
