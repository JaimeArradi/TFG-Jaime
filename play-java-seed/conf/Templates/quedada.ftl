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

<h2>Información de la quedada ${quedada.id}</h2>
<table>
  <tr>
    <th>Nombre</th>
    <th>Usuario creador</th>
    <th>Hora incial</th>
    <th>Hora final</th>
    <th>Lugar de partida</th>
    <th>Lugar final</th>
    <th>Usuarios confirmados</th>
    <th>Usuarios invitados</th>
    <th>Usuarios recomendados</th>
    <th>Ruta</th>
    <th>Tipo de ruta</th>
    <th>Paradas</th>
  </tr>
  <tr>
    <td>${quedada.name}</td>
    <td><a href="${quedada.getCreador().uri}">${quedada.getCreador().uri}</a></td>
    <td>${quedada.horaInicial}</td>
    <td>${quedada.horaFinal}</td>
    <td>${quedada.lugarPartida}</td>
    <td>${quedada.lugarFinal}</td>
    <td>
        <#list quedada.getUsuariosConfirmados() as confirmados>
        <br><a href="${confirmados.getUri()!}">${confirmados.getUri()!}</a></br>
        </#list>
    </td>
    <td>
         <#list quedada.getUsuariosInvitados() as invitados>
         <br><a href="${invitados.getUri()!}">${invitados.getUri()!}</a></br>
         </#list>
    </td>
    <td>
          <#list quedada.getUsuariosRecomendados() as recomendados>
          <br><a href="${recomendados.getUri()!}">${recomendados.getUri()!}</a></br>
          </#list>
    </td>
    <td><a href="${quedada.getRuta().uri}">${quedada.getRuta().uri}</a></td>
    <td>${quedada.tipo}</td>
    <td>${quedada.paradas}</td>
  </tr>
</table>
</body>
</html>