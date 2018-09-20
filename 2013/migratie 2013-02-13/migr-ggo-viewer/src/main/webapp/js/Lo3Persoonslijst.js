var soortLo3PlConversie = '';

Lo3Persoonslijst =
{
	maakLo3Persoonslijst : function(lo3Persoonslijst, soort) {
		if (!lo3Persoonslijst) return null;

		soortLo3PlConversie = soort;
		var aNummer = lo3Persoonslijst.actueelAdministratienummer;
		
		var $overzicht = $(_.template(overzichtTemplate, {type: 'overzicht', classes : "gesloten", titel: soortLo3PlConversie}));
		var $secties = $overzicht.find('.inhoud');

		this.maakTabel($secties, 1, " - Persoon", lo3Persoonslijst.persoonStapel, aNummer, soortLo3PlConversie);

		for (var j in lo3Persoonslijst.ouder1Stapels) {
			this.maakTabel($secties, 2, " - Ouder 1", lo3Persoonslijst.ouder1Stapels[j], aNummer, soortLo3PlConversie);
		}
		for (var j in lo3Persoonslijst.ouder2Stapels) {
			this.maakTabel($secties, 3, " - Ouder 2", lo3Persoonslijst.ouder2Stapels[j], aNummer, soortLo3PlConversie);
		}
		
		for (var j in lo3Persoonslijst.nationaliteitStapels) {
			this.maakTabel($secties, 4, " - Nationaliteit", lo3Persoonslijst.nationaliteitStapels[j], aNummer, soortLo3PlConversie);
		}
		
		for (var j in lo3Persoonslijst.huwelijkOfGpStapels) {
			this.maakTabel($secties, 5, " - Huwelijk / GP", lo3Persoonslijst.huwelijkOfGpStapels[j], aNummer, soortLo3PlConversie);
		}
		
		this.maakTabel($secties, 6, " - Overlijden", lo3Persoonslijst.overlijdenStapel, aNummer, soortLo3PlConversie);
		
		this.maakTabel($secties, 7, " - Inschrijving", lo3Persoonslijst.inschrijvingStapel, aNummer, soortLo3PlConversie);
		this.maakTabel($secties, 8, " - Verblijfplaats", lo3Persoonslijst.verblijfplaatsStapel, aNummer, soortLo3PlConversie);
		
		for (var j in lo3Persoonslijst.kindStapels) {
			this.maakTabel($secties, 9, " - Kind", lo3Persoonslijst.kindStapels[j], aNummer, soortLo3PlConversie);
		}

		this.maakTabel($secties, 10, " - Verblijfstitel", lo3Persoonslijst.verblijfstitelStapel, aNummer, soortLo3PlConversie);
		this.maakTabel($secties, 11, " - Gezagsverhouding", lo3Persoonslijst.gezagsverhoudingStapel, aNummer, soortLo3PlConversie);

		for (var j in lo3Persoonslijst.reisdocumentStapels) {
			this.maakTabel($secties, 12, " - Reisdocument", lo3Persoonslijst.reisdocumentStapels[j], aNummer, soortLo3PlConversie);
		}

		this.maakTabel($secties, 13, " - Kiesrecht", lo3Persoonslijst.kiesrechtStapel, aNummer, soortLo3PlConversie);
	
		return $overzicht;
	},

	maakTabel : function($el, catNr, titel, stapel, aNummer, soort) {
		if (stapel != null) {
			var voorkomens = stapel.categorieen;
			for (var i = voorkomens.length -1; i >= 0; i--) {
				var historisch = i < voorkomens.length - 1;
				var historischClass = historisch ? " historisch" : "";
				var ongeldigClass = this.bepaalOngeldig(voorkomens[i]) ? " ongeldig" : "";

			    var sectieTitel = this.maakCategorieNrLabel(catNr, voorkomens, historisch) + titel;
			    var $sectie = $(_.template(overzichtTemplate, {type: 'sectie', classes: "gesloten" + historischClass + ongeldigClass, titel : sectieTitel}));
	            var $table = $('<table summary="' + sectieTitel + '" />');
	            $sectie.find('.inhoud').append($table);

	            var keys = this.vindGebruikteVelden(stapel.categorieen);
				this.maakTabelHeader($table, keys);
				this.maakTabelRegel($table, keys, voorkomens[i], aNummer, soort);
				$el.append($sectie);
			}
		}
	},

	maakCategorieNrLabel : function(catNr, voorkomens, historisch) {
	    if (historisch) {
            catNr = catNr + 50;
        }
        else if (catNr < 10) {
            catNr = '0' + catNr;
        }
	    return catNr;
	},

	bepaalOngeldig: function(voorkomen) {
		var ongeldig = voorkomen.historie['indicatieOnjuist'];
		ongeldig = ongeldig ? ongeldig.code == "O" : false;
		return ongeldig;
	},

	vindGebruikteVelden : function (voorkomens) {
		var keys = [];
		for (var key in Lo3ElementMap) {
			for (var i in voorkomens) {
				if (!empty(voorkomens[i].inhoud[key]) || !empty(voorkomens[i].documentatie[key]) || !empty(voorkomens[i].historie.nullHistorie ? null : voorkomens[i].historie[key])) {
					if ($.inArray(key, keys) == -1) keys.push(key);
				}
			}
		}
		return keys;
	},

	maakTabelHeader : function ($table, keys) {
		var regel = '<tr class="header">';
		for (var i in keys) {
			var clss = keys[i];
			var label = Lo3ElementMap[keys[i]];
			var code = label.substr(0,5);
			label = label.substr(6);
			regel = regel + _.template(thTemplate, {clss: clss, label: label, code: code});
		}
		$table.append(regel + '</tr>\r\n');
	},

	maakTabelRegel : function ($table, keys, voorkomen, aNummer, soort) {
		var herkomstClass = ViewerUtils.bepaalHerkomstLo3Class(voorkomen, aNummer);

		var regel = '<tr class="' + herkomstClass + ' ' + soort.toLowerCase() + '">';

		for (var i in keys) {
			var key = keys[i];
			var val = voorkomen.inhoud[key];
			if (val == null) val = voorkomen.documentatie[key];
			if (val == null) val = voorkomen.historie.nullHistorie ? null : voorkomen.historie[key];

			val = ViewerUtils.gebruikVeldBijMatch(/Code|aanduiding|gemeenteInschrijving|gemeenteAkte|gemeenteDocument|indicatieDocument|functieAdres|aangifteAdreshouding|indicatieOnjuist|soortHuwelijk|soortVerbintenis|signalering|landVanwaarIngeschreven|landWaarnaarVertrokken|indicatieGezagMinderjarige/, 'code', key, val);
			val = ViewerUtils.gebruikVeldBijMatch(/datum|aanvangAdreshouding|familierechtelijkeBetrekking|vertrekUitNederland/, 'datum', key, val);
			val = ViewerUtils.gebruikVeldBijMatch(/soortReisdocument|soortNederlandsReisdocument/, 'soort', key, val);
			val = ViewerUtils.gebruikVeldBijMatch(/huisnummer/, 'nummer', key, val);
			val = ViewerUtils.gebruikVeldBijMatch(/autoriteitVanAfgifteNederlandsReisdocument/, 'autoriteit', key, val);
			val = ViewerUtils.gebruikVeldBijMatch(/lengteHouder/, 'lengte', key, val);

			if (val == null || val == undefined) val = "";
			
			var title = val;
			val = _.escape(val);
			if (key == 'aNummer') val = '<a href="#' + val + '">' + val +'</a>';
			regel = regel + _.template(tdTemplate, {key: key, title: title, val: val});
		}

		var $regel = $(regel + '</tr>\r\n');
		$regel.find('td').on('click', null, herkomstClass, Overzicht.relateer.bind(Overzicht));
		$regel.find('a').on('click', null, null, Overzicht.relateerPersonen.bind(Overzicht));

		$table.append($regel);
	}
}
