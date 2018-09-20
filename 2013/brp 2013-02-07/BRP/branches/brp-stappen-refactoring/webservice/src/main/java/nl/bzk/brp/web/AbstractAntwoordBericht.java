/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import java.util.Collection;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;


/**
 * Deze klasse stelt een antwoord bericht voor met alle elementen die terug gecommuniceerd worden via de webservice.
 * Het
 * bevat eventuele meldingen (fouten, waarschuwingen etc.) die zijn opgetreden tijdens de verwerking van het bericht.
 *
 */
public abstract class AbstractAntwoordBericht extends BerichtBericht {

    protected AbstractAntwoordBericht(final SoortBericht soort) {
        super(soort);
    }

    @Override
    public Collection<String> getReadBsnLocks() {
        throw new UnsupportedOperationException("Antwoord berichten kennen geen read/write bsn locks.");
    }

    @Override
    public Collection<String> getWriteBsnLocks() {
        throw new UnsupportedOperationException("Antwoord berichten kennen geen read/write bsn locks.");
    }
}
