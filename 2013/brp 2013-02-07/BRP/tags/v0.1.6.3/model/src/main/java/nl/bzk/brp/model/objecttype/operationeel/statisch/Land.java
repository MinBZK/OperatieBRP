/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.brp.model.attribuuttype.ISO3166_1Alpha2;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;


/**
 * Land objecttype.
 */
//TODO entity naam tijdelijk vanwege botsing met oude entities
@Entity (name = "LandMdl")
@Table(schema = "Kern", name = "Land")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class Land extends AbstractStatischObjectType {

    @Id
    private Long            id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "landcode"))
    private LandCode        landCode;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "iso31661alpha2"))
    private ISO3166_1Alpha2 iso3166_1Alpha2;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Naam            naam;

    public Long getId() {
        return id;
    }

    public LandCode getLandCode() {
        return landCode;
    }

    public void setLandCode(final LandCode landCode) {
        this.landCode = landCode;
    }

    public ISO3166_1Alpha2 getIso3166_1Alpha2() {
        return iso3166_1Alpha2;
    }

    public void setIso3166_1Alpha2(final ISO3166_1Alpha2 iso3166_1Alpha2) {
        this.iso3166_1Alpha2 = iso3166_1Alpha2;
    }

    public Naam getNaam() {
        return naam;
    }

    public void setNaam(final Naam naam) {
        this.naam = naam;
    }
}
