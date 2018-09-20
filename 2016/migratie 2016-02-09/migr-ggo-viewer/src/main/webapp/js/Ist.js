Ist = {
		maakIst : function($stapelSectieInhoud, ggoBrpVoorkomen, label, voorkomenNr) {
			var ggoBrpVoorkomenKey = {label:ggoBrpVoorkomen.label + "_" + ggoBrpVoorkomen.aNummer, aNummer:ggoBrpVoorkomen.aNummer, brpStapelNr:voorkomenNr };
			var popupParam = {key:ggoBrpVoorkomenKey, subkey:"ist", sectie:null};
			popupParam.sectie = this.maakPopup(ggoBrpVoorkomen.istInhoud, label, popupParam);
			$stapelSectieInhoud.find('a').on('click', null, popupParam, Overzicht.plaatsPopup.bind(Overzicht));
		},

		maakPopup : function (istInhoud, label, popupParam) {
			var $istPopupDiv = $(_.template(brpPopupTemplate,
					{popupId: ViewerUtils.bepaalBrpPopupId(popupParam), popupTitel : 'Alle gearchiveerde voorkomens uit GBA bij: ' + label}));
			this.maakIstDataSecties($istPopupDiv.find('.popupcontent'), istInhoud);
			return $istPopupDiv;
		},

		maakIstDataSecties : function ($el, istInhoud) {
			if (istInhoud == null) return;

			var gesloten = 'gesloten';

			// Bij exact 1 voorkomen wel openklappen, staat mooier
			if (istInhoud.length == 1) {
				if (istInhoud[0].voorkomens.length == 1) {
					gesloten = '';
				}
			}

			//data
			for (var i in istInhoud) {
				var stapel = istInhoud[i];

				for (var j in stapel.voorkomens) {
					var voorkomen = stapel.voorkomens[j];

					var regel = '';

					var catNr = ViewerUtils.formatCategorieNr(voorkomen.categorieLabelNr);
	                var sectieTitel = '<span class="code">' + catNr + '</span><span class="label">' + voorkomen.label + '</span>';
	                var sectieTitel = catNr + ' ' + voorkomen.label;

					var $istSectie = $(_.template(categorieTemplate, {type: 'supersectie', classes: gesloten, titel : sectieTitel, info: voorkomen.datumAanvangGeldigheid }));
					var $istTable = $('<table summary="' + voorkomen.label + '" />');
					$istSectie.find('.inhoud').append($istTable);
	
					regel = regel +  '</tr>\r\n';
					regel = regel + '<tr class="headers">';

					for (var key in voorkomen.inhoud) {
						//header
		                var title = key;
		                regel = regel + _.template(thTemplate, {label: title});
	
						var title = voorkomen.inhoud[key];
						val = _.escape(title);
						regel = regel + _.template(tdTemplate, {key: key, title: title, val: val});
	
						regel = regel +  '</tr>\r\n';
					}
	
					var $regel = $(regel);
					$istTable.append($regel);

					$istSectie.find('.inhoud').append(Ist.maakIstAdministratieveHandeling(voorkomen.administratieveHandeling));
					$el.append($istSectie);
				}
			}
		},
		
		maakIstAdministratieveHandeling : function (inhoud) {
			var $sectieAh = $(_.template(categorieTemplate, {type: 'sectie', titel : 'Administratieve handeling' }));
			var $tableAh = $('<table summary="Administratieve handeling" />');
			$sectieAh.find('.inhoud').append($tableAh);

			//data
			var regelActie = '';
			for (var key in inhoud) {
			    regelActie = regelActie + '<tr class="headers">';
				var val = inhoud[key];
				
				//header
                var title = key;
                regelActie = regelActie + _.template(thTemplate, {label: title});
				
				var title = val;
				val = _.escape(val);
				regelActie += _.template(tdTemplate, {title: title, val: val});				
				regelActie = regelActie +  '</tr>\r\n';
			}
			var $regelActie = $(regelActie);
			$tableAh.append($regelActie);
			
			return $sectieAh;
		}
};