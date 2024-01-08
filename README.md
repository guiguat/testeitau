# Getting Started
- Arquivos para o RDS, Api Gateway e SNS na pasta "terraform"
- Para subir com o docker é possivel usar o script start-docker.sh (se for rodar o docker-compose direto é necessário buildar a aplicação)
- Arquivo com as requests de teste em "TesteApplication.http"
- Cobertura de 100% de testes unitários, para ver os reports só rodar o comando:
```sh
./gradlew jacocoTestReport
```
A pagina web com o relatorio fica em `build/reports/jacoco/test/html/index.html`