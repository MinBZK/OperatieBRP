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
 * The persistent class for the his_persids database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persids", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg", "dataanvgel"}))
public class PersoonIDHistorie extends AbstractMaterieleHistorie implements Serializable {

    /**
     * Melding indien een persoon null is.
     */
    public static final String PERSOON_MAG_NIET_NULL_ZIJN = "persoon mag niet null zijn";

    private static final long serialVersionUID = 1L;
    private static final int BSN_LENGTE = 9;
    private static final int ANUMMER_LENGTE = 10;

    @Id
    @SequenceGenerator(name = "his_persids_id_generator", sequenceName = "kern.seq_his_persids", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persids_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "anr", length = 10)
    private String administratienummer;

    @Column(name = "bsn", length = 9)
    private String burgerservicenummer;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonIDHistorie() {}

    /**
     * Maak een nieuwe persoon id historie.
     *
     * @param persoon persoon
     */
    public PersoonIDHistorie(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public PersoonIDHistorie(final PersoonIDHistorie ander) {
        super(ander);
        administratienummer = ander.getAdministratienummer();
        burgerservicenummer = ander.getBurgerservicenummer();
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
     * Zet de waarden voor id van PersoonIDHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonIDHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van administratienummer van PersoonIDHistorie.
     *
     * @return de waarde van administratienummer van PersoonIDHistorie
     */
    public String getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Zet de waarden voor administratienummer van PersoonIDHistorie.
     *
     * @param administratienummer de nieuwe waarde voor administratienummer van PersoonIDHistorie
     */
    public void setAdministratienummer(final String administratienummer) {
        if (administratienummer != null) {
            ValidationUtils.controleerOpLengte("administratienummer moet een lengte van 10 hebben", administratienummer, ANUMMER_LENGTE);
        }
        this.administratienummer = administratienummer;
    }

    /**
     * Geef de waarde van burgerservicenummer van PersoonIDHistorie.
     *
     * @return de waarde van burgerservicenummer van PersoonIDHistorie
     */
    public String getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Zet de waarden voor burgerservicenummer van PersoonIDHistorie.
     *
     * @param burgerservicenummer de nieuwe waarde voor burgerservicenummer van PersoonIDHistorie
     */
    public void setBurgerservicenummer(final String burgerservicenummer) {
        if (burgerservicenummer != null) {
            ValidationUtils.controleerOpLengte("burgerservicenummer moet een lengte van 9 hebben", burgerservicenummer, BSN_LENGTE);
        }
        this.burgerservicenummer = burgerservicenummer;
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonIDHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonIDHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }

    @Override
    public final PersoonIDHistorie kopieer() {
        return new PersoonIDHistorie(this);
    }
}
