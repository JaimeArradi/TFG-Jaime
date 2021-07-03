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

<h2>Información de la moto ${moto.id}</h2>


<table>
<table style="width:25%">
  <tr>
    <th>Marca</th>
    <th>Modelo</th>
    <th>Estilo</th>
    <th>Potencia (cv)</th>
  </tr>
  <tr>
    <td>${moto.marca}</td>
    <td>${moto.modelo}</td>
    <td>${moto.estilo}</td>
    <td>${moto.potencia}</td>
  </tr>
</table>
</body>
</html>