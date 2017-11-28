/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;

/**
 * Stap om het asynchrone bericht op de afnemerqueue te plaatsen voor de diensten 'Synchroniseer Persoon' en 'Plaats Afnemerindicatie'.
 */
@FunctionalInterface
public interface VerzoekAsynchroonBerichtQueueService {

    /**
     * Plaats het bericht op de queue.
     * @param bericht bericht
     * @param autorisatiebundel autorisatiebundel
     * @param datumAanvangMaterielePeriode datum aanvang materiele periode
     * @throws StapException fout in het zetten van bericht op queue.
     */
    void plaatsQueueberichtVoorVerzoek(VerwerkPersoonBericht bericht, Autorisatiebundel autorisatiebundel, final Integer datumAanvangMaterielePeriode)
            throws
            StapException;
}
