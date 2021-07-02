<!DOCTYPE html>
<meta charset="UTF-8">
<html>
<head>
  <title>/Invitaciones</title>

</head>
<body>

<div> <b> La lista de usuarios es: </b><br> <br>

    <table style="width:5%">
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
    </div>



</body>
</html>