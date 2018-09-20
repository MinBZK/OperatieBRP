/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import java.util.List;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;


public abstract class AbstractStapTest {

    /**
     * Zoek een melding op in de lijst met de opgegeven {@link MeldingCode}
     *
     * @param meldingCode de code waarmee gezocht wordt
     * @param meldingen lijst met {@link Melding}
     * @return null wanneer de melding niet gevonden kan worden of de corresponderende {@link
     *         nl.bzk.brp.model.validatie.Melding}
     */
    protected Melding zoekMelding(final MeldingCode meldingCode, final List<Melding> meldingen) {
        Melding resultaat = null;
        for (Melding melding : meldingen) {
            if (meldingCode.equals(melding.getCode())) {
                resultaat = melding;
            }
        }

        return resultaat;
    }

    /**
     * Bouwt en retourneert een standaard {@link nl.bzk.brp.business.dto.BerichtContext} instantie, met ingevulde in-
     * en uitgaande bericht ids, een authenticatiemiddel id en een partij.
     *
     * @return een geldig bericht context.
     */
    protected BerichtContext bouwBerichtContext(final String... bsns) {
        BerichtenIds ids = new BerichtenIds(2L, 3L);
        Partij partij = StatischeObjecttypeBuilder.GEMEENTE_BREDA;
        return new BerichtContext(ids, partij, "ref");
    }

    protected BerichtContext bouwBerichtContext() {
        BerichtenIds ids = new BerichtenIds(2L, 3L);
        Partij partij = StatischeObjecttypeBuilder.GEMEENTE_BREDA;
        return new BerichtContext(ids, partij, "ref");
    }

}
