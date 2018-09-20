/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.NationaliteitCode;
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

    public static final Partij GEMEENTE_AMSTERDAM = bouwGemeente("0363", "Amsterdam");
    public static final Partij GEMEENTE_UTRECHT   = bouwGemeente("0344", "Utrecht");
    public static final Partij GEMEENTE_DEN_HAAG  = bouwGemeente("0518", "'s-Gravenhage");
    public static final Partij GEMEENTE_BREDA     = bouwGemeente("0758", "Breda");

    public static final Plaats WOONPLAATS_AMSTERDAM = bouwWoonplaats("1024", "Amsterdam");
    public static final Plaats WOONPLAATS_UTRECHT   = bouwWoonplaats("3295", "Utrecht");
    public static final Plaats WOONPLAATS_DEN_HAAG  = bouwWoonplaats("1245", "'s-Gravenhage");
    public static final Plaats WOONPLAATS_BREDA     = bouwWoonplaats("1702", "Breda");

    public static final Land LAND_NEDERLAND = bouwLand("6030", "Nederland");

    /** Lege en private constructor daar utility classes niet geinstantieerd dienen te worden. */
    private StatischeObjecttypeBuilder() {
    }

    /**
     * Bouwt een nieuwe gemeente instantie met de opgegeven code en naam. Indien de opgegeven naam
     * <code>null</code> is, zal de naam niet gezet worden.
     *
     * @param gemeenteCode de gemeente code
     * @param gemeenteNaam de gemeente naam
     * @return een nieuwe gemeente
     */
    public static Partij bouwGemeente(final String gemeenteCode, final String gemeenteNaam) {
        final Partij gemeente = new Partij();
        gemeente.setGemeenteCode(new GemeenteCode(gemeenteCode));
        if (gemeenteNaam != null) {
            gemeente.setNaam(new Naam(gemeenteNaam));
        }
        return gemeente;
    }

    /**
     * Bouwt een nieuwe land instantie met de opgegeven code en naam. Indien de opgegeven naam
     * <code>null</code> is, zal de naam niet gezet worden.
     *
     * @param landCode de land code
     * @param landNaam de land naam
     * @return een nieuw land
     */
    public static Land bouwLand(final String landCode, final String landNaam) {
        final Land land = new Land();
        land.setLandCode(new LandCode(landCode));
        if (landNaam != null) {
            land.setNaam(new Naam(landNaam));
        }
        return land;
    }

    /**
     * Bouwt een nieuwe nationaliteit instantie met de opgegeven code en naam. Indien de opgegeve naam
     * <code>null</code> is, zal de naam niet gezet worden.
     *
     * @param nationaliteitCode de nationaliteit code
     * @param nationaliteitNaam de nationaliteit naam
     * @return een nieuwe nationaliteit
     */
    public static Nationaliteit bouwNationaliteit(final String nationaliteitCode,
        final String nationaliteitNaam)
    {
        final Nationaliteit nationaliteit = new Nationaliteit();
        nationaliteit.setNationaliteitCode(new NationaliteitCode(nationaliteitCode));
        if (nationaliteitNaam != null) {
            nationaliteit.setNaam(new Naam(nationaliteitNaam));
        }
        return nationaliteit;
    }

    /**
     * Bouwt een nieuwe woonplaats instantie met de opgegeven code en naam. Indien de opgegeven naam
     * <code>null</code> is, zal de naam niet gezet worden.
     *
     * @param woonplaatsCode de woonplaats code
     * @param woonplaatsNaam de woonplaats naam
     * @return een nieuwe woonplaats
     */
    public static Plaats bouwWoonplaats(final String woonplaatsCode, final String woonplaatsNaam) {
        final Plaats woonplaats = new Plaats();
        woonplaats.setCode(new PlaatsCode(woonplaatsCode));
        if (woonplaatsNaam != null) {
            woonplaats.setNaam(new Naam(woonplaatsNaam));
        }
        return woonplaats;
    }

    /**
     * Bouwt een nieuwe adellijke titel instantie met de opgegeven code en naam.
     *
     * @param adellijkeTitleCode de adellijke titel code
     * @param adellijkeTitelNaam de adellijke titel naam
     * @return een nieuwe adellijke titel
     */
    public static AdellijkeTitel bouwAdellijkeTitel(final String adellijkeTitleCode, final String adellijkeTitelNaam) {
        final AdellijkeTitel adellijkeTitel = new AdellijkeTitel();
        adellijkeTitel.setAdellijkeTitelCode(new AdellijkeTitelCode(adellijkeTitleCode));
        adellijkeTitel.setNaamMannelijk(new Naam(adellijkeTitelNaam));
        adellijkeTitel.setNaamVrouwelijk(new Naam(adellijkeTitelNaam));
        return adellijkeTitel;
    }

    /**
     * Bouwt een nieuwe predikaat instantie met de opgegeven code en naam.
     *
     * @param predikaatCode de predikaat code
     * @param predikaatNaam de predikaat naam
     * @return een nieuw predikaat
     */
    public static Predikaat bouwPredikaat(final String predikaatCode, final String predikaatNaam) {
        final Predikaat predikaat = new Predikaat();
        predikaat.setCode(new PredikaatCode(predikaatCode));
        predikaat.setNaamMannelijk(new Naam(predikaatNaam));
        predikaat.setNaamVrouwelijk(new Naam(predikaatNaam));
        return predikaat;
    }
}
