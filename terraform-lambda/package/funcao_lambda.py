import boto3

QUEUE_URL = 'https://sqs.us-east-2.amazonaws.com/805804964175/receive.fifo'
BUCKET_MONITORADO = 'test-fabricio'

s3 = boto3.client('s3')
sqs = boto3.client('sqs')

def lambda_handler(event, context):

  bucket = event['Records'][0]['s3']['bucket']['name']

  if bucket != BUCKET_MONITORADO:
    print(f'Arquivo ignorado, bucket invalido: {bucket}')
    return

  key = event['Records'][0]['s3']['object']['key']

  print(f'Arquivo {key} adicionado em {BUCKET_MONITORADO}')

  message = f'Novo arquivo {key} carregado em {BUCKET_MONITORADO}'

  response = sqs.send_message(QueueUrl=QUEUE_URL, MessageBody=message, MessageGroupId='test', MessageDeduplicationId='test')

  return {
    'statusCode': 200,
    'body': 'Mensagem enviada para SQS!'
  }