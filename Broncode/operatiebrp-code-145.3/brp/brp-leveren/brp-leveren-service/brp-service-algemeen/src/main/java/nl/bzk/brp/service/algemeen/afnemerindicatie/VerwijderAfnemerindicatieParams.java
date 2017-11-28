/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.afnemerindicatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Param object voor verwijderen afnemerindicatie.
 */
public final class VerwijderAfnemerindicatieParams {

    private final ToegangLeveringsAutorisatie toegangLeveringsautorisatie;
    private final Persoonslijst persoonslijst;
    private final int verantwoordingDienstId;

    /**
     * Constructor voor de input parameter voor verwijderen afnemerindicatie.
     * @param toegangLeveringsautorisatie de autorisatie
     * @param persoonslijst de persoon waarvan de indicatie verwijderd wordt
     * @param verantwoordingDienstId de dienst welke het verwijderen verantwoordt
     */
    public VerwijderAfnemerindicatieParams(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie, final Persoonslijst persoonslijst,
                                           final int verantwoordingDienstId) {
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
        this.persoonslijst = persoonslijst;
        this.verantwoordingDienstId = verantwoordingDienstId;
    }

    public ToegangLeveringsAutorisatie getToegangLeveringsautorisatie() {
        return toegangLeveringsautorisatie;
    }

    public Persoonslijst getPersoonslijst() {
        return persoonslijst;
    }

    public int getVerantwoordingDienstId() {
        return verantwoordingDienstId;
    }
}
