# Miarma_App
Proyecto Miarma AD y PMDM




## Descripción del Proyecto:
### Proyecto enfoncado principalmente en la subida/actualización/borrado/escalado de ficheros, así como implementación de seguridad y algunas excepciones

## <span style="color:red">IMPORTANTE: TODOS LOS ARCHIVOS UTLIZADOS EN EL POSTMAN DEBERÁN SER ADJUNTADOS NUEVAMENTE PARA SU CORRECTO FUNCIONAMIENTO

### Nuestra API tiene algunas "ligeras" similitudes con la aplicación  conocida como Instagram

# Registro/Login

- <span style="color:yellow">POST</span> Crear Usuario: Con esta petición crearemos un nuevo usuario creado previamente con un archivo Json (User.json) que tenga los datos del usuario y otro archivo que tenga la imagen de avatar del mismo (avatar.jpg). Se creará el avatar tanto con su imagen original como su imagen escalada escalada en la carpeta de /uploads.

- <span style="color:yellow">POST</span> En esta petición el nos loguearemos con el usuario previamente creado en el POST anterior para poder autentificarnos y tener permiso para realizar el resto de peticiones 

- <span style="color:green">GET</span> Datos de Usuario: Se nos mostrará los datos del usuario que se encuentra logueado en ese momento con todos atributos


# Publicaciones

- <span style="color:yellow">POST</span> Crear nueva publicación: Esta petición nos permite que el usuario logueado cree una nueva publicación que contenga todos los atributos de la misma guaradados en un fichero Json(Post.json) con la imagen(publicacion.jpg).Se creará la imagen del post tanto con su imagen original como su imagen escalada escalada en la carpeta de /uploads.

- <span style="color:blue">PUT</span> Editar Publicación: Esta petición nos permite que el usuario logueado edite una  publicación previamente creada y buscada por su id (PostUpdate.json) con la imagen(updatePublicacion.jpg).Se creará el avatar tanto con su imagen original como su imagen escalada escalada en la carpeta de /uploads.

- <span style="color:red">DELETE</span> Borrar Publicación: Petición que nos permite borrar el post de un usuario, así como la imagen original y escalada del post de la carpeta de /uploads.

- <span style="color:green">GET</span> Lista de Publicaciones Públicas: Petición que nos permite obtener todas las publicaciones de un usuario. Si no seguimos al usuario, solamente podremos ver las publicaciones guardadas como públicas. Si seguimos al usuario, podremos ver todas las publicaciones.

- <span style="color:green">GET</span> Buscar una publicación por su ID: Petición que nos permite obtener una publicación por su ID. 

- <span style="color:green">GET</span> Publicaciones Un usuario: Petición que nos permite obtener todas las publicaciones de un usuario. Si no seguimos al usuario, solamente podremos ver las publicaciones guardadas como públicas. Si seguimos al usuario, podremos ver todas las publicaciones.

- <span style="color:green">GET</span> Lista Publicaciones Usuario Logueado: Nos muestra todas las publicaciones del usuario que se encuentre logueado.
 # Usuario

 - <span style="color:green">GET</span> Mostrar Perfil de Usuario: Visualizar el perfil de un usuario.

    * #### Si seguimos al usuario, o tiene su perfil como público, se obtendrá su información.
    * #### Si no seguimos al usuario y su perfil es privado, obtendremos un mensaje de error. 


- <span style="color:blue">PUT</span> Editar mi perfil de usuario. Se puede cambiar cualquier información, incluida la foto de avatar.


| Paquete | URL |
| ------ | ------ |
| Carpeta de Imágenes | [MiarmaApp/Images](https://github.com/pabloseguravelasco/Miarma_App/tree/main/miarma/images) |
| Carpeta de Json | [MiarmaApp/Json](https://github.com/pabloseguravelasco/Miarma_App/tree/main/miarma/json) |





## Grupo de Trabajo:
* #### Pablo Segura Velasco

