package com.training.project.efruitcrush.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.project.efruitcrush.exception.FruitNotFoundException;
import com.training.project.efruitcrush.model.Shoes;
import com.training.project.efruitcrush.repository.ShoeRepository;
import com.training.project.efruitcrush.service.ShoeService;

@Service
public class ShoeServiceImpl implements ShoeService {

	@Autowired
	public ShoeRepository fruitRepository;

	@Override
	public List<Shoes> getAllShoes() {
		return fruitRepository.findAll();
	}

	@Override
	public Shoes getFruitById(int fruitId) throws FruitNotFoundException {
		return fruitRepository.findById(fruitId).orElseThrow(() -> new FruitNotFoundException("Fruit not found"));
	}

	@Override
	public Shoes addFruit(Shoes fruit) {
		return fruitRepository.save(fruit);
	}

	@Override
	public Shoes updateFruit(int fruitId, Shoes fruit) throws FruitNotFoundException {
		Shoes existingFruit = fruitRepository.findById(fruitId)
				.orElseThrow(() -> new FruitNotFoundException("Fruit not found"));
		existingFruit.setName(fruit.getName());
		existingFruit.setPrice(fruit.getPrice());
		existingFruit.setQuantityAvailable(fruit.getQuantityAvailable());
		existingFruit.setImageUrl(fruit.getImageUrl());
		return fruitRepository.save(existingFruit);
	}

	@Override
	public void deleteFruit(int fruitId) {
		fruitRepository.deleteById(fruitId);
	}

}
