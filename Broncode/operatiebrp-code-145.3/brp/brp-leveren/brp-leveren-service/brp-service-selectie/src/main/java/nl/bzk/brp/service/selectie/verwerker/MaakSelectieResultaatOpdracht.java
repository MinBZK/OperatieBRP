/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import java.util.Collection;
import java.util.List;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * MaakSelectieResultaatOpdracht.
 */
public final class MaakSelectieResultaatOpdracht {

    private Collection<SelectieAutorisatiebundel> autorisatiebundels;
    private Integer selectieRunId;
    private int selectieStartDatum;
    private Persoonslijst persoonslijst;
    private boolean stop;

    /**
     * Gets autorisatiebundels.
     * @return the autorisatiebundels
     */
    public Collection<SelectieAutorisatiebundel> getAutorisatiebundels() {
        return autorisatiebundels;
    }

    /**
     * Sets autorisatiebundels.
     * @param autorisatiebundels the autorisatiebundels
     */
    public void setAutorisatiebundels(List<SelectieAutorisatiebundel> autorisatiebundels) {
        this.autorisatiebundels = autorisatiebundels;
    }

    /**
     * Gets persoonslijst.
     * @return the persoonslijst
     */
    public Persoonslijst getPersoonslijst() {
        return persoonslijst;
    }

    /**
     * Sets persoonslijst.
     * @param persoonslijst the persoonslijst
     */
    public void setPersoonslijst(Persoonslijst persoonslijst) {
        this.persoonslijst = persoonslijst;
    }

    /**
     * Is stop boolean.
     * @return the boolean
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * Sets stop.
     * @param stop the stop
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    /**
     * Gets selectie run id.
     * @return the selectie run id
     */
    public Integer getSelectieRunId() {
        return selectieRunId;
    }

    /**
     * Sets selectie run id.
     * @param selectieRunId the selectie run id
     */
    public void setSelectieRunId(Integer selectieRunId) {
        this.selectieRunId = selectieRunId;
    }

    /**
     * Sets selectie start datum.
     * @param selectieStartDatum the selectie start datum
     */
    public void setSelectieStartDatum(final int selectieStartDatum) {
        this.selectieStartDatum = selectieStartDatum;
    }

    /**
     * Gets selectie start datum.
     * @return the selectie start datum
     */
    public int getSelectieStartDatum() {
        return selectieStartDatum;
    }
}
