/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DalUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker.LoggingVerwerker;

/**
 * Implementatie voor het bepalen en verwerken van de delta op logging (verantwoording conversie).
 */
public final class LoggingDeltaProces implements DeltaProces {

    @Override
    public void bepaalVerschillen(final DeltaBepalingContext context) {
        // Oude logging (Lo3Voorkomen) verwijderen uit db
        // Inladen van alle berichten uit Hibernate naar een eigen lijst om ConcurrentModificationExceptions te
        // voorkomen.
        final List<Lo3Bericht> lo3Berichten = new LinkedList<>(context.getBestaandePersoon().getLo3Berichten());

        for (final Lo3Bericht bericht : lo3Berichten) {
            if (!bericht.getVoorkomens().isEmpty()) {
                for (final Iterator<Lo3Voorkomen> iterator = bericht.getVoorkomens().iterator(); iterator.hasNext(); ) {
                    filterVoorkomen(iterator);
                }
            }
        }
    }

    private void filterVoorkomen(final Iterator<Lo3Voorkomen> iterator) {
        final Lo3Voorkomen lo3Voorkomen = iterator.next();
        if (isNietCat07OfCat13Voorkomen(lo3Voorkomen)) {
            maakLo3VoorkomenLeeg(lo3Voorkomen);
            iterator.remove();
        }
    }

    private Lo3Voorkomen maakLo3VoorkomenLeeg(final Lo3Voorkomen lo3Voorkomen) {
        if (lo3Voorkomen.getActie() != null) {
            lo3Voorkomen.getActie().setLo3Voorkomen(null);
            lo3Voorkomen.setActie(null);
        }
        return lo3Voorkomen;
    }

    @Override
    public void verwerkVerschillen(final DeltaBepalingContext context) {
        final Map<BRPActie, Set<FormeleHistorie>> persoonActies = DalUtil.verzamelActies(context.getBestaandePersoon());
        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = context.getActieHerkomstMap();
        final Lo3Bericht lo3Bericht = context.getLo3Bericht();
        final Persoon bestaandePersoon = context.getBestaandePersoon();

        new LoggingVerwerker().verwerk(actieHerkomstMap, persoonActies);

        lo3Bericht.setPersoon(bestaandePersoon);
        bestaandePersoon.addLo3Bericht(lo3Bericht);
    }

    private boolean isNietCat07OfCat13Voorkomen(final Lo3Voorkomen lo3Voorkomen) {
        return !("07".equals(lo3Voorkomen.getCategorie()) || "13".equals(lo3Voorkomen.getCategorie()));
    }
}
