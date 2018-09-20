/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.impl;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import org.junit.Assert;
import org.junit.Test;

public class GetMessageRecordTest {

    private static final String TERMINATOR = "00000";

    @Test
    public void testMessageBody() {
        final String lo3bericht =
                "00000000Lg01200505181430000008919258058000000000002539011910110010891925805801200093019439280210018CorneliaFrancisca0240007Wiegman03100081907020303200041157033000460300410001V6110001V821000403638220008199409308230003PKA851000819260715861000819951001021750210020FranciscaGeertruida0230003van0240006Velzen03100081880060503200041157033000460300410001V621000819070203821000403638220008199409308230002PK851000819070203861000819940930031620210016HendrikAlbertus0240007Wiegman03100081877052303200041157033000460300410001M621000819070203821000403638220008199409308230002PK85100081907020386100081994093004051051000400016310003001851000819070203861000819940930052220110010671307312301200093019459370210018AdrianusHendrikus0240010Holthuizen031000819040215032000411570330004603006100081926071506200040363063000460301510001H821000403638220008199409308230002PK8510008192607158610008199510010704268100081994093069100040363701000108710001P08131091000405180920008199505151010001W1030008200107281110015NieuweParklaan11200025811600062597LD7210001K85100082001072886100082001072958127091000405180920008199505151010001W1030008199505151110011Lobelialaan11200025311600062555PC7210001K85100081995051586100081995051658126091000403630920008192101011010001W1030008195407091110010Maasstraat11200026911600061078HE7210001A851000819540709861000819940930091770110010579601382301200093019618270210018Ang�elieFrancisca0240010Holthuizen0310008193106120320004036303300046030821000403638220008199409308230002PK85100081931061286100081995100109158011001082526131320210015Eveline Johanna0240010Holthuizen0310008193106120320004036303300046030821000403638220008199409308230002PK85100081931061286100081994093009165011001090737506700210022Franciscus Engelbertus0240010Holthuizen0310008193003150320004036303300046030821000403638220008199409308230002PK851000819300315861000819940930091690110010706374630801200093019559180210010Margaretha0240010Holthuizen0310008192810120320004036303300046030821000403638220008199409308230002PK851000819281012861000819951001091670110010584593708601200093019579270210008Adrianus0240010Holthuizen0310008192702030320004036303300046030821000403638220008199409308230002PK851000819270203861000819951001121703510002NI3520009IB87656303530008200312153540005B05183550008200812153580003154821000405188220008200312208230030Aanvraagformulier reisdocument851000820031215861000820031220121933510002EK3520009T505049053530008199812043540005B05183550008200312043560008200312153570001I3580003154821000405188220008199812048230030Aanvraagformulier reisdocument851000820031215861000820031215140284010006900208851000819941122";
        // Length + Operationcode + MSSequencenumber
        final String msEntry = "00012" + "210" + "123456790";
        // Length + Operationcode + OriginatorORName + ContentType + Priority + DeliveryTime + SubmissionTime +
        // ActualRecipientORName
        final String getEnvelope = "00041" + "220" + "1234567" + "2" + "1" + "0408121045Z" + "0408121045Z" + "1831010";
        // Length + Operationcode + MessageId + CrossReference + OriginatorUsername + ActualRecipientORName +
        // ActualNotificationRequest
        final String messageHeading = "00042" + "250" + "ANTWOORD2   " + "AA000000001 " + "1234567" + "1234567" + "0";
        // Length + Operationcode + Bodystring
        final String messageBody = StringUtil.zeroPadded(lo3bericht.length() + 3, 5) + "280" + lo3bericht;
        final GetMessageRecord gmr = new GetMessageRecord(msEntry + getEnvelope + messageHeading + messageBody + TERMINATOR);
        try {
            gmr.determineMessageType();

            assertEquals("Verwacht een GBA_MESSAGE type", GetMessageRecord.MT_GBA_MESSAGE, gmr.getMessageType());
            assertEquals(msEntry, gmr.getMsEntry());
            assertEquals(getEnvelope, gmr.getGetEnvelope());
            assertEquals(messageHeading, gmr.getMessageHeading());
            assertEquals(messageBody, gmr.getMessageBody());
        } catch (final SpdProtocolException spe) {
            Assert.fail("Geen SpdProcotolException verwacht\n" + spe.getMessage());
        }
    }

    @Test
    public void testStatusResult() {
        // Length + Operationcode + MSSequencenumber
        final String msEntry = "00012" + "210" + "000011950";
        // Length + Operationcode + OriginatorORName + ContentType + Priority + DeliveryTime + SubmissionTime +
        // ActualRecipientORName
        final String getEnvelope = "00041" + "220" + "1831010" + "2" + "1" + "0408121045Z" + "0408121045Z" + "1831010";
        // Length + Operationcode + ActualRecipientORName + NotificationType + MessageId + NonReceiptReason
        final String statusReport = "00024" + "270" + "1831010" + "1" + "YY000000004 " + "1";

        final GetMessageRecord gmr = new GetMessageRecord(msEntry + getEnvelope + statusReport + TERMINATOR);
        try {
            gmr.determineMessageType();

            assertEquals("Verwacht een STATUS_REPORT type", GetMessageRecord.MT_STATUS_REPORT, gmr.getMessageType());
            assertEquals(msEntry, gmr.getMsEntry());
            assertEquals(getEnvelope, gmr.getGetEnvelope());
            assertEquals(statusReport, gmr.getStatusReport());
        } catch (final SpdProtocolException spe) {
            Assert.fail("Geen SpdProcotolException verwacht\n" + spe.getMessage());
        }
    }

    @Test
    public void testDeliveryReport() {
        // Length + Operationcode + MSSequencenumber
        final String msEntry = "00012" + "210" + "000011950";
        // Length + Operationcode + ReportDeliveryTime + DispatchSequenceNumber + NumberOfRecipients +
        // ReportedRecipientORName + MessageDeliveryTime or MailboxBlockDTS + NonDeliveryReason
        final String deliveryReport = "00048" + "260" + "0408231236Z" + "081200200" + "001" + "1234567" + "           " + "0001";

        final GetMessageRecord gmr = new GetMessageRecord(msEntry + deliveryReport + TERMINATOR);
        try {
            gmr.determineMessageType();

            assertEquals("Verwacht een DELIVERY_REPORT type", GetMessageRecord.MT_DELIVERY_REPORT, gmr.getMessageType());
            assertEquals(msEntry, gmr.getMsEntry());
            assertEquals(deliveryReport, gmr.getDeliveryReport());
        } catch (final SpdProtocolException spe) {
            Assert.fail("Geen SpdProcotolException verwacht\n" + spe.getMessage());
        }
    }

    @Test
    public void testGetMessageConfirmation() {
        // Length + Operationcode + GetError
        final String getMessageConfirmation = "00012" + "290" + "1072";
        final GetMessageRecord gmr = new GetMessageRecord(getMessageConfirmation + TERMINATOR);
        try {
            gmr.determineMessageType();

            assertEquals("Verwacht een MESSAGE_CONFIRMATION type", GetMessageRecord.MT_MESSAGE_CONFIRMATION, gmr.getMessageType());
            assertEquals(getMessageConfirmation, gmr.getMessageConfirmation());
        } catch (final SpdProtocolException spe) {
            Assert.fail("Geen SpdProcotolException verwacht\n" + spe.getMessage());
        }
    }
}
