/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.business.dto.bijhouding.CorrectieAdresBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAdresNederlandBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Before;
import org.junit.Test;

/** Unit test voor de {@link BRBY0532} bedrijfsregel. */
public class BRBY0532Test {

    private BRBY0532 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0532();
    }

    @Test
    public void testGeenBericht() {
        Assert.assertTrue(bedrijfsregel.executeer(null).isEmpty());
    }

    @Test
    public void testGeenActies() {
        Assert.assertTrue(bedrijfsregel.executeer(bouwBerichtMetActies(null)).isEmpty());
    }

    @Test
    public void testEnkeleActie() {
        CorrectieAdresBericht bericht = bouwBerichtMetActies(bouwLijstVanActies(
            PersoonBuilder.bouwRefererendPersoon(123456789)));

        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testTweeActiesZelfdePersoon() {
        CorrectieAdresBericht bericht = bouwBerichtMetActies(bouwLijstVanActies(
            PersoonBuilder.bouwRefererendPersoon(123456789), PersoonBuilder.bouwRefererendPersoon(123456789)));

        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testTweeActiesAnderPersoon() {
        CorrectieAdresBericht bericht = bouwBerichtMetActies(bouwLijstVanActies(
            PersoonBuilder.bouwRefererendPersoon(123456789), PersoonBuilder.bouwRefererendPersoon(987654321)));

        List<Melding> meldingen = bedrijfsregel.executeer(bericht);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0532, meldingen.get(0).getCode());
    }

    @Test
    public void testDrieActiesZelfdePersoon() {
        CorrectieAdresBericht bericht = bouwBerichtMetActies(bouwLijstVanActies(
            PersoonBuilder.bouwRefererendPersoon(123456789), PersoonBuilder.bouwRefererendPersoon(123456789),
            PersoonBuilder.bouwRefererendPersoon(123456789)));

        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testDrieActiesEnkelAnderPersoon() {
        CorrectieAdresBericht bericht;
        List<Melding> meldingen;

        // Derde actie heeft ander persoon
        bericht = bouwBerichtMetActies(bouwLijstVanActies(
            PersoonBuilder.bouwRefererendPersoon(123456789), PersoonBuilder.bouwRefererendPersoon(123456789),
            PersoonBuilder.bouwRefererendPersoon(987654321)));
        meldingen = bedrijfsregel.executeer(bericht);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0532, meldingen.get(0).getCode());

        // Tweede actie heeft ander persoon
        bericht = bouwBerichtMetActies(bouwLijstVanActies(
            PersoonBuilder.bouwRefererendPersoon(123456789), PersoonBuilder.bouwRefererendPersoon(987654321),
            PersoonBuilder.bouwRefererendPersoon(123456789)));
        meldingen = bedrijfsregel.executeer(bericht);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0532, meldingen.get(0).getCode());

        // Eerste actie heeft ander persoon
        bericht = bouwBerichtMetActies(bouwLijstVanActies(
            PersoonBuilder.bouwRefererendPersoon(987654321), PersoonBuilder.bouwRefererendPersoon(123456789),
            PersoonBuilder.bouwRefererendPersoon(123456789)));
        meldingen = bedrijfsregel.executeer(bericht);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0532, meldingen.get(0).getCode());
    }

    @Test
    public void testDrieActiesAllenAnderPersoon() {
        CorrectieAdresBericht bericht = bouwBerichtMetActies(bouwLijstVanActies(
            PersoonBuilder.bouwRefererendPersoon(123456789), PersoonBuilder.bouwRefererendPersoon(567890123),
            PersoonBuilder.bouwRefererendPersoon(987654321)));
        List<Melding> meldingen = bedrijfsregel.executeer(bericht);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0532, meldingen.get(0).getCode());
    }

    /**
     * Bouwt een lijst van acties op waarvan de rootobjecten worden gezet op de persoon. Per persoon wordt er dus
     * een actie aan de lijst toegevoegd met de persoon als (enige) rootobject in de actie.
     *
     * @param personen lijst van personen.
     * @return een lijst van acties.
     */
    private List<ActieBericht> bouwLijstVanActies(final Persoon... personen) {
        List<ActieBericht> acties = new ArrayList<ActieBericht>();

        for (Persoon persoon : personen) {
            acties.add(ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).voegRootObjectToe(persoon)
                                          .getActie());
        }

        return acties;
    }

    /**
     * Instantieert en retourneert een (correctie adres) bericht met de opgegeven acties.
     *
     * @param acties de acties voor het bericht.
     * @return een nieuw (correctie adres) bericht met opgegeven acties.
     */
    private CorrectieAdresBericht bouwBerichtMetActies(final List<ActieBericht> acties) {
        CorrectieAdresBericht bericht = new CorrectieAdresBericht();
        bericht.setAdministratieveHandeling(new HandelingCorrectieAdresNederlandBericht());
        bericht.getAdministratieveHandeling().setActies(acties);
        return bericht;
    }
}
