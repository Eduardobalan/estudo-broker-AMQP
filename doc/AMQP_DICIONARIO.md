# Definições Importantes

## Producing/Produtor

São aplicaçães que se conectam a um broker de mensageria (como Apache Kafka, RabbitMQ, Apache ActiveMQ, etc.) e enviam
mensagens para tópicos ou filas específicas.

## Consuming/Consumir

Os consumidores conectam a um broker de mensageria e ficam à espera de mensagens na filas específicas.
Quando uma mensagem é entregue a fila, o consumidor recebe a processa de acordo com a lógica de negócios definida no
aplicativo.

## Exchange

### durable (boolean)

Isso significa que a exchange e suas definições (como as regras de roteamento associadas a ela) serão preservadas, mesmo
em caso de reinicialização do servidor de mensagens. Isso é importante para sistemas de mensagens críticos nos quais a
consistência e a confiabilidade das exchanges e suas configurações são fundamentais.

### delayed (boolean)

Em sistemas como o RabbitMQ, o atraso no roteamento de mensagens é normalmente alcançado usando uma exchange especial
chamada "exchange de atraso" (delayed exchange). Essa exchange permite que as mensagens sejam encaminhadas para uma fila
com um atraso específico antes de serem entregues à fila de destino final.

### internal (boolean)

As exchanges "internal" são usadas principalmente para implementar cenários avançados de roteamento dentro do servidor
de mensagens e não são diretamente acessíveis aos clientes. Elas são usadas para o roteamento interno de mensagens entre
componentes do servidor AMQP ou para a implementação de funcionalidades específicas da infraestrutura.

## Queue

### durable (boolean)

Quando uma fila é declarada como durável, isso significa que a fila e suas mensagens sobreviverão a reinicializações ou
falhas no servidor de mensagens. Em outras palavras, se você encerrar o servidor de mensagens e reiniciá-lo, a fila
durável e as mensagens que estavam nela serão preservadas. Isso garante que os dados não sejam perdidos e que as
mensagens ainda estarão disponíveis para os consumidores após uma reinicialização.

### exclusive (boolean)

Quando uma fila é declarada como exclusiva, somente a conexão que a criou tem permissão para acessá-la. Nenhuma outra
conexão, mesmo se conectando ao mesmo servidor AMQP, pode declarar uma fila com o mesmo nome e marcada como exclusiva.
Esse recurso é útil em cenários em que você deseja criar filas temporárias que não devem ser compartilhadas entre
diferentes partes do sistema.
**Isso significa que, quando a conexão que a criou é fechada, a fila é automaticamente excluída, independentemente de ser
auto-delete ou não.**

### auto-delete (boolean)

Essa configuração é útil em cenários onde você deseja que a fila exista apenas enquanto houver consumidores ou conexões
ativas associadas a ela. Quando a última conexão ou consumidor é fechado, a fila é automaticamente removida. Isso pode
ser útil em situações em que você deseja que as filas sejam temporárias e desapareçam quando não forem mais necessárias,
economizando recursos no servidor de mensagens.

## Messages

### Ready

Geralmente se refere a uma mensagem que foi publicada em uma fila e está pronta para ser consumida por um consumidor.

### Unacked

É uma mensagem que foi entregue a um consumidor, mas o consumidor ainda não a confirmou como processada com sucesso.
A palavra "Unacked" é uma abreviação de "Unacknowledged" (não reconhecida).

Quando um consumidor recebe uma mensagem de uma fila, ele tem a responsabilidade de processar a mensagem e,
em seguida, confirmar (ou reconhecer) a entrega da mensagem ao servidor de mensagens.
A confirmação é uma parte fundamental do modelo de entrega de mensagens no AMQP e ajuda a garantir a confiabilidade do
sistema.
