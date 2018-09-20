/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.ber;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * Een bericht.
 */
@Entity
@Table(name = "ber", schema = "ber")
@Access(AccessType.FIELD)
public class Bericht implements Serializable {

    @SequenceGenerator(name = "BER_SEQUENCE_GENERATOR", sequenceName = "ber.seq_ber")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BER_SEQUENCE_GENERATOR")
    private Long     id;

    @Column(name = "tsontv")
    private Calendar ontvangst;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data")
    private String   data;

    @Column(name = "Richting")
    private Richting richting;

    @Column(name = "antwoordop")
    private Long     antwoordOp;

    /**
     * No-arg constructor voor JPA.
     */
    protected Bericht() {
    }

    /**
     * Constructor voor programmatische instantiatie, met verplichte parameters.
     *
     * @param data de inhoud van het bericht.
     * @param ontvangst het tijdstip van het bericht.
     * @param richting of het een ingaand of uitgaand bericht is.
     */
    public Bericht(final String data, final Calendar ontvangst, final Richting richting) {
        this.data = data;
        this.ontvangst = ontvangst;
        this.richting = richting;
    }

    /**
     * Constructor voor programmatische instantiatie, met verplichte parameters, maar zonder ontvangst, die een default
     * "nu" heeft.
     *
     * @param data de inhoud van het bericht.
     * @param richting of het een ingaand of uitgaand bericht is.
     */
    public Bericht(final String data, final Richting richting) {
        this(data, Calendar.getInstance(), richting);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Calendar getOntvangst() {
        return ontvangst;
    }

    public void setOntvangst(final Calendar ontvangst) {
        this.ontvangst = ontvangst;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public Richting getRichting() {
        return richting;
    }

    public void setRichting(final Richting richting) {
        this.richting = richting;
    }

    public Long getAntwoordOp() {
        return antwoordOp;
    }

    public void setAntwoordOp(final Long antwoordOp) {
        this.antwoordOp = antwoordOp;
    }
}
