/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_persverstrbeperking database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persverstrbeperking", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persverstrbeperking", "tsreg"}))
public class PersoonVerstrekkingsbeperkingHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persverstrbeperking_id_generator", sequenceName = "kern.seq_his_persverstrbeperking", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persverstrbeperking_id_generator")
    @Column(nullable = false)
    private Long id;

    // bi-directional many-to-one association to PersoonVerificatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "persverstrbeperking", nullable = false)
    private PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking;

    /**
     * JPA default constructor.
     */
    protected PersoonVerstrekkingsbeperkingHistorie() {}

    /**
     * Maak een nieuwe persoon verstrekkingsbeperking historie.
     *
     * @param persoonVerstrekkingsbeperking persoon verstrekkingsbeperking
     */
    public PersoonVerstrekkingsbeperkingHistorie(final PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking) {
        setPersoonVerstrekkingsbeperking(persoonVerstrekkingsbeperking);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van PersoonVerstrekkingsbeperkingHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonVerstrekkingsbeperkingHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van persoon verstrekkingsbeperking van PersoonVerstrekkingsbeperkingHistorie.
     *
     * @return de waarde van persoon verstrekkingsbeperking van
     *         PersoonVerstrekkingsbeperkingHistorie
     */
    public PersoonVerstrekkingsbeperking getPersoonVerstrekkingsbeperking() {
        return persoonVerstrekkingsbeperking;
    }

    /**
     * Zet de waarden voor persoon verstrekkingsbeperking van PersoonVerstrekkingsbeperkingHistorie.
     *
     * @param persoonVerstrekkingsbeperking de nieuwe waarde voor persoon verstrekkingsbeperking van
     *        PersoonVerstrekkingsbeperkingHistorie
     */
    public void setPersoonVerstrekkingsbeperking(final PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking) {
        ValidationUtils.controleerOpNullWaarden("persoonVerstrekkingsbeperking mag niet null zijn", persoonVerstrekkingsbeperking);
        this.persoonVerstrekkingsbeperking = persoonVerstrekkingsbeperking;
    }
}
