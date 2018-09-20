Vergelijking =
{
	maakVergelijking : function(vergelijking, aNummer) {
		if (!vergelijking) return null;

		var leeg = true;

		for (var i in vergelijking) {
			if (vergelijking[i].type != 'IDENTICAL') leeg = false;
		}
		
		if (leeg) return null;

		var $overzicht = $(_.template(overzichtTemplate, {type: 'overzicht', aNummer : aNummer, titel: "Vergelijking"}));
	
		var $sectie = $('<div class="sectie table-sectie"><table summary="Vergelijking" /></div>');
		$overzicht.find('.inhoud').append($sectie);
		var $table = $sectie.find('table');

		for (var i in vergelijking) {
			var type = vergelijking[i].type.toLowerCase();

			if (type == 'identical') continue;

			var catNr = vergelijking[i].categorie;

			if (catNr < 10) {
				catNr = "0" + catNr;
			}

			var oldHerkomst = [];
			var newHerkomst = [];
			var elements = [];

			for (var j in vergelijking[i].voorkomenMatches) {
				if (!empty(vergelijking[i].voorkomenMatches[j].oldVoorkomen)) {
					oldHerkomst.push (this.bepaalHerkomstClassName(vergelijking[i].voorkomenMatches[j].oldVoorkomen, aNummer));
				}
				if (!empty(vergelijking[i].voorkomenMatches[j].newVoorkomen)) {
					newHerkomst.push (this.bepaalHerkomstClassName(vergelijking[i].voorkomenMatches[j].newVoorkomen, aNummer));
				}
				
				elements = elements.concat(vergelijking[i].voorkomenMatches[j].elements);
			}

			var formattedElements = [];
			for (var j in elements) {
				var el = elements[j] > 999 ? "" + elements[j] : "0" + elements[j];
				
				formattedElements.push(catNr + "." + el.substr(0,2) + "." + el.substr(2,4));
			}

			var inhoud = formattedElements.join(", ");
			var herkomst = { oldHerkomst : oldHerkomst.join(" "), newHerkomst : newHerkomst.join(" ")};

			ViewerUtils.addLineToTable($table, {thClass: type, th : type, tdClass: oldHerkomst.concat(newHerkomst).join(" "), td: inhoud}, this.relateer.bind(this), herkomst);
		}
		
		return $overzicht;
	},

	bepaalHerkomstClassName : function (voorkomen, aNummer) {
		var catNr = voorkomen.categorieNr;
		if (catNr > 50) {
			catNr = catNr - 50;
		}

		if (catNr < 10) {
			catNr = "0" + catNr;
		}

		return aNummer + '_' + catNr + '_' + voorkomen.stapelNr + '_' + voorkomen.volgNr;
	},
	
	relateer : function (event) {
		var lo3Classes = '.lo3.' + event.data.oldHerkomst.replace (/ /g, ', .lo3.');
		var terugconversieClasses = '.terugconversie.' + event.data.newHerkomst.replace (/ /g, ', .terugconversie.');
		var vergelijkClasses = '.vergelijk.' + event.data.newHerkomst.replace (/ /g, ', .vergelijk.');

		var $classes = $(lo3Classes + ', ' + terugconversieClasses + ', ' + vergelijkClasses);

		$('.highlight').removeClass('highlight');

		$classes.addClass('highlight');
		$classes.parents('.supersectie-inhoud').prev().addClass('highlight');
		$classes.parents('.sectie-inhoud').prev().addClass('highlight');

		$target = $(event.target);
		$target.addClass('highlight');
		$target.parents('.supersectie-inhoud').prev().addClass('highlight');
		$target.parents('.sectie-inhoud').prev().addClass('highlight');

		$target.parents('.persoon').find('.overzicht').addClass('gesloten');
		$target.parents('.persoon').find('.supersectie').addClass('gesloten');
		$target.parents('.persoon').find('.sectie').addClass('gesloten');

		$target.parents('.overzicht').removeClass('gesloten');
		$classes.parents('.overzicht').removeClass('gesloten');
		$classes.parents('.supersectie').removeClass('gesloten');
		$classes.parents('.sectie').removeClass('gesloten');
	}
}
