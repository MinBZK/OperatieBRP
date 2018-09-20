/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import java.util.Date;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingVerblijfsrechtCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VersienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrecht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrechtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Aangever;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonDeelnameEUVerkiezingenGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonInschrijvingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonMigratieGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNaamgebruikGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNummerverwijzingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonPersoonskaartGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonUitsluitingKiesrechtGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVerblijfsrechtGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieOnderCurateleHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieStaatloosHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerificatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
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
import nl.bzk.brp.util.StamgegevenBuilder;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Persoon.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonHisVolledigImplBuilder {

    private PersoonHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param soort soort van Persoon.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonHisVolledigImplBuilder(final SoortPersoon soort, final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(soort));
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Persoon.
     */
    public PersoonHisVolledigImplBuilder(final SoortPersoon soort) {
        this(soort, false);
    }

    /**
     * Start een nieuw Afgeleid administratief record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Afgeleid administratief groep builder
     */
    public PersoonHisVolledigImplBuilderAfgeleidAdministratief nieuwAfgeleidAdministratiefRecord(final Integer datumRegistratie) {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwAfgeleidAdministratiefRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Afgeleid administratief record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Afgeleid administratief groep builder
     */
    public PersoonHisVolledigImplBuilderAfgeleidAdministratief nieuwAfgeleidAdministratiefRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderAfgeleidAdministratief(actie);
    }

    /**
     * Start een nieuw Identificatienummers record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid datum einde
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Identificatienummers groep builder
     */
    public PersoonHisVolledigImplBuilderIdentificatienummers nieuwIdentificatienummersRecord(
        final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid,
        final Integer datumRegistratie)
    {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumAanvangGeldigheid != null) {
            actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        }
        if (datumEindeGeldigheid != null) {
            actieBericht.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumEindeGeldigheid));
        }
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwIdentificatienummersRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Identificatienummers record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Identificatienummers groep builder
     */
    public PersoonHisVolledigImplBuilderIdentificatienummers nieuwIdentificatienummersRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderIdentificatienummers(actie);
    }

    /**
     * Start een nieuw Samengestelde naam record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid datum einde
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Samengestelde naam groep builder
     */
    public PersoonHisVolledigImplBuilderSamengesteldeNaam nieuwSamengesteldeNaamRecord(
        final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid,
        final Integer datumRegistratie)
    {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumAanvangGeldigheid != null) {
            actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        }
        if (datumEindeGeldigheid != null) {
            actieBericht.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumEindeGeldigheid));
        }
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwSamengesteldeNaamRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Samengestelde naam record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Samengestelde naam groep builder
     */
    public PersoonHisVolledigImplBuilderSamengesteldeNaam nieuwSamengesteldeNaamRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderSamengesteldeNaam(actie);
    }

    /**
     * Start een nieuw Geboorte record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Geboorte groep builder
     */
    public PersoonHisVolledigImplBuilderGeboorte nieuwGeboorteRecord(final Integer datumRegistratie) {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwGeboorteRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Geboorte record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Geboorte groep builder
     */
    public PersoonHisVolledigImplBuilderGeboorte nieuwGeboorteRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderGeboorte(actie);
    }

    /**
     * Start een nieuw Geslachtsaanduiding record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid datum einde
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Geslachtsaanduiding groep builder
     */
    public PersoonHisVolledigImplBuilderGeslachtsaanduiding nieuwGeslachtsaanduidingRecord(
        final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid,
        final Integer datumRegistratie)
    {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumAanvangGeldigheid != null) {
            actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        }
        if (datumEindeGeldigheid != null) {
            actieBericht.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumEindeGeldigheid));
        }
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwGeslachtsaanduidingRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Geslachtsaanduiding record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Geslachtsaanduiding groep builder
     */
    public PersoonHisVolledigImplBuilderGeslachtsaanduiding nieuwGeslachtsaanduidingRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderGeslachtsaanduiding(actie);
    }

    /**
     * Start een nieuw Inschrijving record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Inschrijving groep builder
     */
    public PersoonHisVolledigImplBuilderInschrijving nieuwInschrijvingRecord(final Integer datumRegistratie) {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwInschrijvingRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Inschrijving record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Inschrijving groep builder
     */
    public PersoonHisVolledigImplBuilderInschrijving nieuwInschrijvingRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderInschrijving(actie);
    }

    /**
     * Start een nieuw Nummerverwijzing record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid datum einde
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Nummerverwijzing groep builder
     */
    public PersoonHisVolledigImplBuilderNummerverwijzing nieuwNummerverwijzingRecord(
        final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid,
        final Integer datumRegistratie)
    {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumAanvangGeldigheid != null) {
            actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        }
        if (datumEindeGeldigheid != null) {
            actieBericht.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumEindeGeldigheid));
        }
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwNummerverwijzingRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Nummerverwijzing record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Nummerverwijzing groep builder
     */
    public PersoonHisVolledigImplBuilderNummerverwijzing nieuwNummerverwijzingRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderNummerverwijzing(actie);
    }

    /**
     * Start een nieuw Bijhouding record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid datum einde
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Bijhouding groep builder
     */
    public PersoonHisVolledigImplBuilderBijhouding nieuwBijhoudingRecord(
        final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid,
        final Integer datumRegistratie)
    {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumAanvangGeldigheid != null) {
            actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        }
        if (datumEindeGeldigheid != null) {
            actieBericht.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumEindeGeldigheid));
        }
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwBijhoudingRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Bijhouding record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Bijhouding groep builder
     */
    public PersoonHisVolledigImplBuilderBijhouding nieuwBijhoudingRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderBijhouding(actie);
    }

    /**
     * Start een nieuw Overlijden record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Overlijden groep builder
     */
    public PersoonHisVolledigImplBuilderOverlijden nieuwOverlijdenRecord(final Integer datumRegistratie) {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwOverlijdenRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Overlijden record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Overlijden groep builder
     */
    public PersoonHisVolledigImplBuilderOverlijden nieuwOverlijdenRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderOverlijden(actie);
    }

    /**
     * Start een nieuw Naamgebruik record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Naamgebruik groep builder
     */
    public PersoonHisVolledigImplBuilderNaamgebruik nieuwNaamgebruikRecord(final Integer datumRegistratie) {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwNaamgebruikRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Naamgebruik record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Naamgebruik groep builder
     */
    public PersoonHisVolledigImplBuilderNaamgebruik nieuwNaamgebruikRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderNaamgebruik(actie);
    }

    /**
     * Start een nieuw Migratie record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid datum einde
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Migratie groep builder
     */
    public PersoonHisVolledigImplBuilderMigratie nieuwMigratieRecord(
        final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid,
        final Integer datumRegistratie)
    {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumAanvangGeldigheid != null) {
            actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        }
        if (datumEindeGeldigheid != null) {
            actieBericht.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumEindeGeldigheid));
        }
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwMigratieRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Migratie record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Migratie groep builder
     */
    public PersoonHisVolledigImplBuilderMigratie nieuwMigratieRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderMigratie(actie);
    }

    /**
     * Start een nieuw Verblijfsrecht record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Verblijfsrecht groep builder
     */
    public PersoonHisVolledigImplBuilderVerblijfsrecht nieuwVerblijfsrechtRecord(final Integer datumRegistratie) {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwVerblijfsrechtRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Verblijfsrecht record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Verblijfsrecht groep builder
     */
    public PersoonHisVolledigImplBuilderVerblijfsrecht nieuwVerblijfsrechtRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderVerblijfsrecht(actie);
    }

    /**
     * Start een nieuw Uitsluiting kiesrecht record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Uitsluiting kiesrecht groep builder
     */
    public PersoonHisVolledigImplBuilderUitsluitingKiesrecht nieuwUitsluitingKiesrechtRecord(final Integer datumRegistratie) {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwUitsluitingKiesrechtRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Uitsluiting kiesrecht record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Uitsluiting kiesrecht groep builder
     */
    public PersoonHisVolledigImplBuilderUitsluitingKiesrecht nieuwUitsluitingKiesrechtRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderUitsluitingKiesrecht(actie);
    }

    /**
     * Start een nieuw Deelname EU verkiezingen record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Deelname EU verkiezingen groep builder
     */
    public PersoonHisVolledigImplBuilderDeelnameEUVerkiezingen nieuwDeelnameEUVerkiezingenRecord(final Integer datumRegistratie) {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwDeelnameEUVerkiezingenRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Deelname EU verkiezingen record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Deelname EU verkiezingen groep builder
     */
    public PersoonHisVolledigImplBuilderDeelnameEUVerkiezingen nieuwDeelnameEUVerkiezingenRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderDeelnameEUVerkiezingen(actie);
    }

    /**
     * Start een nieuw Persoonskaart record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Persoonskaart groep builder
     */
    public PersoonHisVolledigImplBuilderPersoonskaart nieuwPersoonskaartRecord(final Integer datumRegistratie) {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwPersoonskaartRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Persoonskaart record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Persoonskaart groep builder
     */
    public PersoonHisVolledigImplBuilderPersoonskaart nieuwPersoonskaartRecord(final ActieModel actie) {
        return new PersoonHisVolledigImplBuilderPersoonskaart(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PersoonHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Voeg een Persoon \ Voornaam toe. Zet tevens de back-reference van Persoon \ Voornaam.
     *
     * @param persoonVoornaam een Persoon \ Voornaam
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonVoornaamToe(final PersoonVoornaamHisVolledigImpl persoonVoornaam) {
        this.hisVolledigImpl.getVoornamen().add(persoonVoornaam);
        ReflectionTestUtils.setField(persoonVoornaam, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Persoon \ Geslachtsnaamcomponent toe. Zet tevens de back-reference van Persoon \ Geslachtsnaamcomponent.
     *
     * @param persoonGeslachtsnaamcomponent een Persoon \ Geslachtsnaamcomponent
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonGeslachtsnaamcomponentToe(
        final PersoonGeslachtsnaamcomponentHisVolledigImpl persoonGeslachtsnaamcomponent)
    {
        this.hisVolledigImpl.getGeslachtsnaamcomponenten().add(persoonGeslachtsnaamcomponent);
        ReflectionTestUtils.setField(persoonGeslachtsnaamcomponent, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Persoon \ Verificatie toe. Zet tevens de back-reference van Persoon \ Verificatie.
     *
     * @param persoonVerificatie een Persoon \ Verificatie
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonVerificatieToe(final PersoonVerificatieHisVolledigImpl persoonVerificatie) {
        this.hisVolledigImpl.getVerificaties().add(persoonVerificatie);
        ReflectionTestUtils.setField(persoonVerificatie, "geverifieerde", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Persoon \ Nationaliteit toe. Zet tevens de back-reference van Persoon \ Nationaliteit.
     *
     * @param persoonNationaliteit een Persoon \ Nationaliteit
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonNationaliteitToe(final PersoonNationaliteitHisVolledigImpl persoonNationaliteit) {
        this.hisVolledigImpl.getNationaliteiten().add(persoonNationaliteit);
        ReflectionTestUtils.setField(persoonNationaliteit, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Persoon \ Adres toe. Zet tevens de back-reference van Persoon \ Adres.
     *
     * @param persoonAdres een Persoon \ Adres
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonAdresToe(final PersoonAdresHisVolledigImpl persoonAdres) {
        this.hisVolledigImpl.getAdressen().add(persoonAdres);
        ReflectionTestUtils.setField(persoonAdres, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Persoon \ Reisdocument toe. Zet tevens de back-reference van Persoon \ Reisdocument.
     *
     * @param persoonReisdocument een Persoon \ Reisdocument
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonReisdocumentToe(final PersoonReisdocumentHisVolledigImpl persoonReisdocument) {
        this.hisVolledigImpl.getReisdocumenten().add(persoonReisdocument);
        ReflectionTestUtils.setField(persoonReisdocument, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Betrokkenheid toe. Zet tevens de back-reference van Betrokkenheid.
     *
     * @param betrokkenheid een Betrokkenheid
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegBetrokkenheidToe(final BetrokkenheidHisVolledigImpl betrokkenheid) {
        this.hisVolledigImpl.getBetrokkenheden().add(betrokkenheid);
        ReflectionTestUtils.setField(betrokkenheid, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Persoon \ Onderzoek toe. Zet tevens de back-reference van Persoon \ Onderzoek.
     *
     * @param persoonOnderzoek een Persoon \ Onderzoek
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonOnderzoekToe(final PersoonOnderzoekHisVolledigImpl persoonOnderzoek) {
        this.hisVolledigImpl.getOnderzoeken().add(persoonOnderzoek);
        ReflectionTestUtils.setField(persoonOnderzoek, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Persoon \ Verstrekkingsbeperking toe. Zet tevens de back-reference van Persoon \ Verstrekkingsbeperking.
     *
     * @param persoonVerstrekkingsbeperking een Persoon \ Verstrekkingsbeperking
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonVerstrekkingsbeperkingToe(
        final PersoonVerstrekkingsbeperkingHisVolledigImpl persoonVerstrekkingsbeperking)
    {
        this.hisVolledigImpl.getVerstrekkingsbeperkingen().add(persoonVerstrekkingsbeperking);
        ReflectionTestUtils.setField(persoonVerstrekkingsbeperking, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Persoon \ Afnemerindicatie toe. Zet tevens de back-reference van Persoon \ Afnemerindicatie.
     *
     * @param persoonAfnemerindicatie een Persoon \ Afnemerindicatie
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonAfnemerindicatieToe(final PersoonAfnemerindicatieHisVolledigImpl persoonAfnemerindicatie) {
        this.hisVolledigImpl.getAfnemerindicaties().add(persoonAfnemerindicatie);
        ReflectionTestUtils.setField(persoonAfnemerindicatie, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een indicatie Derde heeft gezag? toe. Zet tevens de back-reference.
     *
     * @param indicatieDerdeHeeftGezag indicatie Derde heeft gezag?
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonIndicatieDerdeHeeftGezagToe(
        final PersoonIndicatieDerdeHeeftGezagHisVolledigImpl indicatieDerdeHeeftGezag)
    {
        this.hisVolledigImpl.setIndicatieDerdeHeeftGezag(indicatieDerdeHeeftGezag);
        ReflectionTestUtils.setField(indicatieDerdeHeeftGezag, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een indicatie Onder curatele? toe. Zet tevens de back-reference.
     *
     * @param indicatieOnderCuratele indicatie Onder curatele?
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonIndicatieOnderCurateleToe(final PersoonIndicatieOnderCurateleHisVolledigImpl indicatieOnderCuratele) {
        this.hisVolledigImpl.setIndicatieOnderCuratele(indicatieOnderCuratele);
        ReflectionTestUtils.setField(indicatieOnderCuratele, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een indicatie Volledige verstrekkingsbeperking? toe. Zet tevens de back-reference.
     *
     * @param indicatieVolledigeVerstrekkingsbeperking indicatie Volledige verstrekkingsbeperking?
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonIndicatieVolledigeVerstrekkingsbeperkingToe(
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl indicatieVolledigeVerstrekkingsbeperking)
    {
        this.hisVolledigImpl.setIndicatieVolledigeVerstrekkingsbeperking(indicatieVolledigeVerstrekkingsbeperking);
        ReflectionTestUtils.setField(indicatieVolledigeVerstrekkingsbeperking, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een indicatie Vastgesteld niet Nederlander? toe. Zet tevens de back-reference.
     *
     * @param indicatieVastgesteldNietNederlander indicatie Vastgesteld niet Nederlander?
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonIndicatieVastgesteldNietNederlanderToe(
        final PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl indicatieVastgesteldNietNederlander)
    {
        this.hisVolledigImpl.setIndicatieVastgesteldNietNederlander(indicatieVastgesteldNietNederlander);
        ReflectionTestUtils.setField(indicatieVastgesteldNietNederlander, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een indicatie Behandeld als Nederlander? toe. Zet tevens de back-reference.
     *
     * @param indicatieBehandeldAlsNederlander indicatie Behandeld als Nederlander?
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonIndicatieBehandeldAlsNederlanderToe(
        final PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl indicatieBehandeldAlsNederlander)
    {
        this.hisVolledigImpl.setIndicatieBehandeldAlsNederlander(indicatieBehandeldAlsNederlander);
        ReflectionTestUtils.setField(indicatieBehandeldAlsNederlander, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een indicatie Signalering met betrekking tot verstrekken reisdocument? toe. Zet tevens de back-reference.
     *
     * @param indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument indicatie Signalering met betrekking tot
     *            verstrekken reisdocument?
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentToe(
        final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument)
    {
        this.hisVolledigImpl.setIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument);
        ReflectionTestUtils.setField(indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een indicatie Staatloos? toe. Zet tevens de back-reference.
     *
     * @param indicatieStaatloos indicatie Staatloos?
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonIndicatieStaatloosToe(final PersoonIndicatieStaatloosHisVolledigImpl indicatieStaatloos) {
        this.hisVolledigImpl.setIndicatieStaatloos(indicatieStaatloos);
        ReflectionTestUtils.setField(indicatieStaatloos, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een indicatie Bijzondere verblijfsrechtelijke positie? toe. Zet tevens de back-reference.
     *
     * @param indicatieBijzondereVerblijfsrechtelijkePositie indicatie Bijzondere verblijfsrechtelijke positie?
     * @return his volledig builder
     */
    public PersoonHisVolledigImplBuilder voegPersoonIndicatieBijzondereVerblijfsrechtelijkePositieToe(
        final PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl indicatieBijzondereVerblijfsrechtelijkePositie)
    {
        this.hisVolledigImpl.setIndicatieBijzondereVerblijfsrechtelijkePositie(indicatieBijzondereVerblijfsrechtelijkePositie);
        ReflectionTestUtils.setField(indicatieBijzondereVerblijfsrechtelijkePositie, "persoon", this.hisVolledigImpl);
        return this;
    }

    /**
     * Inner klasse builder voor de groep Afgeleid administratief
     *
     */
    public class PersoonHisVolledigImplBuilderAfgeleidAdministratief {

        private ActieModel actie;
        private PersoonAfgeleidAdministratiefGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderAfgeleidAdministratief(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonAfgeleidAdministratiefGroepBericht();
        }

        /**
         * Geef attribuut Administratieve handeling een waarde.
         *
         * @param administratieveHandeling Administratieve handeling van Afgeleid administratief
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderAfgeleidAdministratief administratieveHandeling(final AdministratieveHandelingBericht administratieveHandeling)
        {
            this.bericht.setAdministratieveHandeling(administratieveHandeling);
            return this;
        }

        /**
         * Geef attribuut Tijdstip laatste wijziging een waarde.
         *
         * @param tijdstipLaatsteWijziging Tijdstip laatste wijziging van Afgeleid administratief
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderAfgeleidAdministratief tijdstipLaatsteWijziging(final Date tijdstipLaatsteWijziging) {
            this.bericht.setTijdstipLaatsteWijziging(new DatumTijdAttribuut(tijdstipLaatsteWijziging));
            return this;
        }

        /**
         * Geef attribuut Tijdstip laatste wijziging een waarde.
         *
         * @param tijdstipLaatsteWijziging Tijdstip laatste wijziging van Afgeleid administratief
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderAfgeleidAdministratief tijdstipLaatsteWijziging(final DatumTijdAttribuut tijdstipLaatsteWijziging) {
            this.bericht.setTijdstipLaatsteWijziging(tijdstipLaatsteWijziging);
            return this;
        }

        /**
         * Geef attribuut Sorteervolgorde een waarde.
         *
         * @param sorteervolgorde Sorteervolgorde van Afgeleid administratief
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderAfgeleidAdministratief sorteervolgorde(final Byte sorteervolgorde) {
            this.bericht.setSorteervolgorde(new SorteervolgordeAttribuut(sorteervolgorde));
            return this;
        }

        /**
         * Geef attribuut Sorteervolgorde een waarde.
         *
         * @param sorteervolgorde Sorteervolgorde van Afgeleid administratief
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderAfgeleidAdministratief sorteervolgorde(final SorteervolgordeAttribuut sorteervolgorde) {
            this.bericht.setSorteervolgorde(sorteervolgorde);
            return this;
        }

        /**
         * Geef attribuut Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig? een waarde.
         *
         * @param indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig Onverwerkt bijhoudingsvoorstel
         *            niet-ingezetene aanwezig? van Afgeleid administratief
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderAfgeleidAdministratief indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(
            final Boolean indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig)
        {
            this.bericht.setIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(new JaNeeAttribuut(
                indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig));
            return this;
        }

        /**
         * Geef attribuut Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig? een waarde.
         *
         * @param indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig Onverwerkt bijhoudingsvoorstel
         *            niet-ingezetene aanwezig? van Afgeleid administratief
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderAfgeleidAdministratief indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(
            final JaNeeAttribuut indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig)
        {
            this.bericht.setIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig);
            return this;
        }

        /**
         * Geef attribuut Tijdstip laatste wijziging GBA-systematiek een waarde.
         *
         * @param tijdstipLaatsteWijzigingGBASystematiek Tijdstip laatste wijziging GBA-systematiek van Afgeleid
         *            administratief
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderAfgeleidAdministratief tijdstipLaatsteWijzigingGBASystematiek(final Date tijdstipLaatsteWijzigingGBASystematiek)
        {
            this.bericht.setTijdstipLaatsteWijzigingGBASystematiek(new DatumTijdAttribuut(tijdstipLaatsteWijzigingGBASystematiek));
            return this;
        }

        /**
         * Geef attribuut Tijdstip laatste wijziging GBA-systematiek een waarde.
         *
         * @param tijdstipLaatsteWijzigingGBASystematiek Tijdstip laatste wijziging GBA-systematiek van Afgeleid
         *            administratief
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderAfgeleidAdministratief tijdstipLaatsteWijzigingGBASystematiek(
            final DatumTijdAttribuut tijdstipLaatsteWijzigingGBASystematiek)
        {
            this.bericht.setTijdstipLaatsteWijzigingGBASystematiek(tijdstipLaatsteWijzigingGBASystematiek);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonAfgeleidAdministratiefModel record = new HisPersoonAfgeleidAdministratiefModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonAfgeleidAdministratiefModel record = new HisPersoonAfgeleidAdministratiefModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonAfgeleidAdministratiefModel record) {
            if (record.getTijdstipLaatsteWijziging() != null) {
                record.getTijdstipLaatsteWijziging().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getSorteervolgorde() != null) {
                record.getSorteervolgorde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig() != null) {
                record.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getTijdstipLaatsteWijzigingGBASystematiek() != null) {
                record.getTijdstipLaatsteWijzigingGBASystematiek().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Identificatienummers
     *
     */
    public class PersoonHisVolledigImplBuilderIdentificatienummers {

        private ActieModel actie;
        private PersoonIdentificatienummersGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderIdentificatienummers(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonIdentificatienummersGroepBericht();
        }

        /**
         * Geef attribuut Burgerservicenummer een waarde.
         *
         * @param burgerservicenummer Burgerservicenummer van Identificatienummers
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderIdentificatienummers burgerservicenummer(final Integer burgerservicenummer) {
            this.bericht.setBurgerservicenummer(new BurgerservicenummerAttribuut(burgerservicenummer));
            return this;
        }

        /**
         * Geef attribuut Burgerservicenummer een waarde.
         *
         * @param burgerservicenummer Burgerservicenummer van Identificatienummers
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderIdentificatienummers burgerservicenummer(final BurgerservicenummerAttribuut burgerservicenummer) {
            this.bericht.setBurgerservicenummer(burgerservicenummer);
            return this;
        }

        /**
         * Geef attribuut Administratienummer een waarde.
         *
         * @param administratienummer Administratienummer van Identificatienummers
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderIdentificatienummers administratienummer(final Long administratienummer) {
            this.bericht.setAdministratienummer(new AdministratienummerAttribuut(administratienummer));
            return this;
        }

        /**
         * Geef attribuut Administratienummer een waarde.
         *
         * @param administratienummer Administratienummer van Identificatienummers
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderIdentificatienummers administratienummer(final AdministratienummerAttribuut administratienummer) {
            this.bericht.setAdministratienummer(administratienummer);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonIdentificatienummersModel record = new HisPersoonIdentificatienummersModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonIdentificatienummersHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonIdentificatienummersModel record = new HisPersoonIdentificatienummersModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonIdentificatienummersHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonIdentificatienummersModel record) {
            if (record.getBurgerservicenummer() != null) {
                record.getBurgerservicenummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getAdministratienummer() != null) {
                record.getAdministratienummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Samengestelde naam
     *
     */
    public class PersoonHisVolledigImplBuilderSamengesteldeNaam {

        private ActieModel actie;
        private PersoonSamengesteldeNaamGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonSamengesteldeNaamGroepBericht();
        }

        /**
         * Geef attribuut Afgeleid? een waarde.
         *
         * @param indicatieAfgeleid Afgeleid? van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam indicatieAfgeleid(final Boolean indicatieAfgeleid) {
            this.bericht.setIndicatieAfgeleid(new JaNeeAttribuut(indicatieAfgeleid));
            return this;
        }

        /**
         * Geef attribuut Afgeleid? een waarde.
         *
         * @param indicatieAfgeleid Afgeleid? van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam indicatieAfgeleid(final JaNeeAttribuut indicatieAfgeleid) {
            this.bericht.setIndicatieAfgeleid(indicatieAfgeleid);
            return this;
        }

        /**
         * Geef attribuut Namenreeks? een waarde.
         *
         * @param indicatieNamenreeks Namenreeks? van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam indicatieNamenreeks(final Boolean indicatieNamenreeks) {
            this.bericht.setIndicatieNamenreeks(new JaNeeAttribuut(indicatieNamenreeks));
            return this;
        }

        /**
         * Geef attribuut Namenreeks? een waarde.
         *
         * @param indicatieNamenreeks Namenreeks? van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam indicatieNamenreeks(final JaNeeAttribuut indicatieNamenreeks) {
            this.bericht.setIndicatieNamenreeks(indicatieNamenreeks);
            return this;
        }

        /**
         * Geef attribuut Predicaat een waarde.
         *
         * @param code Predicaat van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam predicaat(final String code) {
            this.bericht.setPredicaat(new PredicaatAttribuut(
                StamgegevenBuilder.bouwDynamischStamgegeven(Predicaat.class, new PredicaatCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Predicaat een waarde.
         *
         * @param code Predicaat van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam predicaat(final PredicaatCodeAttribuut code) {
            this.bericht.setPredicaat(new PredicaatAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Predicaat.class, code)));
            return this;
        }

        /**
         * Geef attribuut Predicaat een waarde.
         *
         * @param predicaat Predicaat van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam predicaat(final Predicaat predicaat) {
            this.bericht.setPredicaat(new PredicaatAttribuut(predicaat));
            return this;
        }

        /**
         * Geef attribuut Voornamen een waarde.
         *
         * @param voornamen Voornamen van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam voornamen(final String voornamen) {
            this.bericht.setVoornamen(new VoornamenAttribuut(voornamen));
            return this;
        }

        /**
         * Geef attribuut Voornamen een waarde.
         *
         * @param voornamen Voornamen van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam voornamen(final VoornamenAttribuut voornamen) {
            this.bericht.setVoornamen(voornamen);
            return this;
        }

        /**
         * Geef attribuut Adellijke titel een waarde.
         *
         * @param code Adellijke titel van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam adellijkeTitel(final String code) {
            this.bericht.setAdellijkeTitel(new AdellijkeTitelAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                AdellijkeTitel.class,
                new AdellijkeTitelCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Adellijke titel een waarde.
         *
         * @param code Adellijke titel van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam adellijkeTitel(final AdellijkeTitelCodeAttribuut code) {
            this.bericht.setAdellijkeTitel(new AdellijkeTitelAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(AdellijkeTitel.class, code)));
            return this;
        }

        /**
         * Geef attribuut Adellijke titel een waarde.
         *
         * @param adellijkeTitel Adellijke titel van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam adellijkeTitel(final AdellijkeTitel adellijkeTitel) {
            this.bericht.setAdellijkeTitel(new AdellijkeTitelAttribuut(adellijkeTitel));
            return this;
        }

        /**
         * Geef attribuut Voorvoegsel een waarde.
         *
         * @param voorvoegsel Voorvoegsel van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam voorvoegsel(final String voorvoegsel) {
            this.bericht.setVoorvoegsel(new VoorvoegselAttribuut(voorvoegsel));
            return this;
        }

        /**
         * Geef attribuut Voorvoegsel een waarde.
         *
         * @param voorvoegsel Voorvoegsel van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam voorvoegsel(final VoorvoegselAttribuut voorvoegsel) {
            this.bericht.setVoorvoegsel(voorvoegsel);
            return this;
        }

        /**
         * Geef attribuut Scheidingsteken een waarde.
         *
         * @param scheidingsteken Scheidingsteken van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam scheidingsteken(final String scheidingsteken) {
            this.bericht.setScheidingsteken(new ScheidingstekenAttribuut(scheidingsteken));
            return this;
        }

        /**
         * Geef attribuut Scheidingsteken een waarde.
         *
         * @param scheidingsteken Scheidingsteken van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam scheidingsteken(final ScheidingstekenAttribuut scheidingsteken) {
            this.bericht.setScheidingsteken(scheidingsteken);
            return this;
        }

        /**
         * Geef attribuut Geslachtsnaamstam een waarde.
         *
         * @param geslachtsnaamstam Geslachtsnaamstam van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam geslachtsnaamstam(final String geslachtsnaamstam) {
            this.bericht.setGeslachtsnaamstam(new GeslachtsnaamstamAttribuut(geslachtsnaamstam));
            return this;
        }

        /**
         * Geef attribuut Geslachtsnaamstam een waarde.
         *
         * @param geslachtsnaamstam Geslachtsnaamstam van Samengestelde naam
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderSamengesteldeNaam geslachtsnaamstam(final GeslachtsnaamstamAttribuut geslachtsnaamstam) {
            this.bericht.setGeslachtsnaamstam(geslachtsnaamstam);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonSamengesteldeNaamModel record = new HisPersoonSamengesteldeNaamModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonSamengesteldeNaamHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonSamengesteldeNaamModel record = new HisPersoonSamengesteldeNaamModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonSamengesteldeNaamHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonSamengesteldeNaamModel record) {
            if (record.getIndicatieAfgeleid() != null) {
                record.getIndicatieAfgeleid().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getIndicatieNamenreeks() != null) {
                record.getIndicatieNamenreeks().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getPredicaat() != null) {
                record.getPredicaat().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getVoornamen() != null) {
                record.getVoornamen().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getAdellijkeTitel() != null) {
                record.getAdellijkeTitel().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getVoorvoegsel() != null) {
                record.getVoorvoegsel().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getScheidingsteken() != null) {
                record.getScheidingsteken().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getGeslachtsnaamstam() != null) {
                record.getGeslachtsnaamstam().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Geboorte
     *
     */
    public class PersoonHisVolledigImplBuilderGeboorte {

        private ActieModel actie;
        private PersoonGeboorteGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderGeboorte(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonGeboorteGroepBericht();
        }

        /**
         * Geef attribuut Datum geboorte een waarde.
         *
         * @param datumGeboorte Datum geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte datumGeboorte(final Integer datumGeboorte) {
            this.bericht.setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(datumGeboorte));
            return this;
        }

        /**
         * Geef attribuut Datum geboorte een waarde.
         *
         * @param datumGeboorte Datum geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte datumGeboorte(final DatumEvtDeelsOnbekendAttribuut datumGeboorte) {
            this.bericht.setDatumGeboorte(datumGeboorte);
            return this;
        }

        /**
         * Geef attribuut Gemeente geboorte een waarde.
         *
         * @param code Gemeente geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte gemeenteGeboorte(final Short code) {
            this.bericht.setGemeenteGeboorte(new GemeenteAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Gemeente.class, new GemeenteCodeAttribuut(
                code))));
            return this;
        }

        /**
         * Geef attribuut Gemeente geboorte een waarde.
         *
         * @param code Gemeente geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte gemeenteGeboorte(final GemeenteCodeAttribuut code) {
            this.bericht.setGemeenteGeboorte(new GemeenteAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Gemeente.class, code)));
            return this;
        }

        /**
         * Geef attribuut Gemeente geboorte een waarde.
         *
         * @param gemeenteGeboorte Gemeente geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte gemeenteGeboorte(final Gemeente gemeenteGeboorte) {
            this.bericht.setGemeenteGeboorte(new GemeenteAttribuut(gemeenteGeboorte));
            return this;
        }

        /**
         * Geef attribuut Woonplaatsnaam geboorte een waarde.
         *
         * @param woonplaatsnaamGeboorte Woonplaatsnaam geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte woonplaatsnaamGeboorte(final String woonplaatsnaamGeboorte) {
            this.bericht.setWoonplaatsnaamGeboorte(new NaamEnumeratiewaardeAttribuut(woonplaatsnaamGeboorte));
            return this;
        }

        /**
         * Geef attribuut Woonplaatsnaam geboorte een waarde.
         *
         * @param woonplaatsnaamGeboorte Woonplaatsnaam geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte woonplaatsnaamGeboorte(final NaamEnumeratiewaardeAttribuut woonplaatsnaamGeboorte) {
            this.bericht.setWoonplaatsnaamGeboorte(woonplaatsnaamGeboorte);
            return this;
        }

        /**
         * Geef attribuut Buitenlandse plaats geboorte een waarde.
         *
         * @param buitenlandsePlaatsGeboorte Buitenlandse plaats geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte buitenlandsePlaatsGeboorte(final String buitenlandsePlaatsGeboorte) {
            this.bericht.setBuitenlandsePlaatsGeboorte(new BuitenlandsePlaatsAttribuut(buitenlandsePlaatsGeboorte));
            return this;
        }

        /**
         * Geef attribuut Buitenlandse plaats geboorte een waarde.
         *
         * @param buitenlandsePlaatsGeboorte Buitenlandse plaats geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte buitenlandsePlaatsGeboorte(final BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte) {
            this.bericht.setBuitenlandsePlaatsGeboorte(buitenlandsePlaatsGeboorte);
            return this;
        }

        /**
         * Geef attribuut Buitenlandse regio geboorte een waarde.
         *
         * @param buitenlandseRegioGeboorte Buitenlandse regio geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte buitenlandseRegioGeboorte(final String buitenlandseRegioGeboorte) {
            this.bericht.setBuitenlandseRegioGeboorte(new BuitenlandseRegioAttribuut(buitenlandseRegioGeboorte));
            return this;
        }

        /**
         * Geef attribuut Buitenlandse regio geboorte een waarde.
         *
         * @param buitenlandseRegioGeboorte Buitenlandse regio geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte buitenlandseRegioGeboorte(final BuitenlandseRegioAttribuut buitenlandseRegioGeboorte) {
            this.bericht.setBuitenlandseRegioGeboorte(buitenlandseRegioGeboorte);
            return this;
        }

        /**
         * Geef attribuut Omschrijving locatie geboorte een waarde.
         *
         * @param omschrijvingLocatieGeboorte Omschrijving locatie geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte omschrijvingLocatieGeboorte(final String omschrijvingLocatieGeboorte) {
            this.bericht.setOmschrijvingLocatieGeboorte(new LocatieomschrijvingAttribuut(omschrijvingLocatieGeboorte));
            return this;
        }

        /**
         * Geef attribuut Omschrijving locatie geboorte een waarde.
         *
         * @param omschrijvingLocatieGeboorte Omschrijving locatie geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte omschrijvingLocatieGeboorte(final LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte) {
            this.bericht.setOmschrijvingLocatieGeboorte(omschrijvingLocatieGeboorte);
            return this;
        }

        /**
         * Geef attribuut Land/gebied geboorte een waarde.
         *
         * @param code Land/gebied geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte landGebiedGeboorte(final Short code) {
            this.bericht.setLandGebiedGeboorte(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                LandGebied.class,
                new LandGebiedCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Land/gebied geboorte een waarde.
         *
         * @param code Land/gebied geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte landGebiedGeboorte(final LandGebiedCodeAttribuut code) {
            this.bericht.setLandGebiedGeboorte(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(LandGebied.class, code)));
            return this;
        }

        /**
         * Geef attribuut Land/gebied geboorte een waarde.
         *
         * @param landGebiedGeboorte Land/gebied geboorte van Geboorte
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeboorte landGebiedGeboorte(final LandGebied landGebiedGeboorte) {
            this.bericht.setLandGebiedGeboorte(new LandGebiedAttribuut(landGebiedGeboorte));
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonGeboorteModel record = new HisPersoonGeboorteModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonGeboorteHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonGeboorteModel record = new HisPersoonGeboorteModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonGeboorteHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonGeboorteModel record) {
            if (record.getDatumGeboorte() != null) {
                record.getDatumGeboorte().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getGemeenteGeboorte() != null) {
                record.getGemeenteGeboorte().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getWoonplaatsnaamGeboorte() != null) {
                record.getWoonplaatsnaamGeboorte().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsePlaatsGeboorte() != null) {
                record.getBuitenlandsePlaatsGeboorte().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandseRegioGeboorte() != null) {
                record.getBuitenlandseRegioGeboorte().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getOmschrijvingLocatieGeboorte() != null) {
                record.getOmschrijvingLocatieGeboorte().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getLandGebiedGeboorte() != null) {
                record.getLandGebiedGeboorte().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Geslachtsaanduiding
     *
     */
    public class PersoonHisVolledigImplBuilderGeslachtsaanduiding {

        private ActieModel actie;
        private PersoonGeslachtsaanduidingGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderGeslachtsaanduiding(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonGeslachtsaanduidingGroepBericht();
        }

        /**
         * Geef attribuut Geslachtsaanduiding een waarde.
         *
         * @param geslachtsaanduiding Geslachtsaanduiding van Geslachtsaanduiding
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderGeslachtsaanduiding geslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
            this.bericht.setGeslachtsaanduiding(new GeslachtsaanduidingAttribuut(geslachtsaanduiding));
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonGeslachtsaanduidingModel record = new HisPersoonGeslachtsaanduidingModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonGeslachtsaanduidingHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonGeslachtsaanduidingModel record = new HisPersoonGeslachtsaanduidingModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonGeslachtsaanduidingHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonGeslachtsaanduidingModel record) {
            if (record.getGeslachtsaanduiding() != null) {
                record.getGeslachtsaanduiding().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Inschrijving
     *
     */
    public class PersoonHisVolledigImplBuilderInschrijving {

        private ActieModel actie;
        private PersoonInschrijvingGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderInschrijving(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonInschrijvingGroepBericht();
        }

        /**
         * Geef attribuut Datum inschrijving een waarde.
         *
         * @param datumInschrijving Datum inschrijving van Inschrijving
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderInschrijving datumInschrijving(final Integer datumInschrijving) {
            this.bericht.setDatumInschrijving(new DatumEvtDeelsOnbekendAttribuut(datumInschrijving));
            return this;
        }

        /**
         * Geef attribuut Datum inschrijving een waarde.
         *
         * @param datumInschrijving Datum inschrijving van Inschrijving
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderInschrijving datumInschrijving(final DatumEvtDeelsOnbekendAttribuut datumInschrijving) {
            this.bericht.setDatumInschrijving(datumInschrijving);
            return this;
        }

        /**
         * Geef attribuut Versienummer een waarde.
         *
         * @param versienummer Versienummer van Inschrijving
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderInschrijving versienummer(final Long versienummer) {
            this.bericht.setVersienummer(new VersienummerAttribuut(versienummer));
            return this;
        }

        /**
         * Geef attribuut Versienummer een waarde.
         *
         * @param versienummer Versienummer van Inschrijving
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderInschrijving versienummer(final VersienummerAttribuut versienummer) {
            this.bericht.setVersienummer(versienummer);
            return this;
        }

        /**
         * Geef attribuut Datumtijdstempel een waarde.
         *
         * @param datumtijdstempel Datumtijdstempel van Inschrijving
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderInschrijving datumtijdstempel(final Date datumtijdstempel) {
            this.bericht.setDatumtijdstempel(new DatumTijdAttribuut(datumtijdstempel));
            return this;
        }

        /**
         * Geef attribuut Datumtijdstempel een waarde.
         *
         * @param datumtijdstempel Datumtijdstempel van Inschrijving
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderInschrijving datumtijdstempel(final DatumTijdAttribuut datumtijdstempel) {
            this.bericht.setDatumtijdstempel(datumtijdstempel);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonInschrijvingModel record = new HisPersoonInschrijvingModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonInschrijvingHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonInschrijvingModel record = new HisPersoonInschrijvingModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonInschrijvingHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonInschrijvingModel record) {
            if (record.getDatumInschrijving() != null) {
                record.getDatumInschrijving().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getVersienummer() != null) {
                record.getVersienummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumtijdstempel() != null) {
                record.getDatumtijdstempel().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Nummerverwijzing
     *
     */
    public class PersoonHisVolledigImplBuilderNummerverwijzing {

        private ActieModel actie;
        private PersoonNummerverwijzingGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderNummerverwijzing(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonNummerverwijzingGroepBericht();
        }

        /**
         * Geef attribuut Vorige burgerservicenummer een waarde.
         *
         * @param vorigeBurgerservicenummer Vorige burgerservicenummer van Nummerverwijzing
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNummerverwijzing vorigeBurgerservicenummer(final Integer vorigeBurgerservicenummer) {
            this.bericht.setVorigeBurgerservicenummer(new BurgerservicenummerAttribuut(vorigeBurgerservicenummer));
            return this;
        }

        /**
         * Geef attribuut Vorige burgerservicenummer een waarde.
         *
         * @param vorigeBurgerservicenummer Vorige burgerservicenummer van Nummerverwijzing
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNummerverwijzing vorigeBurgerservicenummer(final BurgerservicenummerAttribuut vorigeBurgerservicenummer) {
            this.bericht.setVorigeBurgerservicenummer(vorigeBurgerservicenummer);
            return this;
        }

        /**
         * Geef attribuut Volgende burgerservicenummer een waarde.
         *
         * @param volgendeBurgerservicenummer Volgende burgerservicenummer van Nummerverwijzing
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNummerverwijzing volgendeBurgerservicenummer(final Integer volgendeBurgerservicenummer) {
            this.bericht.setVolgendeBurgerservicenummer(new BurgerservicenummerAttribuut(volgendeBurgerservicenummer));
            return this;
        }

        /**
         * Geef attribuut Volgende burgerservicenummer een waarde.
         *
         * @param volgendeBurgerservicenummer Volgende burgerservicenummer van Nummerverwijzing
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNummerverwijzing volgendeBurgerservicenummer(final BurgerservicenummerAttribuut volgendeBurgerservicenummer) {
            this.bericht.setVolgendeBurgerservicenummer(volgendeBurgerservicenummer);
            return this;
        }

        /**
         * Geef attribuut Vorige administratienummer een waarde.
         *
         * @param vorigeAdministratienummer Vorige administratienummer van Nummerverwijzing
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNummerverwijzing vorigeAdministratienummer(final Long vorigeAdministratienummer) {
            this.bericht.setVorigeAdministratienummer(new AdministratienummerAttribuut(vorigeAdministratienummer));
            return this;
        }

        /**
         * Geef attribuut Vorige administratienummer een waarde.
         *
         * @param vorigeAdministratienummer Vorige administratienummer van Nummerverwijzing
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNummerverwijzing vorigeAdministratienummer(final AdministratienummerAttribuut vorigeAdministratienummer) {
            this.bericht.setVorigeAdministratienummer(vorigeAdministratienummer);
            return this;
        }

        /**
         * Geef attribuut Volgende administratienummer een waarde.
         *
         * @param volgendeAdministratienummer Volgende administratienummer van Nummerverwijzing
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNummerverwijzing volgendeAdministratienummer(final Long volgendeAdministratienummer) {
            this.bericht.setVolgendeAdministratienummer(new AdministratienummerAttribuut(volgendeAdministratienummer));
            return this;
        }

        /**
         * Geef attribuut Volgende administratienummer een waarde.
         *
         * @param volgendeAdministratienummer Volgende administratienummer van Nummerverwijzing
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNummerverwijzing volgendeAdministratienummer(final AdministratienummerAttribuut volgendeAdministratienummer) {
            this.bericht.setVolgendeAdministratienummer(volgendeAdministratienummer);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonNummerverwijzingModel record = new HisPersoonNummerverwijzingModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonNummerverwijzingHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonNummerverwijzingModel record = new HisPersoonNummerverwijzingModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonNummerverwijzingHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonNummerverwijzingModel record) {
            if (record.getVorigeBurgerservicenummer() != null) {
                record.getVorigeBurgerservicenummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getVolgendeBurgerservicenummer() != null) {
                record.getVolgendeBurgerservicenummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getVorigeAdministratienummer() != null) {
                record.getVorigeAdministratienummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getVolgendeAdministratienummer() != null) {
                record.getVolgendeAdministratienummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Bijhouding
     *
     */
    public class PersoonHisVolledigImplBuilderBijhouding {

        private ActieModel actie;
        private PersoonBijhoudingGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderBijhouding(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonBijhoudingGroepBericht();
        }

        /**
         * Geef attribuut Bijhoudingspartij een waarde.
         *
         * @param code Bijhoudingspartij van Bijhouding
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderBijhouding bijhoudingspartij(final Integer code) {
            this.bericht.setBijhoudingspartij(new PartijAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Partij.class, new PartijCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Bijhoudingspartij een waarde.
         *
         * @param code Bijhoudingspartij van Bijhouding
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderBijhouding bijhoudingspartij(final PartijCodeAttribuut code) {
            this.bericht.setBijhoudingspartij(new PartijAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Partij.class, code)));
            return this;
        }

        /**
         * Geef attribuut Bijhoudingspartij een waarde.
         *
         * @param bijhoudingspartij Bijhoudingspartij van Bijhouding
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderBijhouding bijhoudingspartij(final Partij bijhoudingspartij) {
            this.bericht.setBijhoudingspartij(new PartijAttribuut(bijhoudingspartij));
            return this;
        }

        /**
         * Geef attribuut Bijhoudingsaard een waarde.
         *
         * @param bijhoudingsaard Bijhoudingsaard van Bijhouding
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderBijhouding bijhoudingsaard(final Bijhoudingsaard bijhoudingsaard) {
            this.bericht.setBijhoudingsaard(new BijhoudingsaardAttribuut(bijhoudingsaard));
            return this;
        }

        /**
         * Geef attribuut Nadere bijhoudingsaard een waarde.
         *
         * @param nadereBijhoudingsaard Nadere bijhoudingsaard van Bijhouding
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderBijhouding nadereBijhoudingsaard(final NadereBijhoudingsaard nadereBijhoudingsaard) {
            this.bericht.setNadereBijhoudingsaard(new NadereBijhoudingsaardAttribuut(nadereBijhoudingsaard));
            return this;
        }

        /**
         * Geef attribuut Onverwerkt document aanwezig? een waarde.
         *
         * @param indicatieOnverwerktDocumentAanwezig Onverwerkt document aanwezig? van Bijhouding
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderBijhouding indicatieOnverwerktDocumentAanwezig(final Boolean indicatieOnverwerktDocumentAanwezig) {
            this.bericht.setIndicatieOnverwerktDocumentAanwezig(new JaNeeAttribuut(indicatieOnverwerktDocumentAanwezig));
            return this;
        }

        /**
         * Geef attribuut Onverwerkt document aanwezig? een waarde.
         *
         * @param indicatieOnverwerktDocumentAanwezig Onverwerkt document aanwezig? van Bijhouding
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderBijhouding indicatieOnverwerktDocumentAanwezig(final JaNeeAttribuut indicatieOnverwerktDocumentAanwezig) {
            this.bericht.setIndicatieOnverwerktDocumentAanwezig(indicatieOnverwerktDocumentAanwezig);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonBijhoudingModel record = new HisPersoonBijhoudingModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonBijhoudingHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonBijhoudingModel record = new HisPersoonBijhoudingModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonBijhoudingHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonBijhoudingModel record) {
            if (record.getBijhoudingspartij() != null) {
                record.getBijhoudingspartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBijhoudingsaard() != null) {
                record.getBijhoudingsaard().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getNadereBijhoudingsaard() != null) {
                record.getNadereBijhoudingsaard().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getIndicatieOnverwerktDocumentAanwezig() != null) {
                record.getIndicatieOnverwerktDocumentAanwezig().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Overlijden
     *
     */
    public class PersoonHisVolledigImplBuilderOverlijden {

        private ActieModel actie;
        private PersoonOverlijdenGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderOverlijden(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonOverlijdenGroepBericht();
        }

        /**
         * Geef attribuut Datum overlijden een waarde.
         *
         * @param datumOverlijden Datum overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden datumOverlijden(final Integer datumOverlijden) {
            this.bericht.setDatumOverlijden(new DatumEvtDeelsOnbekendAttribuut(datumOverlijden));
            return this;
        }

        /**
         * Geef attribuut Datum overlijden een waarde.
         *
         * @param datumOverlijden Datum overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden datumOverlijden(final DatumEvtDeelsOnbekendAttribuut datumOverlijden) {
            this.bericht.setDatumOverlijden(datumOverlijden);
            return this;
        }

        /**
         * Geef attribuut Gemeente overlijden een waarde.
         *
         * @param code Gemeente overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden gemeenteOverlijden(final Short code) {
            this.bericht.setGemeenteOverlijden(new GemeenteAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                Gemeente.class,
                new GemeenteCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Gemeente overlijden een waarde.
         *
         * @param code Gemeente overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden gemeenteOverlijden(final GemeenteCodeAttribuut code) {
            this.bericht.setGemeenteOverlijden(new GemeenteAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Gemeente.class, code)));
            return this;
        }

        /**
         * Geef attribuut Gemeente overlijden een waarde.
         *
         * @param gemeenteOverlijden Gemeente overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden gemeenteOverlijden(final Gemeente gemeenteOverlijden) {
            this.bericht.setGemeenteOverlijden(new GemeenteAttribuut(gemeenteOverlijden));
            return this;
        }

        /**
         * Geef attribuut Woonplaatsnaam overlijden een waarde.
         *
         * @param woonplaatsnaamOverlijden Woonplaatsnaam overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden woonplaatsnaamOverlijden(final String woonplaatsnaamOverlijden) {
            this.bericht.setWoonplaatsnaamOverlijden(new NaamEnumeratiewaardeAttribuut(woonplaatsnaamOverlijden));
            return this;
        }

        /**
         * Geef attribuut Woonplaatsnaam overlijden een waarde.
         *
         * @param woonplaatsnaamOverlijden Woonplaatsnaam overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden woonplaatsnaamOverlijden(final NaamEnumeratiewaardeAttribuut woonplaatsnaamOverlijden) {
            this.bericht.setWoonplaatsnaamOverlijden(woonplaatsnaamOverlijden);
            return this;
        }

        /**
         * Geef attribuut Buitenlandse plaats overlijden een waarde.
         *
         * @param buitenlandsePlaatsOverlijden Buitenlandse plaats overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden buitenlandsePlaatsOverlijden(final String buitenlandsePlaatsOverlijden) {
            this.bericht.setBuitenlandsePlaatsOverlijden(new BuitenlandsePlaatsAttribuut(buitenlandsePlaatsOverlijden));
            return this;
        }

        /**
         * Geef attribuut Buitenlandse plaats overlijden een waarde.
         *
         * @param buitenlandsePlaatsOverlijden Buitenlandse plaats overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden buitenlandsePlaatsOverlijden(final BuitenlandsePlaatsAttribuut buitenlandsePlaatsOverlijden) {
            this.bericht.setBuitenlandsePlaatsOverlijden(buitenlandsePlaatsOverlijden);
            return this;
        }

        /**
         * Geef attribuut Buitenlandse regio overlijden een waarde.
         *
         * @param buitenlandseRegioOverlijden Buitenlandse regio overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden buitenlandseRegioOverlijden(final String buitenlandseRegioOverlijden) {
            this.bericht.setBuitenlandseRegioOverlijden(new BuitenlandseRegioAttribuut(buitenlandseRegioOverlijden));
            return this;
        }

        /**
         * Geef attribuut Buitenlandse regio overlijden een waarde.
         *
         * @param buitenlandseRegioOverlijden Buitenlandse regio overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden buitenlandseRegioOverlijden(final BuitenlandseRegioAttribuut buitenlandseRegioOverlijden) {
            this.bericht.setBuitenlandseRegioOverlijden(buitenlandseRegioOverlijden);
            return this;
        }

        /**
         * Geef attribuut Omschrijving locatie overlijden een waarde.
         *
         * @param omschrijvingLocatieOverlijden Omschrijving locatie overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden omschrijvingLocatieOverlijden(final String omschrijvingLocatieOverlijden) {
            this.bericht.setOmschrijvingLocatieOverlijden(new LocatieomschrijvingAttribuut(omschrijvingLocatieOverlijden));
            return this;
        }

        /**
         * Geef attribuut Omschrijving locatie overlijden een waarde.
         *
         * @param omschrijvingLocatieOverlijden Omschrijving locatie overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden omschrijvingLocatieOverlijden(final LocatieomschrijvingAttribuut omschrijvingLocatieOverlijden) {
            this.bericht.setOmschrijvingLocatieOverlijden(omschrijvingLocatieOverlijden);
            return this;
        }

        /**
         * Geef attribuut Land/gebied overlijden een waarde.
         *
         * @param code Land/gebied overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden landGebiedOverlijden(final Short code) {
            this.bericht.setLandGebiedOverlijden(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                LandGebied.class,
                new LandGebiedCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Land/gebied overlijden een waarde.
         *
         * @param code Land/gebied overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden landGebiedOverlijden(final LandGebiedCodeAttribuut code) {
            this.bericht.setLandGebiedOverlijden(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(LandGebied.class, code)));
            return this;
        }

        /**
         * Geef attribuut Land/gebied overlijden een waarde.
         *
         * @param landGebiedOverlijden Land/gebied overlijden van Overlijden
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderOverlijden landGebiedOverlijden(final LandGebied landGebiedOverlijden) {
            this.bericht.setLandGebiedOverlijden(new LandGebiedAttribuut(landGebiedOverlijden));
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonOverlijdenModel record = new HisPersoonOverlijdenModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonOverlijdenHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonOverlijdenModel record = new HisPersoonOverlijdenModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonOverlijdenHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonOverlijdenModel record) {
            if (record.getDatumOverlijden() != null) {
                record.getDatumOverlijden().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getGemeenteOverlijden() != null) {
                record.getGemeenteOverlijden().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getWoonplaatsnaamOverlijden() != null) {
                record.getWoonplaatsnaamOverlijden().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsePlaatsOverlijden() != null) {
                record.getBuitenlandsePlaatsOverlijden().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandseRegioOverlijden() != null) {
                record.getBuitenlandseRegioOverlijden().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getOmschrijvingLocatieOverlijden() != null) {
                record.getOmschrijvingLocatieOverlijden().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getLandGebiedOverlijden() != null) {
                record.getLandGebiedOverlijden().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Naamgebruik
     *
     */
    public class PersoonHisVolledigImplBuilderNaamgebruik {

        private ActieModel actie;
        private PersoonNaamgebruikGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderNaamgebruik(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonNaamgebruikGroepBericht();
        }

        /**
         * Geef attribuut Naamgebruik een waarde.
         *
         * @param naamgebruik Naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik naamgebruik(final Naamgebruik naamgebruik) {
            this.bericht.setNaamgebruik(new NaamgebruikAttribuut(naamgebruik));
            return this;
        }

        /**
         * Geef attribuut Naamgebruik afgeleid? een waarde.
         *
         * @param indicatieNaamgebruikAfgeleid Naamgebruik afgeleid? van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik indicatieNaamgebruikAfgeleid(final Boolean indicatieNaamgebruikAfgeleid) {
            this.bericht.setIndicatieNaamgebruikAfgeleid(new JaNeeAttribuut(indicatieNaamgebruikAfgeleid));
            return this;
        }

        /**
         * Geef attribuut Naamgebruik afgeleid? een waarde.
         *
         * @param indicatieNaamgebruikAfgeleid Naamgebruik afgeleid? van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik indicatieNaamgebruikAfgeleid(final JaNeeAttribuut indicatieNaamgebruikAfgeleid) {
            this.bericht.setIndicatieNaamgebruikAfgeleid(indicatieNaamgebruikAfgeleid);
            return this;
        }

        /**
         * Geef attribuut Predicaat naamgebruik een waarde.
         *
         * @param code Predicaat naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik predicaatNaamgebruik(final String code) {
            this.bericht.setPredicaatNaamgebruik(new PredicaatAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                Predicaat.class,
                new PredicaatCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Predicaat naamgebruik een waarde.
         *
         * @param code Predicaat naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik predicaatNaamgebruik(final PredicaatCodeAttribuut code) {
            this.bericht.setPredicaatNaamgebruik(new PredicaatAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Predicaat.class, code)));
            return this;
        }

        /**
         * Geef attribuut Predicaat naamgebruik een waarde.
         *
         * @param predicaatNaamgebruik Predicaat naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik predicaatNaamgebruik(final Predicaat predicaatNaamgebruik) {
            this.bericht.setPredicaatNaamgebruik(new PredicaatAttribuut(predicaatNaamgebruik));
            return this;
        }

        /**
         * Geef attribuut Voornamen naamgebruik een waarde.
         *
         * @param voornamenNaamgebruik Voornamen naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik voornamenNaamgebruik(final String voornamenNaamgebruik) {
            this.bericht.setVoornamenNaamgebruik(new VoornamenAttribuut(voornamenNaamgebruik));
            return this;
        }

        /**
         * Geef attribuut Voornamen naamgebruik een waarde.
         *
         * @param voornamenNaamgebruik Voornamen naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik voornamenNaamgebruik(final VoornamenAttribuut voornamenNaamgebruik) {
            this.bericht.setVoornamenNaamgebruik(voornamenNaamgebruik);
            return this;
        }

        /**
         * Geef attribuut Adellijke titel naamgebruik een waarde.
         *
         * @param code Adellijke titel naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik adellijkeTitelNaamgebruik(final String code) {
            this.bericht.setAdellijkeTitelNaamgebruik(new AdellijkeTitelAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                AdellijkeTitel.class,
                new AdellijkeTitelCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Adellijke titel naamgebruik een waarde.
         *
         * @param code Adellijke titel naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik adellijkeTitelNaamgebruik(final AdellijkeTitelCodeAttribuut code) {
            this.bericht.setAdellijkeTitelNaamgebruik(new AdellijkeTitelAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(AdellijkeTitel.class, code)));
            return this;
        }

        /**
         * Geef attribuut Adellijke titel naamgebruik een waarde.
         *
         * @param adellijkeTitelNaamgebruik Adellijke titel naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik adellijkeTitelNaamgebruik(final AdellijkeTitel adellijkeTitelNaamgebruik) {
            this.bericht.setAdellijkeTitelNaamgebruik(new AdellijkeTitelAttribuut(adellijkeTitelNaamgebruik));
            return this;
        }

        /**
         * Geef attribuut Voorvoegsel naamgebruik een waarde.
         *
         * @param voorvoegselNaamgebruik Voorvoegsel naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik voorvoegselNaamgebruik(final String voorvoegselNaamgebruik) {
            this.bericht.setVoorvoegselNaamgebruik(new VoorvoegselAttribuut(voorvoegselNaamgebruik));
            return this;
        }

        /**
         * Geef attribuut Voorvoegsel naamgebruik een waarde.
         *
         * @param voorvoegselNaamgebruik Voorvoegsel naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik voorvoegselNaamgebruik(final VoorvoegselAttribuut voorvoegselNaamgebruik) {
            this.bericht.setVoorvoegselNaamgebruik(voorvoegselNaamgebruik);
            return this;
        }

        /**
         * Geef attribuut Scheidingsteken naamgebruik een waarde.
         *
         * @param scheidingstekenNaamgebruik Scheidingsteken naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik scheidingstekenNaamgebruik(final String scheidingstekenNaamgebruik) {
            this.bericht.setScheidingstekenNaamgebruik(new ScheidingstekenAttribuut(scheidingstekenNaamgebruik));
            return this;
        }

        /**
         * Geef attribuut Scheidingsteken naamgebruik een waarde.
         *
         * @param scheidingstekenNaamgebruik Scheidingsteken naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik scheidingstekenNaamgebruik(final ScheidingstekenAttribuut scheidingstekenNaamgebruik) {
            this.bericht.setScheidingstekenNaamgebruik(scheidingstekenNaamgebruik);
            return this;
        }

        /**
         * Geef attribuut Geslachtsnaamstam naamgebruik een waarde.
         *
         * @param geslachtsnaamstamNaamgebruik Geslachtsnaamstam naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik geslachtsnaamstamNaamgebruik(final String geslachtsnaamstamNaamgebruik) {
            this.bericht.setGeslachtsnaamstamNaamgebruik(new GeslachtsnaamstamAttribuut(geslachtsnaamstamNaamgebruik));
            return this;
        }

        /**
         * Geef attribuut Geslachtsnaamstam naamgebruik een waarde.
         *
         * @param geslachtsnaamstamNaamgebruik Geslachtsnaamstam naamgebruik van Naamgebruik
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderNaamgebruik geslachtsnaamstamNaamgebruik(final GeslachtsnaamstamAttribuut geslachtsnaamstamNaamgebruik) {
            this.bericht.setGeslachtsnaamstamNaamgebruik(geslachtsnaamstamNaamgebruik);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonNaamgebruikModel record = new HisPersoonNaamgebruikModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonNaamgebruikHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonNaamgebruikModel record = new HisPersoonNaamgebruikModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonNaamgebruikHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonNaamgebruikModel record) {
            if (record.getNaamgebruik() != null) {
                record.getNaamgebruik().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getIndicatieNaamgebruikAfgeleid() != null) {
                record.getIndicatieNaamgebruikAfgeleid().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getPredicaatNaamgebruik() != null) {
                record.getPredicaatNaamgebruik().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getVoornamenNaamgebruik() != null) {
                record.getVoornamenNaamgebruik().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getAdellijkeTitelNaamgebruik() != null) {
                record.getAdellijkeTitelNaamgebruik().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getVoorvoegselNaamgebruik() != null) {
                record.getVoorvoegselNaamgebruik().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getScheidingstekenNaamgebruik() != null) {
                record.getScheidingstekenNaamgebruik().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getGeslachtsnaamstamNaamgebruik() != null) {
                record.getGeslachtsnaamstamNaamgebruik().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Migratie
     *
     */
    public class PersoonHisVolledigImplBuilderMigratie {

        private ActieModel actie;
        private PersoonMigratieGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderMigratie(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonMigratieGroepBericht();
        }

        /**
         * Geef attribuut Soort migratie een waarde.
         *
         * @param soortMigratie Soort migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie soortMigratie(final SoortMigratie soortMigratie) {
            this.bericht.setSoortMigratie(new SoortMigratieAttribuut(soortMigratie));
            return this;
        }

        /**
         * Geef attribuut Reden wijziging migratie een waarde.
         *
         * @param code Reden wijziging migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie redenWijzigingMigratie(final String code) {
            this.bericht.setRedenWijzigingMigratie(new RedenWijzigingVerblijfAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                RedenWijzigingVerblijf.class,
                new RedenWijzigingVerblijfCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Reden wijziging migratie een waarde.
         *
         * @param code Reden wijziging migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie redenWijzigingMigratie(final RedenWijzigingVerblijfCodeAttribuut code) {
            this.bericht.setRedenWijzigingMigratie(new RedenWijzigingVerblijfAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                RedenWijzigingVerblijf.class,
                code)));
            return this;
        }

        /**
         * Geef attribuut Reden wijziging migratie een waarde.
         *
         * @param redenWijzigingMigratie Reden wijziging migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie redenWijzigingMigratie(final RedenWijzigingVerblijf redenWijzigingMigratie) {
            this.bericht.setRedenWijzigingMigratie(new RedenWijzigingVerblijfAttribuut(redenWijzigingMigratie));
            return this;
        }

        /**
         * Geef attribuut Aangever migratie een waarde.
         *
         * @param code Aangever migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie aangeverMigratie(final String code) {
            this.bericht.setAangeverMigratie(new AangeverAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Aangever.class, new AangeverCodeAttribuut(
                code))));
            return this;
        }

        /**
         * Geef attribuut Aangever migratie een waarde.
         *
         * @param code Aangever migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie aangeverMigratie(final AangeverCodeAttribuut code) {
            this.bericht.setAangeverMigratie(new AangeverAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Aangever.class, code)));
            return this;
        }

        /**
         * Geef attribuut Aangever migratie een waarde.
         *
         * @param aangeverMigratie Aangever migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie aangeverMigratie(final Aangever aangeverMigratie) {
            this.bericht.setAangeverMigratie(new AangeverAttribuut(aangeverMigratie));
            return this;
        }

        /**
         * Geef attribuut Land/gebied migratie een waarde.
         *
         * @param code Land/gebied migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie landGebiedMigratie(final Short code) {
            this.bericht.setLandGebiedMigratie(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                LandGebied.class,
                new LandGebiedCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Land/gebied migratie een waarde.
         *
         * @param code Land/gebied migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie landGebiedMigratie(final LandGebiedCodeAttribuut code) {
            this.bericht.setLandGebiedMigratie(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(LandGebied.class, code)));
            return this;
        }

        /**
         * Geef attribuut Land/gebied migratie een waarde.
         *
         * @param landGebiedMigratie Land/gebied migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie landGebiedMigratie(final LandGebied landGebiedMigratie) {
            this.bericht.setLandGebiedMigratie(new LandGebiedAttribuut(landGebiedMigratie));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 1 migratie een waarde.
         *
         * @param buitenlandsAdresRegel1Migratie Buitenlands adres regel 1 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel1Migratie(final String buitenlandsAdresRegel1Migratie) {
            this.bericht.setBuitenlandsAdresRegel1Migratie(new AdresregelAttribuut(buitenlandsAdresRegel1Migratie));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 1 migratie een waarde.
         *
         * @param buitenlandsAdresRegel1Migratie Buitenlands adres regel 1 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel1Migratie(final AdresregelAttribuut buitenlandsAdresRegel1Migratie) {
            this.bericht.setBuitenlandsAdresRegel1Migratie(buitenlandsAdresRegel1Migratie);
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 2 migratie een waarde.
         *
         * @param buitenlandsAdresRegel2Migratie Buitenlands adres regel 2 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel2Migratie(final String buitenlandsAdresRegel2Migratie) {
            this.bericht.setBuitenlandsAdresRegel2Migratie(new AdresregelAttribuut(buitenlandsAdresRegel2Migratie));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 2 migratie een waarde.
         *
         * @param buitenlandsAdresRegel2Migratie Buitenlands adres regel 2 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel2Migratie(final AdresregelAttribuut buitenlandsAdresRegel2Migratie) {
            this.bericht.setBuitenlandsAdresRegel2Migratie(buitenlandsAdresRegel2Migratie);
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 3 migratie een waarde.
         *
         * @param buitenlandsAdresRegel3Migratie Buitenlands adres regel 3 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel3Migratie(final String buitenlandsAdresRegel3Migratie) {
            this.bericht.setBuitenlandsAdresRegel3Migratie(new AdresregelAttribuut(buitenlandsAdresRegel3Migratie));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 3 migratie een waarde.
         *
         * @param buitenlandsAdresRegel3Migratie Buitenlands adres regel 3 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel3Migratie(final AdresregelAttribuut buitenlandsAdresRegel3Migratie) {
            this.bericht.setBuitenlandsAdresRegel3Migratie(buitenlandsAdresRegel3Migratie);
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 4 migratie een waarde.
         *
         * @param buitenlandsAdresRegel4Migratie Buitenlands adres regel 4 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel4Migratie(final String buitenlandsAdresRegel4Migratie) {
            this.bericht.setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut(buitenlandsAdresRegel4Migratie));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 4 migratie een waarde.
         *
         * @param buitenlandsAdresRegel4Migratie Buitenlands adres regel 4 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel4Migratie(final AdresregelAttribuut buitenlandsAdresRegel4Migratie) {
            this.bericht.setBuitenlandsAdresRegel4Migratie(buitenlandsAdresRegel4Migratie);
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 5 migratie een waarde.
         *
         * @param buitenlandsAdresRegel5Migratie Buitenlands adres regel 5 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel5Migratie(final String buitenlandsAdresRegel5Migratie) {
            this.bericht.setBuitenlandsAdresRegel5Migratie(new AdresregelAttribuut(buitenlandsAdresRegel5Migratie));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 5 migratie een waarde.
         *
         * @param buitenlandsAdresRegel5Migratie Buitenlands adres regel 5 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel5Migratie(final AdresregelAttribuut buitenlandsAdresRegel5Migratie) {
            this.bericht.setBuitenlandsAdresRegel5Migratie(buitenlandsAdresRegel5Migratie);
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 6 migratie een waarde.
         *
         * @param buitenlandsAdresRegel6Migratie Buitenlands adres regel 6 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel6Migratie(final String buitenlandsAdresRegel6Migratie) {
            this.bericht.setBuitenlandsAdresRegel6Migratie(new AdresregelAttribuut(buitenlandsAdresRegel6Migratie));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 6 migratie een waarde.
         *
         * @param buitenlandsAdresRegel6Migratie Buitenlands adres regel 6 migratie van Migratie
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderMigratie buitenlandsAdresRegel6Migratie(final AdresregelAttribuut buitenlandsAdresRegel6Migratie) {
            this.bericht.setBuitenlandsAdresRegel6Migratie(buitenlandsAdresRegel6Migratie);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonMigratieModel record = new HisPersoonMigratieModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonMigratieHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonMigratieModel record = new HisPersoonMigratieModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonMigratieHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonMigratieModel record) {
            if (record.getSoortMigratie() != null) {
                record.getSoortMigratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getRedenWijzigingMigratie() != null) {
                record.getRedenWijzigingMigratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getAangeverMigratie() != null) {
                record.getAangeverMigratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getLandGebiedMigratie() != null) {
                record.getLandGebiedMigratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel1Migratie() != null) {
                record.getBuitenlandsAdresRegel1Migratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel2Migratie() != null) {
                record.getBuitenlandsAdresRegel2Migratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel3Migratie() != null) {
                record.getBuitenlandsAdresRegel3Migratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel4Migratie() != null) {
                record.getBuitenlandsAdresRegel4Migratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel5Migratie() != null) {
                record.getBuitenlandsAdresRegel5Migratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel6Migratie() != null) {
                record.getBuitenlandsAdresRegel6Migratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Verblijfsrecht
     *
     */
    public class PersoonHisVolledigImplBuilderVerblijfsrecht {

        private ActieModel actie;
        private PersoonVerblijfsrechtGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderVerblijfsrecht(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonVerblijfsrechtGroepBericht();
        }

        /**
         * Geef attribuut Aanduiding verblijfsrecht een waarde.
         *
         * @param code Aanduiding verblijfsrecht van Verblijfsrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderVerblijfsrecht aanduidingVerblijfsrecht(final Short code) {
            this.bericht.setAanduidingVerblijfsrecht(new AanduidingVerblijfsrechtAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                AanduidingVerblijfsrecht.class,
                new AanduidingVerblijfsrechtCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Aanduiding verblijfsrecht een waarde.
         *
         * @param code Aanduiding verblijfsrecht van Verblijfsrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderVerblijfsrecht aanduidingVerblijfsrecht(final AanduidingVerblijfsrechtCodeAttribuut code) {
            this.bericht.setAanduidingVerblijfsrecht(new AanduidingVerblijfsrechtAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                AanduidingVerblijfsrecht.class,
                code)));
            return this;
        }

        /**
         * Geef attribuut Aanduiding verblijfsrecht een waarde.
         *
         * @param aanduidingVerblijfsrecht Aanduiding verblijfsrecht van Verblijfsrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderVerblijfsrecht aanduidingVerblijfsrecht(final AanduidingVerblijfsrecht aanduidingVerblijfsrecht) {
            this.bericht.setAanduidingVerblijfsrecht(new AanduidingVerblijfsrechtAttribuut(aanduidingVerblijfsrecht));
            return this;
        }

        /**
         * Geef attribuut Datum aanvang verblijfsrecht een waarde.
         *
         * @param datumAanvangVerblijfsrecht Datum aanvang verblijfsrecht van Verblijfsrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderVerblijfsrecht datumAanvangVerblijfsrecht(final Integer datumAanvangVerblijfsrecht) {
            this.bericht.setDatumAanvangVerblijfsrecht(new DatumEvtDeelsOnbekendAttribuut(datumAanvangVerblijfsrecht));
            return this;
        }

        /**
         * Geef attribuut Datum aanvang verblijfsrecht een waarde.
         *
         * @param datumAanvangVerblijfsrecht Datum aanvang verblijfsrecht van Verblijfsrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderVerblijfsrecht datumAanvangVerblijfsrecht(final DatumEvtDeelsOnbekendAttribuut datumAanvangVerblijfsrecht) {
            this.bericht.setDatumAanvangVerblijfsrecht(datumAanvangVerblijfsrecht);
            return this;
        }

        /**
         * Geef attribuut Datum mededeling verblijfsrecht een waarde.
         *
         * @param datumMededelingVerblijfsrecht Datum mededeling verblijfsrecht van Verblijfsrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderVerblijfsrecht datumMededelingVerblijfsrecht(final Integer datumMededelingVerblijfsrecht) {
            this.bericht.setDatumMededelingVerblijfsrecht(new DatumEvtDeelsOnbekendAttribuut(datumMededelingVerblijfsrecht));
            return this;
        }

        /**
         * Geef attribuut Datum mededeling verblijfsrecht een waarde.
         *
         * @param datumMededelingVerblijfsrecht Datum mededeling verblijfsrecht van Verblijfsrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderVerblijfsrecht datumMededelingVerblijfsrecht(final DatumEvtDeelsOnbekendAttribuut datumMededelingVerblijfsrecht)
        {
            this.bericht.setDatumMededelingVerblijfsrecht(datumMededelingVerblijfsrecht);
            return this;
        }

        /**
         * Geef attribuut Datum voorzien einde verblijfsrecht een waarde.
         *
         * @param datumVoorzienEindeVerblijfsrecht Datum voorzien einde verblijfsrecht van Verblijfsrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderVerblijfsrecht datumVoorzienEindeVerblijfsrecht(final Integer datumVoorzienEindeVerblijfsrecht) {
            this.bericht.setDatumVoorzienEindeVerblijfsrecht(new DatumEvtDeelsOnbekendAttribuut(datumVoorzienEindeVerblijfsrecht));
            return this;
        }

        /**
         * Geef attribuut Datum voorzien einde verblijfsrecht een waarde.
         *
         * @param datumVoorzienEindeVerblijfsrecht Datum voorzien einde verblijfsrecht van Verblijfsrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderVerblijfsrecht datumVoorzienEindeVerblijfsrecht(
            final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeVerblijfsrecht)
        {
            this.bericht.setDatumVoorzienEindeVerblijfsrecht(datumVoorzienEindeVerblijfsrecht);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonVerblijfsrechtModel record = new HisPersoonVerblijfsrechtModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonVerblijfsrechtHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonVerblijfsrechtModel record = new HisPersoonVerblijfsrechtModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonVerblijfsrechtHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonVerblijfsrechtModel record) {
            if (record.getAanduidingVerblijfsrecht() != null) {
                record.getAanduidingVerblijfsrecht().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumAanvangVerblijfsrecht() != null) {
                record.getDatumAanvangVerblijfsrecht().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumMededelingVerblijfsrecht() != null) {
                record.getDatumMededelingVerblijfsrecht().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumVoorzienEindeVerblijfsrecht() != null) {
                record.getDatumVoorzienEindeVerblijfsrecht().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Uitsluiting kiesrecht
     *
     */
    public class PersoonHisVolledigImplBuilderUitsluitingKiesrecht {

        private ActieModel actie;
        private PersoonUitsluitingKiesrechtGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderUitsluitingKiesrecht(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonUitsluitingKiesrechtGroepBericht();
        }

        /**
         * Geef attribuut Uitsluiting kiesrecht? een waarde.
         *
         * @param indicatieUitsluitingKiesrecht Uitsluiting kiesrecht? van Uitsluiting kiesrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderUitsluitingKiesrecht indicatieUitsluitingKiesrecht(final Ja indicatieUitsluitingKiesrecht) {
            this.bericht.setIndicatieUitsluitingKiesrecht(new JaAttribuut(indicatieUitsluitingKiesrecht));
            return this;
        }

        /**
         * Geef attribuut Datum voorzien einde uitsluiting kiesrecht een waarde.
         *
         * @param datumVoorzienEindeUitsluitingKiesrecht Datum voorzien einde uitsluiting kiesrecht van Uitsluiting
         *            kiesrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderUitsluitingKiesrecht datumVoorzienEindeUitsluitingKiesrecht(
            final Integer datumVoorzienEindeUitsluitingKiesrecht)
        {
            this.bericht.setDatumVoorzienEindeUitsluitingKiesrecht(new DatumEvtDeelsOnbekendAttribuut(datumVoorzienEindeUitsluitingKiesrecht));
            return this;
        }

        /**
         * Geef attribuut Datum voorzien einde uitsluiting kiesrecht een waarde.
         *
         * @param datumVoorzienEindeUitsluitingKiesrecht Datum voorzien einde uitsluiting kiesrecht van Uitsluiting
         *            kiesrecht
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderUitsluitingKiesrecht datumVoorzienEindeUitsluitingKiesrecht(
            final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingKiesrecht)
        {
            this.bericht.setDatumVoorzienEindeUitsluitingKiesrecht(datumVoorzienEindeUitsluitingKiesrecht);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonUitsluitingKiesrechtModel record = new HisPersoonUitsluitingKiesrechtModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonUitsluitingKiesrechtHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonUitsluitingKiesrechtModel record = new HisPersoonUitsluitingKiesrechtModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonUitsluitingKiesrechtHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonUitsluitingKiesrechtModel record) {
            if (record.getIndicatieUitsluitingKiesrecht() != null) {
                record.getIndicatieUitsluitingKiesrecht().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumVoorzienEindeUitsluitingKiesrecht() != null) {
                record.getDatumVoorzienEindeUitsluitingKiesrecht().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Deelname EU verkiezingen
     *
     */
    public class PersoonHisVolledigImplBuilderDeelnameEUVerkiezingen {

        private ActieModel actie;
        private PersoonDeelnameEUVerkiezingenGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderDeelnameEUVerkiezingen(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonDeelnameEUVerkiezingenGroepBericht();
        }

        /**
         * Geef attribuut Deelname EU verkiezingen? een waarde.
         *
         * @param indicatieDeelnameEUVerkiezingen Deelname EU verkiezingen? van Deelname EU verkiezingen
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderDeelnameEUVerkiezingen indicatieDeelnameEUVerkiezingen(final Boolean indicatieDeelnameEUVerkiezingen) {
            this.bericht.setIndicatieDeelnameEUVerkiezingen(new JaNeeAttribuut(indicatieDeelnameEUVerkiezingen));
            return this;
        }

        /**
         * Geef attribuut Deelname EU verkiezingen? een waarde.
         *
         * @param indicatieDeelnameEUVerkiezingen Deelname EU verkiezingen? van Deelname EU verkiezingen
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderDeelnameEUVerkiezingen indicatieDeelnameEUVerkiezingen(final JaNeeAttribuut indicatieDeelnameEUVerkiezingen) {
            this.bericht.setIndicatieDeelnameEUVerkiezingen(indicatieDeelnameEUVerkiezingen);
            return this;
        }

        /**
         * Geef attribuut Datum aanleiding aanpassing deelname EU verkiezingen een waarde.
         *
         * @param datumAanleidingAanpassingDeelnameEUVerkiezingen Datum aanleiding aanpassing deelname EU verkiezingen
         *            van Deelname EU verkiezingen
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderDeelnameEUVerkiezingen datumAanleidingAanpassingDeelnameEUVerkiezingen(
            final Integer datumAanleidingAanpassingDeelnameEUVerkiezingen)
        {
            this.bericht.setDatumAanleidingAanpassingDeelnameEUVerkiezingen(new DatumEvtDeelsOnbekendAttribuut(
                datumAanleidingAanpassingDeelnameEUVerkiezingen));
            return this;
        }

        /**
         * Geef attribuut Datum aanleiding aanpassing deelname EU verkiezingen een waarde.
         *
         * @param datumAanleidingAanpassingDeelnameEUVerkiezingen Datum aanleiding aanpassing deelname EU verkiezingen
         *            van Deelname EU verkiezingen
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderDeelnameEUVerkiezingen datumAanleidingAanpassingDeelnameEUVerkiezingen(
            final DatumEvtDeelsOnbekendAttribuut datumAanleidingAanpassingDeelnameEUVerkiezingen)
        {
            this.bericht.setDatumAanleidingAanpassingDeelnameEUVerkiezingen(datumAanleidingAanpassingDeelnameEUVerkiezingen);
            return this;
        }

        /**
         * Geef attribuut Datum voorzien einde uitsluiting EU verkiezingen een waarde.
         *
         * @param datumVoorzienEindeUitsluitingEUVerkiezingen Datum voorzien einde uitsluiting EU verkiezingen van
         *            Deelname EU verkiezingen
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderDeelnameEUVerkiezingen datumVoorzienEindeUitsluitingEUVerkiezingen(
            final Integer datumVoorzienEindeUitsluitingEUVerkiezingen)
        {
            this.bericht.setDatumVoorzienEindeUitsluitingEUVerkiezingen(new DatumEvtDeelsOnbekendAttribuut(datumVoorzienEindeUitsluitingEUVerkiezingen));
            return this;
        }

        /**
         * Geef attribuut Datum voorzien einde uitsluiting EU verkiezingen een waarde.
         *
         * @param datumVoorzienEindeUitsluitingEUVerkiezingen Datum voorzien einde uitsluiting EU verkiezingen van
         *            Deelname EU verkiezingen
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderDeelnameEUVerkiezingen datumVoorzienEindeUitsluitingEUVerkiezingen(
            final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingEUVerkiezingen)
        {
            this.bericht.setDatumVoorzienEindeUitsluitingEUVerkiezingen(datumVoorzienEindeUitsluitingEUVerkiezingen);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonDeelnameEUVerkiezingenModel record = new HisPersoonDeelnameEUVerkiezingenModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonDeelnameEUVerkiezingenHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonDeelnameEUVerkiezingenModel record = new HisPersoonDeelnameEUVerkiezingenModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonDeelnameEUVerkiezingenHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonDeelnameEUVerkiezingenModel record) {
            if (record.getIndicatieDeelnameEUVerkiezingen() != null) {
                record.getIndicatieDeelnameEUVerkiezingen().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumAanleidingAanpassingDeelnameEUVerkiezingen() != null) {
                record.getDatumAanleidingAanpassingDeelnameEUVerkiezingen().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumVoorzienEindeUitsluitingEUVerkiezingen() != null) {
                record.getDatumVoorzienEindeUitsluitingEUVerkiezingen().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Persoonskaart
     *
     */
    public class PersoonHisVolledigImplBuilderPersoonskaart {

        private ActieModel actie;
        private PersoonPersoonskaartGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonHisVolledigImplBuilderPersoonskaart(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonPersoonskaartGroepBericht();
        }

        /**
         * Geef attribuut Gemeente persoonskaart een waarde.
         *
         * @param code Gemeente persoonskaart van Persoonskaart
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderPersoonskaart gemeentePersoonskaart(final Integer code) {
            this.bericht.setGemeentePersoonskaart(new PartijAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Partij.class, new PartijCodeAttribuut(
                code))));
            return this;
        }

        /**
         * Geef attribuut Gemeente persoonskaart een waarde.
         *
         * @param code Gemeente persoonskaart van Persoonskaart
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderPersoonskaart gemeentePersoonskaart(final PartijCodeAttribuut code) {
            this.bericht.setGemeentePersoonskaart(new PartijAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Partij.class, code)));
            return this;
        }

        /**
         * Geef attribuut Gemeente persoonskaart een waarde.
         *
         * @param gemeentePersoonskaart Gemeente persoonskaart van Persoonskaart
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderPersoonskaart gemeentePersoonskaart(final Partij gemeentePersoonskaart) {
            this.bericht.setGemeentePersoonskaart(new PartijAttribuut(gemeentePersoonskaart));
            return this;
        }

        /**
         * Geef attribuut Persoonskaart volledig geconverteerd? een waarde.
         *
         * @param indicatiePersoonskaartVolledigGeconverteerd Persoonskaart volledig geconverteerd? van Persoonskaart
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderPersoonskaart indicatiePersoonskaartVolledigGeconverteerd(
            final Boolean indicatiePersoonskaartVolledigGeconverteerd)
        {
            this.bericht.setIndicatiePersoonskaartVolledigGeconverteerd(new JaNeeAttribuut(indicatiePersoonskaartVolledigGeconverteerd));
            return this;
        }

        /**
         * Geef attribuut Persoonskaart volledig geconverteerd? een waarde.
         *
         * @param indicatiePersoonskaartVolledigGeconverteerd Persoonskaart volledig geconverteerd? van Persoonskaart
         * @return de groep builder
         */
        public PersoonHisVolledigImplBuilderPersoonskaart indicatiePersoonskaartVolledigGeconverteerd(
            final JaNeeAttribuut indicatiePersoonskaartVolledigGeconverteerd)
        {
            this.bericht.setIndicatiePersoonskaartVolledigGeconverteerd(indicatiePersoonskaartVolledigGeconverteerd);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord() {
            final HisPersoonPersoonskaartModel record = new HisPersoonPersoonskaartModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonPersoonskaartHistorie().voegToe(record);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonPersoonskaartModel record = new HisPersoonPersoonskaartModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonPersoonskaartHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonPersoonskaartModel record) {
            if (record.getGemeentePersoonskaart() != null) {
                record.getGemeentePersoonskaart().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getIndicatiePersoonskaartVolledigGeconverteerd() != null) {
                record.getIndicatiePersoonskaartVolledigGeconverteerd().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
