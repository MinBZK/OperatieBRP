/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroep;
import nl.bzk.brp.model.logisch.ber.basis.BerichtResultaatGroepBasis;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractBerichtResultaatGroepBericht extends AbstractGroepBericht implements
        BerichtResultaatGroepBasis
{

    private Verwerkingsresultaat verwerking;
    private Bijhoudingsresultaat bijhouding;
    private SoortMelding         hoogsteMeldingsniveau;

    /**
     * {@inheritDoc}
     */
    @Override
    public Verwerkingsresultaat getVerwerking() {
        return verwerking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bijhoudingsresultaat getBijhouding() {
        return bijhouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortMelding getHoogsteMeldingsniveau() {
        return hoogsteMeldingsniveau;
    }

    /**
     * Zet Verwerking van Resultaat.
     *
     * @param verwerking Verwerking.
     */
    public void setVerwerking(final Verwerkingsresultaat verwerking) {
        this.verwerking = verwerking;
    }

    /**
     * Zet Bijhouding van Resultaat.
     *
     * @param bijhouding Bijhouding.
     */
    public void setBijhouding(final Bijhoudingsresultaat bijhouding) {
        this.bijhouding = bijhouding;
    }

    /**
     * Zet Hoogste meldingsniveau van Resultaat.
     *
     * @param hoogsteMeldingsniveau Hoogste meldingsniveau.
     */
    public void setHoogsteMeldingsniveau(final SoortMelding hoogsteMeldingsniveau) {
        this.hoogsteMeldingsniveau = hoogsteMeldingsniveau;
    }

}
