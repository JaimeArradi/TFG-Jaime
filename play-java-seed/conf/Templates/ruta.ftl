<!DOCTYPE html>
<meta charset="UTF-8">
<html>
<head>
      <style>
      table {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 100%;
      }

      td, th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
      }

      tr:nth-child(even) {
        background-color: #dddddd;
      }
      </style>
</head>


<body>

<h2>Información de la ruta ${ruta.id}</h2>
<table>
  <tr>
    <th>Nombre</th>
    <th>Recorrido</th>
    <th>km totales</th>
    <th>Estado del asfaslto</th>
    <th>Tipo de terreno</th>
    <th>Dificultad sobre 5</th>
    <th>Valoraciones</th>
    <th>Duración total en minutos</th>
    <th>Tráfico habitual</th>
  </tr>
  <tr>
    <td>${ruta.name}</td>
    <td>${ruta.recorrido}</td>
    <td>${ruta.km}</td>
    <td>${ruta.estadoAsfalto}</td>
    <td>${ruta.terreno}</td>
    <td>${ruta.dificultad}</td>
    <td>
    <#list ruta.getValoraciones() as valoracion>
        <br><a href="${valoracion.getUri()!}">${valoracion.getUri()!}</a></br>
    </#list>
    </td>
    <td>${ruta.duracion}</td>
    <td>${ruta.trafico}</td>
  </tr>
</table>
</body>
</html>