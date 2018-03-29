/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.selectie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.List;

/**
 * SelectieVerwerkTaak. Informatie voor een verwerk selectie taak.
 */
@JsonAutoDetect
public final class SelectieVerwerkTaakBericht {

    private List<SelectiePersoonBericht> personen;
    private List<SelectieAutorisatieBericht> selectieAutorisaties;
    private Integer selectieRunId;
    private int selectieStartDatum;

    /**
     * Gets personen.
     * @return the personen
     */
    public List<SelectiePersoonBericht> getPersonen() {
        return personen;
    }

    /**
     * Sets personen.
     * @param personen the personen
     */
    public void setPersonen(List<SelectiePersoonBericht> personen) {
        this.personen = personen;
    }

    /**
     * Gets selectie autorisaties.
     * @return the selectie autorisaties
     */
    public List<SelectieAutorisatieBericht> getSelectieAutorisaties() {
        return selectieAutorisaties;
    }

    /**
     * Sets selectie autorisaties.
     * @param selectieAutorisaties the selectie autorisaties
     */
    public void setSelectieAutorisaties(List<SelectieAutorisatieBericht> selectieAutorisaties) {
        this.selectieAutorisaties = selectieAutorisaties;
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
