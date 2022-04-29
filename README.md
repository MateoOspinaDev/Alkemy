# DisneyApp
### Java Spring Boot (API) 

Desarrollo de una API que permite crear, conocer y modifcar entidades de personajes, peliculas y generos. También permite agregar y quitar personajes de peliculas. Además permite navegar por estos personajes y sus películas y se expone la información para que cualquier frontend pueda consumirla.

**Documentación**:La documentación de los endpoints fueron realizados con Postman y pueden ser vistos en este enlace: https://documenter.getpostman.com/view/17448776/UVysxbYp
### Especificaciones técnicas:
- Realizado con SpringBoot siguiendo el patrón REST.
- Modelado de base de datos con MySQL.
- Uso de la libreria Spring Security para la autenticación.
- Uso de JPA para la persistencia de todos los datos.
- Test de los controladores con JUnit, Mockito y SpringBootTest(MockMvc).
- Envío de emails con JavaMailSender.
- Gestión de excepciones con RestControllerAdvice.

### Especificaciones funcionales:
- Para acceder a las funcionalidades, el usuario se deberá registrar y posteriormente loguear.
Al momento de regitrarse recibirá un correo de bienvenida y cuando ingrese recibirá el token de acceso que deberá colocar en los headers de cada petición
- Creación, edición y eliminación de personajes.
- Acceso a las peliculas con todos sus detalles, incluido genero y personajes asociados.
 - Filtrado de peliculas por nombre, genero y ordenadas de forma ascendente o descendente dependiendo su fecha de creación. La respuesta contendrá: imagen, titulo y fecha de creación.
- Creación, edición y eliminación de personajes.
- Acceso a los personajes con todos sus detalles, incluido peliculas donde participa.
- Filtrado de personajes por nombre o edad. La respuesta contendrá nombre e imagen.
- Creación de generos.
