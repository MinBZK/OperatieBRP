/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import nl.bzk.brp.business.actie.validatie.GeboorteActieValidator;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor de {@link InschrijvingGeboorteUitvoerder} class. */
public class InschrijvingGeboorteUitvoerderTest extends AbstractStapTest {

    private InschrijvingGeboorteUitvoerder uitvoerder;
    @Mock
    private PersoonRepository              persoonRepository;
    @Mock
    private RelatieRepository              relatieRepository;
    @Mock
    private GeboorteActieValidator         geboorteActieValidator;

    @Test(expected = IllegalArgumentException.class)
    public void testUitvoerZonderActie() {
        uitvoerder.voerUit(null, bouwBerichtContext());
    }

    @Test
    public void testUitvoerMetActieMetNullVoorRootObjecten() {
        BRPActie actie = new BRPActie();
        actie.setRootObjecten(null);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieZonderRootObjecten() {
        BRPActie actie = new BRPActie();
        actie.setRootObjecten(new ArrayList<RootObject>());

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testNormaleActie() {
        List<Melding> meldingen = uitvoerder.voerUit(getInschrijvingGeboorteActie(), bouwBerichtContext());
        Assert.assertNull(meldingen);
        Mockito.verify(persoonRepository).opslaanNieuwPersoon(Matchers.any(Persoon.class),
            Matchers.eq(Integer.valueOf(20120325)), Matchers.any(Date.class));
        Mockito.verify(relatieRepository).opslaanNieuweRelatie(Matchers.any(Relatie.class),
            Matchers.eq(Integer.valueOf(20120325)), Matchers.any(Date.class));
    }

    @Test
    public void testActieZonderBetrokkenheden() {
        BRPActie actie = getInschrijvingGeboorteActie();
        ((Relatie) actie.getRootObjecten().get(0)).setBetrokkenheden(null);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testActieMetOnbekendeOuder() {
        BRPActie actie = getInschrijvingGeboorteActie();
        ((Relatie) actie.getRootObjecten().get(0)).getBetrokkenheden().add(bouwOuderBetrokkenheid(3L, "456789123"));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testActieMetOuderZonderBsn() {
        BRPActie actie = getInschrijvingGeboorteActie();
        ((Relatie) actie.getRootObjecten().get(0)).getBetrokkenheden().add(bouwOuderBetrokkenheid(4L, null));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    @Ignore
    public void testUitvoerMetActieMetAndereRootObjecten() {
        BRPActie actie = new BRPActie();
        actie.voegRootObjectToe(Mockito.mock(RootObject.class));
        actie.voegRootObjectToe(Mockito.mock(RootObject.class));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    @Ignore
    public void testUitvoerMetActieMetPersoonEnAndereRootObjecten() {
        BRPActie actie = getInschrijvingGeboorteActie();
        actie.getRootObjecten().add(0, Mockito.mock(RootObject.class));
        actie.getRootObjecten().add(Mockito.mock(RootObject.class));

        Assert.assertNull(uitvoerder.voerUit(actie, bouwBerichtContext()));
    }

    @Test
    @Ignore
    public void testUitvoerMetBestaandBsn() {
        BRPActie actie = getInschrijvingGeboorteActie();
        Mockito.doThrow(new ObjectReedsBestaandExceptie(ObjectReedsBestaandExceptie.ReferentieVeld.BSN, null, null))
               .when(persoonRepository)
               .opslaanNieuwPersoon(Matchers.any(Persoon.class), Matchers.anyInt(), Matchers.any(Date.class));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBER00121, meldingen.get(0).getCode());
    }

    @Test
    @Ignore
    public void testUitvoerMetOnbekendeReferentie() {
        BRPActie actie = getInschrijvingGeboorteActie();
        Mockito
            .doThrow(new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDCODE, "abc", null))
            .when(persoonRepository)
            .opslaanNieuwPersoon(Matchers.any(Persoon.class), Matchers.anyInt(), Matchers.any(Date.class));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.REF0001, meldingen.get(0).getCode());
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

        Relatie relatie = new Relatie();
        relatie.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        actie.voegRootObjectToe(relatie);

        relatie.setBetrokkenheden(new HashSet<Betrokkenheid>());
        relatie.getBetrokkenheden().add(bouwKindBetrokkenheid("123456789"));
        relatie.getBetrokkenheden().add(bouwOuderBetrokkenheid(1L, "234567891"));
        relatie.getBetrokkenheden().add(bouwOuderBetrokkenheid(2L, "345678912"));
        return actie;
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        uitvoerder = new InschrijvingGeboorteUitvoerder();
        ReflectionTestUtils.setField(uitvoerder, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(uitvoerder, "relatieRepository", relatieRepository);
        ReflectionTestUtils.setField(uitvoerder, "geboorteActieValidator", geboorteActieValidator);

        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("234567891"))
               .thenReturn(bouwPersoon(1L, "234567891"));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("345678912"))
               .thenReturn(bouwPersoon(2L, "345678912"));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("456789123"))
               .thenReturn(null);
    }

    /**
     * Bouwt en retourneert een {@link Betrokkenheid} instantie met daarin de minimale velden ingevuld voor een
     * kind zoals benodigd voor een eerste inschrijving.
     */
    private Betrokkenheid bouwKindBetrokkenheid(final String bsn) {
        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        betrokkenheid.setBetrokkene(bouwPersoon(null, bsn));
        return betrokkenheid;
    }

    /**
     * Bouwt en retourneert een {@link Betrokkenheid} instantie met daarin de minimale velden ingevuld voor een
     * ouder zoals benodigd voor een eerste inschrijving.
     */
    private Betrokkenheid bouwOuderBetrokkenheid(final Long id, final String bsn) {
        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        betrokkenheid.setBetrokkene(bouwPersoon(id, bsn));
        return betrokkenheid;
    }

    /**
     * Bouwt en retourneert een instantie van {@link Persoon} waarbij de id en bsn is gezet naar de opgegeven
     * waardes.
     */
    private Persoon bouwPersoon(final Long id, final String bsn) {
        Persoon persoon = new Persoon();
        persoon.setId(id);
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer(bsn);
        return persoon;
    }
}
