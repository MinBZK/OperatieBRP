/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Het land overlijden moet verwijzen naar een geldig voorkomen in stamgegeven "Land" op peildatum datum overlijden.
 *
 * @brp.bedrijfsregel BRBY0904
 *
 */
public class BRBY0904 implements ActieBedrijfsRegel<Persoon> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0904.class);

    @Override
    public String getCode() {
        return "BRBY0904";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie, final Actie actie) {
        List<Melding> meldingen = Collections.emptyList();

        if (nieuweSituatie.getOverlijden() == null || nieuweSituatie.getOverlijden().getLandOverlijden() == null) {
            LOGGER.warn("Bedrijfsregel BRBY0904 aangeroepen zonder land overlijden. "
                + "Bedrijfsregel wordt daarom niet verder uitgevoerd (geen meldingen naar gebruiker).");
        } else {

            Land land = nieuweSituatie.getOverlijden().getLandOverlijden();
            if (!DatumUtil.isGeldigOp(land.getDatumAanvang(), land.getDatumEinde(),
                    nieuweSituatie.getOverlijden().getDatumOverlijden()))
            {
                meldingen =
                    Collections.singletonList(new Melding(Soortmelding.FOUT, MeldingCode.BRBY0904, MeldingCode.BRBY0904
                            .getOmschrijving(), (Identificeerbaar) nieuweSituatie.getOverlijden(), "landCode"));
            } else {
                meldingen = Collections.emptyList();
            }

        }
        return meldingen;
    }
}
