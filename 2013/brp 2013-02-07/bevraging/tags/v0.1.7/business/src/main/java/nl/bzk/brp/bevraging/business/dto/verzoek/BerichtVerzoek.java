/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.verzoek;

import java.util.Calendar;
import java.util.Collection;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;


/**
 * Interface voor alle BRP specifieke verzoek berichten.
 *
 * @param <T> Type van het antwoord object behorende bij dit verzoek.
 */
public interface BerichtVerzoek<T extends BerichtAntwoord> {

    /**
     * Retourneert het soort bericht.
     *
     * @return het soort bericht.
     */
    SoortBericht getSoortBericht();

    /**
     * Beschouwingsmoment t.b.v. protocollering
     *
     * @return het beschouwingsmoment.
     */
    Calendar getBeschouwing();

    /**
     * BSN's betrokken in het Verzoek t.b.v. applicatief READ locking
     *
     * @return Collectie van BSN's waarvoor het verzoek een read lock nodig heeft.
     */
    Collection<Long> getReadBSNLocks();

    /**
     * BSN's betrokken in het Verzoek t.b.v. applicatief WRITE locking
     *
     * @return Collectie van BSN's waarvoor het verzoek een write lock nodig heeft.
     */
    Collection<Long> getWriteBSNLocks();

    /**
     * Retourneert de class/type van het antwoord object behorende bij dit verzoek.
     *
     * @return de class/type van het antwoord object.
     */
    Class<T> getAntwoordClass();

}
