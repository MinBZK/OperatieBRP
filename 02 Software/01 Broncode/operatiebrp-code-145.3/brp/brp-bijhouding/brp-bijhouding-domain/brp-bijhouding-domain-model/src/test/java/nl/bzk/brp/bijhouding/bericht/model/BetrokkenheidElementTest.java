/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BetrokkenheidElementTest {

    ElementBuilder builder;

    @Mock
    BijhoudingVerzoekBericht bericht;

    @Before
    public void setup(){
        builder = new ElementBuilder();
    }

    @Test
    public void verwijstNaarBestaandEnJuisteType() throws Exception {
        BetrokkenheidElement element1 = builder.maakBetrokkenheidElement("test1", null, "ref1", BetrokkenheidElementSoort.OUDER,
                (FamilierechtelijkeBetrekkingElement) null);
        BetrokkenheidElement element2 = builder.maakBetrokkenheidElement("test2", null, "ref2", BetrokkenheidElementSoort.OUDER,
                (FamilierechtelijkeBetrekkingElement) null);
        BetrokkenheidElement referentie1 = builder.maakBetrokkenheidElement("ref1", null, null, BetrokkenheidElementSoort.OUDER,
                (FamilierechtelijkeBetrekkingElement) null);
        NationaliteitElement referentie2 = builder.maakNationaliteitElement("ref2", "0001", null);

        final Map<String, BmrGroep> map = new HashMap<>();
        map.put("ref1", referentie1);
        map.put("ref2", referentie2);
        element1.initialiseer(map);
        element2.initialiseer(map);

        assertTrue(element1.verwijstNaarBestaandEnJuisteType());
        assertFalse(element2.verwijstNaarBestaandEnJuisteType());
        assertTrue(element1.heeftReferentie());
        assertFalse(referentie1.heeftReferentie());
    }

    @Test
    public void testMaakOuderNouwkigOngelijkErkenning(){
        when(bericht.getAdministratieveHandeling()).thenReturn(maakAdministratieveHandeling("ah",AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM));

        BetrokkenheidElement element = builder.maakBetrokkenheidElement("test1", null, "ref1", BetrokkenheidElementSoort.OUDER,
                (FamilierechtelijkeBetrekkingElement) null);
        element.setVerzoekBericht(bericht);
        element.maakOuderNouwkig();
        assertEquals(Boolean.FALSE,element.getOuderschap().getIndicatieOuderUitWieKindIsGeboren().getWaarde());

    }

    @Test(expected = IllegalStateException.class)
    public void testMaakOuderNouwkigDaarnaOuwkig(){
        when(bericht.getAdministratieveHandeling()).thenReturn(maakAdministratieveHandeling("ah",AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM));

        BetrokkenheidElement element = builder.maakBetrokkenheidElement("test1", null, "ref1", BetrokkenheidElementSoort.OUDER,
                (FamilierechtelijkeBetrekkingElement) null);
        element.setVerzoekBericht(bericht);
        element.maakOuderNouwkig();
        assertEquals(Boolean.FALSE,element.getOuderschap().getIndicatieOuderUitWieKindIsGeboren().getWaarde());
        element.maakOuderOuwkig();
    }


    public AdministratieveHandelingElement maakAdministratieveHandeling(final String comid,AdministratieveHandelingElementSoort soort) {
        final ElementBuilder.AdministratieveHandelingParameters ahPara = new ElementBuilder.AdministratieveHandelingParameters();
        ahPara.soort(soort);
        ahPara.acties(new LinkedList<>());
        ahPara.partijCode("123455");
        final AdministratieveHandelingElement administratieveHandelingElement = builder.maakAdministratieveHandelingElement(comid, ahPara);
        administratieveHandelingElement.setVerzoekBericht(bericht);
        return administratieveHandelingElement;
    }

}
