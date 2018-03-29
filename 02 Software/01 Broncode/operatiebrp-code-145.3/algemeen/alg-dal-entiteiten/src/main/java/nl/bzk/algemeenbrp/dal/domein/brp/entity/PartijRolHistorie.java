/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the his_partijrol database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_partijrol", schema = "kern", uniqueConstraints = {@UniqueConstraint(columnNames = {"partijrol", "tsreg"})})
public class PartijRolHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_partijrol_id_generator", sequenceName = "kern.seq_his_partijrol", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_partijrol_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    // bi-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partijrol", nullable = false)
    private PartijRol partijRol;

    /**
     * JPA default constructor.
     */
    protected PartijRolHistorie() {}

    /**
     * Maak een nieuwe partij historie.
     *
     * @param partijRol partij rol
     * @param datumTijdRegistratie datumTijdRegistratie
     * @param datumIngang datum ingang
     */
    public PartijRolHistorie(final PartijRol partijRol, final Timestamp datumTijdRegistratie, final int datumIngang) {
        setPartijRol(partijRol);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setDatumIngang(datumIngang);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de waarden voor id van PartijRolHistorie.
     *
     * @param id de nieuwe waarde voor id van PartijRolHistorie
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van datum ingang van PartijRolHistorie.
     *
     * @return de waarde van datum ingang van PartijRolHistorie
     */
    public int getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van PartijRolHistorie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van PartijRolHistorie
     */
    public void setDatumIngang(final int datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van datum einde van PartijRolHistorie.
     *
     * @return de waarde van datum einde van PartijRolHistorie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van PartijRolHistorie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van PartijRolHistorie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van partij rol van PartijRolHistorie.
     *
     * @return de waarde van partij rol van PartijRolHistorie
     */
    public PartijRol getPartijRol() {
        return partijRol;
    }

    /**
     * Zet de waarden voor partij rol van PartijRolHistorie.
     *
     * @param partijRol de nieuwe waarde voor partij rol van PartijRolHistorie
     */
    public void setPartijRol(final PartijRol partijRol) {
        ValidationUtils.controleerOpNullWaarden("partijrol mag niet null zijn", partijRol);
        this.partijRol = partijRol;
    }
}
