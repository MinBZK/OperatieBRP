/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber.basis;

import java.util.Collection;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Berichtdata;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.logisch.ber.BerichtMelding;
import nl.bzk.brp.model.logisch.ber.BerichtParametersGroep;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroep;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroep;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;


/**
 * (Toekomstig) Bericht zoals verzonden door of ontvangen door de centrale voorzieningen van de BRP.
 *
 * Berichten worden door de BRP gearchiveerd. Dit betreft enerzijds ontvangen Berichten, anderzijds Berichten die
 * verzonden gaan worden.
 *
 * 1. Soort bericht (weer) verwijderd uit model als eigenschap van Bericht: reden is dat het op het moment van
 * archiveren nog niet bekend zal zijn. RvdP 8 november 2011.
 *
 *
 *
 */
public interface BerichtBasis extends ObjectType {

    /**
     * Retourneert Soort van Bericht.
     *
     * @return Soort.
     */
    SoortBericht getSoort();

    /**
     * Retourneert Administratieve Handeling van Bericht.
     *
     * @return Administratieve Handeling.
     */
    AdministratieveHandeling getAdministratieveHandeling();

    /**
     * Retourneert Data van Bericht.
     *
     * @return Data.
     */
    Berichtdata getData();

    /**
     * Retourneert Datum/tijd ontvangst van Bericht.
     *
     * @return Datum/tijd ontvangst.
     */
    DatumTijd getDatumTijdOntvangst();

    /**
     * Retourneert Datum/tijd verzenden van Bericht.
     *
     * @return Datum/tijd verzenden.
     */
    DatumTijd getDatumTijdVerzenden();

    /**
     * Retourneert Antwoord op van Bericht.
     *
     * @return Antwoord op.
     */
    Bericht getAntwoordOp();

    /**
     * Retourneert Richting van Bericht.
     *
     * @return Richting.
     */
    Richting getRichting();

    /**
     * Retourneert Stuurgegevens van Bericht.
     *
     * @return Stuurgegevens.
     */
    BerichtStuurgegevensGroep getStuurgegevens();

    /**
     * Retourneert Parameters van Bericht.
     *
     * @return Parameters.
     */
    BerichtParametersGroep getParameters();

    /**
     * Retourneert Resultaat van Bericht.
     *
     * @return Resultaat.
     */
    BerichtResultaatGroep getResultaat();

    /**
     * Retourneert Bericht \ Meldingen van Bericht.
     *
     * @return Bericht \ Meldingen van Bericht.
     */
    Collection<? extends BerichtMelding> getMeldingen();

}
