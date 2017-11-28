/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;

/**
 * De repository interface voor Leveringsautorisaties.
 */
public interface LeveringsautorisatieRepository {

    /**
     * Haalt alle ToegangLeveringsautorisaties op (zonder verdere associaties).
     * @return lijst met alle toegangLeveringsautorisaties
     */
    List<ToegangLeveringsAutorisatie> haalAlleToegangLeveringsautorisatiesOpZonderAssociaties();

    /**
     * Haalt alle leveringsautorisaties op (zonder verdere associaties).
     * @return lijst met alle leveringsautorisaties
     */
    List<Leveringsautorisatie> haalAlleLeveringsautorisatiesOpZonderAssocaties();

    /**
     * Haalt alle Dienstbundels op (zonder verdere associaties).
     * @return lijst met alle dienstbundels
     */
    List<Dienstbundel> haalAlleDienstbundelsOpZonderAssocaties();

    /**
     * Haalt alle Diensten op (zonder verdere associaties).
     * @return lijst met alle diensten
     */
    List<Dienst> haalAlleDienstenOpZonderAssocaties();

    /**
     * Haalt alle dienstbundelGroepen op (zonder verdere associaties).
     * @return lijst met alle dienstbundelgroepen
     */
    List<DienstbundelGroep> haalAlleDienstbundelGroepenOpZonderAssocaties();

    /**
     * Haalt alle dienstbundelLo3Rubrieken en Lo3Rubrieken op.
     * @return lijst met alle Dienstbundello3rubrieken
     */
    List<DienstbundelLo3Rubriek> haalAlleDienstbundelLo3Rubrieken();

    /**
     * Haalt alle dienstbundelGroepAttributen op.
     * @return lijst met alle dienstbundelGroepAttributen
     */
    List<DienstbundelGroepAttribuut> haalAlleDienstbundelGroepAttributenOpZonderAssocaties();
}
