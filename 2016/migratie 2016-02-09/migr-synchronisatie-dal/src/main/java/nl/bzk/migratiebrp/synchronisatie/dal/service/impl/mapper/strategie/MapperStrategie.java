/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

/**
 * De strategie die wordt gevolgt voor het mappen van een BrpStapel uit het migratie model naar de corresponderen
 * entiteiten in het operationele gegevensmodel van de BRP.
 * 
 * @param <T>
 *            het type inhoud van de BrpGroep
 * @param <E>
 *            de topLevelEntiteit
 */
public interface MapperStrategie<T extends BrpGroepInhoud, E extends Object> {

    /**
     * Mapt de stapel uit het BRP-migratiemodel naar het BRP-datamodel en voegt deze toe op de meegegeven toplevel
     * enititeit (zoals bijvoorbeeld Persoon of Nationaliteit.
     * 
     * @param brpStapel
     *            De stapel uit het BRP-migratiemodel. Mag wel null zijn, in dit geval heeft een aanroep van de methode
     *            mapVanMigratie geen wijziging op de meegegeven topLevelEntiteit tot gevolg.
     * @param topLevelEntiteit
     *            De topLevelEntiteit waaraan de groepen worden toegevoegd. Mag niet null zijn.
     */
    void mapVanMigratie(final BrpStapel<T> brpStapel, final E topLevelEntiteit);
}
