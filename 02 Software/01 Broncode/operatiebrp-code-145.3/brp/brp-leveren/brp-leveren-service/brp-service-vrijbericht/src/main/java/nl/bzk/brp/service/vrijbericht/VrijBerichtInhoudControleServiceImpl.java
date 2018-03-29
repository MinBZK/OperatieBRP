/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.stamgegevens.StamTabelService;
import org.springframework.stereotype.Service;

/**
 * UCS VB.1.AV.CI implementatie – Controleer inhoud van het vrij bericht.
 */
@Bedrijfsregel(Regel.R2472)
@Bedrijfsregel(Regel.R2473)
@Service
final class VrijBerichtInhoudControleServiceImpl implements VrijBerichtInhoudControleService {

    @Inject
    private StamTabelService stamTabelService;

    private VrijBerichtInhoudControleServiceImpl() {
    }

    @Override
    public void controleerInhoud(final VrijBerichtVerzoek verzoek) throws StapMeldingException {
        final Map<String, Object> soortVrijBerichtStamgegeven = bepaalSoortVrijBerichtStamgegeven(verzoek.getVrijBericht().getSoortNaam());
        if (soortVrijBerichtStamgegeven == null) {
            throw new StapMeldingException(Regel.R2472);
        } else if (!soortVrijBerichtIsGeldig(soortVrijBerichtStamgegeven)) {
            throw new StapMeldingException(Regel.R2473);
        }
    }

    //controleer of het soort vrij bericht bestaat (R2472)
    private Map<String, Object> bepaalSoortVrijBerichtStamgegeven(final String soortNaam) {
        final StamtabelGegevens stamtabelGegevens = stamTabelService.geefStamgegevens(Element.SOORTVRIJBERICHT.getNaam() + StamtabelGegevens.TABEL_POSTFIX);
        final List<Map<String, Object>> gegevens = stamtabelGegevens.getStamgegevens();
        for (final Map<String, Object> gegeven : gegevens) {
            final Object naam = gegeven.get("naam");
            if (naam != null && soortNaam.equals(naam.toString())) {
                return gegeven;
            }
        }
        return null;
    }

    //controleer of het soort vrij bericht geldig is (R2473)
    private static boolean soortVrijBerichtIsGeldig(final Map<String, Object> gegeven) {
        final Integer datAanvGeldigheid = (Integer) gegeven.get("dataanvgel");
        final Integer datEindeGeldigheid = (Integer) gegeven.get("dateindegel");
        return beginDatumCorrect(datAanvGeldigheid) && eindDatumCorrect(datEindeGeldigheid);
    }

    private static boolean eindDatumCorrect(final Integer datEindeGeldigheid) {
        return datEindeGeldigheid == null || datEindeGeldigheid > DatumUtil.vandaag();
    }

    private static boolean beginDatumCorrect(final Integer datAanvGeldigheid) {
        return datAanvGeldigheid == null || datAanvGeldigheid <= DatumUtil.vandaag();
    }
}
