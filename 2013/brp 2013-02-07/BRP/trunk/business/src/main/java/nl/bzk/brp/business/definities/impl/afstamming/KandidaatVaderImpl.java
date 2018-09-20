/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.definities.impl.afstamming;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.util.DatumUtil;
import org.springframework.stereotype.Component;

/**
 * .
 */
@Component("kandidaatVader")
public class KandidaatVaderImpl implements KandidaatVader {

    public static final int DAGEN_NA_OVERLIJDEN_NL_NATIONALITEIT = 306;
    private static final int DAGEN_NA_OVERLIJDEN_NIET_NL_NATIONALITEIT = 365;

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @Override
    public List<PersoonModel> bepaalKandidatenVader(final PersoonModel moeder, final Datum geboorteDatumKind) {
        List<PersoonModel> kandidatenVader = new ArrayList<PersoonModel>();
        // zoek in de relaties, welke persone hebben een 'huwelijk' relatie met de moeder, rekening houdend
        // met het geslacht van de partner (== man) en dat de relatie geldig is op het moment van de
        // peilDatum (aka. geboorteDatumKind).

        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK);
        filter.setSoortBetrokkenheden(SoortBetrokkenheid.PARTNER);
        filter.setUitGeslachtsaanduidingen(Geslachtsaanduiding.MAN);

        List<BetrokkenheidModel> kandidaten = relatieRepository.haalOpBetrokkenhedenVanPersoon(moeder, filter);
        for (BetrokkenheidModel kandidaatVader : kandidaten) {
            HuwelijkGeregistreerdPartnerschap relatieKandidaatVader =
                    (HuwelijkGeregistreerdPartnerschap) kandidaatVader.getRelatie();
            if (relatieKandidaatVader.getStandaard().getRedenEinde() != null
                    && relatieKandidaatVader.getStandaard().getRedenEinde().getCode().getWaarde()
                    .equals(BrpConstanten.REDEN_BEEINDIGING_RELATIE_OVERLIJDEN_CODE.getWaarde()))
            {
                // BRPUC50110 2. De moeder was gehuwd met de man echter dit huwelijk is ontbonden vanwege het
                // overlijden van de
                // man terwijl het kind is geboren binnen 306 dagen na het overlijden van de man (m.a.w.: verschil
                // tussen datum geboorte en datum overlijden is maximaal 306 dagen)

                // 2. De 306 dagen termijn geldt alleen als in de BRP kan worden vastgesteld dat de man de
                // Nederlandse nationaliteit had op datum overlijden, in alle andere gevallen zoals geen Nederlandse
                // nationaliteit, man is Niet-ingeschrevene of datum overlijden (gedeeltelijk) onbekend - moet 365
                // dagen worden genomen.
                Datum overlijdenDatum = relatieKandidaatVader.getStandaard().getDatumEinde();
                Datum pijlDatum;
                // TODO onbekende datum nog niet in scope
                if (kandidaatVader.getPersoon().heeftActueleNederlandseNationaliteit()) {
                    pijlDatum = DatumUtil.voegToeDatum(overlijdenDatum, Calendar.DATE,
                                                       DAGEN_NA_OVERLIJDEN_NL_NATIONALITEIT);
                } else {
                    pijlDatum = DatumUtil
                            .voegToeDatum(overlijdenDatum, Calendar.DATE, DAGEN_NA_OVERLIJDEN_NIET_NL_NATIONALITEIT);
                }

                if (geboorteDatumKind.getWaarde().equals(pijlDatum.getWaarde()) || geboorteDatumKind.voor(pijlDatum)) {
                    kandidatenVader.add(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(kandidaatVader));
                }
            }
        }

        if (kandidatenVader.size() == 0) {
            // BRPUC50110 3. De moeder is op de geboortedatum van het kind gehuwd met de man en er is geen andere
            // man die aan voorwaarde 2 voldoet.
            for (BetrokkenheidModel kandidaatVader : kandidaten) {
                HuwelijkGeregistreerdPartnerschap relatieKandidaatVader =
                        (HuwelijkGeregistreerdPartnerschap) kandidaatVader.getRelatie();
                Datum geboorteDatum = geboorteDatumKind;
                Datum huwelijkAanvang = relatieKandidaatVader.getStandaard().getDatumAanvang();
                Datum huwelijkEinde = relatieKandidaatVader.getStandaard().getDatumEinde();

                if (DatumUtil.isGeldigOp(huwelijkAanvang, huwelijkEinde, geboorteDatum)) {
                    kandidatenVader.add(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(kandidaatVader));
                }
            }
        }

        return kandidatenVader;
    }
}
