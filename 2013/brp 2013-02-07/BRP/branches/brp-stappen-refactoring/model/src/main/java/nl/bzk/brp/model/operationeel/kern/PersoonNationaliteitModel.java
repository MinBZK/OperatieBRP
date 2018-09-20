/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonNationaliteitModel;
import org.apache.commons.lang.builder.CompareToBuilder;


/**
 * De juridische band tussen een persoon en een staat zoals bedoeld in het Europees verdrag inzake nationaliteit,
 * Straatsburg 06-11-1997.
 *
 * Indien iemand tegelijk meerdere nationaliteiten heeft, zijn dit ook aparte exemplaren van Nationaliteit. Indien
 * aannemelijk is dat iemand een Nationaliteit heeft, maar deze is onbekend, dat wordt dit vastgelegd als 'Onbekend'.
 * Situaties als 'behandeld als Nederlander', 'Vastgesteld niet-Nederlander' en 'Statenloos' worden geregistreerd onder
 * 'overige indicaties', en niet als Nationaliteit. .
 *
 * 1. Waarden 'Vastgesteld niet-Nederlander', 'Behandelen als Nederlander' en 'Statenloos' worden niet geregistreerd als
 * Nationaliteit, maar onder een aparte groep. Motivatie voor het apart behandelen van 'Vastgesteld niet-Nederlander',
 * is dat het een expliciete uitspraak is (van een rechter), dat iemand geen Nederlander (meer) is. Deze waarde kan best
 * n??st bijvoorbeeld een Belgische Nationaliteit gelden, en moet niet worden gezien als 'deelinformatie' over de
 * Nationaliteit. De 'Behandelen als Nederlander' gaat over de wijze van behandeling, en past dientengevolge minder goed
 * als waarde voor 'Nationaliteit'. Als er (vermoedelijk) wel een Nationaliteit is, alleen die is onbekend, d?n wordt
 * 06-11-1997), en dan de Nederlandse vertaling ervan: ""de juridische band tussen een persoon en een Staat; deze term
 * verwijst niet naar de etnische afkomst van de persoon"". Deze zin loop niet heel lekker en is ook niet ??nduidig (er
 * is ook een juridische band tussen een president van een Staat en die Staat ten gevolge van het presidentschap, en die
 * wordt niet bedoeld). Daarom gekozen voor deze definitie met verwijzing. Verder goed om in de toelichting te
 * beschrijven hoe wordt omgegaan met meerdere Nationaliteiten (ook wel dubbele Nationaliteiten genoemd), dan wel
 * statenloosheid en vastgesteld niet-nederlander.
 *
 * HUP text: omgaan met niet erkende nationaliteiten (bijv. palestijnse authoriteit)
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:35 CET 2012.
 */
@Entity
@Table(schema = "Kern", name = "PersNation")
public class PersoonNationaliteitModel extends AbstractPersoonNationaliteitModel
    implements PersoonNationaliteit, Comparable<PersoonNationaliteitModel>
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonNationaliteitModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert.
     *
     * @param persoon persoon van Persoon \ Nationaliteit.
     * @param nationaliteit nationaliteit van Persoon \ Nationaliteit.
     */
    public PersoonNationaliteitModel(final PersoonModel persoon, final Nationaliteit nationaliteit) {
        super(persoon, nationaliteit);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonNationaliteit Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public PersoonNationaliteitModel(final PersoonNationaliteit persoonNationaliteit, final PersoonModel persoon) {
        super(persoonNationaliteit, persoon);
    }

    @Override
    public int compareTo(final PersoonNationaliteitModel o) {
        // TODO Tijdelijk workaround om ervoor te zorgen dat er twee nieuwe objecten aan een SortedSet toegevoegd kan worden.
        // Zonder de workaround denkt de lijst dat een object zonder ID hetzelfde object is.
        if (getID() == null && o.getID() == null) {
            return 1;
        }

        return new CompareToBuilder().append(getID(), o.getID()).toComparison();
    }
}
