package com.example.demo;

public class ClientNotFoundException extends RuntimeException {

	public ClientNotFoundException(Long id) {
		super("Client with id: " + id + " not found.");
	}
}
