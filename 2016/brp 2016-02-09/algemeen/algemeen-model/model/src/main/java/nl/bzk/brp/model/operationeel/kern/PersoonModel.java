/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNaamgebruikGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 * <p/>
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt. In de BRP worden zowel personen waarvan de bijhouding valt onder afdeling I
 * ('Ingezetenen') van de Wet BRP, als personen waarvoor de bijhouding onder afdeling II ('Niet ingezetenen') van de Wet BRP valt, ingeschreven.
 * <p/>
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam "Natuurlijk persoon" te gebruiken. Binnen de
 * context van BRP hebben we het bij het hanteren van de term Persoon echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de
 * term Persoon is verder dermate gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over Persoon
 * en niet over "Natuurlijk persoon". 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken we in het logisch & operationeel
 * model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die wellicht wel verplicht zouden zijn
 * in geval van (alleen) ingeschrevenen. RvdP 27 juni 2011
 */
@Entity
@Table(schema = "Kern", name = "Pers")
public class PersoonModel extends AbstractPersoonModel implements Persoon, Comparable<PersoonModel> {

    private static final int HASHCODE_GETAL1 = 4209;
    private static final int HASHCODE_GETAL2 = 54353;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Persoon.
     */
    public PersoonModel(final SoortPersoonAttribuut soort) {
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
        return getBetrokkenheden() != null && !getBetrokkenheden().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon voornamen heeft.
     *
     * @return True indien voornamen aanwezig.
     */
    public boolean heeftVoornamen() {
        return getVoornamen() != null && !getVoornamen().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon geslachtsnaam componenten heeft.
     *
     * @return True indien geslachtsnaam componenten aanwezig.
     */
    public boolean heeftGeslachtsnaamcomponenten() {
        return getGeslachtsnaamcomponenten() != null && !getGeslachtsnaamcomponenten().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon nationaliteiten heeft.
     *
     * @return True indien nationaliteiten aanwezig.
     */
    public boolean heeftNationaliteiten() {
        return getNationaliteiten() != null && !getNationaliteiten().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon een niet vervallen nederlandse nationaliteit heeft.
     *
     * @return True indien hij nederlander is.
     */
    public boolean heeftActueleNederlandseNationaliteit() {
        boolean retval = false;
        for (PersoonNationaliteitModel nationaliteit : getNationaliteiten()) {
            if (nationaliteit.getNationaliteit().getWaarde().getCode().equals(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE)) {
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
                if (indicatie.getStandaard() != null && Ja.J.equals(indicatie.getStandaard().getWaarde().getWaarde())) {
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
            if (soortIndicatie == indicatie.getSoort().getWaarde()
                && Ja.J.equals(indicatie.getStandaard().getWaarde().getWaarde()))
            {
                retval = true;
                break;
            }
        }
        return retval;
    }

    @Transient
    public Boolean isOverleden() {
        return null != getOverlijden();
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
    public Set<KindModel> getKindBetrokkenheden() {
        final Set<KindModel> gesorteerdeSet = new TreeSet<KindModel>();
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (betrokkenheid instanceof KindModel) {
                gesorteerdeSet.add((KindModel) betrokkenheid);
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
    public Set<OuderModel> getOuderBetrokkenheden() {
        final Set<OuderModel> gesorteerdeSet = new TreeSet<OuderModel>();
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (betrokkenheid instanceof OuderModel) {
                gesorteerdeSet.add((OuderModel) betrokkenheid);
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
    public Set<PartnerModel> getPartnerBetrokkenHeden() {
        final Set<PartnerModel> gesorteerdeSet = new TreeSet<PartnerModel>();
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (betrokkenheid instanceof PartnerModel) {
                gesorteerdeSet.add((PartnerModel) betrokkenheid);
            }
        }
        return gesorteerdeSet;
    }

    /**
     * Geeft alle personen terug die te vinden zijn via this->betrokkenheden->relatie->betrokkenheden->persoon. NB: Deze persoon zelf wordt expliciet niet
     * opgenomen in de terug te geven collectie.
     *
     * @return alle personen in alle betrokkenheden-relaties-betrokkenheden
     */
    @Transient
    public Set<PersoonModel> getBetrokkenPersonenInRelaties() {
        final Set<PersoonModel> personen = new HashSet<PersoonModel>();
        if (this.getBetrokkenheden() != null) {
            for (BetrokkenheidModel betrokkenheid : this.getBetrokkenheden()) {
                if (betrokkenheid.getRelatie() != null && betrokkenheid.getRelatie().getBetrokkenheden() != null) {
                    for (BetrokkenheidModel indirecteBetrokkenheid : betrokkenheid.getRelatie().getBetrokkenheden()) {
                        // Neem deze persoon zelf niet op in de collectie.
                        if (indirecteBetrokkenheid.getPersoon() != null
                            && !indirecteBetrokkenheid.getPersoon().getID().equals(this.getID()))
                        {
                            personen.add(indirecteBetrokkenheid.getPersoon());
                        }
                    }
                }
            }
        }
        return personen;
    }

    /**
     * Retourneert of persoon een verstrekkingsbeperking heeft.
     *
     * @return true of false.
     */
    protected boolean heeftVolledigeVerstrekkingsBeperking() {
        return getIndicatieVolledigeVerstrekkingsbeperking() != null;
    }

    /**
     * Vervang een lijst van groepen met nieuwe data.
     *
     * @param groepen de lijst
     */
    public void vervangGroepen(final Groep... groepen) {
        for (Groep groep : groepen) {
            if (groep instanceof PersoonBijhoudingGroep) {
                setBijhouding(new PersoonBijhoudingGroepModel((PersoonBijhoudingGroep) groep));
            } else if (groep instanceof PersoonNaamgebruikGroepModel) {
                setNaamgebruik(new PersoonNaamgebruikGroepModel((PersoonNaamgebruikGroep) groep));
            } else if (groep instanceof PersoonOverlijdenGroep) {
                setOverlijden(new PersoonOverlijdenGroepModel((PersoonOverlijdenGroep) groep));
            } else if (groep instanceof PersoonSamengesteldeNaamGroep) {
                setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepModel((PersoonSamengesteldeNaamGroep) groep));
            } else {
                throw new UnsupportedOperationException("Vervang groepen voor de groep ["
                    + groep.getClass().getSimpleName() + "] moet nog toegevoegd worden.");
            }
            // TODO andere groepen ook implementeren.
        }
    }

    @Override
    public int compareTo(final PersoonModel persoon) {
        final int vergelijk;

        if (persoon == null) {
            vergelijk = -1;
        } else {
            vergelijk =
                new CompareToBuilder().append(this.getSoort(), persoon.getSoort())
                    .append(this.getIdentificatienummers(), persoon.getIdentificatienummers())
                    .append(this.getID(), persoon.getID()).toComparison();
        }

        return vergelijk;
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            final PersoonModel persoon = (PersoonModel) obj;
            isGelijk =
                new EqualsBuilder().append(this.getSoort(), persoon.getSoort())
                    .append(this.getIdentificatienummers(), persoon.getIdentificatienummers())
                    .append(this.getID(), persoon.getID()).isEquals();
        }
        return isGelijk;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2).append(this.getSoort())
            .append(this.getIdentificatienummers()).append(this.getID()).hashCode();
    }

    /**
     * Functionele equals.
     *
     * @param obj object waarmee vergeleken moet worden.
     * @return of het object gelijk is aan dit object.
     */
    public boolean isGelijkAan(final Object obj) {
        // Er zit hier wel een verschil dat de ID niet meegenomen is in de vergelijking.
        final boolean resultaat;
        if (!(obj instanceof PersoonModel)) {
            resultaat = false;
        } else {
            if (this == obj) {
                resultaat = true;
            } else {
                final PersoonModel p = (PersoonModel) obj;
                resultaat =
                    new EqualsBuilder().append(this.getSoort(), p.getSoort())
                        .append(this.getIdentificatienummers(), p.getIdentificatienummers())
                        .append(this.getGeboorte(), p.getGeboorte())
                        .appendSuper(this.getSamengesteldeNaam().isGelijkAan(p.getSamengesteldeNaam()))
                        .isEquals();
            }
        }
        return resultaat;
    }

    @Override
    public boolean heeftNederlandseNationaliteit() {
        return heeftActueleNederlandseNationaliteit();
    }

    @Override
    public boolean isIngezetene() {
        return this.getBijhouding() != null && this.getBijhouding().getBijhoudingsaard() != null
            && this.getBijhouding().getBijhoudingsaard().getWaarde() == Bijhoudingsaard.INGEZETENE;
    }

    @Override
    public boolean isPersoonGelijkAan(final Persoon persoon) {
        return isGelijkAan(persoon);
    }
}
