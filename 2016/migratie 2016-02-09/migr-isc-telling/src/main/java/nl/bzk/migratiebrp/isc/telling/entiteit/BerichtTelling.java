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
@Table(name = "mig_telling_bericht")
@IdClass(BerichtTellingId.class)
public class BerichtTelling implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "datum", nullable = false, insertable = true, updatable = true)
    private Timestamp datum;

    @Id
    @Column(name = "bericht_type", nullable = false, insertable = true, updatable = true, length = 40)
    private String berichtType;

    @Id
    @Column(name = "kanaal", nullable = false, insertable = true, updatable = true, length = 20)
    private String kanaal;

    @Column(name = "aantal_ingaand", nullable = false, insertable = true, updatable = true)
    private Integer aantalIngaand;

    @Column(name = "aantal_uitgaand", nullable = false, insertable = true, updatable = true)
    private Integer aantalUitgaand;

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
     * Geef de waarde van aantal ingaand.
     *
     * @return aantal ingaand
     */
    public final Integer getAantalIngaand() {
        return aantalIngaand;
    }

    /**
     * Zet de waarde van aantal ingaand.
     *
     * @param aantalIngaand
     *            aantal ingaand
     */
    public final void setAantalIngaand(final Integer aantalIngaand) {
        this.aantalIngaand = aantalIngaand;
    }

    /**
     * Geef de waarde van aantal uitgaand.
     *
     * @return aantal uitgaand
     */
    public final Integer getAantalUitgaand() {
        return aantalUitgaand;
    }

    /**
     * Zet de waarde van aantal uitgaand.
     *
     * @param aantalUitgaand
     *            aantal uitgaand
     */
    public final void setAantalUitgaand(final Integer aantalUitgaand) {
        this.aantalUitgaand = aantalUitgaand;
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
