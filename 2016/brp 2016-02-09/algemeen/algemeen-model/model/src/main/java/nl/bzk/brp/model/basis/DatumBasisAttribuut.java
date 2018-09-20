/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.Date;

/**
 * Basis interface voor datum attributen (typen).
 */
public interface DatumBasisAttribuut extends Attribuut<Integer>, Comparable<Attribuut<Integer>> {

    /**
     * Test of deze datum na de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum na de opgegeven datum ligt.
     */
    boolean na(final DatumBasisAttribuut vergelijkingsDatum);

    /**
     * Test of deze datum na of op vergelijkingsDatum ligt.
     *
     * @param vergelijkingsDatum vergelijkingsDatum.
     * @return of deze datum na of op vergelijkingsDatum ligt.
     */
    boolean naOfOp(final DatumBasisAttribuut vergelijkingsDatum);

    /**
     * Test of deze datum voor of op vergelijkingsDatum ligt.
     *
     * @param vergelijkingsDatum vergelijkingsDatum.
     * @return of deze datum voor of op vergelijkingsDatum ligt.
     */
    boolean voorOfOp(final DatumBasisAttribuut vergelijkingsDatum);

    /**
     * Test of deze datum voor de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum voor de opgegeven datum ligt.
     */
    boolean voor(final DatumBasisAttribuut vergelijkingsDatum);

    /**
     * Test of deze datum dezelfde datum is als de opgegeven datum.
     *
     * @param vergelijkingsDatum de datum
     * @return true of false
     */
    boolean op(final DatumBasisAttribuut vergelijkingsDatum);

    /**
     * Geeft het aantal dagen verschil aan tussen 2 datums. Dit aantal is altijd een positief (niet-negatief) getal!
     *
     * @param vergelijkingsDatum de datum
     * @return het verschil in dagen
     */
    int aantalDagenVerschil(final DatumBasisAttribuut vergelijkingsDatum);

    /**
     * Test of dit een volledig gevulde DatumAttribuut is (dus maand <> 0 en dag <> 0).
     * LET OP:  bij een null datum, wordt de waarde als "GOED" gekeurd, deze validatie dient elders te gebeuren.
     *
     * @return true als volledig (of null), false als deels onbekend.
     */
    boolean isVolledigDatumWaarde();

    /**
     * Controleert of dit een geldige kalenderdatum is.
     *
     * @return true als geldig, anders false.
     */
    boolean isGeldigeKalenderdatum();

    /**
     * Test of een datum geldig is binnen een periode die gemarkeerd is met twee datums.
     *
     * @param datumAanvangPeriode de datum aanvang periode
     * @param datumEindePeriode de datum einde periode
     * @return true als geldig, anders false.
     */
    boolean isGeldigTussen(DatumBasisAttribuut datumAanvangPeriode, DatumBasisAttribuut datumEindePeriode);

    /**
     * Vergelijk of DatumAttribuut 1 ligt op of voor datum2. Beide datums kunnen deels onbekend zijn en er wordt soepel mee gerekend
     * <p/>
     * Let op dat geen null pointers zijn.
     *
     * @param vergelijkDatum de vergelijk datum
     * @return true als datum voor of op vergelijkingsdatum ligt.
     */
    boolean voorOfOpDatumSoepel(final DatumBasisAttribuut vergelijkDatum);

    /**
     * Vergelijk of DatumAttribuut 1 ligt voor datum2. Beide datums kunnen deels onbekend zijn en er wordt soepel mee gerekend
     * <p/>
     * Let op dat geen null pointers zijn.
     *
     * @param vergelijkDatum the vergelijk datum
     * @return the boolean
     */
    boolean voorDatumSoepel(final DatumBasisAttribuut vergelijkDatum);



    /**
     * Geeft jaar terug in de DatumAttribuut of 0 als die niet bepaalt kan worden.
     *
     * @return jaar (0- )
     */
    int getJaar();

    /**
     * Geeft maand terug in de datum, of 0 als die niet bepaalt kan worden.
     *
     * @return maand (0-12)
     */
    int getMaand();

    /**
     * Geeft dag terug in de datum, of 0 als die niet bepaalt kan worden.
     *
     * @return dag (0-31)
     */
    int getDag();

    /**
     * Geeft de integer waarde terug van de datum. Alleen omdat checkstyle geen inline conditionals toestaat ( (boolean) ? 1 : 2 ).
     *
     * @param defaultWaarde de default waarde
     * @return de int waarde van de datum.
     */
    int getIntWaarde(int defaultWaarde);

    /**
     * Geeft de integer waarde terug van de datum. Alleen omdat checkstyle geen inline conditionals toestaat ( (boolean) ? 1 : 2 ). Als er geen waarde
     * aanwezig is, dan wordt de minimale waarde teruggegeven.
     *
     * @return de int waarde van de datum.
     */
    int getIntWaardeOfMin();

    /**
     * Geeft de integer waarde terug van de datum. Alleen omdat checkstyle geen inline conditionals toestaat ( (boolean) ? 1 : 2 ). Als er geen waarde
     * aanwezig is, dan wordt de maximale waarde teruggegeven.
     *
     * @return de int waarde van de datum.
     */
    int getIntWaardeOfMax();

    /**
     * Converteer volledig (aka. kalender) DatumAttribuut naar een date.
     *
     * @return een java.util.date object.
     */
    Date toDate();
}
