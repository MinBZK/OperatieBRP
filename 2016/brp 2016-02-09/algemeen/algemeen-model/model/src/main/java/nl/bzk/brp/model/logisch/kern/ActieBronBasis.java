/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RechtsgrondAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * De Verantwoording van een Actie door een bron, hetzij een Document hetzij een vooraf bekende Rechtsgrond, hetzij de
 * omschrijving van een (niet vooraf bekende) rechtsgrond.
 *
 * Een BRP Actie wordt verantwoord door nul, één of meer Documenten en nul, één of meer Rechtsgronden. Elke combinatie
 * van de Actie enerzijds en een bron (een Document of een Rechtsgrond) anderzijds, wordt vastgelegd.
 *
 * De naam is een tijdje 'verantwoording' geweest. Het is echter niet meer dan een koppeltabel tussen een actie
 * enerzijds, en een document of rechtsgrond anderzijds. Een generalisatie van document en rechtsgrond zou 'bron' zijn.
 * Passend in het BMR toegepaste patroon is dan om de koppeltabel - die actie enerzijds en bron anderzijds koppelt - dan
 * de naam Actie/Bron te noemen. Hiervoor is uiteindelijk gekozen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface ActieBronBasis extends BrpObject {

    /**
     * Retourneert Actie van Actie \ Bron.
     *
     * @return Actie.
     */
    Actie getActie();

    /**
     * Retourneert Document van Actie \ Bron.
     *
     * @return Document.
     */
    Document getDocument();

    /**
     * Retourneert Rechtsgrond van Actie \ Bron.
     *
     * @return Rechtsgrond.
     */
    RechtsgrondAttribuut getRechtsgrond();

    /**
     * Retourneert Rechtsgrondomschrijving van Actie \ Bron.
     *
     * @return Rechtsgrondomschrijving.
     */
    OmschrijvingEnumeratiewaardeAttribuut getRechtsgrondomschrijving();

}
