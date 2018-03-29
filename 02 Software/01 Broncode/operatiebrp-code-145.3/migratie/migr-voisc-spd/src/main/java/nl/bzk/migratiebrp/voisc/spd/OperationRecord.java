/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import org.springframework.util.Assert;

/**
 * Represents an OperationRecord as described in the sPd-protocol.
 */
public interface OperationRecord extends SpdElement {

    /**
     * The length of an operation record. This includes the operation code and all fields, but excludes the length field
     * itself.
     * @return the length
     */
    default int length() {
        return operationCodeField().length() + toSpdString().length();
    }

    /**
     * Class invariant.
     */
    default void invariant() {
        fields().forEach(Field::validate);
        final int expected = fields().stream().mapToInt(Field::length).sum() + operationCodeField().length();
        Assert.isTrue(
                length() == expected,
                String.format("Invariant of class %s failed. Expected length: %d, actual: %d", this.getClass(), expected, length()));
    }

    /**
     * The length field of the operation record.
     * @return a fixed-length String containing the length of the fields of this operation record left-padded with zeros.
     */
    default String lengthField() {
        return String.format(String.format("%%0%dd", SpdConstants.LENGTH_LENGTH), length());
    }

    /**
     * The operation code field of the operation record.
     * @return a fixed-length String containing the operation code for this record left-padded with zeros.
     */
    default String operationCodeField() {
        return String.format(String.format("%%0%dd", SpdConstants.OPCODE_LENGTH), operationCode());
    }

    /**
     * The operation code.
     * @return an integer representing the operation code
     */
    int operationCode();

    /**
     * The String representation of this operation record in the form of the concatenation of the operation items.
     * @return String representation of this operation record.
     */
    default String toSpdString() {
        return fields().stream().map(Field::toSpdString).reduce("", String::concat);
    }

    /**
     * Default implementation where current object is passed to the visitor.
     * @param visitor visitor
     * @param <V> type of return value
     * @return result of visit
     */
    @Override
    default <V> V accept(SpdElementVisitor<V> visitor) {
        return visitor.visit(this);
    }

    /**
     * Returns a list of all Field objects of the implementing class.
     * Returning a list of fields with generic wilcard type is intentional. It really only should be used internally here.
     * @return a list of all Field objects of the implementing class
     */
    default Collection<Field<?>> fields() {
        return Collections
                .unmodifiableList(Stream.of(this.getClass().getDeclaredFields()).filter(field -> Field.class.isAssignableFrom(field.getType())).map(field -> {
                    field.setAccessible(true);
                    return field;
                }).map(field -> {
                    try {
                        return (Field<?>) field.get(this);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                }).collect(Collectors.toList()));
    }

    /**
     * Gooit een SPD protocol exceptie met het meegegeven bericht.
     * @param bericht het bericht
     * @throws SpdProtocolException De te gooien SPD protocol exceptie
     */
    default void vulBericht(final Bericht bericht) throws SpdProtocolException {
        throw new SpdProtocolException(
                MessagesCodes.ERRMSG_VOSPG_SPD_GETMESSAGE_MSENTRY_OPCODE,
                new Object[]{operationCode()},
                null,
                new Bericht());
    }
}
