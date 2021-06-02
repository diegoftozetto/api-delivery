package com.delivery.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.api.dto.RestauranteInputDTO;
import com.delivery.api.dto.RestauranteOutputDTO;
import com.delivery.api.dto.RestauranteOutputResumidoDTO;
import com.delivery.api.entity.Restaurante;
import com.delivery.api.mapper.RestauranteInputMapper;
import com.delivery.api.mapper.RestauranteOutputMapper;
import com.delivery.api.mapper.RestauranteOutputResumidoMapper;
import com.delivery.api.service.RestauranteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "Acesso aos restaurantes da plataforma")
@SecurityRequirement(name = "bearerAuth")
@ApiResponses(value = { @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content) })
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private RestauranteOutputMapper restauranteOutputMapper;

	@Autowired
	private RestauranteOutputResumidoMapper restauranteOutputResumidoMapper;

	@Autowired
	private RestauranteInputMapper restauranteInputMapper;
	
	@Operation(summary = "Obter todos os restaurantes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Restaurantes encontrados", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RestauranteOutputResumidoDTO.class))) }) })
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestauranteOutputResumidoDTO> getAll() {
		List<Restaurante> restaurantes = restauranteService.listar();
		return restauranteOutputResumidoMapper.mapearCollection(restaurantes);
	}

	@Operation(summary = "Obter restaurante por UUID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Restaurante encontrado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content) })
	@GetMapping("/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteOutputDTO get(@PathVariable String uuid) {
		Restaurante restaurante = restauranteService.buscarPorUUID(uuid);
		return restauranteOutputMapper.mapearEntity(restaurante);
	}

	@Operation(summary = "Adicionar restaurante")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Restaurante adicionado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteOutputDTO.class)) }) })
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public RestauranteOutputDTO post(@RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
		Restaurante restaurante = restauranteInputMapper.mapearEntity(restauranteInputDTO);
		restaurante = restauranteService.salvar(restaurante);
		return restauranteOutputMapper.mapearEntity(restaurante);
	}

	@Operation(summary = "Atualizar restaurante")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Restaurante atualizado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content) })
	@PutMapping("/{uuid}")
	public RestauranteOutputDTO put(@PathVariable String uuid, @RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
		Restaurante restaurante = restauranteInputMapper.mapearEntity(restauranteInputDTO);
		restaurante = restauranteService.atualizar(uuid, restaurante);
		return restauranteOutputMapper.mapearEntity(restaurante);
	}

	@Operation(summary = "Ajustar restaurante")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Restaurante ajustado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content) })
	@PatchMapping("/{uuid}")
	public RestauranteOutputDTO patch(@PathVariable String uuid, @RequestBody Map<String, Object> changes) {
		Restaurante restaurante = restauranteService.ajustar(uuid, changes);
		return restauranteOutputMapper.mapearEntity(restaurante);
	}

	@Operation(summary = "Remover restaurante")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Restaurante removido", content = @Content),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content) })
	@DeleteMapping("/{uuid}")
	public ResponseEntity<Restaurante> delete(@PathVariable String uuid) {
		restauranteService.excluir(uuid);
		return ResponseEntity.noContent().build();
	}
}
