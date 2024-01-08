resource "aws_db_instance" "default" {
  allocated_storage    = 10
  db_name              = "person"
  engine               = "mysql"
  engine_version       = "8"
  instance_class       = "db.t3.micro"
  username             = "teste"
  password             = "teste"
  parameter_group_name = "default.mysql8"
  skip_final_snapshot  = true
}

resource "aws_sns_topic" "person-events" {
  name = "person-events-topic"
}

resource "aws_api_gateway_rest_api" "gtw" {
  body = jsonencode({
    openapi = "3.0.1"
    info = {
      title   = "Api do teste do itau"
      version = "1.0"
    }
    paths = {
      "/person" = {
        get = {
          x-amazon-apigateway-integration = {
            httpMethod           = "GET"
            payloadFormatVersion = "1.0"
            type                 = "HTTP_PROXY"
            uri                  = "http://localhost:8080/person"
          }
        }
        post = {
          x-amazon-apigateway-integration = {
            httpMethod           = "POST"
            payloadFormatVersion = "1.0"
            type                 = "HTTP_PROXY"
            uri                  = "http://localhost:8080/person"
          }
        }
      }
      "/person/{id}" = {
        get = {
          x-amazon-apigateway-integration = {
            httpMethod           = "GET"
            payloadFormatVersion = "1.0"
            type                 = "HTTP_PROXY"
            uri                  = "http://localhost:8080/person/{id}"
          }
        }
        post = {
          x-amazon-apigateway-integration = {
            httpMethod           = "POST"
            payloadFormatVersion = "1.0"
            type                 = "HTTP_PROXY"
            uri                  = "http://localhost:8080/person/{id}"
          }
        }
        patch = {
          x-amazon-apigateway-integration = {
            httpMethod           = "POST"
            payloadFormatVersion = "1.0"
            type                 = "HTTP_PROXY"
            uri                  = "http://localhost:8080/person/{id}"
          }
        }
        delete = {
          x-amazon-apigateway-integration = {
            httpMethod           = "POST"
            payloadFormatVersion = "1.0"
            type                 = "HTTP_PROXY"
            uri                  = "http://localhost:8080/person/{id}"
          }
        }
      }
    }
  })

  name = "gtw"

  endpoint_configuration {
    types = ["REGIONAL"]
  }
}

resource "aws_api_gateway_deployment" "gtw" {
  rest_api_id = aws_api_gateway_rest_api.gtw.id

  triggers = {
    redeployment = sha1(jsonencode(aws_api_gateway_rest_api.gtw.body))
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_api_gateway_stage" "gtw" {
  deployment_id = aws_api_gateway_deployment.gtw.id
  rest_api_id   = aws_api_gateway_rest_api.gtw.id
  stage_name    = "testeitau"
}