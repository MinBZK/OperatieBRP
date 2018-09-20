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

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;


/**
 * Partij.
 */
@Entity (name = "PartijMdl")
@Table(schema = "Kern", name = "Partij")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class Partij extends AbstractStatischObjectType {

    @Id
    @Column (name = "id")
    private Long         partijId;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Naam         naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanv"))
    private Datum        datumAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateinde"))
    private Datum        datumEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "gemcode"))
    private GemeenteCode gemeenteCode;

    public Long getPartijId() {
        return partijId;
    }

    public Naam getNaam() {
        return naam;
    }

    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    public Datum getDatumEinde() {
        return datumEinde;
    }

    public GemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }
}
