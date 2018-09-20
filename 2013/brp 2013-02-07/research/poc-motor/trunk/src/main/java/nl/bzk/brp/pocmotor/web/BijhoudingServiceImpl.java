/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.web;

import java.util.List;

import javax.inject.Inject;
import javax.jws.WebService;

import nl.bzk.brp.pocmotor.bedrijfsregels.BedrijfsRegelFout;
import nl.bzk.brp.pocmotor.bedrijfsregels.BedrijfsRegelFoutErnst;
import nl.bzk.brp.pocmotor.model.Bericht;
import nl.bzk.brp.pocmotor.model.BerichtResultaat;
import nl.bzk.brp.pocmotor.service.BerichtVerwerker;

/**
 * Implementatie van de Bijhouding Service.
 */
@WebService
public class BijhoudingServiceImpl implements BijhoudingService {

    @Inject
    private BerichtVerwerker berichtVerwerker;

    @Override
    public BerichtResultaat bijhouden(final Bericht bericht) {
        List<BedrijfsRegelFout> fouten = berichtVerwerker.verwerkBericht(bericht);

        String resultaatBericht = bepaalResultaatBericht(fouten);
        return new BerichtResultaat(resultaatBericht, fouten);
    }

    /**
     * Bepaalt het resultaat bericht aan de hand van zwaarst opgetreden fouten.
     *
     * @param fouten de opgetreden fouten.
     * @return een resultaat bericht.
     */
    private String bepaalResultaatBericht(final List<BedrijfsRegelFout> fouten) {
        BedrijfsRegelFoutErnst zwaarst = null;

        for (BedrijfsRegelFout fout : fouten) {
            BedrijfsRegelFoutErnst ernst = fout.getErnst();
            if (zwaarst == null) {
                zwaarst = ernst;
            } else {
                if (ernst.ordinal() > zwaarst.ordinal()) {
                    zwaarst = ernst;
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();

        if (zwaarst != null) {
            switch (zwaarst) {
                case INFO:
                    sb.append("Alles is goed gegaan en controleer nog voor de zekerheid de meegestuurde info.");
                    break;
                case WAARSCHUWING:
                    sb.append("De bijhouding is doorgevoerd, maar er zijn wel enkele waarschuwingen.");
                    break;
                case ZACHTE_FOUT:
                    sb.append("De bijhouding is niet verwerkt vanwege opgetreden (overrulebare) fouten.");
                    break;
                case HARDE_FOUT:
                    sb.append("De bijhouding is niet verwerkt vanwege opgetreden (onoverrulebare) fouten.");
                    break;
            }
        } else {
            sb.append("Alles is goed gegaan.");
        }
        return sb.toString();
    }
}
