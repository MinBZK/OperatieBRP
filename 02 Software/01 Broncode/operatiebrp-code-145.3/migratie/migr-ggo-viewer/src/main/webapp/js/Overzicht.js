Overzicht =
{
	klapOpenOfDicht : function(event) {
	    if ($(event.target).hasClass('checkbox')) {
	        return;
	    } else {
	        $(event.target).closest('.titel').parent().toggleClass("gesloten");
	    }
	},

	relateer : function(event) {
		if (!event.data) return; // negeer o.a. lege string (geen classes)
		var $classes = $('.' + event.data.replace (/ /g, ', .'));
		var $target = $(event.target);

		//highlight rows
		ViewerUtils.highlight($classes, $target);
	},

	relateerPersonen : function(event) {
		var aNummer = event.target.text;
		var aNummerId = '#' + aNummer;

		if ($(aNummerId).size() > 0) return; // al op scherm, href werkt automatisch

	    $.ajax({
			type : "GET",
			url : "zoek",
			complete: function() {
				var resultPL = document.getElementById(aNummer);
				if (resultPL) {
					resultPL.scrollIntoView(true);
				}
			},
			error: function() {
				ViewerUtils.addZoekFout('err', 'Onbekende fout bij opvragen a-nummer.', null, false);
			},
			data : 'aNummer=' + aNummer,
			success: function(responseJSON) {
               if (typeof responseJSON == 'string' || responseJSON instanceof String) {
                   location.href= window.location.pathname;
               }
               else {
        	       Overzicht.maakOverzichten(responseJSON);
               }
			}
		});
	},

	sluit : function (event) {
		// close div (pl or actie popup)
		var $div = event.data;
		$div.unbind();
		$div.remove();

		// If div is pl then close resultaat index and popup
		if ($div.hasClass('persoon')) {
			Overzicht.clear();

			// toon default PLen indien van toepassing
			$('#defaultPLen').show();
		}	
	},

	toggleTerugconversie : function (event) {
	    var $target = $(event.target);
	    $target.parents('.persoon').find('.teruggeconverteerdColumn').toggle();
	    $target.parents('.persoon').find('.brpColumn').toggle();
        $(event.target).closest('.getabd').removeClass("gesloten");
	    
	    return false;
    },

    nullEvent : function(event) {
    	return false;
	},

    opnieuw : function (event) {
        //reset PL. Sluit en de-highlight
    	var $target = $(event.target);
        $target.parents('.persoon').find('.overzicht').removeClass('gesloten');
        $target.parents('.persoon').find('.supersectie').addClass('gesloten');
        $target.parents('.persoon').find('.sectie').not('.actiePopup .sectie').addClass('gesloten');
        $target.parents('.persoon').find('.highlight').removeClass('highlight');
        $target.parents('.persoon').find('.highlightmelding').removeClass('highlightmelding');
        $target.parents('.persoon').find('.highlightvergelijking').removeClass('highlightvergelijking');

        //show brp
		if ($target.parents('.persoon').find('.brpColumn').is(":hidden")) {
			this.toggleTerugconversie(event);
		}
        
        var $div = event.data;
        var aNr = $div[0].id;
        //remove specific actie popup
        var $plActiePopup = $('div[id^="' + aNr + '_"]');
        if ($plActiePopup.hasClass('actiePopup')) {
            $plActiePopup.unbind();
            $plActiePopup.remove();
        }
        
        //reset scrollbars
        $('.overzicht.inhoud').scrollTop(0);
    },
	
	clear : function () {
		//remove popups
		var $plPopup = $('.popup');
		$plPopup.unbind();
		$plPopup.remove();

		//clear foutmeldingen
		ViewerUtils.clearFoutmeldingen();

		// clear bestaande PLs
		var $personen = $('.persoon');
		$personen.unbind();
		$personen.remove();

		//remove title if empty
		$contentDiv = $('#content');
		if ($contentDiv.find('div').length == 0) {
			$contentDiv.find('h1').remove();
		}

		// clear resultaten
		var $resultaten = $('.resultaat');
		$resultaten.unbind();
		$resultaten.remove();

		$resultaatIndexDiv = $('#resultaatIndex');
		if ($resultaatIndexDiv.find('.resultaat_index').children().length == 0) {
			$resultaatIndexDiv.find('h1').remove();
		}
	},

	plaats : function($overzicht, $div) {
		if ($overzicht == null) return;

		$div.append($overzicht);

		$overzicht.find('.titel').bind('click', this.klapOpenOfDicht.bind(this));
	},
	
	plaatsPopup : function(event) {
		var popupParam = event.data;
		var brpPopupId = ViewerUtils.bepaalBrpPopupId(popupParam);
		console.log(brpPopupId);
		if ($('#' + brpPopupId).size() > 0) {
			window.location.hash = '#' + brpPopupId; 
			return false;
		}

		var $popupDiv = popupParam.sectie;

		// position
		$popupDiv.css({left : event.pageX - 260, top: event.pageY});
		
		// add to screen
		$("body").append($popupDiv);
		Overzicht.verwerkFilters();

		// draggable en closable
		$popupDiv.draggable({handle : '.popuptitelbalk', containment : 'body'});
		$popupDiv.find('.plbutton').on('click', null, $popupDiv, this.sluit.bind(this));		
		// open/dicht tabellen
		$popupDiv.find('.titel').bind('click', this.klapOpenOfDicht.bind(this));
		
		return false;
	},
	
	maakOverzichten : function (data) {
		this.clear();

		if (data.persoonslijstGroepen == null || data.persoonslijstGroepen.length == 0) {
			for (var i in data.foutRegels) {
				var foutRegel = data.foutRegels[i];
				ViewerUtils.addZoekFout('err', foutRegel.code, foutRegel.omschrijving, false, foutRegel.htmlFieldId);
			}
		} else if (data.persoonslijstGroepen.length == 1) {
			this.maakOverzicht(data.persoonslijstGroepen[0]);
		} else { // # > 1
			ResultaatIndex.maakSelectie(data.persoonslijstGroepen);
		}
	},

	maakOverzicht : function (persoonslijstGroep) {
		$('#defaultPLen').hide();
		var persoonId = ViewerUtils.bepaalPersoonId(persoonslijstGroep.aNummer);
		var persoonTitel = ViewerUtils.bepaalPersoonTitel(persoonslijstGroep.aNummer, persoonslijstGroep.volledigeNaam);

		var $content = $('#content');

		$content.empty();
		$content.append("<h1>Resultaten</h1>");

		//geldig aNummer nog niet aanwezig op de pagina
		var closeBtnText = !persoonslijstGroep.ggoLo3PL ? "sluiten" : "sluit PL";
		$persoonDiv = $(_.template(persoonTemplate, {persoonId: persoonId, persoonTitel: persoonTitel, closeBtnText: closeBtnText}));
		$persoonDiv.find('.close').on('click', null, $persoonDiv, this.sluit.bind(this));
		$persoonDiv.find('.opnieuw').on('click', null, $persoonDiv, this.opnieuw.bind(this));

		var $meldingen = Meldingen.maakMeldingen(persoonslijstGroep.meldingen, persoonId, persoonslijstGroep.foutLog);
		var $vergelijking = Vergelijking.maakVergelijking(persoonslijstGroep.vergelijking, persoonId, persoonslijstGroep.foutLog);

		var $teruggeconverteerd = null;
		if ($vergelijking != null || Overzicht.checkTerugConversieFout(persoonslijstGroep.foutLog)) {
			$teruggeconverteerd = Lo3Persoonslijst.maakLo3Persoonslijst(persoonslijstGroep.ggoLo3PLTerugConversie, "GBA-PL (terugconversie)", persoonslijstGroep.foutLog);
		}

		var $lo3Persoonslijst = Lo3Persoonslijst.maakLo3Persoonslijst(persoonslijstGroep.ggoLo3PL, "GBA-PL", persoonslijstGroep.foutLog, $teruggeconverteerd != null);
		var $brpPersoonslijst = BrpPersoonslijst.maakBrpPersoonslijst(persoonslijstGroep.ggoBrpPL, persoonslijstGroep.foutLog, $teruggeconverteerd != null);

		$mainDiv = $persoonDiv.find('.persoonslijsten');

		//append gba-pl
		$lo3Div = $('<div class="lo3Column"><div class="scrollDiv" /></div>');
		$mainDiv.append($lo3Div);
		Overzicht.plaats($lo3Persoonslijst, $lo3Div.find(".scrollDiv"));

		//append teruggeconverteerd gba-pl
		if ($teruggeconverteerd) {
			$teruggeconverteerdDiv = $('<div class="teruggeconverteerdColumn"><div class="scrollDiv" /></div>');
			$mainDiv.append($teruggeconverteerdDiv);
			Overzicht.plaats($teruggeconverteerd, $teruggeconverteerdDiv.find(".scrollDiv"));
		}

		//append brp-pl
		$brpDiv = $('<div class="brpColumn"><div class="scrollDiv" /></div>');
		$mainDiv.append($brpDiv);
		Overzicht.plaats($brpPersoonslijst, $brpDiv.find(".scrollDiv"));

		$controlesDiv = $persoonDiv.find('.controles');
		Overzicht.plaats($meldingen, $controlesDiv);
		Overzicht.plaats($vergelijking, $controlesDiv);

		$content.append($persoonDiv);

		if ($teruggeconverteerd) {
			// Hide terugconversie PL standaard
			$persoonDiv.find('.teruggeconverteerdColumn').hide();
		}

		//resultaat index
		Overzicht.voegIndexToeAanOverzicht(persoonslijstGroep, persoonId, persoonTitel);

		//filters
		Overzicht.verwerkFilters();
		
		//sorteer
		Overzicht.sorteerTabellen(persoonId);
		
		// disable checkboxes als pl's niet ingeladen
		if (!persoonslijstGroep.ggoLo3PL && !persoonslijstGroep.ggoBrpPL) {
			$persoonDiv.find('.controles .checkbox').addClass('disabled');
			$persoonDiv.find('.controles .checkbox').off('click');
			$persoonDiv.find('.opnieuw').hide();
		}
	},
	
	checkTerugConversieFout: function (fouten) {
	    var vergelijkingTonen = false;
        for (var f in fouten) {
            var fout = fouten[f];
            if (fout && fout.stap == 'TERUGCONVERSIE') {
                vergelijkingTonen = true;
            }
        }
        return vergelijkingTonen;
	},

	verwerkFilters : function () {
		if ($('#vertical').hasClass('vertical')) {
			$('.persoonslijsten').addClass('vertical');
			$('.actiePopup').addClass('vertical');
		} else {
			$('.persoonslijsten').removeClass('vertical');
			$('.actiePopup').removeClass('vertical');
		}
/*
		if ($('#historisch').hasClass('historisch')) {
			$('.sectie.historisch').css('display', 'block');
			this.displayBijbehorendeBrpGegevens('.sectie.historisch', ''); 
		} else {
			$('.sectie.historisch').css('display', 'none');
			this.displayBijbehorendeBrpGegevens('.sectie.historisch', 'none');
		}

		if ($('#ongeldig').hasClass('ongeldig')) {
			$('.sectie.ongeldig').css('display', 'block');
			this.displayBijbehorendeBrpGegevens('.sectie.ongeldig', '');
		} else {
			$('.sectie.ongeldig').css('display', 'none');
			this.displayBijbehorendeBrpGegevens('.sectie.ongeldig', 'none');
		}
*/
	},

	/**
	 * Vind de bijbehorende BRP gegevens op basis van de herkomst van de selection.
	 * Wijzig hier vervolgens de CSS 'display' van; voer dit door voor de bijbehorende header.
	 */
	displayBijbehorendeBrpGegevens : function (selection, display) {
		var categorieen = [];
		
		//selecteer lo3 herkomst class
		$(selection + ' tr').each(function() {
			var classes = $(this).attr('class').split(' ');
			for (var i in classes) {
				if (classes[i].match('^[0-9]')) {
					categorieen.push(classes[i]);
				}
			}
		});
		
		//select bijbehorende brp groepen
		if (categorieen.length > 0) {
			var $categorieen = $('.brpColumn .' + categorieen.join(', .brpColumn .'));
			$categorieen.parents('.sectie.overall').css('display', display);
		}
	}, 
	
	voegIndexToeAanOverzicht : function (persoonslijstGroep, persoonId, persoonTitel) {
		var $plIndex = ResultaatIndex.maakIndex(persoonslijstGroep.indexRegels, persoonId, persoonTitel);
		$plIndexDiv = $("#resultaatIndex");
		if ($plIndex != null) {
			if ($plIndexDiv.find('h1').length == 0) {
				$plIndexDiv.prepend("<h1>Overzicht resultaten</h1>");
			}
		}
		Overzicht.plaats($plIndex, $plIndexDiv.find(".resultaat_index"));		
	},
	
	sorteerTabellen : function (persoonId) {
		$("#meldingtable_" + persoonId).tablesorter( {sortList: [[0,0]],  
		    headers: { 
		        3: { 
                    sorter: false 
                },
                4: { 
                    sorter: false 
                }
		    },
		    textExtraction : function (node) {
		    	return node.innerHTML;
		    }
		});
		$("#vergelijkingtable_" + persoonId).tablesorter( {sortList: [[0,0]],  
            headers: { 
                2: { 
                    sorter: false 
                },
                3: { 
                    sorter: false 
                }
            }
        });
	}
};
