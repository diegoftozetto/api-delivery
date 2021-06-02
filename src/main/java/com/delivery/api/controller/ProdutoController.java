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

import com.delivery.api.dto.ProdutoInputDTO;
import com.delivery.api.dto.ProdutoOutputDTO;
import com.delivery.api.dto.ProdutoOutputResumidoDTO;
import com.delivery.api.dto.RestauranteOutputDTO;
import com.delivery.api.entity.Produto;
import com.delivery.api.entity.Restaurante;
import com.delivery.api.mapper.ProdutoInputMapper;
import com.delivery.api.mapper.ProdutoOutputMapper;
import com.delivery.api.mapper.ProdutoOutputResumidoMapper;
import com.delivery.api.service.ProdutoService;
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
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Acesso aos produtos da plataforma")
@SecurityRequirement(name = "bearerAuth")
@ApiResponses(value = { @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content) })
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private ProdutoOutputMapper produtoOutputMapper;

	@Autowired
	private ProdutoOutputResumidoMapper produtoOutputResumidoMapper;

	@Autowired
	private ProdutoInputMapper produtoInputMapper;

	@Operation(summary = "Obter todos os produtos")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Produtos encontrados", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProdutoOutputResumidoDTO.class))) }) })
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<ProdutoOutputResumidoDTO> getAll() {
		List<Produto> produtos = produtoService.listar();
		return produtoOutputResumidoMapper.mapearCollection(produtos);
	}

	@Operation(summary = "Obter produtos por restaurante")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Produtos encontrados", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProdutoOutputResumidoDTO.class))) }),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content) })
	@GetMapping("/restaurante/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ProdutoOutputResumidoDTO> listarTodosOsProdutosDoRestaurante(@PathVariable String uuid) {
		restauranteService.buscarPorUUID(uuid);
		List<Produto> produtos = produtoService.listarPorRestaurante(uuid);
		return produtoOutputResumidoMapper.mapearCollection(produtos);
	}

	@Operation(summary = "Obter produto por UUID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Produto encontrado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content) })
	@GetMapping("/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	public ProdutoOutputDTO get(@PathVariable String uuid) {
		Produto produto = produtoService.buscarPorUUID(uuid);
		return produtoOutputMapper.mapearEntity(produto);
	}

	@Operation(summary = "Adicionar produto")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Produto adicionado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoOutputDTO.class)) }) })
	@PostMapping("/restaurante/{uuid}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProdutoOutputDTO adicionar(@PathVariable String uuid, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
		Restaurante restaurante = restauranteService.buscarPorUUID(uuid);

		Produto produto = produtoInputMapper.mapearEntity(produtoInputDTO);
		produto.setRestaurante(restaurante);
		produto = produtoService.salvar(produto);

		return produtoOutputMapper.mapearEntity(produto);
	}

	@Operation(summary = "Atualizar produto")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Produto atualizado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content) })
	@PutMapping("/{uuid}")
	public ProdutoOutputDTO alterar(@PathVariable String uuid, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
		Produto produto = produtoInputMapper.mapearEntity(produtoInputDTO);
		produto = produtoService.atualizar(uuid, produto);

		return produtoOutputMapper.mapearEntity(produto);
	}

	@Operation(summary = "Ajustar produto")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Produto ajustado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteOutputDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content) })
	@PatchMapping("/{uuid}")
	public ProdutoOutputDTO ajustar(@PathVariable String uuid, @RequestBody Map<String, Object> campos) {
		Produto produto = produtoService.ajustar(uuid, campos);
		return produtoOutputMapper.mapearEntity(produto);
	}

	@Operation(summary = "Remover produto")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Produto removido", content = @Content),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content) })
	@DeleteMapping("/{uuid}")
	public ResponseEntity<Produto> deletarProduto(@PathVariable String uuid) {
		produtoService.excluir(uuid);
		return ResponseEntity.noContent().build();
	}
}
