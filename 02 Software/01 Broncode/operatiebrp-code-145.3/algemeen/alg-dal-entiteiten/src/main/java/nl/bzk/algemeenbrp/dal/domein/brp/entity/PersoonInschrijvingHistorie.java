/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the his_persinschr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persinschr", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg"}))
public class PersoonInschrijvingHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persinschr_id_generator", sequenceName = "kern.seq_his_persinschr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persinschr_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "datinschr", nullable = false)
    private int datumInschrijving;

    @Column(name = "versienr", nullable = false)
    private long versienummer;

    @Column(name = "dattijdstempel", nullable = false)
    private Timestamp datumtijdstempel;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonInschrijvingHistorie() {}

    /**
     * Maak een nieuwe persoon inschrijving historie.
     *
     * @param persoon persoon
     * @param datumInschrijving datum inschrijving
     * @param versienummer versienummer
     * @param datumtijdstempel datumtijdstempel
     */
    public PersoonInschrijvingHistorie(final Persoon persoon, final int datumInschrijving, final long versienummer, final Timestamp datumtijdstempel) {
        setPersoon(persoon);
        setDatumInschrijving(datumInschrijving);
        setVersienummer(versienummer);
        setDatumtijdstempel(datumtijdstempel);
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
     * Zet de waarden voor id van PersoonInschrijvingHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonInschrijvingHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum inschrijving van PersoonInschrijvingHistorie.
     *
     * @return de waarde van datum inschrijving van PersoonInschrijvingHistorie
     */
    public int getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * Zet de waarden voor datum inschrijving van PersoonInschrijvingHistorie.
     *
     * @param datumInschrijving de nieuwe waarde voor datum inschrijving van
     *        PersoonInschrijvingHistorie
     */
    public void setDatumInschrijving(final int datumInschrijving) {
        this.datumInschrijving = datumInschrijving;
    }

    /**
     * Geef de waarde van versienummer van PersoonInschrijvingHistorie.
     *
     * @return de waarde van versienummer van PersoonInschrijvingHistorie
     */
    public long getVersienummer() {
        return versienummer;
    }

    /**
     * Zet de waarden voor versienummer van PersoonInschrijvingHistorie.
     *
     * @param versienummer de nieuwe waarde voor versienummer van PersoonInschrijvingHistorie
     */
    public void setVersienummer(final long versienummer) {
        this.versienummer = versienummer;
    }

    /**
     * Geef de waarde van datumtijdstempel van PersoonInschrijvingHistorie.
     *
     * @return de waarde van datumtijdstempel van PersoonInschrijvingHistorie
     */
    public Timestamp getDatumtijdstempel() {
        return Entiteit.timestamp(datumtijdstempel);
    }

    /**
     * Zet de waarden voor datumtijdstempel van PersoonInschrijvingHistorie.
     *
     * @param datumtijdstempel de nieuwe waarde voor datumtijdstempel van
     *        PersoonInschrijvingHistorie
     */
    public void setDatumtijdstempel(final Timestamp datumtijdstempel) {
        ValidationUtils.controleerOpNullWaarden("datumtijdstempel mag niet null zijn", datumtijdstempel);
        this.datumtijdstempel = Entiteit.timestamp(datumtijdstempel);
    }

    /**
     * Geef de waarde van persoon van PersoonInschrijvingHistorie.
     *
     * @return de waarde van persoon van PersoonInschrijvingHistorie
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonInschrijvingHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonInschrijvingHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }
}
