package com.example.blogapprestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blogapprestapi.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
