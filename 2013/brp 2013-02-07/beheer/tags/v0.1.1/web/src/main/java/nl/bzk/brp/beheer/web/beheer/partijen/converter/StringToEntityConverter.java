/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.converter;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.beheer.web.beheer.stamgegevens.service.StamgegevensService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;


/**
 * Deze class zorgt voor het converteren van form binding strings naar entity en andersom.
 *
 */
@Component("stringToEntityConverter")
public class StringToEntityConverter implements GenericConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringToEntityConverter.class);

    @Autowired
    private StamgegevensService stamgegevensService;

    private String[]            entitiesToConvert;

    // Declares the list of supported conversions
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> pairs = new HashSet<ConvertiblePair>();

        for (String entity : entitiesToConvert) {
            try {
                pairs.add(new ConvertiblePair(String.class, Class.forName(entity)));
                pairs.add(new ConvertiblePair(Class.forName(entity), String.class));
            } catch (ClassNotFoundException e) {
                LOGGER.error(e.getMessage());
                throw new IllegalArgumentException("Kan class niet mappen!", e);
            }
        }

        return pairs;
    }

    @Override
    public Object convert(final Object source, final TypeDescriptor sourceType, final TypeDescriptor targetType) {

        Object result = null;
        if (sourceType.getObjectType().equals(String.class)) {
            // Convert String to Object
            String id = (String) source;

            if (!StringUtils.isBlank(id)) {
                // From String to ListItem
                try {
                    result = stamgegevensService.find(targetType.getObjectType(), Integer.parseInt(id));
                } catch (ObjectNotFoundException e) {
                    throw new IllegalArgumentException("Item '" + targetType + "' met id '" + source
                        + "' kan niet gevonden worden!", e);
                }
            }
        } else if (targetType.getObjectType().equals(String.class)) {
            // Convert from Object to String

            if (source == null) {
                result = "";
            } else {
                // From ListItem to String

                try {
                    result = sourceType.getObjectType().getMethod("getId").invoke(source);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return result;
    }

    public void setEntitiesToConvert(final String[] entitiesToConvert) {
        this.entitiesToConvert = entitiesToConvert;
    }


    public void setStamgegevensService(final StamgegevensService stamgegevensService) {
        this.stamgegevensService = stamgegevensService;
    }
}
