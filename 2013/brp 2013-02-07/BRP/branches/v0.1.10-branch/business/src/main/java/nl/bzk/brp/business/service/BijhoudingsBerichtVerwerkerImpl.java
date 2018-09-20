/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Een verdere specificatie van de berichtverwerker, nu voor bijhouding.
 *
 */
public class BijhoudingsBerichtVerwerkerImpl extends BerichtVerwerkerImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BijhoudingsBerichtVerwerkerImpl.class);

    @Override
    protected boolean correctiesNaStap(final boolean stapResultaat, final BRPBericht bericht,
            final BerichtContext context, final BerichtResultaat resultaat)
    {
        if (stapResultaat != AbstractBerichtVerwerkingsStap.STOP_VERWERKING && bericht instanceof BijhoudingsBericht) {
            verwerkOverrulbareRegels((BijhoudingsBericht) bericht, context, resultaat);
        }
        return stapResultaat;
    }

    /**
     * Corrigeer alle fouten van het type OVERRULEBAAR, met deze voorwaarden:
     * - alle overrulebare fouten zijn gespecificeerd in de lijst met overrulemeldingen.
     * - het aantal is identiek (maw. alle fouten moeten ook daadwerkelijk geconstateerd zijn).
     *
     * @param bericht het volledig bericht
     * @param context de context
     * @param resultaat de lijst met meldingen.
     */
    private void verwerkOverrulbareRegels(final BijhoudingsBericht bericht, final BerichtContext context,
        final BerichtResultaat resultaat)
    {
        if (CollectionUtils.isNotEmpty(bericht.getOverruledMeldingen())) {
            List<OverruleMelding> teVerwijderenOverrMeldingen = new ArrayList<OverruleMelding>();
            List<Melding> teVerwijderenMeldingen = new ArrayList<Melding>();

            if (resultaat.bevatVerwerkingStoppendeFouten()) {
                List<Melding> resultaatMeldingen = resultaat.getMeldingen();
                for (Melding melding : resultaatMeldingen) {
                    if (melding.getSoort() == SoortMelding.FOUT_OVERRULEBAAR) {
                        // gevondenMelding is het object in de overrulebareMeldingen lijst.
                        OverruleMelding gevondenMelding = isMeldingOverruled(bericht.getOverruledMeldingen(), melding);
                        if (gevondenMelding != null) {
                            teVerwijderenOverrMeldingen.add(gevondenMelding);
                            teVerwijderenMeldingen.add(melding);
                            gevondenMelding.setOmschrijving(melding.getOmschrijving());
                            // wat doen we met de attribuutnaam? de foutmelding copieren we met die van de
                            // geconstateerde fout
                        }
                    }
                }
                bericht.getOverruledMeldingen().removeAll(teVerwijderenOverrMeldingen);
                resultaat.overruleAlleOverrulebareFouten(teVerwijderenMeldingen);
                resultaat.voegtoeOverruleMeldingen(teVerwijderenOverrMeldingen);
                logAlleMeldingenDieZijnOverruled(teVerwijderenOverrMeldingen, context.getIngaandBerichtId());
            }
        }
    }


    /**
     * Melding is gevonden als de meldingscode gelijk is en het verzendendId gelijk is (beide leeg/null) of
     * beide dezelfde waarde.
     *
     * @param overrulebareMeldingen lijst van overrulbare meldingen.
     * @param melding de geconstateerde melding.
     * @return de gevonden overrulebare melding uit de lijst (of null als niet gevonden).
     */
    private OverruleMelding isMeldingOverruled(final List<OverruleMelding> overrulebareMeldingen,
        final Melding melding)
    {

        LOGGER.debug("Gevonden Melding: verz=[{}], code=[{}], attr=[{}]", new Object[]{ melding.getVerzendendId(),
            melding.getCode().getNaam(), melding.getAttribuutNaam() });
        for (OverruleMelding m : overrulebareMeldingen) {
            LOGGER.debug("Given overrulMelding: verz=[{}], code=[{}], attr=[{}]",
                new Object[]{ m.getVerzendendId(), m.getCode(), melding.getAttribuutNaam() });
            if (m.getCode().equals(melding.getCode().getNaam())
                && isEquals(m.getVerzendendId(), melding.getVerzendendId()))
            {
                LOGGER.debug("Wordt overruled");
                return m;
            }
        }
        LOGGER.debug("Is NIET gevonden");
        return null;
    }

    /**
     * Test of 2 strings gelijk zijn aan elkaar. waarbij null gelijk is aan string (met evt. aleen maar spaties)
     *
     * @param s1 string 1
     * @param s2 string 2
     * @return true als gelijk, false anders.
     */
    boolean isEquals(final String s1, final String s2) {
        boolean retval = false;
        if (s1 == null && s2 == null) {
            retval = true;
        } else if (s1 == null || s2 == null) {
            retval = StringUtils.isBlank(s1) && StringUtils.isBlank(s2);
        } else {
            retval = s1.equals(s2);
        }
        return retval;
    }


    /**
     * .
     * @param overruleMeldingen .
     * @param berichtId .
     */
    private void logAlleMeldingenDieZijnOverruled(final List<OverruleMelding> overruleMeldingen, final Long berichtId)
    {
        // schrijf nu als 'administratief' in de logfile, dat deze meldingen zijn overschreven.
        if (CollectionUtils.isNotEmpty(overruleMeldingen)) {
            List<String> codes = new ArrayList<String>();
            for (OverruleMelding m : overruleMeldingen) {
                codes.add(m.getCode());
            }
            String msg =
                    String.format("%s: Voor het bericht %d zijn de volgende regelcodes overrruled %s",
                            "[OVERRULED]", berichtId, codes);
            LOGGER.warn(msg);
        }
    }
}
