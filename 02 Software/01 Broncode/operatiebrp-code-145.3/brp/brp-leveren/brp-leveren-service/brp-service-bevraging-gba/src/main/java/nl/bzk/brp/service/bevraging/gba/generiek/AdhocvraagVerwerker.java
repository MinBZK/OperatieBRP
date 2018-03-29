/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;

import nl.bzk.brp.gba.domain.bevraging.Basisvraag;

/**
 * Adhoc vraag verwerker.
 * @param <T> een subtype van type Basisvraag
 * @param <U> het type van het antwoord
 */
@FunctionalInterface
public interface AdhocvraagVerwerker<T extends Basisvraag, U> {
    /**
     * Verwerk de ad hoc vraag.
     * @param vraag vraag
     * @param berichtReferentie bericht referentie
     * @return ad hoc antwoord
     */
    U verwerk(T vraag, String berichtReferentie);
}
