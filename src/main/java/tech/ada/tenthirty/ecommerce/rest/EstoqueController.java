package tech.ada.tenthirty.ecommerce.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.tenthirty.ecommerce.payload.ItemRequest;
import tech.ada.tenthirty.ecommerce.payload.response.EstoqueResponse;
import tech.ada.tenthirty.ecommerce.payload.response.ItemResponse;
import tech.ada.tenthirty.ecommerce.service.AdicionarProdutoService;
import tech.ada.tenthirty.ecommerce.service.ConsultarQuantidadeItemService;

import java.util.List;

@RestController
@RequestMapping(name = "/item")
@RequiredArgsConstructor
public class EstoqueController {

    private final ConsultarQuantidadeItemService consultarQuantidadeItemService;

    private final AdicionarProdutoService adicionarProdutoService;

    @Operation(summary = "Reservar Itens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item Adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao tentar reservar um item"),
    })
    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public EstoqueResponse reservarItem(@RequestBody List<ItemRequest> itens){

        return new EstoqueResponse();
    }

    @Operation(summary = "Consultar Estoque Item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item em estoque"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
    })
    @GetMapping(value = "/query/{sku}")
    public ItemResponse pesquisarItem(@PathVariable("sku") String sku){

        return consultarQuantidadeItemService.execute(sku);
    }

    @PostMapping(value = "/add")
    @Operation(summary = "Adicionar um item ao Estoque")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse adicionarItem(@RequestBody ItemRequest itemRequest){
        return adicionarProdutoService.execute(itemRequest);
    }
}
