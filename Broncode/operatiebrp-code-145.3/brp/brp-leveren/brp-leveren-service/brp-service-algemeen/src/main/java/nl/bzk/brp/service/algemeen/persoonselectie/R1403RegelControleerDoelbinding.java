/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.persoonselectie;


import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.RegelValidatie;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;


/**
 * Controleer doelbinding.
 */
@Bedrijfsregel(Regel.R1403)
final class R1403RegelControleerDoelbinding implements RegelValidatie {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final Autorisatiebundel autorisatiebundel;
    private final Persoonslijst persoonslijstNu;
    private final ExpressieService expressieService;

    /**
     * @param persoonslijst persoonsgegevens
     * @param expressieService expressieService
     * @param autorisatiebundel autorisatiebundel
     */
    R1403RegelControleerDoelbinding(final Persoonslijst persoonslijst, final ExpressieService expressieService,
                                    final Autorisatiebundel autorisatiebundel) {
        this.expressieService = expressieService;
        this.autorisatiebundel = autorisatiebundel;
        this.persoonslijstNu = persoonslijst;
    }


    @Override
    public Regel getRegel() {
        return Regel.R1403;
    }

    @Override
    public Melding valideer() {
        if (autorisatiebundel != null && autorisatiebundel.getDienst() != null && autorisatiebundel.getToegangLeveringsautorisatie() != null) {
            final boolean valtPersoonBinnenPopulatieBeperking = valtPersoonBinnenPopulatieBeperking();
            if (!valtPersoonBinnenPopulatieBeperking) {
                return new Melding(Regel.R1403);
            }
        }
        return null;
    }

    private boolean valtPersoonBinnenPopulatieBeperking() {
        try {
            final Expressie expressieParsed = expressieService.geefPopulatiebeperking(autorisatiebundel);
            return expressieService.evalueer(expressieParsed, persoonslijstNu);
        } catch (ExpressieException expressieExceptie) {
            LOGGER.error("Er is een fout opgetreden tijdens het bepalen van de leveringsautorisatie populatie map.", expressieExceptie);
            return false;
        }
    }
}
