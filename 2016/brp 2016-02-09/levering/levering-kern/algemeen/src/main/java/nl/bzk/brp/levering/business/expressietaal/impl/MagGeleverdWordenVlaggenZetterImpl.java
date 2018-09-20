/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.expressietaal.impl;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpAttribuutReferentieExpressie;
import nl.bzk.brp.levering.business.expressietaal.MagGeleverdWordenVlaggenZetter;
import nl.bzk.brp.model.basis.Attribuut;

/**
 * Dit is de klasse die de MagGeleverdWorden vlaggen zet.
 */
public class MagGeleverdWordenVlaggenZetterImpl implements MagGeleverdWordenVlaggenZetter {

    /**
     * Zet de magGeleverdWorden vlaggen voor alle elementen die de expressie teruggeeft. Dit gebeurt recursief, omdat
     * onder een expressie een lijst van expressies kan hangen.
     *
     * @param element De expressie.
     * @param waarde  De waarde waarop de vlaggen gezet moeten worden.
     * @return Een lijst van alle geraakte attributen.
     */
    public final List<Attribuut> zetMagGeleverdWordenVlaggenOpWaarde(final Expressie element, final boolean waarde) {
        final List<Attribuut> lijstVanGeraakteAttributen = new ArrayList<>();

        if (element instanceof LijstExpressie) {
            final LijstExpressie historieElementen = (LijstExpressie) element;

            for (final Expressie subElement : historieElementen.getElementen()) {
                lijstVanGeraakteAttributen.addAll(zetMagGeleverdWordenVlaggenOpWaarde(subElement, waarde));
            }
        } else if (element instanceof BrpAttribuutReferentieExpressie) {
            final Attribuut attribuut = element.getAttribuut();
            if (attribuut != null) {
                attribuut.setMagGeleverdWorden(waarde);
                lijstVanGeraakteAttributen.add(attribuut);
            }
        }

        return lijstVanGeraakteAttributen;
    }

}
