/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.attribuuttype.RedenVervallenReisdocumentCode;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Object type reden vervallen reisdocument.
 */
@Entity
@Table(schema = "Kern", name = "RdnVervallenReisdoc")
@Access(AccessType.FIELD)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuppressWarnings("serial")
public class RedenVervallenReisdocument extends AbstractStatischObjectType {

    @Id
    @Column(name = "id")
    private Short id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private RedenVervallenReisdocumentCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Naam naam;

    public Short getId() {
        return id;
    }

    public RedenVervallenReisdocumentCode getCode() {
        return code;
    }

    public Naam getNaam() {
        return naam;
    }
}
