package com.delivery.api.controller;

import java.util.ArrayList;
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

import com.delivery.api.dto.PedidoItemInputDTO;
import com.delivery.api.dto.PedidoItemOutputDTO;
import com.delivery.api.dto.PedidoItemOutputResumidoDTO;
import com.delivery.api.entity.Pedido;
import com.delivery.api.entity.PedidoItem;
import com.delivery.api.entity.Produto;
import com.delivery.api.mapper.PedidoItemInputMapper;
import com.delivery.api.mapper.PedidoItemOutputMapper;
import com.delivery.api.mapper.PedidoItemOutputResumidoMapper;
import com.delivery.api.service.PedidoItemService;
import com.delivery.api.service.PedidoService;
import com.delivery.api.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/itens")
@Tag(name = "Itens do Pedido", description = "Acesso aos itens do pedido da plataforma")
@SecurityRequirement(name = "bearerAuth")
@ApiResponses(value = { @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content) })
public class PedidoItemController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private PedidoItemService pedidoItemService;

	@Autowired
	private PedidoItemOutputMapper pedidoItemOutputMapper;

	@Autowired
	private PedidoItemOutputResumidoMapper pedidoItemOutputResumidoMapper;

	@Autowired
	private PedidoItemInputMapper pedidoItemInputMapper;

	@Operation(summary = "Obter item do pedido por UUID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Item do pedido encontrado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoItemOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Item do pedido não encontrado", content = @Content) })
	@GetMapping("/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	public PedidoItemOutputDTO getItem(@PathVariable String uuid) {
		PedidoItem pedidoItem = pedidoItemService.buscarPorUUID(uuid);
		return pedidoItemOutputMapper.mapearEntity(pedidoItem);
	}

	@Operation(summary = "Atualizar item do pedido")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Item do pedido atualizado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoItemOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Item do pedido não encontrado", content = @Content) })
	@PutMapping("/{uuid}")
	public PedidoItemOutputDTO alterarItem(@PathVariable String uuid,
			@RequestBody @Valid PedidoItemInputDTO pedidoItemInputDTO) {
		Produto produto = produtoService.buscarPorUUID(pedidoItemInputDTO.getProdutoUuid());

		PedidoItem pedidoItem = pedidoItemInputMapper.mapearEntity(pedidoItemInputDTO);
		pedidoItem.setProduto(produto);
		pedidoItem = pedidoItemService.atualizar(uuid, pedidoItem);

		return pedidoItemOutputMapper.mapearEntity(pedidoItem);
	}

	@Operation(summary = "Ajustar item do pedido")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Item do pedido ajustado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoItemOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Item do pedido não encontrado", content = @Content) })
	@PatchMapping("/{uuid}")
	public PedidoItemOutputDTO ajustarItem(@PathVariable String uuid, @RequestBody Map<String, Object> campos) {
		PedidoItem pedidoItem = pedidoItemService.ajustar(uuid, campos);
		return pedidoItemOutputMapper.mapearEntity(pedidoItem);
	}

	@Operation(summary = "Remover item do pedido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Item do pedido removido", content = @Content),
			@ApiResponse(responseCode = "404", description = "Item do pedido não encontrado", content = @Content) })
	@DeleteMapping("/{uuid}")
	public ResponseEntity<PedidoItem> deletarProduto(@PathVariable String uuid) {
		pedidoItemService.excluir(uuid);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Obter itens do pedido")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Item do pedido encontrado", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PedidoItemOutputDTO.class))) }),
			@ApiResponse(responseCode = "404", description = "Item do pedido não encontrado", content = @Content) })
	@GetMapping("/pedido/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PedidoItemOutputResumidoDTO> getPedido(@PathVariable String uuid) {
		pedidoService.buscarPorUUID(uuid);
		List<PedidoItem> pedidoItens = pedidoItemService.listarItensDoPedido(uuid);
		return pedidoItemOutputResumidoMapper.mapearCollection(pedidoItens);
	}

	@Operation(summary = "Adicionar itens ao pedido")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Itens do pedido adicionado", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PedidoItemOutputDTO.class))) }),
			@ApiResponse(responseCode = "404", description = "Pedido ou produto não encontrado", content = @Content) })
	@PostMapping("/pedido/{uuid}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public List<PedidoItemOutputDTO> adicionarListItem(@PathVariable String uuid,
			@RequestBody @Valid List<PedidoItemInputDTO> pedidoItemInputDTO) {
		Pedido pedido = pedidoService.buscarPorUUID(uuid);

		List<PedidoItem> listPedidoItem = new ArrayList<>();
		for (PedidoItemInputDTO pedItemDTO : pedidoItemInputDTO) {
			Produto produto = produtoService.buscarPorUUID(pedItemDTO.getProdutoUuid());

			PedidoItem pedidoItem = pedidoItemInputMapper.mapearEntity(pedItemDTO);
			pedidoItem.setPedido(pedido);
			pedidoItem.setProduto(produto);
			listPedidoItem.add(pedidoItem);
		}

		for (PedidoItem currentPedidoItem : listPedidoItem) {
			pedidoItemService.salvar(currentPedidoItem);
		}

		return pedidoItemOutputMapper.mapearCollection(listPedidoItem);
	}
}
