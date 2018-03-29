/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import java.util.List;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.FingerPrint;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.VerschilAnalyseRegel;

/**
 * Class waarin het resultaat van de verschil analyse wordt opgeslagen.
 */
public class VergelijkResultaat {
    private final List<VerschilAnalyseRegel> regels;
    private final FingerPrint vingerafdruk;

    /**
     * Constructor voor de {@link VergelijkResultaat}.
     * @param regels de regels van de verschil analyse
     * @param vingerafdruk de vingerafdruk die gemaakt is op basis van de verschilanalyse regels.
     */
    public VergelijkResultaat(final List<VerschilAnalyseRegel> regels, final FingerPrint vingerafdruk) {
        this.regels = regels;
        this.vingerafdruk = vingerafdruk;
    }

    /**
     * Geeft de regels terug.
     * @return de lijst van regels
     */
    public List<VerschilAnalyseRegel> getRegels() {
        return regels;
    }

    /**
     * Geeft de vingerafdruk terug.
     * @return de vingerafdruk voor dit resultaat
     */
    public FingerPrint getVingerafdruk() {
        return vingerafdruk;
    }
}
