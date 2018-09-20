/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.vergelijk;

import java.util.ArrayList;
import java.util.List;

import nl.gba.gbav.lo3.StapelGS;
import nl.gba.gbav.spontaan.verschilanalyse.StapelMatch;

/**
 * Als de GBAv class StapelMatch, maar dan wat eenvoudiger te verwerken voor JSON omdat alle lists gewoon exposed zijn.
 */
public class StapelVergelijking {
    private final int categorie;
    private final List<StapelGS> newStapels;
    private final List<StapelGS> oldStapels;
    private final String type;
    private final List<VoorkomenVergelijking> voorkomenMatches;

    /**
     * Constructor.
     * 
     * @param match
     *            de StapelMatch waarop deze StapelVergelijking zich zal baseren
     */
    public StapelVergelijking(final StapelMatch match) {
        categorie = match.getCategorie();
        newStapels = match.getNewStapels();
        oldStapels = match.getOldStapels();
        type = match.getTypeDescription();

        voorkomenMatches = new ArrayList<VoorkomenVergelijking>();
        for (int i = 0; i < match.getIdxVoorkomenMatchWithVA() + 1; i++) {
            // controleer op not-null omdat de idx niet altijd goed op -1 gezet is indien leeg
            if (match.getVoorkomenMatch(i) != null) {
                voorkomenMatches.add(new VoorkomenVergelijking(match.getVoorkomenMatch(i)));
            }
        }
    }

    public final int getCategorie() {
        return categorie;
    }

    public final List<StapelGS> getNewStapels() {
        return newStapels;
    }

    public final List<StapelGS> getOldStapels() {
        return oldStapels;
    }

    public final String getType() {
        return type;
    }

    public final List<VoorkomenVergelijking> getVoorkomenMatches() {
        return voorkomenMatches;
    }
}
