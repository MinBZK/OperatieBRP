/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.serialisatie.AbstractJacksonSmileSerializer;
import nl.bzk.brp.serialisatie.JacksonJsonSerializer;


/**
 * Smile serializer voor de persoonHisVolledig.
 */
public class PersoonHisVolledigSmileSerializer
        implements PersoonHisVolledigSerializer, JacksonJsonSerializer<PersoonHisVolledigImpl>
{

    private final JacksonJsonSerializer<PersoonHisVolledigImplWrapper> serializer;

    /**
     * Default constructor die de juiste mapping configuratie meegeeft.
     */
    public PersoonHisVolledigSmileSerializer() {
        this.serializer =
            new AbstractJacksonSmileSerializer<PersoonHisVolledigImplWrapper>(
                new PersoonHisVolledigMappingConfiguratieModule(), new IdPropertyFilterProvider()) {
            };
    }

    /**
     * Constructor die de mogelijkheid geeft de serializer aan te passen met een nieuwe configuratie en factory.
     *
     * @param factory de jsonfactory
     * @param module de configuratie module
     * @param filterProvider de filter provider
     */
    protected PersoonHisVolledigSmileSerializer(final JsonFactory factory, final SimpleModule module, final FilterProvider filterProvider) {
        this.serializer = new AbstractJacksonSmileSerializer<PersoonHisVolledigImplWrapper>(factory, module, filterProvider) { };
    }

    @Override
    public final byte[] serialiseer(final PersoonHisVolledigImpl object) {
        final PersoonHisVolledigImplWrapper wrapper = new PersoonHisVolledigImplWrapper(object);
        return serializer.serialiseer(wrapper);
    }

    @Override
    public final PersoonHisVolledigImpl deserialiseer(final byte[] bytes) {
        final PersoonHisVolledigImplWrapper wrapper = serializer.deserialiseer(bytes);
        return wrapper.unwrap();
    }
}
