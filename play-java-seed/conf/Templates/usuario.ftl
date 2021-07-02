<!DOCTYPE html>
<meta charset="UTF-8">
<html>
<head>
  <title>/Usuarios/${usuario.name}</title>
</head>


<body>

<h2>Usuarios registrados</h2>

<table style="width:100%">
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
    <td><a href="${usuario.getMoto().uri}">${usuario.getMoto().uri}</a></td>
    <td>${usuario.edad}</td>
  </tr>
  <tr>
    <td>Eve</td>
    <td>Jackson</td>
    <td>94</td>
  </tr>
  <tr>
    <td>John</td>
    <td>Doe</td>
    <td>80</td>
  </tr>
</table>
</body>
</html>