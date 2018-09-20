/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerhuizingIntergemeentelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVoltrekkingHuwelijkInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import org.junit.Assert;
import org.junit.Test;

public class BRBY0152Test {

    private final BRBY0152 regel = new BRBY0152();

    @Test
    public void testGeboorteBerichtMetNevenActieDieVerwijstNaarHoofdActie() {
        final PersoonBericht kindUitHoofdActie = PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        kindUitHoofdActie.setCommunicatieID("111");

        final PersoonBericht kindUitNevenActie = PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        kindUitNevenActie.setReferentieID("111");

        final RegistreerGeboorteBericht registreerGeboorteBericht =
                maakRegistreerGeboorteBericht(kindUitHoofdActie, kindUitNevenActie);

        final List<BerichtIdentificeerbaar> berichtEntiteit = regel.voerRegelUit(registreerGeboorteBericht);

        Assert.assertEquals(0, berichtEntiteit.size());
    }

    @Test
    public void testRegistreerVerhuizingBerichtMetNevenActieDieVerwijstNaarHoofdActie() {
        final PersoonBericht verhuizendPersoonUitHoofdActie =
                PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        verhuizendPersoonUitHoofdActie.setCommunicatieID("111");

        final PersoonBericht persoonUitNevenActie = PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        persoonUitNevenActie.setReferentieID("111");

        final RegistreerVerhuizingBericht registreerVerhuizingBericht =
                maakRegistreerVerhuizingBericht(verhuizendPersoonUitHoofdActie, persoonUitNevenActie);

        final List<BerichtIdentificeerbaar> berichtEntiteit = regel.voerRegelUit(registreerVerhuizingBericht);

        Assert.assertEquals(0, berichtEntiteit.size());
    }

    @Test
    public void testGeboorteBerichteMetNevenActieDieNietVerwijstNaarHoofdActie() {
        final PersoonBericht kindUitHoofdActie = PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        kindUitHoofdActie.setCommunicatieID("111");

        final PersoonBericht kindUitNevenActie = PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        kindUitNevenActie.setReferentieID("222");

        final RegistreerGeboorteBericht registreerGeboorteBericht =
                maakRegistreerGeboorteBericht(kindUitHoofdActie, kindUitNevenActie);

        final List<BerichtIdentificeerbaar> berichtEntiteit = regel.voerRegelUit(registreerGeboorteBericht);

        Assert.assertEquals(1, berichtEntiteit.size());
        Assert.assertEquals(kindUitNevenActie, berichtEntiteit.get(0));
    }

    @Test
    public void testRegistreerVerhuizingBerichtMetNevenActieDieNietVerwijstNaarHoofdActie() {
        final PersoonBericht verhuizendPersoonUitHoofdActie =
                PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        verhuizendPersoonUitHoofdActie.setCommunicatieID("111");

        final PersoonBericht persoonUitNevenActie =
                PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        persoonUitNevenActie.setReferentieID("222");

        final RegistreerVerhuizingBericht registreerVerhuizingBericht =
                maakRegistreerVerhuizingBericht(verhuizendPersoonUitHoofdActie, persoonUitNevenActie);

        final List<BerichtIdentificeerbaar> berichtEntiteit = regel.voerRegelUit(registreerVerhuizingBericht);

        Assert.assertEquals(1, berichtEntiteit.size());
        Assert.assertEquals(persoonUitNevenActie, berichtEntiteit.get(0));
    }

    @Test
    public void testHuwelijkMetNevenActieDieVerwijstNaarPersonenInHoofdActie() {
        final PersoonBericht persoon1UitHoofdActie =
                PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        persoon1UitHoofdActie.setObjectSleutel("111");

        final PersoonBericht persoon2UitHoofdActie =
                PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        persoon2UitHoofdActie.setObjectSleutel("222");

        final PersoonBericht persoon1UitNevenActie =
                PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        persoon1UitNevenActie.setObjectSleutel("111");

        final PersoonBericht persoon2UitNevenActie =
                PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        persoon2UitNevenActie.setObjectSleutel("222");

        final RegistreerHuwelijkGeregistreerdPartnerschapBericht huwelijkBericht =
                maakHuwelijkBericht(persoon1UitHoofdActie, persoon2UitHoofdActie, persoon1UitNevenActie,
                                    persoon2UitNevenActie);

        final List<BerichtIdentificeerbaar> berichtEntiteit = regel.voerRegelUit(huwelijkBericht);

        Assert.assertEquals(0, berichtEntiteit.size());
    }

    @Test
    public void testHuwelijkMetNevenActieDieNietVerwijstNaarPersonenInHoofdActie() {
        final PersoonBericht persoon1UitHoofdActie =
                PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        persoon1UitHoofdActie.setObjectSleutel("111");

        final PersoonBericht persoon2UitHoofdActie =
                PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        persoon2UitHoofdActie.setObjectSleutel("222");

        final PersoonBericht persoon1UitNevenActie =
                PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        persoon1UitNevenActie.setObjectSleutel("333");

        final PersoonBericht persoon2UitNevenActie =
                PersoonBuilder.bouwRefererendPersoon(Integer.parseInt("057993014"));
        persoon2UitNevenActie.setObjectSleutel("444");

        final RegistreerHuwelijkGeregistreerdPartnerschapBericht huwelijkBericht =
                maakHuwelijkBericht(persoon1UitHoofdActie, persoon2UitHoofdActie, persoon1UitNevenActie,
                                    persoon2UitNevenActie);

        final List<BerichtIdentificeerbaar> berichtEntiteit = regel.voerRegelUit(huwelijkBericht);

        Assert.assertEquals(2, berichtEntiteit.size());
        Assert.assertEquals(persoon1UitNevenActie, berichtEntiteit.get(0));
        Assert.assertEquals(persoon2UitNevenActie, berichtEntiteit.get(1));
    }

    @Test
    public void testGetRegel() throws Exception {
        Assert.assertEquals(Regel.BRBY0152, regel.getRegel());
    }

    /**
     * Maakt een inschrijving geboorte bericht.
     *
     * @param kindUitHoofdActie kind uit hoofd actie
     * @param kindUitNevenActie kind uit neven actie
     * @return inschrijving geboorte bericht
     */
    private RegistreerGeboorteBericht maakRegistreerGeboorteBericht(final PersoonBericht kindUitHoofdActie,
                                                                        final PersoonBericht kindUitNevenActie)
    {
        final RelatieBericht familie = new RelatieBuilder()
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegKindToe(kindUitHoofdActie)
                .getRelatie();

        final ActieBericht actieRegistratieGeboorteBericht =
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_GEBOORTE)
                        .voegRootObjectToe(familie)
                        .getActie();

        final ActieBericht actieRegistratieNationaliteitBericht =
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_NATIONALITEIT)
                        .voegRootObjectToe(kindUitNevenActie)
                        .getActie();

        final HandelingGeboorteInNederlandBericht handeling = new HandelingGeboorteInNederlandBericht();
        handeling.setActies(new ArrayList<ActieBericht>());
        handeling.getActies().add(actieRegistratieGeboorteBericht);
        handeling.getActies().add(actieRegistratieNationaliteitBericht);

        final RegistreerGeboorteBericht registreerGeboorteBericht = new RegistreerGeboorteBericht();
        registreerGeboorteBericht.getStandaard().setAdministratieveHandeling(handeling);

        return registreerGeboorteBericht;
    }

    /**
     * Maakt een huwelijk bericht.
     *
     * @param persoon1UitHoofdActie persoon 1 uit hoofd actie
     * @param persoon2UitHoofdActie persoon 2 uit hoofd actie
     * @param persoon1UitNevenActie persoon 1 uit neven actie
     * @param persoon2UitNevenActie persoon 2 uit neven actie
     * @return huwelijk bericht
     */
    private RegistreerHuwelijkGeregistreerdPartnerschapBericht maakHuwelijkBericht(final PersoonBericht persoon1UitHoofdActie,
                                                final PersoonBericht persoon2UitHoofdActie,
                                                final PersoonBericht persoon1UitNevenActie,
                                                final PersoonBericht persoon2UitNevenActie)
    {
        final RelatieBericht familie = new RelatieBuilder()
                .bouwHuwelijkRelatie()
                .voegPartnerToe(persoon1UitHoofdActie)
                .voegPartnerToe(persoon2UitHoofdActie)
                .getRelatie();

        final ActieBericht actieRegistratieHuwelijkGeregistreerdPartnerschapBericht =
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP)
                        .voegRootObjectToe(familie)
                        .getActie();

        final ActieBericht actieRegistratieAanschrijvingBericht1 =
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_NAAMGEBRUIK)
                        .voegRootObjectToe(persoon1UitNevenActie)
                        .getActie();

        final ActieBericht actieRegistratieAanschrijvingBericht2 =
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_NAAMGEBRUIK)
                        .voegRootObjectToe(persoon2UitNevenActie)
                        .getActie();

        final HandelingVoltrekkingHuwelijkInNederlandBericht handeling =
                new HandelingVoltrekkingHuwelijkInNederlandBericht();
        handeling.setActies(new ArrayList<ActieBericht>());
        handeling.getActies().add(actieRegistratieHuwelijkGeregistreerdPartnerschapBericht);
        handeling.getActies().add(actieRegistratieAanschrijvingBericht1);
        handeling.getActies().add(actieRegistratieAanschrijvingBericht2);

        final RegistreerHuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new RegistreerHuwelijkGeregistreerdPartnerschapBericht();
        huwelijkBericht.getStandaard().setAdministratieveHandeling(handeling);

        return huwelijkBericht;
    }

    /**
     * Maakt een verhuizing bericht.
     *
     * @param persoon1UitHoofdActie persoon 1 uit hoofd actie
     * @param persoon1UitNevenActie persoon 1 uit neven actie
     * @return verhuizing bericht
     */
    private RegistreerVerhuizingBericht maakRegistreerVerhuizingBericht(final PersoonBericht persoon1UitHoofdActie,
                                                    final PersoonBericht persoon1UitNevenActie)
    {
        final ActieBericht actieRegistratieAdresBericht =
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_ADRES)
                        .voegRootObjectToe(persoon1UitHoofdActie)
                        .getActie();

        final HandelingVerhuizingIntergemeentelijkBericht handeling =
                new HandelingVerhuizingIntergemeentelijkBericht();

        handeling.setActies(new ArrayList<ActieBericht>());
        handeling.getActies().add(actieRegistratieAdresBericht);

        final ActieBericht actieRegistratieAanschrijvingBericht1 =
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_NAAMGEBRUIK)
                        .voegRootObjectToe(persoon1UitNevenActie)
                        .getActie();
        handeling.getActies().add(actieRegistratieAanschrijvingBericht1);

        // TBV coverage soort rootobject van nevenactie
        final RelatieBericht familie = new RelatieBuilder()
                .bouwHuwelijkRelatie()
                .getRelatie();

        final ActieBericht actieRegistratieAanschrijvingBericht2 =
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_NAAMGEBRUIK)
                        .voegRootObjectToe(familie)
                        .getActie();

        handeling.getActies().add(actieRegistratieAanschrijvingBericht2);

        final RegistreerVerhuizingBericht registreerVerhuizingBericht = new RegistreerVerhuizingBericht();
        registreerVerhuizingBericht.getStandaard().setAdministratieveHandeling(handeling);

        return registreerVerhuizingBericht;
    }
}
