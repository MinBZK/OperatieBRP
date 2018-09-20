/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschorting;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOpschortingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonModel;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 * <p/>
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt.
 * In de BRP worden zowel personen ingeschreven die onder een College van Burgemeester en Wethouders vallen
 * ('ingezetenen'), als personen waarvoor de Minister verantwoordelijkheid geldt.
 * <p/>
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam
 * "Natuurlijk persoon" te gebruiken. Binnen de context van BRP hebben we het bij het hanteren van de term Persoon
 * echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon is verder dermate
 * gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over
 * Persoon en niet over "Natuurlijk persoon".
 * 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken we in het logisch & operationeel
 * model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die
 * wellicht wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.
 * RvdP 27 juni 2011
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:35 CET 2012.
 */
@Entity
@Table(schema = "Kern", name = "Pers")
public class PersoonModel extends AbstractPersoonModel implements Persoon, Comparable<PersoonModel> {

    private static final int HASHCODE_GETAL1 = 7;
    private static final int HASHCODE_GETAL2 = 3;

    /** Standaard constructor (t.b.v. Hibernate/JPA). */
    protected PersoonModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert.
     *
     * @param soort soort van Persoon.
     */
    public PersoonModel(final SoortPersoon soort) {
        super(soort);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoon Te kopieren object type.
     */
    public PersoonModel(final Persoon persoon) {
        super(persoon);
    }

    /**
     * Retourneert true indien deze persoon betrokkenheden heeft.
     *
     * @return True indien betrokkenheden aanwezig.
     */
    public boolean heeftBetrokkenheden() {
        return getBetrokkenheden() != null
            && !getBetrokkenheden().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon voornamen heeft.
     *
     * @return True indien voornamen aanwezig.
     */
    public boolean heeftVoornamen() {
        return getVoornamen() != null
            && !getVoornamen().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon geslachtsnaam componenten heeft.
     *
     * @return True indien geslachtsnaam componenten aanwezig.
     */
    public boolean heeftGeslachtsnaamcomponenten() {
        return getGeslachtsnaamcomponenten() != null
            && !getGeslachtsnaamcomponenten().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon nationaliteiten heeft.
     *
     * @return True indien nationaliteiten aanwezig.
     */
    public boolean heeftNationaliteiten() {
        return getNationaliteiten() != null
            && !getNationaliteiten().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon een niet vervallen nederlandse nationaliteit heeft.
     *
     * @return True indien hij nederlander is.
     */
    public boolean heeftActueleNederlandseNationaliteit() {
        boolean retval = false;
        for (PersoonNationaliteitModel nationaliteit : getNationaliteiten()) {
            if (nationaliteit.getNationaliteit().getCode().equals(
                BrpConstanten.NL_NATIONALITEIT_CODE)
                && StatusHistorie.A.equals(nationaliteit.getPersoonNationaliteitStatusHis()))
            {
                retval = true;
                break;
            }
        }
        return retval;
    }

    /**
     * Retourneert true indien deze persoon indicaties heeft.
     *
     * @return True indien indicaties aanwezig.
     */
    public boolean heeftIndicaties() {
        boolean retval = false;
        if (getIndicaties() != null) {
            for (PersoonIndicatieModel indicatie : getIndicaties()) {
                if (StatusHistorie.A.equals(indicatie.getPersoonIndicatieStatusHis())
                    && Ja.J.equals(indicatie.getStandaard().getWaarde()))
                {
                    retval = true;
                }
            }
        }
        return retval;
    }

    /**
     * Retourneert true als deze persoon een actuele indicatie heeft.
     *
     * @param soortIndicatie de soort indicatie
     * @return true als deze actief neeft, false anders.
     */
    public boolean heeftActueleSoortIndicatie(final SoortIndicatie soortIndicatie) {
        boolean retval = false;
        for (PersoonIndicatieModel indicatie : getIndicaties()) {
            if (soortIndicatie == indicatie.getSoort()
                && StatusHistorie.A.equals(indicatie.getPersoonIndicatieStatusHis())
                && Ja.J.equals(indicatie.getStandaard().getWaarde()))
            {
                retval = true;
                break;
            }
        }
        return retval;
    }


    @Transient
    public Boolean isOverleden() {
        return (getOpschorting() != null
            && RedenOpschorting.OVERLIJDEN.equals(getOpschorting().getRedenOpschortingBijhouding()));
    }

    /**
     * Retourneert kind betrokkenheid van deze persoon.
     *
     * @return Kind betrokkenheid.
     */
    @Transient
    public KindModel getKindBetrokkenHeid() {
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (betrokkenheid instanceof KindModel) {
                return (KindModel) betrokkenheid;
            }
        }
        return null;
    }

    /**
     * Retourneert kind betrokkenheden van deze persoon.
     *
     * @return Kind betrokkenheden.
     */
    @Transient
    public Set<BetrokkenheidModel> getKindBetrokkenheden() {
        final TreeSet<BetrokkenheidModel> gesorteerdeSet = new TreeSet<BetrokkenheidModel>();
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betrokkenheid.getRol()) {
                gesorteerdeSet.add(betrokkenheid);
            }
        }
        return gesorteerdeSet;
    }

    /**
     * Retourneert ouder betrokkenheden van deze persoon.
     *
     * @return Ouder betrokkenheden.
     */
    @Transient
    public Set<BetrokkenheidModel> getOuderBetrokkenheden() {
        final TreeSet<BetrokkenheidModel> gesorteerdeSet = new TreeSet<BetrokkenheidModel>();
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (betrokkenheid instanceof OuderModel) {
                gesorteerdeSet.add(betrokkenheid);
            }
        }
        return gesorteerdeSet;
    }

    /**
     * Retourneert partner betrokkenheden van deze persoon.
     *
     * @return Partner betrokkenheden.
     */
    @Transient
    public Set<BetrokkenheidModel> getPartnerBetrokkenHeden() {
        final TreeSet<BetrokkenheidModel> gesorteerdeSet = new TreeSet<BetrokkenheidModel>();
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (betrokkenheid instanceof PartnerModel) {
                gesorteerdeSet.add(betrokkenheid);
            }
        }
        return gesorteerdeSet;
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param betr KindModel.
     */
    protected void voegKindBetrokkenheidToe(final KindModel betr) {
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param betr Betrokkenheid.
     */
    protected void voegBetrokkenheidToe(final BetrokkenheidModel betr) {
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param betr Betrokkenheid.
     */
    protected void voegBetrokkenhedenToe(final Set<BetrokkenheidModel> betr) {
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param soort SoortPersoon.
     */
    protected void setSoort(final SoortPersoon soort) {
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param Set voornamen.
     */
    protected void setVoornamen(final Set<PersoonVoornaamModel> voornamen) {
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param Set geslachtsnaamcomponenten.
     */
    protected void setGeslachtsnaamcomponenten(final Set<PersoonGeslachtsnaamcomponentModel> geslachtsnaamcomponenten) {
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param Set adressen.
     */
    protected void setAdressen(final Set<PersoonAdresModel> adressen) {
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param Set nationaliteiten.
     */
    protected void setNationaliteiten(final Set<PersoonNationaliteitModel> nationaliteiten) {
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param Set indicaties.
     */
    protected void setIndicaties(final Set<PersoonIndicatieModel> indicaties) {
    }

    /**
     * Retourneert de indicatie verstrekkings beperking indien aanwezig.
     *
     * @return Indicatie.
     */
    @Transient
    protected PersoonIndicatieModel getIndicatieVerstrekkingsbeperking() {
        if (getIndicaties() != null) {
            for (PersoonIndicatieModel ind : getIndicaties()) {
                if (SoortIndicatie.INDICATIE_VERSTREKKINGSBEPERKING == ind.getSoort()) {
                    return ind;
                }
            }
        }
        return null;
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param ind Indicatie.
     */
    protected void voegPersoonIndicatieToe(final PersoonIndicatieModel ind) {
    }

    /**
     * Retourneert of persoon een verstrekkingsbeperking heeft.
     *
     * @return true of false.
     */
    protected boolean heeftVerstrekkingsBeperking() {
        return getIndicatieVerstrekkingsbeperking() != null;
    }


    /**
     * Vervang een lijst van groepen met nieuwe data.
     *
     * @param groepen de lijst
     */
    public void vervangGroepen(final Groep... groepen) {
        for (Groep groep : groepen) {
            if (groep instanceof PersoonBijhoudingsgemeenteGroep) {
                setBijhoudingsgemeente(
                    new PersoonBijhoudingsgemeenteGroepModel((PersoonBijhoudingsgemeenteGroep) groep));
                // TODO: wat als deze null is, of wat als dit een 'verval' groep => dan moet de status anders worden.
                setBijhoudingsgemeenteStatusHis(StatusHistorie.A);
            } else if (groep instanceof PersoonBijhoudingsaardGroep) {
                setBijhoudingsaard(new PersoonBijhoudingsaardGroepModel((PersoonBijhoudingsaardGroep) groep));
                // TODO: wat als deze null is, of wat als dit een 'verval' groep => dan moet de status anders worden.
                setBijhoudingsaardStatusHis(StatusHistorie.A);
            } else if (groep instanceof PersoonAanschrijvingGroep) {
                setAanschrijving(new PersoonAanschrijvingGroepModel((PersoonAanschrijvingGroep) groep));
                // TODO: wat als deze null is, of wat als dit een 'verval' groep => dan moet de status anders worden.
                setAanschrijvingStatusHis(StatusHistorie.A);
            } else if (groep instanceof PersoonOverlijdenGroep) {
                setOverlijden(new PersoonOverlijdenGroepModel((PersoonOverlijdenGroep) groep));
                setOverlijdenStatusHis(StatusHistorie.A);
            } else if (groep instanceof PersoonOpschortingGroep) {
                setOpschorting(new PersoonOpschortingGroepModel((PersoonOpschortingGroep) groep));
                setOpschortingStatusHis(StatusHistorie.A);
            } else {
                throw new UnsupportedOperationException("Vervang groepen voor de groep ["
                    + groep.getClass().getSimpleName() +
                    "] moet nog toegevoegd worden.");
            }
            // etc ... TODO andere groepen ook implementeren.
        }
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param ts String.
     */
    protected void setTechnischeSleutel(final String ts) {
    }

    /**
     * Geeft de technische sleutel terug. Bsn wordt teruggeven als de sleutel, wanneer deze niet bestaat wordt de id
     * met de prefix db teruggegeven.
     *
     * @return bsn of "brp"[id]
     */
    public String getTechnischeSleutel() {
        String technischeSleutel;
        if (getIdentificatienummers() != null && getIdentificatienummers().getBurgerservicenummer() != null
            && getIdentificatienummers().getBurgerservicenummer().getWaarde() != null)
        {
            technischeSleutel = getIdentificatienummers().getBurgerservicenummer().toString();
        } else {
            technischeSleutel = "db" + getID().toString();
        }

        return technischeSleutel;
    }

    /**
     * Vergelijkt dit object met het opgegeven object voor volgorde. Deze methode retourneert een negatief getal, nul,
     * of een positief getal indien dit object 'kleiner', gelijk of 'groter' dan het opgegeven object is. Hierbij wordt
     * voor {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} speficiek gekeken naar de waardes voor
     * de groep identificatienummers en de soort.
     *
     * @param persoon het object dat wordt vergeleken.
     * @return een negatief getal, nul of een positief getal indien dit object 'kleiner', gelijk aan of 'groter' is
     *         dan het opgegeven object.
     */
    @Override
    // TODO: Herimplementatie compare methode; nog onbekend is hoe werkelijk gesorteerd dient te worden.
    // TODO: Mogelijk dient deze methode hier verwijderd te worden en dient sortering op bericht gedefinieerd te worden.
    public int compareTo(final PersoonModel persoon)
    {
        final int vergelijk;

        if (persoon == null) {
            vergelijk = -1;
        } else {
            vergelijk = new CompareToBuilder()
                .append(this.getSoort(), persoon.getSoort())
                .append(this.getIdentificatienummers(), persoon.getIdentificatienummers())
                .append(this.getID(), persoon.getID()).toComparison();
        }

        return vergelijk;
    }


}
