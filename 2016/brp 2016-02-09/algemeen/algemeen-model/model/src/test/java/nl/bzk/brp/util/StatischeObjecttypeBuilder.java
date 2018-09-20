/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingInhoudingVermissingReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RechtsgrondCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SoortNederlandsReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rechtsgrond;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RechtsgrondAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRechtsgrond;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestAangeverBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestAdellijkeTitelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestGemeenteBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestLandGebiedBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestNationaliteitBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPredicaatBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestRedenEindeRelatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestRedenVerkrijgingNLNationaliteitBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestRedenWijzigingVerblijfBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestSoortDocumentBuilder;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Een helper klasse voor het bouwen/instantieren en vullen van statische objecttypes ten behoeve van de unit tests.
 * Tevens biedt deze helper klasse nog enkele constantes voor standaard instanties van deze statische objecttypes welke
 * direct gebruikt kunnen worden in de unit tests.
 * <p/>
 * NB: Alleen maar gebruiken voor unit tests in Model, niet in Data Access!
 */
public final class StatischeObjecttypeBuilder {

    public static final PartijAttribuut PARTIJ_GEMEENTE_AMSTERDAM   =
        bouwPartij(363,
            "Gemeente Amsterdam");
    public static final PartijAttribuut PARTIJ_GEMEENTE_UTRECHT     =
        bouwPartij(364,
            "Gemeente Utrecht");
    public static final PartijAttribuut PARTIJ_GEMEENTE_SGRAVENHAGE =
        bouwPartij(365,
            "Gemeente 's-Gravenhage");
    public static final PartijAttribuut PARTIJ_GEMEENTE_BREDA       = bouwPartij(758,
        "Gemeente Breda");
    public static final PartijAttribuut PARTIJ_MINISTERIE_BZK       = bouwPartij(101,
        "Ministerie BZK");

    public static final GemeenteAttribuut GEMEENTE_AMSTERDAM =
        bouwGemeente(
            363,
            "Amsterdam",
            PARTIJ_GEMEENTE_AMSTERDAM
                .getWaarde());
    public static final GemeenteAttribuut GEMEENTE_UTRECHT   =
        bouwGemeente(
            344,
            "Utrecht",
            PARTIJ_GEMEENTE_UTRECHT
                .getWaarde());
    public static final GemeenteAttribuut GEMEENTE_DEN_HAAG  =
        bouwGemeente(
            518,
            "'s-Gravenhage",
            PARTIJ_GEMEENTE_SGRAVENHAGE
                .getWaarde());
    public static final GemeenteAttribuut GEMEENTE_BREDA     =
        bouwGemeente(
            758,
            "Breda",
            PARTIJ_GEMEENTE_BREDA
                .getWaarde());

    public static final NaamEnumeratiewaardeAttribuut WOONPLAATS_ALMERE    =
        bouwWoonplaatsnaam("Almere");
    public static final NaamEnumeratiewaardeAttribuut WOONPLAATS_AMSTERDAM =
        bouwWoonplaatsnaam("Amsterdam");
    public static final NaamEnumeratiewaardeAttribuut WOONPLAATS_UTRECHT   =
        bouwWoonplaatsnaam("Utrecht");
    public static final NaamEnumeratiewaardeAttribuut WOONPLAATS_DEN_HAAG  =
        bouwWoonplaatsnaam("'s-Gravenhage");
    public static final NaamEnumeratiewaardeAttribuut WOONPLAATS_BREDA     =
        bouwWoonplaatsnaam("Breda");
    public static final NaamEnumeratiewaardeAttribuut WOONPLAATS_SCHIPHOL  =
        bouwWoonplaatsnaam("Schiphol");

    public static final LandGebiedAttribuut LAND_NEDERLAND  = bouwLandGebied(
        (short) 6030,
        "Nederland");
    public static final LandGebiedAttribuut LAND_BELGIE     = bouwLandGebied(
        (short) 5010,
        "Belgie");
    public static final LandGebiedAttribuut LAND_FRANKRIJK  = bouwLandGebied(
        (short) 5002,
        "Frankrijk");
    public static final LandGebiedAttribuut LAND_AFGANISTAN = bouwLandGebied(
        (short) 6023,
        "Afganistan");

    public static final NationaliteitAttribuut NATIONALITEIT_ONBEKEND   = bouwNationaliteit("0",
        "Onbekend");
    public static final NationaliteitAttribuut NATIONALITEIT_NEDERLANDS =
        bouwNationaliteit(
            NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE
                .toString(),
            "Nederlandse");
    public static final NationaliteitAttribuut NATIONALITEIT_TURKS      = bouwNationaliteit(
        "339", "Turkse");
    public static final NationaliteitAttribuut NATIONALITEIT_SLOWAAKS   = bouwNationaliteit("27",
        "Slowaaks");

    public static final SoortDocumentAttribuut VERHUIS_AKTE    =
        bouwSoortDocument("Verhuisakte");
    public static final SoortDocumentAttribuut GEBOORTE_AKTE   =
        bouwSoortDocument("Geboorteakte");
    public static final SoortDocumentAttribuut OVERLIJDEN_AKTE =
        bouwSoortDocument("Overlijdenakte");

    public static final AangeverAttribuut               AANGEVER_GEZAGHOUDER    = bouwAangever("G",
        "Gezaghouder");
    public static final AangeverAttribuut               AANGEVER_INGESCHREVENE  = bouwAangever("I",
        "Ingeschrevene");
    public static final AangeverAttribuut               AANGEVER_KIND           =
        bouwAangever("K",
            "Meerderjarig inwonend kind voor ouder");
    public static final AangeverAttribuut               AANGEVER_PARTNER        =
        bouwAangever("P",
            "Echtgenoot/geregistreerd partner");
    public static final AangeverAttribuut               AANGEVER_VERZORGER      = bouwAangever("V",
        "Verzorger");
    /**
     * .. etc ..
     */

    public static final RedenWijzigingVerblijfAttribuut RDN_WIJZ_ADRES_PERSOON  =
        bouwRedenWijzigAdres(
            "P",
            "Aangifte door Persoon");
    public static final RedenWijzigingVerblijfAttribuut RDN_WIJZ_ADRES_AMBT     =
        bouwRedenWijzigAdres(
            "A",
            "Ambtshalve");
    public static final RedenWijzigingVerblijfAttribuut RDN_WIJZ_ADRES_MINISTER =
        bouwRedenWijzigAdres(
            "M",
            "Ministerieel besluit");
    public static final RedenWijzigingVerblijfAttribuut RDN_WIJZ_ADRES_BAG      =
        bouwRedenWijzigAdres(
            "B",
            "Technische wijzigingen i.v.m. BAG");
    public static final RedenWijzigingVerblijfAttribuut RDN_WIJZ_ADRES_INFRA    =
        bouwRedenWijzigAdres(
            "I",
            "Infrastructurele wijziging");

    public static final PredicaatAttribuut PREDICAAT_KONING   =
        bouwPredicaat("K",
            "zijne majesteit");
    public static final PredicaatAttribuut PREDICAAT_HOOGHEID = bouwPredicaat("H",
        "hoogheid");
    public static final PredicaatAttribuut PREDICAAT_JONKHEER = bouwPredicaat("J",
        "jonkheer");

    public static final AdellijkeTitelAttribuut ADEL_TITEL_BARON  = bouwAdellijkeTitel("B",
        "baron");
    public static final AdellijkeTitelAttribuut ADEL_TITEL_GRAAF  = bouwAdellijkeTitel("G",
        "graaf");
    public static final AdellijkeTitelAttribuut ADEL_TITEL_HERTOG = bouwAdellijkeTitel("H",
        "hertog");

    public static final SoortNederlandsReisdocumentAttribuut EUROPEES_ID_KAART    =
        bouwSoortNederlandsReisdocument("EK");
    public static final SoortNederlandsReisdocumentAttribuut NEDERLANDSE_ID_KAART =
        bouwSoortNederlandsReisdocument("NI");

    public static final AanduidingInhoudingVermissingReisdocument INGEHOUDEN_INGELEVERD =
        bouwAanduidingInhoudingVermissingReisdocument(
            "I",
            "Ingehouden, ingeleverd");
    public static final AanduidingInhoudingVermissingReisdocument VERMIST               =
        bouwAanduidingInhoudingVermissingReisdocument(
            "V",
            "Vermist");

    /**
     * Lege en private constructor daar utility classes niet geinstantied dienen te worden.
     */
    private StatischeObjecttypeBuilder() {
    }

    /**
     * Bouwt een rechtsgrond attribuut, echter er zijn nog geen waardes voor bekend (stamgegevens),
     * dus worden hier random wat waardes gebruikt.
     *
     * @return rechtsgrond attribuut
     */
    private static RechtsgrondAttribuut bouwRechtsgrond(final short code, final SoortRechtsgrond soortRechtsgrond,
        final String omschrijving)
    {
        Rechtsgrond rechtsgrond =
            new Rechtsgrond(new RechtsgrondCodeAttribuut(code), soortRechtsgrond,
                new OmschrijvingEnumeratiewaardeAttribuut(omschrijving), null, null, null);
        ReflectionTestUtils.setField(rechtsgrond, "iD", (short) 587);
        return new RechtsgrondAttribuut(rechtsgrond);
    }

    /**
     * Bouw het stamgegeven aanduiding inhouding vermissing reisdocument.
     *
     * @param code code
     * @param naam naam
     * @return AanduidingInhoudingVermissingReisdocument
     */
    private static AanduidingInhoudingVermissingReisdocument bouwAanduidingInhoudingVermissingReisdocument(
        final String code, final String naam)
    {
        return new AanduidingInhoudingVermissingReisdocument(
            new AanduidingInhoudingVermissingReisdocumentCodeAttribuut(code), new NaamEnumeratiewaardeAttribuut(
            naam));
    }

    /**
     * Bouwt een nieuwe gemeente instantie met de opgegeven code en naam.
     *
     * @param code   de gemeente code
     * @param naam   de gemeente naam
     * @param partij de partij
     * @return een nieuwe gemeente
     */
    public static GemeenteAttribuut bouwGemeente(final Integer code, final String naam, final Partij partij) {
        Gemeente gemeente = TestGemeenteBuilder.maker()
            .metNaam(naam)
            .metCode(code)
            .metPartij(partij)
            .metId(code)
            .maak();
        return new GemeenteAttribuut(gemeente);
    }

    /**
     * Bouwt een nieuwe partij instantie met de opgegeven code en naam.
     *
     * @param code de partij code
     * @param naam de partij naam
     * @return een nieuwe partij
     */
    public static PartijAttribuut bouwPartij(final Integer code, final String naam) {
        final Partij partij = TestPartijBuilder.maker()
            .metNaam(naam)
            .metSoort(SoortPartij.GEMEENTE)
            .metCode(code)
            .metId(code.shortValue())
            .maak();

        return new PartijAttribuut(partij);
    }

    /**
     * Bouwt een nieuwe soort nederlands reisdocument instantie met de opgegeven code en naam.
     *
     * @param code de soort NL reisdoc code
     * @return een nieuwe partij
     */
    public static SoortNederlandsReisdocumentAttribuut bouwSoortNederlandsReisdocument(final String code) {
        SoortNederlandsReisdocument srtNederlandsReisdocument =
            new SoortNederlandsReisdocument(new SoortNederlandsReisdocumentCodeAttribuut(code), null, null, null);
        return new SoortNederlandsReisdocumentAttribuut(srtNederlandsReisdocument);
    }

    /**
     * Bouwt een nieuwe landGebied instantie met de opgegeven code en naam. Indien de opgegeven naam <code>null</code>
     * is, zal
     * de naam niet gezet worden.
     *
     * @param code de landGebied code
     * @param naam de landGebied naam
     * @return een nieuw land gebied
     */
    public static LandGebiedAttribuut bouwLandGebied(final Short code, final String naam) {
        return new LandGebiedAttribuut(TestLandGebiedBuilder.maker().metCode(code).metNaam(naam).metAlpha2Naam(naam).maak());
    }

    /**
     * Bouwt een nieuwe nationaliteit instantie met de opgegeven code en naam. Indien de opgegeve naam <code>null</code>
     * is, zal de naam niet gezet worden.
     *
     * @param code de nationaliteit code
     * @param naam de nationaliteit naam
     * @return een nieuwe nationaliteit
     */
    public static NationaliteitAttribuut bouwNationaliteit(final String code, final String naam) {
        return new NationaliteitAttribuut(TestNationaliteitBuilder.maker().metCode(code).metNaam(naam).maak());
    }

    /**
     * Bouwt een nieuwe woonplaats instantie met de opgegeven naam.
     *
     * @param naam de woonplaats naam
     * @return een nieuwe woonplaats
     */
    public static NaamEnumeratiewaardeAttribuut bouwWoonplaatsnaam(final String naam) {
        return new NaamEnumeratiewaardeAttribuut(naam);
    }

    /**
     * Bouwt een nieuwe adellijke titel instantie met de opgegeven code en naam.
     *
     * @param code de adellijke titel code
     * @param naam de adellijke titel naam
     * @return een nieuwe adellijke titel
     */
    public static AdellijkeTitelAttribuut bouwAdellijkeTitel(final String code, final String naam) {
        return new AdellijkeTitelAttribuut(TestAdellijkeTitelBuilder.maker().metCode(code).metNaamMnl(naam).metNaamVrl(naam).maak());
    }

    /**
     * Bouwt een nieuwe predikaat instantie met de opgegeven code en naam.
     *
     * @param code de predicaat code
     * @param naam de predicaat naam
     * @return een nieuw predicaat
     */
    public static PredicaatAttribuut bouwPredicaat(final String code, final String naam) {
        return new PredicaatAttribuut(TestPredicaatBuilder.maker().metCode(code)
            .metNaamMnl(naam).metNaamVrl(naam).maak());
    }

    public static SoortDocumentAttribuut bouwSoortDocument(final String naam) {
        return new SoortDocumentAttribuut(TestSoortDocumentBuilder.maker().metNaam(naam).maak());
    }

    public static AangeverAttribuut bouwAangever(final String code, final String naam) {
        return new AangeverAttribuut(TestAangeverBuilder.maker().metCode(code).metNaam(naam).maak());
    }

    public static RedenWijzigingVerblijfAttribuut bouwRedenWijzigAdres(final String code, final String naam) {
        return new RedenWijzigingVerblijfAttribuut(
            TestRedenWijzigingVerblijfBuilder.maker().metCode(code).metNaam(naam).maak());
    }

    public static RedenVerkrijgingNLNationaliteitAttribuut bouwRedenVerkrijgingNLNationaliteit(final Short code) {
        return new RedenVerkrijgingNLNationaliteitAttribuut(TestRedenVerkrijgingNLNationaliteitBuilder.maker().metCode(code).maak());
    }

    public static RedenEindeRelatieAttribuut bouwRedenEindeRelatie(final String redenEindeRelatieCode,
            final String omschrijving)
    {
        return new RedenEindeRelatieAttribuut(
            TestRedenEindeRelatieBuilder.maker().metCode(redenEindeRelatieCode).metOmschrijving(omschrijving).maak());
    }

}
