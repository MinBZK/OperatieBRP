/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import org.springframework.util.Assert;

/**
 * DeliveryReport according to the sPd-protocol.
 */
public final class DeliveryReport implements OperationRecord {

    private static final Pattern PATTERN = Pattern.compile("^(.{11})(.{9})(.{3})((.{7})(.{11})(.{4}))+$");
    private static final int TIME_GROUP = 1;
    private static final int SEQUENCE_NUMBER_GROUP = 2;
    private static final int NR_OF_RECIP_GROUP = 3;
    private static final int RECIPIENT_GROUP = 5;
    private static final int NON_DELIVERY_REASON_GROUP = 7;

    private static final int SEQUENCE_NUMBER_LENGTH = 9;
    private static final int NR_OF_RECIP_LENGTH = 3;
    private static final int RECIPIENT_OR_NAME_LENGTH = 7;
    private static final int NON_DELIVERY_REASON_LENGTH = 4;

    private final InstantField reportDeliveryTime = (InstantField) InstantField.builder().name("ReportedDeliveryTime").build();
    private final NumericField dispatchSequenceNumber =
            (NumericField) NumericField.builder().name("DispatchSequenceNumber").length(SEQUENCE_NUMBER_LENGTH).build();
    private final NumericField numberOfRecipients = (NumericField) NumericField.builder().name("NumberOfRecipients").length(NR_OF_RECIP_LENGTH).build();
    private final StringField reportedRecipientORName =
            (StringField) StringField.builder().name("ReportedRecipientORName").length(RECIPIENT_OR_NAME_LENGTH).build();
    private final InstantField messageDeliveryTime = (InstantField) InstantField.builder().name("MessageDeliveryTime").optional().build();
    private final StringField nonDeliveryReason = (StringField) StringField.builder().name("NonDeliveryReason").length(NON_DELIVERY_REASON_LENGTH).build();

    private DeliveryReport(
            final Instant reportDeliveryTime,
            final String dispatchSequenceNumber,
            final Integer numberOfRecipients,
            final String reportedRecipientORName,
            final Instant messageDeliveryTime,
            final String nonDeliveryReason) throws SpdProtocolException {
        this.reportDeliveryTime.setValue(reportDeliveryTime);
        this.dispatchSequenceNumber.setRawValue(dispatchSequenceNumber);
        this.numberOfRecipients.setValue(numberOfRecipients);
        this.reportedRecipientORName.setRawValue(reportedRecipientORName);
        this.messageDeliveryTime.setValue(messageDeliveryTime);
        this.nonDeliveryReason.setRawValue(nonDeliveryReason);

        if (this.numberOfRecipients.getValue() != 1) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_DELREP_NR_RECIPIENTS);
        }

        invariant();
    }

    private DeliveryReport(
            final String reportDeliveryTime,
            final String dispatchSequenceNumber,
            final String numberOfRecipients,
            final String reportedRecipientORName,
            final String nonDeliveryReason) throws SpdProtocolException {
        this.reportDeliveryTime.setRawValue(reportDeliveryTime);
        this.dispatchSequenceNumber.setRawValue(dispatchSequenceNumber);
        this.numberOfRecipients.setRawValue(numberOfRecipients);
        this.reportedRecipientORName.setRawValue(reportedRecipientORName);
        this.nonDeliveryReason.setRawValue(nonDeliveryReason);

        if (this.numberOfRecipients.getValue() != 1) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_DELREP_NR_RECIPIENTS);
        }

        invariant();
    }

    /**
     * Factory method for creating a StatusReport from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return StatusReport object
     * @throws SpdProtocolException if it is not possible to build the DeliveryReport
     */
    public static DeliveryReport fromOperationItems(final String operationItems) throws SpdProtocolException {
        final Matcher matcher = PATTERN.matcher(operationItems);
        Assert.isTrue(matcher.matches(), String.format("operationItems: '%s' does not match message structure.", operationItems));
        return new DeliveryReport(
                matcher.group(TIME_GROUP),
                matcher.group(SEQUENCE_NUMBER_GROUP),
                matcher.group(NR_OF_RECIP_GROUP),
                matcher.group(RECIPIENT_GROUP),
                matcher.group(NON_DELIVERY_REASON_GROUP));
    }

    public Instant getReportDeliveryTime() {
        return reportDeliveryTime.getValue();
    }

    public int getDispatchSequenceNumber() {
        return dispatchSequenceNumber.getValue();
    }

    public int getNumberOfRecipients() {
        return numberOfRecipients.getValue();
    }

    public String getReportedRecipientORName() {
        return reportedRecipientORName.getValue();
    }

    public String getNonDeliveryReason() {
        return nonDeliveryReason.getValue();
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_DELIVERY_REPORT;
    }

    @Override
    public void vulBericht(final Bericht bericht) throws SpdProtocolException {
        bericht.setTijdstipMailbox(Timestamp.from(getReportDeliveryTime()));
        bericht.setDispatchSequenceNumber(getDispatchSequenceNumber());
        bericht.setOriginator(getReportedRecipientORName());
        bericht.setNonDeliveryReason(getNonDeliveryReason());
    }

    /**
     * Builder for DeliveryReport objects.
     */
    public static final class Builder {
        private Instant reportDeliveryTime;
        private String dispatchSequenceNumber;
        private Integer numberOfRecipients;
        private String reportedRecipientORName;
        private Instant messageDeliveryTime;
        private String nonDeliveryReason;

        /**
         * Set reportDeliveryTime.
         * @param value reportDeliveryTime
         * @return this builder
         */
        public Builder reportDeliveryTime(final Instant value) {
            this.reportDeliveryTime = value;
            return this;
        }

        /**
         * Set dispatchSequenceNumber.
         * @param value dispatchSequenceNumber
         * @return this builder
         */
        public Builder dispatchSequenceNumber(final String value) {
            this.dispatchSequenceNumber = value;
            return this;
        }

        /**
         * Set numberOfRecipients.
         * @param value numberOfRecipients
         * @return this builder
         */
        public Builder numberOfRecipients(final int value) {
            this.numberOfRecipients = value;
            return this;
        }

        /**
         * Set reportedRecipientORName.
         * @param value reportedRecipientORName
         * @return this builder
         */
        public Builder reportedRecipientORName(final String value) {
            this.reportedRecipientORName = value;
            return this;
        }

        /**
         * Set messageDeliveryTime.
         * @param value messageDeliveryTime
         * @return this builder
         */
        public Builder messageDeliveryTime(final Instant value) {
            this.messageDeliveryTime = value;
            return this;
        }

        /**
         * Set nonDeliveryReason.
         * @param value nonDeliveryReason
         * @return this builder
         */
        public Builder nonDeliveryReason(final String value) {
            this.nonDeliveryReason = value;
            return this;
        }

        /**
         * Build StatusReport object.
         * @return StatusReport object
         * @throws SpdProtocolException if it is not possible to build the DeliveryReport
         */
        public DeliveryReport build() throws SpdProtocolException {
            return new DeliveryReport(
                    reportDeliveryTime,
                    dispatchSequenceNumber,
                    numberOfRecipients,
                    reportedRecipientORName,
                    messageDeliveryTime,
                    nonDeliveryReason);
        }
    }
}
