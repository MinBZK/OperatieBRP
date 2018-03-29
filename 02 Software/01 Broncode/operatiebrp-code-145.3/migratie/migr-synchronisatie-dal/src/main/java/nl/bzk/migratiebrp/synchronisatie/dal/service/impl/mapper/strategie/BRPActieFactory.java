/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;

/**
 * BRPActies zijn geen value-objects. Er wordt vanuit verschillende BRP entiteiten naar BRP acties verwezen. Om te
 * zorgen dat hetzelfde BRPActie object wordt gebruikt - om associaties te creeeren - dient de creatie van deze BRPActie
 * per persoon gemanaged te worden.
 * <p/>
 * Vanuit het migratie model wordt een Long (ID) gebruikt om aan te geven dat twee acties gelijk zijn ondanks dat het om
 * verschillende objecten gaat. Omdat bij de mapping naar het operationele BRP model met entities wordt gewerkt en de
 * technische IDs door de JPA provider worden beheerd dienen de objecten daadwerkelijk hetzelfde te zijn wanneer ze
 * gemapped worden op acties uit het migratie model waarbij de id gelijk is.
 * <p/>
 * Dus object 1 van type BrpActie met id = 1 en object 2 van type BrpActie met id = 1 moet worden gemapped op één object
 * BRPActie waarbij alle associates op dit object worden gelegd.
 * <p/>
 * De class is niet threadsafe. Het ligt voor de hand om één BRPActieFactory per te converteren persoonslijst aan te
 * maken.
 */
public interface BRPActieFactory {

    /**
     * Converteerd de meegegeven migratieActie naar de corresponderen BRPActie uit het operationele gegevensmodel van de
     * BRP. Als voor de {@link BrpActie#getId()} al een BRPActie was geconverteerd dan wordt dit object geretourneerd.
     * @param migratieActie de te converteren BrpActie uit het migratie model, mag null zijn
     * @return de geconverteerde BRPActie uit het operationele gegevensmodel van de BRP of null als migratieActie null is
     * @throws NullPointerException als migratieActie niet null is maar <code>migratieActie.getId</code> is null
     */
    BRPActie getBRPActie(BrpActie migratieActie);

    /**
     * De administratieve handeling waaronder alle acties worden geplaatst.
     * @return de administratieve handeling waaronder alle acties worden geplaatst.
     */
    AdministratieveHandeling getAdministratieveHandeling();

    /**
     * Het (dummy) lo3bericht waaraan alle lo3voorkomens als tracing informatie zijn gekoppeld.
     * @return het lo3bericht waaraan alle lo3voorkomens als tracing informatie zijn gekoppeld.
     */
    Lo3Bericht getLo3Bericht();
}
