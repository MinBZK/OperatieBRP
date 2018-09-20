/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonGeslachtsnaamcomponentModel;
import org.apache.commons.lang.builder.CompareToBuilder;


/**
 * Component van de Geslachtsnaam van een Persoon
 *
 * De geslachtsnaam van een Persoon kan uit meerdere delen bestaan, bijvoorbeeld ten gevolge van een namenreeks. Ook kan
 * er sprake zijn van het voorkomen van meerdere geslachten, die in de geslachtsnaam terugkomen. In dat geval valt de
 * Geslachtsnaam uiteen in meerdere Geslachtsnaamcomponenten. Een Geslachtsnaamcomponent bestaat vervolgens
 * mogelijkerwijs uit meerdere onderdelen, waaronder Voorvoegsel en Naamdeel. Zie verder toelichting bij Geslachtsnaam.
 *
 * 1. Vooruitlopend op liberalisering namenwet, waarbij het waarschijnlijk mogelijk wordt om de (volledige)
 * geslachtsnaam van een kind te vormen door delen van de geslachtsnaam van beide ouders samen te voegen, is het alvast
 * mogelijk gemaakt om deze delen apart te 'kennen'. Deze beslissing is genomen na raadpleging van ministerie van
 * Justitie, in de persoon van Jet Lenters.
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
@Table(schema = "Kern", name = "PersGeslnaamcomp")
public class PersoonGeslachtsnaamcomponentModel extends AbstractPersoonGeslachtsnaamcomponentModel
    implements PersoonGeslachtsnaamcomponent, Comparable<PersoonGeslachtsnaamcomponentModel>
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonGeslachtsnaamcomponentModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert.
     *
     * @param persoon persoon van Persoon \ Geslachtsnaamcomponent.
     * @param volgnummer volgnummer van Persoon \ Geslachtsnaamcomponent.
     */
    public PersoonGeslachtsnaamcomponentModel(final PersoonModel persoon, final Volgnummer volgnummer) {
        super(persoon, volgnummer);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeslachtsnaamcomponent Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public PersoonGeslachtsnaamcomponentModel(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent,
            final PersoonModel persoon)
    {
        super(persoonGeslachtsnaamcomponent, persoon);
    }

    @Override
    public int compareTo(final PersoonGeslachtsnaamcomponentModel o) {
        return new CompareToBuilder().append(getVolgnummer(), o.getVolgnummer()).toComparison();
    }
}
