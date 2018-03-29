/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.afnemerindicatie;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Param object voor plaatsen afnemerindicatie.
 */
public final class PlaatsAfnemerindicatieParams {

    private final ToegangLeveringsAutorisatie toegangLeveringsautorisatie;
    private final Persoonslijst persoonslijst;
    private final Integer datumAanvangMaterielePeriode;
    private final Integer datumEindeVolgen;
    private final ZonedDateTime tijdstipRegistratie;
    private final int verantwoordingDienstId;

    /**
     * Constructor voor de input parameters.
     * @param toegangLeveringsautorisatie de toegang leveringautorisatie
     * @param persoonHisVolledig de persoon waar de indicatie op geplaatst wordt
     * @param datumAanvangMaterielePeriode de datum waarop de indicatie gaat gelden
     * @param datumEindeVolgen de datum waarop de indicatie verloopt
     * @param tijdstipRegistratie het moment van registreren
     * @param verantwoordingDienstId het id van de dienst welke de plaatsing verantwoordt.
     */
    public PlaatsAfnemerindicatieParams(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie, final Persoonslijst persoonHisVolledig,
                                        final Integer datumAanvangMaterielePeriode, final Integer datumEindeVolgen,
                                        final ZonedDateTime tijdstipRegistratie, final int verantwoordingDienstId) {
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
        this.persoonslijst = persoonHisVolledig;
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
        this.datumEindeVolgen = datumEindeVolgen;
        this.tijdstipRegistratie = tijdstipRegistratie;
        this.verantwoordingDienstId = verantwoordingDienstId;
    }

    public ToegangLeveringsAutorisatie getToegangLeveringsautorisatie() {
        return toegangLeveringsautorisatie;
    }

    public Persoonslijst getPersoonslijst() {
        return persoonslijst;
    }

    public Integer getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    public Integer getDatumEindeVolgen() {
        return datumEindeVolgen;
    }

    public ZonedDateTime getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    public int getVerantwoordingDienstId() {
        return verantwoordingDienstId;
    }
}
