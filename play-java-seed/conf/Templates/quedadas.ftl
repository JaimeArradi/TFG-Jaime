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


<h2>Lista de quedadas</h2>

<table>
    <tr>
            <th>Id de la quedada</th>
            <th>URI</th>
          </tr>
    <#list quedadas as quedada>
      <tr>
        <td>${quedada.id}</td>
        <td><a href="${quedada.uri}">${quedada.uri}</a></td>
      </tr>
      </#list>
    </table>
</body>
</html>