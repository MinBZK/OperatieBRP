/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningOngeborenVruchtBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Unit test voor de {@link BRBY04011} bedrijfsregel. */
public class BRBY04011Test {

    private BRBY04011 bedrijfsregel;
    private BRBY0152  bedrijfsregel2;
    private Persoon        man          = null;
    private Persoon        vrouw        = null;
    private Persoon        kind         = null;
    private Persoon        kindNat      = null;
    private Persoon        vreemde      = null;
    private Persoon        gast         = null;
    private PersoonBericht buitenlander = null;

    @Before
    public void init() {
        bedrijfsregel = new BRBY04011();
        bedrijfsregel2 = new BRBY0152();

        man = PersoonBuilder.bouwRefererendPersoon(123456789);
        vrouw = PersoonBuilder.bouwRefererendPersoon(987654321);
        vreemde = PersoonBuilder.bouwRefererendPersoon(567890123);
        kind = PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        kindNat = PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        ((PersoonBericht) kindNat).setReferentieID(((PersoonBericht) kind).getCommunicatieID());

        gast = null;
        buitenlander = PersoonBuilder.bouwRefererendPersoon(567890123);
        buitenlander.getIdentificatienummers().setBurgerservicenummer(null);
    }

    @Test
    public void testGeenBericht() {
        Assert.assertTrue(bedrijfsregel.executeer(null).isEmpty());
    }

    @Test
    public void testGeenActies() {
        Assert.assertTrue(bedrijfsregel.executeer(bouwHuwelijkBerichtMetActies(null)).isEmpty());
    }

    @Test
    public void testEnkeleActie() {
        HuwelijkBericht bericht = bouwHuwelijkBerichtMetActies(
            bouwHuwelijkEnLijstVanActies(man, vrouw, man));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testMetMeerdereActie() {
        HuwelijkBericht bericht = bouwHuwelijkBerichtMetActies(
            bouwHuwelijkEnLijstVanActies(man, vrouw, man, vrouw, vrouw));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testGeenActie() {
        HuwelijkBericht bericht = bouwHuwelijkBerichtMetActies(
            bouwHuwelijkEnLijstVanActies(man, vrouw));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testMetVreemde() {
        HuwelijkBericht bericht = bouwHuwelijkBerichtMetActies(
            bouwHuwelijkEnLijstVanActies(man, vrouw, vreemde));
        List<Melding> meldingen = bedrijfsregel.executeer(bericht);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY04011, meldingen.get(0).getCode());
    }

    @Test
    public void testMetVreemde2() {
        HuwelijkBericht bericht = bouwHuwelijkBerichtMetActies(
            bouwHuwelijkEnLijstVanActies(man, vrouw, vreemde, man));
        List<Melding> meldingen = bedrijfsregel.executeer(bericht);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY04011, meldingen.get(0).getCode());
    }

    @Test
    public void testMetVreemde3() {
        // 3x ongeldig nr => 3 fouten, want het zijn 3 acties
        HuwelijkBericht bericht = bouwHuwelijkBerichtMetActies(
            bouwHuwelijkEnLijstVanActies(man, vrouw, vreemde, vrouw, vreemde, vreemde));
        List<Melding> meldingen = bedrijfsregel.executeer(bericht);
        Assert.assertEquals(3, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY04011, meldingen.get(0).getCode());
        Assert.assertEquals(MeldingCode.BRBY04011, meldingen.get(1).getCode());
        Assert.assertEquals(MeldingCode.BRBY04011, meldingen.get(2).getCode());
    }

    @Test
    public void testMetGast() {
        // 3x ongeldig nr => 3 fouten, want het zijn 3 acties
        HuwelijkBericht bericht = bouwHuwelijkBerichtMetActies(
            bouwHuwelijkEnLijstVanActies(man, vrouw, gast));
        List<Melding> meldingen = bedrijfsregel.executeer(bericht);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testMetBuitenlander() {
        // 3x ongeldig nr => 3 fouten, want het zijn 3 acties
        HuwelijkBericht bericht = bouwHuwelijkBerichtMetActies(
            bouwHuwelijkEnLijstVanActies(man, vrouw, vrouw, buitenlander));
        List<Melding> meldingen = bedrijfsregel.executeer(bericht);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testGeboorte() {
        // 3x ongeldig nr => 3 fouten, want het zijn 3 acties
        InschrijvingGeboorteBericht bericht = bouwFamilieBerichtMetActies(
            bouwFamilieRelatieEnLijstVanActies(man, vrouw, kind, kindNat));
        List<Melding> meldingen = bedrijfsregel2.executeer(bericht);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeboorteMetFout() {
        // 3x ongeldig nr => 3 fouten, want het zijn 3 acties
        InschrijvingGeboorteBericht bericht = bouwFamilieBerichtMetActies(
            bouwFamilieRelatieEnLijstVanActies(man, vrouw, kind, vreemde));
        List<Melding> meldingen = bedrijfsregel2.executeer(bericht);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testGeboorteMetFout2() {
        // 3x ongeldig nr => 3 fouten, want het zijn 3 acties
        InschrijvingGeboorteBericht bericht = bouwFamilieBerichtMetActies(
            bouwFamilieRelatieEnLijstVanActies(man, vrouw, kind, man));
        List<Melding> meldingen = bedrijfsregel2.executeer(bericht);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testGeboorteMetFout3() {
        // 3x ongeldig nr => 3 fouten, want het zijn 3 acties
        InschrijvingGeboorteBericht bericht = bouwFamilieBerichtMetActies(
            bouwFamilieRelatieEnLijstVanActies(man, vrouw, kind, vrouw));
        List<Melding> meldingen = bedrijfsregel2.executeer(bericht);
        Assert.assertEquals(1, meldingen.size());
    }

    /**
     * Bouwt een lijst van acties op waarvan de eerste een huwelijk is en de daaropvolgende 0,..n naam wijzig gebruik.
     * De rootobject van het huwelijk actie is een relatie met daarin de man/vrow als personen.
     * De rootobjecten van de sub acties worden gezet op de persoon. Per persoon wordt er dus
     * een actie aan de lijst toegevoegd met de persoon als (enige) rootobject in de actie.
     *
     * @param partner1 de ene partner in het huwelijk.
     * @param partner2 de ander partner in het huwelijk.
     * @param personen lijst van personen.
     * @return een lijst van acties.
     */
    private List<ActieBericht> bouwHuwelijkEnLijstVanActies(final Persoon partner1, final Persoon partner2,
        final Persoon... personen)
    {
        List<ActieBericht> acties = new ArrayList<ActieBericht>();
        acties.add(bouwActieHuwelijk(partner1, partner2));
        for (Persoon persoon : personen) {
            acties.add(bouwActieWijzigNaamGebruik(persoon));
        }
        return acties;
    }

    private ActieBericht bouwActieHuwelijk(final Persoon partner1, final Persoon partner2) {
        RelatieBericht huwelijk = new RelatieBuilder()
            .bouwHuwlijkRelatie()
            .voegPartnerToe((PersoonBericht) partner1)
            .voegPartnerToe((PersoonBericht) partner2)
            .getRelatie();

        return ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_HUWELIJK)
                                  .voegRootObjectToe(huwelijk)
                                  .getActie();
    }

    private List<ActieBericht> bouwFamilieRelatieEnLijstVanActies(final Persoon ouder1, final Persoon ouder2,
        final Persoon kind1, final Persoon... personen)
    {
        List<ActieBericht> acties = new ArrayList<ActieBericht>();
        acties.add(bouwActieFamilie(ouder1, ouder2, kind1));
        for (Persoon persoon : personen) {
            acties.add(bouwActieRegistratieNationaliteit(persoon));
        }
        return acties;
    }

    private ActieBericht bouwActieFamilie(final Persoon ouder1, final Persoon ouder2, final Persoon kind1) {
        RelatieBericht huwelijk = new RelatieBuilder()
            .bouwFamilieRechtelijkeBetrekkingRelatie()
            .voegOuderToe((PersoonBericht) ouder1)
            .voegOuderToe((PersoonBericht) ouder2)
            .voegKindToe((PersoonBericht) kind1)
            .getRelatie();

        return ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_HUWELIJK)
                                  .voegRootObjectToe(huwelijk)
                                  .getActie();
    }

    private ActieBericht bouwActieWijzigNaamGebruik(final Persoon persoon) {
        return ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_AANSCHRIJVING)
                                  .voegRootObjectToe(persoon)
                                  .getActie();
    }

    private ActieBericht bouwActieRegistratieNationaliteit(final Persoon persoon) {
        return ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_NATIONALITEIT)
                                  .voegRootObjectToe(persoon)
                                  .getActie();
    }

    /**
     * Instantieert en retourneert een (correctie adres) bericht met de opgegeven acties.
     *
     * @param acties de acties voor het bericht.
     * @return een nieuw (correctie adres) bericht met opgegeven acties.
     */
    private HuwelijkBericht bouwHuwelijkBerichtMetActies(final List<ActieBericht> acties) {
        HuwelijkBericht bericht = new HuwelijkBericht();
        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        bericht.getAdministratieveHandeling().setActies(acties);
        return bericht;
    }

    /**
     * Instantieert en retourneert een (correctie adres) bericht met de opgegeven acties.
     *
     * @param acties de acties voor het bericht.
     * @return een nieuw (correctie adres) bericht met opgegeven acties.
     */
    private InschrijvingGeboorteBericht bouwFamilieBerichtMetActies(final List<ActieBericht> acties) {
        InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        bericht.getAdministratieveHandeling().setActies(acties);
        return bericht;
    }

}
