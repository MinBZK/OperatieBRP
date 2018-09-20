/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.util.BerichtAdministratieUtil;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.BerichtBuilder;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor de {@link BRBY9905} bedrijfsregel. */
public class BRBY9905Test {


    private BRBY9905 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY9905();
    }

    @Test
    public void testGeenBericht() {
        Assert.assertTrue(bedrijfsregel.executeer(null).isEmpty());
    }
    @Test
    public void testGeenmap() {
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        ReflectionTestUtils.setField(simplegGeboorteBericht, "identificeerbaarObjectIndex", null);
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBRY9905Normaal() {
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBRY9905FoutSitiuatieNaarZichZelf() {
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);

        // Kind refereert naar zichtzelf (niet toegestaan)
        famBericht.getKindBetrokkenheid().getPersoon().setReferentieID(
                famBericht.getKindBetrokkenheid().getPersoon().getCommunicatieID());
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testBRY9905FoutSitiuatieNaarZichZelfMetDubbeleID() {
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);

        // Kind refereert naar zichtzelf (niet toegestaan), maar er zijn dubbele ID's --> geen conclussie.
        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        Iterator<OuderBericht> it = famBericht.getOuderBetrokkenheden().iterator();
        PersoonBericht ouder1 = it.next().getPersoon();
        PersoonBericht ouder2 = it.next().getPersoon();

        kind.setReferentieID(kind.getCommunicatieID());
        ouder2.setCommunicatieID(kind.getCommunicatieID());

        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        // kan geen conclussie trekken, er zijn 2 waar we naar toe referen.
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBRY9905FoutSitiuatieRefereertNaarEenReferentie() {
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);

        // een van de ouders refereert naar kind (is technisch toegestaan).
        // de 2e ouder refereert naar de eerste ouder (niet toegestaan - keten).
        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        Iterator<OuderBericht> it = famBericht.getOuderBetrokkenheden().iterator();
        PersoonBericht ouder1 = it.next().getPersoon();
        PersoonBericht ouder2 = it.next().getPersoon();

        ouder1.setReferentieID(kind.getCommunicatieID());
        ouder2.setReferentieID(ouder1.getCommunicatieID());

        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testBRY9905FoutSitiuatieRefereertNaarEenReferentieEnNaarZichzelf() {
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);

        // een van de ouders refereer naar kind (is technisch toegestaan).
        // de 2e ouder naar de eerste (keten).

        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        Iterator<OuderBericht> it = famBericht.getOuderBetrokkenheden().iterator();
        PersoonBericht ouder1 = it.next().getPersoon();
        PersoonBericht ouder2 = it.next().getPersoon();

        ouder1.setReferentieID(kind.getCommunicatieID());
        ouder2.setReferentieID(ouder1.getCommunicatieID());
        kind.setReferentieID(kind.getCommunicatieID());

        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(3, meldingen.size());
    }

    @Test
    public void testBRY9905FoutSitiuatieRefereertNaarDubbele() {
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);

        // als een object refereert naar een lijst (dus niet unieke CommID), dan kunnen we niets van zeggen, en
        // wordt dit goedgekeurd.
        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        Iterator<OuderBericht> it = famBericht.getOuderBetrokkenheden().iterator();
        PersoonBericht ouder1 = it.next().getPersoon();
        PersoonBericht ouder2 = it.next().getPersoon();

        ouder1.setReferentieID(kind.getCommunicatieID());
        // dubbere commID.
        ouder2.setCommunicatieID(kind.getCommunicatieID());

        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        // 0, kan geen conclussie trekken.
        Assert.assertEquals(0, meldingen.size());
    }

    private InschrijvingGeboorteBericht maakSimpleInschrijvingBericht2() {
        final InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        PersoonBericht kind = PersoonBuilder.bouwPersoon(
                123456789, Geslachtsaanduiding.MAN, 20100704,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Klaas", "van", "Veldhuijsen");
        kind.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    20100809)));
        PersoonBericht vader = PersoonBuilder.bouwPersoon(
                123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        vader.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    20100809)));
        PersoonBericht moeder = PersoonBuilder.bouwPersoon(
                123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Marie", "van", "Veldhuijsen-Teunis");
        moeder.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    20100809)));
        RelatieBuilder<FamilierechtelijkeBetrekkingBericht> builder = new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        RelatieBericht familieBericht = builder
            .bouwFamilieRechtelijkeBetrekkingRelatie()
            .voegKindToe(kind)
            .voegOuderToe(vader)
            .voegOuderToe(moeder)
            .getRelatie();

        PersoonBericht kindNat = PersoonBuilder.bouwPersoon(
                123456789, Geslachtsaanduiding.MAN, 20100704,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Klaas", "van", "Veldhuijsen");
        kindNat.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    20100809)));

        ActieRegistratieGeboorteBericht geboorteActie = BerichtBuilder.bouwActieRegistratieGeboorte(
                20100909, null,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                familieBericht);
        ActieRegistratieNationaliteitBericht nationActie = BerichtBuilder.bouwActieRegistratieNationaliteit(
                20100909, null,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                kindNat);

        bericht.setAdministratieveHandeling(
                BerichtBuilder.bouwHandelingGeboorte(
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    null, null, geboorteActie, nationActie
            ));
        bericht.setMeldingen(null);
        bericht.setStuurgegevens(BerichtBuilder.bouwStuurGegegevens(
                "APP", "ORG", "REFNR", "CROSREF", "FUNCTIE"));
        return bericht;
    }

}
