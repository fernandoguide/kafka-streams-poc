# kafka-streams-poc

Aplicacao usando Java 11 e Spring Boot.

### Como executar a aplicação
Certifique-se de ter instalado:
```
GIT
JAVA 11 ou superior
MAVEN 
DOCKER
DOCKER-COMPOSE
```
### Install docker and docker-compose

`````
https://docs.docker.com/compose/install/

https://docs.docker.com/engine/install/

`````

### Como executar ?
```
git clone https://github.com/fernandoguide/kafka-streams-poc.git

cd kafka-streams-poc

docker-compose up -d

./mvnw spring-boot:run


A applicação será executada em 
http://localhost:8080
```

# Banco de dados utilizado

```
H2 DataBase
para consultar as tabelas acesse 
http://localhost:8080/h2-console
User Name: sa
Password: 
```


### Comandos utils

```
Entrar no Container
docker exec -it [NOME_CONTAINER]   bash 
EX: docker exec -it kafkastream_kafka_1   bash 


# CONSUMER
# kafka-console-consumer --bootstrap-server localhost:9094 --topic topic2 --from-beginning

# PRODUCER
#  kafka-console-producer --bootstrap-server localhost:9094 --topic topic1


Objetos utilizado para produzir um evento

# {"nome": "Fernando 0","cpf": "123","dataNascimento": "1989-10-05"}
# {"nome": "Fernando 1","cpf": "12345","dataNascimento": "1989-10-05"}
# {"nome": "Fernando 2","cpf": "12345678","dataNascimento": "1989-10-05"}

```


Created by Fernando Oliveira

Contact the developer https://github.com/fernandoguide