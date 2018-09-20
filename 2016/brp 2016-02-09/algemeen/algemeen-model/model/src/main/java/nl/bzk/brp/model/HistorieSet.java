/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;


/**
 * Een slimme Set interface voor collections die C/D-laag entiteiten bevatten met materiele historie.
 *
 * @param <T> type historie entiteit wat in deze Set gaat.
 */
public interface HistorieSet<T extends HistorieEntiteit> extends Iterable<T> {

    /**
     * Voeg een nieuwe record toe aan de historie. Hierbij wordt rekening gehouden met overlapping (indien van toepassing).
     *
     * @param nieuwRecord het toe te voegen record.
     */
    void voegToe(T nieuwRecord);

    /**
     * Retourneert een niet te modificeren lijst van de volledige historie. Dus zowel de vervallen als de niet vervallen records (indien van toepassing).
     *
     * @return lijst van de huidige historie.
     */
    Set<T> getHistorie();

    /**
     * Retourneert het huidige geldende record voor formeel tijdstip nu.
     *
     * @return het geldende record voor formeel tijdstip nu.
     */
    T getActueleRecord();

    /**
     * Retourneert het record voor de opgegeven materiele datum en formeel tijdstip.
     *
     * @param materieleDatum  de materiele datum (wordt genegeerd indien niet van toepassing).
     * @param formeleTijdstip het formele tijdstip.
     * @return record wat van toepassing is/was op de opgegeven formele en materiele data.
     */
    T getHistorieRecord(final DatumAttribuut materieleDatum, final DatumTijdAttribuut formeleTijdstip);

    /**
     * Retourneert het aantal records/instanties in de historie.
     *
     * @return aantal records/instanties in de historie.
     */
    int getAantal();

    /**
     * Retourneert of de historie leeg is.
     *
     * @return <code>true</code> als de historie leeg is.
     */
    boolean isLeeg();

}
