/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.util.Set;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Class voor het testen van deltabepaling/toepassing code. Deze class bevat 2 personen. De nieuw geconverteerde persoon
 * en de database persoon.
 */
public class TestPersonen {

    private Pair<Persoon, Set<Onderzoek>> kluizenaar;
    private Pair<Persoon, Set<Onderzoek>> dbPersoon;

    public TestPersonen(Persoon dbPersoon, Set<Onderzoek> dbPersoonOnderzoek, Persoon kluizenaar, Set<Onderzoek> kluizenaarOnderzoek) {
        this.kluizenaar = Pair.of(kluizenaar, kluizenaarOnderzoek);
        this.dbPersoon = Pair.of(dbPersoon, dbPersoonOnderzoek);
    }

    /**
     * Geef de waarde van db persoon.
     *
     * @return db persoon
     */
    public Persoon getDbPersoon() {
        return dbPersoon.getLeft();
    }

    /**
     * Geef de waarde van db persoon onderzoek.
     *
     * @return db persoon onderzoek
     */
    public Set<Onderzoek> getDbPersoonOnderzoek() {
        return dbPersoon.getRight();
    }

    /**
     * Geef de waarde van kluizenaar.
     *
     * @return kluizenaar
     */
    public Persoon getKluizenaar() {
        return kluizenaar.getLeft();
    }

    /**
     * Geef de waarde van kluizenaar onderzoek.
     *
     * @return kluizenaar onderzoek
     */
    public Set<Onderzoek> getKluizenaarOnderzoek() {
        return kluizenaar.getRight();
    }
}
