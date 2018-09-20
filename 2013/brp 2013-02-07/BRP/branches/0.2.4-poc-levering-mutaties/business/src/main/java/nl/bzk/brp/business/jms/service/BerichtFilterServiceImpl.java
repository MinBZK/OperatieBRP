/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;

import nl.bzk.brp.business.levering.LEVLeveringBijgehoudenPersoonLv;
import nl.bzk.brp.model.levering.Abonnement;
import nl.bzk.brp.model.levering.AbonnementGegevenselement;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.JDOMSource;
import org.jibx.extras.JDOMWriter;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class BerichtFilterServiceImpl implements BerichtFilterService {

    private static final Logger       LOGGER                   = LoggerFactory
                                                                       .getLogger(BerichtFilterServiceImpl.class);

    private static final List<String> MOCKUP_GEGEVENSELEMENTEN = Arrays.asList(new String[] { "huisnummer", "postcode",
        "naamopenbareruimte", "woonplaatscode", "soortcode", "tijdstipontlening", "afgekortenaamopenbareruimte" });

    @Override
    public LEVLeveringBijgehoudenPersoonLv filterBericht(final LEVLeveringBijgehoudenPersoonLv bericht,
            final Abonnement abonnement)
    {
        LEVLeveringBijgehoudenPersoonLv gefilterdBericht = filterVeldenVanBericht(bericht, abonnement);

        return gefilterdBericht;
    }

    @SuppressWarnings("rawtypes")
    private LEVLeveringBijgehoudenPersoonLv filterVeldenVanBericht(final LEVLeveringBijgehoudenPersoonLv bericht,
            final Abonnement abonnement)
    {
        if (abonnement.getAbonnementGegevenselementen().size() > 0) {
            Document document = verkrijgDocumentVanBericht(bericht);
            List<Element> filterElementen = new ArrayList<Element>();

            Iterator itr = document.getDescendants();
            while (itr.hasNext()) {
                Content content = (Content) itr.next();
                if (content instanceof Element) {
                    // LOGGER.debug("document element: " + element.getName());
                    if (veldNietToegestaan(((Element) content).getName(), abonnement)) {
                        filterElementen.add((Element) content);
                    }
                }
            }

            // Verwijder elementen die gefilterd moeten worden
            for (Element element : filterElementen) {
                element.getParentElement().removeChild(element.getName(), (element.getNamespace()));
            }

            LEVLeveringBijgehoudenPersoonLv gefilterdBericht = creeerBerichtVanDocument(document);
            return gefilterdBericht;
        } else {
            return bericht;
        }
    }

    private boolean veldNietToegestaan(final String veld, final Abonnement abonnement) {
        List<String> gegevenselementenAbonnement = new ArrayList<String>();
        for (AbonnementGegevenselement abonnementGegevenselement : abonnement.getAbonnementGegevenselementen()) {
            gegevenselementenAbonnement.add(abonnementGegevenselement.getGegevenselement());
        }

        if (gegevenselementenAbonnement.contains(veld.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Abonnement verkrijgRandomAbonnement() {
        Abonnement abonnement = new Abonnement();
        List<AbonnementGegevenselement> abonnementGegevenselementen = new ArrayList<AbonnementGegevenselement>();
        for (int i = 0; i < new Random().nextInt(MOCKUP_GEGEVENSELEMENTEN.size() - 1); i++) {
            AbonnementGegevenselement abonnementGegevenselement = verkrijgRandomAbonnementGegevenselement();
            abonnementGegevenselementen.add(abonnementGegevenselement);
        }
        abonnement.setAbonnementGegevenselementen(abonnementGegevenselementen);

        return abonnement;
    }

    private AbonnementGegevenselement verkrijgRandomAbonnementGegevenselement() {
        AbonnementGegevenselement abonnementGegevenselement = new AbonnementGegevenselement();
        String gegevenselement =
            MOCKUP_GEGEVENSELEMENTEN.get(new Random().nextInt(MOCKUP_GEGEVENSELEMENTEN.size() - 1));
        abonnementGegevenselement.setGegevenselement(gegevenselement);
        return abonnementGegevenselement;
    }

    @Override
    public String verkrijgXmlVanBericht(final LEVLeveringBijgehoudenPersoonLv bericht) {
        try {
            IBindingFactory bfact = creeerBindingFactoryVoorBericht();
            OutputStream out = new ByteArrayOutputStream();
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.marshalDocument(bericht, "UTF-8", null, out);

            return out.toString();
        } catch (JiBXException e) {
            LOGGER.error("Error: fout opgetreden bij marshallen naar XML.", e);
        }

        return "";
    }

    @Override
    public Document verkrijgDocumentVanBericht(final LEVLeveringBijgehoudenPersoonLv bericht) {
        Document document = null;

        try {
            IBindingFactory bfact = creeerBindingFactoryVoorBericht();
            IMarshallingContext mctx = bfact.createMarshallingContext();

            JDOMWriter jdomWriter = new JDOMWriter(bfact.getNamespaces());
            mctx.setXmlWriter(jdomWriter);
            mctx.marshalDocument(bericht);
            mctx.endDocument();

            document = jdomWriter.getDocument();

            return document;
        } catch (JiBXException e) {
            LOGGER.error("Error: fout opgetreden bij marshallen naar JDOM Document.", e);
        }

        return document;
    }

    @Override
    public LEVLeveringBijgehoudenPersoonLv creeerBerichtVanDocument(final Document document) {
        LEVLeveringBijgehoudenPersoonLv bericht = null;
        try {
            JDOMSource source = new JDOMSource(document);
            StringWriter xmlAsWriter = new StringWriter();
            StreamResult result = new StreamResult(xmlAsWriter);
            TransformerFactory.newInstance().newTransformer().transform(source, result);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlAsWriter.toString().getBytes("UTF-8"));

            IBindingFactory bfact = creeerBindingFactoryVoorBericht();
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            bericht = (LEVLeveringBijgehoudenPersoonLv) uctx.unmarshalDocument(inputStream, "UTF-8");

            return bericht;
        } catch (JiBXException e) {
            LOGGER.error("Error: fout opgetreden bij marshallen.", e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error: fout opgetreden bij marshallen.", e);
        } catch (TransformerConfigurationException e) {
            LOGGER.error("Error: fout opgetreden bij marshallen.", e);
        } catch (TransformerException e) {
            LOGGER.error("Error: fout opgetreden bij marshallen.", e);
        } catch (TransformerFactoryConfigurationError e) {
            LOGGER.error("Error: fout opgetreden bij marshallen.", e);
        }

        return bericht;
    }

    private IBindingFactory creeerBindingFactoryVoorBericht() {
        IBindingFactory bfact = null;
        try {
            bfact = BindingDirectory.getFactory("binding_levering_mutaties", LEVLeveringBijgehoudenPersoonLv.class);
        } catch (JiBXException e) {
            LOGGER.error("Error: fout opgetreden bij aanmaken bindingfactory.", e);
        }

        return bfact;
    }

}
