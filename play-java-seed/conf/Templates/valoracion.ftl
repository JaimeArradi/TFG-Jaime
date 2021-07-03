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

<h2>Información de la valoración ${valoracion.id}</h2>
<table>
  <tr>
    <th>Usuario</th>
    <th>Ruta</th>
    <th>Comentario</th>
    <th>Puntuación sobre 5</th>
  </tr>
  <tr>
    <td><a href="${valoracion.getUsuario().uri}">${valoracion.getUsuario().uri}</a></td>
    <td><a href="${valoracion.getRuta().uri}">${valoracion.getRuta().uri}</a></td>
    <td>${valoracion.comentario}</td>
    <td>${valoracion.puntuacion}</td>
  </tr>
</table>
</body>
</html>