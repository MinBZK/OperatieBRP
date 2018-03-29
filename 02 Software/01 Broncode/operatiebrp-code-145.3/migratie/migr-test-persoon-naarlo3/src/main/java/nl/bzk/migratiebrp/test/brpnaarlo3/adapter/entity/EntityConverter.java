/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.DataObject;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.DataObjectConverter;

/**
 * Basis converter.
 */
public abstract class EntityConverter implements DataObjectConverter {
    protected static final String HEADER_SOORT = "srt";
    protected static final String HEADER_PARTIJ = "partij";
    protected static final String HEADER_PERSOON = "persId";
    protected static final String HEADER_BETROKKENHEID = "betrokkenheidId";
    protected static final String HEADER_DATUM_AANVANG_GELDIGHEID = "dataanvgel";
    protected static final String HEADER_DATUM_EINDE_GELDIGHEID = "dateindegel";
    protected static final String HEADER_TIJDSTIP_REGISTRATIE = "tsreg";
    protected static final String HEADER_TIJDSTIP_VERVAL = "tsverval";
    protected static final String HEADER_ACTIE_INHOUD = "actieinh";
    protected static final String HEADER_ACTIE_VERVAL = "actieverval";
    protected static final String HEADER_ACTIE_AANPASSING_GELDIGHEID = "actieaanpgel";

    private final String type;

    /**
     * Constructor.
     * @param type type die deze converter kan converteren
     */
    protected EntityConverter(final String type) {
        this.type = type;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.brpnaarlo3.adapter.DataObjectConverter#getType()
     */
    @Override
    public final String getType() {
        return type;
    }

    @Override
    public final void convert(final DataObject dataObject, final ConverterContext context) {
        if (!getType().equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException();
        }

        final List<List<String>> data = dataObject.getData();
        final List<String> headers = dataObject.getHeaders();

        for (int dataIndex = 0; dataIndex < data.size(); dataIndex++) {
            final List<String> dataValues = data.get(dataIndex);
            resetConverter();
            if (!dataValues.isEmpty()) {
                for (int columnIndex = 0; columnIndex < headers.size(); columnIndex++) {
                    final String header = headers.get(columnIndex);
                    final String dataValue = getValue(dataValues, columnIndex);

                    if (isEmpty(dataValue)) {
                        continue;
                    }
                    convertInhoudelijk(context, header, dataValue);
                }
                maakEntity(context);
            }
        }
    }

    /**
     * Converteert inhoudelijk het gegeven.
     * @param context Converter context
     * @param header welke veld gaan we een waarde aan toekennen
     * @param value de waarde
     */
    protected abstract void convertInhoudelijk(final ConverterContext context, final String header, final String value);

    /**
     * Maakt de entity die geconverteerd wordt.
     * @param context converter context waar de entity mogelijk in opgeslagen wordt (bv Acties en Personen)
     */
    protected abstract void maakEntity(final ConverterContext context);

    /**
     * Reset de converter zodat de volgende {@link DataObject} geconverteerd kan worden.
     */
    protected abstract void resetConverter();

    /**
     * Maakt een {@link Timestamp} aan op basis van een datum/tijd.
     * @param value datum/tijd dat in het DataObject is opgeslagen
     * @return een {@link Timestamp}
     */
    protected final Timestamp maakTimestamp(final String value) {
        final BrpDatumTijd brpDatumTijd = BrpDatumTijd.fromDatumTijd(Long.valueOf(value), null);
        return new Timestamp(brpDatumTijd.getJavaDate().getTime());
    }

    /**
     * Bepaal of een string een waarde heeft (dus niet null en niet leeg is).
     * @param value string
     * @return true als de string een waar heeft
     */
    protected final boolean isEmpty(final String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Geeft de waarde terug uit een lijst. Als de index groter is dan de lengte van de lijst, dan wordt er null
     * geretourneerd.
     * @param values list
     * @param index index
     * @return waarde of index (of null)
     */
    protected final String getValue(final List<String> values, final int index) {
        return index < values.size() ? values.get(index) : null;

    }

    /**
     * Geeft de naam van de converter.
     * @return de naam van de converter
     */
    public final String getName() {
        final String fullName = this.getClass().getName();
        return fullName.substring(fullName.lastIndexOf(".") + 1);
    }
}
