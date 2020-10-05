package guru.springframework.msscbeerservice.services;

import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

//@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService{
	
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;

	public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
		super();
		this.beerRepository = beerRepository;
		this.beerMapper = beerMapper;
	}

	@Override
	public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
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

	@Override
	public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
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

}
