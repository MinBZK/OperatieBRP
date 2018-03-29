/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.filter.VulBerichtFilter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
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

    private static final String GEMEENTE_CODE_0518    = "0518";
    private static final String LAND_CODE_6030        = "6030";
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
        final List<Lo3CategorieWaarde> categorieenGefiltered = new VulBerichtFilter().filter(null, null, null, null, categorieen, lo3Filterrubrieken);

        // Maak persoonsgegevens om actuele anummer uit te halen
        final Persoonslijst persoon = maakPersoonslijst();

        final String resultaat = subject.maakPlatteTekst(persoon, null, categorieen, categorieenGefiltered);

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
        return Lo3StapelHelper.lo3Stapel(
            Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Persoon("3450924321", "Scarlet", "Simpspoon", 19770101, GEMEENTE_CODE_0518, LAND_CODE_6030, GESLACHTSAANDUIDING_V),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(19770101),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
    }

    private Lo3Stapel<Lo3OuderInhoud> buildOuder1Stapel() {
        return Lo3StapelHelper.lo3Stapel(
            Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Ouder("3450924321", "Jessica", "Geller", 19170101, GEMEENTE_CODE_0518, LAND_CODE_6030, GESLACHTSAANDUIDING_V, 19770101),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(19770101),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0)));
    }

    private Lo3Stapel<Lo3OuderInhoud> buildOuder2Stapel() {
        return Lo3StapelHelper.lo3Stapel(
            Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Ouder("3450924321", "Jaap", "Jansen", 19160101, GEMEENTE_CODE_0518, LAND_CODE_6030, "M", 19770101),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(19770101),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 0, 0)));
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijvingStapel() {
        return Lo3StapelHelper.lo3Stapel(
            Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Inschrijving(null, null, null, 19770202, GEMEENTE_CODE_0518, 0, 1, 19770102123014000L, true),
                null,
                new Lo3Historie(null, new Lo3Datum(0), new Lo3Datum(0)),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> buildVerblijfplaatsStapel() {
        return Lo3StapelHelper.lo3Stapel(
            Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Verblijfplaats(GEMEENTE_CODE_0518, 19770101, 19770101, "Straat", 14, "9654AA", "O"),
                null,
                Lo3StapelHelper.lo3His(19770101),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0)));
    }

    private Persoonslijst maakPersoonslijst() {

        final ZonedDateTime tsReg = ZonedDateTime.of(1920, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        final AdministratieveHandeling administratieveHandeling = AdministratieveHandeling.converter().converteer(TestVerantwoording
            .maakAdministratieveHandeling(-21L, "000034", tsReg, SoortAdministratieveHandeling
                .GBA_BIJHOUDING_ACTUEEL)
            .metObject(TestVerantwoording.maakActieBuilder(-21L, SoortActie.REGISTRATIE_AANVANG_HUWELIJK, tsReg, "000001", null)
            ).build());
        final Actie actieInhoud = administratieveHandeling.getActies().iterator().next();

        final MetaObject.Builder basispersoon = MetaObject.maakBuilder();
        basispersoon.metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId())).metId(4645);
        final MetaGroep.Builder idnrGroep = basispersoon.metGroep().metGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId());
        final MetaRecord.Builder inrnrRecord = idnrGroep.metRecord().metId(1231213).metActieInhoud(actieInhoud);
        inrnrRecord.metAttribuut().metType(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()).metWaarde("3450924321");

        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(administratieveHandeling);
        final Persoonslijst persoon = new Persoonslijst(basispersoon.build(), 0L);
        return persoon;
    }
}
