# TelegramBot
> Desenvolvimento de um bot no telegram utilizando alguns comandos.

## Para começar

### Passo 1
- **Clone**
  - Clonar esse repositório em sua máquina local utilizando a URL `https://github.com/andreAlbuquerqueHub/TelegramBot.git`

### Passo 2
- **Configuração**
  - Incluir todas as dependências que estão em `lib/*` no Build Path do projeto.
  - Executar o `lib/lombok.jar` para a instalação/atualização.

### Passo 3
  - **Execução**
    - Acessar a URL <a href="https://web.telegram.org/#/im?p=@BotFather" target="_blank">`https://web.telegram.org/#/im?p=@BotFather`</a> para a criação do bot e gerar o token pelo telegram.
    - Na classe Main da linha 27, inserir o token no construtor da classe TelegramBot como no exemplo abaixo:
    ```java
    private static final TelegramBot bot = new TelegramBot("INSERIR O TOKEN AQUI");
    ```
    - Rebuildar o projeto
    - Executar a classe Main

## Documentação

- Para acessar a documentação gerada pelo javadoc, é necessario abrir o arquivo `TelegramBot/doc/index.html`
