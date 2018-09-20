/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * De woonplaatsen, zoals onderhouden vanuit de BAG.
 *
 * De inhoud van de woonplaatsentabel wordt overgenomen vanuit de BAG. Qua vorm wijkt deze wel af, zo wordt er apart
 * bijgehouden welke gemeenten er zijn, terwijl de BAG deze in één en dezelfde tabel heeft gestopt.
 */
@Entity
@Table(name = "Plaats", schema = "Kern")
@Access(AccessType.FIELD)
public class Plaats implements Serializable {

    @Id
    private Long    id;
    @Column(name = "Naam")
    private String  naam;
    @Column(name = "Wplcode")
    private String  woonplaatscode;
    @Column(name = "DatAanvGel")
    private Integer datumAanvangGeldigheid;
    @Column(name = "DatEindeGel")
    private Integer datumEindeGeldigheid;

    /**
     * No-arg constructor voor JPA.
     */
    protected Plaats() {
    }

    /**
     * Constructor voor programmatische instantiatie, met parameter voor verplichte properties.
     *
     * @param naam De naam van de plaats.
     */
    public Plaats(final String naam) {
        this.naam = naam;
    }

    public Long getId() {
        return id;
    }

    public String getWoonplaatscode() {
        return woonplaatscode;
    }

    public void setWoonplaatscode(final String woonplaatscode) {
        this.woonplaatscode = woonplaatscode;
    }

    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    public void setDatumEindeGeldigheid(final Integer datum) {
        this.datumEindeGeldigheid = datum;
    }

    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public void setDatumAanvangGeldigheid(final Integer datum) {
        this.datumAanvangGeldigheid = datum;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id).append("naam", naam)
                .toString();
    }
}
