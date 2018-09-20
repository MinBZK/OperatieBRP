/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verantwoordelijke;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;


/**
 *
 * De persoon van wie gegevens worden bijgehouden moet een Ingezetene zijn (BRAL0106: Persoon is Ingezetene), tenzij er
 * sprake is van een actie waardoor de eerste inschrijving van deze persoon plaatsvindt.
 *
 * @brp.bedrijfsregel BRAL0216
 * @brp.bedrijfsregel BRAL0106
 */
public class BRAL0216 implements ActieBedrijfsRegel<Persoon> {

    @Override
    public String getCode() {
        return "BRBY0216";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie, final Actie actie) {

        List<Melding> meldingen = new ArrayList<Melding>();

        // TODO: dit zou eventueel verplaats kunnen worden naar de persoon, maar moet nog besproken worden hoe dit te
        // verwerken in de interface
        // en of het zit vol is om in PersoonModel en PersoonBericht te implementeren.
        // Potentieel is dit @brp.bedrijfsregel BRAL0106: Persoon is Ingezetene, in afwachting van verheldering Erwin en
        // Jeroen
        if (huidigeSituatie != null && huidigeSituatie.getBijhoudingsverantwoordelijkheid() != null) {
            if (!(huidigeSituatie.getSoort() == SoortPersoon.INGESCHREVENE && huidigeSituatie
                    .getBijhoudingsverantwoordelijkheid().getVerantwoordelijke() == Verantwoordelijke.COLLEGE))
            {
                meldingen.add(new Melding(Soortmelding.FOUT, MeldingCode.BRAL0216, (Identificeerbaar) nieuweSituatie,
                        "burgerservicenummer"));
            }
        }

        return meldingen;
    }
}
