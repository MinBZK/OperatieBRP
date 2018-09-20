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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingVerblijfsrechtCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Immutable;

/**
 * Categorisatie van de Aanduiding van het verblijfsrecht van Persoon.
 *
 * 1. Naam: aanduiding verblijfsrecht is een verwarrende naam. Immers, de hier bedoelde aanduiding betreft niet alleen
 * het recht op verblijf van de vreemdeling (zoals geregeld in de Vreemdelingenwet 2000), maar ook o.a. het recht op
 * arbeid (zoals [deels] geregeld in de wet arbeid vreemdelingen). De in LO3.x gebruikte terminologie (verblijfstitel)
 * leek een correcte term. Deze term stuit echter op bezwaren vanuit juridische hoek. Om die reden is toch de term
 * verblijfsrecht gehanteerd, voor dit gegeven dat in de hoek van de IND ook wel wordt gedefinieerd als 'het recht op
 * voorzieningen'.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractAanduidingVerblijfsrecht implements SynchroniseerbaarStamgegeven, BestaansPeriodeStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = AanduidingVerblijfsrechtCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    @JsonProperty
    private AanduidingVerblijfsrechtCodeAttribuut code;

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
    protected AbstractAanduidingVerblijfsrecht() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van AanduidingVerblijfsrecht.
     * @param omschrijving omschrijving van AanduidingVerblijfsrecht.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van AanduidingVerblijfsrecht.
     * @param datumEindeGeldigheid datumEindeGeldigheid van AanduidingVerblijfsrecht.
     */
    protected AbstractAanduidingVerblijfsrecht(
        final AanduidingVerblijfsrechtCodeAttribuut code,
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
     * Retourneert ID van Aanduiding verblijfsrecht.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Aanduiding verblijfsrecht.
     *
     * @return Code.
     */
    public final AanduidingVerblijfsrechtCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van Aanduiding verblijfsrecht.
     *
     * @return Omschrijving.
     */
    public final OmschrijvingEnumeratiewaardeAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Aanduiding verblijfsrecht.
     *
     * @return Datum aanvang geldigheid voor Aanduiding verblijfsrecht
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Aanduiding verblijfsrecht.
     *
     * @return Datum einde geldigheid voor Aanduiding verblijfsrecht
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
        return ElementEnum.AANDUIDINGVERBLIJFSRECHT;
    }

}
