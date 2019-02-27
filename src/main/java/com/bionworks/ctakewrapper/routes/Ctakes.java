package com.bionworks.ctakewrapper.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bionworks.ctakewrapper.beans.CTakeResponse;
import com.bionworks.ctakewrapper.beans.CTakesRequest;
import com.bionworks.ctakewrapper.service.CtakesService;

@RestController
@RequestMapping("/")
public class Ctakes {

	@Autowired
	private CtakesService ctakesService;

	@PostMapping
	public CTakeResponse getClinicalMappinsg(@RequestBody CTakesRequest request) {
		return ctakesService.getResponse(request.getDocument());
	}

}
