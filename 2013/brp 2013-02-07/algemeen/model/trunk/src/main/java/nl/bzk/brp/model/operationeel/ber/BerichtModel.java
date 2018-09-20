/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Berichtdata;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.operationeel.ber.basis.AbstractBerichtModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

import org.springframework.util.ReflectionUtils;


/**
 * (Toekomstig) Bericht zoals verzonden door of ontvangen door de centrale voorzieningen van de BRP.
 *
 * Berichten worden door de BRP gearchiveerd. Dit betreft enerzijds ontvangen Berichten, anderzijds Berichten die
 * verzonden gaan worden.
 *
 * 1. Soort bericht (weer) verwijderd uit model als eigenschap van Bericht: reden is dat het op het moment van
 * archiveren nog niet bekend zal zijn. RvdP 8 november 2011.
 *
 *
 *
 */
@Entity
@Table(schema = "Ber", name = "Ber")
public class BerichtModel extends AbstractBerichtModel implements Bericht {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected BerichtModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Bericht.
     * @param administratieveHandeling administratieveHandeling van Bericht.
     * @param data data van Bericht.
     * @param datumTijdOntvangst datumTijdOntvangst van Bericht.
     * @param datumTijdVerzenden datumTijdVerzenden van Bericht.
     * @param antwoordOp antwoordOp van Bericht.
     * @param richting richting van Bericht.
     */
    public BerichtModel(final SoortBericht soort, final AdministratieveHandelingModel administratieveHandeling,
            final Berichtdata data, final DatumTijd datumTijdOntvangst, final DatumTijd datumTijdVerzenden,
            final BerichtModel antwoordOp, final Richting richting)
    {
        super(soort, administratieveHandeling, data, datumTijdOntvangst, datumTijdVerzenden, antwoordOp, richting);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param bericht Te kopieren object type.
     * @param administratieveHandeling Bijbehorende Administratieve handeling.
     * @param antwoordOp Bijbehorende Bericht.
     */
    public BerichtModel(final Bericht bericht, final AdministratieveHandelingModel administratieveHandeling,
            final BerichtModel antwoordOp)
    {
        super(bericht, administratieveHandeling, antwoordOp);
    }

    // TODO BOLIE: voorlopig, totdat we setters hebben, gebruiken we hier reflections.
    // Bij inkomend bericht worden 2 berichtModels aangemaakt. We hebben gonstructors met parameters die we (nog) niet
    // kunnen gebruiken. Soort,AdministratieveHandeling is op dat ogenblik voor beide records nog niet bekend.
    // Voor uitgaande record, hebben we setters nodog voor Data, datumTijdVerzenden.
    // Eventueel zouden we Soort,AdministratieveHandeling eventueel tussendoor kunnen updaten.
    /**
     * .
     * @param data .
     */
    public void setData(final Berichtdata data) {
        try {
            Field field = ReflectionUtils.findField(this.getClass(), "data");
            if (field != null) {
                field.setAccessible(true);
                field.set(this, data);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Kan niet de waarde 'data' zetten: " + e.getMessage(), e);
        }
    }

    /**
     * .
     * @param datumTijdVerzenden .
     */
    public void setDatumTijdVerzenden(final DatumTijd datumTijdVerzenden) {
        try {
            Field field = ReflectionUtils.findField(this.getClass(), "datumTijdVerzenden");
            if (field != null) {
                field.setAccessible(true);
                field.set(this, datumTijdVerzenden);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Kan niet de waarde 'datumTijdVerzenden' zetten: " + e.getMessage(), e);
        }
    }

    /**
     * .
     * @param soort .
     */
    public void setSoort(final SoortBericht soort) {
        try {
            Field field = ReflectionUtils.findField(this.getClass(), "soort");
            if (field != null) {
                field.setAccessible(true);
                field.set(this, soort);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Kan niet de waarde 'soort' zetten: " + e.getMessage(), e);
        }
    }

    /**
     * .
     * @param administratieveHandeling .
     */
    public void setAdministratieveHandeling(final AdministratieveHandelingModel administratieveHandeling) {
        try {
            Field field = ReflectionUtils.findField(this.getClass(), "administratieveHandeling");
            if (field != null) {
                field.setAccessible(true);
                field.set(this, administratieveHandeling);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Kan niet de waarde 'administratieveHandeling' zetten: " + e.getMessage(), e);
        }
    }

}
