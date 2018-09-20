/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bevraging;

import java.util.Map;

import javax.xml.bind.JAXBElement;

import nl.bzk.brp.bijhouding.service.BhgBevraging;
import nl.bzk.brp.brp0200.Burgerservicenummer;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** De Class AbstractBevragingAanroeper. */
public abstract class AbstractBevragingAanroeper extends AbstractAanroeper<BhgBevraging> {


    private static final String ENTITEITTYPE_VRAAG = "Vraag";

    private static final Logger LOG = LoggerFactory.getLogger(AbstractBevragingAanroeper.class);
    private static JaxWsProxyFactoryBean proxyFactory = null;
    private static BhgBevraging portType = null;

    private final ObjectFactory objectFactory = getObjectFactory();

    /**
     * Instantieert een nieuwe bevraging aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param parameterMap de parameter map
     * @param bevragingPortType de port type
     * @throws Exception de exception
     */
    protected AbstractBevragingAanroeper(final Eigenschappen eigenschappen,
                                         final BhgBevraging bevragingPortType,
                                         final Map<String, String> parameterMap) throws Exception
    {
        super(eigenschappen, bevragingPortType, parameterMap);
    }

//    /**
//     * Instantieert een {@link ObjecttypeVraag} en vult deze met de juiste entiteittype en de opgegeven communicatie id.
//     * @param communicatieId de communicatie id
//     * @return vraag jaxb element.
//     */
//    protected ObjecttypeVraag bouwVraag(final String communicatieId) {
//        final ObjecttypeVraag vraag = new ObjecttypeVraag();
//        vraag.setEntiteittype(ENTITEITTYPE_VRAAG);
//        vraag.setCommunicatieID(communicatieId);
//        return vraag;
//    }

    /**
     * Bouwt een bsn element.
     *
     * @param bsn bsn
     * @return JAXB element
     */
    protected JAXBElement<Burgerservicenummer> bouwBsnElement(final String bsn) {
        if (StringUtils.isEmpty(bsn)) {
            return null;
        } else {
            final Burgerservicenummer burgerservicenummer = new Burgerservicenummer();
            burgerservicenummer.setValue(StringUtils.rightPad(bsn, BSN_LENGTE, '0'));
            final JAXBElement<Burgerservicenummer> bsnElement = objectFactory
                    .createGroepBerichtZoekcriteriaPersoonBurgerservicenummer(burgerservicenummer);
            return bsnElement;
        }
    }
}
