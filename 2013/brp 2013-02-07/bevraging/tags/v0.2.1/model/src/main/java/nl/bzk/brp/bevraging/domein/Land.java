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
 * Deze class vertegenwoordigt een Land.
 */
@Entity
@Table(name = "Land", schema = "Kern")
@Access(AccessType.FIELD)
public class Land implements Serializable {

    @Id
    private Long    id;
    @Column(name = "Naam")
    private String  naam;

    @Column(name = "ISO31661Alpha2")
    private String isoLandCode;
    @Column(name = "DatAanvGel")
    private Integer datumAanvangGeldigheid;
    @Column(name = "DatEindeGel")
    private Integer datumEindeGeldigheid;

    /**
     * No-arg constructor voor JPA.
     */
    protected Land() {
    }

    /**
     * Constructor voor programmatische instantiatie, met parameter voor verplichte properties.
     *
     * @param naam De naam van het land.
     */
    public Land(final String naam) {
        this.naam = naam;
    }

    public Long getId() {
        return id;
    }

    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Retourneert de land code conform de 'ISO 3166-1 alpha 2' standaard.
     * @return land code conform de ' ISO 3166-1 alpha 2' standaard.
     */
    public String getIsoLandCode() {
        return isoLandCode;
    }

    /**
     * Zet de land code conform de 'ISO 3166-1 alpha 2' standaard.
     * @param isoLandCode land code conform de ' ISO 3166-1 alpha 2' standaard.
     */
    public void setIsoLandCode(final String isoLandCode) {
        this.isoLandCode = isoLandCode;
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
                .append("isoLandCode", isoLandCode).append("datum aanvang geldigheid", datumAanvangGeldigheid)
                .toString();
    }
}
