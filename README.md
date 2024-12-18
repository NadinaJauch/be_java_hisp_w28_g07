# Este proyecto fue creado en el marco del Bootcamp de JAVA de la Wave 28 de MERCADO LIBRE. API SOCIAL MELI 

# Descripción
  
Mercado Libre sigue creciendo y para el año que viene  tiene como objetivo empezar a implementar una serie de herramientas que permitan a los compradores y vendedores tener una experiencia totalmente innovadora, en donde el lazo que los una sea mucho más cercano. Es necesaria la presentación de una versión Beta de lo que va a ser conocido como “SocialMeli”, en donde los compradores van a poder seguir a sus vendedores favoritos y enterarse de todas las novedades que los mismos posteen.

# Requisitos

- **Java 21 (JDK 21)**
- **Maven**

# Instrucciones para ejecutar el proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/NadinaJauch/be_java_hisp_w28_g07.git
2. Entrar al directorio del proyecto:
   ```bash
    cd be_java_hisp_w28_g07
    cd social-meli
3. Limpiar e instalar las dependencias con Maven:
   ```bash
    mvn clean install
4. Ejecutar la aplicación con Spring Boot:
   ```bash
   mvn spring-boot:run

# Datos

Este proyecto maneja los datos del modelo a través de listas, que inicialmente se cargan a partir de archivos de tipo `.JSON` que contienen datos de pruebas, acordes a las entidades. Estos archivos se encuentran en la carpeta `src/main/resources`.

El método que se encarga de llenar inicialmente las listas está en el `BaseRepository`, y se ejecuta cada vez que se instancia un repositorio hijo, pasando por parámetro el nombre del archivo correspondiente a la entidad del repositorio.

# Pruebas 

Las pruebas para este proyecto se realizan desde Postman, la colección para realizar las mismas se encuentra en la carpeta de resources. 

## Repositorios

Se utilizó la clase `BaseRepository` como base para proporcionar métodos comunes a los repositorios hijos, de forma que estos puedan definir una interfaz con los métodos comunes sin tener que implementar nuevamente los mismos, ya que se encuentran implementados en la clase padre (`Base`).

# Convenciones

- **Rutas**: kebab-case
- **Datos devueltos**: snake_case
- **Métodos y variables**: camelCase
- **Nombres de clases**: PascalCase

# Endpoints y responsables de su programación 

- 1. Poder realizar la acción de “Follow” (seguir) a un determinado usuario - (Fernanda Agustina Castaldo)
- 2. Obtener el resultado de la cantidad de usuarios que siguen a un determinado vendedor - (Lisandro Giussani Alo)
- 3. Obtener un listado de todos los usuarios que siguen a un determinado vendedor (¿Quién me sigue?) - (Pilar Innocenti)
- 4. Obtener un listado de todos los vendedores a los cuales sigue un determinado usuario (¿A quién sigo?) - (Martin Daniel Simonetti)
- 5. Dar de alta una nueva publicación. - (Martin Ignacio Romero)
- 6. Obtener un listado de las publicaciones realizadas en las últimas dos semanas, por los vendedores que un usuario sigue (para esto tener en cuenta ordenamiento por fecha, publicaciones más recientes primero). - (Nadina Ambar Jauch)
- 7. Poder realizar la acción de “Unfollow” (dejar de seguir) a un determinado vendedor. - (Lisandro Giussani Alo)
- 8. Alfabético Ascendente y Descendente de los seguidos y lo seguidores - (Martin Daniel Simonetti)
- 9. Fecha Ascendente y Descendente de los posteos de los vendedores que sigue un usuario - (Pilar Innocenti)
- 10. Llevar a cabo la publicación de un nuevo producto en promoción.- (Martin Ignacio Romero)
- 11. Obtener la cantidad de productos en promoción de un determinado vendedor - (Lisandro Giussani Alo)
- 12. (Bonus) Obtener posts por id de categoria y rango de precio - (Lisandro Giussani Alo)
- 13. (Bonus) Marcar post como favorito, y obtener los favoritos de un usuario - (Nadina Ambar Jauch)
- 14. (Bonus) Traer todos los usuarios - (Fernanda Agustina Castaldo) 

# Integrantes 

- Nadina Ambar Jauch 
- Martin Daniel Simonetti
- Martin Ignacio Romero
- Lisandro Giussani Alo
- Fernanda Agustina Castaldo
- Pilar Innocenti 
