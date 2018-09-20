/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.lo3.filter.VulBerichtFilter;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor {@link AbstractGvFormatter}
 */
@RunWith(MockitoJUnitRunner.class)
public class GvFormatterTest {

    private static final String GEMEENTE_CODE_0518 = "0518";
    private static final String LAND_CODE_6030 = "6030";
    private static final String GESLACHTSAANDUIDING_V = "V";

    @InjectMocks
    private Gv01Formatter subject;

    @Test
    public void test() {
        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(buildLo3Persoonslijst());

        final List<String> lo3Filterrubrieken = new ArrayList<>();
        lo3Filterrubrieken.add("01.01.10");
        lo3Filterrubrieken.add("01.01.20");
        lo3Filterrubrieken.add("01.03.10");
        lo3Filterrubrieken.add("08.09.10");

        // TODO: Gebruik mutatie bericht filter (maar dan moet de persoonslijst/categorieen die er uit komen ook wel
        // historie hebben)
        final List<Lo3CategorieWaarde> categorieenGefiltered = new VulBerichtFilter().filter(null, null, null, categorieen, lo3Filterrubrieken);

        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.nieuwIdentificatienummersRecord(19770101, null, 19770101).administratienummer(3450924321L).eindeRecord();
        final PersoonHisVolledig persoon = builder.build();

        final String resultaat = subject.maakPlatteTekst(persoon, categorieen, categorieenGefiltered);

        final String verwachteResultaat = "00000000Gv0134509243210005301032011001034509243210310008197701010801109100040518";
        Assert.assertEquals(verwachteResultaat, resultaat);
    }

    public Lo3Persoonslijst buildLo3Persoonslijst() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(buildPersoonstapel());
        builder.ouder1Stapel(buildOuder1Stapel());
        builder.ouder2Stapel(buildOuder2Stapel());
        builder.inschrijvingStapel(buildInschrijvingStapel());
        builder.verblijfplaatsStapel(buildVerblijfplaatsStapel());

        return builder.build();
    }

    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonstapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Persoon(3450924321L, "Scarlet", "Simpspoon", 19770101, GEMEENTE_CODE_0518, LAND_CODE_6030, GESLACHTSAANDUIDING_V),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(19770101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
    }

    private Lo3Stapel<Lo3OuderInhoud> buildOuder1Stapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Ouder(3450924321L, "Jessica", "Geller", 19170101, GEMEENTE_CODE_0518, LAND_CODE_6030, GESLACHTSAANDUIDING_V, 19770101),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(19770101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0)));
    }

    private Lo3Stapel<Lo3OuderInhoud> buildOuder2Stapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Ouder(3450924321L, "Jaap", "Jansen", 19160101, GEMEENTE_CODE_0518, LAND_CODE_6030, "M", 19770101),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(19770101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 0, 0)));
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijvingStapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Inschrijving(null, null, null, 19770202, GEMEENTE_CODE_0518, 0, 1, 19770102123014000L, true),
            null,
            Lo3Historie.NULL_HISTORIE,
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> buildVerblijfplaatsStapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Verblijfplaats(GEMEENTE_CODE_0518, 19770101, 19770101, "Straat", 14, "9654AA", "O"),
            null,
            Lo3StapelHelper.lo3His(19770101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0)));
    }

}
