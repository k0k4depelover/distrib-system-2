**Que estaba haciendo la ultima vez?**

*LEER ESTA NOTA PARA LA SIGUIENTE SESION DE CODIGO*

Ya estan los repositorios, interfaces y servicios para guardar imagenes, el plan es crear los controladores que manejen la subida de archivos por separado, dicha api se llama desde la primera api para subir una prenda al servidor por lo que este facilitarà la subida de archivos unicamente, y tambien proveera la URL que sera indexada en el servidor Elastic, posteriormente se implmentara la opcion para obtener la imagen mediante la URL cuando se realicen busquedas, dado que el servidor responde con la metadata de los archivos, entre ellas la url del archivo, nos ayudara para llamar a la API con dichas urls y mostrarlas al cliente, lo unico que quedaria pendiente de definir en ese contexto es saber si se implementara un servicio para obtener varios paths a la vez, o alguna clase de buffer o cola para servir los archivos del backend, quedaria pendiente tras construir el frontend y analizar el rendimiento de la aplicacion.


Actualizaciòn:

*Dado que no se habia considerado previamente la posibilidad de subir imagenes al sitio no se habia agregado una base de datos con propiedades ACID, por lo que el repositorio de Elastic Search tendra 2 bases de datos y un bucket de datos La logica es la siguiente:*

* Una Base de datos Indexada para busqueda de texto -> Elastic
* Una Base de datos que soporte subir, actualizar datos garantizando la Integridad Relacional y las propiedades ACID -> MySQL
* Bucket donde se almacenan streams de bytes.