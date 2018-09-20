<%@ page isELIgnored="false" %>
<html>
	<head>
		<title>Object Sleutels maken</title>
		<script type="text/javascript">
			function focus() {
				var sleutelField = document.getElementById("sleutel");
				if (sleutelField.value != "") {
					sleutelField.select();
				}
			}
		</script>
	</head>
	<body onLoad="focus()">
		<form method="post" action="maakSleutel.do">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="150">Persoon id:</td>
					<td><input type="text" name="persoonId" value="${persoonId}" /></td>
				</tr>
				<tr>
					<td>Partij code:</td>
					<td><input type="text" name="partijCode" value="${not empty partijCode ? partijCode : '36101'}" /></td>
				</tr>
<!--
Timestamp meegeven zit niet in service interface. 
				<tr>
					<td>Timestamp:</td>
					<td><input type="text" name="timestamp" /></td>
				</tr>
-->
				<tr colspan="2">
					<td><input type="submit" value="Maak sleutel" /></td>
				</tr>
			</table>
			<br />
			<br />
			Gemaakte sleutel: <input size="50" type="text" id="sleutel" name="sleutel" value="${sleutel}" />
			<br />
			<br />
			<div style="color: red;">${error}</div>
		</form>
	</body>
</html>
