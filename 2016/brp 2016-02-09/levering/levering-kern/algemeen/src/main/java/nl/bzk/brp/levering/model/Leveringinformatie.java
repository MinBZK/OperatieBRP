/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.model;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;

/**
 */
public class Leveringinformatie {

    private final ToegangLeveringsautorisatie toegangLeveringsautorisatie;
    private final Dienst                      dienst;

    public Leveringinformatie(final ToegangLeveringsautorisatie toegangLeveringsautorisatie, final Dienst dienst) {
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
        this.dienst = dienst;
    }

    public ToegangLeveringsautorisatie getToegangLeveringsautorisatie() {
        return toegangLeveringsautorisatie;
    }

    public Dienst getDienst() {
        return dienst;
    }

    /**
     * Geeft aan of deze Leveringinformatie geldig is op de opgegeven datum.
     * Checkt dat toegangleveringautorisatie geldig en niet geblokkeerd is
     * Checkt dat leveringautorisatie geldig en niet geblokkeerd is
     * Checkt dat dienstbundel hij niet geblokkeerd is (check op geldigheid niet nodig)
     * Check dat dienst geldig en niet geblokkeerd is
     *
     * @param datum de datum
     * @return true als deze geldig is op de gegeven datum, anders false
     * @brp.bedrijfsregel R1261
     * @brp.bedrijfsregel BRLV0029
     * @brp.bedrijfsregel R2052
     * @brp.bedrijfsregel R2056
     * @brp.bedrijfsregel R1263
     * @brp.bedrijfsregel R1264
     * @brp.bedrijfsregel R1265
     */
    @Regels({ Regel.R1261, Regel.BRLV0029, Regel.R2056, Regel.R1263, Regel.R1264, Regel.R1258 })
    public final boolean isGeldigOp(final DatumAttribuut datum) {
        return toegangLeveringsautorisatie.getLeveringsautorisatie().isGeldigOp(datum) && !toegangLeveringsautorisatie.getLeveringsautorisatie()
            .isGeblokkeerd() && toegangLeveringsautorisatie.isGeldigOp(datum) && !toegangLeveringsautorisatie.isGeblokkeerd() && dienst
            .isDienstGeldigOp(datum) && !dienst.isGeblokkeerd() && !dienst.getDienstbundel().isGeblokkeerd();
    }

    public SoortDienst getSoortDienst() {
        return dienst.getSoort();
    }

}
