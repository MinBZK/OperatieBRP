/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.business.actie.validatie.VerhuisActieValidator;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsGemeente;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor de VerhuizingUitvoerder. */
@RunWith(MockitoJUnitRunner.class)
public class VerhuizingUitvoerderTest extends AbstractStapTest {

    private VerhuizingUitvoerder   verhuizingUitvoerder;

    @Mock
    private PersoonAdresRepository persoonAdresRepository;
    @Mock
    private VerhuisActieValidator  verhuisActieValidator;
    @Mock
    private PersoonRepository      persoonRepository;

    @Before
    public void init() {
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.anyString())).thenReturn(
                new PersistentPersoon());

        verhuizingUitvoerder = new VerhuizingUitvoerder();
        ReflectionTestUtils.setField(verhuizingUitvoerder, "persoonAdresRepository", persoonAdresRepository);
        ReflectionTestUtils.setField(verhuizingUitvoerder, "verhuisActieValidator", verhuisActieValidator);
        ReflectionTestUtils.setField(verhuizingUitvoerder, "persoonRepository", persoonRepository);
    }

    @Test
    public void testVoerUitZonderFouten() {
        BRPActie actie = maakStandaardActie();
        BerichtContext bc = bouwBerichtContext();
        List<Melding> result = verhuizingUitvoerder.voerUit(actie, bc);

        Mockito.verify(persoonAdresRepository, Mockito.times(1)).opslaanNieuwPersoonAdres(
                Matchers.notNull(PersoonAdres.class), Matchers.eq(actie.getDatumAanvangGeldigheid()),
                (Integer) Matchers.eq(null), Matchers.any(Date.class));

        Mockito.verify(persoonRepository, Mockito.times(1)).werkbijBijhoudingsGemeente(Matchers.eq("abc"),
                Matchers.notNull(PersoonBijhoudingsGemeente.class), Matchers.eq(actie.getDatumAanvangGeldigheid()),
                Matchers.any(Date.class));
        // 1 hoofd persoon bijgehouden.
        Assert.assertEquals(1, bc.getHoofdPersonen().size());
        Assert.assertEquals(0, bc.getBijPersonen().size());
    }

    @Test
    public void testMetOnbekendeReferentieExceptieUitDAL() {
        BRPActie actie = maakStandaardActie();
        Mockito.doThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, "0013", null))
                .when(persoonAdresRepository)
                .opslaanNieuwPersoonAdres(Matchers.any(PersoonAdres.class), Matchers.any(Integer.class),
                        Matchers.any(Integer.class), Matchers.any(Date.class));

        List<Melding> result = verhuizingUitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, result.get(0).getSoort());
    }

    @Test
    public void testVerhuizendePersoonBSNNietBekendInDB() {
        BRPActie actie = maakStandaardActie();
        Mockito.when(persoonRepository.findByBurgerservicenummer("abc")).thenReturn(null);
        List<Melding> result = verhuizingUitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, result.get(0).getSoort());
    }

    /** Check BRPUC05108 */
    @Test
    public void testJuisteDatumAanvangGeldigheidMeegegevenAanDAL() {
        BRPActie actie = maakStandaardActie();
        actie.setDatumAanvangGeldigheid(20120505);
        List<Melding> result = verhuizingUitvoerder.voerUit(actie, bouwBerichtContext());
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(persoonAdresRepository).opslaanNieuwPersoonAdres(Matchers.any(PersoonAdres.class),
                argumentCaptor.capture(), Matchers.anyInt(), Matchers.any(Date.class));
        Assert.assertNull(result);
        Assert.assertEquals(actie.getDatumAanvangGeldigheid(), argumentCaptor.getValue());
    }

    /**
     * Instantieert een nieuwe {@link BRPActie}, met een {@link Persoon} als rootobject waarvan alleen een
     * {@link PersoonAdres} is gezet.
     *
     * @return een nieuwe BRPActie instantie.
     */
    private BRPActie maakStandaardActie() {
        PersoonIdentificatienummers pin = new PersoonIdentificatienummers();
        pin.setBurgerservicenummer("abc");

        PersoonAdres persoonAdres = new PersoonAdres();
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        adressen.add(persoonAdres);

        Persoon persoon = new Persoon();
        persoon.setAdressen(adressen);
        persoon.setIdentificatienummers(pin);

        BRPActie actie = new BRPActie();
        Integer datumAanvangGeldigheid = new Integer(1);
        actie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        return actie;
    }

}
