<!DOCTYPE html>
<meta charset="UTF-8">
<html>
<head>
    <style>
    table {
      font-family: arial, sans-serif;
      border-collapse: collapse;
      width: 15%;
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


<h2>Lista de valoraciones de una ruta</h2>

<table>
    <tr>
            <th>Id de la valoración</th>
            <th>URI</th>
          </tr>
    <#list valoraciones as valoracion>
      <tr>
        <td>${valoracion.id}</td>
        <td><a href="${valoracion.uri}">${valoracion.uri}</a></td>
      </tr>
      </#list>
    </table>

</body>
</html>