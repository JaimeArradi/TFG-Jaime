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


<h2>Lista de motos</h2>

<table>
    <tr>
            <th>Id de la moto</th>
            <th>URI</th>
          </tr>
    <#list motos as moto>
      <tr>
        <td>${moto.id}</td>
        <td><a href="${moto.uri}">${moto.uri}</a></td>
      </tr>
      </#list>
    </table>

</body>
</html>