/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.vergelijk;

import java.util.Set;
import java.util.TreeSet;

import nl.gba.gbav.lo3.GegevensSet;
import nl.gba.gbav.spontaan.verschilanalyse.VoorkomenMatch;

/**
 * Als de GBAv class VoorkomenMatch, maar dan wat eenvoudiger te verwerken voor JSON omdat alle lists gewoon exposed
 * zijn.
 */
public class VoorkomenVergelijking {
    private final Integer categorie;
    private final GegevensSet newVoorkomen;
    private final GegevensSet oldVoorkomen;
    private final Set<Integer> elements;
    private final String type;

    /**
     * Constructor.
     * 
     * @param voorkomenMatch
     *            de VoorkomenMatch waarop dit object zich zal baseren.
     */
    public VoorkomenVergelijking(final VoorkomenMatch voorkomenMatch) {
        categorie = voorkomenMatch.getCategorie();

        elements = new TreeSet<Integer>();

        if (voorkomenMatch.getDifference() != null) {
            elements.addAll(voorkomenMatch.getDifference().getCategorie(voorkomenMatch.getCategorie()).getElements());
        }

        newVoorkomen = voorkomenMatch.getNewVoorkomen();
        oldVoorkomen = voorkomenMatch.getOldVoorkomen();
        type = voorkomenMatch.getTypeDescription();
    }

    public final Integer getCategorie() {
        return categorie;
    }

    public final GegevensSet getNewVoorkomen() {
        return newVoorkomen;
    }

    public final GegevensSet getOldVoorkomen() {
        return oldVoorkomen;
    }

    public final Set<Integer> getElements() {
        return elements;
    }

    public final String getType() {
        return type;
    }
}
