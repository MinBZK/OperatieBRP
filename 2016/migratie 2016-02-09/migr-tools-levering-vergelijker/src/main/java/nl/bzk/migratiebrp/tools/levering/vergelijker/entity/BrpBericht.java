/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractUnparsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;

/**
 * Persistent klasse voor de berichten database tabel in BRP.
 *
 */
@Entity
@Table(name = "mig_bericht")
public class BrpBericht implements Bericht, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Lo3BerichtFactory LO3_BERICHT_FACTORY = new Lo3BerichtFactory();

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
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public final String getNaam() {
        return naam;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.tools.levering.vergelijker.entity.Bericht#getBericht()
     */
    @Override
    public final String getBericht() {

        return bericht;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.tools.levering.vergelijker.entity.Bericht#getBerichtInhoud()
     */
    @Override
    public final String getBerichtInhoud() {
        final Lo3Bericht lo3Bericht = LO3_BERICHT_FACTORY.getBericht(bericht);
        if (lo3Bericht instanceof AbstractUnparsedLo3Bericht) {
            return ((AbstractUnparsedLo3Bericht) lo3Bericht).getInhoud();
        }
        return bericht;
    }

    /**
     * Geef de waarde van tijdstip.
     *
     * @return tijdstip
     */
    public final Timestamp getTijdstip() {
        return tijdstip;
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
     * Geef de waarde van proces instantie id.
     *
     * @return proces instantie id
     */
    public final Long getProcesInstantieId() {
        return procesInstantieId;
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
     * Geef de waarde van jbpm actie.
     *
     * @return jbpm actie
     */
    public final String getJbpmActie() {
        return jbpmActie;
    }

    /**
     * Geef de waarde van indicatie geteld.
     *
     * @return indicatie geteld
     */
    public final Boolean getIndicatieGeteld() {
        return indicatieGeteld;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.tools.levering.vergelijker.entity.Bericht#getBerichtType()
     */
    @Override
    public final String getBerichtType() {
        final Lo3Bericht lo3Bericht = LO3_BERICHT_FACTORY.getBericht(bericht);
        if (lo3Bericht instanceof AbstractUnparsedLo3Bericht) {
            return ((AbstractUnparsedLo3Bericht) lo3Bericht).getBerichtType();
        }
        return naam;

    }

}
