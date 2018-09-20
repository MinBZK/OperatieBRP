/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAfgeleidAdministratiefInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBezitBuitenlandsReisdocumentIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsverantwoordelijkheidInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeprivilegieerdeIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpImmigratieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpStatenloosIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerstrekkingsbeperkingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;

/**
 * Deze class helpt bij het maken van een BrpPersoonslijst. De verplichte argumenten moeten in de contructor worden
 * meegegeven en optionele argumenten kunnen via method-chaining worden toegevoegd.
 * 
 */
// CHECKSTYLE:OFF het aantal classes in een persoonslijst is een gegeven voor ons en mag hier groter zijn dan 20
public final class BrpPersoonslijstBuilder {
    // CHECKSTYLE:ON

    private BrpStapel<BrpAanschrijvingInhoud> aanschrijvingStapel;
    private BrpStapel<BrpAdresInhoud> adresStapel;
    private BrpStapel<BrpAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel;
    private BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel;
    // CHECKSTYLE:OFF het afkorten van namen uit BRP maakt de code minder leesbaar
    private BrpStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> belemmeringVerstrekkingReisdocumentIndicatieStapel;
    private BrpStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud> bezitBuitenlandsReisdocumentIndicatieStapel;
    // CHECKSTYLE:ON
    private BrpStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel;
    private BrpStapel<BrpBijhoudingsverantwoordelijkheidInhoud> bijhoudingsverantwoordelijkheidStapel;
    private BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel;
    private BrpStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel;
    private BrpStapel<BrpGeboorteInhoud> geboorteStapel;
    private BrpStapel<BrpGeprivilegieerdeIndicatieInhoud> geprivilegieerdeIndicatieStapel;
    private BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    private final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels =
            new ArrayList<BrpStapel<BrpGeslachtsnaamcomponentInhoud>>();
    private BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel;
    private BrpStapel<BrpImmigratieInhoud> immigratieStapel;
    private BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel;
    private final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels =
            new ArrayList<BrpStapel<BrpNationaliteitInhoud>>();
    private BrpStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel;
    private BrpStapel<BrpOpschortingInhoud> opschortingStapel;
    private BrpStapel<BrpOverlijdenInhoud> overlijdenStapel;
    private BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel;
    private final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels =
            new ArrayList<BrpStapel<BrpReisdocumentInhoud>>();
    private final List<BrpRelatie> relaties = new ArrayList<BrpRelatie>();
    private BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    private BrpStapel<BrpStatenloosIndicatieInhoud> statenloosIndicatieStapel;
    private BrpStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel;
    private BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel;
    private BrpStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel;
    private BrpStapel<BrpVerstrekkingsbeperkingInhoud> verstrekkingsbeperkingStapel;
    private final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels = new ArrayList<BrpStapel<BrpVoornaamInhoud>>();

    /**
     * Maak lege builder.
     */
    public BrpPersoonslijstBuilder() {

    }

    /**
     * Maak builder gevuld met stapels uit persoonslijst.
     * 
     * @param persoonslijst
     *            initiele vulling
     */
    // CHECKSTYLE:OFF - Executable statement count - gewoon veel properties
    public BrpPersoonslijstBuilder(final BrpPersoonslijst persoonslijst) {
        // CHECKSTYLE:ON
        aanschrijvingStapel = persoonslijst.getAanschrijvingStapel();
        adresStapel = persoonslijst.getAdresStapel();
        afgeleidAdministratiefStapel = persoonslijst.getAfgeleidAdministratiefStapel();
        behandeldAlsNederlanderIndicatieStapel = persoonslijst.getBehandeldAlsNederlanderIndicatieStapel();
        belemmeringVerstrekkingReisdocumentIndicatieStapel =
                persoonslijst.getBelemmeringVerstrekkingReisdocumentIndicatieStapel();
        bezitBuitenlandsReisdocumentIndicatieStapel = persoonslijst.getBezitBuitenlandsReisdocumentIndicatieStapel();
        bijhoudingsgemeenteStapel = persoonslijst.getBijhoudingsgemeenteStapel();
        bijhoudingsverantwoordelijkheidStapel = persoonslijst.getBijhoudingsverantwoordelijkheidStapel();
        derdeHeeftGezagIndicatieStapel = persoonslijst.getDerdeHeeftGezagIndicatieStapel();
        europeseVerkiezingenStapel = persoonslijst.getEuropeseVerkiezingenStapel();
        geboorteStapel = persoonslijst.getGeboorteStapel();
        geprivilegieerdeIndicatieStapel = persoonslijst.getGeprivilegieerdeIndicatieStapel();
        geslachtsaanduidingStapel = persoonslijst.getGeslachtsaanduidingStapel();
        geslachtsnaamcomponentStapels.addAll(persoonslijst.getGeslachtsnaamcomponentStapels());
        identificatienummersStapel = persoonslijst.getIdentificatienummerStapel();
        immigratieStapel = persoonslijst.getImmigratieStapel();
        inschrijvingStapel = persoonslijst.getInschrijvingStapel();
        nationaliteitStapels.addAll(persoonslijst.getNationaliteitStapels());
        onderCurateleIndicatieStapel = persoonslijst.getOnderCurateleIndicatieStapel();
        opschortingStapel = persoonslijst.getOpschortingStapel();
        overlijdenStapel = persoonslijst.getOverlijdenStapel();
        persoonskaartStapel = persoonslijst.getPersoonskaartStapel();
        reisdocumentStapels.addAll(persoonslijst.getReisdocumentStapels());
        relaties.addAll(persoonslijst.getRelaties());
        samengesteldeNaamStapel = persoonslijst.getSamengesteldeNaamStapel();
        statenloosIndicatieStapel = persoonslijst.getStatenloosIndicatieStapel();
        uitsluitingNederlandsKiesrechtStapel = persoonslijst.getUitsluitingNederlandsKiesrechtStapel();
        vastgesteldNietNederlanderIndicatieStapel = persoonslijst.getVastgesteldNietNederlanderIndicatieStapel();
        verblijfsrechtStapel = persoonslijst.getVerblijfsrechtStapel();
        verstrekkingsbeperkingStapel = persoonslijst.getVerstrekkingsbeperkingStapel();
        voornaamStapels.addAll(persoonslijst.getVoornaamStapels());
    }

    /**
     * @return een nieuwe BrpPersoonslijst object o.b.v. de parameters van deze builder
     */
    public BrpPersoonslijst build() {
        return new BrpPersoonslijst(aanschrijvingStapel, adresStapel, afgeleidAdministratiefStapel,
                behandeldAlsNederlanderIndicatieStapel, belemmeringVerstrekkingReisdocumentIndicatieStapel,
                bezitBuitenlandsReisdocumentIndicatieStapel, bijhoudingsgemeenteStapel,
                bijhoudingsverantwoordelijkheidStapel, derdeHeeftGezagIndicatieStapel, europeseVerkiezingenStapel,
                geboorteStapel, geprivilegieerdeIndicatieStapel, geslachtsaanduidingStapel,
                geslachtsnaamcomponentStapels, identificatienummersStapel, immigratieStapel, inschrijvingStapel,
                nationaliteitStapels, onderCurateleIndicatieStapel, opschortingStapel, overlijdenStapel,
                persoonskaartStapel, reisdocumentStapels, relaties, samengesteldeNaamStapel,
                statenloosIndicatieStapel, uitsluitingNederlandsKiesrechtStapel,
                vastgesteldNietNederlanderIndicatieStapel, verblijfsrechtStapel, verstrekkingsbeperkingStapel,
                voornaamStapels);
    }

    /**
     * Voegt de aanschrijf stapel toe aan deze persoonslijst builder.
     * 
     * @param aanschrijvingStapel
     *            de aanschrijving stapel, mag null zijn
     * @return de BrpPersoonslijstBuilder
     */
    public BrpPersoonslijstBuilder aanschrijvingStapel(final BrpStapel<BrpAanschrijvingInhoud> aanschrijvingStapel) {

        this.aanschrijvingStapel = aanschrijvingStapel;
        return this;
    }

    /**
     * Voegt de adresStapel toe aan deze persoonslijst builder.
     * 
     * @param adresStapel
     *            de adresStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder adresStapel(final BrpStapel<BrpAdresInhoud> adresStapel) {
        this.adresStapel = adresStapel;
        return this;
    }

    /**
     * Voegt de afgeleidAdministratiefStapel toe aan deze persoonslijst builder.
     * 
     * @param afgeleidAdministratiefStapel
     *            de geslachtsaanduidingStapel, mag null zijn
     * @return het builder object
     */
    public BrpPersoonslijstBuilder afgeleidAdministratiefStapel(
            final BrpStapel<BrpAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel) {
        this.afgeleidAdministratiefStapel = afgeleidAdministratiefStapel;
        return this;
    }

    /**
     * Voegt de BrpBehandeldAlsNederlanderIndicatieStapel toe aan deze persoonslijst builder.
     * 
     * @param behandeldAlsNederlanderIndicatieStapel
     *            de BrpBehandeldAlsNederlanderIndicatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder behandeldAlsNederlanderIndicatieStapel(
            final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel) {
        this.behandeldAlsNederlanderIndicatieStapel = behandeldAlsNederlanderIndicatieStapel;
        return this;
    }

    /**
     * Voegt de BrpBelemmeringVerstrekkingReisdocumentIndicatie stapel toe aan deze persoonslijst builder.
     * 
     * @param stapel
     *            de BrpBelemmeringVerstrekkingReisdocumentIndicatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder belemmeringVerstrekkingReisdocumentIndicatieStapel(
            final BrpStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> stapel) {
        belemmeringVerstrekkingReisdocumentIndicatieStapel = stapel;
        return this;
    }

    /**
     * Voegt de BrpBezitBuitenlandsReisdocumentIndicatie stapel toe aan deze persoonslijst builder.
     * 
     * @param stapel
     *            de BrpBezitBuitenlandsReisdocumentIndicatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder bezitBuitenlandsReisdocumentIndicatieStapel(
            final BrpStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud> stapel) {
        bezitBuitenlandsReisdocumentIndicatieStapel = stapel;
        return this;
    }

    /**
     * Voegt de bijhoudingsgemeenteStapel toe aan deze persoonslijst builder.
     * 
     * @param bijhoudingsgemeenteStapel
     *            de bijhoudingsgemeenteStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder bijhoudingsgemeenteStapel(
            final BrpStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel) {
        this.bijhoudingsgemeenteStapel = bijhoudingsgemeenteStapel;
        return this;
    }

    /**
     * Voegt de bijhoudingsverantwoordelijkheidStapel toe aan deze persoonslijst builder.
     * 
     * @param bijhoudingsverantwoordelijkheidStapel
     *            de bijhoudingsverantwoordelijkheidStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder bijhoudingsverantwoordelijkheidStapel(
            final BrpStapel<BrpBijhoudingsverantwoordelijkheidInhoud> bijhoudingsverantwoordelijkheidStapel) {
        this.bijhoudingsverantwoordelijkheidStapel = bijhoudingsverantwoordelijkheidStapel;
        return this;
    }

    /**
     * Voegt de derdeHeeftGezagIndicatieStapel toe aan deze persoonslijst builder.
     * 
     * @param derdeHeeftGezagIndicatieStapel
     *            de derdeHeeftGezagIndicatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder derdeHeeftGezagIndicatieStapel(
            final BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel) {
        this.derdeHeeftGezagIndicatieStapel = derdeHeeftGezagIndicatieStapel;
        return this;
    }

    /**
     * Voegt de europeseVerkiezingenStapel toe aan deze persoonslijst builder.
     * 
     * @param europeseVerkiezingenStapel
     *            de europeseVerkiezingenStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder europeseVerkiezingenStapel(
            final BrpStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel) {
        this.europeseVerkiezingenStapel = europeseVerkiezingenStapel;
        return this;
    }

    /**
     * Voegt de Geboorte BrpStapel toe aan deze persoonslijst builder.
     * 
     * @param geboorteStapel
     *            de geboorteStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder geboorteStapel(final BrpStapel<BrpGeboorteInhoud> geboorteStapel) {

        this.geboorteStapel = geboorteStapel;
        return this;
    }

    /**
     * Voegt de geprivilegieerdeIndicatieStapel toe aan deze persoonslijst builder.
     * 
     * @param geprivilegieerdeIndicatieStapel
     *            de geprivilegieerdeIndicatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder geprivilegieerdeIndicatieStapel(
            final BrpStapel<BrpGeprivilegieerdeIndicatieInhoud> geprivilegieerdeIndicatieStapel) {
        this.geprivilegieerdeIndicatieStapel = geprivilegieerdeIndicatieStapel;
        return this;
    }

    /**
     * Voegt de geslachtsaanduidingStapel toe aan deze persoonslijst builder.
     * 
     * @param geslachtsaanduidingStapel
     *            de geslachtsaanduidingStapel, mag null zijn
     * @return het builder object
     */
    public BrpPersoonslijstBuilder geslachtsaanduidingStapel(
            final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel) {
        this.geslachtsaanduidingStapel = geslachtsaanduidingStapel;
        return this;
    }

    /**
     * Voegt de Geslachtsnaamcomponent BrpStapel toe aan deze persoonslijst builder.
     * 
     * @param geslachtsnaamcomponentStapel
     *            de geslachtsnaamcomponentStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder geslachtsnaamcomponentStapel(
            final BrpStapel<BrpGeslachtsnaamcomponentInhoud> geslachtsnaamcomponentStapel) {
        if (geslachtsnaamcomponentStapel != null) {
            this.geslachtsnaamcomponentStapels.add(geslachtsnaamcomponentStapel);
        }
        return this;
    }

    /**
     * Voegt de lijst met Geslachtsnaamcomponent BrpStapels toe aan deze persoonslijst builder.
     * 
     * @param geslachtsnaamcomponentStapels
     *            de lijst met geslachtsnaamcomponentStapels, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder geslachtsnaamcomponentStapels(
            final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels) {
        if (geslachtsnaamcomponentStapels != null) {
            this.geslachtsnaamcomponentStapels.clear();
            this.geslachtsnaamcomponentStapels.addAll(geslachtsnaamcomponentStapels);
        }
        return this;
    }

    /**
     * Voegt de identificatienummer stapel toe aan deze persoonslijst builder.
     * 
     * @param identificatienummersStapel
     *            de identificatienummer, mag null zijn
     * @return het builder object
     */
    public BrpPersoonslijstBuilder identificatienummersStapel(
            final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel) {
        this.identificatienummersStapel = identificatienummersStapel;
        return this;
    }

    /**
     * Voegt de immigratieStapel toe aan deze persoonslijst builder.
     * 
     * @param immigratieStapel
     *            de immigratieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder immigratieStapel(final BrpStapel<BrpImmigratieInhoud> immigratieStapel) {
        this.immigratieStapel = immigratieStapel;
        return this;
    }

    /**
     * Voegt de inschrijvingStapel toe aan deze BrpPersoonslijstBuilder.
     * 
     * @param inschrijvingStapel
     *            de inschrijvingStapel, mag null zijn
     * @return de persoonslijstbuilder met daaraan de inschrijvingStapel toegevoegd
     */
    public BrpPersoonslijstBuilder inschrijvingStapel(final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel) {
        this.inschrijvingStapel = inschrijvingStapel;
        return this;
    }

    /**
     * Voegt de BrpNationaliteitStapel toe aan deze persoonslijst builder.
     * 
     * @param nationaliteitStapel
     *            de BrpNationaliteitStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder nationaliteitStapel(final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel) {
        if (nationaliteitStapel != null) {
            this.nationaliteitStapels.add(nationaliteitStapel);
        }
        return this;
    }

    /**
     * Voegt de lijst met BrpNationaliteitStapels toe aan deze persoonslijst builder.
     * 
     * @param nationaliteitStapels
     *            de lijst met BrpNationaliteitStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder nationaliteitStapels(
            final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels) {
        if (nationaliteitStapels != null) {
            this.nationaliteitStapels.clear();
            this.nationaliteitStapels.addAll(nationaliteitStapels);
        }
        return this;
    }

    /**
     * Voegt de onderCurateleIndicatieStapel toe aan deze persoonslijst builder.
     * 
     * @param onderCurateleIndicatieStapel
     *            de onderCurateleIndicatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder onderCurateleIndicatieStapel(
            final BrpStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel) {
        this.onderCurateleIndicatieStapel = onderCurateleIndicatieStapel;
        return this;
    }

    /**
     * Voegt de opschorting stapel toe aan deze persoonslijst builder.
     * 
     * @param opschortingStapel
     *            the opschortingStapel to set
     * @return de builder (this)
     */
    public BrpPersoonslijstBuilder opschortingStapel(final BrpStapel<BrpOpschortingInhoud> opschortingStapel) {
        this.opschortingStapel = opschortingStapel;
        return this;
    }

    /**
     * Voegt de overlijden stapel aan deze persoonslijst builder.
     * 
     * @param overlijdenStapel
     *            de overlijden stapel
     * @return de BrpPersoonslijstBuilder
     */
    public BrpPersoonslijstBuilder overlijdenStapel(final BrpStapel<BrpOverlijdenInhoud> overlijdenStapel) {
        this.overlijdenStapel = overlijdenStapel;
        return this;
    }

    /**
     * Voegt de persoonskaart stapel toe aan deze persoonslijst builder.
     * 
     * @param persoonskaartStapel
     *            the persoonskaartStapel to set
     * @return de builder (this)
     */
    public BrpPersoonslijstBuilder persoonskaartStapel(final BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel) {
        this.persoonskaartStapel = persoonskaartStapel;
        return this;
    }

    /**
     * Voegt de Reisdocument BrpStapel toe aan deze persoonslijst builder.
     * 
     * @param reisdocumentStapel
     *            de reisdocumentStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder reisdocumentStapel(final BrpStapel<BrpReisdocumentInhoud> reisdocumentStapel) {
        if (reisdocumentStapel != null) {
            this.reisdocumentStapels.add(reisdocumentStapel);
        }
        return this;
    }

    /**
     * Voegt de Reisdocument Stapels toe aan deze persoonslijst builder.
     * 
     * @param reisdocumentStapels
     *            de reisdocumentStapels, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder reisdocumentStapels(
            final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels) {
        if (reisdocumentStapels != null) {
            this.reisdocumentStapels.clear();
            this.reisdocumentStapels.addAll(reisdocumentStapels);
        }
        return this;
    }

    /**
     * Voegt de BrpRelatieStapel toe aan deze persoonslijst builder.
     * 
     * @param relatie
     *            de relatie, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder relatie(final BrpRelatie relatie) {
        if (relatie != null) {
            this.relaties.add(relatie);
        }
        return this;
    }

    /**
     * Voegt de BrpRelatieStapels toe aan deze persoonslijst builder.
     * 
     * @param relaties
     *            de relaties, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder relaties(final List<BrpRelatie> relaties) {
        if (relaties != null) {
            this.relaties.clear();
            this.relaties.addAll(relaties);
        }
        return this;
    }

    /**
     * Voegt de samengestelde naam stapel toe aan deze persoonlijst builder.
     * 
     * @param samengesteldeNaamStapel
     *            samengestelde naam stapel
     * @return builder (this)
     */
    public BrpPersoonslijstBuilder samengesteldeNaamStapel(
            final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel) {
        this.samengesteldeNaamStapel = samengesteldeNaamStapel;
        return this;
    }

    /**
     * Voegt de statenloosIndicatieStapel toe aan deze persoonslijst builder.
     * 
     * @param statenloosIndicatieStapel
     *            de statenloosIndicatieStapel, mag null zijn
     * @return het builder object
     */
    public BrpPersoonslijstBuilder statenloosIndicatieStapel(
            final BrpStapel<BrpStatenloosIndicatieInhoud> statenloosIndicatieStapel) {
        this.statenloosIndicatieStapel = statenloosIndicatieStapel;
        return this;
    }

    /**
     * Voegt de uitsluitingNederlandsKiesrechtStapel toe aan deze persoonslijst builder.
     * 
     * @param uitsluitingNederlandsKiesrechtStapel
     *            de uitsluitingNederlandsKiesrechtStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder uitsluitingNederlandsKiesrechtStapel(
            final BrpStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel) {
        this.uitsluitingNederlandsKiesrechtStapel = uitsluitingNederlandsKiesrechtStapel;
        return this;
    }

    /**
     * Voegt de BrpVastgesteldNietNederlanderIndicatieStapel toe aan deze persoonslijst builder.
     * 
     * @param vastgesteldNietNederlanderIndicatieStapel
     *            de BrpVastgesteldNietNederlanderIndicatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder vastgesteldNietNederlanderIndicatieStapel(
            final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel) {

        this.vastgesteldNietNederlanderIndicatieStapel = vastgesteldNietNederlanderIndicatieStapel;

        return this;
    }

    /**
     * Voegt de verblijfsrecht stapel aan deze persoonslijst builder.
     * 
     * @param verblijfsrechtStapel
     *            de verblijfsrecht stapel
     * @return de BrpPersoonslijstBuilder
     */
    public BrpPersoonslijstBuilder
            verblijfsrechtStapel(final BrpStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel) {
        this.verblijfsrechtStapel = verblijfsrechtStapel;
        return this;
    }

    /**
     * Voegt de verstrekkingsbeperking stapel toe aan deze persoonslijst builder.
     * 
     * @param verstrekkingsbeperkingStapel
     *            the verstrekkingsbeperkingStapel to set
     * @return de builder (this)
     */
    public BrpPersoonslijstBuilder verstrekkingsbeperkingStapel(
            final BrpStapel<BrpVerstrekkingsbeperkingInhoud> verstrekkingsbeperkingStapel) {
        this.verstrekkingsbeperkingStapel = verstrekkingsbeperkingStapel;
        return this;
    }

    /**
     * Voegt de Voornaam BrpStapel toe aan deze persoonslijst builder.
     * 
     * @param voornaamStapel
     *            de migratieVoornaamStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder voornaamStapel(final BrpStapel<BrpVoornaamInhoud> voornaamStapel) {
        if (voornaamStapel != null) {
            this.voornaamStapels.add(voornaamStapel);
        }
        return this;
    }

    /**
     * Voegt de lijst met Voornaam BrpStapels toe aan deze persoonslijst builder.
     * 
     * @param voornaamStapels
     *            de lijst met migratieVoornaamStapels, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     * 
     */
    public BrpPersoonslijstBuilder voornaamStapels(final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels) {
        if (voornaamStapels != null) {
            this.voornaamStapels.clear();
            this.voornaamStapels.addAll(voornaamStapels);
        }
        return this;
    }
}
