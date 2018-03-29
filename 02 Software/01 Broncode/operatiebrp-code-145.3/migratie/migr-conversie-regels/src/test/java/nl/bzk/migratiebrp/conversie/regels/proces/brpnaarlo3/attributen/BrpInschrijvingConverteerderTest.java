/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrpInschrijvingConverteerderTest {

    private static final String ASSERT_INHOUD_GELIJK = "Inhoud niet gelijk";
    private static final String ASSERT_STAPEL_IS_NIET_LEEG = "Stapel is leeg";
    private static final String VERIFICATIE_OMS = "nieuweVerificatie";
    private static final String ASSERT_STAPEL_IS_NOT_NULL = "Stapel is null";
    private static final String ASSERT_CATEGORIE_IS_NOT_NULL = "Categorie is null";
    private static final String BRP_GEMEENTE_PARTIJ_CODE = "059901";
    private static final String LO3_GEMEENTE_CODE = "0599";
    private static final String RNI_PARTIJ_CODE = "250001";
    private static final int DATUM_VERVALLEN_VERIFICATIE = 2011_01_01;
    private static final int DATUM_OUDE_VERIFICATIE = 2010_01_01;
    private static final int DATUM_VERIFICATIE = 2013_01_01;
    private static final int DATUM_INSCHRIJVING = 1994_01_01;
    private static final int VERSIENUMMER = 1042;
    private static final long DATUMTIJDSTEMPEL = 1999_01_01_01_00_00L;
    private static final int DATUM_TIJD_REGISTRATIE = 1994_01_02;

    @Mock
    private BrpAttribuutConverteerder attribuutConverteerder;

    private BrpInschrijvingConverteerder subject;

    @Before
    public void setUp() {
        subject = new BrpInschrijvingConverteerder(attribuutConverteerder);
        // BRP Persoonskaart Inhoud
        when(attribuutConverteerder.converteerGemeenteCode(new BrpPartijCode(BRP_GEMEENTE_PARTIJ_CODE))).thenReturn(new Lo3GemeenteCode(LO3_GEMEENTE_CODE));
        when(attribuutConverteerder.converteerIndicatiePKVolledigGeconverteerd(new BrpBoolean(false))).thenReturn(null);
        // BRP Verstrekkingsbeperking Inhoud
        when(attribuutConverteerder.converteerIndicatieGeheim(new BrpBoolean(true))).thenReturn(new Lo3IndicatieGeheimCode(
                Lo3IndicatieGeheimCodeEnum.NIET_TER_UITVOERING_VAN_VOORSCHRIFT_EN_NIET_AAN_VRIJE_DERDEN_EN_NIET_AAN_KERKEN.getCode()));
        when(attribuutConverteerder.converteerIndicatieGeheim(new BrpBoolean(false))).thenReturn(new Lo3IndicatieGeheimCode(
                Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.getCode()));
        // BRP Verificatie Inhoud
        when(attribuutConverteerder.converteerDatum(new BrpDatum(DATUM_VERIFICATIE, null))).thenReturn(new Lo3Datum(DATUM_VERIFICATIE));
        when(attribuutConverteerder.converteerString(new BrpString(VERIFICATIE_OMS))).thenReturn(new Lo3String(VERIFICATIE_OMS));
        when(attribuutConverteerder.converteerRNIDeelnemer(new BrpPartijCode(RNI_PARTIJ_CODE))).thenReturn(new Lo3RNIDeelnemerCode("0101"));
        // BRP Inschrijving Inhoud
        when(attribuutConverteerder.converteerDatum(new BrpDatum(DATUM_INSCHRIJVING, null))).thenReturn(new Lo3Datum(DATUM_INSCHRIJVING));
        when(attribuutConverteerder.converteerVersienummer(new BrpLong((long) VERSIENUMMER))).thenReturn(new Lo3Integer(VERSIENUMMER));
        final BrpDatumTijd brpDatumTijd = BrpDatumTijd.fromDatumTijd(DATUMTIJDSTEMPEL, null);
        when(attribuutConverteerder.converteerDatumtijdstempel(brpDatumTijd)).thenReturn(brpDatumTijd.converteerNaarLo3Datumtijdstempel());

        // BRP Bijhouding Inhoud
        when(attribuutConverteerder.converteerRedenOpschortingBijhouding(BrpNadereBijhoudingsaardCode.OVERLEDEN))
                .thenReturn(new Lo3RedenOpschortingBijhoudingCode("O"));
    }

    @Test
    public void test() {
        final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                        BrpStapelHelper.inschrijving(DATUM_INSCHRIJVING, VERSIENUMMER, DATUMTIJDSTEMPEL),
                        BrpStapelHelper.his(DATUM_INSCHRIJVING),
                        BrpStapelHelper.act(9, DATUM_TIJD_REGISTRATIE)));

        final BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                        BrpStapelHelper.persoonskaart(BRP_GEMEENTE_PARTIJ_CODE, false),
                        BrpStapelHelper.his(DATUM_INSCHRIJVING),
                        BrpStapelHelper.act(1, DATUM_TIJD_REGISTRATIE)));

        final BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> beperkingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                        BrpStapelHelper.verstrekkingsbeperking(true),
                        BrpStapelHelper.his(DATUM_INSCHRIJVING),
                        BrpStapelHelper.act(4, DATUM_TIJD_REGISTRATIE)));

        final BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                        BrpStapelHelper.afgeleid(),
                        BrpStapelHelper.his(DATUM_INSCHRIJVING),
                        BrpStapelHelper.act(3, DATUM_TIJD_REGISTRATIE)));

        // Execute
        final Lo3Stapel<Lo3InschrijvingInhoud> result =
                subject.converteer(inschrijvingStapel, persoonskaartStapel, beperkingStapel, afgeleidAdministratiefStapel);

        // Expectation
        // cat(inhoud, historie, documentatie)
        // his(ingangsdatumGeldigheid)
        // akt(id)
        final Lo3InschrijvingInhoud expected =
                Lo3StapelHelper.lo3Inschrijving(null, null, null, DATUM_INSCHRIJVING, LO3_GEMEENTE_CODE, 7, VERSIENUMMER, 1999_01_010100_00_000L, false);

        // Check
        assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }

    @Test

    public void testBepaalVerificatieStapel() {
        final List<BrpStapel<BrpVerificatieInhoud>> stapels = new ArrayList<>();

        stapels.add(BrpStapelHelper.stapel(BrpStapelHelper.groep(
                BrpStapelHelper.verificatie(DATUM_VERVALLEN_VERIFICATIE, "vervallenVerificatie", RNI_PARTIJ_CODE),
                BrpStapelHelper.his(null, null, 2011_01_01090909L, 2012_01_01090909L),
                BrpStapelHelper.act(10, DATUM_VERVALLEN_VERIFICATIE),
                BrpStapelHelper.act(11, 2012_01_01))));
        assertNull(subject.bepaalVerificatieStapel(stapels));

        stapels.add(BrpStapelHelper.stapel(BrpStapelHelper.groep(
                BrpStapelHelper.verificatie(DATUM_OUDE_VERIFICATIE, "oudeVerificatie", RNI_PARTIJ_CODE),
                BrpStapelHelper.his(null, null, 2010_01_01090909L, null),
                BrpStapelHelper.act(9, DATUM_OUDE_VERIFICATIE))));
        assertNotNull(subject.bepaalVerificatieStapel(stapels));
        assertEquals(new BrpString("oudeVerificatie"), subject.bepaalVerificatieStapel(stapels).getActueel().getInhoud().getSoort());

        stapels.add(BrpStapelHelper.stapel(BrpStapelHelper.groep(
                BrpStapelHelper.verificatie(DATUM_VERIFICATIE, VERIFICATIE_OMS, RNI_PARTIJ_CODE),
                BrpStapelHelper.his(null, null, 2013_01_01090909L, null),
                BrpStapelHelper.act(12, DATUM_VERIFICATIE))));
        assertNotNull(subject.bepaalVerificatieStapel(stapels));
        assertEquals(new BrpString(VERIFICATIE_OMS), subject.bepaalVerificatieStapel(stapels).getActueel().getInhoud().getSoort());
    }

    @Test
    public void testConverteerVerificatie() {
        final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                        BrpStapelHelper.inschrijving(DATUM_INSCHRIJVING, VERSIENUMMER, DATUMTIJDSTEMPEL),
                        BrpStapelHelper.his(DATUM_INSCHRIJVING),
                        BrpStapelHelper.act(9, DATUM_TIJD_REGISTRATIE)));

        final BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                        BrpStapelHelper.persoonskaart(BRP_GEMEENTE_PARTIJ_CODE, false),
                        BrpStapelHelper.his(DATUM_INSCHRIJVING),
                        BrpStapelHelper.act(1, DATUM_TIJD_REGISTRATIE)));

        final BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> beperkingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                        BrpStapelHelper.verstrekkingsbeperking(true),
                        BrpStapelHelper.his(DATUM_INSCHRIJVING),
                        BrpStapelHelper.act(4, DATUM_TIJD_REGISTRATIE)));

        final BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                        BrpStapelHelper.afgeleid(),
                        BrpStapelHelper.his(DATUM_INSCHRIJVING),
                        BrpStapelHelper.act(3, DATUM_TIJD_REGISTRATIE)));

        final BrpStapel<BrpVerificatieInhoud> verificatieStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(
                        BrpStapelHelper.verificatie(DATUM_VERIFICATIE, VERIFICATIE_OMS, RNI_PARTIJ_CODE),
                        BrpStapelHelper.his(null, null, 2013_01_01090909L, null),
                        BrpStapelHelper.act(12, DATUM_VERIFICATIE)));

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
                        DATUM_INSCHRIJVING,
                        "0599",
                        7,
                        VERSIENUMMER,
                        1999_01_010100_00_000L,
                        false));
        builder.setDatumVerificatie(new Lo3Datum(DATUM_VERIFICATIE));
        builder.setOmschrijvingVerificatie(new Lo3String(VERIFICATIE_OMS));
        final Lo3InschrijvingInhoud expected = builder.build();

        // Check
        assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }

    @Test
    @Definitie(Definities.DEF058)
    @Requirement(Requirements.CCA07_BL05)
    public void testBijhoudingOpgeschort() {
        // Input
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                        "059901",
                        BrpBijhoudingsaardCode.INGEZETENE,
                        BrpNadereBijhoudingsaardCode.OVERLEDEN
                ), BrpStapelHelper.his(2010_01_01), BrpStapelHelper.act(1, 2010_01_01)));

        // Execute
        Lo3Stapel<Lo3InschrijvingInhoud> result = subject.converteer(bijhoudingStapel);
        result = subject.nabewerking(result, bijhoudingStapel);

        // Expectation
        final Lo3InschrijvingInhoud expected = Lo3StapelHelper.lo3Inschrijving(null, 2010_01_01, "O", null, null, 0, null, null, false);

        // Check
        assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }

    @Test
    @Definitie(Definities.DEF058)
    @Requirement(Requirements.CCA07_BL05)
    public void testVerhuizingNaOverlijden() {
        // Input
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel =
                BrpStapelHelper.stapel(
                        BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                                "059901",
                                BrpBijhoudingsaardCode.INGEZETENE,
                                BrpNadereBijhoudingsaardCode.OVERLEDEN
                        ), BrpStapelHelper.his(DATUM_VERIFICATIE), BrpStapelHelper.act(1, DATUM_VERIFICATIE)),
                        BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                                "051801",
                                BrpBijhoudingsaardCode.INGEZETENE,
                                BrpNadereBijhoudingsaardCode.OVERLEDEN
                        ), BrpStapelHelper.his(2012_12_31, DATUM_VERIFICATIE, 2012_12_31, null), BrpStapelHelper.act(
                                1,
                                2012_12_31)),
                        BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                                "051801",
                                BrpBijhoudingsaardCode.ONBEKEND,
                                BrpNadereBijhoudingsaardCode.ONBEKEND
                        ), BrpStapelHelper.his(2010_01_01, 2012_12_31, 2010_01_01, null), BrpStapelHelper.act(
                                1,
                                2010_01_01)));

        // Execute
        Lo3Stapel<Lo3InschrijvingInhoud> result = subject.converteer(bijhoudingStapel);
        result = subject.nabewerking(result, bijhoudingStapel);

        // Expectation
        final Lo3InschrijvingInhoud expected = Lo3StapelHelper.lo3Inschrijving(null, 2012_12_31, "O", null, null, 0, null, null, false);

        // Check
        assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }

    @Test
    @Definitie(Definities.DEF080)
    @Requirement(Requirements.CCA07_BL05)
    public void testBijhoudingOpgeschortVOW() {
        // Input
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                        "059901",
                        BrpBijhoudingsaardCode.NIET_INGEZETENE,
                        BrpNadereBijhoudingsaardCode.VERTROKKEN_ONBEKEND_WAARHEEN
                ), BrpStapelHelper.his(2010_01_01), BrpStapelHelper.act(1, 2010_01_01)));

        // Execute
        Lo3Stapel<Lo3InschrijvingInhoud> result = subject.converteer(bijhoudingStapel);
        result = subject.nabewerking(result, bijhoudingStapel);

        // Expectation
        final Lo3InschrijvingInhoud expected = Lo3StapelHelper.lo3Inschrijving(null, 2010_01_01, "E", null, null, 0, null, null, false);

        // Check
        assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }

    @Test
    @Definitie(Definities.DEF079)
    @Requirement(Requirements.CCA07_BL05)
    public void testBijhoudingNietOpgeschort() {
        // Input
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.bijhouding(
                        "059901",
                        BrpBijhoudingsaardCode.INGEZETENE,
                        BrpNadereBijhoudingsaardCode.ACTUEEL
                ), BrpStapelHelper.his(2010_01_01), BrpStapelHelper.act(1, 2010_01_01)));

        // Execute
        final Lo3Stapel<Lo3InschrijvingInhoud> result = subject.converteer(bijhoudingStapel);

        // Expectation
        final Lo3InschrijvingInhoud expected = Lo3StapelHelper.lo3Inschrijving(null, null, null, null, null, 0, null, null, false);

        // Check
        assertNotNull(ASSERT_STAPEL_IS_NOT_NULL, result);
        assertFalse(ASSERT_STAPEL_IS_NIET_LEEG, result.isEmpty());
        assertNotNull(ASSERT_CATEGORIE_IS_NOT_NULL, result.get(0));

        assertEquals(ASSERT_INHOUD_GELIJK, expected, result.get(0).getInhoud());
    }
}
