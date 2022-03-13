# Pix
A url do arquivo do h2 é jdbc:h2:file:./pix-db, este pode ser acessado no http://localhost:8080/h2-console

Caso nao informado o tipo da Conta sera cadastrada como PF

http://localhost:8080/pix

@PostMapping: 

        - As validação de unique foi deixada por conta da base de dados, pois o custo da tentativa de insert é menor que o de select, 
    caso a chave ja exista a constraint de unique sera capturada para exibir a mensagem.
        - Não identifiquei o que iria diferenciar a conta PF e PJ, quando este é enviado vazio a conta entra por default PJ

@PutMapping

        - Busca a conta pela Chave do PIX e atualiza dados da conta.
        - Chave pix não é alteravel.

@GetMapping

        - Recebe infor para ser filtrada


http://localhost:8080/pix/{id}


@DeleteMapping(value = "/{id}")

        - Inativa chave pelo UUID

@GetMapping(value = "/{id}")

        - Busca chave pelo UUID