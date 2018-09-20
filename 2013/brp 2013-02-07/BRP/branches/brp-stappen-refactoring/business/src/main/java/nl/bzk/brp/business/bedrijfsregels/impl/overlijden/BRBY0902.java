/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * De datum overlijden mag niet voor de datum eerste inschrijving liggen (en mag ook niet in de toekomst liggen),
 * Vergelijken gedeeltelijk onbekende datums toe.
 *
 * @brp.bedrijfsregel BRBY0902
 */
public class BRBY0902 implements ActieBedrijfsRegel<Persoon> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActieBedrijfsRegel.class);

    @Override
    public String getCode() {
        return "BRBY0902";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie, final Actie actie) {

        final List<Melding> meldingen = new ArrayList<Melding>();

        if (nieuweSituatie == null) {
            LOGGER.warn("Bedrijfsregel BRBY0902 aangeroepen met null waarde voor nieuwe situatie. Bedrijfsregel "
                + "daarom niet verder uitgevoerd (geen meldingen naar gebruiker).");
        } else {
            if (null != huidigeSituatie && null != huidigeSituatie.getInschrijving()
                && (null != huidigeSituatie.getInschrijving().getDatumInschrijving())
                && (null != nieuweSituatie.getOverlijden())
                && (null != nieuweSituatie.getOverlijden().getDatumOverlijden())
                )
            {
                final Datum datumInschrijving = huidigeSituatie.getInschrijving().getDatumInschrijving();
                final Datum datumOverlijden = nieuweSituatie.getOverlijden().getDatumOverlijden();

                // TODO: bolie, nieuwe regel van vergelijken moeten toepassen (veel flexibeler dan nu)
                // bv. inschrijving 1980 05 05, overlijden = 1980 05 00  => goed (omdat we alleen t/m maand mogen
                // meegnemen)
                // nu is dit fout (1980 05 05 > 1980 05 00)
                // de method voor, na etc, moeten herziend worden.
                if (datumOverlijden.voor(datumInschrijving) || datumOverlijden.na(DatumUtil.vandaag())) {
                    meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.BRBY0902,
                        (Identificeerbaar) nieuweSituatie.getOverlijden(), "datum"));
                }
            }
        }
        return meldingen;
    }
}

