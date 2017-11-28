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
 * The persistent class for the his_persverificatie database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persverificatie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persverificatie", "tsreg"}))
public class PersoonVerificatieHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persverificatie_id_generator", sequenceName = "kern.seq_his_persverificatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persverificatie_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "dat", nullable = false)
    private int datum;

    // bi-directional many-to-one association to PersoonVerificatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "persverificatie", nullable = false)
    private PersoonVerificatie persoonVerificatie;

    /**
     * JPA default constructor.
     */
    protected PersoonVerificatieHistorie() {}

    /**
     * Maak een nieuwe persoon verificatie historie.
     *
     * @param persoonVerificatie persoon verificatie
     * @param datum datum
     */
    public PersoonVerificatieHistorie(final PersoonVerificatie persoonVerificatie, final int datum) {
        setPersoonVerificatie(persoonVerificatie);
        this.datum = datum;
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
     * Zet de waarden voor id van PersoonVerificatieHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonVerificatieHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum van PersoonVerificatieHistorie.
     *
     * @return de waarde van datum van PersoonVerificatieHistorie
     */
    public Integer getDatum() {
        return datum;
    }

    /**
     * Zet de waarden voor datum van PersoonVerificatieHistorie.
     *
     * @param datum de nieuwe waarde voor datum van PersoonVerificatieHistorie
     */
    public void setDatum(final int datum) {
        this.datum = datum;
    }

    /**
     * Geef de waarde van persoon verificatie van PersoonVerificatieHistorie.
     *
     * @return de waarde van persoon verificatie van PersoonVerificatieHistorie
     */
    public PersoonVerificatie getPersoonVerificatie() {
        return persoonVerificatie;
    }

    /**
     * Zet de waarden voor persoon verificatie van PersoonVerificatieHistorie.
     *
     * @param persoonVerificatie de nieuwe waarde voor persoon verificatie van
     *        PersoonVerificatieHistorie
     */
    public void setPersoonVerificatie(final PersoonVerificatie persoonVerificatie) {
        ValidationUtils.controleerOpNullWaarden("persoonVerificatie mag niet null zijn", persoonVerificatie);
        this.persoonVerificatie = persoonVerificatie;
    }
}
