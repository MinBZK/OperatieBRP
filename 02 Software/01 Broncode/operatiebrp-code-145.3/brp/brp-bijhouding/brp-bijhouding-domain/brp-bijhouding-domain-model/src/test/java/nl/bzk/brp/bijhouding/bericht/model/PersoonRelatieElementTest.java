/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class PersoonRelatieElementTest {
    ElementBuilder builder = new ElementBuilder();
    @Test
    public void valideerInhoud() throws Exception {
        PersoonRelatieElement element = builder.maakPersoonRelatieElement("test","test", null, Collections.emptyList());
        assertTrue(element.valideerInhoud().isEmpty());
    }

    @Test
    public void verwijstNaarBestaandEnJuisteType() throws Exception {
        PersoonRelatieElement element1 = builder.maakPersoonRelatieElement("test1","test", null, Collections.emptyList());
        PersoonRelatieElement element2 = builder.maakPersoonRelatieElement("test2","1234", null, Collections.emptyList());
        PersoonRelatieElement element3 = builder.maakPersoonRelatieElement("test3",null, null, Collections.emptyList());
        PersoonElement referentie1 = builder.maakPersoonGegevensElement("pers","test");
        NationaliteitElement referentie2 = builder.maakNationaliteitElement("1234","0001",null);

        final Map<String, BmrGroep> map = new HashMap<>();
        map.put("test",referentie1);
        map.put("1234",referentie2);
        element1.initialiseer(map);
        element2.initialiseer(map);
        element3.initialiseer(map);

        assertTrue(element1.verwijstNaarBestaandEnJuisteType());
        assertFalse(element2.verwijstNaarBestaandEnJuisteType());
        assertTrue(element3.verwijstNaarBestaandEnJuisteType());



    }

}
