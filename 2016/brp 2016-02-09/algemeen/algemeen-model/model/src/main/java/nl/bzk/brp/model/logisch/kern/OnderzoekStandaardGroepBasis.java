/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnderzoekOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoekAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Vorm van historie: formeel. Motivatie: 'onderzoek' is een construct om vast te leggen dat een bepaald gegeven
 * onderwerp is van onderzoek. Hierbij is het in principe alleen relevant of een gegeven NU in onderzoek is. Verder is
 * het voldoende om te weten of tijdens een bepaalde levering een gegeven wel of niet als 'in onderzoek' stond
 * geregistreerd. NB: de gegevens over het onderzoek zelf staan niet in de BRP, maar in bijvoorbeeld de zaaksystemen.
 * Omdat formele historie dus volstaat, wordt de materiële historie onderdrukt.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface OnderzoekStandaardGroepBasis extends Groep {

    /**
     * Retourneert Datum aanvang van Standaard.
     *
     * @return Datum aanvang.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumAanvang();

    /**
     * Retourneert Verwachte afhandeldatum van Standaard.
     *
     * @return Verwachte afhandeldatum.
     */
    DatumEvtDeelsOnbekendAttribuut getVerwachteAfhandeldatum();

    /**
     * Retourneert Datum einde van Standaard.
     *
     * @return Datum einde.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumEinde();

    /**
     * Retourneert Omschrijving van Standaard.
     *
     * @return Omschrijving.
     */
    OnderzoekOmschrijvingAttribuut getOmschrijving();

    /**
     * Retourneert Status van Standaard.
     *
     * @return Status.
     */
    StatusOnderzoekAttribuut getStatus();

}
