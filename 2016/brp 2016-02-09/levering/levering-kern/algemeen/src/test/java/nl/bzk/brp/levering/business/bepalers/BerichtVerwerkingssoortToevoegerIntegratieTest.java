/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ErkennerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.InstemmerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamgeverHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.ErkenningOngeborenVruchtHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.GeregistreerdPartnerschapHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.HuwelijkHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.NaamskeuzeOngeborenVruchtHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.OuderHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Test;


/**
 * Deze test controleert of alle elementen onder de persoonHisVolledigView een verwerkingssoort hebben gekregen.
 */
public class BerichtVerwerkingssoortToevoegerIntegratieTest extends AbstractIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final Long ADMINISTRATIEVE_HANDELING_ID = 1001L;

    private static final int PERSOON_ID_GETROUWDE = 48;

    private static final int PERSOON_ID_KIND = 46;

    private static final int PERSOON_ID_OUDER = 47;

    private static final int PERSOON_ID_VEEL_WIJZIGINGEN = 49;

    private static final List<String> BETROKKEN_PERSOON_GROEPEN = Arrays.asList(
            "persoonIdentificatienummersHistorie", "persoonSamengesteldeNaamHistorie",
            "persoonGeboorteHistorie", "persoonGeslachtsaanduidingHistorie");

    private static final String LOG_VERWERKINGSSOORT_AANGETROFFEN = "Verwerkingssoort {} aangetroffen op {}";

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @Inject
    @Named("berichtVerwerkingssoortToevoeger")
    private BerichtVerwerkingssoortZetter berichtVerwerkingssoortToevoeger;

    private PersoonHisVolledigView persoonHisVolledigView;

    private PersoonHisVolledig persoonHisVolledig;

    @Test
    public final void testVoegVerwerkingssoortenToeOpGroepen() throws IntrospectionException, ReflectiveOperationException {
        persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(PERSOON_ID_VEEL_WIJZIGINGEN);
        persoonHisVolledigView = new PersoonHisVolledigView(persoonHisVolledig, null);
        berichtVerwerkingssoortToevoeger.voegVerwerkingssoortenToe(persoonHisVolledigView, ADMINISTRATIEVE_HANDELING_ID);

        Assert.assertTrue(alleVerwerkingssoortenZijnGevuld(persoonHisVolledigView, false));
    }

    @Test
    public final void testVoegVerwerkingssoortenToeOpSets() {
        persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(PERSOON_ID_VEEL_WIJZIGINGEN);
        persoonHisVolledigView = new PersoonHisVolledigView(persoonHisVolledig, null);
        berichtVerwerkingssoortToevoeger.voegVerwerkingssoortenToe(persoonHisVolledigView, ADMINISTRATIEVE_HANDELING_ID);

        for (final PersoonAdresHisVolledig adres : persoonHisVolledigView.getAdressen()) {
            Assert.assertTrue(alleHiselementenHebbenEenVerwerkingssoort(adres.getPersoonAdresHistorie()));
        }
        for (final PersoonVoornaamHisVolledig voornaam : persoonHisVolledigView.getVoornamen()) {
            Assert.assertTrue(alleHiselementenHebbenEenVerwerkingssoort(voornaam.getPersoonVoornaamHistorie()));
        }
        for (final PersoonGeslachtsnaamcomponentHisVolledig geslachtsnaam : persoonHisVolledigView.getGeslachtsnaamcomponenten()) {
            Assert.assertTrue(alleHiselementenHebbenEenVerwerkingssoort(geslachtsnaam.getPersoonGeslachtsnaamcomponentHistorie()));
        }
        for (final PersoonNationaliteitHisVolledig nationaliteit : persoonHisVolledigView.getNationaliteiten()) {
            Assert.assertTrue(alleHiselementenHebbenEenVerwerkingssoort(nationaliteit.getPersoonNationaliteitHistorie()));
        }
        for (final PersoonIndicatieHisVolledig indicatie : persoonHisVolledigView.getIndicaties()) {
            Assert.assertTrue(alleHiselementenHebbenEenVerwerkingssoort(indicatie.getPersoonIndicatieHistorie()));
        }
        for (final PersoonReisdocumentHisVolledig reisdocument : persoonHisVolledigView.getReisdocumenten()) {
            Assert.assertTrue(alleHiselementenHebbenEenVerwerkingssoort(reisdocument.getPersoonReisdocumentHistorie()));
        }
    }

    @Test
    public final void testVoegVerwerkingssoortenToeOpBetrokkenhedenKind() throws IntrospectionException, ReflectiveOperationException {
        persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(PERSOON_ID_KIND);
        persoonHisVolledigView = new PersoonHisVolledigView(persoonHisVolledig, null);
        berichtVerwerkingssoortToevoeger.voegVerwerkingssoortenToe(persoonHisVolledigView, ADMINISTRATIEVE_HANDELING_ID);

        for (final BetrokkenheidHisVolledig betrokkenheid : persoonHisVolledigView.getBetrokkenheden()) {
            Assert.assertTrue(alleVerwerkingsoortenOpBetrokkenheidEnRelatieZijnGevuld(betrokkenheid, true));
        }
    }

    @Test
    public final void testVoegVerwerkingssoortenToeOpBetrokkenhedenOuder() throws IntrospectionException, ReflectiveOperationException {
        persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(PERSOON_ID_OUDER);
        persoonHisVolledigView = new PersoonHisVolledigView(persoonHisVolledig, null);
        berichtVerwerkingssoortToevoeger.voegVerwerkingssoortenToe(persoonHisVolledigView, ADMINISTRATIEVE_HANDELING_ID);

        for (final BetrokkenheidHisVolledig betrokkenheid : persoonHisVolledigView.getBetrokkenheden()) {
            Assert.assertTrue(alleVerwerkingsoortenOpBetrokkenheidEnRelatieZijnGevuld(betrokkenheid, true));
        }
    }

    @Test
    public final void testVoegVerwerkingssoortenToeOpBetrokkenhedenGetrouwde() throws IntrospectionException, ReflectiveOperationException {
        persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(PERSOON_ID_GETROUWDE);
        persoonHisVolledigView = new PersoonHisVolledigView(persoonHisVolledig, null);
        berichtVerwerkingssoortToevoeger.voegVerwerkingssoortenToe(persoonHisVolledigView, ADMINISTRATIEVE_HANDELING_ID);

        for (final BetrokkenheidHisVolledig betrokkenheid : persoonHisVolledigView.getBetrokkenheden()) {
            Assert.assertTrue(alleVerwerkingsoortenOpBetrokkenheidEnRelatieZijnGevuld(betrokkenheid, true));
        }
    }

    private boolean alleVerwerkingsoortenOpBetrokkenheidEnRelatieZijnGevuld(final BetrokkenheidHisVolledig betrokkenheid,
        final boolean doorzoekOokRelatie) throws IntrospectionException, ReflectiveOperationException
    {
        boolean alleBetrokkenhedenZijnGevuld = true;
        if (betrokkenheid instanceof ErkennerHisVolledig) {
            throw new NotImplementedException();
        } else if (betrokkenheid instanceof InstemmerHisVolledig) {
            throw new NotImplementedException();
        } else if (betrokkenheid instanceof KindHisVolledig) {
            //Doorzoek alleen relatie voor kind, historie staat op de ouder betrokkenheden
            boolean relatieEnBetrokkenhedenZijnGevuld = true;
            if (doorzoekOokRelatie) {
                for (final BetrokkenheidHisVolledig relatieBetrokkenheid : betrokkenheid.getRelatie().getBetrokkenheden()) {
                    relatieEnBetrokkenhedenZijnGevuld = relatieEnBetrokkenhedenZijnGevuld
                            && alleVerwerkingsoortenOpBetrokkenheidEnRelatieZijnGevuld(relatieBetrokkenheid, false);
                }
            }
            alleBetrokkenhedenZijnGevuld = relatieEnBetrokkenhedenZijnGevuld;
        } else if (betrokkenheid instanceof NaamgeverHisVolledig) {
            throw new NotImplementedException();
        } else if (betrokkenheid instanceof OuderHisVolledig) {
            final OuderHisVolledigView ouderBetrokkenheid = (OuderHisVolledigView) betrokkenheid;
            final boolean hisElementenZijnGevuld =
                    alleHiselementenHebbenEenVerwerkingssoort(ouderBetrokkenheid.getOuderOuderlijkGezagHistorie())
                            &&
                            alleHiselementenHebbenEenVerwerkingssoort(ouderBetrokkenheid.getOuderOuderschapHistorie());

            boolean relatieEnBetrokkenhedenZijnGevuld = true;
            if (doorzoekOokRelatie) {
                relatieEnBetrokkenhedenZijnGevuld =
                        alleVerwerkingssoortenOpRelatieZijnGevuld(betrokkenheid.getRelatie());
                for (final BetrokkenheidHisVolledig relatieBetrokkenheid : betrokkenheid.getRelatie().getBetrokkenheden()) {
                    relatieEnBetrokkenhedenZijnGevuld = relatieEnBetrokkenhedenZijnGevuld
                            && alleVerwerkingsoortenOpBetrokkenheidEnRelatieZijnGevuld(relatieBetrokkenheid, false);
                }
            }

            alleBetrokkenhedenZijnGevuld = hisElementenZijnGevuld && relatieEnBetrokkenhedenZijnGevuld;
        } else if (betrokkenheid instanceof PartnerHisVolledig) {
            alleBetrokkenhedenZijnGevuld = alleVerwerkingssoortenOpRelatieZijnGevuld(betrokkenheid.getRelatie());
        }

        final boolean betrokkenPersoonIsGevuld =
                alleVerwerkingssoortenZijnGevuld((PersoonHisVolledigView) betrokkenheid.getPersoon(), true);

        return alleBetrokkenhedenZijnGevuld && betrokkenPersoonIsGevuld;
    }

    private boolean alleVerwerkingssoortenOpRelatieZijnGevuld(final RelatieHisVolledig relatie) {
        boolean resultaat = true;
        if (relatie instanceof HuwelijkHisVolledigView) {
            final HuwelijkHisVolledigView huwelijk = (HuwelijkHisVolledigView) relatie;

            if (!alleHiselementenHebbenEenVerwerkingssoort(huwelijk.getRelatieHistorie())) {
                resultaat = false;
            }
        } else if (relatie instanceof GeregistreerdPartnerschapHisVolledigView) {
            final GeregistreerdPartnerschapHisVolledigView geregistreerdPartnerschap = (GeregistreerdPartnerschapHisVolledigView) relatie;

            if (!alleHiselementenHebbenEenVerwerkingssoort(geregistreerdPartnerschap.getRelatieHistorie())) {
                resultaat = false;
            }
        } else if (relatie instanceof ErkenningOngeborenVruchtHisVolledigView) {
            final ErkenningOngeborenVruchtHisVolledigView erkenning = (ErkenningOngeborenVruchtHisVolledigView) relatie;

            if (!alleHiselementenHebbenEenVerwerkingssoort(erkenning.getRelatieHistorie())) {
                resultaat = false;
            }
        } else if (relatie instanceof NaamskeuzeOngeborenVruchtHisVolledigView) {
            final NaamskeuzeOngeborenVruchtHisVolledigView naamskeuze = (NaamskeuzeOngeborenVruchtHisVolledigView) relatie;

            if (!alleHiselementenHebbenEenVerwerkingssoort(naamskeuze.getRelatieHistorie())) {
                resultaat = false;
            }
        }
        return resultaat;
    }

    private boolean alleVerwerkingssoortenZijnGevuld(final PersoonHisVolledigView persoonView, final boolean isBetrokkene)
        throws IntrospectionException, ReflectiveOperationException
    {
        final BeanInfo info = Introspector.getBeanInfo(persoonView.getClass());
        for (final PropertyDescriptor pd : info.getPropertyDescriptors()) {
            final Object veldVanPersoonHisVolledig = pd.getReadMethod().invoke(persoonView);
            if (isFormeleOfMaterieleHistorieSet(veldVanPersoonHisVolledig)) {
                if (isBetrokkene && groepIsNietNodigVoorBetrokkene(pd.getName())) {
                    continue;
                }
                if (!alleHiselementenHebbenEenVerwerkingssoort(veldVanPersoonHisVolledig)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean groepIsNietNodigVoorBetrokkene(final String veldNaam) {
        return !BETROKKEN_PERSOON_GROEPEN.contains(veldNaam);
    }

    private boolean isFormeleOfMaterieleHistorieSet(final Object veldVanPersoonHisVolledig) {
        return veldVanPersoonHisVolledig instanceof MaterieleHistorieSet || veldVanPersoonHisVolledig instanceof FormeleHistorieSet;
    }

    private boolean alleHiselementenHebbenEenVerwerkingssoort(final Object materieleOfFormeleHistorieSet) {
        boolean resultaat = true;
        if (materieleOfFormeleHistorieSet instanceof MaterieleHistorieSet) {
            for (final Object materieleHistorieEntiteit : ((MaterieleHistorieSet) materieleOfFormeleHistorieSet).getHistorie()) {
                final MaterieelHistorisch materieleHistorie = (MaterieelHistorisch) materieleHistorieEntiteit;
                if (materieleHistorie.getVerwerkingssoort() == null) {
                    LOGGER.error("Geen verwerkingssoort gevonden op materiele historie entiteit: {}.", materieleHistorie.getClass());
                    resultaat = false;
                } else {
                    LOGGER.debug(LOG_VERWERKINGSSOORT_AANGETROFFEN, materieleHistorie.getVerwerkingssoort(), materieleHistorie.getClass());
                }
            }
        } else {
            for (final Object formeleHistorieEntiteit : ((FormeleHistorieSet) materieleOfFormeleHistorieSet).getHistorie()) {
                final FormeelHistorisch formeleHistorie = (FormeelHistorisch) formeleHistorieEntiteit;
                if (formeleHistorie.getVerwerkingssoort() == null) {
                    LOGGER.error("Geen verwerkingssoort gevonden op formele historie entiteit: {}.", formeleHistorie.getClass());
                    resultaat = false;
                } else {
                    LOGGER.debug(LOG_VERWERKINGSSOORT_AANGETROFFEN, formeleHistorie.getVerwerkingssoort(), formeleHistorie.getClass());
                }
            }
        }
        return resultaat;
    }

}
