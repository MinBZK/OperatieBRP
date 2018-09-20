/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Deze class representeert de migratie specifieke kijk op een BRP persoon. De BRP persoonslijst.
 */
@Root
public final class BrpPersoonslijst implements Persoonslijst {

    private final BrpStapel<BrpAanschrijvingInhoud> aanschrijvingStapel;
    private final BrpStapel<BrpAdresInhoud> adresStapel;
    private final BrpStapel<BrpAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel;
    private final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel;
    // CHECKSTYLE:OFF het afkorten van namen uit BRP maakt de code minder leesbaar
    private final BrpStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> belemmeringVerstrekkingReisdocumentIndicatieStapel;
    private final BrpStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud> bezitBuitenlandsReisdocumentIndicatieStapel;
    // CHECKSTYLE:ON
    private final BrpStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel;
    private final BrpStapel<BrpBijhoudingsverantwoordelijkheidInhoud> bijhoudingsverantwoordelijkheidStapel;
    private final BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel;
    private final BrpStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel;
    private final BrpStapel<BrpGeboorteInhoud> geboorteStapel;
    private final BrpStapel<BrpGeprivilegieerdeIndicatieInhoud> geprivilegieerdeIndicatieStapel;
    private final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    private final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels;
    private final BrpStapel<BrpIdentificatienummersInhoud> identificatienummerStapel;
    private final BrpStapel<BrpImmigratieInhoud> immigratieStapel;
    private final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel;
    private final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels;
    private final BrpStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel;
    private final BrpStapel<BrpOpschortingInhoud> opschortingStapel;
    private final BrpStapel<BrpOverlijdenInhoud> overlijdenStapel;
    private final BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel;
    private final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels;
    private final List<BrpRelatie> relaties;
    private final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    private final BrpStapel<BrpStatenloosIndicatieInhoud> statenloosIndicatieStapel;
    private final BrpStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel;
    private final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel;
    private final BrpStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel;
    private final BrpStapel<BrpVerstrekkingsbeperkingInhoud> verstrekkingsbeperkingStapel;
    private final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels;

    /**
     * Maakt een nieuw BrpPersoonslijst object.
     * 
     * Note: default access modifier, de persoonslijst kan alleen worden aangemaakt via de builder
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    BrpPersoonslijst(
            @Element(name = "aanschrijvingStapel", required = false) final BrpStapel<BrpAanschrijvingInhoud> aanschrijvingStapel,
            @Element(name = "adresStapel", required = false) final BrpStapel<BrpAdresInhoud> adresStapel,
            @Element(name = "afgeleidAdministratiefStapel", required = false) final BrpStapel<BrpAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel,
            @Element(name = "behandeldAlsNederlanderIndicatieStapel", required = false) final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel,
            @Element(name = "belemmeringVerstrekkingReisdocumentIndicatieStapel", required = false) final BrpStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> belemmeringVerstrekkingReisdocumentIndicatieStapel,
            @Element(name = "bezitBuitenlandsReisdocumentIndicatieStapel", required = false) final BrpStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud> bezitBuitenlandsReisdocumentIndicatieStapel,
            @Element(name = "bijhoudingsgemeenteStapel", required = false) final BrpStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel,
            @Element(name = "bijhoudingsverantwoordelijkheidStapel", required = false) final BrpStapel<BrpBijhoudingsverantwoordelijkheidInhoud> bijhoudingsverantwoordelijkheidStapel,
            @Element(name = "derdeHeeftGezagIndicatieStapel", required = false) final BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel,
            @Element(name = "europeseVerkiezingenStapel", required = false) final BrpStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel,
            @Element(name = "geboorteStapel", required = false) final BrpStapel<BrpGeboorteInhoud> geboorteStapel,
            @Element(name = "geprivilegieerdeIndicatieStapel", required = false) final BrpStapel<BrpGeprivilegieerdeIndicatieInhoud> geprivilegieerdeIndicatieStapel,
            @Element(name = "geslachtsaanduidingStapel", required = false) final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel,
            @ElementList(name = "geslachtsnaamcomponentStapels", entry = "geslachtsnaamcomponentStapel",
                    type = BrpStapel.class, required = false) final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels,
            @Element(name = "identificatienummerStapel", required = false) final BrpStapel<BrpIdentificatienummersInhoud> identificatienummerStapel,
            @Element(name = "immigratieStapel", required = false) final BrpStapel<BrpImmigratieInhoud> immigratieStapel,
            @Element(name = "inschrijvingStapel", required = false) final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel,
            @ElementList(name = "nationaliteitStapels", entry = "nationaliteitStapel", type = BrpStapel.class,
                    required = false) final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels,
            @Element(name = "onderCurateleIndicatieStapel", required = false) final BrpStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel,
            @Element(name = "opschortingStapel", required = false) final BrpStapel<BrpOpschortingInhoud> opschortingStapel,
            @Element(name = "overlijdenStapel", required = false) final BrpStapel<BrpOverlijdenInhoud> overlijdenStapel,
            @Element(name = "persoonskaartStapel", required = false) final BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel,
            @ElementList(name = "reisdocumentStapels", entry = "reisdocumentStapel", type = BrpStapel.class,
                    required = false) final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels,
            @ElementList(name = "relaties", entry = "relatie", type = BrpRelatie.class, required = false) final List<BrpRelatie> relaties,
            @Element(name = "samengesteldeNaamStapel", required = false) final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel,
            @Element(name = "statenloosIndicatieStapel", required = false) final BrpStapel<BrpStatenloosIndicatieInhoud> statenloosIndicatieStapel,
            @Element(name = "uitsluitingNederlandsKiesrechtStapel", required = false) final BrpStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel,
            @Element(name = "vastgesteldNietNederlanderIndicatieStapel", required = false) final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel,
            @Element(name = "verblijfsrechtStapel", required = false) final BrpStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel,
            @Element(name = "verstrekkingsbeperkingStapel", required = false) final BrpStapel<BrpVerstrekkingsbeperkingInhoud> verstrekkingsbeperkingStapel,
            @ElementList(name = "voornaamStapels", entry = "voornaamStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels) {
        // CHECKSTYLE:ON
        super();
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
        this.geslachtsnaamcomponentStapels = geslachtsnaamcomponentStapels;
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
        this.voornaamStapels = voornaamStapels;
    }

    /**
     * Valideer de BRP persoonslijst.
     */
    // CHECKSTYLE:OFF - Executable Statement Size. Er moeten nu eenmaal veel verschillende stapels worden gevalideerd.
    public void valideer() {
        // CHECKSTYLE:ON
        // inhoud validatie (in meeste gevallen no-op)
        BrpPersoonslijstValidator.valideerInhoud(aanschrijvingStapel);
        BrpPersoonslijstValidator.valideerInhoud(adresStapel);
        BrpPersoonslijstValidator.valideerInhoud(afgeleidAdministratiefStapel);
        BrpPersoonslijstValidator.valideerInhoud(behandeldAlsNederlanderIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(belemmeringVerstrekkingReisdocumentIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(bezitBuitenlandsReisdocumentIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(bijhoudingsgemeenteStapel);
        BrpPersoonslijstValidator.valideerInhoud(bijhoudingsverantwoordelijkheidStapel);
        BrpPersoonslijstValidator.valideerInhoud(derdeHeeftGezagIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(europeseVerkiezingenStapel);
        BrpPersoonslijstValidator.valideerInhoud(geboorteStapel);
        BrpPersoonslijstValidator.valideerInhoud(geprivilegieerdeIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(geslachtsaanduidingStapel);
        BrpPersoonslijstValidator.valideerInhoud(geslachtsnaamcomponentStapels);
        BrpPersoonslijstValidator.valideerInhoud(identificatienummerStapel);
        BrpPersoonslijstValidator.valideerInhoud(immigratieStapel);
        BrpPersoonslijstValidator.valideerInhoud(inschrijvingStapel);
        BrpPersoonslijstValidator.valideerInhoud(nationaliteitStapels);
        BrpPersoonslijstValidator.valideerInhoud(onderCurateleIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(opschortingStapel);
        BrpPersoonslijstValidator.valideerInhoud(overlijdenStapel);
        BrpPersoonslijstValidator.valideerInhoud(persoonskaartStapel);
        BrpPersoonslijstValidator.valideerInhoud(reisdocumentStapels);
        BrpPersoonslijstValidator.valideerInhoud(samengesteldeNaamStapel);
        BrpPersoonslijstValidator.valideerInhoud(statenloosIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(uitsluitingNederlandsKiesrechtStapel);
        BrpPersoonslijstValidator.valideerInhoud(vastgesteldNietNederlanderIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(verblijfsrechtStapel);
        BrpPersoonslijstValidator.valideerInhoud(verstrekkingsbeperkingStapel);
        BrpPersoonslijstValidator.valideerInhoud(voornaamStapels);

        // valideer relaties
        for (final BrpRelatie relatie : relaties) {
            relatie.valideer();
        }

        // groepsoverstijgende validaties
        BrpPersoonslijstValidator.valideerGeprivilegieerde(geprivilegieerdeIndicatieStapel, nationaliteitStapels);
        BrpPersoonslijstValidator.valideerActueleGeslachtsnaam(geslachtsnaamcomponentStapels, aanschrijvingStapel,
                samengesteldeNaamStapel);
        BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(
                behandeldAlsNederlanderIndicatieStapel, vastgesteldNietNederlanderIndicatieStapel);
    }

    /**
     * @return het actuele a-nummer van deze persoonslijst
     */
    @Override
    public Long getActueelAdministratienummer() {
        return getIdentificatienummerStapel() == null ? null : getIdentificatienummerStapel()
                .getMeestRecenteElement().getInhoud().getAdministratienummer();
    }

    /**
     * @return het actuele a-nummer van deze persoonslijst
     */
    @Override
    public BigDecimal getActueelAdministratienummerAsBigDecimal() {
        return getActueelAdministratienummer() == null ? null : BigDecimal.valueOf(getActueelAdministratienummer());
    }

    /**
     * @return de aanschrijving stapel voor deze persoonslijst
     */
    @Element(name = "aanschrijvingStapel", required = false)
    public BrpStapel<BrpAanschrijvingInhoud> getAanschrijvingStapel() {
        return aanschrijvingStapel;
    }

    /**
     * @return de adres stapel voor deze persoonslijst, of null
     */
    @Element(name = "adresStapel", required = false)
    public BrpStapel<BrpAdresInhoud> getAdresStapel() {
        return adresStapel;
    }

    /**
     * @return de afgeleid administratief stapel voor deze persoonslijst
     */
    @Element(name = "afgeleidAdministratiefStapel", required = false)
    public BrpStapel<BrpAfgeleidAdministratiefInhoud> getAfgeleidAdministratiefStapel() {
        return afgeleidAdministratiefStapel;
    }

    /**
     * @return de 'Behandeld als Nederlander'-indicatie stapel voor deze persoonslijst
     */
    @Element(name = "behandeldAlsNederlanderIndicatieStapel", required = false)
    public BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> getBehandeldAlsNederlanderIndicatieStapel() {
        return behandeldAlsNederlanderIndicatieStapel;
    }

    /**
     * @return de 'Belemmering verstrekking reisdocument'-indicatie stapel voor deze persoonslijst
     */
    @Element(name = "belemmeringVerstrekkingReisdocumentIndicatieStapel", required = false)
    public BrpStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud>
            getBelemmeringVerstrekkingReisdocumentIndicatieStapel() {
        return belemmeringVerstrekkingReisdocumentIndicatieStapel;
    }

    /**
     * @return de 'Bezit buitenlands reisdocument'-indicatie stapel voor deze persoonslijst
     */
    @Element(name = "bezitBuitenlandsReisdocumentIndicatieStapel", required = false)
    public BrpStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud> getBezitBuitenlandsReisdocumentIndicatieStapel() {
        return bezitBuitenlandsReisdocumentIndicatieStapel;
    }

    /**
     * @return de bijhoudingsgemeente stapel voor deze persoonslijst, of null
     */
    @Element(name = "bijhoudingsgemeenteStapel", required = false)
    public BrpStapel<BrpBijhoudingsgemeenteInhoud> getBijhoudingsgemeenteStapel() {
        return bijhoudingsgemeenteStapel;
    }

    /**
     * @return de bijhoudingsverantwoordelijkheid stapel voor deze persoonslijst, of null
     */
    @Element(name = "bijhoudingsverantwoordelijkheidStapel", required = false)
    public BrpStapel<BrpBijhoudingsverantwoordelijkheidInhoud> getBijhoudingsverantwoordelijkheidStapel() {
        return bijhoudingsverantwoordelijkheidStapel;
    }

    /**
     * @return de 'Derde heeft gezag'-indicatie stapel voor deze persoonslijst, of null
     */
    @Element(name = "derdeHeeftGezagIndicatieStapel", required = false)
    public BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> getDerdeHeeftGezagIndicatieStapel() {
        return derdeHeeftGezagIndicatieStapel;
    }

    /**
     * @return de Europese verkiezingen stapel voor deze persoonslijst, of null
     */
    @Element(name = "europeseVerkiezingenStapel", required = false)
    public BrpStapel<BrpEuropeseVerkiezingenInhoud> getEuropeseVerkiezingenStapel() {
        return europeseVerkiezingenStapel;
    }

    /**
     * @return de geboortestapel voor deze persoonslijst
     */
    @Element(name = "geboorteStapel", required = false)
    public BrpStapel<BrpGeboorteInhoud> getGeboorteStapel() {
        return geboorteStapel;
    }

    /**
     * @return de geprivilegieerde indicatie stapel voor deze persoonslijst, of null
     */
    @Element(name = "geprivilegieerdeIndicatieStapel", required = false)
    public BrpStapel<BrpGeprivilegieerdeIndicatieInhoud> getGeprivilegieerdeIndicatieStapel() {
        return geprivilegieerdeIndicatieStapel;
    }

    /**
     * @return de geslachtsaanduiding stapel voor deze persoonslijst
     */
    @Element(name = "geslachtsaanduidingStapel", required = false)
    public BrpStapel<BrpGeslachtsaanduidingInhoud> getGeslachtsaanduidingStapel() {
        return geslachtsaanduidingStapel;
    }

    /**
     * @return de geslachtsnaamcomponent stapels voor deze persoonslijst
     */
    @ElementList(name = "geslachtsnaamcomponentStapels", entry = "geslachtsnaamcomponentStapel",
            type = BrpStapel.class, required = false)
    public List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> getGeslachtsnaamcomponentStapels() {
        return geslachtsnaamcomponentStapels;
    }

    /**
     * @return de identificatienummer stapel voor deze persoonslijst
     */
    @Element(name = "identificatienummerStapel", required = false)
    public BrpStapel<BrpIdentificatienummersInhoud> getIdentificatienummerStapel() {
        return identificatienummerStapel;
    }

    /**
     * @return de immigratie stapel voor deze persoonslijst, of null
     */
    @Element(name = "immigratieStapel", required = false)
    public BrpStapel<BrpImmigratieInhoud> getImmigratieStapel() {
        return immigratieStapel;
    }

    /**
     * @return de inschrijving stapel voor deze persoonslijst
     */
    @Element(name = "inschrijvingStapel", required = false)
    public BrpStapel<BrpInschrijvingInhoud> getInschrijvingStapel() {
        return inschrijvingStapel;
    }

    /**
     * @return de lijst met nationaliteit stapels voor deze persoonslijst
     */
    @ElementList(name = "nationaliteitStapels", entry = "nationaliteitStapel", type = BrpStapel.class,
            required = false)
    public List<BrpStapel<BrpNationaliteitInhoud>> getNationaliteitStapels() {
        return nationaliteitStapels;
    }

    /**
     * @return de 'Onder curatele'-indicatie stapel voor deze persoonslijst, of null
     */
    @Element(name = "onderCurateleIndicatieStapel", required = false)
    public BrpStapel<BrpOnderCurateleIndicatieInhoud> getOnderCurateleIndicatieStapel() {
        return onderCurateleIndicatieStapel;
    }

    /**
     * @return de opschorting stapel voor deze persoonslijst, of null
     */
    @Element(name = "opschortingStapel", required = false)
    public BrpStapel<BrpOpschortingInhoud> getOpschortingStapel() {
        return opschortingStapel;
    }

    /**
     * @return de overlijden stapel voor deze persoonslijst, of null
     */
    @Element(name = "overlijdenStapel", required = false)
    public BrpStapel<BrpOverlijdenInhoud> getOverlijdenStapel() {
        return overlijdenStapel;
    }

    /**
     * @return de persoonskaart stapel voor deze persoonslijst, of null
     */
    @Element(name = "persoonskaartStapel", required = false)
    public BrpStapel<BrpPersoonskaartInhoud> getPersoonskaartStapel() {
        return persoonskaartStapel;
    }

    /**
     * @return de reisdocument stapels voor deze persoonslijst
     */
    @ElementList(name = "reisdocumentStapels", entry = "reisdocumentStapel", type = BrpStapel.class, required = false)
    public
            List<BrpStapel<BrpReisdocumentInhoud>> getReisdocumentStapels() {
        return reisdocumentStapels;
    }

    /**
     * @return de lijst met BrpRelatie stapels of null
     */
    @ElementList(name = "relaties", entry = "relatie", type = BrpRelatie.class, required = false)
    public List<BrpRelatie> getRelaties() {
        return relaties;
    }

    /**
     * Doorzoekt de lijst met relatie stapels en geeft alleen die stapels terug die van het gewenste soort zijn. Als de
     * gewenste soort niet wordt gevonden wordt een lege lijst geretourneerd.
     * 
     * @param soortRelatieCode
     *            de gewenste soort relatie, mag niet null zijn
     * @return een lijst met relatie stapels van het gewenste soort
     * @throws NullPointerException
     *             als soortRelatieCode null is
     */
    public List<BrpRelatie> getRelaties(final BrpSoortRelatieCode soortRelatieCode) {
        if (soortRelatieCode == null) {
            throw new NullPointerException("soortRelatieCode mag niet null zijn");
        }
        final List<BrpRelatie> result = new ArrayList<BrpRelatie>();
        for (final BrpRelatie relatie : getRelaties()) {
            if (relatie.getSoortRelatieCode().equals(soortRelatieCode)) {
                result.add(relatie);
            }
        }
        return result;
    }

    /**
     * @return de samengestelde naam stapel voor deze persoonslijst, of null
     */
    @Element(name = "samengesteldeNaamStapel", required = false)
    public BrpStapel<BrpSamengesteldeNaamInhoud> getSamengesteldeNaamStapel() {

        return samengesteldeNaamStapel;
    }

    /**
     * @return de statenloos indicatie stapel voor deze persoonslijst
     */
    @Element(name = "statenloosIndicatieStapel", required = false)
    public BrpStapel<BrpStatenloosIndicatieInhoud> getStatenloosIndicatieStapel() {
        return statenloosIndicatieStapel;
    }

    /**
     * @return de Uitsluiting Nederlands kiesrecht stapel voor deze persoonslijst, of null
     */
    @Element(name = "uitsluitingNederlandsKiesrechtStapel", required = false)
    public BrpStapel<BrpUitsluitingNederlandsKiesrechtInhoud> getUitsluitingNederlandsKiesrechtStapel() {
        return uitsluitingNederlandsKiesrechtStapel;
    }

    /**
     * @return de 'Vastgesteld Niet Nederlander'-indicatie stapel voor deze persoonslijst
     */
    @Element(name = "vastgesteldNietNederlanderIndicatieStapel", required = false)
    public BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> getVastgesteldNietNederlanderIndicatieStapel() {
        return vastgesteldNietNederlanderIndicatieStapel;
    }

    /**
     * @return de verblijfsrecht stapel voor deze persoonslijst, of null
     */
    @Element(name = "verblijfsrechtStapel", required = false)
    public BrpStapel<BrpVerblijfsrechtInhoud> getVerblijfsrechtStapel() {
        return verblijfsrechtStapel;
    }

    /**
     * @return de verstrekkingsbeperking stapel voor deze persoonslijst, of null
     */
    @Element(name = "verstrekkingsbeperkingStapel", required = false)
    public BrpStapel<BrpVerstrekkingsbeperkingInhoud> getVerstrekkingsbeperkingStapel() {
        return verstrekkingsbeperkingStapel;
    }

    /**
     * @return de voornaam stapels voor deze persoonslijst
     */
    @ElementList(name = "voornaamStapels", entry = "voornaamStapel", type = BrpStapel.class, required = false)
    public List<BrpStapel<BrpVoornaamInhoud>> getVoornaamStapels() {
        return voornaamStapels;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpPersoonslijst)) {
            return false;
        }
        final BrpPersoonslijst castOther = (BrpPersoonslijst) other;
        return new EqualsBuilder()
                .append(aanschrijvingStapel, castOther.aanschrijvingStapel)
                .append(adresStapel, castOther.adresStapel)
                .append(afgeleidAdministratiefStapel, castOther.afgeleidAdministratiefStapel)
                .append(behandeldAlsNederlanderIndicatieStapel, castOther.behandeldAlsNederlanderIndicatieStapel)
                .append(belemmeringVerstrekkingReisdocumentIndicatieStapel,
                        castOther.belemmeringVerstrekkingReisdocumentIndicatieStapel)
                .append(bezitBuitenlandsReisdocumentIndicatieStapel,
                        castOther.bezitBuitenlandsReisdocumentIndicatieStapel)
                .append(bijhoudingsgemeenteStapel, castOther.bijhoudingsgemeenteStapel)
                .append(bijhoudingsverantwoordelijkheidStapel, castOther.bijhoudingsverantwoordelijkheidStapel)
                .append(derdeHeeftGezagIndicatieStapel, castOther.derdeHeeftGezagIndicatieStapel)
                .append(europeseVerkiezingenStapel, castOther.europeseVerkiezingenStapel)
                .append(geboorteStapel, castOther.geboorteStapel)
                .append(geprivilegieerdeIndicatieStapel, castOther.geprivilegieerdeIndicatieStapel)
                .append(geslachtsaanduidingStapel, castOther.geslachtsaanduidingStapel)
                .append(geslachtsnaamcomponentStapels, castOther.geslachtsnaamcomponentStapels)
                .append(identificatienummerStapel, castOther.identificatienummerStapel)
                .append(immigratieStapel, castOther.immigratieStapel)
                .append(inschrijvingStapel, castOther.inschrijvingStapel)
                .append(nationaliteitStapels, castOther.nationaliteitStapels)
                .append(onderCurateleIndicatieStapel, castOther.onderCurateleIndicatieStapel)
                .append(opschortingStapel, castOther.opschortingStapel)
                .append(overlijdenStapel, castOther.overlijdenStapel)
                .append(persoonskaartStapel, castOther.persoonskaartStapel)
                .append(reisdocumentStapels, castOther.reisdocumentStapels)
                .append(relaties, castOther.relaties)
                .append(samengesteldeNaamStapel, castOther.samengesteldeNaamStapel)
                .append(statenloosIndicatieStapel, castOther.statenloosIndicatieStapel)
                .append(uitsluitingNederlandsKiesrechtStapel, castOther.uitsluitingNederlandsKiesrechtStapel)
                .append(vastgesteldNietNederlanderIndicatieStapel,
                        castOther.vastgesteldNietNederlanderIndicatieStapel)
                .append(verblijfsrechtStapel, castOther.verblijfsrechtStapel)
                .append(verstrekkingsbeperkingStapel, castOther.verstrekkingsbeperkingStapel)
                .append(voornaamStapels, castOther.voornaamStapels).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aanschrijvingStapel).append(adresStapel)
                .append(afgeleidAdministratiefStapel).append(behandeldAlsNederlanderIndicatieStapel)
                .append(belemmeringVerstrekkingReisdocumentIndicatieStapel)
                .append(bezitBuitenlandsReisdocumentIndicatieStapel).append(bijhoudingsgemeenteStapel)
                .append(bijhoudingsverantwoordelijkheidStapel).append(derdeHeeftGezagIndicatieStapel)
                .append(europeseVerkiezingenStapel).append(geboorteStapel).append(geprivilegieerdeIndicatieStapel)
                .append(geslachtsaanduidingStapel).append(geslachtsnaamcomponentStapels)
                .append(identificatienummerStapel).append(immigratieStapel).append(inschrijvingStapel)
                .append(nationaliteitStapels).append(onderCurateleIndicatieStapel).append(opschortingStapel)
                .append(overlijdenStapel).append(persoonskaartStapel).append(reisdocumentStapels).append(relaties)
                .append(samengesteldeNaamStapel).append(statenloosIndicatieStapel)
                .append(uitsluitingNederlandsKiesrechtStapel).append(vastgesteldNietNederlanderIndicatieStapel)
                .append(verblijfsrechtStapel).append(verstrekkingsbeperkingStapel).append(voornaamStapels)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("aanschrijvingStapel", aanschrijvingStapel)
                .append("adresStapel", adresStapel)
                .append("afgeleidAdministratiefStapel", afgeleidAdministratiefStapel)
                .append("behandeldAlsNederlanderIndicatieStapel", behandeldAlsNederlanderIndicatieStapel)
                .append("belemmeringVerstrekkingReisdocumentIndicatieStapel",
                        belemmeringVerstrekkingReisdocumentIndicatieStapel)
                .append("bezitBuitenlandsReisdocumentIndicatieStapel", bezitBuitenlandsReisdocumentIndicatieStapel)
                .append("bijhoudingsgemeenteStapel", bijhoudingsgemeenteStapel)
                .append("bijhoudingsverantwoordelijkheidStapel", bijhoudingsverantwoordelijkheidStapel)
                .append("derdeHeeftGezagIndicatieStapel", derdeHeeftGezagIndicatieStapel)
                .append("europeseVerkiezingenStapel", europeseVerkiezingenStapel)
                .append("geboorteStapel", geboorteStapel)
                .append("geprivilegieerdeIndicatieStapel", geprivilegieerdeIndicatieStapel)
                .append("geslachtsaanduidingStapel", geslachtsaanduidingStapel)
                .append("geslachtsnaamcomponentStapels", geslachtsnaamcomponentStapels)
                .append("identificatienummerStapel", identificatienummerStapel)
                .append("immigratieStapel", immigratieStapel).append("inschrijvingStapel", inschrijvingStapel)
                .append("nationaliteitStapels", nationaliteitStapels)
                .append("onderCurateleIndicatieStapel", onderCurateleIndicatieStapel)
                .append("opschortingStapel", opschortingStapel).append("overlijdenStapel", overlijdenStapel)
                .append("persoonskaartStapel", persoonskaartStapel)
                .append("reisdocumentStapels", reisdocumentStapels).append("relaties", relaties)
                .append("samengesteldeNaamStapel", samengesteldeNaamStapel)
                .append("statenloosIndicatieStapel", statenloosIndicatieStapel)
                .append("uitsluitingNederlandsKiesrechtStapel", uitsluitingNederlandsKiesrechtStapel)
                .append("vastgesteldNietNederlanderIndicatieStapel", vastgesteldNietNederlanderIndicatieStapel)
                .append("verblijfsrechtStapel", verblijfsrechtStapel)
                .append("verstrekkingsbeperkingStapel", verstrekkingsbeperkingStapel)
                .append("voornaamStapels", voornaamStapels).toString();
    }

}
