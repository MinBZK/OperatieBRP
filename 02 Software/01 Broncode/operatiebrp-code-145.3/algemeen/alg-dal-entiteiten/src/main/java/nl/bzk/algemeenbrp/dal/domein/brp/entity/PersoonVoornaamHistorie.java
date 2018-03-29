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
 * The persistent class for the his_persvoornaam database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persvoornaam", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persvoornaam", "tsreg", "dataanvgel"}))
public class PersoonVoornaamHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String PERSOON_VOORNAAM_MAG_NIET_NULL_ZIJN = "persoonVoornaam mag niet null zijn";
    private static final String NAAM_MAG_NIET_NULL_ZIJN = "naam mag niet null zijn";
    private static final String NAAM_MAG_GEEN_LEGE_STRING_ZIJN = "naam mag geen lege string zijn";

    @Id
    @SequenceGenerator(name = "his_persvoornaam_id_generator", sequenceName = "kern.seq_his_persvoornaam", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persvoornaam_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 200)
    private String naam;

    // bi-directional many-to-one association to PersoonVoornaam
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "persvoornaam", nullable = false)
    private PersoonVoornaam persoonVoornaam;

    /**
     * JPA default constructor.
     */
    protected PersoonVoornaamHistorie() {}

    /**
     * Maak een nieuwe persoon voornaam historie.
     *
     * @param persoonVoornaam persoon voornaam
     * @param naam naam
     */
    public PersoonVoornaamHistorie(final PersoonVoornaam persoonVoornaam, final String naam) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_VOORNAAM_MAG_NIET_NULL_ZIJN, persoonVoornaam);
        this.persoonVoornaam = persoonVoornaam;
        ValidationUtils.controleerOpNullWaarden(NAAM_MAG_NIET_NULL_ZIJN, naam);
        ValidationUtils.controleerOpLegeWaarden(NAAM_MAG_GEEN_LEGE_STRING_ZIJN, naam);
        this.naam = naam;
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public PersoonVoornaamHistorie(final PersoonVoornaamHistorie ander) {
        super(ander);
        naam = ander.getNaam();
        persoonVoornaam = ander.getPersoonVoornaam();
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
     * Zet de waarden voor id van PersoonVoornaamHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonVoornaamHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van naam van PersoonVoornaamHistorie.
     *
     * @return de waarde van naam van PersoonVoornaamHistorie
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van PersoonVoornaamHistorie.
     *
     * @param naam de nieuwe waarde voor naam van PersoonVoornaamHistorie
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden(NAAM_MAG_NIET_NULL_ZIJN, naam);
        ValidationUtils.controleerOpLegeWaarden(NAAM_MAG_GEEN_LEGE_STRING_ZIJN, naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van persoon voornaam van PersoonVoornaamHistorie.
     *
     * @return de waarde van persoon voornaam van PersoonVoornaamHistorie
     */
    public PersoonVoornaam getPersoonVoornaam() {
        return persoonVoornaam;
    }

    /**
     * Zet de waarden voor persoon voornaam van PersoonVoornaamHistorie.
     *
     * @param persoonVoornaam de nieuwe waarde voor persoon voornaam van PersoonVoornaamHistorie
     */
    public void setPersoonVoornaam(final PersoonVoornaam persoonVoornaam) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_VOORNAAM_MAG_NIET_NULL_ZIJN, persoonVoornaam);
        this.persoonVoornaam = persoonVoornaam;
    }

    @Override
    public final PersoonVoornaamHistorie kopieer() {
        return new PersoonVoornaamHistorie(this);
    }

    @Override
    public Persoon getPersoon() {
        return getPersoonVoornaam().getPersoon();
    }

    @Override
    public boolean isDegGelijkAanDagToegestaan() {
        return true;
    }
}
