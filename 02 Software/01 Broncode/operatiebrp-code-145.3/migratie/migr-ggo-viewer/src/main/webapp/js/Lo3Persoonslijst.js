Lo3Persoonslijst =
{
	maakLo3Persoonslijst : function(ggoPL, soortLo3PlConversie, foutRegels, metTerugconversie) {

		var $overzicht;
		var stap;

		if (soortLo3PlConversie == 'GBA-PL') {
			var classes = metTerugconversie ? 'hoger' : '';
			$overzicht = $(_.template(categorieTemplate, {type: 'overzicht', classes: classes, titel: soortLo3PlConversie}));
			stap = 'LO3';
		} else {
			$overzicht = $(_.template(categorieTemplate, {type: 'overzicht', classes: 'getabd', titel: 'BRP-PL', info: soortLo3PlConversie}));
			$overzicht.find('.titeltekst').on('click', null, $overzicht, Overzicht.toggleTerugconversie.bind(Overzicht));;
			$overzicht.find('.info').on('click', null, $overzicht, Overzicht.nullEvent.bind(Overzicht));;
			stap = 'TERUGCONVERSIE';
		}

		var $secties = $overzicht.find('.inhoud');

		ViewerUtils.schrijfFoutRegels($secties, foutRegels, stap);

		if (ggoPL) {
		    this.maakTabel($secties, ggoPL, soortLo3PlConversie);
		}
		
		return $overzicht;
	},

	maakTabel : function($el, ggoPL, soort) {
		var soortClass = (soort.toLowerCase().indexOf("terugconversie") > 0) ? "terugconversie" : "lo3";

		for (var i in ggoPL) {
	    	var ggoStapel = ggoPL[i];
	    	var stapelTitel = ggoStapel.label + (ggoStapel.omschrijving ? " - " + ggoStapel.omschrijving : "");
	    	var $stapelSectie = ViewerUtils.maakSupersectie($el, stapelTitel).find('.inhoud');
	    	var $stapelSectieCheckbox = $stapelSectie.parent().find('.checkbox');
	    	var herkomstClassStapel = [];

	    	for (var j in ggoStapel.voorkomens) {
		        var ggoCategorie = ggoStapel.voorkomens[j];
		        var inhoud = ggoCategorie.inhoud;
	
		        if (inhoud != null) {
		            var key = ggoCategorie; //JSON.parse(j);
		            var historisch = key.categorieLabelNr > 50;
	                var classes = historisch ? " historisch" : "";
	                classes += ggoCategorie.inhoud["84.10 Indicatie onjuist, dan wel strijdigheid met de openbare orde"] != null ? " ongeldig" : "";
	                classes += ggoCategorie.vervallen ? " vervallen" : "";
	
	                var catNr = ViewerUtils.formatCategorieNr(key.categorieLabelNr);
	                var sectieTitel = '<span class="code">' + catNr + '</span><span class="label">' + key.label + '</span>';
	                var $sectie = $(_.template(categorieTemplate, {type: 'sectie', classes: "gesloten" + classes, titel : sectieTitel, info : key.datumAanvangGeldigheid}));
	                var $table = $('<table summary="' + catNr + ' - ' + key.label + '" />');
	                $sectie.find('.inhoud').append($table);
	
	                //herkomst
	                var herkomstClass = ViewerUtils.bepaalHerkomstClassName(key, key.aNummer);
	                $sectie.find('.checkbox').on('click', null, herkomstClass, Overzicht.relateer.bind(Overzicht));
	                herkomstClassStapel.push(herkomstClass);
	
	                //data rows
	                herkomstClass = herkomstClass + ' ' + soortClass;
	                this.maakTabelRegel($table, inhoud, herkomstClass);
	                                
	                $stapelSectie.append($sectie);
		        }
	    	}
	    	$stapelSectieCheckbox.on('click', null, herkomstClassStapel.join(" "), Overzicht.relateer.bind(Overzicht));
	    }
	},

	maakTabelRegel : function ($table, inhoud, herkomstClass) {
	    var regel = "";
	    for (var i in inhoud) {
	        regel = regel + '<tr class="headers ' + herkomstClass + '">';
			var key = i;
			var val = inhoud[key];
			
		     //Header
	        var label = key;
	        var code = label.substr(0,5);
	        label = label.substr(6);
	        regel = regel + _.template(thTemplate, {label: label, code: code});

			var title = val;
			val = _.escape(val);
			regel = regel + _.template(tdTemplate, {title: title, val: val});
			regel = regel + '</tr>';
		}
		var $regel = $(regel);
		$regel.find('a').on('click', null, null, Overzicht.relateerPersonen.bind(Overzicht));

		$table.append($regel);
	}
};
