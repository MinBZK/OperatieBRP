/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractReisdocumentTest;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Test het contract van ReisdocumentConverteerder.
 */
public class ReisdocumentConverteerderTest extends AbstractReisdocumentTest {

    private ReisdocumentConverteerder converteerder = new ReisdocumentConverteerder(new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl()));
    private TussenPersoonslijstBuilder tussenPersoonslijstBuilder;

    @Before
    public void setUp() {
        tussenPersoonslijstBuilder = new TussenPersoonslijstBuilder();
    }

    @Test(expected = NullPointerException.class)
    public void testNullInputs1() {
        converteerder.converteer(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullInputs2() {
        converteerder.converteer(Collections.singletonList(maakStapel(nederlandsReisdocumentBuilder())), null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullInputs3() {
        converteerder.converteer(null, tussenPersoonslijstBuilder);
    }

    @Test
    @Definitie(Definities.DEF063)
    @Requirement(Requirements.CCA12)
    public void testConverteerNederlandsReisdocument() {
        final Lo3ReisdocumentInhoud.Builder builder = nederlandsReisdocumentBuilder();
        builder.aanduidingInhoudingNederlandsReisdocument(
                new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(String.valueOf(AANDUIDING_INHOUDING_OF_VERMISSING)));
        builder.datumInhoudingVermissingNederlandsReisdocument(new Lo3Datum(DATUM_INHOUDING_OF_VERMISSING));

        final Lo3Stapel<Lo3ReisdocumentInhoud> reisdocumentLo3Stapel = maakStapel(builder);
        converteerder.converteer(Collections.singletonList(reisdocumentLo3Stapel), tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final List<TussenStapel<BrpReisdocumentInhoud>> reisdocumentStapels = tussenPersoonslijst.getReisdocumentStapels();

        assertNotNull(reisdocumentStapels);
        assertEquals(1, reisdocumentStapels.size());

        final TussenStapel<BrpReisdocumentInhoud> reisdocumentStapel = reisdocumentStapels.get(0);
        assertEquals(1, reisdocumentStapel.size());

        final TussenGroep<BrpReisdocumentInhoud> reisdocument = reisdocumentStapel.get(0);
        assertNotNull(reisdocument);
        assertFalse(reisdocument.isInhoudelijkLeeg());
        final BrpReisdocumentInhoud reisdocumentInhoud = reisdocument.getInhoud();
        assertEquals(new BrpReisdocumentAutoriteitVanAfgifteCode(AUTORITEIT_VAN_AFGIFTE), reisdocumentInhoud.getAutoriteitVanAfgifte());

        assertEquals(new BrpDatum(DATUM_INGANG_GELDIGHEID, null), reisdocumentInhoud.getDatumIngangDocument());
        assertEquals(new BrpDatum(DATUM_UITGIFTE_NL_REISDOCUMENT, null), reisdocumentInhoud.getDatumUitgifte());
        assertEquals(new BrpDatum(DATUM_EINDE_GELDIGHEID_NL_REISDOCUMENT, null), reisdocumentInhoud.getDatumEindeDocument());
        assertEquals(new BrpString(NUMMER_NL_REISDOCUMENT), reisdocumentInhoud.getNummer());
        assertEquals(new BrpSoortNederlandsReisdocumentCode(SOORT_REISDOCUMENT), reisdocumentInhoud.getSoort());

        assertEquals(new BrpDatum(DATUM_INHOUDING_OF_VERMISSING, null), reisdocumentInhoud.getDatumInhoudingOfVermissing());
        assertEquals(
                new BrpAanduidingInhoudingOfVermissingReisdocumentCode(AANDUIDING_INHOUDING_OF_VERMISSING),
                reisdocumentInhoud.getAanduidingInhoudingOfVermissing());

        assertGeenSignalering(tussenPersoonslijst);
    }

    @Test
    @Definitie(Definities.DEF065)
    @Requirement(Requirements.CCA12)
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB026)
    public void testConverteerSignalering() {
        final Lo3ReisdocumentInhoud.Builder builder = signaleringBuilder();

        final Lo3Stapel<Lo3ReisdocumentInhoud> reisdocumentLo3Stapel = maakStapel(builder);
        converteerder.converteer(Collections.singletonList(reisdocumentLo3Stapel), tussenPersoonslijstBuilder);
        converteerEnControleerSignalering();
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB026, 1);
    }

    @Test
    @Definitie(Definities.DEF065)
    @Requirement(Requirements.CCA12)
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB026)
    public void testConverteerSignaleringOpnemingIngangGeldigheidGelijk() {
        final Lo3ReisdocumentInhoud.Builder builder = signaleringBuilder();

        final Lo3Categorie<Lo3ReisdocumentInhoud> voorkomen =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, DATUM_INGANG_GELDIGHEID, DATUM_INGANG_GELDIGHEID),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_12, 0, 0));
        final Lo3Stapel<Lo3ReisdocumentInhoud> reisdocumentLo3Stapel = Lo3StapelHelper.lo3Stapel(voorkomen);

        converteerder.converteer(Collections.singletonList(reisdocumentLo3Stapel), tussenPersoonslijstBuilder);
        converteerEnControleerSignalering();
        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB026, 0);
    }

    private void converteerEnControleerSignalering() {
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> signaleringStapel =
                tussenPersoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel();
        assertEquals(1, signaleringStapel.size());

        final TussenGroep<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> signalering = signaleringStapel.get(0);
        assertNotNull(signalering);
        assertFalse(signalering.isInhoudelijkLeeg());
        final BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud inhoud = signalering.getInhoud();
        assertTrue(inhoud.heeftIndicatie());
        assertGeenNederlandsReisdocument(tussenPersoonslijst);
    }

    /**
     * @return builder voor nederlands reisdocument welke niet ingehouden/vermist is.
     */
    private Lo3ReisdocumentInhoud.Builder nederlandsReisdocumentBuilder() {
        final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder();
        builder.soortNederlandsReisdocument(new Lo3SoortNederlandsReisdocument(SOORT_REISDOCUMENT));
        builder.nummerNederlandsReisdocument(Lo3String.wrap(NUMMER_NL_REISDOCUMENT));
        builder.datumUitgifteNederlandsReisdocument(new Lo3Datum(DATUM_UITGIFTE_NL_REISDOCUMENT));
        builder.autoriteitVanAfgifteNederlandsReisdocument(new Lo3AutoriteitVanAfgifteNederlandsReisdocument(AUTORITEIT_VAN_AFGIFTE));
        builder.datumEindeGeldigheidNederlandsReisdocument(new Lo3Datum(DATUM_EINDE_GELDIGHEID_NL_REISDOCUMENT));

        return builder;
    }

    /**
     * @return builder voor belemmering verstrekking(signalering).
     */
    private Lo3ReisdocumentInhoud.Builder signaleringBuilder() {
        final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder();
        builder.signalering(LO3_SIGNALERING);
        return builder;
    }

    private void assertGeenSignalering(final TussenPersoonslijst tussenPersoonslijst) {
        assertNull(tussenPersoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel());
    }

    private void assertGeenNederlandsReisdocument(final TussenPersoonslijst tussenPersoonslijst) {
        final List<TussenStapel<BrpReisdocumentInhoud>> reisdocumentStapels = tussenPersoonslijst.getReisdocumentStapels();
        assertNotNull(reisdocumentStapels);
        assertEquals(0, reisdocumentStapels.size());
    }

    private Lo3Categorie<Lo3ReisdocumentInhoud> maakCategorie(final Lo3ReisdocumentInhoud.Builder builder) {
        return Lo3StapelHelper.lo3Cat(
                builder.build(),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(null, DATUM_INGANG_GELDIGHEID, DATUM_OPNEMING),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_12, 0, 0));
    }

    private Lo3Stapel<Lo3ReisdocumentInhoud> maakStapel(final Lo3ReisdocumentInhoud.Builder builder) {
        return Lo3StapelHelper.lo3Stapel(maakCategorie(builder));
    }
}
