package com.backend.stayEasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.stayEasy.entity.Category;


public interface ICategoryRepository extends JpaRepository<Category, UUID>{

}
