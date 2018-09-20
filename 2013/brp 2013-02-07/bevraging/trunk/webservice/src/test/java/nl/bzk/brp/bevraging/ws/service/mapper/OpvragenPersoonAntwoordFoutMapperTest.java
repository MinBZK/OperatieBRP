/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonAntwoordFout;
import org.junit.Test;


/**
 * Unit test voor de {@link OpvragenPersoonAntwoordFoutMapper} class.
 */
public class OpvragenPersoonAntwoordFoutMapperTest {

    @Test
    public void testNullDomeinObject() {
        assertNull(OpvragenPersoonAntwoordFoutMapper.mapDomeinObjectNaarDTO(null));
    }

    @Test
    public void testLeegDomeinObject() {
        assertNotNull(OpvragenPersoonAntwoordFoutMapper.mapDomeinObjectNaarDTO(new BerichtVerwerkingsFout(null, null)));
    }

    @Test
    public void testCompleetDomeinObject() {
        BerichtVerwerkingsFout foutDO = getVolledigGevuldeBerichtVerwerkingsFout();
        OpvragenPersoonAntwoordFout foutDTO = OpvragenPersoonAntwoordFoutMapper.mapDomeinObjectNaarDTO(foutDO);

        assertEquals(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT.getCode(), foutDTO.getId());
        assertEquals("Test Melding", foutDTO.getBeschrijving());
        assertEquals("INFO", foutDTO.getToelichting());
    }

    /**
     * {@link OpvragenPersoonAntwoordFoutMapper} is een utility class en zou daarom niet instantieerbaar moeten zijn.
     * Deze test test dan ook of er een exceptie wordt gegooid indien de class wordt geinstantieerd.
     */
    @Test(expected = IllegalAccessException.class)
    public void testFalenConstructieUtilityClass() throws InstantiationException, IllegalAccessException {
        OpvragenPersoonAntwoordFoutMapper.class.newInstance();
    }

    /**
     * Test de private constructor en test ook dat deze geen fouten gooit. Dit doen we puur voor de coverage :(
     */
    @Test
    public void testPrivateConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException
    {
        Constructor<OpvragenPersoonAntwoordFoutMapper> constructor =
            OpvragenPersoonAntwoordFoutMapper.class.getDeclaredConstructor();
        assertFalse(constructor.isAccessible());

        constructor.setAccessible(true);
        assertNotNull(constructor.newInstance());
    }

    /**
     * Genereert een volledig gevulde {@link BerichtVerwerkingsFout} instantie (alle velden hebben een waarde).
     *
     * @return een fout instantie.
     */
    private BerichtVerwerkingsFout getVolledigGevuldeBerichtVerwerkingsFout() {
        BerichtVerwerkingsFout fout =
            new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT, BerichtVerwerkingsFoutZwaarte.INFO,
                    "Test Melding");

        return fout;
    }
}
