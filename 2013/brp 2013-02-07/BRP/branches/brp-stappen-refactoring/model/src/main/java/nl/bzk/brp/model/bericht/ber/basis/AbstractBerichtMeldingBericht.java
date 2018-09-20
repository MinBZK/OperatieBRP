/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber.basis;

import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.MeldingBericht;
import nl.bzk.brp.model.logisch.ber.BerichtMelding;
import nl.bzk.brp.model.logisch.ber.basis.BerichtMeldingBasis;


/**
 * Het voorkomen van een melding in een uitgaand bericht.
 *
 * Een bijhoudingsbericht (een inkomend bericht) kan leiden tot ��n of meer meldingen. Deze worden via een uitgaand
 * bericht gecommuniceerd naar de partij die het inkomende bericht had gestuurd.
 *
 * Een melding had zowel aan een inkomend als aan een uitgaand bericht gekoppeld kunnen zijn. De reden voor het opnemen
 * van het objecttype in het BMR is echter vooral om het uitgaand bericht te kunnen vormgeven. Om die reden is bij de
 * definitie aangesloten op het uitgaand bericht (waar de melding in wordt medegedeeld), en niet aan het inkomend
 * bericht (die heeft geleid tot de melding). Indien wordt besloten om de bericht/melding te persisteren, kan dit
 * besluit eventueel worden herzien: het is dan denkbaar dat (direct) aan het inkomend bericht de daaruit ontstane
 * meldingen worden gekoppeld. Vooralsnog is dit niet aan de orde, en is een 'bericht/melding' alleen te koppelen aan
 * een inkomend bericht door vanuit het inkomend bericht te beschouwen welk uitgaand bericht daarop volgde, en welke
 * meldingen daar in werden vermeld.
 * RvdP 4-12-2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractBerichtMeldingBericht extends AbstractObjectTypeBericht implements BerichtMeldingBasis {

    private BerichtBericht bericht;
    private MeldingBericht melding;

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtBericht getBericht() {
        return bericht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MeldingBericht getMelding() {
        return melding;
    }

    /**
     * Zet Bericht van Bericht \ Melding.
     *
     * @param bericht Bericht.
     */
    public void setBericht(final BerichtBericht bericht) {
        this.bericht = bericht;
    }

    /**
     * Zet Melding van Bericht \ Melding.
     *
     * @param melding Melding.
     */
    public void setMelding(final MeldingBericht melding) {
        this.melding = melding;
    }

}
