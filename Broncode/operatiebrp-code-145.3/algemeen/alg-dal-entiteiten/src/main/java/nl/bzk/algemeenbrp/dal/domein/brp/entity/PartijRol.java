/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.util.Geldigheid;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the partijrol database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "partijrol", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"partij", "rol"}))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Access(AccessType.FIELD)
public class PartijRol extends AbstractEntiteit implements Afleidbaar, Serializable, DynamischeStamtabel, Geldigheid {
    private static final long serialVersionUID = 1L;
    private static final String PARTIJ_MAG_NIET_NULL_ZIJN = "partij mag niet null zijn";

    @Id
    @SequenceGenerator(name = "partijrol_id_generator", sequenceName = "kern.seq_partijrol", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partijrol_id_generator")
    @Column(nullable = false, updatable = false)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "rol", nullable = false)
    private int rolId;

    @Column(name = "datingang")
    private Integer datumIngang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partijRol", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<PartijRolHistorie> partijRolHistorieSet = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected PartijRol() {}

    /**
     * Maak een nieuwe partij rol.
     *
     * @param partij partij
     * @param rol rol
     */
    public PartijRol(final Partij partij, final Rol rol) {
        ValidationUtils.controleerOpNullWaarden(PARTIJ_MAG_NIET_NULL_ZIJN, partij);
        this.partij = partij;
        setRol(rol);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van PartijRol.
     *
     * @param id de nieuwe waarde voor id van PartijRol
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van partij van PartijRol.
     *
     * @return de waarde van partij van PartijRol
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarden voor partij van PartijRol.
     *
     * @param partij de nieuwe waarde voor partij van PartijRol
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden(PARTIJ_MAG_NIET_NULL_ZIJN, partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van rol van PartijRol.
     *
     * @return de waarde van rol van PartijRol
     */
    public Rol getRol() {
        return Rol.parseId(rolId);
    }

    /**
     * Zet de waarden voor rol van PartijRol.
     *
     * @param rol de nieuwe waarde voor rol van PartijRol
     */
    public void setRol(final Rol rol) {
        ValidationUtils.controleerOpNullWaarden("rol mag niet null zijn", rol);
        rolId = rol.getId();
    }

    /**
     * Geef de waarde van datum ingang van PartijRol.
     *
     * @return de waarde van datum ingang van PartijRol
     */
    public Integer getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van PartijRol.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van PartijRol
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van datum einde van PartijRol.
     *
     * @return de waarde van datum einde van PartijRol
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Geef de waarde van isActueelEnGeldig.
     *
     * @return isActueelEnGeldig
     */
    public boolean isActueelEnGeldig() {
        return isActueelEnGeldig;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     *
     * @param actueelEnGeldig isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }

    /**
     * Zet de waarden voor datum einde van PartijRol.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van PartijRol
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van partij bijhouding historie set van PartijRol.
     *
     * @return de waarde van partij bijhouding historie set van PartijRol
     */
    public Set<PartijRolHistorie> getPartijBijhoudingHistorieSet() {
        return partijRolHistorieSet;
    }

    /**
     * Toevoegen van een partijRolHistorie.
     *
     * @param partijRolHistorie partijRolHistorie
     */
    public void addPartijBijhoudingHistorie(final PartijRolHistorie partijRolHistorie) {
        partijRolHistorie.setPartijRol(this);
        partijRolHistorieSet.add(partijRolHistorie);
    }

    /**
     * Zet de waarden voor partijrol historie set van PartijRol.
     *
     * @param partijRolHistorieSet de nieuwe waarde voor partijrol historie set van PartijRol
     */
    public void setPartijRolHistorieSet(final Set<PartijRolHistorie> partijRolHistorieSet) {
        this.partijRolHistorieSet = partijRolHistorieSet;
    }

}
