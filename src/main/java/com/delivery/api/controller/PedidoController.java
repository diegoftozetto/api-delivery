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

import com.delivery.api.dto.PedidoInputDTO;
import com.delivery.api.dto.PedidoOutputDTO;
import com.delivery.api.dto.PedidoOutputResumidoDTO;
import com.delivery.api.dto.ProdutoOutputDTO;
import com.delivery.api.entity.Cliente;
import com.delivery.api.entity.Pedido;
import com.delivery.api.entity.Restaurante;
import com.delivery.api.mapper.PedidoInputMapper;
import com.delivery.api.mapper.PedidoOutputMapper;
import com.delivery.api.mapper.PedidoOutputResumidoMapper;
import com.delivery.api.service.ClienteService;
import com.delivery.api.service.PedidoService;
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
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Acesso aos pedidos da plataforma")
@SecurityRequirement(name = "bearerAuth")
@ApiResponses(value = { @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content) })
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private PedidoOutputMapper pedidoOutputMapper;

	@Autowired
	private PedidoOutputResumidoMapper pedidoOutputResumidoMapper;

	@Autowired
	private PedidoInputMapper pedidoInputMapper;

	@Operation(summary = "Obter todos os pedidos")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedidos encontrados", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PedidoOutputResumidoDTO.class))) }) })
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<PedidoOutputResumidoDTO> getAll() {
		List<Pedido> pedidos = pedidoService.listar();
		return pedidoOutputResumidoMapper.mapearCollection(pedidos);
	}

	@Operation(summary = "Obter pedido por UUID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedido encontrado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content) })
	@GetMapping("/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	public PedidoOutputDTO get(@PathVariable String uuid) {
		Pedido pedido = pedidoService.buscarPorUUID(uuid);
		return pedidoOutputMapper.mapearEntity(pedido);
	}

	@Operation(summary = "Obter pedidos por cliente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedidos encontrados", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PedidoOutputResumidoDTO.class))) }),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content) })
	@GetMapping("/cliente/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PedidoOutputResumidoDTO> listarTodosOsPedidosDoCliente(@PathVariable String uuid) {
		clienteService.buscarPorUUID(uuid);
		List<Pedido> pedidos = pedidoService.listarPorCliente(uuid);
		return pedidoOutputResumidoMapper.mapearCollection(pedidos);
	}

	@Operation(summary = "Obter pedidos por restaurante")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedidos encontrados", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PedidoOutputResumidoDTO.class))) }),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content) })
	@GetMapping("/restaurante/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PedidoOutputResumidoDTO> listarTodosOsPedidosDoRestaurante(@PathVariable String uuid) {
		restauranteService.buscarPorUUID(uuid);
		List<Pedido> pedidos = pedidoService.listarPorRestaurante(uuid);
		return pedidoOutputResumidoMapper.mapearCollection(pedidos);
	}

	@Operation(summary = "Adicionar pedido")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Pedido adicionado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoOutputDTO.class)) }) })
	@PostMapping("/cliente/{clienteUuid}/restaurante/{restauranteUuid}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PedidoOutputDTO adicionar(@PathVariable String clienteUuid, @PathVariable String restauranteUuid,
			@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
		Cliente cliente = clienteService.buscarPorUUID(clienteUuid);
		Restaurante restaurante = restauranteService.buscarPorUUID(restauranteUuid);

		Pedido pedido = pedidoInputMapper.mapearEntity(pedidoInputDTO);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido = pedidoService.salvar(pedido);

		return pedidoOutputMapper.mapearEntity(pedido);
	}

	@Operation(summary = "Atualizar pedido")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedido atualizado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content) })
	@PutMapping("/{uuid}")
	public PedidoOutputDTO alterar(@PathVariable String uuid, @RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
		Pedido pedido = pedidoInputMapper.mapearEntity(pedidoInputDTO);
		pedido = pedidoService.atualizar(uuid, pedido);

		return pedidoOutputMapper.mapearEntity(pedido);
	}

	@Operation(summary = "Ajustar pedido")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedido ajustado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content) })
	@PatchMapping("/{uuid}")
	public PedidoOutputDTO ajustar(@PathVariable String uuid, @RequestBody Map<String, Object> campos) {
		Pedido pedido = pedidoService.ajustar(uuid, campos);
		return pedidoOutputMapper.mapearEntity(pedido);
	}

	@Operation(summary = "Remover pedido")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Pedido removido", content = @Content),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content) })
	@DeleteMapping("/{uuid}")
	public ResponseEntity<Pedido> deletarProduto(@PathVariable String uuid) {
		pedidoService.excluir(uuid);
		return ResponseEntity.noContent().build();
	}
}
