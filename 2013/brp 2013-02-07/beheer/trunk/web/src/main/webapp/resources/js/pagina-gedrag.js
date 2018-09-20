/**
 * Zorgt ervoor dat regels geselecteerd kan worden in een tabel.
 */
function initSelecteerbaarTabel() {
	$("table.selecteerbaar :checkbox").hide();

	// Zorgt ervoor dat de selectie status gelijk is aan de checkbox status bij
	// het laden van de pagina
	$("table.selecteerbaar :checkbox").each(function(index, element) {
		var checkbox = $(element);
		if (checkbox.attr("checked") == "checked") {
			checkbox.parents("tr").addClass("actief");
		}
	});

	// Zorgt ervoor dat de regels geselecteerd zijn en dat de verborgen checkbox
	// geselecteerd is
	$("table.selecteerbaar tr").click(function() {
		if ($(this).hasClass("actief")) {
			$(this).removeClass("actief");
			$(this).find(":checkbox").attr("checked", false);
		} else {
			$(this).addClass("actief");
			$(this).find(":checkbox").attr("checked", true);
		}
	});
}

/**
 * Zorgt ervoor dat de achtergrond van een invoerveld oplichten wanneer ze in
 * focus zijn.
 */
function initInvoerveldHighlight() {
	// Zorgt ervoor dat de actieve invoer veld de achtergrond ge-highlight wordt
	$(".formSectie .input").each(function(index, element) {
		$(element).focus(function() {
			$(this).parents(".rij").addClass("actief");
		});
		$(element).blur(function() {
			$(this).parents(".rij").removeClass("actief");
		});
	});

	initVoorkomenEnterToets();
}

/**
 * Zorgt ervoor dat de formulier niet gesubmit wordt wanneer er op de enter
 * toets gedrukt wordt, behalve wanneer een knop aangegeven is als default.
 */
function initVoorkomenEnterToets() {
	$("form input[type=text]").keypress(function(e) {
		if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
			$('input[type=submit].default').click();
			return false;
		} else {
			return true;
		}
	});
}

function initKlikbaarTabelRegel() {
	$(".iconEdit").hide();
	
	//Zorgt ervoor dat er op de regel ge-clicked kan worden
	$("table tr").click(function() {
		var url = $(this).find("a").attr("href");
		
		if (url != null) {
			document.location = url;
		}
	})
}
