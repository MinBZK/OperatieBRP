/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.servicegeneriek.service;

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
import nl.brp.actie.verwijderennationaliteit.VerwijderenNationaliteit;
import nl.brp.basis.BSNE;
import nl.brp.basis.DatE;
import nl.brp.basis.GemIDE;
import nl.brp.basis.NationIDE;
import nl.brp.basis.PersNationIDE;
import nl.brp.basis.SrtBRPActieIDE;
import nl.brp.basis.antwoord.StandaardAntwoord;
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
 * Unit test for the {@link BewerkenNationaliteitGeneriekImpl} class.
 */
public class BewerkenNationaliteitGeneriekImplTest {

    @Mock
    private PersoonEnNationaliteitService persoonEnNationaliteitServiceMock;

    /**
     * Unit test for the
     * {@link BewerkenNationaliteitGeneriekImpl#toevoegenNationaliteitGeneriek(ToevoegenNationaliteit)} method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void testToevoegenNationaliteitGeneriek() {
        BewerkenNationaliteitGeneriekImpl service = new BewerkenNationaliteitGeneriekImpl();
        ReflectionTestUtils.setField(service, "persoonEnNationaliteitService", persoonEnNationaliteitServiceMock);

        // Initialisatie
        ToevoegenNationaliteit bericht = new ToevoegenNationaliteit();
        nl.brp.basis.nationaliteit.Actie berichtActie = new nl.brp.basis.nationaliteit.Actie();
        nl.brp.actie.toevoegennationaliteit.PersToevoegenNationaliteit berichtPersoon =
                new nl.brp.actie.toevoegennationaliteit.PersToevoegenNationaliteit();

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
        berichtPersoon.setBSN(bsne);
        NationIDE natId = new NationIDE();
        natId.setValue(new BigInteger("2"));
        JAXBElement<NationIDE> elemNatId = new JAXBElement<NationIDE>(new QName(""), NationIDE.class, natId);

        DatE datAanvGel = new DatE();
        datAanvGel.setValue(new BigDecimal("110101"));
        JAXBElement<DatE> elemDatAanvGel = new JAXBElement<DatE>(new QName(""), DatE.class, datAanvGel);

        nl.brp.actie.toevoegennationaliteit.PersNationToevoegenNationaliteit persNat =
                new nl.brp.actie.toevoegennationaliteit.PersNationToevoegenNationaliteit();
        persNat.setNation(elemNatId);
        persNat.setDatAanvGel(elemDatAanvGel);
        berichtPersoon.setPersNation(persNat);

        // Service Aanroep
        StandaardAntwoord antwoord = service.toevoegenNationaliteitGeneriek(bericht);

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
     * Unit test for the
     * {@link BewerkenNationaliteitGeneriekImpl#verwijderenNationaliteitGeneriek(VerwijderNationaliteit)} method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void testVerwijderenNationaliteitGeneriek() {
        BewerkenNationaliteitGeneriekImpl service = new BewerkenNationaliteitGeneriekImpl();
        ReflectionTestUtils.setField(service, "persoonEnNationaliteitService", persoonEnNationaliteitServiceMock);

        // Initialisatie

        VerwijderenNationaliteit bericht = new VerwijderenNationaliteit();
        nl.brp.basis.nationaliteit.Actie berichtActie = new nl.brp.basis.nationaliteit.Actie();
        nl.brp.actie.verwijderennationaliteit.PersVerwijderenNationaliteit berichtPersoon =
                new nl.brp.actie.verwijderennationaliteit.PersVerwijderenNationaliteit();
        SrtBRPActieIDE actieSoortId = new SrtBRPActieIDE();
        actieSoortId.setValue(new BigInteger("5"));
        berichtActie.setSrt(actieSoortId);
        GemIDE gemeenteId = new GemIDE();
        gemeenteId.setValue(new BigInteger("3"));
        JAXBElement<GemIDE> elem = new JAXBElement<GemIDE>(new QName(""), GemIDE.class, gemeenteId);
        berichtActie.setGem(elem);
        bericht.setActie(berichtActie);

        BSNE bsne = new BSNE();
        bsne.setValue(new BigDecimal("123456789"));
        berichtPersoon.setBSN(bsne);
        bericht.setPers(berichtPersoon);

        PersNationIDE persNatId = new PersNationIDE();
        persNatId.setValue(2);
        JAXBElement<PersNationIDE> elemPersNatId =
                new JAXBElement<PersNationIDE>(new QName(""), PersNationIDE.class, persNatId);
        nl.brp.actie.verwijderennationaliteit.PersNationVerwijderenNationaliteit persNat =
                new nl.brp.actie.verwijderennationaliteit.PersNationVerwijderenNationaliteit();
        persNat.setID(elemPersNatId);
        berichtPersoon.setPersNation(persNat);

        // Service Aanroep
        StandaardAntwoord antwoord = service.verwijderenNationaliteitGeneriek(bericht);

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
