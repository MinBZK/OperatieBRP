/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitoring;

import javax.inject.Inject;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * Interceptor om soapFaults te tellen.
 *
 */
public class FaultOutInterceptor extends AbstractSoapInterceptor {

    @Inject
    private BerichtenMBean berichtenLijst;

    /**
     * Constructor.
     */
    public FaultOutInterceptor() {
        super(Phase.MARSHAL);
    }

    @Override
    public void handleMessage(final SoapMessage message) {
        berichtenLijst.telOpSoapFault();
    }
}
