/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpEffectAfnemerindicatiesCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpSoortDienstCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpStelselCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstAttenderingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstSelectieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenDienst;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenDienstbundel;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenLeveringsautorisatie;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenPartij;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Foutmelding;
import org.springframework.stereotype.Component;

/**
 * AutorisatieConverteerder.
 */
@Component
public class AutorisatieConverteerder {

    private static final Integer IND_CONDITIONEEL = Integer.valueOf(1);
    private static final Integer IND_PLAATSEN_AFNIND = Integer.valueOf(0);
    private static final Integer IND_ADHOC_ADRESVRAAG_BEVOEGD = Integer.valueOf(1);
    private static final Integer IND_ADHOC_PLAATSINGS_BEVOEGD = Integer.valueOf(1);
    private static final Integer SELECTIESOORT_BERICHT = Integer.valueOf(0);
    private static final Integer SELECTIESOORT_PLAATSEN = Integer.valueOf(1);
    private static final Integer SELECTIESOORT_VERWIJDEREN_LOGISCH = Integer.valueOf(2);
    private static final Integer SELECTIESOORT_VERWIJDEREN_VOORWAARDELIJK_FYSIEK = Integer.valueOf(3);
    private static final Integer SELECTIESOORT_VERWIJDEREN_ONVOORWAARDELIJK_FYSIEK = Integer.valueOf(4);
    private static final String MEDIUM_TYPE_A = "A";
    private static final String MEDIUM_TYPE_N = "N";

    @Inject
    private DienstConverteerder dienstConverteerder;
    @Inject
    private DienstbundelConverteerder dienstbundelConverteerder;
    @Inject
    private LeveringsautorisatieConverteerder leveringsautorisatieConverteerder;
    @Inject
    private DienstbundelLo3RubriekenConverteerder lo3RubriekenConverteerder;
    @Inject
    private PartijConverteerder partijConverteerder;

    /**
     * Converteer van Lo3 model naar Migratie model.
     *
     * @param lo3Autorisatie
     *            {@link Lo3Autorisatie}
     * @return {@link nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenPartij}
     */
    public final TussenPartij converteerPartij(final Lo3Autorisatie lo3Autorisatie) {
        final Lo3AutorisatieInhoud meestRecenteNietLege = lo3Autorisatie.getAutorisatieStapel().get(0).getInhoud();

        final String naam = meestRecenteNietLege.getAfnemernaam();
        final BrpPartijCode partijCode = new BrpPartijCode(meestRecenteNietLege.getAfnemersindicatie());
        final TussenStapel<BrpPartijInhoud> partijStapel = partijConverteerder.converteerPartijStapel(lo3Autorisatie.getAutorisatieStapel());

        return new TussenPartij(null, naam, partijCode, partijStapel);
    }

    /**
     * Converteer van Lo3 model naar Migratie model.
     *
     * @param lo3Autorisatie
     *            {@link Lo3Autorisatie}
     * @return {@link List} van {@link nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenLeveringsautorisatie}
     */
    public final List<TussenLeveringsautorisatie> converteerAutorisatie(final Lo3Autorisatie lo3Autorisatie) {

        final List<TussenLeveringsautorisatie> leveringsautorisaties = new ArrayList<>();

        int index = 1;
        for (final Lo3Categorie<Lo3AutorisatieInhoud> categorie : lo3Autorisatie.getAutorisatieStapel()) {

            final TussenStapel<BrpLeveringsautorisatieInhoud> leveringsautorisatieStapel =
                    leveringsautorisatieConverteerder.converteerLeveringsautorisatie(categorie, index);

            final List<TussenDienstbundel> dienstbundels = converteerDienstbundels(categorie);

            leveringsautorisaties.add(new TussenLeveringsautorisatie(BrpStelselCode.GBA, false, leveringsautorisatieStapel, dienstbundels));
            index++;
        }

        return leveringsautorisaties;
    }

    /**
     * Converteer van Lo3 model naar Migratie model.
     *
     * @param autorisatie
     *            {@link List} van {@link Lo3AutorisatieInhoud}
     * @return {@link List} van {@link nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenDienstbundel}
     */
    public final List<TussenDienstbundel> converteerDienstbundels(final Lo3Categorie<Lo3AutorisatieInhoud> autorisatie) {
        final List<TussenDienstbundel> dienstbundels = new ArrayList<>();

        final TussenStapel<BrpDienstInhoud> dienstInhoud = dienstConverteerder.converteerDienst(autorisatie);
        final TussenStapel<BrpDienstAttenderingInhoud> dienstAttenderingInhoud = dienstConverteerder.converteerAttenderingDienst(autorisatie);
        final TussenStapel<BrpDienstSelectieInhoud> dienstSelectieInhoud = dienstConverteerder.converteerSelectieDienst(autorisatie);

        // SPONTAAN
        final boolean heeftSpontaanBundel;
        if (MEDIUM_TYPE_A.equals(autorisatie.getInhoud().getMediumSpontaan()) || MEDIUM_TYPE_N.equals(autorisatie.getInhoud().getMediumSpontaan())) {
            voegSpontaanDienstbundelToe(dienstbundels, autorisatie, dienstInhoud, dienstAttenderingInhoud);
            heeftSpontaanBundel = true;
        } else {
            heeftSpontaanBundel = false;
        }

        // AD HOC
        if (MEDIUM_TYPE_A.equals(autorisatie.getInhoud().getMediumAdHoc()) || MEDIUM_TYPE_N.equals(autorisatie.getInhoud().getMediumAdHoc())) {
            voegAdHocDienstbundelToe(dienstbundels, autorisatie, dienstInhoud, heeftSpontaanBundel);
        }

        // SELECTIE
        if (MEDIUM_TYPE_A.equals(autorisatie.getInhoud().getMediumSelectie()) || MEDIUM_TYPE_N.equals(autorisatie.getInhoud().getMediumSelectie())) {
            voegSelectieDienstbundelToe(dienstbundels, autorisatie, dienstInhoud, dienstSelectieInhoud);

        }

        return dienstbundels;
    }

    private void voegSpontaanDienstbundelToe(
        final List<TussenDienstbundel> dienstbundels,
        final Lo3Categorie<Lo3AutorisatieInhoud> autorisatie,
        final TussenStapel<BrpDienstInhoud> dienstInhoud,
        final TussenStapel<BrpDienstAttenderingInhoud> dienstAttenderingInhoud)
    {
        final List<TussenDienst> diensten = new ArrayList<>();

        final List<TussenDienstbundelLo3Rubriek> lo3Rubrieken =
                lo3RubriekenConverteerder.converteerDienstbundelLo3Rubriek(autorisatie, autorisatie.getInhoud().getRubrieknummerSpontaan());

        // Mutatielevering obv afnemersindicaties
        diensten.add(new TussenDienst(null, BrpSoortDienstCode.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERSINDICATIE, dienstInhoud, null, null));

        // Attendering dmv plaatsen
        if (IND_PLAATSEN_AFNIND.equals(autorisatie.getInhoud().getConditioneleVerstrekking())
            && autorisatie.getInhoud().getSleutelrubriek() != null
            && !"".equals(autorisatie.getInhoud().getSleutelrubriek()))
        {
            // De werking van LO3 en BRP verschilt bij de conditionele verstrekking; LO3 schrijft voor dat de
            // conditionele verstrekking niet wordt gedaan indien er een afnemersindicatie bestaat. Bij BRP is
            // dit niet zo; daarom een nadere populatiebeperking toevoegen die controleert of er geen afnemers-
            // indicatie bestaat.
            diensten.add(
                new TussenDienst(BrpEffectAfnemerindicatiesCode.PLAATSEN, BrpSoortDienstCode.ATTENDERING, dienstInhoud, dienstAttenderingInhoud, null));
        }

        // Attendering
        if (IND_CONDITIONEEL.equals(autorisatie.getInhoud().getConditioneleVerstrekking())
            && autorisatie.getInhoud().getSleutelrubriek() != null
            && !"".equals(autorisatie.getInhoud().getSleutelrubriek()))
        {
            diensten.add(new TussenDienst(null, BrpSoortDienstCode.ATTENDERING, dienstInhoud, dienstAttenderingInhoud, null));
        }

        final TussenStapel<BrpDienstbundelInhoud> dienstbundelStapel =
                dienstbundelConverteerder.converteerDienstbundel(autorisatie, "Spontaan", autorisatie.getInhoud().getVoorwaarderegelSpontaan());
        dienstbundels.add(new TussenDienstbundel(diensten, lo3Rubrieken, dienstbundelStapel));
    }

    private void voegSelectieDienstbundelToe(
        final List<TussenDienstbundel> dienstbundels,
        final Lo3Categorie<Lo3AutorisatieInhoud> autorisatie,
        final TussenStapel<BrpDienstInhoud> dienstInhoud,
        final TussenStapel<BrpDienstSelectieInhoud> dienstSelectieInhoud)
    {
        final List<TussenDienst> diensten = new ArrayList<>();

        final List<TussenDienstbundelLo3Rubriek> lo3Rubrieken =
                lo3RubriekenConverteerder.converteerDienstbundelLo3Rubriek(autorisatie, autorisatie.getInhoud().getRubrieknummerSelectie());

        if (SELECTIESOORT_BERICHT.equals(autorisatie.getInhoud().getSelectiesoort())) {
            diensten.add(new TussenDienst(null, BrpSoortDienstCode.SELECTIE, dienstInhoud, null, dienstSelectieInhoud));
        }

        if (SELECTIESOORT_PLAATSEN.equals(autorisatie.getInhoud().getSelectiesoort())) {
            diensten.add(new TussenDienst(BrpEffectAfnemerindicatiesCode.PLAATSEN, BrpSoortDienstCode.SELECTIE, dienstInhoud, null, dienstSelectieInhoud));
        }

        if (SELECTIESOORT_VERWIJDEREN_LOGISCH.equals(autorisatie.getInhoud().getSelectiesoort())
            || SELECTIESOORT_VERWIJDEREN_ONVOORWAARDELIJK_FYSIEK.equals(autorisatie.getInhoud().getSelectiesoort())
            || SELECTIESOORT_VERWIJDEREN_VOORWAARDELIJK_FYSIEK.equals(autorisatie.getInhoud().getSelectiesoort()))
        {
            diensten.add(
                new TussenDienst(BrpEffectAfnemerindicatiesCode.VERWIJDEREN, BrpSoortDienstCode.SELECTIE, dienstInhoud, null, dienstSelectieInhoud));
        }

        final TussenStapel<BrpDienstbundelInhoud> dienstbundelStapel =
                dienstbundelConverteerder.converteerDienstbundel(autorisatie, "Selectie", autorisatie.getInhoud().getVoorwaarderegelSelectie());
        dienstbundels.add(new TussenDienstbundel(diensten, lo3Rubrieken, dienstbundelStapel));
    }

    private void voegAdHocDienstbundelToe(
        final List<TussenDienstbundel> dienstbundels,
        final Lo3Categorie<Lo3AutorisatieInhoud> autorisatie,
        final TussenStapel<BrpDienstInhoud> dienstInhoud,
        final boolean heeftSpontaanBundel)
    {
        final List<TussenDienst> diensten = new ArrayList<>();

        final List<TussenDienstbundelLo3Rubriek> lo3Rubrieken =
                lo3RubriekenConverteerder.converteerDienstbundelLo3Rubriek(autorisatie, autorisatie.getInhoud().getRubrieknummerAdHoc());

        // Dienst ZOEK_PERSOON
        diensten.add(new TussenDienst(null, BrpSoortDienstCode.ZOEK_PERSOON, dienstInhoud, null, null));
        // Dienst GEEF_DETAILS_PERSOON
        diensten.add(new TussenDienst(null, BrpSoortDienstCode.GEEF_DETAILS_PERSOON, dienstInhoud, null, null));

        if (IND_ADHOC_ADRESVRAAG_BEVOEGD.equals(autorisatie.getInhoud().getAdresvraagBevoegdheid())) {
            // Dienst ZOEK_PERSOON_OP_ADRESGEGEVENS
            diensten.add(new TussenDienst(null, BrpSoortDienstCode.ZOEK_PERSOON_OP_ADRESGEGEVENS, dienstInhoud, null, null));
            // Dienst GEEF_MEDEBEWONERS_VAN_PERSOON
            diensten.add(new TussenDienst(null, BrpSoortDienstCode.GEEF_MEDEBEWONERS_VAN_PERSOON, dienstInhoud, null, null));
        }

        if (IND_ADHOC_PLAATSINGS_BEVOEGD.equals(autorisatie.getInhoud().getPlaatsingsbevoegdheidPersoonslijst())) {
            diensten.add(new TussenDienst(null, BrpSoortDienstCode.PLAATSEN_AFNEMERINDICATIE, dienstInhoud, null, null));
            diensten.add(new TussenDienst(null, BrpSoortDienstCode.VERWIJDEREN_AFNEMERINDICATIE, dienstInhoud, null, null));

            if (!heeftSpontaanBundel) {
                Foutmelding.logMeldingFout(autorisatie.getLo3Herkomst(), LogSeverity.WARNING, SoortMeldingCode.AUT013, null);
            }
        }

        final TussenStapel<BrpDienstbundelInhoud> dienstbundelStapel =
                dienstbundelConverteerder.converteerDienstbundel(autorisatie, "Ad hoc", autorisatie.getInhoud().getVoorwaarderegelAdHoc());
        dienstbundels.add(new TussenDienstbundel(diensten, lo3Rubrieken, dienstbundelStapel));
    }
}
