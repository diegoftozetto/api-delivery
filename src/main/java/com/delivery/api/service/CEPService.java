package com.delivery.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.delivery.api.dto.EnderecoDTO;
import com.delivery.api.exception.NotFoundException;

@Service
public class CEPService {

	public EnderecoDTO consultarCEP(String cep) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://viacep.com.br/ws/" + cep + "/json/";

		try {
			return restTemplate.getForObject(url, EnderecoDTO.class);
		} catch (RestClientException ex) {
			throw new NotFoundException("CEP Não encontrado");
		}
	}
}
