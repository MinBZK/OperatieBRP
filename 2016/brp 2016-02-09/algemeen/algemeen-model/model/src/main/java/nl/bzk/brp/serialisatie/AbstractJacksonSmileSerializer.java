/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.smile.SmileGenerator;


/**
 * Serialiseerder die gebruik maakt van de <a href="http://jackson.codehaus.org/">Jackson</a> library en het <a
 * href="http://wiki.fasterxml.com/JacksonForSmile">Smile</a> formaat.
 *
 * @param <T> Het type voor de serialisatie
 */
public abstract class AbstractJacksonSmileSerializer<T> extends AbstractJacksonJsonSerializer<T> {

    /**
     * Constructor voor de serializer.
     *
     * @param mappingConfiguratie De mapping configuratie voor de serialisatie.
     */
    public AbstractJacksonSmileSerializer(final SimpleModule mappingConfiguratie) {
        this(mappingConfiguratie, null);
    }

    /**
    /**
     * Constructor voor de serializer.
     *
     * @param mappingConfiguratie De mapping configuratie voor de serialisatie.
     * @param filterProvider de filterprovider
     */
    public AbstractJacksonSmileSerializer(final SimpleModule mappingConfiguratie, final FilterProvider filterProvider) {
        this(createFactory(), mappingConfiguratie, filterProvider);
    }

    public AbstractJacksonSmileSerializer(final JsonFactory factory, final SimpleModule module) {
        this(factory, module, null);
    }

    /**
     * Instantieert een nieuw Abstract jackson smile serializer.
     *
     * @param factory the factory
     * @param module  the module
     * @param filterProvider de filterprovider
     */
    public AbstractJacksonSmileSerializer(final JsonFactory factory, final SimpleModule module, final FilterProvider filterProvider) {
        super(factory, module, filterProvider);
    }

    /**
     * Creeer een SmileFactory.
     *
     * @return factory
     */
    private static JsonFactory createFactory() {
        final SmileFactory factory = new SmileFactory();
        factory.enable(SmileGenerator.Feature.CHECK_SHARED_NAMES);
        factory.enable(SmileGenerator.Feature.CHECK_SHARED_STRING_VALUES);
        factory.enable(SmileGenerator.Feature.WRITE_HEADER);

        return factory;
    }
}
