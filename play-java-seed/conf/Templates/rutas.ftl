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


<h2>Lista de rutas</h2>

<table>
    <tr>
            <th>Id de la ruta</th>
            <th>URI</th>
          </tr>
    <#list rutas as ruta>
      <tr>
        <td>${ruta.id}</td>
        <td><a href="${ruta.uri}">${ruta.uri}</a></td>
      </tr>
      </#list>
    </table>

</body>
</html>