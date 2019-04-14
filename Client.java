package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
class Client {

	private @Id @GeneratedValue Long id;
	private String name;
	private String email;
	private String telephone;

	public Client(){
		
	}
	
	public Client(String name, String email, String telephone) {
		this.name = name;
		this.email = email;
		this.telephone =  telephone;
	}
	
}