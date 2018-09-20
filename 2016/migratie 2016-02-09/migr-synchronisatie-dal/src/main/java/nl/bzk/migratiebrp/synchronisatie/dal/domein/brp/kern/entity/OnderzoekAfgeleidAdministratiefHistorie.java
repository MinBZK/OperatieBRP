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
 * The persistent class for the his_onderzoekafgeleidadminis database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_onderzoekafgeleidadminis", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"onderzoek", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class OnderzoekAfgeleidAdministratiefHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_onderzoekafgeleidadminis_id_generator", sequenceName = "kern.seq_his_onderzoekafgeleidadminis", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_onderzoekafgeleidadminis_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "onderzoek", nullable = false)
    private Onderzoek onderzoek;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "admhnd", nullable = false)
    private AdministratieveHandeling administratieveHandeling;

    /**
     * JPA default constructor.
     */
    protected OnderzoekAfgeleidAdministratiefHistorie() {
    }

    /**
     * Maak een nieuwe onderzoek afgeleid administratief historie.
     *
     * @param onderzoek
     *            onderzoek
     * @param administratieveHandeling
     *            administratieve handeling
     */
    public OnderzoekAfgeleidAdministratiefHistorie(final Onderzoek onderzoek, final AdministratieveHandeling administratieveHandeling) {
        setOnderzoek(onderzoek);
        setAdministratieveHandeling(administratieveHandeling);

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
     * Geef de waarde van onderzoek.
     *
     * @return onderzoek
     */
    public Onderzoek getOnderzoek() {
        return onderzoek;
    }

    /**
     * Zet de waarde van onderzoek.
     *
     * @param onderzoek
     *            onderzoek
     */
    public void setOnderzoek(final Onderzoek onderzoek) {
        ValidationUtils.controleerOpNullWaarden("onderzoek mag niet null zijn", onderzoek);
        this.onderzoek = onderzoek;
    }

    /**
     * Geef de waarde van administratieve handeling.
     *
     * @return administratieve handeling
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de waarde van administratieve handeling.
     *
     * @param administratieveHandeling
     *            administratieve handeling
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        ValidationUtils.controleerOpNullWaarden("administratieveHandeling mag niet null zijn", administratieveHandeling);
        this.administratieveHandeling = administratieveHandeling;
    }
}
