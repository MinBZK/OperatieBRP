/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
     * Genereert een volledig gevulde {@link BerichtVerwerkingsFout} instantie (alle velden hebben een waarde).
     * @return een fout instantie.
     */
    private BerichtVerwerkingsFout getVolledigGevuldeBerichtVerwerkingsFout() {
        BerichtVerwerkingsFout fout = new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT,
                BerichtVerwerkingsFoutZwaarte.INFO, "Test Melding");

        return fout;
    }
}
