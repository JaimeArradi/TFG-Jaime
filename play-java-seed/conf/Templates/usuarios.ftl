<!DOCTYPE html>
<meta charset="UTF-8">
<html>
<head>
  <title>/Laboratorios</title>

</head>
<body>

<div> <b> La lista de laboratorios es: </b><br> <br>

    <table style="width:100%">
    <tr>
     <th>Nombre</th>
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
    </div>



</body>
</html>