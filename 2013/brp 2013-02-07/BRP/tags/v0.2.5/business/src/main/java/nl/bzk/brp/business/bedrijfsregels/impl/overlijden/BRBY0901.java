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
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenOpschorting;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * BRBY0901: Verwerken Groep Overlijden.
 *
 * @brp.bedrijfsregel BRBY0901
 */
public class BRBY0901 implements ActieBedrijfsRegel<Persoon> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0901.class);

    @Override
    public String getCode() {
        return "BRBY0901";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie,
                                   final Actie actie)
    {
        final List<Melding> meldingen;

        if (nieuweSituatie == null) {
            LOGGER.warn("Bedrijfsregel BRBY0901 aangeroepen met null waarde voor nieuwe situatie. Bedrijfsregel "
                                + "daarom niet verder uitgevoerd (geen meldingen naar gebruiker).");
            meldingen = new ArrayList<Melding>();
        } else {
            meldingen = controleerOverlijden(huidigeSituatie);
        }

        return meldingen;

    }

    /**
     * .
     * @param persoon .
     * @return .
     */
    private List<Melding> controleerOverlijden(final Persoon persoon) {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (persoon.getOpschorting() != null
            && RedenOpschorting.OVERLIJDEN.equals(persoon.getOpschorting().getRedenOpschorting()))
        {
            meldingen.add(new Melding(Soortmelding.FOUT, MeldingCode.BRBY0901,
                    (Identificeerbaar) persoon.getOverlijden(), "datum"));
        }

        return meldingen;
    }
}
