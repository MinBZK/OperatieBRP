/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.service;

import javax.inject.Inject;

import nl.bzk.brp.business.levering.LEVLeveringBijgehoudenPersoonLv;
import nl.bzk.brp.business.levering.LEVLeveringBijgehoudenPersoonLvAntwoord;
import nl.bzk.brp.business.levering.LeveringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Deze uitgaande berichten verwerker zet het bericht op de uitgaande reliable messaging queue (eBMS of WS-RM)
 */

@Service
public class MutatieBerichtVerwerkerImpl implements MutatieBerichtVerwerker {

    private static final Logger  LOGGER = LoggerFactory.getLogger(MutatieBerichtVerwerkerImpl.class);

    @Inject
    LeveringService              leveringService;

    @Inject
    private BerichtFilterService berichtFilterService;

    @Override
    public boolean verwerkBericht(final LEVLeveringBijgehoudenPersoonLv bericht, final Long berichtId) {

        if (bericht.getAdministratieveHandeling() != null
            && bericht.getAdministratieveHandeling().getLeveringen().getLevering().getBijhouding().getPersoon()
                    .getAdressen().getAdres() != null)
        {
            //LEVLeveringBijgehoudenPersoonLvAntwoord antwoordBericht =
            //    leveringService.verzendMutatieBericht(bericht, "http://localhost:8088/mockLeveringBinding");

            LEVLeveringBijgehoudenPersoonLvAntwoord antwoordBericht =
                    leveringService.verzendMutatieBericht(bericht, "http://localhost:8082/brp/services/levering");
//
//            if (antwoordBericht.getAntwoord().getAntwoord().equals(AntwoordInner.GOED)) {
//                Adres adres =
//                    bericht.getAdministratieveHandeling().getLeveringen().getLevering().getBijhouding().getPersoon()
//                            .getAdressen().getAdres();

                LOGGER.info("Bericht van soort: \""
                    + bericht.getAdministratieveHandeling().getSoortNaam().getSoortNaam()
                    + "\" is verstuurd naar afnemer "
                    + bericht.getStuurgegevens().getOntvanger().getOrganisatie().getOrganisatie() + "\nXML inhoud: "
                    + berichtFilterService.verkrijgXmlVanBericht(bericht));
                return true;
//            } else if (antwoordBericht.getAntwoord().getAntwoord().equals(AntwoordInner.FOUT)) {
//                LOGGER.info("Bericht van soort: \""
//                    + bericht.getAdministratieveHandeling().getSoortNaam().getSoortNaam()
//                    + "\" is NIET!!!! verstuurd naar afnemer "
//                    + bericht.getStuurgegevens().getOntvanger().getOrganisatie().getOrganisatie() + "\nXML inhoud: "
//                    + berichtFilterService.verkrijgXmlVanBericht(bericht));
//            }
        }
        return false;
    }
}
