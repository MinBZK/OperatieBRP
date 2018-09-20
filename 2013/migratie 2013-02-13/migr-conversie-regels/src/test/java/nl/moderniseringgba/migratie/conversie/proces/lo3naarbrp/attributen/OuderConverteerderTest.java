/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieRelatie;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.testutils.ReflectionUtil;

import org.junit.Assert;
import org.junit.Test;

public class OuderConverteerderTest extends AbstractComponentTest {

    @Inject
    private OuderConverteerder ouderConverteerder;

    private static Lo3Stapel<Lo3OuderInhoud> buildOuder1Stapel() {
        final Lo3OuderInhoud inhoud = new Lo3OuderInhoud(1111111111L,// aNummer,
                111111111L,// burgerservicenummer,
                "Jaap",// voornamen,
                null,// adellijkeTitelPredikaatCode,
                null,// voorvoegselGeslachtsnaam,
                "Jaspers",// geslachtsnaam,
                new Lo3Datum(19430101),// geboortedatum,
                new Lo3GemeenteCode("0518"),// geboorteGemeenteCode,
                new Lo3LandCode("6030"),// geboorteLandCode,
                Lo3GeslachtsaanduidingEnum.MAN.asElement(),// geslachtsaanduiding,
                new Lo3Datum(19780101)// familierechtelijkeBetrekking
                );

        final Lo3Documentatie documentatie =
                new Lo3Documentatie(913, new Lo3GemeenteCode("0518"), "A1", null, null, null, null, null);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(19780101), new Lo3Datum(20090102));

        final List<Lo3Categorie<Lo3OuderInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3OuderInhoud>(inhoud, documentatie, historie, new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_02, 0, 0)));

        return new Lo3Stapel<Lo3OuderInhoud>(categorieen);
    }

    public static Lo3Stapel<Lo3OuderInhoud> buildOuder2Stapel() {
        final Lo3OuderInhoud inhoud = new Lo3OuderInhoud(2222222222L,// aNummer,
                222222222L,// burgerservicenummer,
                "Gloria",// voornamen,
                null,// adellijkeTitelPredikaatCode,
                null,// voorvoegselGeslachtsnaam,
                "Jaspers-de Parel",// geslachtsnaam,
                new Lo3Datum(19440101),// geboortedatum,
                new Lo3GemeenteCode("0599"),// geboorteGemeenteCode,
                new Lo3LandCode("6030"),// geboorteLandCode,
                Lo3GeslachtsaanduidingEnum.VROUW.asElement(),// geslachtsaanduiding,
                new Lo3Datum(19780101)// familierechtelijkeBetrekking
                );

        final Lo3Documentatie documentatie =
                new Lo3Documentatie(913, new Lo3GemeenteCode("0518"), "A1", null, null, null, null, null);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(19780101), new Lo3Datum(20090102));

        final List<Lo3Categorie<Lo3OuderInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3OuderInhoud>(inhoud, documentatie, historie, new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_03, 0, 0)));

        return new Lo3Stapel<Lo3OuderInhoud>(categorieen);
    }

    private static Lo3Stapel<Lo3GezagsverhoudingInhoud> buildGezagStapel() {
        final Lo3GezagsverhoudingInhoud inhoud =
                new Lo3GezagsverhoudingInhoud(Lo3IndicatieGezagMinderjarigeEnum.OUDER_1.asElement(), null);

        final Lo3Documentatie documentatie =
                new Lo3Documentatie(913, new Lo3GemeenteCode("0518"), "A1", null, null, null, null, null);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20090101), new Lo3Datum(20090102));

        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, documentatie, historie, new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_11, 0, 0)));

        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(categorieen);
    }

    private static Lo3Categorie<Lo3PersoonInhoud> buildPersoonStapel() {
        final Lo3PersoonInhoud inhoud = new Lo3PersoonInhoud(9999999999L,// aNummer,
                999999999L,// burgerservicenummer,
                "Billy", // voornamen,
                null,// adellijkeTitelPredikaatCode,
                null,// voorvoegselGeslachtsnaam,
                "Jaspers",// geslachtsnaam,
                new Lo3Datum(19780101),// geboortedatum,
                new Lo3GemeenteCode("0518"),// geboorteGemeenteCode,
                new Lo3LandCode("6030"),// geboorteLandCode,
                Lo3GeslachtsaanduidingEnum.MAN.asElement(),// geslachtsaanduiding,
                Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement(),// aanduidingNaamgebruikCode,
                null,// vorigANummer,
                null// volgendANummer
                );

        final Lo3Documentatie documentatie =
                new Lo3Documentatie(913, new Lo3GemeenteCode("0518"), "A1", null, null, null, null, null);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20090101), new Lo3Datum(20090102));

        return new Lo3Categorie<Lo3PersoonInhoud>(inhoud, documentatie, historie, new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_01, 0, 0));
    }

    @Test
    public void test() {
        final List<Lo3Stapel<Lo3OuderInhoud>> ouder1Stapels = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();
        ouder1Stapels.add(buildOuder1Stapel());

        final List<Lo3Stapel<Lo3OuderInhoud>> ouder2Stapels = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();
        ouder2Stapels.add(buildOuder2Stapel());

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel = buildGezagStapel();
        final Lo3Categorie<Lo3PersoonInhoud> persoon = buildPersoonStapel();

        final MigratiePersoonslijstBuilder builder = new MigratiePersoonslijstBuilder();
        ouderConverteerder.converteer(ouder1Stapels, ouder2Stapels, gezagsverhoudingStapel, persoon, builder);

        @SuppressWarnings("unchecked")
        final List<MigratieRelatie> relaties = (List<MigratieRelatie>) ReflectionUtil.getField(builder, "relaties");

        checkRelaties(relaties);
    }

    private void checkRelaties(final List<MigratieRelatie> relaties) {
        Assert.assertNotNull(relaties);
        Assert.assertEquals(1, relaties.size());

        final MigratieRelatie relatie = relaties.get(0);
        Assert.assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoortRelatieCode());
        Assert.assertEquals(BrpSoortBetrokkenheidCode.KIND, relatie.getRolCode());
        Assert.assertNull(relatie.getRelatieStapel());

        Assert.assertNotNull(relatie.getBetrokkenheden());
        Assert.assertEquals(2, relatie.getBetrokkenheden().size());
        for (final MigratieBetrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
            final Long ouderAnummer =
                    betrokkenheid.getIdentificatienummersStapel().get(0).getInhoud().getAdministratienummer();

            if (1111111111L == ouderAnummer.longValue()) {
                checkOuder1(betrokkenheid);
            } else if (2222222222L == ouderAnummer.longValue()) {
                checkOuder2(betrokkenheid);
            } else {
                Assert.fail();
            }
        }
    }

    private void checkOuder1(final MigratieBetrokkenheid betrokkenheid) {
        Assert.assertNotNull(betrokkenheid);
        Assert.assertEquals(BrpSoortBetrokkenheidCode.OUDER, betrokkenheid.getRol());

        final MigratieStapel<BrpGeboorteInhoud> geboorteStapel = betrokkenheid.getGeboorteStapel();
        final MigratieStapel<BrpGeslachtsaanduidingInhoud> geslachtStapel =
                betrokkenheid.getGeslachtsaanduidingStapel();
        final MigratieStapel<BrpIdentificatienummersInhoud> identificatieStapel =
                betrokkenheid.getIdentificatienummersStapel();
        final MigratieStapel<BrpOuderlijkGezagInhoud> gezagStapel = betrokkenheid.getOuderlijkGezagStapel();
        final MigratieStapel<BrpSamengesteldeNaamInhoud> naamStapel = betrokkenheid.getSamengesteldeNaamStapel();

        Assert.assertNotNull(geboorteStapel);
        Assert.assertEquals(1, geboorteStapel.size());
        final MigratieGroep<BrpGeboorteInhoud> geboorteGroep = geboorteStapel.get(0);
        Assert.assertNotNull(geboorteGroep);
        Assert.assertEquals(new BrpDatum(19430101), geboorteGroep.getInhoud().getGeboortedatum());

        Assert.assertNotNull(geslachtStapel);
        Assert.assertEquals(1, geslachtStapel.size());
        final MigratieGroep<BrpGeslachtsaanduidingInhoud> geslachtGroep = geslachtStapel.get(0);
        Assert.assertNotNull(geslachtGroep);
        Assert.assertEquals(BrpGeslachtsaanduidingCode.MAN, geslachtGroep.getInhoud().getGeslachtsaanduiding());

        Assert.assertNotNull(identificatieStapel);
        Assert.assertEquals(1, identificatieStapel.size());
        final MigratieGroep<BrpIdentificatienummersInhoud> identificatieGroep = identificatieStapel.get(0);
        Assert.assertNotNull(identificatieGroep);
        Assert.assertEquals(new Long(1111111111L), identificatieGroep.getInhoud().getAdministratienummer());
        Assert.assertEquals(new Long(111111111L), identificatieGroep.getInhoud().getBurgerservicenummer());

        Assert.assertNotNull(gezagStapel);
        Assert.assertEquals(1, gezagStapel.size());
        final MigratieGroep<BrpOuderlijkGezagInhoud> gezagGroep = gezagStapel.get(0);
        Assert.assertNotNull(gezagGroep);
        Assert.assertEquals(Boolean.TRUE, gezagGroep.getInhoud().getOuderHeeftGezag());

        Assert.assertNotNull(naamStapel);
        Assert.assertEquals(1, naamStapel.size());
        final MigratieGroep<BrpSamengesteldeNaamInhoud> naamGroep = naamStapel.get(0);
        Assert.assertNotNull(naamGroep);
        Assert.assertEquals("Jaap", naamGroep.getInhoud().getVoornamen());
    }

    private void checkOuder2(final MigratieBetrokkenheid betrokkenheid) {
        Assert.assertNotNull(betrokkenheid);
        Assert.assertEquals(BrpSoortBetrokkenheidCode.OUDER, betrokkenheid.getRol());

        final MigratieStapel<BrpGeboorteInhoud> geboorteStapel = betrokkenheid.getGeboorteStapel();
        final MigratieStapel<BrpGeslachtsaanduidingInhoud> geslachtStapel =
                betrokkenheid.getGeslachtsaanduidingStapel();
        final MigratieStapel<BrpIdentificatienummersInhoud> identificatieStapel =
                betrokkenheid.getIdentificatienummersStapel();
        final MigratieStapel<BrpOuderlijkGezagInhoud> gezagStapel = betrokkenheid.getOuderlijkGezagStapel();
        final MigratieStapel<BrpSamengesteldeNaamInhoud> naamStapel = betrokkenheid.getSamengesteldeNaamStapel();

        Assert.assertNotNull(geboorteStapel);
        Assert.assertEquals(1, geboorteStapel.size());
        final MigratieGroep<BrpGeboorteInhoud> geboorteGroep = geboorteStapel.get(0);
        Assert.assertNotNull(geboorteGroep);
        Assert.assertEquals(new BrpDatum(19440101), geboorteGroep.getInhoud().getGeboortedatum());

        Assert.assertNotNull(geslachtStapel);
        Assert.assertEquals(1, geslachtStapel.size());
        final MigratieGroep<BrpGeslachtsaanduidingInhoud> geslachtGroep = geslachtStapel.get(0);
        Assert.assertNotNull(geslachtGroep);
        Assert.assertEquals(BrpGeslachtsaanduidingCode.VROUW, geslachtGroep.getInhoud().getGeslachtsaanduiding());

        Assert.assertNotNull(identificatieStapel);
        Assert.assertEquals(1, identificatieStapel.size());
        final MigratieGroep<BrpIdentificatienummersInhoud> identificatieGroep = identificatieStapel.get(0);
        Assert.assertNotNull(identificatieGroep);
        Assert.assertEquals(new Long(2222222222L), identificatieGroep.getInhoud().getAdministratienummer());
        Assert.assertEquals(new Long(222222222L), identificatieGroep.getInhoud().getBurgerservicenummer());

        Assert.assertNotNull(gezagStapel);
        Assert.assertEquals(1, gezagStapel.size());
        final MigratieGroep<BrpOuderlijkGezagInhoud> gezagGroep = gezagStapel.get(0);
        Assert.assertNotNull(gezagGroep);
        Assert.assertEquals(Boolean.FALSE, gezagGroep.getInhoud().getOuderHeeftGezag());

        Assert.assertNotNull(naamStapel);
        Assert.assertEquals(1, naamStapel.size());
        final MigratieGroep<BrpSamengesteldeNaamInhoud> naamGroep = naamStapel.get(0);
        Assert.assertNotNull(naamGroep);
        Assert.assertEquals("Gloria", naamGroep.getInhoud().getVoornamen());
    }
}
