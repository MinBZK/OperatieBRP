/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;

/**
 * Bericht verwerking voor Lo3. Dit bericht fungeert ook als context om de BRP leveringscontext hier niet mee te vervuilen.
 */
public interface Bericht {

    /**
     * Geef de persoon voor wie een bericht wordt gemaakt.
     * @return persoon
     */
    Persoonslijst getPersoonsgegevens();

    /**
     * Converteer de persoon (of mutatie) (die het bericht al kent) naar Lo3.
     * @param cache conversie cache
     */
    void converteerNaarLo3(ConversieCache cache);

    /**
     * Filter de geconverteerde persoon of mutatie obv de gegeven rubrieken.
     * @param rubrieken rubrieken
     * @return false, als de levering moet stoppen (omdat bv alle rubrieken zijn gefilterd), anders true
     */
    boolean filterRubrieken(List<String> rubrieken);

    /**
     * Maak het uitgaande bericht obv de gefilterde rubrieken.
     * @return uitgaand bericht
     */
    String maakUitgaandBericht();

    /**
     * @return het soort synchronisatie
     */
    SoortSynchronisatie getSoortSynchronisatie();
}
