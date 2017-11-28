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
 * The persistent class for the his_persoverlijden database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persoverlijden", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg"}))
public class PersoonOverlijdenHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persoverlijden_id_generator", sequenceName = "kern.seq_his_persoverlijden", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persoverlijden_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "blplaatsoverlijden", length = 40)
    private String buitenlandsePlaatsOverlijden;

    @Column(name = "blregiooverlijden", length = 35)
    private String buitenlandseRegioOverlijden;

    @Column(name = "datoverlijden", nullable = false)
    private int datumOverlijden;

    @Column(name = "omslocoverlijden", length = 40)
    private String omschrijvingLocatieOverlijden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedoverlijden", nullable = false)
    private LandOfGebied landOfGebiedOverlijden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemoverlijden")
    private Gemeente gemeenteOverlijden;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "wplnaamoverlijden")
    private String woonplaatsnaamOverlijden;

    /**
     * JPA default constructor.
     */
    protected PersoonOverlijdenHistorie() {}

    /**
     * Maak een nieuwe persoon overlijden historie.
     *
     * @param persoon persoon
     * @param datumOverlijden datum overlijden
     * @param landOfGebied land of gebied
     */
    public PersoonOverlijdenHistorie(final Persoon persoon, final int datumOverlijden, final LandOfGebied landOfGebied) {
        this.datumOverlijden = datumOverlijden;
        setPersoon(persoon);
        setLandOfGebied(landOfGebied);
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
     * Zet de waarden voor id van PersoonOverlijdenHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonOverlijdenHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van buitenlandse plaats overlijden van PersoonOverlijdenHistorie.
     *
     * @return de waarde van buitenlandse plaats overlijden van PersoonOverlijdenHistorie
     */
    public String getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    /**
     * Zet de waarden voor buitenlandse plaats overlijden van PersoonOverlijdenHistorie.
     *
     * @param buitenlandsePlaatsOverlijden de nieuwe waarde voor buitenlandse plaats overlijden van
     *        PersoonOverlijdenHistorie
     */
    public void setBuitenlandsePlaatsOverlijden(final String buitenlandsePlaatsOverlijden) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsePlaatsOverlijden mag geen lege string zijn", buitenlandsePlaatsOverlijden);
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    /**
     * Geef de waarde van buitenlandse regio overlijden van PersoonOverlijdenHistorie.
     *
     * @return de waarde van buitenlandse regio overlijden van PersoonOverlijdenHistorie
     */
    public String getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    /**
     * Zet de waarden voor buitenlandse regio overlijden van PersoonOverlijdenHistorie.
     *
     * @param buitenlandseRegioOverlijden de nieuwe waarde voor buitenlandse regio overlijden van
     *        PersoonOverlijdenHistorie
     */
    public void setBuitenlandseRegioOverlijden(final String buitenlandseRegioOverlijden) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandseRegioOverlijden mag geen lege string zijn", buitenlandseRegioOverlijden);
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
    }

    /**
     * Geef de waarde van datum overlijden van PersoonOverlijdenHistorie.
     *
     * @return de waarde van datum overlijden van PersoonOverlijdenHistorie
     */
    public int getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * Zet de waarden voor datum overlijden van PersoonOverlijdenHistorie.
     *
     * @param datumOverlijden de nieuwe waarde voor datum overlijden van PersoonOverlijdenHistorie
     */
    public void setDatumOverlijden(final int datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    /**
     * Geef de waarde van omschrijving locatie overlijden van PersoonOverlijdenHistorie.
     *
     * @return de waarde van omschrijving locatie overlijden van PersoonOverlijdenHistorie
     */
    public String getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    /**
     * Zet de waarden voor omschrijving locatie overlijden van PersoonOverlijdenHistorie.
     *
     * @param omschrijvingLocatieOverlijden de nieuwe waarde voor omschrijving locatie overlijden
     *        van PersoonOverlijdenHistorie
     */
    public void setOmschrijvingLocatieOverlijden(final String omschrijvingLocatieOverlijden) {
        ValidationUtils.controleerOpLegeWaarden("omschrijvingLocatieOverlijden mag geen lege string zijn", omschrijvingLocatieOverlijden);
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
    }

    /**
     * Geef de waarde van land of gebied van PersoonOverlijdenHistorie.
     *
     * @return de waarde van land of gebied van PersoonOverlijdenHistorie
     */
    public LandOfGebied getLandOfGebied() {
        return landOfGebiedOverlijden;
    }

    /**
     * Zet de waarden voor land of gebied van PersoonOverlijdenHistorie.
     *
     * @param landOfGebied de nieuwe waarde voor land of gebied van PersoonOverlijdenHistorie
     */
    public void setLandOfGebied(final LandOfGebied landOfGebied) {
        ValidationUtils.controleerOpNullWaarden("landOfGebied mag niet null zijn", landOfGebied);
        landOfGebiedOverlijden = landOfGebied;
    }

    /**
     * Geef de waarde van gemeente van PersoonOverlijdenHistorie.
     *
     * @return de waarde van gemeente van PersoonOverlijdenHistorie
     */
    public Gemeente getGemeente() {
        return gemeenteOverlijden;
    }

    /**
     * Zet de waarden voor gemeente van PersoonOverlijdenHistorie.
     *
     * @param gemeente de nieuwe waarde voor gemeente van PersoonOverlijdenHistorie
     */
    public void setGemeente(final Gemeente gemeente) {
        gemeenteOverlijden = gemeente;
    }

    /**
     * Geef de waarde van persoon van PersoonOverlijdenHistorie.
     *
     * @return de waarde van persoon van PersoonOverlijdenHistorie
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonOverlijdenHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonOverlijdenHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van woonplaatsnaam overlijden van PersoonOverlijdenHistorie.
     *
     * @return de waarde van woonplaatsnaam overlijden van PersoonOverlijdenHistorie
     */
    public String getWoonplaatsnaamOverlijden() {
        return woonplaatsnaamOverlijden;
    }

    /**
     * Zet de waarden voor woonplaatsnaam overlijden van PersoonOverlijdenHistorie.
     *
     * @param woonplaats de nieuwe waarde voor woonplaatsnaam overlijden van
     *        PersoonOverlijdenHistorie
     */
    public void setWoonplaatsnaamOverlijden(final String woonplaats) {
        woonplaatsnaamOverlijden = woonplaats;
    }
}
