/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Meldingtekst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.logisch.ber.Melding;
import nl.bzk.brp.model.logisch.ber.basis.MeldingBasis;


/**
 * Het optreden van een soort melding naar aanleiding van het controleren van een Regel.
 *
 * Vanuit het oogpunt van het bewaken van de kwaliteit van de gegevens in de BRP, en het kunnen garanderen van een
 * correcte werking van de BRP, worden inkomende berichten gecontroleerd door bepaalde wetmatigheden te controleren:
 * Regels. Als een Regel iets constateerd, zal dat leiden tot een specifieke soort melding, en zal bekend zijn welk
 * attribuut of welke attributen daarbij het probleem veroorzaken.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractMeldingBericht extends AbstractObjectTypeBericht implements MeldingBasis {

    private Regel        regel;
    private SoortMelding soort;
    private Meldingtekst melding;
    private Element      attribuut;

    /**
     * {@inheritDoc}
     */
    @Override
    public Regel getRegel() {
        return regel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortMelding getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Meldingtekst getMelding() {
        return melding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element getAttribuut() {
        return attribuut;
    }

    /**
     * Zet Regel van Melding.
     *
     * @param regel Regel.
     */
    public void setRegel(final Regel regel) {
        this.regel = regel;
    }

    /**
     * Zet Soort van Melding.
     *
     * @param soort Soort.
     */
    public void setSoort(final SoortMelding soort) {
        this.soort = soort;
    }

    /**
     * Zet Melding van Melding.
     *
     * @param melding Melding.
     */
    public void setMelding(final Meldingtekst melding) {
        this.melding = melding;
    }

    /**
     * Zet Attribuut van Melding.
     *
     * @param attribuut Attribuut.
     */
    public void setAttribuut(final Element attribuut) {
        this.attribuut = attribuut;
    }

}
