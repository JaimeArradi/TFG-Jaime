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


<h2>Lista de invitaciones para una quedada</h2>

<table>
    <tr>
            <th>Id de la valoración</th>
            <th>URI</th>
          </tr>
    <#list invitaciones as invitacion>
      <tr>
        <td>${invitacion.idInvitacion}</td>
        <td><a href="${invitacion.uriInvitacion}">${invitacion.uriInvitacion}</a></td>
      </tr>
      </#list>
    </table>

</body>
</html>