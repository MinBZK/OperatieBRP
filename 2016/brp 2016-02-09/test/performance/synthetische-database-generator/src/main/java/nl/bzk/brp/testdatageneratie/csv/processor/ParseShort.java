/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.processor;

import nl.bzk.brp.testdatageneratie.utils.Constanten;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.LongCellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

/**
 * Parse short.
 */
public class ParseShort extends CellProcessorAdaptor implements StringCellProcessor {
    /**
     * Constructs a new <tt>ParseShort</tt> processor, which converts a String to an Short.
     */
    public ParseShort() {
        super();
    }

    /**
     * Constructs a new <tt>ParseShort</tt> processor, which converts a String to an Short, then calls the next
     * processor in the chain.
     *
     * @param next
     *            the next processor in the chain
     * @throws NullPointerException
     *             if next is null
     */
    public ParseShort(final LongCellProcessor next) {
        super(next);
    }

    /**
     * {@inheritDoc}
     *
     * @throws SuperCsvCellProcessorException
     *             if value is null, isn't an Integer or String, or can't be parsed as an Integer
     */
    @Override
    public Object execute(final Object value, final CsvContext context) {
        validateInputNotNull(value, context);

        final Short result;
        if (value instanceof Short) {
            result = (Short) value;
        } else if (value instanceof String) {
            try {
                result = Short.valueOf((String) value, Constanten.TIEN);
            }
            catch (final NumberFormatException e) {
                throw new SuperCsvCellProcessorException(
                    String.format("'%s' could not be parsed as an Integer", value), context, this, e);
            }
        } else {
            final String actualClassName = value.getClass().getName();
            throw new SuperCsvCellProcessorException(String.format(
                "the input value should be of type Integer or String but is of type %s", actualClassName), context,
                this);
        }

        return next.execute(result, context);
    }

}
