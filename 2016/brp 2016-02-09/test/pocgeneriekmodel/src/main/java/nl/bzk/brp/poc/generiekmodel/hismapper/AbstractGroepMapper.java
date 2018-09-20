/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel.hismapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.NadereAanduidingVervalToepasbaar;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.poc.generiekmodel.BrpAttribuut;
import nl.bzk.brp.poc.generiekmodel.BrpGroep;
import nl.bzk.brp.poc.generiekmodel.BrpGroepVoorkomen;
import nl.bzk.brp.poc.generiekmodel.BrpObject;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementAttribuut;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementGroep;

/**
 *
 * @param <M>
 * @param <T>
 */
abstract class AbstractGroepMapper<M, T extends AbstractObjectMapper> {

    /**
     *
     * @param brpObject
     * @return
     */
    protected final BrpGroep maakGroep(final BrpObject brpObject) {
        final BrpGroep groep = new BrpGroep();
        groep.setParent(brpObject);
        groep.setElement(geefElementGroep());
        groep.setVoorkomens(new HashSet<BrpGroepVoorkomen>());
        return groep;
    }

    /**
     *
     * @return
     */
    protected abstract ElementGroep geefElementGroep();

    /**
     *
     * @param objectMapper
     * @param brpObject
     * @return
     */
    public abstract BrpGroep mapGroep(final T objectMapper, final BrpObject brpObject);

    /**
     *
     * @param groep
     * @param model
     * @return
     */
    protected final BrpGroepVoorkomen maakVoorkomen(final BrpGroep groep, final M model) {
        final BrpGroepVoorkomen voorkomen = new BrpGroepVoorkomen();
        groep.getVoorkomens().add(voorkomen);
        voorkomen.setParent(groep);

        if (model instanceof FormeelHistorisch) {
            voorkomen.setVervalDatumTijd(((FormeelHistorisch) model).getFormeleHistorie().getDatumTijdVerval());
            voorkomen.setRegistratieDatumTijd(((FormeelHistorisch) model).getFormeleHistorie().getTijdstipRegistratie());
        }
        if (model instanceof MaterieelHistorisch) {
            voorkomen.setDatumAanvangGeldigheid(((MaterieelHistorisch) model).getMaterieleHistorie().getDatumAanvangGeldigheid());
            voorkomen.setDatumEindeGeldigheid(((MaterieelHistorisch) model).getMaterieleHistorie().getDatumEindeGeldigheid());
        }
        if (model instanceof NadereAanduidingVervalToepasbaar) {
            voorkomen.setNadereAanduidingVerval(((NadereAanduidingVervalToepasbaar) model).getNadereAanduidingVerval());
        }
        if (model instanceof VerantwoordingTbvLeveringMutaties) {
            voorkomen.setIndicatieVoorkomenTbvLeveringMutaties(((VerantwoordingTbvLeveringMutaties) model).getIndicatieVoorkomenTbvLeveringMutaties());
            voorkomen.setActieVervalTbvLeveringMutaties(((VerantwoordingTbvLeveringMutaties) model).getVerantwoordingVervalTbvLeveringMutaties());
        }
        if (model instanceof FormeelVerantwoordbaar) {
            if (((FormeelVerantwoordbaar) model).getVerantwoordingInhoud() instanceof ActieModel) {
                voorkomen.setActieInhoud((ActieModel) ((FormeelVerantwoordbaar) model).getVerantwoordingInhoud());
                voorkomen.setActieVerval((ActieModel) ((FormeelVerantwoordbaar) model).getVerantwoordingVerval());
            } else if (((FormeelVerantwoordbaar) model).getVerantwoordingInhoud() instanceof Dienst) {
                voorkomen.setDienstInhoud((Dienst) ((FormeelVerantwoordbaar) model).getVerantwoordingInhoud());
                voorkomen.setDienstVerval((Dienst) ((FormeelVerantwoordbaar) model).getVerantwoordingVerval());
            }
        }

        final Map<ElementAttribuut, Attribuut> attribuutMap = mapAttributen(model);
        for (Map.Entry<ElementAttribuut, Attribuut> entry : attribuutMap.entrySet()) {
            voegAttrbuutToe(voorkomen, entry.getKey(), entry.getValue());
        }
        return voorkomen;
    }

    /**
     *
     * @param voorkomen
     * @param elementAttribuut
     * @param attribuut
     */
    protected final void voegAttrbuutToe(final BrpGroepVoorkomen voorkomen, final ElementAttribuut elementAttribuut, final Attribuut attribuut) {
        if (attribuut == null) {
            return;
        }
        final BrpAttribuut attr = new BrpAttribuut();
        attr.setElement(elementAttribuut);
        attr.setParent(voorkomen);
        attr.setWaarde(attribuut.getWaarde());
        voorkomen.setAttribuut(attr);
    }

    protected abstract Iterable<M> geefHistorieSet(final T objectMapper);

    /**
     *
     * @param model
     * @return
     */
    protected Map<ElementAttribuut, Attribuut> mapAttributen(final M model) {
        return new HashMap<>();
    }

}
