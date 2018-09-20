/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel.hismapper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementObjectType;

/**
 * Created by dennis on 2/5/16.
 */
class ObjectPersoonMapper extends AbstractObjectMapper<PersoonHisVolledigImpl> {

    /**
     *
     * @param mappedObject
     */
    protected ObjectPersoonMapper(final PersoonHisVolledigImpl mappedObject) {
        super(mappedObject);
    }

    @Override
    public ElementObjectType geefObjectType() {
        return ElementObjectType.Persoon;
    }

    @Override
    public int geefObjectSleutel() {
        return getMappedObject().getID();
    }

    @Override
    public List<AbstractGroepMapper> geefGroepMappers() {
        return Arrays.<AbstractGroepMapper>asList(
            new GroepIndentificatienummerMapper(),
            new GroepGeboorteMapper(),
            new GroepAfgeleidAdministratiefMapper(),
            new IndicatieStaatloosMapper());
    }

    @Override
    public List<AbstractObjectMapper> geefObjectMappers() {
        final List<AbstractObjectMapper> mapperList = new LinkedList<>();
        final Set<BetrokkenheidHisVolledigImpl> betrokkenheden = getMappedObject().getBetrokkenheden();

        //betrokkenheid
        for (BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : betrokkenheden) {
            mapperList.add(new ObjectBetrokkenheidMapper(betrokkenheidHisVolledig));
        }
        //adres
        for (PersoonAdresHisVolledigImpl adresHisVolledig : getMappedObject().getAdressen()) {
            mapperList.add(new ObjectAdresMapper(adresHisVolledig));
        }

        for (PersoonGeslachtsnaamcomponentHisVolledigImpl geslachtnaam : getMappedObject().getGeslachtsnaamcomponenten()) {

        }
        return mapperList;
    }


}
