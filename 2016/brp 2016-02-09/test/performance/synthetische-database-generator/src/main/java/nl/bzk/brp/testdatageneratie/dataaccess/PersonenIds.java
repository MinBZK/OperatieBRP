/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.dataaccess;

import java.io.Serializable;
import java.util.List;

/**
 * Personen ids.
 */
public class PersonenIds extends Ids {
    private final List<Integer> uitzonderingen;

    /**
     * Instantieert Personen ids.
     *
     * @param entiteit entiteit
     * @param uitzonderingen uitzonderingen
     */
    public PersonenIds(final Class<? extends Serializable> entiteit, final List<Integer> uitzonderingen) {
        super(entiteit);
        this.uitzonderingen = uitzonderingen;
    }

//    /**
//     * Geef de volgende id uit de database, onafhankelijk of dit een 'art-data' is of niet.
//     * Is nodig voor administratievehandelingBijgehoudenPersonen
//     * @return long id
//     */
//    public long selecteerIdZonderFilter() {
//        long id;
//        do {
//            id = RandomUtil.nextLong(range) + min;
//        } while (!super.isAanwezig(id));
//        return id;
//    }


    @Override
    public boolean isAanwezig(final long id) {
        // TODO test dat je niet per ongeluk een brpPersoonId op pikt, want dit vervuilt onze brp testdata.
        if (super.isAanwezig(id)) {
            return !uitzonderingen.contains((int) id);
        }
        return false;
    }

    /**
     * Geeft vrije persoon id.
     *
     * @param current current
     * @return vrije persoon id
     */
    private long getFree(final long current) {
        long lnext = current;
        while (uitzonderingen.contains(new Integer((int) lnext))) {
            lnext++;
        }
        return lnext;
    }

    @Override
    public long getMin() {
        return getFree(min);
    }

    @Override
    public long getVolgende(final long offset) {
        return getFree(offset + 1);
    }

    /**
     * Is brp.
     *
     * @param id id
     * @return the boolean
     */
    public boolean isBrp(final Integer id) {
        return uitzonderingen.contains(id);
    }

}
