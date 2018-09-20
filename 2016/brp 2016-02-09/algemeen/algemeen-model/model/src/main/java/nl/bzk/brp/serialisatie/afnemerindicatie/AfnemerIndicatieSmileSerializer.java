/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.afnemerindicatie;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;
import java.io.IOException;
import java.util.Set;
import nl.bzk.brp.model.hisvolledig.SerialisatieExceptie;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.serialisatie.AbstractJacksonSmileSerializer;


/**
 * Smile serializer voor sets van {@link nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl}.
 */
public class AfnemerIndicatieSmileSerializer extends
    AbstractJacksonSmileSerializer<Set<PersoonAfnemerindicatieHisVolledigImpl>> implements
    AfnemerIndicatieSerializer
{

    /**
     * Default constructor.
     */
    public AfnemerIndicatieSmileSerializer() {
        super(new AfnemerIndicatieMappingConfiguratieModule());
    }

    /**
     * Constructor met Jackson modules.
     *
     * @param factory de jsonFactory
     * @param module module met configuratie voor deze serializer
     */
    public AfnemerIndicatieSmileSerializer(final JsonFactory factory, final SimpleModule module) {
        super(factory, module);
    }

    @Override
    public final Set<PersoonAfnemerindicatieHisVolledigImpl> deserialiseer(final byte[] bytes) {
        try {
            return super.deserialiseer(bytes, CollectionType.construct(Set.class, SimpleType.construct(PersoonAfnemerindicatieHisVolledigImpl.class)));
        } catch (final IOException e) {
            throw new SerialisatieExceptie("Het deserialiseren van de set afnemerindicaties is mislukt", e);
        }
    }

}
