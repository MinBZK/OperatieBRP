Vergelijking =
{
	maakVergelijking : function(vergelijking, persoonId, foutRegels) {
		if (!vergelijking) return null;
		if (vergelijking.length == 0) return null;
		
		var $overzicht = $(_.template(categorieTemplate, {type: 'overzicht', classes: 'verschillen', titel: "Verschillen bij terugconversie (GBA > BRP > GBA)"}));
	
		var $sectie = $('<div class="sectie table-sectie"></div>');
		ViewerUtils.schrijfFoutRegels($sectie, foutRegels, "VERGELIJKING");
		$sectie.append('<table summary="Verschillen" id="vergelijkingtable_' + persoonId +'" />');

		$overzicht.find('.inhoud').append($sectie);
		var $table = $sectie.find('table');
		
		var $header = $(_.template(trVergelijkingHeaderTemplate, {}));
        $table.append($header);
        
        $table.append('<tbody>');
		for (var i in vergelijking) {
			var oudeHerkomst = this.converteerHerkomsten(vergelijking[i].oudeHerkomsten, persoonId);
			var nieuweHerkomst = this.converteerHerkomsten(vergelijking[i].nieuweHerkomsten, persoonId);
			
			var herkomst = { oldHerkomst : oudeHerkomst, newHerkomst : nieuweHerkomst};
			var herkomstClass = oudeHerkomst.concat(" ", nieuweHerkomst);
			var categorie = vergelijking[i].categorie;
			var type = vergelijking[i].type;
			var omschrijving = vergelijking[i].inhoud;
			
			var $row = $(_.template(trVergelijkingTemplate, {tdCategorie : categorie, tdType : type, trClass: herkomstClass, tdOms: omschrijving}));
			$row.find('.checkbox').on('click', null, herkomst, this.relateer.bind(this));
			$table.append($row);			
		}
		$table.append('</tbody>');
		
		return $overzicht;
	},

	relateer : function (event) {
		var herkomstClasses = [];
		var oldHerkomst = event.data.oldHerkomst;
		var newHerkomst = event.data.newHerkomst;
		
		if (!!oldHerkomst) {
			herkomstClasses.push('.lo3.' + oldHerkomst.replace (/ /g, ', .lo3.'));			
			herkomstClasses.push('.terugconversie.' + oldHerkomst.replace (/ /g, ', .terugconversie.'));
			herkomstClasses.push('.vergelijk.' + oldHerkomst.replace (/ /g, ', .vergelijk.'));
			herkomstClasses.push('.meldingen.' + oldHerkomst.replace (/ /g, ', .meldingen.'));
			herkomstClasses.push('.brp.' + oldHerkomst.replace (/ /g, ', .brp.'))
		} else if (!!newHerkomst) {
			herkomstClasses.push('.terugconversie.' + newHerkomst.replace (/ /g, ', .terugconversie.'));
			herkomstClasses.push('.vergelijk.' + newHerkomst.replace (/ /g, ', .vergelijk.'));
			herkomstClasses.push('.meldingen.' + newHerkomst.replace (/ /g, ', .meldingen.'));
			herkomstClasses.push('.brp.' + newHerkomst.replace (/ /g, ', .brp.'))
		}

		var $classes = $(herkomstClasses.join(", "));
        var $target = $(event.target);

		//highlight rows
		ViewerUtils.highlight($classes, $target);
		
		//show terugconversie tab
		if ($target.parents('.persoon').find('.teruggeconverteerdColumn').is(":hidden") && ViewerUtils.isHighlighted($target)) {
			Overzicht.toggleTerugconversie(event);
		}
	},

	
	converteerHerkomsten : function (herkomsten, persoonId) {
		var herkomstenString = "";		
		for (var i in herkomsten) {
			var herkomst = herkomsten[i];
			if ("null" == herkomst.substr(0, 4)) {
				//replace it with persoonId
				herkomst = herkomst.replace("null", persoonId);			
			}
			herkomstenString = herkomstenString.concat(herkomst, " ");
		}		
		//trim it
		herkomstenString = herkomstenString.replace(/^\s+|\s+$/g, '');
		
		return herkomstenString;
	}
};