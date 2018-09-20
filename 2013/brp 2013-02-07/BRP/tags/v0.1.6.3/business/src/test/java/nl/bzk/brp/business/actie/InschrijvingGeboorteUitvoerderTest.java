/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.business.actie.validatie.GeboorteActieValidator;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.PersoonMdlRepository;
import nl.bzk.brp.dataaccess.repository.RelatieMdlRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor de {@link InschrijvingGeboorteUitvoerder} class. */
public class InschrijvingGeboorteUitvoerderTest extends AbstractStapTest {

    private AbstractActieUitvoerder uitvoerder;
    @Mock
    private PersoonMdlRepository    persoonRepository;
    @Mock
    private RelatieMdlRepository    relatieRepository;
    @Mock
    private GeboorteActieValidator  geboorteActieValidator;
    @Mock
    private ActieRepository         actieRepository;

    @Test(expected = IllegalArgumentException.class)
    public void testUitvoerZonderActie() {
        uitvoerder.voerUit(null, bouwBerichtContext());
    }

    @Test
    public void testUitvoerMetActieMetNullVoorRootObjecten() {
        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(null);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieZonderRootObjecten() {
        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(new ArrayList<RootObject>());

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testNormaleActie() {
        BerichtContext bc = bouwBerichtContext();
        List<Melding> meldingen = uitvoerder.voerUit(getInschrijvingGeboorteActie(), bc);
        Assert.assertNull(meldingen);
        // Verifieer opslag actie
        Mockito.verify(actieRepository, Mockito.times(1)).save(Matchers.any(ActieModel.class));
        // verifieer opslag persoon
        Mockito.verify(persoonRepository).opslaanNieuwPersoon(Matchers.any(PersoonModel.class),
                Matchers.any(ActieModel.class), Matchers.eq(new Datum(20120325)), Matchers.any(DatumTijd.class));
        // Opslag historie is verplaats naar mdlRepository
        // verifieer opslag historie
        // Mockito.verify(persoonRepository).werkHistorieBij(Matchers.any(PersoonModel.class),
        // Matchers.any(PersoonModel.class), Matchers.any(ActieModel.class),
        // Matchers.eq(Integer.valueOf(20120325)));
        // Verifieer opslag relatie
        Mockito.verify(relatieRepository).opslaanNieuweRelatie(Matchers.any(RelatieModel.class),
                Matchers.eq(new Datum(20120325)), Matchers.any(Date.class), Matchers.any(ActieModel.class));
        // Opslag historie is verplaats naar mdlRepository
        // Verifieer opslag historie voor relatie
        // Mockito.verify(relatieRepository).werkHistorieBij(Matchers.any(PersistentRelatie.class),
        // Matchers.any(ActieModel.class), Matchers.eq(Integer.valueOf(20120325)));
        // 1 hoofd persoon bijgehouden. 2 ouders
        Assert.assertEquals(1, bc.getHoofdPersonen().size());
        Assert.assertEquals(2, bc.getBijPersonen().size());

        Assert.assertEquals("123456789", bc.getHoofdPersonen().get(0).getIdentificatieNummers()
                .getBurgerServiceNummer().getWaarde());
        Assert.assertTrue(Arrays.asList("345678912", "234567891").contains(
                bc.getBijPersonen().get(0).getIdentificatieNummers().getBurgerServiceNummer().getWaarde()));
        Assert.assertTrue(Arrays.asList("345678912", "234567891").contains(
                bc.getBijPersonen().get(1).getIdentificatieNummers().getBurgerServiceNummer().getWaarde()));

        Assert.assertEquals(new Integer(20120325), bc.getHoofdPersonen().get(0).getInschrijving().getDatumInschrijving()
                .getWaarde());
        Assert.assertEquals(new Long(1L), bc.getHoofdPersonen().get(0).getInschrijving().getVersienummer()
                .getWaarde());
    }

    @Test
    public void testActieZonderBetrokkenheden() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).setBetrokkenheden(null);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testActieMetOnbekendeOuder() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).getBetrokkenheden().add(
                bouwOuderBetrokkenheid(3L, "456789123"));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testActieMetOuderZonderBsn() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).getBetrokkenheden().add(bouwOuderBetrokkenheid(4L, null));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testUitvoerMetActieMetAndereRootObjecten() {
        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(Mockito.mock(RootObject.class));
        rootObjecten.add(Mockito.mock(RootObject.class));

        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(rootObjecten);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieMetPersoonEnAndereRootObjecten() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        actie.getRootObjecten().add(0, Mockito.mock(RootObject.class));
        actie.getRootObjecten().add(Mockito.mock(RootObject.class));

        Assert.assertNull(uitvoerder.voerUit(actie, bouwBerichtContext()));
    }

    @Test
    public void testUitvoerMetBestaandBsn() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        Mockito.doThrow(new ObjectReedsBestaandExceptie(ObjectReedsBestaandExceptie.ReferentieVeld.BSN, null, null))
                .when(persoonRepository)
                .opslaanNieuwPersoon(Matchers.any(PersoonModel.class), Matchers.any(ActieModel.class),
                        Matchers.any(Datum.class), Matchers.any(DatumTijd.class));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBER00121, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetOnbekendeReferentie() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        Mockito.doThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDCODE, "abc", null))
                .when(persoonRepository)
                .opslaanNieuwPersoon(Matchers.any(PersoonModel.class), Matchers.any(ActieModel.class),
                        Matchers.any(Datum.class), Matchers.any(DatumTijd.class));

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
    private ActieBericht getInschrijvingGeboorteActie() {
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.AANGIFTE_GEBOORTE);
        actie.setDatumAanvangGeldigheid(new Datum(20120325));

        RelatieStandaardGroepBericht gegevens = new RelatieStandaardGroepBericht();

        RelatieBericht relatie = new RelatieBericht();
        relatie.setGegevens(gegevens);
        relatie.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(relatie);
        actie.setRootObjecten(rootObjecten);

        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
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
        ReflectionTestUtils.setField(uitvoerder, "actieRepository", actieRepository);

        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("234567891")))
                .thenReturn(bouwPersoon(1L, "234567891"));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("345678912")))
                .thenReturn(bouwPersoon(2L, "345678912"));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("456789123")))
                .thenReturn(null);

        Mockito.when(actieRepository.save(Matchers.any(ActieModel.class))).thenReturn(
                new ActieModel(new ActieBericht()));

        PersoonModel pPersoon = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(pPersoon, "id", 15L);

        Mockito.when(
                persoonRepository.opslaanNieuwPersoon(Matchers.any(PersoonModel.class), Matchers.any(ActieModel.class),
                        Matchers.any(Datum.class), Matchers.any(DatumTijd.class))).thenReturn(pPersoon);
    }

    /**
     * Bouwt en retourneert een {@link BetrokkenheidBericht} instantie met daarin de minimale velden ingevuld voor een
     * kind zoals benodigd voor een eerste inschrijving.
     */
    private BetrokkenheidBericht bouwKindBetrokkenheid(final String bsn) {
        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setRol(SoortBetrokkenheid.KIND);
        betrokkenheid.setBetrokkene(bouwPersoonBericht(bsn));
        return betrokkenheid;
    }

    /**
     * Bouwt en retourneert een {@link BetrokkenheidBericht} instantie met daarin de minimale velden ingevuld voor een
     * ouder zoals benodigd voor een eerste inschrijving.
     */
    private BetrokkenheidBericht bouwOuderBetrokkenheid(final Long id, final String bsn) {
        BetrokkenheidOuderlijkGezagGroepBericht betrokkenheidOuderlijkGezag =
            new BetrokkenheidOuderlijkGezagGroepBericht();
        BetrokkenheidOuderschapGroepBericht betrokkenheidOuderschap = new BetrokkenheidOuderschapGroepBericht();

        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setBetrokkenheidOuderschap(betrokkenheidOuderschap);
        betrokkenheid.setBetrokkenheidOuderlijkGezag(betrokkenheidOuderlijkGezag);

        betrokkenheid.setRol(SoortBetrokkenheid.OUDER);
        betrokkenheid.setBetrokkene(bouwPersoonBericht(bsn));
        return betrokkenheid;
    }

    private PersoonBericht bouwPersoonBericht(final String bsn) {
        PersoonBericht persoon = new PersoonBericht();

        persoon.setIdentificatieNummers(new PersoonIdentificatieNummersGroepBericht());
        persoon.getIdentificatieNummers().setBurgerServiceNummer(new Burgerservicenummer(bsn));
        return persoon;
    }

    /**
     * Bouwt en retourneert een instantie van {@link PersoonModel} waarbij de id en bsn is gezet naar de opgegeven
     * waardes.
     */
    private PersoonModel bouwPersoon(final Long id, final String bsn) {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatieNummers(new PersoonIdentificatieNummersGroepBericht());
        persoonBericht.getIdentificatieNummers().setBurgerServiceNummer(new Burgerservicenummer(bsn));

        PersoonModel persoon = new PersoonModel(persoonBericht);
        ReflectionTestUtils.setField(persoon, "id", id);

        return persoon;
    }
}
