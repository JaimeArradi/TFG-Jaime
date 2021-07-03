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

<h2>Información de la invitacion ${invitacion.idInvitacion}</h2>


<table>
<table style="width:25%">
  <tr>
    <th>Usuario</th>
    <th>Quedada</th>
  </tr>
  <tr>
    <td><a href="${invitacion.getUsuario().uri}">${invitacion.getUsuario().uri}</a></td>
    <td><a href="${invitacion.getQuedada().uri}">${invitacion.getQuedada().uri}</a></td>
  </tr>
</table>
</body>
</html>