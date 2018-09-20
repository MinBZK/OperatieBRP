/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

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

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the perscache database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "perscache", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonCache implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String PERSOON_MAG_NIET_NULL_ZIJN = "persoon mag niet null zijn";

    @Id
    @SequenceGenerator(name = "perscache_id_generator", sequenceName = "kern.seq_perscache", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perscache_id_generator")
    @Column(nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "versienr", nullable = false)
    private Short versienummer;

    @Column(length = 40, name = "pershistorievolledigchecksum")
    private String persoonHistorieVolledigChecksum;

    @Column(name = "pershistorievollediggegevens")
    private byte[] persoonHistorieVolledigGegevens;

    @Column(length = 40, name = "afnemerindicatiechecksum")
    private String afnemerindicatieChecksum;

    @Column(name = "afnemerindicatiegegevens")
    private byte[] afnemerindicatieGegevens;

    /**
     * JPA default constructor.
     */
    protected PersoonCache() {
    }

    /**
     * Maak een nieuwe persoon cache.
     *
     * @param persoon
     *            persoon
     * @param versienummer
     *            versienummer
     */
    public PersoonCache(
        final Persoon persoon,
        final Short versienummer)
    {
        setPersoon(persoon);
        setVersienummer(versienummer);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Integer id) {
        this.id = id;
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
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van versienummer.
     *
     * @return versienummer
     */
    public Short getVersienummer() {
        return versienummer;
    }

    /**
     * Zet de waarde van versienummer.
     *
     * @param versienummer
     *            versienummer
     */
    public void setVersienummer(final Short versienummer) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.versienummer = versienummer;
    }

    /**
     * Geef de waarde van persoon historie volledig checksum.
     *
     * @return persoon historie volledig checksum
     */
    public String getPersoonHistorieVolledigChecksum() {
        return persoonHistorieVolledigChecksum;
    }

    /**
     * Zet de waarde van persoon historie volledig checksum.
     *
     * @param persoonHistorieVolledigChecksum
     *            persoon historie volledig checksum
     */
    public void setPersoonHistorieVolledigChecksum(final String persoonHistorieVolledigChecksum) {
        this.persoonHistorieVolledigChecksum = persoonHistorieVolledigChecksum;
    }

    /**
     * Geef de waarde van persoon historie volledig gegevens.
     *
     * @return persoon historie volledig gegevens
     */
    public byte[] getPersoonHistorieVolledigGegevens() {
        final byte[] kopie = new byte[persoonHistorieVolledigGegevens.length];
        System.arraycopy(persoonHistorieVolledigGegevens, 0, kopie, 0, persoonHistorieVolledigGegevens.length);
        return kopie;
    }

    /**
     * Zet de waarde van persoon historie volledig gegevens.
     *
     * @param persoonHistorieVolledigGegevens
     *            persoon historie volledig gegevens
     */
    public void setPersoonHistorieVolledigGegevens(final byte[] persoonHistorieVolledigGegevens) {
        final byte[] kopie = new byte[persoonHistorieVolledigGegevens.length];
        System.arraycopy(persoonHistorieVolledigGegevens, 0, kopie, 0, persoonHistorieVolledigGegevens.length);
        this.persoonHistorieVolledigGegevens = kopie;
    }

    /**
     * Geef de waarde van afnemerindicatie checksum.
     *
     * @return afnemerindicatie checksum
     */
    public String getAfnemerindicatieChecksum() {
        return afnemerindicatieChecksum;
    }

    /**
     * Zet de waarde van afnemerindicatie checksum.
     *
     * @param afnemerindicatieChecksum
     *            afnemerindicatie checksum
     */
    public void setAfnemerindicatieChecksum(final String afnemerindicatieChecksum) {
        this.afnemerindicatieChecksum = afnemerindicatieChecksum;
    }

    /**
     * Geef de waarde van afnemerindicatie gegevens.
     *
     * @return afnemerindicatie gegevens
     */
    public byte[] getAfnemerindicatieGegevens() {
        if (afnemerindicatieGegevens != null) {
            final byte[] kopie = new byte[afnemerindicatieGegevens.length];
            System.arraycopy(afnemerindicatieGegevens, 0, kopie, 0, afnemerindicatieGegevens.length);
            return kopie;
        } else {
            return null;
        }
    }

    /**
     * Zet de waarde van afnemerindicatie gegevens.
     *
     * @param afnemerindicatieGegevens
     *            afnemerindicatie gegevens
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
}
