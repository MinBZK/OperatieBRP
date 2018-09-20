/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Een bericht voor berichtarchivering.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "Ber", schema = "Ber")
public class PersistentBericht {

    @Id
    @SequenceGenerator(name = "BERICHT", sequenceName = "Ber.seq_Ber")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BERICHT")
    private Long id;

    @NotNull
    private String data;

    @Column(name = "TsOntv")
    private Calendar datumTijdOntvangst;

    @Column(name = "TsVerzenden")
    private Calendar datumTijdVerzenden;

    @ManyToOne(targetEntity = PersistentBericht.class)
    @JoinColumn(name = "AntwoordOp")
    private PersistentBericht antwoordOp;

    @NotNull
    private Richting richting;

    /**
     * No-args constructor, vereist voor JPA.
     */
    public PersistentBericht() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public Calendar getDatumTijdOntvangst() {
        return datumTijdOntvangst;
    }

    public void setDatumTijdOntvangst(final Calendar datumTijdOntvangst) {
        this.datumTijdOntvangst = datumTijdOntvangst;
    }

    public Calendar getDatumTijdVerzenden() {
        return datumTijdVerzenden;
    }

    public void setDatumTijdVerzenden(final Calendar datumTijdVerzenden) {
        this.datumTijdVerzenden = datumTijdVerzenden;
    }

    public PersistentBericht getAntwoordOp() {
        return antwoordOp;
    }

    public void setAntwoordOp(final PersistentBericht antwoordOp) {
        this.antwoordOp = antwoordOp;
    }

    public Richting getRichting() {
        return richting;
    }

    public void setRichting(final Richting richting) {
        this.richting = richting;
    }

}
