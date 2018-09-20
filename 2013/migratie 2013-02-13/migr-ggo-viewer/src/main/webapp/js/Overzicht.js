Overzicht =
{
	klapOpenOfDicht : function(event) {
		$(event.target.parentElement).toggleClass("gesloten");
	},

	relateer : function(event, klapOpen) {
		$('.highlight').removeClass('highlight');

		if (!event.data) return; // negeer o.a. lege string (geen classes)
		var $classes = $('.' + event.data.replace (' ', ', .'));

		$classes.addClass('highlight');
		$classes.parents('.supersectie.header').children('.supersectie.titel').addClass('highlight');
		$classes.parents('.sectie').addClass('highlight');

		var $target = $(event.target);
		$target.parents('.persoon').find('.overzicht').addClass('gesloten');
		$target.parents('.persoon').find('.supersectie').addClass('gesloten');
		$target.parents('.persoon').find('.sectie').addClass('gesloten');

		$classes.parents('.overzicht').removeClass('gesloten');
		$classes.parents('.supersectie').removeClass('gesloten');
		$classes.parents('.sectie').removeClass('gesloten');
	},

	relateerPersonen : function(event) {
		var aNummer = event.target.text;
		var aNummerId = '#' + aNummer;

		if ($(aNummerId).size() > 0) return; // al op scherm, href werkt automatisch

	    $.ajax({
			type : "GET",
			url : "zoek",
			data : 'aNummer=' + aNummer,
			success : function(responseJSON) {
				Overzicht.maakOverzichten(JSON.parse(responseJSON));
				location.href=aNummerId;
			}
		});
	},

	sluit : function (event) {
		var $div = $('#' + event.data);
		$div.unbind();
		$div.remove();
		$("#foutmeldingen").empty();
	},

	plaats : function($overzicht, $div) {
		if ($overzicht == null) return;

		$div.append($overzicht);

		$overzicht.find('.titel').bind('click', this.klapOpenOfDicht.bind(this));
	},
	
	plaatsZonderKlapOpen : function($overzicht, $div) {
        if ($overzicht == null) return;

        $div.append($overzicht);
    },

	plaatsPopup : function(event) {
		var popupParam = event.data;
		var brpActieId = ViewerUtils.bepaalBrpActieId(popupParam);
		if ($('#' + brpActieId).size() > 0) return; // al op scherm, href werkt automatisch

		var $popupDiv = popupParam.sectie;		

		// draggable en closable
		$popupDiv.draggable({handle : '.popuptitelbalk' });
		$popupDiv.find('.close').on('click', null, brpActieId, this.sluit.bind(this));		
		// open/dicht tabellen
		$popupDiv.find('.titel').bind('click', this.klapOpenOfDicht.bind(this));

		// position
		var x = event.pageX;
		var y = event.pageY;
		$popupDiv.css({left : x, top : y});
		//hackie hackie
		//staat 'absolute' in css, maar chrome/IE zetten auto position:relative in css
		//$popupDiv.css({position : 'absolute'});
				
		// add to screen
		$("#content").append($popupDiv);
		Overzicht.verwerkFilters();
	},
	
	maakOverzichten : function (data) {
		var $foutmeldingen = $("#foutmeldingen");
		$foutmeldingen.empty();

		for (var i in data.foutRegels) {
			var foutRegel = data.foutRegels[i];
			ViewerUtils.addLineToTable($foutmeldingen, {thClass: foutRegel.severity.toLowerCase(), th : foutRegel.code, td: foutRegel.omschrijving});
		}

		for (var i in data.persoonslijstGroepen) {
			var persoonslijstGroep = data.persoonslijstGroepen[i];
			var aNummer = persoonslijstGroep.lo3Persoonslijst.actueelAdministratienummer;

			var categorieen = persoonslijstGroep.lo3Persoonslijst.persoonStapel.categorieen;

			$div = $(_.template(persoonTemplate, categorieen[categorieen.length -1].inhoud));
			$div.find('.close').on('click', null, aNummer, this.sluit.bind(this));

			var $lo3Persoonslijst = Lo3Persoonslijst.maakLo3Persoonslijst(persoonslijstGroep.lo3Persoonslijst, "Lo3");
			var $precondities = Precondities.maakPrecondities(persoonslijstGroep.precondities, aNummer);
			var $bcmSignaleringen = BcmSignaleringen.maakBcmSignaleringen(persoonslijstGroep.bcmSignaleringen, aNummer);
			var $brpPersoonslijst = BrpPersoonslijst.maakBrpPersoonslijst(persoonslijstGroep.brpPersoonslijst);
			var $vergelijking = Vergelijking.maakVergelijking(persoonslijstGroep.vergelijking, aNummer);

			var $teruggeconverteerd = null;
			if ($vergelijking != null) {
				$teruggeconverteerd = Lo3Persoonslijst.maakLo3Persoonslijst(persoonslijstGroep.teruggeconverteerd, "Terugconversie");
			}

			$mainDiv = $div.find('.persoonslijsten');

			$lo3Div = $('<div class="lo3Column"><div class="scrollDiv" /></div>');
			$mainDiv.append($lo3Div);
			Overzicht.plaats($lo3Persoonslijst, $lo3Div.find(".scrollDiv"));

			if ($teruggeconverteerd) {
				$teruggeconverteerdDiv = $('<div class="teruggeconverteerdColumn"><div class="scrollDiv" /></div>');
				$mainDiv.append($teruggeconverteerdDiv);
				Overzicht.plaats($teruggeconverteerd, $teruggeconverteerdDiv.find(".scrollDiv"));
			}

			$brpDiv = $('<div class="brpColumn"><div class="scrollDiv" /></div>');
			$mainDiv.append($brpDiv);
			Overzicht.plaats($brpPersoonslijst, $brpDiv.find(".scrollDiv"));

			$controlesDiv = $div.find('.controles');

			if (persoonslijstGroep.brpPersoonslijst) {
				for (var j in persoonslijstGroep.brpPersoonslijst.relaties) {
					var brpRelatie = persoonslijstGroep.brpPersoonslijst.relaties[j];
	
					var $brpRelatie = BrpRelatie.maakBrpRelatie(brpRelatie, aNummer);
					Overzicht.plaats($brpRelatie, $brpDiv.find(".scrollDiv"));
				}
			}

			Overzicht.plaats($precondities, $controlesDiv);
			Overzicht.plaats($bcmSignaleringen, $controlesDiv);
			Overzicht.plaats($vergelijking, $controlesDiv);

			$('#content').append($div);
			$('#content').sortable({handle: '.hoofdtitelbalk', axis: 'y'});
			Overzicht.verwerkFilters();
		}
	},
	
	verwerkFilters : function () {
		if ($('#vertical').hasClass('vertical')) {
			$('.persoonslijsten').addClass('vertical');
			$('.actiePopup').addClass('vertical');
		} else {
			$('.persoonslijsten').removeClass('vertical');
			$('.actiePopup').removeClass('vertical');
		}

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
	},
	
	/**
	 * Vind de bijbehorende BRP gegevens op basis van de herkomst van de selection.
	 * Wijzig hier vervolgens de CSS 'display' van; voer dit door voor de bijbehorende header.
	 */
	displayBijbehorendeBrpGegevens : function (selection, display) {
		var categorieen = [];

		$(selection + ' tr').each(function() {
			var classes = $(this).attr('class').split(' ');
			for (var i in classes) {
				if (classes[i].match('^[0-9]')) {
					categorieen.push(classes[i]);
				}
			}
		});

		if (categorieen.length > 0) {
			var $categorieen = $('.brpColumn .' + categorieen.join(', .brpColumn .'));
			$categorieen.css('display', display);
			$categorieen.parents('.sectie.header').css('display', display);
		}
	}
};
