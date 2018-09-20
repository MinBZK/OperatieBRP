/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.sql.Timestamp;
import java.util.Comparator;

/**
 * Interface voor rijen in BRP met formele historie (datum/tijd registratie/verval).
 */
public interface FormeleHistorie extends DeltaEntiteit {

    /**
     * Sorteert de lijst van formelehistorie o.b.v. de datumtijd registratie van oud naar nieuw.
     */
    Comparator<FormeleHistorie> COMPARATOR = new Comparator<FormeleHistorie>() {

        @Override
        public int compare(final FormeleHistorie historie1, final FormeleHistorie historie2) {
            return historie1.getDatumTijdRegistratie().compareTo(historie2.getDatumTijdRegistratie());
        }
    };

    /**
     * Geef de waarde van datum tijd registratie.
     *
     * @return datum tijd registratie
     */
    Timestamp getDatumTijdRegistratie();

    /**
     * Zet de waarde van datum tijd registratie.
     *
     * @param datumTijdRegistratie
     *            de datumtijd registratie die gezet moet worden
     */
    void setDatumTijdRegistratie(Timestamp datumTijdRegistratie);

    /**
     * Geef de waarde van datum tijd verval.
     *
     * @return datum tijd verval
     */
    Timestamp getDatumTijdVerval();

    /**
     * Zet de waarde van datum tijd verval.
     *
     * @param datumTijdVerval
     *            datum/tijd verval voor dit record
     */
    void setDatumTijdVerval(Timestamp datumTijdVerval);

    /**
     * Geef de waarde van actie inhoud.
     *
     * @return actie inhoud
     */
    BRPActie getActieInhoud();

    /**
     * Zet de waarde van actie inhoud.
     *
     * @param actieInhoud
     *            de actie inhoud voor dit record
     */
    void setActieInhoud(BRPActie actieInhoud);

    /**
     * Geef de waarde van actie verval.
     *
     * @return actie verval
     */
    BRPActie getActieVerval();

    /**
     * Zet de waarde van actie verval.
     *
     * @param actieVerval
     *            actie verval
     */
    void setActieVerval(BRPActie actieVerval);

    /**
     * Geef de waarde van nadere aanduiding verval.
     *
     * @return nadere aanduiding verval
     */
    Character getNadereAanduidingVerval();

    /**
     * Zet de waarde van nadere aanduiding verval.
     *
     * @param nadereAanduidingVerval
     *            nadere aanduiding verval
     */
    void setNadereAanduidingVerval(Character nadereAanduidingVerval);

    /**
     * Geef de actueel.
     *
     * @return true als deze historie als actueel beschouwd dient te worden
     */
    boolean isActueel();

    /**
     * Geef de vervallen.
     *
     * @return true als deze historie is vervallen, anders false
     */
    boolean isVervallen();

    /**
     * Geef de waarde van actieVervalTbvLeveringMutaties.
     *
     * @return actieVervalTbvLeveringMutaties
     */
    BRPActie getActieVervalTbvLeveringMutaties();

    /**
     * Zet de waarde van actieVervalTbvLeveringMutaties.
     *
     * @param actieVervalTbvLeveringMutaties
     *            actie verval tbv levering mutaties
     */
    void setActieVervalTbvLeveringMutaties(final BRPActie actieVervalTbvLeveringMutaties);

    /**
     * Geef de waarde van indicatieVoorkomenTbvLeveringMutaties. Null als niet van toepassing, anders true.
     *
     * @return isVoorkomenTbvLeveringMutaties
     */
    Boolean getIndicatieVoorkomenTbvLeveringMutaties();

    /**
     * Zet de waarde voor indicatieVoorkomenTbvLeveringMutaties.
     *
     * @param indicatieVoorkomenTbvLeveringMutaties
     *            <code>NULL</code> als niet van toepassen, anders <code>true</code>
     * @throws IllegalArgumentException
     *             als false wordt meegegeven
     */
    void setIndicatieVoorkomenTbvLeveringMutaties(final Boolean indicatieVoorkomenTbvLeveringMutaties);

    /**
     * @return true als dit voorkomen is toegevoegd tbv het correct leveren igv mutaties, anders false
     */
    boolean isVoorkomenTbvLeveringMutaties();
}
