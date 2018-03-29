/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;

/**
 * Bewerk een Persoon om de oude logging los te koppelen van de bestaande acties, en te vervangen door de nieuwe
 * logging.
 */
public final class LoggingVerwerker {

    /**
     * Koppel de nieuwe logging aan de bijgewerkte Persoon. Kan ook Acties vervangen waar nodig, zodat 1 Lo3Voorkomen
     * met 1 Actie overeenkomt.
     * @param actieHerkomstMap de map van oude acties naar nieuwe logging.
     * @param persoonActies alle acties van de persoon
     */
    public void verwerk(final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap, final Map<BRPActie, Set<FormeleHistorie>> persoonActies) {
        final Set<Lo3Voorkomen> nieuweActieVoorkomens = bepaalNieuweActieVoorkomens(persoonActies);
        final List<BRPActie> oudeActies = bepaalOudeActies(persoonActies);

        for (final BRPActie oudeActie : oudeActies) {
            final Lo3Voorkomen bijgewerktVoorkomen = actieHerkomstMap.get(oudeActie);
            if (bijgewerktVoorkomen != null && !nieuweActieVoorkomens.contains(bijgewerktVoorkomen)) {
                // Voorkomen koppelen aan actie. setActie werkt ook de actie bij.
                bijgewerktVoorkomen.setActie(oudeActie);
            }
        }
    }

    private Set<Lo3Voorkomen> bepaalNieuweActieVoorkomens(final Map<BRPActie, Set<FormeleHistorie>> persoonActies) {
        final Set<Lo3Voorkomen> resultaat = new HashSet<>();
        for (final BRPActie actie : persoonActies.keySet()) {
            if (actie.getLo3Voorkomen() != null) {
                resultaat.add(actie.getLo3Voorkomen());
            }
        }

        return resultaat;
    }

    private List<BRPActie> bepaalOudeActies(final Map<BRPActie, Set<FormeleHistorie>> persoonActies) {
        final List<BRPActie> resultaat = new LinkedList<>();
        for (final BRPActie actie : persoonActies.keySet()) {
            if (actie.getLo3Voorkomen() == null) {
                resultaat.add(actie);
            }
        }

        return resultaat;
    }
}
