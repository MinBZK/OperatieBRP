/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperkingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegistratieAdresActieElementTest extends AbstractElementTest {

    private static final char KIND = 'K';
    private static final char INGEZETENE = 'I';
    private static final char GEZAGHOUDER = 'G';
    private static final char PARTNER = 'P';
    private static final char OUDER = 'O';

    private static final String PARTIJCODE = "000022";
    private static final Integer LEEFTIJD_OUDER_18 = DatumUtil.vanXsdDatumNaarInteger("1979-01-01");
    private static final LandOfGebied NEDERLAND = new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "nederland");
    private static final Map<String, String> attr = new AbstractBmrGroep.AttributenBuilder().communicatieId("adres").objecttype("adres").build();
    private ElementBuilder builder = new ElementBuilder();

    @Mock
    private BijhoudingPersoon bijhoudingPersoonMock;

    @Before
    public void setup() {
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LandOfGebied.CODE_NEDERLAND)).thenReturn(NEDERLAND);
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf('M')).thenReturn(new RedenWijzigingVerblijf('M', "Ministerieel besluit"));
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf('P')).thenReturn(new RedenWijzigingVerblijf('P', "Aangifte door persoon"));
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf('I')).thenReturn(new RedenWijzigingVerblijf('I', "Infrastructureel"));
        when(getDynamischeStamtabelRepository().getAangeverByCode('O')).thenReturn(
                new Aangever('O', "Inwonend ouder voor meerderjarig kind", "Een ouder die op hetzelfde adres woont doet aangifte van het adres van het kind"));
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("7112"))
                .thenReturn(new Gemeente(Short.valueOf("7112"), "Apingendam", "7112", new Partij("Appingendam", "007112")));
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("7019"))
                .thenReturn(new Gemeente(Short.valueOf("7019"), "Ap", "7019", new Partij("App", "007019")));
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0003"))
                .thenReturn(new Gemeente(Short.valueOf("0003"), "test gemeente", "0003", new Partij("test partij", "000003")));
        when(bijhoudingPersoonMock.isDatumNaActueleBijhouding(any(DatumElement.class))).thenReturn(true);
    }

    @Test
    public void ConstructorMinimaal() {

        final ElementBuilder.AdresElementParameters params = new ElementBuilder.AdresElementParameters("A", 'V', 20010101, "7112");

        final AdresElement adres = builder.maakAdres("adres", params);
        assertEquals("A", adres.getSoortCode().getWaarde());
        assertEquals('V', adres.getRedenWijzigingCode().getWaarde().charValue());
        assertEquals("7112", adres.getGemeenteCode().getWaarde());
        assertEquals(20010101, adres.getDatumAanvangAdreshouding().getWaarde().intValue());
    }

    @Test
    public void wijzigPersoonAdresUitgebreid() {

        final AdresElement adres = createVerwerkingAdresElement(true, false);

        //setup
        final BijhoudingPersoon bijhoudingPersoon = maakStandaardIngeschrevene();

        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        final BijhoudingVerzoekBericht verzoekbericht = mock(BijhoudingVerzoekBericht.class);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(anyObject(), anyString())).thenReturn(bijhoudingPersoon);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.soort(AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
        ahParams.partijCode("27017");
        when(verzoekbericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("ah-id", ahParams));
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(new Partij("test partij", "000000"),
                SoortAdministratieveHandeling.VERHUIZING_INTERGEMEENTELIJK, new Timestamp(System.currentTimeMillis()));

        final RegistratieAdresActieElement actie = createRegistratieAdresActieElement(adres, verzoekbericht);

        final Persoon persoon = actie.getHoofdPersonen().get(0);
        assertEquals(1, persoon.getPersoonAdresSet().size());
        //verhuizing 1
        actie.verwerk(verzoekbericht, administratieveHandeling);
        assertEquals(1, persoon.getPersoonAdresSet().size());
        assertEquals(3, persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().size());
        final PersoonAdresHistorie
                actueelAdres =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet());
        assertNotNull(actueelAdres);
        assertNull(actueelAdres.getAangeverAdreshouding());
        assertNotNull(actueelAdres.getHuisnummer());
    }

    @Test
    public void BinnenGemeentelijkeVerhuizing() {

        final AdresElement adres = createVerwerkingAdresElement(true, true);

        //setup
        final BijhoudingPersoon bijhoudingPersoon = maakStandaardIngeschrevene();

        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        final BijhoudingVerzoekBericht verzoekbericht = mock(BijhoudingVerzoekBericht.class);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(anyObject(), anyString())).thenReturn(bijhoudingPersoon);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.soort(AdministratieveHandelingElementSoort.VERHUIZING_BINNENGEMEENTELIJK);
        ahParams.partijCode("27017");
        when(verzoekbericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("ah-id", ahParams));
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(new Partij("test partij", "000000"),
                SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK, new Timestamp(System.currentTimeMillis()));

        final RegistratieAdresActieElement actie = createRegistratieAdresActieElement(adres, verzoekbericht);

        final Persoon persoon = actie.getHoofdPersonen().get(0);
        assertEquals(1, persoon.getPersoonAdresSet().size());
        //verhuizing 1
        actie.verwerk(verzoekbericht, administratieveHandeling);
        assertEquals(1, persoon.getPersoonAdresSet().size());
        assertEquals(3, persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().size());
    }

    @Test
    public void testWijzigingAdresInfrastructureel() {

        final AdresElement adres = createVerwerkingAdresElement(true, true);

        //setup
        final BijhoudingPersoon bijhoudingPersoon = maakStandaardIngeschrevene();
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        final BijhoudingVerzoekBericht verzoekbericht = mock(BijhoudingVerzoekBericht.class);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(anyObject(), anyString())).thenReturn(bijhoudingPersoon);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.soort(AdministratieveHandelingElementSoort.WIJZIGING_ADRES_INFRASTRUCTUREEL);
        ahParams.partijCode("27017");
        when(verzoekbericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("ah-id", ahParams));
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(new Partij("test partij", "000000"),
                SoortAdministratieveHandeling.WIJZIGING_ADRES_INFRASTRUCTUREEL, new Timestamp(System.currentTimeMillis()));

        final RegistratieAdresActieElement actie = createRegistratieAdresActieElement(adres, verzoekbericht);

        final Persoon persoon = actie.getHoofdPersonen().get(0);
        assertEquals(1, persoon.getPersoonAdresSet().size());
        //verhuizing 1
        actie.verwerk(verzoekbericht, administratieveHandeling);
        assertEquals(1, persoon.getPersoonAdresSet().size());
        assertEquals(3, persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().size());
    }

    @Test
    public void testWijzigingGemeenteInfrastructureel() {

        final AdresElement adres = createVerwerkingAdresElement(false, true);

        //setup
        final BijhoudingPersoon bijhoudingPersoon = maakStandaardIngeschrevene();

        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        final BijhoudingVerzoekBericht verzoekbericht = mock(BijhoudingVerzoekBericht.class);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(anyObject(), anyString())).thenReturn(bijhoudingPersoon);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.soort(AdministratieveHandelingElementSoort.WIJZIGING_GEMEENTE_INFRASTRUCTUREEL);
        ahParams.partijCode("27017");
        when(verzoekbericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("ah-id", ahParams));
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(new Partij("test partij", "000000"),
                SoortAdministratieveHandeling.WIJZIGING_GEMEENTE_INFRASTRUCTUREEL, new Timestamp(System.currentTimeMillis()));

        final RegistratieAdresActieElement actie = createRegistratieAdresActieElement(adres, verzoekbericht);

        final Persoon persoon = actie.getHoofdPersonen().get(0);
        assertEquals(1, persoon.getPersoonAdresSet().size());
        //verhuizing 1
        actie.verwerk(verzoekbericht, administratieveHandeling);
        assertEquals(1, persoon.getPersoonAdresSet().size());
        assertEquals(3, persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().size());
        final PersoonAdresHistorie
                actueelAdres =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet());
        assertNotNull(actueelAdres);
        assertNotNull(actueelAdres.getAangeverAdreshouding());
        assertNull(actueelAdres.getHuisnummer());
    }

    private AdresElement createVerwerkingAdresElement(final boolean metHuisnummer, final boolean metAangeverAdreshouding) {
        final ElementBuilder.AdresElementParameters params = new ElementBuilder.AdresElementParameters("W", 'M', 20010101, null);
        if (metHuisnummer) {
            params.setHuisnummer(new StringElement("2"));
        }
        params.setHuisletter(new CharacterElement('L'));
        params.setAfgekorteNaamOpenbareRuimte(new StringElement("afgekort"));
        params.setHuisnummertoevoeging(new StringElement("k2"));
        params.setLocatieomschrijving(new StringElement("achterkant"));
        params.setLocatieTenOpzichteVanAdres(new StringElement("boven"));
        params.setPostcode(new StringElement("1000AA"));
        params.setGemeenteCode(new StringElement("7112"));
        params.setWoonplaatsnaam(new StringElement("Appingendam"));
        params.setNaamOpenbareRuimte(new StringElement("Kerk"));
        if (metAangeverAdreshouding) {
            params.setAangeverAdreshoudingCode(new CharacterElement('O'));
        }
        params.setDatumAanvangAdreshouding(new DatumElement(20160101));

        return builder.maakAdres("adres", params);
    }

    @Test
    public void IntergemeentelijkGemeentelijkeVerhuizingZelfdeGemeente() {

        final ElementBuilder.AdresElementParameters params = new ElementBuilder.AdresElementParameters("W", 'M', 20170101, null);
        params.setHuisnummer(new StringElement("2"));
        params.setHuisletter(new CharacterElement('L'));
        params.setAfgekorteNaamOpenbareRuimte(new StringElement("afgekort"));
        params.setHuisnummertoevoeging(new StringElement("k2"));
        params.setLocatieomschrijving(new StringElement("achterkant"));
        params.setLocatieTenOpzichteVanAdres(new StringElement("boven"));
        params.setPostcode(new StringElement("1000AA"));
        params.setWoonplaatsnaam(new StringElement("Appingendam"));
        params.setNaamOpenbareRuimte(new StringElement("Kerk"));
        params.setAangeverAdreshoudingCode(new CharacterElement('G'));
        params.setGemeenteCode(new StringElement("7119"));

        final AdresElement adres = builder.maakAdres("adres", params);

        //setup
        final BijhoudingPersoon bijhoudingPersoon = maakStandaardIngeschrevene();

        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        final BijhoudingVerzoekBericht verzoekbericht = mock(BijhoudingVerzoekBericht.class);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(anyObject(), anyString())).thenReturn(bijhoudingPersoon);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.soort(AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
        ahParams.partijCode("27017");
        final List<ActieElement> acties = new LinkedList<>();
        final RegistratieAdresActieElement actie = createRegistratieAdresActieElement(adres, verzoekbericht, 20170101);
        acties.add(actie);
        ahParams.acties(acties);
        when(verzoekbericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("ah-id", ahParams));
        //verhuizing 1
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1384, meldingen.get(0).getRegel());

    }

    @Test
    public void wijzigPersoonAdresBijNietAutomatischeBijhoudingsSituatie() {

        final ElementBuilder.AdresElementParameters params = new ElementBuilder.AdresElementParameters("W", 'M', 20010101, "0003");
        final AdresElement adres = builder.maakAdres("adres", params);

        //setup
        final BijhoudingPersoon bijhoudingPersoon = maakStandaardIngeschrevene();

        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.GBA);

        final BijhoudingVerzoekBericht verzoekbericht = mock(BijhoudingVerzoekBericht.class);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(anyObject(), anyString())).thenReturn(bijhoudingPersoon);
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(new Partij("test partij", "000000"),
                SoortAdministratieveHandeling.VERHUIZING_INTERGEMEENTELIJK, new Timestamp(System.currentTimeMillis()));

        final RegistratieAdresActieElement actie = createRegistratieAdresActieElement(adres, verzoekbericht);

        final Persoon persoon = actie.getHoofdPersonen().get(0);
        assertEquals(1, persoon.getPersoonAdresSet().size());
        //verhuizing 1
        actie.verwerk(verzoekbericht, administratieveHandeling);
        assertEquals(1, persoon.getPersoonAdresSet().size());

    }


    @Test
    public void testVerstrekkingsBeperkingen() {
        final RegistratieAdresActieElement
                actie =
                createRegistratieAdresValideerActie(true, false, LEEFTIJD_OUDER_18, INGEZETENE,
                        AdministratieveHandelingElementSoort.WIJZIGING_GEMEENTE_INFRASTRUCTUREEL);
        PersoonVerstrekkingsbeperking vb = new PersoonVerstrekkingsbeperking(bijhoudingPersoonMock);
        PersoonVerstrekkingsbeperkingHistorie vbh = new PersoonVerstrekkingsbeperkingHistorie(vb);
        vb.getPersoonVerstrekkingsbeperkingHistorieSet().add(vbh);
        vb.setGemeenteVerordening(new Partij("partij", "007112"));
        when(bijhoudingPersoonMock.getPersoonVerstrekkingsbeperkingSet()).thenReturn(new HashSet<>(Collections.singletonList(vb)));
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    @Bedrijfsregel(Regel.R2344)
    public void testVerstrekkingsBeperkingenR2344() {
        final RegistratieAdresActieElement
                actie =
                createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, INGEZETENE,
                        AdministratieveHandelingElementSoort.WIJZIGING_GEMEENTE_INFRASTRUCTUREEL);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0000")).thenReturn(new Gemeente(Short.valueOf("0003"), "test gemeente",
                "0003", new Partij("test partij", "000023")));
        PersoonVerstrekkingsbeperking vb = new PersoonVerstrekkingsbeperking(bijhoudingPersoonMock);
        PersoonVerstrekkingsbeperkingHistorie vbh = new PersoonVerstrekkingsbeperkingHistorie(vb);
        vb.getPersoonVerstrekkingsbeperkingHistorieSet().add(vbh);
        vb.setGemeenteVerordening(new Partij("partij", "001010"));
        when(bijhoudingPersoonMock.getPersoonVerstrekkingsbeperkingSet()).thenReturn(new HashSet<>(Collections.singletonList(vb)));
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2344, meldingen.get(0).getRegel());
    }

    @Test
    public void testRelatieHGPZonderPartner() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, PARTNER);
        when(bijhoudingPersoonMock.getRelaties()).thenReturn(null);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1928, meldingen.get(0).getRegel());
    }

    @Test
    public void testRelatieHGPMetPartner() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, PARTNER);
        final Relatie relatie = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        relatie.getRelatieHistorieSet().add(new RelatieHistorie(relatie));
        final Betrokkenheid partner = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final List<Betrokkenheid> partners = Collections.singletonList(partner);
        when(bijhoudingPersoonMock.getActuelePartners()).thenReturn(new HashSet<>(partners));
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testAangeverKindZonderBetrokkenheidOuder() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, KIND);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1929, meldingen.get(0).getRegel());
    }

    @Test
    public void testAangeverKindMetBetrokkenheidOuder() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, KIND);
        Betrokkenheid kindBetrokkenheidMock = mock(Betrokkenheid.class);
        when(kindBetrokkenheidMock.getPersoon()).thenReturn(bijhoudingPersoonMock);
        when(kindBetrokkenheidMock.getSoortBetrokkenheid()).thenReturn(SoortBetrokkenheid.OUDER);

        Relatie relatieMock = mock(Relatie.class);
        when(relatieMock.getSoortRelatie()).thenReturn(SoortRelatie.HUWELIJK);
        when(bijhoudingPersoonMock.getRelaties()).thenReturn(new HashSet<>(Collections.singletonList(relatieMock)));
        when(bijhoudingPersoonMock.getActueleKinderen()).thenReturn(new HashSet<>(Collections.singletonList(kindBetrokkenheidMock)));
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testAangeverOuderMetBetrokkenheidKind() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, OUDER);

        Relatie relatieMock = mock(Relatie.class);
        BetrokkenheidOuderHistorie betrokkenheidOuderHistorieMock = mock(BetrokkenheidOuderHistorie.class);
        when(betrokkenheidOuderHistorieMock.getDatumEindeGeldigheid()).thenReturn(null);
        Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatieMock);
        ouderBetrokkenheid.getBetrokkenheidOuderHistorieSet().add(betrokkenheidOuderHistorieMock);

        when(bijhoudingPersoonMock.getActueleOuders()).thenReturn(new HashSet<>(Collections.singletonList(ouderBetrokkenheid)));
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testAangeverOuderZonderBetrokkenheidKind() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, OUDER);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1930, meldingen.get(0).getRegel());
    }

    @Test
    public void testValideerPartijCodeGelijkAdresGemeente() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, INGEZETENE);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testValideerPartijCodeOngelijkAdresGemeente() {
        final RegistratieAdresActieElement
                actie =
                createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, INGEZETENE, 20010101,
                        20010101, 20000101, true, "12", AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(anyString()))
                .thenReturn(new Gemeente(Short.valueOf("0003"), "test gemeente", "0003", new Partij("test partij", "000023")));
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
    }

    @Test
    public void testValideerSpecifiekeInoudGemeenteCodeOngelijk() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, INGEZETENE);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testValideerGezaghouderCuratele() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, true, LEEFTIJD_OUDER_18, GEZAGHOUDER);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testValideerGezaghouderGeenCurateleEnTeOud() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, GEZAGHOUDER);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1931.getCode(), meldingen.get(0).getRegel().getCode());
    }

    @Test
    public void testValideerGezagHouderGeenCurateleTeJong() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, DatumUtil.vandaag(), GEZAGHOUDER);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testValideerLeeftijdJongerDan16() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, true, DatumUtil.gisteren(), INGEZETENE);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1927, meldingen.get(0).getRegel());
    }

    @Test
    public void testValideerOnderCuratele() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, true, LEEFTIJD_OUDER_18, INGEZETENE);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1927, meldingen.get(0).getRegel());
    }

    @Test
    public void testDatumAanvangGeldigheidEnAdreshoudingGelijk() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, 'I', 20010101, 20010101, 20000101,
                false, PARTIJCODE, AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumAanvangGeldigheidEnAdreshoudingOnGelijk() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, 'I', 20010101, 20020202, 20000101,
                false, PARTIJCODE, AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2500, meldingen.get(0).getRegel());
    }

    @Test
    public void testDatumAanvangAdreshoudingVoorDatumHistorieAdres() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, 'I', 20010101, 20010101, 20020101,
                false, PARTIJCODE, AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(2, meldingen.size());
        assertEquals(Regel.R2322, meldingen.get(0).getRegel());
        assertEquals(Regel.R2358, meldingen.get(1).getRegel());
    }

    @Test
    public void testDatumAanvangAdreshoudingVoorDatumHistorieAdresOp_R2322triggertNuNiet() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, 'I', 20010101, 20010101, 20010101,
                false, PARTIJCODE, AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2358, meldingen.get(0).getRegel());
    }


    @Test
    public void testDatumAanvangAdreshoudingOnbekend() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, 'I', 0, 0, 20020101,
                false, PARTIJCODE, AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumAanvangGeldigheidInDeToekomst() {
        final RegistratieAdresActieElement actie = createRegistratieAdresValideerActie(false, false, LEEFTIJD_OUDER_18, 'I', 20990101, 20990101, 20000101,
                true, PARTIJCODE, AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("17"))
                .thenReturn(new Gemeente(Short.parseShort("17"), "Groningen", "0018", new Partij("Gemeente Groningen", PARTIJCODE)));
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(anyChar())).thenReturn(new RedenWijzigingVerblijf('A', "Ambtshalve"));
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(anyString())).thenReturn(new Plaats("plaats"));
        final List<MeldingElement> meldingen = actie.valideer();
        assertEquals(2, meldingen.size());
        assertEquals(Regel.R2355, meldingen.get(0).getRegel());
        assertEquals(Regel.R2354, meldingen.get(1).getRegel());
    }

    private RegistratieAdresActieElement createRegistratieAdresValideerActie(boolean gemeenteCodeGelijk, boolean onderCuratele,
                                                                             Integer geboorteDatum, char aangeverAdresHoudingsCodeIngeschrevene) {
        return createRegistratieAdresValideerActie(gemeenteCodeGelijk, onderCuratele, geboorteDatum, aangeverAdresHoudingsCodeIngeschrevene, 20010101, 20010101,
                20000101, false, PARTIJCODE, AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
    }

    private RegistratieAdresActieElement createRegistratieAdresValideerActie(boolean gemeenteCodeGelijk, boolean onderCuratele,
                                                                             Integer geboorteDatum, char aangeverAdresHoudingsCodeIngeschrevene,
                                                                             final AdministratieveHandelingElementSoort ahSoort) {
        return createRegistratieAdresValideerActie(gemeenteCodeGelijk, onderCuratele, geboorteDatum, aangeverAdresHoudingsCodeIngeschrevene, 20010101, 20010101,
                20000101, false, PARTIJCODE, ahSoort);
    }


    private RegistratieAdresActieElement createRegistratieAdresValideerActie(boolean gemeenteCodeGelijk, boolean onderCuratele,
                                                                             Integer geboorteDatum, char aangeverAdresHoudingsCode,
                                                                             int datumAanvangGeldigheid, int datumAanvangAdreshouding,
                                                                             Integer entiteitHistorieDatumAanvangAdreshouding, boolean geldigBAG,
                                                                             final String partijCodeBericht,
                                                                             final AdministratieveHandelingElementSoort ahSoort) {
        final String gemeenteCode = "7112";
        AdresElement adres;
        if (geldigBAG) {
            adres = maakAdresElementMetGeldigeBAG(datumAanvangAdreshouding);
        } else {

            final ElementBuilder.AdresElementParameters
                    params =
                    new ElementBuilder.AdresElementParameters("W", 'M', datumAanvangAdreshouding, gemeenteCodeGelijk ? gemeenteCode : "0000");
            params.setAangeverAdreshoudingCode(new CharacterElement('I'));
            adres = builder.maakAdres("adres", params);
        }

        final BijhoudingVerzoekBericht verzoekbericht = mock(BijhoudingVerzoekBericht.class);
        when(verzoekbericht.getDatumOntvangst()).thenReturn(new DatumElement(DatumUtil.vandaag()));
        final PersoonAdres persoonAdres = mock(PersoonAdres.class);
        final Set<PersoonAdres> persoonAdressen = new HashSet<>(Collections.singletonList(persoonAdres));
        final PersoonAdresHistorie
                persoonAdresHistorie =
                new PersoonAdresHistorie(persoonAdres, SoortAdres.WOONADRES, new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "NL"),
                        new RedenWijzigingVerblijf('R', "NAAM"));
        if (entiteitHistorieDatumAanvangAdreshouding != null) {
            persoonAdresHistorie.setDatumAanvangAdreshouding(entiteitHistorieDatumAanvangAdreshouding);
            persoonAdresHistorie.setDatumAanvangGeldigheid(entiteitHistorieDatumAanvangAdreshouding);
        }
        persoonAdresHistorie.setGemeente(new Gemeente(Short.valueOf(gemeenteCode), "Appingendam", gemeenteCode, new Partij("Appingendam", PARTIJCODE)));
        final Set<PersoonAdresHistorie> persoonHistorieAdressen = new HashSet<>(Collections.singletonList(persoonAdresHistorie));

        final RegistratieAdresActieElement actie = createRegistratieAdresActieElement(adres, verzoekbericht, datumAanvangGeldigheid);

        final AdministratieveHandelingElement
                administratieveHandelingElement =
                builder.maakAdministratieveHandelingElement("ci_ah",
                        new ElementBuilder.AdministratieveHandelingParameters().soort(ahSoort)
                                .partijCode(partijCodeBericht).bronnen(Collections.emptyList()).acties(Collections.singletonList(actie)));

        final StuurgegevensElement
                stuurgegevensElement =
                builder.maakStuurgegevensElement("com_stuur",
                        new ElementBuilder.StuurgegevensParameters().zendendePartij("053001").zendendeSysteem("BRP").referentienummer("1234")
                                .tijdstipVerzending(new DatumTijdElement(ZonedDateTime.now()).toString()));
        stuurgegevensElement.setVerzoekBericht(verzoekbericht);
        when(verzoekbericht.getStuurgegevens())
                .thenReturn(stuurgegevensElement);

        if (onderCuratele) {
            Set<PersoonIndicatie>
                    persoonIndicatieSet =
                    new HashSet<>(Collections.singletonList(new PersoonIndicatie(bijhoudingPersoonMock, SoortIndicatie.ONDER_CURATELE)));
            when(bijhoudingPersoonMock.getPersoonIndicatieSet()).thenReturn(persoonIndicatieSet);
        }

        when(getDynamischeStamtabelRepository().getAangeverByCode(anyChar()))
                .thenReturn(new Aangever(aangeverAdresHoudingsCode, "naamAangever", "omschrijvingAangever"));

        when(bijhoudingPersoonMock.isPersoonIngeschrevene()).thenReturn(true);

        when(bijhoudingPersoonMock.getPersoonGeboorteHistorieSet()).thenReturn(new HashSet<>(Collections.singletonList(
                new PersoonGeboorteHistorie(bijhoudingPersoonMock, geboorteDatum, new LandOfGebied("0003", "Land")))));

        when(bijhoudingPersoonMock.getActuelePersoonAdresHistorie()).thenReturn(persoonAdresHistorie);

        when(verzoekbericht.getAdministratieveHandeling()).thenReturn(administratieveHandelingElement);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(anyObject(), anyString())).thenReturn(bijhoudingPersoonMock);

        when(bijhoudingPersoonMock.getPersoonAdresSet()).thenReturn(persoonAdressen);
        when(persoonAdres.getPersoonAdresHistorieSet()).thenReturn(persoonHistorieAdressen);
        when(bijhoudingPersoonMock.bepaalLeeftijd(anyInt())).thenReturn(DatumUtil.vandaag() - geboorteDatum);
        return actie;
    }

    private RegistratieAdresActieElement createRegistratieAdresActieElement(final AdresElement adres, final BijhoudingVerzoekBericht verzoekbericht) {
        return createRegistratieAdresActieElement(adres, verzoekbericht, 22010101);
    }


    private RegistratieAdresActieElement createRegistratieAdresActieElement(final AdresElement adres, final BijhoudingVerzoekBericht verzoekbericht,
                                                                            int datumAanvangGeldigheid) {
        ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.adres(adres);

        ElementBuilder builder = new ElementBuilder();
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("adresP", "1234", null, persoonParameters);

        persoonElement.setVerzoekBericht(verzoekbericht);

        final DatumElement aanvang = new DatumElement(datumAanvangGeldigheid);
        RegistratieAdresActieElement actie = new RegistratieAdresActieElement(attr, aanvang, null, Collections.emptyList(), persoonElement);
        actie.setVerzoekBericht(verzoekbericht);

        return actie;
    }

    private AdresElement maakAdresElementMetGeldigeBAG(final int datumAanvangAdreshouding) {
        final ElementBuilder.AdresElementParameters params = new ElementBuilder.AdresElementParameters("soort", 'p', datumAanvangAdreshouding, "17");
        params.identificatiecodeAdresseerbaarObject = new StringElement("identcodeAddr");
        params.identificatiecodeNummeraanduiding = new StringElement("identcodeNr");
        params.naamOpenbareRuimte = new StringElement("naamOpbR");
        params.huisnummer = new StringElement("1");
        params.woonplaatsnaam = new StringElement("woonplaatsnaam");
        params.afgekorteNaamOpenbareRuimte = new StringElement("kort");
        params.setRedenWijzigingCode(new CharacterElement('A'));
        return builder.maakAdres("com_adres", params);
    }

    private BijhoudingPersoon maakStandaardIngeschrevene() {
        final Persoon persoonEntiteit = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoonEntiteit);
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        final PersoonBijhoudingHistorie
                actueelBijhoudingVoorkomen =
                new PersoonBijhoudingHistorie(persoonEntiteit, new Partij("oude partij", "000000"), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        actueelBijhoudingVoorkomen.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));
        actueelBijhoudingVoorkomen.setDatumAanvangGeldigheid(20000101);
        persoonEntiteit.addPersoonBijhoudingHistorie(actueelBijhoudingVoorkomen);
        final PersoonAdres adres = new PersoonAdres(persoonEntiteit);
        final PersoonAdresHistorie
                adresHistorie =
                new PersoonAdresHistorie(adres, SoortAdres.WOONADRES, new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "NL"),
                        new RedenWijzigingVerblijf('P', "Aangifte door persoon"));
        adresHistorie.setGemeente(new Gemeente(Short.parseShort("7019"), "gemeente", "7119", new Partij("partij", "027019")));
        adresHistorie.setDatumAanvangAdreshouding(20160101);
        adres.getPersoonAdresHistorieSet().add(adresHistorie);
        persoonEntiteit.getPersoonAdresSet().add(adres);
        return bijhoudingPersoon;
    }
}
