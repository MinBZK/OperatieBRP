/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.util.List;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;


/**
 * Berichtfactory voor Zoek Persoon.
 */
@FunctionalInterface
public interface ZoekPersoonBerichtFactory {

    /**
     * Maak Zoek persoon bericht.
     * @param persoonsgegevens persoonsgegevens
     * @param autorisatiebundel autorisatiebundel
     * @param bevragingVerzoek bevragingsverzoek
     * @param peilmomentMaterieel het materiele peilmoment
     * @return geefDetailsPersoonBericht
     */
    VerwerkPersoonBericht maakZoekPersoonBericht(List<Persoonslijst> persoonsgegevens,
                                                 Autorisatiebundel autorisatiebundel, BevragingVerzoek bevragingVerzoek, Integer peilmomentMaterieel);
}
