package com.cinema.repository;
import org.springframework.data.repository.CrudRepository;

import com.cinema.model.UserDao;
public interface UserRepository extends CrudRepository<UserDao, Long> {
    UserDao findByUsername(String username);
}