/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.Woonplaatscode;
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

    public static final Partij GEMEENTE_AMSTERDAM   = bouwGemeente((short) 363, "Amsterdam");
    public static final Partij GEMEENTE_UTRECHT     = bouwGemeente((short) 344, "Utrecht");
    public static final Partij GEMEENTE_DEN_HAAG    = bouwGemeente((short) 518, "'s-Gravenhage");
    public static final Partij GEMEENTE_BREDA       = bouwGemeente((short) 758, "Breda");

    public static final Plaats WOONPLAATS_AMSTERDAM = bouwWoonplaats((short) 1024, "Amsterdam");
    public static final Plaats WOONPLAATS_UTRECHT   = bouwWoonplaats((short) 3295, "Utrecht");
    public static final Plaats WOONPLAATS_DEN_HAAG  = bouwWoonplaats((short) 1245, "'s-Gravenhage");
    public static final Plaats WOONPLAATS_BREDA     = bouwWoonplaats((short) 1702, "Breda");

    public static final Land   LAND_NEDERLAND       = bouwLand((short) 6030, "Nederland");

    /** Lege en private constructor daar utility classes niet geinstantieerd dienen te worden. */
    private StatischeObjecttypeBuilder() {
    }

    /**
     * Bouwt een nieuwe gemeente instantie met de opgegeven code en NaamEnumeratiewaarde. Indien de opgegeven NaamEnumeratiewaarde
     * <code>null</code> is, zal de NaamEnumeratiewaarde niet gezet worden.
     *
     * @param code de gemeente code
     * @param NaamEnumeratiewaarde de gemeente NaamEnumeratiewaarde
     * @return een nieuwe gemeente
     */
    public static Partij bouwGemeente(final Short code, final String NaamEnumeratiewaarde) {
        final Partij gemeente = new Partij();
        gemeente.setGemeentecode(new Gemeentecode(code));
        if (NaamEnumeratiewaarde != null) {
            gemeente.setNaam(new NaamEnumeratiewaarde(NaamEnumeratiewaarde));
        }
        return gemeente;
    }

    /**
     * Bouwt een nieuwe land instantie met de opgegeven code en NaamEnumeratiewaarde. Indien de opgegeven NaamEnumeratiewaarde
     * <code>null</code> is, zal de NaamEnumeratiewaarde niet gezet worden.
     *
     * @param code de land code
     * @param NaamEnumeratiewaarde de land NaamEnumeratiewaarde
     * @return een nieuw land
     */
    public static Land bouwLand(final Short code, final String NaamEnumeratiewaarde) {
        final Land land = new Land();
        land.setCode(new Landcode(code));
        if (NaamEnumeratiewaarde != null) {
            land.setNaam(new NaamEnumeratiewaarde(NaamEnumeratiewaarde));
        }
        return land;
    }

    /**
     * Bouwt een nieuwe nationaliteit instantie met de opgegeven code en NaamEnumeratiewaarde. Indien de opgegeve NaamEnumeratiewaarde
     * <code>null</code> is, zal de NaamEnumeratiewaarde niet gezet worden.
     *
     * @param code de nationaliteit code
     * @param NaamEnumeratiewaarde de nationaliteit NaamEnumeratiewaarde
     * @return een nieuwe nationaliteit
     */
    public static Nationaliteit bouwNationaliteit(final Short code, final String NaamEnumeratiewaarde) {
        final Nationaliteit nationaliteit = new Nationaliteit();
        nationaliteit.setNationaliteitcode(new Nationaliteitcode(code));
        if (NaamEnumeratiewaarde != null) {
            nationaliteit.setNaam(new NaamEnumeratiewaarde(NaamEnumeratiewaarde));
        }
        return nationaliteit;
    }

    /**
     * Bouwt een nieuwe woonplaats instantie met de opgegeven code en NaamEnumeratiewaarde. Indien de opgegeven NaamEnumeratiewaarde
     * <code>null</code> is, zal de NaamEnumeratiewaarde niet gezet worden.
     *
     * @param code de woonplaats code
     * @param NaamEnumeratiewaarde de woonplaats NaamEnumeratiewaarde
     * @return een nieuwe woonplaats
     */
    public static Plaats bouwWoonplaats(final short code, final String NaamEnumeratiewaarde) {
        final Plaats woonplaats = new Plaats();
        woonplaats.setCode(new Woonplaatscode(code));
        if (NaamEnumeratiewaarde != null) {
            woonplaats.setNaam(new NaamEnumeratiewaarde(NaamEnumeratiewaarde));
        }
        return woonplaats;
    }

    /**
     * Bouwt een nieuwe adellijke titel instantie met de opgegeven code en NaamEnumeratiewaarde.
     *
     * @param code de adellijke titel code
     * @param NaamEnumeratiewaarde de adellijke titel NaamEnumeratiewaarde
     * @return een nieuwe adellijke titel
     */
    public static AdellijkeTitel bouwAdellijkeTitel(final String code, final String NaamEnumeratiewaarde) {
        final AdellijkeTitel adellijkeTitel = new AdellijkeTitel();
        adellijkeTitel.setAdellijkeTitelCode(new AdellijkeTitelCode(code));
        adellijkeTitel.setNaamMannelijk(new NaamEnumeratiewaarde(NaamEnumeratiewaarde));
        adellijkeTitel.setNaamVrouwelijk(new NaamEnumeratiewaarde(NaamEnumeratiewaarde));
        return adellijkeTitel;
    }

    /**
     * Bouwt een nieuwe predikaat instantie met de opgegeven code en NaamEnumeratiewaarde.
     *
     * @param code de predikaat code
     * @param NaamEnumeratiewaarde de predikaat NaamEnumeratiewaarde
     * @return een nieuw predikaat
     */
    public static Predikaat bouwPredikaat(final String code, final String naamEnumeratiewaarde) {
        final Predikaat predikaat = new Predikaat();
        predikaat.setCode(new PredikaatCode(code));
        predikaat.setNaamMannelijk(new NaamEnumeratiewaarde(naamEnumeratiewaarde));
        predikaat.setNaamVrouwelijk(new NaamEnumeratiewaarde(naamEnumeratiewaarde));
        return predikaat;
    }
}
