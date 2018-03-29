/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl;

import javax.xml.soap.SOAPMessage;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl.dispatch.DispatchClient;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl.util.BzmSoapUtil;

/**
 * Implementatie van BzmBrpService.
 */
public final class BzmBrpServiceImpl implements BzmBrpService {

    private final DispatchClient dispatchClient;

    private BzmSoapUtil bzmSoapUtil;

    /**
     * Constructor.
     * @param dispatchClient dispatch client
     * @param bzmSoapUtil soaputil
     */
    public BzmBrpServiceImpl(final DispatchClient dispatchClient, final BzmSoapUtil bzmSoapUtil) {
        this.dispatchClient = dispatchClient;
        this.bzmSoapUtil = bzmSoapUtil;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String verstuurBzmBericht(final String xmlBody, final String oinTransporteur, final String oinOndertekenaar) {
        String result = null;

        // Maak SOAP bericht met xmlBody
        final SOAPMessage request = bzmSoapUtil.maakSOAPBericht(xmlBody);
        if (request == null) {
            throw new IllegalArgumentException("Parameter xmlBody geen geldige XML");
        }

        // Verstuur SOAP bericht
        final SOAPMessage response = dispatchClient.doInvokeService(request, oinTransporteur, oinOndertekenaar);
        if (response != null) {
            result = bzmSoapUtil.getSOAPResultaat(response);
        }
        return result;
    }
}
