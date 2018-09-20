/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.entity;

import java.util.List;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BRPActie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Document;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.DocumentHistorie;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObject;

import org.springframework.stereotype.Component;

/**
 * Actie converter.
 */
@Component
public final class ActieConverter extends EntityConverter {

    private static final String TYPE = "kern.actie";
    private static final String DOCUMENT_TYPE = "kern.doc";

    /**
     * Default constructor.
     */
    public ActieConverter() {
        super(TYPE);
    }

    @Override
    public void convert(final DataObject dataObject, final ConverterContext context) {
        if (!TYPE.equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException();
        }

        BRPActie currentActie = null;

        for (int dataIndex = 0; dataIndex < dataObject.getData().size(); dataIndex++) {
            final List<String> dataValues = dataObject.getData().get(dataIndex);

            DocumentHistorie currentDoc = null;

            for (int columnIndex = 0; columnIndex < dataObject.getHeaders().size(); columnIndex++) {
                final String header = dataObject.getHeaders().get(columnIndex);
                final String dataValue = getValue(dataValues, columnIndex);

                if (isEmpty(dataValue)) {
                    continue;
                }

                if (TYPE.equals(header)) {
                    currentActie = new BRPActie();
                    context.storeActie(Integer.valueOf(dataValue), currentActie);
                } else if (DOCUMENT_TYPE.equals(header)) {
                    currentDoc = new DocumentHistorie();
                } else {
                    if (currentDoc != null && "srt".equals(header)) {
                        final Document doc = new Document();
                        doc.addDocumentHistorie(currentDoc);
                        setJPAColumn(context, doc, header, dataValue);
                        currentActie.addDocument(doc);
                    } else {
                        setJPAColumn(context, currentDoc == null ? currentActie : currentDoc, header, dataValue);
                    }
                }
            }
        }
    }
}
