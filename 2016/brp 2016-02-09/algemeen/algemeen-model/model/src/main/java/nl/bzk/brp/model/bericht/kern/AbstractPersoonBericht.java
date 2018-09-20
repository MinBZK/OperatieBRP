/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.bericht.autaut.PersoonAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.ber.PersoonAdministratieveHandelingBericht;
import nl.bzk.brp.model.logisch.kern.PersoonBasis;

/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 *
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt. In de BRP worden zowel personen waarvan de
 * bijhouding valt onder afdeling I ('Ingezetenen') van de Wet BRP, als personen waarvoor de bijhouding onder afdeling
 * II ('Niet-ingezetenen') van de Wet BRP valt, ingeschreven.
 *
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam
 * "Natuurlijk persoon" te gebruiken. Binnen de context van BRP hebben we het bij het hanteren van de term Persoon
 * echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon is verder dermate
 * gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over
 * Persoon en niet over "Natuurlijk persoon". 2. Voor gerelateerde personen, en voor niet-ingeschrevenen, gebruiken we
 * in het logisch & operationeel model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel
 * groepen niet verplicht, die wellicht wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar, PersoonBasis {

    private static final Integer META_ID = 3010;
    private SoortPersoonAttribuut soort;
    private PersoonAfgeleidAdministratiefGroepBericht afgeleidAdministratief;
    private PersoonIdentificatienummersGroepBericht identificatienummers;
    private PersoonSamengesteldeNaamGroepBericht samengesteldeNaam;
    private PersoonGeboorteGroepBericht geboorte;
    private PersoonGeslachtsaanduidingGroepBericht geslachtsaanduiding;
    private PersoonInschrijvingGroepBericht inschrijving;
    private PersoonNummerverwijzingGroepBericht nummerverwijzing;
    private PersoonBijhoudingGroepBericht bijhouding;
    private PersoonOverlijdenGroepBericht overlijden;
    private PersoonNaamgebruikGroepBericht naamgebruik;
    private PersoonMigratieGroepBericht migratie;
    private PersoonVerblijfsrechtGroepBericht verblijfsrecht;
    private PersoonUitsluitingKiesrechtGroepBericht uitsluitingKiesrecht;
    private PersoonDeelnameEUVerkiezingenGroepBericht deelnameEUVerkiezingen;
    private PersoonPersoonskaartGroepBericht persoonskaart;
    private List<PersoonVoornaamBericht> voornamen;
    private List<PersoonGeslachtsnaamcomponentBericht> geslachtsnaamcomponenten;
    private List<PersoonVerificatieBericht> verificaties;
    private List<PersoonNationaliteitBericht> nationaliteiten;
    private List<PersoonAdresBericht> adressen;
    private List<PersoonIndicatieBericht> indicaties;
    private List<PersoonReisdocumentBericht> reisdocumenten;
    private List<BetrokkenheidBericht> betrokkenheden;
    private List<PersoonOnderzoekBericht> onderzoeken;
    private List<PersoonVerstrekkingsbeperkingBericht> verstrekkingsbeperkingen;
    private List<PersoonAfnemerindicatieBericht> afnemerindicaties;
    private List<PersoonAdministratieveHandelingBericht> administratieveHandelingen;

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortPersoonAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonAfgeleidAdministratiefGroepBericht getAfgeleidAdministratief() {
        return afgeleidAdministratief;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIdentificatienummersGroepBericht getIdentificatienummers() {
        return identificatienummers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonSamengesteldeNaamGroepBericht getSamengesteldeNaam() {
        return samengesteldeNaam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonGeboorteGroepBericht getGeboorte() {
        return geboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonGeslachtsaanduidingGroepBericht getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonInschrijvingGroepBericht getInschrijving() {
        return inschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonNummerverwijzingGroepBericht getNummerverwijzing() {
        return nummerverwijzing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBijhoudingGroepBericht getBijhouding() {
        return bijhouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonOverlijdenGroepBericht getOverlijden() {
        return overlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonNaamgebruikGroepBericht getNaamgebruik() {
        return naamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonMigratieGroepBericht getMigratie() {
        return migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonVerblijfsrechtGroepBericht getVerblijfsrecht() {
        return verblijfsrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonUitsluitingKiesrechtGroepBericht getUitsluitingKiesrecht() {
        return uitsluitingKiesrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonDeelnameEUVerkiezingenGroepBericht getDeelnameEUVerkiezingen() {
        return deelnameEUVerkiezingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonPersoonskaartGroepBericht getPersoonskaart() {
        return persoonskaart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersoonVoornaamBericht> getVoornamen() {
        return voornamen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersoonGeslachtsnaamcomponentBericht> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersoonVerificatieBericht> getVerificaties() {
        return verificaties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersoonNationaliteitBericht> getNationaliteiten() {
        return nationaliteiten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersoonAdresBericht> getAdressen() {
        return adressen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersoonIndicatieBericht> getIndicaties() {
        return indicaties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersoonReisdocumentBericht> getReisdocumenten() {
        return reisdocumenten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BetrokkenheidBericht> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersoonOnderzoekBericht> getOnderzoeken() {
        return onderzoeken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersoonVerstrekkingsbeperkingBericht> getVerstrekkingsbeperkingen() {
        return verstrekkingsbeperkingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersoonAfnemerindicatieBericht> getAfnemerindicaties() {
        return afnemerindicaties;
    }

    /**
     * Retourneert Persoon \ Administratieve handelingen van Persoon.
     *
     * @return Persoon \ Administratieve handelingen van Persoon.
     */
    public List<PersoonAdministratieveHandelingBericht> getAdministratieveHandelingen() {
        return administratieveHandelingen;
    }

    /**
     * Zet Soort van Persoon.
     *
     * @param soort Soort.
     */
    public void setSoort(final SoortPersoonAttribuut soort) {
        this.soort = soort;
    }

    /**
     * Zet Afgeleid administratief van Persoon.
     *
     * @param afgeleidAdministratief Afgeleid administratief.
     */
    public void setAfgeleidAdministratief(final PersoonAfgeleidAdministratiefGroepBericht afgeleidAdministratief) {
        this.afgeleidAdministratief = afgeleidAdministratief;
    }

    /**
     * Zet Identificatienummers van Persoon.
     *
     * @param identificatienummers Identificatienummers.
     */
    public void setIdentificatienummers(final PersoonIdentificatienummersGroepBericht identificatienummers) {
        this.identificatienummers = identificatienummers;
    }

    /**
     * Zet Samengestelde naam van Persoon.
     *
     * @param samengesteldeNaam Samengestelde naam.
     */
    public void setSamengesteldeNaam(final PersoonSamengesteldeNaamGroepBericht samengesteldeNaam) {
        this.samengesteldeNaam = samengesteldeNaam;
    }

    /**
     * Zet Geboorte van Persoon.
     *
     * @param geboorte Geboorte.
     */
    public void setGeboorte(final PersoonGeboorteGroepBericht geboorte) {
        this.geboorte = geboorte;
    }

    /**
     * Zet Geslachtsaanduiding van Persoon.
     *
     * @param geslachtsaanduiding Geslachtsaanduiding.
     */
    public void setGeslachtsaanduiding(final PersoonGeslachtsaanduidingGroepBericht geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;
    }

    /**
     * Zet Inschrijving van Persoon.
     *
     * @param inschrijving Inschrijving.
     */
    public void setInschrijving(final PersoonInschrijvingGroepBericht inschrijving) {
        this.inschrijving = inschrijving;
    }

    /**
     * Zet Nummerverwijzing van Persoon.
     *
     * @param nummerverwijzing Nummerverwijzing.
     */
    public void setNummerverwijzing(final PersoonNummerverwijzingGroepBericht nummerverwijzing) {
        this.nummerverwijzing = nummerverwijzing;
    }

    /**
     * Zet Bijhouding van Persoon.
     *
     * @param bijhouding Bijhouding.
     */
    public void setBijhouding(final PersoonBijhoudingGroepBericht bijhouding) {
        this.bijhouding = bijhouding;
    }

    /**
     * Zet Overlijden van Persoon.
     *
     * @param overlijden Overlijden.
     */
    public void setOverlijden(final PersoonOverlijdenGroepBericht overlijden) {
        this.overlijden = overlijden;
    }

    /**
     * Zet Naamgebruik van Persoon.
     *
     * @param naamgebruik Naamgebruik.
     */
    public void setNaamgebruik(final PersoonNaamgebruikGroepBericht naamgebruik) {
        this.naamgebruik = naamgebruik;
    }

    /**
     * Zet Migratie van Persoon.
     *
     * @param migratie Migratie.
     */
    public void setMigratie(final PersoonMigratieGroepBericht migratie) {
        this.migratie = migratie;
    }

    /**
     * Zet Verblijfsrecht van Persoon.
     *
     * @param verblijfsrecht Verblijfsrecht.
     */
    public void setVerblijfsrecht(final PersoonVerblijfsrechtGroepBericht verblijfsrecht) {
        this.verblijfsrecht = verblijfsrecht;
    }

    /**
     * Zet Uitsluiting kiesrecht van Persoon.
     *
     * @param uitsluitingKiesrecht Uitsluiting kiesrecht.
     */
    public void setUitsluitingKiesrecht(final PersoonUitsluitingKiesrechtGroepBericht uitsluitingKiesrecht) {
        this.uitsluitingKiesrecht = uitsluitingKiesrecht;
    }

    /**
     * Zet Deelname EU verkiezingen van Persoon.
     *
     * @param deelnameEUVerkiezingen Deelname EU verkiezingen.
     */
    public void setDeelnameEUVerkiezingen(final PersoonDeelnameEUVerkiezingenGroepBericht deelnameEUVerkiezingen) {
        this.deelnameEUVerkiezingen = deelnameEUVerkiezingen;
    }

    /**
     * Zet Persoonskaart van Persoon.
     *
     * @param persoonskaart Persoonskaart.
     */
    public void setPersoonskaart(final PersoonPersoonskaartGroepBericht persoonskaart) {
        this.persoonskaart = persoonskaart;
    }

    /**
     * Zet Voornamen van Persoon.
     *
     * @param voornamen Voornamen.
     */
    public void setVoornamen(final List<PersoonVoornaamBericht> voornamen) {
        this.voornamen = voornamen;
    }

    /**
     * Zet Geslachtsnaamcomponenten van Persoon.
     *
     * @param geslachtsnaamcomponenten Geslachtsnaamcomponenten.
     */
    public void setGeslachtsnaamcomponenten(final List<PersoonGeslachtsnaamcomponentBericht> geslachtsnaamcomponenten) {
        this.geslachtsnaamcomponenten = geslachtsnaamcomponenten;
    }

    /**
     * Zet Verificaties van Persoon.
     *
     * @param verificaties Verificaties.
     */
    public void setVerificaties(final List<PersoonVerificatieBericht> verificaties) {
        this.verificaties = verificaties;
    }

    /**
     * Zet Persoon \ Nationaliteiten van Persoon.
     *
     * @param nationaliteiten Persoon \ Nationaliteiten.
     */
    public void setNationaliteiten(final List<PersoonNationaliteitBericht> nationaliteiten) {
        this.nationaliteiten = nationaliteiten;
    }

    /**
     * Zet Adressen van Persoon.
     *
     * @param adressen Adressen.
     */
    public void setAdressen(final List<PersoonAdresBericht> adressen) {
        this.adressen = adressen;
    }

    /**
     * Zet Indicaties van Persoon.
     *
     * @param indicaties Indicaties.
     */
    public void setIndicaties(final List<PersoonIndicatieBericht> indicaties) {
        this.indicaties = indicaties;
    }

    /**
     * Zet Reisdocumenten van Persoon.
     *
     * @param reisdocumenten Reisdocumenten.
     */
    public void setReisdocumenten(final List<PersoonReisdocumentBericht> reisdocumenten) {
        this.reisdocumenten = reisdocumenten;
    }

    /**
     * Zet Betrokkenheden van Persoon.
     *
     * @param betrokkenheden Betrokkenheden.
     */
    public void setBetrokkenheden(final List<BetrokkenheidBericht> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

    /**
     * Zet Personen \ Onderzoek van Persoon.
     *
     * @param onderzoeken Personen \ Onderzoek.
     */
    public void setOnderzoeken(final List<PersoonOnderzoekBericht> onderzoeken) {
        this.onderzoeken = onderzoeken;
    }

    /**
     * Zet Persoon \ Verstrekkingsbeperkingen van Persoon.
     *
     * @param verstrekkingsbeperkingen Persoon \ Verstrekkingsbeperkingen.
     */
    public void setVerstrekkingsbeperkingen(final List<PersoonVerstrekkingsbeperkingBericht> verstrekkingsbeperkingen) {
        this.verstrekkingsbeperkingen = verstrekkingsbeperkingen;
    }

    /**
     * Zet Persoon \ Afnemerindicaties van Persoon.
     *
     * @param afnemerindicaties Persoon \ Afnemerindicaties.
     */
    public void setAfnemerindicaties(final List<PersoonAfnemerindicatieBericht> afnemerindicaties) {
        this.afnemerindicaties = afnemerindicaties;
    }

    /**
     * Zet Persoon \ Administratieve handelingen van Persoon.
     *
     * @param administratieveHandelingen Persoon \ Administratieve handelingen.
     */
    public void setAdministratieveHandelingen(final List<PersoonAdministratieveHandelingBericht> administratieveHandelingen) {
        this.administratieveHandelingen = administratieveHandelingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteit> getBerichtEntiteiten() {
        final List<BerichtEntiteit> berichtEntiteiten = new ArrayList<>();
        if (voornamen != null) {
            berichtEntiteiten.addAll(getVoornamen());
        }
        if (geslachtsnaamcomponenten != null) {
            berichtEntiteiten.addAll(getGeslachtsnaamcomponenten());
        }
        if (verificaties != null) {
            berichtEntiteiten.addAll(getVerificaties());
        }
        if (nationaliteiten != null) {
            berichtEntiteiten.addAll(getNationaliteiten());
        }
        if (adressen != null) {
            berichtEntiteiten.addAll(getAdressen());
        }
        if (indicaties != null) {
            berichtEntiteiten.addAll(getIndicaties());
        }
        if (reisdocumenten != null) {
            berichtEntiteiten.addAll(getReisdocumenten());
        }
        if (betrokkenheden != null) {
            berichtEntiteiten.addAll(getBetrokkenheden());
        }
        if (onderzoeken != null) {
            berichtEntiteiten.addAll(getOnderzoeken());
        }
        if (verstrekkingsbeperkingen != null) {
            berichtEntiteiten.addAll(getVerstrekkingsbeperkingen());
        }
        if (afnemerindicaties != null) {
            berichtEntiteiten.addAll(getAfnemerindicaties());
        }
        return berichtEntiteiten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        final List<BerichtEntiteitGroep> berichtGroepen = new ArrayList<>();
        berichtGroepen.add(getAfgeleidAdministratief());
        berichtGroepen.add(getIdentificatienummers());
        berichtGroepen.add(getSamengesteldeNaam());
        berichtGroepen.add(getGeboorte());
        berichtGroepen.add(getGeslachtsaanduiding());
        berichtGroepen.add(getInschrijving());
        berichtGroepen.add(getNummerverwijzing());
        berichtGroepen.add(getBijhouding());
        berichtGroepen.add(getOverlijden());
        berichtGroepen.add(getNaamgebruik());
        berichtGroepen.add(getMigratie());
        berichtGroepen.add(getVerblijfsrecht());
        berichtGroepen.add(getUitsluitingKiesrecht());
        berichtGroepen.add(getDeelnameEUVerkiezingen());
        berichtGroepen.add(getPersoonskaart());
        return berichtGroepen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieBericht getIndicatieDerdeHeeftGezag() {
        PersoonIndicatieBericht indicatieDerdeHeeftGezag = null;
        for (PersoonIndicatieBericht persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG) {
                indicatieDerdeHeeftGezag = persoonIndicatie;
            }
        }
        return indicatieDerdeHeeftGezag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieBericht getIndicatieOnderCuratele() {
        PersoonIndicatieBericht indicatieOnderCuratele = null;
        for (PersoonIndicatieBericht persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_ONDER_CURATELE) {
                indicatieOnderCuratele = persoonIndicatie;
            }
        }
        return indicatieOnderCuratele;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieBericht getIndicatieVolledigeVerstrekkingsbeperking() {
        PersoonIndicatieBericht indicatieVolledigeVerstrekkingsbeperking = null;
        for (PersoonIndicatieBericht persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING) {
                indicatieVolledigeVerstrekkingsbeperking = persoonIndicatie;
            }
        }
        return indicatieVolledigeVerstrekkingsbeperking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieBericht getIndicatieVastgesteldNietNederlander() {
        PersoonIndicatieBericht indicatieVastgesteldNietNederlander = null;
        for (PersoonIndicatieBericht persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_VASTGESTELD_NIET_NEDERLANDER) {
                indicatieVastgesteldNietNederlander = persoonIndicatie;
            }
        }
        return indicatieVastgesteldNietNederlander;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieBericht getIndicatieBehandeldAlsNederlander() {
        PersoonIndicatieBericht indicatieBehandeldAlsNederlander = null;
        for (PersoonIndicatieBericht persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER) {
                indicatieBehandeldAlsNederlander = persoonIndicatie;
            }
        }
        return indicatieBehandeldAlsNederlander;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieBericht getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument() {
        PersoonIndicatieBericht indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument = null;
        for (PersoonIndicatieBericht persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT) {
                indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument = persoonIndicatie;
            }
        }
        return indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieBericht getIndicatieStaatloos() {
        PersoonIndicatieBericht indicatieStaatloos = null;
        for (PersoonIndicatieBericht persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_STAATLOOS) {
                indicatieStaatloos = persoonIndicatie;
            }
        }
        return indicatieStaatloos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieBericht getIndicatieBijzondereVerblijfsrechtelijkePositie() {
        PersoonIndicatieBericht indicatieBijzondereVerblijfsrechtelijkePositie = null;
        for (PersoonIndicatieBericht persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE) {
                indicatieBijzondereVerblijfsrechtelijkePositie = persoonIndicatie;
            }
        }
        return indicatieBijzondereVerblijfsrechtelijkePositie;
    }

}
