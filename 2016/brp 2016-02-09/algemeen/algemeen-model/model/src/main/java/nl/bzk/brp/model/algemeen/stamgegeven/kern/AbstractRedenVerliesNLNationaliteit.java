/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCodeAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Immutable;

/**
 * De mogelijke reden voor het verliezen van de Nederlandse nationaliteit.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractRedenVerliesNLNationaliteit implements SynchroniseerbaarStamgegeven, BestaansPeriodeStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = RedenVerliesCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    @JsonProperty
    private RedenVerliesCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaardeAttribuut omschrijving;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "dataanvgel"))
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "dateindegel"))
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

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
    protected AbstractRedenVerliesNLNationaliteit(
        final RedenVerliesCodeAttribuut code,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijving,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
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
    public final RedenVerliesCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van Reden verlies NL nationaliteit.
     *
     * @return Omschrijving.
     */
    public final OmschrijvingEnumeratiewaardeAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Reden verlies NL nationaliteit.
     *
     * @return Datum aanvang geldigheid voor Reden verlies NL nationaliteit
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Reden verlies NL nationaliteit.
     *
     * @return Datum einde geldigheid voor Reden verlies NL nationaliteit
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.REDENVERLIESNLNATIONALITEIT;
    }

}
