/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.ErkenningOngeborenVruchtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.logisch.kern.ErkenningOngeborenVrucht;
import nl.bzk.brp.model.logisch.kern.basis.ErkenningOngeborenVruchtBasis;


/**
 * De erkenning ongeboren vrucht zoals bedoeld in artikel 5, BW boek 1.
 *
 * Een erkenning ongeboren vrucht is een relatie tussen twee personen, de toekomstige ouders van het kind of de kinderen
 * waarvan ��n van de twee in verwachting is. Hierbij is er sprake van enerzijds een erkenner, en anderzijds een
 * instemmer. De laatst is de (toekomstig) ouder die thans de drager van het ongeboren vrucht is, zonder diens
 * toestemming is erkenning niet mogelijk.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractErkenningOngeborenVruchtBericht extends RelatieBericht implements
        ErkenningOngeborenVruchtBasis
{

    private ErkenningOngeborenVruchtStandaardGroepBericht standaard;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     */
    public AbstractErkenningOngeborenVruchtBericht() {
        super(SoortRelatie.ERKENNING_ONGEBOREN_VRUCHT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ErkenningOngeborenVruchtStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Standaard van Erkenning ongeboren vrucht.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final ErkenningOngeborenVruchtStandaardGroepBericht standaard) {
        this.standaard = standaard;
    }

}
