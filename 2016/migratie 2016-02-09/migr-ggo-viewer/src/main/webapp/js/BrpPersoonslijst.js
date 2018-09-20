BrpPersoonslijst =
{
	maakBrpPersoonslijst : function(ggoBrpPL, foutRegels, metTerugconversie) {
		var $overzicht;

		if (metTerugconversie) {
			$overzicht = $(_.template(categorieTemplate, {type: 'overzicht', classes: 'getabd', titel: "BRP-PL", info: "GBA-PL (terugconversie)"}));
			$overzicht.find('.titeltekst').on('click', null, $overzicht, Overzicht.nullEvent.bind(Overzicht));;
			$overzicht.find('.info').on('click', null, $overzicht, Overzicht.toggleTerugconversie.bind(Overzicht));;
		} else {
			$overzicht = $(_.template(categorieTemplate, {type: 'overzicht', titel: "BRP-PL"}));
		}

		var $secties = $overzicht.find('.inhoud');

		ViewerUtils.schrijfFoutRegels($secties, foutRegels, 'BRP');

		if (ggoBrpPL) {
		    this.maakTabel($secties, ggoBrpPL);
		}
		
		return $overzicht;
	},
	
	maakTabel : function($el, ggoBrpPL) {
		for (var i in ggoBrpPL) {
	    	var ggoStapel = ggoBrpPL[i];
	    	var herkomstClassStapel = [];
	    	// Huwelijk / Geregistreerd Partnerschap / Familierechtelijke betrekking
    		if (ggoStapel.label == 'H' || ggoStapel.label == 'G' || ggoStapel.label == 'F') {
    			this.maakRelatie(ggoStapel, herkomstClassStapel, $el);
    		} else if (ggoStapel.label == 'Administratieve handeling') {
    			this.maakAdministratieveHandeling(ggoStapel, herkomstClassStapel, $el);
			} else {
    			this.maakReguliereStapel(ggoStapel, herkomstClassStapel, $el);
    		}
		}
	},

	maakReguliereStapel : function(ggoStapel, herkomstClassStapel, $el) {
    	var stapelTitel = ggoStapel.label + (ggoStapel.omschrijving ? " - " + ggoStapel.omschrijving : "");
    	var $stapelSectie = ViewerUtils.maakSupersectie($el, stapelTitel).find('.inhoud');
    	var $stapelSectieCheckbox = $stapelSectie.parent().find('.checkbox');
    	
    	for (var j in ggoStapel.voorkomens) {
	    	this.maakVoorkomen(ggoStapel.voorkomens[j], herkomstClassStapel, $stapelSectie);
    	}

    	$stapelSectieCheckbox.on('click', null, herkomstClassStapel.join(" "), Overzicht.relateer.bind(Overzicht));
	},

	maakAdministratieveHandeling : function(ggoStapel, herkomstClassStapel, $el) {
    	for (var j in ggoStapel.voorkomens) {
	    	$ah = this.maakVoorkomen(ggoStapel.voorkomens[j], herkomstClassStapel, $el, true);
	    	this.maakBetrokkenVoorkomens(ggoStapel.voorkomens[j], herkomstClassStapel, $ah.find('.inhoud'));
    	}
	},

	maakRelatie : function(ggoStapel, herkomstClassStapel, $el) {
		for (var i in ggoStapel.voorkomens) {
			var voorkomen = ggoStapel.voorkomens[i];

			for (var j in voorkomen.betrokkenheden) {
				var betrokkenheid = voorkomen.betrokkenheden[j];

				var stapelTitel = betrokkenheid.label;
				var $stapelSectie = ViewerUtils.maakSupersectie($el, stapelTitel, '<a href="#">Alle voorkomens</a>');
		    	var $stapelSectieInhoud = $stapelSectie.find('.inhoud');
		    	Ist.maakIst($stapelSectie, voorkomen, betrokkenheid.label, j);

		        var $table = $('<table summary="' + stapelTitel + '" />');
		        $stapelSectieInhoud.append($table);

	            //herkomst + toevoegen voorkomen (komt pas na maakTabelRegels hieronder)
		        var bherkomst = [];
		        for (var x in betrokkenheid.stapels) {
		        	var bstapel = betrokkenheid.stapels[x];
		        	for (var y in bstapel.voorkomens) {
		        		var bvoorkomen = bstapel.voorkomens[y];
		        		this.maakVoorkomen(bvoorkomen, bherkomst, $stapelSectieInhoud);
		        	}
		        }

				if (voorkomen.relatieInhoud) {
					for (var x in voorkomen.relatieInhoud.voorkomens) {
						this.maakVoorkomen(voorkomen.relatieInhoud.voorkomens[x], bherkomst, $stapelSectieInhoud);
					}
				}

				if (voorkomen.relatieAfgeleidAdministratief) {
					console.log(voorkomen.relatieAfgeleidAdministratief);
					for (var x in voorkomen.relatieAfgeleidAdministratief.voorkomens) {
						this.maakVoorkomen(voorkomen.relatieAfgeleidAdministratief.voorkomens[x], bherkomst, $stapelSectieInhoud);
					}
				}

//		        this.maakTabelRegels($table, bherkomst.join(" "), betrokkenheid, betrokkenheid.inhoud, true);
		    	$stapelSectieInhoud.parent().find('.checkbox').first().on('click', null, bherkomst.join(" "), Overzicht.relateer.bind(Overzicht));
		        herkomstClassStapel.push(bherkomst);
			}
		}
	},

	maakVoorkomen : function(ggoBrpVoorkomen, herkomstClassStapel, $stapelSectie, isAh) {
        if (ggoBrpVoorkomen != null && ggoBrpVoorkomen.inhoud != null) {
            var classes = ggoBrpVoorkomen.vervallen ? " vervallen" : "";

            var sectieTitel = '<span class="code">' + ggoBrpVoorkomen.label + '</span>';
            var type = 'sectie';
            if (isAh) {
            	type = 'supersectie regulier';
            	sectieTitel = ggoBrpVoorkomen.label;
            }
            var $sectie = $(_.template(categorieTemplate, {type: type, classes: "gesloten" + classes, titel : sectieTitel, info : ggoBrpVoorkomen.datumAanvangGeldigheid }));
            var $table = $('<table summary="' + ggoBrpVoorkomen.label + '" />');
            $sectie.find('.inhoud').append($table);
            
            //herkomst
            var herkomstClass = ViewerUtils.bepaalHerkomstClassName(ggoBrpVoorkomen, ggoBrpVoorkomen.aNummer);
    		$sectie.find('.checkbox').on('click', null, herkomstClass, Overzicht.relateer.bind(Overzicht));
    		herkomstClassStapel.push(herkomstClass);
    		
    		//data rows
            this.maakTabelRegels($table, herkomstClass, ggoBrpVoorkomen, ggoBrpVoorkomen.inhoud, false, isAh);
            
            $stapelSectie.append($sectie);
            return $sectie;
        }
        
        return null;
	},
	
	maakBetrokkenVoorkomens : function(ggoBrpVoorkomen, herkomstClassStapel, $stapelSectie) {
        if (ggoBrpVoorkomen.betrokkenVoorkomens != null) {
            var classes = ggoBrpVoorkomen.vervallen ? " vervallen" : "";

            var sectieTitel = '<span class="code">Betrokken voorkomens</span>';

            var $sectie = $(_.template(categorieTemplate, {type: 'sectie', classes: "gesloten" + classes, titel : sectieTitel }));
            var $table = $('<table summary="Betrokken voorkomens" />');
            $sectie.find('.inhoud').append($table);
            
            //herkomst
            var herkomstClass = ViewerUtils.bepaalHerkomstClassName(ggoBrpVoorkomen, ggoBrpVoorkomen.aNummer);
    		$sectie.find('.checkbox').on('click', null, herkomstClass, Overzicht.relateer.bind(Overzicht));
    		herkomstClassStapel.push(herkomstClass);

    		//data rows
            this.maakTabelRegels($table, herkomstClass, ggoBrpVoorkomen, ggoBrpVoorkomen.betrokkenVoorkomens, false, false);
            
            $stapelSectie.append($sectie);
        }
	},

	maakTabelRegels : function ($table, herkomstClass, ggoBrpVoorkomen, inhoud, isRelatieStapel, isAh) {
		var classes = herkomstClass + ' brp';

		if (isRelatieStapel || isAh) {
			classes = 'nivo1 ' + classes;
		}

		var regel = "";
	    for (var key in inhoud) {
		    regel += '<tr class="headers ' +classes + '">';
			var val = inhoud[key];

			// Voor betrokken voorkomens bij admin handeling (deze is geen map maar een list string)
			if (!isNaN(parseInt(key))) {
				var splitted = val.split(" - ");
				key = splitted[0];
				val = splitted[1];
			}

			//header
            regel = regel + _.template(thTemplate, {code: key});
            
            //data
			var title = val;
			val = _.escape(val);
			if (key == 'Geldigheid' || key == 'Registratie - verval') {
				var datum = val.split(' - ');
				regel += _.template(tdDatumTemplate, {datumVan: datum[0], datumTot: datum[1]});
			} else {
				regel += _.template(tdTemplate, {title: title, val: val});				
			}
			regel += '</tr>';
		}
	    
	    // extra headers
	    if (isRelatieStapel) {
	    	// lege header
	    	regel += this.maakActieOfRelatieRegel(classes, 'Relatie_meer', '\xA0'); // non-breaking space
	    } else { 
	    	// actie inhoud
	    	if (ggoBrpVoorkomen.actieInhoud) {
	    		regel += this.maakActieOfRelatieRegel(classes, 'Acties_inhoud', 'Acties');
	    	}
	    	// overige acties
	    	if (ggoBrpVoorkomen.actieVerval) {
		    	regel += this.maakActieOfRelatieRegel(classes, 'Acties_verval', '\xA0');
	    	}
	    	if (ggoBrpVoorkomen.actieGeldigheid) {
		    	regel += this.maakActieOfRelatieRegel(classes, 'Acties_aanpassingGeldigheid', '\xA0');
	    	}
	    }
	    
	    if (ggoBrpVoorkomen.onderzoeken) {
    		var titel = 'Onderzoeken';
	    	for (var i in ggoBrpVoorkomen.onderzoeken) {
	    		regel += this.maakActieOfRelatieRegel(classes, 'Onderzoeken_' + i, titel);
	    		titel = '\xA0'; // non-breaking space
	    	}
	    }
	    
        // aNr click
		var $regel = $(regel);
		$regel.find('a').on('click', null, null, Overzicht.relateerPersonen.bind(Overzicht));
		
		// maak link en popup
		if (isRelatieStapel) {
			BrpRelatie.maakTabelRegel($regel, ggoBrpVoorkomen);
		} else {
			BrpActie.maakTabelRegelActies($regel, ggoBrpVoorkomen);
		}

		BrpOnderzoek.maakTabelRegelOnderzoeken($regel, ggoBrpVoorkomen);

		$table.append($regel);
	},
	
	maakActieOfRelatieRegel : function (classes, clss, code) {
		var regel = '<tr class="headers ' + classes + '">';
		regel += _.template(thTemplate, {clss: clss, code: code}); // non-breaking space
		return regel + '</tr>';
	}
};
