/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import nl.bzk.migratiebrp.voisc.database.entities.Bericht;

/**
 * Helper methods for creating operation objects.
 */
public interface Operations {
    /**
     * Constructs a PutMessageOperation object based on a Bericht.
     * @param bericht Bericht
     * @return PutMessageOperation
     */
    static PutMessageOperation fromBericht(final Bericht bericht) {
        return (PutMessageOperation) new Operation.Builder().add(
                new PutEnvelope.Builder()
                        .contentType(SpdConstants.ContentType.P2)
                        .priority(SpdConstants.Priority.NORMAL)
                        .attention(SpdConstants.Attention.NO_ATTENTION)
                        .originatorOrName(bericht.getOriginator())
                        .build())
                .add(PutMessageHeading.fromBericht(bericht))
                .add(new PutMessageBody(bericht.getBerichtInhoud()))
                .build();
    }
}
