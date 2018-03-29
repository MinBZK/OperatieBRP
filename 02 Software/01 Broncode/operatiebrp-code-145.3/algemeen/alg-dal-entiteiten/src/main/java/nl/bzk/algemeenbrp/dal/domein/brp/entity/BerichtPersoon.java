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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLInsert;

/**
 * BerichtPersoon entiteit uit het ber-schema.
 */
@Entity
@Table(name = "berpers", schema = "ber")
@IdClass(BerichtPersoonSleutel.class)
@SQLInsert(sql = "insert into ber.berpers (tsreg, ber, pers) values (?, ?, ?)", check= ResultCheckStyle.NONE)
public class BerichtPersoon implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ber", nullable = false, updatable = false)
    private Bericht bericht;

    @Column(name = "tsreg",  nullable = false)
    private Timestamp datumTijdRegistratie;

    @Id
    @Column(name = "pers", nullable = false, updatable = false)
    private Long persoon;

    /**
     * JPA Default constructor.
     */
    protected BerichtPersoon() {
    }

    /**
     * Maakt een nieuw berichtpersoon object.
     * @param bericht bericht
     * @param persoon persoon
     * @param datumTijdRegistratie datumTijdRegistratie
     */
    public BerichtPersoon(final Bericht bericht, final Long persoon, final Timestamp datumTijdRegistratie) {
        ValidationUtils.controleerOpNullWaarden("bericht mag niet null zijn", bericht);
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        ValidationUtils.controleerOpNullWaarden("datumregistratie mag niet null zijn", datumTijdRegistratie);
        this.bericht = bericht;
        this.persoon = persoon;
        this.datumTijdRegistratie = Entiteit.timestamp(datumTijdRegistratie);
    }

    /**
     * Geef de waarde van persoon.
     * @return persoon
     */
    public Long getPersoon() {
        return persoon;
    }

    /**
     * Geef de waarde van datumTijdRegistratie.
     * @return datumTijdRegistratie
     */
    public Timestamp getDatumTijdRegistratie() {
        return Entiteit.timestamp(datumTijdRegistratie);
    }

    /**
     * Geef de waarde van bericht.
     * @return bericht
     */
    public Bericht getBericht() {
        return bericht;
    }
}
