Meldingen =
{
	maakMeldingen : function(foutRegels, persoonId, logFoutRegels) {
		if (!foutRegels) return null;
		if (foutRegels.length == 0) return null;

		var $overzicht = $(_.template(categorieTemplate, {type: 'overzicht', classes: 'meldingen', titel: "Meldingen bij conversie (GBA > BRP)"}));

		var $sectie = $('<div class="sectie table-sectie"></div>');
		ViewerUtils.schrijfFoutRegels($sectie, logFoutRegels, "PRECONDITIES");
		ViewerUtils.schrijfFoutRegels($sectie, logFoutRegels, "BCM");
		$sectie.append('<table summary="Meldingen" id="meldingtable_' + persoonId +'" />');

		$overzicht.find('.inhoud').append($sectie);
		var $table = $sectie.find('table');

        var $header = $(_.template(trMeldingenHeaderTemplate, {}));
        $table.append($header);
        $table.append('<tbody>');

        var severities = [];

		for (var i in foutRegels) {

			var type = empty(foutRegels[i].type) ? '' : foutRegels[i].type;
			var code = empty(foutRegels[i].code) ? '' : foutRegels[i].code;

			var severity = empty(foutRegels[i].severity) ? '' : foutRegels[i].severity.toLowerCase();

			// Meldingen aanpassen t.b.v. begrijpelijkheid gebruiker
			if (severity == "warning") {
				continue; // geen warning precondities tonen
			} else if (severity == "info") {
				type = "Info"; //
			} else if (severity == "removed") {
				type = "Verwijderd";
				severity = "warning";
			}

			severities[severity] = true;

			var omschrijving = foutRegels[i].omschrijving;
			var catLabel = empty(foutRegels[i].herkomst) || empty(foutRegels[i].herkomst.categorieNr) || empty(foutRegels[i].herkomst.label) 
				? "Onbekend" 
				: ViewerUtils.formatCategorieNr(foutRegels[i].herkomst.categorieNr) + " " + foutRegels[i].herkomst.label;
			var herkomstClass = ViewerUtils.bepaalHerkomstClassName(foutRegels[i].herkomst, persoonId);

			var $row = $(_.template(trMeldingenTemplate, {severity: severity, tdType : type, trClass: 'meldingen ' + herkomstClass, tdNr: code, tdCat: catLabel, tdOms: omschrijving}));
			$row.find('.checkbox').on('click', null, herkomstClass, this.relateer.bind(this));
			$table.append($row);
		}
		$table.append('</tbody>');
		
		this.maakLegenda($sectie, severities);

		return $overzicht;
	},

	maakLegenda : function ($sectie, severities) {
		$sectie.append('<div class="legenda">');
		if (severities["error"]) $sectie.append('<div><span class="severity severity_error" />= Voldoet niet aan Baseline 1.</div>');
		if (severities["warning"]) $sectie.append('<div><span class="severity severity_warning" />= Onjust voorkomen niet overgezet naar BRP omdat deze niet aan Lo3.8 regels voldoet.</div>');
		if (severities["info"]) $sectie.append('<div><span class="severity severity_info" />= Bijzonderheid bij conversie.</div>');
		$sectie.append('</div>');
	},

	relateer : function (event) {
		//relateer en highlight
		Overzicht.relateer(event);

		//scroll to first highlighted
		if (ViewerUtils.isHighlighted($(event.target))) {
			var rowpos = $('.lo3Column div.overzicht.inhoud .highlight:first').position().top;
			$('.lo3Column div.overzicht.inhoud').scrollTop($('.lo3Column div.overzicht.inhoud').scrollTop() + rowpos - $('.lo3Column div.overzicht.inhoud').position().top);
		}
	}
};