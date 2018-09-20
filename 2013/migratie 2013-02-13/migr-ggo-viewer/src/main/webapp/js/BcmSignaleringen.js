BcmSignaleringen =
{
	maakBcmSignaleringen : function(bcmSignaleringen, aNummer) {
		if (bcmSignaleringen == null || bcmSignaleringen.length == 0) return null;

		var $overzicht = $(_.template(overzichtTemplate, {type: 'overzicht', aNummer : aNummer, titel: "BCM Signaleringen"}));

		var $sectie = $('<div class="sectie table-sectie"><table summary="BCM Signaleringen" /></div>');
		$overzicht.find('.inhoud').append($sectie);
		var $table = $sectie.find('table');
	
		for (var i in bcmSignaleringen) {
			var code = bcmSignaleringen[i].code
			var omschrijving = bcmSignaleringen[i].omschrijving;
			var herkomstClass = ViewerUtils.bepaalHerkomstClassName(bcmSignaleringen[i].lo3Herkomst, aNummer);

			ViewerUtils.addLineToTable($table, {th : code, tdClass: herkomstClass, td: omschrijving}, Overzicht.relateer.bind(Overzicht), herkomstClass);
		}
		
		return $overzicht;
	}
}
