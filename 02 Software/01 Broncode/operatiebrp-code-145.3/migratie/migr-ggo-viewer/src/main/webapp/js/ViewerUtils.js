ViewerUtils =
{
		// als categorie nummer gevuld is en tussen 0 en 10, 0 ervoor plakken.
		formatCategorieNr : function(catNr) {
			if (!empty(catNr) && catNr > 0 && catNr < 10) {
                catNr = "0" + catNr;
            }
			return catNr;
		},

		// gebruikt GgoCategorieKey om een unieke class te maken
		bepaalHerkomstClassName : function(ggoCategorieKey, aNummer) {
			var herkomstClass = [];
			// Eigenlijk willen we van alles een herkomst hebben, maar de terugconversie
            // heeft die momenteel niet. Hopelijk komt dit nog in enige vorm terug.
            if (ggoCategorieKey != null) {
            	var persoonId = ViewerUtils.bepaalPersoonId(aNummer);
    			var catNr = ggoCategorieKey.categorieNr;			
				var stapelNr = ggoCategorieKey.stapelNr;
				var volgNr = ggoCategorieKey.voorkomenNr;				
				herkomstClass.push(persoonId + '_' + catNr + '_' + stapelNr + '_' + volgNr);				
			}
			return herkomstClass.join(" ");
		},

		addLineToTable : function($table, data, onClick, onClickData) {
			var $row = $(_.template(trTemplate, data));
			$row.find('td').on('click', null, onClickData, onClick);
			$table.append($row);
		},

		maakSupersectie : function ($el, titel, info) {
			var type = 'supersectie';
			type += info ? ' relatie' : ' regulier';
			var $supersectie = $(_.template(categorieTemplate, {type: type, classes: "gesloten", titel : titel, info: info}));
			$el.append($supersectie);
		
			return $supersectie;
		},
		
		bepaalBrpPopupId : function(popupParam) {
			var persoonId = ViewerUtils.bepaalPersoonId(popupParam.key.aNummer);
			var groepNaam = popupParam.key.label.replace(/ /g, '-').replace(/\//g, '-');
			var subkey = popupParam.subkey === undefined ? 'undefined' : popupParam.subkey.replace(/[ \/\:]/g, '-');
			var brpPopupId = persoonId + '_' + groepNaam + '_' + subkey;
			if (!empty(popupParam.key.brpStapelNr)) {
				brpPopupId = brpPopupId.concat('_' + 
						popupParam.key.brpStapelNr);						
			}
			if (!empty(popupParam.key.categorieNr)) {
				brpPopupId = brpPopupId.concat('_' + 
						popupParam.key.categorieNr + '_' + 
						popupParam.key.stapelNr + '_' + 
						popupParam.key.voorkomenNr);
			}
			return  brpPopupId;
		},

		//hightlights the secties
		highlight : function($classes, $target, targetHerkomstClass) {
		    var gesloten = $target.parents('.overall').hasClass('gesloten') == true;
		    
			if (ViewerUtils.isHighlighted($target)) {
				//de-highlight only
				$('.highlight').removeClass('highlight');
		        $('.highlightmelding').removeClass('highlightmelding');
		        $('.highlightvergelijking').removeClass('highlightvergelijking');
			} else {
			    //Sluit de groepen, maar niet die van de popup
		        $target.parents('.persoon').find('.overzicht').removeClass('gesloten');
		        $target.parents('.persoon').find('.supersectie').addClass('gesloten');
		        $target.parents('.persoon').find('.sectie').not('.actiePopup .sectie').addClass('gesloten');
		        
			    //de-highlight
				$('.highlight').removeClass('highlight');
		        $('.highlightmelding').removeClass('highlightmelding');
		        $('.highlightvergelijking').removeClass('highlightvergelijking');

		        //highlight pl row
	    		$classes.parents('.supersectie').children('.supersectie.titel').addClass('highlight');
	    		$classes.parents('.sectie').children('.sectie.titel').addClass('highlight');
	    		//highlight controle row
	    		$classes.hasParent('.controles').addClass('highlight');
	    		
	    		//clicked from meldingen
	    		if ($target.parents('.meldingen').length > 0) {
	    			//highlight pl row with extra color
	    			$classes.parents('.supersectie').children('.supersectie.titel').addClass('highlightmelding');
	                $classes.parents('.sectie').children('.sectie.titel').addClass('highlightmelding');
	                $classes.hasParent('.meldingen').addClass('highlightmelding');	    			
	    			
	            }

	    		//clicked from vergelijking
	    		if ($target.parents('.verschillen').length > 0) {
	    			//highlight pl row with extra color
	    			$classes.parents('.supersectie').children('.supersectie.titel').addClass('highlightvergelijking');
	                $classes.parents('.sectie').children('.sectie.titel').addClass('highlightvergelijking');
	                $classes.hasParent('.verschillen').addClass('highlightvergelijking');	    			
	            }

	    		//open the highlighted
	    		$classes.parents('.overzicht').removeClass('gesloten');
	    		$classes.parents('.supersectie').removeClass('gesloten');
	    		if (!gesloten) {
	    		    $target.parents('.sectie').removeClass('gesloten');
	    		}
			}
		},
		
		isHighlighted : function ($target) {
			isHighlighted = false;
			if ($target.parents('.highlight').length > 0) {
				isHighlighted = true;
			}
			return isHighlighted;
		},
		
		bepaalPersoonTitel : function (aNummer, volledigeNaam) {
			if (aNummer == null) {
				return 'PL niet ingelezen';
			} else {
				volledigeNaam = volledigeNaam != null ? volledigeNaam : 'Naam onbekend';
				return aNummer + ' (' + volledigeNaam + ')';
			}
		},
		
		bepaalPersoonId : function (aNummer) {
			var persoonId = aNummer;
			if (persoonId == null) {
				var onbekendIndex = $('.persoon').length;
				persoonId = "onbekend_" + onbekendIndex;
			}			
			return persoonId;			
		},
		
		isValidAnummer : function (aNummer) {
			var validateExpr = /\d+/; // /\d{10}/;
			return validateExpr.test(aNummer) && aNummer.length == 10;
		},
		
		schrijfFoutRegels : function ($secties, foutRegels, stap) {
			var heeftFoutRegels = false;

			for (var i in foutRegels) {
				var foutRegel = foutRegels[i];
				if (foutRegel.stap == stap) {
					var melding = (foutRegel.code ? foutRegel.code + ': ' : '') + foutRegel.omschrijving;
					$secties.append($(_.template(foutmeldingTemplate, {melding: melding})));
					heeftFoutRegels = true;
				}
			}
			
			return heeftFoutRegels;
		},
		
		addZoekFout : function (severity, headMsg, detailMsg, clear, fieldId) {
			if (clear) {
				ViewerUtils.clearFoutmeldingen();
			}
			htmlDetailMsg = detailMsg ? '<p>' + detailMsg + '</p>' : '';
			$('#zoekfout').append('<div class="mod message_' + severity + '"><h2>' + headMsg + '</h2>' + htmlDetailMsg + '</div>');
			
			if (fieldId) {
				$('#' + fieldId).addClass('field_err');
			}
		},
		
		clearFoutmeldingen : function () {
			$('.foutmeldingen').empty();
			$('.field_err').removeClass('field_err');
		},
		
		unescape : function (html) {
			return _.unescape(html);
		}
};