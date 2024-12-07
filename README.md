Funcionalidades.

1. Firebase Auth: Autenticación con Google utilizando los servicios de Firebase Authentication. Una vez se haya iniciado sesión con Google, el activity redirige al usuario a otro activity con productos.

<img src="https://github.com/Toudopooks/KiteApp/blob/8ddb8d8265c7f4ed3110c7b2f4cc21b9f8ba8158/Screenshot_20241207_035707_KiteApp.jpg" height="400" width="200"/>

2. Galería de productos: Esta activity despliega todos los productos que están almacenados en Firebase Firestore y permite al usuario interactuar con estos productos para verlos más a detalle.

<img src="https://github.com/Toudopooks/KiteApp/blob/f12ab2412c00efe03b6c0a60dfa01d6c01f707eb/Screenshot_20241207_035717_KiteApp.jpg" height="400" width="200"/>

3. Agregar al carrito: En esta activity se puede ver un producto en más detalle, permitiendo al usuario la posibilidad de añadir el producto a un carrito de compras para luego comprarlo.

<img src="https://github.com/Toudopooks/KiteApp/blob/25e46710a78a47b65fb2581d2420631b8b8049c6/Screenshot_20241207_035727_KiteApp.jpg" height="400" width="200"/>

4. Comprar: Al comprar, se vacía el carrito y se notifica por medio de un toast que ya se ha realizado la compra, además de que se envía un mensaje por MQTT notificando que se ha realizado una compra. Luego, los detalles de la compra se quedan registrados en la aplicación.

<img src="https://github.com/Toudopooks/KiteApp/blob/f561c76e1d2ac5ce9f1841d255d60fa77904e89a/Screenshot_20241207_035738_KiteApp.jpg" height="400" width="200"/> <img src="https://github.com/Toudopooks/KiteApp/blob/f561c76e1d2ac5ce9f1841d255d60fa77904e89a/Screenshot_20241207_035825_KiteApp.jpg" height="400" width="200"/> <img src="https://github.com/Toudopooks/KiteApp/blob/f561c76e1d2ac5ce9f1841d255d60fa77904e89a/Screenshot_20241207_035829_IoT%20MQTT%20Panel.jpg" height="400" width="200"/>

5. Historial de compras: En los detalles de la cuenta del usuario, hay un botón que permite ingresar a la visualización de los productos que se han comprado desde que se tiene la aplicación.

<img src="https://github.com/Toudopooks/KiteApp/blob/f561c76e1d2ac5ce9f1841d255d60fa77904e89a/Screenshot_20241207_035842_KiteApp.jpg" height="400" width="200"/> <img src="https://github.com/Toudopooks/KiteApp/blob/f561c76e1d2ac5ce9f1841d255d60fa77904e89a/Screenshot_20241207_035837_KiteApp.jpg" height="400" width="200"/>

6. Cerrar sesión: En los detalles de la cuenta del usuario, hay un botón que permite al usuario cerrar su sesión de Google.

<img src="https://github.com/Toudopooks/KiteApp/blob/f561c76e1d2ac5ce9f1841d255d60fa77904e89a/Screenshot_20241207_035842_KiteApp.jpg" height="400" width="200"/> <img src="https://github.com/Toudopooks/KiteApp/blob/8ddb8d8265c7f4ed3110c7b2f4cc21b9f8ba8158/Screenshot_20241207_035707_KiteApp.jpg" height="400" width="200"/> 
