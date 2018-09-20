/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor de {@link InschrijvingGeboorteUitvoerder} class. */
public class InschrijvingGeboorteUitvoerderTest {

    private InschrijvingGeboorteUitvoerder uitvoerder;
    @Mock
    private PersoonRepository              persoonRepository;

    @Test
    public void testStandaardUitvoer() {
        List<Melding> meldingen = uitvoerder.voerUit(getInschrijvingGeboorteActie());
        Assert.assertNull(meldingen);
        Mockito.verify(persoonRepository).opslaanNieuwPersoon(Mockito.any(Persoon.class),
            Mockito.eq(Integer.valueOf(20120325)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUitvoerZonderActie() {
        uitvoerder.voerUit(null);
    }

    @Test
    public void testUitvoerMetActieMetNullVoorRootObjecten() {
        BRPActie actie = new BRPActie();
        actie.setRootObjecten(null);

        List<Melding> meldingen = uitvoerder.voerUit(actie);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieZonderRootObjecten() {
        BRPActie actie = new BRPActie();
        actie.setRootObjecten(new ArrayList<RootObject>());

        List<Melding> meldingen = uitvoerder.voerUit(actie);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieMetAndereRootObjecten() {
        BRPActie actie = new BRPActie();
        actie.voegRootObjectToe(Mockito.mock(RootObject.class));
        actie.voegRootObjectToe(Mockito.mock(RootObject.class));

        List<Melding> meldingen = uitvoerder.voerUit(actie);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieMetPersoonEnAndereRootObjecten() {
        BRPActie actie = getInschrijvingGeboorteActie();
        actie.getRootObjecten().add(0, Mockito.mock(RootObject.class));
        actie.getRootObjecten().add(Mockito.mock(RootObject.class));

        Assert.assertNull(uitvoerder.voerUit(actie));
    }

    @Test
    public void testUitvoerMetBestaandBsn() {
        BRPActie actie = getInschrijvingGeboorteActie();
        Mockito.doThrow(new ObjectReedsBestaandExceptie(ObjectReedsBestaandExceptie.ReferentieVeld.BSN, null, null))
               .when(persoonRepository).opslaanNieuwPersoon(Mockito.any(Persoon.class), Mockito.anyInt());

        List<Melding> meldingen = uitvoerder.voerUit(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.REF0010, meldingen.get(0).getCode());
    }

    /**
     * Retourneert een standaard 'aangifte geboorte' actie met een leeg persoon als root object.
     *
     * @return een standaard 'aangifte geboorte' actie met een leeg persoon als root object.
     */
    private BRPActie getInschrijvingGeboorteActie() {
        BRPActie actie = new BRPActie();
        actie.setSoort(SoortActie.AANGIFTE_GEBOORTE);
        actie.setDatumAanvangGeldigheid(Integer.valueOf(20120325));

        Persoon persoon = new Persoon();
        actie.voegRootObjectToe(persoon);
        return actie;
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        uitvoerder = new InschrijvingGeboorteUitvoerder();
        ReflectionTestUtils.setField(uitvoerder, "persoonRepository", persoonRepository);
    }
}
