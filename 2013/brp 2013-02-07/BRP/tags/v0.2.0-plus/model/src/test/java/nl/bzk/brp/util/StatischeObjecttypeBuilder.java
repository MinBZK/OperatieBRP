/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;

/**
 * Een helper klasse voor het bouwen/instantieren en vullen van statische objecttypes zoals {@link Partij},
 * {@link Land} en {@link Plaats} ten behoeve van de unit tests. Tevens biedt deze helper klasse nog enkele constantes
 * voor standaard instanties van deze statische objecttypes welk direct gebruikt kunnen worden in de unit tests.
 */
public final class StatischeObjecttypeBuilder {

    public static final Partij GEMEENTE_AMSTERDAM = bouwGemeente((short) 363, "Amsterdam");
    public static final Partij GEMEENTE_UTRECHT   = bouwGemeente((short) 344, "Utrecht");
    public static final Partij GEMEENTE_DEN_HAAG  = bouwGemeente((short) 518, "'s-Gravenhage");
    public static final Partij GEMEENTE_BREDA     = bouwGemeente((short) 758, "Breda");

    public static final Plaats WOONPLAATS_AMSTERDAM = bouwWoonplaats((short) 1024, "Amsterdam");
    public static final Plaats WOONPLAATS_UTRECHT   = bouwWoonplaats((short) 3295, "Utrecht");
    public static final Plaats WOONPLAATS_DEN_HAAG  = bouwWoonplaats((short) 1245, "'s-Gravenhage");
    public static final Plaats WOONPLAATS_BREDA     = bouwWoonplaats((short) 1702, "Breda");

    public static final Land LAND_NEDERLAND = bouwLand((short) 6030, "Nederland");

    public static final Nationaliteit NATIONALITEIT_ONBEKEND = bouwNationaliteit((short) 0, "Onbekend");
    public static final Nationaliteit NATIONALITEIT_NEDERLANDS = bouwNationaliteit(
                            BrpConstanten.NL_NATIONALITEIT_CODE.getWaarde(), "Nederlandse");
    public static final Nationaliteit NATIONALITEIT_TURKS = bouwNationaliteit((short) 339, "Turkse");
    public static final Nationaliteit NATIONALITEIT_SLOWAAKS = bouwNationaliteit((short) 27, "Slowaaks");

    /** Lege en private constructor daar utility classes niet geinstantieerd dienen te worden. */
    private StatischeObjecttypeBuilder() {
    }

    /**
     * Bouwt een nieuwe gemeente instantie met de opgegeven code en naam. Indien de opgegeven naam
     * <code>null</code> is, zal de naam niet gezet worden.
     *
     * @param code de gemeente code
     * @param naam de gemeente naam
     * @return een nieuwe gemeente
     */
    public static Partij bouwGemeente(final Short code, final String naam) {
        final Partij gemeente = new Partij();
        gemeente.setGemeentecode(new Gemeentecode(code));
        if (naam != null) {
            gemeente.setNaam(new Naam(naam));
        }
        return gemeente;
    }

    /**
     * Bouwt een nieuwe land instantie met de opgegeven code en naam. Indien de opgegeven naam
     * <code>null</code> is, zal de naam niet gezet worden.
     *
     * @param code de land code
     * @param naam de land naam
     * @return een nieuw land
     */
    public static Land bouwLand(final Short code, final String naam) {
        final Land land = new Land();
        land.setCode(new Landcode(code));
        if (naam != null) {
            land.setNaam(new Naam(naam));
        }
        return land;
    }

    /**
     * Bouwt een nieuwe nationaliteit instantie met de opgegeven code en naam. Indien de opgegeve naam
     * <code>null</code> is, zal de naam niet gezet worden.
     *
     * @param code de nationaliteit code
     * @param naam de nationaliteit naam
     * @return een nieuwe nationaliteit
     */
    public static Nationaliteit bouwNationaliteit(final Short code, final String naam) {
        final Nationaliteit nationaliteit = new Nationaliteit();
        nationaliteit.setNationaliteitcode(new Nationaliteitcode(code));
        if (naam != null) {
            nationaliteit.setNaam(new Naam(naam));
        }
        return nationaliteit;
    }

    /**
     * Bouwt een nieuwe woonplaats instantie met de opgegeven code en naam. Indien de opgegeven naam
     * <code>null</code> is, zal de naam niet gezet worden.
     *
     * @param code de woonplaats code
     * @param naam de woonplaats naam
     * @return een nieuwe woonplaats
     */
    public static Plaats bouwWoonplaats(final short code, final String naam) {
        final Plaats woonplaats = new Plaats();
        woonplaats.setCode(new PlaatsCode(code));
        if (naam != null) {
            woonplaats.setNaam(new Naam(naam));
        }
        return woonplaats;
    }

    /**
     * Bouwt een nieuwe adellijke titel instantie met de opgegeven code en naam.
     *
     * @param code de adellijke titel code
     * @param naam de adellijke titel naam
     * @return een nieuwe adellijke titel
     */
    public static AdellijkeTitel bouwAdellijkeTitel(final String code, final String naam) {
        final AdellijkeTitel adellijkeTitel = new AdellijkeTitel();
        adellijkeTitel.setAdellijkeTitelCode(new AdellijkeTitelCode(code));
        adellijkeTitel.setNaamMannelijk(new Naam(naam));
        adellijkeTitel.setNaamVrouwelijk(new Naam(naam));
        return adellijkeTitel;
    }

    /**
     * Bouwt een nieuwe predikaat instantie met de opgegeven code en naam.
     *
     * @param code de predikaat code
     * @param naam de predikaat naam
     * @return een nieuw predikaat
     */
    public static Predikaat bouwPredikaat(final String code, final String naam) {
        final Predikaat predikaat = new Predikaat();
        predikaat.setCode(new PredikaatCode(code));
        predikaat.setNaamMannelijk(new Naam(naam));
        predikaat.setNaamVrouwelijk(new Naam(naam));
        return predikaat;
    }
}
