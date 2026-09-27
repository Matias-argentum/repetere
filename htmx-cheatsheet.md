# htmx Cheat Sheet

## Idea general

htmx te deja hacer peticiones AJAX directamente desde atributos HTML,
sin escribir JS. El servidor responde con HTML (no JSON) y htmx lo
inserta en el DOM donde vos le digas.

## Atributos de petición

```html
hx-get="/ruta"
hx-post="/ruta"
hx-put="/ruta"
hx-delete="/ruta"
hx-patch="/ruta"
```

## Trigger (cuándo se dispara la petición)

```html
hx-trigger="click"                          <!-- default en la mayoría de elementos -->
hx-trigger="change"                         <!-- default en <input>, <select> -->
hx-trigger="submit"                         <!-- default en <form> -->
hx-trigger="keyup changed delay:500ms"      <!-- buscador con debounce -->
hx-trigger="load"                           <!-- al cargar el elemento -->
hx-trigger="every 5s"                       <!-- polling -->
hx-trigger="click once"                     <!-- solo la primera vez -->
hx-trigger="click from:body"                <!-- escuchar evento en otro elemento -->
```

## Target y swap (dónde y cómo se inserta la respuesta)

```html
hx-target="#content"        <!-- si no se especifica, es el propio elemento -->
hx-target="closest tr"      <!-- selectores relativos -->
hx-target="find .child"

hx-swap="innerHTML"         <!-- default: reemplaza el contenido interno -->
hx-swap="outerHTML"         <!-- reemplaza el elemento completo -->
hx-swap="beforeend"         <!-- agrega al final, dentro del target -->
hx-swap="afterbegin"        <!-- agrega al principio, dentro del target -->
hx-swap="beforebegin"       <!-- agrega antes del target, afuera -->
hx-swap="afterend"          <!-- agrega después del target, afuera -->
hx-swap="delete"            <!-- borra el target, ignora la respuesta -->
hx-swap="none"              <!-- no hace swap (útil si solo te interesan side effects) -->
```

Modificadores de swap:

```html
hx-swap="innerHTML swap:200ms settle:200ms"   <!-- timing de la transición -->
hx-swap="innerHTML scroll:top"                <!-- controla el scroll -->
```

## Out of Band swaps

Permite que la respuesta actualice OTRO elemento además del target
normal. Se define en el HTML que devuelve el servidor:

```html
<!-- Esto va en la respuesta del server -->
<div id="notificaciones" hx-swap-oob="true">
    Tenés 3 notificaciones nuevas
</div>
<div>
    <!-- este es el contenido normal que va al hx-target -->
</div>
```

## Navegación tipo SPA

```html
hx-boost="true"        <!-- convierte <a> y <form> normales en peticiones ajax -->
hx-push-url="true"     <!-- actualiza la URL del navegador (soporta back/forward) -->
hx-push-url="/ruta-custom"
```

Ejemplo típico para un layout con nav fijo:

```html
<nav hx-boost="true" hx-target="#content" hx-swap="innerHTML" hx-push-url="true">
    <a href="/decks">Mazos</a>
    <a href="/stats">Estadísticas</a>
</nav>
```

## Indicadores de carga

```html
<button hx-get="/algo" hx-indicator="#spinner">Cargar</button>
<img id="spinner" class="htmx-indicator" src="/spinner.gif">
```

htmx agrega automáticamente la clase `htmx-request` al elemento
mientras la petición está en curso (útil para CSS de loading states).

## Confirmación

```html
hx-confirm="¿Seguro que querés borrar esto?"
```

## Enviar datos extra

```html
hx-vals='{"origen": "dashboard"}'
hx-include="[name='filtro']"     <!-- incluye otros inputs del DOM -->
hx-params="not password"         <!-- excluye un campo del form al enviar -->
```

## Extraer parte de la respuesta

```html
hx-select="#solo-esto"     <!-- de la respuesta completa, usa solo ese selector -->
hx-select-oob="#otro-id"   <!-- selecciona algo para un oob swap -->
```

## Eventos y JS inline

```html
hx-on::after-request="console.log('listo')"
hx-on::before-request="this.disabled = true"
hx-on::response-error="alert('Error del servidor')"
```

Eventos disponibles: `htmx:beforeRequest`, `htmx:afterRequest`,
`htmx:responseError`, `htmx:beforeSwap`, `htmx:afterSwap`,
`htmx:load`, entre otros. Se pueden escuchar también con JS normal:

```js
document.body.addEventListener('htmx:afterSwap', function(evt) {
    console.log('swap hecho en', evt.detail.target);
});
```

## Headers importantes (server-side, en Java/Spring)

Leer si la petición viene de htmx:

```java
if (request.getHeader("HX-Request") != null) {
    // devolver solo el fragmento
}
```

Otros headers útiles que manda htmx en el request:

```
HX-Request: true
HX-Trigger: id del elemento que disparó la petición
HX-Target: id del target
HX-Current-URL: URL actual del navegador
```

Headers que podés devolver desde el servidor:

```
HX-Redirect: /login          <!-- fuerza un redirect completo del navegador -->
HX-Refresh: true             <!-- fuerza un full page reload -->
HX-Trigger: miEvento         <!-- dispara un evento custom en el cliente -->
HX-Push-Url: /nueva-ruta     <!-- fuerza push de URL desde el server -->
```

En Spring podés setearlos así:

```java
response.setHeader("HX-Redirect", "/login");
```

## Patrón típico con Thymeleaf (fragmentos)

```java
@GetMapping("/decks")
public String decks(HttpServletRequest request, Model model) {
    model.addAttribute("decks", deckService.findAll());
    if (request.getHeader("HX-Request") != null) {
        return "decks/list :: content";
    }
    return "decks/list :: page";
}
```

## Errores comunes

- Te olvidás de darle un `id` al div target (`hx-target="#content"` no funciona si nadie tiene `id="content"`).
- `hx-boost` en un link que apunta a otro dominio o a un recurso que no devuelve HTML (por ejemplo un PDF) puede romper el comportamiento esperado; ahí conviene sacarle el boost con `hx-boost="false"` en ese link puntual.
- Confundir `hx-target` (dónde se pone la respuesta) con `hx-swap` (cómo se pone).
- El script de htmx tiene que estar cargado en el `<head>` o antes de cerrar `<body>`, sino ningún atributo `hx-*` va a funcionar.
