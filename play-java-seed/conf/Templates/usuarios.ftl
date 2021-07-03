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


<h2>Lista de usuarios</h2>

<table>
    <tr>
            <th>Id de usuario</th>
            <th>URI</th>
          </tr>
    <#list usuarios as usuario>
      <tr>
        <td>${usuario.id}</td>
        <td><a href="${usuario.uri}">${usuario.uri}</a></td>
      </tr>
      </#list>
    </table>

</body>
</html>