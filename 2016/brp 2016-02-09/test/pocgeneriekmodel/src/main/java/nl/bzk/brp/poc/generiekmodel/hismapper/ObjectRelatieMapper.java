/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel.hismapper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.brp.poc.generiekmodel.BrpGroep;
import nl.bzk.brp.poc.generiekmodel.BrpGroepVoorkomen;
import nl.bzk.brp.poc.generiekmodel.BrpObject;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementAttribuut;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementGroep;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementObjectType;

/**
 * Created by dennis on 2/5/16.
 */
class ObjectRelatieMapper extends AbstractObjectMapper<RelatieHisVolledigImpl> {


    private final int hoofdpersoonId;

    /**
     * Constructor
     *
     * @param mappedObject het object dat gemapped dient te worden
     * @param hoofdpersoonId id van de hoofdpersoon
     */
    protected ObjectRelatieMapper(final RelatieHisVolledigImpl mappedObject, final Integer hoofdpersoonId) {
        super(mappedObject);
        this.hoofdpersoonId = hoofdpersoonId;
    }

    @Override
    public ElementObjectType geefObjectType() {
        final ElementObjectType ot;
        final RelatieHisVolledigImpl mappedObject = getMappedObject();
        switch (mappedObject.getSoort().getWaarde()) {
            case HUWELIJK:
            case GEREGISTREERD_PARTNERSCHAP:
                ot = ElementObjectType.HuwelijkGeregistreerdPartnerschap;
                break;
            case ERKENNING_ONGEBOREN_VRUCHT:
                ot = ElementObjectType.ErkenningOngeborenVrucht;
                break;
            case FAMILIERECHTELIJKE_BETREKKING:
                ot = ElementObjectType.FamilierechtelijkeBetrekking;
                break;
            case NAAMSKEUZE_ONGEBOREN_VRUCHT:
                ot = ElementObjectType.NaamskeuzeOngeborenVrucht;
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return ot;
    }

    @Override
    public int geefObjectSleutel() {
        return getMappedObject().getID();
    }

    @Override
    public List<AbstractGroepMapper> geefGroepMappers() {
        return Arrays.<AbstractGroepMapper>asList(
            new RelatieIdentiteitGroepMapper(),
            new RelatieStandaardGroepMapper()
        );
    }

    @Override
    public List<AbstractObjectMapper> geefObjectMappers() {
        final List<AbstractObjectMapper> objectMappers = new LinkedList<>();
        for (final BetrokkenheidHisVolledigImpl o : getMappedObject().getBetrokkenheden()) {
            if (o.getPersoon().getID() != hoofdpersoonId) {
                objectMappers.add(new ObjectGerelateerdeBetrokkenheidMapper(o));
            }
        }
        return objectMappers;
    }

    private static class RelatieIdentiteitGroepMapper extends AbstractGroepMapper<HisRelatieModel, ObjectRelatieMapper> {
        @Override
        protected ElementGroep geefElementGroep() {
            return ElementGroep.RelatieIdentiteit;
        }

        @Override
        public BrpGroep mapGroep(final ObjectRelatieMapper objectMapper, final BrpObject brpObject) {
            final BrpGroep groep = super.maakGroep(brpObject);
            for (HisRelatieModel model : objectMapper.getMappedObject().getRelatieHistorie()) {
                final BrpGroepVoorkomen voorkomen = maakVoorkomen(groep, model);
                voorkomen.setVoorkomensleutel(model.getID());
            }
            return groep;
        }

        @Override
        protected Iterable<HisRelatieModel> geefHistorieSet(final ObjectRelatieMapper objectMapper) {
            return objectMapper.getMappedObject().getRelatieHistorie();
        }

        @Override
        protected Map<ElementAttribuut, Attribuut> mapAttributen(final HisRelatieModel model) {
            final Map<ElementAttribuut, Attribuut> map = super.mapAttributen(model);
            map.put(ElementAttribuut.RelatieBuitenlandsePlaatsAanvang, model.getBuitenlandsePlaatsAanvang());
            map.put(ElementAttribuut.RelatieBuitenlandsePlaatsEinde, model.getBuitenlandsePlaatsEinde());
            map.put(ElementAttribuut.RelatieBuitenlandseRegioAanvang, model.getBuitenlandseRegioAanvang());
            map.put(ElementAttribuut.RelatieBuitenlandseRegioEinde, model.getBuitenlandseRegioEinde());
            map.put(ElementAttribuut.RelatieGemeenteAanvangCode, model.getGemeenteAanvang());
            map.put(ElementAttribuut.RelatieGemeenteEindeCode, model.getGemeenteEinde());
            return map;
        }
    }

    private static class RelatieStandaardGroepMapper extends AbstractGroepMapper<HisRelatieModel, ObjectRelatieMapper> {
        @Override
        protected ElementGroep geefElementGroep() {
            return ElementGroep.RelatieStandaard;
        }

        @Override
        public BrpGroep mapGroep(final ObjectRelatieMapper objectMapper, final BrpObject brpObject) {
            return null;
        }

        @Override
        protected Iterable<HisRelatieModel> geefHistorieSet(final ObjectRelatieMapper objectMapper) {
            return objectMapper.getMappedObject().getRelatieHistorie();
        }

        @Override
        protected Map<ElementAttribuut, Attribuut> mapAttributen(final HisRelatieModel model) {
            return super.mapAttributen(model);
        }
    }
}
