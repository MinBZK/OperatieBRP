/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.List;

import javax.validation.Valid;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.basis.AbstractRelatieBericht;
import nl.bzk.brp.model.logisch.kern.Relatie;


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
 */
public abstract class RelatieBericht extends AbstractRelatieBericht implements Relatie {

    /** De unieke (database) id van de relatie.
     * Let op: dit veld is alleen voor 'intern' gebruik!
     * Het zal meestal dan ook niet gevuld zijn, alleen in een scenario waarbij
     * dit binnen de logica 'door-gecommuniceerd' dient te worden. */
    private Integer modelID;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public RelatieBericht(final SoortRelatie soort) {
        super(soort);
    }

    public Integer getModelID() {
        return modelID;
    }

    public void setModelID(final Integer modelID) {
        this.modelID = modelID;
    }

    @Valid
    @Override
    public List<BetrokkenheidBericht> getBetrokkenheden() {
        return super.getBetrokkenheden();
    }
}
