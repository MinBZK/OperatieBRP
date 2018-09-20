/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverAdreshoudingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ISO31661Alpha2;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LangeNaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nationaliteitcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenBeeindigingRelatieCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenBeeindigingRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestAangeverAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestAdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestLand;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPlaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPredikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestRedenBeeindigingRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestRedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestRedenWijzigingAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestSoortDocument;


/**
 * Een helper klasse voor het bouwen/instantieren en vullen van statische objecttypes zoals {@link Partij}, {@link Land}
 * en {@link Plaats} ten behoeve van de unit tests. Tevens biedt deze helper klasse nog enkele constantes
 * voor standaard instanties van deze statische objecttypes welk direct gebruikt kunnen worden in de unit tests.
 *
 * NB: Alleen maar gebruiken voor unit tests in Model, niet in Data Access!
 */
public final class StatischeObjecttypeBuilder {

    public static final Partij        GEMEENTE_AMSTERDAM       = bouwGemeente("363", "Amsterdam");
    public static final Partij        GEMEENTE_UTRECHT         = bouwGemeente("344", "Utrecht");
    public static final Partij        GEMEENTE_DEN_HAAG        = bouwGemeente("518", "'s-Gravenhage");
    public static final Partij        GEMEENTE_BREDA           = bouwGemeente("758", "Breda");

    public static final Plaats        WOONPLAATS_AMSTERDAM     = bouwWoonplaats("1024", "Amsterdam");
    public static final Plaats        WOONPLAATS_UTRECHT       = bouwWoonplaats("3295", "Utrecht");
    public static final Plaats        WOONPLAATS_DEN_HAAG      = bouwWoonplaats("1245", "'s-Gravenhage");
    public static final Plaats        WOONPLAATS_BREDA         = bouwWoonplaats("1702", "Breda");

    public static final Land          LAND_NEDERLAND           = bouwLand("6030", "Nederland");
    public static final Land          LAND_BELGIE              = bouwLand("5010", "Belgie");

    public static final Nationaliteit NATIONALITEIT_ONBEKEND   = bouwNationaliteit("0", "Onbekend");
    public static final Nationaliteit NATIONALITEIT_NEDERLANDS = bouwNationaliteit(
                                                                       BrpConstanten.NL_NATIONALITEIT_CODE.toString(),
                                                                       "Nederlandse");
    public static final Nationaliteit NATIONALITEIT_TURKS      = bouwNationaliteit("339", "Turkse");
    public static final Nationaliteit NATIONALITEIT_SLOWAAKS   = bouwNationaliteit("27", "Slowaaks");

    public static final SoortDocument VERHUIS_AKTE             = bouwSoortDocument("Verhuisakte");
    public static final SoortDocument GEBOORTE_AKTE            = bouwSoortDocument("Geboorteakte");
    public static final SoortDocument OVERLIJDEN_AKTE          = bouwSoortDocument("Overlijdenakte");


    public static final AangeverAdreshouding AANGEVER_ADRESHOUDING_GEZAGHOUDER = bouwAangeverAdreshouding("G", "Gezaghouder");
    public static final AangeverAdreshouding AANGEVER_ADRESHOUDING_INGESCHREVENE = bouwAangeverAdreshouding("I", "Ingeschrevene");
    public static final AangeverAdreshouding AANGEVER_ADRESHOUDING_KIND = bouwAangeverAdreshouding("K", "Meerderjarig inwonend kind voor ouder");
    public static final AangeverAdreshouding AANGEVER_ADRESHOUDING_PARTNER = bouwAangeverAdreshouding("P", "Echtgenoot/geregistreerd partner");
    public static final AangeverAdreshouding AANGEVER_ADRESHOUDING_VERZORGER = bouwAangeverAdreshouding("V", "Verzorger");
    /** .. etc .. */

    public static final RedenWijzigingAdres RDN_WIJZ_ADRES_PERSOON = bouwRedenWijzigAdres("P", "Aangifte door Persoon");
    public static final RedenWijzigingAdres RDN_WIJZ_ADRES_AMBT = bouwRedenWijzigAdres("A", "Ambtshalve");
    public static final RedenWijzigingAdres RDN_WIJZ_ADRES_MINISTER = bouwRedenWijzigAdres("M", "Ministerieel besluit");
    public static final RedenWijzigingAdres RDN_WIJZ_ADRES_BAG = bouwRedenWijzigAdres("B", "Technische wijzigingen i.v.m. BAG");
    public static final RedenWijzigingAdres RDN_WIJZ_ADRES_INFRA = bouwRedenWijzigAdres("I", "Infrastructurele wijziging");

    /** Lege en private constructor daar utility classes niet geinstantied dienen te worden. */
    private StatischeObjecttypeBuilder() {
    }

    /**
     * Bouwt een nieuwe gemeente instantie met de opgegeven code en naam. Indien de opgegeven naam <code>null</code> is,
     * zal de naam niet gezet worden.
     *
     * @param code de gemeente code
     * @param naam de gemeente naam
     * @return een nieuwe gemeente
     */
    public static Partij bouwGemeente(final String code, final String naam) {
        return new TestPartij(new NaamEnumeratiewaarde(naam), SoortPartij.GEMEENTE, new GemeenteCode(code), null, null,
                null, null, null, StatusHistorie.A, null);
    }

    /**
     * Bouwt een nieuwe land instantie met de opgegeven code en naam. Indien de opgegeven naam <code>null</code> is, zal
     * de naam niet gezet worden.
     *
     * @param code de land code
     * @param naam de land naam
     * @return een nieuw land
     */
    public static Land bouwLand(final String code, final String naam) {
        return new TestLand(new Landcode(code), new NaamEnumeratiewaarde(naam), new ISO31661Alpha2(naam), null, null);
    }

    /**
     * Bouwt een nieuwe nationaliteit instantie met de opgegeven code en naam. Indien de opgegeve naam <code>null</code>
     * is, zal de naam niet gezet worden.
     *
     * @param code de nationaliteit code
     * @param naam de nationaliteit naam
     * @return een nieuwe nationaliteit
     */
    public static Nationaliteit bouwNationaliteit(final String code, final String naam) {
        return new TestNationaliteit(new Nationaliteitcode(code), new NaamEnumeratiewaarde(naam), null, null);
    }

    /**
     * Bouwt een nieuwe woonplaats instantie met de opgegeven code en naam. Indien de opgegeven naam <code>null</code>
     * is, zal de naam niet gezet worden.
     *
     * @param code de woonplaats code
     * @param naam de woonplaats naam
     * @return een nieuwe woonplaats
     */
    public static Plaats bouwWoonplaats(final String code, final String naam) {
        return new TestPlaats(new Woonplaatscode(code), new LangeNaamEnumeratiewaarde(naam), null, null);
    }

    /**
     * Bouwt een nieuwe adellijke titel instantie met de opgegeven code en naam.
     *
     * @param code de adellijke titel code
     * @param naam de adellijke titel naam
     * @return een nieuwe adellijke titel
     */
    public static AdellijkeTitel bouwAdellijkeTitel(final String code, final String naam) {
        return new TestAdellijkeTitel(new AdellijkeTitelCode(code), new NaamEnumeratiewaarde(naam),
                new NaamEnumeratiewaarde(naam));
    }

    /**
     * Bouwt een nieuwe predikaat instantie met de opgegeven code en naam.
     *
     * @param code de predikaat code
     * @param naam de predikaat naam
     * @return een nieuw predikaat
     */
    public static Predikaat bouwPredikaat(final String code, final String naam) {
        return new TestPredikaat(new PredikaatCode(code), new NaamEnumeratiewaarde(naam),
                new NaamEnumeratiewaarde(naam));
    }

    public static SoortDocument bouwSoortDocument(final String naam) {
        return new TestSoortDocument(new NaamEnumeratiewaarde(naam));
    }

    public static AangeverAdreshouding bouwAangeverAdreshouding(final String code, final String naam) {
        return new TestAangeverAdreshouding(new AangeverAdreshoudingCode(code), new NaamEnumeratiewaarde(naam), null);
    }

    public static RedenWijzigingAdres bouwRedenWijzigAdres(final String code, final String naam) {
        return new TestRedenWijzigingAdres(new RedenWijzigingAdresCode(code), new NaamEnumeratiewaarde(naam));
    }

    public static RedenVerkrijgingNLNationaliteit bouwRedenVerkrijgingNLNationaliteit(final String code) {
        return new TestRedenVerkrijgingNLNationaliteit(new RedenVerkrijgingCode(code), null, null, null);
    }

    public static RedenBeeindigingRelatie bouwRedenBeeindigingRelatie(final String redenBeeindigingRelatieCode,
            final String omschrijving)
    {
        return new TestRedenBeeindigingRelatie(new RedenBeeindigingRelatieCode(redenBeeindigingRelatieCode),
                new OmschrijvingEnumeratiewaarde(omschrijving));
    }
}
