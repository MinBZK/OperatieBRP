/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.lev;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.bevraging.domein.Persoon;


/**
 * Representeert een levering van gegevens van een persoon.
 */
@Entity
@Table(name = "levpers", schema = "lev")
@Access(AccessType.FIELD)
public class LeveringPersoon implements Serializable {

    private static final String SEQUENCE_GENERATOR_NAME = "LEVPERS_SEQUENCE_GENERATOR";

    @SequenceGenerator(name = SEQUENCE_GENERATOR_NAME, sequenceName = "lev.seq_levpers")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
    private Long id;

    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "pers")
    private Persoon  persoon;

    @ManyToOne
    @JoinColumn(name = "lev")
    private Levering levering;

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public Levering getLevering() {
        return levering;
    }

    public void setLevering(final Levering levering) {
        this.levering = levering;
    }

}
