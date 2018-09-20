/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.migratie;

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

/**
 * Deze class helpt bij het maken van een MigratiePersoonslijst. Argumenten kunnen via method-chaining worden
 * toegevoegd.
 */
public final class MigratiePersoonslijstBuilder {

    private MigratieStapel<BrpAanschrijvingInhoud> aanschrijvingStapel;
    private MigratieStapel<BrpAdresInhoud> adresStapel;
    // CHECKSTYLE:OFF - Het afkorten van BRP namen maakt de code minder leesbaar
    private MigratieStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel;
    private MigratieStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> belemmeringVerstrekkingReisdocumentIndicatieStapel;
    private MigratieStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud> bezitBuitenlandsReisdocumentIndicatieStapel;
    // CHECKSTYLE:ON
    private MigratieStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel;
    private MigratieStapel<BrpBijhoudingsverantwoordelijkheidInhoud> bijhoudingsverantwoordelijkheidStapel;
    private MigratieStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel;
    private MigratieStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel;
    private MigratieStapel<BrpGeboorteInhoud> geboorteStapel;
    private MigratieStapel<BrpGeprivilegieerdeIndicatieInhoud> geprivilegieerdeIndicatieStapel;
    private MigratieStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    private MigratieStapel<BrpIdentificatienummersInhoud> identificatienummerStapel;
    private MigratieStapel<BrpImmigratieInhoud> immigratieStapel;
    private MigratieStapel<BrpInschrijvingInhoud> inschrijvingStapel;
    private final List<MigratieStapel<BrpNationaliteitInhoud>> nationaliteitStapels =
            new ArrayList<MigratieStapel<BrpNationaliteitInhoud>>();
    private MigratieStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel;
    private MigratieStapel<BrpOpschortingInhoud> opschortingStapel;
    private MigratieStapel<BrpOverlijdenInhoud> overlijdenStapel;
    private MigratieStapel<BrpPersoonskaartInhoud> persoonskaartStapel;
    private final List<MigratieStapel<BrpReisdocumentInhoud>> reisdocumentStapels =
            new ArrayList<MigratieStapel<BrpReisdocumentInhoud>>();
    private final List<MigratieRelatie> relaties = new ArrayList<MigratieRelatie>();
    private MigratieStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    private MigratieStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel;
    private MigratieStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel;
    private MigratieStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel;
    private MigratieStapel<BrpVerstrekkingsbeperkingInhoud> verstrekkingsbeperkingStapel;
    private MigratieStapel<BrpStatenloosIndicatieInhoud> statenloosIndicatieStapel;
    private MigratieStapel<BrpAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel;

    /**
     * Maak een lege builder.
     */
    public MigratiePersoonslijstBuilder() {
    }

    /**
     * Maak een builder gevuld met de stapels uit de persoonslijst.
     * 
     * @param persoonslijst
     *            initiele vulling
     */
    public MigratiePersoonslijstBuilder(final MigratiePersoonslijst persoonslijst) {
        aanschrijvingStapel = persoonslijst.getAanschrijvingStapel();
        adresStapel = persoonslijst.getAdresStapel();
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

        identificatienummerStapel = persoonslijst.getIdentificatienummerStapel();
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
        uitsluitingNederlandsKiesrechtStapel = persoonslijst.getUitsluitingNederlandsKiesrechtStapel();
        vastgesteldNietNederlanderIndicatieStapel = persoonslijst.getVastgesteldNietNederlanderIndicatieStapel();
        verblijfsrechtStapel = persoonslijst.getVerblijfsrechtStapel();
        verstrekkingsbeperkingStapel = persoonslijst.getVerstrekkingsbeperkingStapel();

        statenloosIndicatieStapel = persoonslijst.getStatenloosIndicatieStapel();
        afgeleidAdministratiefStapel = persoonslijst.getAfgeleidAdministratiefStapel();
    }

    /**
     * @return een nieuwe MigratiePersoonslijst object o.b.v. de parameters van deze builder
     */
    public MigratiePersoonslijst build() {
        return new MigratiePersoonslijst(aanschrijvingStapel, adresStapel, afgeleidAdministratiefStapel,
                behandeldAlsNederlanderIndicatieStapel, belemmeringVerstrekkingReisdocumentIndicatieStapel,
                bezitBuitenlandsReisdocumentIndicatieStapel, bijhoudingsgemeenteStapel,
                bijhoudingsverantwoordelijkheidStapel, derdeHeeftGezagIndicatieStapel, europeseVerkiezingenStapel,
                geboorteStapel, geprivilegieerdeIndicatieStapel, geslachtsaanduidingStapel,
                identificatienummerStapel, immigratieStapel, inschrijvingStapel, nationaliteitStapels,
                onderCurateleIndicatieStapel, opschortingStapel, overlijdenStapel, persoonskaartStapel,
                reisdocumentStapels, relaties, samengesteldeNaamStapel, statenloosIndicatieStapel,
                uitsluitingNederlandsKiesrechtStapel, vastgesteldNietNederlanderIndicatieStapel,
                verblijfsrechtStapel, verstrekkingsbeperkingStapel);
    }

    /**
     * Voegt de Aanschrijving MigratieStapel toe aan deze persoonslijst builder.
     * 
     * @param aanschrijvingStapel
     *            de aanschrijvingStapel, mag niet null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder aanschrijvingStapel(
            final MigratieStapel<BrpAanschrijvingInhoud> aanschrijvingStapel) {
        this.aanschrijvingStapel = aanschrijvingStapel;
        return this;
    }

    /**
     * Voegt de adres stapel toe aan deze persoonslijst builder.
     * 
     * @param adresStapel
     *            adres stapel
     * @return de builder (this)
     */
    public MigratiePersoonslijstBuilder adresStapel(final MigratieStapel<BrpAdresInhoud> adresStapel) {
        this.adresStapel = adresStapel;
        return this;

    }

    /**
     * Voegt de afgeleid administratief stapel toe aan deze persoonslijst builder.
     * 
     * @param afgeleidAdministratiefStapel
     *            stapel
     * @return de builder (this)
     */
    public MigratiePersoonslijstBuilder afgeleidAdministratiefStapel(
            final MigratieStapel<BrpAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel) {
        this.afgeleidAdministratiefStapel = afgeleidAdministratiefStapel;
        return this;

    }

    /**
     * Voegt de statenloos stapel toe aan deze persoonslijst builder.
     * 
     * @param statenloosIndicatieStapel
     *            stapel
     * @return de builder (this)
     */
    public MigratiePersoonslijstBuilder statenloosIndicatieStapel(
            final MigratieStapel<BrpStatenloosIndicatieInhoud> statenloosIndicatieStapel) {
        this.statenloosIndicatieStapel = statenloosIndicatieStapel;
        return this;
    }

    /**
     * Voegt de MigratieBehandeldAlsNederlanderIndicatieStapel toe aan deze persoonslijst builder.
     * 
     * @param behandeldAlsNederlanderIndicatieStapel
     *            de MigratieBehandeldAlsNederlanderIndicatieStapel, mag niet null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder behandeldAlsNederlanderIndicatieStapel(
            final MigratieStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel) {
        this.behandeldAlsNederlanderIndicatieStapel = behandeldAlsNederlanderIndicatieStapel;
        return this;
    }

    /**
     * Voegt de belemmeringVerstrekkingReisdocumentIndicatieStapel toe aan deze persoonslijst builder.
     * 
     * @param stapel
     *            de belemmeringVerstrekkingReisdocumentIndicatieStapel, mag null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder belemmeringVerstrekkingReisdocumentIndicatieStapel(
            final MigratieStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> stapel) {
        belemmeringVerstrekkingReisdocumentIndicatieStapel = stapel;

        return this;
    }

    /**
     * Voegt de bezitBuitenlandsReisdocumentIndicatieStapel toe aan deze persoonslijst builder.
     * 
     * @param stapel
     *            de belemmeringVerstrekkingReisdocumentIndicatieStapel, mag null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder bezitBuitenlandsReisdocumentIndicatieStapel(
            final MigratieStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud> stapel) {
        bezitBuitenlandsReisdocumentIndicatieStapel = stapel;
        return this;
    }

    /**
     * Voegt de bijhoudingsgemeente stapel toe aan deze persoonslijst builder.
     * 
     * @param bijhoudingsgemeenteStapel
     *            bijhoudingsgemeente stapel
     * @return de builder (this)
     */
    public MigratiePersoonslijstBuilder bijhoudingsgemeenteStapel(
            final MigratieStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel) {
        this.bijhoudingsgemeenteStapel = bijhoudingsgemeenteStapel;
        return this;
    }

    /**
     * Voegt de bijhoudingsverantwoordelijkheid stapel toe aan deze persoonslijst builder.
     * 
     * @param bijhoudingsverantwoordelijkheidStapel
     *            bijhoudingsverantwoordelijkheid stapel
     * @return de builder (this)
     */
    public MigratiePersoonslijstBuilder bijhoudingsverantwoordelijkheidStapel(
            final MigratieStapel<BrpBijhoudingsverantwoordelijkheidInhoud> bijhoudingsverantwoordelijkheidStapel) {
        this.bijhoudingsverantwoordelijkheidStapel = bijhoudingsverantwoordelijkheidStapel;
        return this;
    }

    /**
     * Voegt de derdeHeeftGezagIndicatie stapel toe aan deze persoonslijst builder.
     * 
     * @param derdeHeeftGezagIndicatieStapel
     *            derdeHeeftGezagIndicatie stapel
     * @return de builder (this)
     */
    public MigratiePersoonslijstBuilder derdeHeeftGezagIndicatieStapel(
            final MigratieStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel) {
        this.derdeHeeftGezagIndicatieStapel = derdeHeeftGezagIndicatieStapel;
        return this;
    }

    /**
     * Voegt de europeseVerkiezingen stapel toe aan deze persoonslijst builder.
     * 
     * @param europeseVerkiezingenStapel
     *            europeseVerkiezingen stapel
     * @return de builder (this)
     */
    public MigratiePersoonslijstBuilder europeseVerkiezingen(
            final MigratieStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel) {
        this.europeseVerkiezingenStapel = europeseVerkiezingenStapel;
        return this;
    }

    /**
     * Voegt de Geboorte MigratieStapel toe aan deze persoonslijst builder.
     * 
     * @param geboorteStapel
     *            de geboorteStapel, mag niet null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder geboorteStapel(final MigratieStapel<BrpGeboorteInhoud> geboorteStapel) {
        this.geboorteStapel = geboorteStapel;
        return this;
    }

    /**
     * Voegt de geprivilegieerde stapel toe aan deze persoonslijst builder.
     * 
     * @param geprivilegieerdeIndicatieStapel
     *            geprivilegieerdeIndicatie stapel
     * @return de builder (this)
     */
    public MigratiePersoonslijstBuilder geprivilegieerdeIndicatieStapel(
            final MigratieStapel<BrpGeprivilegieerdeIndicatieInhoud> geprivilegieerdeIndicatieStapel) {
        this.geprivilegieerdeIndicatieStapel = geprivilegieerdeIndicatieStapel;
        return this;
    }

    /**
     * Voegt de geslachtsaanduidingStapel toe aan deze persoonslijst builder.
     * 
     * @param geslachtsaanduidingStapel
     *            de geslachtsaanduidingStapel, mag null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder geslachtsaanduidingStapel(
            final MigratieStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel) {
        this.geslachtsaanduidingStapel = geslachtsaanduidingStapel;
        return this;
    }

    /**
     * Voegt de identificatienummer stapel toe aan deze persoonslijst builder.
     * 
     * @param identificatienummerStapel
     *            de identificatienummer, mag null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder identificatienummerStapel(
            final MigratieStapel<BrpIdentificatienummersInhoud> identificatienummerStapel) {
        this.identificatienummerStapel = identificatienummerStapel;
        return this;
    }

    /**
     * Voegt de immigratie stapel toe aan deze persoonslijst builder.
     * 
     * @param immigratieStapel
     *            immigratie stapel
     * @return de builder (this)
     */
    public MigratiePersoonslijstBuilder immigratieStapel(final MigratieStapel<BrpImmigratieInhoud> immigratieStapel) {
        this.immigratieStapel = immigratieStapel;
        return this;
    }

    /**
     * Voegt de Inschrijving MigratieStapel toe aan deze persoonslijst builder.
     * 
     * @param inschrijvingStapel
     *            de inschrijvingStapel, mag niet null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder inschrijvingStapel(
            final MigratieStapel<BrpInschrijvingInhoud> inschrijvingStapel) {
        this.inschrijvingStapel = inschrijvingStapel;
        return this;
    }

    /**
     * Voegt de MigratieNationaliteitStapel toe aan deze persoonslijst builder.
     * 
     * @param nationaliteitStapel
     *            de MigratieNationaliteitStapel, mag niet null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder nationaliteitStapel(
            final MigratieStapel<BrpNationaliteitInhoud> nationaliteitStapel) {
        if (nationaliteitStapel != null) {
            this.nationaliteitStapels.add(nationaliteitStapel);
        }
        return this;
    }

    /**
     * Voegt de MigratieNationaliteitStapel toe aan deze persoonslijst builder.
     * 
     * @param nationaliteitStapels
     *            de MigratieNationaliteitStapel, mag niet null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder nationaliteitStapels(
            final List<MigratieStapel<BrpNationaliteitInhoud>> nationaliteitStapels) {
        this.nationaliteitStapels.clear();
        if (nationaliteitStapels != null) {
            this.nationaliteitStapels.addAll(nationaliteitStapels);
        }
        return this;
    }

    /**
     * Voegt de onderCurateleIndicatie stapel toe aan deze persoonslijst builder.
     * 
     * @param onderCurateleIndicatieStapel
     *            onderCurateleIndicatie stapel
     * @return de builder (this)
     */
    public MigratiePersoonslijstBuilder onderCurateleIndicatieStapel(
            final MigratieStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel) {
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
    public MigratiePersoonslijstBuilder
            opschortingStapel(final MigratieStapel<BrpOpschortingInhoud> opschortingStapel) {
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
    public MigratiePersoonslijstBuilder overlijdenStapel(final MigratieStapel<BrpOverlijdenInhoud> overlijdenStapel) {
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
    public MigratiePersoonslijstBuilder persoonskaartStapel(
            final MigratieStapel<BrpPersoonskaartInhoud> persoonskaartStapel) {
        this.persoonskaartStapel = persoonskaartStapel;
        return this;
    }

    /**
     * Voegt de Reisdocument MigratieStapel toe aan deze persoonslijst builder.
     * 
     * @param reisdocumentStapel
     *            de reisdocumentStapel, mag null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder reisdocumentStapel(
            final MigratieStapel<BrpReisdocumentInhoud> reisdocumentStapel) {
        if (reisdocumentStapel != null) {
            this.reisdocumentStapels.add(reisdocumentStapel);
        }
        return this;
    }

    /**
     * Voegt de Reisdocument MigratieStapels toe aan deze persoonslijst builder.
     * 
     * @param reisdocumentStapels
     *            de reisdocumentStapels, mag null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder reisdocumentStapels(
            final List<MigratieStapel<BrpReisdocumentInhoud>> reisdocumentStapels) {
        this.reisdocumentStapels.clear();
        if (reisdocumentStapels != null) {
            this.reisdocumentStapels.addAll(reisdocumentStapels);
        }
        return this;
    }

    /**
     * Voegt de MigratieRelatieStapel toe aan deze persoonslijst builder.
     * 
     * @param relatie
     *            de relatieStapel, mag null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder relatie(final MigratieRelatie relatie) {
        if (relatie != null) {
            this.relaties.add(relatie);
        }
        return this;
    }

    /**
     * Voegt de MigratieRelatieStapels toe aan deze persoonslijst builder.
     * 
     * @param relaties
     *            de relatieStapels, mag null zijn
     * @return het MigratiePersoonslijstBuilder object
     * @throws NullPointerException
     *             als relatieStapels null is
     */
    public MigratiePersoonslijstBuilder relaties(final List<MigratieRelatie> relaties) {
        this.relaties.clear();
        if (relaties != null) {
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
    public MigratiePersoonslijstBuilder samengesteldeNaamStapel(
            final MigratieStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel) {
        this.samengesteldeNaamStapel = samengesteldeNaamStapel;
        return this;
    }

    /**
     * Voegt de uitsluitingNederlandsKiesrecht stapel toe aan deze persoonslijst builder.
     * 
     * @param uitsluitingNederlandsKiesrechtStapel
     *            uitsluitingNederlandsKiesrecht stapel
     * @return de builder (this)
     */
    public MigratiePersoonslijstBuilder uitsluitingNederlandsKiesrecht(
            final MigratieStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel) {
        this.uitsluitingNederlandsKiesrechtStapel = uitsluitingNederlandsKiesrechtStapel;
        return this;
    }

    /**
     * Voegt de MigratieVastgesteldNietNederlanderIndicatieStapel toe aan deze persoonslijst builder.
     * 
     * @param stapel
     *            de MigratieVastgesteldNietNederlanderIndicatieStapel, mag niet null zijn
     * @return het MigratiePersoonslijstBuilder object
     */
    public MigratiePersoonslijstBuilder vastgesteldNietNederlanderIndicatieStapel(
            final MigratieStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> stapel) {
        this.vastgesteldNietNederlanderIndicatieStapel = stapel;
        return this;
    }

    /**
     * Voegt de verblijfsrecht stapel aan deze persoonslijst builder.
     * 
     * @param verblijfsrechtStapel
     *            de verblijfsrecht stapel
     * @return de BrpPersoonslijstBuilder
     */
    public MigratiePersoonslijstBuilder verblijfsrechtStapel(
            final MigratieStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel) {
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
    public MigratiePersoonslijstBuilder verstrekkingsbeperkingStapel(
            final MigratieStapel<BrpVerstrekkingsbeperkingInhoud> verstrekkingsbeperkingStapel) {
        this.verstrekkingsbeperkingStapel = verstrekkingsbeperkingStapel;
        return this;
    }
}
