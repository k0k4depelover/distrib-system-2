# distrib-system-2


Sistema Distribuido que se conecta a un microservicio de python utilizando 
**remote procedure call**, ademas posee un sistema de web-sockets, partes totales de este sistema:

**Completo:**
*Docker compose*
*Redis cache*
*Redis rate limiting*
*Auto-descubrimiento con Netflix eureka*
*API Gateway con sesiones stateful (Guardadas en Redis)*
*Soporte para multiples instancias del servicio de autenticacion (LB)*
*Servidor de Websockets*


**Pendiente:**
*Remote procedure call hacia python*
*Sharding en bases de datos para las conexiones stateful*
*Soporte de load balancing en websocket (LB)*
*Frontend*
*Visibilidad y trazabilidad con grafana*