/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.OnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElement;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBetrokkenheid;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpOnderzoek;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Voegt Onderzoeken toe.
 */
@Component
public class GgoBrpOnderzoekBuilder {
    private static final String DATUM_EINDE = "Datum einde";
    private static final String DATUM_AANVANG = "Datum aanvang";

    /**
     * Maak de onderzoeken voor dit voorkomen.
     * @param voorkomen Het viewer-voorkomen
     * @param persoon Persoon
     * @param brpGroep Het entitymodel voorkomen
     */
    final void createOnderzoeken(final GgoBrpVoorkomen voorkomen, final Persoon persoon, final FormeleHistorie brpGroep) {
        for (final Onderzoek onderzoek : persoon.getOnderzoeken()) {
            boolean vanToepassing = false;
            for (final GegevenInOnderzoek gegeven : onderzoek.getGegevenInOnderzoekSet()) {
                if (gegeven.getEntiteitOfVoorkomen() == brpGroep) {
                    vanToepassing = true;
                }
            }

            if (vanToepassing) {
                voorkomen.addOnderzoek(maakOnderzoek(onderzoek));
            }
        }
    }

    /**
     * Maak onderzoek aan voor betrokkenheid.
     * @param ggoBetrokkenheid De viewer-betrokkenheid.
     * @param persoon Persoon
     * @param brpGroep De entitymodel betrokkenheid
     */
    final void createOnderzoeken(final GgoBetrokkenheid ggoBetrokkenheid, final Persoon persoon, final FormeleHistorie brpGroep) {
        for (final Onderzoek onderzoek : persoon.getOnderzoeken()) {
            boolean vanToepassing = false;
            for (final GegevenInOnderzoek gegeven : onderzoek.getGegevenInOnderzoekSet()) {
                if (gegeven.getEntiteitOfVoorkomen() == brpGroep) {
                    vanToepassing = true;
                }
            }

            if (vanToepassing) {
                ggoBetrokkenheid.getOnderzoeken().add(maakOnderzoek(onderzoek));
            }
        }
    }

    private GgoBrpOnderzoek maakOnderzoek(final Onderzoek onderzoek) {
        final GgoBrpOnderzoek ggoBrpOnderzoek = new GgoBrpOnderzoek(onderzoek);

        for (final GegevenInOnderzoek gegeven : onderzoek.getGegevenInOnderzoekSet()) {
            // We willen velden die verwijzen naar een andere (parent) tabel niet laten zien, maar ze zijn lastig
            // te onderscheiden omdat ze in Dbobject ook van het type KOLOM zijn. Daarom als heuristiek maar ineens
            // alles overslaan wat met "Pers" begint.
            if (SoortElement.ATTRIBUUT.equals(gegeven.getSoortGegeven().getSoort()) && !gegeven.getSoortGegeven().getLaatsteNaamDeel().startsWith("Pers")) {
                ggoBrpOnderzoek.addBetrokkenVeld(getNaamVoorGroep(gegeven) + "." + getNaamVoorElement(gegeven));
            }
        }

        final OnderzoekHistorie onderzoekHistorie = onderzoek.getOnderzoekHistorieSet().iterator().next();
        final Map<String, String> inhoud = new LinkedHashMap<>();
        if (onderzoekHistorie.getDatumAanvang() != null) {
            inhoud.put(DATUM_AANVANG, ViewerDateUtil.formatDatum(onderzoekHistorie.getDatumAanvang()));
        }
        if (onderzoekHistorie.getDatumEinde() != null) {
            inhoud.put(DATUM_EINDE, ViewerDateUtil.formatDatum(onderzoekHistorie.getDatumEinde()));
        }
        inhoud.put("Omschrijving", onderzoekHistorie.getOmschrijving());

        inhoud.put("Status",
                onderzoekHistorie.getDatumEinde() == null ? StatusOnderzoek.IN_UITVOERING.getNaam() : StatusOnderzoek.AFGESLOTEN.getNaam());

        ggoBrpOnderzoek.setInhoud(inhoud);
        return ggoBrpOnderzoek;
    }

    private String getNaamVoorGroep(final GegevenInOnderzoek gegeven) {
        if (gegeven.getEntiteitOfVoorkomen() instanceof PersoonIndicatieHistorie) {
            final String result = ((PersoonIndicatieHistorie) gegeven.getEntiteitOfVoorkomen()).getPersoonIndicatie().getSoortIndicatie().getOmschrijving();
            return result.substring(0, result.length() - 1);
        } else {
            final Element soortGegeven = gegeven.getSoortGegeven();
            final Element groep;
            if (soortGegeven.getSoort().equals(SoortElement.ATTRIBUUT)) {
                groep = soortGegeven.getGroep();
            } else {
                groep = soortGegeven;
            }
            final GgoBrpGroepEnum result = GgoBrpGroepEnum.findByElement(groep);
            return result == null ? groep.getLaatsteNaamDeel() : result.getLabel();
        }
    }

    private String getNaamVoorElement(final GegevenInOnderzoek gegeven) {
        final String result;
        final String naam = gegeven.getSoortGegeven().getLaatsteNaamDeel();
        switch (naam) {
            case "DatumAanvang":
                result = DATUM_AANVANG;
                break;
            case "DatumEinde":
                result = DATUM_EINDE;
                break;
            case "DatumAanvangGeldigheid":
                result = "Datum aanvang geldigheid";
                break;
            case "DatumEindeGeldigheid":
                result = "Datum einde geldigheid";
                break;
            case "TijdstipRegistratie":
                result = "Registratie";
                break;
            case "TijdstipVerval":
                result = "Verval";
                break;
            case "NadereAanduidingVerval":
                result = "Nadere aanduiding verval";
                break;
            case "Waarde":
                result = "Heeft indicatie";
                break;
            default:
                final GgoBrpElementEnum elementEnum = GgoBrpElementEnum.findByElement(gegeven.getSoortGegeven());
                return elementEnum == null ? naam : elementEnum.getLabel();
        }
        return result;
    }
}
