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
 * The persistent class for the his_persgeboorte database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persgeboorte", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonGeboorteHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persgeboorte_id_generator", sequenceName = "kern.seq_his_persgeboorte", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persgeboorte_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "blplaatsgeboorte", length = 40)
    private String buitenlandsePlaatsGeboorte;

    @Column(name = "blregiogeboorte", length = 35)
    private String buitenlandseRegioGeboorte;

    @Column(name = "datgeboorte", nullable = false)
    private int datumGeboorte;

    @Column(name = "omslocgeboorte", length = 40)
    private String omschrijvingGeboortelocatie;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedgeboorte", nullable = false)
    private LandOfGebied landOfGebied;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemgeboorte")
    private Gemeente gemeente;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "wplnaamgeboorte")
    private String woonplaatsnaamGeboorte;

    /**
     * JPA default constructor.
     */
    protected PersoonGeboorteHistorie() {
    }

    /**
     * Maak een nieuwe persoon geboorte historie.
     *
     * @param persoon
     *            persoon
     * @param datumGeboorte
     *            datum geboorte
     * @param landOfGebied
     *            land of gebied
     */
    public PersoonGeboorteHistorie(final Persoon persoon, final int datumGeboorte, final LandOfGebied landOfGebied) {
        this.datumGeboorte = datumGeboorte;
        setPersoon(persoon);
        setLandOfGebied(landOfGebied);
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
     * Geef de waarde van buitenlandse plaats geboorte.
     *
     * @return buitenlandse plaats geboorte
     */
    public String getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * Zet de waarde van buitenlandse plaats geboorte.
     *
     * @param buitenlandsePlaatsGeboorte
     *            buitenlandse plaats geboorte
     */
    public void setBuitenlandsePlaatsGeboorte(final String buitenlandsePlaatsGeboorte) {
        Validatie.controleerOpLegeWaarden("buitenlandsePlaatsGeboorte mag geen lege string zijn", buitenlandsePlaatsGeboorte);
        this.buitenlandsePlaatsGeboorte = buitenlandsePlaatsGeboorte;
    }

    /**
     * Geef de waarde van buitenlandse regio geboorte.
     *
     * @return buitenlandse regio geboorte
     */
    public String getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    /**
     * Zet de waarde van buitenlandse regio geboorte.
     *
     * @param buitenlandseRegioGeboorte
     *            buitenlandse regio geboorte
     */
    public void setBuitenlandseRegioGeboorte(final String buitenlandseRegioGeboorte) {
        Validatie.controleerOpLegeWaarden("buitenlandseRegioGeboorte mag geen lege string zijn", buitenlandseRegioGeboorte);
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
    }

    /**
     * Geef de waarde van datum geboorte.
     *
     * @return datum geboorte
     */
    public Integer getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * Zet de waarde van datum geboorte.
     *
     * @param datumGeboorte
     *            datum geboorte
     */
    public void setDatumGeboorte(final Integer datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    /**
     * Geef de waarde van omschrijving geboortelocatie.
     *
     * @return omschrijving geboortelocatie
     */
    public String getOmschrijvingGeboortelocatie() {
        return omschrijvingGeboortelocatie;
    }

    /**
     * Zet de waarde van omschrijving geboortelocatie.
     *
     * @param omschrijvingGeboortelocatie
     *            omschrijving geboortelocatie
     */
    public void setOmschrijvingGeboortelocatie(final String omschrijvingGeboortelocatie) {
        Validatie.controleerOpLegeWaarden("omschrijvingGeboortelocatie mag geen lege string zijn", omschrijvingGeboortelocatie);
        this.omschrijvingGeboortelocatie = omschrijvingGeboortelocatie;
    }

    /**
     * Geef de waarde van land of gebied.
     *
     * @return land of gebied
     */
    public LandOfGebied getLandOfGebied() {
        return landOfGebied;
    }

    /**
     * Zet de waarde van land of gebied.
     *
     * @param landOfGebied
     *            land of gebied
     */
    public void setLandOfGebied(final LandOfGebied landOfGebied) {
        ValidationUtils.controleerOpNullWaarden("landOfGebied mag niet null zijn", landOfGebied);
        this.landOfGebied = landOfGebied;
    }

    /**
     * Geef de waarde van gemeente.
     *
     * @return gemeente
     */
    public Gemeente getGemeente() {
        return gemeente;
    }

    /**
     * Zet de waarde van gemeente.
     *
     * @param gemeente
     *            gemeente
     */
    public void setGemeente(final Gemeente gemeente) {
        this.gemeente = gemeente;
    }

    /**
     * Geef de waarde van persoon.
     *
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarde van persoon.
     *
     * @param persoon
     *            persoon
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van woonplaatsnaam geboorte.
     *
     * @return woonplaatsnaam geboorte
     */
    public String getWoonplaatsnaamGeboorte() {
        return woonplaatsnaamGeboorte;
    }

    /**
     * Zet de waarde van woonplaatsnaam geboorte.
     *
     * @param woonplaatsnaam
     *            woonplaatsnaam geboorte
     */
    public void setWoonplaatsnaamGeboorte(final String woonplaatsnaam) {
        woonplaatsnaamGeboorte = woonplaatsnaam;
    }
}
