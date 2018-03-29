/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsaantekeningPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.test.common.jbehave.GeprotocolleerdePersoon;
import nl.bzk.brp.test.common.jbehave.ScopeElement;
import nl.bzk.brp.test.common.jbehave.VeldEnWaarde;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import org.springframework.util.Assert;

/**
 * De implementatie van {@link ProtocolleringControleService}.
 */
public final class ProtocolleringControleServiceImpl implements ProtocolleringControleService {

    private static final Map<String, String> PROT_KOLOM_NAAR_GETTER = new HashMap<String, String>() {
        {
            put("id", "getId");
            put("toeganglevsautorisatie", "getToegangLeveringsautorisatie");
            put("dienst", "getDienst");
            put("tsklaarzettenlev", "getDatumTijdKlaarzettenLevering");
            put("dataanvmaterieleperioderes", "getDatumAanvangMaterielePeriodeResultaat");
            put("dateindematerieleperioderes", "getDatumEindeMaterielePeriodeResultaat");
            put("tsaanvformeleperioderes", "getDatumTijdAanvangFormelePeriodeResultaat");
            put("tseindeformeleperioderes", "getDatumTijdEindeFormelePeriodeResultaat");
            put("admhnd", "getAdministratieveHandeling");
            put("srtsynchronisatie", "getSoortSynchronisatie");
        }
    };

    @Inject
    private ProtocolleringStubService protocolleringStubService;
    @Inject
    private VerzoekService verzoekService;

    @Override
    public void assertLaatsteSynchroneBerichtGeprotocolleerd() {
        Assert.isTrue(verzoekService.getLaatsteVerzoek().getSoortDienst() == SoortDienst.GEEF_DETAILS_PERSOON
                || verzoekService.getLaatsteVerzoek().getSoortDienst() == SoortDienst.ZOEK_PERSOON);

        Assert.isTrue(protocolleringStubService.erIsGeprotocolleerd());
    }

    @Override
    public void assertLaatsteSynchroneBerichtNietGeprotocolleerd() {
        Assert.isTrue(verzoekService.getLaatsteVerzoek().getSoortDienst() == SoortDienst.GEEF_DETAILS_PERSOON
                || verzoekService.getLaatsteVerzoek().getSoortDienst() == SoortDienst.ZOEK_PERSOON);

        Assert.isTrue(!protocolleringStubService.erIsGeprotocolleerd());
    }

    @Override
    public void assertIsGeprotocolleerdMetDeWaarden(final List<VeldEnWaarde> gegevens) {
        final Leveringsaantekening opdracht = geefEnigeLeveringsaantekening();
        gegevens.forEach(gegeven -> {
            if (!PROT_KOLOM_NAAR_GETTER.containsKey(gegeven.getVeld())) {
                throw new UnsupportedOperationException(String.format("Kan de kolom %s niet vinden.", gegeven.getVeld()));
            }
            final String getter = PROT_KOLOM_NAAR_GETTER.get(gegeven.getVeld());
            try {
                final Object waarde = Leveringsaantekening.class.getMethod(getter).invoke(opdracht);
                if ("null".equals(gegeven.getWaarde())) {
                    Assert.isNull(waarde, String.format("%s is niet null maar heeft waarde %s.", gegeven.getVeld(), waarde));
                } else if ("morgen".equalsIgnoreCase(gegeven.getWaarde())) {
                    org.junit.Assert.assertTrue(DatumUtil.morgen().equals(waarde));
                } else if ("vandaag".equalsIgnoreCase(gegeven.getWaarde())) {
                    org.junit.Assert.assertTrue(Integer.valueOf(DatumUtil.vandaag()).equals(waarde));
                } else {
                    final String actueel = "" + waarde;
                    Assert.isTrue(actueel.matches("" + gegeven.getWaarde()), gegeven.getVeld() + " was " + actueel);
                }
            } catch (final NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new UnsupportedOperationException("De getter van ProtocolleringOpdracht kan niet aangeroepen worden.", e);
            }
        });
    }

    @Override
    public void assertLaatstGeprotocolleerdePersonenMetWaarden(final List<GeprotocolleerdePersoon> geprotocolleerdePersonen) {
        final Leveringsaantekening leveringsaantekening = geefEnigeLeveringsaantekening();
        final List<LeveringsaantekeningPersoon> geleverdePersonen = new ArrayList<>(leveringsaantekening.getLeveringsaantekeningPersoonSet());
        int i = 0;
        for (final GeprotocolleerdePersoon geprotocolleerdePersoon : geprotocolleerdePersonen) {
            final String actueel = "" + geleverdePersonen.get(i++).getPersoon();
            Assert.isTrue(actueel.matches(geprotocolleerdePersoon.getPers()), geprotocolleerdePersoon.getPers() + " was " + actueel);
        }
    }


    @Override
    public void assertLaatstGeprotocolleerdeScopeElementen(final List<ScopeElement> scopeElementen) {
        final Leveringsaantekening opdracht = geefEnigeLeveringsaantekening();
        Assert.notNull(opdracht.getScopePatroon(), "Er zijn geen gescopete elementen gevonden in de protocolleringopdracht.");
        Assert.isTrue(opdracht.getScopePatroon().getScopePatroonElementSet().size() == scopeElementen.size(), "Aantal gescopete elementen komt niet overeen.");
        scopeElementen.forEach(scopeElement -> {
            final String element = scopeElement.getElement();
            final ElementObject elementObject = ElementHelper.getAttribuutElement(element);
            final AttribuutElement attribuutElement = (AttribuutElement) elementObject;
            org.junit.Assert.assertTrue(opdracht.getScopePatroon().getScopePatroonElementSet().stream().map(s -> s.getElementId()).collect(Collectors.toSet())
                    .contains(attribuutElement.getId()));
        });
    }

    @Override
    public void assertGeenScopeElementenGeprotocolleerd() {
        final Leveringsaantekening leveringsaantekening = geefEnigeLeveringsaantekening();
        Set<Integer>
                scopingAttributen = null;
        if (leveringsaantekening.getScopePatroon() != null) {
            scopingAttributen =
                    leveringsaantekening.getScopePatroon().getScopePatroonElementSet().stream().map(s -> s.getElementId()).collect(Collectors.toSet());
        }
        Assert.isTrue(scopingAttributen == null || scopingAttributen.isEmpty(), "Er zijn wel scoping attributen geprotocolleerd.");
    }

    @Override
    public void reset() {
        protocolleringStubService.reset();
    }

    private Leveringsaantekening geefEnigeLeveringsaantekening() {
        if (!protocolleringStubService.erIsGeprotocolleerd()) {
            throw new UnsupportedOperationException("Er is helemaal niet geprotocolleerd.");
        }
        final List<Leveringsaantekening> leveringsaantekeningen = protocolleringStubService.getLeveringsaantekeningen();
        if (leveringsaantekeningen.size() > 1) {
            throw new UnsupportedOperationException("Er zijn meer dan één protocolleringopdrachten gevonden.");
        }
        return leveringsaantekeningen.get(0);
    }
}
