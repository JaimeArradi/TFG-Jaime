<!DOCTYPE html>
<meta charset="UTF-8">
<html>
<head>
  <title>/Usuarios/${usuario.name}</title>
</head>


<body>

<h2>Usuarios con id ${usuario.id}</h2>

<table style="width:75%">
  <tr>
    <th>Nombre</th>
    <th>Edad</th>
    <th>Sexo</th>
    <th>Biografia</th>
    <th>Nivel de conduccion</th>
    <th>Terreno preferido</th>
    <th>Carnet de moto</th>
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
    <td>${usuario.edad}</td>
  </tr>
</table>
</body>
</html>