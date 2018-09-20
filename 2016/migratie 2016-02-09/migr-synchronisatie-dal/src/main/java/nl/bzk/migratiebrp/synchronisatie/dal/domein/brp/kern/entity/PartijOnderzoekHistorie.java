/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

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

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_partijonderzoek database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_partijonderzoek", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"partijonderzoek", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PartijOnderzoekHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_partijonderzoek_id_generator", sequenceName = "kern.seq_his_partijonderzoek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_partijonderzoek_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    // bi-directional many-to-one association to PartijOnderzoek
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partijonderzoek", nullable = false)
    private PartijOnderzoek partijOnderzoek;

    @Column(name = "rol", nullable = false)
    private short soortPartijOnderzoekId;

    /**
     * JPA default constructor.
     */
    protected PartijOnderzoekHistorie() {
    }

    /**
     * Maak een nieuwe partij onderzoek historie.
     *
     * @param partijOnderzoek
     *            partij onderzoek
     * @param soortPartijOnderzoek
     *            soort partij onderzoek
     */
    public PartijOnderzoekHistorie(final PartijOnderzoek partijOnderzoek, final SoortPartijOnderzoek soortPartijOnderzoek) {
        setPartijOnderzoek(partijOnderzoek);
        setSoortPartijOnderzoek(soortPartijOnderzoek);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van partij onderzoek.
     *
     * @return partij onderzoek
     */
    public PartijOnderzoek getPartijOnderzoek() {
        return partijOnderzoek;
    }

    /**
     * Zet de waarde van partij onderzoek.
     *
     * @param partijOnderzoek
     *            partij onderzoek
     */
    public void setPartijOnderzoek(final PartijOnderzoek partijOnderzoek) {
        ValidationUtils.controleerOpNullWaarden("partijOnderzoek mag niet null zijn", partijOnderzoek);
        this.partijOnderzoek = partijOnderzoek;
    }

    /**
     * Geef de waarde van soort partij onderzoek.
     *
     * @return soort partij onderzoek
     */
    public SoortPartijOnderzoek getSoortPartijOnderzoek() {
        return SoortPartijOnderzoek.parseId(soortPartijOnderzoekId);
    }

    /**
     * Zet de waarde van soort partij onderzoek.
     *
     * @param soortPartijOnderzoek
     *            soort partij onderzoek
     */
    public void setSoortPartijOnderzoek(final SoortPartijOnderzoek soortPartijOnderzoek) {
        ValidationUtils.controleerOpNullWaarden("soortPartijOnderzoek mag niet null zijn", soortPartijOnderzoek);
        soortPartijOnderzoekId = soortPartijOnderzoek.getId();
    }
}
