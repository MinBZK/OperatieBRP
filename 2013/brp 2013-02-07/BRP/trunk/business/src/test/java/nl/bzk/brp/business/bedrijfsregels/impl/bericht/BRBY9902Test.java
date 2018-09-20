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

/** Unit test voor de {@link BRBY9902} bedrijfsregel. */
public class BRBY9902Test {


    private BRBY9902 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY9902();
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
    public void testBRY9902Normaal() {
        // niemand heeft een referentie, geen dubbele communicatieID's
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(0, meldingen.size());
    }


    @Test
    public void testBRY9902NietBestaand2x() {
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);

        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        Iterator<OuderBericht> it = famBericht.getOuderBetrokkenheden().iterator();
        PersoonBericht ouder1 = it.next().getPersoon();
        PersoonBericht ouder2 = it.next().getPersoon();

        ouder1.setReferentieID("id.random.nr");
        ouder2.setReferentieID("id.random.nr2");
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(2, meldingen.size());
        List<String> ids = Arrays.asList(ouder1.getCommunicatieID(), ouder2.getCommunicatieID());

        // voorlopig testen we op communicatieID, het kan zijn dat op een bepaald ogenblik
        // de communicatieID van het ident opgeslagen wordt in de referentieiD van in de meldingen.
        // dan moet deze test angepast worden.
        Assert.assertEquals(true, ids.contains(meldingen.get(0).getCommunicatieID()));
        Assert.assertEquals(true, ids.contains(meldingen.get(1).getCommunicatieID()));
    }

    @Test
    public void testBRY9902NietBestaandDezelfde() {
        // dit is om te testen of er inderdaad ook 2 foutmelingen komen als ze naar dezlfde 'onbekende' id refereert.
        // kan ook verwijderd worden.
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);

        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        Iterator<OuderBericht> it = famBericht.getOuderBetrokkenheden().iterator();
        PersoonBericht ouder1 = it.next().getPersoon();
        PersoonBericht ouder2 = it.next().getPersoon();

        ouder1.setReferentieID("id.random.nr");
        ouder2.setReferentieID("id.random.nr");
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(2, meldingen.size());
        List<String> ids = Arrays.asList(ouder1.getCommunicatieID(), ouder2.getCommunicatieID());

        // voorlopig testen we op communicatieID, het kan zijn dat op een bepaald ogenblik
        // de communicatieID van het ident opgeslagen wordt in de referentieiD van in de meldingen.
        // dan moet deze test angepast worden.
        Assert.assertEquals(true, ids.contains(meldingen.get(0).getCommunicatieID()));
        Assert.assertEquals(true, ids.contains(meldingen.get(1).getCommunicatieID()));
    }

    @Test
    public void testBRY990ReferentieNietbestaandMetZelfdeCommID() {
        // dit is om te testen of er inderdaad ook 2 foutmelingen komen als ze naar dezlfde 'onbekende' id refereert.
        // kan ook verwijderd worden.
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);

        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        Iterator<OuderBericht> it = famBericht.getOuderBetrokkenheden().iterator();
        PersoonBericht ouder1 = it.next().getPersoon();
        PersoonBericht ouder2 = it.next().getPersoon();

        ouder1.setReferentieID("id.random.nr");
        ouder2.setReferentieID("id.random.nr2");
        ouder1.setCommunicatieID("id.ouder.beide");
        ouder2.setCommunicatieID("id.ouder.beide");
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = bedrijfsregel.executeer(simplegGeboorteBericht);
        Assert.assertEquals(2, meldingen.size());

        // verzeker dat we 2 verschillende objecten hebben (ouders), maar hebbendelfde commID
        Assert.assertEquals(true, (meldingen.get(0).getCommunicatieID()).equals(meldingen.get(1).getCommunicatieID()));
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
