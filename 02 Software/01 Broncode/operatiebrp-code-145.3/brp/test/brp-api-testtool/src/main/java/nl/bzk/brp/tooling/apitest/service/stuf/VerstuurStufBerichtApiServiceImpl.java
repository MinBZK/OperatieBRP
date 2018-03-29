/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.stuf;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.delivery.stuf.StufBerichtCallbackImpl;
import nl.bzk.brp.delivery.stuf.StufWebService;
import nl.bzk.brp.service.stuf.StufBericht;
import nl.bzk.brp.service.stuf.StufBerichtVerwerker;
import nl.bzk.brp.service.stuf.StufBerichtVerzoek;
import nl.bzk.brp.test.common.TestclientExceptie;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.AutorisatieStubService;
import org.springframework.stereotype.Service;

/**
 * VerstuurStufBerichtApiServiceImpl.
 */
@Service
public final class VerstuurStufBerichtApiServiceImpl implements VerstuurStufBerichtApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerzoekService verzoekService;
    @Inject
    @Named("maakLeveringsautorisatieRepository")
    private AutorisatieStubService autorisatieService;
    @Inject
    private StufBerichtVerwerker stufBerichtVerwerker;
    @Inject
    private StufWebService webserviceProvider;


    @Override
    public void verzoekVerstuurStufBericht(final Map<String, String> map) {
        LOGGER.info("Verzoek: Stuf Bericht: " + map);
        final StufBerichtVerzoek verzoek = new StufBerichtVerzoek();
        InputStream inputStream = this.getClass().getResourceAsStream("/stuf/stv_stvGeefStufBgBericht_Verzoek.xml");

        try {
            String result = CharStreams.toString(new InputStreamReader(
                    inputStream, Charsets.UTF_8));
            vulVerzoek(map, verzoek, result);

            final StufBerichtCallbackImpl callback = new StufBerichtCallbackImpl();
            stufBerichtVerwerker.verwerkVerzoek(verzoek, callback);
            verzoekService.bewaarVerzoekAntwoord(verzoek, callback.getBerichtResultaat());
        } catch (IOException e) {
            throw new TestclientExceptie(e);
        }
    }

    private void vulVerzoek(final Map<String, String> map, final StufBerichtVerzoek verzoek, final String inhoud) {
        VerzoekService.vulGeneriek(verzoek, map);
        final StufBericht stufBericht = new StufBericht(inhoud, map.get(StufKeys.SOORT_SYNCHRONISATIE));
        verzoek.setSoortDienst(SoortDienst.GEEF_STUF_BG_BERICHT);
        verzoek.setStufBericht(stufBericht);
        verzoek.getParameters().setVersieStufbg(map.get(StufKeys.VERSIE_NR_STUF));
        verzoek.getParameters().setVertalingBerichtsoortBRP(map.get(StufKeys.SOORT_BERICHT_NAAM));
        //vul dienst en leveringsautorisatie
        final String leveringsautorisatieId;
        if (map.containsKey(VerzoekService.LEVERING_AUTORISATIE_ID)) {
            leveringsautorisatieId = map.get(VerzoekService.LEVERING_AUTORISATIE_ID);
        } else {
            leveringsautorisatieId = autorisatieService.getLeveringsautorisatie(map.get(VerzoekService.LEVERING_AUTORISATIE_NAAM)).getId().toString();
        }
        verzoek.getParameters().setLeveringsAutorisatieId(leveringsautorisatieId);
    }

    @Override
    public void verstuurStufBericht(final XmlVerzoek xmlVerzoek) {
        verzoekService.xmlVerzoek(xmlVerzoek, webserviceProvider, SoortDienst.GEEF_STUF_BG_BERICHT);
    }
}
