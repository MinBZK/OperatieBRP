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
import javax.persistence.Table;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * Persistent klasse voor de processen extractie database tabel.
 */
@Entity
@Table(name = "mig_extractie_proces")
public class ProcesExtractie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "process_instance_id", nullable = false, insertable = true, updatable = true, unique = true)
    private Long procesInstantieId;

    @Column(name = "proces_naam", nullable = false, insertable = true, updatable = true, length = 30)
    private String procesnaam;

    @Column(name = "bericht_type", nullable = false, insertable = true, updatable = true, length = 40)
    private String berichtType;

    @Column(name = "foutreden", nullable = true, insertable = true, updatable = true, length = 255)
    private String foutreden;

    @Column(name = "kanaal", nullable = true, insertable = true, updatable = true, length = 20)
    private String kanaal;

    @Column(name = "anummer", nullable = true, insertable = true, updatable = true, length = 10)
    private String anummer;

    @Column(name = "startdatum", nullable = true, insertable = true, updatable = true)
    private Timestamp startDatum;

    @Column(name = "wacht_startdatum", nullable = true, insertable = true, updatable = true)
    private Timestamp wachtStartDatum;

    @Column(name = "wacht_eindDatum", nullable = true, insertable = true, updatable = true)
    private Timestamp wachtEindDatum;

    @Column(name = "einddatum", nullable = true, insertable = true, updatable = true)
    private Timestamp eindDatum;

    @Column(name = "indicatie_gestart_geteld", nullable = true, insertable = true, updatable = true)
    private Boolean indicatieGestartGeteld;

    @Column(name = "indicatie_beeindigd_geteld", nullable = true, insertable = true, updatable = true)
    private Boolean indicatieBeeindigdGeteld;

    /**
     * Geef de waarde van proces instantie id.
     * @return proces instantie id
     */
    public final Long getProcesInstantieId() {
        return procesInstantieId;
    }

    /**
     * Zet de waarde van proces instantie id.
     * @param procesInstantieId proces instantie id
     */
    public final void setProcesInstantieId(final Long procesInstantieId) {
        this.procesInstantieId = procesInstantieId;
    }

    /**
     * Geef de waarde van procesnaam.
     * @return procesnaam
     */
    public final String getProcesnaam() {
        return procesnaam;
    }

    /**
     * Geef de waarde van bericht type.
     * @return bericht type
     */
    public final String getBerichtType() {
        return berichtType;
    }

    /**
     * Zet de waarde van bericht type.
     * @param berichtType bericht type
     */
    public final void setBerichtType(final String berichtType) {
        this.berichtType = berichtType;
    }

    /**
     * Zet de waarde van procesnaam.
     * @param procesnaam procesnaam
     */
    public final void setProcesnaam(final String procesnaam) {
        this.procesnaam = procesnaam;
    }

    /**
     * Geef de waarde van foutreden.
     * @return foutreden
     */
    public final String getFoutreden() {
        return foutreden;
    }

    /**
     * Zet de waarde van foutreden.
     * @param foutreden foutreden
     */
    public final void setFoutreden(final String foutreden) {
        this.foutreden = foutreden;
    }

    /**
     * Geef de waarde van start datum.
     * @return start datum
     */
    public final Timestamp getStartDatum() {
        return Kopieer.timestamp(startDatum);
    }

    /**
     * Zet de waarde van start datum.
     * @param startDatum start datum
     */
    public final void setStartDatum(final Timestamp startDatum) {
        this.startDatum = Kopieer.timestamp(startDatum);
    }

    /**
     * Geef de waarde van wacht start datum.
     * @return wacht start datum
     */
    public final Timestamp getWachtStartDatum() {
        return Kopieer.timestamp(wachtStartDatum);
    }

    /**
     * Zet de waarde van wacht start datum.
     * @param wachtStartDatum wacht start datum
     */
    public final void setWachtStartDatum(final Timestamp wachtStartDatum) {
        this.wachtStartDatum = Kopieer.timestamp(wachtStartDatum);
    }

    /**
     * Geef de waarde van wacht eind datum.
     * @return wacht eind datum
     */
    public final Timestamp getWachtEindDatum() {
        return Kopieer.timestamp(wachtEindDatum);
    }

    /**
     * Zet de waarde van wacht eind datum.
     * @param wachtEindDatum wacht eind datum
     */
    public final void setWachtEindDatum(final Timestamp wachtEindDatum) {
        this.wachtEindDatum = Kopieer.timestamp(wachtEindDatum);
    }

    /**
     * Geef de waarde van eind datum.
     * @return eind datum
     */
    public final Timestamp getEindDatum() {
        return Kopieer.timestamp(eindDatum);
    }

    /**
     * Zet de waarde van eind datum.
     * @param eindDatum eind datum
     */
    public final void setEindDatum(final Timestamp eindDatum) {
        this.eindDatum = Kopieer.timestamp(eindDatum);
    }

    /**
     * Geef de waarde van indicatie gestart geteld.
     * @return indicatie gestart geteld
     */
    public final Boolean getIndicatieGestartGeteld() {
        return indicatieGestartGeteld;
    }

    /**
     * Zet de waarde van indicatie gestart geteld.
     * @param indicatieGestartGeteld indicatie gestart geteld
     */
    public final void setIndicatieGestartGeteld(final Boolean indicatieGestartGeteld) {
        this.indicatieGestartGeteld = indicatieGestartGeteld;
    }

    /**
     * Geef de waarde van indicatie beeindigd geteld.
     * @return indicatie beeindigd geteld
     */
    public final Boolean getIndicatieBeeindigdGeteld() {
        return indicatieBeeindigdGeteld;
    }

    /**
     * Zet de waarde van indicatie beeindigd geteld.
     * @param indicatieBeeindigdGeteld indicatie beeindigd geteld
     */
    public final void setIndicatieBeeindigdGeteld(final Boolean indicatieBeeindigdGeteld) {
        this.indicatieBeeindigdGeteld = indicatieBeeindigdGeteld;
    }

    /**
     * Geef de waarde van kanaal.
     * @return kanaal
     */
    public final String getKanaal() {
        return kanaal;
    }

    /**
     * Zet de waarde van kanaal.
     * @param kanaal kanaal
     */
    public final void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    /**
     * Geef de waarde van anummer.
     * @return anummer
     */
    public final String getAnummer() {
        return anummer;
    }

    /**
     * Zet de waarde van anummer.
     * @param anummer anummer
     */
    public final void setAnummer(final String anummer) {
        this.anummer = anummer;
    }

}
