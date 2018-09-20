/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.servicespecifiek.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import nl.brp.actie.toevoegennationaliteit.ToevoegenNationaliteit;
import nl.brp.actie.verwijderennationaliteit.VerwijderNationaliteit;
import nl.brp.actie.wijzigennationaliteit.WijzigNationaliteit;
import nl.brp.basis.BSNE;
import nl.brp.basis.DatE;
import nl.brp.basis.GemIDE;
import nl.brp.basis.NationIDE;
import nl.brp.basis.PersNationIDE;
import nl.brp.basis.SrtBRPActieIDE;
import nl.brp.basis.antwoord.StandaardAntwoord;
import nl.brp.contract.bewerkennationaliteitspecifiek.BewerkenNationaliteitSpecifiekPortType;
import nl.bzk.brp.poc.berichtenverwerker.model.Actie;
import nl.bzk.brp.poc.berichtenverwerker.model.Bron;
import nl.bzk.brp.poc.berichtenverwerker.service.PersoonEnNationaliteitService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test for the {@link BewerkenNationaliteitSpecifiekImpl} class.
 */
public class BewerkenNationaliteitSpecifiekImplTest {

    @Mock
    private PersoonEnNationaliteitService persoonEnNationaliteitServiceMock;

    /**
     * Unit test for the
     * {@link BewerkenNationaliteitSpecifiekImpl#toevoegenNationaliteitSpecifiek(ToevoegenNationaliteit)} method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void testToevoegenNationaliteitSpecifiek() {
        BewerkenNationaliteitSpecifiekImpl service = new BewerkenNationaliteitSpecifiekImpl();
        ReflectionTestUtils.setField(service, "persoonEnNationaliteitService", persoonEnNationaliteitServiceMock);

        // Initialisatie

        ToevoegenNationaliteit bericht = new ToevoegenNationaliteit();
        nl.brp.actie.toevoegennationaliteit.Actie berichtActie = new nl.brp.actie.toevoegennationaliteit.Actie();
        nl.brp.actie.toevoegennationaliteit.Pers berichtPersoon = new nl.brp.actie.toevoegennationaliteit.Pers();
        SrtBRPActieIDE actieSoortId = new SrtBRPActieIDE();
        actieSoortId.setValue(new BigInteger("5"));
        berichtActie.setSrt(actieSoortId);
        GemIDE gemeenteId = new GemIDE();
        gemeenteId.setValue(new BigInteger("3"));
        JAXBElement<GemIDE> elem = new JAXBElement<GemIDE>(new QName(""), GemIDE.class, gemeenteId);
        berichtActie.setGem(elem);
        bericht.setBRPActie(berichtActie);
        bericht.setPers(berichtPersoon);
        BSNE bsne = new BSNE();
        bsne.setValue(new BigDecimal("123456789"));
        berichtPersoon.setBSN(bsne);
        NationIDE natId = new NationIDE();
        natId.setValue(new BigInteger("2"));

        DatE datAanvGel = new DatE();
        datAanvGel.setValue(new BigDecimal("110101"));
        nl.brp.actie.toevoegennationaliteit.PersNation persNat = new nl.brp.actie.toevoegennationaliteit.PersNation();
        persNat.setNation(natId);
        persNat.setDatAanvGel(datAanvGel);
        berichtPersoon.setPersNation(persNat);

        // Service Aanroep
        StandaardAntwoord antwoord = service.toevoegenNationaliteitSpecifiek(bericht);

        // Verificatie
        ArgumentCaptor<Actie> actieCaptor = ArgumentCaptor.forClass(Actie.class);
        ArgumentCaptor<BigDecimal> bsnCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        ArgumentCaptor<Integer> nationIdCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(persoonEnNationaliteitServiceMock).toevoegenNationaliteit(actieCaptor.capture(), (Set<Bron>) any(),
                bsnCaptor.capture(), nationIdCaptor.capture(), eq(new BigDecimal("110101")));
        assertEquals("Verkeerde BSN doorgestuurd naar service", new BigDecimal("123456789"), bsnCaptor.getValue());
        assertEquals("Verkeerde Nationaliteit ID doorgestuurd naar service", new Integer(2), nationIdCaptor.getValue());
        assertNotNull(antwoord);
    }

    /**
     * Unit test for the {@link BewerkenNationaliteitSpecifiekImpl#wijzigenNationaliteitSpecifiek(WijzigNationaliteit)}
     * method.
     */
    @Test
    public final void testWijzigenNationaliteitSpecifiek() {
        BewerkenNationaliteitSpecifiekPortType service = new BewerkenNationaliteitSpecifiekImpl();

        WijzigNationaliteit updateNatParam = new WijzigNationaliteit();
        nl.brp.actie.wijzigennationaliteit.Pers pers = new nl.brp.actie.wijzigennationaliteit.Pers();
        nl.brp.actie.wijzigennationaliteit.PersNation persNat = new nl.brp.actie.wijzigennationaliteit.PersNation();
        NationIDE natId = new NationIDE();

        updateNatParam.setPers(pers);
        pers.setPersNation(persNat);
        persNat.setNation(natId);
        natId.setValue(new BigInteger("3"));

        StandaardAntwoord response = service.wijzigenNationaliteitSpecifiek(updateNatParam);
        assertNotNull(response);
    }

    /**
     * Unit test for the
     * {@link BewerkenNationaliteitSpecifiekImpl#verwijderenNationaliteitSpecifiek(VerwijderNationaliteit)} method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void testVerwijderenNationaliteitSpecifiek() {
        BewerkenNationaliteitSpecifiekImpl service = new BewerkenNationaliteitSpecifiekImpl();
        ReflectionTestUtils.setField(service, "persoonEnNationaliteitService", persoonEnNationaliteitServiceMock);

        // Initialisatie

        VerwijderNationaliteit bericht = new VerwijderNationaliteit();
        nl.brp.actie.verwijderennationaliteit.Actie berichtActie = new nl.brp.actie.verwijderennationaliteit.Actie();
        nl.brp.actie.verwijderennationaliteit.Pers berichtPersoon = new nl.brp.actie.verwijderennationaliteit.Pers();
        SrtBRPActieIDE actieSoortId = new SrtBRPActieIDE();
        actieSoortId.setValue(new BigInteger("5"));
        berichtActie.setSrt(actieSoortId);
        GemIDE gemeenteId = new GemIDE();
        gemeenteId.setValue(new BigInteger("3"));
        JAXBElement<GemIDE> elem = new JAXBElement<GemIDE>(new QName(""), GemIDE.class, gemeenteId);
        berichtActie.setGem(elem);
        bericht.setActie(berichtActie);
        bericht.setPers(berichtPersoon);
        BSNE bsne = new BSNE();
        bsne.setValue(new BigDecimal("123456789"));
        JAXBElement<BSNE> bsnelem = new JAXBElement<BSNE>(new QName(""), BSNE.class, bsne);
        berichtPersoon.setBSN(bsnelem);
        NationIDE natId = new NationIDE();
        natId.setValue(new BigInteger("2"));

        PersNationIDE persNatId = new PersNationIDE();
        persNatId.setValue(2);
        nl.brp.actie.verwijderennationaliteit.PersNation persNat =
                new nl.brp.actie.verwijderennationaliteit.PersNation();
        persNat.setId(persNatId);
        berichtPersoon.setPersNation(persNat);

        // Service Aanroep
        StandaardAntwoord antwoord = service.verwijderenNationaliteitSpecifiek(bericht);

        // Verificatie
        ArgumentCaptor<Actie> actieCaptor = ArgumentCaptor.forClass(Actie.class);
        ArgumentCaptor<Long> persNatIdCaptor = ArgumentCaptor.forClass(Long.class);

        verify(persoonEnNationaliteitServiceMock).opheffenNationaliteit(actieCaptor.capture(), (Set<Bron>) any(),
                persNatIdCaptor.capture());
        assertEquals("Verkeerde ID doorgestuurd naar service", new Long(2), persNatIdCaptor.getValue());
        assertNotNull(antwoord);
    }

    /**
     * Initializeert de mocks die in deze unit test class worden gebruikt.
     */
    @Before
    public final void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

}
