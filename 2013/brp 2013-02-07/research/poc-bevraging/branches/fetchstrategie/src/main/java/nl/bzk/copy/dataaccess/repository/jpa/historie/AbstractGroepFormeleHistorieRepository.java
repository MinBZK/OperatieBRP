/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.dataaccess.repository.jpa.historie;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.copy.dataaccess.repository.historie.GroepFormeleHistorieRepository;
import nl.bzk.copy.model.basis.AbstractDynamischObjectType;
import nl.bzk.copy.model.basis.FormeleHistorie;
import nl.bzk.copy.model.objecttype.operationeel.ActieModel;


/**
 * Implementatie van de HistorieRepository die alles regels omtrent het opslaan van historie voor groepen.
 *
 * @param <Y> C/D-Laag object waarvoor de historie wordt geregeld. (De groep)
 * @param <T> A-Laag object waarvoor de historie wordt geregeld. (Het object type)
 */
public abstract class AbstractGroepFormeleHistorieRepository<T extends AbstractDynamischObjectType,
    Y extends FormeleHistorie> implements GroepFormeleHistorieRepository<T>
{

    @PersistenceContext(unitName = "nl.bzk.copy")
    private EntityManager entityManager;

    @Override
    public void persisteerHistorie(final T objectType, final ActieModel actie) {
        // Selecteer bestaande record uit de C-laag
        Y claagRecord = selecteerClaagRecord(objectType);

        // Verplaats bestaande C-laag record naar de D-laag
        if (claagRecord != null) {
            verplaatsCLaagNaarDLaag(claagRecord, actie);
        }

        // Sla nieuwe C-laag record op
        Y y = maakNieuwHistorieRecord(objectType);
        y.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
        y.setActieInhoud(actie);
        entityManager.persist(y);
    }

    /**
     * Selecteert (en haalt op) record uit de C-laag (dus nog niet vervallen) die
     * bij de persoon waarvoor het nieuwe C laag record is gemaakt.
     * Indien er geen dergelijke records aanwezig zijn, zal een null
     * geretourneerd worden
     *
     * @param objectType Objecttype uit A laag waarvoor overlappende records moeten worden geselecteerd.
     * @return een object uit de C-laag.
     */
    protected Y selecteerClaagRecord(final T objectType) {
        TypedQuery<Y> query;
        query =
            entityManager.createQuery("SELECT hpa FROM " + getCLaagDomainClass().getSimpleName() + " hpa "
                + "WHERE hpa.historie.datumTijdVerval IS NULL AND " + "hpa." + padNaarALaagEntiteitInCLaagEntiteit()
                + " = " + ":aLaagEntiteit", getCLaagDomainClass());
        query.setParameter("aLaagEntiteit", objectType);

        Y resultaat;

        try {
            resultaat = query.getSingleResult();
        } catch (NoResultException e) {
            resultaat = null;
        }

        return resultaat;
    }

    /**
     * Creeert een nieuw history entiteit (C/D-Laag) op basis van een A-Laag object.
     *
     * @return Een C/D-Laag record.
     * @param objectType Objecttype uit A laag waar history entiteit voor gemaakt moet worden.
     */
    protected abstract Y maakNieuwHistorieRecord(final T objectType);

    /**
     * Retourneert de naam van de property van het A-Laag object dat in het C/D-Laag object zit.
     *
     * @return De naam van de property.
     */
    protected abstract String padNaarALaagEntiteitInCLaagEntiteit();

    /**
     * Het C/D-Laag domein object class waarvoor deze history repository zijn werk doet.
     *
     * @return C/D-Laag domein object class.
     */
    protected abstract Class<Y> getCLaagDomainClass();

    /**
     * Verplaats C-laag naar de D-laag
     * Hiervoor wordt het record aangepast en wordt het tijdstipvervallen ingevuld.
     *
     * @param cLaagRecord records uit de C-laag.
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     */
    protected void verplaatsCLaagNaarDLaag(final Y cLaagRecord, final ActieModel actie) {
        cLaagRecord.setDatumTijdVerval(actie.getTijdstipRegistratie());
        cLaagRecord.setActieVerval(actie);
        entityManager.persist(cLaagRecord);
    }
}
