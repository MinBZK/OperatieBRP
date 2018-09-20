/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import java.util.Collection;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Een door het bijhoudingsorgaan geïnitieerde activiteit in de BRP, waarmee persoonsgegevens worden bijgehouden.
 *
 * De binnen de BRP geadministreerde persoonsgegevens worden bijgehouden doordat wijzigingen worden doorgevoerd vanuit
 * de gemeentelijke of ministeriële verantwoordelijkheid. Het initiatief gegevens te wijzigen komt vanuit het
 * betreffende bijhoudingsorgaan; deze stuurt daartoe een bericht aan de BRP die de daadwerkelijke bijhouding doet
 * plaatsvinden. Voor de verwerking binnen de BRP wordt dit bericht uiteen gerafeld in één of meer Acties. Het geheel
 * aan acties wordt de administratieve handeling genoemd; dit is in de BRP de weerslag van wat in termen van de
 * burgerzakenmodule 'de zaak' zal zijn geweest. Qua niveau staat het op hetzelfde niveau als het bericht; het verschil
 * bestaat eruit dat het bericht het vehikel is waarmee de administratieve handeling wordt bewerkstelligd.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface AdministratieveHandelingBasis extends BrpObject {

    /**
     * Retourneert Soort van Administratieve handeling.
     *
     * @return Soort.
     */
    SoortAdministratieveHandelingAttribuut getSoort();

    /**
     * Retourneert Partij van Administratieve handeling.
     *
     * @return Partij.
     */
    PartijAttribuut getPartij();

    /**
     * Retourneert Toelichting ontlening van Administratieve handeling.
     *
     * @return Toelichting ontlening.
     */
    OntleningstoelichtingAttribuut getToelichtingOntlening();

    /**
     * Retourneert Tijdstip registratie van Administratieve handeling.
     *
     * @return Tijdstip registratie.
     */
    DatumTijdAttribuut getTijdstipRegistratie();

    /**
     * Retourneert Standaard van Administratieve handeling.
     *
     * @return Standaard.
     */
    AdministratieveHandelingStandaardGroep getStandaard();

    /**
     * Retourneert Levering van Administratieve handeling.
     *
     * @return Levering.
     */
    AdministratieveHandelingLeveringGroep getLevering();

    /**
     * Retourneert Administratieve handeling \ Gedeblokkeerde Meldingen van Administratieve handeling.
     *
     * @return Administratieve handeling \ Gedeblokkeerde Meldingen van Administratieve handeling.
     */
    Collection<? extends AdministratieveHandelingGedeblokkeerdeMelding> getGedeblokkeerdeMeldingen();

    /**
     * Retourneert Acties van Administratieve handeling.
     *
     * @return Acties van Administratieve handeling.
     */
    Collection<? extends Actie> getActies();

}
