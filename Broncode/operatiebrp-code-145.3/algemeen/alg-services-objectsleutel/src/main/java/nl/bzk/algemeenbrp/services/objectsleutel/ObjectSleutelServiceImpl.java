/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.objectsleutel;

import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link ObjectSleutelService}.
 */
@Service
public final class ObjectSleutelServiceImpl implements ObjectSleutelService {

    @Override
    public ObjectSleutel maakPersoonObjectSleutel(final long persoonId, final long versienummer) {
        return new ObjectSleutel(persoonId, ObjectSleutel.ObjectSleutelSoort.PERSOON, versienummer);
    }

    @Override
    public ObjectSleutel maakPersoonObjectSleutel(final String gemaskeerdeObjectSleutel) throws OngeldigeObjectSleutelException {
        return ObjectSleutel.ontmaskeren(gemaskeerdeObjectSleutel, ObjectSleutel.ObjectSleutelSoort.PERSOON);
    }

    @Override
    public ObjectSleutel maakRelatieObjectSleutel(final long relatieId) {
        return new ObjectSleutel(relatieId, ObjectSleutel.ObjectSleutelSoort.RELATIE, null);
    }

    @Override
    public ObjectSleutel maakRelatieObjectSleutel(final String gemaskeerdeObjectSleutel) throws OngeldigeObjectSleutelException {
        return ObjectSleutel.ontmaskeren(gemaskeerdeObjectSleutel, ObjectSleutel.ObjectSleutelSoort.RELATIE);
    }

    @Override
    public ObjectSleutel maakBetrokkenheidObjectSleutel(final long betrokkenheidId) {
        return new ObjectSleutel(betrokkenheidId, ObjectSleutel.ObjectSleutelSoort.BETROKKENHEID, null);
    }

    @Override
    public ObjectSleutel maakBetrokkenheidObjectSleutel(final String gemaskeerdeObjectSleutel) throws OngeldigeObjectSleutelException {
        return ObjectSleutel.ontmaskeren(gemaskeerdeObjectSleutel, ObjectSleutel.ObjectSleutelSoort.BETROKKENHEID);
    }

    @Override
    public ObjectSleutel maakOnderzoekObjectSleutel(long onderzoekId) {
        return new ObjectSleutel(onderzoekId, ObjectSleutel.ObjectSleutelSoort.ONDERZOEK, null);
    }

    @Override
    public ObjectSleutel maakOnderzoekObjectSleutel(String gemaskeerdeObjectSleutel) throws OngeldigeObjectSleutelException {
        return ObjectSleutel.ontmaskeren(gemaskeerdeObjectSleutel, ObjectSleutel.ObjectSleutelSoort.ONDERZOEK);
    }
}
