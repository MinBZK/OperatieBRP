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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
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

public class HuwelijkConverteerderTest extends AbstractComponentTest {

    @Inject
    private HuwelijkConverteerder huwelijkConverteerder;

    @Test
    public void test() {
        final Lo3HuwelijkOfGpInhoud inhoud = new Lo3HuwelijkOfGpInhoud(1234567890L,// aNummer,
                123456789L,// burgerservicenummer,
                "Bella",// voornamen,
                null,// adellijkeTitelPredikaatCode,
                null,// voorvoegselGeslachtsnaam,
                "Pietersen",// geslachtsnaam,
                new Lo3Datum(19730101),// geboortedatum,
                new Lo3GemeenteCode("0518"),// geboorteGemeenteCode,
                new Lo3LandCode("6030"),// geboorteLandCode,
                Lo3GeslachtsaanduidingEnum.VROUW.asElement(),// geslachtsaanduiding,
                new Lo3Datum(20090101),// datumSluitingHuwelijkOfAangaanGp,
                new Lo3GemeenteCode("0518"),// gemeenteCodeSluitingHuwelijkOfAangaanGp,
                new Lo3LandCode("6030"),// landCodeSluitingHuwelijkOfAangaanGp,
                null,// datumOntbindingHuwelijkOfGp,
                null,// gemeenteCodeOntbindingHuwelijkOfGp,
                null,// landCodeOntbindingHuwelijkOfGp,
                null,// redenOntbindingHuwelijkOfGpCode,
                Lo3SoortVerbintenisEnum.HUWELIJK.asElement()// soortVerbintenis
                );
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(913, new Lo3GemeenteCode("0518"), "A1", null, null, null, null, null);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20090101), new Lo3Datum(20090102));
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
                new Lo3Categorie<Lo3HuwelijkOfGpInhoud>(inhoud, documentatie, historie, null);
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijken =
                new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();
        huwelijken.add(huwelijk);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkStapel = new Lo3Stapel<Lo3HuwelijkOfGpInhoud>(huwelijken);
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                new ArrayList<Lo3Stapel<Lo3HuwelijkOfGpInhoud>>();
        huwelijkStapels.add(huwelijkStapel);

        final MigratiePersoonslijstBuilder builder = new MigratiePersoonslijstBuilder();
        huwelijkConverteerder.converteer(huwelijkStapels, builder);

        @SuppressWarnings("unchecked")
        final List<MigratieRelatie> relatieStapels =
                (List<MigratieRelatie>) ReflectionUtil.getField(builder, "relaties");
        checkRelatieStapels(relatieStapels);
    }

    private void checkRelatieStapels(final List<MigratieRelatie> relaties) {
        Assert.assertEquals(1, relaties.size());
        final MigratieRelatie relatie = relaties.get(0);

        Assert.assertNotNull(relatie);
        Assert.assertEquals(BrpSoortRelatieCode.HUWELIJK, relatie.getSoortRelatieCode());

        final MigratieStapel<BrpRelatieInhoud> relatieStapel = relatie.getRelatieStapel();
        Assert.assertNotNull(relatieStapel);
        Assert.assertEquals(1, relatieStapel.size());
        final MigratieGroep<BrpRelatieInhoud> relatieGroep = relatieStapel.get(0);
        Assert.assertNotNull(relatieGroep);
        Assert.assertEquals(new BrpDatum(20090101), relatieGroep.getInhoud().getDatumAanvang());

        Assert.assertEquals(BrpSoortBetrokkenheidCode.PARTNER, relatie.getRolCode());

        final List<MigratieBetrokkenheid> betrokkenheden = relatie.getBetrokkenheden();
        Assert.assertNotNull(betrokkenheden);
        Assert.assertEquals(1, betrokkenheden.size());

        final MigratieBetrokkenheid betrokkenheid = betrokkenheden.get(0);
        Assert.assertNotNull(betrokkenheid);
        Assert.assertEquals(BrpSoortBetrokkenheidCode.PARTNER, betrokkenheid.getRol());

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
        Assert.assertEquals(new BrpDatum(19730101), geboorteGroep.getInhoud().getGeboortedatum());

        Assert.assertNotNull(geslachtStapel);
        Assert.assertEquals(1, geslachtStapel.size());
        final MigratieGroep<BrpGeslachtsaanduidingInhoud> geslachtGroep = geslachtStapel.get(0);
        Assert.assertNotNull(geslachtGroep);
        Assert.assertEquals(BrpGeslachtsaanduidingCode.VROUW, geslachtGroep.getInhoud().getGeslachtsaanduiding());

        Assert.assertNotNull(identificatieStapel);
        Assert.assertEquals(1, identificatieStapel.size());
        final MigratieGroep<BrpIdentificatienummersInhoud> identificatieGroep = identificatieStapel.get(0);
        Assert.assertNotNull(identificatieGroep);
        Assert.assertEquals(new Long(1234567890L), identificatieGroep.getInhoud().getAdministratienummer());
        Assert.assertEquals(new Long(123456789L), identificatieGroep.getInhoud().getBurgerservicenummer());

        Assert.assertNull(gezagStapel);

        Assert.assertNotNull(naamStapel);
        Assert.assertEquals(1, naamStapel.size());
        final MigratieGroep<BrpSamengesteldeNaamInhoud> naamGroep = naamStapel.get(0);
        Assert.assertNotNull(naamGroep);
        Assert.assertEquals("Bella", naamGroep.getInhoud().getVoornamen());

    }
}
