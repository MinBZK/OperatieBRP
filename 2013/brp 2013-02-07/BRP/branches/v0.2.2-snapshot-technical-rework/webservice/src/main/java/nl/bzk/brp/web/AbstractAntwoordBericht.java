/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.model.objecttype.bericht.BerichtBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.OverruleMelding;


/**
 * Deze klasse stelt een antwoord bericht voor met alle elementen die terug gecommuniceerd worden via de webservice.
 * Het
 * bevat eventuele meldingen (fouten, waarschuwingen etc.) die zijn opgetreden tijdens de verwerking van het bericht.
 *
 */
public abstract class AbstractAntwoordBericht extends BerichtBericht {

    private List<Melding> meldingen;
    private List<OverruleMelding> overruledMeldingen;

    /**geth
     * Retourneert een (onaanpasbare) lijst van de opgetreden meldingen.
     *
     * @return een (onaanpasbare) lijst van de opgetreden meldingen.
     */
    public List<Melding> getMeldingen() {
        List<Melding> resultaat;
        if (meldingen == null) {
            resultaat = null;
        } else {
            resultaat = Collections.unmodifiableList(meldingen);
        }
        return resultaat;
    }

    public void setMeldingen(final List<Melding> meldingen) {
        this.meldingen = meldingen;
    }

    public List<OverruleMelding> getOverruledMeldingen() {
        return overruledMeldingen;
    }

    public void setOverruledMeldingen(final List<OverruleMelding> overruledMeldingen) {
        this.overruledMeldingen = overruledMeldingen;
    }

    @Override
    public Collection<String> getReadBsnLocks() {
        return null;
    }

    @Override
    public Collection<String> getWriteBsnLocks() {
        return null;
    }
}
