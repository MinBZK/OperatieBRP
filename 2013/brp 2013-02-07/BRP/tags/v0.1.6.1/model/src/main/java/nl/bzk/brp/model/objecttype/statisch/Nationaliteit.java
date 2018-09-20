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
import nl.bzk.brp.model.attribuuttype.NationaliteitCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;


/**
 * Objecttype Nationaliteit.
 */
//TODO
@Entity (name = "NationaliteitMdl")
@Table(schema = "Kern", name = "Nation")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class Nationaliteit extends AbstractStatischObjectType {

    @Id
    @Column (name = "id")
    private Long              nationaliteitId;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Naam              naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "nationcode"))
    private NationaliteitCode nationaliteitCode;

    public Long getNationaliteitId() {
        return nationaliteitId;
    }

    public Naam getNaam() {
        return naam;
    }

    public NationaliteitCode getNationaliteitCode() {
        return nationaliteitCode;
    }
}
