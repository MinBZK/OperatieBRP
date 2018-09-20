BrpRelatie = {
		maakTabelRegel : function($regel, ggoBrpVoorkomen) {
			//add link
			if (ggoBrpVoorkomen.meer != null && $.isPlainObject(ggoBrpVoorkomen.meer)) {
				//maak custom key voor brpPopupId
				var ggoBrpVoorkomenKey = {label:ggoBrpVoorkomen.label + "_" + ggoBrpVoorkomen.inhoud['Administratienummer'], aNummer:ggoBrpVoorkomen.aNummer, brpStapelNr:ggoBrpVoorkomen.brpStapelNr };
				var popupParam = {key:ggoBrpVoorkomenKey, subkey:"relatiesMeer", sectie:null};
				popupParam.sectie = this.maakRelatieTabel(ggoBrpVoorkomen.label, ggoBrpVoorkomen.meer, popupParam);
				var brpPopupId = ViewerUtils.bepaalBrpPopupId(popupParam);
				$regel.find('.Relatie_meer').parent().append('<td class="Relatie_meer"><a class="actie" title="Meer" href="#' + brpPopupId + '">Meer</a></td>');			
				$regel.find('a[href="#' + brpPopupId + '"]').on('click', null, popupParam, Overzicht.plaatsPopup.bind(Overzicht));
			}
			if ($regel.find('.actie').length == 0) {
				//geen acties toegevoegd, dan maar wel een td invoegen
				$regel.find('.Relatie_meer').parent().append('<td class="Relatie_meer">&nbsp;</td>');
			}
		},
		
		maakRelatieTabel : function (titel, brpRelatie, popupParam) {			
			//actie		
			var $sectieRelatieData = this.maakRelatieDataSectie(titel, brpRelatie);
						
			//popup div
			var brpPopupId = ViewerUtils.bepaalBrpPopupId(popupParam);
			var $brpPopupDiv = $(_.template(brpPopupTemplate, 
					{popupId: brpPopupId, popupTitel : titel}));
			$brpPopupDiv.append($sectieRelatieData);
			
			return $brpPopupDiv;
		},		
		
		maakRelatieDataSectie : function (titel, inhoud) {
			var $tableRelatie = $('<table summary="' + titel + '" />');
		
			//data
			var regel = '';
			for (var key in inhoud) {
				regel = regel + '<tr class="headers">';
				var val = inhoud[key];
				
				//header
                var title = key;
                regel = regel + _.template(thTemplate, {code: title});
				
				var title = val;
				val = _.escape(val);
				regel = regel + _.template(tdTemplate, {key: key, title: title, val: val});
				
				regel = regel +  '</tr>\r\n';
			}
			var $regel = $(regel);
			$tableRelatie.append($regel);
			return $tableRelatie;
		}
};
