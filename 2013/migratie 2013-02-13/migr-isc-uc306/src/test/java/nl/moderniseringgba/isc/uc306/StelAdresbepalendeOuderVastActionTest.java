/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteVerzoekBericht;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/uc306-test-beans.xml", "classpath*:usecase-beans.xml" })
public class StelAdresbepalendeOuderVastActionTest {
    @Inject
    private BepaalAdresbepalendeOuderAction action;

    @Test
    public void testMoeder() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BepaalAdresbepalendeOuderAction.INPUT, maakGeboorteBericht("uc306_adresbepalendeMoeder.xml"));
        parameters = action.execute(parameters);
        assertEquals(BepaalAdresbepalendeOuderAction.MOEDER,
                parameters.get(BepaalAdresbepalendeOuderAction.ADRES_BEPALENDE_OUDER));
    }

    @Test
    public void testVader() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BepaalAdresbepalendeOuderAction.INPUT, maakGeboorteBericht("uc306_adresbepalendeVader.xml"));
        parameters = action.execute(parameters);
        assertEquals(BepaalAdresbepalendeOuderAction.VADER,
                parameters.get(BepaalAdresbepalendeOuderAction.ADRES_BEPALENDE_OUDER));
    }

    @Test
    public void testGeenAdresbepalendeOuder() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BepaalAdresbepalendeOuderAction.INPUT,
                maakGeboorteBericht("uc306_geenAdresbepalendeOuder.xml"));
        parameters = action.execute(parameters);
        assertNull(parameters.get(BepaalAdresbepalendeOuderAction.ADRES_BEPALENDE_OUDER));
        assertEquals(String.format(BepaalAdresbepalendeOuderAction.TOELICHTING,
                BepaalAdresbepalendeOuderAction.REDEN_GEEN_ADRESGEVENDE_OUDER),
                parameters.get(BepaalAdresbepalendeOuderAction.ADRES_BEPALENDE_OUDER_FOUT));
    }

    @Test
    public void testOngeldigeGeslachtAanduidingAdresbepalendeOuder() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BepaalAdresbepalendeOuderAction.INPUT,
                maakGeboorteBericht("uc306_verkeerdeGeslachtsaanduidingAdresbepalendeOuder.xml"));
        parameters = action.execute(parameters);
        assertNull(parameters.get(BepaalAdresbepalendeOuderAction.ADRES_BEPALENDE_OUDER));
        assertEquals(String.format(BepaalAdresbepalendeOuderAction.TOELICHTING,
                BepaalAdresbepalendeOuderAction.REDEN_VERKEERDE_GESLACHTSAANDUIDING),
                parameters.get(BepaalAdresbepalendeOuderAction.ADRES_BEPALENDE_OUDER_FOUT));
    }

    @Test
    public void testBeideOudersAdresbepalend() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BepaalAdresbepalendeOuderAction.INPUT,
                maakGeboorteBericht("uc306_beideOudersAdresbepalend.xml"));
        parameters = action.execute(parameters);
        assertNull(parameters.get(BepaalAdresbepalendeOuderAction.ADRES_BEPALENDE_OUDER));
        assertEquals(String.format(BepaalAdresbepalendeOuderAction.TOELICHTING,
                BepaalAdresbepalendeOuderAction.REDEN_MEER_DAN_1_OUDER_ADRESGEVEND),
                parameters.get(BepaalAdresbepalendeOuderAction.ADRES_BEPALENDE_OUDER_FOUT));
    }

    private GeboorteVerzoekBericht maakGeboorteBericht(final String xmlResource) throws Exception {
        return (GeboorteVerzoekBericht) BrpBerichtFactory.SINGLETON.getBericht(IOUtils.toString(getClass()
                .getResourceAsStream(xmlResource)));
    }
}
