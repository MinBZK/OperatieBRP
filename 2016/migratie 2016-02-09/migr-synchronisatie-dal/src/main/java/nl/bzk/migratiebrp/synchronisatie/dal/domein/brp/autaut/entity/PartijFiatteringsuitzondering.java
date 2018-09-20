/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;

/**
 * The persistent class for the partijfiatuitz database table.
 * 
 */
@Entity
@Table(name = "partijfiatuitz", schema = "autaut")
@NamedQuery(name = "PartijFiatteringsuitzondering.findAll", query = "SELECT p FROM PartijFiatteringsuitzondering p")
@SuppressWarnings("checkstyle:designforextension")
public class PartijFiatteringsuitzondering implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "partijfiatuitz_id_generator", sequenceName = "autaut.seq_partijfiatuitz", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partijfiatuitz_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang")
    private Integer datumIngang;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "partijbijhvoorstel")
    private Partij partijBijhoudingsvoorstel;

    @Column(name = "srtadmhnd")
    private Short soortAdministratieveHandelingId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "srtdoc")
    private SoortDocument soortDocument;

    // bi-directional many-to-one association to PartijFiatteringsuitzonderingHistorie
    @OneToMany(mappedBy = "partijFiatteringsuitzondering", cascade = CascadeType.ALL)
    private Set<PartijFiatteringsuitzonderingHistorie> partijFiatteringsuitzonderingHistorieSet;

    /**
     * JPA no-args constructor.
     */
    PartijFiatteringsuitzondering() {
    }

    /**
     * Maakt een nieuw PartijFiatteringsuitzondering object.
     *
     * @param partij
     *            partij
     */
    public PartijFiatteringsuitzondering(final Partij partij) {
        setPartij(partij);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
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
     * Geef de waarde van datumEinde.
     *
     * @return datumEinde
     */
    public Integer getDatumEinde() {
        return this.datumEinde;
    }

    /**
     * Zet de waarde van datumEinde.
     *
     * @param datumEinde
     *            datumEinde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datumIngang.
     *
     * @return datumIngang
     */
    public Integer getDatumIngang() {
        return this.datumIngang;
    }

    /**
     * Zet de waarde van datumIngang.
     *
     * @param datumIngang
     *            datumIngang
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van indicatieGeblokkeerd.
     *
     * @return indicatieGeblokkeerd
     */
    public Boolean getIndicatieGeblokkeerd() {
        return this.indicatieGeblokkeerd;
    }

    /**
     * Zet de waarde van indicatieGeblokkeerd.
     *
     * @param indicatieGeblokkeerd
     *            indicatieGeblokkeerd
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public Partij getPartij() {
        return this.partij;
    }

    /**
     * Zet de waarde van partij.
     *
     * @param partij
     *            partij
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van partijBijhoudingsvoorstel.
     *
     * @return partijBijhoudingsvoorstel
     */
    public Partij getPartijBijhoudingsvoorstel() {
        return this.partijBijhoudingsvoorstel;
    }

    /**
     * Zet de waarde van partijBijhoudingsvoorstel.
     *
     * @param partijBijhoudingsvoorstel
     *            partijBijhoudingsvoorstel
     */
    public void setPartijBijhoudingsvoorstel(final Partij partijBijhoudingsvoorstel) {
        this.partijBijhoudingsvoorstel = partijBijhoudingsvoorstel;
    }

    /**
     * Geef de waarde soortAdministratieveHandeling.
     *
     * @return soortAdministratieveHandeling
     */
    public SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        if (soortAdministratieveHandelingId == null) {
            return null;
        } else {
            return SoortAdministratieveHandeling.parseId(soortAdministratieveHandelingId);
        }
    }

    /**
     * Zet de waarde van soortAdministratieveHandeling.
     *
     * @param soortAdministratieveHandeling
     *            soortAdministratieveHandeling
     */
    public void setSoortAdministratieveHandeling(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        if (soortAdministratieveHandeling == null) {
            soortAdministratieveHandelingId = null;
        } else {
            soortAdministratieveHandelingId = soortAdministratieveHandeling.getId();
        }
    }

    /**
     * Geef de waarde van soortDocument.
     *
     * @return soortDocument
     */
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Zet de waarde van soortDocument.
     *
     * @param soortDocument
     *            soortDocument
     */
    public void setSoortDocument(final SoortDocument soortDocument) {
        this.soortDocument = soortDocument;
    }

    /**
     * Geef de waarde van partijFiatteringsuitzonderingHistorieSet.
     *
     * @return partijFiatteringsuitzonderingHistorieSet
     */
    public Set<PartijFiatteringsuitzonderingHistorie> getPartijFiatteringsuitzonderingHistorieSet() {
        return this.partijFiatteringsuitzonderingHistorieSet;
    }

    /**
     * Zet de waarde van partijFiatteringsuitzonderingHistorieSet.
     *
     * @param partijFiatteringsuitzonderingHistorieSet
     *            partijFiatteringsuitzonderingHistorieSet
     */
    public void setPartijFiatteringsuitzonderingHistorieSet(final Set<PartijFiatteringsuitzonderingHistorie> partijFiatteringsuitzonderingHistorieSet) {
        this.partijFiatteringsuitzonderingHistorieSet = partijFiatteringsuitzonderingHistorieSet;
    }

    /**
     * Voegt een partijFiatteringsuitzonderingHistorie toe aan partijFiatteringsuitzonderingHistorieSet.
     *
     * @param partijFiatteringsuitzonderingHistorie
     *            partijFiatteringsuitzonderingHistorie
     */
    public void addPartijFiatteringsuitzonderingHistorieSet(final PartijFiatteringsuitzonderingHistorie partijFiatteringsuitzonderingHistorie) {
        getPartijFiatteringsuitzonderingHistorieSet().add(partijFiatteringsuitzonderingHistorie);
        partijFiatteringsuitzonderingHistorie.setPartijFiatteringsuitzondering(this);
    }

    /**
     * Verwijderd een partijFiatteringsuitzonderingHistorie uit de partijFiatteringsuitzonderingHistorieSet.
     *
     * @param partijFiatteringsuitzonderingHistorie
     *            partijFiatteringsuitzonderingHistorie
     * @return true als partijFiatteringsuitzonderingHistorie is verwijderd, anders false
     */
    public boolean removePartijFiatteringsuitzonderingHistorieSet(final PartijFiatteringsuitzonderingHistorie partijFiatteringsuitzonderingHistorie) {
        final boolean result = getPartijFiatteringsuitzonderingHistorieSet().remove(partijFiatteringsuitzonderingHistorie);
        partijFiatteringsuitzonderingHistorie.setPartijFiatteringsuitzondering(null);
        return result;
    }

}
