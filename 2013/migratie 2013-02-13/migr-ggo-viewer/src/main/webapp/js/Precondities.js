Precondities =
{
	maakPrecondities : function(precondities, aNummer) {
		if (!precondities) return null;
		if (precondities.length == 0) return null;

		var $overzicht = $(_.template(overzichtTemplate, {type: 'overzicht', aNummer : aNummer, titel: "Precondities"}));

		var $sectie = $('<div class="sectie table-sectie"><table summary="Precondities" /></div>');
		$overzicht.find('.inhoud').append($sectie);
		var $table = $sectie.find('table');
	
		for (var i in precondities) {
			var severity = precondities[i].severity.toLowerCase();
			var type = precondities[i].type.toLowerCase();
			var code = empty(precondities[i].code) ? "" : precondities[i].code;
			var omschrijving = precondities[i].omschrijving;
			var herkomstCatNr = ViewerUtils.bepaalHerkomstCategorie(precondities[i].lo3Herkomst);
			var categorie = "Categorie " + herkomstCatNr;
			var herkomstClass = ViewerUtils.bepaalHerkomstClassName(precondities[i].lo3Herkomst, aNummer);
			
			var $row = $(_.template(trPreconditiesTemplate, {thClass: severity, th : type, tdClass: herkomstClass, tdNr: code, tdCat: categorie, tdOms: omschrijving}));
			$row.find('td').on('click', null, herkomstClass, Overzicht.relateer.bind(Overzicht));
			$table.append($row);
		}
		
		return $overzicht;
	}	
}
