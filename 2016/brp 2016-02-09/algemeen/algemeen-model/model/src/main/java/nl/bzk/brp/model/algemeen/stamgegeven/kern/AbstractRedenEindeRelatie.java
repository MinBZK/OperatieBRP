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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Immutable;

/**
 * De mogelijke reden om een Relatie te beëindigen.
 *
 * Dit stamgegeven kent geen bestaansperiode. Op zich is het denkbaar dat er een bestaansperiode is, zo is omzetting
 * vanuit een Huwelijk naar een Geregistreerd partnerschap vanaf een bepaalde datum niet meer toegestaan. Er is echter
 * besloten vooralsnog geen bestaansperiode te onderkennen. Redenen hiervoor: er is nu geen behoefte aan (immers,
 * omzetting Geregistreerd partnerschap naar Huwelijk is wel mogelijk), en het invoeren hiervan ZODRA DAT NODIG IS, is
 * relatief eenvoudig. Er wordt daarom nu hier geen voorschot op de toekomst genomen.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractRedenEindeRelatie implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = RedenEindeRelatieCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    @JsonProperty
    private RedenEindeRelatieCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaardeAttribuut omschrijving;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractRedenEindeRelatie() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van RedenEindeRelatie.
     * @param omschrijving omschrijving van RedenEindeRelatie.
     */
    protected AbstractRedenEindeRelatie(final RedenEindeRelatieCodeAttribuut code, final OmschrijvingEnumeratiewaardeAttribuut omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;

    }

    /**
     * Retourneert ID van Reden einde relatie.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Reden einde relatie.
     *
     * @return Code.
     */
    public final RedenEindeRelatieCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van Reden einde relatie.
     *
     * @return Omschrijving.
     */
    public final OmschrijvingEnumeratiewaardeAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.REDENEINDERELATIE;
    }

}
