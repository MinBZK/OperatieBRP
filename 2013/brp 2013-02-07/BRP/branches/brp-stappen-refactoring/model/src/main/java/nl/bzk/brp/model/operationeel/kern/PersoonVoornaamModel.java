/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonVoornaamModel;
import org.apache.commons.lang.builder.CompareToBuilder;


/**
 * Voornaam van een Persoon
 * <p/>
 * Voornamen worden in de BRP los van elkaar geregistreerd. Het LO BRP is voorbereid op het kunnen vastleggen van
 * voornamen zoals 'Jan Peter', 'Aberto di Maria' of 'Wonder op aarde' als ??n enkele voornaam. In de BRP is het
 * namelijk niet noodzakelijk (conform LO 3.x) om de verschillende woorden aan elkaar te plakken met een koppelteken.
 * <p/>
 * Het gebruik van de spatie als koppelteken is echter (nog) niet toegestaan.
 * <p/>
 * Indien er sprake is van een namenreeks wordt dit opgenomen als geslachtsnaam; er is dan geen sprake van een
 * Voornaam.
 * <p/>
 * Een voornaam mag voorlopig nog geen spatie bevatten.
 * Hiertoe dient eerst de akten van burgerlijke stand aangepast te worden (zodat voornamen individueel kunnen worden
 * vastgelegd, en er geen interpretatie meer nodig is van de ambtenaar over waar de ene voornaam eindigt en een tweede
 * begint).
 * Daarnaast is er ook nog geen duidelijkheid over de wijze waarop bestaande namen aangepast kunnen worden: kan de
 * burger hier simpelweg om verzoeken en wordt het dan aangepast?
 * <p/>
 * De BRP is wel al voorbereid op het kunnen bevatten van spaties.
 * RvdP 5 augustus 2011
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
@Table(schema = "Kern", name = "PersVoornaam")
public class PersoonVoornaamModel extends AbstractPersoonVoornaamModel
    implements PersoonVoornaam, Comparable<PersoonVoornaamModel>
{

    /** Standaard constructor (t.b.v. Hibernate/JPA). */
    protected PersoonVoornaamModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert.
     *
     * @param persoon persoon van Persoon \ Voornaam.
     * @param volgnummer volgnummer van Persoon \ Voornaam.
     */
    public PersoonVoornaamModel(final PersoonModel persoon, final Volgnummer volgnummer) {
        super(persoon, volgnummer);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVoornaam Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public PersoonVoornaamModel(final PersoonVoornaam persoonVoornaam, final PersoonModel persoon) {
        super(persoonVoornaam, persoon);
    }

    @Override
    public int compareTo(PersoonVoornaamModel o) {
        return new CompareToBuilder().append(getVolgnummer(), o.getVolgnummer()).toComparison();
    }
}
