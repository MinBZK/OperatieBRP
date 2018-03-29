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
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.leveringmodel.Afnemerindicatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import org.springframework.stereotype.Component;

/**
 * Voert de dienst specifieke validatie uit voor verwijderen afnemerindicatie.
 */
@Bedrijfsregel(Regel.R1401)
@Component
final class OnderhoudAfnemerindicatieValideerRegelsVerwijdering implements GeneriekeOnderhoudAfnemerindicatieStappen.ValideerRegelsVerwijderen {

    private OnderhoudAfnemerindicatieValideerRegelsVerwijdering() {
    }

    @Override
    public void valideer(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final Persoonslijst persoonslijst) throws StapMeldingException {
        final List<Melding> meldingen = new ArrayList<>();
        //Er moet een afnemerindicatie bestaan voor de opgegeven persoon bij deze partij en deze leveringsautorisatie
        if (!heeftAfnemerindicatie(toegangLeveringsAutorisatie, persoonslijst)) {
            meldingen.add(new Melding(Regel.R1401));
        }
        //meldingen zijn altijd van type FOUT
        if (!meldingen.isEmpty()) {
            throw new StapMeldingException(meldingen);
        }
    }

    @Bedrijfsregel(Regel.R1401)
    private boolean heeftAfnemerindicatie(final ToegangLeveringsAutorisatie tla,
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
}
