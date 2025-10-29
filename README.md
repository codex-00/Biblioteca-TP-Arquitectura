# Sistema de Gestión de Biblioteca 📚

Trabajo Práctico – Unidad 3: Diseño Arquitectónico  
* Materia: Ingeniería de Software II
* Profesor: Víctor Hugo Contreras

## 🏗️ Arquitectura en 3 Capas

El sistema se organiza en:

- **Capa de Presentación**: Interacción con el usuario.
- **Capa de Negocio**: Reglas del negocio (préstamos, devoluciones, validaciones).
- **Capa de Datos**: Acceso a la base de datos mediante DAOs.

Se implementa el patrón de diseño **Singleton** para un acceso centralizado a la BD.

---

## 📂 Estructura del Proyecto

```
├── src/
│   ├── presentacion/
│   │   └── Main.java
│   ├── negocio/
│   │   └── GestorLibros.java
│   └── datos/
│       ├── ConexionDB.java
│       ├── LibroDAO.java
│       └── SocioDAO.java (opcional)
├── uml/
│   └── uml_biblioteca.png
└── README.md
```

---

## ▶️ Ejecución

Compilar y ejecutar en consola:
```sh
cd src
javac presentacion/Main.java
java presentacion.Main
```

---

## ✨ Autor
Alejo Ezequiel Escurra

