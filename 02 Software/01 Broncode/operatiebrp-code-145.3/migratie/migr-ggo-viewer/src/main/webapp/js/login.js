$(document).ready(function() {

	var $username = $('#gebruikersnaam');
	var $password = $('#wachtwoord');

	$('#loginForm').submit(function() {

		var username = $('#gebruikersnaam').val();
		var password = $('#wachtwoord').val();

		if (!username && !password) {
			toonMelding ('De volgende velden zijn verplicht:', ['Gebruikersnaam', 'Wachtwoord']);
			$username.addClass('err');
			$password.addClass('err');
			return false;
		} else if (!username) {
			toonMelding ('Het volgende veld is verplicht:', ['Gebruikersnaam']);
			$username.addClass('err');
			return false;
		} else if (!password) {
			toonMelding ('Het volgende veld is verplicht:', ['Wachtwoord']);
			$password.addClass('err');
			return false;
		}
		
		return true;
	});

	$username.keydown(function (event) {
		$username.removeClass('err');
	});

	$password.keydown(function (event) {
		$password.removeClass('err');
	});
});


function toonMelding (titel, items) {
	var omschrijving = '<ul>';
	for (var i in items) {
		omschrijving += '<li>' + items[i] + '</li>';
	}

	$errorcontainer = $('#errorcontainer');
	$errorcontainer.empty();
	$errorcontainer.append('<div class="mod message_err"><h2>' + titel + '</h2><p>' + omschrijving +'</div>');
}