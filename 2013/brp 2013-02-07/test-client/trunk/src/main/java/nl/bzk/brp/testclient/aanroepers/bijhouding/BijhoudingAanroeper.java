/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import java.util.Map;

import nl.bprbzk.brp._0001.service.BijhoudingPortType;
import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;


/**
 * De Class BijhoudingAanroeper.
 */
public abstract class BijhoudingAanroeper extends AbstractAanroeper<BijhoudingPortType> {

    public static final String  PARAMETER_PREVALIDATIE = "PREVALIDATIE";

    /** De Constante URL. */
    private static final String URL                    = "%s://%s:%s/%s/services/bijhouding";

    /**
     * Instantieert een nieuwe bijhouding aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    protected BijhoudingAanroeper(final Eigenschappen eigenschappen, final Map<String, String> parameterMap)
            throws Exception
    {
        super(eigenschappen, creeerPortType(eigenschappen), parameterMap);
    }

    /**
     * Creeer port type.
     *
     * @param eigenschappen de eigenschappen
     * @return de bijhouding port type
     * @throws Exception de exception
     */
    public final static BijhoudingPortType creeerPortType(final Eigenschappen eigenschappen) throws Exception {
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(BijhoudingPortType.class);
        String url =
            String.format(URL, eigenschappen.getProtocolBijhouding(), eigenschappen.getHostBijhouding(),
                    eigenschappen.getPortBijhouding(), eigenschappen.getContextRootBijhouding());
        proxyFactory.setAddress(url);
        BijhoudingPortType portType = (BijhoudingPortType) proxyFactory.create();
        return portType;
    }
}
