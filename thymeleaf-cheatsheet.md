# Thymeleaf Cheat Sheet

## Texto y variables

```html
th:text="${var}"      <!-- texto escapado -->
th:utext="${var}"     <!-- texto sin escapar (cuidado con XSS) -->
```

Inlining (usar variables dentro de texto normal, sin necesidad de un tag):

```html
<p>Hola [[${usuario.nombre}]]</p>       <!-- escapado -->
<p>Hola [(${usuario.nombre})]</p>       <!-- sin escapar -->
```

## Condicionales

```html
th:if="${cond}"
th:unless="${cond}"
th:switch="${var}"
    th:case="'valor1'"
    th:case="*"   <!-- default -->
```

## Loops

```html
th:each="item : ${lista}"
th:each="item, stat : ${lista}"
```

Variables de `stat`: `stat.index` (desde 0), `stat.count` (desde 1), `stat.size`,
`stat.first`, `stat.last`, `stat.even`, `stat.odd`.

## Atributos dinámicos

```html
th:href="@{/ruta}"
th:href="@{/ruta/{id}(id=${obj.id})}"          <!-- path variable -->
th:href="@{/ruta(param1=${a}, param2=${b})}"   <!-- query params -->
th:src="@{/img/logo.png}"
th:value="${valor}"
th:class="${activo} ? 'active' : ''"
th:classappend="${activo} ? 'active'"
th:attr="data-id=${obj.id}, data-foo=${bar}"   <!-- atributo genérico -->
th:disabled="${cond}"                          <!-- atributos booleanos -->
```

## Formularios

```html
<form th:action="@{/decks}" th:object="${deckForm}" method="post">
    <input th:field="*{nombre}" />
    <span th:if="${#fields.hasErrors('nombre')}" th:errors="*{nombre}"></span>
    <button type="submit">Guardar</button>
</form>
```

`th:field` genera automáticamente `id`, `name` y `value` ligados al objeto.

## Variables locales

```html
<div th:with="total=${precio * cantidad}">
    <p th:text="${total}"></p>
</div>
```

## th:block

Agrupa contenido sin generar una etiqueta HTML extra en el output:

```html
<th:block th:each="item : ${lista}">
    <p th:text="${item.nombre}"></p>
    <p th:text="${item.precio}"></p>
</th:block>
```

## Fragmentos y layouts

Definir un fragmento:

```html
<div th:fragment="content">
    ...
</div>
```

Fragmento parametrizado:

```html
<html th:fragment="layout(content, title)">
```

Insertar / reemplazar:

```html
th:insert="plantilla :: fragmento"    <!-- agrega el fragmento DENTRO del tag actual -->
th:replace="plantilla :: fragmento"   <!-- reemplaza el tag actual por el fragmento -->
```

Diferencia clave: `th:insert` deja el tag contenedor (por ej. tu `<main>`),
`th:replace` lo elimina y pone directamente el contenido del fragmento.

Pasar contenido como parámetro (fragmento anónimo con `~{::selector}`):

```html
<div th:replace="~{layout/main-layout :: layout(~{::content}, ~{::title})}">
```

Esto es lo que le permite a tu layout recibir "el contenido de esta página"
como si fuera un argumento.

Llamar un fragmento desde el controller (Spring):

```java
return "dashboard/dashboard :: content";  // solo el fragmento
return "dashboard/dashboard :: page";     // la página completa (layout + contenido)
```

## Utilidades built-in

```html
${#dates.format(fecha, 'dd/MM/yyyy')}
${#numbers.formatDecimal(valor, 1, 2)}
${#strings.isEmpty(texto)}
${#strings.toUpperCase(texto)}
${#lists.size(lista)}
${#objects.nullSafe(obj, 'default')}
```

## Internacionalización

```html
th:text="#{clave.mensaje}"
```

Requiere un `messages.properties` (o `messages_es.properties`, etc).

## Seguridad (con thymeleaf-extras-springsecurity)

```html
xmlns:sec="http://www.thymeleaf.org/extras/spring-security"

<div sec:authorize="hasRole('ADMIN')">...</div>
<span sec:authentication="name"></span>
```

## Comentarios (no se envían al navegador)

```html
<!--/* Esto es un comentario de Thymeleaf, no aparece en el HTML final */-->
```

## Errores comunes

- `th:insert` vs `th:replace`: si tu `<main>` desaparece del HTML final, es porque usaste `th:replace` en vez de `th:insert` (o viceversa según lo que quieras).
- Fragmentos parametrizados: el orden de los parámetros en la llamada tiene que coincidir con la definición (`layout(content, title)`).
- `th:field` no funciona sin `th:object` en el `<form>` padre.
- Si el layout no encuentra el fragmento, revisá que el nombre de archivo en el selector (`"layout/main-layout"`) coincida con la ruta real dentro de `templates/`.
