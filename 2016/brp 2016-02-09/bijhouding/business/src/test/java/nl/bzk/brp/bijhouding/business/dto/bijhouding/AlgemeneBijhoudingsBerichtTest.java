/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.dto.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOverlijdenBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningNaGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link BijhoudingsBericht} class.
 */
public class AlgemeneBijhoudingsBerichtTest {

    @Test
    public void testGetterEnSetterVoorActies() {
        final BijhoudingsBericht bericht = new BijhoudingsBericht(null) {
        };

        Assert.assertNull(bericht.getAdministratieveHandeling());

        final List<ActieBericht> acties = new ArrayList<>();
        acties.add(new ActieRegistratieOverlijdenBericht());
        acties.add(new ActieRegistratieOverlijdenBericht());

        bericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        bericht.getAdministratieveHandeling().setActies(acties);

        Assert.assertNotNull(bericht.getAdministratieveHandeling().getActies());
        Assert.assertSame(acties, bericht.getAdministratieveHandeling().getActies());
    }

    @Test
    public void testGetterEnSetterVoorOverruledMeldingen() {
        final BijhoudingsBericht bericht = new BijhoudingsBericht(null) {
        };

        Assert.assertNull(bericht.getAdministratieveHandeling());

        //Na refactoring met code generatie is de lijst verplaats naar administratievehandeling en nu standaard null.
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());
        Assert.assertNull(bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen());

        final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> meldingen =
            Arrays.asList(new AdministratieveHandelingGedeblokkeerdeMeldingBericht(),
                new AdministratieveHandelingGedeblokkeerdeMeldingBericht());

        bericht.getAdministratieveHandeling().setGedeblokkeerdeMeldingen(meldingen);

        Assert.assertNotNull(bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen());
        Assert.assertSame(meldingen, bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen());
    }

    /**
     * Bouwt een lijst van acties op met voor elke bsn een actie met een persoon met opgegeven bsn.
     *
     * @param bsns de bsns van de personen van de acties.
     * @return een lijst van acties.
     */
    private List<ActieBericht> bouwActieLijstMetPersonen(final String... bsns) {
        final List<ActieBericht> acties = new ArrayList<>();
        for (final String bsn : bsns) {
            final ActieBericht actie = new ActieRegistratieOverlijdenBericht();
            final PersoonBericht persoon = new PersoonBericht();
            persoon.setObjectSleutel(bsn);
            actie.setRootObject(persoon);
            acties.add(actie);
        }
        return acties;
    }

    /**
     * Bouwt een lijst met een actie op met daarin een relatie met twee betrokken personen (BSNs).
     *
     * @param bsn1 bsn persoon 1
     * @param bsn2 bsn persoon 2
     * @return een lijst van acties.
     */
    private List<ActieBericht> bouwActieLijstMetRelaties(final String bsn1, final String bsn2) {
        final List<ActieBericht> acties = new ArrayList<>();
        final HuwelijkBericht huwelijkBericht = new HuwelijkBericht();
        final List<BetrokkenheidBericht> betrokkenheden = new ArrayList<>();
        final PartnerBericht partner1 = new PartnerBericht();
        final PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setObjectSleutel(bsn1);
        partner1.setPersoon(persoon1);
        betrokkenheden.add(partner1);
        final PartnerBericht partner2 = new PartnerBericht();
        if (bsn2 != null) {
            final PersoonBericht persoon2 = new PersoonBericht();
            persoon2.setObjectSleutel(bsn2);
            partner2.setPersoon(persoon2);
        }
        betrokkenheden.add(partner2);
        huwelijkBericht.setBetrokkenheden(betrokkenheden);
        final ActieBericht actie = new ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht();
        actie.setRootObject(huwelijkBericht);
        acties.add(actie);

        return acties;
    }

    /**
     * Maakt een standaard bericht voor een administratieve handeling.
     *
     * @param administratieveHandelingBericht administratieve handeling bericht
     * @param acties                          lijst met acties
     * @return bijhoudings bericht
     */
    private BijhoudingsBericht maakBericht(final AdministratieveHandelingBericht administratieveHandelingBericht,
        final List<ActieBericht> acties)
    {
        final BijhoudingsBericht bericht = new BijhoudingsBericht(null) {
        };
        bericht.getStandaard().setAdministratieveHandeling(administratieveHandelingBericht);
        bericht.getAdministratieveHandeling().setActies(acties);
        return bericht;
    }
}
