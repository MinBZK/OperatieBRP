/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;

/**
 * Deze class helpt bij het maken van een BrpPersoonslijst. De verplichte argumenten moeten in de contructor worden
 * meegegeven en optionele argumenten kunnen via method-chaining worden toegevoegd.
 *
 */
public final class BrpPersoonslijstBuilder {

    private final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels = new ArrayList<>();
    private final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels = new ArrayList<>();
    private final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels = new ArrayList<>();
    private final List<BrpRelatie> relaties = new ArrayList<>();
    private final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels = new ArrayList<>();
    private final List<BrpStapel<BrpVerificatieInhoud>> verificatieStapels = new ArrayList<>();
    private final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> istHuwelijkOfGpStapels = new ArrayList<>();
    private final List<BrpStapel<BrpIstRelatieGroepInhoud>> istKindStapels = new ArrayList<>();
    private Integer persoonId;
    private BrpStapel<BrpNaamgebruikInhoud> naamgebruikStapel;
    private BrpStapel<BrpAdresInhoud> adresStapel;
    private BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> persoonAfgeleidAdministratiefStapel;
    private BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel;
    private BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel;
    private BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel;
    private BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel;
    private BrpStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingenStapel;
    private BrpStapel<BrpGeboorteInhoud> geboorteStapel;
    private BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    private BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel;
    private BrpStapel<BrpMigratieInhoud> migratieStapel;
    private BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel;
    private BrpStapel<BrpNummerverwijzingInhoud> nummerverwijzingStapel;
    private BrpStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel;
    private BrpStapel<BrpOverlijdenInhoud> overlijdenStapel;
    private BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel;
    private BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    private BrpStapel<BrpStaatloosIndicatieInhoud> staatloosIndicatieStapel;
    private BrpStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel;
    private BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel;
    private BrpStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel;
    private BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> verstrekkingsbeperkingIndicatieStapel;
    private BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bijzondereVerblijfsrechtelijkePositieIndicatieStapel;
    // IST groepen
    private BrpStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel;
    private BrpStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel;
    private BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingStapel;

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
    public BrpPersoonslijstBuilder(final BrpPersoonslijst persoonslijst) {
        naamgebruikStapel = persoonslijst.getNaamgebruikStapel();
        adresStapel = persoonslijst.getAdresStapel();
        persoonAfgeleidAdministratiefStapel = persoonslijst.getPersoonAfgeleidAdministratiefStapel();
        behandeldAlsNederlanderIndicatieStapel = persoonslijst.getBehandeldAlsNederlanderIndicatieStapel();
        signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel = persoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel();
        bijhoudingStapel = persoonslijst.getBijhoudingStapel();
        derdeHeeftGezagIndicatieStapel = persoonslijst.getDerdeHeeftGezagIndicatieStapel();
        deelnameEuVerkiezingenStapel = persoonslijst.getDeelnameEuVerkiezingenStapel();
        geboorteStapel = persoonslijst.getGeboorteStapel();
        geslachtsaanduidingStapel = persoonslijst.getGeslachtsaanduidingStapel();
        geslachtsnaamcomponentStapels.addAll(persoonslijst.getGeslachtsnaamcomponentStapels());
        identificatienummersStapel = persoonslijst.getIdentificatienummerStapel();
        migratieStapel = persoonslijst.getMigratieStapel();
        inschrijvingStapel = persoonslijst.getInschrijvingStapel();
        nationaliteitStapels.addAll(persoonslijst.getNationaliteitStapels());
        nummerverwijzingStapel = persoonslijst.getNummerverwijzingStapel();
        onderCurateleIndicatieStapel = persoonslijst.getOnderCurateleIndicatieStapel();
        overlijdenStapel = persoonslijst.getOverlijdenStapel();
        persoonskaartStapel = persoonslijst.getPersoonskaartStapel();
        reisdocumentStapels.addAll(persoonslijst.getReisdocumentStapels());
        samengesteldeNaamStapel = persoonslijst.getSamengesteldeNaamStapel();
        staatloosIndicatieStapel = persoonslijst.getStaatloosIndicatieStapel();
        uitsluitingKiesrechtStapel = persoonslijst.getUitsluitingKiesrechtStapel();
        vastgesteldNietNederlanderIndicatieStapel = persoonslijst.getVastgesteldNietNederlanderIndicatieStapel();
        verblijfsrechtStapel = persoonslijst.getVerblijfsrechtStapel();
        verstrekkingsbeperkingIndicatieStapel = persoonslijst.getVerstrekkingsbeperkingIndicatieStapel();
        voornaamStapels.addAll(persoonslijst.getVoornaamStapels());
        bijzondereVerblijfsrechtelijkePositieIndicatieStapel = persoonslijst.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel();
        verificatieStapels.addAll(persoonslijst.getVerificatieStapels());

        // IST stapels & relaties
        istOuder1Stapel = persoonslijst.getIstOuder1Stapel();
        istOuder2Stapel = persoonslijst.getIstOuder2Stapel();
        istHuwelijkOfGpStapels.addAll(persoonslijst.getIstHuwelijkOfGpStapels());
        istKindStapels.addAll(persoonslijst.getIstKindStapels());
        istGezagsverhoudingStapel = persoonslijst.getIstGezagsverhoudingsStapel();
        relaties.addAll(persoonslijst.getRelaties());
    }

    /**
     * @return een nieuwe BrpPersoonslijst object o.b.v. de parameters van deze builder
     */
    public BrpPersoonslijst build() {
        return new BrpPersoonslijst(
            persoonId,
            naamgebruikStapel,
            adresStapel,
            persoonAfgeleidAdministratiefStapel,
            behandeldAlsNederlanderIndicatieStapel,
            signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel,
            bijhoudingStapel,
            derdeHeeftGezagIndicatieStapel,
            deelnameEuVerkiezingenStapel,
            geboorteStapel,
            geslachtsaanduidingStapel,
            geslachtsnaamcomponentStapels,
            identificatienummersStapel,
            migratieStapel,
            inschrijvingStapel,
            nationaliteitStapels,
            nummerverwijzingStapel,
            onderCurateleIndicatieStapel,
            overlijdenStapel,
            persoonskaartStapel,
            reisdocumentStapels,
            relaties,
            samengesteldeNaamStapel,
            staatloosIndicatieStapel,
            uitsluitingKiesrechtStapel,
            vastgesteldNietNederlanderIndicatieStapel,
            verblijfsrechtStapel,
            verstrekkingsbeperkingIndicatieStapel,
            voornaamStapels,
            bijzondereVerblijfsrechtelijkePositieIndicatieStapel,
            verificatieStapels,
            istOuder1Stapel,
            istOuder2Stapel,
            istHuwelijkOfGpStapels,
            istKindStapels,
            istGezagsverhoudingStapel);
    }

    /**
     * Zet de persoon.id toe aan deze persoonslijst builder.
     *
     * @param param
     *            de persoon id, mag null zijn
     * @return de BrpPersoonslijstBuilder
     */
    public BrpPersoonslijstBuilder persoonId(final Integer param) {
        persoonId = param;
        return this;
    }

    /**
     * Voegt de naamgebruik stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de naamgebruik stapel, mag null zijn
     * @return de BrpPersoonslijstBuilder
     */
    public BrpPersoonslijstBuilder naamgebruikStapel(final BrpStapel<BrpNaamgebruikInhoud> param) {

        naamgebruikStapel = param;
        return this;
    }

    /**
     * Voegt de adresStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de adresStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder adresStapel(final BrpStapel<BrpAdresInhoud> param) {
        adresStapel = param;
        return this;
    }

    /**
     * Voegt de persoonAfgeleidAdministratiefStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de geslachtsaanduidingStapel, mag null zijn
     * @return het builder object
     */
    public BrpPersoonslijstBuilder persoonAfgeleidAdministratiefStapel(final BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> param) {
        persoonAfgeleidAdministratiefStapel = param;
        return this;
    }

    /**
     * Voegt de BrpBehandeldAlsNederlanderIndicatieStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de BrpBehandeldAlsNederlanderIndicatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder behandeldAlsNederlanderIndicatieStapel(final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> param) {
        behandeldAlsNederlanderIndicatieStapel = param;
        return this;
    }

    /**
     * Voegt de BrpSignaleringMetBetrekkingTotVerstrekkenReisdocument stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapelStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(
        final BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> param)
    {
        signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel = param;
        return this;
    }

    /**
     * Voegt de bijhoudingStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de bijhoudingStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder bijhoudingStapel(final BrpStapel<BrpBijhoudingInhoud> param) {
        bijhoudingStapel = param;
        return this;
    }

    /**
     * Voegt de derdeHeeftGezagIndicatieStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de derdeHeeftGezagIndicatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder derdeHeeftGezagIndicatieStapel(final BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> param) {
        derdeHeeftGezagIndicatieStapel = param;
        return this;
    }

    /**
     * Voegt de deelnameEuVerkiezingenStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de deelnameEuVerkiezingenStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder deelnameEuVerkiezingenStapel(final BrpStapel<BrpDeelnameEuVerkiezingenInhoud> param) {
        deelnameEuVerkiezingenStapel = param;
        return this;
    }

    /**
     * Voegt de Geboorte BrpStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de geboorteStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder geboorteStapel(final BrpStapel<BrpGeboorteInhoud> param) {

        geboorteStapel = param;
        return this;
    }

    /**
     * Voegt de geslachtsaanduidingStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de geslachtsaanduidingStapel, mag null zijn
     * @return het builder object
     */
    public BrpPersoonslijstBuilder geslachtsaanduidingStapel(final BrpStapel<BrpGeslachtsaanduidingInhoud> param) {
        geslachtsaanduidingStapel = param;
        return this;
    }

    /**
     * Voegt de Geslachtsnaamcomponent BrpStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de geslachtsnaamcomponentStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder geslachtsnaamcomponentStapel(final BrpStapel<BrpGeslachtsnaamcomponentInhoud> param) {
        if (param != null) {
            geslachtsnaamcomponentStapels.add(param);
        }
        return this;
    }

    /**
     * Voegt de lijst met Geslachtsnaamcomponent BrpStapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de lijst met geslachtsnaamcomponentStapels, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder geslachtsnaamcomponentStapels(final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> param) {
        if (param != null) {
            geslachtsnaamcomponentStapels.clear();
            geslachtsnaamcomponentStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de identificatienummer stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de identificatienummer, mag null zijn
     * @return het builder object
     */
    public BrpPersoonslijstBuilder identificatienummersStapel(final BrpStapel<BrpIdentificatienummersInhoud> param) {
        identificatienummersStapel = param;
        return this;
    }

    /**
     * Voegt de migratieStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de migratieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder migratieStapel(final BrpStapel<BrpMigratieInhoud> param) {
        migratieStapel = param;
        return this;
    }

    /**
     * Voegt de inschrijvingStapel toe aan deze BrpPersoonslijstBuilder.
     *
     * @param param
     *            de inschrijvingStapel, mag null zijn
     * @return de persoonslijstbuilder met daaraan de inschrijvingStapel toegevoegd
     */
    public BrpPersoonslijstBuilder inschrijvingStapel(final BrpStapel<BrpInschrijvingInhoud> param) {
        inschrijvingStapel = param;
        return this;
    }

    /**
     * Voegt de BrpNationaliteitStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de BrpNationaliteitStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder nationaliteitStapel(final BrpStapel<BrpNationaliteitInhoud> param) {
        if (param != null) {
            nationaliteitStapels.add(param);
        }
        return this;
    }

    /**
     * Voegt de lijst met BrpNationaliteitStapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de lijst met BrpNationaliteitStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder nationaliteitStapels(final List<BrpStapel<BrpNationaliteitInhoud>> param) {
        if (param != null) {
            nationaliteitStapels.clear();
            nationaliteitStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de nummerverwijzingStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de nummerverwijzingStapel, mag null zijn
     * @return het brpPersoonslijstbuilder object
     */
    public BrpPersoonslijstBuilder nummerverwijzingStapel(final BrpStapel<BrpNummerverwijzingInhoud> param) {
        nummerverwijzingStapel = param;
        return this;
    }

    /**
     * Voegt de onderCurateleIndicatieStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de onderCurateleIndicatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder onderCurateleIndicatieStapel(final BrpStapel<BrpOnderCurateleIndicatieInhoud> param) {
        onderCurateleIndicatieStapel = param;
        return this;
    }

    /**
     * Voegt de overlijden stapel aan deze persoonslijst builder.
     *
     * @param param
     *            de overlijden stapel
     * @return de BrpPersoonslijstBuilder
     */
    public BrpPersoonslijstBuilder overlijdenStapel(final BrpStapel<BrpOverlijdenInhoud> param) {
        overlijdenStapel = param;
        return this;
    }

    /**
     * Voegt de persoonskaart stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            the persoonskaartStapel to set
     * @return de builder (this)
     */
    public BrpPersoonslijstBuilder persoonskaartStapel(final BrpStapel<BrpPersoonskaartInhoud> param) {
        persoonskaartStapel = param;
        return this;
    }

    /**
     * Voegt de Reisdocument BrpStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de reisdocumentStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder reisdocumentStapel(final BrpStapel<BrpReisdocumentInhoud> param) {
        if (param != null) {
            reisdocumentStapels.add(param);
        }
        return this;
    }

    /**
     * Voegt de Reisdocument Stapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de reisdocumentStapels, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder reisdocumentStapels(final List<BrpStapel<BrpReisdocumentInhoud>> param) {
        if (param != null) {
            reisdocumentStapels.clear();
            reisdocumentStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de BrpRelatieStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de relatie, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder relatie(final BrpRelatie param) {
        if (param != null) {
            relaties.add(param);
        }
        return this;
    }

    /**
     * Voegt de BrpRelatieStapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de relaties, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder relaties(final List<BrpRelatie> param) {
        if (param != null) {
            relaties.clear();
            relaties.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de samengestelde naam stapel toe aan deze persoonlijst builder.
     *
     * @param param
     *            samengestelde naam stapel
     * @return builder (this)
     */
    public BrpPersoonslijstBuilder samengesteldeNaamStapel(final BrpStapel<BrpSamengesteldeNaamInhoud> param) {
        samengesteldeNaamStapel = param;
        return this;
    }

    /**
     * Voegt de staatloosIndicatieStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de staatloosIndicatieStapel, mag null zijn
     * @return het builder object
     */
    public BrpPersoonslijstBuilder staatloosIndicatieStapel(final BrpStapel<BrpStaatloosIndicatieInhoud> param) {
        staatloosIndicatieStapel = param;
        return this;
    }

    /**
     * Voegt de uitsluitingKiesrechtStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de uitsluitingKiesrechtStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder uitsluitingKiesrechtStapel(final BrpStapel<BrpUitsluitingKiesrechtInhoud> param) {
        uitsluitingKiesrechtStapel = param;
        return this;
    }

    /**
     * Voegt de BrpVastgesteldNietNederlanderIndicatieStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de BrpVastgesteldNietNederlanderIndicatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder vastgesteldNietNederlanderIndicatieStapel(final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> param) {

        vastgesteldNietNederlanderIndicatieStapel = param;

        return this;
    }

    /**
     * Voegt de verblijfsrecht stapel aan deze persoonslijst builder.
     *
     * @param param
     *            de verblijfsrecht stapel
     * @return de BrpPersoonslijstBuilder
     */
    public BrpPersoonslijstBuilder verblijfsrechtStapel(final BrpStapel<BrpVerblijfsrechtInhoud> param) {
        verblijfsrechtStapel = param;
        return this;
    }

    /**
     * Voegt de verstrekkingsbeperkingIndicatie stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            the verstrekkingsbeperkingIndicatieStapel to set
     * @return de builder (this)
     */
    public BrpPersoonslijstBuilder verstrekkingsbeperkingIndicatieStapel(final BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> param) {
        verstrekkingsbeperkingIndicatieStapel = param;
        return this;
    }

    /**
     * Voegt de Voornaam BrpStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de brpVoornaamStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder voornaamStapel(final BrpStapel<BrpVoornaamInhoud> param) {
        if (param != null) {
            voornaamStapels.add(param);
        }
        return this;
    }

    /**
     * Voegt de lijst met Voornaam BrpStapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de lijst met brpVoornaamStapels, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     *
     */
    public BrpPersoonslijstBuilder voornaamStapels(final List<BrpStapel<BrpVoornaamInhoud>> param) {
        if (param != null) {
            voornaamStapels.clear();
            voornaamStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de Verificatie BrpStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de brpVerificatieStapel, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpPersoonslijstBuilder verificatieStapel(final BrpStapel<BrpVerificatieInhoud> param) {
        if (param != null) {
            verificatieStapels.add(param);
        }
        return this;
    }

    /**
     * Voegt de lijst met Verificatie BrpStapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de lijst met brpVerificatieStapels, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     *
     */
    public BrpPersoonslijstBuilder verificatieStapels(final List<BrpStapel<BrpVerificatieInhoud>> param) {
        if (param != null) {
            verificatieStapels.clear();
            verificatieStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de bijzondereVerblijfsrechtelijkePositieIndicatie stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de bijzondereVerblijfsrechtelijkePositieIndicatie stapel die moet worden toegevoegd
     * @return de builder
     */
    public BrpPersoonslijstBuilder bijzondereVerblijfsrechtelijkePositieIndicatieStapel(
        final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> param)
    {
        bijzondereVerblijfsrechtelijkePositieIndicatieStapel = param;
        return this;
    }

    /**
     * Voegt de IST ouder1 stapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de IST ouder1 stapel die moet worden toegevoegd
     * @return de builder
     */
    public BrpPersoonslijstBuilder istOuder1Stapel(final BrpStapel<BrpIstRelatieGroepInhoud> param) {
        istOuder1Stapel = param;
        return this;
    }

    /**
     * Voegt de IST ouder2 stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de IST ouder2 stapel die moet worden toegevoegd
     * @return de builder
     */
    public BrpPersoonslijstBuilder istOuder2Stapel(final BrpStapel<BrpIstRelatieGroepInhoud> param) {
        istOuder2Stapel = param;
        return this;
    }

    /**
     * Voegt de IST huwelijkOfGp stapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de IST huwelijkOfGp stapels die moet worden toegevoegd
     * @return de builder
     */
    public BrpPersoonslijstBuilder istHuwelijkOfGpStapels(final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> param) {
        if (param != null) {
            istHuwelijkOfGpStapels.clear();
            istHuwelijkOfGpStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de IST kind stapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de IST kind stapels die moet worden toegevoegd
     * @return de builder
     */
    public BrpPersoonslijstBuilder istKindStapels(final List<BrpStapel<BrpIstRelatieGroepInhoud>> param) {
        if (param != null) {
            istKindStapels.clear();
            istKindStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de IST gezagsverhouding stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de IST gezagsverhouding stapel die moet worden toegevoegd
     * @return de builder
     */
    public BrpPersoonslijstBuilder istGezagsverhoudingStapel(final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> param) {
        istGezagsverhoudingStapel = param;
        return this;
    }
}
