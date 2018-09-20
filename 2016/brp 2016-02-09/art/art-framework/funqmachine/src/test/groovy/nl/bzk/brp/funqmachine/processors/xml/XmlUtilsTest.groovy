package nl.bzk.brp.funqmachine.processors.xml

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual

import nl.bzk.brp.funqmachine.processors.xml.XmlUtils
import org.junit.Test
import org.w3c.dom.Document
import org.w3c.dom.Node

class XmlUtilsTest {
    @Test
    void kanDocumentMaken() {
        Document document = XmlUtils.bouwDocument('<xml><child></child></xml>')

        assert document.childNodes.length == 1
    }

    @Test
    void kanBrpDocumentMaken() {
        Document document = XmlUtils.bouwDocument('<brp:xml xmlns:brp="http://www.bzk.nl/brp/brp0200"><brp:child></brp:child></brp:xml>')

        assert document.childNodes.length == 1
    }

    @Test
    void kanNodeOphalen() {
        Document document = XmlUtils.bouwDocument('<brp:xml xmlns:brp="http://www.bzk.nl/brp/brp0200"><brp:child></brp:child></brp:xml>')

        Node node = XmlUtils.getNode('//brp:child', document)

        assertXMLEqual('<?xml version="1.0" encoding="UTF-8"?><brp:child xmlns:brp="http://www.bzk.nl/brp/brp0200"/>', XmlUtils.toXmlString(node))
    }

    @Test
    void prettifyXml() {
        def pretty = XmlUtils.prettify("""
<brp:xml xmlns:brp="http://www.bzk.nl/brp/brp0200">
    <brp:child></brp:child>
    <brp:child>content</brp:child>
    <brp:child> </brp:child>
</brp:xml>
""")

        assert pretty.contains('<brp:child> </brp:child>')
    }
}
