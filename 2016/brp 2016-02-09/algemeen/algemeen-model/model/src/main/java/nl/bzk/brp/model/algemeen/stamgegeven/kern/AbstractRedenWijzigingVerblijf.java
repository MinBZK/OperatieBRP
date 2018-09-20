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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Immutable;

/**
 * De mogelijke reden voor het wijzigen van verblijf.
 *
 * De waarde onbekend (?) is bestemd voor conversie-doeleinden. In de BRP-bijhoudingen dient vulling van de waarde
 * voorkomen te worden.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractRedenWijzigingVerblijf implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = RedenWijzigingVerblijfCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    @JsonProperty
    private RedenWijzigingVerblijfCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractRedenWijzigingVerblijf() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van RedenWijzigingVerblijf.
     * @param naam naam van RedenWijzigingVerblijf.
     */
    protected AbstractRedenWijzigingVerblijf(final RedenWijzigingVerblijfCodeAttribuut code, final NaamEnumeratiewaardeAttribuut naam) {
        this.code = code;
        this.naam = naam;

    }

    /**
     * Retourneert ID van Reden wijziging verblijf.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Reden wijziging verblijf.
     *
     * @return Code.
     */
    public final RedenWijzigingVerblijfCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Reden wijziging verblijf.
     *
     * @return Naam.
     */
    public final NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.REDENWIJZIGINGVERBLIJF;
    }

}
