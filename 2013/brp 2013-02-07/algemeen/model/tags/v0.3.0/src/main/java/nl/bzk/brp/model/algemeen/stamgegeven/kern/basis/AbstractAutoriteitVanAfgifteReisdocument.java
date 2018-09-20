/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteitVanAfgifteReisdocumentCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De mogelijke Autoriteit van afgifte van een reisdocument.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractAutoriteitVanAfgifteReisdocument extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Integer                              iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private AutoriteitVanAfgifteReisdocumentCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaarde         omschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    private Datum                                datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    private Datum                                datumEindeGeldigheid;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractAutoriteitVanAfgifteReisdocument() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van AutoriteitVanAfgifteReisdocument.
     * @param omschrijving omschrijving van AutoriteitVanAfgifteReisdocument.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van AutoriteitVanAfgifteReisdocument.
     * @param datumEindeGeldigheid datumEindeGeldigheid van AutoriteitVanAfgifteReisdocument.
     */
    protected AbstractAutoriteitVanAfgifteReisdocument(final AutoriteitVanAfgifteReisdocumentCode code,
            final OmschrijvingEnumeratiewaarde omschrijving, final Datum datumAanvangGeldigheid,
            final Datum datumEindeGeldigheid)
    {
        this.code = code;
        this.omschrijving = omschrijving;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Autoriteit van afgifte reisdocument.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Code van Autoriteit van afgifte reisdocument.
     *
     * @return Code.
     */
    public AutoriteitVanAfgifteReisdocumentCode getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van Autoriteit van afgifte reisdocument.
     *
     * @return Omschrijving.
     */
    public OmschrijvingEnumeratiewaarde getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Autoriteit van afgifte reisdocument.
     *
     * @return datum aanvang geldigheid voor Autoriteit van afgifte reisdocument.
     */
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Autoriteit van afgifte reisdocument.
     *
     * @return datum einde geldigheid voor Autoriteit van afgifte reisdocument.
     */
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
