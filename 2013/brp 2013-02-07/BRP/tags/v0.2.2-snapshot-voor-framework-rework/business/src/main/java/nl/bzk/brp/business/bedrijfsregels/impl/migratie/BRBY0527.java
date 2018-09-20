/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;


/**
 * Implementatie van bedrijfsregel BRBY0427.
 * <p/>
 * Waarde van gemeentecode moet geldig zijn in stamgegeven "Gemeente" vanaf datum aanvang geldigheid tot datum einde
 * geldigheid van het adres.
 * <p/>
 *
 * @brp.bedrijfsregel BRBY0427
 */
public class BRBY0527 implements ActieBedrijfsRegel<Persoon> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0527.class);

    @Override
    public String getCode() {
        return "BRBY0527";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie, final Actie actie) {
        List<Melding> meldingen = Collections.emptyList();

        if (CollectionUtils.isEmpty(nieuweSituatie.getAdressen())) {
            LOGGER.warn("Bedrijfsregel BRBY0527 aangeroepen zonder adressen. "
                + "Bedrijfsregel wordt daarom niet verder uitgevoerd (geen meldingen naar gebruiker).");
        } else {
            for (PersoonAdres adres : nieuweSituatie.getAdressen()) {
                Partij gemeente = adres.getGegevens().getGemeente();
                if (!DatumUtil.isDatumsGeldigOpPeriode(gemeente.getDatumAanvang(), gemeente.getDatumEinde(),
                        actie.getDatumAanvangGeldigheid(), actie.getDatumEindeGeldigheid()))
                {
                    meldingen =
                        Collections.singletonList(new Melding(Soortmelding.OVERRULEBAAR, MeldingCode.BRBY0527,
                                String.format(MeldingCode.BRBY0527.getOmschrijving(), adres.getGegevens().getGemeente()
                                        .getGemeentecode()), (Identificeerbaar) adres, "gemeenteCode"));
                } else {
                    meldingen = Collections.emptyList();
                }
            }
        }
        return meldingen;
    }
}
