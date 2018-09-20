/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging.bijhouding;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathNotExists;

import java.io.IOException;
import nl.bzk.brp.binding.bevraging.AbstractVraagBerichtBindingUitIntegratieTest;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.HuidigeSituatiePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.PersoonViewUtil;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.jibx.runtime.JiBXException;
import org.junit.BeforeClass;
import org.junit.Test;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.xml.sax.SAXException;

public class LegeContainerGeefDetailsPersoonTest extends AbstractVraagBerichtBindingUitIntegratieTest<GeefDetailsPersoonAntwoordBericht> {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String EEN = "1";

    @BeforeClass
    public static void beforeClass() {
        final SimpleNamespaceContext ctx = new SimpleNamespaceContext(BRP_NAMESPACE_MAP);
        XMLUnit.setXpathNamespaceContext(ctx);
    }

    @Test
    public void verwijdertMeldingenEnPersonenContainer() throws JiBXException, SAXException, IOException, XpathException {
        final GeefDetailsPersoonAntwoordBericht bericht = maakBericht();

        final String xml = marshalObject(bericht);
        LOGGER.info(xml);

        assertXpathNotExists("//brp:meldingen", xml);
        assertXpathNotExists("//brp:personen", xml);
    }

    @Test
    public void verwijdertLegeContainersBinnenPersoon() throws JiBXException, SAXException, IOException, XpathException {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        persoon.getVoornamen().clear();
        persoon.getGeslachtsnaamcomponenten().clear();
        persoon.getAdressen().clear();
        persoon.getNationaliteiten().clear();
        persoon.setIndicatieOnderCuratele(null);
        persoon.getBetrokkenheden().clear();
        persoon.getReisdocumenten().clear();
        persoon.getAdministratieveHandelingen().clear();
        persoon.getOnderzoeken().clear();

        final GeefDetailsPersoonAntwoordBericht bericht = maakBericht();
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, new HuidigeSituatiePredikaat());
        zetObjectSleutels(persoonView);
        bericht.voegGevondenPersoonToe(persoonView);

        final String xml = marshalObject(bericht);
        LOGGER.info(xml);

        assertXpathNotExists("/brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:voornamen", xml);
        assertXpathNotExists("//brp:geslachtsnaamcomponenten", xml);
        assertXpathNotExists("//brp:adressen", xml);
        assertXpathNotExists("//brp:nationaliteiten", xml);
        assertXpathNotExists("//brp:indicaties", xml);
        assertXpathNotExists("//brp:verstrekkingsbeperkingen", xml);
        assertXpathNotExists("//brp:betrokkenheden", xml);
        assertXpathNotExists("//brp:reisdocumenten", xml);
        assertXpathNotExists("//brp:verificaties", xml);
        assertXpathNotExists("//brp:onderzoeken", xml);
        assertXpathNotExists("//brp:administratieveHandelingen", xml);
    }

    @Test
    public void verwijdertAdministratieveHandelingenChildContainers() throws JiBXException, SAXException, IOException, XpathException {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        for (final AdministratieveHandelingHisVolledigImpl ah : persoon.getAdministratieveHandelingen()) {
            ah.getGedeblokkeerdeMeldingen().clear();
            ah.getActies().clear();
        }
        final GeefDetailsPersoonAntwoordBericht bericht = maakBericht();
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, new HuidigeSituatiePredikaat());
        zetObjectSleutels(persoonView);
        bericht.voegGevondenPersoonToe(persoonView);

        final String xml = marshalObject(bericht);
        LOGGER.info(xml);

        assertXpathNotExists("//brp:gedebokkeerdeMeldingen", xml);
        assertXpathNotExists("//brp:bronnen", xml);
        assertXpathNotExists("//brp:bijgehoudenActies", xml);
    }

    @Test
    public void verwijdertLegeGerelateerdeBetrokkenhedenContainer() throws JiBXException, SAXException, IOException, XpathException {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        leegGerelateerdeBetrokkenheden(persoon);
        final GeefDetailsPersoonAntwoordBericht bericht = maakBericht();
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, new HuidigeSituatiePredikaat());
        zetObjectSleutels(persoonView);
        bericht.voegGevondenPersoonToe(persoonView);

        PersoonViewUtil.zetAlleMagGeleverdWordenVlaggen(persoonView);

        final String xml = marshalObject(bericht);
        LOGGER.info(xml);

        assertXpathNotExists("//brp:betrokkenheden[ancestor::brp:betrokkenheden]", xml);
    }

    /**
     * Leeg de gerelateerde betrokkenheden.
     *
     * @param persoon de persoon
     */
    private void leegGerelateerdeBetrokkenheden(final PersoonHisVolledigImpl persoon) {
        for (final BetrokkenheidHisVolledigImpl betrokkenheid : persoon.getBetrokkenheden()) {
            betrokkenheid.getRelatie().getBetrokkenheden().clear();
        }
    }

    /**
     * Maak bericht.
     *
     * @return vul bericht
     */
    private GeefDetailsPersoonAntwoordBericht maakBericht() {
        final GeefDetailsPersoonAntwoordBericht bericht = new GeefDetailsPersoonAntwoordBericht();
        bericht.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht(EEN, EEN));
        bericht.setResultaat(maakBerichtResultaat(SoortMelding.GEEN));
        return bericht;
    }

    @Override
    protected Class<GeefDetailsPersoonAntwoordBericht> getBindingClass() {
        return GeefDetailsPersoonAntwoordBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdBijhoudingBevragingBerichten();
    }
}
