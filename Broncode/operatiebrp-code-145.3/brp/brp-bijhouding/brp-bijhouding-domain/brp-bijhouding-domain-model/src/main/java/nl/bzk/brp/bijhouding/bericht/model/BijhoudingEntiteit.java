/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;

/**
 * Definieert de functionaliteit voor classes die entiteiten decoreren met bijhouding functionaliteit.
 */
public interface BijhoudingEntiteit {

    /**
     * Geef de dynamische stamtabel repository.
     * @return de dynamische stamtabel repository
     */
    static DynamischeStamtabelRepository getDynamischeStamtabelRepository() {
        return ApplicationContextProvider.getDynamischeStamtabelRepository();
    }

    /**
     * Voegt verantwoording toe aan het materiele historie voorkomen en voegt dit voorkomen vervolgens toe aan de lijst met voorkomens.
     * @param materieleHistorie vookomen met materiele historie
     * @param actie de actie die moet worden toegevoegd als actie inhoud
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param voorkomens de set met voorkomens waar het nieuwe voorkomen aan moet worden toegevoegd
     */
    static <T extends MaterieleHistorie> void voegMaterieleHistorieToe(final T materieleHistorie, final BRPActie actie, final int datumAanvangGeldigheid,
                                                                       final Set<T> voorkomens) {
        materieleHistorie.setActieInhoud(actie);
        materieleHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        materieleHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        MaterieleHistorie.voegNieuweActueleToe(materieleHistorie, voorkomens);
    }

    /**
     * Voegt PersoonIndicatieHistorie toe volgens het materiele of formele/historie patroon. patroon word bepaald aan de hand van {@link SoortIndicatie}
     * @param historie PersoonsIndicatie
     * @param actie actie
     * @param datumAanvangGeldigheid datum
     * @param voorkomens alle voorkomens
     */
    static void voegPersoonIndicatieHistorieToe(
            final PersoonIndicatieHistorie historie,
            final BRPActie actie,
            final Integer datumAanvangGeldigheid,
            final Set<PersoonIndicatieHistorie> voorkomens) {
        if (historie.getPersoonIndicatie().getSoortIndicatie().isMaterieleHistorieVanToepassing()) {
            voegMaterieleHistorieToe(historie, actie, datumAanvangGeldigheid, voorkomens);
        } else {
            historie.setActieInhoud(actie);
            historie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
            FormeleHistorie.voegToe(historie, voorkomens);
        }
    }

    /**
     * Voegt verantwoording toe aan het formele historie voorkomen en voegt dit voorkomen vervolgens toe aan de lijst met voorkomens.
     * @param formeleHistorie vookomen met formele historie
     * @param actie de actie die moet worden toegevoegd als actie inhoud
     * @param voorkomens de set met voorkomens waar het nieuwe voorkomen aan moet worden toegevoegd
     * @param <T> het type formeleHistorie
     * @throws IllegalArgumentException wanneer dit een voorkomen met Materiele historie betreft
     */
    static <T extends FormeleHistorie> void voegFormeleHistorieToe(final T formeleHistorie, final BRPActie actie, final Set<T> voorkomens) {
        if (formeleHistorie instanceof MaterieleHistorie) {
            throw new IllegalArgumentException("Deze methode voegt alleen formele verantwoording toe maar het voorkomen heeft materiele historie.");
        }
        formeleHistorie.setActieInhoud(actie);
        formeleHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        FormeleHistorie.voegToe(formeleHistorie, voorkomens);
    }

    /**
     * Laat het actuele voorkomen vervallen in de set van voorkomens. Ter verantwoording wordt de gegeven actie gebruikt. Als de set geen actueel voorkomen
     * bevat dan doet deze methode niets.
     * @param voorkomens de set met voorkomens waarvan het actuele voorkomen moet vervallen
     * @param actie actie waarmee het actuele voorkomen moet vervallen
     * @param <T> het type historie
     */
    static <T extends FormeleHistorie> void laatActueelVoorkomenVervallen(final Set<T> voorkomens, final BRPActie actie) {
        FormeleHistorie.laatActueelVoorkomenVervallen(voorkomens, actie);
    }
}
