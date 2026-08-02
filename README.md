# distrib-system-2

Sistema Distribuido que trabaja en conjunto con el repo:
https://github.com/k0k4depelover/fashion-mnist 

Para brindar servicios de prediccion de prendas, ademas tiene pendiente la implementacion de Web-Sockets, y gRPC para la llamada de dichos servicios a la API REST de python.


**Completo:**
- *Docker compose*
- *Redis cache*
- *Redis rate limiting*
- *Auto-descubrimiento con Netflix eureka*
- *API Gateway con sesiones stateful (Guardadas en Redis)*
- *Soporte para multiples instancias del servicio de autenticacion (LB)*
- *Servidor de Websockets*


**Pendiente:**
- *Remote procedure call hacia python*
- *Sharding en bases de datos para las conexiones stateful*
- *Soporte de load balancing en websocket (LB)*
- *Frontend*
- *Visibilidad y trazabilidad con grafana*