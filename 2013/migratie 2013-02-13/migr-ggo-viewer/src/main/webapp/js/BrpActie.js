BrpActie = {
		maakTabelRegelActies : function($table, titel, voorkomen, volgNr, aNummer) {
			//add actie links
			var $actieRegels = $('<td>');			
			if (voorkomen.actieInhoud != null && $.isPlainObject(voorkomen.actieInhoud)) {
				var popupParam = {aNummer:aNummer, groep:titel, volgNr:volgNr, actie:"actieInhoud", sectie:null};
				popupParam.sectie = this.maakActieTabel("Actie inhoud", voorkomen.actieInhoud, popupParam);
				var brpActieId = ViewerUtils.bepaalBrpActieId(popupParam);
				$actieRegels.append('<a title="actie inhoud" href="#' + brpActieId + '">actie inhoud</a>   ');			
				$actieRegels.find('a[href="#' + brpActieId + '"]').on('click', null, popupParam, Overzicht.plaatsPopup.bind(Overzicht));
			}
			if (voorkomen.actieVerval != null && $.isPlainObject(voorkomen.actieVerval)) {
				var popupParam = {aNummer:aNummer, groep:titel, volgNr:volgNr, actie:"actieVerval", sectie:null};
				popupParam.sectie = this.maakActieTabel("Actie verval", voorkomen.actieVerval, popupParam);
				var brpActieId = ViewerUtils.bepaalBrpActieId(popupParam);
				$actieRegels.append('<a title="actie verval" href="#' + brpActieId + '">actie verval</a>   ');			
				$actieRegels.find('a[href="#' + brpActieId + '"]').on('click', null, popupParam, Overzicht.plaatsPopup.bind(Overzicht));
			}
			if (voorkomen.actieGeldigheid != null && $.isPlainObject(voorkomen.actieGeldigheid)) {
				var popupParam = {aNummer:aNummer, groep:titel, volgNr:volgNr, actie:"actieGeldigheid", sectie:null};
				popupParam.sectie = this.maakActieTabel("Actie geldigheid", voorkomen.actieGeldigheid, popupParam);
				var brpActieId = ViewerUtils.bepaalBrpActieId(popupParam);
				$actieRegels.append('<a title="actie geldigheid" href="#' + brpActieId + '">actie geldigheid</a>   ');			
				$actieRegels.find('a[href="#' + brpActieId + '"]').on('click', null, popupParam, Overzicht.plaatsPopup.bind(Overzicht));
			}
			$actieRegels.append('</td>');
			return $actieRegels
		},
		
		maakActieTabel : function (titel, brpActie, popupParam) {			
					
			var $sectieActieData = this.maakActieDataSectie(titel, brpActie);
			var $sectieActieDocumenten = $(_.template(overzichtTemplate, {type: 'supersectie', classes : "gesloten", titel: "Documenten"}));
			for (var i in brpActie.documentStapels) {
				this.maakActieDocSectie($sectieActieDocumenten, "Document", brpActie.documentStapels[i]);
			}
			
			var brpActieId = ViewerUtils.bepaalBrpActieId(popupParam);
			var $brpActiePopupDiv = $(_.template(brpActieTemplate, 
					{brpActieId: brpActieId, aNummer : popupParam.aNummer, groep: popupParam.groep}));
			$brpActiePopupDiv.append($sectieActieData);
			$brpActiePopupDiv.append($sectieActieDocumenten);
			
			return $brpActiePopupDiv;
		},
		
		maakActieTabelHeader: function($table, keys) {
			var regel = '<tr class="header">';
			for (var i in keys) {
				var label = ViewerUtils.maakNaamUitKey(keys[i]);
				regel = regel + _.template(thTemplate, {clss: keys[i], code: label});
			}			
			$table.append(regel + '</tr>\r\n');
		},
		
		maakActieDataSectie : function (titel, brpActie) {
			var $sectieActie = $(_.template(overzichtTemplate, {type: 'sectie', classes: "gesloten", titel : titel }));
			var $tableActie = $('<table summary="' + titel + '" />');
			$sectieActie.find('.inhoud').append($tableActie);
			
			//keys
			var keys = [];
			for (var key in brpActie) {
				if (!key.match(/id|sortering|documentStapels|lo3Herkomst/) && 
						$.inArray(key, keys) == -1 && 
						!empty(brpActie[key])) {
					keys.push(key);
				}
			}
			//header
			this.maakActieTabelHeader($tableActie, keys);
			//data
			var regelActie = '<tr">';
			for (var i in keys) {
				var key = keys[i];
				var val = brpActie[key];

				val = ViewerUtils.gebruikVeldBijMatch(/soortActieCode/, 'code', key, val);
				val = ViewerUtils.gebruikVeldBijMatch(/verdragCode/, 'omschrijving', key, val);
				val = ViewerUtils.gebruikVeldBijMatchCustom(key, val, val);
				
				if (val == null || val == undefined) val = "";
				
				var title = val;
				val = _.escape(val);
				regelActie = regelActie + _.template(tdTemplate, {key: key, title: title, val: val});
			}
			var $regelActie = $(regelActie + '</tr>\r\n');
			$tableActie.append($regelActie);
			
			return $sectieActie;
		},
		
		maakActieDocSectie : function($sectie, titel, stapel) {
			if (stapel != null) {
				var voorkomens = stapel.groepen;

				var $sectieDoc = $(_.template(overzichtTemplate, {type: 'sectie', classes: "gesloten", titel : titel }));
				var $tableDoc = $('<table summary="' + titel + '" />');
				$sectieDoc.find('.inhoud').append($tableDoc);

				var keys = BrpPersoonslijst.vindGebruikteVelden(stapel.groepen);

				for (var i = voorkomens.length -1; i >= 0; i--) {
					this.maakActieTabelHeader($tableDoc, keys);
					this.maakActieDocTabelRegel($tableDoc, keys, voorkomens[i]);
				}

				$sectie.find('.supersectie.inhoud').append($sectieDoc);
			}
		},
		
		maakActieDocTabelRegel : function($table, keys, voorkomen) {
			var regel = '<tr>';
			for (var i in keys) {
				var key = keys[i];
				var val = voorkomen.inhoud[key];
				if (val == null) {
					val = voorkomen.historie[key];
					val = ViewerUtils.gebruikVeldBijMatchCustom(key, val, voorkomen.historie);
				} else {				
					val = ViewerUtils.gebruikVeldBijMatch(/soortDocumentCode/, 'code', key, val);				
					val = ViewerUtils.gebruikVeldBijMatchCustom(key, val, val);
				}
				
				if (empty(val)) val = "";
				
				var title = val;
				val = _.escape(val);
				regel = regel + _.template(tdTemplate, {key: keys[i], title: title, val: val});
			}
			var $regel = $(regel + '</tr>\r\n');			
			$table.append($regel);
		},
};
