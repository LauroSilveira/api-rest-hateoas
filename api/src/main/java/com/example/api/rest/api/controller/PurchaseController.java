package com.example.api.rest.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.rest.api.dto.PurchaseDto;
import com.example.api.rest.api.service.PurchaseService;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

	private static final Logger LOG = LoggerFactory.getLogger(PurchaseController.class);

	@Autowired
	private PurchaseService purchaseService;

	@PostMapping
	public ResponseEntity<PurchaseDto> createPurchase(@RequestBody PurchaseDto purchaseDto) {
		LOG.info("A new Purchase has been received");
		purchaseService.savePurchase(purchaseDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/purchase/{purchaseId}")
	public ResponseEntity<PurchaseDto> getPurchaseById(@RequestParam String purchaseId) {
		LOG.info("Retrieving date of ID {} ", purchaseId);
		Optional<PurchaseDto> purchaseList = purchaseService.getPurchaseById(purchaseId);
		if (purchaseList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			purchaseList.stream().forEach(pur -> {
				pur.getProducstDto().stream().forEach(p -> {
					Link link = linkTo(PurchaseController.class).slash(p.getProductId()).withSelfRel();
					p.add(link);
				});
			});
		}
		return new ResponseEntity<>(purchaseList.get(), HttpStatus.FOUND);
	}

	@GetMapping
	public ResponseEntity<List<PurchaseDto>> getAllPurchases() {
		LOG.info("Retrieveng all Purchases");
		Optional<List<PurchaseDto>> allPurchases = purchaseService.getAllPurchases();
		if (allPurchases.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			allPurchases.get().stream().forEach(a -> {
				a.getProducstDto().forEach(p -> {
					Link link = linkTo(PurchaseController.class).slash(p.getProductId()).withSelfRel();
					p.add(link);
				});
			});
			return new ResponseEntity<>(allPurchases.get(), HttpStatus.OK);
		}
	}

	@PutMapping
	public ResponseEntity<PurchaseDto> updatePurchaseById(@RequestBody PurchaseDto orderDto, @RequestParam String id) {
		LOG.info("Update request with id {}", id);
		Optional<PurchaseDto> entityUpdated = purchaseService.updatePurchaseById(orderDto, id);
		if (entityUpdated.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			entityUpdated.stream().forEach(e -> {
				e.getProducstDto().stream().forEach(p -> {
					Link link = linkTo(PurchaseController.class).slash(p.getProductId()).withSelfRel();
					p.add(link);
				});
			});
			return new ResponseEntity<>(entityUpdated.get(), HttpStatus.OK);
		}
	}

	@DeleteMapping
	public ResponseEntity<PurchaseDto> deletePurchaseById(@RequestParam String purchaseId) {
		LOG.info("Deleting a Purchase with Id {}", purchaseId);
		if (!purchaseService.deletePurchaseById(purchaseId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
