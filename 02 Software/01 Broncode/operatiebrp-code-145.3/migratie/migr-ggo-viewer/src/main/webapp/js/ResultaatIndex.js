ResultaatIndex =
{
	maakIndex : function(indexRegels, persoonId, persoonTitel) {
		if (!indexRegels) return null;
		if (indexRegels.length == 0) return null;
		
		var $plIndex = $(_.template(resultaatIndexTemplate, {persoonId: persoonId, persoonTitel: persoonTitel}));
		$plIndex.find('a').on('click', null, {persoonId: persoonId, clickType: null}, this.springNaar.bind(this));
		
		var $sectie = $('<div class="sectie table-sectie"><table summary="Overzicht resultaten" /></div>');
		$plIndex.find('.inhoud').append($sectie);
		
		var $table = $sectie.find('table');
		for (var i in indexRegels) {
			var omschrijving = empty(indexRegels[i].omschrijving) ? "" : indexRegels[i].omschrijving;
			omschrijving = ViewerUtils.unescape(omschrijving);
			var aantal = empty(indexRegels[i].aantalMeldingen) ? "" : indexRegels[i].aantalMeldingen;
			var resultaat = empty(indexRegels[i].resultaat) ? "" : indexRegels[i].resultaat;
			var resultaatClass = this.bepaalResultaatClass(resultaat, aantal);
			
			var $row = $(_.template(trResultaatIndexTemplate, {tdOms : omschrijving, tdType : this.bepaalClickType(resultaat), tdAantal: aantal, resultaatClass: resultaatClass, persoonId: persoonId}));
			$table.append($row);
			
			$row.find('a').on('click', null, {persoonId: persoonId, clickType: this.bepaalClickType(resultaat)}, this.springNaar.bind(this));
		}
		
		return $plIndex;
	},
	
	bepaalResultaatClass : function(resultaat, aantal) {
		var resultaatClass = "";
		if ("INLEZEN_GBA_PL_NIET_GESLAAGD" == resultaat || "CONVERSIE_NIET_GESLAAGD" == resultaat || "TERUGCONVERSIE_NIET_GESLAAGD" == resultaat) {
			resultaatClass = "resultaatNOK";
		} else if ("CONVERSIE_GESLAAGD" == resultaat || "TERUGCONVERSIE_GESLAAGD" == resultaat) {
			if (aantal > 0) {
				resultaatClass = "resultaatOKMsg";
			} else {
				resultaatClass = "resultaatOK";
			}
		}
		return resultaatClass;
	},
	
	bepaalClickType : function(resultaat) {
		var clickType = "meldingen";
		if (resultaat.substring(0, 5) === "TERUG") {
			clickType = "verschillen";
		}
		return clickType;
	},
	
	springNaar : function(event) {		
		if (!event.data) return; // negeer o.a. lege string (geen classes)
		var persoonId = event.data.persoonId;
		var clickType = event.data.clickType;
		
		//close all pl
		var $personen = $('#content').children('.persoon');
		$personen.find('.overzicht').addClass('gesloten');
		$personen.find('.supersectie').addClass('gesloten');
		$personen.find('.sectie').not('.actiePopup .sectie').addClass('gesloten');
		
		//open first level of the selected pl
		$('#' + persoonId).find('.persoonslijsten .overzicht').removeClass('gesloten');
		
		//open melding or verschil based on clickType
		if (!clickType) {
			$('#' + persoonId).find('.controles .overzicht').removeClass('gesloten');
		} else {
			$('#' + persoonId).find('.controles .overzicht.' + clickType).removeClass('gesloten');
		}
		
		// now this part stops the click from propagating so the Overzicht.klapOpenOfDicht won't be executed.
	    if (!event) var event = window.event;
	    event.cancelBubble = true;
	    if (event.stopPropagation) event.stopPropagation();
	},
	
	maakSelectie : function (persoonslijstGroepen) {
		var $inhoud = this.maakSelectieInhoud(persoonslijstGroepen);
		$resultaatIndex = $("#resultaatIndex");
		$resultaatIndex.prepend("<h1>Meerdere resultaten</h1>");
		Overzicht.plaats($inhoud, $resultaatIndex.find(".resultaat_index"));		
	},
	
	maakSelectieInhoud : function (persoonslijstGroepen) {
		var $plIndex = $(_.template(meerdereResultatenTemplate, {titel: 'Selecteer een PL:'}));
		var $sectie = $('<div class="sectie table-sectie"><table summary="Overzicht resultaten" /></div>');
		$plIndex.find('.inhoud').append($sectie);
		
		var $table = $sectie.find('table');

		
		for (var i in persoonslijstGroepen) {
			var persoonslijstGroep = persoonslijstGroepen[i];
			var persoonTitel = ViewerUtils.bepaalPersoonTitel(persoonslijstGroep.aNummer, persoonslijstGroep.volledigeNaam);
			
			var $row = $('<tr><th><a href="#">' + persoonTitel + '</a></th></tr>');
			$row.find('a').on('click', null, persoonslijstGroep, this.toonSelectie.bind(this));
			$table.append($row);
		}
		
		return $plIndex;
	},

	toonSelectie : function (event) {
		Overzicht.clear();
		Overzicht.maakOverzicht(event.data);
	}
};