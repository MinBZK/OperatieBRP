/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model.optional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.xml.context.Context;

/**
 * Referentie helper.
 */
public final class RefsHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String WRITTEN_IDS_DATAKEY = "RefsHelper$WrittenIds";
    private static final String READ_IDS_DATAKEY = "RefsHelper$ReadIds";

    private RefsHelper() {
        // Niet instantieerbaar
    }

    /**
     * Registreer dat een gegeven id van een bepaalde klasse is geschreven.
     * @param context context
     * @param clazz klasse
     * @param id id
     */
    public static void registerWrittenId(final Context context, final Class<?> clazz, final Object id) {
        LOGGER.debug("registerWrittenId(clazz={}, id={})", clazz.getSimpleName(), id);
        getWrittenIds(context).add(new IdIdentifier(clazz, id));
    }

    /**
     * Bepaal of een gegeven id van een bepaalde klasse al is geschreven.
     * @param context context
     * @param clazz klasse
     * @param id id
     * @return true, als deze combinatie van klasse en id al is geregistreerd met {@link #registerWrittenId(Class, Object)}, anders false.
     */
    public static boolean isIdAlreadyWritten(final Context context, final Class<?> clazz, final Object id) {
        LOGGER.debug("isIdAlreadyWritten(clazz={}, id={})", clazz.getSimpleName(), id);
        return getWrittenIds(context).contains(new IdIdentifier(clazz, id));
    }

    /**
     * Registreer dat een gegeven id van een bepaalde klasse is gelezen.
     * @param context context
     * @param clazz klasse
     * @param id id
     * @param object gelezen object
     */
    public static void registerReadId(final Context context, final Class<?> clazz, final Object id, final Object object) {
        LOGGER.debug("registerReadId(clazz={}, id={})", clazz.getSimpleName(), id);
        getReadIds(context).put(new IdIdentifier(clazz, id), object);
    }

    /**
     * Bepaal of een gegevens id van een bepaalde klasse al is gelezen.
     * @param context context
     * @param clazz klasse
     * @param id id
     * @return gelezen object, of null als deze combinatie van klasse en id nog niet is geregistreerd met {@link #registerReadId(Class, Object, Object)}.
     */
    public static Object getReadId(final Context context, final Class<?> clazz, final Object id) {
        LOGGER.debug("getReadId(clazz={}, id={})", clazz.getSimpleName(), id);
        return getReadIds(context).get(new IdIdentifier(clazz, id));
    }

    private static Map<IdIdentifier, Object> getReadIds(final Context context) {
        Map<IdIdentifier, Object> result = (Map<IdIdentifier, Object>) context.getData(READ_IDS_DATAKEY);
        if (result == null) {
            result = new HashMap<>();
            context.setData(READ_IDS_DATAKEY, result);
        }
        return result;
    }

    private static Set<IdIdentifier> getWrittenIds(final Context context) {
        Set<IdIdentifier> result = (Set<IdIdentifier>) context.getData(WRITTEN_IDS_DATAKEY);
        if (result == null) {
            result = new HashSet<>();
            context.setData(WRITTEN_IDS_DATAKEY, result);
        }
        return result;

    }

    /**
     * Id.
     */
    private static class IdIdentifier {
        private final Class<?> clazz;
        private final Object id;

        /**
         * Constructor.
         * @param clazz klasse
         * @param id id
         */
        IdIdentifier(final Class<?> clazz, final Object id) {
            this.clazz = clazz;
            this.id = id;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (clazz == null ? 0 : clazz.hashCode());
            result = prime * result + (id == null ? 0 : id.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final IdIdentifier other = (IdIdentifier) obj;
            if (clazz == null) {
                if (other.clazz != null) {
                    return false;
                }
            } else if (!clazz.equals(other.clazz)) {
                return false;
            }
            if (id == null) {
                if (other.id != null) {
                    return false;
                }
            } else if (!id.equals(other.id)) {
                return false;
            }
            return true;
        }
    }
}
