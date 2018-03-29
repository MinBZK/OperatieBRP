/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis.impl;

import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.test.common.xml.XPathHelper;
import nl.bzk.brp.test.common.xml.XmlUtils;
import nl.bzk.brp.tooling.apitest.service.basis.BerichtControleService;
import nl.bzk.brp.tooling.apitest.service.basis.StoryService;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.LeverberichtStubService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * BerichtControleService impl.
 */
final class BerichtControleServiceImpl implements BerichtControleService {

    @Inject
    private VerzoekService verzoekService;
    @Inject
    private LeverberichtStubService leverberichtStubService;
    @Inject
    private StoryService storyService;
    @Inject
    private XPathHelper xPathHelper;

    private int verzoekAntwoordIndex;
    private int leverBerichtIndex;


    @Override
    public void assertBerichtGelijkAanExpected(final TypeBericht typeBericht, final String expectedFile, final List<String> sortAttributen)
            throws Exception {
        final Set<String> sorteerAttributen = sortAttributen == null || sortAttributen.isEmpty() ? Collections.emptySet() : ImmutableSet.copyOf(sortAttributen);
        final String xml = getXmlVoorTypeBericht(typeBericht);
        if (typeBericht == TypeBericht.ANTWOORDBERICHT) {
            assertAntwoordberichtGelijkAanExpected(xml, expectedFile, sorteerAttributen);
        } else {
            assertSynchronisatieberichtGelijkAanExpected(xml, expectedFile, sorteerAttributen);
        }
    }

    @Override
    public void assertBerichtGelijkAanExpectedOngeachtVolgorde(final TypeBericht typeBericht, final String expectedFile, final List<String> sortAttributen)
            throws Exception {
        final Set<String> sorteerAttributen = ImmutableSet.copyOf(sortAttributen);
        final String xml = getXmlVoorTypeBericht(typeBericht);
        if (typeBericht == TypeBericht.ANTWOORDBERICHT) {
            assertAntwoordberichtGelijkAanExpected(xml, expectedFile, sorteerAttributen);
        } else {
            assertSynchronisatieberichtGelijkAanExpected(xml, expectedFile, sorteerAttributen);
        }
    }


    @Override
    public void assertAntwoordberichtHeeftVerwerking(final String verwerking) {
        Assert.notNull(verwerking, "Verwerking leeg");
        final String antwoordbericht = verzoekService.getLaatsteAntwoordbericht();
        if (!xPathHelper.isNodeAanwezig(antwoordbericht, String.format("//brp:verwerking[text() = '%s']", verwerking))) {
            throw new AssertionError(String.format("Verwerking is niet '%s'", verwerking));
        }
    }

    @Override
    public void assertBerichtHeeftMelding(final TypeBericht typeBericht, final Set<String> meldingSet) {
        final String xml = getXmlVoorTypeBericht(typeBericht);
        for (final String entry : meldingSet) {
            xPathHelper.assertRegelCodeBestaat(xml, entry);
        }
    }

    @Override
    public void assertNodeBestaatNiet(final TypeBericht typeBericht, final String xpathExpressie) {
        xPathHelper.assertNodeBestaatNiet(getXmlVoorTypeBericht(typeBericht), xpathExpressie);

    }

    @Override
    public void assertNodeBestaat(final TypeBericht typeBericht, final String xpathExpressie) {
        if (!xPathHelper.isNodeAanwezig(getXmlVoorTypeBericht(typeBericht), xpathExpressie)) {
            throw new AssertionError("Node niet gevonden voor expressie: " + xpathExpressie);
        }
    }

    @Override
    public void assertPlatteWaardeGelijk(final TypeBericht typeBericht, final String xpathExpressie, final String waarde) {
        xPathHelper.assertPlatteWaardeGelijk(getXmlVoorTypeBericht(typeBericht), xpathExpressie, waarde);
    }

    @Override
    public void assertEvaluatieGelijkAanWaarde(final TypeBericht typeBericht, final String xpathExpressie, final String waarde) {
        xPathHelper.assertWaardeGelijk(getXmlVoorTypeBericht(typeBericht), xpathExpressie, waarde);
    }

    @Override
    public void assertBevatLeafNode(final TypeBericht typeBericht, final String xpathExpressie) {
        xPathHelper.assertLeafNodeBestaat(getXmlVoorTypeBericht(typeBericht), xpathExpressie);
    }

    @Override
    public void assertHeeftBerichtAantalMeldingen(final TypeBericht typeBericht, final int count) {
        final String xml = getXmlVoorTypeBericht(typeBericht);
        xPathHelper.assertAantalMeldingenGelijk(xml, count);
    }

    @Override
    public void assertHeeftBerichtAantalMeldingenVoorPersonen(final TypeBericht typeBericht, final int count, final String regelNummer,
                                                              final List<String> bsnLijst) {
        xPathHelper.assertMeldingReferentiesNaarPersonenCorrect(getXmlVoorTypeBericht(typeBericht), "R1340", count, bsnLijst);
    }

    @Override
    public void assertElementAanwezig(final TypeBericht typeBericht, final String element, final boolean aanwezig) {
        final String xml = getXmlVoorTypeBericht(typeBericht);
        final int aantalElementen = xPathHelper.getAantalElementen(xml, element);
        Assert.isTrue(aantalElementen == 0 && !aanwezig, String.format("Aanwezigheid element incorrect, aanwezig = %s en aantalElementen = %d",
                aanwezig, aantalElementen));
    }

    @Override
    public void assertElementAantal(final TypeBericht typeBericht, final String xmlElement, final int aantal) {
        final String xml = getXmlVoorTypeBericht(typeBericht);
        final int aantalElementen = xPathHelper.getAantalElementen(xml, xmlElement);
        Assert.isTrue(aantalElementen == aantal,
                String.format("Aantal '%s' elementen incorrect verwacht = %d  gevonden = %d", xmlElement, aantal, aantalElementen));
    }

    @Override
    public void assertVerantwoordingCorrect(final TypeBericht typeBericht) {
        xPathHelper.assertVerantwoordingCorrect(getXmlVoorTypeBericht(typeBericht));
    }

    @Override
    public void assertBerichtHeeftWaardes(final TypeBericht typeBericht, final String groep, final String attribuut, final List<String> verwachteWaardes) {
        xPathHelper.assertBerichtHeeftWaardes(getXmlVoorTypeBericht(typeBericht), groep, attribuut, verwachteWaardes);
    }

    @Override
    public void assertAttribuutAanwezigheid(final TypeBericht typeBericht, final String voorkomen,
                                            final int voorkomenIndex, final String attribuut, final boolean aanwezig) {
        xPathHelper.assertBerichtHeeftAttribuutAanwezigheid(getXmlVoorTypeBericht(typeBericht), voorkomen, voorkomenIndex, attribuut, aanwezig);
    }

    @Override
    public void assertBerichtHeeftWaarde(final TypeBericht typeBericht, final String voorkomen, final int voorkomenIndex, final String attribuut,
                                         final String verwachteWaarde) {
        xPathHelper.assertBerichtHeeftAttribuutWaarde(getXmlVoorTypeBericht(typeBericht), voorkomen, voorkomenIndex, attribuut, verwachteWaarde);
    }

    @Override
    public void assertBerichtVerwerkingssoortCorrect(final TypeBericht typeBericht, final String voorkomen, final int voorkomenIndex,
                                                     final Verwerkingssoort verwerkingssoort) {
        xPathHelper.assertBerichtVerwerkingssoortCorrect(getXmlVoorTypeBericht(typeBericht), voorkomen, voorkomenIndex, verwerkingssoort);
    }

    @Override
    public void reset() {
        verzoekAntwoordIndex = 0;
        leverBerichtIndex = 0;
    }

    private String getXmlVoorTypeBericht(final TypeBericht typeBericht) {
        if (typeBericht == TypeBericht.ANTWOORDBERICHT) {
            return verzoekService.getLaatsteAntwoordbericht();
        } else {
            return leverberichtStubService.getLaatstBekekenBericht();
        }
    }


    private void assertAntwoordberichtGelijkAanExpected(final String xml, final String expectedFile, final Set<String> sorteerAttributen)
            throws Exception {
        Assert.notNull(expectedFile, "Expected leeg");
        Assert.notNull(expectedFile, "Xpath leeg");
        final Resource resource = storyService.resolvePath(expectedFile);
        final String xmlFormatted = XmlUtils.format(xml);
        final String expectedXml = XmlUtils.format(IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8));

        final File outputDir = storyService.getOutputDir();
        outputDir.mkdirs();

        verzoekAntwoordIndex++;
        final File fileExpected = new File(outputDir, String.format("RESPONSE-%d-EXPECTED.xml", verzoekAntwoordIndex));
        try (final OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileExpected), StandardCharsets.UTF_8)) {
            IOUtils.write(expectedXml, osw);
        }
        final File fileActual = new File(outputDir, String.format("RESPONSE-%d-ACTUAL.xml", verzoekAntwoordIndex));
        try (final OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileActual), StandardCharsets.UTF_8)) {
            IOUtils.write(xmlFormatted, osw);
        }
        final String antwoordberichtGesterred = XmlUtils.ster2(xmlFormatted);
        final File fileActualSter = new File(outputDir, String.format("RESPONSE-%d-ACTUAL.xml.ster.xml", verzoekAntwoordIndex));
        try (final OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileActualSter), StandardCharsets.UTF_8)) {
            IOUtils.write(antwoordberichtGesterred, osw);
        }

        if (sorteerAttributen != null && !sorteerAttributen.isEmpty()) {
            XmlUtils.assertGelijkNegeerVolgorde(expectedXml, antwoordberichtGesterred, sorteerAttributen);
        } else {
            XmlUtils.assertGelijk("", expectedXml, antwoordberichtGesterred);
        }
    }

    private void assertSynchronisatieberichtGelijkAanExpected(final String xml, final String expectedFile, final Set<String> sorteerAttributen)
            throws Exception {
        final Resource resource = storyService.resolvePath(expectedFile);
        final String expectedXml;
        try (final InputStream inputStream = resource.getInputStream()) {
            expectedXml = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }

        final File outputDir = storyService.getOutputDir();
        outputDir.mkdirs();
        leverBerichtIndex++;
        try (final OutputStreamWriter osw = new OutputStreamWriter(
                new FileOutputStream(new File(outputDir, String.format("LEVERING-%d-EXPECTED.xml", leverBerichtIndex))), StandardCharsets.UTF_8)) {
            IOUtils.write(expectedXml, osw);
        }
        try (final FileOutputStream fos = new FileOutputStream(new File(outputDir, String.format("LEVERING-%d-ACTUAL.xml", leverBerichtIndex)))) {
            IOUtils.write(XmlUtils.format(xml), fos, StandardCharsets.UTF_8);
        }

        if (sorteerAttributen != null && !sorteerAttributen.isEmpty()) {
            XmlUtils.assertGelijkNegeerVolgorde(expectedXml, XmlUtils.ster(xml), sorteerAttributen);
        } else {
            XmlUtils.assertGelijk(null, expectedXml, XmlUtils.ster(xml));
        }

    }

}
