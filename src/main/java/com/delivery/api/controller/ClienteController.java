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

import com.delivery.api.dto.ClienteInputDTO;
import com.delivery.api.dto.ClienteOutputDTO;
import com.delivery.api.dto.ClienteOutputResumidoDTO;
import com.delivery.api.entity.Cliente;
import com.delivery.api.mapper.ClienteInputMapper;
import com.delivery.api.mapper.ClienteOutputMapper;
import com.delivery.api.mapper.ClienteOutputResumidoMapper;
import com.delivery.api.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Acesso aos clientes da plataforma")
@SecurityRequirement(name = "bearerAuth")
@ApiResponses(value = { @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content) })
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteOutputMapper clienteOutputMapper;

	@Autowired
	private ClienteOutputResumidoMapper clienteOutputResumidoMapper;

	@Autowired
	private ClienteInputMapper clienteInputMapper;

	@Operation(summary = "Obter todos os clientes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Clientes encontrados", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClienteOutputResumidoDTO.class))) }) })
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<ClienteOutputResumidoDTO> getAll() {
		List<Cliente> clientes = clienteService.listar();
		return clienteOutputResumidoMapper.mapearCollection(clientes);
	}

	@Operation(summary = "Obter cliente por UUID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content) })
	@GetMapping("/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	public ClienteOutputDTO get(@PathVariable String uuid) {
		Cliente cliente = clienteService.buscarPorUUID(uuid);
		return clienteOutputMapper.mapearEntity(cliente);
	}

	@Operation(summary = "Adicionar cliente")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Cliente adicionado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteOutputDTO.class)) }) })
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClienteOutputDTO post(@RequestBody @Valid ClienteInputDTO clienteInputDTO) {
		Cliente cliente = clienteInputMapper.mapearEntity(clienteInputDTO);
		cliente = clienteService.salvar(cliente);
		return clienteOutputMapper.mapearEntity(cliente);
	}

	@Operation(summary = "Atualizar cliente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente atualizado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content) })
	@PutMapping("/{uuid}")
	public ClienteOutputDTO put(@PathVariable String uuid, @RequestBody @Valid ClienteInputDTO clienteInputDTO) {
		Cliente cliente = clienteInputMapper.mapearEntity(clienteInputDTO);
		cliente = clienteService.atualizar(uuid, cliente);
		return clienteOutputMapper.mapearEntity(cliente);
	}

	@Operation(summary = "Ajustar cliente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente ajustado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content) })
	@PatchMapping("/{uuid}")
	public ClienteOutputDTO patch(@PathVariable String uuid, @RequestBody Map<String, Object> changes) {
		Cliente cliente = clienteService.ajustar(uuid, changes);
		return clienteOutputMapper.mapearEntity(cliente);
	}

	@Operation(summary = "Remover cliente")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Cliente removido", content = @Content),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content) })
	@DeleteMapping("/{uuid}")
	public ResponseEntity<Cliente> delete(@PathVariable String uuid) {
		clienteService.excluir(uuid);
		return ResponseEntity.noContent().build();
	}
}
