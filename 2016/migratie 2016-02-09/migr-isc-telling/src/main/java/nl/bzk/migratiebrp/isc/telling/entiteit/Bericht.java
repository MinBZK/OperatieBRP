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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * Persistent klasse voor de berichten database tabel.
 *
 */
@Entity
@Table(name = "mig_bericht")
public class Bericht implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "bericht_id_generator", sequenceName = "mig_berichten_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bericht_id_generator")
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Long id;

    @Column(name = "tijdstip", nullable = true, insertable = false, updatable = false)
    private Timestamp tijdstip;

    @Column(name = "kanaal", nullable = true, insertable = false, updatable = false)
    private String kanaal;

    @Column(name = "richting", nullable = true, insertable = false, updatable = false)
    private Character richting;

    @Column(name = "message_id", nullable = true, insertable = false, updatable = false)
    private String messageId;

    @Column(name = "correlation_id", nullable = true, insertable = false, updatable = false)
    private String correlationId;

    @Column(name = "bericht", nullable = false, insertable = false, updatable = false)
    private String bericht;

    @Column(name = "naam", nullable = false, insertable = false, updatable = false)
    private String naam;

    @Column(name = "process_instance_id", nullable = true, insertable = false, updatable = false)
    private Long procesInstantieId;

    @Column(name = "verzendende_partij", nullable = true, insertable = false, updatable = false)
    private String bronGemeente;

    @Column(name = "ontvangende_partij", nullable = true, insertable = false, updatable = false)
    private String doelGemeente;

    @Column(name = "actie", nullable = true, insertable = false, updatable = false)
    private String jbpmActie;

    @Column(name = "indicatie_geteld", nullable = true, insertable = true, updatable = true)
    private Boolean indicatieGeteld;

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public final Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van indicatie geteld.
     *
     * @return indicatie geteld
     */
    public final Boolean getIndicatieGeteld() {
        return indicatieGeteld;
    }

    /**
     * Zet de waarde van indicatie geteld.
     *
     * @param indicatieGeteld
     *            indicatie geteld
     */
    public final void setIndicatieGeteld(final Boolean indicatieGeteld) {
        this.indicatieGeteld = indicatieGeteld;
    }

    /**
     * Geef de waarde van tijdstip.
     *
     * @return tijdstip
     */
    public final Timestamp getTijdstip() {
        return Kopieer.timestamp(tijdstip);
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
     * Geef de waarde van richting.
     *
     * @return richting
     */
    public final Character getRichting() {
        return richting;
    }

    /**
     * Geef de waarde van proces instantie id.
     *
     * @return proces instantie id
     */
    public final Long getProcesInstantieId() {
        return procesInstantieId;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public final String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van jbpm actie.
     *
     * @return jbpm actie
     */
    public final String getJbpmActie() {
        return jbpmActie;
    }

    /**
     * Zet de waarde van tijdstip.
     *
     * @param tijdstip
     *            tijdstip
     */
    public final void setTijdstip(final Timestamp tijdstip) {
        this.tijdstip = Kopieer.timestamp(tijdstip);
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

    /**
     * Zet de waarde van richting.
     *
     * @param richting
     *            richting
     */
    public final void setRichting(final Character richting) {
        this.richting = richting;
    }

    /**
     * Zet de waarde van jbpm actie.
     *
     * @param jbpmActie
     *            jbpm actie
     */
    public final void setJbpmActie(final String jbpmActie) {
        this.jbpmActie = jbpmActie;
    }

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public final void setNaam(final String naam) {
        this.naam = naam;
    }

    /**
     * Geef de waarde van message id.
     *
     * @return message id
     */
    public final String getMessageId() {
        return messageId;
    }

    /**
     * Geef de waarde van correlation id.
     *
     * @return correlation id
     */
    public final String getCorrelationId() {
        return correlationId;
    }

    /**
     * Geef de waarde van bericht.
     *
     * @return bericht
     */
    public final String getBericht() {
        return bericht;
    }

    /**
     * Geef de waarde van bron gemeente.
     *
     * @return bron gemeente
     */
    public final String getBronGemeente() {
        return bronGemeente;
    }

    /**
     * Geef de waarde van doel gemeente.
     *
     * @return doel gemeente
     */
    public final String getDoelGemeente() {
        return doelGemeente;
    }

    /**
     * De richting van een bericht (vanuit het zichtpunt van ISC).
     */
    public static enum Direction {
        /** Inkomend. */
        INKOMEND('I'),
        /** Uitgaand. */
        UITGAAND('U');

        private final Character code;

        /**
         * Constructor.
         *
         * @param code
         *            code
         */
        Direction(final Character code) {
            this.code = code;
        }

        /**
         * Geef de waarde van code.
         *
         * @return code
         */
        public Character getCode() {
            return code;
        }

        /**
         * Zoek de richting obv de code.
         *
         * @param code
         *            code
         * @return richting, of null als niet gevonden
         */
        public static Direction valueOfCode(final Character code) {
            for (final Direction direction : values()) {
                if (direction.getCode().equals(code)) {
                    return direction;
                }
            }

            return null;
        }
    }
}
