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

import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.RedenBeeindigingRelatieCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;


/**
 * Gegevens model object voor Reden beeindiging relatie.
 */
@Entity (name = "RedenBeeindigingRelatieMdl")
@Table(schema = "Kern", name = "RdnBeeindRelatie")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class RedenBeeindigingRelatie extends AbstractStatischObjectType {

    @Id
    @Column (name = "id")
    private Integer            id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private RedenBeeindigingRelatieCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "oms"))
    private Omschrijving                omschrijving;

    public Integer getId() {
        return id;
    }

    public RedenBeeindigingRelatieCode getCode() {
        return code;
    }

    public Omschrijving getOmschrijving() {
        return omschrijving;
    }
}
