/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

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

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * The persistent class for the his_persinschr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persinschr", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg" }))

public class PersoonInschrijvingHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persinschr_id_generator", sequenceName = "kern.seq_his_persinschr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persinschr_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "datinschr", nullable = false)
    private int datumInschrijving;

    @Column(name = "versienr", nullable = false)
    private long versienummer;

    @Column(name = "dattijdstempel", nullable = false)
    private Timestamp datumtijdstempel;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonInschrijvingHistorie() {
    }

    /**
     * Maak een nieuwe persoon inschrijving historie.
     *
     * @param persoon
     *            persoon
     * @param datumInschrijving
     *            datum inschrijving
     * @param versienummer
     *            versienummer
     * @param datumtijdstempel
     *            datumtijdstempel
     */
    public PersoonInschrijvingHistorie(final Persoon persoon, final int datumInschrijving, final long versienummer, final Timestamp datumtijdstempel) {
        setPersoon(persoon);
        setDatumInschrijving(datumInschrijving);
        setVersienummer(versienummer);
        setDatumtijdstempel(datumtijdstempel);
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
     * Geef de waarde van datum inschrijving.
     *
     * @return datum inschrijving
     */
    public int getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * Zet de waarde van datum inschrijving.
     *
     * @param datumInschrijving
     *            datum inschrijving
     */
    public void setDatumInschrijving(final int datumInschrijving) {
        this.datumInschrijving = datumInschrijving;
    }

    /**
     * Geef de waarde van versienummer.
     *
     * @return versienummer
     */
    public long getVersienummer() {
        return versienummer;
    }

    /**
     * Zet de waarde van versienummer.
     *
     * @param versienummer
     *            versienummer
     */
    public void setVersienummer(final long versienummer) {
        this.versienummer = versienummer;
    }

    /**
     * Geef de waarde van datumtijdstempel.
     *
     * @return datumtijdstempel
     */
    public Timestamp getDatumtijdstempel() {
        return Kopieer.timestamp(datumtijdstempel);
    }

    /**
     * Zet de waarde van datumtijdstempel.
     *
     * @param datumtijdstempel
     *            datumtijdstempel
     */
    public void setDatumtijdstempel(final Timestamp datumtijdstempel) {
        ValidationUtils.controleerOpNullWaarden("datumtijdstempel mag niet null zijn", datumtijdstempel);
        this.datumtijdstempel = Kopieer.timestamp(datumtijdstempel);
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
}
