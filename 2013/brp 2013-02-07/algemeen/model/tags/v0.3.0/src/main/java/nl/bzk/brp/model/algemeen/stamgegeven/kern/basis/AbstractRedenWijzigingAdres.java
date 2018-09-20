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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De mogelijke reden voor het wijzigen van een adres.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractRedenWijzigingAdres extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Short                   iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private RedenWijzigingAdresCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    private NaamEnumeratiewaarde    naam;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractRedenWijzigingAdres() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van RedenWijzigingAdres.
     * @param naam naam van RedenWijzigingAdres.
     */
    protected AbstractRedenWijzigingAdres(final RedenWijzigingAdresCode code, final NaamEnumeratiewaarde naam) {
        this.code = code;
        this.naam = naam;

    }

    /**
     * Retourneert ID van Reden wijziging adres.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Reden wijziging adres.
     *
     * @return Code.
     */
    public RedenWijzigingAdresCode getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Reden wijziging adres.
     *
     * @return Naam.
     */
    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

}
