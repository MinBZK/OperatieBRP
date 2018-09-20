/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.PersoonVoornaamBasis;


/**
 * Voornaam van een Persoon
 *
 * Voornamen worden in de BRP los van elkaar geregistreerd. Het LO BRP is voorbereid op het kunnen vastleggen van
 * voornamen zoals 'Jan Peter', 'Aberto di Maria' of 'Wonder op aarde' als ��n enkele voornaam. In de BRP is het
 * namelijk niet noodzakelijk (conform LO 3.x) om de verschillende woorden aan elkaar te plakken met een koppelteken.
 *
 * Het gebruik van de spatie als koppelteken is echter (nog) niet toegestaan.
 *
 * Indien er sprake is van een namenreeks wordt dit opgenomen als geslachtsnaam; er is dan geen sprake van een Voornaam.
 *
 * Een voornaam mag voorlopig nog geen spatie bevatten.
 * Hiertoe dient eerst de akten van burgerlijke stand aangepast te worden (zodat voornamen individueel kunnen worden
 * vastgelegd, en er geen interpretatie meer nodig is van de ambtenaar over waar de ene voornaam eindigt en een tweede
 * begint).
 * Daarnaast is er ook nog geen duidelijkheid over de wijze waarop bestaande namen aangepast kunnen worden: kan de
 * burger hier simpelweg om verzoeken en wordt het dan aangepast?
 *
 * De BRP is wel al voorbereid op het kunnen bevatten van spaties.
 * RvdP 5 augustus 2011
 *
 *
 *
 */
public abstract class AbstractPersoonVoornaamBericht extends AbstractObjectTypeBericht implements PersoonVoornaamBasis {

    private PersoonBericht                       persoon;
    private Volgnummer                           volgnummer;
    private PersoonVoornaamStandaardGroepBericht standaard;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Volgnummer getVolgnummer() {
        return volgnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonVoornaamStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Persoon van Persoon \ Voornaam.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

    /**
     * Zet Volgnummer van Persoon \ Voornaam.
     *
     * @param volgnummer Volgnummer.
     */
    public void setVolgnummer(final Volgnummer volgnummer) {
        this.volgnummer = volgnummer;
    }

    /**
     * Zet Standaard van Persoon \ Voornaam.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonVoornaamStandaardGroepBericht standaard) {
        this.standaard = standaard;
    }

}
