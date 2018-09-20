/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsnaamcomponent.GeslachtsnaamcomponentVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.HuwelijkGeregistreerdPartnerschapVerwerker;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class RegistratieHuwelijkGeregistreerdPartnerschapUitvoerderTest {

    public static final String MODEL_ROOT_OBJECT = "modelRootObject";
    //Bestaande personen
    private PersoonHisVolledigImpl persoonHisVolledigMan;
    private PersoonHisVolledigImpl persoonHisVolledigVrouw;

    @Mock
    private HuwelijkGeregistreerdPartnerschapHisVolledigImpl huwelijkGeregistreerdPartnerschapHisVolledig;

    //Nieuwe situatie
    private PersoonBericht persoonBerichtMan;
    private PersoonBericht persoonBerichtVrouw;

    @Mock
    private BijhoudingBerichtContext context;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        bereidBestaandeSituatieVoor();
        context = Mockito.mock(BijhoudingBerichtContext.class);
    }

    @Test
    public void testVerzamelVerwerkingsregels() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijkGeregistreerdPartnerschapBericht = maakNieuweSituatie();
        final RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder uitvoerder = maakUitvoerderHuwelijk(huwelijkGeregistreerdPartnerschapBericht);
        final Set<BetrokkenheidHisVolledigImpl> betrokkenheden = new HashSet<>();
        Mockito.when(huwelijkGeregistreerdPartnerschapHisVolledig.getBetrokkenheden()).thenReturn(betrokkenheden);
        ReflectionTestUtils.setField(uitvoerder, MODEL_ROOT_OBJECT, huwelijkGeregistreerdPartnerschapHisVolledig);
        uitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(1, getVerwerkingsregels(uitvoerder).size());
        Assert.assertTrue(getVerwerkingsregels(uitvoerder).get(0) instanceof HuwelijkGeregistreerdPartnerschapVerwerker);
    }

    @Test
    public void testVerzamelVerwerkingsregelsMetGeslachtsnaamcomponent() {
        final RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder uitvoerder = maakUitvoerderHuwelijk(maakNieuweSituatie(true));
        uitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(3, getVerwerkingsregels(uitvoerder).size());
        Assert.assertTrue(getVerwerkingsregels(uitvoerder).get(1)
            instanceof GeslachtsnaamcomponentVerwerker);
    }

    @Test
    public void testMaakNieuwRootObjectHisVolledigVoorVoltrekkingHuwelijk() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijkGeregistreerdPartnerschapBericht = maakNieuweSituatie();
        final RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder uitvoerder = maakUitvoerderHuwelijk(huwelijkGeregistreerdPartnerschapBericht);
        final Set<BetrokkenheidHisVolledigImpl> betrokkenheden = new HashSet<>();
        Mockito.when(huwelijkGeregistreerdPartnerschapHisVolledig.getBetrokkenheden()).thenReturn(betrokkenheden);
        ReflectionTestUtils.setField(uitvoerder, MODEL_ROOT_OBJECT, huwelijkGeregistreerdPartnerschapHisVolledig);
        uitvoerder.verzamelVerwerkingsregels();

        final HuwelijkGeregistreerdPartnerschapHisVolledigImpl huwelijk = uitvoerder.maakNieuwRootObjectHisVolledig();

        Assert.assertNotNull(huwelijk);
        Assert.assertTrue(isPersoonAanwezigInBetrokkenheden(huwelijk.getBetrokkenheden(), persoonHisVolledigMan));
        Assert.assertTrue(isPersoonAanwezigInBetrokkenheden(huwelijk.getBetrokkenheden(), persoonHisVolledigVrouw));

        // 2 betrokkenheden: partners
        Assert.assertEquals(2, huwelijk.getBetrokkenheden().size());
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : huwelijk.getBetrokkenheden()) {
            Assert.assertEquals(1, betrokkenheidHisVolledig.getBetrokkenheidHistorie().getHistorie().size());
            Assert.assertNotNull(betrokkenheidHisVolledig.getBetrokkenheidHistorie().getActueleRecord());
        }
    }

    @Test
    public void testMaakNieuwRootObjectHisVolledigVoorRegistratiePartnerschap() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijkGeregistreerdPartnerschapBericht = maakNieuweSituatie();
        final RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder uitvoerder = maakUitvoerderHuwelijk(huwelijkGeregistreerdPartnerschapBericht);
        final Set<BetrokkenheidHisVolledigImpl> betrokkenheden = new HashSet<>();
        Mockito.when(huwelijkGeregistreerdPartnerschapHisVolledig.getBetrokkenheden()).thenReturn(betrokkenheden);
        ReflectionTestUtils.setField(uitvoerder, MODEL_ROOT_OBJECT, huwelijkGeregistreerdPartnerschapHisVolledig);
        uitvoerder.verzamelVerwerkingsregels();

        final HuwelijkGeregistreerdPartnerschapHisVolledigImpl partnerschap = uitvoerder.maakNieuwRootObjectHisVolledig();

        Assert.assertNotNull(partnerschap);
        Assert.assertTrue(isPersoonAanwezigInBetrokkenheden(partnerschap.getBetrokkenheden(), persoonHisVolledigMan));
        Assert.assertTrue(isPersoonAanwezigInBetrokkenheden(partnerschap.getBetrokkenheden(), persoonHisVolledigVrouw));

        // 2 betrokkenheden: partners
        Assert.assertEquals(2, partnerschap.getBetrokkenheden().size());
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : partnerschap.getBetrokkenheden()) {
            Assert.assertEquals(1, betrokkenheidHisVolledig.getBetrokkenheidHistorie().getHistorie().size());
            Assert.assertNotNull(betrokkenheidHisVolledig.getBetrokkenheidHistorie().getActueleRecord());
        }
    }

    @Test
    public void testMaakNieuwRootObjectHisVolledigVoorGbaHuwelijk() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijkGeregistreerdPartnerschapBericht = maakNieuweSituatie();
        final RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder uitvoerder =
            maakUitvoerderHuwelijk(huwelijkGeregistreerdPartnerschapBericht,
                SoortAdministratieveHandeling.G_B_A_SLUITING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP);
        final Set<BetrokkenheidHisVolledigImpl> betrokkenheden = new HashSet<>();
        Mockito.when(huwelijkGeregistreerdPartnerschapHisVolledig.getBetrokkenheden()).thenReturn(betrokkenheden);
        ReflectionTestUtils.setField(uitvoerder, MODEL_ROOT_OBJECT, huwelijkGeregistreerdPartnerschapHisVolledig);
        uitvoerder.verzamelVerwerkingsregels();

        final HuwelijkGeregistreerdPartnerschapHisVolledigImpl huwelijk = uitvoerder.maakNieuwRootObjectHisVolledig();

        Assert.assertNotNull(huwelijk);
        Assert.assertTrue(isPersoonAanwezigInBetrokkenheden(huwelijk.getBetrokkenheden(), persoonHisVolledigMan));
        Assert.assertTrue(isPersoonAanwezigInBetrokkenheden(huwelijk.getBetrokkenheden(), persoonHisVolledigVrouw));

        // 2 betrokkenheden: partners
        Assert.assertEquals(2, huwelijk.getBetrokkenheden().size());
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : huwelijk.getBetrokkenheden()) {
            Assert.assertEquals(1, betrokkenheidHisVolledig.getBetrokkenheidHistorie().getHistorie().size());
            Assert.assertNotNull(betrokkenheidHisVolledig.getBetrokkenheidHistorie().getActueleRecord());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testMaakNieuwRootObjectHisVolledigVoorVerkeerdeAdministratieveHandeling() {
        final RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder uitvoerder = maakUitvoerderOnbekend(maakNieuweSituatie());

        uitvoerder.maakNieuwRootObjectHisVolledig();
    }

    /**
     * Maakt de uitvoerder voor registratie huwelijk.
     *
     * @param nieuweSituatie de nieuwe situatie die door middel van getBerichtRootObject wordt teruggegeven
     * @return de uitvoerder
     */
    private RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder maakUitvoerderHuwelijk(final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie) {
        return maakUitvoerderHuwelijk(nieuweSituatie, SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND);
    }

    /**
     * Maakt de uitvoerder voor registratie huwelijk.
     *
     * @param nieuweSituatie de nieuwe situatie die door middel van getBerichtRootObject wordt teruggegeven
     * @return de uitvoerder
     */
    private RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder maakUitvoerderHuwelijk(final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
     final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        final RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder huwelijkPartnerschapUitvoerder = new RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder() {
            @Override
            public HuwelijkGeregistreerdPartnerschapBericht getBerichtRootObject() {
                return nieuweSituatie;
            }
        };

        Mockito.when(context.zoekHisVolledigRootObject(persoonBerichtMan)).thenReturn(persoonHisVolledigMan);
        Mockito.when(context.zoekHisVolledigRootObject(persoonBerichtVrouw)).thenReturn(persoonHisVolledigVrouw);
        huwelijkPartnerschapUitvoerder.setContext(context);

        huwelijkPartnerschapUitvoerder.setActieModel(maakActie(soortAdministratieveHandeling));

        return huwelijkPartnerschapUitvoerder;
    }

    /**
     * Maakt de uitvoerder voor registratie partnerschap.
     *
     * @param nieuweSituatie de nieuwe situatie die door middel van getBerichtRootObject wordt teruggegeven
     * @return de uitvoerder
     */
    private RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder maakUitvoerderPartnerschap(
        final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie)
    {
        final RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder huwelijkPartnerschapUitvoerder = new RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder() {
            @Override
            public HuwelijkGeregistreerdPartnerschapBericht getBerichtRootObject() {
                return nieuweSituatie;
            }
        };

        Mockito.when(context.zoekHisVolledigRootObject(persoonBerichtMan)).thenReturn(persoonHisVolledigMan);
        Mockito.when(context.zoekHisVolledigRootObject(persoonBerichtVrouw)).thenReturn(persoonHisVolledigVrouw);
        huwelijkPartnerschapUitvoerder.setContext(context);

        huwelijkPartnerschapUitvoerder.setActieModel(
                maakActie(SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND));

        return huwelijkPartnerschapUitvoerder;
    }

    /**
     * Maakt de uitvoerder voor registratie partnerschap.
     *
     * @param nieuweSituatie de nieuwe situatie die door middel van getBerichtRootObject wordt teruggegeven
     * @return de uitvoerder
     */
    private RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder maakUitvoerderOnbekend(final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie) {
        final RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder huwelijkPartnerschapUitvoerder = new RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder() {
            @Override
            public HuwelijkGeregistreerdPartnerschapBericht getBerichtRootObject() {
                return nieuweSituatie;
            }
        };

        Mockito.when(context.zoekHisVolledigRootObject(persoonBerichtMan)).thenReturn(persoonHisVolledigMan);
        Mockito.when(context.zoekHisVolledigRootObject(persoonBerichtVrouw)).thenReturn(persoonHisVolledigVrouw);
        huwelijkPartnerschapUitvoerder.setContext(context);

        huwelijkPartnerschapUitvoerder.setActieModel(
                maakActie(SoortAdministratieveHandeling.BETWISTING_VAN_STAAT));

        return huwelijkPartnerschapUitvoerder;
    }

    /**
     * Maakt nieuwe situatie: relatie met man en vrouw.
     *
     * @return the huwelijk geregistreerd partnerschap bericht
     */
    private HuwelijkGeregistreerdPartnerschapBericht maakNieuweSituatie() {
        return maakNieuweSituatie(false);
    }

    private HuwelijkGeregistreerdPartnerschapBericht maakNieuweSituatie(final boolean geslachtsnaamcomponenten) {
        persoonBerichtMan = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 111, null, null, null, null, null, null, null);
        persoonBerichtVrouw = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 222, null, null, null, null, null, null, null);
        persoonBerichtMan.setIdentificatienummers(null);
        if (geslachtsnaamcomponenten) {
            persoonBerichtMan.setGeslachtsnaamcomponenten(Collections.singletonList(maakPersoonGeslachtsnaamcomponentBericht()));
        }
        persoonBerichtVrouw.setIdentificatienummers(null);
        if (geslachtsnaamcomponenten) {
            persoonBerichtVrouw.setGeslachtsnaamcomponenten(Collections.singletonList(maakPersoonGeslachtsnaamcomponentBericht()));
        }

        HuwelijkGeregistreerdPartnerschapBericht relatie = new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwelijkRelatie()
            .voegPartnerToe(persoonBerichtMan).voegPartnerToe(persoonBerichtVrouw).getRelatie();

        return relatie;

    }

    private PersoonGeslachtsnaamcomponentBericht maakPersoonGeslachtsnaamcomponentBericht() {
        final PersoonGeslachtsnaamcomponentBericht pgb = new PersoonGeslachtsnaamcomponentBericht();
        pgb.setVolgnummer(new VolgnummerAttribuut(1));
        pgb.setStandaard(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        return pgb;
    }

    /**
     * Zet de bestaande situatie klaar.
     */
    private void bereidBestaandeSituatieVoor() {
        persoonHisVolledigMan =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        persoonHisVolledigVrouw =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
    }

    /**
     * Creeert een registratie huwelijk actie.
     *
     * @return het actie model
     */
    private ActieModel maakActie(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        soortAdministratieveHandeling), null,
                        null, null
                ), null, new DatumEvtDeelsOnbekendAttribuut(20130701), null, DatumTijdAttribuut.nu(), null
        );
    }

    /**
     * Controleert of de persoon aanwezig is in de betrokkenheden.
     *
     * @param betrokkenheidHisVolledigs de betrokkenheden
     * @param persoonHisVolledig        persoon die verwacht wordt in de betrokkenheden
     * @return true als persoon aanwezig is
     */
    private boolean isPersoonAanwezigInBetrokkenheden(final Set<BetrokkenheidHisVolledigImpl> betrokkenheidHisVolledigs,
                                                      final PersoonHisVolledigImpl persoonHisVolledig)
    {
        boolean resultaat = false;

        for (final BetrokkenheidHisVolledigImpl betr : betrokkenheidHisVolledigs) {
            if (betr.getPersoon() == persoonHisVolledig) {
                resultaat = true;
                break;
            }
        }

        return resultaat;
    }

    /**
     * Haalt de verwerkingsregels uit de uitvoerder.
     *
     * @param regUitvoerder registratie overlijden uitvoerder
     * @return lijst met verwerkingsregels
     */
    @SuppressWarnings("unchecked")
    private List<Verwerkingsregel> getVerwerkingsregels(final RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder regUitvoerder) {
        return (List<Verwerkingsregel>) ReflectionTestUtils.getField(regUitvoerder, "verwerkingsregels");
    }
}
