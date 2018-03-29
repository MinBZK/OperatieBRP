/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

/**
 * SelectieResultaatSchrijfInfo.
 */
public final class SelectieResultaatSchrijfInfo {

    private int berichtId;
    private int toegangLeveringsAutorisatieId;
    private int selectieRunId;
    private int dienstId;

    /**
     * Gets bericht id.
     * @return the bericht id
     */
    public int getBerichtId() {
        return berichtId;
    }

    /**
     * Sets bericht id.
     * @param berichtId the bericht id
     */
    public void setBerichtId(int berichtId) {
        this.berichtId = berichtId;
    }

    /**
     * Gets toegang leverings autorisatie id.
     * @return the toegang leverings autorisatie id
     */
    public int getToegangLeveringsAutorisatieId() {
        return toegangLeveringsAutorisatieId;
    }

    /**
     * Sets toegang leverings autorisatie id.
     * @param toegangLeveringsAutorisatieId the toegang leverings autorisatie id
     */
    public void setToegangLeveringsAutorisatieId(int toegangLeveringsAutorisatieId) {
        this.toegangLeveringsAutorisatieId = toegangLeveringsAutorisatieId;
    }

    /**
     * Gets selectie run id.
     * @return the selectie run id
     */
    public int getSelectieRunId() {
        return selectieRunId;
    }

    /**
     * Sets selectie run id.
     * @param selectieRunId the selectie run id
     */
    public void setSelectieRunId(int selectieRunId) {
        this.selectieRunId = selectieRunId;
    }

    /**
     * Gets dienst id.
     * @return the dienst id
     */
    public int getDienstId() {
        return dienstId;
    }

    /**
     * Sets dienst id.
     * @param dienstId the dienst id
     */
    public void setDienstId(int dienstId) {
        this.dienstId = dienstId;
    }
}
