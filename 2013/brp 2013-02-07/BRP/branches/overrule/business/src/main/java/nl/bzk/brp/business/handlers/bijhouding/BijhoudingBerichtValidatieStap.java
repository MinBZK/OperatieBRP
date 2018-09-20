/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.Arrays;
import java.util.List;


import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In deze stap wordt het bericht gevalideerd .
 */
public class BijhoudingBerichtValidatieStap extends AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht,
        BerichtResultaat>
{

    private static final Logger LOGGER                  = LoggerFactory.getLogger(BijhoudingBerichtValidatieStap.class);

    // voorlopig is dit hier gedefinieerd, uiteindelijk moet dit naar de xml configuratie.
    private static final List<String> WHITELIST = Arrays.asList(
           MeldingCode.BRBY0106.getNaam(), MeldingCode.BRAL9003.getNaam()
    );

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
                                                     final BerichtContext context,
                                                     final BerichtResultaat resultaat)
    {
        testVoorOverruleMeldingen(bericht, context, resultaat);
        return DOORGAAN_MET_VERWERKING;
    }


    /**
     * Doe een generieke validatie dat de overrule meldingen in de white list zitten.
     * @param bericht het bericht
     * @param context de context
     * @param resultaat het uiteindelijk resultaat berichten.
     */
    private void testVoorOverruleMeldingen(final AbstractBijhoudingsBericht bericht,
            final BerichtContext context,
            final BerichtResultaat resultaat)
    {
        if (bericht.getOverruledMeldingen() != null && !bericht.getOverruledMeldingen().isEmpty()) {
            for (OverruleMelding m : bericht.getOverruledMeldingen()) {
                if (StringUtils.isBlank(m.getCode())) {
                    resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                            "Code is verplicht voor overrule."));
                } else if (!WHITELIST.contains(m.getCode())) {
                    // test in de whitelist.
                    String msg = String.format("code %s mag niet overruled worden.", m.getCode());
                    resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001, msg));
                    LOGGER.warn(msg);
                }
            }
        }
    }
}
