/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;

public final class BerichtTeller<T> implements BerichtVerwerker<T> {

    private final List<T> berichten = new ArrayList<>();

    @Override
    public void voegBerichtToe(final T bericht) {
        berichten.add(bericht);
    }

    @Override
    public void verwerkBerichten() {
    }

    @Override
    public int aantalBerichten() {
        return berichten.size();
    }

    @Override
    public long aantalVerzonden() {
        return berichten.size();
    }

    public List<T> getBerichten() {
        return berichten;
    }
}
