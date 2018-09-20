/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Bericht zoals verzonden door of ontvangen door de centrale voorzieningen van de BRP.
 *
 * Berichten worden door de BRP gearchiveerd. Dit betreft enerzijds ontvangen Berichten, anderzijds Berichten die
 * verzonden zijn. Hierbij geldt dat; Uitsluitend berichten aan en van 'de buitenwereld' gearchiveerd worden. Dus niet
 * de interne berichtenstroom tussen de diverse modules. De definitie van 'verzonden' is; door de BRP op de queue gezet.
 * De verzending aan de afnemer valt hier buiten scope.
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface BerichtBasis extends BrpObject {

    /**
     * Retourneert Soort van Bericht.
     *
     * @return Soort.
     */
    SoortBerichtAttribuut getSoort();

    /**
     * Retourneert Richting van Bericht.
     *
     * @return Richting.
     */
    RichtingAttribuut getRichting();

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
     * Retourneert Standaard van Bericht.
     *
     * @return Standaard.
     */
    BerichtStandaardGroep getStandaard();

    /**
     * Retourneert Zoekcriteria persoon van Bericht.
     *
     * @return Zoekcriteria persoon.
     */
    BerichtZoekcriteriaPersoonGroep getZoekcriteriaPersoon();

}
