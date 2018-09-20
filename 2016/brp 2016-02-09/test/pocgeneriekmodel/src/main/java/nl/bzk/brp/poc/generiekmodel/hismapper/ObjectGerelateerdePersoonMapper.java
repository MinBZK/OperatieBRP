/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel.hismapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.poc.generiekmodel.BrpGroep;
import nl.bzk.brp.poc.generiekmodel.BrpGroepVoorkomen;
import nl.bzk.brp.poc.generiekmodel.BrpObject;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementAttribuut;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementGroep;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementObjectType;

/**
 * Created by dennis on 2/5/16.
 */
class ObjectGerelateerdePersoonMapper extends AbstractObjectMapper<PersoonHisVolledigImpl> {

    private final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig;

    /**
     *
     * @param betrokkenheidHisVolledig
     */
    protected ObjectGerelateerdePersoonMapper(final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig) {
        super(betrokkenheidHisVolledig.getPersoon());
        this.betrokkenheidHisVolledig = betrokkenheidHisVolledig;
    }

    @Override
    public ElementObjectType geefObjectType() {
        final ElementObjectType ot;
        switch (betrokkenheidHisVolledig.getRol().getWaarde()) {
            case KIND:
                ot = ElementObjectType.GerelateerdeKindPersoon;
                break;
            case ERKENNER:
                ot = ElementObjectType.GerelateerdeErkennerPersoon;
                break;
            case INSTEMMER:
                ot = ElementObjectType.GerelateerdeInstemmerPersoon;
                break;
            case NAAMGEVER:
                ot = ElementObjectType.GerelateerdeNaamgeverPersoon;
                break;
            case OUDER:
                ot = ElementObjectType.GerelateerdeOuderPersoon;
                break;
            case PARTNER:
                ot = ElementObjectType.GerelateerdeGeregistreerdePartnerPersoon;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return ot;
    }

    @Override
    public int geefObjectSleutel() {
        return getMappedObject().getID();
    }

    @Override
    public List<AbstractGroepMapper> geefGroepMappers() {
        return Arrays.<AbstractGroepMapper>asList(new GroepIndentificatienummerMapper());

    }

    @Override
    public List<AbstractObjectMapper> geefObjectMappers() {
        return null;
    }

    private static final class GroepIndentificatienummerMapper extends AbstractGroepMapper<HisPersoonIdentificatienummersModel, ObjectGerelateerdePersoonMapper> {

        @Override
        protected ElementGroep geefElementGroep() {
            return ElementGroep.PersoonIdentificatienummers;
        }

        @Override
        public BrpGroep mapGroep(final ObjectGerelateerdePersoonMapper objectMapper, final BrpObject brpObject) {
            final BrpGroep groep = super.maakGroep(brpObject);
            for (HisPersoonIdentificatienummersModel model : objectMapper.getMappedObject().getPersoonIdentificatienummersHistorie()) {
                final BrpGroepVoorkomen voorkomen = maakVoorkomen(groep, model);
                voorkomen.setVoorkomensleutel(model.getID());
            }
            return groep;
        }

        @Override
        protected HistorieSet<HisPersoonIdentificatienummersModel> geefHistorieSet(final ObjectGerelateerdePersoonMapper objectMapper) {
            return objectMapper.getMappedObject().getPersoonIdentificatienummersHistorie();
        }

        @Override
        protected Map<ElementAttribuut, Attribuut> mapAttributen(final HisPersoonIdentificatienummersModel model) {
            final Map<ElementAttribuut, Attribuut> map = super.mapAttributen(model);
            map.put(ElementAttribuut.PersoonIdentificatienummersBurgerservicenummer, model.getBurgerservicenummer());
            map.put(ElementAttribuut.PersoonIdentificatienummersAdministratienummer, model.getAdministratienummer());
            return map;
        }
    }
}
