ViewerUtils =
{
		bepaalHerkomstCategorie : function(herkomst) {
			var catNr = herkomst.categorie == null ? -1 : herkomst.categorie.substring(10);
			if (catNr > 50) {
				catNr = catNr - 50;
				if (catNr < 10) {
					catNr = "0" + catNr;
				}
			}
			return catNr;
		},
		
		bepaalHerkomstClassName : function(herkomst, aNummer) {
			var herkomstClass = [];
			var catNr = ViewerUtils.bepaalHerkomstCategorie(herkomst);			
			var stapelNr = herkomst.stapel;
			var volgNr = herkomst.voorkomen;
			herkomstClass.push(aNummer + '_' + catNr + '_' + stapelNr + '_' + volgNr);
			
			return herkomstClass.join(" ");
		},
		
		bepaalHerkomstLo3Class : function(voorkomen, aNummer) {
            var herkomstArray = [];
            // Eigenlijk willen we van alles een herkomst hebben, maar de terugconversie
            // heeft die momenteel niet. Hopelijk komt dit nog in enige vorm terug.
            if (voorkomen.lo3Herkomst != null) {
            	herkomstArray.push(ViewerUtils.bepaalHerkomstClassName(voorkomen.lo3Herkomst, aNummer));
            }
            return herkomstArray.join(" ");
        },
		
		bepaalHerkomstClass : function(voorkomen, aNummer) {
		    var herkomstArray = [];
		    if (!empty(voorkomen.actieInhoud) && !empty(voorkomen.actieInhoud.lo3Herkomst)) {
		        herkomstArray.push(ViewerUtils.bepaalHerkomstClassName(voorkomen.actieInhoud.lo3Herkomst, aNummer));
		    }
            return herkomstArray.join(" ");
		},
				
		gebruikVeldBijMatch : function (match, veld, key, val) {
			if (key.match(match) && $.isPlainObject(val) && val != null) {
				return val[veld];
			} else {
				return val;
			}
		},
		
		gebruikVeldBijMatchCustom : function (key, originalVal, searchObj) {
			var returnVal = originalVal;
			if (!empty(searchObj)) {
				if ($.isPlainObject(searchObj)) {
					if (key == 'partijCode') {
						var gemeenteCode = empty(searchObj['gemeenteCode']) ? "" : searchObj['gemeenteCode'];
						var naam = empty(searchObj['naam']) ? "" : '(' + searchObj['naam'] + ')';
						returnVal = gemeenteCode + naam;				
					}
					if (key == 'geldigheid') {
						var datA = empty(searchObj['datumAanvangGeldigheid']) ? "" : searchObj['datumAanvangGeldigheid'];
						var datE = empty(searchObj['datumEindeGeldigheid']) ? "" : searchObj['datumEindeGeldigheid'];
						returnVal = datA + ' - ' + datE;
					} 
					if (key == 'registratie') {
						var datR = empty(searchObj['datumTijdRegistratie']) ? "" : searchObj['datumTijdRegistratie'];
						var datV = empty(searchObj['datumTijdVerval']) ? "" : searchObj['datumTijdVerval'];
						returnVal = ViewerUtils.formatDateTime(datR) + ' - ' + ViewerUtils.formatDateTime(datV);
					}
				} else {
					if (key == 'datumTijdOntlening' || key == 'datumTijdRegistratie') {
						var datum = empty(searchObj) ? "" : searchObj;
						returnVal = ViewerUtils.formatDateTime(datum);
					}
				}				
			}			
			return returnVal;
		},
		
		formatDateTime : function (dateTime) {
			var matches = dateTime.toString().match(/(\d{8})(\d{2})(\d{2})(\d{2})/);
			if (matches) {
				return matches[1] + ' ' + matches[2] + ':' + matches[3] + ':' + matches[4];
			} else {
				return dateTime;
			}
		},

		addLineToTable : function($table, data, onClick, onClickData) {
			var $row = $(_.template(trTemplate, data));
			$row.find('td').on('click', null, onClickData, onClick);
			$table.append($row);
		},
		
		bepaalBrpActieId : function(popupParam) {
			var groepNaam = popupParam.groep.replace(/ /g, '-');
			return  popupParam.aNummer + '_' + groepNaam + '_' + popupParam.volgNr + '_' + popupParam.actie;
		},

		maakNaamUitKey: function (key) {
		    var newKey = key.replace(/([A-Z])/g, ' $1').toLowerCase().replace(/ code$/, '').replace(/^indicatie/, '').replace(/p k/, 'pk');
		    if (newKey == 'registratie') {
		        newKey = newKey + ' - verval';
		    }
		    return newKey;
		},

		maakWaardeOpBijLeegOfBoolean: function (val) {
			if (empty(val)) return "";
			if (!isNaN(parseFloat((val)) && isFinite((val)))) return val;
			if (val === true) return 'ja';
			if (val === false) return 'nee';
			return val;
		}
};