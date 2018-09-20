/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpLeveringsautorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpPartij;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstAttenderingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstSelectieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelLo3RubriekInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;

import org.springframework.stereotype.Component;

/**
 * AutorisatieConversie.
 */
@Component
public class AutorisatieConversie {

    /**
     * Vul afgeleide gegevens in inhoudelijke gegevens obv historie.
     *
     * @param brpAutorisatie
     *            autorisatie
     * @return autorisaie
     */
    public final BrpAutorisatie vulAfgeleideGegevens(final BrpAutorisatie brpAutorisatie) {
        return new BrpAutorisatie(verwerkPartij(brpAutorisatie.getPartij()), verwerkLeveringsautorisaties(brpAutorisatie.getLeveringsAutorisatieLijst()));
    }

    private BrpPartij verwerkPartij(final BrpPartij partij) {
        return new BrpPartij(partij.getId(), partij.getNaam(), partij.getPartijCode(), verwerkStapel(partij.getPartijStapel()));
    }

    private List<BrpLeveringsautorisatie> verwerkLeveringsautorisaties(final List<BrpLeveringsautorisatie> leveringsautorisaties) {
        final List<BrpLeveringsautorisatie> brpLeveringsautorisaties = new ArrayList<>();
        for (final BrpLeveringsautorisatie leveringsautorisatie : leveringsautorisaties) {

            brpLeveringsautorisaties.add(verwerkLeveringsautorisatie(leveringsautorisatie));
        }
        return leveringsautorisaties;
    }

    private BrpLeveringsautorisatie verwerkLeveringsautorisatie(final BrpLeveringsautorisatie leveringsautorisatie) {
        return new BrpLeveringsautorisatie(
            leveringsautorisatie.getStelsel(),
            leveringsautorisatie.getIndicatieModelautorisatie(),
            verwerkStapel(leveringsautorisatie.getLeveringsautorisatieStapel()),
            verwerkDienstbundels(leveringsautorisatie.getDienstbundels()));
    }

    private List<BrpDienstbundel> verwerkDienstbundels(final List<BrpDienstbundel> dienstbundels) {
        final List<BrpDienstbundel> brpDienstbundels = new ArrayList<>();
        for (final BrpDienstbundel dienstbundel : dienstbundels) {

            brpDienstbundels.add(verwerkDienstbundel(dienstbundel));
        }
        return dienstbundels;
    }

    private BrpDienstbundel verwerkDienstbundel(final BrpDienstbundel dienstbundel) {
        return new BrpDienstbundel(
            verwerkDiensten(dienstbundel.getDiensten()),
            verwerkDienstbundelLo3Rubrieken(dienstbundel.getLo3Rubrieken()),
            verwerkStapel(dienstbundel.getDienstbundelStapel()));
    }

    private List<BrpDienstbundelLo3Rubriek> verwerkDienstbundelLo3Rubrieken(final List<BrpDienstbundelLo3Rubriek> dienstbundelExpressies) {
        final List<BrpDienstbundelLo3Rubriek> brpDienstbundelLo3Rubrieken = new ArrayList<>();
        for (final BrpDienstbundelLo3Rubriek dienstbundelExpressie : dienstbundelExpressies) {
            brpDienstbundelLo3Rubrieken.add(verwerkDienstbundelLo3Rubriek(dienstbundelExpressie));
        }
        return brpDienstbundelLo3Rubrieken;
    }

    private BrpDienstbundelLo3Rubriek verwerkDienstbundelLo3Rubriek(final BrpDienstbundelLo3Rubriek dienstbundelExpressie) {
        return new BrpDienstbundelLo3Rubriek(
            dienstbundelExpressie.getConversieRubriek(),
            dienstbundelExpressie.getActief(),
            verwerkStapel(dienstbundelExpressie.getDienstbundelLo3RubriekStapel()));
    }

    private List<BrpDienst> verwerkDiensten(final List<BrpDienst> diensten) {
        final List<BrpDienst> brpDiensten = new ArrayList<>();
        for (final BrpDienst dienst : diensten) {

            brpDiensten.add(verwerkDienst(dienst));
        }
        return diensten;
    }

    private BrpDienst verwerkDienst(final BrpDienst dienst) {
        return new BrpDienst(
            dienst.getEffectAfnemersindicatie(),
            dienst.getSoortDienstCode(),
            verwerkStapel(dienst.getDienstStapel()),
            verwerkStapel(dienst.getDienstAttenderingStapel()),
            verwerkStapel(dienst.getDienstSelectieStapel()));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private <T extends BrpGroepInhoud> BrpStapel<T> verwerkStapel(final BrpStapel<T> stapel) {
        final List<BrpGroep<T>> groepen = new ArrayList<>();

        for (final BrpGroep<T> groep : stapel) {
            groepen.add(verwerkGroep(groep));
        }

        return new BrpStapel<>(groepen);
    }

    @SuppressWarnings("unchecked")
    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkGroep(final BrpGroep<T> groep) {
        final T inhoud = groep.getInhoud();
        final BrpHistorie historie = groep.getHistorie();
        final T result;

        if (inhoud instanceof BrpLeveringsautorisatieInhoud) {
            final BrpLeveringsautorisatieInhoud leveringsautorisatieInhoud = (BrpLeveringsautorisatieInhoud) inhoud;
            result =
                    (T) new BrpLeveringsautorisatieInhoud(
                        leveringsautorisatieInhoud.getNaam(),
                        leveringsautorisatieInhoud.getProtocolleringsniveau(),
                        leveringsautorisatieInhoud.getIndicatieAliasSoortAdministratieHandelingLeveren(),
                        leveringsautorisatieInhoud.getIndicatieGeblokkeerd(),
                        leveringsautorisatieInhoud.getIndicatiePopulatiebeperkingVolledigGeconverteerd(),
                        leveringsautorisatieInhoud.getPopulatiebeperking(),
                        leveringsautorisatieInhoud.getDatumIngang(),
                        leveringsautorisatieInhoud.getDatumEinde(),
                        leveringsautorisatieInhoud.getToelichting());

        } else if (inhoud instanceof BrpAfnemersindicatieInhoud) {
            final BrpAfnemersindicatieInhoud afnemersindicatieInhoud = (BrpAfnemersindicatieInhoud) inhoud;
            result =
                    (T) new BrpAfnemersindicatieInhoud(
                        afnemersindicatieInhoud.getDatumAanvangMaterielePeriode(),
                        historie.getDatumEindeGeldigheid(),
                        afnemersindicatieInhoud.isLeeg());

        } else if (inhoud instanceof BrpDienstbundelInhoud) {
            final BrpDienstbundelInhoud dienstbundelInhoud = (BrpDienstbundelInhoud) inhoud;
            result =
                    (T) new BrpDienstbundelInhoud(
                        dienstbundelInhoud.getNaam(),
                        dienstbundelInhoud.getDatumIngang(),
                        dienstbundelInhoud.getDatumEinde(),
                        dienstbundelInhoud.getNaderePopulatiebeperking(),
                        dienstbundelInhoud.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(),
                        dienstbundelInhoud.getToelichting(),
                        dienstbundelInhoud.getGeblokkeerd());

        } else if (inhoud instanceof BrpDienstbundelLo3RubriekInhoud) {
            result = (T) new BrpDienstbundelLo3RubriekInhoud(false);

        } else if (inhoud instanceof BrpDienstInhoud) {
            final BrpDienstInhoud dienstInhoud = (BrpDienstInhoud) inhoud;
            result = (T) new BrpDienstInhoud(dienstInhoud.getDatumIngang(), dienstInhoud.getDatumEinde(), dienstInhoud.getGeblokkeerd());

        } else if (inhoud instanceof BrpDienstAttenderingInhoud) {
            final BrpDienstAttenderingInhoud dienstAttenderingInhoud = (BrpDienstAttenderingInhoud) inhoud;
            result = (T) new BrpDienstAttenderingInhoud(dienstAttenderingInhoud.getAttenderingscriterium());

        } else if (inhoud instanceof BrpDienstSelectieInhoud) {
            final BrpDienstSelectieInhoud dienstSelectieInhoud = (BrpDienstSelectieInhoud) inhoud;
            result = (T) new BrpDienstSelectieInhoud(dienstSelectieInhoud.getEersteSelectieDatum(), dienstSelectieInhoud.getSelectiePeriodeInMaanden());

        } else if (inhoud instanceof BrpPartijInhoud) {
            final BrpPartijInhoud partijInhoud = (BrpPartijInhoud) inhoud;
            result =
                    (T) new BrpPartijInhoud(
                        historie.getDatumAanvangGeldigheid(),
                        historie.getDatumEindeGeldigheid(),
                        partijInhoud.getIndicatieVerstrekkingsbeperking(),
                        !inhoud.isLeeg());

        } else {
            result = inhoud;
        }

        // Geen materiele historie in BRP
        return new BrpGroep<>(result, new BrpHistorie(
            null,
            null,
            historie.getDatumTijdRegistratie(),
            historie.getDatumTijdVerval(),
            historie.getNadereAanduidingVerval()), groep.getActieInhoud(), groep.getActieVerval(), groep.getActieGeldigheid());
    }
}
