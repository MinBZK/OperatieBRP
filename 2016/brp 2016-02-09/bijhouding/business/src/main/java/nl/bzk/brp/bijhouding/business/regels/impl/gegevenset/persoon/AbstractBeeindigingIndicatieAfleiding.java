/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.AbstractBeeindigingAfleiding;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AbstractHisPersoonIndicatieMaterieleHistorieModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;

/**
 * Deze afleidingsregel beëindigt een indicatie op de overlappende delen
 * van de geregistreerde nationaliteit, volgens het bekende historie patroon.
 */
public abstract class AbstractBeeindigingIndicatieAfleiding extends
    AbstractBeeindigingAfleiding<PersoonHisVolledig,
            HisPersoonNationaliteitModel, AbstractHisPersoonIndicatieMaterieleHistorieModel>
{

    /**
     * Forwarding constructor.
     *
     * @param persoon de persoon
     * @param actie de actie
     * @param toegevoegdHisRecord het his record dat is toegevoegd
     */
    public AbstractBeeindigingIndicatieAfleiding(final PersoonHisVolledig persoon, final ActieModel actie,
            final HisPersoonNationaliteitModel toegevoegdHisRecord)
    {
        super(persoon, actie, toegevoegdHisRecord);
    }

    @Override
    protected final boolean checkPrecondities(final HisPersoonNationaliteitModel toegevoegdRecord) {
        return this.isNationaliteitVanToepassing(toegevoegdRecord.
                getPersoonNationaliteit().getNationaliteit().getWaarde());
    }

    /**
     * Checkt of de toegevoegde nationaliteit van toepassing is voor de beëindigde afleiding.
     *
     * @param nationaliteit de nationaliteit
     * @return of ie van toepassing is
     */
    protected abstract boolean isNationaliteitVanToepassing(Nationaliteit nationaliteit);

    @Override
    protected final MaterieleHistorieSet<AbstractHisPersoonIndicatieMaterieleHistorieModel> getTeBeeindigenHistorie() {
        MaterieleHistorieSet<AbstractHisPersoonIndicatieMaterieleHistorieModel> historie = null;
        if (getIndicatieHisVolledig() != null) {
            historie = (MaterieleHistorieSet<AbstractHisPersoonIndicatieMaterieleHistorieModel>)
                    getIndicatieHisVolledig().getPersoonIndicatieHistorie();
        }
        return historie;
    }

    /**
     * Geeft de indicatie his volledig terug voor de betreffende indicatie,
     * of <code>null</code> indien niet aanwezig.
     *
     * @return de indicatie of null
     */
    @SuppressWarnings("unchecked")
    private PersoonIndicatieHisVolledig<AbstractHisPersoonIndicatieMaterieleHistorieModel> getIndicatieHisVolledig() {
        PersoonIndicatieHisVolledig<AbstractHisPersoonIndicatieMaterieleHistorieModel> indicatie = null;
        for (PersoonIndicatieHisVolledig<?> persoonIndicatie : getModel().getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == getSoortIndicatie()) {
                indicatie =
                     (PersoonIndicatieHisVolledig<AbstractHisPersoonIndicatieMaterieleHistorieModel>) persoonIndicatie;
            }
        }
        return indicatie;
    }

    /**
     * Geeft de soort indicatie terug die van toepassing is.
     *
     * @return de soort indicatie
     */
    protected abstract SoortIndicatie getSoortIndicatie();

}
