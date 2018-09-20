/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import nl.bzk.brp.model.operationeel.kern.HisPartijOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBehandeldAlsNederlanderModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieDerdeHeeftGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieOnderCurateleModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieStaatloosModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVastgesteldNietNederlanderModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNummerverwijzingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonPersoonskaartModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingKiesrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfsrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import org.hibernate.annotations.Immutable;

/**
 * Actie object met alle gerelateerde gewijzigde gegevens gekoppeld.
 */
@Access(value = AccessType.FIELD)
@Entity(name = "Actie")
@Immutable
@Table(schema = "Kern", name = "Actie")
@SuppressWarnings("checkstyle:designforextension")
public class Actie {

    @Id
    @SequenceGenerator(name = "ACTIE", sequenceName = "Kern.seq_Actie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ACTIE")
    @JsonProperty
    private Long iD;

    @Embedded
    @AttributeOverride(name = SoortActieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt", updatable = false))
    @JsonProperty
    private SoortActieAttribuut soort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    // PdJ: Handmatige wijziging: TEAMBRP-1621
    @JsonProperty
    private AdministratieveHandelingModel administratieveHandeling;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsReg"))
    @JsonProperty
    private DatumTijdAttribuut tijdstipRegistratie;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatOntlening"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumOntlening;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Actie")
    @JsonProperty
    private Set<ActieBronModel> bronnen;

    // PERSOON
    @Embedded
    private FormeleHistorie<HisPersoonAfgeleidAdministratiefModel> hisPersoonAfgeleidAdministratief;
    @Embedded
    private MaterieleHistorie<HisPersoonIdentificatienummersModel> hisPersoonIdentificatienummer;
    @Embedded
    private MaterieleHistorie<HisPersoonSamengesteldeNaamModel> hisPersoonSamengesteldeNaam;
    @Embedded
    private FormeleHistorie<HisPersoonGeboorteModel> hisPersoonGeboorte;
    @Embedded
    private MaterieleHistorie<HisPersoonGeslachtsaanduidingModel> hisPersoonGeslachtsaanduiding;
    @Embedded
    private FormeleHistorie<HisPersoonInschrijvingModel> hisPersoonInschrijving;
    @Embedded
    private MaterieleHistorie<HisPersoonNummerverwijzingModel> hisPersoonNummerverwijzing;
    @Embedded
    private MaterieleHistorie<HisPersoonBijhoudingModel> hisPersoonBijhoudingModel;
    @Embedded
    private FormeleHistorie<HisPersoonOverlijdenModel> hisPersoonOverlijdenModel;
    @Embedded
    private FormeleHistorie<HisPersoonNaamgebruikModel> hisPersoonNaamgebruikModel;
    @Embedded
    private MaterieleHistorie<HisPersoonMigratieModel> hisPersoonMigratie;
    @Embedded
    private FormeleHistorie<HisPersoonVerblijfsrechtModel> hisPersoonVerblijfsrechtModel;
    @Embedded
    private FormeleHistorie<HisPersoonUitsluitingKiesrechtModel> hisPersoonUitsluitingKiesrecht;
    @Embedded
    private FormeleHistorie<HisPersoonDeelnameEUVerkiezingenModel> hisPersoonDeelnameEUVerkiezingen;
    @Embedded
    private FormeleHistorie<HisPersoonPersoonskaartModel> hisPersoonPersoonskaart;

    // PERSOON / ADRES
    @Embedded
    private MaterieleHistorie<HisPersoonAdresModel> hisAdresStandaard;

    // PERSOON / GESLACHTSNAAMCOMPONENT
    @Embedded
    private MaterieleHistorie<HisPersoonGeslachtsnaamcomponentModel> hisGeslachtsnaamcomponentStandaard;

    // PERSOON / INDICATIE
    // extends niet van AbstractMaterieelHistorischMetActieVerantwoording
    @Embedded
    private MaterieleHistorie<HisPersoonIndicatieModel> hisIndicatieStandaard;

    // PERSOON / NATIONALITEIT
    @Embedded
    private MaterieleHistorie<HisPersoonNationaliteitModel> hisNationaliteitStandaard;

    // PERSOON / REISDOCUMENT
    @Embedded
    private FormeleHistorie<HisPersoonReisdocumentModel> hisReisdocumentStandaard;

    // PERSOON / VERIFICATIE
    @Embedded
    private FormeleHistorie<HisPersoonVerificatieModel> hisVerificatieStandaard;

    // PERSOON / VERSTREKKINGSBEPERKING
    @Embedded
    private FormeleHistorie<HisPersoonVerstrekkingsbeperkingModel> hisVerstrekkingsbeperkingStandaard;

    // PERSOON / VOORNAAM
    @Embedded
    private MaterieleHistorie<HisPersoonVoornaamModel> hisVoornaamStandaard;

    // RELATIE
    @Embedded
    private FormeleHistorie<HisRelatieModel> hisRelatieStandaard;

    // RELATIE / BETROKKENHEID
    @Embedded
    private FormeleHistorie<HisBetrokkenheidModel> hisBetrokkenheidStandaard;
    @Embedded
    private MaterieleHistorie<HisOuderOuderschapModel> hisBetrokkenheidOuderschap;
    @Embedded
    private MaterieleHistorie<HisOuderOuderlijkGezagModel> hisBetrokkenheidOuderlijkGezag;

    // ONDERZOEK
    @Embedded
    private FormeleHistorie<HisOnderzoekModel> hisOnderzoekStandaard;
    @Embedded
    private FormeleHistorie<HisOnderzoekAfgeleidAdministratiefModel> hisOnderzoekAfgeleidAdministratief;

    // ONDERZOEK / PARTIJ
    @Embedded
    private FormeleHistorie<HisPartijOnderzoekModel> hisOnderzoekPartijStandaard;

    // ONDERZOEK / PERSOON
    @Embedded
    private FormeleHistorie<HisPersoonOnderzoekModel> hisOnderzoekPersoonStandaard;

    /**
     * Retourneert ID van Actie.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * soort actie.
     *
     * @return soort
     */
    public SoortActieAttribuut getSoort() {
        return soort;
    }

    /**
     * Administratieve handeling.
     *
     * @return administratieve handeling
     */
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Partij.
     *
     * @return partij
     */
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * Tijdstip registratie.
     *
     * @return tijdstip registratie
     */
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Datum ontlening.
     *
     * @return datum ontlening
     */
    public DatumEvtDeelsOnbekendAttribuut getDatumOntlening() {
        return datumOntlening;
    }

    /**
     * Bronnen.
     *
     * @return bronnen
     */
    public Set<ActieBronModel> getBronnen() {
        return bronnen;
    }

    /**
     * Retourneert iD van Actie.
     *
     * @return iD.
     */
    public Long getiD() {
        return iD;
    }

    /**
     * Retourneert de historisch afgeleid administratieve gegevens van een persoon voor de Actie.
     *
     * @return De historisch afgeleid administratieve gegevens van een persoon.
     */
    public Historie<HisPersoonAfgeleidAdministratiefModel> getHisPersoonAfgeleidAdministratief() {
        return hisPersoonAfgeleidAdministratief;
    }

    /**
     * Retourneert de historische identificatienummer gegevens van een persoon voor de Actie.
     *
     * @return De historische identificatienummer gegevens van een persoon.
     */
    public Historie<HisPersoonIdentificatienummersModel> getHisPersoonIdentificatienummer() {
        return hisPersoonIdentificatienummer;
    }

    /**
     * Retourneert de historische samengestelde naam gegevens van een persoon voor de Actie.
     *
     * @return De historische samengestelde naam gegevens van een persoon.
     */
    public Historie<HisPersoonSamengesteldeNaamModel> getHisPersoonSamengesteldeNaam() {
        return hisPersoonSamengesteldeNaam;
    }

    /**
     * Retourneert de historische geboorte gegevens van een persoon voor de Actie.
     *
     * @return De historische geboorte gegevens van een persoon.
     */
    public Historie<HisPersoonGeboorteModel> getHisPersoonGeboorte() {
        return hisPersoonGeboorte;
    }

    /**
     * Retourneert de historische geslachtsaanduiding gegevens van een persoon voor de Actie.
     *
     * @return De historische geslachtsaanduiding gegevens van een persoon.
     */
    public Historie<HisPersoonGeslachtsaanduidingModel> getHisPersoonGeslachtsaanduiding() {
        return hisPersoonGeslachtsaanduiding;
    }

    /**
     * Retourneert de historische inschrijving gegevens van een persoon voor de Actie.
     *
     * @return De historische inschrijving gegevens van een persoon.
     */
    public Historie<HisPersoonInschrijvingModel> getHisPersoonInschrijving() {
        return hisPersoonInschrijving;
    }

    /**
     * Retourneert de historische nummerverwijzing gegevens van een persoon voor de Actie.
     *
     * @return De historische nummerverwijzing gegevens van een persoon.
     */
    public Historie<HisPersoonNummerverwijzingModel> getHisPersoonNummerverwijzing() {
        return hisPersoonNummerverwijzing;
    }

    public Historie<HisPersoonBijhoudingModel> getHisPersoonBijhoudingModel() {
        return hisPersoonBijhoudingModel;
    }

    /**
     * Retourneert de historische overlijdens gegevens van een persoon voor de Actie.
     *
     * @return De historische overlijdens gegevens van een persoon.
     */
    public Historie<HisPersoonOverlijdenModel> getHisPersoonOverlijdenModel() {
        return hisPersoonOverlijdenModel;
    }

    /**
     * Retourneert de historische naamgebruik gegevens van een persoon voor de Actie.
     *
     * @return De historische naamgebruik gegevens van een persoon.
     */
    public Historie<HisPersoonNaamgebruikModel> getHisPersoonNaamgebruikModel() {
        return hisPersoonNaamgebruikModel;
    }

    /**
     * Retourneert de historische indicatie gezag gegevens van een persoon voor de Actie.
     *
     * @return De historische indicatie gezag gegevens van een persoon.
     */
    public Historie<HisPersoonIndicatieDerdeHeeftGezagModel> getHisPersoonIndicatieDerdeHeeftGezagModel() {
        return IndicatieUtil.bepaalIndicatieHistorie(hisIndicatieStandaard, HisPersoonIndicatieDerdeHeeftGezagModel.class);
    }

    /**
     * Retourneert de historische indicatie onder curatele gegevens van een persoon voor de Actie.
     *
     * @return De historische indicatie onder curatele gegevens van een persoon.
     */
    public Historie<HisPersoonIndicatieOnderCurateleModel> getHisPersoonIndicatieOnderCuratele() {
        return IndicatieUtil.bepaalIndicatieHistorie(hisIndicatieStandaard, HisPersoonIndicatieOnderCurateleModel.class);
    }

    /**
     * Retourneert de historische migratie gegevens van een persoon voor de Actie.
     *
     * @return De historische migratie gegevens van een persoon.
     */
    public Historie<HisPersoonMigratieModel> getHisPersoonMigratie() {
        return hisPersoonMigratie;
    }

    /**
     * Retourneert de historische verblijfsrecht gegevens van een persoon voor de Actie.
     *
     * @return De historische verblijfsrecht gegevens van een persoon.
     */
    public Historie<HisPersoonVerblijfsrechtModel> getHisPersoonVerblijfsrechtModel() {
        return hisPersoonVerblijfsrechtModel;
    }

    /**
     * Retourneert de historische bijzondere verblijfsrechtelijke gegevens van een persoon voor de Actie.
     *
     * @return De historische bijzonder verblijfsrechtelijke gegevens van een persoon.
     */
    public Historie<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel> getHisPersoonIndicatieBijzondereVerblijfsrechtelijkePositie() {
        return IndicatieUtil.bepaalIndicatieHistorie(hisIndicatieStandaard, HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel.class);
    }

    /**
     * Retourneert de historische uitsluiting kiesrecht gegevens van een persoon voor de Actie.
     *
     * @return De historische uitsluiting kiesrecht gegevens van een persoon.
     */
    public Historie<HisPersoonUitsluitingKiesrechtModel> getHisPersoonUitsluitingKiesrecht() {
        return hisPersoonUitsluitingKiesrecht;
    }

    /**
     * Retourneert de historische deelname EU verkiezingen gegevens van een persoon voor de Actie.
     *
     * @return De historische deelname EU verkiezingen gegevens van een persoon.
     */
    public Historie<HisPersoonDeelnameEUVerkiezingenModel> getHisPersoonDeelnameEUVerkiezingen() {
        return hisPersoonDeelnameEUVerkiezingen;
    }

    /**
     * Retourneert de historische persoonskaart gegevens van een persoon voor de Actie.
     *
     * @return De historische persoonskaart gegevens van een persoon.
     */
    public Historie<HisPersoonPersoonskaartModel> getHisPersoonPersoonskaart() {
        return hisPersoonPersoonskaart;
    }

    /**
     * Retourneert de historische indicatie staatloos gegevens van een persoon voor de Actie.
     *
     * @return De historische indicatie staatloos gegevens van een persoon.
     */
    public Historie<HisPersoonIndicatieStaatloosModel> getHisPersoonIndicatieStaatloos() {
        return IndicatieUtil.bepaalIndicatieHistorie(hisIndicatieStandaard, HisPersoonIndicatieStaatloosModel.class);
    }

    /**
     * Retourneert de historische indicatie volledige verstrekkingsbeperking gegevens van een persoon voor de Actie.
     *
     * @return De historische indicatie volledige verstrekkingsbeperking gegevens van een persoon.
     */
    public Historie<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel> getHisPersoonIndicatieVolledigeVerstrekkingsbeperking() {
        return IndicatieUtil.bepaalIndicatieHistorie(hisIndicatieStandaard, HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel.class);
    }

    /**
     * Retourneert de historische indicatie vastgesteld niet-Nederlander gegevens van een persoon voor de Actie.
     *
     * @return De historische indicatie vastgesteld niet-Nederlander gegevens van een persoon.
     */
    public Historie<HisPersoonIndicatieVastgesteldNietNederlanderModel> getHisPersoonIndicatieVastgesteldNietNederlander() {
        return IndicatieUtil.bepaalIndicatieHistorie(hisIndicatieStandaard, HisPersoonIndicatieVastgesteldNietNederlanderModel.class);
    }

    /**
     * Retourneert de historische indicatie behandeld als Nederlander gegevens van een persoon voor de Actie.
     *
     * @return De historische indicatie behanedeld als Nederlander gegevens van een persoon.
     */
    public Historie<HisPersoonIndicatieBehandeldAlsNederlanderModel> getHisPersoonIndicatieBehandeldAlsNederlander() {
        return IndicatieUtil.bepaalIndicatieHistorie(hisIndicatieStandaard, HisPersoonIndicatieBehandeldAlsNederlanderModel.class);
    }

    /**
     * Retourneert de historische indicatie signalering m.b.t. verstrekken reisdocument gegevens van een persoon voor de
     * Actie.
     *
     * @return De historische indicatie signalering m.b.t. verstrekken reisdocument gegevens van een persoon.
     */
    public Historie<HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel>
        getHisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument()
    {
        return IndicatieUtil.bepaalIndicatieHistorie(
                hisIndicatieStandaard,
                HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel.class);
    }

    /**
     * Retourneert de historische adres gegevens van een persoon voor de Actie.
     *
     * @return De historische adres gegevens van een persoon.
     */
    public Historie<HisPersoonAdresModel> getHisAdresStandaard() {
        return hisAdresStandaard;
    }

    /**
     * Retourneert de historische geslachtsnaamcomponent gegevens van een persoon voor de Actie.
     *
     * @return De historische geslachtsnaamcomponent gegevens van een persoon.
     */
    public Historie<HisPersoonGeslachtsnaamcomponentModel> getHisGeslachtsnaamcomponentStandaard() {
        return hisGeslachtsnaamcomponentStandaard;
    }

    /**
     * Retourneert de historische nationaliteit gegevens van een persoon voor de Actie.
     *
     * @return De historische nationaliteit gegevens van een persoon.
     */
    public Historie<HisPersoonNationaliteitModel> getHisNationaliteitStandaard() {
        return hisNationaliteitStandaard;
    }

    /**
     * Retourneert de historische reisdocument gegevens van een persoon voor de Actie.
     *
     * @return De historische reisdocument gegevens van een persoon.
     */
    public Historie<HisPersoonReisdocumentModel> getHisReisdocumentStandaard() {
        return hisReisdocumentStandaard;
    }

    /**
     * Retourneert de historische verificatie gegevens van een persoon voor de Actie.
     *
     * @return De historische verificatie gegevens van een persoon.
     */
    public Historie<HisPersoonVerificatieModel> getHisVerificatieStandaard() {
        return hisVerificatieStandaard;
    }

    /**
     * Retourneert de historische verstrekkingsbeperking gegevens van een persoon voor de Actie.
     *
     * @return De historische verstrekkingsbeperking gegevens van een persoon.
     */
    public Historie<HisPersoonVerstrekkingsbeperkingModel> getHisVerstrekkingsbeperkingStandaard() {
        return hisVerstrekkingsbeperkingStandaard;
    }

    /**
     * Retourneert de historische voornaam gegevens van een persoon voor de Actie.
     *
     * @return De historische voornaam gegevens van een persoon.
     */
    public Historie<HisPersoonVoornaamModel> getHisVoornaamStandaard() {
        return hisVoornaamStandaard;
    }

    /**
     * Retourneert de historische relatie gegevens van een persoon voor de Actie.
     *
     * @return De historische relatie gegevens van een persoon.
     */
    public Historie<HisRelatieModel> getHisRelatieStandaard() {
        return hisRelatieStandaard;
    }

    /**
     * Retourneert de historische betrokkenheid gegevens van een persoon voor de Actie.
     *
     * @return De historische betrokkenheid gegevens van een persoon.
     */
    public Historie<HisBetrokkenheidModel> getHisBetrokkenheidStandaard() {
        return hisBetrokkenheidStandaard;
    }

    /**
     * Retourneert de historische betrokkenheid ouderschap gegevens van een persoon voor de Actie.
     *
     * @return De historische betrokkenheid ouderschap gegevens van een persoon.
     */
    public Historie<HisOuderOuderschapModel> getHisBetrokkenheidOuderschap() {
        return hisBetrokkenheidOuderschap;
    }

    /**
     * Retourneert de historische betrokkenheid ouderlijk gezag gegevens van een persoon voor de Actie.
     *
     * @return De historische betrokkenheid ouderlijk gezag gegevens van een persoon.
     */
    public Historie<HisOuderOuderlijkGezagModel> getHisBetrokkenheidOuderlijkGezag() {
        return hisBetrokkenheidOuderlijkGezag;
    }

    /**
     * Retourneert de historische onderzoek gegevens van een persoon voor de Actie.
     *
     * @return De historische onderzoek gegevens van een persoon.
     */
    public Historie<HisOnderzoekModel> getHisOnderzoekStandaard() {
        return hisOnderzoekStandaard;
    }

    /**
     * Retourneert de historische onderzoek afgeleid administratief gegevens van een persoon voor de Actie.
     *
     * @return De historische onderzoek afgeleid administratief gegevens van een persoon.
     */
    public Historie<HisOnderzoekAfgeleidAdministratiefModel> getHisOnderzoekAfgeleidAdministratief() {
        return hisOnderzoekAfgeleidAdministratief;
    }

    /**
     * Retourneert de historische onderzoek partij gegevens van een persoon voor de Actie.
     *
     * @return De historische onderzoek partij gegevens van een persoon.
     */
    public Historie<HisPartijOnderzoekModel> getHisOnderzoekPartijStandaard() {
        return hisOnderzoekPartijStandaard;
    }

    /**
     * Retourneert de historische onderzoek persoon gegevens van een persoon voor de Actie.
     *
     * @return De historische onderzoek persoon gegevens van een persoon.
     */
    public Historie<HisPersoonOnderzoekModel> getHisOnderzoekPersoonStandaard() {
        return hisOnderzoekPersoonStandaard;
    }

}
