/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.pocmotor.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.pocmotor.bedrijfsregels.OpaOmaArtikel;
import nl.bzk.brp.pocmotor.bedrijfsregels.VerhuizingNaarHetzelfdeAdres;
import nl.bzk.brp.pocmotor.bedrijfsregels.VoornaamGewenst;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortActie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;
import org.springframework.stereotype.Service;

/**
 * Standaard implementatie van de {@link BedrijfsRegelManager} interface. Deze implementatie geeft op dit moment een
 * standaard lijst van bedrijfsregels en dus niet uitgelezen uit de database.
 */
@Service
public class BedrijfsRegelManagerImpl implements BedrijfsRegelManager {

    private Map<SoortActie, List<? extends BedrijfsRegel>> regelsMap;

    public BedrijfsRegelManagerImpl() {
        regelsMap = new HashMap<SoortActie, List<? extends BedrijfsRegel>>();
        regelsMap.put(SoortActie.AANGIFTE_GEBOORTE, Arrays.asList(new OpaOmaArtikel(),
                new VoornaamGewenst()));
        regelsMap.put(SoortActie.VERHUIZING, Arrays.asList(new VerhuizingNaarHetzelfdeAdres()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends BedrijfsRegel> getLijstVanBedrijfsRegels(final BRPActie actie) {
        if (!regelsMap.containsKey(actie.getIdentiteit().getSoort())) {
            return new ArrayList<BedrijfsRegel>();
        }
        return regelsMap.get(actie.getIdentiteit().getSoort());
    }

}
