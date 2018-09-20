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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverAdreshoudingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De mogelijke hoedanigheid waarmee een persoon die aangifte doet van verblijf en adres of van adreswijziging kan staan
 * ten opzichte van de Persoon wiens adres is aangegeven.
 *
 * In veel gevallen is degene wiens adres wijzigt ook degene die de adreswijziging doorgeeft. Maar het is ook mogelijk
 * dat anderen de wijziging doorgeven, bijvoorbeeld een meerderjarig gemachtigde.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractAangeverAdreshouding extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Short                        iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private AangeverAdreshoudingCode     code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    private NaamEnumeratiewaarde         naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaarde omschrijving;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractAangeverAdreshouding() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van AangeverAdreshouding.
     * @param naam naam van AangeverAdreshouding.
     * @param omschrijving omschrijving van AangeverAdreshouding.
     */
    protected AbstractAangeverAdreshouding(final AangeverAdreshoudingCode code, final NaamEnumeratiewaarde naam,
            final OmschrijvingEnumeratiewaarde omschrijving)
    {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;

    }

    /**
     * Retourneert ID van Aangever adreshouding.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Aangever adreshouding.
     *
     * @return Code.
     */
    public AangeverAdreshoudingCode getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Aangever adreshouding.
     *
     * @return Naam.
     */
    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Aangever adreshouding.
     *
     * @return Omschrijving.
     */
    public OmschrijvingEnumeratiewaarde getOmschrijving() {
        return omschrijving;
    }

}
