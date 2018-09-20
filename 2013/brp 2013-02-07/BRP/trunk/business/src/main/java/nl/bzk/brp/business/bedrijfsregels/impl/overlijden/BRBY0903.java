/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De gemeente overlijden moet verwijzen naar een geldig voorkomen in stamgegeven "Gemeente" op peildatum datum
 * overlijden.
 *
 * @brp.bedrijfsregel BRBY0903
 */
public class BRBY0903 implements ActieBedrijfsRegel<Persoon> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0903.class);

    @Override
    public String getCode() {
        return "BRBY0903";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie, final Actie actie) {
        List<Melding> meldingen = Collections.emptyList();

        if (nieuweSituatie.getOverlijden() == null || nieuweSituatie.getOverlijden().getGemeenteOverlijden() == null) {
            LOGGER.warn("Bedrijfsregel BRBY0903 aangeroepen zonder gemeente overlijden. "
                + "Bedrijfsregel wordt daarom niet verder uitgevoerd (geen meldingen naar gebruiker).");
        } else {

            Partij gemeente = nieuweSituatie.getOverlijden().getGemeenteOverlijden();
            if (!DatumUtil.isGeldigOp(gemeente.getDatumAanvang(), gemeente.getDatumEinde(),
                nieuweSituatie.getOverlijden().getDatumOverlijden()))
            {
                meldingen =
                    Collections.singletonList(new Melding(SoortMelding.FOUT, MeldingCode.BRBY0903,
                        (Identificeerbaar) nieuweSituatie.getOverlijden(), "gemeenteCode"));
            } else {
                meldingen = Collections.emptyList();
            }

        }
        return meldingen;
    }
}
