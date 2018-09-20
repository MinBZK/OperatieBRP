/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import java.util.Collection;

/**
 * Interface die boven elk BRP bericht hoort en die methodes biedt om op een generieke wijze bericht specifieke zaken
 * uit het bericht te halen zoals de partijId en de BSN's die eventueel gelockt moeten worden.
 * <p/>
 * Een bericht kent haar eigen inhoud en daardoor dus ook welke BSN's er "geraakt" worden door het bericht.
 * Daarom moet elk bericht ook een implementatie bevatten van {@link #getReadBsnLocks} en {@link #getWriteBsnLocks}.
 */
public interface BRPBericht {

    /**
     * Retourneert de id van de partij die de bevraging of bijhouding doet.
     *
     * @return de id van de partij die de bevraging of bijhouding doet.
     */
    Short getPartijId();

    /**
     * Retourneert de stuurgegevens van het bericht.
     *
     * @return de stuurgegevens van het bericht.
     */
    BerichtStuurgegevens getBerichtStuurgegevens();

    /**
     * BSN's betrokken in het Verzoek t.b.v. applicatief READ locking
     *
     * @return Collectie van BSN's waarvoor het verzoek een read lock nodig heeft.
     */
    Collection<String> getReadBsnLocks();

    /**
     * BSN's betrokken in het Verzoek t.b.v. applicatief WRITE locking
     *
     * @return Collectie van BSN's waarvoor het verzoek een write lock nodig heeft.
     */
    Collection<String> getWriteBsnLocks();

}
