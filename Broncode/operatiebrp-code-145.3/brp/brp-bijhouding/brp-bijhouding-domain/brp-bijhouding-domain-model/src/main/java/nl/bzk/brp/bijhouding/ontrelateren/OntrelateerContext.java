/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ontrelateren;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;

/**
 * Deze class draagt zorg voor de kennisoverdracht tussen de verschillende ontrelateer stappen. Voor elk bijhoudings bericht dat verwerkt wordt dient
 * slechts 1 OntrelateerContext object gemaakt te worden.
 */
public final class OntrelateerContext {

    private final Map<Long, BRPActie> relatieIdBrpActieMap = new HashMap<>();

    /**
     * Geeft de BRPActie terug die gebruikt dient te worden als verantwoording voor het ontrelateren van de gegeven relatie. Voor elke relatie die ontrelateerd
     * moet worden wordt slechts 1 {@link AdministratieveHandeling} en 1 {@link BRPActie} gemaakt.
     * @param datumTijdRegistratie het tijdstip dat gebruikt moet worden om de ontrelateer {@link AdministratieveHandeling} en {@link BRPActie} te maken
     * @param teOntrelaterenRelatie de relatie waarvoor de ontrelateer verantwoording moet worden gegeven
     * @return de verantwoordingsgegevend die gebruikt moet worden bij het ontrelateren van de gegeven relatie
     */
    public BRPActie maakOntrelateerActie(final Timestamp datumTijdRegistratie, final Relatie teOntrelaterenRelatie) {
        if (teOntrelaterenRelatie == null || teOntrelaterenRelatie.getId() == null) {
            throw new IllegalArgumentException("Alleen opgeslagen relaties kunnen ontrelateerd worden.");
        }
        if (!relatieIdBrpActieMap.containsKey(teOntrelaterenRelatie.getId())) {
            final AdministratieveHandeling
                    administratieveHandeling =
                    new AdministratieveHandeling(ApplicationContextProvider.getDynamischeStamtabelRepository().getPartijByCode(Partij.PARTIJ_CODE_BRP),
                            SoortAdministratieveHandeling.ONTRELATEREN, datumTijdRegistratie);
            relatieIdBrpActieMap.put(teOntrelaterenRelatie.getId(), new BRPActie(
                    SoortActie.ONTRELATEREN,
                    administratieveHandeling,
                    administratieveHandeling.getPartij(),
                    administratieveHandeling.getDatumTijdRegistratie()));
        }
        return relatieIdBrpActieMap.get(teOntrelaterenRelatie.getId());
    }
}
