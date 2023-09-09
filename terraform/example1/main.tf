resource "aws_sqs_queue" "receive" {
  name                  = "receive.fifo"
  fifo_queue            = true
  deduplication_scope   = "messageGroup"
  fifo_throughput_limit = "perMessageGroupId"
  content_based_deduplication = true
  #kms_master_key_id                 = "alias/aws/sqs"
  #kms_data_key_reuse_period_seconds = 300

  visibility_timeout_seconds = 30
  message_retention_seconds = 60
  delay_seconds = 5
  receive_wait_time_seconds = 1

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.receive_queue_deadletter.arn
    maxReceiveCount     = 4 #numero de vez para tentar receber a msg antes de mandar a dlq
  })


}

resource "aws_sqs_queue" "receive_queue_deadletter" {
  name = "receive-queue-deadletter.fifo"
  fifo_queue = true

  kms_master_key_id                 = "alias/aws/sqs"
  kms_data_key_reuse_period_seconds = 300

}
