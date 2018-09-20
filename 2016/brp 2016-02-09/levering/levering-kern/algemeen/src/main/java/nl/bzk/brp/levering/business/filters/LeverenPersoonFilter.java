/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.filters;

import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * Filter interface. Deze filters kunnen aangeven of de levering van een persoon
 * wel door kan gaan. Deze filters bevatten de voorwaarden voor het mogen leveren
 * op basis van de beschrijving(en) in de Use-Cases.
 */
public interface LeverenPersoonFilter {

    /**
     * Bepaalt of de levering van een persoon kan doorgaan, gegeven de parameters en de
     * functionele omschrijving in de UC.
     *
     * @param persoon De persoon
     * @param populatie De positie van de persoon t.o.v. de populatieBeperking
     * @param leveringAutorisatie De leveringAutorisatie waarvoor de bepaling plaats vindt
     * @param administratieveHandeling De administratieveHandeling waardoor de persoon geraakt is
     * @return als de levering om logische redenen mag doorgaan
     * @throws ExpressieExceptie de expressie exceptie
     */
    boolean magLeverenDoorgaan(PersoonHisVolledig persoon, Populatie populatie,
            Leveringinformatie leveringAutorisatie,
            AdministratieveHandelingModel administratieveHandeling) throws ExpressieExceptie;
}
