/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.handlers;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.bevraging.business.handlers.BerichtVerwerkingsStap;
import nl.bzk.brp.poc.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.poc.business.bedrijfsregels.DatumVerhuizingInVerleden;
import nl.bzk.brp.poc.business.bedrijfsregels.PersoonNietVerhuizenNaarHuidigAdres;
import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingResultaat;
import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingWaarschuwing;
import nl.bzk.brp.poc.business.dto.verzoek.BijhoudingVerzoek;
import nl.bzk.brp.poc.dal.PocPersoonRepository;
import nl.bzk.brp.poc.domein.PocPersoon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stap voor het uitvoeren van de bedrijfsregels.
 */
public class BedrijfsRegelStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(BedrijfsRegelStap.class);

    @Inject
    private PocPersoonRepository persoonRepository;

    /**
     * Voert de stap voor het uivoeren van de voor het bericht benodigde bedrijfsregels uit. Hiervoor worden eerst de
     * bedrijfsregels opgehaald die van toepassing zijn op het bericht, waarna ze regel voor regel worden uitgevoerd.
     * Indien een regel een resultaat oplevert, dus een waarschuwing retourneert, wordt deze aan de collectie van
     * waarschuwingen in het resultaat toegevoegd. Standaard wordt er doorgegaan met de verwerking, tenzij een of
     * meerdere bedrijfsregels echt een fout constateren. Indien er een fout wordt geconstateerd worden de overige
     * bedrijfsregels nog wel gecontroleerd (en eventueel gerapporteerd), maar de verwerking wordt daarna wel door
     * de stap gestopt.
     *
     * @param verzoek het berichtverzoek waarvoor de verwerkingsstap dient te worden uitgevoerd.
     * @param context de context waarbinnen het berichtverzoek dient te worden uitgevoerd.
     * @param antwoord het antwoordbericht dat in de stap kan worden (aan)gevuld.
     * @param <T> het type van het antwoord bericht.
     * @return een indicator of de verwerking van het bericht moet worden gestopt of dat er doorgegaan kan worden.
     */
    @Override
    public <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        if (!(verzoek instanceof BijhoudingVerzoek)) {
            return BerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
        }

        BijhoudingVerzoek bijhoudingVerzoek = (BijhoudingVerzoek) verzoek;
        BijhoudingResultaat resultaat = (BijhoudingResultaat) antwoord;

        for (BedrijfsRegel bedrijfsRegel : bepaalBedrijfsRegels()) {
            BijhoudingWaarschuwing waarschuwing =
                    bedrijfsRegel.voerUit(bijhoudingVerzoek.getNieuweSituatie(),
                            getHuidigeSituatie(bijhoudingVerzoek));
            if (waarschuwing != null) {
                LOGGER.debug(String.format("Waarschuwing (%2$s) vanuit bedrijfsregel %1$s.",
                        waarschuwing.getBedrijfsregelId(), waarschuwing.getNiveau()));
                resultaat.voegWaarschuwingToe(waarschuwing);
            }
        }

        boolean stapResultaat = BerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
        if (resultaat.heeftBedrijfsregelFout()) {
            antwoord.voegFoutToe(new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.POC_BEDRIJFSREGEL_FOUT,
                    BerichtVerwerkingsFoutZwaarte.FOUT));
            stapResultaat = BerichtVerwerkingsStap.STOP_VERWERKING;
        }
        return stapResultaat;
    }

    /**
     * Retourneert voorlopig een standaard lijst van bedrijfsregels.
     *
     * @return een standaard lijst van bedrijfsregels.
     */
    private List<? extends BedrijfsRegel<?>> bepaalBedrijfsRegels() {
        List<BedrijfsRegel<PocPersoon>> bedrijfsRegels = new ArrayList();
        bedrijfsRegels.add(new DatumVerhuizingInVerleden());
        bedrijfsRegels.add(new PersoonNietVerhuizenNaarHuidigAdres());
        return bedrijfsRegels;
    }

    /**
     * Haalt op basis van het verzoek de huidige situatie op van de binnen het verzoek bekende root entity.
     *
     * @param verzoek het verzoek waarvoor de huidige situatie moet worden opgehaald.
     * @return de huidige situatie van de root entity die wordt aangepast.
     */
    private Object getHuidigeSituatie(final BijhoudingVerzoek verzoek) {
        Object huidigeSituatie = null;

        if (verzoek.getNieuweSituatie() instanceof PocPersoon) {
            Long bsn = ((PocPersoon) verzoek.getNieuweSituatie()).getBurgerservicenummer();
            huidigeSituatie = persoonRepository.findByBurgerservicenummer(bsn).get(0);
        }

        return huidigeSituatie;
    }

}
