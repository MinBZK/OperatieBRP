/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnverwerktDocumentAanwezigIndicatieInhoud;
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
    private final TussenStapel<BrpOnverwerktDocumentAanwezigIndicatieInhoud> onverwerktDocumentAanwezigIndicatieStapel;
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
    private final List<TussenStapel<BrpBuitenlandsPersoonsnummerInhoud>> buitenlandsPersoonsnummerStapels;
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
     * @param naamgebruikStapel De naamgebruik stapel.
     * @param adresStapel De adres stapel.
     * @param persoonAfgeleidAdministratiefStapel De persoon afgeleid administratief stapel.
     * @param behandeldAlsNederlanderIndicatieStapel De behandeld als Nederlander stapel.
     * @param onverwerktDocumentAanwezigIndicatieStapel De onverwerkt document aanwezig stapel.
     * @param signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel De signalering met betrekking tot verstrekken reisdocument stapel.
     * @param bijhoudingStapel De bijhouding stapel.
     * @param derdeHeeftGezagIndicatieStapel De derede heeft gezag stapel.
     * @param deelnameEuVerkiezingenStapel De deelname EU verkiezingen stapel.
     * @param geboorteStapel De geboorte stapel.
     * @param geslachtsaanduidingStapel De geslachtsaanduiding stapel.
     * @param identificatienummerStapel De identificatienummer stapel.
     * @param migratieStapel De migratie stapel.
     * @param inschrijvingStapel De inschrijving stapel.
     * @param nationaliteitStapels De nationaliteit stapels.
     * @param buitenlandsPersoonsnummerStapels De buitenlands persoonsnummer stapels.
     * @param nummerverwijzingStapel De nummerverwijzing stapel.
     * @param onderCurateleIndicatieStapel De onder curatele stapel.
     * @param overlijdenStapel De overlijden stapel.
     * @param persoonskaartStapel De persoonskaart stapel.
     * @param reisdocumentStapels De reisdocument stapel.
     * @param relaties De relaties stapel.
     * @param samengesteldeNaamStapel De samengestelde naam stapel.
     * @param staatloosIndicatieStapel De staatloos stapel.
     * @param uitsluitingKiesrechtStapel De uitsluiting kiesrecht stapel.
     * @param vastgesteldNietNederlanderIndicatieStapel De vastgesteld niet-Nederlander stapel.
     * @param verblijfsrechtStapel De verblijfsrecht stapel.
     * @param verstrekkingsbeperkingIndicatieStapel De verstrekkingsbeperking stapel.
     * @param bijzondereVerblijfsrechtelijkePositieIndicatieStapel De bijzondere verblijfsrechtelijke positie stapel.
     * @param verificatieStapels De verificatie stapel.
     * @param istOuder1Stapel De IST ouder 1 stapel.
     * @param istOuder2Stapel De IST ouder 2 stapel.
     * @param istHuwelijkOfGpStapels De huwelijk of geregistreerdpartnerschap stapel.
     * @param istKindStapels De kind stapel.
     * @param istGezagsverhoudingStapel De gezagsverhouding stapel.
     */

    TussenPersoonslijst(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        final TussenStapel<BrpNaamgebruikInhoud> naamgebruikStapel,
        final TussenStapel<BrpAdresInhoud> adresStapel,
        final TussenStapel<BrpPersoonAfgeleidAdministratiefInhoud> persoonAfgeleidAdministratiefStapel,
        final TussenStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel,
        final TussenStapel<BrpOnverwerktDocumentAanwezigIndicatieInhoud> onverwerktDocumentAanwezigIndicatieStapel,
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
        final List<TussenStapel<BrpBuitenlandsPersoonsnummerInhoud>> buitenlandsPersoonsnummerStapels,
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
        final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingStapel) {
        this.naamgebruikStapel = naamgebruikStapel;
        this.adresStapel = adresStapel;
        this.persoonAfgeleidAdministratiefStapel = persoonAfgeleidAdministratiefStapel;
        this.behandeldAlsNederlanderIndicatieStapel = behandeldAlsNederlanderIndicatieStapel;
        this.onverwerktDocumentAanwezigIndicatieStapel = onverwerktDocumentAanwezigIndicatieStapel;
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
        this.buitenlandsPersoonsnummerStapels = buitenlandsPersoonsnummerStapels;
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
     * Geef de waarde van naamgebruik stapel van TussenPersoonslijst.
     * @return de waarde van naamgebruik stapel van TussenPersoonslijst
     */
    public TussenStapel<BrpNaamgebruikInhoud> getNaamgebruikStapel() {
        return naamgebruikStapel;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.conversie.model.Persoonslijst#getActueelAdministratienummer()
     */
    @Override
    public String getActueelAdministratienummer() {
        return BrpString.unwrap(getIdentificatienummerStapel().getLaatsteElement().getInhoud().getAdministratienummer());
    }

    @Override
    public String getActueelBurgerservicenummer() {
        return BrpString.unwrap(getIdentificatienummerStapel().getLaatsteElement().getInhoud().getBurgerservicenummer());
    }

    /**
     * Geef de waarde van adres stapel van TussenPersoonslijst.
     * @return de waarde van adres stapel van TussenPersoonslijst
     */
    public TussenStapel<BrpAdresInhoud> getAdresStapel() {
        return adresStapel;
    }

    /**
     * Geef de waarde van persoon afgeleid administratief stapel van TussenPersoonslijst.
     * @return de waarde van persoon afgeleid administratief stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpPersoonAfgeleidAdministratiefInhoud> getPersoonAfgeleidAdministratiefStapel() {
        return persoonAfgeleidAdministratiefStapel;
    }

    /**
     * Geef de waarde van behandeld als nederlander indicatie stapel van TussenPersoonslijst.
     * @return de waarde van behandeld als nederlander indicatie stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> getBehandeldAlsNederlanderIndicatieStapel() {
        return behandeldAlsNederlanderIndicatieStapel;
    }

    /**
     * Geef de waarde van onverwerkt document aanwezig indicatie stapel van TussenPersoonslijst.
     * @return de waarde van onverwerkt document aanwezig indicatie stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpOnverwerktDocumentAanwezigIndicatieInhoud> getOnverwerktDocumentAanwezigIndicatieStapel() {
        return onverwerktDocumentAanwezigIndicatieStapel;
    }

    /**
     * Geef de waarde van signalering met betrekking tot verstrekken reisdocument stapel van TussenPersoonslijst.
     * @return de waarde van signalering met betrekking tot verstrekken reisdocument stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel() {
        return signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel;
    }

    /**
     * Geef de waarde van bijhouding stapel van TussenPersoonslijst.
     * @return de waarde van bijhouding stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpBijhoudingInhoud> getBijhoudingStapel() {

        return bijhoudingStapel;
    }

    /**
     * Geef de waarde van derde heeft gezag indicatie stapel van TussenPersoonslijst.
     * @return de waarde van derde heeft gezag indicatie stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpDerdeHeeftGezagIndicatieInhoud> getDerdeHeeftGezagIndicatieStapel() {

        return derdeHeeftGezagIndicatieStapel;
    }

    /**
     * Geef de waarde van deelname eu verkiezingen stapel van TussenPersoonslijst.
     * @return de waarde van deelname eu verkiezingen stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpDeelnameEuVerkiezingenInhoud> getDeelnameEuVerkiezingenStapel() {

        return deelnameEuVerkiezingenStapel;
    }

    /**
     * Geef de waarde van geboorte stapel van TussenPersoonslijst.
     * @return de waarde van geboorte stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpGeboorteInhoud> getGeboorteStapel() {
        return geboorteStapel;
    }

    /**
     * Geef de waarde van geslachtsaanduiding stapel van TussenPersoonslijst.
     * @return de waarde van geslachtsaanduiding stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpGeslachtsaanduidingInhoud> getGeslachtsaanduidingStapel() {
        return geslachtsaanduidingStapel;
    }

    /**
     * Geef de waarde van identificatienummer stapel van TussenPersoonslijst.
     * @return de waarde van identificatienummer stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpIdentificatienummersInhoud> getIdentificatienummerStapel() {
        return identificatienummerStapel;
    }

    /**
     * Geef de waarde van migratie stapel van TussenPersoonslijst.
     * @return de waarde van migratie stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpMigratieInhoud> getMigratieStapel() {
        return migratieStapel;
    }

    /**
     * Geef de waarde van inschrijving stapel van TussenPersoonslijst.
     * @return de waarde van inschrijving stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpInschrijvingInhoud> getInschrijvingStapel() {
        return inschrijvingStapel;
    }

    /**
     * Geef de waarde van nationaliteit stapels van TussenPersoonslijst.
     * @return de waarde van nationaliteit stapels van TussenPersoonslijst
     */
    public List<TussenStapel<BrpNationaliteitInhoud>> getNationaliteitStapels() {
        return TussenPersoonslijst.copyOf(nationaliteitStapels);
    }

    /**
     * Geef de waarde van buitenlands persoonsnummer stapels van TussenPersoonslijst.
     * @return de waarde van buitenlands persoonsnummer stapels van TussenPersoonslijst
     */
    public List<TussenStapel<BrpBuitenlandsPersoonsnummerInhoud>> getBuitenlandsPersoonsnummerStapels() {
        return TussenPersoonslijst.copyOf(buitenlandsPersoonsnummerStapels);
    }

    /**
     * Geef de waarde van nummerverwijzing stapel van TussenPersoonslijst.
     * @return de waarde van nummerverwijzing stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpNummerverwijzingInhoud> getNummerverwijzingStapel() {
        return nummerverwijzingStapel;
    }

    /**
     * Geef de waarde van onder curatele indicatie stapel van TussenPersoonslijst.
     * @return de waarde van onder curatele indicatie stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpOnderCurateleIndicatieInhoud> getOnderCurateleIndicatieStapel() {

        return onderCurateleIndicatieStapel;
    }

    /**
     * Geef de waarde van overlijden stapel van TussenPersoonslijst.
     * @return de waarde van overlijden stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpOverlijdenInhoud> getOverlijdenStapel() {

        return overlijdenStapel;
    }

    /**
     * Geef de waarde van persoonskaart stapel van TussenPersoonslijst.
     * @return de waarde van persoonskaart stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpPersoonskaartInhoud> getPersoonskaartStapel() {

        return persoonskaartStapel;
    }

    /**
     * Geef de waarde van reisdocument stapels van TussenPersoonslijst.
     * @return de waarde van reisdocument stapels van TussenPersoonslijst
     */
    public List<TussenStapel<BrpReisdocumentInhoud>> getReisdocumentStapels() {
        return TussenPersoonslijst.copyOf(reisdocumentStapels);
    }

    /**
     * Geef de waarde van relaties van TussenPersoonslijst.
     * @return de waarde van relaties van TussenPersoonslijst
     */
    public List<TussenRelatie> getRelaties() {
        return TussenPersoonslijst.copyOf(relaties);
    }

    /**
     * Geef de waarde van samengestelde naam stapel van TussenPersoonslijst.
     * @return de waarde van samengestelde naam stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpSamengesteldeNaamInhoud> getSamengesteldeNaamStapel() {

        return samengesteldeNaamStapel;
    }

    /**
     * Geef de waarde van staatloos indicatie stapel van TussenPersoonslijst.
     * @return de waarde van staatloos indicatie stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpStaatloosIndicatieInhoud> getStaatloosIndicatieStapel() {
        return staatloosIndicatieStapel;
    }

    /**
     * Geef de waarde van uitsluiting kiesrecht stapel van TussenPersoonslijst.
     * @return de waarde van uitsluiting kiesrecht stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpUitsluitingKiesrechtInhoud> getUitsluitingKiesrechtStapel() {
        return uitsluitingKiesrechtStapel;
    }

    /**
     * Geef de waarde van vastgesteld niet nederlander indicatie stapel van TussenPersoonslijst.
     * @return de waarde van vastgesteld niet nederlander indicatie stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> getVastgesteldNietNederlanderIndicatieStapel() {
        return vastgesteldNietNederlanderIndicatieStapel;
    }

    /**
     * Geef de waarde van verblijfsrecht stapel van TussenPersoonslijst.
     * @return de waarde van verblijfsrecht stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpVerblijfsrechtInhoud> getVerblijfsrechtStapel() {
        return verblijfsrechtStapel;
    }

    /**
     * Geef de waarde van verstrekkingsbeperking indicatie stapel van TussenPersoonslijst.
     * @return de waarde van verstrekkingsbeperking indicatie stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> getVerstrekkingsbeperkingIndicatieStapel() {
        return verstrekkingsbeperkingIndicatieStapel;
    }

    /**
     * Geef de waarde van bijzondere verblijfsrechtelijke positie indicatie stapel van TussenPersoonslijst.
     * @return de waarde van bijzondere verblijfsrechtelijke positie indicatie stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> getBijzondereVerblijfsrechtelijkePositieIndicatieStapel() {
        return bijzondereVerblijfsrechtelijkePositieIndicatieStapel;
    }

    /**
     * Geef de waarde van verificatie stapels van TussenPersoonslijst.
     * @return de waarde van verificatie stapels van TussenPersoonslijst
     */
    public List<TussenStapel<BrpVerificatieInhoud>> getVerificatieStapels() {
        return TussenPersoonslijst.copyOf(verificatieStapels);
    }

    /**
     * Geef de waarde van ist ouder1 stapel van TussenPersoonslijst.
     * @return de waarde van ist ouder1 stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpIstRelatieGroepInhoud> getIstOuder1Stapel() {
        return istOuder1Stapel;
    }

    /**
     * Geef de waarde van ist ouder2 stapel van TussenPersoonslijst.
     * @return de waarde van ist ouder2 stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpIstRelatieGroepInhoud> getIstOuder2Stapel() {
        return istOuder2Stapel;
    }

    /**
     * Geef de waarde van ist huwelijk of gp stapels van TussenPersoonslijst.
     * @return de waarde van ist huwelijk of gp stapels van TussenPersoonslijst
     */
    public List<TussenStapel<BrpIstHuwelijkOfGpGroepInhoud>> getIstHuwelijkOfGpStapels() {
        return istHuwelijkOfGpStapels;
    }

    /**
     * Geef de waarde van ist kind stapels van TussenPersoonslijst.
     * @return de waarde van ist kind stapels van TussenPersoonslijst
     */
    public List<TussenStapel<BrpIstRelatieGroepInhoud>> getIstKindStapels() {
        return istKindStapels;
    }

    /**
     * Geef de waarde van ist gezagsverhouding stapel van TussenPersoonslijst.
     * @return de waarde van ist gezagsverhouding stapel van TussenPersoonslijst
     */

    public TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> getIstGezagsverhoudingStapel() {
        return istGezagsverhoudingStapel;
    }
}
