/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import org.junit.Assert;
import org.junit.Test;

public class BrpInschrijvingConverteerderTest extends AbstractComponentTest {

    private static final String UNCHECKED = "unchecked";
    private static final String ASSERT_INHOUD_GELIJK = "Inhoud niet gelijk";
    private static final String ASSERT_STAPEL_IS_NIET_LEEG = "Stapel is leeg";
    private static final String VERIFICATIE_OMS = "nieuweVerificatie";
    private static final String ASSERT_STAPEL_IS_NOT_NULL = "Stapel is null";
    private static final String ASSERT_CATEGORIE_IS_NOT_NULL = "Categorie is null";
    @Inject
    private BrpInschrijvingConverteerder subject;

    @Test
    
    public void test() {
        final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                    BrpStapelHelper.inschrijving(19940101, 1042, 19990101010000L),
                    BrpStapelHelper.his(19940101),
                    BrpStapelHelper.act(9, 19940102)));

        final BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                    BrpStapelHelper.persoonskaart("059901", false),
                    BrpStapelHelper.his(19940101),
                    BrpStapelHelper.act(1, 19940102)));

        final BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> beperkingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                    BrpStapelHelper.verstrekkingsbeperking(true),
                    BrpStapelHelper.his(19940101),
                    BrpStapelHelper.act(4, 19940102)));

        final BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                    BrpStapelHelper.afgeleid(),
                    BrpStapelHelper.his(19940101),
                    BrpStapelHelper.act(3, 19940102)));

        // Execute
        final Lo3Stapel<Lo3InschrijvingInhoud> result =
                subject.converteer(inschrijvingStapel, persoonskaartStapel, beperkingStapel, afgeleidAdministratiefStapel);

        // Expectation
        // cat(inhoud, historie, documentatie)
        // his(ingangsdatumGeldigheid)
        // akt(id)
        final Lo3InschrijvingInhoud expected =
                Lo3StapelHelper.lo3Inschrijving(null, null, null, 19940101, "0599", 7, 1042, 19990101010000000L, false);

        // Check
        Assert.assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        Assert.assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        Assert.assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        Assert.assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }

    @Test
    
    public void testBepaalVerificatieStapel() {
        final List<BrpStapel<BrpVerificatieInhoud>> stapels = new ArrayList<>();

        stapels.add(BrpStapelHelper.stapel(BrpStapelHelper.groep(
            BrpStapelHelper.verificatie(20110101, "vervallenVerificatie", 250001),
            BrpStapelHelper.his(null, null, 20110101090909L, 20120101090909L),
            BrpStapelHelper.act(10, 20110101),
            BrpStapelHelper.act(11, 20120101))));
        Assert.assertNull(subject.bepaalVerificatieStapel(stapels));

        stapels.add(BrpStapelHelper.stapel(BrpStapelHelper.groep(
            BrpStapelHelper.verificatie(20100101, "oudeVerificatie", 250001),
            BrpStapelHelper.his(null, null, 20100101090909L, null),
            BrpStapelHelper.act(9, 20100101))));
        Assert.assertNotNull(subject.bepaalVerificatieStapel(stapels));
        Assert.assertEquals(new BrpString("oudeVerificatie"), subject.bepaalVerificatieStapel(stapels).getActueel().getInhoud().getSoort());

        stapels.add(BrpStapelHelper.stapel(BrpStapelHelper.groep(
            BrpStapelHelper.verificatie(20130101, VERIFICATIE_OMS, 250001),
            BrpStapelHelper.his(null, null, 20130101090909L, null),
            BrpStapelHelper.act(12, 20130101))));
        Assert.assertNotNull(subject.bepaalVerificatieStapel(stapels));
        Assert.assertEquals(new BrpString(VERIFICATIE_OMS), subject.bepaalVerificatieStapel(stapels).getActueel().getInhoud().getSoort());
    }

    @Test
    
    public void testConverteerVerificatie() {

        final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                    BrpStapelHelper.inschrijving(19940101, 1042, 19990101010000L),
                    BrpStapelHelper.his(19940101),
                    BrpStapelHelper.act(9, 19940102)));

        final BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                    BrpStapelHelper.persoonskaart("059901", false),
                    BrpStapelHelper.his(19940101),
                    BrpStapelHelper.act(1, 19940102)));

        final BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> beperkingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                    BrpStapelHelper.verstrekkingsbeperking(true),
                    BrpStapelHelper.his(19940101),
                    BrpStapelHelper.act(4, 19940102)));

        final BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                    BrpStapelHelper.afgeleid(),
                    BrpStapelHelper.his(19940101),
                    BrpStapelHelper.act(3, 19940102)));

        final BrpStapel<BrpVerificatieInhoud> verificatieStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                    BrpStapelHelper.verificatie(20130101, VERIFICATIE_OMS, 250001),
                    BrpStapelHelper.his(null, null, 20130101090909L, null),
                    BrpStapelHelper.act(12, 20130101)));

        // Execute
        final Lo3Stapel<Lo3InschrijvingInhoud> result =
                subject.converteer(
                    inschrijvingStapel,
                    persoonskaartStapel,
                    beperkingStapel,
                    afgeleidAdministratiefStapel,
                    verificatieStapel);

        final Lo3InschrijvingInhoud.Builder builder =
                new Lo3InschrijvingInhoud.Builder(Lo3StapelHelper.lo3Inschrijving(
                    null,
                    null,
                    null,
                    19940101,
                    "0599",
                    7,
                    1042,
                    19990101010000000L,
                    false));
        builder.setDatumVerificatie(new Lo3Datum(20130101));
        builder.setOmschrijvingVerificatie(new Lo3String(VERIFICATIE_OMS));
        final Lo3InschrijvingInhoud expected = builder.build();

        // Check
        Assert.assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        Assert.assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        Assert.assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        Assert.assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }

    @Test
    
    @Definitie(Definities.DEF058)
    @Requirement(Requirements.CCA07_BL05)
    public void testBijhoudingOpgeschort() {
        // Input
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                    599,
                    BrpBijhoudingsaardCode.INGEZETENE,
                    BrpNadereBijhoudingsaardCode.OVERLEDEN,
                    new BrpBoolean(false, null)), BrpStapelHelper.his(20100101), BrpStapelHelper.act(1, 20100101)));

        // Execute
        Lo3Stapel<Lo3InschrijvingInhoud> result = subject.converteer(bijhoudingStapel);
        result = subject.nabewerking(result, bijhoudingStapel);

        // Expectation
        final Lo3InschrijvingInhoud expected = Lo3StapelHelper.lo3Inschrijving(null, 20100101, "O", null, null, 0, null, null, false);

        // Check
        Assert.assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        Assert.assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        Assert.assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        Assert.assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }

    @Test
    
    @Definitie(Definities.DEF058)
    @Requirement(Requirements.CCA07_BL05)
    public void testVerhuizingNaOverlijden() {
        // Input
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel =
                BrpStapelHelper.stapel(
                    BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                        599,
                        BrpBijhoudingsaardCode.INGEZETENE,
                        BrpNadereBijhoudingsaardCode.OVERLEDEN,
                        new BrpBoolean(false, null)), BrpStapelHelper.his(20130101), BrpStapelHelper.act(1, 20130101)),
                    BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                        518,
                        BrpBijhoudingsaardCode.INGEZETENE,
                        BrpNadereBijhoudingsaardCode.OVERLEDEN,
                        new BrpBoolean(false, null)), BrpStapelHelper.his(20121231, 20130101, 20121231, null), BrpStapelHelper.act(
                        1,
                        20121231)),
                    BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                        518,
                        BrpBijhoudingsaardCode.ONBEKEND,
                        BrpNadereBijhoudingsaardCode.ONBEKEND,
                        new BrpBoolean(false, null)), BrpStapelHelper.his(20100101, 20121231, 20100101, null), BrpStapelHelper.act(
                        1,
                        20100101)));

        // Execute
        Lo3Stapel<Lo3InschrijvingInhoud> result = subject.converteer(bijhoudingStapel);
        result = subject.nabewerking(result, bijhoudingStapel);

        // Expectation
        final Lo3InschrijvingInhoud expected = Lo3StapelHelper.lo3Inschrijving(null, 20121231, "O", null, null, 0, null, null, false);

        // Check
        Assert.assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        Assert.assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        Assert.assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        Assert.assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }

    @Test
    
    @Definitie(Definities.DEF080)
    @Requirement(Requirements.CCA07_BL05)
    public void testBijhoudingOpgeschortVOW() {
        // Input
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                    599,
                    BrpBijhoudingsaardCode.NIET_INGEZETENE,
                    BrpNadereBijhoudingsaardCode.VERTROKKEN_ONBEKEND_WAARHEEN,
                    new BrpBoolean(false, null)), BrpStapelHelper.his(20100101), BrpStapelHelper.act(1, 20100101)));

        // Execute
        Lo3Stapel<Lo3InschrijvingInhoud> result = subject.converteer(bijhoudingStapel);
        result = subject.nabewerking(result, bijhoudingStapel);

        // Expectation
        final Lo3InschrijvingInhoud expected = Lo3StapelHelper.lo3Inschrijving(null, 20100101, "E", null, null, 0, null, null, false);

        // Check
        Assert.assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        Assert.assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        Assert.assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        Assert.assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }

    @Test
    
    @Definitie(Definities.DEF079)
    @Requirement(Requirements.CCA07_BL05)
    public void testBijhoudingNietOpgeschort() {
        // Input
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                    599,
                    BrpBijhoudingsaardCode.INGEZETENE,
                    BrpNadereBijhoudingsaardCode.ACTUEEL,
                    new BrpBoolean(false, null)), BrpStapelHelper.his(20100101), BrpStapelHelper.act(1, 20100101)));

        // Execute
        final Lo3Stapel<Lo3InschrijvingInhoud> result = subject.converteer(bijhoudingStapel);

        // Expectation
        final Lo3InschrijvingInhoud expected = Lo3StapelHelper.lo3Inschrijving(null, null, null, null, null, 0, null, null, false);

        // Check
        Assert.assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        Assert.assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        Assert.assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        Assert.assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }
}
