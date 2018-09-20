/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersonenType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersoonType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class ControleerZoekPersoonBinnenGemeenteAntwoordBerichtDecisionTest {

    private static final String BERICHT_NAAM = "brpBericht";

    private final ControleerZoekPersoonBinnenGemeenteAntwoordBerichtDecision controleerZoekPersoonBinnenGemeenteAntwoordBerichtDecision =
            new ControleerZoekPersoonBinnenGemeenteAntwoordBerichtDecision();

    @Test
    public void testHappyFlow() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BERICHT_NAAM, maakZoekPersoonAntwoordBerichtHappyFlow());

        final String transition = controleerZoekPersoonBinnenGemeenteAntwoordBerichtDecision.execute(parameters);
        Assert.assertNull("Bij HappyFlow hoort de transition 'null' te zijn.", transition);
    }

    @Test
    public void testBerichtZonderGevondenPersonen() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BERICHT_NAAM, maakZoekPersoonAntwoordBerichtZonderGevondenPersonen());

        final String transition = controleerZoekPersoonBinnenGemeenteAntwoordBerichtDecision.execute(parameters);
        Assert.assertEquals("Niets gevonden", transition);
    }

    @Test
    public void testBerichtMeerdereGevondenPersonen() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BERICHT_NAAM, maakZoekPersoonAntwoordBerichtMeerdereGevondenPersonen());

        final String transition = controleerZoekPersoonBinnenGemeenteAntwoordBerichtDecision.execute(parameters);
        Assert.assertEquals("Niet uniek", transition);
    }

    @Test
    public void testFoutBericht() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BERICHT_NAAM, maakZoekPersoonAntwoordBerichtFoutStatus());

        final String transition = controleerZoekPersoonBinnenGemeenteAntwoordBerichtDecision.execute(parameters);
        Assert.assertEquals("Fout", transition);
    }

    @Test
    public void testGeenBericht() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        final String transition = controleerZoekPersoonBinnenGemeenteAntwoordBerichtDecision.execute(parameters);
        Assert.assertEquals("Fout", transition);
    }

    private Object maakZoekPersoonAntwoordBerichtHappyFlow() {
        final GevondenPersonenType gevondenPersonenType = new GevondenPersonenType();
        final GevondenPersoonType gevondenPersoonType = new GevondenPersoonType();
        gevondenPersonenType.getGevondenPersoon().add(gevondenPersoonType);

        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        zoekPersoonAntwoordType.setGevondenPersonen(gevondenPersonenType);
        return new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
    }

    private Object maakZoekPersoonAntwoordBerichtZonderGevondenPersonen() {
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        return new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
    }

    private Object maakZoekPersoonAntwoordBerichtMeerdereGevondenPersonen() {
        final GevondenPersonenType gevondenPersonen = new GevondenPersonenType();
        final GevondenPersoonType gevondenPersoon1 = new GevondenPersoonType();
        final GevondenPersoonType gevondenPersoon2 = new GevondenPersoonType();
        gevondenPersonen.getGevondenPersoon().add(gevondenPersoon1);
        gevondenPersonen.getGevondenPersoon().add(gevondenPersoon2);
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        zoekPersoonAntwoordType.setGevondenPersonen(gevondenPersonen);
        return new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
    }

    private Object maakZoekPersoonAntwoordBerichtFoutStatus() {
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setStatus(StatusType.FOUT);
        return new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
    }

}
