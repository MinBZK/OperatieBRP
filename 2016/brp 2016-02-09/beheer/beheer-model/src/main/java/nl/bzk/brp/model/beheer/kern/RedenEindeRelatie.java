/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;

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
@Entity(name = "beheer.RedenEindeRelatie")
@Table(schema = "Kern", name = "RdnEindeRelatie")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class RedenEindeRelatie {

    @Id
    @SequenceGenerator(name = "REDENEINDERELATIE", sequenceName = "Kern.seq_RdnEindeRelatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "REDENEINDERELATIE")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = RedenEindeRelatieCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private RedenEindeRelatieCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaardeAttribuut omschrijving;

    /**
     * Retourneert ID van Reden einde relatie.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Reden einde relatie.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public RedenEindeRelatieCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van Reden einde relatie.
     *
     * @return Omschrijving.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public OmschrijvingEnumeratiewaardeAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet ID van Reden einde relatie.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Reden einde relatie.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final RedenEindeRelatieCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Omschrijving van Reden einde relatie.
     *
     * @param pOmschrijving Omschrijving.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setOmschrijving(final OmschrijvingEnumeratiewaardeAttribuut pOmschrijving) {
        this.omschrijving = pOmschrijving;
    }

}
