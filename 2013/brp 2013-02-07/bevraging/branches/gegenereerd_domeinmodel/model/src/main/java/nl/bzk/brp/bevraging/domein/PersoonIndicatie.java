/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

import java.io.Serializable;

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


/**
 * Indicaties bij een persoon.
 */
@Entity
@Table(name = "PersIndicatie", schema = "Kern")
@Access(AccessType.FIELD)
public class PersoonIndicatie implements Serializable {

    private static final String SEQUENCE_GENERATOR_NAME = "PERSOON_INDICATIE_SEQUENCE_GENERATOR";

    @SequenceGenerator(name = SEQUENCE_GENERATOR_NAME, sequenceName = "Kern.seq_PersIndicatie")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
    private Long           id;
    @ManyToOne
    @JoinColumn(name = "Pers", insertable = false, updatable = false)
    private Persoon        persoon;
    @Column(name = "Srt")
    private SoortIndicatie soort;
    @Column(name = "Waarde")
    private Boolean        waarde;

    /**
     * No-arg constructor voor JPA.
     */
    public PersoonIndicatie() {
    }

    /**
     * Constructor voor nieuwe instanties.
     *
     * @param persoon de persoon waarvoor de indicatie geldt.
     * @param soort het soort indicatie dat wordt gecreeerd.
     */
    public PersoonIndicatie(final Persoon persoon, final SoortIndicatie soort) {
        this.persoon = persoon;
        this.soort = soort;
    }

    public Long getId() {
        return id;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public SoortIndicatie getSoort() {
        return soort;
    }

    public void setSoort(final SoortIndicatie soort) {
        this.soort = soort;
    }

    public Boolean getWaarde() {
        return waarde;
    }

    public void setWaarde(final Boolean waarde) {
        this.waarde = waarde;
    }
}
