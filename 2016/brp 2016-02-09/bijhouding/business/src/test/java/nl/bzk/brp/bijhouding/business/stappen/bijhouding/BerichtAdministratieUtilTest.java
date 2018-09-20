/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY9905;
import nl.bzk.brp.bijhouding.business.stappen.AbstractStapTest;
import nl.bzk.brp.bijhouding.business.util.BerichtAdministratieUtil;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.BerichtBuilder;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class BerichtAdministratieUtilTest extends AbstractStapTest {

    private static final String                  GEM_34      = "0034";
    private static final String                  LAND_2      = "0002";
    private static final String                  PLAATS_234  = "0234";
    private static final RedenVerkrijgingCodeAttribuut    VERKRIJG_10 = new RedenVerkrijgingCodeAttribuut((short) 10);
    private static final RedenVerliesCodeAttribuut        VERLIES_07  = new RedenVerliesCodeAttribuut((short) 7);
    private static final RedenWijzigingVerblijfCodeAttribuut WIJZADRES_P = new RedenWijzigingVerblijfCodeAttribuut("P");

    @Mock
    private ReferentieDataRepository             referentieDataRepository;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
//        ReflectionTestUtils.setField(berichtAdministratieStap, "referentieRepository", referentieDataRepository);
    }

    @Test
    public void testNormaalBericht() {
        BijhoudingResultaat res = new BijhoudingResultaat(new ArrayList<Melding>());

        BijhoudingsBericht simpleVerhuisBericht = maakSimpleVerhuisBericht2();
        Assert.assertNull(simpleVerhuisBericht.getCommunicatieIdMap());
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simpleVerhuisBericht);
        Assert.assertNotNull(simpleVerhuisBericht.getCommunicatieIdMap());


        BijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        Assert.assertNull(simplegGeboorteBericht.getCommunicatieIdMap());
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        Assert.assertNotNull(simplegGeboorteBericht.getCommunicatieIdMap());
    }

    @Test
    public void testBRY9905Normaal() {
        BRBY9905 brby = new BRBY9905();
        BijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        brby.voerRegelUit(simplegGeboorteBericht);
    }

    @Test
    public void testBRY9905NaarZichZelf() {
        BRBY9905 brby = new BRBY9905();
        BijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObject();
        // Kind refereert naar zichzelf.
        famBericht.getKindBetrokkenheid().getPersoon().setReferentieID(
                famBericht.getKindBetrokkenheid().getPersoon().getCommunicatieID());
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<BerichtIdentificeerbaar> meldingen = brby.voerRegelUit(simplegGeboorteBericht);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testBRY9905RefereertNaarEenReferentie() {
        BRBY9905 brby = new BRBY9905();
        BijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObject();
        // een van de ouders refereert naar kind (is technisch toegestaan).
        // refereer de 2e ouder naar de eerste.

        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        PersoonBericht ouder1 = famBericht.getOuderBetrokkenheden().iterator().next().getPersoon();
        PersoonBericht ouder2 = famBericht.getOuderBetrokkenheden().iterator().next().getPersoon();

        ouder1.setReferentieID(kind.getCommunicatieID());
        ouder2.setReferentieID(ouder1.getCommunicatieID());

        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<BerichtIdentificeerbaar> meldingen = brby.voerRegelUit(simplegGeboorteBericht);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testBRY9905RefereertNaarEenReferentieEnNaarZichzelf() {
        BRBY9905 brby = new BRBY9905();
        BijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObject();
        // een van de ouders refereer naar kind (is technisch toegestaan).
        // refereer de 2e ouder naar de eerste.

        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        PersoonBericht ouder1 = famBericht.getOuderBetrokkenheden().iterator().next().getPersoon();
        PersoonBericht ouder2 = famBericht.getOuderBetrokkenheden().iterator().next().getPersoon();

        ouder1.setReferentieID(kind.getCommunicatieID());
        ouder2.setReferentieID(ouder1.getCommunicatieID());
        kind.setReferentieID(kind.getCommunicatieID());

        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<BerichtIdentificeerbaar> meldingen = brby.voerRegelUit(simplegGeboorteBericht);
        // EEN omdat Alle fouten gecombineerd wordt tot 1 fout.
        Assert.assertEquals(2, meldingen.size());
    }

    private RegistreerVerhuizingBericht maakSimpleVerhuisBericht2() {
        RegistreerVerhuizingBericht bericht = new  RegistreerVerhuizingBericht();
        PersoonBericht verhuisdePersoon = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE,
                123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van", "Veldhuijsen");
        verhuisdePersoon.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA.getWaarde(),
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                    20100809)));

        ActieBericht verhuisActie = BerichtBuilder.bouwActieRegistratieAdres(
                20100909, null,
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                verhuisdePersoon);
        verhuisActie.setBronnen(Arrays.asList(
                BerichtBuilder.bouwActieBron("AA", GEM_34, null)));

        bericht.getStandaard().setAdministratieveHandeling(
            BerichtBuilder.bouwHandelingVerhuizing(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                null, verhuisActie
            ));
        bericht.setMeldingen(null);
        bericht.setStuurgegevens(BerichtBuilder.bouwStuurGegegevens(
                "APP", "ORG", "REFNR", "CROSREF"));
        return bericht;
    }

    private RegistreerGeboorteBericht maakSimpleInschrijvingBericht2() {
        final RegistreerGeboorteBericht bericht = new RegistreerGeboorteBericht();
        PersoonBericht kind = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE,
                123456789, Geslachtsaanduiding.MAN, 20100704,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Klaas", "van", "Veldhuijsen");
        kind.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA.getWaarde(),
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                    20100809)));
        PersoonBericht vader = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE,
                123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van", "Veldhuijsen");
        vader.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA.getWaarde(),
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                    20100809)));
        PersoonBericht moeder = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE,
                123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Marie", "van", "Veldhuijsen-Teunis");
        moeder.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA.getWaarde(),
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                    20100809)));
        RelatieBuilder builder = new RelatieBuilder<>();
        RelatieBericht familieBericht = builder
            .bouwFamilieRechtelijkeBetrekkingRelatie()
            .voegKindToe(kind)
            .voegOuderToe(vader)
            .voegOuderToe(moeder)
            .getRelatie();

        PersoonBericht kindNat = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE,
                123456789, Geslachtsaanduiding.MAN, 20100704,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Klaas", "van", "Veldhuijsen");
        kindNat.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA.getWaarde(),
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                    20100809)));

        ActieRegistratieGeboorteBericht geboorteActie = BerichtBuilder.bouwActieRegistratieGeboorte(
                20100909, null,
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                familieBericht);
        ActieRegistratieNationaliteitBericht nationActie = BerichtBuilder.bouwActieRegistratieNationaliteit(
                20100909, null,
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                kindNat);

        bericht.getStandaard().setAdministratieveHandeling(
                BerichtBuilder.bouwHandelingGeboorte(
                    StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                    null, geboorteActie, nationActie
            ));
        bericht.setMeldingen(null);
        bericht.setStuurgegevens(BerichtBuilder.bouwStuurGegegevens(
                "APP", "ORG", "REFNR", "CROSREF"));
        return bericht;
    }


    // TODO zet de unit test beter op .....


    private void bouwMaximaalOverlijdenBericht() {

    }
}
