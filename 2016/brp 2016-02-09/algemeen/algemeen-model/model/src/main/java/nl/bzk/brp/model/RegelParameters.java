/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;


/**
 * De regel parameters is een DTO klasse die informatie voor een specifieke regel bevat. - Melding tekst - Meldingsniveau; fout, deblokkeerbaar,
 * waarschuwing etc. - RegelCode
 */
public class RegelParameters {

    private final MeldingtekstAttribuut meldingTekst;
    private final SoortMelding          soortMelding;
    private final Regel                 regelCode;
    /**
     * DbObject van het attribuut of objecttype dat gecontroleerd wordt door de regel. *
     */
    private final DatabaseObject        databaseObject;
    private final SoortFout             soortFout;

    /**
     * Constructor.
     *
     * @param meldingTekst   melding tekst van de regel
     * @param soortMelding   soort melding
     * @param regelCode      de unieke regel code.
     * @param databaseObject DbObject van het attribuut of objecttype dat gecontroleerd wordt door de regel
     */
    public RegelParameters(final MeldingtekstAttribuut meldingTekst, final SoortMelding soortMelding,
        final Regel regelCode, final DatabaseObject databaseObject)
    {
        this(meldingTekst, soortMelding, regelCode, databaseObject, SoortFout.NVT);
    }

    /**
     * Constructor.
     *
     * @param meldingTekst   melding tekst van de regel
     * @param soortMelding   soort melding
     * @param regelCode      de unieke regel code.
     * @param databaseObject DbObject van het attribuut of objecttype dat gecontroleerd wordt door de regel
     * @param soortFout      de soort fout
     */
    public RegelParameters(final MeldingtekstAttribuut meldingTekst, final SoortMelding soortMelding,
        final Regel regelCode, final DatabaseObject databaseObject, final SoortFout soortFout)
    {
        if (soortMelding == SoortMelding.FOUT && soortFout == SoortFout.NVT) {
            throw new IllegalArgumentException("Bij een soort melding 'fout' is een soort fout verplicht.");
        }
        this.meldingTekst = meldingTekst;
        this.soortMelding = soortMelding;
        this.regelCode = regelCode;
        this.databaseObject = databaseObject;
        this.soortFout = soortFout;
    }

    public MeldingtekstAttribuut getMeldingTekst() {
        return meldingTekst;
    }

    public SoortMelding getSoortMelding() {
        return soortMelding;
    }

    public Regel getRegelCode() {
        return regelCode;
    }

    public DatabaseObject getDatabaseObject() {
        return databaseObject;
    }

    public SoortFout getSoortFout() {
        return soortFout;
    }
}
