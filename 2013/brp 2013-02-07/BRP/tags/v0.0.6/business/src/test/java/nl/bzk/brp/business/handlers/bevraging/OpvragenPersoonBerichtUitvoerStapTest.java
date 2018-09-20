/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bevraging;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.binding.bevraging.OpvragenPersoonBericht;
import nl.bzk.brp.binding.bevraging.OpvragenPersoonCriteria;
import nl.bzk.brp.binding.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class OpvragenPersoonBerichtUitvoerStapTest {

    private OpvragenPersoonBerichtUitvoerStap opvragenPersoonBerichtUitvoerStap;

    @Mock
    private PersoonRepository                 persoonRepository;

    @Before
    public void init() {
        opvragenPersoonBerichtUitvoerStap = new OpvragenPersoonBerichtUitvoerStap();

        ReflectionTestUtils.setField(opvragenPersoonBerichtUitvoerStap, "persoonRepository", persoonRepository);
    }

    @Test
    public void testPersoonGevonden() {
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.anyString())).thenReturn(
                maakGevondenPersoon());

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakBericht(), null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Persoon gevondenPersoon = resultaat.getGevondenPersonen().iterator().next();
        PersoonAdres persoonAdres = gevondenPersoon.getAdressen().iterator().next();

        // Note: alleen velden die voorkomen in de XSD zijn hier getest
        Assert.assertEquals("1234", gevondenPersoon.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertEquals("1235", gevondenPersoon.getIdentificatienummers().getAdministratienummer());
        Assert.assertEquals(19800101, gevondenPersoon.getPersoonGeboorte().getDatumGeboorte().longValue());
        Assert.assertEquals(GeslachtsAanduiding.MAN, gevondenPersoon.getPersoonGeslachtsAanduiding()
                .getGeslachtsAanduiding());
        Assert.assertEquals("M", gevondenPersoon.getGeslachtsNaam());
        Assert.assertEquals("12", persoonAdres.getHuisnummer());
        Assert.assertEquals("Postcode", persoonAdres.getPostcode());
        Assert.assertEquals("voornaam", gevondenPersoon.getVoornamen());
    }

    @Test
    public void testGeenPersoonGevonden() {
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.anyString())).thenReturn(null);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakBericht(), null, resultaat);

        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.INFO, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    private OpvragenPersoonBericht maakBericht() {
        PersoonIdentificatienummers identificatienummers = new PersoonIdentificatienummers();
        identificatienummers.setBurgerservicenummer("1234");

        OpvragenPersoonCriteria criteria = new OpvragenPersoonCriteria();
        ReflectionTestUtils.setField(criteria, "identificatienummers", identificatienummers);

        OpvragenPersoonBericht bericht = new OpvragenPersoonBericht();
        ReflectionTestUtils.setField(bericht, "opvragenPersoonCriteria", criteria);

        return bericht;
    }

    private PersistentPersoon maakGevondenPersoon() {
        Set<PersistentPersoonAdres> adressen = new HashSet<PersistentPersoonAdres>();

        PersistentPersoonAdres persistentPersoonAdres = new PersistentPersoonAdres();
        persistentPersoonAdres.setHuisletter("A");
        persistentPersoonAdres.setHuisnummer("12");
        persistentPersoonAdres.setPostcode("Postcode");
        adressen.add(persistentPersoonAdres);

        PersistentPersoon persoon = new PersistentPersoon();
        persoon.setBurgerservicenummer("1234");
        persoon.setANummer("1235");
        persoon.setDatumGeboorte(19800101);
        persoon.setGeslachtsAanduiding(GeslachtsAanduiding.MAN);
        persoon.setGeslachtsNaam("M");
        persoon.setVoornaam("voornaam");

        persoon.setAdressen(adressen);

        return persoon;
    }

}
