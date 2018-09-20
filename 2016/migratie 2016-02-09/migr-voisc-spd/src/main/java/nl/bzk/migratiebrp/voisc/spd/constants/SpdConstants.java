/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.constants;

/**
 * This interface defines constants of the sPd-protocol. These constants are used to compose and parse GBA-berichten to
 * and from the MailboxServer.
 * 
 */
public final class SpdConstants {

    /**
     *
     */
    public static final String TERMINATOR = "00000";

    /**
     *
     */
    public static final int MAX_NUMBER_LIST_MESSAGES = 171;
    /**
     *
     */
    public static final int MIN_NUMBER_LIST_MESSAGES = 40;

    /**
     *
     */
    public static final String PRIORITY = " ";

    /*
     * Generic values.
     */
    /**
     *
     */
    public static final int LENGTH_START = 0;
    /**
     *
     */
    public static final int LENGTH_LENGTH = 5;
    /**
     *
     */
    public static final int OPCODE_START = LENGTH_START + LENGTH_LENGTH;
    /**
     *
     */
    public static final int OPCODE_LENGTH = 3;
    /**
     *
     */
    public static final int MAX_SPD_LEN = 20000;
    /**
     *
     */
    public static final int RESULT_LENGTH = 4;

    /*
     * MSEntry values
     */
    /**
     *
     */
    public static final int MSE_MSSEQUENCE_NUMBER_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int MSE_MSSEQUENCE_NUMBER_LEN = 9;
    /**
     *
     */
    public static final int MSE_EXPECTED_LENGTH = OPCODE_LENGTH + MSE_MSSEQUENCE_NUMBER_LEN;

    /*
     * GetEnvelop specific variables
     */
    /**
     *
     */
    public static final int GE_ORIGINATOR_ORNAME_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int GE_ORIGINATOR_ORNAME_LEN = 7;
    /**
     *
     */
    public static final int GE_EXPECTED_LENGTH = 41;
    /**
     *
     */
    public static final int GE_DELIVERYTIME_START = 17;
    /**
     *
     */
    public static final int GE_DELIVERYTIME_LEN = 11;
    /**
     *
     */
    public static final int GE_RECIPIENT_ORNAME_START = 39;
    /**
     *
     */
    public static final int GE_RECIPIENT_ORNAME_LEN = 7;

    /*
     * MessageHeading specific variables.
     */
    /**
     *
     */
    public static final int MH_MESSAGE_ID_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int MH_MESSAGE_ID_LEN = 12;
    /**
     *
     */
    public static final int MH_CROSS_REFERENCE_START = MH_MESSAGE_ID_START + MH_MESSAGE_ID_LEN;
    /**
     *
     */
    public static final int MH_CROSS_REFERENCE_LEN = 12;
    /**
     *
     */
    public static final int MH_EXPECTED_LENGTH = 42;

    /**
     *
     */
    public static final int MB_BODYSTRING_START = OPCODE_START + OPCODE_LENGTH;

    /*
     * StatusReport specific values.
     */
    /**
     *
     */
    public static final int STATUS_REPORT_NOT_TYPE_START = 15;
    /**
     *
     */
    public static final int STATUS_REPORT_NOT_TYPE_LEN = 1;

    /**
     *
     */
    public static final int STATUS_REPORT_MESSAGE_ID_START = STATUS_REPORT_NOT_TYPE_START + STATUS_REPORT_NOT_TYPE_LEN;
    /**
     *
     */
    public static final int STATUS_REPORT_MESSAGE_ID_LEN = 12;
    /**
     *
     */
    public static final int STATUS_REPORT_NON_REC_REASON_START = STATUS_REPORT_MESSAGE_ID_START + STATUS_REPORT_MESSAGE_ID_LEN;
    /**
     *
     */
    public static final int STATUS_REPORT_NON_REC_REASON_LEN = 1;
    /**
     *
     */
    public static final int STATUS_REPORT_EXPECTED_LENGTH = 24;

    /*
     * DeliveryReport specific values.
     */
    /**
     *
     */
    public static final int DELIVERY_REP_DELIVERY_TIME_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int DELIVERY_REP_DELIVERY_TIME_LEN = 11;
    /**
     *
     */
    public static final int DELIVERY_REP_DISPATCH_SEQNR_START = DELIVERY_REP_DELIVERY_TIME_START + DELIVERY_REP_DELIVERY_TIME_LEN;
    /**
     *
     */
    public static final int DELIVERY_REP_DISPATCH_SEQNR_LEN = 9;
    /**
     *
     */
    public static final int DELIVERY_REP_NR_OR_RECEPIENTS_START = DELIVERY_REP_DISPATCH_SEQNR_START + DELIVERY_REP_DISPATCH_SEQNR_LEN;
    /**
     *
     */
    public static final int DELIVERY_REP_NR_OR_RECEPIENTS_LEN = 3;
    /**
     *
     */
    public static final int DELIVERY_REP_RECIPIENT_ORNAME_START = 31;
    /**
     *
     */
    public static final int DELIVERY_REP_RECIPIENT_ORNAME_LEN = 7;
    /**
     *
     */
    public static final int DELIVERY_REP_NON_DEL_REASON_START = 49;
    /**
     *
     */
    public static final int DELIVERY_REP_NON_DEL_REASON_LEN = 4;
    /**
     *
     */
    public static final int DELIVERY_REP_EXPECTED_LENGTH = 48;

    /*
     * GetMessage Confirmation values.
     */
    /**
     *
     */
    public static final int GETMESSAGE_CONFIRMATION_RESULT_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int GETMESSAGE_CONFIRMATION_EXCEPTED_LENGTH = 7;

    /*
     * Logoff Confirmation values.
     */
    /**
     *
     */
    public static final int LOGOFF_RESULT_START = OPCODE_START + OPCODE_LENGTH;

    /*
     * Logon Confirmation values.
     */
    /**
     *
     */
    public static final int LOGON_RESULT_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int LOGON_IDENTIFICATIE_ONGELDIG = 1033;

    /*
     * PutMessage Confirmation values.
     */
    /**
     *
     */
    public static final int PUTMESSAGE_RESULT_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int PUTMESSAGE_DISPATCH_SEQNR_START = PUTMESSAGE_RESULT_START + RESULT_LENGTH;
    /**
     *
     */
    public static final int PUTMESSAGE_DISPATCH_SEQNR_LEN = 9;
    /**
     *
     */
    public static final int PUTMESSAGE_SUBMISSION_TIME_START = PUTMESSAGE_DISPATCH_SEQNR_START + PUTMESSAGE_DISPATCH_SEQNR_LEN;
    /**
     *
     */
    public static final int PUTMESSAGE_SUBMISSION_TIME_LEN = 11;
    /**
     *
     */
    public static final int PUTMESSAGE_MESSAGE_ID_START = PUTMESSAGE_SUBMISSION_TIME_START + PUTMESSAGE_SUBMISSION_TIME_LEN;
    /**
     *
     */
    public static final int PUTMESSAGE_MESSAGE_ID_LEN = 12;

    /**
     *
     */
    public static final int PUTMESSAGE_PRIORITY_DEFAULT = 0;

    /**
     *
     */
    public static final int PUTMESSAGE_NO_NOTIFICATION_REQUEST = 0;

    /**
     *
     */
    public static final int PUTMESSAGE_NON_RECIPIENT_NOTIFICATION_REQUEST = 1;

    /*
     * SummarizeResult values.
     */
    /**
     *
     */
    public static final int SUMMARIZE_CONFIRMATION_ERROR_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int SUMMARIZE_NR_OF_COUNTS_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int SUMMARIZE_NR_OF_COUNTS_LEN = 3;
    /**
     *
     */
    public static final int SUMMARIZE_NR_OF_ENTRIES_START = SUMMARIZE_NR_OF_COUNTS_START + SUMMARIZE_NR_OF_COUNTS_LEN;
    /**
     *
     */
    public static final int SUMMARIZE_NR_OF_ENTRIES_LEN = 9;
    /**
     *
     */
    public static final int SUMMARIZE_MSSTATUS_START = SUMMARIZE_NR_OF_ENTRIES_START + SUMMARIZE_NR_OF_ENTRIES_LEN;
    /**
     *
     */
    public static final int SUMMARIZE_MSSTATUS_LEN = 1;
    /**
     *
     */
    public static final int SUMMARIZE_PRIO_START = SUMMARIZE_MSSTATUS_START + SUMMARIZE_MSSTATUS_LEN;
    /**
     *
     */
    public static final int SUMMARIZE_PRIO_LEN = 1;

    /*
     * ListMessageResult values.
     */
    /**
     *
     */
    public static final int LR_STATUS_LENGTH = 3;
    /**
     *
     */
    public static final int LR_PRIORITY_LENGTH = 1;
    /**
     *
     */
    public static final int LR_MSSEQUENCE_NUMBER_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int LR_MSSEQUENCE_NUMBER_LEN = 9;
    /**
     *
     */
    public static final int MSL_NUMBER_OF_MSENTRIES_START = LR_MSSEQUENCE_NUMBER_START
                                                            + LR_MSSEQUENCE_NUMBER_LEN
                                                            + OPCODE_START
                                                            + OPCODE_LENGTH;
    /**
     *
     */
    public static final int MSL_NUMBER_OF_MSENTRIES_LEN = 5;
    /**
     *
     */
    public static final int MSL_MSSEQUENCE_NUMBER_START = MSL_NUMBER_OF_MSENTRIES_START + MSL_NUMBER_OF_MSENTRIES_LEN;
    /**
     *
     */
    public static final int MSL_MSSEQUENCE_NUMBER_LEN = 9;
    /**
     *
     */
    public static final int MSL_MSSTATUS_START = MSL_MSSEQUENCE_NUMBER_START + MSL_MSSEQUENCE_NUMBER_LEN;
    /**
     *
     */
    public static final int MSL_MSSTATUS_LEN = 1;
    /**
     *
     */
    public static final int MSL_PRIORITY_START = MSL_MSSTATUS_START + MSL_MSSTATUS_LEN;
    /**
     *
     */
    public static final int MSL_PRIORITY_LEN = 1;
    /**
     *
     */
    public static final int MSL_DELIVERY_TIME_START = MSL_PRIORITY_START + MSL_PRIORITY_LEN;
    /**
     *
     */
    public static final int MSL_DELIVERY_TIME_LEN = 11;
    /**
     *
     */
    public static final int MSL_ORIGINATOR_OR_NAME_START = MSL_DELIVERY_TIME_START + MSL_DELIVERY_TIME_LEN;
    /**
     *
     */
    public static final int MSL_ORIGINATOR_OR_NAME_LEN = 7;
    /**
     *
     */
    public static final int MSL_ENTRY_LEN = MSL_MSSEQUENCE_NUMBER_LEN
                                            + MSL_MSSTATUS_LEN
                                            + MSL_PRIORITY_LEN
                                            + MSL_DELIVERY_TIME_LEN
                                            + MSL_ORIGINATOR_OR_NAME_LEN;

    /**
     *
     */
    public static final int LIST_MESSAGES_CONFIRMATION_ERROR_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int LIST_MESSAGES_CONFIRMATION_ERROR_LEN = RESULT_LENGTH;

    /*
     * Change Password values.
     */
    /**
     *
     */
    public static final int CPWD_RESULT_START = OPCODE_START + OPCODE_LENGTH;
    /**
     *
     */
    public static final int CPWD_MAX_PWD_LENGTH = 8;
    /**
     *
     */
    public static final int CPWD_MIN_PWD_LENGTH = 6;

    /*
     * Total Lengths.
     */
    /**
     *
     */
    public static final int TOT_LENGTH_MSENTRY = MSE_EXPECTED_LENGTH + LENGTH_LENGTH;
    /**
     *
     */
    public static final int TOT_LENGTH_GET_ENVELOP = GE_EXPECTED_LENGTH + LENGTH_LENGTH;
    /**
     *
     */
    public static final int TOT_LENGTH_MESSAGE_HEADING = MH_EXPECTED_LENGTH + LENGTH_LENGTH;
    /**
     *
     */
    public static final int TOT_LENGTH_STATUS_REPORT = STATUS_REPORT_EXPECTED_LENGTH + LENGTH_LENGTH;
    /**
     *
     */
    public static final int TOT_LENGTH_DEL_REPORT = DELIVERY_REP_EXPECTED_LENGTH + LENGTH_LENGTH;
    /**
     *
     */
    public static final int TOT_LENGTH_MESSAGE_CONFIRMATION = GETMESSAGE_CONFIRMATION_EXCEPTED_LENGTH + LENGTH_LENGTH;

    /*
     * Operation codes.
     */
    // GetMessage
    /**
     *
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
    public static final int OPC_MESSAGE_HEADING = 250;
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
    public static final int OPC_MESSAGE_BODY = 280;
    /**
     *
     */
    public static final int OPC_MESSAGE_CONFIRMATION = 290;
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
    // PutMessage
    /**
     *
     */
    public static final int OPC_PUT_MESSAGE = 120;
    /**
     *
     */
    public static final int OPC_PUT_MESSAGE_CONFIRMATION = 190;
    // ListMessages
    /**
     *
     */
    public static final int OPC_LIST_MESSAGES = 400;
    // SummarizeMessages
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
    // ListMessages
    /**
     *
     */
    public static final int OPC_LIST_MESSAGES_RESULT = 410;
    /**
     *
     */
    public static final int OPC_LIST_MESSAGES_CONFIRMATION = 419;
    // ChangePasswordConfirmation
    /**
     *
     */
    public static final int OPC_CHANGE_PASSWORD_CONFIRMATION = 919;

    /**
     *
     */
    public static final int ERR_CODE_LIST_MESSAGES_NO_ENTRIES = 1113;

    /**
     *
     */
    public static final int ERR_CODE_CHANGEPASSWORD_OLD_PWD_MISSING = 1131;
    /**
     *
     */
    public static final int ERR_CODE_CHANGEPASSWORD_OLD_PWD_INVALID = 1132;
    /**
     *
     */
    public static final int ERR_CODE_CHANGEPASSWORD_NEW_PWD_MISSING = 1133;
    /**
     *
     */
    public static final int ERR_CODE_CHANGEPASSWORD_NEW_PWD_UNACCEPTABLE = 1134;

    private SpdConstants() {

    }
}
