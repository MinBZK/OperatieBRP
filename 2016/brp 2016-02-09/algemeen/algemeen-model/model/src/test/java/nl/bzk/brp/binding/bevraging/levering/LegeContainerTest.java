/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging.levering;

import static java.util.Collections.singletonList;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathNotExists;

import java.io.IOException;
import java.util.HashSet;
import nl.bzk.brp.binding.bevraging.AbstractVraagBerichtBindingUitIntegratieTest;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieHisVolledigImpl;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.SAXException;

public class LegeContainerTest extends AbstractVraagBerichtBindingUitIntegratieTest<GeefDetailsPersoonAntwoordBericht> {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @BeforeClass
    public static void beforeClass() {
        final SimpleNamespaceContext ctx = new SimpleNamespaceContext(BRP_NAMESPACE_MAP);
        XMLUnit.setXpathNamespaceContext(ctx);
    }

    @Test
    public void verwijdertMeldingenEnPersonenContainer() throws JiBXException, SAXException, IOException, XpathException {
        final GeefDetailsPersoonAntwoordBericht bericht = maakBericht();

        String xml = marshalObject(bericht);
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
        persoon.getReisdocumenten().clear();
        persoon.getAdministratieveHandelingen().clear();
        persoon.getOnderzoeken().clear();

        final GeefDetailsPersoonAntwoordBericht bericht = maakBericht();
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, new HuidigeSituatiePredikaat());
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
    public void verwijdertLegeContainersBinnenAdministratieveHandelingen() throws JiBXException, SAXException, IOException, XpathException {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        leegAdministratieveHandelingen(persoon);
        final GeefDetailsPersoonAntwoordBericht bericht = maakBericht();
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, new HuidigeSituatiePredikaat());
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
        bericht.voegGevondenPersoonToe(persoonView);

        PersoonViewUtil.zetAlleMagGeleverdWordenVlaggen(persoonView);

        final String xml = marshalObject(bericht);
        LOGGER.info(xml);

        assertXpathNotExists("//brp:betrokkenheden[ancestor::brp:betrokkenheden]", xml);
    }

    @Test
    public void verwijdertLegeContainersBinnenBijgehoudenActies() throws JiBXException, SAXException, IOException, XpathException {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        voegBijgehoudenActiesToeAanAdministratieveHandelingen(persoon);
        final GeefDetailsPersoonAntwoordBericht bericht = maakBericht();
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, new HuidigeSituatiePredikaat());
        zetObjectSleutels(persoonView);
        bericht.voegGevondenPersoonToe(persoonView);

        final String xml = marshalObject(bericht);
        LOGGER.info(xml);

        assertXpathNotExists("//brp:bronnen", xml);
    }

    private void voegBijgehoudenActiesToeAanAdministratieveHandelingen(final PersoonHisVolledigImpl persoon) {
        for (AdministratieveHandelingHisVolledigImpl handeling : persoon.getAdministratieveHandelingen()) {
            ActieHisVolledigImpl actie = new ActieHisVolledigImpl(new SoortActieAttribuut(SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER),
                handeling, null, null, null, null, null);
            actie.setMagGeleverdWorden(true);
            ReflectionTestUtils.setField(actie, "iD", 1L);
            handeling.setActies(new HashSet<>(singletonList(actie)));
        }
    }

    private void leegGerelateerdeBetrokkenheden(final PersoonHisVolledigImpl persoon) {
        for (BetrokkenheidHisVolledigImpl betrokkenheid : persoon.getBetrokkenheden()) {
            betrokkenheid.getRelatie().getBetrokkenheden().clear();
        }
    }

    private void leegAdministratieveHandelingen(PersoonHisVolledigImpl persoon) {
        for (AdministratieveHandelingHisVolledigImpl ah : persoon.getAdministratieveHandelingen()) {
            ah.getGedeblokkeerdeMeldingen().clear();
            ah.getActies().clear();
        }
    }

    /**
     * Maak bericht.
     *
     * @return vul bericht
     */
    private GeefDetailsPersoonAntwoordBericht maakBericht() {
        final GeefDetailsPersoonAntwoordBericht bericht = new GeefDetailsPersoonAntwoordBericht();
        bericht.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht("1", "1"));
        bericht.setResultaat(maakBerichtResultaat(SoortMelding.INFORMATIE));
        return bericht;
    }

    @Override
    protected Class<GeefDetailsPersoonAntwoordBericht> getBindingClass() {
        return GeefDetailsPersoonAntwoordBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "brp0200_lvgSynchronisatie_Berichten.xsd";
    }
}
