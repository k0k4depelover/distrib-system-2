**Que estaba haciendo la ultima vez?**

*LEER ESTA NOTA PARA LA SIGUIENTE SESION DE CODIGO*

# Estrategia de Subida de Imágenes y Creación de Prendas

## Contexto y Objetivo
El frontend requiere subir una imagen primero, obtener su URL generada y pre-llenar de forma inmutable el formulario de la prenda antes de guardarla. Se requiere manejar las **imágenes huérfanas** (imágenes subidas donde el usuario abandona el formulario sin guardar la prenda).

---

## Arquitectura de 2 Endpoints con Staging / Limpieza

### Flujo Técnico

1. **Endpoint 1 — Subida Temporal de Imagen (`POST /api/imagenes/upload`)**
   - Recibe el archivo `MultipartFile`.
   - Guarda el archivo físico en el storage local/S3.
   - Crea un registro en `ImagenMD` con `confirmado = false` (o `id_prenda = null`).
   - Retorna un `ImageResponseDTO` con la `imageUrl` y el `id` generado.

2. **Endpoint 2 — Creación de Prenda (`POST /api/prendas`)**
   - Recibe la entidad/DTO de `Prenda` incluyendo el `id_imagen` o `imageUrl`.
   - Modifica el estado de `ImagenMD` a `confirmado = true`.
   - Guarda/Indexa el documento `Prenda` en Elasticsearch (`prendas`).

3. **Tarea Programada — Limpieza de Imágenes Huérfanas (`@Scheduled`)**
   - Tarea en segundo plano (ej. ejecuta cada medianoche o cada X horas).
   - Busca registros `ImagenMD` con `confirmado == false` y `fechaSubida` anterior a 24 horas.
   - Elimina los archivos físicos correspondientes del sistema de archivos.
   - Borra los registros huérfanos de la base de datos MySQL/PostgreSQL.

---

## Ajustes Importantes Pendientes en Código Java

1. **Corregir anotación `@Id` en `ImagenMD`:**
   - Cambiar de `org.springframework.data.annotation.Id` a `jakarta.persistence.Id`.
   - Añadir `@GeneratedValue(strategy = GenerationType.IDENTITY)`.

2. **Añadir campo de confirmación en `ImagenMD`:**
   ```java
   @Column(name = "confirmado")
   private Boolean confirmado = false;
   ```

3. **Corrección de ruta en `FileStorageService.guardarImagen`:**
   - Cambiar la asignación de `destino` para incluir el nombre del archivo:
     ```java
     Path destino = base.resolve(filename).normalize();
     ```

4. **Crear el Job de Limpieza (`ImageCleanupTask`):**
   ```java
   @Scheduled(cron = "0 0 0 * * ?")
   @Transactional
   public void limpiarHuerfanas() {
       LocalDateTime limite = LocalDateTime.now().minusHours(24);
       List<ImagenMD> huerfanas = repository.findByConfirmadoFalseAndFechaSubidaBefore(limite);
       for (ImagenMD img : huerfanas) {
           storage.eliminar(img.getNombreImagen());
           repository.delete(img);
       }
   }
   ```




## Notas Extra :

*Dado que no se habia considerado previamente la posibilidad de subir imagenes al sitio no se habia agregado una base de datos con propiedades ACID, por lo que el repositorio de Elastic Search tendra 2 bases de datos y un bucket de datos La logica es la siguiente:*

* Una Base de datos Indexada para busqueda de texto -> Elastic
* Una Base de datos que soporte subir, actualizar datos garantizando la Integridad Relacional y las propiedades ACID -> MySQL
* Bucket donde se almacenan streams de bytes.



Model -> Referencia a un objeto de Base de Datos relacional.
Document -> Referencia a un objeto de Base de Datos no relacional.