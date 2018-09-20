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

function initKlikbaarTabelRegel() {
	//Zorgt ervoor dat er op de regel ge-clicked kan worden
	$("table tr").click(function() {
		var url = $(this).find("a").attr("href");
		
		if (url != null) {
			document.location = url;
		}
	})
}
