/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;


/**
 * VR00015a : Afgeleide registratie Bijhouding wegens Overlijden.
 */
public class BijhoudingAfleidingDoorOverlijden extends AbstractAfleidingsregel<PersoonHisVolledig> {

    /**
     * Constructor van BijhoudingAfleidingDoorOverlijden.
     *
     * @param model het PersoonHisVolledig model
     * @param actie de actie
     */
    public BijhoudingAfleidingDoorOverlijden(final PersoonHisVolledig model, final ActieModel actie) {
        super(model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00015a;
    }

    @Override
    public final AfleidingResultaat leidAf() {
        final PersoonHisVolledig persoonHisVolledig = getModel();

        final HisPersoonBijhoudingModel actueleRecordBijhouding =
            persoonHisVolledig.getPersoonBijhoudingHistorie().getActueleRecord();

        final HisPersoonBijhoudingModel persoonBijhouding =
            new HisPersoonBijhoudingModel(persoonHisVolledig, actueleRecordBijhouding.getBijhoudingspartij(),
                    actueleRecordBijhouding.getBijhoudingsaard(),
                    new NadereBijhoudingsaardAttribuut(NadereBijhoudingsaard.OVERLEDEN),
                    actueleRecordBijhouding.getIndicatieOnverwerktDocumentAanwezig(), getActie(), getActie());

        persoonHisVolledig.getPersoonBijhoudingHistorie().voegToe(persoonBijhouding);

        return GEEN_VERDERE_AFLEIDINGEN;
    }
}
