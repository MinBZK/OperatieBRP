/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
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
 * Deze class representeert een migratie specifieke persoonslijst waarbij de inhoud in BRP structuur is weergegeven en
 * de historie in LO3 structuur.
 */
public final class TussenPersoonslijst implements Persoonslijst {

    private final TussenStapel<BrpNaamgebruikInhoud> naamgebruikStapel;
    private final TussenStapel<BrpAdresInhoud> adresStapel;
    private final TussenStapel<BrpPersoonAfgeleidAdministratiefInhoud> persoonAfgeleidAdministratiefStapel;
    private final TussenStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel;
    private final TussenStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel;
    private final TussenStapel<BrpBijhoudingInhoud> bijhoudingStapel;
    private final TussenStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel;
    private final TussenStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingenStapel;
    private final TussenStapel<BrpGeboorteInhoud> geboorteStapel;
    private final TussenStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    private final TussenStapel<BrpIdentificatienummersInhoud> identificatienummerStapel;
    private final TussenStapel<BrpMigratieInhoud> migratieStapel;
    private final TussenStapel<BrpInschrijvingInhoud> inschrijvingStapel;
    private final TussenStapel<BrpNummerverwijzingInhoud> nummerverwijzingStapel;
    private final List<TussenStapel<BrpNationaliteitInhoud>> nationaliteitStapels;
    private final TussenStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel;
    private final TussenStapel<BrpOverlijdenInhoud> overlijdenStapel;
    private final TussenStapel<BrpPersoonskaartInhoud> persoonskaartStapel;
    private final List<TussenStapel<BrpReisdocumentInhoud>> reisdocumentStapels;
    private final List<TussenRelatie> relaties;
    private final TussenStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    private final TussenStapel<BrpStaatloosIndicatieInhoud> staatloosIndicatieStapel;
    private final TussenStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel;
    private final TussenStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel;
    private final TussenStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bijzondereVerblijfsrechtelijkePositieIndicatieStapel;
    private final TussenStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel;
    private final List<TussenStapel<BrpVerificatieInhoud>> verificatieStapels;
    private final TussenStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> verstrekkingsbeperkingIndicatieStapel;

    /*
     * IST gegevens
     */
    private final TussenStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel;
    private final TussenStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel;
    private final List<TussenStapel<BrpIstHuwelijkOfGpGroepInhoud>> istHuwelijkOfGpStapels;
    private final List<TussenStapel<BrpIstRelatieGroepInhoud>> istKindStapels;
    private final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingStapel;

    /**
     * Maakt een TussenPersoonslijst object.
     * <p/>
     * Note: default access modifier, de persoonslijst kan alleen worden aangemaakt via de builder.
     *
     * @param naamgebruikStapel
     *            De naamgebruik stapel.
     * @param adresStapel
     *            De adres stapel.
     * @param persoonAfgeleidAdministratiefStapel
     *            De persoon afgeleid administratief stapel.
     * @param behandeldAlsNederlanderIndicatieStapel
     *            De behandeld als Nederlander stapel.
     * @param signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel
     *            De signalering met betrekking tot verstrekken reisdocument stapel.
     * @param bijhoudingStapel
     *            De bijhouding stapel.
     * @param derdeHeeftGezagIndicatieStapel
     *            De derede heeft gezag stapel.
     * @param deelnameEuVerkiezingenStapel
     *            De deelname EU verkiezingen stapel.
     * @param geboorteStapel
     *            De geboorte stapel.
     * @param geslachtsaanduidingStapel
     *            De geslachtsaanduiding stapel.
     * @param identificatienummerStapel
     *            De identificatienummer stapel.
     * @param migratieStapel
     *            De migratie stapel.
     * @param inschrijvingStapel
     *            De inschrijving stapel.
     * @param nationaliteitStapels
     *            De nationaliteit stapel.
     * @param nummerverwijzingStapel
     *            De nummerverwijzing stapel.
     * @param onderCurateleIndicatieStapel
     *            De onder curatele stapel.
     * @param overlijdenStapel
     *            De overlijden stapel.
     * @param persoonskaartStapel
     *            De persoonskaart stapel.
     * @param reisdocumentStapels
     *            De reisdocument stapel.
     * @param relaties
     *            De relaties stapel.
     * @param samengesteldeNaamStapel
     *            De samengestelde naam stapel.
     * @param staatloosIndicatieStapel
     *            De staatloos stapel.
     * @param uitsluitingKiesrechtStapel
     *            De uitsluiting kiesrecht stapel.
     * @param vastgesteldNietNederlanderIndicatieStapel
     *            De vastgesteld niet-Nederlander stapel.
     * @param verblijfsrechtStapel
     *            De verblijfsrecht stapel.
     * @param verstrekkingsbeperkingIndicatieStapel
     *            De verstrekkingsbeperking stapel.
     * @param bijzondereVerblijfsrechtelijkePositieIndicatieStapel
     *            De bijzondere verblijfsrechtelijke positie stapel.
     * @param verificatieStapels
     *            De verificatie stapel.
     * @param istOuder1Stapel
     *            De IST ouder 1 stapel.
     * @param istOuder2Stapel
     *            De IST ouder 2 stapel.
     * @param istHuwelijkOfGpStapels
     *            De huwelijk of geregistreerdpartnerschap stapel.
     * @param istKindStapels
     *            De kind stapel.
     * @param istGezagsverhoudingStapel
     *            De gezagsverhouding stapel.
     */
    @SuppressWarnings("checkstyle:parameternumber")
    TussenPersoonslijst(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        final TussenStapel<BrpNaamgebruikInhoud> naamgebruikStapel,
        final TussenStapel<BrpAdresInhoud> adresStapel,
        final TussenStapel<BrpPersoonAfgeleidAdministratiefInhoud> persoonAfgeleidAdministratiefStapel,
        final TussenStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel,
        final TussenStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel,
        final TussenStapel<BrpBijhoudingInhoud> bijhoudingStapel,
        final TussenStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel,
        final TussenStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingenStapel,
        final TussenStapel<BrpGeboorteInhoud> geboorteStapel,
        final TussenStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel,
        final TussenStapel<BrpIdentificatienummersInhoud> identificatienummerStapel,
        final TussenStapel<BrpMigratieInhoud> migratieStapel,
        final TussenStapel<BrpInschrijvingInhoud> inschrijvingStapel,
        final List<TussenStapel<BrpNationaliteitInhoud>> nationaliteitStapels,
        final TussenStapel<BrpNummerverwijzingInhoud> nummerverwijzingStapel,
        final TussenStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel,
        final TussenStapel<BrpOverlijdenInhoud> overlijdenStapel,
        final TussenStapel<BrpPersoonskaartInhoud> persoonskaartStapel,
        final List<TussenStapel<BrpReisdocumentInhoud>> reisdocumentStapels,
        final List<TussenRelatie> relaties,
        final TussenStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel,
        final TussenStapel<BrpStaatloosIndicatieInhoud> staatloosIndicatieStapel,
        final TussenStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel,
        final TussenStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel,
        final TussenStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel,
        final TussenStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> verstrekkingsbeperkingIndicatieStapel,
        final TussenStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bijzondereVerblijfsrechtelijkePositieIndicatieStapel,
        final List<TussenStapel<BrpVerificatieInhoud>> verificatieStapels,
        final TussenStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel,
        final TussenStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel,
        final List<TussenStapel<BrpIstHuwelijkOfGpGroepInhoud>> istHuwelijkOfGpStapels,
        final List<TussenStapel<BrpIstRelatieGroepInhoud>> istKindStapels,
        final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingStapel)
    {
        this.naamgebruikStapel = naamgebruikStapel;
        this.adresStapel = adresStapel;
        this.persoonAfgeleidAdministratiefStapel = persoonAfgeleidAdministratiefStapel;
        this.behandeldAlsNederlanderIndicatieStapel = behandeldAlsNederlanderIndicatieStapel;
        this.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel = signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel;
        this.bijhoudingStapel = bijhoudingStapel;
        this.derdeHeeftGezagIndicatieStapel = derdeHeeftGezagIndicatieStapel;
        this.deelnameEuVerkiezingenStapel = deelnameEuVerkiezingenStapel;
        this.geboorteStapel = geboorteStapel;
        this.geslachtsaanduidingStapel = geslachtsaanduidingStapel;
        this.identificatienummerStapel = identificatienummerStapel;
        this.migratieStapel = migratieStapel;
        this.inschrijvingStapel = inschrijvingStapel;
        this.nationaliteitStapels = nationaliteitStapels;
        this.nummerverwijzingStapel = nummerverwijzingStapel;
        this.onderCurateleIndicatieStapel = onderCurateleIndicatieStapel;
        this.overlijdenStapel = overlijdenStapel;
        this.persoonskaartStapel = persoonskaartStapel;
        this.reisdocumentStapels = reisdocumentStapels;
        this.relaties = relaties;
        this.samengesteldeNaamStapel = samengesteldeNaamStapel;
        this.staatloosIndicatieStapel = staatloosIndicatieStapel;
        this.uitsluitingKiesrechtStapel = uitsluitingKiesrechtStapel;
        this.vastgesteldNietNederlanderIndicatieStapel = vastgesteldNietNederlanderIndicatieStapel;
        this.verblijfsrechtStapel = verblijfsrechtStapel;
        this.verstrekkingsbeperkingIndicatieStapel = verstrekkingsbeperkingIndicatieStapel;
        this.bijzondereVerblijfsrechtelijkePositieIndicatieStapel = bijzondereVerblijfsrechtelijkePositieIndicatieStapel;
        this.verificatieStapels = verificatieStapels;
        this.istOuder1Stapel = istOuder1Stapel;
        this.istOuder2Stapel = istOuder2Stapel;
        this.istHuwelijkOfGpStapels = istHuwelijkOfGpStapels;
        this.istKindStapels = istKindStapels;
        this.istGezagsverhoudingStapel = istGezagsverhoudingStapel;
    }

    private static <T> List<T> copyOf(final List<T> list) {
        return new ArrayList<>(list);
    }

    /**
     * Geef de waarde van naamgebruik stapel.
     *
     * @return de naamgebruik stapel voor deze persoonslijst
     */
    public TussenStapel<BrpNaamgebruikInhoud> getNaamgebruikStapel() {
        return naamgebruikStapel;
    }

    /**
     * Geef de waarde van actueel administratienummer.
     *
     * @return het actuele a-nummer van deze persoonslijst
     */
    @Override
    public Long getActueelAdministratienummer() {
        return BrpLong.unwrap(getIdentificatienummerStapel().getLaatsteElement().getInhoud().getAdministratienummer());
    }

    /**
     * Geef de waarde van adres stapel.
     *
     * @return de adres stapel voor deze persoonslijst, of null
     */
    public TussenStapel<BrpAdresInhoud> getAdresStapel() {
        return adresStapel;
    }

    /**
     * Geef de waarde van persoon afgeleid administratief stapel.
     *
     * @return de Behandeld als Nederlander stapel voor deze persoonslijst
     */

    public TussenStapel<BrpPersoonAfgeleidAdministratiefInhoud> getPersoonAfgeleidAdministratiefStapel() {
        return persoonAfgeleidAdministratiefStapel;
    }

    /**
     * Geef de waarde van behandeld als nederlander indicatie stapel.
     *
     * @return de Behandeld als Nederlander stapel voor deze persoonslijst
     */

    public TussenStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> getBehandeldAlsNederlanderIndicatieStapel() {
        return behandeldAlsNederlanderIndicatieStapel;
    }

    /**
     * Geef de waarde van signalering met betrekking tot verstrekken reisdocument stapel.
     *
     * @return de signaleringMetBetrekkingTotVerstrekkenReisdocument stapel voor deze persoonslijst
     */

    public TussenStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel() {
        return signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel;
    }

    /**
     * Geef de waarde van bijhouding stapel.
     *
     * @return de bijhouding stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpBijhoudingInhoud> getBijhoudingStapel() {

        return bijhoudingStapel;
    }

    /**
     * Geef de waarde van derde heeft gezag indicatie stapel.
     *
     * @return de 'derde heeft gezag'-indicatie stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpDerdeHeeftGezagIndicatieInhoud> getDerdeHeeftGezagIndicatieStapel() {

        return derdeHeeftGezagIndicatieStapel;
    }

    /**
     * Geef de waarde van deelname eu verkiezingen stapel.
     *
     * @return de europese verkiezingen stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpDeelnameEuVerkiezingenInhoud> getDeelnameEuVerkiezingenStapel() {

        return deelnameEuVerkiezingenStapel;
    }

    /**
     * Geef de waarde van geboorte stapel.
     *
     * @return de geboorte stapel voor deze persoonslijst
     */

    public TussenStapel<BrpGeboorteInhoud> getGeboorteStapel() {
        return geboorteStapel;
    }

    /**
     * Geef de waarde van geslachtsaanduiding stapel.
     *
     * @return de geslachtsaanduiding stapel voor deze persoonslijst
     */

    public TussenStapel<BrpGeslachtsaanduidingInhoud> getGeslachtsaanduidingStapel() {
        return geslachtsaanduidingStapel;
    }

    /**
     * Geef de waarde van identificatienummer stapel.
     *
     * @return de identificatienummer stapel voor deze persoonslijst
     */

    public TussenStapel<BrpIdentificatienummersInhoud> getIdentificatienummerStapel() {
        return identificatienummerStapel;
    }

    /**
     * Geef de waarde van migratie stapel.
     *
     * @return de migratie stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpMigratieInhoud> getMigratieStapel() {
        return migratieStapel;
    }

    /**
     * Geef de waarde van inschrijving stapel.
     *
     * @return de inschrijving stapel voor deze persoonslijst
     */

    public TussenStapel<BrpInschrijvingInhoud> getInschrijvingStapel() {
        return inschrijvingStapel;
    }

    /**
     * Geef de waarde van nationaliteit stapels.
     *
     * @return de lijst met nationaliteit stapels voor deze persoonslijst
     */
    public List<TussenStapel<BrpNationaliteitInhoud>> getNationaliteitStapels() {
        return TussenPersoonslijst.copyOf(nationaliteitStapels);
    }

    /**
     * Geef de waarde van nummerverwijzing stapel.
     *
     * @return de nummerverwijzing stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpNummerverwijzingInhoud> getNummerverwijzingStapel() {
        return nummerverwijzingStapel;
    }

    /**
     * Geef de waarde van onder curatele indicatie stapel.
     *
     * @return de 'onder curatele'-indicatie stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpOnderCurateleIndicatieInhoud> getOnderCurateleIndicatieStapel() {

        return onderCurateleIndicatieStapel;
    }

    /**
     * Geef de waarde van overlijden stapel.
     *
     * @return de overlijden stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpOverlijdenInhoud> getOverlijdenStapel() {

        return overlijdenStapel;
    }

    /**
     * Geef de waarde van persoonskaart stapel.
     *
     * @return de persoonskaart stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpPersoonskaartInhoud> getPersoonskaartStapel() {

        return persoonskaartStapel;
    }

    /**
     * Geef de waarde van reisdocument stapels.
     *
     * @return de reisdocument stapels voor deze persoonslijst
     */
    public List<TussenStapel<BrpReisdocumentInhoud>> getReisdocumentStapels() {
        return TussenPersoonslijst.copyOf(reisdocumentStapels);
    }

    /**
     * Geef de waarde van relaties.
     *
     * @return de lijst met BrpRelatie stapels of null
     */
    public List<TussenRelatie> getRelaties() {
        return TussenPersoonslijst.copyOf(relaties);
    }

    /**
     * Geef de waarde van samengestelde naam stapel.
     *
     * @return de samengestelde naam stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpSamengesteldeNaamInhoud> getSamengesteldeNaamStapel() {

        return samengesteldeNaamStapel;
    }

    /**
     * Geef de waarde van staatloos indicatie stapel.
     *
     * @return de Staatloos indicatie stapel voor deze persoonslijst
     */

    public TussenStapel<BrpStaatloosIndicatieInhoud> getStaatloosIndicatieStapel() {
        return staatloosIndicatieStapel;
    }

    /**
     * Geef de waarde van uitsluiting kiesrecht stapel.
     *
     * @return de uitsluiting Nederlands kiesrecht stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpUitsluitingKiesrechtInhoud> getUitsluitingKiesrechtStapel() {
        return uitsluitingKiesrechtStapel;
    }

    /**
     * Geef de waarde van vastgesteld niet nederlander indicatie stapel.
     *
     * @return de Vastgesteld Niet Nederlander indicatie stapel voor deze persoonslijst
     */

    public TussenStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> getVastgesteldNietNederlanderIndicatieStapel() {
        return vastgesteldNietNederlanderIndicatieStapel;
    }

    /**
     * Geef de waarde van verblijfsrecht stapel.
     *
     * @return de verblijfsrecht stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpVerblijfsrechtInhoud> getVerblijfsrechtStapel() {
        return verblijfsrechtStapel;
    }

    /**
     * Geef de waarde van verstrekkingsbeperking indicatie stapel.
     *
     * @return de verstrekkingsbeperkingIndicatie stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> getVerstrekkingsbeperkingIndicatieStapel() {
        return verstrekkingsbeperkingIndicatieStapel;
    }

    /**
     * Geef de waarde van bijzondere verblijfsrechtelijke positie indicatie stapel.
     *
     * @return de bijzondereVerblijfsrechtelijkePositieIndicatie stapel voor deze persoonslijst, of null
     */

    public TussenStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> getBijzondereVerblijfsrechtelijkePositieIndicatieStapel() {
        return bijzondereVerblijfsrechtelijkePositieIndicatieStapel;
    }

    /**
     * Geef de waarde van verificatie stapels.
     *
     * @return de verificatie stapels voor deze persoonslijst
     */
    public List<TussenStapel<BrpVerificatieInhoud>> getVerificatieStapels() {
        return TussenPersoonslijst.copyOf(verificatieStapels);
    }

    /**
     * Geef de waarde van ist ouder1 stapel.
     *
     * @return de ist stapel voor ouder1
     */

    public TussenStapel<BrpIstRelatieGroepInhoud> getIstOuder1Stapel() {
        return istOuder1Stapel;
    }

    /**
     * Geef de waarde van ist ouder2 stapel.
     *
     * @return de ist stapel voor ouder2
     */

    public TussenStapel<BrpIstRelatieGroepInhoud> getIstOuder2Stapel() {
        return istOuder2Stapel;
    }

    /**
     * Geef de waarde van ist huwelijk of gp stapels.
     *
     * @return de ist stapels voor huwelijk of geregistreerd partnerschap
     */
    public List<TussenStapel<BrpIstHuwelijkOfGpGroepInhoud>> getIstHuwelijkOfGpStapels() {
        return istHuwelijkOfGpStapels;
    }

    /**
     * Geef de waarde van ist kind stapels.
     *
     * @return de ist stapels voor kind
     */
    public List<TussenStapel<BrpIstRelatieGroepInhoud>> getIstKindStapels() {
        return istKindStapels;
    }

    /**
     * Geef de waarde van ist gezagsverhouding stapel.
     *
     * @return de ist stapel voor gezagsverhouding
     */

    public TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> getIstGezagsverhoudingStapel() {
        return istGezagsverhoudingStapel;
    }
}
