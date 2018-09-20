/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.migratie;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.Persoonslijst;
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

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Deze class representeert een migratie specifieke persoonslijst waarbij de inhoud in BRP structuur is weergegeven en
 * de historie in LO3 structuur.
 */
@Root
public final class MigratiePersoonslijst implements Persoonslijst {

    private final MigratieStapel<BrpAanschrijvingInhoud> aanschrijvingStapel;
    private final MigratieStapel<BrpAdresInhoud> adresStapel;
    private final MigratieStapel<BrpAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel;
    private final MigratieStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel;
    // CHECKSTYLE:OFF - Het afkorten van BRP namen maakt de code minder leesbaar
    private final MigratieStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> belemmeringVerstrekkingReisdocumentIndicatieStapel;
    private final MigratieStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud> bezitBuitenlandsReisdocumentIndicatieStapel;
    // CHECKSTYLE:ON
    private final MigratieStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel;
    private final MigratieStapel<BrpBijhoudingsverantwoordelijkheidInhoud> bijhoudingsverantwoordelijkheidStapel;
    private final MigratieStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel;
    private final MigratieStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel;
    private final MigratieStapel<BrpGeboorteInhoud> geboorteStapel;
    private final MigratieStapel<BrpGeprivilegieerdeIndicatieInhoud> geprivilegieerdeIndicatieStapel;
    private final MigratieStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    private final MigratieStapel<BrpIdentificatienummersInhoud> identificatienummerStapel;
    private final MigratieStapel<BrpImmigratieInhoud> immigratieStapel;
    private final MigratieStapel<BrpInschrijvingInhoud> inschrijvingStapel;
    private final List<MigratieStapel<BrpNationaliteitInhoud>> nationaliteitStapels;
    private final MigratieStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel;
    private final MigratieStapel<BrpOpschortingInhoud> opschortingStapel;
    private final MigratieStapel<BrpOverlijdenInhoud> overlijdenStapel;
    private final MigratieStapel<BrpPersoonskaartInhoud> persoonskaartStapel;
    private final List<MigratieStapel<BrpReisdocumentInhoud>> reisdocumentStapels;
    private final List<MigratieRelatie> relaties;
    private final MigratieStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    private final MigratieStapel<BrpStatenloosIndicatieInhoud> statenloosIndicatieStapel;
    private final MigratieStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel;
    // CHECKSTYLE:OFF - Het afkorten van BRP namen maakt de code minder leesbaar
    private final MigratieStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel;
    // CHECKSTYLE:ON
    private final MigratieStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel;
    private final MigratieStapel<BrpVerstrekkingsbeperkingInhoud> verstrekkingsbeperkingStapel;

    /**
     * Maakt een MigratiePersoonslijst object.
     * 
     * Note: default access modifier, de persoonslijst kan alleen worden aangemaakt via de builder.
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    MigratiePersoonslijst(
            @Element(name = "aanschrijvingStapel", required = false) final MigratieStapel<BrpAanschrijvingInhoud> aanschrijvingStapel,
            @Element(name = "adresStapel", required = false) final MigratieStapel<BrpAdresInhoud> adresStapel,
            @Element(name = "afgeleidAdministratiefStapel", required = false) final MigratieStapel<BrpAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel,
            @Element(name = "behandeldAlsNederlanderIndicatieStapel", required = false) final MigratieStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel,
            @Element(name = "belemmeringVerstrekkingReisdocumentIndicatieStapel", required = false) final MigratieStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> belemmeringVerstrekkingReisdocumentIndicatieStapel,
            @Element(name = "bezitBuitenlandsReisdocumentIndicatieStapel", required = false) final MigratieStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud> bezitBuitenlandsReisdocumentIndicatieStapel,
            @Element(name = "bijhoudingsgemeenteStapel", required = false) final MigratieStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel,
            @Element(name = "bijhoudingsverantwoordelijkheidStapel", required = false) final MigratieStapel<BrpBijhoudingsverantwoordelijkheidInhoud> bijhoudingsverantwoordelijkheidStapel,
            @Element(name = "derdeHeeftGezagIndicatieStapel", required = false) final MigratieStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel,
            @Element(name = "europeseVerkiezingenStapel", required = false) final MigratieStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel,
            @Element(name = "geboorteStapel", required = false) final MigratieStapel<BrpGeboorteInhoud> geboorteStapel,
            @Element(name = "geprivilegieerdeIndicatieStapel", required = false) final MigratieStapel<BrpGeprivilegieerdeIndicatieInhoud> geprivilegieerdeIndicatieStapel,
            @Element(name = "geslachtsaanduidingStapel", required = false) final MigratieStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel,
            @Element(name = "identificatienummerStapel", required = false) final MigratieStapel<BrpIdentificatienummersInhoud> identificatienummerStapel,
            @Element(name = "immigratieStapel", required = false) final MigratieStapel<BrpImmigratieInhoud> immigratieStapel,
            @Element(name = "inschrijvingStapel", required = false) final MigratieStapel<BrpInschrijvingInhoud> inschrijvingStapel,
            @ElementList(name = "nationaliteitStapels", entry = "nationaliteitStapel", type = MigratieStapel.class,
                    required = false) final List<MigratieStapel<BrpNationaliteitInhoud>> nationaliteitStapels,
            @Element(name = "onderCurateleIndicatieStapel", required = false) final MigratieStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel,
            @Element(name = "opschortingStapel", required = false) final MigratieStapel<BrpOpschortingInhoud> opschortingStapel,
            @Element(name = "overlijdenStapel", required = false) final MigratieStapel<BrpOverlijdenInhoud> overlijdenStapel,
            @Element(name = "persoonskaartStapel", required = false) final MigratieStapel<BrpPersoonskaartInhoud> persoonskaartStapel,
            @ElementList(name = "reisdocumentStapels", entry = "reisdocumentStapel", type = MigratieStapel.class,
                    required = false) final List<MigratieStapel<BrpReisdocumentInhoud>> reisdocumentStapels,
            @ElementList(name = "relaties", entry = "relatie", type = MigratieRelatie.class, required = false) final List<MigratieRelatie> relaties,
            @Element(name = "samengesteldeNaamStapel", required = false) final MigratieStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel,
            @Element(name = "statenloosIndicatieStapel", required = false) final MigratieStapel<BrpStatenloosIndicatieInhoud> statenloosIndicatieStapel,
            @Element(name = "uitsluitingNederlandsKiesrechtStapel", required = false) final MigratieStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel,
            @Element(name = "vastgesteldNietNederlanderIndicatieStapel", required = false) final MigratieStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel,
            @Element(name = "verblijfsrechtStapel", required = false) final MigratieStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel,
            @Element(name = "verstrekkingsbeperkingStapel", required = false) final MigratieStapel<BrpVerstrekkingsbeperkingInhoud> verstrekkingsbeperkingStapel) {
        // CHECKSTYLE:ON
        this.aanschrijvingStapel = aanschrijvingStapel;
        this.adresStapel = adresStapel;
        this.afgeleidAdministratiefStapel = afgeleidAdministratiefStapel;
        this.behandeldAlsNederlanderIndicatieStapel = behandeldAlsNederlanderIndicatieStapel;
        this.belemmeringVerstrekkingReisdocumentIndicatieStapel = belemmeringVerstrekkingReisdocumentIndicatieStapel;
        this.bezitBuitenlandsReisdocumentIndicatieStapel = bezitBuitenlandsReisdocumentIndicatieStapel;
        this.bijhoudingsgemeenteStapel = bijhoudingsgemeenteStapel;
        this.bijhoudingsverantwoordelijkheidStapel = bijhoudingsverantwoordelijkheidStapel;
        this.derdeHeeftGezagIndicatieStapel = derdeHeeftGezagIndicatieStapel;
        this.europeseVerkiezingenStapel = europeseVerkiezingenStapel;
        this.geboorteStapel = geboorteStapel;
        this.geprivilegieerdeIndicatieStapel = geprivilegieerdeIndicatieStapel;
        this.geslachtsaanduidingStapel = geslachtsaanduidingStapel;
        this.identificatienummerStapel = identificatienummerStapel;
        this.immigratieStapel = immigratieStapel;
        this.inschrijvingStapel = inschrijvingStapel;
        this.nationaliteitStapels = nationaliteitStapels;
        this.onderCurateleIndicatieStapel = onderCurateleIndicatieStapel;
        this.opschortingStapel = opschortingStapel;
        this.overlijdenStapel = overlijdenStapel;
        this.persoonskaartStapel = persoonskaartStapel;
        this.reisdocumentStapels = reisdocumentStapels;
        this.relaties = relaties;
        this.samengesteldeNaamStapel = samengesteldeNaamStapel;
        this.statenloosIndicatieStapel = statenloosIndicatieStapel;
        this.uitsluitingNederlandsKiesrechtStapel = uitsluitingNederlandsKiesrechtStapel;
        this.vastgesteldNietNederlanderIndicatieStapel = vastgesteldNietNederlanderIndicatieStapel;
        this.verblijfsrechtStapel = verblijfsrechtStapel;
        this.verstrekkingsbeperkingStapel = verstrekkingsbeperkingStapel;
    }

    private static <T> List<T> copyOf(final List<T> list) {
        return new ArrayList<T>(list);
    }

    /**
     * @return de aanschrijving stapel voor deze persoonslijst
     */
    @Element(name = "aanschrijvingStapel", required = false)
    public MigratieStapel<BrpAanschrijvingInhoud> getAanschrijvingStapel() {
        return aanschrijvingStapel;
    }

    /**
     * @return het actuele a-nummer van deze persoonslijst
     */
    @Override
    public Long getActueelAdministratienummer() {
        return getIdentificatienummerStapel().getMeestRecenteElement().getInhoud().getAdministratienummer();
    }

    /**
     * @return het actuele a-nummer van deze persoonslijst
     */
    @Override
    public BigDecimal getActueelAdministratienummerAsBigDecimal() {
        return getActueelAdministratienummer() == null ? null : BigDecimal.valueOf(getActueelAdministratienummer());
    }

    /**
     * @return de adres stapel voor deze persoonslijst, of null
     */
    @Element(name = "adresStapel", required = false)
    public MigratieStapel<BrpAdresInhoud> getAdresStapel() {
        return adresStapel;
    }

    /**
     * @return de Behandeld als Nederlander stapel voor deze persoonslijst
     */
    @Element(name = "afgeleidAdministratiefStapel", required = false)
    public MigratieStapel<BrpAfgeleidAdministratiefInhoud> getAfgeleidAdministratiefStapel() {
        return afgeleidAdministratiefStapel;
    }

    /**
     * @return de Behandeld als Nederlander stapel voor deze persoonslijst
     */
    @Element(name = "behandeldAlsNederlanderIndicatieStapel", required = false)
    public MigratieStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> getBehandeldAlsNederlanderIndicatieStapel() {
        return behandeldAlsNederlanderIndicatieStapel;
    }

    /**
     * @return de belemmeringVerstrekkingReisdocumentIndicatie stapel voor deze persoonslijst
     */
    @Element(name = "belemmeringVerstrekkingReisdocumentIndicatieStapel", required = false)
    public MigratieStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud>
            getBelemmeringVerstrekkingReisdocumentIndicatieStapel() {
        return belemmeringVerstrekkingReisdocumentIndicatieStapel;
    }

    /**
     * @return de bezitBuitenlandsReisdocumentIndicatie stapel voor deze persoonslijst
     */
    @Element(name = "bezitBuitenlandsReisdocumentIndicatieStapel", required = false)
    public MigratieStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud>
            getBezitBuitenlandsReisdocumentIndicatieStapel() {
        return bezitBuitenlandsReisdocumentIndicatieStapel;
    }

    /**
     * @return de bijhoudingsgemeente stapel voor deze persoonslijst, of null
     */
    @Element(name = "bijhoudingsgemeenteStapel", required = false)
    public MigratieStapel<BrpBijhoudingsgemeenteInhoud> getBijhoudingsgemeenteStapel() {

        return bijhoudingsgemeenteStapel;
    }

    /**
     * @return de bijhoudingsgemeente stapel voor deze persoonslijst, of null
     */
    @Element(name = "bijhoudingsverantwoordelijkheidStapel", required = false)
    public MigratieStapel<BrpBijhoudingsverantwoordelijkheidInhoud> getBijhoudingsverantwoordelijkheidStapel() {

        return bijhoudingsverantwoordelijkheidStapel;
    }

    /**
     * @return de 'derde heeft gezag'-indicatie stapel voor deze persoonslijst, of null
     */
    @Element(name = "derdeHeeftGezagIndicatieStapel", required = false)
    public MigratieStapel<BrpDerdeHeeftGezagIndicatieInhoud> getDerdeHeeftGezagIndicatieStapel() {

        return derdeHeeftGezagIndicatieStapel;
    }

    /**
     * @return de europese verkiezingen stapel voor deze persoonslijst, of null
     */
    @Element(name = "europeseVerkiezingenStapel", required = false)
    public MigratieStapel<BrpEuropeseVerkiezingenInhoud> getEuropeseVerkiezingenStapel() {

        return europeseVerkiezingenStapel;
    }

    /**
     * @return de geboorte stapel voor deze persoonslijst
     */
    @Element(name = "geboorteStapel", required = false)
    public MigratieStapel<BrpGeboorteInhoud> getGeboorteStapel() {
        return geboorteStapel;
    }

    /**
     * @return de geprivilegieerde indicatie stapel voor deze persoonslijst, of null
     */
    @Element(name = "geprivilegieerdeIndicatieStapel", required = false)
    public MigratieStapel<BrpGeprivilegieerdeIndicatieInhoud> getGeprivilegieerdeIndicatieStapel() {

        return geprivilegieerdeIndicatieStapel;
    }

    /**
     * @return de geslachtsaanduiding stapel voor deze persoonslijst
     */
    @Element(name = "geslachtsaanduidingStapel", required = false)
    public MigratieStapel<BrpGeslachtsaanduidingInhoud> getGeslachtsaanduidingStapel() {
        return geslachtsaanduidingStapel;
    }

    /**
     * @return de identificatienummer stapel voor deze persoonslijst
     */
    @Element(name = "identificatienummerStapel", required = false)
    public MigratieStapel<BrpIdentificatienummersInhoud> getIdentificatienummerStapel() {
        return identificatienummerStapel;
    }

    /**
     * @return de immigratie stapel voor deze persoonslijst, of null
     */
    @Element(name = "immigratieStapel", required = false)
    public MigratieStapel<BrpImmigratieInhoud> getImmigratieStapel() {

        return immigratieStapel;
    }

    /**
     * @return de inschrijving stapel voor deze persoonslijst
     */
    @Element(name = "inschrijvingStapel", required = false)
    public MigratieStapel<BrpInschrijvingInhoud> getInschrijvingStapel() {
        return inschrijvingStapel;
    }

    /**
     * @return de lijst met nationaliteit stapels voor deze persoonslijst
     */
    @ElementList(name = "nationaliteitStapels", entry = "nationaliteitStapel", type = MigratieStapel.class,
            required = false)
    public List<MigratieStapel<BrpNationaliteitInhoud>> getNationaliteitStapels() {
        return copyOf(nationaliteitStapels);
    }

    /**
     * @return de 'onder curatele'-indicatie stapel voor deze persoonslijst, of null
     */
    @Element(name = "onderCurateleIndicatieStapel", required = false)
    public MigratieStapel<BrpOnderCurateleIndicatieInhoud> getOnderCurateleIndicatieStapel() {

        return onderCurateleIndicatieStapel;
    }

    /**
     * @return de opschorting stapel voor deze persoonslijst, of null
     */
    @Element(name = "opschortingStapel", required = false)
    public MigratieStapel<BrpOpschortingInhoud> getOpschortingStapel() {

        return opschortingStapel;
    }

    /**
     * @return de overlijden stapel voor deze persoonslijst, of null
     */
    @Element(name = "overlijdenStapel", required = false)
    public MigratieStapel<BrpOverlijdenInhoud> getOverlijdenStapel() {

        return overlijdenStapel;
    }

    /**
     * @return de persoonskaart stapel voor deze persoonslijst, of null
     */
    @Element(name = "persoonskaartStapel", required = false)
    public MigratieStapel<BrpPersoonskaartInhoud> getPersoonskaartStapel() {

        return persoonskaartStapel;
    }

    /**
     * @return de reisdocument stapels voor deze persoonslijst
     */
    @ElementList(name = "reisdocumentStapels", entry = "reisdocumentStapel", type = MigratieStapel.class,
            required = false)
    public List<MigratieStapel<BrpReisdocumentInhoud>> getReisdocumentStapels() {
        return copyOf(reisdocumentStapels);
    }

    /**
     * @return de lijst met BrpRelatie stapels of null
     */
    @ElementList(name = "relaties", required = false, entry = "relatie", type = MigratieRelatie.class)
    public List<MigratieRelatie> getRelaties() {
        return copyOf(relaties);
    }

    /**
     * @return de samengestelde naam stapel voor deze persoonslijst, of null
     */
    @Element(name = "samengesteldeNaamStapel", required = false)
    public MigratieStapel<BrpSamengesteldeNaamInhoud> getSamengesteldeNaamStapel() {

        return samengesteldeNaamStapel;
    }

    /**
     * @return de Statenloos indicatie stapel voor deze persoonslijst
     */
    @Element(name = "statenloosIndicatieStapel", required = false)
    public MigratieStapel<BrpStatenloosIndicatieInhoud> getStatenloosIndicatieStapel() {
        return statenloosIndicatieStapel;
    }

    /**
     * @return de uitsluiting Nederlands kiesrecht stapel voor deze persoonslijst, of null
     */
    @Element(name = "uitsluitingNederlandsKiesrechtStapel", required = false)
    public MigratieStapel<BrpUitsluitingNederlandsKiesrechtInhoud> getUitsluitingNederlandsKiesrechtStapel() {
        return uitsluitingNederlandsKiesrechtStapel;
    }

    /**
     * @return de Vastgesteld Niet Nederlander indicatie stapel voor deze persoonslijst
     */
    @Element(name = "vastgesteldNietNederlanderIndicatieStapel", required = false)
    public MigratieStapel<BrpVastgesteldNietNederlanderIndicatieInhoud>
            getVastgesteldNietNederlanderIndicatieStapel() {
        return vastgesteldNietNederlanderIndicatieStapel;
    }

    /**
     * @return de verblijfsrecht stapel voor deze persoonslijst, of null
     */
    @Element(name = "verblijfsrechtStapel", required = false)
    public MigratieStapel<BrpVerblijfsrechtInhoud> getVerblijfsrechtStapel() {

        return verblijfsrechtStapel;
    }

    /**
     * @return de verstrekkingsbeperking stapel voor deze persoonslijst, of null
     */
    @Element(name = "verstrekkingsbeperkingStapel", required = false)
    public MigratieStapel<BrpVerstrekkingsbeperkingInhoud> getVerstrekkingsbeperkingStapel() {

        return verstrekkingsbeperkingStapel;
    }
}
