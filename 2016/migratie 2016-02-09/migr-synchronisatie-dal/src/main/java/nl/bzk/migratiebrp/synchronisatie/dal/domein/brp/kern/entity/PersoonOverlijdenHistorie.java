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
 * The persistent class for the his_persoverlijden database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persoverlijden", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonOverlijdenHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persoverlijden_id_generator", sequenceName = "kern.seq_his_persoverlijden", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persoverlijden_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "blplaatsoverlijden", length = 40)
    private String buitenlandsePlaatsOverlijden;

    @Column(name = "blregiooverlijden", length = 35)
    private String buitenlandseRegioOverlijden;

    @Column(name = "datoverlijden", nullable = false)
    private int datumOverlijden;

    @Column(name = "omslocoverlijden", length = 40)
    private String omschrijvingLocatieOverlijden;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedoverlijden", nullable = false)
    private LandOfGebied landOfGebied;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemoverlijden")
    private Gemeente gemeente;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "wplnaamoverlijden")
    private String woonplaatsnaamOverlijden;

    /**
     * JPA default constructor.
     */
    protected PersoonOverlijdenHistorie() {
    }

    /**
     * Maak een nieuwe persoon overlijden historie.
     *
     * @param persoon
     *            persoon
     * @param datumOverlijden
     *            datum overlijden
     * @param landOfGebied
     *            land of gebied
     */
    public PersoonOverlijdenHistorie(final Persoon persoon, final int datumOverlijden, final LandOfGebied landOfGebied) {
        this.datumOverlijden = datumOverlijden;
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
     * Geef de waarde van buitenlandse plaats overlijden.
     *
     * @return buitenlandse plaats overlijden
     */
    public String getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    /**
     * Zet de waarde van buitenlandse plaats overlijden.
     *
     * @param buitenlandsePlaatsOverlijden
     *            buitenlandse plaats overlijden
     */
    public void setBuitenlandsePlaatsOverlijden(final String buitenlandsePlaatsOverlijden) {
        Validatie.controleerOpLegeWaarden("buitenlandsePlaatsOverlijden mag geen lege string zijn", buitenlandsePlaatsOverlijden);
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    /**
     * Geef de waarde van buitenlandse regio overlijden.
     *
     * @return buitenlandse regio overlijden
     */
    public String getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    /**
     * Zet de waarde van buitenlandse regio overlijden.
     *
     * @param buitenlandseRegioOverlijden
     *            buitenlandse regio overlijden
     */
    public void setBuitenlandseRegioOverlijden(final String buitenlandseRegioOverlijden) {
        Validatie.controleerOpLegeWaarden("buitenlandseRegioOverlijden mag geen lege string zijn", buitenlandseRegioOverlijden);
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
    }

    /**
     * Geef de waarde van datum overlijden.
     *
     * @return datum overlijden
     */
    public int getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * Zet de waarde van datum overlijden.
     *
     * @param datumOverlijden
     *            datum overlijden
     */
    public void setDatumOverlijden(final int datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    /**
     * Geef de waarde van omschrijving locatie overlijden.
     *
     * @return omschrijving locatie overlijden
     */
    public String getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    /**
     * Zet de waarde van omschrijving locatie overlijden.
     *
     * @param omschrijvingLocatieOverlijden
     *            omschrijving locatie overlijden
     */
    public void setOmschrijvingLocatieOverlijden(final String omschrijvingLocatieOverlijden) {
        Validatie.controleerOpLegeWaarden("omschrijvingLocatieOverlijden mag geen lege string zijn", omschrijvingLocatieOverlijden);
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
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
     * Geef de waarde van woonplaatsnaam overlijden.
     *
     * @return woonplaatsnaam overlijden
     */
    public String getWoonplaatsnaamOverlijden() {
        return woonplaatsnaamOverlijden;
    }

    /**
     * Zet de waarde van woonplaatsnaam overlijden.
     *
     * @param woonplaats
     *            woonplaatsnaam overlijden
     */
    public void setWoonplaatsnaamOverlijden(final String woonplaats) {
        woonplaatsnaamOverlijden = woonplaats;
    }
}
