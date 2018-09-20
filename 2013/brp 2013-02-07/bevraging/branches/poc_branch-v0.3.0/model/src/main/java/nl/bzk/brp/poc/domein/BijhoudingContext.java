/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.domein;

import java.util.Date;

/**
 * Object dat dient als context voor het verwerken van bijhoudingsberichten.
 */
public class BijhoudingContext {

    private final Integer datumIngangGeldigheid;
    private final Integer datumEindeGeldigheid;
    private final Date    tijdstipRegistratie;

    /**
     * Standaard constructor die de datums van ingang geldigheid en einde geldigheid (voor de bijhouding) zet.
     * @param datumIngangGeldigheid datum van ingang geldigheid voor de bijhouding.
     * @param datumEindeGeldigheid datum van einde geldigheid voor de bijhouding.
     */
    public BijhoudingContext(final Integer datumIngangGeldigheid, final Integer datumEindeGeldigheid) {
        this.datumIngangGeldigheid = datumIngangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
        this.tijdstipRegistratie = new Date();
    }

    /**
     * Retourneert de datum van ingang geldigheid voor de bijhouding.
     * @return de datum van ingang geldigheid voor de bijhouding.
     */
    public Integer getDatumIngangGeldigheid() {
        return datumIngangGeldigheid;
    }

    /**
     * Retourneert de datum van einde geldigheid voor de bijhouding.
     * @return de datum van einde geldigheid voor de bijhouding.
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Retourneert het tijdstip van de registratie.
     * @return het tijdstip van de registratie.
     */
    public Date getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }
}
