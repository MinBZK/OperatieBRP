/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingsaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingsgemeenteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijzondereVerblijfsrechtelijkePositieGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonEUVerkiezingenGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonImmigratieGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonInschrijvingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOpschortingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonPersoonskaartGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonUitsluitingNLKiesrechtGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVerblijfstitelGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.logisch.kern.basis.PersoonBasis;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 *
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt.
 * In de BRP worden zowel personen ingeschreven die onder een College van Burgemeester en Wethouders vallen
 * ('ingezetenen'), als personen waarvoor de Minister verantwoordelijkheid geldt.
 *
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam
 * "Natuurlijk persoon" te gebruiken. Binnen de context van BRP hebben we het bij het hanteren van de term Persoon
 * echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon is verder dermate
 * gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over
 * Persoon en niet over "Natuurlijk persoon".
 * 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken we in het logisch & operationeel
 * model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die
 * wellicht wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.
 * RvdP 27 juni 2011
 *
 *
 *
 *
 */
public abstract class AbstractPersoonBericht extends AbstractObjectTypeBericht implements PersoonBasis {

    private SoortPersoon                                             soort;
    private PersoonAfgeleidAdministratiefGroepBericht                afgeleidAdministratief;
    private PersoonIdentificatienummersGroepBericht                  identificatienummers;
    private PersoonSamengesteldeNaamGroepBericht                     samengesteldeNaam;
    private PersoonGeboorteGroepBericht                              geboorte;
    private PersoonGeslachtsaanduidingGroepBericht                   geslachtsaanduiding;
    private PersoonInschrijvingGroepBericht                          inschrijving;
    private PersoonBijhoudingsaardGroepBericht                       bijhoudingsaard;
    private PersoonBijhoudingsgemeenteGroepBericht                   bijhoudingsgemeente;
    private PersoonOpschortingGroepBericht                           opschorting;
    private PersoonOverlijdenGroepBericht                            overlijden;
    private PersoonAanschrijvingGroepBericht                         aanschrijving;
    private PersoonImmigratieGroepBericht                            immigratie;
    private PersoonVerblijfstitelGroepBericht                        verblijfstitel;
    private PersoonBijzondereVerblijfsrechtelijkePositieGroepBericht bijzondereVerblijfsrechtelijkePositie;
    private PersoonUitsluitingNLKiesrechtGroepBericht                uitsluitingNLKiesrecht;
    private PersoonEUVerkiezingenGroepBericht                        eUVerkiezingen;
    private PersoonPersoonskaartGroepBericht                         persoonskaart;
    private List<PersoonVoornaamBericht>                             voornamen;
    private List<PersoonGeslachtsnaamcomponentBericht>               geslachtsnaamcomponenten;
    private List<PersoonNationaliteitBericht>                        nationaliteiten;
    private List<PersoonAdresBericht>                                adressen;
    private List<PersoonIndicatieBericht>                            indicaties;
    private List<PersoonReisdocumentBericht>                         reisdocumenten;
    private List<BetrokkenheidBericht>                               betrokkenheden;

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortPersoon getSoort() {
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
    public PersoonBijhoudingsaardGroepBericht getBijhoudingsaard() {
        return bijhoudingsaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBijhoudingsgemeenteGroepBericht getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonOpschortingGroepBericht getOpschorting() {
        return opschorting;
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
    public PersoonAanschrijvingGroepBericht getAanschrijving() {
        return aanschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonImmigratieGroepBericht getImmigratie() {
        return immigratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonVerblijfstitelGroepBericht getVerblijfstitel() {
        return verblijfstitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBijzondereVerblijfsrechtelijkePositieGroepBericht getBijzondereVerblijfsrechtelijkePositie() {
        return bijzondereVerblijfsrechtelijkePositie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonUitsluitingNLKiesrechtGroepBericht getUitsluitingNLKiesrecht() {
        return uitsluitingNLKiesrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonEUVerkiezingenGroepBericht getEUVerkiezingen() {
        return eUVerkiezingen;
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
     * Retourneert Indicaties van Persoon.
     *
     * @return Indicaties van Persoon.
     */
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
     * Zet Soort van Persoon.
     *
     * @param soort Soort.
     */
    public void setSoort(final SoortPersoon soort) {
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
     * Zet Bijhoudingsaard van Persoon.
     *
     * @param bijhoudingsaard Bijhoudingsaard.
     */
    public void setBijhoudingsaard(final PersoonBijhoudingsaardGroepBericht bijhoudingsaard) {
        this.bijhoudingsaard = bijhoudingsaard;
    }

    /**
     * Zet Bijhoudingsgemeente van Persoon.
     *
     * @param bijhoudingsgemeente Bijhoudingsgemeente.
     */
    public void setBijhoudingsgemeente(final PersoonBijhoudingsgemeenteGroepBericht bijhoudingsgemeente) {
        this.bijhoudingsgemeente = bijhoudingsgemeente;
    }

    /**
     * Zet Opschorting van Persoon.
     *
     * @param opschorting Opschorting.
     */
    public void setOpschorting(final PersoonOpschortingGroepBericht opschorting) {
        this.opschorting = opschorting;
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
     * Zet Aanschrijving van Persoon.
     *
     * @param aanschrijving Aanschrijving.
     */
    public void setAanschrijving(final PersoonAanschrijvingGroepBericht aanschrijving) {
        this.aanschrijving = aanschrijving;
    }

    /**
     * Zet Immigratie van Persoon.
     *
     * @param immigratie Immigratie.
     */
    public void setImmigratie(final PersoonImmigratieGroepBericht immigratie) {
        this.immigratie = immigratie;
    }

    /**
     * Zet Verblijfstitel van Persoon.
     *
     * @param verblijfstitel Verblijfstitel.
     */
    public void setVerblijfstitel(final PersoonVerblijfstitelGroepBericht verblijfstitel) {
        this.verblijfstitel = verblijfstitel;
    }

    /**
     * Zet Bijzondere verblijfsrechtelijke positie van Persoon.
     *
     * @param bijzondereVerblijfsrechtelijkePositie Bijzondere verblijfsrechtelijke positie.
     */
    public void setBijzondereVerblijfsrechtelijkePositie(
            final PersoonBijzondereVerblijfsrechtelijkePositieGroepBericht bijzondereVerblijfsrechtelijkePositie)
    {
        this.bijzondereVerblijfsrechtelijkePositie = bijzondereVerblijfsrechtelijkePositie;
    }

    /**
     * Zet Uitsluiting NL kiesrecht van Persoon.
     *
     * @param uitsluitingNLKiesrecht Uitsluiting NL kiesrecht.
     */
    public void setUitsluitingNLKiesrecht(final PersoonUitsluitingNLKiesrechtGroepBericht uitsluitingNLKiesrecht) {
        this.uitsluitingNLKiesrecht = uitsluitingNLKiesrecht;
    }

    /**
     * Zet EU verkiezingen van Persoon.
     *
     * @param eUVerkiezingen EU verkiezingen.
     */
    public void setEUVerkiezingen(final PersoonEUVerkiezingenGroepBericht eUVerkiezingen) {
        this.eUVerkiezingen = eUVerkiezingen;
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

}
