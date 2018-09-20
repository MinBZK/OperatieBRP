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
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
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

/** Unit test voor de {@link BRBY9901} bedrijfsregel. */
public class BRBY9901Test {


    private BRBY9901 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY9901();
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
    public void testBRY9901Normaal() {
        // niemand heeft een referentie, geen dubbele communicatieID's
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(0, meldingen.size());
    }


    @Test
    public void testBRY9901DrieDubbeleID() {
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);

        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        Iterator<OuderBericht> it = famBericht.getOuderBetrokkenheden().iterator();
        PersoonBericht ouder1 = it.next().getPersoon();
        PersoonBericht ouder2 = it.next().getPersoon();

        // ook met 3 objecten geeft het 1 fout.
        ouder1.setCommunicatieID(kind.getCommunicatieID());
        ouder2.setCommunicatieID(kind.getCommunicatieID());
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testBRY9901MeerdereDubbeleID() {
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);

        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        PersoonAdresBericht kindAdres = kind.getAdressen().get(0);
        Iterator<OuderBericht> it = famBericht.getOuderBetrokkenheden().iterator();
        PersoonBericht ouder1 = it.next().getPersoon();
        PersoonBericht ouder2 = it.next().getPersoon();

        //
        kindAdres.setCommunicatieID(ouder1.getCommunicatieID());
        ouder2.setCommunicatieID(kind.getCommunicatieID());
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(2, meldingen.size());
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
