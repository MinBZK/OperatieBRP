/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;

/**
 * VR00016b: Afgeleide beeindiging Deelname EU verkiezingen door Nederlanderschap.
 */
public class DeelnameEUVerkiezingAfleidingDoorNederlanderschap extends AbstractAfleidingsregel<PersoonHisVolledig> {

    private final HisPersoonNationaliteitModel toegevoegdHisNationaliteitRecord;

    /**
     * Forwarding constructor.
     *
     * @param persoon het model
     * @param actie de actie
     * @param toegevoegdHisNationaliteitRecord het his record dat is toegevoegd
     */
    public DeelnameEUVerkiezingAfleidingDoorNederlanderschap(
            final PersoonHisVolledig persoon, final ActieModel actie,
            final HisPersoonNationaliteitModel toegevoegdHisNationaliteitRecord)
    {
        super(persoon, actie);
        this.toegevoegdHisNationaliteitRecord = toegevoegdHisNationaliteitRecord;
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00016b;
    }

    @Override
    public AfleidingResultaat leidAf() {
        final Nationaliteit nationaliteit = toegevoegdHisNationaliteitRecord.getPersoonNationaliteit()
                .getNationaliteit().getWaarde();
        if (nationaliteit.getCode().equals(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE)) {
            final FormeleHistorieSet<HisPersoonDeelnameEUVerkiezingenModel> euVerkiezingenHistorie =
                    getModel().getPersoonDeelnameEUVerkiezingenHistorie();
            if (euVerkiezingenHistorie.heeftActueelRecord()) {
                euVerkiezingenHistorie.verval(getActie(), getActie().getTijdstipRegistratie());
            }
        }
        return GEEN_VERDERE_AFLEIDINGEN;
    }

}
