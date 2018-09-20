/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.VerantwoordingsEntiteit;


/**
 * Een slimme Set interface voor collections die C/D laag entiteiten bevatten met formele historie.
 *
 * @param <T> type historie entiteit wat in deze Set gaat.
 */
public interface FormeleHistorieSet<T extends FormeelHistorisch & FormeelVerantwoordbaar> extends HistorieSet<T> {

    /**
     * Doet de formele historie vervallen.
     *
     * @param verantwoordingVerval verantwoordings entiteit dat de historie moet doen vervallen.
     * @param datumTijdVerval      tijdstip van vervallen.
     */
    void verval(final VerantwoordingsEntiteit verantwoordingVerval, final DatumTijdAttribuut datumTijdVerval);

    /**
     * Retourneert het record voor de opgegeven formeel tijdstip.
     *
     * @param formeleTijdstip het formele tijdstip.
     * @return record wat van toepassing is/was op het opgegeven formele tijdstip.
     */
    T getHistorieRecord(final DatumTijdAttribuut formeleTijdstip);

    /**
     * Retourneert het huidige geldende record voor formeel tijdstip nu.
     *
     * @return het geldende record voor formeel tijdstip nu.
     */
    @Override
    T getActueleRecord();

    /**
     * Retourneert of er een actueel record aanwezig is.
     *
     * @return of er een actueel record is
     */
    boolean heeftActueelRecord();

    /**
     * Retourneert of deze historie vervallen is, dwz dat er geen actueel record is.
     *
     * @return of de historie vervallen is
     */
    boolean isVervallen();
}
