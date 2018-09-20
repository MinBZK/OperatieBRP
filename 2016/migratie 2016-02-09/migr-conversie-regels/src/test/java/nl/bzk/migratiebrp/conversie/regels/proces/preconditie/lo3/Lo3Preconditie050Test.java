/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import org.junit.Test;

public class Lo3Preconditie050Test extends AbstractLoggingTest {

    private static final Lo3PreconditiesTester TESTER = new Lo3PreconditiesTester();
    private static final Lo3Datum DEFAULT_DATUM = new Lo3Datum(19900101);

    // Situatie 1
    private final Lo3Historie s1Lo3Historie1 = new Lo3Historie(Lo3IndicatieOnjuist.O, new Lo3Datum(19930107), new Lo3Datum(20050901));
    private final Lo3Historie s1Lo3Historie2 = new Lo3Historie(null, new Lo3Datum(19951229), new Lo3Datum(19960109));
    private final Lo3Historie s1Lo3Historie3 = new Lo3Historie(null, new Lo3Datum(20030107), new Lo3Datum(20051208));

    private final Lo3Herkomst s1Lo3Herkomst1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_61, 0, 2);
    private final Lo3Herkomst s1Lo3Herkomst2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_61, 0, 1);
    private final Lo3Herkomst s1Lo3Herkomst3 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0);

    private final Lo3GezagsverhoudingInhoud s1InhoudLeeg = new Lo3GezagsverhoudingInhoud(null, null);
    private final Lo3GezagsverhoudingInhoud s1InhoudGevuld = new Lo3GezagsverhoudingInhoud(new Lo3IndicatieGezagMinderjarige("1"), null);

    // Situatie 2
    private final Lo3Historie s2Lo3Historie1 = new Lo3Historie(null, new Lo3Datum(0), new Lo3Datum(20080707));
    private final Lo3Historie s2Lo3Historie2 = new Lo3Historie(Lo3IndicatieOnjuist.O, new Lo3Datum(20051013), new Lo3Datum(20080218));
    private final Lo3Historie s2Lo3Historie3 = new Lo3Historie(Lo3IndicatieOnjuist.O, new Lo3Datum(20051013), new Lo3Datum(20051013));
    private final Lo3Historie s2Lo3Historie4 = new Lo3Historie(Lo3IndicatieOnjuist.O, new Lo3Datum(0), new Lo3Datum(20010321));

    private final Lo3Herkomst s2Lo3Herkomst1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_59, 0, 3);
    private final Lo3Herkomst s2Lo3Herkomst2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_59, 0, 2);
    private final Lo3Herkomst s2Lo3Herkomst3 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_59, 0, 1);
    private final Lo3Herkomst s2Lo3Herkomst4 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_09, 0, 0);

    private final Lo3KindInhoud s2InhoudLeeg = new Lo3KindInhoud(null, null, null, null, null, null, null, null, null);
    private final Lo3KindInhoud s2InhoudGevuld = new Lo3KindInhoud(
        null,
        null,
        Lo3String.wrap("Piet"),
        null,
        null,
        Lo3String.wrap("Jansen"),
        null,
        null,
        null);

    // Situatie 3
    private final Lo3Historie s3Lo3Historie1 = new Lo3Historie(Lo3IndicatieOnjuist.O, new Lo3Datum(19960107), new Lo3Datum(19950901));
    private final Lo3Historie s3Lo3Historie2 = new Lo3Historie(null, new Lo3Datum(19951229), new Lo3Datum(19960109));
    private final Lo3Historie s3Lo3Historie3 = new Lo3Historie(null, new Lo3Datum(20030107), new Lo3Datum(20051208));

    private final Lo3Herkomst s3Lo3Herkomst1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_61, 0, 2);
    private final Lo3Herkomst s3Lo3Herkomst2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_61, 0, 1);
    private final Lo3Herkomst s3Lo3Herkomst3 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0);

    private final Lo3GezagsverhoudingInhoud s3InhoudLeeg = new Lo3GezagsverhoudingInhoud(null, null);
    private final Lo3GezagsverhoudingInhoud s3InhoudGevuld = new Lo3GezagsverhoudingInhoud(new Lo3IndicatieGezagMinderjarige("1"), null);

    // Situatie 4
    private final Lo3Historie s4Lo3Historie1 = new Lo3Historie(null, null, new Lo3Datum(20080707));
    private final Lo3Historie s4Lo3Historie2 = new Lo3Historie(Lo3IndicatieOnjuist.O, new Lo3Datum(20051013), new Lo3Datum(20080218));
    private final Lo3Historie s4Lo3Historie3 = new Lo3Historie(Lo3IndicatieOnjuist.O, new Lo3Datum(20051013), new Lo3Datum(20051013));
    private final Lo3Historie s4Lo3Historie4 = new Lo3Historie(Lo3IndicatieOnjuist.O, null, new Lo3Datum(20010321));

    private final Lo3Herkomst c4Lo3Herkomst0 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
    private final Lo3Herkomst c4Lo3Herkomst1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1);

    private final Lo3NationaliteitInhoud c4InhoudDuits = new Lo3NationaliteitInhoud(new Lo3NationaliteitCode("6029"), null, null, null);
    private final Lo3NationaliteitInhoud c4InhoudLegeRij = new Lo3NationaliteitInhoud(null, null, null, null);
    private final Lo3Historie c4HistorieLegeRij = new Lo3Historie(null, DEFAULT_DATUM, DEFAULT_DATUM);

    // Algemeen
    private final Lo3Documentatie documentatie = new Lo3Documentatie(
        1L,
        null,
        null,
        new Lo3GemeenteCode("9999"),
        new Lo3Datum(20051001),
        Lo3String.wrap("Document"),
        null,
        null);

    /**
     * Controleer op 85.10.
     */
    @Test
    public void testControleerPreconditie050Situatie1VolgordeA() {
        TESTER.controleerPreconditie050(maakStapelSituatie1VolgordeA());
        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    /**
     * Controleer op 85.10.
     */
    @Test
    public void testControleerPreconditie050Situatie1VolgordeB() {
        TESTER.controleerPreconditie050(maakStapelSituatie1VolgordeB());
        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    /**
     * Geldige situatie.
     */
    @Test
    public void testControleerPreconditie050Situatie2() {
        TESTER.controleerPreconditie050(maakStapelSituatie2());
        assertGeenLogRegels();
    }

    /**
     * Controleer op 86.10.
     */
    @Test
    public void testControleerPreconditie050Situatie3() {
        TESTER.controleerPreconditie050(maakStapelSituatie3());
        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    /**
     * Geldige situatie met null waarden.
     */
    @Test
    public void testControleerPreconditie050Situatie4() {
        TESTER.controleerPreconditie050(maakStapelSituatie4());
        assertGeenLogRegels();

        TESTER.controleerPreconditie050(maakStapelSituatie4a());
        assertGeenLogRegels();

        TESTER.controleerPreconditie050(maakStapelSituatie4b());
        assertGeenLogRegels();
    }

    // @formatter:off
    /**
     * Tests mbt Cat04 85.10 86.10 PRE050 triggered? testcases Situatie 1 <leeg> 01-01-1990 01-01-1990 Duits 01-01-1990
     * 01-01-1990 nee 1 Situatie 2 <leeg> 01-01-1990 01-01-1990 Duits 01-01-1991 dont' care ja 3 Situatie 3 <leeg>
     * 01-01-1990 01-01-1990 Duits 01-01-1989 01-01-1990 nee 1 Situatie 4 <leeg> 01-01-1990 01-01-1990 Duits don't care
     * 01-01-1991 ja 3 Situatie 5 <leeg> 01-01-1990 01-01-1990 Duits 01-01-1990 01-01-1989 nee 1
     */
    // @formatter:on
    @Test
    public void cat04Situatie1() {
        final Lo3Historie historie = new Lo3Historie(new Lo3IndicatieOnjuist("O"), DEFAULT_DATUM, DEFAULT_DATUM);

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie, c4Lo3Herkomst1));
        categorieen.add(maakLegeActueleNationaliteitRij());
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));

        assertGeenLogRegels();
    }

    @Test
    public void cat04Situatie2a() {
        final Lo3Historie historie = new Lo3Historie(new Lo3IndicatieOnjuist("O"), new Lo3Datum(19910101), DEFAULT_DATUM);

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie, c4Lo3Herkomst1));
        categorieen.add(maakLegeActueleNationaliteitRij());
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));
        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);

        // Test of de code maar 1x PRE050 logt
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));
        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    @Test
    public void cat04Situatie2b() {
        final Lo3Historie historie = new Lo3Historie(new Lo3IndicatieOnjuist("O"), new Lo3Datum(19910101), new Lo3Datum(19910101));

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie, c4Lo3Herkomst1));
        categorieen.add(maakLegeActueleNationaliteitRij());
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));

        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    @Test
    public void cat04Situatie2c() {
        final Lo3Historie historie = new Lo3Historie(new Lo3IndicatieOnjuist("O"), new Lo3Datum(19910101), new Lo3Datum(19890101));

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie, c4Lo3Herkomst1));
        categorieen.add(maakLegeActueleNationaliteitRij());
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));

        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    @Test
    public void cat04Situatie3() {
        final Lo3Historie historie = new Lo3Historie(new Lo3IndicatieOnjuist("O"), new Lo3Datum(19890101), DEFAULT_DATUM);

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie, c4Lo3Herkomst1));
        categorieen.add(maakLegeActueleNationaliteitRij());
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));

        assertGeenLogRegels();
    }

    @Test
    public void cat04Situatie4a() {
        final Lo3Historie historie = new Lo3Historie(new Lo3IndicatieOnjuist("O"), DEFAULT_DATUM, new Lo3Datum(19910101));

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie, c4Lo3Herkomst1));
        categorieen.add(maakLegeActueleNationaliteitRij());
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));

        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    @Test
    public void cat04Situatie4b() {
        final Lo3Historie historie = new Lo3Historie(new Lo3IndicatieOnjuist("O"), new Lo3Datum(19910101), new Lo3Datum(19910101));

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie, c4Lo3Herkomst1));
        categorieen.add(maakLegeActueleNationaliteitRij());
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));

        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    @Test
    public void cat04Situatie4c() {
        final Lo3Historie historie = new Lo3Historie(new Lo3IndicatieOnjuist("O"), new Lo3Datum(19890101), new Lo3Datum(19910101));

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie, c4Lo3Herkomst1));
        categorieen.add(maakLegeActueleNationaliteitRij());
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));

        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    @Test
    public void cat04Situatie5() {
        final Lo3Historie historie2 = new Lo3Historie(new Lo3IndicatieOnjuist("O"), DEFAULT_DATUM, new Lo3Datum(19890101));

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie2, c4Lo3Herkomst1));
        categorieen.add(maakLegeActueleNationaliteitRij());
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));

        assertGeenLogRegels();
    }

    private Lo3Categorie<Lo3NationaliteitInhoud> maakLegeActueleNationaliteitRij() {
        return maakLegeNationaliteitRij(c4Lo3Herkomst0);
    }

    private Lo3Categorie<Lo3NationaliteitInhoud> maakLegeNationaliteitRij(final Lo3Herkomst herkomst) {
        return new Lo3Categorie<>(c4InhoudLegeRij, null, c4HistorieLegeRij, herkomst);
    }

    // @formatter:off
    /**
     * 
     * Cat04 meerdere gevulde rijen 85.10 86.10 PRE050 triggered? testcases Gevulde rij Situatie 1 Duits 01-01-1990
     * 01-01-1989 nee 1 <leeg> 01-01-1990 01-01-1990 Duits 01-01-1989 01-01-1990
     * 
     * Situatie 2 Duits 01-01-1991 01-01-1990 ja 1 <leeg> 01-01-1990 01-01-1990 Duits 01-01-1990 01-01-1991
     */
    // @formatter:on
    @Test
    public void cat04MeerdereRijenSituatie1() {
        final Lo3Herkomst c4Lo3Herkomst2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 2);

        final Lo3Historie historie0 = new Lo3Historie(null, DEFAULT_DATUM, new Lo3Datum(19890101));
        final Lo3Historie historie2 = new Lo3Historie(new Lo3IndicatieOnjuist("O"), new Lo3Datum(19890101), DEFAULT_DATUM);

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie2, c4Lo3Herkomst2));
        categorieen.add(maakLegeNationaliteitRij(c4Lo3Herkomst1));
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie0, c4Lo3Herkomst0));
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));

        assertGeenLogRegels();
    }

    @Test
    public void cat04MeerdereRijenSituatie2() {
        final Lo3Herkomst c4Lo3Herkomst2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 2);

        final Lo3Historie historie0 = new Lo3Historie(null, DEFAULT_DATUM, new Lo3Datum(19910101));
        final Lo3Historie historie2 = new Lo3Historie(new Lo3IndicatieOnjuist("O"), new Lo3Datum(19910101), DEFAULT_DATUM);

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie2, c4Lo3Herkomst2));
        categorieen.add(maakLegeNationaliteitRij(c4Lo3Herkomst1));
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie0, c4Lo3Herkomst0));
        TESTER.controleerPreconditie050(new Lo3Stapel<>(categorieen));

        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelSituatie1VolgordeA() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(s1InhoudLeeg, null, s1Lo3Historie1, s1Lo3Herkomst1));
        categorieen.add(new Lo3Categorie<>(s1InhoudGevuld, null, s1Lo3Historie2, s1Lo3Herkomst2));
        categorieen.add(new Lo3Categorie<>(s1InhoudLeeg, null, s1Lo3Historie3, s1Lo3Herkomst3));
        return new Lo3Stapel<>(categorieen);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelSituatie1VolgordeB() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(s1InhoudLeeg, null, s1Lo3Historie3, s1Lo3Herkomst3));
        categorieen.add(new Lo3Categorie<>(s1InhoudLeeg, null, s1Lo3Historie1, s1Lo3Herkomst1));
        categorieen.add(new Lo3Categorie<>(s1InhoudGevuld, null, s1Lo3Historie2, s1Lo3Herkomst2));
        return new Lo3Stapel<>(categorieen);
    }

    private Lo3Stapel<Lo3KindInhoud> maakStapelSituatie2() {
        final List<Lo3Categorie<Lo3KindInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(s2InhoudLeeg, documentatie, s2Lo3Historie1, s2Lo3Herkomst1));
        categorieen.add(new Lo3Categorie<>(s2InhoudLeeg, documentatie, s2Lo3Historie2, s2Lo3Herkomst2));
        categorieen.add(new Lo3Categorie<>(s2InhoudGevuld, documentatie, s2Lo3Historie3, s2Lo3Herkomst3));
        categorieen.add(new Lo3Categorie<>(s2InhoudGevuld, documentatie, s2Lo3Historie4, s2Lo3Herkomst4));
        return new Lo3Stapel<>(categorieen);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelSituatie3() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(s3InhoudLeeg, null, s3Lo3Historie1, s3Lo3Herkomst1));
        categorieen.add(new Lo3Categorie<>(s3InhoudGevuld, null, s3Lo3Historie2, s3Lo3Herkomst2));
        categorieen.add(new Lo3Categorie<>(s3InhoudLeeg, null, s3Lo3Historie3, s3Lo3Herkomst3));
        return new Lo3Stapel<>(categorieen);
    }

    private Lo3Stapel<Lo3KindInhoud> maakStapelSituatie4() {
        final List<Lo3Categorie<Lo3KindInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(s2InhoudLeeg, documentatie, s4Lo3Historie1, s2Lo3Herkomst1));
        categorieen.add(new Lo3Categorie<>(s2InhoudLeeg, documentatie, s4Lo3Historie2, s2Lo3Herkomst2));
        categorieen.add(new Lo3Categorie<>(s2InhoudGevuld, documentatie, s4Lo3Historie3, s2Lo3Herkomst3));
        categorieen.add(new Lo3Categorie<>(s2InhoudGevuld, documentatie, s4Lo3Historie4, s2Lo3Herkomst4));
        return new Lo3Stapel<>(categorieen);
    }

    private Lo3Stapel<Lo3KindInhoud> maakStapelSituatie4a() {
        final List<Lo3Categorie<Lo3KindInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(s2InhoudLeeg, documentatie, s4Lo3Historie1, s2Lo3Herkomst1));
        categorieen.add(new Lo3Categorie<>(s2InhoudLeeg, documentatie, s4Lo3Historie2, s2Lo3Herkomst2));
        categorieen.add(new Lo3Categorie<>(s2InhoudGevuld, documentatie, s4Lo3Historie1, s2Lo3Herkomst3));
        categorieen.add(new Lo3Categorie<>(s2InhoudGevuld, documentatie, s4Lo3Historie4, s2Lo3Herkomst4));
        return new Lo3Stapel<>(categorieen);
    }

    private Lo3Stapel<Lo3KindInhoud> maakStapelSituatie4b() {
        final List<Lo3Categorie<Lo3KindInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(s2InhoudLeeg, documentatie, s4Lo3Historie2, s2Lo3Herkomst1));
        categorieen.add(new Lo3Categorie<>(s2InhoudLeeg, documentatie, s4Lo3Historie2, s2Lo3Herkomst2));
        categorieen.add(new Lo3Categorie<>(s2InhoudGevuld, documentatie, s4Lo3Historie1, s2Lo3Herkomst3));
        categorieen.add(new Lo3Categorie<>(s2InhoudGevuld, documentatie, s4Lo3Historie4, s2Lo3Herkomst4));
        return new Lo3Stapel<>(categorieen);
    }

    private static final class Lo3PreconditiesTester extends AbstractLo3Precondities {
    }
}
