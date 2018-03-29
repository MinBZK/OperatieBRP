/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * UT voor {@link VerstrekkingsbeperkingElement}.
 */
public class VerstrekkingsbeperkingElementTest extends AbstractElementTest {
    private ElementBuilder builder;
    private ElementBuilder.VerstrekkingsbeperkingParameters verstrekkingsbeperkingParameters;


    @Before
    public void setup() {
        builder = new ElementBuilder();
        verstrekkingsbeperkingParameters = new ElementBuilder.VerstrekkingsbeperkingParameters();
    }

    @Test
    public void controleerOmschrijvingDerdeGeldig() {
        final Gemeente gemeente = new Gemeente(Short.parseShort("1"), "Gemeente",  "1234", new Partij("partij", "001234"));
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode("1234")).thenReturn(gemeente);
        verstrekkingsbeperkingParameters.omschrijvingDerde("oms");
        verstrekkingsbeperkingParameters.gemeenteVerordeningPartijCodes("1234");
        final VerstrekkingsbeperkingElement verstrekkingbseperkingElement = maakVerstrekkingsbeperkingElement(20160101);
        final List<MeldingElement> meldingen = verstrekkingbseperkingElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }


    @Test
    public void controleerOmschrijvingDerdeOngeldig() {
        final VerstrekkingsbeperkingElement
                verstrekkingsbeperkingElement =
                builder.maakVerstrekkingsbeperkingElement("com_verstr", verstrekkingsbeperkingParameters);
        final List<MeldingElement> meldingen = verstrekkingsbeperkingElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1907, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerOmschrijvingDerdeOnGeldigGemVoorDerdeNull() {
        verstrekkingsbeperkingParameters.omschrijvingDerde("oms");
        final VerstrekkingsbeperkingElement
                verstrekkingbseperkingElement =
                builder.maakVerstrekkingsbeperkingElement("com_verstr", verstrekkingsbeperkingParameters);
        final List<MeldingElement> meldingen = verstrekkingbseperkingElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1908, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerPartijNull() {
        verstrekkingsbeperkingParameters.partijCode("1234");
        final VerstrekkingsbeperkingElement
                verstrekkingbseperkingElement =
                builder.maakVerstrekkingsbeperkingElement("com_verstr", verstrekkingsbeperkingParameters);
        final List<MeldingElement> meldingen = verstrekkingbseperkingElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2256, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerPartijOngeldig() {
        final Partij partij = new Partij("ongeldig", "001234");
        partij.setIndicatieVerstrekkingsbeperkingMogelijk(Boolean.FALSE);
        when(getDynamischeStamtabelRepository().getPartijByCode("1234")).thenReturn(partij);
        verstrekkingsbeperkingParameters.partijCode("1234");
        final VerstrekkingsbeperkingElement
                verstrekkingbseperkingElement =
                builder.maakVerstrekkingsbeperkingElement("com_verstr", verstrekkingsbeperkingParameters);
        final List<MeldingElement> meldingen = verstrekkingbseperkingElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1911, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerPartijGeldig() {
        final Partij partij = new Partij("ongeldig", "001234");
        partij.setIndicatieVerstrekkingsbeperkingMogelijk(Boolean.TRUE);
        when(getDynamischeStamtabelRepository().getPartijByCode("1234")).thenReturn(partij);
        verstrekkingsbeperkingParameters.partijCode("1234");
        final VerstrekkingsbeperkingElement verstrekkingbseperkingElement = maakVerstrekkingsbeperkingElement(20160101);
        final List<MeldingElement> meldingen = verstrekkingbseperkingElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void controleerPartijNietMeerGeldig() {
        final Partij partij = new Partij("ongeldig", "001234");
        partij.setIndicatieVerstrekkingsbeperkingMogelijk(Boolean.TRUE);
        partij.setDatumIngang(20150101);
        partij.setDatumEinde(20160101);
        when(getDynamischeStamtabelRepository().getPartijByCode("1234")).thenReturn(partij);
        verstrekkingsbeperkingParameters.partijCode("1234");
        final VerstrekkingsbeperkingElement verstrekkingbseperkingElement = maakVerstrekkingsbeperkingElement(20160101);
        final List<MeldingElement> meldingen = verstrekkingbseperkingElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2253, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerGemeenteVerordeningPartijCodesOnGeldig() {
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("1234")).thenReturn(null);
        verstrekkingsbeperkingParameters.gemeenteVerordeningPartijCodes("1234");
        verstrekkingsbeperkingParameters.omschrijvingDerde("oms");
        final VerstrekkingsbeperkingElement
                verstrekkingbseperkingElement =
                builder.maakVerstrekkingsbeperkingElement("com_verstr", verstrekkingsbeperkingParameters);
        final List<MeldingElement> meldingen = verstrekkingbseperkingElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2257, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerGemeenteVerordeningPartijCodesNietMeerGeldig() {
        Gemeente gemeenteMock = mock(Gemeente.class);
        Partij partijMock = mock(Partij.class);
        when(gemeenteMock.getPartij()).thenReturn(partijMock);
        when(partijMock.getDatumIngang()).thenReturn(DatumUtil.vanXsdDatumNaarInteger("2000-01-01"));
        when(partijMock.getDatumEinde()).thenReturn(DatumUtil.vanXsdDatumNaarInteger("2000-01-02"));
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString())).thenReturn(gemeenteMock);
        verstrekkingsbeperkingParameters.gemeenteVerordeningPartijCodes("1234");
        verstrekkingsbeperkingParameters.omschrijvingDerde("oms");

        final VerstrekkingsbeperkingElement verstrekkingbseperkingElement = maakVerstrekkingsbeperkingElement(20160101);

        final List<MeldingElement> meldingen = verstrekkingbseperkingElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2255, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerOmschrijvingDerdeOngeldigPartijNotNull() {
        final Gemeente gemeente = new Gemeente(Short.parseShort("1"), "Gemeente",  "1234", new Partij("partij", "001234"));
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode("1234")).thenReturn(gemeente);
        verstrekkingsbeperkingParameters.gemeenteVerordeningPartijCodes("1234");
        final Partij partij = new Partij("Geldig", "001234");
        partij.setIndicatieVerstrekkingsbeperkingMogelijk(Boolean.TRUE);
        when(getDynamischeStamtabelRepository().getPartijByCode("1234")).thenReturn(partij);
        verstrekkingsbeperkingParameters.partijCode("1234");
        verstrekkingsbeperkingParameters.omschrijvingDerde("oms");
        final VerstrekkingsbeperkingElement verstrekkingbseperkingElement = maakVerstrekkingsbeperkingElement(20160101);
        final List<MeldingElement> meldingen = verstrekkingbseperkingElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2418, meldingen.get(0).getRegel());
    }


    @Test
    public void controleerGemeenteVerordeningPartijCodesOnGeldigMetOms() {
        final Gemeente gemeente = new Gemeente(Short.parseShort("1"), "Gemeente",  "1234", new Partij("partij", "001234"));
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode("1234")).thenReturn(gemeente);
        verstrekkingsbeperkingParameters.gemeenteVerordeningPartijCodes("1234");
        final Partij partij = new Partij("Geldig", "001234");
        partij.setIndicatieVerstrekkingsbeperkingMogelijk(Boolean.TRUE);
        when(getDynamischeStamtabelRepository().getPartijByCode("1234")).thenReturn(partij);
        verstrekkingsbeperkingParameters.partijCode("1234");
        final VerstrekkingsbeperkingElement verstrekkingbseperkingElement = maakVerstrekkingsbeperkingElement(20160101);
        final List<MeldingElement> meldingen = verstrekkingbseperkingElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2419, meldingen.get(0).getRegel());
    }

    private VerstrekkingsbeperkingElement maakVerstrekkingsbeperkingElement(final int datumAanvangAdresHouding) {
        final AdresElement
                adresElement =
                builder.maakAdres("com_adres", new ElementBuilder.AdresElementParameters("C", 'P', datumAanvangAdresHouding, "Gemeente"));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("com_persoon", "123456", null, new ElementBuilder.PersoonParameters().adres(adresElement));

        RegistratieAdresActieElement
                registratieAdresActieElement =
                builder.maakRegistratieAdresActieElement("com_registratie_adres", 20160101, persoonElement);

        final AdministratieveHandelingElement
                ahElement =
                builder.maakAdministratieveHandelingElement("ci_ah",
                        new ElementBuilder.AdministratieveHandelingParameters().soort(AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK)
                                .partijCode("053001").toelichtingOntlening("Test toelichting op de ontlening").bronnen(Collections.emptyList()).acties(
                                Collections.singletonList(registratieAdresActieElement)));

        BijhoudingVerzoekBericht verzoekBerichtMock = mock(BijhoudingVerzoekBericht.class);
        registratieAdresActieElement.setVerzoekBericht(verzoekBerichtMock);
        when(verzoekBerichtMock.getAdministratieveHandeling()).thenReturn(ahElement);

        final VerstrekkingsbeperkingElement
                verstrekkingbseperkingElement =
                builder.maakVerstrekkingsbeperkingElement("com_verstr", verstrekkingsbeperkingParameters);
        verstrekkingbseperkingElement.setVerzoekBericht(verzoekBerichtMock);
        return verstrekkingbseperkingElement;
    }
}
