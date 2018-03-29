/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.vrijbericht;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.delivery.vrijbericht.VrijBerichtCallbackImpl;
import nl.bzk.brp.delivery.vrijbericht.VrijBerichtWebService;
import nl.bzk.brp.service.vrijbericht.VrijBerichtBericht;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerwerker;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerzoek;
import nl.bzk.brp.tooling.apitest.autorisatie.Partijen;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import org.springframework.stereotype.Service;

/**
 * VerstuurBerichtApiServiceImpl.
 */
@Service
public final class VerstuurVrijBerichtApiServiceImpl implements VerstuurVrijBerichtApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerzoekService verzoekService;
    @Inject
    private VrijBerichtVerwerker vrijBerichtVerwerker;
    @Inject
    private VrijBerichtWebService webserviceProvider;


    @Override
    public void verzoekVerstuurVrijBericht(final Map<String, String> map) {
        LOGGER.info("Verzoek: Vrij Bericht: " + map);
        final VrijBerichtVerzoek verzoek = new VrijBerichtVerzoek();

        vulVerzoek(map, verzoek);

        final VrijBerichtCallbackImpl callback = new VrijBerichtCallbackImpl();
        vrijBerichtVerwerker.stuurVrijBericht(verzoek, callback);
        verzoekService.bewaarVerzoekAntwoord(verzoek, callback.getBerichtResultaat());
    }

    private void vulVerzoek(final Map<String, String> map, final VrijBerichtVerzoek verzoek) {
        VerzoekService.vulGeneriek(verzoek, map);
        final VrijBerichtBericht vrijBericht = new VrijBerichtBericht();
        verzoek.setVrijBericht(vrijBericht);

        if (map.containsKey(VrijBerichtKeys.ZENDER_VRIJBERICHT)) {
            final Partij partij = Partijen.getPartij(map.get(VrijBerichtKeys.ZENDER_VRIJBERICHT));
            verzoek.getParameters().setZenderVrijBericht(String.valueOf(partij.getCode()));
        }
        if (map.containsKey(VrijBerichtKeys.ONTVANGER_VRIJBERICHT)) {
            final Partij partij = Partijen.getPartij(map.get(VrijBerichtKeys.ONTVANGER_VRIJBERICHT));
            verzoek.getParameters().setOntvangerVrijBericht(String.valueOf(partij.getCode()));
        }
        if (map.containsKey(VrijBerichtKeys.SOORTNAAM)) {
            vrijBericht.setSoortNaam(map.get(VrijBerichtKeys.SOORTNAAM));
        }
        if (map.containsKey(VrijBerichtKeys.INHOUD)) {
            vrijBericht.setInhoud(map.get(VrijBerichtKeys.INHOUD));
        }

    }

    @Override
    public void verstuurVrijBericht(final XmlVerzoek xmlVerzoek) {
        verzoekService.xmlVerzoek(xmlVerzoek, webserviceProvider, null);
    }
}
