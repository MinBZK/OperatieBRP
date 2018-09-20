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

import nl.bzk.brp.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;

/**
 * Objecttype Reden verlies nationaliteit.
 */
//TODO entity naam tijdelijk vanwege botsing met oude entities
@Entity (name = "RedenVerliesNLNationaliteitMdl")
@Table(schema = "Kern", name = "RdnVerkNLNation")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class RedenVerliesNLNationaliteit extends AbstractStatischObjectType {

    @Id
    @Column (name = "id")
    private Integer id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private RedenVerliesNaam naam;

    public Integer getId() {
        return id;
    }

    public RedenVerliesNaam getNaam() {
        return naam;
    }
}
