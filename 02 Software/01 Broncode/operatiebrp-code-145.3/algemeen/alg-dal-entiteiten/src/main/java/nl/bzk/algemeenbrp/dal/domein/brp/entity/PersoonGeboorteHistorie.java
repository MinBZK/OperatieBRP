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
 * The persistent class for the his_persgeboorte database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persgeboorte", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg"}))
public class PersoonGeboorteHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persgeboorte_id_generator", sequenceName = "kern.seq_his_persgeboorte", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persgeboorte_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "blplaatsgeboorte", length = 40)
    private String buitenlandsePlaatsGeboorte;

    @Column(name = "blregiogeboorte", length = 35)
    private String buitenlandseRegioGeboorte;

    @Column(name = "datgeboorte", nullable = false)
    private int datumGeboorte;

    @Column(name = "omslocgeboorte", length = 40)
    private String omschrijvingGeboortelocatie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedgeboorte", nullable = false)
    private LandOfGebied landOfGebiedGeboorte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemgeboorte")
    private Gemeente gemeenteGeboorte;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "wplnaamgeboorte")
    private String woonplaatsnaamGeboorte;

    /**
     * JPA default constructor.
     */
    protected PersoonGeboorteHistorie() {}

    /**
     * Maak een nieuwe persoon geboorte historie.
     *
     * @param persoon persoon
     * @param datumGeboorte datum geboorte
     * @param landOfGebied land of gebied
     */
    public PersoonGeboorteHistorie(final Persoon persoon, final int datumGeboorte, final LandOfGebied landOfGebied) {
        this.datumGeboorte = datumGeboorte;
        setPersoon(persoon);
        setLandOfGebied(landOfGebied);
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public PersoonGeboorteHistorie(final PersoonGeboorteHistorie ander) {
        super(ander);
        gemeenteGeboorte = ander.getGemeente();
        buitenlandsePlaatsGeboorte = ander.getBuitenlandsePlaatsGeboorte();
        woonplaatsnaamGeboorte = ander.getWoonplaatsnaamGeboorte();
        landOfGebiedGeboorte = ander.getLandOfGebied();
        omschrijvingGeboortelocatie = ander.getOmschrijvingGeboortelocatie();
        buitenlandseRegioGeboorte = ander.getBuitenlandseRegioGeboorte();
        datumGeboorte = ander.getDatumGeboorte();
        persoon = ander.getPersoon();
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
     * Zet de waarden voor id van PersoonGeboorteHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonGeboorteHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van buitenlandse plaats geboorte van PersoonGeboorteHistorie.
     *
     * @return de waarde van buitenlandse plaats geboorte van PersoonGeboorteHistorie
     */
    public String getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * Zet de waarden voor buitenlandse plaats geboorte van PersoonGeboorteHistorie.
     *
     * @param buitenlandsePlaatsGeboorte de nieuwe waarde voor buitenlandse plaats geboorte van
     *        PersoonGeboorteHistorie
     */
    public void setBuitenlandsePlaatsGeboorte(final String buitenlandsePlaatsGeboorte) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsePlaatsGeboorte mag geen lege string zijn", buitenlandsePlaatsGeboorte);
        this.buitenlandsePlaatsGeboorte = buitenlandsePlaatsGeboorte;
    }

    /**
     * Geef de waarde van buitenlandse regio geboorte van PersoonGeboorteHistorie.
     *
     * @return de waarde van buitenlandse regio geboorte van PersoonGeboorteHistorie
     */
    public String getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    /**
     * Zet de waarden voor buitenlandse regio geboorte van PersoonGeboorteHistorie.
     *
     * @param buitenlandseRegioGeboorte de nieuwe waarde voor buitenlandse regio geboorte van
     *        PersoonGeboorteHistorie
     */
    public void setBuitenlandseRegioGeboorte(final String buitenlandseRegioGeboorte) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandseRegioGeboorte mag geen lege string zijn", buitenlandseRegioGeboorte);
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
    }

    /**
     * Geef de waarde van datum geboorte van PersoonGeboorteHistorie.
     *
     * @return de waarde van datum geboorte van PersoonGeboorteHistorie
     */
    public int getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * Zet de waarden voor datum geboorte van PersoonGeboorteHistorie.
     *
     * @param datumGeboorte de nieuwe waarde voor datum geboorte van PersoonGeboorteHistorie
     */
    public void setDatumGeboorte(final int datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    /**
     * Geef de waarde van omschrijving geboortelocatie van PersoonGeboorteHistorie.
     *
     * @return de waarde van omschrijving geboortelocatie van PersoonGeboorteHistorie
     */
    public String getOmschrijvingGeboortelocatie() {
        return omschrijvingGeboortelocatie;
    }

    /**
     * Zet de waarden voor omschrijving geboortelocatie van PersoonGeboorteHistorie.
     *
     * @param omschrijvingGeboortelocatie de nieuwe waarde voor omschrijving geboortelocatie van
     *        PersoonGeboorteHistorie
     */
    public void setOmschrijvingGeboortelocatie(final String omschrijvingGeboortelocatie) {
        ValidationUtils.controleerOpLegeWaarden("omschrijvingGeboortelocatie mag geen lege string zijn", omschrijvingGeboortelocatie);
        this.omschrijvingGeboortelocatie = omschrijvingGeboortelocatie;
    }

    /**
     * Geef de waarde van land of gebied van PersoonGeboorteHistorie.
     *
     * @return de waarde van land of gebied van PersoonGeboorteHistorie
     */
    public LandOfGebied getLandOfGebied() {
        return landOfGebiedGeboorte;
    }

    /**
     * Zet de waarden voor land of gebied van PersoonGeboorteHistorie.
     *
     * @param landOfGebied de nieuwe waarde voor land of gebied van PersoonGeboorteHistorie
     */
    public void setLandOfGebied(final LandOfGebied landOfGebied) {
        ValidationUtils.controleerOpNullWaarden("landOfGebied mag niet null zijn", landOfGebied);
        landOfGebiedGeboorte = landOfGebied;
    }

    /**
     * Geef de waarde van gemeente van PersoonGeboorteHistorie.
     *
     * @return de waarde van gemeente van PersoonGeboorteHistorie
     */
    public Gemeente getGemeente() {
        return gemeenteGeboorte;
    }

    /**
     * Zet de waarden voor gemeente van PersoonGeboorteHistorie.
     *
     * @param gemeente de nieuwe waarde voor gemeente van PersoonGeboorteHistorie
     */
    public void setGemeente(final Gemeente gemeente) {
        gemeenteGeboorte = gemeente;
    }

    /**
     * Geef de waarde van persoon van PersoonGeboorteHistorie.
     *
     * @return de waarde van persoon van PersoonGeboorteHistorie
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonGeboorteHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonGeboorteHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van woonplaatsnaam geboorte van PersoonGeboorteHistorie.
     *
     * @return de waarde van woonplaatsnaam geboorte van PersoonGeboorteHistorie
     */
    public String getWoonplaatsnaamGeboorte() {
        return woonplaatsnaamGeboorte;
    }

    /**
     * Zet de waarden voor woonplaatsnaam geboorte van PersoonGeboorteHistorie.
     *
     * @param woonplaatsnaam de nieuwe waarde voor woonplaatsnaam geboorte van
     *        PersoonGeboorteHistorie
     */
    public void setWoonplaatsnaamGeboorte(final String woonplaatsnaam) {
        woonplaatsnaamGeboorte = woonplaatsnaam;
    }

    /**
     * Maakt een (shallow) kopie van deze materiele historie entiteit. De id waarde van de kopie
     * moet null zijn.
     *
     * @return de kopie
     */
    public final PersoonGeboorteHistorie kopieer() {
        return new PersoonGeboorteHistorie(this);
    }
}
