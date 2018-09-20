BrpOnderzoek = {
		maakTabelRegelOnderzoeken : function($regel, ggoBrpVoorkomen) {
			if (ggoBrpVoorkomen.onderzoeken != null) {
				for (var i in ggoBrpVoorkomen.onderzoeken) {
					var onderzoek = ggoBrpVoorkomen.onderzoeken[i];

					var popupParam = {key: ggoBrpVoorkomen, subkey: onderzoek.inhoud['Omschrijving'], sectie: null};
					var onderzoekTitel = "Onderzoek";
					popupParam.sectie = this.maakOnderzoekTabel(onderzoekTitel, onderzoek, popupParam);
					var brpOnderzoekId = ViewerUtils.bepaalBrpPopupId(popupParam);
					$regel.find('.Onderzoeken_' + i).parent().append('<td class="Onderzoeken"><a class="onderzoek" title="onderzoek" href="#' + brpOnderzoekId + '">' + onderzoekTitel + '</a></td>');			
					$regel.find('a[href="#' + brpOnderzoekId + '"]').on('click', null, popupParam, Overzicht.plaatsPopup.bind(Overzicht));
				}
			}
		},

		maakOnderzoekTabel : function (titel, brpOnderzoek, popupParam) {			
			//popup div
			var popupId = ViewerUtils.bepaalBrpPopupId(popupParam);
			var onderzoekTitel = titel + " bij: " + popupParam.key.label;
			var $brpOnderzoekPopupDiv = $(_.template(brpPopupTemplate, 
					{popupId: popupId, popupTitel : onderzoekTitel}));

			var $onderzoekcontent = $brpOnderzoekPopupDiv.find('.popupcontent');

			// onderzoek 			
			$onderzoekcontent.append(this.maakOnderzoekDataSectie(titel, brpOnderzoek.inhoud));

			// betrokken velden
			$onderzoekcontent.append(this.maakBetrokkenVeldenDataSectie("Betrokken velden", brpOnderzoek.betrokkenVelden));

			return $brpOnderzoekPopupDiv;
		},
		
		maakOnderzoekDataSectie : function (titel, inhoud) {
			var $sectieOnderzoek = $(_.template(categorieTemplate, {type: 'supersectie', titel : titel }));
			var $tableOnderzoek = $('<table summary="' + titel + '" />');
			$sectieOnderzoek.find('.inhoud').append($tableOnderzoek);

			//data
			var regelOnderzoek = '';
			for (var key in inhoud) {
			    regelOnderzoek = regelOnderzoek + '<tr class="headers">';
				var val = inhoud[key];
				
				//header
                var title = key;
                regelOnderzoek = regelOnderzoek + _.template(thTemplate, {label: title});
				
				var title = val;
				val = _.escape(val);
				if (key == 'Geldigheid' || key == 'Registratie - verval') {
					var datum = val.split(' - ');
					regelOnderzoek += _.template(tdDatumTemplate, {datumVan: datum[0], datumTot: datum[1]});
				} else {
					regelOnderzoek += _.template(tdTemplate, {title: title, val: val});				
				}				
				regelOnderzoek = regelOnderzoek +  '</tr>\r\n';
			}
			var $regelOnderzoek = $(regelOnderzoek);
			$tableOnderzoek.append($regelOnderzoek);
			return $sectieOnderzoek;
		},

		maakBetrokkenVeldenDataSectie : function (titel, betrokkenVelden) {
			var $sectieOnderzoek = $(_.template(categorieTemplate, {type: 'supersectie', titel : titel }));
			var $tableOnderzoek = $('<table summary="' + titel + '" />');
			$sectieOnderzoek.find('.inhoud').append($tableOnderzoek);

			//data
			var regelOnderzoek = '';
			for (var i in betrokkenVelden) {
				var betrokkenVeld = betrokkenVelden[i].split('.');

			    regelOnderzoek = regelOnderzoek + '<tr class="headers">';
				
				//header
                var title = betrokkenVeld[0]; // groepsnaam
                regelOnderzoek = regelOnderzoek + _.template(thTemplate, {label: title});
				
				var title = betrokkenVelden[i].replace(betrokkenVeld[0] + '.', ''); // veldnaam
				var val = _.escape(title);
				regelOnderzoek += _.template(tdTemplate, {title: title, val: val});				
				regelOnderzoek = regelOnderzoek +  '</tr>\r\n';
			}
			var $regelOnderzoek = $(regelOnderzoek);
			$tableOnderzoek.append($regelOnderzoek);
			return $sectieOnderzoek;
		},

};
