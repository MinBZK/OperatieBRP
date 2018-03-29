/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * Interface voor rijen in BRP met formele historie (datum/tijd registratie/verval) inclusief
 * verantwoording met Acties.
 */
public interface FormeleHistorie extends FormeleHistorieZonderVerantwoording {

    /**
     * Geef de waarde van actie inhoud van FormeleHistorie.
     * @return de waarde van actie inhoud van FormeleHistorie
     */
    BRPActie getActieInhoud();

    /**
     * Zet de waarden voor actie inhoud van FormeleHistorie.
     * @param actieInhoud de nieuwe waarde voor actie inhoud van FormeleHistorie
     */
    void setActieInhoud(BRPActie actieInhoud);

    /**
     * Geef de waarde van actie verval van FormeleHistorie.
     * @return de waarde van actie verval van FormeleHistorie
     */
    BRPActie getActieVerval();

    /**
     * Zet de waarden voor actie verval van FormeleHistorie.
     * @param actieVerval de nieuwe waarde voor actie verval van FormeleHistorie
     */
    void setActieVerval(BRPActie actieVerval);

    /**
     * Geef de waarde van actie verval tbv levering mutaties van FormeleHistorie.
     * @return de waarde van actie verval tbv levering mutaties van FormeleHistorie
     */
    BRPActie getActieVervalTbvLeveringMutaties();

    /**
     * Zet de waarden voor actie verval tbv levering mutaties van FormeleHistorie.
     * @param actieVervalTbvLeveringMutaties de nieuwe waarde voor actie verval tbv levering mutaties van FormeleHistorie
     */
    void setActieVervalTbvLeveringMutaties(BRPActie actieVervalTbvLeveringMutaties);

    /**
     * Geef de waarde van indicatie voorkomen tbv levering mutaties van FormeleHistorie.
     * @return de waarde van indicatie voorkomen tbv levering mutaties van FormeleHistorie
     */
    Boolean getIndicatieVoorkomenTbvLeveringMutaties();

    /**
     * Zet de waarden voor indicatie voorkomen tbv levering mutaties van FormeleHistorie.
     * @param indicatieVoorkomenTbvLeveringMutaties de nieuwe waarde voor indicatie voorkomen tbv levering mutaties van FormeleHistorie
     */
    void setIndicatieVoorkomenTbvLeveringMutaties(Boolean indicatieVoorkomenTbvLeveringMutaties);

    /**
     * Checks if is voorkomen tbv levering mutaties.
     * @return true, if is voorkomen tbv levering mutaties
     */
    boolean isVoorkomenTbvLeveringMutaties();

    /**
     * Geef de waarde van nadere aanduiding verval van FormeleHistorieZonderVerantwoording.
     * @return de waarde van nadere aanduiding verval van FormeleHistorieZonderVerantwoording
     */
    Character getNadereAanduidingVerval();

    /**
     * Zet de waarden voor nadere aanduiding verval van FormeleHistorieZonderVerantwoording.
     * @param nadereAanduidingVerval de nieuwe waarde voor nadere aanduiding verval van FormeleHistorieZonderVerantwoording
     */
    void setNadereAanduidingVerval(Character nadereAanduidingVerval);

    /**
     * Laat dit voorkomen vervallen naar de formele historie. Optioneel kan een nadere aanduiding verval worden meegegeven.
     *
     * @param actie de verantwoording voor het verval
     * @param nadereAanduidingVerval nadere aanduiding verval
     */
    void laatVervallen(BRPActie actie, Character nadereAanduidingVerval);

    /**
     * Voegt een nieuwVoorkomen met formele historie toe aan de gegeven lijst met voorkomens. Dit
     * nieuwVoorkomen dient aan de voorwaarden te voldoen die worden gecontroleerd door
     * {@link FormeleHistorie#valideerActueelVoorkomen}.
     * @param <E> het type historie
     * @param nieuwVoorkomen het nieuwVoorkomen dat moet worden toegevoegd
     * @param voorkomens de lijst met voorkomens dat moet worden uitgebreid met het gegeven nieuwVoorkomen
     * @throws IllegalArgumentException wanneer het nieuwe voorkomen materiele historie bevat
     * @see MaterieleHistorie#voegNieuweActueleToe(MaterieleHistorie, Set)
     */
    static <E extends FormeleHistorie> void voegToe(final E nieuwVoorkomen, final Set<E> voorkomens) {
        valideerTypeVoorkomen(nieuwVoorkomen);
        valideerActueelVoorkomen(nieuwVoorkomen);
        final E bestaandActueelVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(voorkomens);
        if (bestaandActueelVoorkomen != null) {
            if (bestaandActueelVoorkomen.getDatumTijdRegistratie().equals(nieuwVoorkomen.getDatumTijdRegistratie())) {
                // Dit is het geval als 2 acties een nieuw voorkomen toevoegen op hetzelfde tsreg.
                // We verwijderen het vorige nieuw toegevoegde voorkomen, want het nieuwe voorkomen
                // is van de laatste
                // actie! Ook moeten we overal waar deze actieInhoud als actieVerval is gebruikt
                // aanpassen naar de
                // nieuwe actieInhoud
                voorkomens.remove(bestaandActueelVoorkomen);
                for (final E voorkomen : voorkomens) {
                    if (voorkomen.getActieVerval() != null && voorkomen.getActieVerval() == bestaandActueelVoorkomen.getActieInhoud()) {
                        voorkomen.setActieVerval(nieuwVoorkomen.getActieInhoud());
                    }
                }
            } else {
                bestaandActueelVoorkomen.setDatumTijdVerval(nieuwVoorkomen.getDatumTijdRegistratie());
                bestaandActueelVoorkomen.setActieVerval(nieuwVoorkomen.getActieInhoud());
            }
        }
        voorkomens.add(nieuwVoorkomen);
    }

    /**
     * Laat het actuele voorkomen vervallen in de set van voorkomens. Ter verantwoording wordt de gegeven actie gebruikt. Als de set geen actueel voorkomen
     * bevat dan doet deze methode niets.
     * @param voorkomens de set met voorkomens waarvan het actuele voorkomen moet vervallen
     * @param actie actie waarmee het actuele voorkomen moet vervallen
     * @param <E> het type historie
     */
    static <E extends FormeleHistorie> void laatActueelVoorkomenVervallen(final Set<E> voorkomens, final BRPActie actie) {
        final E bestaandActueelVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(voorkomens);
        if (bestaandActueelVoorkomen != null) {
            bestaandActueelVoorkomen.setDatumTijdVerval(actie.getDatumTijdRegistratie());
            bestaandActueelVoorkomen.setActieVerval(actie);
        }
    }

    /**
     * Controleert of het nieuwe voorkomen geen Materiele Historie is.
     * @param nieuwVoorkomen het nieuwe voorkomen
     * @param <E> het type historie
     */
    static <E extends FormeleHistorie> void valideerTypeVoorkomen(final E nieuwVoorkomen) {
        if (nieuwVoorkomen instanceof MaterieleHistorie && !(nieuwVoorkomen instanceof PersoonIndicatieHistorie) ) {
            throw new IllegalArgumentException("Deze validatie mag alleen op FormeleHistorie worden uitgevoerd.");
        }
        if (nieuwVoorkomen instanceof PersoonIndicatieHistorie
                && ((PersoonIndicatieHistorie)nieuwVoorkomen).getPersoonIndicatie().getSoortIndicatie().isMaterieleHistorieVanToepassing()) {
            throw new IllegalArgumentException("Deze validatie mag alleen op FormeleHistorie worden uitgevoerd.");
        }
    }

    /**
     * Controleert of het nieuwe voorkomen valide is. Dit voorkomen dient aan de volgende
     * voorwaarden te voldoen:
     * <ul>
     * <li>het nieuwVoorkomen is niet null</li>
     * <li>het nieuwVoorkomen is actueel, dus datumTijdRegistratie en actieInhoud moeten gevuld zijn
     * en tsVerval en actieVerval moeten null zijn</li>
     * </ul>
     * @param nieuwVoorkomen het nieuwe voorkomen
     * @param <E> het type historie
     */
    static <E extends FormeleHistorie> void valideerActueelVoorkomen(final E nieuwVoorkomen) {
        ValidationUtils.controleerOpNullWaarden("nieuwVoorkomen mag niet null zijn", nieuwVoorkomen);
        ValidationUtils.controleerOpNullWaarden("nieuwVoorkomen.datumTijdRegistratie mag niet null zijn", nieuwVoorkomen.getDatumTijdRegistratie());
        ValidationUtils.controleerOpNullWaarden("nieuwVoorkomen.actieInhoud mag niet null zijn", nieuwVoorkomen.getActieInhoud());
        if (nieuwVoorkomen.getDatumTijdVerval() != null) {
            throw new IllegalArgumentException("nieuwVoorkomen.tsverval moet leeg zijn voor een actueel nieuwVoorkomen");
        }
        if (nieuwVoorkomen.getActieVerval() != null) {
            throw new IllegalArgumentException("nieuwVoorkomen.actieVerval moet leeg zijn voor een actueel nieuwVoorkomen");
        }
    }

}
