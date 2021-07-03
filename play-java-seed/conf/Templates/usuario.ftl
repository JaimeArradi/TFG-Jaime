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

<h2>Información del usuario ${usuario.id}</h2>
<table>
  <tr>
    <th>Nombre</th>
    <th>Edad</th>
    <th>Sexo</th>
    <th>Biografía</th>
    <th>Nivel de conducción</th>
    <th>Terreno preferído</th>
    <th>Carné de moto</th>
    <th>Modelo de moto</th>
    <th>Intercomunicador</th>
    <th>Invitaciones</th>
  </tr>
  <tr>
    <td>${usuario.name}</td>
    <td>${usuario.edad}</td>
    <td>${usuario.sexo}</td>
    <td>${usuario.bio}</td>
    <td>${usuario.nivel}</td>
    <td>${usuario.terreno}</td>
    <td>${usuario.carne}</td>
    <td><a href="${usuario.getMoto().uri}">${usuario.getMoto().uri}</a></td>
    <td>${usuario.intercomunicador?string('Si', 'No')}</td>
    <td>
    <#list usuario.getInvitacionShort() as invitacion>
    <br><a href="${invitacion.getUriInvitacion()!}">${invitacion.getUriInvitacion()!}</a></br>
    </#list>
    </td>

  </tr>
</table>
</body>
</html>