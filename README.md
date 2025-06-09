# FerroMex

FerroMex es un sistema de gestión para una ferretería que permite administrar clientes, productos, categorías, usuarios con roles, y generar ventas con facturas enviadas por correo electrónico.

---

## Características principales

- **Gestión de clientes:** agregar, editar, eliminar.
- **Gestión de productos:** agregar, editar, eliminar.
- **Gestión de categorías:** administrar categorías para productos.
- **Administración de usuarios:** crear usuarios y asignar roles (Cajero, Supervisor, Admin).
- **Generación de ventas:** registrar ventas y generar comprobantes en PDF.
- **Envío de comprobantes:** el sistema envía automáticamente la factura PDF al correo electrónico del cliente.
- **Seguridad:** requiere contraseña de aplicación para enviar correos (compatible con Gmail y otros proveedores).

---

## Tecnologías usadas

- Lenguaje: **Java**
- Librerías:
  - **iText** para generación de PDFs.
  - **JavaMail** para envío de correos electrónicos.
- Interfaces gráficas: **Swing**
- Base de datos: MySQL (u otro gestor compatible con SQL)

---

## Instalación y configuración

1. **Importar el proyecto:**
   - Asegúrate de tener Java y un IDE configurado.
   - Importa las carpetas `componente` y `conectores` con las librerías necesarias. Si alguna librería no funciona, impórtala manualmente.

2. **Configurar base de datos:**
   - Descarga el archivo `sistema_ventas.sql` incluido.
   - Importa este archivo en tu gestor de base de datos (MySQL, MariaDB, etc.).
   - Realiza los ajustes necesarios en la base de datos según tus necesidades.

3. **Configurar credenciales de correo:**
   - El sistema utiliza JavaMail para enviar correos.
   - Para Gmail, genera una **contraseña de aplicación** y configúrala en el código donde se define la variable `clave`.
   - Asegúrate de que la cuenta de correo tenga habilitado el acceso para apps menos seguras o usa OAuth si es necesario.

---

## Uso

- Inicia el sistema y registra usuarios con los roles adecuados.
- Administra clientes, productos y categorías desde la interfaz.
- Realiza ventas seleccionando productos y clientes.
- Al generar una venta, se crea un PDF con el comprobante y se envía automáticamente al correo del cliente.

---

## Notas importantes

- El archivo PDF no se abre automáticamente, sino que se envía por correo para mayor seguridad y comodidad.
- Verifica que las librerías estén correctamente importadas en tu entorno.
- Personaliza el archivo SQL y la configuración del sistema para adaptarlo a tu ferretería.

---

¡Gracias por FerroMex!

