/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nl.bzk.brp.bmr.generator.AbstractGenerator;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.AttribuutType;
import nl.bzk.brp.ecore.bmr.BasisType;
import nl.bzk.brp.ecore.bmr.GelaagdElement;
import nl.bzk.brp.ecore.bmr.Groep;
import nl.bzk.brp.ecore.bmr.Historie;
import nl.bzk.brp.ecore.bmr.InSetOfModel;
import nl.bzk.brp.ecore.bmr.MetaRegister;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.Schema;
import nl.bzk.brp.ecore.bmr.SoortTekst;
import nl.bzk.brp.ecore.bmr.Tekst;
import nl.bzk.brp.ecore.bmr.Type;

import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


@Service
public class GegevensWoordenboek extends AbstractGenerator {

    private static final int    LOGISCHE_LAAG       = 1749;
    private static final String LABEL_KOLOM_BREEDTE = "90";
    private static final String XML                 = "http://www.w3.org/XML/1998/namespace";
    private static final String XMLNS               = "http://www.w3.org/2000/xmlns/";
    private static final String XSI                 = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String DOCBK               = "http://docbook.org/ns/docbook";
    private static final String XINCLUDE            = "http://www.w3.org/2001/XInclude";
    private static final String XLINK               = "http://www.w3.org/1999/xlink";

    private static final Logger LOGGER              = LoggerFactory.getLogger(GegevensWoordenboek.class);
    private Document            doc;

    @Override
    public void generate(final MetaRegister register, final String naam, final FileSystemAccess file) {
        try {
            serializeDocument(file, genereer(register, naam));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param element
     * @return TODO
     * @throws ParserConfigurationException
     */
    private Document genereer(final MetaRegister register, final String naam) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        doc = documentBuilder.newDocument();
        Element root = doc.createElementNS(DOCBK, "book");
        doc.setXmlVersion("1.0");
        /*
         * Namespaces.
         */
        root.setAttributeNS(XMLNS, "xmlns", DOCBK);
        root.setAttributeNS(XMLNS, "xmlns:xsi", XSI);
        root.setAttributeNS(XMLNS, "xmlns:xlink", XLINK);
        root.setAttributeNS(XMLNS, "xmlns:xi", XINCLUDE);
        root.setAttributeNS(XSI, "xsi:schemaLocation", DOCBK + " http://docbook.org/xml/5.0/xsd/docbook.xsd");

        root.setAttributeNS(XML, "xml:lang", "NL");
        root.setAttribute("version", "5.0");
        doc.appendChild(root);

        Node info = root.appendChild(doc.createElementNS(DOCBK, "info"));
        info.appendChild(doc.createElementNS(DOCBK, "title"))
                .appendChild(doc.createTextNode("BRP Gegevenswoordenboek"));

        for (Schema schema : register.getDomein(naam).getSchemas()) {
            root.appendChild(genereer(schema));
        }
        root.appendChild(genereerBasistypen(register));
        return doc;
    }

    private Node genereerBasistypen(final MetaRegister register) {
        Element chapter = doc.createElementNS(DOCBK, "chapter");
        chapter.appendChild(doc.createElementNS(DOCBK, "title")).appendChild(doc.createTextNode("Basistypen"));
        for (BasisType basisType : register.getBasisTypen()) {
            chapter.appendChild(genereer(basisType));
        }
        return chapter;
    }

    private Node genereer(final BasisType element) {
        Element section = doc.createElementNS(DOCBK, "section");
        section.appendChild(doc.createElementNS(DOCBK, "title")).appendChild(doc.createTextNode(element.getNaam()));
        Element tabel = (Element) section.appendChild(doc.createElementNS(DOCBK, "informaltable"));
        tabel.setAttribute("frame", "none");
        Element tgroup = (Element) tabel.appendChild(doc.createElementNS(DOCBK, "tgroup"));
        tgroup.setAttribute("cols", "2");
        tgroup.setAttribute("colsep", "0");
        tgroup.setAttribute("rowsep", "0");
        Element colspec = (Element) tgroup.appendChild(doc.createElementNS(DOCBK, "colspec"));
        colspec.setAttribute("align", "right");
        colspec.setAttribute("colwidth", LABEL_KOLOM_BREEDTE);
        colspec = (Element) tgroup.appendChild(doc.createElementNS(DOCBK, "colspec"));
        colspec.setAttribute("align", "left");
        Node tbody = tgroup.appendChild(doc.createElementNS(DOCBK, "tbody"));

        tbody.appendChild(row(entry("ID:", "bold"), entry(element.getSyncId().toString(), null, element.xmlId())));
        if (element.getBeschrijving() != null) {
            tbody.appendChild(row(entry("Beschrijving:", "bold"), entry(element.getBeschrijving().toString())));
        }

        if (!element.getTeksten().isEmpty()) {
            section.appendChild(tekstenTabel(element));
        }
        return section;
    }

    /**
     * @param schema
     * @return
     */
    private Element genereer(final Schema schema) {
        Element schemaChapter = doc.createElementNS(DOCBK, "chapter");
        schemaChapter.appendChild(doc.createElementNS(DOCBK, "title"))
                .appendChild(doc.createTextNode(schema.getNaam()));
        schemaChapter.appendChild(doc.createElementNS(DOCBK, "subtitle")).appendChild(doc.createTextNode("Schema"));
        schemaChapter.setAttributeNS(XML, "xml:id", schema.xmlId());

        /*
         * Objecttypen
         */
        Node ot = schemaChapter.appendChild(section("Objecttypen"));
        for (ObjectType type : schema.getWerkVersie().getObjectTypes(LOGISCHE_LAAG)) {
            ot.appendChild(genereer(type));
        }

        /*
         * Attribuuttypen
         */
        Node at = schemaChapter.appendChild(section("Attribuuttypen"));
        for (AttribuutType type : schema.getWerkVersie().getAttribuutTypes(LOGISCHE_LAAG)) {
            at.appendChild(genereer(type));
        }
        return schemaChapter;
    }

    private Node genereer(final AttribuutType type) {
        Element typeSection = section(type);
        typeSection.appendChild(header(type));
        if (!type.getTeksten().isEmpty()) {
            typeSection.appendChild(tekstenTabel(type));
        }
        return typeSection;
    }

    /**
     * @param type
     * @return
     */
    private Element genereer(final ObjectType type) {
        Element ot = section(type);
        ot.appendChild(header(type));
        if (!type.getTeksten().isEmpty()) {
            ot.appendChild(tekstenTabel(type));
        }

        EList<Groep> groepen = type.getGroepen();
        if (!groepen.isEmpty()) {
            for (Groep groep : groepen) {
                ot.appendChild(genereer(groep));
            }
        }
        return ot;
    }

    private Node genereer(final Groep groep) {
        Element groepSection = section(groep);
        groepSection.appendChild(header(groep));

        if (!groep.getTeksten().isEmpty()) {
            groepSection.appendChild(tekstenTabel(groep));
        }
        for (Attribuut attribuut : groep.getAttributen()) {
            groepSection.appendChild(genereer(attribuut));
        }

        return groepSection;
    }

    private Node genereer(final Attribuut attribuut) {
        Node attribuutSection = section(attribuut);
        attribuutSection.appendChild(header(attribuut));
        if (!attribuut.getTeksten().isEmpty()) {
            attribuutSection.appendChild(tekstenTabel(attribuut));
        }
        return attribuutSection;
    }

    /**
     * @param element TODO
     * @return
     */
    private Node header(final GelaagdElement element) {
        Element tabel = doc.createElementNS(DOCBK, "informaltable");
        tabel.setAttribute("frame", "none");
        Element tgroup = (Element) tabel.appendChild(doc.createElementNS(DOCBK, "tgroup"));
        tgroup.setAttribute("cols", "2");
        tgroup.setAttribute("colsep", "0");
        tgroup.setAttribute("rowsep", "0");
        Element colspec = (Element) tgroup.appendChild(doc.createElementNS(DOCBK, "colspec"));
        colspec.setAttribute("align", "right");
        colspec.setAttribute("colwidth", LABEL_KOLOM_BREEDTE);
        colspec = (Element) tgroup.appendChild(doc.createElementNS(DOCBK, "colspec"));
        colspec.setAttribute("align", "left");
        Node tbody = tgroup.appendChild(doc.createElementNS(DOCBK, "tbody"));
        tbody.appendChild(row(entry("ID:", "bold"), entry(element.getSyncId().toString())));
        tbody.appendChild(row(entry("Code identifier:", "bold"), entry(element.getIdentifierCode())));
        tbody.appendChild(row(entry("Database identifier:", "bold"), entry(element.getIdentifierDB())));

        if (element instanceof ObjectType) {
            ObjectType type = (ObjectType) element;
            tbody.appendChild(row(entry("Typering:", "bold"), entry(getSoortInhoudOmschrijving((ObjectType) element))));
            if ((type.getHistorieVastleggen() != null) && (type.getHistorieVastleggen() != Historie.G)) {
                tbody.appendChild(row(entry("Historie:", "bold"),
                        entry(getHistorieOmschrijving(type.getHistorieVastleggen()))));
            }
        }
        if (element.getInSetOfModel() != null) {
            tbody.appendChild(row(entry("Zichtbaarheid:", "bold"),
                    entry(getInSomOmschrijving(element.getInSetOfModel()))));
        }
        if (element instanceof Groep) {
            Groep groep = (Groep) element;
            tbody.appendChild(row(entry("Historie:", "bold"),
                    entry(getHistorieOmschrijving(groep.getHistorieVastleggen()))));
            tbody.appendChild(row(entry("Voorkomen:", "bold"), entry(groep.isVerplicht() ? "Verplicht" : "Optioneel")));
        }
        if (element instanceof Attribuut) {
            Attribuut attribuut = (Attribuut) element;
            Type type = attribuut.getType();
            tbody.appendChild(row(entry("Type:", "bold"), entry(type.getGekwalificeerdeNaam(), null, type.xmlId())));
            tbody.appendChild(row(entry("Voorkomen:", "bold"), entry(attribuut.isVerplicht() ? "Verplicht"
                : "Optioneel")));
        }
        if (element instanceof AttribuutType) {
            AttribuutType type = (AttribuutType) element;
            BasisType basisType = type.getType();
            tbody.appendChild(row(entry("Datatype:", "bold"), entry(basisType.getNaam(), null, basisType.xmlId())));
            if ((type.getMinimumLengte() != null) && (type.getMinimumLengte() > 0)) {
                tbody.appendChild(row(entry("Minimale lengte:", "bold"),
                        entry(type.getMinimumLengte().toString(), null, basisType.xmlId())));
            }
            if ((type.getMaximumLengte() != null) && (type.getMaximumLengte() > 0)) {
                tbody.appendChild(row(entry("Maximale lengte:", "bold"),
                        entry(type.getMaximumLengte().toString(), null, basisType.xmlId())));
            }
            if ((type.getAantalDecimalen() != null) && (type.getAantalDecimalen() > 0)) {
                tbody.appendChild(row(entry("Aantal decimalen:", "bold"),
                        entry(type.getMaximumLengte().toString(), null, basisType.xmlId())));
            }
        }
        return tabel;
    }

    private Node tekstenTabel(final nl.bzk.brp.ecore.bmr.Element element) {
        Element tabel = doc.createElementNS(DOCBK, "informaltable");
        tabel.setAttribute("frame", "none");
        Element tgroup = (Element) tabel.appendChild(doc.createElementNS(DOCBK, "tgroup"));
        tgroup.setAttribute("cols", "2");
        tgroup.setAttribute("colsep", "0");
        tgroup.setAttribute("rowsep", "0");
        Element colspec = (Element) tgroup.appendChild(doc.createElementNS(DOCBK, "colspec"));
        colspec.setAttribute("align", "right");
        colspec.setAttribute("colwidth", LABEL_KOLOM_BREEDTE);
        colspec = (Element) tgroup.appendChild(doc.createElementNS(DOCBK, "colspec"));
        colspec.setAttribute("align", "left");
        Node tbody = tgroup.appendChild(doc.createElementNS(DOCBK, "tbody"));

        for (SoortTekst soortTekst : SoortTekst.values()) {
            if (element.getTekst(soortTekst) != null) {
                tbody.appendChild(textRow(element, soortTekst));
            }
        }
        return tabel;
    }

    private Node section(final String string) {
        Element section = doc.createElementNS(DOCBK, "section");
        section.appendChild(doc.createElementNS(DOCBK, "title")).appendChild(doc.createTextNode(string));
        return section;
    }

    /**
     * @param element TODO
     * @return
     */
    private Element section(final nl.bzk.brp.ecore.bmr.Element element) {
        Element section = doc.createElementNS(DOCBK, "section");
        section.appendChild(doc.createElementNS(DOCBK, "title")).appendChild(doc.createTextNode(element.getNaam()));
        section.appendChild(subTitel(element));

        section.setAttributeNS(XML, "xml:id", element.xmlId());
        return section;
    }

    /**
     * @param element
     * @return
     */
    private Node subTitel(final nl.bzk.brp.ecore.bmr.Element element) {
        Node resultaat = doc.createElementNS(DOCBK, "subtitle");
        if (element instanceof ObjectType) {
            resultaat.appendChild(doc.createTextNode("Objecttype in het schema "));
            resultaat.appendChild(emphasis(((ObjectType) element).getSchema().getGekwalificeerdeNaam()));
        } else if (element instanceof AttribuutType) {
            resultaat.appendChild(doc.createTextNode("Attribuutype in het schema "));
            resultaat.appendChild(emphasis(((AttribuutType) element).getSchema().getGekwalificeerdeNaam()));
        } else if (element instanceof Groep) {
            resultaat.appendChild(doc.createTextNode("Groep in het objecttype "));
            resultaat.appendChild(emphasis(((Groep) element).getObjectType().getGekwalificeerdeNaam()));
        } else if (element instanceof Attribuut) {
            resultaat.appendChild(doc.createTextNode("Attribuut in de groep "));
            resultaat.appendChild(emphasis(((Attribuut) element).getGroep().getGekwalificeerdeNaam()));
        }
        return resultaat;
    }

    /**
     * @param naam TODO
     * @return
     */
    private Element emphasis(final String naam) {
        Element emphasis = doc.createElementNS(DOCBK, "emphasis");
        emphasis.setAttribute("role", "italic");
        emphasis.appendChild(doc.createTextNode(naam));
        return emphasis;
    }

    private Element textRow(final nl.bzk.brp.ecore.bmr.Element element, final SoortTekst soortTekst) {
        return row(entry(getSoortTekstLabel(soortTekst), "bold"), entry(teksten(element, soortTekst)));
    }

    /**
     * @param nodes TODO
     * @param emphasis TODO
     * @param type
     */
    private Element row(final Node label, final Node value) {
        Element row = doc.createElementNS(DOCBK, "row");
        for (Node node : new Node[] { label, value }) {
            if (node != null) {
                Node container = null;
                if (!node.getNodeName().equals("entry")) {
                    container = row.appendChild(doc.createElementNS(DOCBK, "entry"));
                } else {
                    container = row;
                }
                container.appendChild(node);
            } else {
                row.appendChild(doc.createElementNS(DOCBK, "entry"));
            }
        }
        return row;
    }

    /**
     * @param element
     * @param soortTekst TODO
     * @return
     */
    private List<Node> teksten(final nl.bzk.brp.ecore.bmr.Element element, final SoortTekst soortTekst) {

        List<Node> nodeLijst = new ArrayList<Node>();
        Tekst tekst = element.getTekst(soortTekst);
        if (tekst != null) {
            for (String string : tekst.getTekst().split("\r\n")) {
                Node paragraaf = doc.createElementNS(DOCBK, "para");
                paragraaf.appendChild(doc.createTextNode(string));
                nodeLijst.add(paragraaf);
            }
        }
        return nodeLijst;
    }

    private Node entry(final String content) {
        return entry(content, null);
    }

    private Node entry(final List<Node> nodes) {
        Element entry = doc.createElementNS(DOCBK, "entry");
        for (Node node : nodes) {
            entry.appendChild(node);
        }
        return entry;
    }

    private Node entry(final String content, final String emphasis) {
        return entry(content, emphasis, null);
    }

    /**
     * @param content
     * @param emphasis
     */
    private Node entry(final String content, final String emphasis, final String id) {
        Element entry = doc.createElementNS(DOCBK, "entry");
        if (id != null) {
            entry.setAttribute("linkend", id);
        }
        Element container = entry;
        if (content != null) {
            if (emphasis != null) {
                container = (Element) container.appendChild(doc.createElementNS(DOCBK, "emphasis"));
                container.setAttribute("role", emphasis);
            }
            container.appendChild(doc.createTextNode(content));
        }
        return entry;
    }

    /**
     * Serialiseer de DOM tree naar een bestand als XMLNS.
     *
     * @param output De outputstream waar het bestand naartoe gaat.
     * @param doc Het DOM doc dat geserialiseerd wordt.
     * @throws TransformerFactoryConfigurationError een uitzondering.
     * @throws TransformerException een uitzondering
     */
    private void serializeDocument(final FileSystemAccess output, final Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        StringWriter stringWriter = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        output.generateFile("gegevenswoordenboek.xml", stringWriter.getBuffer());
    }

    private String getInSomOmschrijving(final InSetOfModel inSom) {
        String resultaat = null;
        switch (inSom) {
            case SET:
                resultaat = "Gegevensset";
                break;
            case MODEL:
                resultaat = "Logisch model";
                break;
            case BEIDE:
                resultaat = "Gegevensset en logisch model";
                break;
        }
        return resultaat;
    }

    /**
     * @param type
     * @return
     */
    private String getSoortInhoudOmschrijving(final ObjectType type) {
        String resultaat = null;
        switch (type.getSoortInhoud()) {
            case D:
                resultaat = "Operationele gegevens - Dynamisch";
                break;
            case S:
                resultaat = "Stamgegevens - Dynamisch";
                break;
            case X:
                resultaat = "Stamgegevens - Statisch";
                break;
        }
        return resultaat;
    }

    private String getHistorieOmschrijving(final Historie historie) {
        String resultaat = null;
        switch (historie) {
            case B:
                resultaat = "Zowel materiële als formele historie";
                break;
            case F:
                resultaat = "Alleen formele historie";
                break;
            case G:
                resultaat = "Geen historie";
                break;
            case M:
                resultaat = "Alleen materiële historie";
                break;
            case P:
                resultaat = "Bestaansperiode vastleggen";
                break;
        }
        return resultaat;
    }

    private Map<SoortTekst, String> soortTekstLabel = new TreeMap<SoortTekst, String>();
    {
        soortTekstLabel.put(SoortTekst.DEF, "Definitie");
        soortTekstLabel.put(SoortTekst.DEFT, "Toelichting");
        soortTekstLabel.put(SoortTekst.POP, "Populatie beschrijving");
        soortTekstLabel.put(SoortTekst.MOB, "Modelleer- en ontwerpbeslissingen");
        soortTekstLabel.put(SoortTekst.UITT, "Uitvoeringstoelichting");
        soortTekstLabel.put(SoortTekst.CONT, "Conversie toelichting");
        soortTekstLabel.put(SoortTekst.PSA, "Pseudo algoritme");
        soortTekstLabel.put(SoortTekst.REAT, "Realisatie toelichting");
        soortTekstLabel.put(SoortTekst.AAN, "Aantekeningen");
        soortTekstLabel.put(SoortTekst.LOG, "Logboek/verslag");
        soortTekstLabel.put(SoortTekst.TUP, "Inhoud");
        soortTekstLabel.put(SoortTekst.XSD, "XSD");
        soortTekstLabel.put(SoortTekst.XML, "XML");
        soortTekstLabel.put(SoortTekst.VOR, "Voorbeeld");
        soortTekstLabel.put(SoortTekst.BGR, "Bedrijfs- en Gegevensregels");
    }

    private String getSoortTekstLabel(final SoortTekst soort) {
        return soortTekstLabel.get(soort) + ":";
    }
}
