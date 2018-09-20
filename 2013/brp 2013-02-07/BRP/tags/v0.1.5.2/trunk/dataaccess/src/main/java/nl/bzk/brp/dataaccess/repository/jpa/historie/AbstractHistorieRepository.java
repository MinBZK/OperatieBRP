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

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstracte klasse met functionaliteit voor repositories die gedeeld wordt door
 * {@link nl.bzk.brp.dataaccess.repository.jpa.historie.AbstractGroepHistorieRepository} en
 * {@link nl.bzk.brp.dataaccess.repository.jpa.historie.AbstractObjectTypeHistorieRepository}.
 * @param <T> Type C/D-laag object waarvoor de historie wordt geregeld.
 */
public abstract class AbstractHistorieRepository<T extends AbstractMaterieleEnFormeleHistorieEntiteit> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHistorieRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Selecteert (en haalt op) alle records uit de C-laag (dus nog niet vervallen) die
     * deels of geheel worden overlapt en die horen bij de persoon waarvoor het nieuwe C laag record is gemaakt.
     * Indien er geen dergelijke records aanwezig zijn, zal een lege lijst worden
     * geretourneerd.
     *
     * @param pers De persoon waarvoor een nieuw C laag record is gemaakt.
     * @param datumAanvangGeldigheid Datum waarop het nieuwe C-laag record ingaat.
     * @param datumEindeGeldigheid Datum waarop het nieuwe C-laag record niet meer geldig is.
     * @return een lijst van voor de persoon overlapte records uit de C-laag.
     */
    protected List<T> selecteerOverlappendeRecords(final Integer datumAanvangGeldigheid,
                                                   final Integer datumEindeGeldigheid,
                                                   final PersistentPersoon pers)
    {
        TypedQuery<T> query;
        if (datumEindeGeldigheid == null) {
            query =
                entityManager.createQuery("SELECT hpa FROM " + getCLaagDomainClass().getSimpleName() + " hpa "
                    + "WHERE (hpa.datumAanvangGeldigheid > "
                    + ":aanvangGeldigheid OR hpa.datumEindeGeldigheid IS NULL OR hpa.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) AND hpa.datumTijdVerval IS NULL AND "

                    + "hpa." + padNaarPersoonEntiteitInCLaagEntiteit() + " = "
                    + ":pers", getCLaagDomainClass());
        } else {
            query =
                entityManager.createQuery("SELECT hpa FROM " + getCLaagDomainClass().getSimpleName() + " hpa "
                    + "WHERE ((hpa.datumAanvangGeldigheid >= "
                    + ":aanvangGeldigheid AND hpa.datumAanvangGeldigheid < :eindeGeldigheid) OR "
                    + "(hpa.datumEindeGeldigheid <= :eindeGeldigheid AND hpa.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) OR (hpa.datumEindeGeldigheid IS NULL AND hpa.datumAanvangGeldigheid < "
                    + ":eindeGeldigheid) OR (hpa.datumAanvangGeldigheid <= :aanvangGeldigheid AND "
                    + "hpa.datumEindeGeldigheid >= :eindeGeldigheid)) AND hpa.datumTijdVerval IS NULL "
                    + "AND hpa." + padNaarPersoonEntiteitInCLaagEntiteit() + " = :pers", getCLaagDomainClass());
            query.setParameter("eindeGeldigheid", datumEindeGeldigheid);

        }
        query.setParameter("aanvangGeldigheid", datumAanvangGeldigheid);
        query.setParameter("pers", pers);
        return query.getResultList();
    }

    /**
     * Retourneert de naam van de property van het A-Laag object dat in het C/D-Laag object zit.
     * @return De naam van de property.
     */
    protected abstract String padNaarPersoonEntiteitInCLaagEntiteit();

    /**
     * Het C/D-Laag domein object class waarvoor deze history repository zijn werk doet.
     * @return C/D-Laag domein object class.
     */
    protected abstract Class<T> getCLaagDomainClass();

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
    protected void kopieerGrensRecordsEnPasDezeAan(final List<T> overlapteRecords,
                                                   final PersistentActie actie,
                                                   final Integer datumIngangGeldigheid,
                                                   final Integer datumEindeGeldigheid)
    {
        try {
            for (T t : overlapteRecords) {
                if (t.getDatumAanvangGeldigheid() < datumIngangGeldigheid) {
                    // Controlleer huidige adres AanvangGeldigheid of die valt voor de nieuwe adres IngangGeldigheid en
                    // kort
                    // in de huidige adres eindeGeldigheid

                    AbstractMaterieleEnFormeleHistorieEntiteit nieuwRecord = t.clone();
                    nieuwRecord.setDatumEindeGeldigheid(datumIngangGeldigheid);
                    nieuwRecord.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
                    nieuwRecord.setActieAanpassingGeldigheid(actie);
                    entityManager.persist(nieuwRecord);
                }
                if (datumEindeGeldigheid != null
                    && (t.getDatumEindeGeldigheid() == null
                    || t.getDatumEindeGeldigheid() > datumEindeGeldigheid))
                {
                    AbstractMaterieleEnFormeleHistorieEntiteit nieuwRecord = t.clone();
                    nieuwRecord.setDatumAanvangGeldigheid(datumEindeGeldigheid);
                    nieuwRecord.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
                    nieuwRecord.setActieAanpassingGeldigheid(actie);
                    entityManager.persist(nieuwRecord);
                }
            }
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Fout opgetreden vanwege niet bestaande clone methode", e);
            throw new RuntimeException("Technische fout vanwege incorrecte code", e);
        }
    }

    /**
     * Alle (deels) overlapte records dienen uit de C-laag te worden gehaald en naar de D-laag te worden verplaatst,
     * daar ze niet meer geldig zijn. Hiervoor wordt het record aangepast en wordt het tijdstipvervallen ingevuld.
     *
     * @param overlapteRecords alle overlapte records uit de C-laag.
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     */
    protected void verplaatsOverlapteRecordsNaarDLaag(final List<T> overlapteRecords, final PersistentActie actie)
    {
        for (T t : overlapteRecords) {
            t.setDatumTijdVerval(actie.getTijdstipRegistratie());
            t.setActieVerval(actie);
            entityManager.persist(t);
        }
    }
}
