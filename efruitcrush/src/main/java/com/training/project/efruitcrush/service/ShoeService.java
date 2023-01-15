package com.training.project.efruitcrush.service;

import java.util.List;

import com.training.project.efruitcrush.exception.FruitNotFoundException;
import com.training.project.efruitcrush.model.Shoes;

public interface ShoeService {

	public List<Shoes> getAllShoes();

	public Shoes getFruitById(int fruitId) throws FruitNotFoundException;

	public Shoes addFruit(Shoes fruit);

	public Shoes updateFruit(int fruitId, Shoes fruit) throws FruitNotFoundException;

	public void deleteFruit(int fruitId);

}
