/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.herkomst;

/**
 * Enumeratie van alle LO3 groepen.
 */
public enum Lo3GroepEnum {
    /**
     * Identificatienummers.
     */
    GROEP01,
    /**
     * Naam.
     */
    GROEP02,
    /**
     * Geboorte.
     */
    GROEP03,
    /**
     * Geslacht.
     */
    GROEP04,
    /**
     * Nationaliteit.
     */
    GROEP05,
    /**
     * Huwelijkssluiting/aangaan geregistreerd partnerschap.
     */
    GROEP06,
    /**
     * Ontbinding huwelijk/geregistreerd partnerschap.
     */
    GROEP07,
    /**
     * Overlijden.
     */
    GROEP08,
    /**
     * Gemeente.
     */
    GROEP09,
    /**
     * Adreshouding.
     */
    GROEP10,
    /**
     * Adres.
     */
    GROEP11,
    /**
     * Locatie.
     */
    GROEP12,
    /**
     * Adres buitenland.
     */
    GROEP13,
    /**
     * Immigratie.
     */
    GROEP14,
    /**
     * Soort verbintenis.
     */
    GROEP15,
    /**
     * A-nummerverwijzingen.
     */
    GROEP20,
    /**
     * Europees kiesrecht.
     */
    GROEP31,
    /**
     * Gezag minderjarige.
     */
    GROEP32,
    /**
     * Curatele.
     */
    GROEP33,
    /**
     * Nederlands reisdocument.
     */
    GROEP35,
    /**
     * Signalering.
     */
    GROEP36,
    /**
     * Buitenlands reisdocument.
     */
    GROEP37,
    /**
     * Uitsluiting kiesrecht.
     */
    GROEP38,
    /**
     * Verblijfstitel.
     */
    GROEP39,
    /**
     * Afnemer.
     */
    GROEP40,
    /**
     * Aantekening.
     */
    GROEP42,
    /**
     * Naamgebruik.
     */
    GROEP61,
    /**
     * Familierechtelijke betrekking.
     */
    GROEP62,
    /**
     * Verkrijging Nederlanderschap.
     */
    GROEP63,
    /**
     * Verlies Nederlanderschap.
     */
    GROEP64,
    /**
     * Bijzonder Nederlanderschap.
     */
    GROEP65,
    /**
     * Blokkering.
     */
    GROEP66,
    /**
     * Opschorting.
     */
    GROEP67,
    /**
     * Opname.
     */
    GROEP68,
    /**
     * Gemeente PK.
     */
    GROEP69,
    /**
     * Geheim.
     */
    GROEP70,
    /**
     * Verificatie.
     */
    GROEP71,
    /**
     * Adresaangifte.
     */
    GROEP72,
    /**
     * Documentindicatie.
     */
    GROEP75,
    /**
     * Synchroniciteit.
     */
    GROEP80,
    /**
     * Akte.
     */
    GROEP81,
    /**
     * Document.
     */
    GROEP82,
    /**
     * Procedure.
     */
    GROEP83,
    /**
     * Onjuist.
     */
    GROEP84,
    /**
     * Geldigheid.
     */
    GROEP85,
    /**
     * Opneming.
     */
    GROEP86,
    /**
     * PK-Conversie.
     */
    GROEP87,
    /**
     * RNI-deelnemer.
     */
    GROEP88;

    private static final String GROEP_PREFIX = "GROEP";

    @Override
    public String toString() {
        final int startCodeIndex = GROEP_PREFIX.length();
        return name().substring(startCodeIndex);
    }

    /**
     * Geef de waarde van groep as int.
     *
     * @return Het groepnummmer als een int.
     */
    public int getGroepAsInt() {
        final int startCodeIndex = GROEP_PREFIX.length();
        return Integer.parseInt(name().substring(startCodeIndex));
    }

    /**
     * @param groep
     *            de groep nummer
     * @return de corresponderende LO3 groep
     */
    public static Lo3GroepEnum getLo3Groep(final String groep) {
        try {
            return Lo3GroepEnum.valueOf(GROEP_PREFIX + groep);
        } catch (final IllegalArgumentException iae) {
            // Waarde niet gevonden in de enumeratie
            return null;
        }
    }
}
