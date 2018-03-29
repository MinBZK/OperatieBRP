/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.persoonselectie;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.leveringmodel.Afnemerindicatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.RegelValidatie;

/**
 * Controleer de aanwezigheid van afnemerindicatie.
 */
@Bedrijfsregel(Regel.R1401)
final class R1401Regel implements RegelValidatie {

    private final Persoonslijst persoonslijstNu;
    private final Autorisatiebundel autorisatiebundel;

    /**
     * Constructor.
     * @param persoonslijstNu persoonsgegevensNu
     * @param autorisatiebundel de Autorisatiebundel
     */
    R1401Regel(final Persoonslijst persoonslijstNu, final Autorisatiebundel autorisatiebundel) {
        this.persoonslijstNu = persoonslijstNu;
        this.autorisatiebundel = autorisatiebundel;
    }

    @Override
    public Regel getRegel() {
        return Regel.R1401;
    }

    @Override
    public Melding valideer() {
        boolean moetValideren = autorisatiebundel != null && autorisatiebundel.getLeveringsautorisatie() != null;
        if (moetValideren) {
            moetValideren = bepaalMoetValideren();
            if (moetValideren && controleerAfnemerindicatie()) {
                return null;
            }
        }
        return moetValideren ? new Melding(Regel.R1401) : null;
    }

    private boolean bepaalMoetValideren() {
        boolean moetValideren = true;
        final SoortDienst soortDienst = autorisatiebundel.getSoortDienst();
        if (SoortDienst.SYNCHRONISATIE_PERSOON != soortDienst && SoortDienst.VERWIJDERING_AFNEMERINDICATIE != soortDienst) {
            moetValideren = false;
        }
        //als la geen dienst MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE bevat is geen afnemerindicatie controle nodig
        if (moetValideren && SoortDienst.SYNCHRONISATIE_PERSOON == soortDienst && AutAutUtil
                .zoekDiensten(autorisatiebundel.getLeveringsautorisatie(), SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE)
                .isEmpty()) {
            moetValideren = false;
        }
        return moetValideren;
    }

    private boolean controleerAfnemerindicatie() {
        //Controleert of de persoon een afnemerindicatie heeft binnen het leveringsautorisatie.
        for (final Afnemerindicatie afnemerindicatie : persoonslijstNu.getGeldendeAfnemerindicaties()) {
            if (afnemerindicatie.getLeveringsAutorisatieId() == autorisatiebundel.getLeveringsautorisatie().getId()
                    && afnemerindicatie.getAfnemerCode().equals(autorisatiebundel.getPartij().getCode())) {
                return true;
            }
        }
        return false;
    }
}
