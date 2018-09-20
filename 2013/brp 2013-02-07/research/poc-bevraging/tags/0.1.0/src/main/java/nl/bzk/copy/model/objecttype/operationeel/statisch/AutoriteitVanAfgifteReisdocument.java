/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.AutoriteitVanAfgifteReisdocumentCode;
import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.Omschrijving;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Autoriteit van afgifte reisdocument.
 */
@Entity
@Table(schema = "Kern", name = "AutVanAfgifteReisdoc")
@Access(AccessType.FIELD)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuppressWarnings("serial")
public class AutoriteitVanAfgifteReisdocument extends AbstractStatischObjectType {

    @Id
    @Column(name = "id")
    private Integer autoriteitVanAfgifteDocumentID;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private AutoriteitVanAfgifteReisdocumentCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "oms"))
    private Omschrijving omschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    private Datum datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    private Datum datumEindeGeldigheid;

    /**
     * .
     *
     * @return .
     */
    public Integer getautoriteitVanAfgifteDocumentId() {
        return autoriteitVanAfgifteDocumentID;
    }

    public AutoriteitVanAfgifteReisdocumentCode getCode() {
        return code;
    }

    public Omschrijving getOmschrijving() {
        return omschrijving;
    }

    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
