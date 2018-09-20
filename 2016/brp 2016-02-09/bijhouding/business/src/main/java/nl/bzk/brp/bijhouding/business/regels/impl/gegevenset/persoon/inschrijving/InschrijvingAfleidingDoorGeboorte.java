/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.inschrijving;

import java.util.Iterator;
import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VersienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;

/**
 * VR00015a : Afgeleide inschrijving door geboorte.
 */
public class InschrijvingAfleidingDoorGeboorte extends AbstractAfleidingsregel<PersoonHisVolledig> {

    /**
     * Constructor van InschrijvingAfleidingDoorGeboorte.
     *
     * @param model het PersoonHisVolledig model
     * @param actie de actie
     */
    public InschrijvingAfleidingDoorGeboorte(final PersoonHisVolledig model, final ActieModel actie) {
        super(model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00024a;
    }

    @Override
    public AfleidingResultaat leidAf() {
        //Let op, het kan dat er geen adres is afgeleid. (Vanwege het ontbreken van ouwkiv etc.)
        final Iterator<? extends PersoonAdresHisVolledig> adresIter = getModel().getAdressen().iterator();
        if (adresIter.hasNext()) {
            final PersoonAdresHisVolledig adresKind = adresIter.next();
            if (adresKind.getPersoonAdresHistorie().getActueleRecord() != null) {
                final HisPersoonInschrijvingModel hisInschrijving =
                        new HisPersoonInschrijvingModel(
                                getModel(),
                                adresKind.getPersoonAdresHistorie().getActueleRecord().getDatumAanvangGeldigheid(),
                                new VersienummerAttribuut(1L),
                                DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1), getActie());
                getModel().getPersoonInschrijvingHistorie().voegToe(hisInschrijving);
            }
        }
        return GEEN_VERDERE_AFLEIDINGEN;
    }
}
