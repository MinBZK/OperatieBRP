/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocument;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Document dat vereist is voor het reizen naar het buitenland.
 * <p/>
 * In geval van bijschrijving van een kind in het reisdocument van de ouder is er geen sprake van een uitreiking aan het kind maar van het opnemen van de
 * gegevens van het kind in het reisdocument. Er is dan in deze terminologie wel sprake van een nieuw persoon/reisdocument, waarbij het reisdocument het
 * reisdocument is van de ouder.
 * <p/>
 * 1. De opname van deze gegevens is noodzakelijk tot uitfasering LO 3.7 en/of ingebruikname ORRA. RvdP 5 september 2011 2. De modellering rondom
 * autoriteit van afgifte kent vele opties, zoals ��n codetabel (waarin alle mogelijke codes staan zoals benoemd in de LO3.x tabel), maar ook het splitsen
 * in een 'typering' van autoriteit, en dan afhankelijk van de 'type' of een verwijzing naar gemeente, provincie of land, of bijvoorbeeld verschillende
 * verwijzingen naar de autoriteit a-la 'provincie van autoriteit afgifte commissaris' en 'gemeente van autoriteit afgifte burgermeester' en 'gemeente van
 * autoriteit van afgifte college B&W'. De keuze is uiteindelijk op de middelste gevallen op basis van de volgende overwegingen: - bij beheer van de
 * landentabel of gemeentetabel (partijentabel) moet het niet noodzakelijk zijn om ook (extra) beheer te doen op de stamgegevens rondom autoriteit:
 * hierdoor viel de optie '��n codetabel' af. - de verschillende attributen moeten een relatief eenvoudige naam hebben, en de definitie moet hierbij goed
 * aansluiten. De derde optie - met een verwijzing naar gemeenten voor verschillende typen autoriteiten [namelijk bij o.a. commandant Maurechausse,
 * Burgermeester �n Burgermeester-en-Wethouders) - is minder begrijpelijk en valt door dit criterium af. RvdP 2 oktober 2012.
 */
@Entity
@Table(schema = "Kern", name = "PersReisdoc")
public class PersoonReisdocumentModel extends AbstractPersoonReisdocumentModel implements PersoonReisdocument,
    Comparable<PersoonReisdocumentModel>
{

    private static final int HASHCODE_GETAL1 = 78897;
    private static final int HASHCODE_GETAL2 = 2312;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonReisdocumentModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Reisdocument.
     * @param soort   soort van Persoon \ Reisdocument.
     */
    public PersoonReisdocumentModel(final PersoonModel persoon, final SoortNederlandsReisdocumentAttribuut soort) {
        super(persoon, soort);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonReisdocument Te kopieren object type.
     * @param persoon             Bijbehorende Persoon.
     */
    public PersoonReisdocumentModel(final PersoonReisdocument persoonReisdocument, final PersoonModel persoon) {
        super(persoonReisdocument, persoon);
    }

    @Override
    public int compareTo(final PersoonReisdocumentModel o) {
        return new CompareToBuilder().append(this.getSoort(), o.getSoort()).toComparison();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            final PersoonReisdocumentModel reisdocumentModel = (PersoonReisdocumentModel) obj;
            isGelijk = new EqualsBuilder().append(this.getSoort(), reisdocumentModel.getSoort()).isEquals();
        }
        return isGelijk;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2).append(this.getSoort()).hashCode();
    }
}
