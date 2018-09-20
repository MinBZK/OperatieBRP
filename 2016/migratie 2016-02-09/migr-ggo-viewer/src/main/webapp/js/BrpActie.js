BrpActie = {
		maakTabelRegelActies : function($regel, ggoBrpVoorkomen) {
			//add actie links
			if (ggoBrpVoorkomen.actieInhoud != null && $.isPlainObject(ggoBrpVoorkomen.actieInhoud)) {
				var popupParam = {key:ggoBrpVoorkomen, subkey:"actieInhoud", sectie:null};
				var actieTitel = "Actie inhoud";
				popupParam.sectie = this.maakActieTabel(actieTitel, ggoBrpVoorkomen.actieInhoud, popupParam);
				var popupId = ViewerUtils.bepaalBrpPopupId(popupParam);
				$regel.find('.Acties_inhoud').parent().append('<td class="Acties_inhoud"><a class="actie" title="actie inhoud" href="#' + popupId + '">' + actieTitel + '</a></td>');			
				$regel.find('a[href="#' + popupId + '"]').on('click', null, popupParam, Overzicht.plaatsPopup.bind(Overzicht));
			}
			if (ggoBrpVoorkomen.actieVerval != null && $.isPlainObject(ggoBrpVoorkomen.actieVerval)) {
				var popupParam = {key:ggoBrpVoorkomen, subkey:"actieVerval", sectie:null};
				var actieTitel = "Actie verval";
				popupParam.sectie = this.maakActieTabel(actieTitel, ggoBrpVoorkomen.actieVerval, popupParam);
				var popupId = ViewerUtils.bepaalBrpPopupId(popupParam);
				$regel.find('.Acties_verval').parent().append('<td class="Acties_verval"><a class="actie" title="actie verval" href="#' + popupId + '">' + actieTitel + '</a></td>');			
				$regel.find('a[href="#' + popupId + '"]').on('click', null, popupParam, Overzicht.plaatsPopup.bind(Overzicht));
			}
			if (ggoBrpVoorkomen.actieGeldigheid != null && $.isPlainObject(ggoBrpVoorkomen.actieGeldigheid)) {
				var popupParam = {key:ggoBrpVoorkomen, subkey:"actieGeldigheid", sectie:null};
				var actieTitel = "Actie aanpassing geldigheid";
				popupParam.sectie = this.maakActieTabel(actieTitel, ggoBrpVoorkomen.actieGeldigheid, popupParam);
				var popupId = ViewerUtils.bepaalBrpPopupId(popupParam);
				$regel.find('.Acties_aanpassingGeldigheid').parent().append('<td class="Acties_aanpassingGeldigheid"><a class="actie" title="actie geldigheid" href="#' + popupId + '">' + actieTitel + '</a></td>');			
				$regel.find('a[href="#' + popupId + '"]').on('click', null, popupParam, Overzicht.plaatsPopup.bind(Overzicht));
			}
			if ($regel.find('.actie').length == 0) {
				//geen acties toegevoegd, dan maar wel een td invoegen
				$regel.find('.Acties_inhoud').parent().append('<td>&nbsp;</td>');
			}
		},
		
		maakActieTabel : function (titel, brpActie, popupParam) {			
			//popup div
			var popupId = ViewerUtils.bepaalBrpPopupId(popupParam);
			var actieTitel = titel + " bij: " + popupParam.key.label;
			var $brpActiePopupDiv = $(_.template(brpPopupTemplate, 
					{popupId: popupId, popupTitel : actieTitel}));
			
			var $actiecontent = $brpActiePopupDiv.find('.popupcontent');
			
			//actie			
			$actiecontent.append(this.maakActieDataSectie(titel, brpActie.inhoud));
			
			//administratieve handeling
			$actiecontent.append(this.maakActieDataSectie("Administratieve handeling", brpActie.administratieveHandeling));
			//actie / bron
			for (var i in brpActie.actieBronnen) {
				var actieBron = brpActie.actieBronnen[i];
				$actiecontent.append(this.maakActieDataSectie("Actie / Bron", actieBron));
			}

			//documenten
			for (var i in brpActie.documenten) {
				var document = brpActie.documenten[i];
				for (var j in document.voorkomens) {
					$actiecontent.append(this.maakActieDataSectie("Document", document.voorkomens[j].inhoud));
				}
			}
			
			return $brpActiePopupDiv;
		},
		
		maakActieDataSectie : function (titel, inhoud) {
			var $sectieActie = $(_.template(categorieTemplate, {type: 'supersectie', titel : titel }));
			var $tableActie = $('<table summary="' + titel + '" />');
			$sectieActie.find('.inhoud').append($tableActie);

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
				if (key == 'Geldigheid' || key == 'Registratie - verval') {
					var datum = val.split(' - ');
					regelActie += _.template(tdDatumTemplate, {datumVan: datum[0], datumTot: datum[1]});
				} else {
					regelActie += _.template(tdTemplate, {title: title, val: val});				
				}				
				regelActie = regelActie +  '</tr>\r\n';
			}
			var $regelActie = $(regelActie);
			$tableActie.append($regelActie);
			return $sectieActie;
		}
};
