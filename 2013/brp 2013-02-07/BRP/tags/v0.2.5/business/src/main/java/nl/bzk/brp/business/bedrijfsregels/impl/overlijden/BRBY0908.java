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
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;


/**
 * Datum aanvang Geldigheid actie moet exact zelfde zijn als datum.
 *
 * @brp.bedrijfsregel BRBY0908
 *
 */
public class BRBY0908 implements ActieBedrijfsRegel<Persoon> {

    @Override
    public String getCode() {
        return "BRBY0908";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie, final Actie actie) {
        final List<Melding> meldingen = new ArrayList<Melding>();

        if (!actie.getDatumAanvangGeldigheid().equals(nieuweSituatie.getOverlijden().getDatumOverlijden())) {
            meldingen.add(new Melding(Soortmelding.FOUT, MeldingCode.BRBY0908, (Identificeerbaar) nieuweSituatie
                    .getOverlijden(), "datum"));
        }

        return meldingen;
    }
}
