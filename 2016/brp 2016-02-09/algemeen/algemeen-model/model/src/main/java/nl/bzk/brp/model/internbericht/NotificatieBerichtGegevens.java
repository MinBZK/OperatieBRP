/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.internbericht;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Bericht object dat via JMS gecommuniceerd wordt tussen de levering componenten. JSON wordt gebruikt voor (De)serialisatie.
 */
public final class NotificatieBerichtGegevens implements BrpObject {

    /**
     * Het id van de ontvangende partij.
     */
    @JsonProperty
    private Short ontvangendePartijId;

    /**
     * Het afleverkanaal.
     */
    @JsonProperty
    private Stelsel stelsel;

    /**
     * De Stuurgegevens.
     */
    @JsonProperty
    private BerichtStuurgegevensGroepModel stuurgegevens;

    /**
     * Default constructor nodig voor deserialisatie.
     */
    public NotificatieBerichtGegevens() {

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("kanaal", stelsel.getNaam())
            .append("ontvangendePartijId", ontvangendePartijId)
            .toString();
    }

    public BerichtStuurgegevensGroepModel getStuurgegevens() {
        return stuurgegevens;
    }

    public void setStuurgegevens(final BerichtStuurgegevensGroepModel stuurgegevens) {
        this.stuurgegevens = stuurgegevens;
    }

    public Stelsel getStelsel() {
        return stelsel;
    }

    public void setStelsel(final Stelsel stelsel) {
        this.stelsel = stelsel;
    }

    public Short getOntvangendePartijId() {
        return ontvangendePartijId;
    }

    public void setOntvangendePartijId(final Short ontvangendePartijId) {
        this.ontvangendePartijId = ontvangendePartijId;
    }
}
