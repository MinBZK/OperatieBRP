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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteittypeVanAfgifteReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Immutable;

/**
 * De mogelijke Autoriteittypen van afgifte van een reisdocument.
 *
 * Deze tabel wordt niet als directe verwijzing gebruikt van A:"Persoon \ Reisdocument.Autoriteit van afgifte", maar
 * slechts de eerste paar tekens worden gevalideerd.
 *
 * Naamgeving is wat problematisch, zo is de code BZ en BU beiden voor 'minister van buitenlandse zaken'. Om de naam
 * toch uniek te maken, is deze "stamtabel", die toch alleen bestaat ter afdekking van een bedrijfsregel voor de code
 * voor een autoriteit, uniek gemaakt.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractAutoriteittypeVanAfgifteReisdocument implements SynchroniseerbaarStamgegeven, BestaansPeriodeStamgegeven,
        ElementIdentificeerbaar
{

    @Id
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = AutoriteittypeVanAfgifteReisdocumentCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    @JsonProperty
    private AutoriteittypeVanAfgifteReisdocumentCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

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
    protected AbstractAutoriteittypeVanAfgifteReisdocument() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van AutoriteittypeVanAfgifteReisdocument.
     * @param naam naam van AutoriteittypeVanAfgifteReisdocument.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van AutoriteittypeVanAfgifteReisdocument.
     * @param datumEindeGeldigheid datumEindeGeldigheid van AutoriteittypeVanAfgifteReisdocument.
     */
    protected AbstractAutoriteittypeVanAfgifteReisdocument(
        final AutoriteittypeVanAfgifteReisdocumentCodeAttribuut code,
        final NaamEnumeratiewaardeAttribuut naam,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.code = code;
        this.naam = naam;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Autoriteittype van afgifte reisdocument.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Autoriteittype van afgifte reisdocument.
     *
     * @return Code.
     */
    public final AutoriteittypeVanAfgifteReisdocumentCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Autoriteittype van afgifte reisdocument.
     *
     * @return Naam.
     */
    public final NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Autoriteittype van afgifte reisdocument.
     *
     * @return Datum aanvang geldigheid voor Autoriteittype van afgifte reisdocument
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Autoriteittype van afgifte reisdocument.
     *
     * @return Datum einde geldigheid voor Autoriteittype van afgifte reisdocument
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
        return ElementEnum.AUTORITEITTYPEVANAFGIFTEREISDOCUMENT;
    }

}
