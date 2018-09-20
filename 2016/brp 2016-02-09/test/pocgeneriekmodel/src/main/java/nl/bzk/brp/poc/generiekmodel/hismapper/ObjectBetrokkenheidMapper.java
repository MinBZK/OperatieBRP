/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel.hismapper;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementObjectType;

/**
 * Created by dennis on 2/5/16.
 */
final class ObjectBetrokkenheidMapper extends AbstractObjectMapper<BetrokkenheidHisVolledigImpl> {

    /**
     *
     * @param mappedObject de betrokkenheid.
     */
    protected ObjectBetrokkenheidMapper(final BetrokkenheidHisVolledigImpl mappedObject) {
        super(mappedObject);
    }

    @Override
    public ElementObjectType geefObjectType() {
        final ElementObjectType ot;
        switch (getMappedObject().getRol().getWaarde()) {
            case KIND:
                ot = ElementObjectType.Kind;
                break;
            case ERKENNER:
                ot = ElementObjectType.Erkenner;
                break;
            case INSTEMMER:
                ot = ElementObjectType.Instemmer;
                break;
            case NAAMGEVER:
                ot = ElementObjectType.Naamgever;
                break;
            case OUDER:
                ot = ElementObjectType.Ouder;
                break;
            case PARTNER:
                ot = ElementObjectType.Partner;
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
        return null;
    }

    @Override
    public List<AbstractObjectMapper> geefObjectMappers() {
        return Collections.<AbstractObjectMapper>singletonList(
            new ObjectRelatieMapper(getMappedObject().getRelatie(), getMappedObject().getPersoon().getID())
        );
    }
}
