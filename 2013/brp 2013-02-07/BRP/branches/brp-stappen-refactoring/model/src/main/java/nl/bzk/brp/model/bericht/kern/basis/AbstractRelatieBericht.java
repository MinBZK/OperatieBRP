/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.logisch.kern.Relatie;
import nl.bzk.brp.model.logisch.kern.basis.RelatieBasis;


/**
 * De Relatie tussen personen.
 *
 * Een Relatie tussen twee of meer Personen is als aparte object opgenomen. Het relatie-object beschrijft om wat voor
 * soort relatie het gaat, en waar en wanneer deze begonnen en/of be�indigd is. Het koppelen van een Persoon aan een
 * Relatie gebeurt via een object van het type Betrokkenheid.
 *
 * 1. Naast de nu onderkende relatievormen (Huwelijk, geregistreerd partnerschap en familierechtelijkebetrekking) is er
 * lange tijd sprake geweest van nog een aantal binaire relatievormen: erkenning ongeboren vrucht, ontkenning ouderschap
 * en naamskeuze ongeboren vrucht. Deze relatievormen zijn in een laat stadium alsnog geschrapt uit de gegevensset.
 * De gekozen constructie van o.a. Relatie is echter nog steeds gebaseerd op mogelijke toevoegingen.
 * De keuze om NIET terug te komen op de constructie is gebaseerd op enerzijds het late stadium waarin het schrappen van
 * de verschillende relatievormen is doorgevoerd, en anderzijds de mogelijkheid om in de toekomst eventuele nieuwe
 * (binaire) relatievormen eenvoudig te kunnen toevoegen.
 * RvdP, 5 augustus 2011
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractRelatieBericht extends AbstractObjectTypeBericht implements RelatieBasis {

    private SoortRelatie               soort;
    private List<BetrokkenheidBericht> betrokkenheden;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public AbstractRelatieBericht(final SoortRelatie soort) {
        this.soort = soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortRelatie getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BetrokkenheidBericht> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * Zet Betrokkenheden van Relatie.
     *
     * @param betrokkenheden Betrokkenheden.
     */
    public void setBetrokkenheden(final List<BetrokkenheidBericht> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

}
