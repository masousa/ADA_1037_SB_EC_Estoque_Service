# Estoque Service
## Descrição 
 > Serviço adicionar, consultar e reservar itens
 

## Endpoints: 
  ### Adicionar itens
    URL: /add
    Método: POST
  #### Request

``` json
{
  "skuId": "9877",
  "quantidade": 340
}
```
#### Responses
  #### 201

``` json
{
  "skuId": "9877",
  "quantidade": 340
}
```

#### 404

``` json
{
  "statusResponse": 400,
  "message": "Não foi possível encontrar o 9877 para o identificador informado Item "
}
```

### Reservar itens
    URL: /
    Método: POST
#### Request

``` json
{
  "skuId": "9877",
  "quantidade": 340
}
```
#### Responses
#### 201

``` json
{
  "idReserva": "string",
  "itemsReservados": [
    "string"
  ]
}
```

### Consultar item
    URL: /query/{sku}
    Método: POST
#### Request

```
/query/{sku}
```

Example:
```
/query/9877
```
#### Responses
#### 200

``` json
{
  "skuId": "9877",
  "quantidade": 340
}
```

#### 404

``` json
{
  "statusResponse": 400,
  "message": "Não foi possível encontrar o 9877 para o identificador informado Item "
}
```
