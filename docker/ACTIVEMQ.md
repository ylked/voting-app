# Docker active MQ container

## Récupération et lancement de l'image

> docker run --detach --name rabbitmq -p 61616:61616 -p 8161:8161 --rm apache/activemq-artemis:latest-alpine

## Console d'administration web
La console d'administration du broker de message est accessible à cette adresse:
> http://localhost:8161

* username: artemis
* password: artemis