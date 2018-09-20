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

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_persvoornaam database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persvoornaam", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persvoornaam", "tsreg", "dataanvgel" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonVoornaamHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String PERSOON_VOORNAAM_MAG_NIET_NULL_ZIJN = "persoonVoornaam mag niet null zijn";
    private static final String NAAM_MAG_NIET_NULL_ZIJN = "naam mag niet null zijn";
    private static final String NAAM_MAG_GEEN_LEGE_STRING_ZIJN = "naam mag geen lege string zijn";

    @Id
    @SequenceGenerator(name = "his_persvoornaam_id_generator", sequenceName = "kern.seq_his_persvoornaam", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persvoornaam_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(nullable = false, length = 200)
    private String naam;

    // bi-directional many-to-one association to PersoonVoornaam
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "persvoornaam", nullable = false)
    private PersoonVoornaam persoonVoornaam;

    /**
     * JPA default constructor.
     */
    protected PersoonVoornaamHistorie() {
    }

    /**
     * Maak een nieuwe persoon voornaam historie.
     *
     * @param persoonVoornaam
     *            persoon voornaam
     * @param naam
     *            naam
     */
    public PersoonVoornaamHistorie(final PersoonVoornaam persoonVoornaam, final String naam) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_VOORNAAM_MAG_NIET_NULL_ZIJN, persoonVoornaam);
        this.persoonVoornaam = persoonVoornaam;
        ValidationUtils.controleerOpNullWaarden(NAAM_MAG_NIET_NULL_ZIJN, naam);
        Validatie.controleerOpLegeWaarden(NAAM_MAG_GEEN_LEGE_STRING_ZIJN, naam);
        this.naam = naam;
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
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden(NAAM_MAG_NIET_NULL_ZIJN, naam);
        Validatie.controleerOpLegeWaarden(NAAM_MAG_GEEN_LEGE_STRING_ZIJN, naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van persoon voornaam.
     *
     * @return persoon voornaam
     */
    public PersoonVoornaam getPersoonVoornaam() {
        return persoonVoornaam;
    }

    /**
     * Zet de waarde van persoon voornaam.
     *
     * @param persoonVoornaam
     *            persoon voornaam
     */
    public void setPersoonVoornaam(final PersoonVoornaam persoonVoornaam) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_VOORNAAM_MAG_NIET_NULL_ZIJN, persoonVoornaam);
        this.persoonVoornaam = persoonVoornaam;
    }
}
