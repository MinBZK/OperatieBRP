/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Cacheable;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.DynamischeStamtabel;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the partijrol database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "partijrol", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"partij", "rol" }))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class PartijRol extends AbstractDeltaEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;
    private static final String PARTIJ_MAG_NIET_NULL_ZIJN = "partij mag niet null zijn";

    @Id
    @SequenceGenerator(name = "partijrol_id_generator", sequenceName = "kern.seq_partijrol", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partijrol_id_generator")
    @Column(nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "rol", nullable = false)
    private short rolId;

    @Column(name = "datingang", insertable = true, updatable = true)
    private Integer datumIngang;

    @Column(name = "dateinde", insertable = true, updatable = false)
    private Integer datumEinde;

    @OneToMany(mappedBy = "partijRol", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    private final Set<PartijRolHistorie> partijRolHistorieSet = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected PartijRol() {
    }

    /**
     * Maak een nieuwe partij rol.
     *
     * @param partij
     *            partij
     * @param rol
     *            rol
     */
    public PartijRol(final Partij partij, final Rol rol) {
        ValidationUtils.controleerOpNullWaarden(PARTIJ_MAG_NIET_NULL_ZIJN, partij);
        this.partij = partij;
        setRol(rol);
    }

    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarde van partij.
     *
     * @param partij
     *            partij
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden(PARTIJ_MAG_NIET_NULL_ZIJN, partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van rol.
     *
     * @return rol
     */
    public Rol getRol() {
        return Rol.parseId(rolId);
    }

    /**
     * Zet de waarde van rol.
     *
     * @param rol
     *            rol
     */
    public void setRol(final Rol rol) {
        ValidationUtils.controleerOpNullWaarden("rol mag niet null zijn", rol);
        rolId = rol.getId();
    }

    /**
     * Geef de waarde van datum ingang.
     *
     * @return datum ingang
     */
    public Integer getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarde van datum ingang.
     *
     * @param datumIngang
     *            datum ingang
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van datum einde.
     *
     * @return datum einde
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarde van datum einde.
     *
     * @param datumEinde
     *            datum einde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van partijRolHistorieSet.
     *
     * @return partijRolHistorieSet
     */
    public Set<PartijRolHistorie> getPartijBijhoudingHistorieSet() {
        return partijRolHistorieSet;
    }

    /**
     * Toevoegen van een partijRolHistorie.
     *
     * @param partijRolHistorie
     *            partijRolHistorie
     */
    public void addPartijBijhoudingHistorie(final PartijRolHistorie partijRolHistorie) {
        partijRolHistorie.setPartijRol(this);
        partijRolHistorieSet.add(partijRolHistorie);
    }
}
