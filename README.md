# ineednewmusic

Aplicación desarrollada por María Sisamón Márquez - FP DAW 💻 Vedruna Sevilla

## Índice
1. [Introducción](#introducción)
2. [Funcionalidades y Tecnologías Utilizadas](#funcionalidades-y-tecnologías-utilizadas)
3. [Guía de Instalación](#guía-de-instalación)
4. [Guía de Uso](#guía-de-uso)
5. [Documentación](#documentación)
6. [Interfaz de Usuario en Figma](#interfaz-de-usuario-en-figma)
7. [Despliegue](#despliegue)
8. [Conclusión](#conclusión)
9. [Contribuciones, Agradecimientos y Referencias](#contribuciones-agradecimientos-y-referencias)
10. [Licencias](#licencias)
11. [Contacto](#contacto)

---

## Introducción

### Descripción del proyecto:
Esta aplicación basada en microservicios permite a los usuarios buscar canciones o artistas en Spotify, seleccionar hasta 5 elementos y generar una playlist basada en esas elecciones, que luego pueden guardar directamente en su cuenta de Spotify. Se compone de tres partes, el front con react, el core con java spring y una api desarrollada en python que se conecta con la base de datos de Azure MySQL.

### Justificación:
El proyecto busca simplificar la creación de playlists personalizadas utilizando la API de Spotify, ofreciendo una interfaz amigable y funcionalidades avanzadas para gestionar la música.

Actualmente esta funcionalidad la implementa Spotify con las playlists semanales o las playlists autogeneradas que aparecen en cada perfil, pero la única manera de usar esta funcionlidad de manera personalizada y bajo demanda es usando la API de Spotify. Es por eso que decidí crear una interfaz gráfica que facilitara su uso, para que fuera más accesible.

### Objetivos:
- Integrar la API de Spotify para realizar búsquedas y generar playlists.
- Implementar autenticación y autorización de usuarios.
- Guardar información de usuarios en una base de datos en la nube.
- Desplegar la aplicación usando Docker y Kubernetes.

### Motivación:
La motivación principal es facilitar la creación de playlists personalizadas, aprovechando la vasta biblioteca de música de Spotify y proporcionando una herramienta útil para los usuarios de esta plataforma. Es una funcionalidad oculta que está muy desaprovechada.

Entre las motivaciones también se encuentra el interés por aprender a desplegar un proyecto de manera real, poniendo especial hincapié en kubernetes y github actions; así como aprender a usar React con más soltura.


## Funcionalidades y Tecnologías Utilizadas

**Funcionalidades del Proyecto:**
- Búsqueda de canciones y artistas en Spotify.
- Selección de hasta 5 elementos para generar una playlist.
- Guardado directo de la playlist en la cuenta de Spotify del usuario.
- Almacenamiento de datos de usuarios en una base de datos MySQL en Azure.
- Acceso restringido mediante Auth header a la api de gestión de la base de datos.
- Autenticación y autorización de usuarios con tokens de acceso.

**Tecnologías Utilizadas:**
- **Front-end:** React para la interfaz de usuario y gestión de tokens de acceso.
- **Back-end Core:** Java Spring para la lógica de negocio y comunicación con la API de Spotify y la api de gestión de usuarios.
- **Back-end Database API:** Python FastAPI para gestionar la base de datos de usuarios.
- **Base de Datos:** MySQL alojado en Azure.
- **Despliegue:** GitHub Actions, Docker, Kubernetes y Azure Container Registry.


## Guía de Instalación

1. **Clonar todos los repositorios del proyecto**

2. **Configurar Azure virtual machine, Azure Kubernetes Cluster, Azure Registry**

3. **Configurar los Github secrets según tus claves y credenciales**
    - Spotify Credentials
    - App callback
    - Azure config
    - Kuberetes Config
    - Database Url
    - Database header Auth string

4. **Configurar variables de entorno:**  
   Si es necesario, en caso de añadir o quitar algún Github secret, asegurar que las variables de entorno de cada proyecto se ajustan según estos cambios.

5. **Instalar Docker y Kubernetes en la máquina virtual de azure, además de configurar el Cluster en la máquina**


## Guía de Uso

1. **Abrir la aplicación en el navegador:**  
   Acceder a la URL **http://20.40.146.138**

2. **Iniciar sesión con Spotify:**  
   Hacer clic en **Spotify** y seguir las instrucciones de autenticación.

3. **Buscar canciones o artistas:**  
   Utilizar la barra de búsqueda para encontrar canciones o artistas y seleccionarlos.

4. **Generar y guardar la playlist:**  
   Después de seleccionar hasta 5 elementos, generar la playlist, asignarle un nombre y guardarla en tu cuenta de Spotify. Cada canción puede escucharse mediante una preview antes de guardar la playlist.

## Documentación

### Front-end

**Desarrollado con:** React

**Dependencias Principales:**
- **react:** Biblioteca principal para construir la interfaz de usuario.
- **axios:** Cliente HTTP para realizar peticiones a la API.
- **react-router-dom:** Para manejo de rutas en la aplicación.

**Características Principales:**
- **Autenticación:** Implementada en **Login.jsx** , **Callback.jsx** y **RefreshToken.jsx**.
- **Búsqueda:** Implementada en **SearchComponent.jsx** y **SearchBar.jsx**.
- **Generación de Playlists:** Implementada en **RecommendationPlaylist.jsx**.

**Componentes Clave:**
- **App.jsx:** Componente principal que maneja las rutas.
- **Navbar.jsx:** Barra de navegación. Contiene el logo de la app, el modal con la info básica y la información del usuario con el logout.
- **SearchComponent.jsx:** Componente para buscar canciones y artistas.
- **RecommendationPlaylist.jsx:** Componente para generar y guardar playlists.

### Core (Back-end)

**Desarrollado con:** Java Spring Boot

**Dependencias Principales:**
- **spring-boot-starter-web:** Para construir APIs REST.
- **gson:** Para gestionar los json.
- **lombok:** Para automatizar la implementación de getters, setters y constructores.
- **michaelthelin.spotify:** Dependencia para interactuar con la API de Spotify.

**Características Principales:**
- **Login usuarios:** Gestiona el resgistro de usuarios, ya que se comunica con la API de gestión de usuarios. Para ello usa **Webclient**. Además, realiza la petición de la información del usuario a la API de Spotify según el token proporcionado.
- **Búsqueda y Recomendaciones:** Implementado en **SpotifyController.java** y **RecommendationService.java**.

**Clases y Métodos Clave:**

#### **SpotifyController.java**:
Controlador principal que maneja las peticiones a la API de Spotify.
- **Métodos:**
    - **getUserProfile(String token):** Recupera datos básicos del perfil de usuario.
    - **login(String token):** Aplica la lógica para conectarse con la api de gestión de usarios para crearlos si no se han logueado previamente.
    - **searchItem(SearchRequestDTO request):** Busca canciones y artistas en Spotify según el término de búsqueda proporcionado.
    - **getRecommendations(RecommendationDTO recommendationDTO):** Genera una playlist basada en las recomendaciones proporcionadas.
    - **createPlaylist(RecommendationDTO recommendationDTO):** Guarda la playlist en el perfil del usuario.

#### **RecommendationService.java**
Lógica de negocio para generar recomendaciones.
- **Métodos:**
    - Aplica la lógica necesaria, usando el token proporcionado y el DTO para generar la petición y devolver los datos al controlador para cada endpoint de creación de listas y búsqueda de items.
#### **UserService.java**
Lógica de negocio para el perfil de usuario.
- **Métodos:**
    - Aplica la lógica necesaria, usando el token proporcionado, para la gestión de usuarios.

#### **GlobalCorsConfig.java**
Configuración de CORS para permitir solicitudes desde el front-end.
- **Métodos:**
    - **addCorsMappings(CorsRegistry registry):** Configura los orígenes permitidos, métodos y encabezados para las solicitudes CORS.

### Database API

**Lenguaje y Framework:** Python FastAPI

**Dependencias Principales:**
- **fastapi:** Framework para construir APIs web rápidas.
- **sqlalchemy:** ORM para interactuar con la base de datos MySQL.
- **pydantic:** Para validación de datos.

**Características Principales:**
- **Gestión de Usuarios:** Implementada en **user_Service.py**.
- **Conexión a la Base de Datos:** Configurada en **connect.py**.

**Módulos Clave:**

#### **main.py**
Punto de entrada de la aplicación.
- **Rutas:**
    - Incluye rutas para manejo de usuarios.

#### **main_controller.py**
Manejo de rutas y lógica de negocio relacionada con los usuarios.
- **Rutas:**
    - **@app.post("/users/"):** Crea un nuevo usuario si no existe anteriormente.
    - **@app.get("/users/{user_id}):** Obtiene la información de un usuario por ID.
- **Métodos:**
    - **create_user(user: UserCreate, db: Session):** Crea y almacena un nuevo usuario en la base de datos.

#### **users.py**
Configuración de la base de datos y modelos.
- **Modelos:**
    - **User:** Define la estructura de la tabla de usuarios en la base de datos.

**Autorización:**
Todas las peticiones a la API requieren un encabezado de autorización con un token válido, en este caso es una contraseña estática.

### Conexión entre las Partes del Proyecto

1. **Front-end** se comunica con **Core** a través de peticiones HTTP usando **axios** y realiza peticiones directamente a la API de Spotify para recuperar el token de usuario con el Callback. El token se refresca de manera automática gracias a una funcionalidad que almacena y comprueba cuándo está a punto de caducar.
2. **Core** maneja la lógica de negocio y se comunica con la API de Spotify para el resto de peticiones que no tienen que ver con el token de autenticación. También se comunica con **User Database API** para almacenar el resgistro de usuarios.
4. **User Database API** gestiona la base de datos MySQL en Azure.


## Interfaz de Usuario en Figma
Muestra el diseño de la interfaz de usuario tanto en móvil como en desktop. [Enlace a Figma](https://www.figma.com/design/7m8qMj7rrkv59DqWGB6SxW/TFG-INEEDNEWMUSIC?node-id=0-1&t=GeCb3cLT52Qw8Rzr-1)

## Despliegue

### Docker

- **Construcción de Imágenes:**  
  Cada parte del proyecto (**front**, **core**, **dbapi**) tiene un **Dockerfile** para construir las imágenes de Docker. Las imágenes se suben a Azure Registry.


### GitHub Actions

- **Automatización del Despliegue:**  
  El archivo **.github/workflows/release.yml** define un flujo de trabajo que automatiza la construcción y despliegue de imágenes de Docker en cada push al repositorio y con Kubernetes.

### Kubernetes

1. **Despliegue en Kubernetes:**  
   Los archivos en la carpeta **manifest** (**release-deployment.yml**, **release-namespace.yml**, **release-services.yml**) definen los despliegues, namespaces y servicios para Kubernetes.

2. **Registro de Imágenes:**  
   Las imágenes de Docker se suben al Azure Container Registry y se despliegan en el clúster de Kubernetes.

### Conexión entre las Partes del Proyecto en el despliegue
1. **Front-end:** Desplegado como un servicio en Kubernetes, accesible públicamente.
2. **Core:** Desplegado como un servicio en Kubernetes, accesible desde el front-end.
3. **Database API:** Desplegado como un servicio en Kubernetes, accesible desde el Core.
4. **Base de Datos MySQL:** Alojada en Azure y accesible desde la API de base de datos.


## Conclusión
Me ha gustado desarrollar esta aplicación por dos motivos principales. El primero es que he conseguido crear una app realmente útil y necesaria para los usuarios de Spotify, que realiza exactamente lo que se propone y aporta valor a la experiencia de usuario de Spotify.

La segunda cosa positiva que saco del desarrollo de este proyecto es el aprendizaje que me llevo tanto en relación a la planificación y gestión de proyectos; React y, sobre todo, en Github Actions y Kubernetes, así como en Azure.

## Contribuciones, Agradecimientos y Referencias

**Agradecimientos:** Agradezco a las personas que han testeado mi aplicación una vez desplegada.

**Referencias:**

Documentación:
- [React](https://reactjs.org/docs/getting-started.html)
- [Spotify API](https://developer.spotify.com/documentation/web-api)
- [FastAPI](https://fastapi.tiangolo.com/)
- [spotify-web-api-java](https://github.com/spotify-web-api-java/spotify-web-api-java/blob/master/README.md)

## Licencias

Este proyecto está licenciado bajo la Licencia MIT.

## Contacto
**Nombre**: María Sisamón


**Email**: maria.sisamon@a.vedrunasevillasj.es


**GitHub**: [maria1sm](https://github.com/maria1sm)
