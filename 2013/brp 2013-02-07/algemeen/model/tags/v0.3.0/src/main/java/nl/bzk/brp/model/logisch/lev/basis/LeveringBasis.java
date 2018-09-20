/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.lev.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Authenticatiemiddel;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.Abonnement;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.SoortLevering;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.ber.Bericht;


/**
 * De (voorgenomen) Levering van persoonsgegevens aan een Afnemer.
 *
 * Een Afnemer krijgt gegevens doordat er sprake is van een Abonnement. Vlak voordat de gegevens daadwerkelijk
 * afgeleverd gaan worden, wordt dit geprotocolleerd door een regel weg te schrijven in de Levering tabel.
 *
 *
 *
 */
public interface LeveringBasis extends ObjectType {

    /**
     * Retourneert Soort van Levering.
     *
     * @return Soort.
     */
    SoortLevering getSoort();

    /**
     * Retourneert Authenticatiemiddel van Levering.
     *
     * @return Authenticatiemiddel.
     */
    Authenticatiemiddel getAuthenticatiemiddel();

    /**
     * Retourneert Abonnement van Levering.
     *
     * @return Abonnement.
     */
    Abonnement getAbonnement();

    /**
     * Retourneert Datum/tijd beschouwing van Levering.
     *
     * @return Datum/tijd beschouwing.
     */
    DatumTijd getDatumTijdBeschouwing();

    /**
     * Retourneert Datum/tijd klaarzetten levering van Levering.
     *
     * @return Datum/tijd klaarzetten levering.
     */
    DatumTijd getDatumTijdKlaarzettenLevering();

    /**
     * Retourneert Gebaseerd op van Levering.
     *
     * @return Gebaseerd op.
     */
    Bericht getGebaseerdOp();

}
