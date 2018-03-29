/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Arrays;
import java.util.Optional;

/**
 * This interface defines constants of the sPd-protocol. These constants are used to compose and parse GBA-berichten to
 * and from the MailboxServer.
 */
public final class SpdConstants {

    /**
     * Generic values.
     */
    public static final int LENGTH_LENGTH = 5;
    /**
     *
     */
    public static final int OPCODE_LENGTH = 3;
    /**
     *
     */
    public static final int MAX_SPD_LEN = 20_000;
    /**
     *
     */
    public static final int RESULT_LENGTH = 4;

    /**
     * Operation codes.
     */
    public static final int OPC_GET_MESSAGE = 200;
    /**
     *
     */
    public static final int OPC_MSENTRY = 210;
    /**
     *
     */
    public static final int OPC_GET_ENVELOP = 220;
    /**
     *
     */
    public static final int OPC_GET_MESSAGE_HEADING = 250;
    /**
     *
     */
    public static final int OPC_DELIVERY_REPORT = 260;
    /**
     *
     */
    public static final int OPC_STATUS_REPORT = 270;
    /**
     *
     */
    public static final int OPC_GET_MESSAGE_BODY = 280;
    /**
     *
     */
    public static final int OPC_GET_MESSAGE_CONFIRMATION = 290;
    // Logoff
    /**
     *
     */
    public static final int OPC_LOGOFF_REQUEST = 990;
    /**
     *
     */
    public static final int OPC_LOGOFF_CONFIRMATION = 999;
    // Logon
    /**
     *
     */
    public static final int OPC_LOGON_REQUEST = 900;
    /**
     *
     */
    public static final int OPC_LOGON_CONFIRMATION = 909;
    /**
     *
     */
    public static final int OPC_PUT_ENVELOPE = 120;
    /**
     *
     */
    public static final int OPC_PUT_MESSAGE_HEADING = 150;
    /**
     *
     */
    public static final int OPC_PUT_MESSAGE_BODY = 180;
    /**
     *
     */
    public static final int OPC_PUT_MESSAGE_CONFIRMATION = 190;
    /**
     *
     */
    public static final int OPC_LIST_MESSAGES = 400;
    /**
     *
     */
    public static final int OPC_SUMMARIZE_MESSAGES = 500;
    /**
     *
     */
    public static final int OPC_SUMMARIZE_RESULT = 510;
    /**
     *
     */
    public static final int OPC_SUMMARIZE_CONFIRMATION = 590;
    /**
     *
     */
    public static final int OPC_LIST_RESULT = 410;
    /**
     *
     */
    public static final int OPC_MS_LIST = 411;
    /**
     *
     */
    public static final int OPC_LIST_MESSAGES_CONFIRMATION = 419;
    /**
     *
     */
    public static final int OPC_CHANGE_PASSWORD_REQUEST = 910;
    /**
     *
     */
    public static final int OPC_CHANGE_PASSWORD_CONFIRMATION = 919;
    /**
     *
     */
    public static final int OPC_NO_OPERATION_CONFIRMATION = 9;

    private SpdConstants() {

    }

    /**
     * Attention enumeration.
     */
    public enum Attention implements CodeEnum {
        /***/
        NO_ATTENTION(0),
        /***/
        ATTENTION(1);

        private int code;

        /**
         * @param code code
         */
        Attention(final int code) {
            this.code = code;
        }

        /**
         * Returns the default value.
         * @return default value
         */
        public static Attention defaultValue() {
            return NO_ATTENTION;
        }

        /**
         * Creates enum from code.
         * @param code error code
         * @return enum or null if there is no corresponding code
         */
        public static Optional<Attention> fromCode(final int code) {
            return Arrays.stream(values()).filter(result -> result.code() == code).findFirst();
        }

        @Override
        public int code() {
            return code;
        }
    }

    /**
     * ContentType enumeration.
     */
    public enum ContentType implements CodeEnum {
        /***/
        P2(2);

        private int code;

        /**
         * @param code code
         */
        ContentType(final int code) {
            this.code = code;
        }

        /**
         * Returns the default value.
         * @return default value
         */
        public static ContentType defaultValue() {
            return P2;
        }

        /**
         * Creates enum from code.
         * @param code error code
         * @return enum or null if there is no corresponding code
         */
        public static Optional<ContentType> fromCode(final int code) {
            return Arrays.stream(values()).filter(result -> result.code() == code).findFirst();
        }

        @Override
        public int code() {
            return code;
        }
    }

    /**
     * Priority enumeration.
     */
    public enum Priority implements CodeEnum {
        /***/
        NORMAL(0),
        /***/
        LOW(1),
        /***/
        URGENT(2);

        private int code;

        /**
         * @param code code
         */
        Priority(final int code) {
            this.code = code;
        }

        /**
         * Returns the default value.
         * @return default value
         */
        public static Priority defaultValue() {
            return NORMAL;
        }

        /**
         * Creates enum from code.
         * @param code error code
         * @return enum or null if there is no corresponding code
         */
        public static Optional<Priority> fromCode(final int code) {
            return Arrays.stream(values()).filter(result -> result.code() == code).findFirst();
        }

        @Override
        public int code() {
            return code;
        }
    }

    /**
     * NotificationRequest enumeration.
     */
    public enum NotificationRequest implements CodeEnum {
        /***/
        NONE(0),
        /***/
        NON_RECEIPT(1),
        /***/
        RECEIPT(2);

        private int code;

        /**
         * @param code code
         */
        NotificationRequest(final int code) {
            this.code = code;
        }

        /**
         * Returns the default value.
         * @return default value
         */
        public static NotificationRequest defaultValue() {
            return NONE;
        }

        /**
         * Creates enum from code.
         * @param code error code
         * @return enum or null if there is no corresponding code
         */
        public static Optional<NotificationRequest> fromCode(final int code) {
            return Arrays.stream(values()).filter(result -> result.code() == code).findFirst();
        }

        @Override
        public int code() {
            return code;
        }
    }

    /**
     * NotificationType enumeration.
     */
    public enum NotificationType implements CodeEnum {
        /***/
        RECEIPT(0),
        /***/
        NON_RECEIPT(1);

        private int code;

        /**
         * @param code code
         */
        NotificationType(final int code) {
            this.code = code;
        }

        /**
         * Creates enum from code.
         * @param code error code
         * @return enum or null if there is no corresponding code
         */
        public static Optional<NotificationType> fromCode(final int code) {
            return Arrays.stream(values()).filter(result -> result.code() == code).findFirst();
        }

        @Override
        public int code() {
            return code;
        }
    }

    /**
     * NonReceiptReason enumeration.
     */
    public enum NonReceiptReason implements CodeEnum {
        /***/
        EXPIRED(0);

        private int code;

        /**
         * @param code code
         */
        NonReceiptReason(final int code) {
            this.code = code;
        }

        /**
         * Creates enum from code.
         * @param code error code
         * @return enum or null if there is no corresponding code
         */
        public static Optional<NonReceiptReason> fromCode(final int code) {
            return Arrays.stream(values()).filter(result -> result.code() == code).findFirst();
        }

        @Override
        public int code() {
            return code;
        }
    }

    /**
     * MSStatus enumeration.
     */
    public enum MSStatus implements CodeEnum {
        /***/
        NEW(0),
        /***/
        LISTED(1),
        /***/
        PROCESSED(2);

        private int code;

        /**
         * @param code code
         */
        MSStatus(final int code) {
            this.code = code;
        }

        /**
         * Creates enum from code.
         * @param code error code
         * @return enum or null if there is no corresponding code
         */
        public static Optional<MSStatus> fromCode(final int code) {
            return Arrays.stream(values()).filter(result -> result.code() == code).findFirst();
        }

        @Override
        public int code() {
            return code;
        }
    }

    /**
     * Interface for enums having a code field
     */
    public interface CodeEnum {
        /**
         * Code field
         * @return code
         */
        int code();
    }
}
