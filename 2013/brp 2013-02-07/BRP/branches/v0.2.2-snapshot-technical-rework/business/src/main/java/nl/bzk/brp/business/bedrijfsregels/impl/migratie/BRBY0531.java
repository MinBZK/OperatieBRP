/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;


/**
 * Waarde van woonplaatscode moet geldig zijn in stamgegeven "Woonplaats"
 * vanaf datum aanvang geldigheid tot datum einde geldigheid van het adres.
 *
 * @brp.bedrijfsregel BRBY0531
 *
 */
public class BRBY0531 implements ActieBedrijfsRegel<Persoon> {

    @Override
    public String getCode() {
        return "BRBY0531";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie, final Actie actie) {
        List<Melding> meldingen = new ArrayList<Melding>();
        Datum aanvang = actie.getDatumAanvangGeldigheid();
        Datum einde = actie.getDatumEindeGeldigheid();
        if (null != nieuweSituatie && null != nieuweSituatie.getAdressen()) {
            for (PersoonAdres adres : nieuweSituatie.getAdressen()) {
                if (null != adres.getGegevens().getWoonplaats()) {
                    Plaats plaats = adres.getGegevens().getWoonplaats();
                    boolean geldig =
                        DatumUtil.isDatumsGeldigOpPeriode(plaats.getDatumAanvang(), plaats.getDatumEinde(), aanvang,
                                einde);
                    if (!geldig) {
                        meldingen.add(new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.BRBY0531, String.format(
                                MeldingCode.BRBY0531.getOmschrijving(), adres.getGegevens().getWoonplaats().getCode()),
                                (Identificeerbaar) adres, "woonplaatsCode"));
                    }
                }
            }
        }
        return meldingen;
    }
}
