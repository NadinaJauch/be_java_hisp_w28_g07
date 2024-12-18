# Este proyecto fue creado en el marco del Bootcamp de JAVA de la Wave 28 de MERCADO LIBRE.

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

## Repositorios

Se utilizó la clase `BaseRepository` como base para proporcionar métodos comunes a los repositorios hijos, de forma que estos puedan definir una interfaz con los métodos comunes sin tener que implementar nuevamente los mismos, ya que se encuentran implementados en la clase padre (`Base`).

# Convenciones

- **Rutas**: kebab-case
- **Datos devueltos**: snake_case
- **Métodos y variables**: camelCase
- **Nombres de clases**: PascalCase
