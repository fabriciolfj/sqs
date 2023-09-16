# Permissões SQS
resource "aws_iam_role" "lambda_role" {
  name = "lambda-sqs-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Sid    = ""
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy" "lambda_policy" {
  name = "lambda-sqs-policy"
  role = aws_iam_role.lambda_role.id

  policy = jsonencode({
    "Version": "2012-10-17",
    "Statement": [
      {
        "Effect": "Allow",
        "Action": "*",
        "Resource": "*"
      }
    ]
  })
}

# Função Lambda
resource "aws_lambda_function" "notify_file_upload" {
   filename      = "lambda.zip"
   function_name = "notify-file-upload"
   role          = aws_iam_role.lambda_role.arn
   handler       = "funcao_lambda.lambda_handler"
   runtime       = "python3.8"

   environment {
     variables = {
       SQS_URL = "arn:aws:sqs:us-east-2:805804964175:receive.fifo"
     }
   }
 }


resource "aws_s3_bucket_notification" "bucket_notification" {
  bucket = aws_s3_bucket.bucket.id

  lambda_function {
    lambda_function_arn = aws_lambda_function.notify_file_upload.arn
    events              = ["s3:ObjectCreated:*"]
  }

  depends_on = [aws_lambda_permission.allow_bucket]
}

resource "aws_s3_bucket" "bucket" {
  bucket = "test-fabricio"
}


# Permissões S3
resource "aws_lambda_permission" "allow_bucket" {
   statement_id  = "AllowExecutionFromS3Bucket"
   action        = "lambda:InvokeFunction"
   function_name = aws_lambda_function.notify_file_upload.arn
   principal     = "s3.amazonaws.com"
   source_arn    = aws_s3_bucket.bucket.arn
}