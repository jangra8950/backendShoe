package com.training.project.efruitcrush.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.project.efruitcrush.model.Shoes;

public interface ShoeRepository extends JpaRepository<Shoes, Integer> {

}
