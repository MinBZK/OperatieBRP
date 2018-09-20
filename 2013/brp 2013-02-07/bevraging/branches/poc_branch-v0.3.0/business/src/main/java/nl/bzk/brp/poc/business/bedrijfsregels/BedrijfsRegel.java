/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.bedrijfsregels;

import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingWaarschuwing;

/**
 * Generieke interface voor alle bedrijfsregels.
 */
public interface BedrijfsRegel<T> {

    /**
     * Executeert de bedrijfsregel met de opgegeven nieuwe en huidige situatie en geeft, indien de bedrijfsregel faalt
     * (dus indien de bedrijfsregel constateert dat het niet goed is), een {@link BijhoudingWaarschuwing} instantie
     * retour. Deze {@link BijhoudingWaarschuwing} geeft dan ook het niveau van de fout aan en bevat een omschrijving
     * en code van de bedrijfsregel zodat deze gerapporteerd kunnen worden.
     *
     * @param nieuweSituatie de nieuwe situatie, welke beschreven wordt in het bericht dat gecontroleerd dient te
     * worden.
     * @param huidigeSituatie de huidige situatie, zoals op moment van executie bekend is binnen het systeem.
     * @return <code>null</code> indien de bedrijfsregel geen problemen constateert. Indien er wel problemen worden
     *         geconstateerd, zal er een {@link BijhoudingWaarschuwing} instantie worden geretourneerd.
     */
    BijhoudingWaarschuwing voerUit(T nieuweSituatie, T huidigeSituatie);

}
