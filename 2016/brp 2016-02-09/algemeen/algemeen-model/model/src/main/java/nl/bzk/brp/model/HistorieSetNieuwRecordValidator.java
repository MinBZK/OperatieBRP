/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;
import nl.bzk.brp.model.basis.VerantwoordingsEntiteit;


/**
 * Een utility klasse met enkele static methodes voor het valideren van nieuwe records voor onze 'smart sets'.
 */
public final class HistorieSetNieuwRecordValidator {

    /**
     * Constructor.
     */
    private HistorieSetNieuwRecordValidator() {
    }

    /**
     * Valideer een nieuw formele historie record. Als een van de pre-condities wordt geschonden, zal deze methode een exceptie gooien.
     *
     * @param <T>         Historisch formeel en verantwoordbaar entiteit
     * @param nieuwRecord het nieuwe formele historie record
     */
    public static <T extends FormeelHistorisch & FormeelVerantwoordbaar> void valideerNieuwFormeleHistorieRecord(
        final T nieuwRecord)
    {
        // Valideer het record zelf.
        valideerNietNull(nieuwRecord, "Een nieuw record");
        valideerNietNull(nieuwRecord.getFormeleHistorie(), "De formele historie van een nieuw record");

        // Valideer de historie van het record.
        final FormeleHistorieModel formeleHistorie = nieuwRecord.getFormeleHistorie();
        valideerDatumTijdRegistratieVerval(formeleHistorie);

        // Valideer de bijbehorende acties.
        valideerActieInhoudVerval(nieuwRecord.getVerantwoordingInhoud(), nieuwRecord.getVerantwoordingVerval());
    }

    /**
     * Valideer een nieuw materiele historie record. Als een van de pre-condities wordt geschonden, zal deze methode een exceptie gooien.
     *
     * @param <T>         Materieel historisch en verantwoordbaar entiteit.
     * @param nieuwRecord het nieuwe materiele historie record
     */
    public static <T extends MaterieelHistorisch & MaterieelVerantwoordbaar> void valideerNieuwMaterieleHistorieRecord(
        final T nieuwRecord)
    {
        // Valideer het record zelf.
        valideerNietNull(nieuwRecord, "Een nieuw record");
        valideerNietNull(nieuwRecord.getMaterieleHistorie(), "De materiele historie van een nieuw record");

        // Valideer de historie van het record.
        final MaterieleHistorieModel materieleHistorie = nieuwRecord.getMaterieleHistorie();
        valideerDatumTijdRegistratieVerval(materieleHistorie);
        valideerNietNull(materieleHistorie.getDatumAanvangGeldigheid(),
            "De datum aanvang geldigheid van een nieuw record");

        // Valideer de bijbehorende acties.
        valideerActieInhoudVerval(nieuwRecord.getVerantwoordingInhoud(), nieuwRecord.getVerantwoordingVerval());
        valideerNull(nieuwRecord.getVerantwoordingAanpassingGeldigheid(),
            "De verantwoording aanpassing geldigheid van een nieuw record");
    }

    /**
     * Valideer de regels rond datum tijd registratie (mag niet null zijn) en verval (moet null zijn).
     *
     * @param formeleHistorie een FormeleHistorieModel object, kan ook concrete klasse MaterieleHisorieModel zijn
     */
    private static void valideerDatumTijdRegistratieVerval(final FormeleHistorieModel formeleHistorie) {
        valideerNietNull(formeleHistorie.getTijdstipRegistratie(), "De datum tijd registratie van een nieuw record");
        valideerNull(formeleHistorie.getDatumTijdVerval(), "De datum tijd verval van een nieuw record");
    }

    /**
     * Valideer de regels rond actie inhoud (mag niet null zijn) en actie verval (moet null zijn).
     *
     * @param verantwInhoud verantwoording inhoud
     * @param verantwVerval verantwoording verval
     */
    private static void valideerActieInhoudVerval(final VerantwoordingsEntiteit verantwInhoud,
        final VerantwoordingsEntiteit verantwVerval)
    {
        valideerNietNull(verantwInhoud, "De verantwoording inhoud van een nieuw record");
        valideerNull(verantwVerval, "De verantwoording verval van een nieuw record");
    }

    /**
     * Valideer dat een bepaald object niet null mag zijn. Indien dat wel zo is, wordt een IllegalArgumentException gegooid.
     *
     * @param object       het object
     * @param beschrijving de beschrijving van het object
     */
    private static void valideerNietNull(final Object object, final String beschrijving) {
        if (object == null) {
            throw new IllegalArgumentException(beschrijving + " mag niet null zijn");
        }
    }

    /**
     * Valideer dat een bepaald object null moet zijn. Indien dat niet zo is, wordt een IllegalArgumentException gegooid.
     *
     * @param object       het object
     * @param beschrijving de beschrijving van het object
     */
    private static void valideerNull(final Object object, final String beschrijving) {
        if (object != null) {
            throw new IllegalArgumentException(beschrijving + " moet null zijn");
        }
    }

}
