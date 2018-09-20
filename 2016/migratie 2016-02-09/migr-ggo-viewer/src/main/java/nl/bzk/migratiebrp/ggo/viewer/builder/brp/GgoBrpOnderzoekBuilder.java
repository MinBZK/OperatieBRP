/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import java.util.LinkedHashMap;
import java.util.Map;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBetrokkenheid;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpOnderzoek;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortElement;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StatusOnderzoek;
import org.springframework.stereotype.Component;

/**
 * Voegt Onderzoeken toe.
 */
@Component
public class GgoBrpOnderzoekBuilder {
    private static final String DATUM_EINDE = "Datum einde";
    private static final String DATUM_AANVANG = "Datum aanvang";

    /**
     * Maak de onderzoeken voor dit voorkomen.
     *
     * @param voorkomen
     *            Het viewer-voorkomen
     * @param persoon
     *            Persoon
     * @param brpGroep
     *            Het entitymodel voorkomen
     * @param aNummer
     *            Het anummer van de persoon
     */
    public final void createOnderzoeken(final GgoBrpVoorkomen voorkomen, final Persoon persoon, final FormeleHistorie brpGroep, final Long aNummer) {
        for (final PersoonOnderzoek onderzoek : persoon.getPersoonOnderzoekSet()) {
            boolean vanToepassing = false;
            for (final GegevenInOnderzoek gegeven : onderzoek.getOnderzoek().getGegevenInOnderzoekSet()) {
                if (gegeven.getObjectOfVoorkomen() == brpGroep) {
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
     *
     * @param ggoBetrokkenheid
     *            De viewer-betrokkenheid.
     * @param persoon
     *            Persoon
     * @param brpGroep
     *            De entitymodel betrokkenheid
     * @param aNummer
     *            Het anummer van de persoon
     */
    public final void createOnderzoeken(final GgoBetrokkenheid ggoBetrokkenheid, final Persoon persoon, final FormeleHistorie brpGroep, final Long aNummer)
    {
        for (final PersoonOnderzoek onderzoek : persoon.getPersoonOnderzoekSet()) {
            boolean vanToepassing = false;
            for (final GegevenInOnderzoek gegeven : onderzoek.getOnderzoek().getGegevenInOnderzoekSet()) {
                if (gegeven.getObjectOfVoorkomen() == brpGroep) {
                    vanToepassing = true;
                }
            }

            if (vanToepassing) {
                ggoBetrokkenheid.getOnderzoeken().add(maakOnderzoek(onderzoek));
            }
        }
    }

    private GgoBrpOnderzoek maakOnderzoek(final PersoonOnderzoek persoonOnderzoek) {
        final GgoBrpOnderzoek onderzoek = new GgoBrpOnderzoek(persoonOnderzoek);

        for (final GegevenInOnderzoek gegeven : persoonOnderzoek.getOnderzoek().getGegevenInOnderzoekSet()) {
            // We willen velden die verwijzen naar een andere (parent) tabel niet laten zien, maar ze zijn lastig
            // te onderscheiden omdat ze in Dbobject ook van het type KOLOM zijn. Daarom als heuristiek maar ineens
            // alles overslaan wat met "Pers" begint.
            if (SoortElement.ATTRIBUUT.equals(gegeven.getSoortGegeven().getSoort()) && !gegeven.getSoortGegeven().getLaatsteNaamDeel().startsWith(
                "Pers"))
            {
                onderzoek.addBetrokkenVeld(getNaamVoorGroep(gegeven) + "." + getNaamVoorElement(gegeven));
            }
        }

        final Map<String, String> inhoud = new LinkedHashMap<>();
        if (persoonOnderzoek.getOnderzoek().getDatumAanvang() != null) {
            inhoud.put(DATUM_AANVANG, ViewerDateUtil.formatDatum(persoonOnderzoek.getOnderzoek().getDatumAanvang()));
        }
        if (persoonOnderzoek.getOnderzoek().getDatumEinde() != null) {
            inhoud.put(DATUM_EINDE, ViewerDateUtil.formatDatum(persoonOnderzoek.getOnderzoek().getDatumEinde()));
        }
        inhoud.put("Omschrijving", persoonOnderzoek.getOnderzoek().getOmschrijving());

        inhoud.put("Status", persoonOnderzoek.getOnderzoek().getDatumEinde() == null ? StatusOnderzoek.IN_UITVOERING.getNaam()
                                                                                    : StatusOnderzoek.AFGESLOTEN.getNaam());

        onderzoek.setInhoud(inhoud);
        return onderzoek;
    }

    private String getNaamVoorGroep(final GegevenInOnderzoek gegeven) {
        if (gegeven.getObjectOfVoorkomen() instanceof PersoonIndicatieHistorie) {
            final String result = ((PersoonIndicatieHistorie) gegeven.getObjectOfVoorkomen()).getPersoonIndicatie().getSoortIndicatie().getOmschrijving();
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
        if ("DatumAanvang".equals(naam)) {
            result = DATUM_AANVANG;
        } else if ("DatumEinde".equals(naam)) {
            result = DATUM_EINDE;
        } else if ("DatumAanvangGeldigheid".equals(naam)) {
            result = "Datum aanvang geldigheid";
        } else if ("DatumEindeGeldigheid".equals(naam)) {
            result = "Datum einde geldigheid";
        } else if ("TijdstipRegistratie".equals(naam)) {
            result = "Registratie";
        } else if ("TijdstipVerval".equals(naam)) {
            result = "Verval";
        } else if ("NadereAanduidingVerval".equals(naam)) {
            result = "Nadere aanduiding verval";
        } else if ("Waarde".equals(naam)) {
            result = "Heeft indicatie";
        } else {
            final GgoBrpElementEnum elementEnum = GgoBrpElementEnum.findByElement(gegeven.getSoortGegeven());
            return elementEnum == null ? naam : elementEnum.getLabel();
        }

        return result;
    }
}
