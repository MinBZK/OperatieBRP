/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
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

    protected Relatie creeerRelatie(final Srtrelatie srtrelatie, final Locatie locatieAanvang, final int datumAanvang) {
        Relatie relatie = new Relatie();

        relatie.setSrtrelatie(srtrelatie);

        relatie.setLandByLandaanv(locatieAanvang.getLand());
        relatie.setPartijByGemaanv(locatieAanvang.getPartij());
        relatie.setDataanv(datumAanvang);
        if (locatieAanvang.isNederland()) {
            // NL
            relatie.setPlaatsByWplaanv(RandomService.getWplGeboorte());
        } else if (locatieAanvang.getLandCode() == Locatie.LAND_CODE_ONBEKEND) {
            //Land onbekend, gebruik omschrijving:
            relatie.setOmslocaanv(locatieAanvang.getPlaats());
        } else {
            // Buitenland
            relatie.setBlplaatsaanv(locatieAanvang.getPlaats());
            if (RandomService.isFractie(10)) {
                relatie.setBlregioaanv(RandomStringUtils.randomAlphabetic(35));
            }
        }

        return relatie;
    }

    protected Locatie getRelatieStartPlaats() {
        Locatie locatie;
        do {
            locatie = BronnenRepo.getBron(RelatieStartPlaats.class).getLocatie();
        } while (locatie.getLand() == null);
        return locatie;
    }

}
