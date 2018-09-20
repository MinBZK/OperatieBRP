/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;


/**
 * Een door het bijhoudingsorgaan ge�nitieerde activiteit in de BRP, waarmee persoonsgegevens worden bijgehouden.
 * <p/>
 * De binnen de BRP geadministreerde persoonsgegevens worden bijgehouden doordat wijzigingen worden doorgevoerd vanuit de gemeentelijke of ministeri�le
 * verantwoordelijkheid. Het initiatief gegevens te wijzigen komt vanuit het betreffende bijhoudingsorgaan; deze stuurt daartoe een bericht aan de BRP die
 * de daadwerkelijke bijhouding doet plaatsvinden. Voor de verwerking binnen de BRP wordt dit bericht uiteen gerafeld in ��n of meer Acties. Het geheel aan
 * acties wordt de administratieve handeling genoemd; dit is in de BRP de weerslag van wat in termen van de burgerzakenmodule 'de zaak' zal zijn geweest.
 * Qua niveau staat het op hetzelfde niveau als het bericht; het verschil bestaat eruit dat het bericht het vehikel is waarmee de administratieve handeling
 * wordt bewerkstelligt.
 */
public interface AdministratieveHandeling extends AdministratieveHandelingBasis {

}
