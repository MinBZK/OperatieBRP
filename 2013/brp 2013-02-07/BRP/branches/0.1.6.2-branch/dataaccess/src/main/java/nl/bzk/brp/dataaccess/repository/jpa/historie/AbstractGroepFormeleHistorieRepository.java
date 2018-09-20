/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.basis.FormeleHistorie;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;


/**
 * Implementatie van de HistorieRepository die alles regels omtrent het opslaan van historie voor groepen.
 *
 * @param <Y> C/D-Laag object waarvoor de historie wordt geregeld. (De groep)
 * @param <T> A-Laag object waarvoor de historie wordt geregeld. (Het object type)
 */
public abstract class AbstractGroepFormeleHistorieRepository<T extends AbstractDynamischObjectType,
    Y extends FormeleHistorie>
        implements GroepHistorieRepository<T>
{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void persisteerHistorie(final T objectType, final ActieModel actie, final Datum datumAanvangGeldigheid,
            final Datum datumEindeGeldigheid)
    {
        // Selecteer records uit C-laag met overlap (in de tijd) voor het opgegeven nieuwe adres en bijbehorende
        // geldigheid.
        List<Y> overlappendeRecords =
            selecteerOverlappendeRecords(objectType, datumAanvangGeldigheid, datumEindeGeldigheid);

        // De niet geheel overlapte records zullen moeten worden aangepast (ingekort).
        kopieerGrensRecordsEnPasDezeAan(overlappendeRecords, actie, datumAanvangGeldigheid, datumEindeGeldigheid);

        // Alle overlapte en deels overlapte records zullen moeten worden verplaatst naar de D-laag
        verplaatsOverlapteRecordsNaarDLaag(overlappendeRecords, actie);

        // Nieuwe huidige historie moet opgeslagen worden.
        Y y = maakNieuwHistorieRecord(objectType);
        y.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
        y.setActieInhoud(actie);
        entityManager.persist(y);
    }

    /**
     * Selecteert (en haalt op) alle records uit de C-laag (dus nog niet vervallen) die
     * deels of geheel worden overlapt en die horen bij de persoon waarvoor het nieuwe C laag record is gemaakt.
     * Indien er geen dergelijke records aanwezig zijn, zal een lege lijst worden
     * geretourneerd.
     *
     * @param objectType Objecttype uit A laag waarvoor overlappende records moeten worden geselecteerd.
     * @param datumAanvangGeldigheid Datum waarop het nieuwe C-laag record ingaat.
     * @param datumEindeGeldigheid Datum waarop het nieuwe C-laag record niet meer geldig is.
     * @return een lijst van voor de persoon overlapte records uit de C-laag.
     */
    protected List<Y> selecteerOverlappendeRecords(final T objectType, final Datum datumAanvangGeldigheid,
            final Datum datumEindeGeldigheid)
    {
        TypedQuery<Y> query;
        query =
            entityManager
                    .createQuery(
                            "SELECT hpa FROM "
                                + getCLaagDomainClass().getSimpleName()
                                + " hpa "
                                + "WHERE hpa.historie.datumTijdVerval IS NULL AND "
                                + "hpa." + padNaarALaagEntiteitInCLaagEntiteit() + " = " + ":aLaagEntiteit",
                            getCLaagDomainClass());
        query.setParameter("aLaagEntiteit", objectType);
        return query.getResultList();
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
     * Records die slechts deels worden overlapt, dus die aanvangen reeds voor de aanvang uit de context of nog
     * doorlopen tot na het einde uit de context, zullen moeten worden ingekort. Hiervoor worden nieuwe records aan de
     * C-laag toegevoegd, welke een kopie zijn van de deels overlapte records, maar waarvan de aanvangdatum of
     * einddatum is aangepast, conform de context.
     *
     * @param overlapteRecords alle overlapte records uit de C-laag.
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     * @param datumIngangGeldigheid Datum waarop het nieuwe C-Laag record ingaat
     * @param datumEindeGeldigheid Datum waarop het huidige record vervalt.
     */
    protected void kopieerGrensRecordsEnPasDezeAan(final List<Y> overlapteRecords, final ActieModel actie,
            final Datum datumIngangGeldigheid, final Datum datumEindeGeldigheid)
    {
//        for (Y y : overlapteRecords) {
//            if (y.getDatumAanvangGeldigheid().getWaarde() < datumIngangGeldigheid.getWaarde()) {
//                // Controlleer huidige adres AanvangGeldigheid of die valt voor de nieuwe adres IngangGeldigheid en
//                // kort
//                // in de huidige adres eindeGeldigheid
//
//                MaterieleHistorie nieuwRecord = y.kopieer();
//                nieuwRecord.setDatumEindeGeldigheid(datumIngangGeldigheid);
//                nieuwRecord.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
//                nieuwRecord.setActieAanpassingGeldigheid(actie);
//                entityManager.persist(nieuwRecord);
//            }
//            if (datumEindeGeldigheid != null
//                && (y.getDatumEindeGeldigheid() == null
//                    || y.getDatumEindeGeldigheid().getWaarde() > datumEindeGeldigheid.getWaarde()))
//            {
//                MaterieleHistorie nieuwRecord = y.kopieer();
//                nieuwRecord.setDatumAanvangGeldigheid(datumEindeGeldigheid);
//                nieuwRecord.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
//                nieuwRecord.setActieAanpassingGeldigheid(actie);
//                entityManager.persist(nieuwRecord);
//            }
//        }
    }

    /**
     * Alle (deels) overlapte records dienen uit de C-laag te worden gehaald en naar de D-laag te worden verplaatst,
     * daar ze niet meer geldig zijn. Hiervoor wordt het record aangepast en wordt het tijdstipvervallen ingevuld.
     *
     * @param overlapteRecords alle overlapte records uit de C-laag.
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     */
    protected void verplaatsOverlapteRecordsNaarDLaag(final List<Y> overlapteRecords, final ActieModel actie) {
        for (Y y : overlapteRecords) {
            y.setDatumTijdVerval(actie.getTijdstipRegistratie());
            y.setActieVerval(actie);
            entityManager.persist(y);
        }
    }
}
