/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;

/**
 * Set wrapper implementatie voor de bijhoudings persoon groepen.
 * @param <T> type van de groep (bv {@link PersoonIDHistorie})
 */
public class BijhoudingPersoonGroepSet<T> implements Set<T> {
    public static final String DEZE_METHODE_WORDT_NIET_ONDERSTEUND_OP_DEZE_SET = "Deze methode wordt niet ondersteund op deze set";
    private final Map<Persoon, Set<T>> persoonToGroepHistorie = new LinkedHashMap<>();

    /**
     * Voegt een persoon en zijn groep historie toe aan deze set.
     * @param persoon de persoon
     * @param groepHistorie de groep historie bv {@link PersoonIDHistorie}
     */
    public void addPersoonGroepSet(final Persoon persoon, final Set<T> groepHistorie) {
        persoonToGroepHistorie.put(persoon, groepHistorie);
    }

    private Set<T> geefEerste() {
        return persoonToGroepHistorie.values().isEmpty() ? Collections.emptySet() : persoonToGroepHistorie.values().iterator().next();
    }

    @Override
    public int size() {
        return geefEerste().size();
    }

    @Override
    public boolean isEmpty() {
        return geefEerste().isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return geefEerste().contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return geefEerste().iterator();
    }

    @Override
    public Object[] toArray() {
        return geefEerste().toArray();
    }

    @Override
    public <T1> T1[] toArray(final T1[] a) {
        return geefEerste().toArray(a);
    }

    @Override
    public boolean add(final T t) {
        boolean result = false;
        for (final Map.Entry<Persoon, Set<T>> entry : persoonToGroepHistorie.entrySet()) {
            if (t instanceof PersoonIDHistorie) {
                final PersoonIDHistorie historie = ((PersoonIDHistorie) t).kopieer();
                historie.setPersoon(entry.getKey());
                MaterieleHistorie.voegVoorkomenToe(historie, (Set<PersoonIDHistorie>) entry.getValue());
                result = true;
            } else if (t instanceof PersoonGeboorteHistorie) {
                final PersoonGeboorteHistorie historie = ((PersoonGeboorteHistorie) t).kopieer();
                historie.setPersoon(entry.getKey());
                FormeleHistorie.voegToe(historie, (Set<PersoonGeboorteHistorie>) entry.getValue());
                result = true;
            } else if (t instanceof PersoonSamengesteldeNaamHistorie) {
                final PersoonSamengesteldeNaamHistorie historie = ((PersoonSamengesteldeNaamHistorie) t).kopieer();
                historie.setPersoon(entry.getKey());
                MaterieleHistorie.voegVoorkomenToe(historie, (Set<PersoonSamengesteldeNaamHistorie>) entry.getValue());
                result = true;
            } else if (t instanceof PersoonGeslachtsaanduidingHistorie) {
                final PersoonGeslachtsaanduidingHistorie historie = ((PersoonGeslachtsaanduidingHistorie) t).kopieer();
                historie.setPersoon(entry.getKey());
                MaterieleHistorie.voegVoorkomenToe(historie, (Set<PersoonGeslachtsaanduidingHistorie>) entry.getValue());
                result = true;
            } else {
                throw new UnsupportedOperationException(DEZE_METHODE_WORDT_NIET_ONDERSTEUND_OP_DEZE_SET);
            }
        }
        return result;
    }

    @Override
    public boolean remove(final Object o) {
        throw new UnsupportedOperationException(DEZE_METHODE_WORDT_NIET_ONDERSTEUND_OP_DEZE_SET);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return geefEerste().containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        throw new UnsupportedOperationException(DEZE_METHODE_WORDT_NIET_ONDERSTEUND_OP_DEZE_SET);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        throw new UnsupportedOperationException(DEZE_METHODE_WORDT_NIET_ONDERSTEUND_OP_DEZE_SET);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        throw new UnsupportedOperationException(DEZE_METHODE_WORDT_NIET_ONDERSTEUND_OP_DEZE_SET);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(DEZE_METHODE_WORDT_NIET_ONDERSTEUND_OP_DEZE_SET);
    }
}
