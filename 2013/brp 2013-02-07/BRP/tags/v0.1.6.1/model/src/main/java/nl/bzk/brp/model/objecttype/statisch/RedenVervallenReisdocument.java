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
import nl.bzk.brp.model.attribuuttype.RedenVervallenReisDocumentCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;

/**
 * Object type reden vervallen reisdocument.
 */
@Entity
@Table(schema = "Kern", name = "RdnVervallenReisdoc")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class RedenVervallenReisdocument extends AbstractStatischObjectType {

    @Id
    @Column (name = "id")
    private Integer id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private RedenVervallenReisDocumentCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Naam naam;

    public Integer getId() {
        return id;
    }

    public RedenVervallenReisDocumentCode getCode() {
        return code;
    }

    public Naam getNaam() {
        return naam;
    }
}
