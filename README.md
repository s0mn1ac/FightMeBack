<p align="center">
<h1 align="center">
<img src="https://cdn3.iconfinder.com/data/icons/fantasy-and-role-play-game-adventure-quest/512/Elf-512.png" width="50" />
<img src="https://cdn3.iconfinder.com/data/icons/fantasy-and-role-play-game-adventure-quest/512/Knight-512.png" width="50" />
<img src="https://cdn3.iconfinder.com/data/icons/fantasy-and-role-play-game-adventure-quest/512/Sorceress-512.png" width="50" />
FightMe!
<img src="https://cdn3.iconfinder.com/data/icons/fantasy-and-role-play-game-adventure-quest/512/Sorceress-512.png" width="50" />
<img src="https://cdn3.iconfinder.com/data/icons/fantasy-and-role-play-game-adventure-quest/512/Knight-512.png" width="50" />
<img src="https://cdn3.iconfinder.com/data/icons/fantasy-and-role-play-game-adventure-quest/512/Elf-512.png" width="50" />
</h1>
</p>

Este proyecto ha sido desarrollado por Juan Miguel González Machado como práctica personal.


## Contenido

* [Descripción](#descripcion)
* [Autores](#autores)
* [Instalación](#instalacion)
* [Apartados](#apartados)
* [Agradecimientos](#agradecimientos)
  
  
## <a name="descripcion"> </a>Descripción

¡Ahora con un modo de lucha manual!

![Demo batalla manual](https://i.imgur.com/zQ4GBLB.gif "Demo batalla manual")

El proyecto **FightMe!** consta de un [Frontend](https://github.com/s0mn1ac/FightMeFront) desarrollado en **Angular** y un [Backend](https://github.com/s0mn1ac/FightMeBack) desarrollado en **Spring**. La base de este proyecto es la de ofrecer a los *administradores* de la app un **CRUD** completo mediante el cual puedan gestionar el sitio, a la vez que se le brinda a los *usuarios* la posibilidad de visualizar tanto sus personajes como los de sus rivales, para enfrentarlos en batallas automáticas (generadas aleatoriamente) o manuales.




## <a name="autores"> </a>Autores

* Juan Miguel Gonzalez Machado
  
  
## <a name="instalacion"> </a>Instalación

A continuación encontrarás todo lo necesario para poner en marcha el proyecto.

### Requisitos previos

- Spring Tool Suite
- Java Development Kit
- Oracle Express
- SQL Developer
- Postman (opcional)
- [Frontend](https://github.com/s0mn1ac/FightMeFront) operativo

### Descarga del proyecto

Utiliza el siguiente comando para clonar el **repositorio**:

``` bash
$ git clone https://github.com/s0mn1ac/FightMeBack.git
```

También puedes descargar el proyecto en formato **zip** desde [aquí](https://github.com/s0mn1ac/FightMeBack/archive/master.zip) 

### Crear una nueva conexión en Oracle SQL Developer

Una vez descargado el proyecto, el siguiente paso es preparar la base de datos. Para ello deberemos crear una nueva conexión en **Oracle SQL Developer**.

> Recomendamos crear un usuario en exclusiva para la conexión. En nuestro caso hemos usado el nombre **fightme** para identificar tanto al usuario como a la conexión. A  continuación encontrarás un script de ejemplo para dar de alta un nuevo usuario y otorgarle los permisos necesarios.

``` plsql
create user fightme identified by root;
grant connect to fightme;
grant connect, resource, dba to fightme;
grant create session, grant any privilege to fightme;
grant unlimited tablespace to fightme;
```

> Nota: Los datos anteriores son orientativos. Si deseas utilizar una nomenclatura propia no tienes más que actualizar el fichero de configuración de Spring  **application.properties**.

### Añadir un usuario administrador

Actualmente estamos trabajando en mejorar este apartado. De momento, para poder comenzar a trabajar en el proyecto hay que crear un usuario **administrador**. Para ello, hay que añadir los roles **ROLE_ADMIN** y **ROLE_USER** a la base de datos y luego crear un usuario administrador.

Para hacer más sencillo este paso te recomendamos desactivar **Spring Security** e inyectar la información a través de **Postman**

![Desactivar Spring Security](https://i.imgur.com/0V2HUsj.gif "Desactivar Spring Security")

> Una vez dado de alta el usuario administrador ya podrás loguearte y comenzar a utilizar la aplicación en su totalidad



## <a name="apartados"> </a>Apartados

La aplicación está dividida en **5 apartados principales**:

### Login

El nuevo login mejorado permite iniciar sesión con un *usuario* y *contraseña* o utilizando la api **Face** de **Microsoft Azure**, la cual utiliza reconocimiento facial para ello. Esta actualización añade además una capa extra de seguridad, ya que únicamente permite el reconocimiento facial mediante webcam y únicamente si detecta una cara humana.

> **Nota:** El reconocimiento facial debe ser previamente habilitado por un administrador.

![FaceId con cara](https://i.imgur.com/XIWXsyQ.png "FaceId con cara")

![FaceId sin cara](https://i.imgur.com/0C3Insx.png "FaceId sin cara")


### Panel del jugadores

Aquí encontrarás todos los personajes asignados a tu usuario, así como tus rivales. Podrás consultar las estadísticas de todos y cada uno de ellos y además podrás usar alguno de los tuyos para comenzar una batalla.

![Panel de jugadores](https://i.imgur.com/pqe9rGT.png "Panel de jugadores")

![Información de jugador](https://i.imgur.com/2487Ekh.png "Información de jugador")


### CRUD

Separado en tres secciones, permite a los *administradores* añadir, editar, eliminar y visualizar **usuarios**, **personajes** y **habilidades**.

> **Nota:** Este apartado es de uso exclusivo para administradores. El resto de usuarios tiene restringido el acceso.

![Usuarios](https://i.imgur.com/VvBsZZJ.png "Usuarios")

![Personajes](https://i.imgur.com/bvbd2ut.png "Personajes")

![Habilidades](https://i.imgur.com/5LY4qU8.png "Habilidades")


### Información

Si tienes dudas, puedes consultar este apartado para saber más sobre cada tipo de personaje.

![Información](https://i.imgur.com/4cSq3g3.png "Información")

### Batalla

Pon a prueba tus personajes enfrentándolos a los del resto de jugadores.

![Demo batalla automática](https://i.imgur.com/ukhwLh7.gif "Demo batalla automática")


## <a name="agradecimientos"> </a>Agradecimientos

Las imágenes de los personajes empleadas en el proyecto han sido diseñadas por [**Chanut is Industries**](https://www.iconfinder.com/Chanut-is).
Todas las banderas empleadas son un diseño original de [**Freepik**](https://www.flaticon.es/autores/freepik).