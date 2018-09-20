/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonBasis;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractPersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortIndicatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;

/**
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(schema = "Kern", name = "Pers")
public class PersoonModel extends AbstractPersoonModel implements Persoon {

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param persoon Object type dat gekopieerd dient te worden.
     */
    public PersoonModel(final PersoonBasis persoon) {
        super(persoon);
    }

    /**
     * Default constructor. Vereist voor Hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonModel() {

    }

    /**
     * Retourneert true indien deze persoon betrokkenheden heeft.
     * @return True indien betrokkenheden aanwezig.
     */
    public boolean heeftBetrokkenheden() {
        return getBetrokkenheden() != null
               && !getBetrokkenheden().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon voornamen heeft.
     * @return True indien voornamen aanwezig.
     */
    public boolean heeftVoornamen() {
        return getPersoonVoornaam() != null
               && !getPersoonVoornaam().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon geslachtsnaam componenten heeft.
     * @return True indien geslachtsnaam componenten aanwezig.
     */
    public boolean heeftGeslachtsnaamcomponenten() {
        return getGeslachtsnaamcomponenten() != null
               && !getGeslachtsnaamcomponenten().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon nationaliteiten heeft.
     * @return True indien nationaliteiten aanwezig.
     */
    public boolean heeftNationaliteiten() {
        return getNationaliteiten() != null
               && !getNationaliteiten().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon een niet vervallen nederlandse nationaliteit heeft.
     * @return True indien hij nederlander is.
     */
    public boolean heeftActueleNederlandseNationaliteit() {
        boolean retval = false;
        for (PersoonNationaliteitModel nationaliteit : getNationaliteiten()) {
            if (nationaliteit.getNationaliteit().getNationaliteitcode().equals(
                    BrpConstanten.NL_NATIONALITEIT_CODE)
                && StatusHistorie.A == nationaliteit.getStatusHistorie())
            {
                retval = true;
                break;
            }
        }
        return retval;
    }

    /**
     * Retourneert true indien deze persoon indicaties heeft.
     * @return True indien indicaties aanwezig.
     */
    public boolean heeftIndicaties() {
        boolean retval = false;
        if (getIndicaties() != null) {
            for (PersoonIndicatieModel indicatie : getIndicaties()) {
                if (StatusHistorie.A == indicatie.getStatusHistorie()
                    && Ja.Ja == indicatie.getGegevens().getWaarde())
                {
                    retval = true;
                }
            }
        }
        return retval;
    }

    /**
     * Retourneert true als deze persoon een actuele indicatie heeft.
     * @param soortIndicatie de soort indicatie
     * @return true als deze actief neeft, false anders.
     */
    public boolean heeftActueleSoortIndicatie(final SoortIndicatie soortIndicatie) {
        boolean retval = false;
        for (PersoonIndicatieModel indicatie : getIndicaties()) {
            if (soortIndicatie == indicatie.getGegevens().getSoort()
                && StatusHistorie.A == indicatie.getStatusHistorie()
                && Ja.Ja == indicatie.getGegevens().getWaarde())
            {
                retval = true;
                break;
            }
        }
        return retval;
    }

    /**
     * Retourneert kind betrokkenheid van deze persoon.
     * @return Kind betrokkenheid.
     */
    @Transient
    public BetrokkenheidModel getKindBetrokkenHeid() {
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betrokkenheid.getRol()) {
                return betrokkenheid;
            }
        }
        return null;
    }

    /**
     * Retourneert kind betrokkenheden van deze persoon.
     * @return Kind betrokkenheden.
     */
    @Transient
    public Set<BetrokkenheidModel> getKindBetrokkenheden() {
        final TreeSet<BetrokkenheidModel> gesorteerdeSet = new TreeSet<BetrokkenheidModel>(
                new Comparator<BetrokkenheidModel>() {
                    @Override
                    public int compare(final BetrokkenheidModel betr1, final BetrokkenheidModel betr2) {
                        if (betr1.getId() == null || betr2.getId() == null) {
                            return betr1.getBetrokkene().getId().compareTo(betr2.getBetrokkene().getId());
                        } else {
                            return betr1.getId().compareTo(betr2.getId());
                        }
                    }
                });
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betrokkenheid.getRol()) {
                gesorteerdeSet.add(betrokkenheid);
            }
        }
        return gesorteerdeSet;
    }

    /**
     * Retourneert ouder betrokkenheden van deze persoon.
     * @return Ouder betrokkenheden.
     */
    @Transient
    public Set<BetrokkenheidModel> getOuderBetrokkenheden() {
        final TreeSet<BetrokkenheidModel> gesorteerdeSet = new TreeSet<BetrokkenheidModel>(
                new Comparator<BetrokkenheidModel>() {
                    @Override
                    public int compare(final BetrokkenheidModel betr1, final BetrokkenheidModel betr2) {
                        if (betr1.getId() == null || betr2.getId() == null) {
                            return betr1.getBetrokkene().getId().compareTo(betr2.getBetrokkene().getId());
                        } else {
                            return betr1.getId().compareTo(betr2.getId());
                        }
                    }
                });
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betrokkenheid.getRol()) {
                gesorteerdeSet.add(betrokkenheid);
            }
        }
        return gesorteerdeSet;
    }

    /**
     * Retourneert partner betrokkenheden van deze persoon.
     * @return Partner betrokkenheden.
     */
    @Transient
    public Set<BetrokkenheidModel> getPartnerBetrokkenHeden() {
        final TreeSet<BetrokkenheidModel> gesorteerdeSet = new TreeSet<BetrokkenheidModel>(
                new Comparator<BetrokkenheidModel>() {
                    @Override
                    public int compare(final BetrokkenheidModel betr1, final BetrokkenheidModel betr2) {
                        if (betr1.getId() == null || betr2.getId() == null) {
                            return betr1.getBetrokkene().getId().compareTo(betr2.getBetrokkene().getId());
                        } else {
                            return betr1.getId().compareTo(betr2.getId());
                        }
                    }
                });
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (SoortBetrokkenheid.PARTNER == betrokkenheid.getRol()) {
                gesorteerdeSet.add(betrokkenheid);
            }
        }
        return gesorteerdeSet;
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     * @param betr Betrokkenheid.
     */
    protected void voegBetrokkenheidToe(final BetrokkenheidModel betr) { }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     * @param betr Betrokkenheid.
     */
    protected void voegBetrokkenhedenToe(final Set<BetrokkenheidModel> betr) { }

    /**
     * Retourneert de indicatie verstrekkings beperking indien aanwezig.
     * @return Indicatie.
     */
    protected PersoonIndicatieModel getIndicatieVerstrekkingsbeperking() {
        if (getIndicaties() != null) {
            for (PersoonIndicatieModel ind : getIndicaties()) {
                if (SoortIndicatie.VERSTREKKINGSBEPERKING == ind.getGegevens().getSoort()) {
                    return ind;
                }
            }
        }
        return null;
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     * @param ind Indicatie.
     */
    protected void voegPersoonIndicatieToe(final PersoonIndicatieModel ind) { }

    /**
     * Retourneert of persoon een verstrekkingsbeperking heeft.
     * @return true of false.
     */
    protected boolean heeftVerstrekkingsBeperking() {
        return getIndicatieVerstrekkingsbeperking() != null;
    }

}
