/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.prot;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * De (voorgenomen) Levering van persoonsgegevens aan een Afnemer.
 *
 * Een Afnemer krijgt gegevens doordat er sprake is van een Abonnement. Vlak voordat de gegevens daadwerkelijk
 * afgeleverd gaan worden, wordt dit geprotocolleerd door een regel weg te schrijven in de Levering tabel.
 *
 * Voorheen was er een link tussen de uitgaande en eventueel inkomende (vraag) berichten. Omdat de bericht tabel
 * geschoond wordt, is deze afhankelijkheid niet wenselijk. Het is daarom ook van belang om alle informatie die
 * noodzakelijk is te kunnen voldoen aan de protocollering hier vast te leggen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface LeveringsaantekeningBasis extends BrpObject {

    /**
     * Retourneert Toegang leveringsautorisatie van Leveringsaantekening.
     *
     * @return Toegang leveringsautorisatie.
     */
    Integer getToegangLeveringsautorisatieId();

    /**
     * Retourneert Dienst van Leveringsaantekening.
     *
     * @return Dienst.
     */
    Integer getDienstId();

    /**
     * Retourneert Datum/tijd klaarzetten levering van Leveringsaantekening.
     *
     * @return Datum/tijd klaarzetten levering.
     */
    DatumTijdAttribuut getDatumTijdKlaarzettenLevering();

    /**
     * Retourneert Datum materieel selectie van Leveringsaantekening.
     *
     * @return Datum materieel selectie.
     */
    DatumAttribuut getDatumMaterieelSelectie();

    /**
     * Retourneert Datum aanvang materiële periode resultaat van Leveringsaantekening.
     *
     * @return Datum aanvang materiële periode resultaat.
     */
    DatumAttribuut getDatumAanvangMaterielePeriodeResultaat();

    /**
     * Retourneert Datum einde materiële periode resultaat van Leveringsaantekening.
     *
     * @return Datum einde materiële periode resultaat.
     */
    DatumAttribuut getDatumEindeMaterielePeriodeResultaat();

    /**
     * Retourneert Datum/tijd aanvang formele periode resultaat van Leveringsaantekening.
     *
     * @return Datum/tijd aanvang formele periode resultaat.
     */
    DatumTijdAttribuut getDatumTijdAanvangFormelePeriodeResultaat();

    /**
     * Retourneert Datum/tijd einde formele periode resultaat van Leveringsaantekening.
     *
     * @return Datum/tijd einde formele periode resultaat.
     */
    DatumTijdAttribuut getDatumTijdEindeFormelePeriodeResultaat();

    /**
     * Retourneert Administratieve handeling van Leveringsaantekening.
     *
     * @return Administratieve handeling.
     */
    Long getAdministratieveHandelingId();

    /**
     * Retourneert Soort synchronisatie van Leveringsaantekening.
     *
     * @return Soort synchronisatie.
     */
    SoortSynchronisatieAttribuut getSoortSynchronisatie();

}
