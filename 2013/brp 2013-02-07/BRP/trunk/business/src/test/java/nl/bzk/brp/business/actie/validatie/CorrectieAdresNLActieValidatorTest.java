/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;

public class CorrectieAdresNLActieValidatorTest {

    @Test
    public void testAlleVeldenLeeg() throws IllegalAccessException {
        ActieBericht actie = new ActieCorrectieAdresBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setCommunicatieID("AAA");
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new CorrectieAdresNLActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        boolean meldingenGevonden = false;
        for (Melding melding : meldingen) {
            if (melding.getCommunicatieID() != null) {
                if (melding.getCommunicatieID().equals(persoon.getCommunicatieID())) {
                    meldingenGevonden = true;
                }
            }
        }
        Assert.assertTrue("Verwachte meldingen niet gevonden!", meldingenGevonden);
    }

    @Test
    public void testGroepIdentificatieNummersLeeg() {
        ActieBericht actie = new ActieCorrectieAdresBericht();
        PersoonBericht persoon = new PersoonBericht();
        PersoonIdentificatienummersGroepBericht idnrs = new PersoonIdentificatienummersGroepBericht();
        idnrs.setCommunicatieID("XXX");
        persoon.setIdentificatienummers(idnrs);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new CorrectieAdresNLActieValidator().valideerActie(actie);
        int aantalmeldingenOverVerzendendId = 0;
        for (Melding melding : meldingen) {
            if (melding.getCommunicatieID() != null) {
                if (melding.getCommunicatieID().equals(idnrs.getCommunicatieID())) {
                    aantalmeldingenOverVerzendendId++;
                }
            }
        }
        //Alleen BSN is verplicht
        Assert.assertTrue("Verwachte meldingen niet gevonden!", aantalmeldingenOverVerzendendId == 1);
    }

    @Test
    public void testAdresHelemaalLeeg() {
        ActieBericht actie = new ActieCorrectieAdresBericht();
        PersoonBericht persoon = new PersoonBericht();
        PersoonIdentificatienummersGroepBericht idnrs = new PersoonIdentificatienummersGroepBericht();
        idnrs.setCommunicatieID("XXX");
        idnrs.setBurgerservicenummer(new Burgerservicenummer("123456789"));
        persoon.setIdentificatienummers(idnrs);

        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(gegevens);
        gegevens.setCommunicatieID("BBB");
        persoon.getAdressen().add(adres);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        List<Melding> meldingen = new CorrectieAdresNLActieValidator().valideerActie(actie);
        int aantalmeldingenOverVerzendendId = 0;
        for (Melding melding : meldingen) {
            if (melding.getCommunicatieID() != null) {
                if (melding.getCommunicatieID().equals(gegevens.getCommunicatieID())) {
                    aantalmeldingenOverVerzendendId++;
                }
            }
        }
        Assert.assertTrue("Verwachte meldingen niet gevonden!", aantalmeldingenOverVerzendendId == 8);
    }
}
