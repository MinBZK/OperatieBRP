/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;

/**
 * Woonplaats.
 */
@Entity (name = "PlaatsMdl")
@Table(schema = "Kern", name = "Plaats")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class Plaats extends AbstractStatischObjectType {

    @Id
    @Column (name = "id")
    private Long id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "wplcode"))
    private PlaatsCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Naam naam;

    public Long getId() {
        return id;
    }

    public PlaatsCode getCode() {
        return code;
    }

    public Naam getNaam() {
        return naam;
    }
}
