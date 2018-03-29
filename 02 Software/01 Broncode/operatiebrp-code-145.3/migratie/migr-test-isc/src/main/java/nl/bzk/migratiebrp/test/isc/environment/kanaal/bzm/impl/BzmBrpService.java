/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl;

/**
 * Verantwoordelijk voor de communicatie met BRP middels SOAP.
 */
public interface BzmBrpService {

    /**
     * Verstuurd het meegegeven xml bericht naar de BRP Service. Bericht word ingepakt in SOAP Envelope en voorzien van
     * WS-A en WS-Security Headers. Aan de hand van het bericht zal ook de juiste soapAction (wsdl:operation) worden
     * bepaald en zal het bericht naar de juist BRP Service worden gestuurd. Bericht wordt gesigneerd door het
     * certificaat behorende bij de meegegeven bronPartijCode.
     * @param xmlBody String
     * @param oinTransporteur OIN van de transporteur
     * @param oinOndertekenaar OIN van de ondertekenaar
     * @return xmlBody String van het SOAP response. Indien excepties, zoals SOAP:fault, return null.
     */
    String verstuurBzmBericht(final String xmlBody, final String oinTransporteur, final String oinOndertekenaar);

}
