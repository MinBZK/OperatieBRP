BrpPersoonslijst =
{
	maakBrpPersoonslijst : function(brpPersoonslijst) {
		if (!brpPersoonslijst) return null;

		var aNummer = brpPersoonslijst.actueelAdministratienummer;
		var $overzicht = $(_.template(overzichtTemplate, {type: 'overzicht', classes : "gesloten", titel: "BRP"}));
		var $secties = $overzicht.find('.inhoud');

		// Indelen in supersecties is hier naar eigen inzicht gedaan

		var $persoonSectie = this.maakSupersectie($secties, "Persoon");
		this.maakTabel($persoonSectie, "Identificatienummer", brpPersoonslijst.identificatienummerStapel, aNummer);
		this.maakTabel($persoonSectie, "Aanschrijving", brpPersoonslijst.aanschrijvingStapel, aNummer);
		this.maakTabel($persoonSectie, "Samengestelde naam", brpPersoonslijst.samengesteldeNaamStapel, aNummer);

		for (var i in brpPersoonslijst.voornaamStapels) {
			this.maakTabel($persoonSectie, "Voornaam", brpPersoonslijst.voornaamStapels[i], aNummer);
		}

		for (var i in brpPersoonslijst.geslachtsnaamcomponentStapels) {
			this.maakTabel($persoonSectie, "Geslachtsnaam", brpPersoonslijst.geslachtsnaamcomponentStapels[i], aNummer);
		}

		this.maakTabel($persoonSectie, "Adres", brpPersoonslijst.adresStapel, aNummer);
		this.maakTabel($persoonSectie, "Geslachtsaanduiding", brpPersoonslijst.geslachtsaanduidingStapel, aNummer);
		this.maakTabel($persoonSectie, "Geboorte", brpPersoonslijst.geboorteStapel, aNummer);
		this.maakTabel($persoonSectie, "Overlijden", brpPersoonslijst.overlijdenStapel, aNummer);

		var $nationaliteitSectie = this.maakSupersectie($secties, "Nationaliteit");
		for (var i in brpPersoonslijst.nationaliteitStapels) {
			this.maakTabel($nationaliteitSectie, "Nationaliteit", brpPersoonslijst.nationaliteitStapels[i], aNummer);
		}

		this.maakTabel($nationaliteitSectie, "Verblijfsrecht", brpPersoonslijst.verblijfsrechtStapel, aNummer);
		this.maakTabel($nationaliteitSectie, "Immigratie", brpPersoonslijst.immigratieStapel, aNummer);
		this.maakTabel($nationaliteitSectie, "Uitsluiting Nederlands kiesrecht", brpPersoonslijst.uitsluitingNederlandsKiesrechtStapel, aNummer);
		this.maakTabel($nationaliteitSectie, "Europese verkiezingen", brpPersoonslijst.europeseVerkiezingenStapel, aNummer);

		var $reisdocumentSectie = this.maakSupersectie($secties, "Reisdocument");        
		for (var i in brpPersoonslijst.reisdocumentStapels) {
            this.maakTabel($reisdocumentSectie, "Reisdocument", brpPersoonslijst.reisdocumentStapels[i], aNummer);
        }
        
		var $bijhoudingSectie = this.maakSupersectie($secties, "Bijhouding");
		this.maakTabel($bijhoudingSectie, "Bijhoudingsgemeente", brpPersoonslijst.bijhoudingsgemeenteStapel, aNummer);
		this.maakTabel($bijhoudingSectie, "Bijhoudingsverantwoordelijkheid", brpPersoonslijst.bijhoudingsverantwoordelijkheidStapel, aNummer);
		this.maakTabel($bijhoudingSectie, "Inschrijving", brpPersoonslijst.inschrijvingStapel, aNummer);
		this.maakTabel($bijhoudingSectie, "Persoonskaart", brpPersoonslijst.persoonskaartStapel, aNummer);
		this.maakTabel($bijhoudingSectie, "Afgeleid Administratief", brpPersoonslijst.afgeleidAdministratiefStapel, aNummer);
		this.maakTabel($bijhoudingSectie, "Opschorting", brpPersoonslijst.opschortingStapel, aNummer);

		var $indicatiesSectie = this.maakSupersectie($secties, "Indicaties");
		this.maakTabel($indicatiesSectie, "Geprivilegieerde indicatie", brpPersoonslijst.geprivilegieerdeIndicatieStapel, aNummer);
		this.maakTabel($indicatiesSectie, "Statenloos", brpPersoonslijst.statenloosIndicatieStapel, aNummer);
		this.maakTabel($indicatiesSectie, "Behandeld als Nederlander", brpPersoonslijst.behandeldAlsNederlanderIndicatieStapel, aNummer);
		this.maakTabel($indicatiesSectie, "Vastgesteld niet-Nederlander", brpPersoonslijst.vastgesteldNietNederlanderIndicatieStapel, aNummer);
		this.maakTabel($indicatiesSectie, "Bezit buitenlands reisdocument", brpPersoonslijst.bezitBuitenlandsReisdocumentIndicatieStapel, aNummer);
		this.maakTabel($indicatiesSectie, "Onder curatele", brpPersoonslijst.onderCurateleIndicatieStapel, aNummer);
		this.maakTabel($indicatiesSectie, "Derde heeft gezag", brpPersoonslijst.derdeHeeftGezagIndicatieStapel, aNummer);
		this.maakTabel($indicatiesSectie, "Belemmering verstrekking reisdocument", brpPersoonslijst.belemmeringVerstrekkingReisdocumentIndicatieStapel, aNummer);
		this.maakTabel($indicatiesSectie, "Verstrekkingsbeperking", brpPersoonslijst.verstrekkingsbeperkingStapel, aNummer);
		
		
		return $overzicht;
	},

	maakSupersectie : function ($el, titel) {
		var $supersectie = $(_.template(overzichtTemplate, {type: 'supersectie', classes: "gesloten", titel : titel}));
		$el.append($supersectie);

		return $supersectie.find('.inhoud');
	},

	maakTabel : function($el, titel, stapel, aNummer) {
		if (stapel != null) {
			var voorkomens = stapel.groepen;

			for (var i = voorkomens.length -1; i >= 0; i--) {
    			var $sectie = $(_.template(overzichtTemplate, {type: 'sectie', classes: "gesloten", titel : titel }));
    			var $table = $('<table summary="' + titel + '" />');
    			$sectie.find('.inhoud').append($table);
    			var keys = this.vindGebruikteVelden(stapel.groepen);
				var herkomstClass = ViewerUtils.bepaalHerkomstClass(voorkomens[i], aNummer);
				this.maakTabelHeader($table, keys, herkomstClass);
				var $actieRegels = BrpActie.maakTabelRegelActies($table, titel, voorkomens[i], i, aNummer);
				this.maakTabelRegel($table, keys, voorkomens[i], $actieRegels, herkomstClass);
                $el.append($sectie);
			}
		}
	},

	vindGebruikteVelden: function(voorkomens) {
		var keys = [];
		for (var i in voorkomens) {
			for (var key in voorkomens[i].inhoud) {
				if ($.inArray(key, keys) == -1 && !empty(voorkomens[i].inhoud[key])) keys.push(key);
			}
			if ((!empty(voorkomens[i].historie.nullHistorie ? null : voorkomens[i].historie['datumAanvangGeldigheid'])
			        || !empty(voorkomens[i].historie.nullHistorie ? null : voorkomens[i].historie['datumEindeGeldigheid']))
			        && $.inArray('geldigheid', keys) == -1
			        //FIXME in conversie? Omdat er een 0 in het lo3 model gestopt wordt (cat 07) komt die ook 
                    //in het brp model. Terwijl geldigheid daar niet van toepassing is!
                    && (voorkomens[i].actieInhoud == null
                            || !$.isPlainObject(voorkomens[i].actieInhoud)
                            || voorkomens[i].actieInhoud.lo3Herkomst == null
                            || !$.isPlainObject(voorkomens[i].actieInhoud.lo3Herkomst)
                            || !(voorkomens[i].actieInhoud.lo3Herkomst.categorie == 'CATEGORIE_07'))) {
			    keys.push('geldigheid');
			}
		}
		// Altijd registratie data tonen, zit in voorkomens[i].historie
		keys.push('registratie');

		return keys;
	},

	maakTabelHeader: function($table, keys, herkomstClass) {
		var regel = '<tr class="header ' + herkomstClass + '">';

		for (var i in keys) {
			var clss =keys[i];
			var title = ViewerUtils.maakNaamUitKey(keys[i]);
			regel = regel + _.template(thTemplate, {clss: clss, code: title});
		}
		// Altijd acties kolom tonen
		regel = regel + _.template(thTemplate, {clss: 'acties', code: 'acties'});
		
		$table.append(regel + '</tr>\r\n');
	},
	
	maakTabelRegel : function($table, keys, voorkomen, $actieRegels, herkomstClass) {
		var regel = '<tr class="' + herkomstClass + '">';

		for (var i in keys) {
			var key = keys[i];
			var val = voorkomen.inhoud[key];
			if (val == null) val = voorkomen.historie[key];

			// Match de uitzonderingen eerst. Daarna falen de algemenere matches vanzelf omdat 'val' al een waarde heeft.
			val = ViewerUtils.gebruikVeldBijMatch(/gemeenteCode|gemeentePKCode/, 'formattedStringCode', key, val);
			var tempVal = ViewerUtils.gebruikVeldBijMatch(/Code|redenEinde|redenOntbreken|^soort$|autoriteitVanAfgifte|landVanwaarIngeschreven/, 'code', key, val);
			//!! Commentaar hierboven, zou mooi zijn maar helaas gebeurt dat niet, dus toch checken op undefined.!!
			//!! Volgorde van de gebruikVeldBijMatch regels is belangrijk!.
	        if(typeof tempVal == 'undefined') {
	            val = ViewerUtils.gebruikVeldBijMatch(/Code/, 'naam', key, val); // sommige codes gebruiken helaas 'naam' ipv 'code'
	        }
	        else {
	            val = tempVal;
	        }
			
			val = ViewerUtils.gebruikVeldBijMatchCustom(key, val, voorkomen.historie);
			val = ViewerUtils.maakWaardeOpBijLeegOfBoolean(val);

			var title = val;
			val = _.escape(val);
			if (key == 'administratienummer') val = '<a href="#' + val + '">' + val +'</a>';
			regel = regel + _.template(tdTemplate, {key: key, title: title, val: val});
		}
		var $regel = $(regel + '</tr>\r\n');
		
		//bind link aNr
		$regel.find('a').on('click', null, null, Overzicht.relateerPersonen.bind(Overzicht));
		//bind whole row
		$regel.find('td').on('click', null, herkomstClass, Overzicht.relateer.bind(Overzicht));
		//add acties
		$regel.append($actieRegels);
		
		$table.append($regel);
	}
}
