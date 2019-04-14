package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

	private final ClientStorage clientStorage;
	
	ClientController(ClientStorage clientStorage){
		this.clientStorage = clientStorage;
	}
	
	@GetMapping("/clients")
	//For test in browser uncomment
	//@GetMapping(value = "/clients", produces = "application/json; charset=UTF-8")
	Resources<Resource<Client>> all() {

		List<Resource<Client>> clients = clientStorage.findAll().stream()
			.map(client -> new Resource<>(client,
				linkTo(methodOn(ClientController.class).oneClient(client.getId())).withSelfRel(),
				linkTo(methodOn(ClientController.class).all()).withRel("clients")))
			.collect(Collectors.toList());

		return new Resources<>(clients,
			linkTo(methodOn(ClientController.class).all()).withSelfRel());
	}
	
	@GetMapping("/clients/{id}")
	//For test in browser uncomment
	//@GetMapping(value = "/clients/{id}", produces = "application/json; charset=UTF-8")
	Resource<Client> oneClient(@PathVariable Long id) {
		Client client = clientStorage.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(id));

			return new Resource<>(client,
				linkTo(methodOn(ClientController.class).oneClient(id)).withSelfRel(),
				linkTo(methodOn(ClientController.class).all()).withRel("clients"));
	}
	
	@PostMapping("/clients")
	Client addNewClient(@RequestBody Client client) {
		return clientStorage.save(client);
	}
	
	@PutMapping("/clients/{id}")
	Client editClient(@RequestBody Client newClient, @PathVariable Long id) {
		return clientStorage.findById(id).map(client -> {
			client.setName(newClient.getName());
			client.setEmail(newClient.getEmail());
			client.setTelephone(newClient.getTelephone());
			return clientStorage.save(client);
		}).orElseGet(() -> {
			newClient.setId(id);
			return clientStorage.save(newClient);
		});
	}
	
	@DeleteMapping("/clients/{id}")
	void deleteClient(@PathVariable Long id) {
		clientStorage.deleteById(id);
	}
}
