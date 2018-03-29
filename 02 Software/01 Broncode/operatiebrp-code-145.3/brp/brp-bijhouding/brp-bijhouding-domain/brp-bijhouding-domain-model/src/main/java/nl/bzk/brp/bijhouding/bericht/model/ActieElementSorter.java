/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Deze class bepaalt de verwerkingsvolgorde van de actie elementen door deze te sortering volgens het volgende algoritme: <p/>
 * <ol>
 * <li>de eerste actie is de hoofdactie, deze blijft altijd de eerste</li>
 * <li>de volgorde van de acties zoals deze in het bericht staan</li>
 * <li>DAG of DEG van oud naar nieuw, bij gelijke DAG en DEG eerst DEG</li>
 * <li>acties zonder DAG en DEG gebruiken de DAG van de hoofdactie</li>
 * </ol>
 */
final class ActieElementSorter {

    private ActieElementSorter() {
    }

    /**
     * Maakt een gesorteerde lijst met acties zodat deze in de juiste volgorde verwerkt kunnen worden. De gegeven lijst wordt niet gewijzigd.
     * @param acties de lijst met de te sorteren acties
     * @return de gesorteerde lijst
     */
    static <T extends ActieElement> List<T> sort(final List<T> acties) {
        final List<T> result = new ArrayList<>(acties.size());
        if (!acties.isEmpty()) {
            final List<SorteerbareActieElement<T>> teSorterenActieWrappers = wrap(acties);
            result.add(teSorterenActieWrappers.remove(0).actie);
            teSorterenActieWrappers.sort(ActieElementSorter::sort);
            result.addAll(unwrap(teSorterenActieWrappers));
        }
        return result;
    }

    private static int sort(final SorteerbareActieElement actieWrapper1, final SorteerbareActieElement actieWrapper2) {
        final boolean isDatum1Dag = actieWrapper1.actie.getDatumEindeGeldigheid() == null;
        final boolean isDatum2Dag = actieWrapper2.actie.getDatumEindeGeldigheid() == null;

        final Integer datum1 = ActieElementSorter.getAanvangOfEindeGeldigheid(actieWrapper1);
        final Integer datum2 = ActieElementSorter.getAanvangOfEindeGeldigheid(actieWrapper2);

        final int result;
        if (datum1.intValue() == datum2.intValue()) {
            if (isDatum1Dag && !isDatum2Dag) {
                result = 1;
            } else if (!isDatum1Dag && isDatum2Dag) {
                result = -1;
            } else {
                result = 0;
            }
        } else {
            result = Integer.compare(datum1, datum2);
        }
        return result;
    }

    private static <T extends ActieElement> List<SorteerbareActieElement<T>> wrap(final List<T> acties) {
        final List<SorteerbareActieElement<T>> results = new ArrayList<>(acties.size());
        final int datumHoofdActie = getAanvangOfEindeGeldigheid(acties.get(0)) == null ? 0 : getAanvangOfEindeGeldigheid(acties.get(0));
        for (final T actieElement : acties) {
            results.add(new SorteerbareActieElement<T>(datumHoofdActie, actieElement));
        }
        return results;
    }

    private static <T extends ActieElement> List<T> unwrap(final List<SorteerbareActieElement<T>> actieWrappers) {
        final List<T> results = new ArrayList<>(actieWrappers.size());
        for (final SorteerbareActieElement<T> actieWrapper : actieWrappers) {
            results.add(actieWrapper.actie);
        }
        return results;
    }

    private static int getAanvangOfEindeGeldigheid(final SorteerbareActieElement actieWrapper) {
        final Integer result = getAanvangOfEindeGeldigheid(actieWrapper.actie);
        if (result != null) {
            return result;
        } else {
            return actieWrapper.datumHoofdActie;
        }
    }

    private static <T extends ActieElement> Integer getAanvangOfEindeGeldigheid(final T actie) {
        if (actie.getDatumAanvangGeldigheid() != null) {
            return actie.getDatumAanvangGeldigheid().getWaarde();
        } else {
            return BmrAttribuut.getWaardeOfNull(actie.getDatumEindeGeldigheid());
        }
    }

    private static final class SorteerbareActieElement<T extends ActieElement> {
        private final int datumHoofdActie;
        private final T actie;

        private SorteerbareActieElement(final int datumHoofdActie, final T actie) {
            this.datumHoofdActie = datumHoofdActie;
            this.actie = actie;
        }
    }
}
