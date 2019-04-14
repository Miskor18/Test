package com.example.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface ClientStorage extends JpaRepository<Client, Long> {

}
