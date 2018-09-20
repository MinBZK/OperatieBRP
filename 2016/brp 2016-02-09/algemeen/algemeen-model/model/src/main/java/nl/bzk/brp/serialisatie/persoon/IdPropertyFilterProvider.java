/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;


/**
 * FilterProvider die we in Jackson kunnen gebruiken om van geselecteerde typen alleen de property 'iD' te serialiseren.
 * Deze typen kun je selecteren door klassen of property's te annoteren met @JsonFilter met als parameter de naam van het filter.
 */
public final class IdPropertyFilterProvider extends SimpleFilterProvider {

    /**
     * Standaard constructor die de juiste filter zet.
     */
    public IdPropertyFilterProvider() {
        addFilter(IdPropertyFilter.NAAM, new IdPropertyFilter());
    }

    /**
     * Een Jackson BeanPropertyFilter dat zorgt dat alleen velden met de naam "iD" worden geserialiseerd.
     * De overige property's worden genegeerd.
     */
    public static final class IdPropertyFilter extends SimpleBeanPropertyFilter {

        /**
         * De naam die gebruikt wordt om aan dit filter te refereren.
         */
        public static final String NAAM = "persoonIdFilter";
        private static final String ID_PROPERTY_NAME = "iD";

        @Override
        protected boolean include(final BeanPropertyWriter beanPropertyWriter) {
            return false;
        }

        @Override
        protected boolean include(final PropertyWriter propertyWriter) {
            return ID_PROPERTY_NAME.equals(propertyWriter.getName());
        }
    }
}
