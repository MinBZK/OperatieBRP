/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.NaamskeuzeOngeborenVruchtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.logisch.kern.basis.NaamskeuzeOngeborenVruchtBasis;


/**
 * De relatie tussen twee ouders-in-spe waarmee zij aangeven welke naamskeuze zij wensen voor het kind/de kinderen
 * waarvan ��n van beide zwanger is.
 *
 * Reeds voor de geboorte kunnen de toekomstig ouders afspraken maken welke geslachtsnaam het kind/de kinderen gaan
 * krijgen. De ouder wiens geslachtnaam zal overgaan op het kind, heeft hier de rol Naamgever; de andere ouder - die
 * hiermee instemt - heeft in deze relatie dan de betrokkenheid in de rol van instemmer.
 *
 *
 *
 */
public abstract class AbstractNaamskeuzeOngeborenVruchtBericht extends RelatieBericht implements
        NaamskeuzeOngeborenVruchtBasis
{

    private NaamskeuzeOngeborenVruchtStandaardGroepBericht standaard;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     */
    public AbstractNaamskeuzeOngeborenVruchtBericht() {
        super(SoortRelatie.NAAMSKEUZE_ONGEBOREN_VRUCHT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamskeuzeOngeborenVruchtStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Standaard van Naamskeuze ongeboren vrucht.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final NaamskeuzeOngeborenVruchtStandaardGroepBericht standaard) {
        this.standaard = standaard;
    }

}
