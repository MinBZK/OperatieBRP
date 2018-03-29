/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.REFERENTIE_ID_ATTRIBUUT;

import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlTransient;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Bevat de attributen die op een BmrGroepReferentie zitten.
 *
 * @param <T> het type van de groep waar deze referentie naar verwijst.
 */
public abstract class AbstractBmrGroepReferentie<T extends BmrGroep> extends AbstractBmrObjecttype implements BmrGroepReferentie<T> {

    @XmlTransient
    private BmrGroep groep;

    /**
     * Maakt een AbstractBmrGroepReferentie object.
     *
     * @param attributen de lijst met attributen waarin objecttype moet voorkomen
     */
    protected AbstractBmrGroepReferentie(final Map<String, String> attributen) {
        super(attributen);
    }

    @Override
    public final String getReferentieId() {
        return getAttributen().get(REFERENTIE_ID_ATTRIBUUT.toString());
    }

    /**
     * Een persoon uit een bijhoudingsbericht moet behandeld worden als ingeschrevenen wanneer de objectsleutel is
     * gevuld.
     * @return true als de objectsleutel gevuld is, anders false
     */
    boolean heeftReferentie() {
        return getReferentie() != null;
    }

    @Override
    public final void initialiseer(final Map<String, BmrGroep> communicatieIdGroepMap) {
        ValidatieHelper.controleerOpNullWaarde(communicatieIdGroepMap, "communicatieIdGroepMap");
        this.groep = communicatieIdGroepMap.get(getReferentieId());
    }

    /**
     * De groep waarnaar deze referentie verwijst.
     *
     * @return de groep
     */
    protected final BmrGroep getGroep() {
        return groep;
    }
}
