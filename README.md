# Facturitas API

Backend API for the Facturitas billing system, which enables invoice processing through WhatsApp using AI.

## Features

- Invoice processing through WhatsApp messages
- Audio transcription using OpenAI Whisper
- Structured data extraction using OpenAI GPT-4
- Google OAuth2 authentication
- API documentation with Swagger/OpenAPI
- PostgreSQL database
- AWS ECS deployment

## Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL
- WhatsApp Business API account
- OpenAI account
- AWS account (for deployment)

## Environment Setup

1. Clone the repository:
```bash
git clone https://github.com/your-username/facturitas.git
cd facturitas/api
```

2. Configure the required environment variables:
```bash
# Database
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=facturitas
export DB_USER=postgres
export DB_PASSWORD=postgres

# Google OAuth
export GOOGLE_CLIENT_ID=your-google-client-id
export GOOGLE_CLIENT_SECRET=your-google-client-secret

# JWT
export JWT_SECRET=your-jwt-secret
export JWT_EXPIRATION=300

# WhatsApp
export WHATSAPP_API_TOKEN=your-whatsapp-token
export WHATSAPP_WEBHOOK_VERIFY_TOKEN=your-webhook-token

# OpenAI
export OPENAI_API_KEY=your-openai-api-key

# AWS (for deployment)
export AWS_ACCESS_KEY_ID=your-aws-access-key
export AWS_SECRET_ACCESS_KEY=your-aws-secret-key
export AWS_REGION=your-aws-region
```

3. Run the application locally:
```bash
./mvnw spring-boot:run
```

## Project Structure

```
src/main/java/online/tufactura/api/
├── application/           # Application layer
│   ├── ports/            # Ports (interfaces)
│   │   ├── inbound/      # Input ports
│   │   └── outbound/     # Output ports
├── domain/               # Domain layer
│   ├── models/          # Entities and value objects
│   └── services/        # Domain services
└── infrastructure/      # Infrastructure layer
    ├── adapters/        # Adapters
    │   ├── client/      # External clients
    │   └── persistence/ # Persistence
    └── controllers/     # REST controllers
```

## API Documentation

API documentation is available in Swagger UI when the application is running:
- URL: `http://localhost:8080/api/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api/api-docs`

## Development

### Running Tests
```bash
./mvnw test
```

### Building the Application
```bash
./mvnw clean package
```

## Deployment

The application is automatically deployed to AWS ECS when pushing to the `main` branch. The process includes:

1. Running tests
2. Building Docker image
3. Pushing to Amazon ECR
4. Deploying to ECS

### Manual Deployment

If you need to deploy manually:

```bash
# Build the image
docker build -t facturitas-api .

# Tag the image for ECR
docker tag facturitas-api:latest ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/facturitas-api:latest

# Push to ECR
docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/facturitas-api:latest
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details. 