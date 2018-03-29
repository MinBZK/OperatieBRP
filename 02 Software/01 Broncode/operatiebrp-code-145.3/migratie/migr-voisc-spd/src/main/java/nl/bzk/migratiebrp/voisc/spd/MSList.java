/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.util.Assert;

/**
 * MSList according to the sPd-protocol.
 */
public final class MSList implements OperationRecord {

    private static final Pattern PATTERN = Pattern.compile("^(.{5})((.{9}...{11}.{7})+)$");
    private static final int NUMBER_GROUP = 1;
    private static final int ENTRIES_GROUP = 2;

    private static final int NR_OF_ENTRIES_LENGTH = 5;
    private static final int MS_ENTRY_LENGTH = 29;

    private final List<MSEntry> msEntries;
    private final NumericField numberofMSEntries = (NumericField) NumericField.builder().name("NumberofMSEntries").length(NR_OF_ENTRIES_LENGTH).build();

    /**
     * Constructor.
     * @param msEntries list of MSList.MSEntry objects
     */
    private MSList(final List<MSEntry> msEntries) {
        Assert.isTrue(!msEntries.isEmpty(), "number of MSEntry objects should be a positive number.");
        this.msEntries = new ArrayList<>(msEntries);
        numberofMSEntries.setValue(msEntries.size());
        invariant();
    }

    /**
     * Factory method for creating an MSList from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return MSList object
     */
    public static MSList fromOperationItems(final String operationItems) {
        final Matcher matcher = PATTERN.matcher(operationItems);
        Assert.isTrue(matcher.matches(), String.format("operationItems: '%s' does not match message structure.", operationItems));

        final int length = Integer.parseInt(matcher.group(NUMBER_GROUP));
        final String entries = matcher.group(ENTRIES_GROUP);

        Assert.isTrue(
                (MSEntry.operationItemsLength() * length) == entries.length(),
                String.format("actual number of MSEntry objects (%d) does not match expected number (%d)", entries.length(), length));

        return new MSList(IntStream.range(0, length)
                .boxed()
                .map(i -> entries.substring(i * MSEntry.operationItemsLength(), (i + 1) * MSEntry.operationItemsLength()))
                .map(MSEntry::fromOperationItems)
                .collect(Collectors.toList()));
    }

    @Override
    public Collection<Field<?>> fields() {
        final StringField field = (StringField) StringField.builder().length(MS_ENTRY_LENGTH).optional().build();
        return Stream.concat(Collections.singletonList(numberofMSEntries).stream(), IntStream.range(0, size()).boxed().map(i -> field))
                .collect(Collectors.toList());
    }

    /**
     * Returns the number of MSEntries in this list.
     * @return the number of MSEntries in this list
     */
    public int size() {
        return msEntries.size();
    }

    /**
     * Returns the MSEntries in this list.
     * @return the MSEntries in this list
     */
    public Collection<MSEntry> entries() {
        return Collections.unmodifiableCollection(msEntries);
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_MS_LIST;
    }

    @Override
    public String toSpdString() {
        return String.format("%05d", msEntries.size()) + msEntries.stream().map(MSEntry::toSpdString).reduce("", String::concat);
    }

    /**
     * MSEntry.
     */
    public static final class MSEntry {
        private static final Pattern PATTERN = Pattern.compile("^(.{9})(.)(.)(.{11})(.{7})$");
        private static final int NUMBER_GROUP = 1;
        private static final int STATUS_GROUP = 2;
        private static final int PRIORITY_GROUP = 3;
        private static final int TIME_GROUP = 4;
        private static final int ORIGINATOR_GROUP = 5;

        private static final int MSL_MSSEQUENCE_NUMBER_LEN = 9;
        private static final int MSL_MSSTATUS_LEN = 1;
        private static final int MSL_PRIORITY_LEN = 1;
        private static final int MSL_DELIVERY_TIME_LEN = 11;
        private static final int MSL_ORIGINATOR_OR_NAME_LEN = 7;

        private final int msSequenceNumber;
        private final SpdConstants.MSStatus msStatus;
        private final SpdConstants.Priority priority;
        private final Instant deliveryTime;
        private final String originatorORName;

        /**
         * Constructor.
         * @param msSequenceNumber msSequenceNumber
         * @param msStatus msStatus
         * @param priority priority
         * @param deliveryTime deliveryTime
         * @param originatorORName originatorORName
         */
        MSEntry(
                final int msSequenceNumber,
                final SpdConstants.MSStatus msStatus,
                final SpdConstants.Priority priority,
                final Instant deliveryTime,
                final String originatorORName) {
            this.msSequenceNumber = msSequenceNumber;
            this.msStatus = msStatus;
            this.priority = priority;
            this.deliveryTime = deliveryTime;
            this.originatorORName = originatorORName;
        }

        private MSEntry(
                final String msSequenceNumber,
                final String msStatus,
                final String priority,
                final String deliveryTime,
                final String originatorORName) {
            Assert.isTrue(originatorORName != null && originatorORName.length() == MSL_ORIGINATOR_OR_NAME_LEN, "originatorORName should have length 7");

            this.msSequenceNumber = Integer.parseInt(msSequenceNumber);
            this.msStatus = SpdConstants.MSStatus.fromCode(Integer.parseInt(msStatus)).orElseThrow(() -> new IllegalArgumentException("illegal msStatus"));
            this.priority = SpdConstants.Priority.fromCode(Integer.parseInt(priority)).orElseThrow(() -> new IllegalArgumentException("illegal priority"));
            this.deliveryTime = SpdTimeConverter.convertSpdTimeStringToInstant(deliveryTime);
            this.originatorORName = originatorORName;
        }

        /**
         * Factory method for creating an MSEntry from the concatenation of operation items.
         * @param operationItems concatenated string of operation items
         * @return MSEntry object
         */
        public static MSEntry fromOperationItems(final String operationItems) {
            final Matcher matcher = PATTERN.matcher(operationItems);
            Assert.isTrue(matcher.matches(), String.format("operationItems: '%s' does not match message structure.", operationItems));
            return new MSEntry(
                    matcher.group(NUMBER_GROUP),
                    matcher.group(STATUS_GROUP),
                    matcher.group(PRIORITY_GROUP),
                    matcher.group(TIME_GROUP),
                    matcher.group(ORIGINATOR_GROUP));
        }

        private static int operationItemsLength() {
            return MSL_MSSEQUENCE_NUMBER_LEN + MSL_MSSTATUS_LEN + MSL_PRIORITY_LEN + MSL_DELIVERY_TIME_LEN + MSL_ORIGINATOR_OR_NAME_LEN;
        }

        public int getMsSequenceNumber() {
            return msSequenceNumber;
        }

        private String toSpdString() {
            return String.format("%09d", msSequenceNumber)
                    + msStatus.code()
                    + priority.code()
                    + SpdTimeConverter.convertInstantToSpdTimeString(deliveryTime)
                    + originatorORName;
        }

        /**
         * Builder for MSList objects.
         */
        public static final class Builder {
            private int msSequenceNumber;
            private SpdConstants.MSStatus msStatus;
            private SpdConstants.Priority priority;
            private Instant deliveryTime;
            private String originatorOrName;

            private MSList.Builder parent;

            /**
             * Creates a new builder.
             * @param builder parent list builder
             */
            public Builder(final MSList.Builder builder) {
                this.parent = builder;
            }

            /**
             * Set msSequenceNumber.
             * @param value msSequenceNumber
             * @return this builder
             */
            public Builder msSequenceNumber(final int value) {
                this.msSequenceNumber = value;
                return this;
            }

            /**
             * Set priority.
             * @param value priority
             * @return this builder
             */
            public Builder priority(final SpdConstants.Priority value) {
                this.priority = value;
                return this;
            }

            /**
             * Set deliveryTime.
             * @param time deliveryTime
             * @return this builder
             */
            public Builder deliveryTime(final Instant time) {
                this.deliveryTime = time;
                return this;
            }

            /**
             * Set attention.
             * @param value attention
             * @return this builder
             */
            public Builder msStatus(final SpdConstants.MSStatus value) {
                this.msStatus = value;
                return this;
            }

            /**
             * Set originatorOrName.
             * @param value originatorOrName
             * @return this builder
             */
            public Builder originatorOrName(final String value) {
                this.originatorOrName = value;
                return this;
            }

            /**
             * Build PutEnvelope object.
             * @return PutEnvelope object
             */
            public MSList.Builder build() {
                parent.addEntry(new MSEntry(msSequenceNumber, msStatus, priority, deliveryTime, originatorOrName));
                return parent;
            }
        }
    }

    /**
     * Builder for MSList objects.
     */
    public static final class Builder {
        private List<MSEntry> entries;

        /**
         * Creates a new builder.
         */
        public Builder() {
            entries = new ArrayList<>();
        }

        /**
         * Add new MSEntry.
         * @return this builder
         */
        public MSEntry.Builder addEntry() {
            return new MSEntry.Builder(this);
        }

        private void addEntry(final MSEntry entry) {
            entries.add(entry);
        }

        /**
         * Build PutEnvelope object.
         * @return PutEnvelope object
         */
        public MSList build() {
            return new MSList(entries);
        }
    }
}
