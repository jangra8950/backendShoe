package com.training.project.efruitcrush.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.project.efruitcrush.dto.ShoeDto;
import com.training.project.efruitcrush.exception.FruitNotFoundException;
import com.training.project.efruitcrush.model.Shoes;
import com.training.project.efruitcrush.response.ApiResponse;
import com.training.project.efruitcrush.service.ShoeService;

@RestController
@RequestMapping(path = { "/shoes", "/admin/shoes" })
public class ShoeController {

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public ShoeService shoeService;

	@GetMapping
	public List<ShoeDto> getAllShoes() {
		return shoeService.getAllShoes().stream().map(shoe -> modelMapper.map(shoe, ShoeDto.class))
				.collect(Collectors.toList());
	}

	@PostMapping("/addShoe")
	public ResponseEntity<ShoeDto> addshoe(@RequestBody ShoeDto shoeDto) {
		Shoes shoeRequest = modelMapper.map(shoeDto, Shoes.class);
		Shoes shoe = shoeService.addFruit(shoeRequest);
		ShoeDto shoeResponse = modelMapper.map(shoe, ShoeDto.class);
		return new ResponseEntity<>(shoeResponse, CREATED);

	}

	@PutMapping("/editShoe/{id}")
	public ResponseEntity<ShoeDto> updateShoe(@PathVariable(name = "id") String id, @RequestBody ShoeDto shoeDto)
			throws FruitNotFoundException {
		Shoes shoeRequest = modelMapper.map(shoeDto, Shoes.class);
		Shoes shoe = shoeService.updateFruit(Integer.parseInt(id), shoeRequest);
		ShoeDto shoeResponse = modelMapper.map(shoe, ShoeDto.class);
		return ResponseEntity.ok().body(shoeResponse);
	}

	@DeleteMapping("/deleteFruit/{id}")
	public ResponseEntity<ApiResponse> deleteFruit(@PathVariable int id) {
		fruitService.deleteFruit(id);
		ApiResponse apiResponse = new ApiResponse(OK, "Fruit deleted successfully");
		return new ResponseEntity<>(apiResponse, OK);
	}

}
