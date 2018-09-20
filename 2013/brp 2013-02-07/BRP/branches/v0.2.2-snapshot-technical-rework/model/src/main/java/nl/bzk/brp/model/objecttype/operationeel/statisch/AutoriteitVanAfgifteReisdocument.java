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

import nl.bzk.brp.model.attribuuttype.AutoriteitVanAfgifteReisdocumentCode;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;


/**
 * Autoriteit van afgifte reisdocument.
 *
 */
@Entity
@Table(schema = "Kern", name = "AutVanAfgifteReisdoc")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class AutoriteitVanAfgifteReisdocument extends AbstractStatischObjectType {

    @Id
    @Column (name = "id")
    private Integer                              autoriteitVanAfgifteDocumentID;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private AutoriteitVanAfgifteReisdocumentCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "oms"))
    private OmschrijvingEnumeratiewaarde omschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    private Datum                                datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    private Datum                                datumEindeGeldigheid;

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

    public OmschrijvingEnumeratiewaarde getOmschrijving() {
        return omschrijving;
    }

    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
