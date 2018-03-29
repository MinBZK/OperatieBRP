/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import nl.bzk.migratiebrp.conversie.regels.testutils.ReflectionUtil;
import org.junit.Assert;
import org.junit.Test;

public class NationaliteitConverteerderTest extends AbstractComponentTest {

    private static final String NATIONALITEIT_CODE = "0001";
    private static final String ROTTERDAM = "Rotterdam";

    private NationaliteitConverteerder
            nationaliteitConverteerder =
            new NationaliteitConverteerder(new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl()));

    private Lo3Categorie<Lo3NationaliteitInhoud> buildNationaliteit(
            final String nationaliteit,
            final String redenVerkrijging,
            final String redenVerlies,
            final String aanduidingBijzonder,
            final Integer ingangsdatumGeldigheid,
            final Integer datumVanOpneming,
            final Integer documentId,
            final String gemeenteCodeAkte,
            final String nummerAkte,
            final boolean probasDocument,
            final String euPersoonsnummer) {

        final Lo3NationaliteitInhoud inhoud =
                new Lo3NationaliteitInhoud(
                        nationaliteit == null ? null : new Lo3NationaliteitCode(nationaliteit),
                        redenVerkrijging == null ? null : new Lo3RedenNederlandschapCode(redenVerkrijging),
                        redenVerlies == null ? null : new Lo3RedenNederlandschapCode(redenVerlies),
                        aanduidingBijzonder == null ? null : new Lo3AanduidingBijzonderNederlandschap(aanduidingBijzonder),
                        euPersoonsnummer == null ? null : new Lo3String(euPersoonsnummer));

        // inhoud.valideer();

        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(ingangsdatumGeldigheid), new Lo3Datum(datumVanOpneming));
        final String beschrijvingDocument = probasDocument ? "probasTest" : null;
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(
                        documentId,
                        new Lo3GemeenteCode(gemeenteCodeAkte),
                        Lo3String.wrap(nummerAkte),
                        null,
                        null,
                        Lo3String.wrap(beschrijvingDocument),
                        null,
                        null);
        return new Lo3Categorie<>(inhoud, documentatie, historie, null);
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildDuitseStapel() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

        // @formatter:off
        categorieen.add(buildNationaliteit("0055", null, null, null, 19900101, 19900102, 11, ROTTERDAM, "Duits-1", false, "1 2+a-f/Z.D"));
        categorieen.add(buildNationaliteit(null, null, null, null, 19920101, 19920102, 12, ROTTERDAM, "Duits-2", true, "1 2+a-f/Z.D"));
        // @formatter:on

        // stapel.valideer();

        return StapelUtils.createStapel(categorieen);
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildNederlandseStapel() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

        // @formatter:off
        categorieen.add(buildNationaliteit(NATIONALITEIT_CODE, "179", null, null, 19920101, 19920102, 21, ROTTERDAM, "Nederlander-1", false, null));
        categorieen.add(buildNationaliteit(null, null, "187", null, 19940101, 19940102, 22, ROTTERDAM, "Nederlander-2", false, null));
        // @formatter:on

        // stapel.valideer();

        return StapelUtils.createStapel(categorieen);

    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildBijzonderStapel() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

        // @formatter:off
        categorieen.add(buildNationaliteit(null, null, null, "B", 19940101, 19940102, 31, ROTTERDAM, "Bijzonder-1", false, null));
        categorieen.add(buildNationaliteit(null, null, null, null, 19960101, 19960102, 32, ROTTERDAM, "Bijzonder-2", false, null));
        categorieen.add(buildNationaliteit(null, null, null, "V", 19980101, 19980102, 33, ROTTERDAM, "Bijzonder-3", false, null));
        categorieen.add(buildNationaliteit(null, null, null, null, 20000101, 20000102, 34, ROTTERDAM, "Bijzonder-4", false, null));
        // @formatter:on

        // stapel.valideer();

        return StapelUtils.createStapel(categorieen);

    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildStaatloosStapel() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

        // @formatter:off
        categorieen.add(buildNationaliteit("0499", null, null, null, 19960101, 19960102, 41, ROTTERDAM, "Staatloos-1", false, null));
        categorieen.add(buildNationaliteit(null, null, null, null, 19980101, 19980102, 42, ROTTERDAM, "Staatloos-2", false, null));
        categorieen.add(buildNationaliteit("0499", null, null, null, 20000101, 20000102, 43, ROTTERDAM, "Staatloos-3", false, null));
        // @formatter:on

        // stapel.valideer();

        return StapelUtils.createStapel(categorieen);
    }

    private List<Lo3Stapel<Lo3NationaliteitInhoud>> buildNationaliteitStapels() {
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(buildNederlandseStapel());
        stapels.add(buildDuitseStapel());
        stapels.add(buildBijzonderStapel());
        stapels.add(buildStaatloosStapel());

        return stapels;
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijvingStapel() {
        return new Lo3Stapel<>(
                Collections.singletonList(
                        new Lo3Categorie<>(
                                new Lo3InschrijvingInhoud(
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        new Lo3Integer(1),
                                        new Lo3Datumtijdstempel(20120405121500000L),
                                        Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement()),
                                null,
                                new Lo3Historie(null, null, null),
                                null)));
    }

    @Test
    public void testConverteer() {
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> lo3NationaliteitStapels = buildNationaliteitStapels();
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel = buildInschrijvingStapel();

        final TussenPersoonslijstBuilder tussenPersoonslijstBuilder = new TussenPersoonslijstBuilder();
        nationaliteitConverteerder.converteer(lo3NationaliteitStapels, lo3InschrijvingStapel, tussenPersoonslijstBuilder);

        controleerNationaliteitStapels(
                (List<TussenStapel<BrpNationaliteitInhoud>>) ReflectionUtil.getField(tussenPersoonslijstBuilder, "nationaliteitStapels"));
        controleerStaatloosStapel(
                (TussenStapel<BrpStaatloosIndicatieInhoud>) ReflectionUtil.getField(tussenPersoonslijstBuilder, "staatloosIndicatieStapel"));
        controleerBehandeldStapel(
                (TussenStapel<BrpBehandeldAlsNederlanderIndicatieInhoud>) ReflectionUtil.getField(
                        tussenPersoonslijstBuilder,
                        "behandeldAlsNederlanderIndicatieStapel"));
        controleerVastgesteldStapel(
                (TussenStapel<BrpVastgesteldNietNederlanderIndicatieInhoud>) ReflectionUtil.getField(
                        tussenPersoonslijstBuilder,
                        "vastgesteldNietNederlanderIndicatieStapel"));
        controleerBvpStapel(
                (TussenStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud>) ReflectionUtil.getField(
                        tussenPersoonslijstBuilder,
                        "bijzondereVerblijfsrechtelijkePositieIndicatieStapel"));
    }

    private void controleerNationaliteitStapels(final List<TussenStapel<BrpNationaliteitInhoud>> stapels) {
        Assert.assertEquals(2, stapels.size());

        for (final TussenStapel<BrpNationaliteitInhoud> stapel : stapels) {
            if (Short.valueOf(NATIONALITEIT_CODE).equals(stapel.get(0).getInhoud().getNationaliteitCode().getWaarde())) {
                controleerNederlandseStapel(stapel);
            }
            if (Short.valueOf("0055").equals(stapel.get(0).getInhoud().getNationaliteitCode().getWaarde())) {
                controleerDuitseStapel(stapel);
            }
        }
    }

    private void controleerNederlandseStapel(final TussenStapel<BrpNationaliteitInhoud> stapel) {
        Assert.assertEquals(2, stapel.size());

        final TussenGroep<BrpNationaliteitInhoud> groep1 = stapel.get(0);
        final TussenGroep<BrpNationaliteitInhoud> groep2 = stapel.get(1);

        Assert.assertEquals(Short.valueOf(NATIONALITEIT_CODE), groep1.getInhoud().getNationaliteitCode().getWaarde());
        Assert.assertNull(NATIONALITEIT_CODE, groep2.getInhoud().getNationaliteitCode());
    }

    private void controleerDuitseStapel(final TussenStapel<BrpNationaliteitInhoud> stapel) {
        Assert.assertEquals(2, stapel.size());

        final TussenGroep<BrpNationaliteitInhoud> groep1 = stapel.get(0);
        final TussenGroep<BrpNationaliteitInhoud> groep2 = stapel.get(1);

        Assert.assertEquals(Short.valueOf("0055"), groep1.getInhoud().getNationaliteitCode().getWaarde());
        Assert.assertEquals(null, groep2.getInhoud().getNationaliteitCode());

    }

    private void controleerStaatloosStapel(final TussenStapel<BrpStaatloosIndicatieInhoud> stapel) {
        Assert.assertEquals(3, stapel.size());

        final TussenGroep<BrpStaatloosIndicatieInhoud> groep1 = stapel.get(0);
        final TussenGroep<BrpStaatloosIndicatieInhoud> groep2 = stapel.get(1);
        final TussenGroep<BrpStaatloosIndicatieInhoud> groep3 = stapel.get(2);

        Assert.assertEquals(Boolean.TRUE, groep1.getInhoud().heeftIndicatie());
        Assert.assertEquals(false, groep2.getInhoud().heeftIndicatie());
        Assert.assertEquals(Boolean.TRUE, groep3.getInhoud().heeftIndicatie());

        Assert.assertEquals(new Lo3Datum(19960101), groep1.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(19980101), groep2.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(20000101), groep3.getHistorie().getIngangsdatumGeldigheid());
    }

    private void controleerBehandeldStapel(final TussenStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> stapel) {
        Assert.assertEquals(4, stapel.size());

        final TussenGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> groep1 = stapel.get(0);
        final TussenGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> groep2 = stapel.get(1);
        final TussenGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> groep3 = stapel.get(2);
        final TussenGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> groep4 = stapel.get(3);

        Assert.assertEquals(Boolean.TRUE, groep1.getInhoud().heeftIndicatie());
        Assert.assertEquals(false, groep2.getInhoud().heeftIndicatie());
        Assert.assertEquals(false, groep3.getInhoud().heeftIndicatie());
        Assert.assertEquals(false, groep4.getInhoud().heeftIndicatie());

        Assert.assertEquals(new Lo3Datum(19940101), groep1.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(19960101), groep2.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(19980101), groep3.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(20000101), groep4.getHistorie().getIngangsdatumGeldigheid());
    }

    private void controleerVastgesteldStapel(final TussenStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> stapel) {
        Assert.assertEquals(4, stapel.size());

        final TussenGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> groep1 = stapel.get(0);
        final TussenGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> groep2 = stapel.get(1);
        final TussenGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> groep3 = stapel.get(2);
        final TussenGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> groep4 = stapel.get(3);

        Assert.assertEquals(false, groep1.getInhoud().heeftIndicatie());
        Assert.assertEquals(false, groep2.getInhoud().heeftIndicatie());
        Assert.assertEquals(Boolean.TRUE, groep3.getInhoud().heeftIndicatie());
        Assert.assertEquals(false, groep4.getInhoud().heeftIndicatie());

        Assert.assertEquals(new Lo3Datum(19940101), groep1.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(19960101), groep2.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(19980101), groep3.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(20000101), groep4.getHistorie().getIngangsdatumGeldigheid());
    }

    private void controleerBvpStapel(final TussenStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> stapel) {
        Assert.assertEquals(1, stapel.size());

        final TussenGroep<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> groep1 = stapel.get(0);

        Assert.assertTrue(groep1.getInhoud().heeftIndicatie());
    }

    @Test
    public void testBevatAanduidingGeprivilegieerde() {
        final Lo3Documentatie actueleDocumentatieOk1 = new Lo3Documentatie(1L, null, null, null, null, Lo3String.wrap("PROBAS"), null, null);
        final Lo3Documentatie actueleDocumentatieOk2 = new Lo3Documentatie(1L, null, null, null, null, Lo3String.wrap("PrObAs"), null, null);
        final Lo3Documentatie actueleDocumentatieOk3 = new Lo3Documentatie(1L, null, null, null, null, Lo3String.wrap(" PrObAs"), null, null);
        final Lo3Documentatie actueleDocumentatieOk4 = new Lo3Documentatie(1L, null, null, null, null, Lo3String.wrap("  PrObAs"), null, null);
        final Lo3Documentatie actueleDocumentatieOk5 = new Lo3Documentatie(1L, null, null, null, null, Lo3String.wrap("  PrObAsTEST"), null, null);
        assertTrue(actueleDocumentatieOk1.bevatAanduidingGeprivilegieerde());
        assertTrue(actueleDocumentatieOk2.bevatAanduidingGeprivilegieerde());
        assertTrue(actueleDocumentatieOk3.bevatAanduidingGeprivilegieerde());
        assertTrue(actueleDocumentatieOk4.bevatAanduidingGeprivilegieerde());
        assertTrue(actueleDocumentatieOk5.bevatAanduidingGeprivilegieerde());
        final Lo3Documentatie actueleDocumentatieFout1 = new Lo3Documentatie(1L, null, null, null, null, Lo3String.wrap("xPROBAS"), null, null);
        final Lo3Documentatie actueleDocumentatieFout2 = new Lo3Documentatie(1L, null, null, null, null, Lo3String.wrap(""), null, null);
        final Lo3Documentatie actueleDocumentatieFout4 = new Lo3Documentatie(1L, null, null, null, null, Lo3String.wrap(" 1 PROBAS"), null, null);
        final Lo3Documentatie actueleDocumentatieFout5 = new Lo3Documentatie(1L, null, null, null, null, null, null, null);
        assertFalse(actueleDocumentatieFout1.bevatAanduidingGeprivilegieerde());
        assertFalse(actueleDocumentatieFout2.bevatAanduidingGeprivilegieerde());
        assertFalse(actueleDocumentatieFout4.bevatAanduidingGeprivilegieerde());
        assertFalse(actueleDocumentatieFout5.bevatAanduidingGeprivilegieerde());
    }
}
