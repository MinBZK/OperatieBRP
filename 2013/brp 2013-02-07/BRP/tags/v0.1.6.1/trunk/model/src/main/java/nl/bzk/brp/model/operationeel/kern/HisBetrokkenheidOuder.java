/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

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

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorieEntiteit;

/** Een (historisch) betrokkenheid in het operationeel model. */
@Entity
@Access(AccessType.FIELD)
@Table(name = "his_BetrOuder", schema = "Kern")
public class HisBetrokkenheidOuder extends AbstractMaterieleEnFormeleHistorieEntiteit implements Cloneable {

    @Id
    @SequenceGenerator(name = "HISBETROUDER", sequenceName = "Kern.seq_his_BetrOuder")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISBETROUDER")
    private Long id;

    @ManyToOne(targetEntity = PersistentBetrokkenheid.class)
    @JoinColumn(name = "Betr")
    @NotNull
    private PersistentBetrokkenheid betrokkenheid;

    @Column(name = "indOuder")
    @NotNull
    private Boolean indicatieOuder;

    /** No-args constructor, vereist voor JPA. */
    public HisBetrokkenheidOuder() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public PersistentBetrokkenheid getBetrokkenheid() {
        return betrokkenheid;
    }

    public void setBetrokkenheid(final PersistentBetrokkenheid betrokkenheid) {
        this.betrokkenheid = betrokkenheid;
    }

    public Boolean getIndicatieOuder() {
        return indicatieOuder;
    }

    public void setIndicatieOuder(final Boolean indicatieOuder) {
        this.indicatieOuder = indicatieOuder;
    }

    @Override
    public HisBetrokkenheidOuder clone() throws CloneNotSupportedException {
        // super.clone maakt al een shallow copy van het gehele object
        HisBetrokkenheidOuder copy = (HisBetrokkenheidOuder) super.clone();

        // Zet enkele zaken specifiek op null.
        copy.setId(null);
        copy.setDatumTijdRegistratie(null);
        copy.setDatumTijdVerval(null);

        return copy;
    }
}
