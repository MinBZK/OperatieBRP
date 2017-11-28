/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Een bron referentie uit het bijhoudingsbericht.
 */
@XmlElement("bron")
public final class BronReferentieElement extends AbstractBmrGroepReferentie<BronElement> {

    /**
     * Maakt een BronElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     */
    public BronReferentieElement(final Map<String, String> basisAttribuutGroep) {
        super(basisAttribuutGroep);
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    @Override
    public BronElement getReferentie() {
        return (BronElement) getGroep();
    }

    @Override
    public boolean verwijstNaarBestaandEnJuisteType() {
        return getGroep() instanceof BronElement;
    }
}
