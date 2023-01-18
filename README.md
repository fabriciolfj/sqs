# sqs
### tempo limite de visibilidade
- tempo em que o consumidor esta processando a mensagem (mensagem não fica visível para outros consumidores), caso ele nao retorne no tempo configurado (ainda está processando), ela será entregue a outro consumidor/instância

### fila padrão
- não garante a ordem
- pode entregar a mensagem mais de uma vez

### fila fifo
- deve possuir um sufixo .fifo
- entrega a mensagem apenas 1 vez ao grupo solicitante
- podemos informar um id de duplicação (controle manual para evitar mensagens duplicadas)
- segue-se a ordem a primeira que entra, é a primeira que sai
- existe um controle por grupo, ou seja, se eu receber uma mensagem do grupo x, não irei receber nenhuma outra desse grupo até que eu exclua a msg ou passe o tempo de limite de visibilidade


### Enable high throughput FIFO (Habilitar FIFO de alta taxa de transferência)
- aumenta a taxa de transferência disponível para mensagens na fila FIFO atual.

### Deduplication scope (Escopo de eliminação de duplicação)
- especifica se a eliminação de duplicação ocorre no nível da fila ou do grupo de mensagens.

### FIFO throughput limit (Limite de taxa de transferência FIFO)
- especifica se a cota de taxa de transferência em mensagens na fila FIFO está definida no nível da fila ou do grupo de mensagens.

### Id de eliminação de duplicação
- se um id de duplicação for enviado, será aceito mas não será entregue mais de uma 1 vez no intervalo de 5 minutos

### dlq
- uma fila dlq e aonde as mensagens que tiveram falha em seu processamento são enviadas
- podemos redirecionar mensagens de uma dlq para fila de origem ou outra
- o parâmetro maxReceiveCount, indica o número de vezes o consumidor vai tentar consumir com sucesso a mensagem, antes de falhar definitivamente e ela ser redirecionada a dlq
- quando uma mensagem de um grupo está sendo processada, as mensagens deste ficam bloqueadas (ele nao recebe outras mensagens). Então em caso de problema na mensagem, falhe rápido
  https://docs.aws.amazon.com/pt_br/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-visibility-timeout.html

### timeout
- defini um tempo de atraso na entrega da mensagem aos consumidores (deixa a mensagem indisponivel para consumo durante um tempo, quando é adicionada pela primeira vez a fila)

### filas virtuais
- dentro de um fila, podemos criar filas virtuais e possuir consumidores específicos para elas.
- exemplo:
  - fila = produtoprice, fila virtual produtopriveweb e produtopriveapp, ambas vinculadas a fila produtoprive 
  - para criar deve utuilizar api create queue com a seguinte url: https://sqs.us-east-2.amazonaws.com/123456789012/produtoprice#produtopriveapp
  - para enviar utilizamos sendMessage("produtoprice#produtopriceapp)
  - para consumir receiveMessage("produtoprice#produtopriceapp)
- obs: se as filas virtuais não forem utilizadas durante 5 min, por padrão, são excluídas
- as filas virtuais são criadas no envio da mensagem, apenas especificando elas na url da fila padrão

https://docs.aws.amazon.com/pt_br/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-best-practices.html