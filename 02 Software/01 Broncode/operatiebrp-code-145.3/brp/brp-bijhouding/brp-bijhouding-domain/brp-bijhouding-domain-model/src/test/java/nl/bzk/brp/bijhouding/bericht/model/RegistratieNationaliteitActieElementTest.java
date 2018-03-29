/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Unittests voor {@link RegistratieNationaliteitActieElementTest}.
 */
public class RegistratieNationaliteitActieElementTest extends AbstractActieMetGeboreneTest {

    @Mock
    private ActieElement dummyActie;
    private ElementBuilder builder;
    private RegistratieGeboreneActieElement standaardGeboreneActie;


    @Before
    public void setup() {
        builder = new ElementBuilder();
        createAdministratieveHandelingElement(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
        standaardGeboreneActie = maakActieGeboreneMetAlleenOuwkig(2012_01_01,builder);
    }

    public void createAdministratieveHandelingElement(final AdministratieveHandelingElementSoort soort) {
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.acties(Collections.singletonList(dummyActie)).soort(soort).partijCode("5100").bronnen(
                Collections.emptyList());
        final AdministratieveHandelingElement administratieveHandelingElement = builder.maakAdministratieveHandelingElement("com_ah"+soort, ahParams);
        when(getBericht().getAdministratieveHandeling()).thenReturn(administratieveHandelingElement);

    }

    @Test
    public void testSoortActie() {
        assertEquals(SoortActie.REGISTRATIE_NATIONALITEIT, maakActie("0001", false, false).getSoortActie());
    }

    @Test
    public void testPeildatum() {
        assertEquals(standaardGeboreneActie.getDatumAanvangGeldigheid().getWaarde(), maakActie("0001", false, false).getPeilDatum().getWaarde());
    }

    @Bedrijfsregel(Regel.R2643)
    @Bedrijfsregel(Regel.R2644)
    @Test
    public void testIndicatieStaatLoosAlAanwezigMaarDagErvoor(){
        final RegistratieNationaliteitActieElement actieElement = getRegistratieNationaliteitActieElement(19900101, null, true);
        final List<MeldingElement> meldingElements = actieElement.valideerSpecifiekeInhoud();
        assertEquals(0,meldingElements.size());
    }

    @Bedrijfsregel(Regel.R2644)
    @Test
    public void testIndicatieStaatLoosAlAanwezigMaarDagErNaEnDegGevuldNaDag(){
        final RegistratieNationaliteitActieElement actieElement = getRegistratieNationaliteitActieElement(20170102, 20800102, true);
        final List<MeldingElement> meldingElements = actieElement.valideerSpecifiekeInhoud();
        assertEquals(1,meldingElements.size());
        assertEquals(Regel.R2644,meldingElements.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2644)
    @Test
    public void testIndicatieStaatLoosAlAanwezigMaarDagErNaEnDegGevuldVoorDag(){
        final RegistratieNationaliteitActieElement actieElement = getRegistratieNationaliteitActieElement(20110102, 20110202, true);
        final List<MeldingElement> meldingElements = actieElement.valideerSpecifiekeInhoud();
        assertEquals(0,meldingElements.size());
    }

    @Bedrijfsregel(Regel.R2643)
    @Test
    public void testIndicatieStaatLoosAlAanwezigMaarDegErNa(){
        final RegistratieNationaliteitActieElement actieElement = getRegistratieNationaliteitActieElement(20170101, null, true);
        final List<MeldingElement> meldingElements = actieElement.valideerSpecifiekeInhoud();
        assertEquals(1,meldingElements.size());
        assertEquals(Regel.R2643,meldingElements.get(0).getRegel());
    }

    public RegistratieNationaliteitActieElement getRegistratieNationaliteitActieElement(final int dag, final Integer deg,
                                                                                        final boolean voegIndicatieStaatloosToe) {
        createAdministratieveHandelingElement(AdministratieveHandelingElementSoort.ERKENNING);
        final RegistratieNationaliteitActieElement actieElement = maakActie("0003", false, true);
        actieElement.setVerzoekBericht(getBericht());
        final BijhoudingPersoon bijhoudingsPersoon = mock(BijhoudingPersoon.class);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class,COMM_ID_KIND)).thenReturn(bijhoudingsPersoon);
        if(voegIndicatieStaatloosToe) {
            final PersoonIndicatieHistorie historie = mock(PersoonIndicatieHistorie.class);
            when(historie.getDatumAanvangGeldigheid()).thenReturn(dag);
            when(historie.getDatumEindeGeldigheid()).thenReturn(deg);
            when(bijhoudingsPersoon.getMeestRecenteIndicatie(SoortIndicatie.STAATLOOS)).thenReturn(historie);
        }
        return actieElement;
    }

    @Test
    public void testVerwerk_persoonNietVerwerkbaar() {
        final RegistratieNationaliteitActieElement actieElement = maakActie("0001", false, false);
        final PersoonElement persoon = actieElement.getPersoon();
        final BijhoudingPersoon persoonEntiteit = persoon.getPersoonEntiteit();
        persoonEntiteit.setBijhoudingSituatie(BijhoudingSituatie.AANVULLEN_EN_OPNIEUW_INDIENEN);

        final BRPActie actie = actieElement.verwerk(getBericht(), getAdministratieveHandeling());
        assertNull(actie);
        assertEquals(0, persoonEntiteit.getPersoonNationaliteitSet().size());
    }

    @Test
    public void testVerwerk_verwerkbaar() {
        final RegistratieNationaliteitActieElement actieElement = maakActie("0001", false, false);
        final PersoonElement persoon = actieElement.getPersoon();
        final BijhoudingPersoon persoonEntiteit = persoon.getPersoonEntiteit();
        persoonEntiteit.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);

        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode(anyString())).thenReturn(new Nationaliteit("NL", "0001"));

        final BRPActie actie = actieElement.verwerk(getBericht(), getAdministratieveHandeling());
        assertNotNull(actie);
        assertEquals(1, persoonEntiteit.getPersoonNationaliteitSet().size());
        final PersoonNationaliteit nationaliteit = persoonEntiteit.getPersoonNationaliteitSet().iterator().next();
        assertEquals(1, nationaliteit.getPersoonNationaliteitHistorieSet().size());
    }

    @Test
    public void testValideer() {
        mockStandaardPersoon();
        final List<MeldingElement> meldingen = koppelActiesAanAhGeboorteInNederlandEnValideerBeeindigingActie(standaardGeboreneActie, maakActie("0001", false,
                false));
        assertEquals(0, meldingen.size());
    }

    public void mockStandaardPersoon() {
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("NL", "0001"));
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(nationaliteit);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        nationaliteit.getPersoonNationaliteitHistorieSet().add(historie);
        persoon.getPersoonNationaliteitSet().add(nationaliteit);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "123456")).thenReturn(persoon);
    }

    @Bedrijfsregel(Regel.R1692)
    @Test
    public void testPersoonHeeftNieuweNationaliteitReeds() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        final String sleutel = "sleutel";
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.nationaliteiten(Collections.singletonList(builder.maakNationaliteitElement("nat", "0001", "001")));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", sleutel, null, persoonParameters);
        persoonElement.setVerzoekBericht(getBericht());
        final RegistratieNationaliteitActieElement actie =
                builder.maakRegistratieNationaliteitActieElement("actie", 2016_01_01, Collections.emptyList(), persoonElement);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_OUDER)).thenReturn(persoon);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, sleutel)).thenReturn(persoon);
        when(persoon.heeftNationaliteitAl(any(NationaliteitElement.class), any(Integer.class))).thenReturn(true);
        final List<MeldingElement> meldingen = koppelActiesAanAhGeboorteInNederlandEnValideerBeeindigingActie(standaardGeboreneActie, actie);

        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1692, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1692)
    @Test
    public void testPersoonHeeftNieuweNationaliteitNiet() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        final String sleutel = "sleutel";
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.nationaliteiten(Collections.singletonList(builder.maakNationaliteitElement("nat", "0001", "001")));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", sleutel, null, persoonParameters);
        persoonElement.setVerzoekBericht(getBericht());
        final RegistratieNationaliteitActieElement actie =
                builder.maakRegistratieNationaliteitActieElement("actie", 2016_01_01, Collections.emptyList(), persoonElement);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_OUDER)).thenReturn(persoon);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, sleutel)).thenReturn(persoon);
        when(persoon.heeftNationaliteitAl(any(NationaliteitElement.class), any(Integer.class))).thenReturn(false);
        final List<MeldingElement> meldingen = koppelActiesAanAhGeboorteInNederlandEnValideerBeeindigingActie(standaardGeboreneActie, actie);

        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2011)
    @Test
    public void testPersoonIsAlNederlander() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        final String sleutel = "sleutel";
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.nationaliteiten(Collections.singletonList(builder.maakNationaliteitElement("nat", "0001", "001")));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", sleutel, null, persoonParameters);
        persoonElement.setVerzoekBericht(getBericht());

        final RegistratieNationaliteitActieElement actie =
                builder.maakRegistratieNationaliteitActieElement("actie", 2016_01_01, Collections.emptyList(), persoonElement);

        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_OUDER)).thenReturn(persoon);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, sleutel)).thenReturn(persoon);
        when(persoon.heeftNationaliteitAl(any(NationaliteitElement.class), any(Integer.class))).thenReturn(false);
        when(persoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(any(Integer.class))).thenReturn(true);
        final List<MeldingElement> meldingen = koppelActiesAanAhGeboorteInNederlandEnValideerBeeindigingActie(standaardGeboreneActie, actie);

        controleerRegels(meldingen);
    }

    @Bedrijfsregel(Regel.R2582)
    @Test
    public void testPersoonMetKinderenWordtNederlander() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        final String sleutel = "sleutel";
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.nationaliteiten(Collections.singletonList(builder.maakNationaliteitElement("nat", "0001", "001")));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", sleutel, null, persoonParameters);
        persoonElement.setVerzoekBericht(getBericht());
        final RegistratieNationaliteitActieElement actie =
                builder.maakRegistratieNationaliteitActieElement("actie", 2016_01_01, Collections.emptyList(), persoonElement);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_OUDER)).thenReturn(persoon);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, sleutel)).thenReturn(persoon);
        when(persoon.heeftNationaliteitAl(any(NationaliteitElement.class), any(Integer.class))).thenReturn(false);
        when(persoon.getActueleKinderen()).thenReturn(Collections.singleton(new Betrokkenheid(SoortBetrokkenheid.KIND, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING))));
        when(persoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(any(Integer.class))).thenReturn(false);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideer(AdministratieveHandelingElementSoort.ERKENNING, actie);

        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2582, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R1695)
    public void testPersoonHeeftAndereNationaliteit() {
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "123456")).thenReturn(persoon);
        final List<MeldingElement> meldingen = koppelActiesAanAhGeboorteInNederlandEnValideerBeeindigingActie(standaardGeboreneActie, maakActie("0001", false,
                false));
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1695, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R1695)
    public void testPersoonHeeftAndereNationaliteitMetBronDocument() {
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "123456")).thenReturn(persoon);
        final List<MeldingElement> meldingen = koppelActiesAanAhGeboorteInNederlandEnValideerBeeindigingActie(standaardGeboreneActie, maakActie("0001", true,
                false));
        assertEquals(0, meldingen.size());
    }


    @Test
    @Bedrijfsregel(Regel.R1707)
    public void testNationaliteitsCode_nietMeerGeldig() {
        final Nationaliteit ongeldigeNationaliteit = new Nationaliteit("OngeldigNat", "0022");
        ongeldigeNationaliteit.setDatumAanvangGeldigheid(20150101);
        ongeldigeNationaliteit.setDatumEindeGeldigheid(20151212);
        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode(anyString())).thenReturn(ongeldigeNationaliteit);

        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("NL", "0001"));
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(nationaliteit);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        nationaliteit.getPersoonNationaliteitHistorieSet().add(historie);

        persoon.getPersoonNationaliteitSet().add(nationaliteit);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "123456")).thenReturn(persoon);
        final List<MeldingElement> meldingen = koppelActiesAanAhGeboorteInNederlandEnValideerBeeindigingActie(standaardGeboreneActie, maakActie("0001", false,
                false));
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1707, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R1708)
    public void testRedenVerkrijgingCode_nietMeerGeldig() {
        final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit = new RedenVerkrijgingNLNationaliteit("001", "omschrijving");
        redenVerkrijgingNLNationaliteit.setDatumAanvangGeldigheid(20150101);
        redenVerkrijgingNLNationaliteit.setDatumEindeGeldigheid(20151212);
        when(getDynamischeStamtabelRepository().getRedenVerkrijgingNLNationaliteitByCode(anyString())).thenReturn(redenVerkrijgingNLNationaliteit);

        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("NL", "0001"));
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(nationaliteit);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        nationaliteit.getPersoonNationaliteitHistorieSet().add(historie);

        persoon.getPersoonNationaliteitSet().add(nationaliteit);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "123456")).thenReturn(persoon);
        final List<MeldingElement> meldingen = koppelActiesAanAhGeboorteInNederlandEnValideerBeeindigingActie(standaardGeboreneActie, maakActie("0001", false,
                false));
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1708, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R2544)
    public void testRedenVerkrijgingBijNietNederlandseNationaliteit() {
        final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit = new RedenVerkrijgingNLNationaliteit("001", "omschrijving");
        redenVerkrijgingNLNationaliteit.setDatumAanvangGeldigheid(20150101);
        when(getDynamischeStamtabelRepository().getRedenVerkrijgingNLNationaliteitByCode(anyString())).thenReturn(redenVerkrijgingNLNationaliteit);

        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("NL", "0022"));
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(nationaliteit);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        nationaliteit.getPersoonNationaliteitHistorieSet().add(historie);

        persoon.getPersoonNationaliteitSet().add(nationaliteit);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_OUDER)).thenReturn(persoon);
        final List<MeldingElement> meldingen = koppelActiesAanAhGeboorteInNederlandEnValideerBeeindigingActie(standaardGeboreneActie, maakActie("0022", false,
                false));
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2544, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2488)
    @Test
    public void testDatumAanvangGeldigheid_gelijkAanGeborene() {
        mockStandaardPersoon();
        final int geboorteDatum = 2012_01_02;
        final RegistratieGeboreneActieElement geboreneActie = maakActieGeboreneMetAlleenOuwkig(geboorteDatum,builder);
        final RegistratieNationaliteitActieElement nationaliteitActie = maakActie(geboreneActie, "0001", geboorteDatum, false);
        final List<MeldingElement> meldingen = koppelActiesAanAhErkenningNaGeboortedatumEnValideerBeeindigingActie(geboreneActie, nationaliteitActie);
        assertTrue(meldingen.isEmpty());
    }

    private List<MeldingElement> koppelActiesAanAhGeboorteInNederlandEnValideerBeeindigingActie(final ActieElement... acties) {
        return koppelActiesAanAhEnValideer(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM, acties);
    }

    private List<MeldingElement> koppelActiesAanAhErkenningNaGeboortedatumEnValideerBeeindigingActie(final ActieElement... acties) {
        return koppelActiesAanAhEnValideer(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND, acties);
    }

    private List<MeldingElement> koppelActiesAanAhEnValideer(final AdministratieveHandelingElementSoort soortAH, final ActieElement... acties) {
        RegistratieNationaliteitActieElement testActie = null;
        final List<ActieElement> actiesVoorAh = new LinkedList<>();
        for (final ActieElement actie : acties) {
            actiesVoorAh.add(actie);
            if (actie instanceof RegistratieNationaliteitActieElement) {
                testActie = (RegistratieNationaliteitActieElement) actie;
            }
        }

        final ElementBuilder.AdministratieveHandelingParameters ahPara = new ElementBuilder.AdministratieveHandelingParameters();
        ahPara.partijCode(Z_PARTIJ.getCode());
        ahPara.soort(soortAH);
        ahPara.acties(actiesVoorAh);

        when(getBericht().getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("comah", ahPara));

        if (testActie == null) {
            throw new AssertionError("de te testen actie niet meegegeven");
        }
        testActie.setVerzoekBericht(getBericht());
        return testActie.valideerSpecifiekeInhoud();
    }

    private RegistratieNationaliteitActieElement maakActie(final String nationaliteitsCode, final boolean voegBronToe, final boolean persoonIsObjectSleutel) {
        return maakActie(standaardGeboreneActie, nationaliteitsCode, standaardGeboreneActie.getDatumAanvangGeldigheid().getWaarde(), voegBronToe, 1, persoonIsObjectSleutel);
    }

    private RegistratieNationaliteitActieElement maakActie(final String nationaliteitsCode, final boolean voegBronToe, final int volgNummer) {
        return maakActie(standaardGeboreneActie, nationaliteitsCode, standaardGeboreneActie.getDatumAanvangGeldigheid().getWaarde(), voegBronToe, volgNummer,
                false);
    }

    private RegistratieNationaliteitActieElement maakActie(final RegistratieGeboreneActieElement geboreneActie, final String nationaliteitsCode,
                                                           final int datumAanvangGeldigheid, final boolean voegBronToe) {
        return maakActie(geboreneActie, nationaliteitsCode, datumAanvangGeldigheid, voegBronToe, 1, false);
    }

    private RegistratieNationaliteitActieElement maakActie(final RegistratieGeboreneActieElement geboreneActie, final String nationaliteitsCode,
                                                           final int datumAanvangGeldigheid, final boolean voegBronToe, final int volgNummer,
                                                           final boolean persoonIsObjectSleutel) {

        final PersoonElement inschrijvingKind =
                geboreneActie.getFamilierechtelijkeBetrekking().getBetrokkenheidElementen(BetrokkenheidElementSoort.KIND).get(0).getPersoon();

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final NationaliteitElement nationaliteit = builder.maakNationaliteitElement("nat" + volgNummer, nationaliteitsCode, "000");
        persoonParams.nationaliteiten(Collections.singletonList(nationaliteit));
        final PersoonGegevensElement persoonElement;
        if(persoonIsObjectSleutel) {
             persoonElement = builder.maakPersoonGegevensElement("persoon" + volgNummer, COMM_ID_KIND,null, persoonParams);
        }else{
            persoonElement = builder.maakPersoonGegevensElement("persoon" + volgNummer, null, COMM_ID_KIND, persoonParams);
        }
        persoonElement.setVerzoekBericht(getBericht());
        final Map<String, BmrGroep> referenties = new HashMap<>();
        referenties.put(COMM_ID_KIND, inschrijvingKind);
        persoonElement.initialiseer(referenties);
        if (voegBronToe) {
            final List<BronReferentieElement> bronnen = new LinkedList<>();
            bronnen.add(builder.maakBronReferentieElement("bron_ref" + volgNummer, "bref"));
            return builder.maakRegistratieNationaliteitActieElement("nat_actie" + volgNummer, datumAanvangGeldigheid, bronnen, persoonElement);
        } else {
            return builder.maakRegistratieNationaliteitActieElement("nat_actie" + volgNummer, datumAanvangGeldigheid, persoonElement);
        }
    }
}
