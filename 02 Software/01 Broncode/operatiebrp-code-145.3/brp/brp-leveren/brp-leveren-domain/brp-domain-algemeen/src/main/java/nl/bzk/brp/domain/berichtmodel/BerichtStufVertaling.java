/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

/**
 * BerichtStufVertaling.
 */
public class BerichtStufVertaling {

    private String vertaling;
    private String communicatieId;

    /**
     * Constructs een nieuw Bericht stuf vertaling.
     * @param vertaling the vertaling
     * @param communicatieId the communicatie id
     */
    public BerichtStufVertaling(String vertaling, String communicatieId) {
        this.vertaling = vertaling;
        this.communicatieId = communicatieId;
    }


    /**
     * Gets vertaling.
     * @return the vertaling
     */
    public String getVertaling() {
        return vertaling;
    }

    /**
     * Gets communicatie id.
     * @return the communicatie id
     */
    public String getCommunicatieId() {
        return communicatieId;
    }
}
