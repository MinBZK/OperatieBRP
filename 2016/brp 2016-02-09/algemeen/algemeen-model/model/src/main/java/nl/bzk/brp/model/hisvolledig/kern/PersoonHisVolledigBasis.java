/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNummerverwijzingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonPersoonskaartModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingKiesrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfsrechtModel;

/**
 * Interface voor Persoon.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface PersoonHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, HisVolledigRootObject {

    /**
     * Retourneert de historie van His Persoon Afgeleid administratief.
     *
     * @return Historie met His Persoon Afgeleid administratief
     */
    FormeleHistorieSet<HisPersoonAfgeleidAdministratiefModel> getPersoonAfgeleidAdministratiefHistorie();

    /**
     * Retourneert de historie van His Persoon Identificatienummers.
     *
     * @return Historie met His Persoon Identificatienummers
     */
    MaterieleHistorieSet<HisPersoonIdentificatienummersModel> getPersoonIdentificatienummersHistorie();

    /**
     * Retourneert de historie van His Persoon Samengestelde naam.
     *
     * @return Historie met His Persoon Samengestelde naam
     */
    MaterieleHistorieSet<HisPersoonSamengesteldeNaamModel> getPersoonSamengesteldeNaamHistorie();

    /**
     * Retourneert de historie van His Persoon Geboorte.
     *
     * @return Historie met His Persoon Geboorte
     */
    FormeleHistorieSet<HisPersoonGeboorteModel> getPersoonGeboorteHistorie();

    /**
     * Retourneert de historie van His Persoon Geslachtsaanduiding.
     *
     * @return Historie met His Persoon Geslachtsaanduiding
     */
    MaterieleHistorieSet<HisPersoonGeslachtsaanduidingModel> getPersoonGeslachtsaanduidingHistorie();

    /**
     * Retourneert de historie van His Persoon Inschrijving.
     *
     * @return Historie met His Persoon Inschrijving
     */
    FormeleHistorieSet<HisPersoonInschrijvingModel> getPersoonInschrijvingHistorie();

    /**
     * Retourneert de historie van His Persoon Nummerverwijzing.
     *
     * @return Historie met His Persoon Nummerverwijzing
     */
    MaterieleHistorieSet<HisPersoonNummerverwijzingModel> getPersoonNummerverwijzingHistorie();

    /**
     * Retourneert de historie van His Persoon Bijhouding.
     *
     * @return Historie met His Persoon Bijhouding
     */
    MaterieleHistorieSet<HisPersoonBijhoudingModel> getPersoonBijhoudingHistorie();

    /**
     * Retourneert de historie van His Persoon Overlijden.
     *
     * @return Historie met His Persoon Overlijden
     */
    FormeleHistorieSet<HisPersoonOverlijdenModel> getPersoonOverlijdenHistorie();

    /**
     * Retourneert de historie van His Persoon Naamgebruik.
     *
     * @return Historie met His Persoon Naamgebruik
     */
    FormeleHistorieSet<HisPersoonNaamgebruikModel> getPersoonNaamgebruikHistorie();

    /**
     * Retourneert de historie van His Persoon Migratie.
     *
     * @return Historie met His Persoon Migratie
     */
    MaterieleHistorieSet<HisPersoonMigratieModel> getPersoonMigratieHistorie();

    /**
     * Retourneert de historie van His Persoon Verblijfsrecht.
     *
     * @return Historie met His Persoon Verblijfsrecht
     */
    FormeleHistorieSet<HisPersoonVerblijfsrechtModel> getPersoonVerblijfsrechtHistorie();

    /**
     * Retourneert de historie van His Persoon Uitsluiting kiesrecht.
     *
     * @return Historie met His Persoon Uitsluiting kiesrecht
     */
    FormeleHistorieSet<HisPersoonUitsluitingKiesrechtModel> getPersoonUitsluitingKiesrechtHistorie();

    /**
     * Retourneert de historie van His Persoon Deelname EU verkiezingen.
     *
     * @return Historie met His Persoon Deelname EU verkiezingen
     */
    FormeleHistorieSet<HisPersoonDeelnameEUVerkiezingenModel> getPersoonDeelnameEUVerkiezingenHistorie();

    /**
     * Retourneert de historie van His Persoon Persoonskaart.
     *
     * @return Historie met His Persoon Persoonskaart
     */
    FormeleHistorieSet<HisPersoonPersoonskaartModel> getPersoonPersoonskaartHistorie();

    /**
     * Retourneert Soort van Persoon.
     *
     * @return Soort van Persoon
     */
    SoortPersoonAttribuut getSoort();

    /**
     * Retourneert lijst van Persoon \ Voornaam.
     *
     * @return lijst van Persoon \ Voornaam
     */
    Set<? extends PersoonVoornaamHisVolledig> getVoornamen();

    /**
     * Retourneert lijst van Persoon \ Geslachtsnaamcomponent.
     *
     * @return lijst van Persoon \ Geslachtsnaamcomponent
     */
    Set<? extends PersoonGeslachtsnaamcomponentHisVolledig> getGeslachtsnaamcomponenten();

    /**
     * Retourneert lijst van Persoon \ Verificatie.
     *
     * @return lijst van Persoon \ Verificatie
     */
    Set<? extends PersoonVerificatieHisVolledig> getVerificaties();

    /**
     * Retourneert lijst van Persoon \ Nationaliteit.
     *
     * @return lijst van Persoon \ Nationaliteit
     */
    Set<? extends PersoonNationaliteitHisVolledig> getNationaliteiten();

    /**
     * Retourneert lijst van Persoon \ Adres.
     *
     * @return lijst van Persoon \ Adres
     */
    Set<? extends PersoonAdresHisVolledig> getAdressen();

    /**
     * Retourneert lijst van Persoon \ Reisdocument.
     *
     * @return lijst van Persoon \ Reisdocument
     */
    Set<? extends PersoonReisdocumentHisVolledig> getReisdocumenten();

    /**
     * Retourneert lijst van Betrokkenheid.
     *
     * @return lijst van Betrokkenheid
     */
    Set<? extends BetrokkenheidHisVolledig> getBetrokkenheden();

    /**
     * Retourneert lijst van Persoon \ Onderzoek.
     *
     * @return lijst van Persoon \ Onderzoek
     */
    Set<? extends PersoonOnderzoekHisVolledig> getOnderzoeken();

    /**
     * Retourneert lijst van Persoon \ Verstrekkingsbeperking.
     *
     * @return lijst van Persoon \ Verstrekkingsbeperking
     */
    Set<? extends PersoonVerstrekkingsbeperkingHisVolledig> getVerstrekkingsbeperkingen();

    /**
     * Retourneert lijst van Persoon \ Afnemerindicatie.
     *
     * @return lijst van Persoon \ Afnemerindicatie
     */
    Set<? extends PersoonAfnemerindicatieHisVolledig> getAfnemerindicaties();

    /**
     * Geeft indicatie Derde heeft gezag? terug.
     *
     * @return indicatie Derde heeft gezag?
     */
    PersoonIndicatieDerdeHeeftGezagHisVolledig getIndicatieDerdeHeeftGezag();

    /**
     * Geeft indicatie Onder curatele? terug.
     *
     * @return indicatie Onder curatele?
     */
    PersoonIndicatieOnderCurateleHisVolledig getIndicatieOnderCuratele();

    /**
     * Geeft indicatie Volledige verstrekkingsbeperking? terug.
     *
     * @return indicatie Volledige verstrekkingsbeperking?
     */
    PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledig getIndicatieVolledigeVerstrekkingsbeperking();

    /**
     * Geeft indicatie Vastgesteld niet Nederlander? terug.
     *
     * @return indicatie Vastgesteld niet Nederlander?
     */
    PersoonIndicatieVastgesteldNietNederlanderHisVolledig getIndicatieVastgesteldNietNederlander();

    /**
     * Geeft indicatie Behandeld als Nederlander? terug.
     *
     * @return indicatie Behandeld als Nederlander?
     */
    PersoonIndicatieBehandeldAlsNederlanderHisVolledig getIndicatieBehandeldAlsNederlander();

    /**
     * Geeft indicatie Signalering met betrekking tot verstrekken reisdocument? terug.
     *
     * @return indicatie Signalering met betrekking tot verstrekken reisdocument?
     */
    PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledig getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument();

    /**
     * Geeft indicatie Staatloos? terug.
     *
     * @return indicatie Staatloos?
     */
    PersoonIndicatieStaatloosHisVolledig getIndicatieStaatloos();

    /**
     * Geeft indicatie Bijzondere verblijfsrechtelijke positie? terug.
     *
     * @return indicatie Bijzondere verblijfsrechtelijke positie?
     */
    PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledig getIndicatieBijzondereVerblijfsrechtelijkePositie();

    /**
     * Geeft alle indicaties terug in een set.
     *
     * @return set met indicaties
     */
    Set<? extends PersoonIndicatieHisVolledig> getIndicaties();

}
