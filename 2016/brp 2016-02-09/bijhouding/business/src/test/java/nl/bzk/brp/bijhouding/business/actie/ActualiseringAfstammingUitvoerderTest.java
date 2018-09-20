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
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.testconfig.AttribuutAdministratieTestConfig;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
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
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.BerichtBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AttribuutAdministratieTestConfig.class })
public class ActualiseringAfstammingUitvoerderTest {

    private static final String IDENTIFICERENDE_SLEUTEL_KIND_1 = "1234";
    private static final String IDENTIFICERENDE_SLEUTEL_OUDER  = "56789";

    private static final String TECHNISCHE_ID_FAMILIE_RECHTELIJK_BETREKKING = "111";
    private static final String IDENTIFICERENDE_SLEUTEL_NIEUWE_VADER        = "222";
    private static final String IDENTIFICERENDE_SLEUTEL_NIEUWE_MOEDER       = "333";
    private static final String IDENTIFICERENDE_SLEUTEL_KIND_2              = "444";
    private static final String JAN                                         = "JAN";
    private static final String SAMEN                                       = "samen";
    private static final String VOORNAMEN                                   = "voornamen";
    private static final String GESLACHTSNAMEN                              = "geslachtsnamen";

    private FamilierechtelijkeBetrekkingHisVolledigImpl familie;
    private PersoonHisVolledigImpl                      moeder;
    private PersoonHisVolledigImpl                      vader;
    private PersoonHisVolledigImpl                      kind;
    private PersoonHisVolledigImpl                      nieuweMoeder;
    private PersoonHisVolledigImpl                      nieuweVader;
    private ActieModel                                  actieModel;


    private FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht = new FamilierechtelijkeBetrekkingBericht();

    @Mock
    private FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekkingHisVolledigImpl;

    private ActualiseringAfstammingUitvoerder uitvoerder;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        nieuweMoeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeboorteRecord(19830101).datumGeboorte(19860101).eindeRecord().build();
        nieuweVader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeboorteRecord(19830101).datumGeboorte(19830101).eindeRecord().build();


        moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeboorteRecord(19830101).datumGeboorte(19860101).eindeRecord().build();
        vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeboorteRecord(19830101).datumGeboorte(19830101).eindeRecord().build();

        kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwSamengesteldeNaamRecord(20100101, null, 20100101)
            .geslachtsnaamstam("PIET")
            .voornamen(JAN)
            .scheidingsteken("-")
            .voorvoegsel("DER").eindeRecord()
            .nieuwGeboorteRecord(20100101).datumGeboorte(20100101).eindeRecord()
            .build();

        actieModel = maakActie();
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(moeder, vader, kind, 20100101, actieModel);

        familie = (FamilierechtelijkeBetrekkingHisVolledigImpl) kind.getBetrokkenheden().iterator().next().getRelatie();

        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, null, null);
        context.voegBestaandBijhoudingsRootObjectToe(TECHNISCHE_ID_FAMILIE_RECHTELIJK_BETREKKING, familie);
        context.voegBestaandBijhoudingsRootObjectToe(IDENTIFICERENDE_SLEUTEL_NIEUWE_VADER, nieuweVader);
        context.voegBestaandBijhoudingsRootObjectToe(IDENTIFICERENDE_SLEUTEL_NIEUWE_MOEDER, nieuweMoeder);
        context.voegBestaandBijhoudingsRootObjectToe(IDENTIFICERENDE_SLEUTEL_KIND_2, kind);


        uitvoerder = new ActualiseringAfstammingUitvoerder();
        uitvoerder.setContext(context);
        uitvoerder.setActieModel(actieModel);

        final ActieRegistratieGeboorteBericht actieBericht =
            BerichtBuilder.bouwActieRegistratieGeboorte(20110101, null, null, null);
        uitvoerder.setActieBericht(actieBericht);
    }

    @Test
    public void testVerzamelVerwerkingsregels() {
        final PersoonBericht testKind = new PersoonBericht();
        testKind.setCommunicatieID("comId");
        testKind.setIdentificerendeSleutel(IDENTIFICERENDE_SLEUTEL_KIND_1);

        testKind.setVoornamen(maakVoornamen(testKind));
        testKind.setGeslachtsnaamcomponenten(maakGeslachtsnaamcomponenten(testKind));
        testKind.setGeboorte(new PersoonGeboorteGroepBericht());

        final List<BetrokkenheidBericht> betrokkenheidBerichten = new ArrayList<>();

        final KindBericht betrokkenheidKind = new KindBericht();
        betrokkenheidKind.setPersoon(testKind);
        betrokkenheidBerichten.add(betrokkenheidKind);

        final PersoonBericht ouder = new PersoonBericht();
        ouder.setIdentificerendeSleutel(IDENTIFICERENDE_SLEUTEL_OUDER);

        final OuderBericht ouderBericht = new OuderBericht();
        ouderBericht.setPersoon(ouder);
        betrokkenheidBerichten.add(ouderBericht);

        familierechtelijkeBetrekkingBericht.setObjectSleutel("");
        familierechtelijkeBetrekkingBericht.setBetrokkenheden(betrokkenheidBerichten);

        ReflectionTestUtils.setField(uitvoerder, "berichtRootObject",
            familierechtelijkeBetrekkingBericht);

        final HashSet<BetrokkenheidHisVolledigImpl> betrokkenheden = new HashSet<>();
        final PersoonHisVolledigImpl kindModel =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final KindHisVolledigImpl kindBetrokkenheid = new KindHisVolledigImpl(
            familierechtelijkeBetrekkingHisVolledigImpl, kindModel);
        betrokkenheden.add(kindBetrokkenheid);
        Mockito.when(familierechtelijkeBetrekkingHisVolledigImpl.getBetrokkenheden()).thenReturn(betrokkenheden);
        ReflectionTestUtils.setField(uitvoerder, "modelRootObject",
            familierechtelijkeBetrekkingHisVolledigImpl);
        uitvoerder.setContext(maakBerichtContext(kindModel));

        Assert.assertEquals(0, getVerwerkingsregels(uitvoerder).size());

        uitvoerder.verzamelVerwerkingsregels();

        // Verwacht 3 regels voor het kind en 1 voor de ouder (ouderschap)
        Assert.assertEquals(4, getVerwerkingsregels(uitvoerder).size());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        List<Afleidingsregel> afleidingsregels = getAfleidingsregels(uitvoerder);
        Assert.assertEquals(0, afleidingsregels.size());

        uitvoerder.verzamelAfleidingsregels();

        afleidingsregels = getAfleidingsregels(uitvoerder);
        Assert.assertEquals(0, afleidingsregels.size());
    }

    /**
     * Haalt de afleidingsregels uit de uitvoerder.
     *
     * @param regUitvoerder registratie geboorte uitvoerder
     * @return lijst met afleidingsregels
     */
    @SuppressWarnings("unchecked")
    private List<Afleidingsregel> getAfleidingsregels(final ActualiseringAfstammingUitvoerder regUitvoerder) {
        return (List<Afleidingsregel>) ReflectionTestUtils.getField(regUitvoerder, "afleidingsregels");
    }

    /**
     * Haalt de verwerkingsregels uit de uitvoerder.
     *
     * @param regUitvoerder registratie geboorte uitvoerder
     * @return lijst met verwerkingsregels
     */
    @SuppressWarnings("unchecked")
    private List<Verwerkingsregel> getVerwerkingsregels(final ActualiseringAfstammingUitvoerder regUitvoerder) {
        return (List<Verwerkingsregel>) ReflectionTestUtils
            .getField(regUitvoerder, "verwerkingsregels");
    }

    /**
     * Maak bericht context.
     *
     * @return bericht context
     */
    private BijhoudingBerichtContext maakBerichtContext(final PersoonHisVolledigImpl kindModel) {
        final BijhoudingBerichtContext berichtContext = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, null, null);
        ReflectionTestUtils
            .setField(berichtContext, "tijdstipVerwerking", new DatumAttribuut(20110101).toDate());

        final PersoonHisVolledigImpl rootObjectOuder =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final Map<String, HisVolledigRootObject> bestaandeBijhoudingsRootObjecten =
            new HashMap<String, HisVolledigRootObject>();
        bestaandeBijhoudingsRootObjecten.put(IDENTIFICERENDE_SLEUTEL_OUDER, rootObjectOuder);
        bestaandeBijhoudingsRootObjecten.put(IDENTIFICERENDE_SLEUTEL_KIND_1, kindModel);
        ReflectionTestUtils.setField(berichtContext, "bestaandeBijhoudingsRootObjecten",
            bestaandeBijhoudingsRootObjecten);

        return berichtContext;
    }

    /**
     * Maak voornamen voor het kind.
     *
     * @param testKind kind
     * @return lijst met voornamen
     */
    private List<PersoonVoornaamBericht> maakVoornamen(final PersoonBericht testKind) {
        final List<PersoonVoornaamBericht> voornamen = new ArrayList<>();
        final PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setPersoon(testKind);
        voornaam.setVolgnummer(new VolgnummerAttribuut(1));
        voornamen.add(voornaam);
        return voornamen;
    }

    /**
     * Maak geslachtsnaamcomponenten voor het kind.
     *
     * @param testKind kind
     * @return lijst met geslachtsnaamcomponenten
     */
    private List<PersoonGeslachtsnaamcomponentBericht> maakGeslachtsnaamcomponenten(final PersoonBericht testKind) {
        final List<PersoonGeslachtsnaamcomponentBericht> geslachtsnaamcomponenten =
            new ArrayList<PersoonGeslachtsnaamcomponentBericht>();
        final PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponentBericht();
        geslachtsnaamcomponent.setPersoon(testKind);
        geslachtsnaamcomponent.setVolgnummer(new VolgnummerAttribuut(1));
        geslachtsnaamcomponenten.add(geslachtsnaamcomponent);
        return geslachtsnaamcomponenten;
    }

    @Test
    public void testAdoptieMetIngeschrevenOudersZonderKindGegevens() {
        Assert.assertEquals(3, familie.getBetrokkenheden().size());

        uitvoerder.setActieBericht(maakBericht(null, null, null, null, null));
        uitvoerder.getActieBericht().setDatumAanvangGeldigheid(actieModel.getDatumAanvangGeldigheid());
        uitvoerder.voerActieUit();

        Assert.assertEquals(5, familie.getBetrokkenheden().size());

        //Begin situatie van kind ongewijzigd.
        Assert.assertEquals(JAN,
            kind.getPersoonSamengesteldeNaamHistorie().getActueleRecord().getVoornamen().getWaarde());
        Assert.assertEquals(20100101,
            kind.getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte().getWaarde().intValue());
        Assert.assertEquals(null, kind.getPersoonGeslachtsaanduidingHistorie().getActueleRecord());
        Assert.assertEquals(0, kind.getVoornamen().size());
        Assert.assertEquals(0, kind.getGeslachtsnaamcomponenten().size());
    }

    @Test
    public void testAdoptieMetIngeschrevenOudersKindMetAangepasteGegevens() {
        Assert.assertEquals(3, familie.getBetrokkenheden().size());

        uitvoerder.setActieBericht(maakBericht(SAMEN, 1980, Geslachtsaanduiding.MAN, VOORNAMEN, GESLACHTSNAMEN));
        uitvoerder.setActieModel(actieModel);
        uitvoerder.voerActieUit();

        Assert.assertEquals(5, familie.getBetrokkenheden().size());

        //Begin situatie van kind ongewijzigd.
        Assert.assertEquals(SAMEN,
            kind.getPersoonSamengesteldeNaamHistorie().getActueleRecord().getVoornamen().getWaarde());
        Assert.assertEquals(1980,
            kind.getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte().getWaarde().intValue());
        Assert.assertEquals(Geslachtsaanduiding.MAN,
            kind.getPersoonGeslachtsaanduidingHistorie().getActueleRecord().getGeslachtsaanduiding().getWaarde());
        Assert.assertEquals(VOORNAMEN,
            kind.getVoornamen().iterator().next().getPersoonVoornaamHistorie().getActueleRecord().getNaam()
                .getWaarde());
        Assert.assertEquals(GESLACHTSNAMEN,
            kind.getGeslachtsnaamcomponenten().iterator().next().getPersoonGeslachtsnaamcomponentHistorie()
                .getActueleRecord().getStam().getWaarde());
    }

    /**
     * Maak een actie registratie geboorte bericht.
     *
     * @param samengesteldenaamVoornaam samengesteldenaam voornaam
     * @param datumGeboorte             datum geboorte
     * @param geslachtsaanduiding       geslachtsaanduiding
     * @param voornamen                 voornamen
     * @param geslachtsnamen            geslachtsnamen
     * @return actie registratie geboorte bericht
     */
    private ActieRegistratieGeboorteBericht maakBericht(final String samengesteldenaamVoornaam,
        final Integer datumGeboorte,
        final Geslachtsaanduiding geslachtsaanduiding,
        final String voornamen,
        final String geslachtsnamen)
    {
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
            new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();

        final PersoonBericht kindBericht = new PersoonBericht();
        kindBericht.setIdentificerendeSleutel(IDENTIFICERENDE_SLEUTEL_KIND_2);
        if (samengesteldenaamVoornaam != null) {
            kindBericht.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
            kindBericht.getSamengesteldeNaam().setDatumAanvangGeldigheid(actieModel.getDatumAanvangGeldigheid());
            kindBericht.getSamengesteldeNaam().setVoornamen(new VoornamenAttribuut(samengesteldenaamVoornaam));
        }
        if (datumGeboorte != null) {
            kindBericht.setGeboorte(new PersoonGeboorteGroepBericht());
            kindBericht.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(datumGeboorte));
        }
        if (geslachtsaanduiding != null) {
            kindBericht.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
            kindBericht.getGeslachtsaanduiding().setDatumAanvangGeldigheid(actieModel.getDatumAanvangGeldigheid());
            kindBericht.getGeslachtsaanduiding()
                .setGeslachtsaanduiding(new GeslachtsaanduidingAttribuut(geslachtsaanduiding));
        }
        if (voornamen != null) {
            final PersoonVoornaamStandaardGroepBericht standaard = new PersoonVoornaamStandaardGroepBericht();
            standaard.setDatumAanvangGeldigheid(actieModel.getDatumAanvangGeldigheid());
            standaard.setNaam(new VoornaamAttribuut(voornamen));
            kindBericht.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
            final PersoonVoornaamBericht pvb = new PersoonVoornaamBericht();
            pvb.setVolgnummer(new VolgnummerAttribuut(1));
            kindBericht.getVoornamen().add(pvb);
            kindBericht.getVoornamen().get(0).setStandaard(standaard);
        }
        if (geslachtsnamen != null) {
            final PersoonGeslachtsnaamcomponentStandaardGroepBericht standaard =
                new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
            standaard.setDatumAanvangGeldigheid(actieModel.getDatumAanvangGeldigheid());
            standaard.setStam(new GeslachtsnaamstamAttribuut(geslachtsnamen));
            kindBericht.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
            final PersoonGeslachtsnaamcomponentBericht pgb = new PersoonGeslachtsnaamcomponentBericht();
            pgb.setVolgnummer(new VolgnummerAttribuut(1));
            kindBericht.getGeslachtsnaamcomponenten().add(pgb);
            kindBericht.getGeslachtsnaamcomponenten().get(0).setStandaard(standaard);
        }

        final PersoonBericht vaderBericht = new PersoonBericht();
        vaderBericht.setIdentificerendeSleutel(IDENTIFICERENDE_SLEUTEL_NIEUWE_VADER);

        final PersoonBericht moederBericht = new PersoonBericht();
        moederBericht.setIdentificerendeSleutel(IDENTIFICERENDE_SLEUTEL_NIEUWE_MOEDER);

        final FamilierechtelijkeBetrekkingBericht familieBericht = relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie()
            .voegKindToe(kindBericht)
            .voegOuderToe(vaderBericht)
            .voegOuderToe(moederBericht)
            .getRelatie();
        familieBericht.setObjectSleutel(TECHNISCHE_ID_FAMILIE_RECHTELIJK_BETREKKING);


        final ActieRegistratieGeboorteBericht actieRegistratieGeboorteBericht = new ActieRegistratieGeboorteBericht();
        actieRegistratieGeboorteBericht.setRootObject(familieBericht);

        return actieRegistratieGeboorteBericht;
    }

    /**
     * Creeert een standaard actie voor registratie geboorte.
     *
     * @return het actie model
     */
    private ActieModel maakActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_OUDER),
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.ADOPTIE_INGEZETENE), null,
                null, null), null, new DatumEvtDeelsOnbekendAttribuut(20130701), null, DatumTijdAttribuut.nu(), null);
    }
}
