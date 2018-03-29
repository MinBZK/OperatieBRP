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
 * The persistent class for the his_persnrverwijzing database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persnrverwijzing", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg", "dataanvgel"}))
public class PersoonNummerverwijzingHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int ANUMMER_LENGTE = 10;
    private static final int BSN_LENGTE = 9;

    @Id
    @SequenceGenerator(name = "his_persnrverwijzing_id_generator", sequenceName = "kern.seq_his_persnrverwijzing", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persnrverwijzing_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "vorigebsn", length = BSN_LENGTE)
    private String vorigeBurgerservicenummer;

    @Column(name = "volgendebsn", length = BSN_LENGTE)
    private String volgendeBurgerservicenummer;

    @Column(name = "vorigeanr", length = ANUMMER_LENGTE)
    private String vorigeAdministratienummer;

    @Column(name = "volgendeanr", length = ANUMMER_LENGTE)
    private String volgendeAdministratienummer;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonNummerverwijzingHistorie() {}

    /**
     * Maak een nieuwe persoon nummerverwijzing historie.
     *
     * @param persoon persoon
     */
    public PersoonNummerverwijzingHistorie(final Persoon persoon) {
        setPersoon(persoon);
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public PersoonNummerverwijzingHistorie(final PersoonNummerverwijzingHistorie ander) {
        super(ander);
        vorigeBurgerservicenummer = ander.getVorigeBurgerservicenummer();
        volgendeBurgerservicenummer = ander.getVolgendeBurgerservicenummer();
        vorigeAdministratienummer = ander.getVorigeAdministratienummer();
        volgendeAdministratienummer = ander.getVolgendeAdministratienummer();
        persoon = ander.persoon;
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
     * Zet de waarden voor id van PersoonNummerverwijzingHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonNummerverwijzingHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van volgende administratienummer van Persoon.
     * @return de waarde van volgende administratienummer van Persoon
     */
    public String getVolgendeAdministratienummer() {
        return volgendeAdministratienummer;
    }

    /**
     * Zet de waarden voor volgende administratienummer van Persoon.
     * @param volgendeAdministratienummer de nieuwe waarde voor volgende administratienummer van Persoon
     */
    public void setVolgendeAdministratienummer(final String volgendeAdministratienummer) {
        if (volgendeAdministratienummer != null) {
            ValidationUtils.controleerOpLengte("volgendeAdministratienummer moet een lengte van 10 hebben", volgendeAdministratienummer, ANUMMER_LENGTE);
        }
        this.volgendeAdministratienummer = volgendeAdministratienummer;
    }

    /**
     * Geef de waarde van vorige administratienummer van Persoon.
     * @return de waarde van vorige administratienummer van Persoon
     */
    public String getVorigeAdministratienummer() {
        return vorigeAdministratienummer;
    }

    /**
     * Zet de waarden voor vorige administratienummer van Persoon.
     * @param vorigeAdministratienummer de nieuwe waarde voor vorige administratienummer van Persoon
     */
    public void setVorigeAdministratienummer(final String vorigeAdministratienummer) {
        if (vorigeAdministratienummer != null) {
            ValidationUtils.controleerOpLengte("vorigeAdministratienummer moet een lengte van 10 hebben", vorigeAdministratienummer, ANUMMER_LENGTE);
        }
        this.vorigeAdministratienummer = vorigeAdministratienummer;
    }

    /**
     * Geef de waarde van volgende burgerservicenummer van Persoon.
     * @return de waarde van volgende burgerservicenummer van Persoon
     */
    public String getVolgendeBurgerservicenummer() {
        return volgendeBurgerservicenummer;
    }

    /**
     * Zet de waarden voor volgende burgerservicenummer van Persoon.
     * @param volgendeBurgerservicenummer de nieuwe waarde voor volgende burgerservicenummer van Persoon
     */
    public void setVolgendeBurgerservicenummer(final String volgendeBurgerservicenummer) {
        if (volgendeBurgerservicenummer != null) {
            ValidationUtils.controleerOpLengte("volgendeBurgerservicenummer moet een lengte van 9 hebben", volgendeBurgerservicenummer, BSN_LENGTE);
        }
        this.volgendeBurgerservicenummer = volgendeBurgerservicenummer;
    }

    /**
     * Geef de waarde van vorige burgerservicenummer van Persoon.
     * @return de waarde van vorige burgerservicenummer van Persoon
     */
    public String getVorigeBurgerservicenummer() {
        return vorigeBurgerservicenummer;
    }

    /**
     * Zet de waarden voor vorige burgerservicenummer van Persoon.
     * @param vorigeBurgerservicenummer de nieuwe waarde voor vorige burgerservicenummer van Persoon
     */
    public void setVorigeBurgerservicenummer(final String vorigeBurgerservicenummer) {
        if (vorigeBurgerservicenummer != null) {
            ValidationUtils.controleerOpLengte("vorigeBurgerservicenummer moet een lengte van 9 hebben", vorigeBurgerservicenummer, BSN_LENGTE);
        }
        this.vorigeBurgerservicenummer = vorigeBurgerservicenummer;
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonNummerverwijzingHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonNummerverwijzingHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    @Override
    public final PersoonNummerverwijzingHistorie kopieer() {
        return new PersoonNummerverwijzingHistorie(this);
    }
}
