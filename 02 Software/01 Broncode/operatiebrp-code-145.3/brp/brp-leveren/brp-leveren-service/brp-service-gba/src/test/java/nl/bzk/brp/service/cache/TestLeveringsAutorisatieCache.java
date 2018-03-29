/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.expressie.Expressie;

public class TestLeveringsAutorisatieCache implements LeveringsAutorisatieCache {

    private Map<String, List<ToegangLeveringsAutorisatie>> autorisaties = new HashMap<>();

    @Override
    public List<ToegangLeveringsAutorisatie> geefToegangleveringautorisatiesVoorGeautoriseerdePartij(final String partijCode) {
        return autorisaties.getOrDefault(partijCode, Collections.emptyList());
    }

    public void clear() {
        autorisaties.clear();
    }

    public void addAutorisatie(final String partijCode, ToegangLeveringsAutorisatie autorisatie) {
        autorisaties.computeIfAbsent(partijCode, key -> new ArrayList<>()).add(autorisatie);
    }

    @Override
    public CacheEntry herlaad(final PartijCacheImpl.Data partijData) {
        return null;
    }

    @Override
    public List<ToegangLeveringsAutorisatie> geefAlleToegangleveringsautorisaties() {
        return null;
    }

    @Override
    public Expressie geefPopulatiebeperking(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final Dienst dienst) {
        return null;
    }

    @Override
    public Expressie geefAttenderingExpressie(final Dienst dienst) {
        return null;
    }

    @Override
    public List<AttribuutElement> geefGeldigeElementen(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final Dienst dienst) {
        return null;
    }

    @Override
    public Leveringsautorisatie geefLeveringsautorisatie(final int leveringautorisatieId) {
        return null;
    }

    @Override
    public ToegangLeveringsAutorisatie geefToegangLeveringsautorisatie(final int leveringautorisatieId, final String partijCode) {
        return null;
    }

    @Override
    public ToegangLeveringsAutorisatie geefToegangLeveringsautorisatie(final Integer toegangLeveringsaugtorisatieId) {
        return null;
    }

    @Override
    public List<Autorisatiebundel> geefAutorisatieBundelsVoorMutatielevering() {
        return null;
    }

    @Override
    public Dienst geefDienst(final int dienstId) {
        return null;
    }
}
