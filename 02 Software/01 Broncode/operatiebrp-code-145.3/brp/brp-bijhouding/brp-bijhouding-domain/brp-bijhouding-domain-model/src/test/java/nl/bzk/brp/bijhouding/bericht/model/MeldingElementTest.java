/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.junit.Test;

/**
 * Test voor {@link MeldingElement}.
 */
public class MeldingElementTest {

    private static final String COMMUNICATIE_ID = "communicatieId";

    @Test
    public void testGetReferentie() {
        final AbstractBmrGroep.AttributenBuilder attributenBuilder = new AbstractBmrGroep.AttributenBuilder().communicatieId(COMMUNICATIE_ID);
        final BmrGroep groep = maakBmrGroep(attributenBuilder.build());
        final MeldingElement melding = MeldingElement.getInstance(Regel.R1244, groep);
        assertEquals(groep, melding.getReferentie());
        assertEquals(COMMUNICATIE_ID, melding.getReferentieId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetReferentieNull() {
        MeldingElement.getInstance(Regel.R1244, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetReferentieCommunicatieIdNull() {
        final BmrGroep groep = maakBmrGroep(Collections.emptyMap());
        MeldingElement.getInstance(Regel.R1244, groep);
    }

    @Test
    public void testGetters() {
        final Regel regel = Regel.ALG0001;
        final AbstractBmrGroep.AttributenBuilder attributenBuilder = new AbstractBmrGroep.AttributenBuilder().communicatieId(COMMUNICATIE_ID);
        final BmrGroep bmrGroep = maakBmrGroep(attributenBuilder.build());

        final MeldingElement
                melding = new MeldingElement(new AbstractBmrGroep.AttributenBuilder().objecttype("Melding").referentieId(bmrGroep.getCommunicatieId())
                .referentieId(bmrGroep.getCommunicatieId()).build(), new StringElement(regel.getCode()),
                new StringElement(regel.getSoortMelding().getNaam()),
                new StringElement(regel.getMelding()));

        assertEquals(regel.getSoortMelding().getNaam(), melding.getSoortNaam().getWaarde());
        assertEquals(0, melding.valideerInhoud().size());
        assertTrue(melding.verwijstNaarBestaandEnJuisteType());
        assertEquals(regel.getMelding(), melding.getMelding().getWaarde());

    }

    private BmrGroep maakBmrGroep(final Map<String, String> attributen) {
        return new IdentificatienummersElement(attributen, new StringElement("1"), new StringElement("2"));
    }

}
