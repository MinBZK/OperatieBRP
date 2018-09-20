var OUDER_ROL = 'OUDER';
var KIND_ROL = 'KIND';

BrpRelatie = {
    bepaalRelatieLabel : function (rol) {
        var label = 'Relatie';
        if (rol == OUDER_ROL) {
            label = 'kind';
        }
        else if (rol == KIND_ROL) {
            label = 'ouders';
        }
        return label;
    },
	maakBrpRelatie : function (relatie, aNummer) {
		var $overzicht = $(_.template(overzichtTemplate, {type: 'overzicht', classes: "gesloten", aNummer : aNummer, titel: "BRP - " + relatie.soortRelatieCode.toLowerCase() + " (" + this.bepaalRelatieLabel(relatie.rolCode) + ")" }));
		var $secties = $overzicht.find('.inhoud');

		for (var i in relatie.betrokkenheden) {
			var $betrokkenheidsSectie = BrpPersoonslijst.maakSupersectie($secties, relatie.betrokkenheden[i].rol.toLowerCase());

			BrpPersoonslijst.maakTabel($betrokkenheidsSectie, "Identificatienummers", relatie.betrokkenheden[i].identificatienummersStapel, aNummer);
			BrpPersoonslijst.maakTabel($betrokkenheidsSectie, "Samengestelde naam", relatie.betrokkenheden[i].samengesteldeNaamStapel, aNummer);
			BrpPersoonslijst.maakTabel($betrokkenheidsSectie, "Geslachtsaanduiding", relatie.betrokkenheden[i].geslachtsaanduidingStapel, aNummer);
			BrpPersoonslijst.maakTabel($betrokkenheidsSectie, "Geboorte", relatie.betrokkenheden[i].geboorteStapel, aNummer);
			BrpPersoonslijst.maakTabel($betrokkenheidsSectie, "Ouderlijk gezag", relatie.betrokkenheden[i].ouderlijkGezagStapel, aNummer);
			BrpPersoonslijst.maakTabel($betrokkenheidsSectie, "Ouder", relatie.betrokkenheden[i].ouderStapel, aNummer);
		}

		BrpPersoonslijst.maakTabel($secties, "Relatie", relatie.relatieStapel, aNummer);

		return $overzicht;
	}
};
