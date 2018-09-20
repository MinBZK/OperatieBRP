/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen;

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

/**
 * Deze class helpt bij het maken van een TussenPersoonslijst. Argumenten kunnen via method-chaining worden toegevoegd.
 */
public final class TussenPersoonslijstBuilder {

    private final List<TussenStapel<BrpNationaliteitInhoud>> nationaliteitStapels = new ArrayList<>();
    private final List<TussenStapel<BrpReisdocumentInhoud>> reisdocumentStapels = new ArrayList<>();
    private final List<TussenRelatie> relaties = new ArrayList<>();
    private final List<TussenStapel<BrpVerificatieInhoud>> verificatieStapels = new ArrayList<>();
    private TussenStapel<BrpNaamgebruikInhoud> naamgebruikStapel;
    private TussenStapel<BrpAdresInhoud> adresStapel;
    private TussenStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel;
    private TussenStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel;
    private TussenStapel<BrpBijhoudingInhoud> bijhoudingStapel;
    private TussenStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel;
    private TussenStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingenStapel;
    private TussenStapel<BrpGeboorteInhoud> geboorteStapel;
    private TussenStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    private TussenStapel<BrpIdentificatienummersInhoud> identificatienummerStapel;
    private TussenStapel<BrpMigratieInhoud> migratieStapel;
    private TussenStapel<BrpNummerverwijzingInhoud> nummerverwijzingStapel;
    private TussenStapel<BrpInschrijvingInhoud> inschrijvingStapel;
    private TussenStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel;
    private TussenStapel<BrpOverlijdenInhoud> overlijdenStapel;
    private TussenStapel<BrpPersoonskaartInhoud> persoonskaartStapel;
    private TussenStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    private TussenStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel;
    private TussenStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel;
    private TussenStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel;
    private TussenStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> verstrekkingsbeperkingIndicatieStapel;
    private TussenStapel<BrpStaatloosIndicatieInhoud> staatloosIndicatieStapel;
    private TussenStapel<BrpPersoonAfgeleidAdministratiefInhoud> persoonAfgeleidAdministratiefStapel;
    private TussenStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bijzondereVerblijfsrechtelijkePositieIndicatieStapel;
    /*
     * IST gegevens
     */
    private TussenStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel;
    private TussenStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel;
    private List<TussenStapel<BrpIstHuwelijkOfGpGroepInhoud>> istHuwelijkOfGpStapels = new ArrayList<>();
    private List<TussenStapel<BrpIstRelatieGroepInhoud>> istKindStapels = new ArrayList<>();
    private TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingStapel;

    /**
     * Maak een lege builder.
     */
    public TussenPersoonslijstBuilder() {
    }

    /**
     * Maak een builder gevuld met de stapels uit de persoonslijst.
     *
     * @param persoonslijst
     *            initiele vulling
     */
    public TussenPersoonslijstBuilder(final TussenPersoonslijst persoonslijst) {
        naamgebruikStapel = persoonslijst.getNaamgebruikStapel();
        adresStapel = persoonslijst.getAdresStapel();
        behandeldAlsNederlanderIndicatieStapel = persoonslijst.getBehandeldAlsNederlanderIndicatieStapel();
        signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel = persoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel();
        bijhoudingStapel = persoonslijst.getBijhoudingStapel();
        derdeHeeftGezagIndicatieStapel = persoonslijst.getDerdeHeeftGezagIndicatieStapel();
        deelnameEuVerkiezingenStapel = persoonslijst.getDeelnameEuVerkiezingenStapel();
        geboorteStapel = persoonslijst.getGeboorteStapel();
        geslachtsaanduidingStapel = persoonslijst.getGeslachtsaanduidingStapel();
        identificatienummerStapel = persoonslijst.getIdentificatienummerStapel();
        migratieStapel = persoonslijst.getMigratieStapel();
        inschrijvingStapel = persoonslijst.getInschrijvingStapel();
        nationaliteitStapels.addAll(persoonslijst.getNationaliteitStapels());
        nummerverwijzingStapel = persoonslijst.getNummerverwijzingStapel();
        onderCurateleIndicatieStapel = persoonslijst.getOnderCurateleIndicatieStapel();
        overlijdenStapel = persoonslijst.getOverlijdenStapel();
        persoonskaartStapel = persoonslijst.getPersoonskaartStapel();
        reisdocumentStapels.addAll(persoonslijst.getReisdocumentStapels());
        relaties.addAll(persoonslijst.getRelaties());
        samengesteldeNaamStapel = persoonslijst.getSamengesteldeNaamStapel();
        uitsluitingKiesrechtStapel = persoonslijst.getUitsluitingKiesrechtStapel();
        vastgesteldNietNederlanderIndicatieStapel = persoonslijst.getVastgesteldNietNederlanderIndicatieStapel();
        verblijfsrechtStapel = persoonslijst.getVerblijfsrechtStapel();
        verstrekkingsbeperkingIndicatieStapel = persoonslijst.getVerstrekkingsbeperkingIndicatieStapel();

        staatloosIndicatieStapel = persoonslijst.getStaatloosIndicatieStapel();
        persoonAfgeleidAdministratiefStapel = persoonslijst.getPersoonAfgeleidAdministratiefStapel();
        bijzondereVerblijfsrechtelijkePositieIndicatieStapel = persoonslijst.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel();
        verificatieStapels.addAll(persoonslijst.getVerificatieStapels());

        istOuder1Stapel = persoonslijst.getIstOuder1Stapel();
        istOuder2Stapel = persoonslijst.getIstOuder2Stapel();
        istHuwelijkOfGpStapels = persoonslijst.getIstHuwelijkOfGpStapels();
        istKindStapels = persoonslijst.getIstKindStapels();
        istGezagsverhoudingStapel = persoonslijst.getIstGezagsverhoudingStapel();
    }

    /**
     * @return een nieuwe TussenPersoonslijst object o.b.v. de parameters van deze builder
     */
    public TussenPersoonslijst build() {
        return new TussenPersoonslijst(
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
            identificatienummerStapel,
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
            bijzondereVerblijfsrechtelijkePositieIndicatieStapel,
            verificatieStapels,
            istOuder1Stapel,
            istOuder2Stapel,
            istHuwelijkOfGpStapels,
            istKindStapels,
            istGezagsverhoudingStapel);
    }

    /**
     * Voegt de Naamgebruik TussenStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de naamgebruikStapel, mag niet null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder naamgebruikStapel(final TussenStapel<BrpNaamgebruikInhoud> param) {
        naamgebruikStapel = param;
        return this;
    }

    /**
     * Voegt de adres stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            adres stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder adresStapel(final TussenStapel<BrpAdresInhoud> param) {
        adresStapel = param;
        return this;

    }

    /**
     * Voegt de persoon afgeleid administratief stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder persoonAfgeleidAdministratiefStapel(final TussenStapel<BrpPersoonAfgeleidAdministratiefInhoud> param) {
        persoonAfgeleidAdministratiefStapel = param;
        return this;

    }

    /**
     * Voegt de staatloos stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder staatloosIndicatieStapel(final TussenStapel<BrpStaatloosIndicatieInhoud> param) {
        staatloosIndicatieStapel = param;
        return this;
    }

    /**
     * Voegt de MigratieBehandeldAlsNederlanderIndicatieStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de MigratieBehandeldAlsNederlanderIndicatieStapel, mag niet null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder behandeldAlsNederlanderIndicatieStapel(final TussenStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> param) {
        behandeldAlsNederlanderIndicatieStapel = param;
        return this;
    }

    /**
     * Voegt de signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(
        final TussenStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> param)
    {
        signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel = param;

        return this;
    }

    /**
     * Voegt de bijhouding stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            bijhouding stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder bijhoudingStapel(final TussenStapel<BrpBijhoudingInhoud> param) {
        bijhoudingStapel = param;
        return this;
    }

    /**
     * Voegt de derdeHeeftGezagIndicatie stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            derdeHeeftGezagIndicatie stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder derdeHeeftGezagIndicatieStapel(final TussenStapel<BrpDerdeHeeftGezagIndicatieInhoud> param) {
        derdeHeeftGezagIndicatieStapel = param;
        return this;
    }

    /**
     * Voegt de deelnameEuVerkiezingen stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            deelnameEuVerkiezingen stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder deelnameEuVerkiezingen(final TussenStapel<BrpDeelnameEuVerkiezingenInhoud> param) {
        deelnameEuVerkiezingenStapel = param;
        return this;
    }

    /**
     * Voegt de Geboorte TussenStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de geboorteStapel, mag niet null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder geboorteStapel(final TussenStapel<BrpGeboorteInhoud> param) {
        geboorteStapel = param;
        return this;
    }

    /**
     * Voegt de geslachtsaanduidingStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de geslachtsaanduidingStapel, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder geslachtsaanduidingStapel(final TussenStapel<BrpGeslachtsaanduidingInhoud> param) {
        geslachtsaanduidingStapel = param;
        return this;
    }

    /**
     * Voegt de identificatienummer stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de identificatienummer, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder identificatienummerStapel(final TussenStapel<BrpIdentificatienummersInhoud> param) {
        identificatienummerStapel = param;
        return this;
    }

    /**
     * Voegt de immigratie stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            immigratie stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder migratieStapel(final TussenStapel<BrpMigratieInhoud> param) {
        migratieStapel = param;
        return this;
    }

    /**
     * Voegt de Inschrijving TussenStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de inschrijvingStapel, mag niet null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder inschrijvingStapel(final TussenStapel<BrpInschrijvingInhoud> param) {
        inschrijvingStapel = param;
        return this;
    }

    /**
     * Voegt de Nummerverwijzing stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de nummerverwijzingStapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder nummerverwijzingStapel(final TussenStapel<BrpNummerverwijzingInhoud> param) {
        nummerverwijzingStapel = param;
        return this;
    }

    /**
     * Voegt de MigratieNationaliteitStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de MigratieNationaliteitStapel, mag niet null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder nationaliteitStapel(final TussenStapel<BrpNationaliteitInhoud> param) {
        if (param != null) {
            nationaliteitStapels.add(param);
        }
        return this;
    }

    /**
     * Voegt de MigratieNationaliteitStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de MigratieNationaliteitStapel, mag niet null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder nationaliteitStapels(final List<TussenStapel<BrpNationaliteitInhoud>> param) {
        nationaliteitStapels.clear();
        if (param != null) {
            nationaliteitStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de onderCurateleIndicatie stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            onderCurateleIndicatie stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder onderCurateleIndicatieStapel(final TussenStapel<BrpOnderCurateleIndicatieInhoud> param) {
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
    public TussenPersoonslijstBuilder overlijdenStapel(final TussenStapel<BrpOverlijdenInhoud> param) {
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
    public TussenPersoonslijstBuilder persoonskaartStapel(final TussenStapel<BrpPersoonskaartInhoud> param) {
        persoonskaartStapel = param;
        return this;
    }

    /**
     * Voegt de Reisdocument TussenStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de reisdocumentStapel, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder reisdocumentStapel(final TussenStapel<BrpReisdocumentInhoud> param) {
        if (param != null) {
            reisdocumentStapels.add(param);
        }
        return this;
    }

    /**
     * Voegt de Reisdocument MigratieStapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de reisdocumentStapels, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder reisdocumentStapels(final List<TussenStapel<BrpReisdocumentInhoud>> param) {
        reisdocumentStapels.clear();
        if (param != null) {
            reisdocumentStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de MigratieRelatieStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de relatieStapel, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder relatie(final TussenRelatie param) {
        if (param != null) {
            relaties.add(param);
        }
        return this;
    }

    /**
     * Voegt de MigratieRelatieStapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de relatieStapels, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     * @throws NullPointerException
     *             als relatieStapels null is
     */
    public TussenPersoonslijstBuilder relaties(final List<TussenRelatie> param) {
        relaties.clear();
        if (param != null) {
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
    public TussenPersoonslijstBuilder samengesteldeNaamStapel(final TussenStapel<BrpSamengesteldeNaamInhoud> param) {
        samengesteldeNaamStapel = param;
        return this;
    }

    /**
     * Voegt de uitsluitingKiesrecht stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            uitsluitingKiesrecht stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder uitsluitingKiesrecht(final TussenStapel<BrpUitsluitingKiesrechtInhoud> param) {
        uitsluitingKiesrechtStapel = param;
        return this;
    }

    /**
     * Voegt de MigratieVastgesteldNietNederlanderIndicatieStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de MigratieVastgesteldNietNederlanderIndicatieStapel, mag niet null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder vastgesteldNietNederlanderIndicatieStapel(final TussenStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> param) {
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
    public TussenPersoonslijstBuilder verblijfsrechtStapel(final TussenStapel<BrpVerblijfsrechtInhoud> param) {
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
    public TussenPersoonslijstBuilder verstrekkingsbeperkingIndicatieStapel(final TussenStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> param) {
        verstrekkingsbeperkingIndicatieStapel = param;
        return this;
    }

    /**
     * Voegt de bijzondereVerblijfsrechtelijkePositieIndicatie stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de bijzondereVerblijfsrechtelijkePositie stapel die moet worden toegevoegd
     * @return de builder
     */
    public TussenPersoonslijstBuilder bijzondereVerblijfsrechtelijkePositieIndicatieStapel(
        final TussenStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> param)
    {
        bijzondereVerblijfsrechtelijkePositieIndicatieStapel = param;
        return this;
    }

    /**
     * Voegt de Verificatie TussenStapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de verificatieStapel, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder verificatieStapel(final TussenStapel<BrpVerificatieInhoud> param) {
        if (param != null) {
            verificatieStapels.add(param);
        }
        return this;
    }

    /**
     * Voegt de Verificatie MigratieStapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de verificatieStapels, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder verificatieStapels(final List<TussenStapel<BrpVerificatieInhoud>> param) {
        verificatieStapels.clear();
        if (param != null) {
            verificatieStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de IST kindstapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de istKindStapels, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder istKindStapels(final List<TussenStapel<BrpIstRelatieGroepInhoud>> param) {
        istKindStapels.clear();
        if (param != null) {
            istKindStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de IST kind stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de istKindStapel, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder istKindStapel(final TussenStapel<BrpIstRelatieGroepInhoud> param) {
        if (param != null) {
            istKindStapels.add(param);
        }
        return this;
    }

    /**
     * Voegt de IST HuwelijkOfGp stapels toe aan deze persoonslijst builder.
     *
     * @param param
     *            de istHuwelijkOfGpStapels, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder istHuwelijkOfGpStapels(final List<TussenStapel<BrpIstHuwelijkOfGpGroepInhoud>> param) {
        istHuwelijkOfGpStapels.clear();
        if (param != null) {
            istHuwelijkOfGpStapels.addAll(param);
        }
        return this;
    }

    /**
     * Voegt de IST HuwelijkOfGp stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            de istKindHuwelijkOfGpStapel, mag null zijn
     * @return het TussenPersoonslijstBuilder object
     */
    public TussenPersoonslijstBuilder istHuwelijkOfGpStapel(final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> param) {
        if (param != null) {
            istHuwelijkOfGpStapels.add(param);
        }
        return this;
    }

    /**
     * Voegt de IST ouder1 stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            istOuder1 stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder istOuder1(final TussenStapel<BrpIstRelatieGroepInhoud> param) {
        istOuder1Stapel = param;
        return this;
    }

    /**
     * Voegt de IST ouder2 stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            istOuder2 stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder istOuder2(final TussenStapel<BrpIstRelatieGroepInhoud> param) {
        istOuder2Stapel = param;
        return this;
    }

    /**
     * Voegt de IST gezagsverhouding stapel toe aan deze persoonslijst builder.
     *
     * @param param
     *            istGezagsverhouding stapel
     * @return de builder (this)
     */
    public TussenPersoonslijstBuilder istGezagsverhouding(final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> param) {
        istGezagsverhoudingStapel = param;
        return this;
    }

}
