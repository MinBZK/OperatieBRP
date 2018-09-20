/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.service;

import java.util.Set;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;

/**
 * De service die het serialiseren en deserialiseren van afnemerindicaties mogelijk maakt.
 */
public interface AfnemerIndicatieBlobifierService {

    /**
     * Maak een 'afdruk' van de afnemerindicaties van een persoon en sla deze op als blob in de database.
     *
     * @param persoonHisVolledig de persoon wiens indicaties worden opgeslagen
     */
    void blobify(final PersoonHisVolledigImpl persoonHisVolledig);

    /**
     * Maak een 'afdruk' van de afnemerindicaties van een persoon en sla deze op als blob in de database.
     *
     * @param technischId de id van de persoon wiens indicaties worden opgeslagen
     */
    void blobify(final Integer technischId);

    /**
     * Maak een 'afdruk' van de afnemerindicaties van een persoon en sla deze op als blob in de database.
     *
     * @param technischId       de id van de persoon wiens indicaties worden opgeslagen
     * @param afnemerindicaties de set van indicaties om op te slaan
     */
    void blobify(final Integer technischId, final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties);

    /**
     * Leest de 'afdruk' van afnemerindicaties in.
     *
     * @param technischId id van de afnemerindicaties blob
     * @return een set van afnemerindicaties
     */
    Set<PersoonAfnemerindicatieHisVolledigImpl> leesBlob(final Integer technischId);

    /**
     * Leest de 'afdruk' van afnemerindicaties in.
     *
     * @param persoonHisVolledig de persoon wiens indicaties worden ingelezen
     * @return een set van afnemerindicaties
     */
    Set<PersoonAfnemerindicatieHisVolledigImpl> leesBlob(final PersoonHisVolledigImpl persoonHisVolledig);

}
