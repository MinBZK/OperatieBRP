/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Meldingtekst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.logisch.ber.GedeblokkeerdeMelding;
import nl.bzk.brp.model.logisch.ber.basis.GedeblokkeerdeMeldingBasis;


/**
 * Een melding die gedeblokkeerd is.
 *
 * Bij het controleren van een bijhoudingsbericht kunnen er ��n of meer meldingen zijn die gedeblokkeerd dienen te
 * worden opdat de bijhouding ook daadwerkelijk verricht kan worden. De gedeblokkeerde meldingen worden geadministreerd.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractGedeblokkeerdeMeldingBericht extends AbstractObjectTypeBericht implements
        GedeblokkeerdeMeldingBasis
{

    private Regel        regel;
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
     * Zet Regel van Gedeblokkeerde melding.
     *
     * @param regel Regel.
     */
    public void setRegel(final Regel regel) {
        this.regel = regel;
    }

    /**
     * Zet Melding van Gedeblokkeerde melding.
     *
     * @param melding Melding.
     */
    public void setMelding(final Meldingtekst melding) {
        this.melding = melding;
    }

    /**
     * Zet Attribuut van Gedeblokkeerde melding.
     *
     * @param attribuut Attribuut.
     */
    public void setAttribuut(final Element attribuut) {
        this.attribuut = attribuut;
    }

}
