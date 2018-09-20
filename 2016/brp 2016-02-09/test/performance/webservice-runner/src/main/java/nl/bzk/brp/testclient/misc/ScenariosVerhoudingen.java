/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;

/**
 * Wrapper klasse voor alle waardes m.b.t. scenarios verhoudingen.
 */
public class ScenariosVerhoudingen {

    private final int promilleVerhuizing;
    private final int promilleCorrectieAdres;
    private final int promilleHuwelijk;
    private final int promilleGeboorte;
    private final int promilleOverlijden;
    private final int promilleAdoptie;


    /**
     * Constructor waarin alle percentages meegegeven worden.
     *
     * @param percVerhuizing percentage verhuis berichten
     * @param percCorrectieAdres percentage correctie adres berichten
     * @param percHuwelijk percentage huwelijk berichten
     * @param percGeboorte percentage eerste inschrijving berichten
     * @param percOverlijden percentage overlijden berichten
     * @param percAdoptie percentage adoptie berichten
     */
    public ScenariosVerhoudingen(final double percVerhuizing, final double percCorrectieAdres,
                                 final double percHuwelijk,
                                 final double percGeboorte, final double percOverlijden, final double percAdoptie)
    {
        final double verhoudingenTotaal =
                percVerhuizing + percCorrectieAdres + percHuwelijk + percGeboorte + percOverlijden + percAdoptie;
        if (verhoudingenTotaal != 100.0) {
            throw new IllegalArgumentException("Verhoudingspercentages scenarios is niet gelijk aan 100%"
                                                       + " controleer de verhoudingen in de test-client properties. "
                                                       + "Huidige totaal is: " + verhoudingenTotaal + "%");
        }

        this.promilleVerhuizing = (int) (Eigenschappen.PROMILLE_FACTOR * percVerhuizing);
        this.promilleCorrectieAdres = (int) (Eigenschappen.PROMILLE_FACTOR * percCorrectieAdres);
        this.promilleHuwelijk = (int) (Eigenschappen.PROMILLE_FACTOR * percHuwelijk);
        this.promilleGeboorte = (int) (Eigenschappen.PROMILLE_FACTOR * percGeboorte);
        this.promilleOverlijden = (int) (Eigenschappen.PROMILLE_FACTOR * percOverlijden);
        this.promilleAdoptie = (int) (Eigenschappen.PROMILLE_FACTOR * percAdoptie);
    }

    public int getPromilleVerhuizing() {
        return promilleVerhuizing;
    }

    public int getPromilleCorrectieAdres() {
        return promilleCorrectieAdres;
    }

    public int getPromilleHuwelijk() {
        return promilleHuwelijk;
    }

    public int getPromilleGeboorte() {
        return promilleGeboorte;
    }

    public int getPromilleOverlijden() {
        return promilleOverlijden;
    }

    public int getPromilleAdoptie() {
        return promilleAdoptie;
    }
}
