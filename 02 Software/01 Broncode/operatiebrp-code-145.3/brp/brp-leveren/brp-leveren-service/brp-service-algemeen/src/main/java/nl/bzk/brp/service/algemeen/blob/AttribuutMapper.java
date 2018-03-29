/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.function.BiConsumer;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.DatabaseType;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Mapper voor attributen op het MetaRecord.Builder object.
 */
final class AttribuutMapper implements BiConsumer<MetaRecord.Builder, BlobRecord> {

    /**
     * Herbruikbare thread-safe instantie van deze klasse.
     */
    public static final AttribuutMapper INSTANCE = new AttribuutMapper();

    @Override
    public void accept(final MetaRecord.Builder builder, final BlobRecord blobRecord) {
        builder.metId(blobRecord.getVoorkomenSleutel());
        if (blobRecord.getAttributen() != null) {
            for (final Map.Entry<Integer, Object> entry : blobRecord.getAttributen().entrySet()) {
                final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(entry.getKey());
                final Object attrValue;
                if (entry.getValue() instanceof Number) {
                    attrValue = bepaalWaardeVanNummer(entry, attribuutElement);
                } else {
                    attrValue = entry.getValue();
                }
                builder.metAttribuut(attribuutElement, attrValue);
            }
        }
    }

    private Object bepaalWaardeVanNummer(final Map.Entry<Integer, Object> entry, final AttribuutElement attribuutElement) {
        final Object attrValue;
        final Number waardeParamNumber = (Number) entry.getValue();
        final Element dataBaseInfo = attribuutElement.getElement();
        final DatabaseType typeidentdb = dataBaseInfo.getElementWaarde().getTypeidentdb();
        if (DatabaseType.SMALLINT == typeidentdb
                || DatabaseType.INTEGER == typeidentdb) {
            //shorts converteren we ook naar integers, deze worden niet altijd correct gemapt in enities.
            attrValue = waardeParamNumber.intValue();
        } else if (DatabaseType.BIGINT == typeidentdb) {
            attrValue = waardeParamNumber.longValue();
        } else if (DatabaseType.TIMESTAMP == typeidentdb) {
            attrValue = converteerNaarZonedDateTime(waardeParamNumber.longValue());
        } else {
            throw new IllegalStateException(
                    String.format("Ongeldig datatype voor element (%d) met waarde %s ", attribuutElement.getId(), entry.getValue()));
        }
        return attrValue;
    }

    private ZonedDateTime converteerNaarZonedDateTime(final Long time) {
        if (time == null) {
            return null;
        }
        return DatumUtil.vanLongNaarZonedDateTime(time);
    }
}
