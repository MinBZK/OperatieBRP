/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.constants;

/**
 *
 */
public final class MessagesCodes {
    // VOSPG 8000-range
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LOGON_ERROR = "8000";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LOGON_RCV_CONFIRMATION = "8001";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LOGON_CONF_LENGTH = "8002";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LOGON_CONF_OPCODE = "8003";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LOGON_INCORRECT_PASSWORD = "8004";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SSL_CONNECTION_ERROR = "8005";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SSL_INCORRECT_CERT_PASSWORD = "8006";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SSL_COMMUNICATION_ERROR = "8007";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LOGOFF_RCV_CONFIRMATION = "8010";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LOGOFF_CONF_LENGTH = "8011";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LOGOFF_CONF_OPCODE = "8012";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LOGOFF_ERROR = "8013";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_NEW_PASSWORD_LENGTH = "8020";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_CHANGEPASSWORD_CONF = "8021";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_CHANGEPASSWORD_CONF_LENGTH = "8022";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_CHANGEPASSWORD_CONF_OPCODE = "8023";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_CHANGEPASSWORD_OLD_PWD_MISSING = "8024";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_CHANGEPASSWORD_OLD_PWD_INVALID = "8025";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_CHANGEPASSWORD_NEW_PWD_MISSING = "8026";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_CHANGEPASSWORD_NEW_PWD_UNACCEPTABLE = "8027";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_CHANGEPASSWORD_ERROR = "8028";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_RCV_LISTMESSAGES = "8030";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LISTMESSAGES_CONF_OPCODE = "8031";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LISTMESSAGES_ERROR = "8032";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_LISTMESSAGES_LENGTH = "8033";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_RCV_GETMESSAGE = "8040";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_GETMESSAGE_CONF_LENGTH = "8041";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_GETMESSAGE_ERROR = "8042";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_GETMESSAGE_MSENTRY_OPCODE = "8043";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_GETMESSAGE_CONTAINS_NULL_BYTES = "8046";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_DELIVERY_REPORT_LENGTH = "8050";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_DELREP_NR_RECIPIENTS = "8052";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_ENVELOPE_LENGTH = "8060";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_HEADING_LENGTH = "8061";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_BODY_LENGTH = "8062";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_STATUS_REPORT_LENGTH = "8070";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_RCV_PUTMESSAGE_CONFIRMATION = "8090";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_PUTMESSAGE_CONF_LENGTH = "8091";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_PUTMESSAGE_CONF_OPCODE = "8092";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_PUTMESSAGE_ERROR = "8093";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SEND_NO_EREF = "8094";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SEND_RECIPIENT_INVALID = "8095";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_PUTMESSAGE_NFE = "8096";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_PUTMESSAGE_CONF_INCORRECT_MSG_ID = "8097";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD = "8098";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_SPD_ILLEGAL_MESSAGE_TYPE = "8099";

    /**
     * 
     */
    public static final String ERRMSG_VOSPG_DB_CONNECTION_FAILED = "8109";
    /**
     * 
     */
    public static final String ERRMSG_VOA_WRONG_MAILBOX_PW = "8111";
    /**
     * 
     */
    public static final String ERRMSG_VOA_WRONG_SSL_PW = "8112";
    /**
     * 
     */
    public static final String ERRMSG_VOA_LOGON_ERROR = "8113";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_CHG_PW_ERROR = "8115";
    /**
     * 
     */
    public static final String ERRMSG_VOA_RECEIVE_ERROR = "8116";
    /**
     * 
     */
    public static final String ERRMSG_VOSPG_UNEXPECTED_EXCEPTION = "8121";

    private MessagesCodes() {

    }
}
