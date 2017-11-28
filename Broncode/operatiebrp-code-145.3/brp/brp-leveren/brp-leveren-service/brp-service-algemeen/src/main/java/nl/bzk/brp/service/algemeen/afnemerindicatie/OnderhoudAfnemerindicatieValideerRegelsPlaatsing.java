/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.afnemerindicatie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.leveringmodel.Afnemerindicatie;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.afnemerindicatie.GeneriekeOnderhoudAfnemerindicatieStappen.ValideerPlaatsAfnemerindicatieParams;
import org.springframework.stereotype.Component;

/**
 * Voert de dienst specifieke validatie uit voor plaatsen afnemerindicatie.
 */
@Bedrijfsregel(Regel.R1402)
@Bedrijfsregel(Regel.R1405)
@Bedrijfsregel(Regel.R1406)
@Component
final class OnderhoudAfnemerindicatieValideerRegelsPlaatsing implements GeneriekeOnderhoudAfnemerindicatieStappen.ValideerRegelsPlaatsing {

    private OnderhoudAfnemerindicatieValideerRegelsPlaatsing() {
    }

    @Override
    public void valideer(final ValideerPlaatsAfnemerindicatieParams params) throws StapMeldingException {
        final List<Melding> meldingen = new ArrayList<>();
        //Er mag geen afnemerindicatie bestaan voor de opgegeven persoon bij deze partij en deze leveringsautorisatie
        if (afnemerindicatieIsReedsBestaand(params.getToegangLeveringsautorisatie(), params.getPersoonslijst())) {
            meldingen.add(new Melding(Regel.R1402));
        }
        //datum aanvang materiele periode mag niet in toekomst liggen
        if (datumAanvangMaterielePeriodeLigtInToekomst(params.getDatumAanvangMaterielePeriode())) {
            meldingen.add(new Melding(Regel.R1405));
        }
        //datum einde volgen moet in toekomst liggen
        if (datumEindeVolgenLigtNietInToekomst(params.getDatumEindeVolgen())) {
            meldingen.add(new Melding(Regel.R1406));
        }

        //meldingen zijn altijd van type FOUTs
        if (!meldingen.isEmpty()) {
            throw new StapMeldingException(meldingen);
        }
    }

    @Bedrijfsregel(Regel.R1402)
    private boolean afnemerindicatieIsReedsBestaand(final ToegangLeveringsAutorisatie tla,
                                                    final Persoonslijst huidigeSituatie) {
        final Leveringsautorisatie leveringsautorisatie = tla.getLeveringsautorisatie();
        final Partij afnemer = tla.getGeautoriseerde().getPartij();
        if (leveringsautorisatie != null && afnemer != null) {
            for (final Afnemerindicatie indicatie : huidigeSituatie.getGeldendeAfnemerindicaties()) {
                // check bestaan
                if (indicatie.getAfnemerCode().equals(afnemer.getCode()) && indicatie.getLeveringsAutorisatieId() == leveringsautorisatie.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Bedrijfsregel(Regel.R1405)
    private boolean datumAanvangMaterielePeriodeLigtInToekomst(final Integer datumAanvangMaterielePeriode) {
        final Integer vandaag = BrpNu.get().alsIntegerDatumNederland();
        return datumAanvangMaterielePeriode != null && !DatumUtil.valtDatumBinnenPeriodeInclusief(datumAanvangMaterielePeriode, null, vandaag);
    }

    @Bedrijfsregel(Regel.R1406)
    private boolean datumEindeVolgenLigtNietInToekomst(final Integer datumEindeVolgen) {
        final Integer vandaag = BrpNu.get().alsIntegerDatumNederland();
        return datumEindeVolgen != null && DatumUtil.valtDatumBinnenPeriodeInclusief(datumEindeVolgen, null, vandaag);
    }

}
