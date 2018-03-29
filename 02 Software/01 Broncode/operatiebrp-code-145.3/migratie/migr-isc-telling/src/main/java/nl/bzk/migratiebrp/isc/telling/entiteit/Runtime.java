/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.entiteit;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * Persistent klasse voor de extractie runtime database tabel.
 */
@Entity
@Table(name = "mig_runtime")
public class Runtime implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "runtime_naam", nullable = false, insertable = true, updatable = false, length = 30)
    private String runtimeNaam;

    @Column(name = "startDatum", nullable = false, insertable = true, updatable = false)
    private Date startDatum;

    @Column(name = "client_naam", nullable = false, insertable = true, updatable = false, length = 60)
    private String clientNaam;

    /**
     * Geef de waarde van runtime naam.
     * @return runtime naam
     */
    public final String getRuntimeNaam() {
        return runtimeNaam;
    }

    /**
     * Zet de waarde van runtime naam.
     * @param runtimeNaam runtime naam
     */
    public final void setRuntimeNaam(final String runtimeNaam) {
        this.runtimeNaam = runtimeNaam;
    }

    /**
     * Geef de waarde van start datum.
     * @return start datum
     */
    public final Date getStartDatum() {
        return Kopieer.sqlDate(startDatum);
    }

    /**
     * Zet de waarde van start datum.
     * @param startDatum start datum
     */
    public final void setStartDatum(final Date startDatum) {
        this.startDatum = Kopieer.sqlDate(startDatum);
    }

    /**
     * Geef de waarde van client naam.
     * @return client naam
     */
    public final String getClientNaam() {
        return clientNaam;
    }

    /**
     * Zet de waarde van client naam.
     * @param clientNaam client naam
     */
    public final void setClientNaam(final String clientNaam) {
        this.clientNaam = clientNaam;
    }

}
