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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenBeeindigingRelatieCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De mogelijke reden om een Relatie te be�indigen.
 *
 * Dit stamgegeven kent geen bestaansperiode. Op zich is het denkbaar dat er een bestaansperiode is, zo is omzetting
 * vanuit een Huwelijk naar een Geregistreerd partnerschap vanaf een bepaalde datum niet meer toegestaan. Er is echter
 * besloten vooralsnog geen bestaansperiode te onderkennen. Redenen hiervoor: er is nu geen behoefte aan (immers,
 * omzetting Geregistreerd partnerschap naar Huwelijk is wel mogelijk), en het invoeren hiervan ZODRA DAT NODIG IS, is
 * relatief eenvoudig. Er wordt daarom nu hier geen voorschot op de toekomst genomen.
 * RvdP 19 maart 2012.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractRedenBeeindigingRelatie extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Short                        iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private RedenBeeindigingRelatieCode  code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaarde omschrijving;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractRedenBeeindigingRelatie() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van RedenBeeindigingRelatie.
     * @param omschrijving omschrijving van RedenBeeindigingRelatie.
     */
    protected AbstractRedenBeeindigingRelatie(final RedenBeeindigingRelatieCode code,
            final OmschrijvingEnumeratiewaarde omschrijving)
    {
        this.code = code;
        this.omschrijving = omschrijving;

    }

    /**
     * Retourneert ID van Reden be�indiging relatie.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Reden be�indiging relatie.
     *
     * @return Code.
     */
    public RedenBeeindigingRelatieCode getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van Reden be�indiging relatie.
     *
     * @return Omschrijving.
     */
    public OmschrijvingEnumeratiewaarde getOmschrijving() {
        return omschrijving;
    }

}
