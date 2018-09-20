/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.dto.antwoord;

/**
 * Enumeratie voor de verschillende niveau's van bijhoudingwaarschuwingen.
 */
public enum BijhoudingWaarschuwingNiveau {

    /**
     * Informatieve waarschuwing; geeft in de meeste gevallen niet direct een probleem aan, maar vaak meer extra
     * informatie op basis de gegevens en/of herinneringen van wat er nog gedaan moet worden.
     */
    INFO,
    /**
     * Normale waarschuwing die niet direct een fout ten gevolgen heeft. Een waarschuwing betekent dat alles op zich
     * correct is uitgevoerd, maar dat er wel een situatie is opgetreden die aandacht behoeft.
     */
    WAARSCHUWING,
    /**
     * Indien er een fout is opgetreden en dus het normale proces is gestopt. Deze 'zachte' fout is echter overrulebaar
     * door de client.
     */
    ZACHTE_FOUT,
    /**
     * Indien er een fout is opgetreden (in dit geval meestal een systeem of syntax fout) en waardoor het normale
     * proces is gestopt. Deze 'harde' fout is niet overrulebaar.
     */
    HARDE_FOUT;

}
