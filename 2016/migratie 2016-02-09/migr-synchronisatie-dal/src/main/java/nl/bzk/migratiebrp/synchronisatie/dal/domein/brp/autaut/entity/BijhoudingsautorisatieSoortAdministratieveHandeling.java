/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;

/**
 * The persistent class for the bijhautorisatiesrtadmhnd database table.
 * 
 */
@Entity
@Table(name = "bijhautorisatiesrtadmhnd", schema = "autaut",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"toegangbijhautorisatie", "srtadmhnd" }) })
@NamedQuery(name = "BijhoudingsautorisatieSoortAdministratieveHandeling.findAll",
        query = "SELECT b FROM BijhoudingsautorisatieSoortAdministratieveHandeling b")
@SuppressWarnings("checkstyle:designforextension")
public class BijhoudingsautorisatieSoortAdministratieveHandeling implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "bijhautorisatiesrtadmhnd_id_generator", sequenceName = "autaut.seq_bijhautorisatiesrtadmhnd", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bijhautorisatiesrtadmhnd_id_generator")
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(name = "srtadmhnd", nullable = false)
    private short soortAdministratieveHandelingId;

    // uni-directional many-to-one association to ToegangBijhoudingsautorisatie
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "toegangbijhautorisatie", nullable = false)
    private ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie;

    /**
     * JPA no-args constructor.
     */
    BijhoudingsautorisatieSoortAdministratieveHandeling() {
    }

    /**
     * Maak een nieuwe bijhoudingssituatie.
     *
     * @param toegangBijhoudingsautorisatie
     *            toegangBijhoudingsautorisatie
     * @param soortAdministratieveHandeling
     *            soortAdministratieveHandeling
     */
    public BijhoudingsautorisatieSoortAdministratieveHandeling(
        final ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        setToegangBijhoudingsautorisatie(toegangBijhoudingsautorisatie);
        setSoortAdministratieveHandeling(soortAdministratieveHandeling);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public final Integer getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public final void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van soort administratieve handeling.
     *
     * @return soort administratieve handeling
     */
    public final SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return SoortAdministratieveHandeling.parseId(soortAdministratieveHandelingId);
    }

    /**
     * Zet de waarde van soort administratieve handeling.
     *
     * @param soortAdministratieveHandeling
     *            soort administratieve handeling
     */
    public final void setSoortAdministratieveHandeling(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        ValidationUtils.controleerOpNullWaarden("soortAdministratieveHandeling mag niet null zijn", soortAdministratieveHandeling);
        soortAdministratieveHandelingId = soortAdministratieveHandeling.getId();
    }

    /**
     * Geef de waarde van toegangBijhoudingsautorisatie.
     *
     * @return toegangBijhoudingsautorisatie
     */
    public ToegangBijhoudingsautorisatie getToegangBijhoudingsautorisatie() {
        return toegangBijhoudingsautorisatie;
    }

    /**
     * Zet de waarde van toegangBijhoudingsautorisatie.
     *
     * @param toegangBijhoudingsautorisatie
     *            toegangBijhoudingsautorisatie
     */
    public void setToegangBijhoudingsautorisatie(final ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("toegangBijhoudingsautorisatie mag niet null zijn", toegangBijhoudingsautorisatie);
        this.toegangBijhoudingsautorisatie = toegangBijhoudingsautorisatie;
    }
}
