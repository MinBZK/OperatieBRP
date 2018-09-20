/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import static nl.bzk.brp.testdatageneratie.RandomService.isPercent;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieDuur;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieDuurId;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieStartPlaats;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisBetrouderlijkgezag;
import nl.bzk.brp.testdatageneratie.domain.kern.HisBetrouderschap;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtrelatie;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;


public class AbstractRelatieGenerator {

    public AbstractRelatieGenerator() {
        super();
    }

    protected void opslaanBetrokkenheidMetHistorie(final Session kernSession, final Betr betr) {
        kernSession.save(betr);
        kernSession.save(new HisBetrouderlijkgezag(betr));
        kernSession.save(new HisBetrouderschap(betr));
    }

    protected Relatie creeerRelatie(final Srtrelatie srtrelatie) {
        Relatie relatie = new Relatie();

        relatie.setSrtrelatie(srtrelatie);

        Locatie locatieAanvang = BronnenRepo.getBron(RelatieStartPlaats.class).getLocatie();
        RelatieDuurId relatiePeriode = BronnenRepo.getBron(RelatieDuur.class).getId();
        relatie.setLandByLandaanv(locatieAanvang.getLand());
        relatie.setPartijByGemaanv(locatieAanvang.getPartij());
        relatie.setDataanv(relatiePeriode.getStart());
        if (locatieAanvang.isNederland()) {
            // NL
            relatie.setPlaatsByWplaanv(RandomService.getWplGeboorte());
        } else if (locatieAanvang.getLandCode() == Locatie.LAND_CODE_ONBEKEND) {
            //Land onbekend, gebruik omschrijving:
            relatie.setOmslocaanv(locatieAanvang.getPlaats());
        } else {
            // Buitenland
            relatie.setBlplaatsaanv(locatieAanvang.getPlaats());
            if (isPercent(10)) {
                relatie.setBlregioaanv(RandomStringUtils.randomAlphabetic(35));
            }
        }

        if (relatiePeriode.getEind() != -1) {
            Locatie locatieEinde = BronnenRepo.getBron(RelatieStartPlaats.class).getLocatie();
            relatie.setLandByLandeinde(locatieEinde.getLand());
            relatie.setPartijByGemeinde(locatieEinde.getPartij());
            relatie.setDateinde(relatiePeriode.getEind());
            relatie.setRdnbeeindrelatie(RandomService.getRedenEindeRelatie());
            if (locatieEinde.isNederland()) {
                // NL
                relatie.setPlaatsByWpleinde(RandomService.getWplGeboorte());
            } else if (locatieAanvang.getLandCode() == Locatie.LAND_CODE_ONBEKEND) {
                //Land onbekend, gebruik omschrijving:
                relatie.setOmsloceinde(locatieAanvang.getPlaats());
            } else {
                // Buitenland
                relatie.setBlplaatseinde(locatieEinde.getPlaats());
                if (isPercent(10)) {
                    relatie.setBlregioeinde(RandomStringUtils.randomAlphabetic(35));
                }
            }
        }
        return relatie;
    }

}
