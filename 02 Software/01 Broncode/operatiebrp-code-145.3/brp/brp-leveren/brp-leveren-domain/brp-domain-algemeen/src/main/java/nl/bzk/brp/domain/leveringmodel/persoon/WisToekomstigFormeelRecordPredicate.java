/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.function.Predicate;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Predicate voor het wegfilteren van alle voorkomens die gemaakt zijn ná een gegeven formeel punt.
 * Dit zijn records met actieInhoud of actieAanpassingGeldigheid welke behoren bij een
 * toekomstige handeling.
 */
final class WisToekomstigFormeelRecordPredicate implements Predicate<MetaRecord> {

    private final Set<Actie> toekomstigeActies;

    /**
     * Constructor.
     *
     * @param toekomstigeHandelingen Set met toekomstige administratiehandelingen
     */
    WisToekomstigFormeelRecordPredicate(final Set<AdministratieveHandeling> toekomstigeHandelingen) {
        this.toekomstigeActies = Sets.newHashSet();
        for (final AdministratieveHandeling administratieveHandeling : toekomstigeHandelingen) {
            this.toekomstigeActies.addAll(administratieveHandeling.getActies());
        }
    }

    @Override
    public boolean test(final MetaRecord metaRecord) {
        //true is behouden
        return !toekomstigeActies.contains(metaRecord.getActieInhoud())
                && !toekomstigeActies.contains(metaRecord.getActieAanpassingGeldigheid());
    }
}
