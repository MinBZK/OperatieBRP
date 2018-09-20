/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Groep identificerende nummers
 * <p/>
 * De groep ""identificatienummers"" bevat het burgerservicenummer, en het Administratienummer, waarmee de persoon uniek wordt aangeduid.
 * <p/>
 * Verplicht aanwezig bij persoon
 * <p/>
 * 1. Omdat in uitzonderingssituaties het nodig kan zijn om het burgerservicenummer en/of Administratienummer van een persoon te wijzigen, is ervoor
 * gekozen om burgerservicenummer en/of Administratienummer NIET als technische sleutel te hanteren binnen de BRP.
 * <p/>
 * 2. Keuze van historie: LO 3.x onderkend beide vormen van historie voor BSN en A nummer: ��k materi�le. Er is een discussie geweest wat precies die
 * materi�le historie zou zijn: is er niet simpelweg sprake van alleen een formele tijdslijn voor het BSN en Anummer. Conclusie was: neen, er is ook een
 * materi�le tijdslijn. En ja, die komt meestal overeen met de formele tijdslijn. NB: geldigheid gegeven wordt wel (mogelijk) geleverd aan afnemer. RvdP
 * vastgelegd 6-1-2012.
 */
@Embeddable
public class PersoonIdentificatienummersGroepModel extends AbstractPersoonIdentificatienummersGroepModel implements
    PersoonIdentificatienummersGroep, Comparable<PersoonIdentificatienummersGroepModel>
{

    private static final int HASHCODE_GETAL1 = 27;

    private static final int HASHCODE_GETAL2 = 23;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonIdentificatienummersGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param burgerservicenummer burgerservicenummer van Identificatienummers.
     * @param administratienummer administratienummer van Identificatienummers.
     */
    public PersoonIdentificatienummersGroepModel(final BurgerservicenummerAttribuut burgerservicenummer,
        final AdministratienummerAttribuut administratienummer)
    {
        super(burgerservicenummer, administratienummer);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonIdentificatienummersGroep
     *         te kopieren groep.
     */
    public PersoonIdentificatienummersGroepModel(
        final PersoonIdentificatienummersGroep persoonIdentificatienummersGroep)
    {
        super(persoonIdentificatienummersGroep);
    }

    /**
     * Vergelijkt dit object met het opgegeven object voor volgorde. Deze methode retourneert een negatief getal, nul, of een positief getal indien dit
     * object 'kleiner', gelijk of 'groter' dan het opgegeven object is. Hierbij wordt voor {@link PersoonIdentificatienummersGroepModel} speficiek gekeken
     * naar de waardes voor burgerservicenummer en administratienummer, waarbij eerst gesorteerd wordt op burgerservicenummer en dan op
     * administratienummer. <p> Merk op dat in deze vergelijking op een niet standaard wijze wordt omgegaan met <code>null</code> waardes. Waar
     * <code>null</code> meestal wordt gezien als 'kleiner' dan een werkelijke waarde, worden in deze vergelijker <code>null</code>-waardes juist als
     * 'groter' gezien, zodat deze ook achteraan de lijst komen in geval van een sortering. </p>
     *
     * @param identificatienummers het object dat wordt vergeleken.
     * @return een negatief getal, nul of een positief getal indien dit object 'kleiner', gelijk aan of 'groter' is dan het opgegeven object.
     */
    @Override
    public int compareTo(final PersoonIdentificatienummersGroepModel identificatienummers) {
        // We kunnen hier geen gebruik maken van de CompareToBuilder, daar deze null waardes standaard vooraan
        // zet, en we dat hier juist net niet willen.
        int vergelijk = 0;

        if (identificatienummers == null) {
            vergelijk = -1;
        } else {
            vergelijk = vergelijkBurgerservicenummer(identificatienummers.getBurgerservicenummer());
            if (vergelijk == 0) {
                vergelijk = vergelijkAdministratienummer(identificatienummers.getAdministratienummer());
            }
        }
        return vergelijk;
    }

    /**
     * Vergelijkt het opgegeven burgerservicenummer met het burgerservicenummer van dit object en retourneert een negatief getal, nul, of een positief
     * getal indien het bsn van dit object 'kleiner', gelijk of 'groter' dan het opgegeven bsn is.
     *
     * @param bsn het bsn dat vergeleken moet worden.
     * @return een negatief getal, nul of een positief getal indien het bsn van dit object 'kleiner', gelijk aan of 'groter' is dan het opgegeven bsn.
     */
    private int vergelijkBurgerservicenummer(final BurgerservicenummerAttribuut bsn) {
        final int vergelijk;

        if (getBurgerservicenummer() == null) {
            if (bsn == null) {
                vergelijk = 0;
            } else {
                vergelijk = 1;
            }
        } else {
            vergelijk = getBurgerservicenummer().compareTo(bsn);
        }
        return vergelijk;
    }

    /**
     * Vergelijkt het opgegeven administratienummer met het administratienummer van dit object en retourneert een negatief getal, nul, of een positief
     * getal indien het administratienummer van dit object 'kleiner', gelijk of 'groter' dan het opgegeven administratienummer is.
     *
     * @param aNummer het administratienummer dat vergeleken moet worden.
     * @return een negatief getal, nul of een positief getal indien het administratienummer van dit object 'kleiner', gelijk aan of 'groter' is dan het
     *         opgegeven administratienummer.
     */
    private int vergelijkAdministratienummer(final AdministratienummerAttribuut aNummer) {
        final int vergelijk;

        if (getAdministratienummer() == null) {
            if (aNummer == null) {
                vergelijk = 0;
            } else {
                vergelijk = 1;
            }
        } else {
            vergelijk = getAdministratienummer().compareTo(aNummer);
        }
        return vergelijk;
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
            PersoonIdentificatienummersGroepModel identificatienummers = (PersoonIdentificatienummersGroepModel) obj;
            isGelijk =
                new EqualsBuilder()
                    .append(this.getBurgerservicenummer(), identificatienummers.getBurgerservicenummer())
                    .append(this.getAdministratienummer(), identificatienummers.getAdministratienummer())
                    .isEquals();
        }
        return isGelijk;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2).append(this.getBurgerservicenummer())
            .append(this.getAdministratienummer()).hashCode();
    }

}
