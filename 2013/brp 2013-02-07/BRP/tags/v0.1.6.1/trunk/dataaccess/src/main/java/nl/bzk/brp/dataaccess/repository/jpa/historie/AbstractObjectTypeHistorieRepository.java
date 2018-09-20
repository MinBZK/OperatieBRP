/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;

/**
 * Implementatie van de HistorieRepository die alles regels omtrent het opslaan van historie voor object typen.
 * @param <T> C/D-Laag object waarvoor de historie wordt geregeld.
 * @param <Y> A laag object waarvoor de historie wordt geregeld.
 */
public
abstract class AbstractObjectTypeHistorieRepository<T extends AbstractMaterieleEnFormeleHistorieEntiteit, Y>
        extends AbstractHistorieRepository<T>
        implements ObjectTypeHistorieRepository<T, Y>
{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void persisteerHistorie(final Y aLaagObject,
                                   final PersistentPersoon persoon,
                                   final PersistentActie actie,
                                   final Integer datumAanvangGeldigheid,
                                   final Integer datumEindeGeldigheid)
    {
        // Selecteer records uit C-laag met overlap (in de tijd) voor het opgegeven nieuwe adres en bijbehorende
        // geldigheid.
        List<T> overlappendeRecords =
            selecteerOverlappendeRecords(datumAanvangGeldigheid, datumEindeGeldigheid, persoon);

        // De niet geheel overlapte records zullen moeten worden aangepast (ingekort).
        kopieerGrensRecordsEnPasDezeAan(overlappendeRecords, actie, datumAanvangGeldigheid, datumEindeGeldigheid);

        // Alle overlapte en deels overlapte records zullen moeten worden verplaatst naar de D-laag
        verplaatsOverlapteRecordsNaarDLaag(overlappendeRecords, actie);

        // Nieuwe huidige historie moet opgeslagen worden.
        T t = maakNieuwHistorieRecord(aLaagObject);
        t.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        t.setDatumEindeGeldigheid(datumEindeGeldigheid);
        t.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
        t.setActieInhoud(actie);
        entityManager.persist(t);
    }

    /**
     * Creeert een nieuw history entiteit (C/D-Laag) op basis van een A-Laag object.
     * @param aLaagObject Het bijbehorende A-Laag object
     * @return Een C/D-Laag record.
     */
    protected abstract T maakNieuwHistorieRecord(final Y aLaagObject);
}
