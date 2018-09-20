/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.entiteit;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * Persistent klasse voor de processen tellingen database tabel.
 * 
 */
@Entity
@Table(name = "mig_telling_proces")
@IdClass(ProcesTellingId.class)
public class ProcesTelling implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "proces_naam", nullable = false, insertable = true, updatable = true, length = 30)
    private String procesnaam;

    @Id
    @Column(name = "datum", nullable = false, insertable = true, updatable = true)
    private Timestamp datum;

    @Id
    @Column(name = "bericht_type", nullable = false, insertable = true, updatable = true, length = 40)
    private String berichtType;

    @Id
    @Column(name = "kanaal", nullable = false, insertable = true, updatable = true, length = 20)
    private String kanaal;

    @Column(name = "aantal_gestarte_processen", nullable = false, insertable = true, updatable = true)
    private Integer aantalGestarteProcessen;

    @Column(name = "aantal_beeindigde_processen", nullable = false, insertable = true, updatable = true)
    private Integer aantalBeeindigdeProcessen;

    /**
     * Geef de waarde van procesnaam.
     *
     * @return procesnaam
     */
    public final String getProcesnaam() {
        return procesnaam;
    }

    /**
     * Zet de waarde van procesnaam.
     *
     * @param procesnaam
     *            procesnaam
     */
    public final void setProcesnaam(final String procesnaam) {
        this.procesnaam = procesnaam;
    }

    /**
     * Geef de waarde van bericht type.
     *
     * @return bericht type
     */
    public final String getBerichtType() {
        return berichtType;
    }

    /**
     * Zet de waarde van bericht type.
     *
     * @param berichtType
     *            bericht type
     */
    public final void setBerichtType(final String berichtType) {
        this.berichtType = berichtType;
    }

    /**
     * Geef de waarde van datum.
     *
     * @return datum
     */
    public final Timestamp getDatum() {
        return Kopieer.timestamp(datum);
    }

    /**
     * Zet de waarde van datum.
     *
     * @param datum
     *            datum
     */
    public final void setDatum(final Timestamp datum) {
        this.datum = Kopieer.timestamp(datum);
    }

    /**
     * Geef de waarde van aantal gestarte processen.
     *
     * @return aantal gestarte processen
     */
    public final Integer getAantalGestarteProcessen() {
        return aantalGestarteProcessen;
    }

    /**
     * Zet de waarde van aantal gestarte processen.
     *
     * @param aantalGestarteProcessen
     *            aantal gestarte processen
     */
    public final void setAantalGestarteProcessen(final Integer aantalGestarteProcessen) {
        this.aantalGestarteProcessen = aantalGestarteProcessen;
    }

    /**
     * Geef de waarde van aantal beeindigde processen.
     *
     * @return aantal beeindigde processen
     */
    public final Integer getAantalBeeindigdeProcessen() {
        return aantalBeeindigdeProcessen;
    }

    /**
     * Zet de waarde van aantal beeindigde processen.
     *
     * @param aantalBeeindigdeProcessen
     *            aantal beeindigde processen
     */
    public final void setAantalBeeindigdeProcessen(final Integer aantalBeeindigdeProcessen) {
        this.aantalBeeindigdeProcessen = aantalBeeindigdeProcessen;
    }

    /**
     * Geef de waarde van kanaal.
     *
     * @return kanaal
     */
    public final String getKanaal() {
        return kanaal;
    }

    /**
     * Zet de waarde van kanaal.
     *
     * @param kanaal
     *            kanaal
     */
    public final void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

}
