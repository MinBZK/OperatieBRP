/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Basis klasse voor alle statische object typen.
 */
public abstract class AbstractStatischObjectType extends AbstractObjectType implements Onderzoekbaar, Nullable {

    private boolean inOnderzoek;

    private SoortNull soortNull;

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

    @Override
    public SoortNull getNullWaarde() {
        return soortNull;
    }
}
