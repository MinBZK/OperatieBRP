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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * Categorisatie van Documenten.
 *
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractSoortDocument extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Short                        iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    private NaamEnumeratiewaarde         naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaarde omschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Rangorde"))
    private Volgnummer                   rangorde;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractSoortDocument() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam naam van SoortDocument.
     * @param omschrijving omschrijving van SoortDocument.
     * @param rangorde rangorde van SoortDocument.
     */
    protected AbstractSoortDocument(final NaamEnumeratiewaarde naam, final OmschrijvingEnumeratiewaarde omschrijving,
            final Volgnummer rangorde)
    {
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.rangorde = rangorde;

    }

    /**
     * Retourneert ID van Soort document.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Naam van Soort document.
     *
     * @return Naam.
     */
    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort document.
     *
     * @return Omschrijving.
     */
    public OmschrijvingEnumeratiewaarde getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert Rangorde van Soort document.
     *
     * @return Rangorde.
     */
    public Volgnummer getRangorde() {
        return rangorde;
    }

}
