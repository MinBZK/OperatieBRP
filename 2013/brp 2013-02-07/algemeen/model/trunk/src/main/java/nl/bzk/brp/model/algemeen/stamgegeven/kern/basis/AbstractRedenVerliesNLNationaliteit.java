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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De mogelijke reden voor het verliezen van de Nederlandse nationaliteit.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractRedenVerliesNLNationaliteit extends AbstractStatischObjectType implements
        BestaansPeriodeStamgegeven
{

    @Id
    @JsonProperty
    private Short                        iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private RedenVerliesCode             code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaarde omschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    private Datum                        datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    private Datum                        datumEindeGeldigheid;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractRedenVerliesNLNationaliteit() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van RedenVerliesNLNationaliteit.
     * @param omschrijving omschrijving van RedenVerliesNLNationaliteit.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van RedenVerliesNLNationaliteit.
     * @param datumEindeGeldigheid datumEindeGeldigheid van RedenVerliesNLNationaliteit.
     */
    protected AbstractRedenVerliesNLNationaliteit(final RedenVerliesCode code,
            final OmschrijvingEnumeratiewaarde omschrijving, final Datum datumAanvangGeldigheid,
            final Datum datumEindeGeldigheid)
    {
        this.code = code;
        this.omschrijving = omschrijving;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Reden verlies NL nationaliteit.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Reden verlies NL nationaliteit.
     *
     * @return Code.
     */
    public RedenVerliesCode getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van Reden verlies NL nationaliteit.
     *
     * @return Omschrijving.
     */
    public OmschrijvingEnumeratiewaarde getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Reden verlies NL nationaliteit.
     *
     * @return datum aanvang geldigheid voor Reden verlies NL nationaliteit.
     */
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Reden verlies NL nationaliteit.
     *
     * @return datum einde geldigheid voor Reden verlies NL nationaliteit.
     */
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
