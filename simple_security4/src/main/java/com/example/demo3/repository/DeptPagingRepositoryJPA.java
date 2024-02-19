package com.example.demo3.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo3.entity.Department;

public interface DeptPagingRepositoryJPA extends PagingAndSortingRepository<Department, Integer> {

}
