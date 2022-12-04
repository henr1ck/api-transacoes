## 1. Breve introdução
Uma API que recebe dados de uma transação (data e valor) 
e retorna informações estatísticas sobre essas transações.

## 2. Status do projeto
Em andamento...

## 3. Funcionalidades
- [X] Calcular estatísticas gerais das transações (Quantidade, somatório, média, máximo, mínimo);
- [ ] Calcular estatísticas das transações realizadas nos últimos 60 segundos;
- [X] Buscar todas as transações;
- [X] Cadastrar novas transações;
- [X] Excluir todas as transações;
- [X] Validação dos dados de entrada;

## 4. Tecnologias | Uso
- [X] Spring Boot | Configuração mínima;
- [X] Bean Validation | Validação dos dados;
- [X] Mockito e AssertJ | Testes de unidade;
- [X] Docker e JIB | Build da imagem;
- [ ] Swagger | Documentação;

## 5. Endpoints
### `GET api/transaction`
- `200 - OK:` Retorna todas as transações armazenadas em memória;

### `POST api/transaction`
- `201 - CREATED:` Recebe uma transação para ser salva e a retorna;

Formato de exemplo:
```json
{
  "value": 49.98,
  "dateTime": "2023-08-23T12:50:00-0800"
}
```
- `400 - BAD REQUEST:` Retorna um corpo padronizado caso algum dos valores enviados sejam 
inválidos, como uma data no futuro, valor negativo ou ambos;

Corpo enviado:
```json
{
  "value": 49.98,
  "dateTime": "2026-03-27T12:00:00-0800"
}
```
Mensagem de erro padronizada:
```json
{
  "timestamp": "2022-12-04T09:23:28.4424693",
  "status": 400,
  "exception": "MethodArgumentNotValidException",
  "fields": "dateTime",
  "fieldsMessage": "transaction date cannot be in the future"
}
```

### `DELETE api/transaction`
- `204 - NO CONTENT:` Apaga todas as transações;

### `POST api/statistics`
- `200 - OK:` Retorna dados estatísticos sobre todas as transações;

Exemplo com valores 512 e 1024 de duas transações:
````json
{
  "count": 2,
  "sum": 1536.0,
  "avg": 768.0,
  "min": 512.0,
  "max": 1024.0
}
````

## 6. Como executar?
Caso tenha o docker instalado, execute o seguinte comando:
```shell
$ docker run --rm -dp 8080:8080 --name api-transacoes henr1ck/api-transacoes
```
