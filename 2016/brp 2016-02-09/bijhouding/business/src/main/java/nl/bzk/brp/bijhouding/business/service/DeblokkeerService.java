/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.GedeblokkeerdeMeldingBericht;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Service voor het verwerken van deblokkeerbare meldingen.
 */
@Service
public class DeblokkeerService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Zoekt deblokkeerbare resultaatmeldingen uit het resultaat en overeenkomende te deblokkeren meldingen uit het bericht. Voor elke overeenkomende
     * melding wordt de status in het resultaat aangepast, en wordt de te deblokkeren melding uit het bericht verwijderd.
     *
     * @param bericht   bericht
     * @param context   context
     * @param resultaat resultaat
     * @return gefilterde meldingen
     */
    public Resultaat deblokkeerResultaatMeldingen(final BerichtBericht bericht, final BijhoudingBerichtContext context,
        final Resultaat resultaat)
    {

        final ResultaatMeldingKoppelLijst meldingKoppelLijst = vindOvereenkomendeTeDeblokkerenResultaatMeldingen(bericht, resultaat.getMeldingen());
        if (meldingKoppelLijst.isLeeg()) {
            return resultaat;
        }
        bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen().removeAll(meldingKoppelLijst.getDeblokkeerverzoeken());

        Resultaat naResultaat = resultaat.deblokkeer(meldingKoppelLijst.getResultaatMeldingen());

        final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> toegepasteDeblokkeerverzoeken = new ArrayList<>();
        toegepasteDeblokkeerverzoeken.addAll(resultaat.getToegepasteDeblokkeerverzoeken());
        toegepasteDeblokkeerverzoeken.addAll(meldingKoppelLijst.getDeblokkeerverzoeken());
        naResultaat = Resultaat.builder(naResultaat).withToegepasteDeblokkeermeldingen(toegepasteDeblokkeerverzoeken).build();

        logGedeblokkeerdeMeldingen(meldingKoppelLijst.getDeblokkeerverzoeken(), context.getIngaandBerichtId());
        return naResultaat;
    }

    private ResultaatMeldingKoppelLijst vindOvereenkomendeTeDeblokkerenResultaatMeldingen(final BerichtBericht bericht,
        final Set<ResultaatMelding> meldingen)
    {
        final ResultaatMeldingKoppelLijst meldingKoppelLijst = new ResultaatMeldingKoppelLijst();
        if (CollectionUtils.isNotEmpty(bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen())) {
            for (final ResultaatMelding resultaatMelding : meldingen) {
                if (resultaatMelding.getSoort() == SoortMelding.DEBLOKKEERBAAR) {
                    final AdministratieveHandelingGedeblokkeerdeMeldingBericht overeenkomendeTeDeblokkerenMelding =
                        vindOvereenkomendeTeDeblokkerenMelding(resultaatMelding, bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen());

                    if (overeenkomendeTeDeblokkerenMelding != null) {
                        muteerGedeblokkeerdeMeldingTekst(overeenkomendeTeDeblokkerenMelding, resultaatMelding.getMeldingTekst());
                        muteerGedeblokkeerdeMeldingReferentieId(overeenkomendeTeDeblokkerenMelding);
                        meldingKoppelLijst.voegToe(resultaatMelding, overeenkomendeTeDeblokkerenMelding);
                    }
                }
            }
        }
        return meldingKoppelLijst;
    }

    private void muteerGedeblokkeerdeMeldingTekst(final AdministratieveHandelingGedeblokkeerdeMeldingBericht gevondenGedeblokkeerdeMelding,
        final String meldingTekst)
    {
        final GedeblokkeerdeMeldingBericht gevondenGedeblokkeerdeMeldingMelding = gevondenGedeblokkeerdeMelding.getGedeblokkeerdeMelding();
        gevondenGedeblokkeerdeMeldingMelding.setMelding(new MeldingtekstAttribuut(meldingTekst));
    }

    private void muteerGedeblokkeerdeMeldingReferentieId(final AdministratieveHandelingGedeblokkeerdeMeldingBericht gevondenGedeblokkeerdeMelding)
    {
        final GedeblokkeerdeMeldingBericht gevondenGedeblokkeerdeMeldingMelding = gevondenGedeblokkeerdeMelding.getGedeblokkeerdeMelding();
        gevondenGedeblokkeerdeMeldingMelding.setReferentieID(gevondenGedeblokkeerdeMeldingMelding.getCommunicatieID());
        gevondenGedeblokkeerdeMeldingMelding.setCommunicatieID(null);
    }

    private AdministratieveHandelingGedeblokkeerdeMeldingBericht vindOvereenkomendeTeDeblokkerenMelding(
        final ResultaatMelding geconstateerdeMelding, final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> gedeblokkeerdeMeldingen)
    {
        final String geconstateerdeMeldingRegelCode = geconstateerdeMelding.getRegel().getCode();
        for (final AdministratieveHandelingGedeblokkeerdeMeldingBericht gedeblokkeerdeMelding : gedeblokkeerdeMeldingen) {
            final String gedeblokkeerdeMeldingRegelCode = gedeblokkeerdeMelding.getGedeblokkeerdeMelding().getRegel().getWaarde().getCode();
            final String gedeblokkeerdeMeldingReferentieId = gedeblokkeerdeMelding.getGedeblokkeerdeMelding().getReferentieID();
            if (gedeblokkeerdeMeldingRegelCode.equals(geconstateerdeMeldingRegelCode) && StringUtils.equals(gedeblokkeerdeMeldingReferentieId,
                geconstateerdeMelding.getReferentieID()))
            {
                return gedeblokkeerdeMelding;
            }
        }
        return null;
    }

    private void logGedeblokkeerdeMeldingen(
        final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> gedeblokkeerdeMeldingen, final Long berichtId)
    {
        // schrijf nu als 'administratief' in de logfile, dat deze meldingen zijn overschreven.
        if (CollectionUtils.isNotEmpty(gedeblokkeerdeMeldingen)) {
            final List<String> codes = new ArrayList<>();
            for (final AdministratieveHandelingGedeblokkeerdeMeldingBericht m : gedeblokkeerdeMeldingen) {
                codes.add(m.getGedeblokkeerdeMelding().getRegel().getWaarde().getCode());
            }
            final String msg =
                String.format("%s: Voor het bericht %d zijn de volgende regelcodes overrruled %s", "[OVERRULED]",
                    berichtId, codes);
            LOGGER.info(FunctioneleMelding.BIJHOUDING_OVERRULEN_REGELCODE, msg);
        }
    }

    /**
     * Wordt gebruikt voor het vastleggen van overeenkomende meldingen uit het resultaat en uit het bericht (te deblokkeerbare meldingen).
     */
    private static class ResultaatMeldingKoppelLijst {
        private final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> deblokkeerverzoeken = new ArrayList<>();
        private final List<ResultaatMelding>                                     resultaatMeldingen  = new ArrayList<>();

        public void voegToe(final ResultaatMelding resultaatMelding,
            final AdministratieveHandelingGedeblokkeerdeMeldingBericht overeenkomendeTeDeblokkerenMelding)
        {
            deblokkeerverzoeken.add(overeenkomendeTeDeblokkerenMelding);
            resultaatMeldingen.add(resultaatMelding);
        }

        public List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> getDeblokkeerverzoeken() {
            return deblokkeerverzoeken;
        }

        public List<ResultaatMelding> getResultaatMeldingen() {
            return resultaatMeldingen;
        }

        public boolean isLeeg() {
            return deblokkeerverzoeken.size() == 0;
        }
    }


}
