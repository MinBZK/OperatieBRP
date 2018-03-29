/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Dit element wordt gebruikt voor alle BMR attributen die datum / tijd bevatten. Definitie als volgt:
 *
 * <ul>
 * <li>datumtijd:
 * <code>[0-9]{4}(-(0[1-9]{1}|1[0-2]{1})(-(0[1-9]{1}|[1-2]{1}[0-9]{1}|3[0-1]{1})))(T((([0-1]{1}[0-9]{1}|[2]{1}[0-3]{1})(:[0-5]{1}[0-9]{1}(:[0-5]{1}[0-9]{1}
 * (\.[0-9]{0,3})?)))|24(:00(:00(\.[0]{0,3}))))((\+|\-)([0-1]{1}[0-9]{1}|[2]{1}[0-3]{1}):[0-5]{1}[0-9]{1})?)</code></li>
 * </ul>
 */
public final class DatumTijdElement extends AbstractBmrAttribuut<ZonedDateTime> {

    private static final String FOUTMELDING = "De waarde '%s' is geen geldig DatumTijdElement.";

    private static final DateTimeFormatter DATUM_TIJD_FORMATTER =
            new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
                                          .appendOffset("+HH:MM", "Z")
                                          .toFormatter()
                                          .withChronology(IsoChronology.INSTANCE);

    /**
     * Maakt een nieuw DatumElement object.
     *
     * @param waarde de datum waarde, mag niet null zijn
     */
    public DatumTijdElement(final ZonedDateTime waarde) {
        super(waarde.withZoneSameInstant(DatumUtil.BRP_ZONE_ID));
    }

    /**
     * Maakt een nieuw DatumElement object.
     *
     * @param waarde de datum waarde, mag niet null zijn
     */
    public DatumTijdElement(final Timestamp waarde) {
        super(ZonedDateTime.ofInstant(waarde.toInstant(), DatumUtil.BRP_ZONE_ID));
    }

    /**
     * Maakt een DatumElement obv de gegeven string.
     *
     * @param waarde de waarde, mag niet null zijn
     * @return DatumElement
     * @throws OngeldigeWaardeException wanneer er geen de gegeven string een ongeldige waarde bevat
     */
    public static DatumTijdElement parseWaarde(final String waarde) throws OngeldigeWaardeException {
        ValidatieHelper.controleerOpNullWaarde(waarde, "waarde");
        try {
            return new DatumTijdElement(ZonedDateTime.from(DATUM_TIJD_FORMATTER.parse(waarde)));
        } catch (DateTimeException e) {
            throw new OngeldigeWaardeException(String.format(FOUTMELDING, waarde), e);
        }
    }

    @Override
    public String toString() {
        return getWaarde().format(DATUM_TIJD_FORMATTER);
    }

    /**
     * Geeft de waarde van deze datum/tijd terug als timestamp zodat deze kan worden opgeslagen in de database.
     *
     * @return timestamp
     */
    public Timestamp toTimestamp() {
        return Timestamp.from(getWaarde().toInstant());
    }
}
