# API Rest em Java Spring Boot - Projeto Integrador Loja Online
- [Breve passeio pelo diagrama de classes do Sistema](https://www.youtube.com/watch?v=qANr19hL2bA)

<p align="center">
    <a href="https://github.com/jciterceros/vr_online_backend/blob/b1c2d6a24ea663d9ca263dc598e0997012c6f8fe/docs/Diagrama%20das%20classes.png" target="blank">
        <img src="https://github.com/jciterceros/vr_online_backend/blob/68254277acb704d77a31320fb86c0e6ae7b99535/docs/Diagrama%20das%20classes.png" width="600" alt="Diagrama de Classes" />
    </a>
</p>

# Projeto VR Online - Documentação Técnica

Este projeto utiliza uma arquitetura de camadas com pacotes separados para cada domínio do sistema, incluindo **Endereço**, **Pessoa e Contatos**, **Produtos e Estoque**, **Pagamentos**, **Pedidos**, e **Segurança**.

## Estrutura de Pacotes

### 1. **Pacote Endereço**
Responsável pela gestão de endereços no sistema.

- **Classes:** `Estado`, `Municipio`, `ViaCep`, `EnderecoViaCepAdapter`, `Endereco`
- **Interfaces:** `IEndereco`

### 2. **Pacote Pessoa e Contatos**
Gerencia entidades relacionadas a pessoas físicas e jurídicas, além de seus contatos.

- **Classes:** `Telefone`, `Contato`, `PessoaFactory`, `Pessoa`, `PessoaFisica`, `PessoaJuridica`
- **Enums:** `TipoPessoa`, `SituacaoCPF`, `SituacaoCNPJ`

### 3. **Pacote Produtos e Estoque**
Gerencia produtos e estoques.

- **Classes:** `Produto`, `LocalArmazenamento`, `Estoque`
- **Enums:** `Metrica`

### 4. **Pacote Pagamentos**
Lida com o processamento e validação de pagamentos.

- **Classes:** `Pagamento`, `PagamentoProcessamentoService`, `PagamentoServiceImpl`
- **Interfaces:** `IPagamentoProcessar`, `IPagamentoValidar`, `IPagamentoNotificar`
- **Fábricas:** `PagamentoProcessarFactory`, `PagamentoValidarFactory`, `PagamentoNotificarFactory`
- **Enums:** `TipoPagamento`, `StatusPagamento`

### 5. **Pacote Pedidos**
Gerencia pedidos de compra e venda.

- **Classes:** `ItemPedido`, `PedidoVenda`, `PedidoCompra`

### 6. **Pacote Segurança**
Implementa mecanismos de autenticação e autorização de usuários.

- **Classes:** `User`, `Role`, `UserPrincipal`, `UserServiceImpl`
- **Interfaces:** `UserDetailsService`, `UserRepository`, `IPasswordEncoder`
- **Classes Adicionais:** `BCryptPasswordEncoder`, `CustomBasicAuthenticationFilter`

## Casos de Uso

Este documento descreve os principais casos de uso do sistema, organizados por pacotes, incluindo os componentes de segurança e funcionalidades específicas para gerenciamento de pessoas, endereços, produtos, pagamentos e pedidos.

## 1. **Pacote Endereço**

### Casos de Uso:
1. **Cadastrar Estado**
   - O administrador pode cadastrar um novo estado, definindo um `id`, `descrição` e `sigla`.
   - **Classes Envolvidas:** `Estado`
   
2. **Cadastrar Município**
   - O administrador pode cadastrar um município, associando-o a um estado já existente.
   - **Classes Envolvidas:** `Municipio`, `Estado`
   
3. **Buscar CEP via API**
   - O sistema consulta o serviço externo ViaCep para buscar informações detalhadas de um endereço com base no CEP.
   - **Classes Envolvidas:** `ViaCep`, `EnderecoViaCepAdapter`
   
4. **Cadastrar Endereço Manualmente**
   - O usuário pode cadastrar um endereço manualmente, fornecendo os detalhes como `rua`, `número`, `cep`, `bairro` e associando-o a um município.
   - **Classes Envolvidas:** `Endereco`

## 2. **Pacote Pessoa e Contatos**

### Casos de Uso:
1. **Cadastrar Pessoa Física**
   - O sistema permite o cadastro de uma pessoa física, utilizando a `PessoaFactory` para criar a instância de `PessoaFisica` e preenchendo informações como `cpf`, `rg`, e `data de nascimento`.
   - **Classes Envolvidas:** `PessoaFisica`, `PessoaFactory`
   
2. **Cadastrar Pessoa Jurídica**
   - Similar ao cadastro de pessoa física, o sistema utiliza a `PessoaFactory` para criar uma instância de `PessoaJuridica`, com campos como `cnpj`, `razão social` e `ramo de atividade`.
   - **Classes Envolvidas:** `PessoaJuridica`, `PessoaFactory`

3. **Gerenciar Contatos de Pessoa**
   - Um usuário pode adicionar ou remover endereços e telefones para uma pessoa através do `ContatoService`.
   - **Classes Envolvidas:** `Contato`, `Pessoa`

## 3. **Pacote Produtos e Estoque**

### Casos de Uso:
1. **Cadastrar Produto**
   - O administrador pode cadastrar novos produtos no sistema, informando `marca`, `modelo`, `descrição`, `métrica`, entre outros atributos.
   - **Classes Envolvidas:** `Produto`
   
2. **Gerenciar Estoque**
   - O sistema permite atualizar a quantidade em estoque de produtos em locais de armazenamento específicos.
   - **Classes Envolvidas:** `Estoque`, `LocalArmazenamento`, `Produto`

## 4. **Pacote Pagamentos**

### Casos de Uso:
1. **Processar Pagamento**
   - O sistema processa o pagamento de um pedido, de acordo com o tipo de pagamento escolhido pelo cliente (ex: PIX, boleto, cartão, bitcoins).
   - **Classes Envolvidas:** `Pagamento`, `IPagamentoProcessar`, `PagamentoProcessarFactory`

2. **Validar Pagamento**
   - Antes de confirmar um pagamento, o sistema valida as informações fornecidas.
   - **Classes Envolvidas:** `Pagamento`, `IPagamentoValidar`, `PagamentoValidarFactory`
   
3. **Notificar Status de Pagamento**
   - O sistema envia notificações ao cliente sobre o status do pagamento (pendente, confirmado, cancelado).
   - **Classes Envolvidas:** `Pagamento`, `IPagamentoNotificar`, `PagamentoNotificarFactory`

## 5. **Pacote Pedidos**

### Casos de Uso:
1. **Registrar Pedido de Venda**
   - O sistema registra um pedido de venda de produtos, associando-o ao cliente e seus itens.
   - **Classes Envolvidas:** `PedidoVenda`, `ItemPedido`, `Pessoa`, `Produto`, `Pagamento`

2. **Registrar Pedido de Compra**
   - Similar ao pedido de venda, o pedido de compra é registrado com o fornecedor e os itens solicitados.
   - **Classes Envolvidas:** `PedidoCompra`, `ItemPedido`, `Pessoa`, `Produto`, `Pagamento`

## 6. **Pacote Segurança**

### Casos de Uso:
1. **Autenticar Usuário**
   - O sistema autentica um usuário utilizando o nome de usuário e senha, garantindo que a senha seja codificada com o `BCryptPasswordEncoder`.
   - **Classes Envolvidas:** `User`, `UserPrincipal`, `BCryptPasswordEncoder`, `UserServiceImpl`
   
2. **Gerenciar Usuários e Papéis**
   - O administrador pode criar, atualizar ou excluir usuários e atribuir papéis (roles) que definem permissões dentro do sistema.
   - **Classes Envolvidas:** `User`, `Role`, `UserServiceImpl`, `UserRepository`

## 7. **Considerações de Segurança**

- O sistema implementa camadas de segurança através de serviços como `UserDetailsService`, onde as credenciais dos usuários são armazenadas de forma segura e o acesso aos dados é controlado com base em permissões atribuídas aos papéis dos usuários.


## Linguagem Ubíqua

Este documento define os termos e conceitos centrais usados no sistema, representando a **Linguagem Ubíqua** utilizada tanto por desenvolvedores quanto pelos stakeholders, garantindo uma comunicação clara e coesa entre todos.

## 1. **Pacote Endereço**

- **Estado:** Representa uma unidade federativa dentro do país. Cada estado possui um `id`, `descrição` e `sigla`.
- **Município:** Refere-se a uma subdivisão administrativa do estado. Cada município está associado a um estado.
- **CEP:** Código postal utilizado para identificar uma região específica para entrega de correspondências.
- **ViaCep:** Serviço externo utilizado para obter informações detalhadas de um endereço com base no CEP.
- **Endereço:** Representa a localização física, composta por elementos como `rua`, `número`, `bairro`, `cep` e `municipio`.

## 2. **Pacote Pessoa e Contatos**

- **Pessoa:** Entidade abstrata que representa uma pessoa no sistema. Pode ser do tipo `PessoaFisica` ou `PessoaJuridica`.
- **PessoaFisica:** Representa um indivíduo, caracterizado por atributos como `cpf`, `rg`, e `data de nascimento`.
- **PessoaJuridica:** Representa uma entidade empresarial ou organização, identificada por atributos como `cnpj`, `razão social` e `ramo de atividade`.
- **Contato:** Representa a relação entre uma pessoa e seus endereços e telefones.
- **Telefone:** Número telefônico associado a uma pessoa, que pode ser `residencial`, `comercial`, ou `celular`.

## 3. **Pacote Produtos e Estoque**

- **Produto:** Representa um item ou mercadoria no sistema, com atributos como `marca`, `modelo`, `descrição`, `métrica`, e `preço`.
- **Estoque:** Refere-se à quantidade de produtos armazenados em um determinado `LocalArmazenamento`.
- **LocalArmazenamento:** Local físico onde os produtos são armazenados, podendo ser um depósito ou uma loja.

## 4. **Pacote Pagamentos**

- **Pagamento:** Representa uma transação financeira realizada por um cliente para adquirir um produto ou serviço.
- **IPagamentoProcessar:** Interface que define o comportamento para processar um pagamento de acordo com o tipo de pagamento (PIX, boleto, cartão, etc.).
- **PagamentoProcessarFactory:** Fábrica responsável por instanciar o processo de pagamento correto, baseado no método escolhido.
- **IPagamentoValidar:** Interface que define a lógica de validação de um pagamento.
- **PagamentoValidarFactory:** Fábrica que cria a instância de validação de pagamento conforme necessário.
- **IPagamentoNotificar:** Interface que define o comportamento para notificar o cliente sobre o status do pagamento.
- **PagamentoNotificarFactory:** Fábrica que cria notificações de pagamento para os clientes.

## 5. **Pacote Pedidos**

- **PedidoVenda:** Pedido de compra de produtos feito por um cliente.
- **PedidoCompra:** Pedido de aquisição de produtos feito a um fornecedor.
- **ItemPedido:** Representa um item dentro de um pedido, contendo o `produto`, a `quantidade`, e o `valor unitário`.

## 6. **Pacote Segurança**

- **Usuário (User):** Representa um usuário do sistema, identificado por um `nome de usuário` e uma `senha`.
- **Papel (Role):** Define o conjunto de permissões atribuídas a um usuário, como `Administrador`, `UsuárioComum`, etc.
- **BCryptPasswordEncoder:** Ferramenta utilizada para criptografar as senhas dos usuários, garantindo a segurança dos dados.
- **UserDetailsService:** Serviço responsável por carregar os detalhes do usuário a partir do banco de dados, durante a autenticação.
- **Autenticação:** Processo que verifica a identidade de um usuário com base em suas credenciais (nome de usuário e senha).
- **Autorização:** Processo de concessão de permissões a usuários para realizar ações dentro do sistema com base em seus papéis.

## Links para acessar os videos da API

- [Consultar Produtos](https://www.youtube.com/watch?v=9PoCP40XCq8)
- [Consulta ViaCEP](https://www.youtube.com/watch?v=QYFjijHqg5U)
- [Consultar Estados](https://www.youtube.com/watch?v=30gU4RHSIr0)
- [Consulta Municípios](https://www.youtube.com/watch?v=Y8N6Sn3qMOI)
- [Consulta Endereços](https://www.youtube.com/watch?v=mPZU6X-s0Ds)
- [Consulta Telefones](https://www.youtube.com/watch?v=TNfYPc1XDpM)
- [Consulta Estoques](https://www.youtube.com/watch?v=S46Higu1K_k)
- [Consulta Locais de Armazenamento](https://www.youtube.com/watch?v=GkRiLY3ZXoQ)
- [Consulta de Pessoas](https://www.youtube.com/watch?v=-FuiOg8M0YQ)
- [Consulta Contatos](https://www.youtube.com/watch?v=Abq6BanjPoA)
- [Consulta Pagamentos](https://www.youtube.com/watch?v=RQAUCqqM4do)
- [Consulta Itens do Pedido](https://www.youtube.com/watch?v=Xm3gtodT-sc)
- [Consulta Pedido de Vendas](https://www.youtube.com/watch?v=dxSzjHF_JAw)
- [Consulta Pedido de Compras](https://www.youtube.com/watch?v=fc7uysx6byw)
- [Breve passeio pelo diagrama de classes do Sistema](https://www.youtube.com/watch?v=qANr19hL2bA)
- [Breve passeio pelo diagrama de classes feito em PlantUML](https://www.youtube.com/watch?v=taJpe8gLRJs)
- [Breve passeio pela estrutura do código](https://www.youtube.com/watch?v=CNFDyBa3NrQ)


## Contribuição

1. Faça um fork do repositório.
2. Crie uma branch: `git checkout -b minha-feature`.
3. Faça commit: `git commit -m 'Adiciona nova feature'`.
4. Envie para a branch: `git push origin minha-feature`.
5. Crie um Pull Request.

## Licença

Este projeto está sob a licença MIT. Para mais detalhes, veja o arquivo [LICENSE](LICENSE).
