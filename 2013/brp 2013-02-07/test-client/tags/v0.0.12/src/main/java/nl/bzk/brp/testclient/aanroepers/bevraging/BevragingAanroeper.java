/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bevraging;

import java.util.Map;

import nl.bprbzk.brp._0001.service.BevragingPortType;
import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * De Class BevragingAanroeper.
 */
public abstract class BevragingAanroeper extends AbstractAanroeper<BevragingPortType> {

    /** De Constante URL. */
    private static final String URL = "%s://%s:%s/%s/services/bevraging";

    /**
     * Instantieert een nieuwe bevraging aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    protected BevragingAanroeper(final Eigenschappen eigenschappen, final Map<String, String> parameterMap) throws Exception
    {
        super(eigenschappen, creeerPortType(eigenschappen), parameterMap);
    }

    /**
     * Creeer port type.
     *
     * @param eigenschappen de eigenschappen
     * @return de bevraging port type
     * @throws Exception de exception
     */
    public final static BevragingPortType creeerPortType(final Eigenschappen eigenschappen) throws Exception {
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(BevragingPortType.class);
        String url =
            String.format(URL, eigenschappen.getProtocolBevraging(), eigenschappen.getHostBevraging(), eigenschappen.getPortBevraging(),
                    eigenschappen.getContextRootBevraging());
        proxyFactory.setAddress(url);
        BevragingPortType portType = (BevragingPortType) proxyFactory.create();
        return portType;
    }
}
