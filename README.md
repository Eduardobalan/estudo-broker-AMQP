# Projeto de Estudo: Broker AMQP

Para uma introdução teórica sobre o AMQ e AMQP clique [aqui](doc/AMQP.md)<br>
Para as definições importantes clique [aqui](doc/AMQP_DICIONARIO.md)

## Introdução

Esse repositório tem como objetivo estudar a comunicação de `produtor` e `consumidor` do protocolo AMQP,
explorando suas diferentes configuração de `queue`, `exchange` e `Bindings`

Esse repositório possui dois projetos com funções bem especificas, são eles:

### Projeto produtor `estudo-produtor-api`

A função desse projeto é transmitir mensagens para o broker AMQP de diferentes formas.
Ele realiza essa função atraves de jobs que podem ser ativados e desativados, por meio de configurações.

### Projeto consumidor `estudo-consumidor-api`

A função desse projeto é consumir as mensagens produzidas de `estudo-produtor-api`,
e dar visibilidade desse consumo atraves dos logs.

## Como startar os projetos

Se estiver utilizando IDE intellij, 4 starts estarão configurados automaticamente:

- Produtor-1-api
- Produtor-2-api
- Consumidor-1-api
- Consumidor-2-api

Caso não esteja utilizando, deve ser configurado pelomenos um start para cada projeto

### Dinamica de comunicação

O projeto `estudo-consumidor-api` sempre está preparado para consumir as `queue`, então não precisamos nos preocupar
tanto com ele.

No projeto `estudo-produtor-api` precisamos ativar/desativar os jobs, para visualizarmos individualmente seu
comportamento.
Você poderia tivados todos os jobs de uma vez, mas recomendamos a ativação individual para facilitar o entendimento dos
logs.

### Inicie o RabbitMQ

Para iniciar o RabbitMQ podemos instalalor em nossa maquina e startalo, ou iniciar via container docker.
Para iniciar via container docker execute o comando.

```shell
docker-compose up -d
```

Após subir o RabbitMQ, uma interface estara disponivel:

- http://localhost:15672/
    - Usuário: guest
    - senha: guest

## Como iniciar jobs

### Mudando configurações dos jobs

Para ativarmos os jobs devemos mudar a configuração do arquivo `estudo-produtor-api/src/main/resources/application.yml`.
vejamos agora cada opção de ativação e sua explicação.

### Simples queues

Esse é o exemplo mais simples possivel de comunicação com AMQP, na [documentação](doc/AMQP.md) ela é chamada
de `Modo automático`.
Nesse modo nos conectamos a uma Default exchange, que está implicitamente vinculada a cada fila, com uma chave de
roteamento igual ao nome da fila.

**Caracteristicas:**

- exchange1: exchange Default do AMQP, Type=direct, Features(durable=true)
- queue1: type=classic, Features(durable=true, exclusive=false, autoDelete=false)

**Arquivos envolvidos:**

- `estudo-produtor-api`
    - SimplesQueuesProductorConfiguration.java
    - SimplesQueuesProductorSend.java
- `estudo-consumidor-api`
    - SimplesQueuesConsumerConfiguration.java
    - SimplesQueuesConsumer

Configuração para ativação no arquivo `estudo-produtor-api/src/main/resources/application.yml`

```yaml
bln:
  simplesqueues:
    enabled: true
```

### Simples queues com ACK

Este exemplo é idêntico ao anterior, exceto pela confirmação manual de ACK pelo consumidor.
O teste foi configurado para rejeitar algumas entradas aleatórias, resultando no envio dessas mensagens rejeitadas para
o final da fila.

**Caracteristicas:**

- exchange1: exchange Default do AMQP, Type=direct, Features(durable=true)
- queue1: type=classic, Features(durable=true, exclusive=false, autoDelete=false)

**Arquivos envolvidos:**

- `estudo-produtor-api`
    - SimplesQueuesComAckManualProductorConfiguration.java
    - SimplesQueuesComAckManualProductorSend.java
- `estudo-consumidor-api`
    - SimplesQueuesComAckManualConsumerConfiguration.java
    - SimplesQueuesComAckManualConsumer

Configuração para ativação no arquivo `estudo-produtor-api/src/main/resources/application.yml`

```yaml
bln:
  simplesqueuescomack:
    enabled: true
```

### Exemplo de Exchanges Fanout

A exchange de fanout é muito simples. Ele apenas transmite todas as mensagens que recebe para todas as filas que
conhece.
E é exatamente disso que precisamos para espalhar nossas mensagens.

Nesse exemplo, possuimos 4 filas com diferentes configuração. Caso queira entender melhor o comportamento de cada uma,
leia [aqui](doc/AMQP_DICIONARIO.md)

**Caracteristicas:**

- exchange:Type=fanout, Features(durable=true)
- queue1: type=classic, Features(durable=true, exclusive=false, autoDelete=false)
- queue2: type=classic, Features(durable=true, exclusive=false, autoDelete=false)
- queue3: type=classic, Features(durable=true, exclusive=false, autoDelete=true)
- queue4: type=classic, Features(durable=true, exclusive=false, autoDelete=true)

**Arquivos envolvidos:**

- `estudo-produtor-api`
    - ExchangesFanoutProductorConfiguration.java
    - ExchangesFanoutProductorSend.java
- `estudo-consumidor-api`
    - ExchangesFanoutConsumerConfiguration.java
    - ExchangesFanoutConsumer

Configuração para ativação no arquivo `estudo-produtor-api/src/main/resources/application.yml`

```yaml
bln:
  exchangesfanout:
    enabled: true
```

### Exemplo de Exchanges Direct

O objetivo principal de uma exchange "direct" é rotear mensagens para filas com base em uma correspondência direta entre
a chave de roteamento (routing key) da mensagem e a chave de ligação (binding key) definida pelo binding.
O funcionamento de uma exchange "direct" pode ser resumido da seguinte maneira:

**1 - Declaração da Exchange:** Primeiro, você declara uma exchange do tipo "direct".

**2 - Declaração das Filas:** Em seguida, você declara as filas que deseja que recebam mensagens da exchange "direct".

**3 - Realiza o Binding:** Durante o binding você deve vuncular a exchange "direct" a fila desejada, e inserindo uma "
binding key".

**4 - Publicação de Mensagens:** Quando você publica uma mensagem em uma exchange "direct", você atribui uma "routing
key" à mensagem.
A exchange usa essa "routing key" para determinar quais filas receberão a mensagem.  
Isso significa que a mensagem é entregue apenas às filas que tenham uma correspondência exata entre a "routing key" e
a "binding key".

**Caracteristicas:**

- exchange:Type=direct, Features(durable=true)
- queue1: type=classic, Features(durable=true, exclusive=true, autoDelete=true)
- queue2: type=classic, Features(durable=true, exclusive=true, autoDelete=true)
- binding1: queueFanout1: routingKey=black
- binding2: queueFanout2: routingKey=orange
- binding3: queueFanout3: routingKey=green
- binding4: queueFanout4: routingKey=black

**Arquivos envolvidos:**

- `estudo-produtor-api`
    - ExchangesDirectProductorConfiguration.java
    - ExchangesDirectProductorSend.java
- `estudo-consumidor-api`
    - ExchangesDirectConsumerConfiguration.java
    - ExchangesDirectConsumer

Configuração para ativação no arquivo `estudo-produtor-api/src/main/resources/application.yml`

```yaml
bln:
  exchangesdirect:
    enabled: true
```

### Exemplo de Exchanges Topic

No modelo de exchange "topic", as mensagens são enviadas para uma exchange usando uma chave de roteamento (routing key)
no
momento da publicação. A exchange então encaminha a mensagem para filas associadas com base em padrões de
correspondência
entre a chave de roteamento e os padrões de ligação (bindings) das filas.

Existem dois conceitos principais em exchanges de tipo "topic":

**Routing Key**: É uma chave atribuída a cada mensagem pelo produtor. Esta chave é usada pela exchange para rotear a
mensagem para as filas. Pode ser uma expressão com palavras-chave separadas por pontos, como "animal.cachorro.cor".

**Binding Pattern**: As filas são vinculadas à exchange usando padrões de ligação. No caso do tipo "topic", esses
padrões de
ligação são expressões de roteamento que especificam como as exchanges devem rotear as mensagens para as filas. Esses
padrões são baseados em padrões de correspondência de palavras-chave na chave de roteamento.

Por exemplo, se uma fila estiver vinculada à exchange com um padrão de ligação de "animal..", ela receberá mensagens com
chaves de roteamento como "animal.cachorro.cor", "animal.gato.tamanho" e assim por diante, onde "*" é um caractere
curinga que corresponde a exatamente uma palavra.

Outra fila com um padrão de ligação de "#.cor" receberá mensagens como "animal.cachorro.cor", "objeto.carro.cor" e "
outro.objeto.bicicleta.cor", onde "#" é um curinga que corresponde a zero ou mais palavras.

O comportamento do RabbitMQ com exchanges do tipo "topic" é poderoso, pois permite que as filas recebam apenas mensagens
relevantes, com base nos padrões de ligação estabelecidos entre as filas e a exchange. Isso oferece flexibilidade e
granularidade no roteamento de mensagens, tornando-o adequado para sistemas distribuídos complexos e com requisitos de
roteamento dinâmico.

**Caracteristicas:**

- exchange:Type=topic, Features(durable=true)
- queue1: type=classic, Features(durable=true, exclusive=true, autoDelete=true)
- queue2: type=classic, Features(durable=true, exclusive=true, autoDelete=true)
- binding1: queueTopic1: routingKey=\*.orange.\*
- binding2: queueTopic1: routingKey=\*.\*.rabbit
- binding3: queueTopic2: routingKey=lazy.\#

**Arquivos envolvidos:**

- `estudo-produtor-api`
    - ExchangesTopicProductorConfiguration.java
    - ExchangesTopicProductorSend
- `estudo-consumidor-api`
    - ExchangesTopicConsumerConfiguration.java
    - ExchangesTopicConsumer.java

Configuração para ativação no arquivo `estudo-produtor-api/src/main/resources/application.yml`

```yaml
bln:
  exchangestopic:
    enabled: true
```

## Referencias

- https://www.amqp.org/specification/0-9-1/amqp-org-download
- https://spring.io/projects/spring-amqp
- https://www.rabbitmq.com/tutorials/tutorial-one-spring-amqp.html
- https://github.com/spring-projects/spring-amqp-samples



