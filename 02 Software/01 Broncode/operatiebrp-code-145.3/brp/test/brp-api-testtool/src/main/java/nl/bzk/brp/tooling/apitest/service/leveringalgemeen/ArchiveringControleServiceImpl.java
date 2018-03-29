/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.service.afnemerindicatie.AfnemerindicatieVerzoek;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.algemeen.request.Verzoek;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import nl.bzk.brp.test.common.xml.XPathHelper;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * ArchiveringControleService implementatie.
 */
public final class ArchiveringControleServiceImpl implements ArchiveringControleService {

    @Inject
    private VerzoekService verzoekService;
    @Inject
    private LeveringsautorisatieService toegangLeveringsautorisatieService;
    @Inject
    private ArchiveringStubService archiveringStubService;
    @Inject
    private XPathHelper xPathHelper;

    @Override
    public void assertLaatsteVerzoekCorrectGearchiveerd() {

        final Verzoek laatsteVerzoek = verzoekService.getLaatsteVerzoek();
        final List<Bericht> archiveringOpdrachtList = archiveringStubService.getBerichten();

        Assert.isTrue(archiveringStubService.erIsGearchiveerd(), "Er is niet gearchiveerd");
        Assert.isTrue(archiveringOpdrachtList.size() == 2,
                "Enkel één Ingaand en één Uitgaand bericht verwacht, gevonden aantal = " + archiveringOpdrachtList.size());

        // check aanwezigheid ingaand bericht
        final Bericht ingaandeOpdracht = archiveringOpdrachtList.stream()
                .filter(opdracht -> opdracht.getRichting() == Richting.INGAAND).findFirst().get();

        // check aanwezigheid uitgaand bericht
        final Bericht uitgaandeOpdracht = archiveringOpdrachtList.stream()
                .filter(opdracht -> opdracht.getRichting() == Richting.UITGAAND).findFirst().get();

        // check referentienummer en crossreferentienummer
        Assert.isTrue(StringUtils.isNotBlank(ingaandeOpdracht.getReferentieNummer()), "Referentienummer ingaand bericht is leeg");
        Assert.isTrue(StringUtils.equals(uitgaandeOpdracht.getCrossReferentieNummer(), ingaandeOpdracht.getReferentieNummer()),
                "CrossReferentienummer uitgaand bericht niet gelijk aan referentienummer ingaand bericht");
        Assert.isTrue(StringUtils.isNotBlank(uitgaandeOpdracht.getReferentieNummer()), "Referentienummer uitgaand bericht is leeg");
        Assert.isTrue(!StringUtils.equals(ingaandeOpdracht.getReferentieNummer(), uitgaandeOpdracht.getReferentieNummer()),
                "Referentienummer kunnen niet gelijk zijk");
        xPathHelper.assertWaardeGelijk(uitgaandeOpdracht.getData(), "//brp:referentienummer", uitgaandeOpdracht.getReferentieNummer());
        xPathHelper.assertWaardeGelijk(uitgaandeOpdracht.getData(), "//brp:crossReferentienummer", uitgaandeOpdracht.getCrossReferentieNummer());

        //controleer autorisatie en dienst

        String verzoekLeveringautorisatieId = null;
        if (laatsteVerzoek instanceof SynchronisatieVerzoek) {
            verzoekLeveringautorisatieId = ((SynchronisatieVerzoek) laatsteVerzoek).getParameters().getLeveringsAutorisatieId();
        } else if (laatsteVerzoek instanceof AfnemerindicatieVerzoek) {
            verzoekLeveringautorisatieId = ((AfnemerindicatieVerzoek) laatsteVerzoek).getParameters().getLeveringsAutorisatieId();
        } else if (laatsteVerzoek instanceof BevragingVerzoek) {
            verzoekLeveringautorisatieId = ((BevragingVerzoek) laatsteVerzoek).getParameters().getLeveringsAutorisatieId();
        }
        Assert.notNull(verzoekLeveringautorisatieId, "LeveringautorisatieId leeg");
        Assert.isTrue(StringUtils.equals(verzoekLeveringautorisatieId, ingaandeOpdracht.getLeveringsAutorisatie().toString()),
                "Leveringautorisatie ingaand bericht incorrect");
        Assert.isTrue(StringUtils.equals(verzoekLeveringautorisatieId, uitgaandeOpdracht.getLeveringsAutorisatie().toString()),
                "Leveringautorisatie uitgaand bericht incorrect");

        final Autorisatiebundel autorisatiebundel = geefAutorisatiebundel(Integer.parseInt(verzoekLeveringautorisatieId),
                laatsteVerzoek.getStuurgegevens().getZendendePartijCode(), laatsteVerzoek.getSoortDienst());
        Assert.isTrue(autorisatiebundel.getDienst().getId().equals(ingaandeOpdracht.getDienst()), "Dienst incorrect");
        Assert.isNull(uitgaandeOpdracht.getDienst());

        // tijdstipverzending moet actueel zijn
        if (System.currentTimeMillis() - uitgaandeOpdracht.getDatumTijdVerzending().toInstant().toEpochMilli() > 1000 * 60 * 60) {
            throw new AssertionError("Tijdstip ontvangst niet actueel (verschil groter dan 1 uur!!)");
        }
    }

    private Autorisatiebundel geefAutorisatiebundel(final int leveringsautorisatieID, final String partijCode, final SoortDienst soortDienst) {
        final ToegangLeveringsAutorisatie la = toegangLeveringsautorisatieService
                .geefToegangLeveringsAutorisatie(leveringsautorisatieID, partijCode);
        Assert.notNull(la);
        final Dienst dienstMetSoort = AutAutUtil.zoekDienst(la.getLeveringsautorisatie(), soortDienst);
        Assert.notNull(dienstMetSoort);
        return new Autorisatiebundel(la, dienstMetSoort);
    }

    @Override
    public void assertMutatieleveringCorrectGearchiveerd() {

    }

    @Override
    public void reset() {
        archiveringStubService.reset();
    }
}
