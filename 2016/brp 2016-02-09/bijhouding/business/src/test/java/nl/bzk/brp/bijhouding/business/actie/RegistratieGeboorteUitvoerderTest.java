/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.inschrijving.InschrijvingAfleidingDoorGeboorte;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.BerichtBuilder;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class RegistratieGeboorteUitvoerderTest {

    private static final String IDENTIFICERENDE_SLEUTEL = "technischeSleutel=123456789";

    private final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht = new FamilierechtelijkeBetrekkingBericht();

    @Mock
    private FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekkingHisVolledigImpl;

    private final RegistratieGeboorteUitvoerder registratieGeboorteUitvoerder = new RegistratieGeboorteUitvoerder();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        final ActieRegistratieGeboorteBericht actieBericht = BerichtBuilder.bouwActieRegistratieGeboorte(20110101, null, null, null);
        registratieGeboorteUitvoerder.setActieBericht(actieBericht);
        registratieGeboorteUitvoerder.setActieModel(maakActie(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND));
    }

    @Test
    public void testVerzamelVerwerkingsregels() {
        final PersoonBericht kind = new PersoonBericht();
        kind.setCommunicatieID("comId");

        kind.setVoornamen(maakVoornamen(kind));
        kind.setGeslachtsnaamcomponenten(maakGeslachtsnaamcomponenten(kind));
        kind.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        kind.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        kind.setGeboorte(new PersoonGeboorteGroepBericht());
        kind.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());

        final KindBericht betrokkenheidKind = new KindBericht();
        betrokkenheidKind.setPersoon(kind);

        final PersoonBericht ouder = new PersoonBericht();
        ouder.setIdentificerendeSleutel(IDENTIFICERENDE_SLEUTEL);

        final List<BetrokkenheidBericht> familieBetrokkenheden = new ArrayList<>();
        familieBetrokkenheden.add(betrokkenheidKind);

        final OuderBericht ouderBericht = new OuderBericht();
        ouderBericht.setPersoon(ouder);
        familieBetrokkenheden.add(ouderBericht);

        familierechtelijkeBetrekkingBericht.setBetrokkenheden(familieBetrokkenheden);
        familierechtelijkeBetrekkingBericht.setObjectSleutel("");

        registratieGeboorteUitvoerder.setContext(maakBerichtContext());
        ReflectionTestUtils.setField(registratieGeboorteUitvoerder, "berichtRootObject", familierechtelijkeBetrekkingBericht);

        final Set<BetrokkenheidHisVolledigImpl> betrokkenheden = new HashSet<>();
        Mockito.when(familierechtelijkeBetrekkingHisVolledigImpl.getBetrokkenheden()).thenReturn(betrokkenheden);
        ReflectionTestUtils.setField(registratieGeboorteUitvoerder, "modelRootObject", familierechtelijkeBetrekkingHisVolledigImpl);

        Assert.assertEquals(0, getVerwerkingsregels(registratieGeboorteUitvoerder).size());

        // Nu nog geen betrokkenheden
        Assert.assertEquals(0, registratieGeboorteUitvoerder.getModelRootObject().getBetrokkenheden().size());

        registratieGeboorteUitvoerder.verzamelVerwerkingsregels();

        // Test dat er 6 verwerkingsregels zijn voor het kind en 1 voor de ouder en 1 voor de familierechtelijke betrekking.
        Assert.assertEquals(8, getVerwerkingsregels(registratieGeboorteUitvoerder).size());

        // 2 betrokkenheden: ouder en kind
        Assert.assertEquals(2, registratieGeboorteUitvoerder.getModelRootObject().getBetrokkenheden().size());
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : registratieGeboorteUitvoerder.getModelRootObject().getBetrokkenheden()) {
            Assert.assertEquals(1, betrokkenheidHisVolledig.getBetrokkenheidHistorie().getHistorie().size());
            Assert.assertNotNull(betrokkenheidHisVolledig.getBetrokkenheidHistorie().getActueleRecord());
        }
        Assert.assertEquals(2, registratieGeboorteUitvoerder.getModelRootObject().getBetrokkenheden().size());
    }

    @Test
    public void testMaakNieuwRootObjectHisVolledig() {
        final FamilierechtelijkeBetrekkingHisVolledig
            resultaat = registratieGeboorteUitvoerder.maakNieuwRootObjectHisVolledig();

        Assert.assertNotNull(resultaat);
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        List<Afleidingsregel> afleidingsregels = getAfleidingsregels(registratieGeboorteUitvoerder);
        Assert.assertEquals(0, afleidingsregels.size());

        registratieGeboorteUitvoerder.verzamelAfleidingsregels();

        afleidingsregels = getAfleidingsregels(registratieGeboorteUitvoerder);
        Assert.assertEquals(1, afleidingsregels.size());
        Assert.assertTrue(afleidingsregels.get(0) instanceof InschrijvingAfleidingDoorGeboorte);
    }

    /**
     * Haalt de afleidingsregels uit de uitvoerder.
     *
     * @param regUitvoerder registratie geboorte uitvoerder
     * @return lijst met afleidingsregels
     */
    @SuppressWarnings("unchecked")
    private List<Afleidingsregel> getAfleidingsregels(final RegistratieGeboorteUitvoerder regUitvoerder) {
        return (List<Afleidingsregel>) ReflectionTestUtils.getField(regUitvoerder, "afleidingsregels");
    }

    /**
     * Haalt de verwerkingsregels uit de uitvoerder.
     *
     * @param regUitvoerder registratie geboorte uitvoerder
     * @return lijst met verwerkingsregels
     */
    @SuppressWarnings("unchecked")
    private List<Verwerkingsregel> getVerwerkingsregels(final RegistratieGeboorteUitvoerder regUitvoerder) {
        return (List<Verwerkingsregel>) ReflectionTestUtils.getField(regUitvoerder, "verwerkingsregels");
    }

    /**
     * Maak bericht context.
     *
     * @return bericht context
     */
    private BijhoudingBerichtContext maakBerichtContext() {
        final BijhoudingBerichtContext berichtContext = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, null, null);
        ReflectionTestUtils.setField(berichtContext, "tijdstipVerwerking", new DatumAttribuut(20110101).toDate());

        final PersoonHisVolledigImpl rootObjectOuder = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final Map<String, HisVolledigRootObject> bestaandeBijhoudingsRootObjecten
            = new HashMap<>();
        bestaandeBijhoudingsRootObjecten.put(IDENTIFICERENDE_SLEUTEL, rootObjectOuder);
        ReflectionTestUtils.setField(berichtContext, "bestaandeBijhoudingsRootObjecten",
            bestaandeBijhoudingsRootObjecten);

        return berichtContext;
    }

    /**
     * Maak voornamen voor het kind.
     *
     * @param kind kind
     * @return lijst met voornamen
     */
    private List<PersoonVoornaamBericht> maakVoornamen(final PersoonBericht kind) {
        final List<PersoonVoornaamBericht> voornamen = new ArrayList<>();
        final PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setPersoon(kind);
        voornaam.setVolgnummer(new VolgnummerAttribuut(1));
        voornamen.add(voornaam);
        return voornamen;
    }

    /**
     * Maak geslachtsnaamcomponenten voor het kind.
     *
     * @param kind kind
     * @return lijst met geslachtsnaamcomponenten
     */
    private List<PersoonGeslachtsnaamcomponentBericht> maakGeslachtsnaamcomponenten(final PersoonBericht kind) {
        final List<PersoonGeslachtsnaamcomponentBericht> geslachtsnaamcomponenten = new ArrayList<>();
        final PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponentBericht();
        geslachtsnaamcomponent.setPersoon(kind);
        geslachtsnaamcomponent.setVolgnummer(new VolgnummerAttribuut(1));
        geslachtsnaamcomponenten.add(geslachtsnaamcomponent);
        return geslachtsnaamcomponenten;
    }

    /**
     * Creeert een registratie huwelijk actie.
     *
     * @return het actie model
     */
    private ActieModel maakActie(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
                              new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(soortAdministratieveHandeling),
                                                                null, null, null), null, new DatumEvtDeelsOnbekendAttribuut(20130701), null,
                              DatumTijdAttribuut.nu(), null
        );
    }
}
