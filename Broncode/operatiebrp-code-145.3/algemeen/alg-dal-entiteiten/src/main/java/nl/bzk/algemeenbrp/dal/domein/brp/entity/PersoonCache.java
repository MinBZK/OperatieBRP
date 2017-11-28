/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the perscache database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "perscache", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class PersoonCache implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String PERSOON_MAG_NIET_NULL_ZIJN = "persoon mag niet null zijn";

    @Id
    @SequenceGenerator(name = "perscache_id_generator", sequenceName = "kern.seq_perscache", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perscache_id_generator")
    @Column(nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "versienr", nullable = false)
    private Short versienummer;

    @Column(name = "pershistorievollediggegevens")
    private byte[] persoonHistorieVolledigGegevens;

    @Column(name = "afnemerindicatiegegevens")
    private byte[] afnemerindicatieGegevens;

    @Column(name = "lockversieafnemerindicatiege")
    private Long lockversieAfnemerindicatie;

    /**
     * JPA default constructor.
     */
    protected PersoonCache() {
    }

    /**
     * Maak een nieuwe persoon cache.
     * @param persoon persoon
     * @param versienummer versienummer
     */
    public PersoonCache(final Persoon persoon, final Short versienummer) {
        setPersoon(persoon);
        setVersienummer(versienummer);
    }

    /**
     * Geef de waarde van id van PersoonCache.
     * @return de waarde van id van PersoonCache
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van PersoonCache.
     * @param id de nieuwe waarde voor id van PersoonCache
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van persoon van PersoonCache.
     * @return de waarde van persoon van PersoonCache
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonCache.
     * @param persoon de nieuwe waarde voor persoon van PersoonCache
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van versienummer van PersoonCache.
     * @return de waarde van versienummer van PersoonCache
     */
    public Short getVersienummer() {
        return versienummer;
    }

    /**
     * Zet de waarden voor versienummer van PersoonCache.
     * @param versienummer de nieuwe waarde voor versienummer van PersoonCache
     */
    public void setVersienummer(final Short versienummer) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.versienummer = versienummer;
    }

    /**
     * Geef de waarde van persoon historie volledig gegevens van PersoonCache.
     * @return de waarde van persoon historie volledig gegevens van PersoonCache
     */
    public byte[] getPersoonHistorieVolledigGegevens() {
        return arrayCopy(persoonHistorieVolledigGegevens);
    }

    /**
     * Zet de waarden voor persoon historie volledig gegevens van PersoonCache.
     * @param persoonHistorieVolledigGegevens de nieuwe waarde voor persoon historie volledig gegevens van PersoonCache
     */
    public void setPersoonHistorieVolledigGegevens(final byte[] persoonHistorieVolledigGegevens) {
        final byte[] kopie = new byte[persoonHistorieVolledigGegevens.length];
        System.arraycopy(persoonHistorieVolledigGegevens, 0, kopie, 0, persoonHistorieVolledigGegevens.length);
        this.persoonHistorieVolledigGegevens = kopie;
    }

    /**
     * Geef de waarde van afnemerindicatie gegevens van PersoonCache.
     * @return de waarde van afnemerindicatie gegevens van PersoonCache
     */
    public byte[] getAfnemerindicatieGegevens() {
        return arrayCopy(afnemerindicatieGegevens);
    }

    /**
     * Zet de waarden voor afnemerindicatie gegevens van PersoonCache.
     * @param afnemerindicatieGegevens de nieuwe waarde voor afnemerindicatie gegevens van PersoonCache
     */
    public void setAfnemerindicatieGegevens(final byte[] afnemerindicatieGegevens) {
        if (afnemerindicatieGegevens != null) {
            final byte[] kopie = new byte[afnemerindicatieGegevens.length];
            System.arraycopy(afnemerindicatieGegevens, 0, kopie, 0, afnemerindicatieGegevens.length);
            this.afnemerindicatieGegevens = kopie;
        } else {
            this.afnemerindicatieGegevens = null;
        }
    }

    /**
     * Geeft het versienummer van de afnemerindicaties tbv optimistic locking.
     * @return een versienummer
     */
    public Long getLockversieAfnemerindicatie() {
        return lockversieAfnemerindicatie;
    }

    /**
     * Zet het versienummer van de afnemerindicaties tbv optimistic locking.
     * @param lockversieAfnemerindicatie een versienummer
     */
    public void setLockversieAfnemerindicatie(final Long lockversieAfnemerindicatie) {
        this.lockversieAfnemerindicatie = lockversieAfnemerindicatie;
    }

    private byte[] arrayCopy(final byte[] array) {
        if (array != null) {
            final byte[] kopie = new byte[array.length];
            System.arraycopy(array, 0, kopie, 0, array.length);
            return kopie;
        } else {
            return new byte[]{};
        }
    }
}
