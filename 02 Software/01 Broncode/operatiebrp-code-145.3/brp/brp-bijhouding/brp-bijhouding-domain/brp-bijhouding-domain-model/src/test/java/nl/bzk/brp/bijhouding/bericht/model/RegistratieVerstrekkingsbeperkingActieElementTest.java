/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * UT voor {@link RegistratieVerstrekkingsbeperkingActieElement}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistratieVerstrekkingsbeperkingActieElementTest extends AbstractElementTest {

    private static final DatumElement VANDAAG = new DatumElement(DatumUtil.vandaag());
    @Mock
    BijhoudingVerzoekBericht bericht;


    private ElementBuilder elementBuilder;
    private Map<String, String> attr;
    private AdministratieveHandeling administratieveHandeling;

    private BijhoudingPersoon bijhoudingPersoon;
    private AdministratieveHandelingElement administratieveHandelingElement;
    RegistratieVerstrekkingsbeperkingActieElement actie;


    @Before
    public void setup() {
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(anyChar())).thenReturn(new RedenWijzigingVerblijf('P', "Persoonlijk"));
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(anyChar())).thenReturn(new RedenWijzigingVerblijf('A', "Ambtshalve"));
        when(getDynamischeStamtabelRepository().getAangeverByCode(anyChar())).thenReturn(new Aangever('C', "Aangever", "test aangever"));
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(anyString())).thenReturn(new Plaats("plaats"));
        init();
        bijhoudingPersoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));
        bijhoudingPersoon.getPersoonIndicatieSet().add(new PersoonIndicatie(bijhoudingPersoon, SoortIndicatie.ONDER_CURATELE));

        when(bericht.getEntiteitVoorObjectSleutel(any(), any())).thenReturn(bijhoudingPersoon);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandelingElement);
        when(getDynamischeStamtabelRepository().getPartijByCode("000012")).thenReturn(new Partij("Partij12", "000012"));
        final Partij partij = new Partij("Partij5100", "005100");
        partij.setDatumIngang(20160101);
        partij.setDatumEinde(20160202);
        when(getDynamischeStamtabelRepository().getPartijByCode("5100")).thenReturn(partij);

    }

    private void init() {
        elementBuilder = new ElementBuilder();
        attr = new LinkedHashMap<>();
        attr.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        attr.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test");

        administratieveHandeling =
                new AdministratieveHandeling(new Partij("test partij", "000000"), SoortAdministratieveHandeling.VERHUIZING_INTERGEMEENTELIJK, new Timestamp(
                        System.currentTimeMillis()));
        final List<ActieElement> acties = new ArrayList<>();
        acties.add(maakActieRegAdres());

        administratieveHandelingElement =
                elementBuilder.maakAdministratieveHandelingElement("com_ah", new ElementBuilder.AdministratieveHandelingParameters().acties(acties)
                        .soort(AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK).partijCode("5100").bronnen(Collections.EMPTY_LIST));

        administratieveHandelingElement.setVerzoekBericht(bericht);
    }

    @Test
    public void testPeilDatum() {
        actie = maakActieVestrekkingsBeperking(true, false, false);
        assertEquals(VANDAAG, actie.getPeilDatum());
    }

    @Test
    public void testVerwerkIndicatieVolledig() {
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        assertTrue(bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().isEmpty());

        //Eerste keer
        actie = maakActieVestrekkingsBeperking(true, false, false);
        actie.verwerk(bericht, administratieveHandeling);
        assertEquals(1, bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING).getPersoonIndicatieHistorieSet().size());
        assertEquals(0, bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().size());

        //Tweede keer
        init();
        final RegistratieVerstrekkingsbeperkingActieElement actie2 = maakActieVestrekkingsBeperking(true, false, false);
        actie2.verwerk(bericht, administratieveHandeling);
        final PersoonIndicatie indicatie = bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING);
        int count = 0;
        for (PersoonIndicatieHistorie his : indicatie.getPersoonIndicatieHistorieSet()) {
            if (his.getActieVerval() != null) {
                count++;
            }
        }
        assertEquals(1, count);
        assertEquals(2, indicatie.getPersoonIndicatieHistorieSet().size());
        assertEquals(0, bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().size());
    }

    @Test
    public void testVerwerkBeperkingDaarnaIndicatieVolledig() {
        actie = maakActieVestrekkingsBeperking(false, true, false);
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        assertTrue(bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().isEmpty());

        //Eerste keer beperking
        final BRPActie brpActie = actie.verwerk(bericht, administratieveHandeling);
        assertNotNull(brpActie);

        assertNull(bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING));
        assertEquals(1, bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().size());
        assertEquals(1, bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().iterator().next().getPersoonVerstrekkingsbeperkingHistorieSet().size());
        for (PersoonVerstrekkingsbeperking beperking : bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet()) {
            assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(beperking.getPersoonVerstrekkingsbeperkingHistorieSet()));

        }
        //Tweede keer indicatie volledig
        init();
        actie = maakActieVestrekkingsBeperking(true, false, false);
        actie.verwerk(bericht, administratieveHandeling);
        assertNotNull(bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING));
        assertEquals(1, bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().size());
        for (PersoonVerstrekkingsbeperking beperking : bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet()) {
            assertNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(beperking.getPersoonVerstrekkingsbeperkingHistorieSet()));
            assertEquals(SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING,
                    beperking.getPersoonVerstrekkingsbeperkingHistorieSet().iterator().next().getActieVerval().getSoortActie());
            assertEquals(administratieveHandeling.getDatumTijdRegistratie(),
                    beperking.getPersoonVerstrekkingsbeperkingHistorieSet().iterator().next().getDatumTijdVerval());
        }

    }

    @Test
    public void testVerwerkIndicatieVolledigDaarnaSpecifiekeBeperking() {
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        assertTrue(bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().isEmpty());

        //Eerste keer indicatie volledig

        actie = maakActieVestrekkingsBeperking(true, false, false);
        actie.verwerk(bericht, administratieveHandeling);
        assertNotNull(bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING));
        assertEquals(0, bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().size());

        //Tweede keer beperking
        init();
        actie = maakActieVestrekkingsBeperking(false, true, false);
        final BRPActie brpActie = actie.verwerk(bericht, administratieveHandeling);
        final PersoonIndicatie indicatie = bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING);
        assertNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(indicatie.getPersoonIndicatieHistorieSet()));
        assertEquals(SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING,
                indicatie.getPersoonIndicatieHistorieSet().iterator().next().getActieVerval().getSoortActie());
        assertEquals(administratieveHandeling.getDatumTijdRegistratie(), indicatie.getPersoonIndicatieHistorieSet().iterator().next().getDatumTijdVerval());
    }


    @Test
    public void testVerwerkVerstrekkingsBeperking() throws InterruptedException {
        actie = maakActieVestrekkingsBeperking(false, true, false);
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        assertTrue(bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().isEmpty());

        final BRPActie brpActie = actie.verwerk(bericht, administratieveHandeling);
        assertNotNull(brpActie);
        assertEquals(SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING, brpActie.getSoortActie());

        //Eerste keer
        assertNull(bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING));
        assertEquals(1, bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().size());
        assertEquals(1, bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().iterator().next().getPersoonVerstrekkingsbeperkingHistorieSet().size());

        //Tweede keer
        init();
        actie = maakActieVestrekkingsBeperking(false, true, false);
        actie.verwerk(bericht, administratieveHandeling);
        assertEquals(1, bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().size());
        assertEquals(2, bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().iterator().next().getPersoonVerstrekkingsbeperkingHistorieSet().size());

        //Derde keer
        init();
        actie = maakActieVestrekkingsBeperking(false, true, true);
        actie.verwerk(bericht, administratieveHandeling);
        assertEquals(2, bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().size());
    }

    @Test
    public void testIndicatieLeegVerstrekkingsbeperkingLeeg() {
        actie = maakActieVestrekkingsBeperking(false, false, true);
        List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2518, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R2769)
    public void testVervalVolledigeVerstrekkingsBeperking() {
        final PersoonIndicatie persoonIndicatie = new PersoonIndicatie(bijhoudingPersoon, SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING);
        persoonIndicatie.getPersoonIndicatieHistorieSet().add(new PersoonIndicatieHistorie(persoonIndicatie, true));
        bijhoudingPersoon.getPersoonIndicatieSet().add(persoonIndicatie);
        actie = maakActieVestrekkingsBeperking(false, true, false);
        controleerRegels(actie.valideerSpecifiekeInhoud(), Regel.R2769);
    }

    @Test
    @Bedrijfsregel(Regel.R2770)
    public void testVervalSpecifiekeVerstrekkingsBeperking() {
        final PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking = new PersoonVerstrekkingsbeperking(bijhoudingPersoon);
        bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().add(persoonVerstrekkingsbeperking);
        actie = maakActieVestrekkingsBeperking(true, false, false);
        controleerRegels(actie.valideerSpecifiekeInhoud(), Regel.R2770);
    }

    @Test
    public void testVerwerkActieNull() {
        actie = maakActieVestrekkingsBeperking(true, false, false);
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.OPGESCHORT);
        final BRPActie brpActie = actie.verwerk(bericht, administratieveHandeling);
        assertNull(brpActie);
    }

    private RegistratieAdresActieElement maakActieRegAdres() {
        ElementBuilder.AdresElementParameters params = new ElementBuilder.AdresElementParameters("soort", 'A', DatumUtil.vandaag(), "17");
        params.setLocatieomschrijving(new StringElement("Een precies geldige locOmschrijving"));

        AdresElement adres = elementBuilder.maakAdres("com_add", params);
        final PersoonGegevensElement
                persoonElementRA =
                elementBuilder.maakPersoonGegevensElement("ci_persoon_ra", "object_ra", null, new ElementBuilder.PersoonParameters().adres
                        (adres));

        final RegistratieAdresActieElement result = new RegistratieAdresActieElement(attr, VANDAAG, null, Collections.emptyList(), persoonElementRA);
        result.setVerzoekBericht(bericht);
        return result;
    }

    //
    /*sonar: TS tussen acties moeten verschillen*/
    private RegistratieVerstrekkingsbeperkingActieElement maakActieVestrekkingsBeperking(final boolean indicatieVolledig,
                                                                                         final boolean verstrekkingsBeperking, final boolean alleenPartij) {
        try {
            //bij het uitvoeren van acties willen we dat er zeker tijd zit tussen de vorige keer dat een actie gemaakt is.
            // bij formele historie worden acties met dezelfde tsreg als dezelfde gezien.
            TimeUnit.MILLISECONDS.sleep(10);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        if (verstrekkingsBeperking) {
            persoonParameters.verstrekkingsbeperking(maakVerstrekkingsBeperkingElement(alleenPartij));
        }
        if (indicatieVolledig) {
            persoonParameters.indicaties(maakIndicatie());
        }
        PersoonGegevensElement persoon = elementBuilder.maakPersoonGegevensElement("ci_persoon", "object", null, persoonParameters);
        RegistratieVerstrekkingsbeperkingActieElement
                actieVB = new RegistratieVerstrekkingsbeperkingActieElement(attr, VANDAAG, null, Collections.emptyList(), persoon);
        persoon.setVerzoekBericht(bericht);
        actieVB.setVerzoekBericht(bericht);
        return actieVB;
    }

    private List<VerstrekkingsbeperkingElement> maakVerstrekkingsBeperkingElement(boolean alleenPartij) {
        final ElementBuilder.VerstrekkingsbeperkingParameters params = new ElementBuilder.VerstrekkingsbeperkingParameters();
        if (alleenPartij) {
            params.partijCode("5100");
        } else {
            params.gemeenteVerordeningPartijCodes("000012").omschrijvingDerde("oms");
        }
        return Collections.singletonList(elementBuilder.maakVerstrekkingsbeperkingElement("com_vers", params));
    }

    private List<IndicatieElement> maakIndicatie() {
        return Collections.singletonList(elementBuilder.maakVolledigeVerstrekkingsbeperkingIndicatieElement("com_id", new ElementBuilder
                .IndicatieElementParameters().heeftIndicatie(Boolean.TRUE)));
    }
}
