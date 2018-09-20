/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.domein;

import java.util.Calendar;


/**
 * Deze class bevat het aantal berichten per partij en de tijdstip van het laatste bericht.
 *
 */
public class BerichtenPerPartij {

    private final Integer  partijId;

    private final String   partijNaam;

    private final Long     aantal;

    private final Calendar tijdLaatsteBericht;

    /**
     * Constructor.
     *
     * @param partijId id van partij
     * @param partijNaam partij Naam
     * @param aantal aantal berichten
     * @param tijdLaatsteBericht timestamp van laatste bericht
     */
    public BerichtenPerPartij(final Integer partijId, final String partijNaam, final Long aantal, final Calendar tijdLaatsteBericht) {
        super();
        this.partijId = partijId;
        this.partijNaam = partijNaam;
        this.aantal = aantal;
        this.tijdLaatsteBericht = tijdLaatsteBericht;
    }

    public Integer getPartijId() {
        return partijId;
    }

    public String getPartijNaam() {
        return partijNaam;
    }

    public Long getAantal() {
        return aantal;
    }

    public Calendar getTijdLaatsteBericht() {
        return tijdLaatsteBericht;
    }
}
