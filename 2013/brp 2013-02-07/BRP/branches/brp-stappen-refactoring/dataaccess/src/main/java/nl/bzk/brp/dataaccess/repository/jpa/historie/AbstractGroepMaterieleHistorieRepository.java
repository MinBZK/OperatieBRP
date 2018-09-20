/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.repository.historie.GroepMaterieleHistorieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MaterieleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.DatumUtil;


/**
 * Implementatie van de HistorieRepository die alles regels omtrent het opslaan van historie voor groepen.
 *
 * @param <Y> C/D-Laag object waarvoor de historie wordt geregeld. (De groep)
 * @param <U> De groep
 * @param <T> A-Laag object waarvoor de historie wordt geregeld. (Het object type)
 */
public abstract class AbstractGroepMaterieleHistorieRepository<T extends AbstractDynamischObjectType,
    U extends Groep,
    Y extends MaterieleHistorieEntiteit> implements GroepMaterieleHistorieRepository<T, U, Y>
{
    private static final String EXCLUDE_FORMELE_HISTORIE = " AND hpa.materieleHistorie.datumTijdVerval IS NULL ";

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager entityManager;

    @Override
    public void persisteerHistorie(final T objectType, final ActieModel actie, final Datum datumAanvangGeldigheid,
        final Datum datumEindeGeldigheid)
    {
        // door null mee te egevn als groep, wordt de standaard groep uit de ALaag gehaald.
        persisteerHistorie(objectType, null, actie, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

    @Override
    public void persisteerHistorie(final T objectType, final U groep, final ActieModel actie,
        final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        // check voor geldigeDatums.
        checkDatumParameters(objectType, datumAanvangGeldigheid, datumEindeGeldigheid);

        // Selecteer records uit C-laag met overlap (in de tijd) voor het opgegeven nieuwe adres en bijbehorende
        // geldigheid.
        List<Y> overlappendeRecords =
            selecteerOverlappendeRecords(objectType, datumAanvangGeldigheid, datumEindeGeldigheid);

        // De niet geheel overlapte records zullen moeten worden aangepast (ingekort).
        // Let op: Het kan zijn dat de originele lijst van overlappendeRecords aangepast wordt in deze methode.
        kopieerGrensRecordsEnPasDezeAan(overlappendeRecords, actie, datumAanvangGeldigheid, datumEindeGeldigheid);

        // Alle overlapte en deels overlapte records zullen moeten worden verplaatst naar de D-laag
        verplaatsOverlapteRecordsNaarDLaag(overlappendeRecords, actie);

        // All voorberedingen al gedaan, schrijf nu het record weg.
        persistTeWijzigenRecord(objectType, groep, actie, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

    /**
     * test dat de waarden van de datums correct zijn ingevuld, zo niet wordt een IllegalArgumentException gethrowed.
     *
     * @param objectType het object type
     * @param datumAanvangGeldigheid de beide datums,
     * @param datumEindeGeldigheid de beide datums,
     * @throws IllegalArgumentException als iets niet klopt.
     */
    private void checkDatumParameters(final T objectType, final Datum datumAanvangGeldigheid,
        final Datum datumEindeGeldigheid)
    {
        if (datumAanvangGeldigheid == null) {
            // verplicht veld.
            throw new IllegalArgumentException("Datum aanvang geldigheid is leeg voor wijzigen van "
                + objectType.getClass().getSimpleName());
        } else if (datumEindeGeldigheid != null
            && (datumAanvangGeldigheid.getWaarde() >= datumEindeGeldigheid.getWaarde()))
        {
            // einde mag niet voor of zelfde zijn als aanvang.
            throw new IllegalArgumentException("Datum aanvang geldigheid is gelijk of later dan einde geldigheid "
                + "voor wijzigen van " + objectType.getClass().getSimpleName()
                + " [" + datumAanvangGeldigheid.getWaarde() + " - " + datumEindeGeldigheid.getWaarde() + "]");
        }
    }

    /**
     * Schrijf de echt te wijzigen record weg in de historie.
     *
     * @param objectType de A-Laag record.
     * @param groep de te wijzigen groep, indien null, wordt de 'standaard' groep uit de A-Laag gehaald.
     * @param actie de bijbehorende actie (id/record)
     * @param datumAanvangGeldigheid de datum aanvang geldigheid
     * @param datumEindeGeldigheid de datum einde geldigheid.
     */
    private void persistTeWijzigenRecord(final T objectType, final U groep, final ActieModel actie,
        final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        // De daadwerkelijk record waar het om gaat historie moet opgeslagen worden.
        Y y = maakNieuwHistorieRecord(objectType, groep);
        y.getMaterieleHistorie().setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        y.getMaterieleHistorie().setDatumEindeGeldigheid(datumEindeGeldigheid);
        y.getMaterieleHistorie().setDatumTijdRegistratie(actie.getTijdstipRegistratie());
        y.getMaterieleHistorie().setActieInhoud(actie);
        entityManager.persist(y);

    }

    /**
     * Selecteer een lijst van historie records an een A-laag groep.
     * De lijst is als volgt gesorteerd: <br/>
     * - tijdstipVervallen, (null eerst, dan jongste eerst, oudste laatst). Hierdoor komt de CLaag als eerste.<br/>
     * - datumAanvangGeldigheid (per 'verval tijdmoment, de jongste eerst). <br/>
     * - datumEindeGeldigheid idem.
     * - id als laatste.
     *
     * @param objectType Objecttype uit A laag waarvoor historie records moeten worden geselecteerd.
     * @param inclFormeleHistorie moeten de formele historie ook meegenomen worden.
     * @return de lijst van historie records.
     */
    @Override
    public List<Y> haalopHistorie(final T objectType, final boolean inclFormeleHistorie) {
        String filter = "";
        if (!inclFormeleHistorie) {
            filter = EXCLUDE_FORMELE_HISTORIE;
        }

        return getEntityManager().createQuery(
            "SELECT hpa FROM "
                + getCLaagDomainClass().getSimpleName()
                + " hpa "
                + "WHERE hpa." + padNaarALaagEntiteitInCLaagEntiteit() + " = " + ":aLaagEntiteit "
                + filter
                + " ORDER BY hpa.materieleHistorie.datumTijdVerval DESC "
                + " ,hpa.materieleHistorie.datumAanvangGeldigheid.waarde DESC "
                + " ,hpa.materieleHistorie.datumEindeGeldigheid.waarde DESC, hpa.id DESC "
            , getCLaagDomainClass())
            .setParameter("aLaagEntiteit", objectType)
            .getResultList();
    }

    @Override
    public Y haalGeldigRecord(final T objectType, final Datum peildDatum) {
        final Class<Y> cLaagDomainClass = getCLaagDomainClass();
        final String cLaagEniteitNaam = cLaagDomainClass.getSimpleName();
        final String aLaangEntiteitNaam = padNaarALaagEntiteitInCLaagEntiteit();
        final String sqlString = "SELECT hpa FROM "
            + cLaagEniteitNaam
            + " hpa "
            + "WHERE hpa.materieleHistorie.datumAanvangGeldigheid = "
            + " (SELECT max(hpa2.materieleHistorie.datumAanvangGeldigheid) FROM "
            + cLaagEniteitNaam
            + " hpa2 WHERE "
            + " hpa2." + aLaangEntiteitNaam + "=" + ":aLaagEntiteit "
            + " AND ( hpa2.materieleHistorie.datumEindeGeldigheid IS NULL OR "
            + " hpa2.materieleHistorie.datumAanvangGeldigheid <> hpa2.materieleHistorie.datumEindeGeldigheid)"
            + " AND hpa2.materieleHistorie.datumAanvangGeldigheid <=  :aanvangGeldigheid "
            + " AND hpa2.materieleHistorie.datumTijdVerval IS NULL )"
            + EXCLUDE_FORMELE_HISTORIE
            + " AND hpa." + aLaangEntiteitNaam + " = " + ":aLaagEntiteit ";

        try {
            return entityManager
                .createQuery(sqlString, cLaagDomainClass)
                .setParameter("aanvangGeldigheid", peildDatum)
                .setParameter("aLaagEntiteit", objectType)
                .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Y haalGeldigRecord(final T objectType) {
        return haalGeldigRecord(objectType, DatumUtil.vandaag());
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
        if (datumEindeGeldigheid == null) {
            query =
                entityManager
                    .createQuery(
                        "SELECT hpa FROM "
                            + getCLaagDomainClass().getSimpleName()
                            + " hpa "
                            + "WHERE (hpa.materieleHistorie.datumAanvangGeldigheid.waarde > :aanvangGeldigheid "
                            + "       OR hpa.materieleHistorie.datumEindeGeldigheid IS NULL "
                            + "       OR hpa.materieleHistorie.datumEindeGeldigheid.waarde > :aanvangGeldigheid) "
                            + EXCLUDE_FORMELE_HISTORIE
                            + "  AND hpa." + padNaarALaagEntiteitInCLaagEntiteit() + " = " + ":aLaagEntiteit",
                        getCLaagDomainClass());
        } else {
            query =
                entityManager
                    .createQuery(
                        "SELECT hpa FROM "
                            + getCLaagDomainClass().getSimpleName()
                            + " hpa "
                            + "WHERE (   (hpa.materieleHistorie.datumAanvangGeldigheid.waarde >= :aanvangGeldigheid "
                            + "           AND hpa.materieleHistorie.datumAanvangGeldigheid.waarde < :eindeGeldigheid) "
                            + "       OR (hpa.materieleHistorie.datumEindeGeldigheid.waarde <= :eindeGeldigheid "
                            + "           AND hpa.materieleHistorie.datumEindeGeldigheid.waarde > :aanvangGeldigheid) "
                            + "       OR (hpa.materieleHistorie.datumEindeGeldigheid.waarde IS NULL "
                            + "           AND hpa.materieleHistorie.datumAanvangGeldigheid.waarde < :eindeGeldigheid) "
                            + "       OR (hpa.materieleHistorie.datumAanvangGeldigheid.waarde <= :aanvangGeldigheid "
                            + "           AND hpa.materieleHistorie.datumEindeGeldigheid.waarde >= :eindeGeldigheid)) "
                            + EXCLUDE_FORMELE_HISTORIE
                            + "  AND hpa." + padNaarALaagEntiteitInCLaagEntiteit() + " = :aLaagEntiteit",
                        getCLaagDomainClass());
            query.setParameter("eindeGeldigheid", datumEindeGeldigheid.getWaarde());

        }
        query.setParameter("aanvangGeldigheid", datumAanvangGeldigheid.getWaarde());
        query.setParameter("aLaagEntiteit", objectType);
        return query.getResultList();
    }

    /**
     * Creeert een nieuw history entiteit (C/D-Laag) op basis van een A-Laag object.
     *
     * @param objectType Objecttype uit A laag waar history entiteit voor gemaakt moet worden.
     * @param groep de groep die de data bevat, als deze null is, moet de implementatie de standaard groep uit de ALaag
     * halen.
     * @return Een C/D-Laag record.
     */
    protected abstract Y maakNieuwHistorieRecord(final T objectType, final U groep);

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
        final List<Y> copieVanOverlapteRecords = new ArrayList<Y>(overlapteRecords);

        for (Y y : copieVanOverlapteRecords) {
            // Van alle overlappende records gaan we deze type gevallen afhandelen.
            // 1) Alle records met zelfde tijdstip registratie, dwz. records die zijn aangemaakt in een
            //      eerdere actie, en nu opnieuw voorkomt in de overlappende records lijst, worden opnieuw aangepast
            //      (kop, of staart of in zijn geheel overschaduwd). worden normaal gesproken gepushed naar de D-Laag.
            //      Dit veroorzaakt 0-periode records (ts registratie == ts verval) en duplicate key violation.
            //      Daarom verwijderen we deze records in zijn geheel.
            //      Let op dat dit moet gebeuren voordat een nieuwe kop-record wordt aangemaakt (optie 2 en 3)
            //      Gelukkig, nadat we het record hebben verwijderd, deze nog in het geheugen beschikbaar is, om
            //      de kop/staart-records alsnog te kunnen aanmaken.
            // 2) aan het eind, het begin deel inkorten, wat we overhouden is staart record. Deze wordt weggeschreven
            //      de originele record wordt naar de D-Laag gepushed.
            // 3) aan het begin, het einde korten we in, wat we overhouden is de kop record. Deze wordt weggeschreven.
            //      de originele record wordt naar de D-laag gepushed.
            // 4) periode die volledig is overschaduwed. Deze gaat in zijn geheel naar de D-Laag. Hoeft niets te doen.

            if (y.getMaterieleHistorie().getDatumTijdRegistratie().equals(actie.getTijdstipRegistratie())) {
                // zelfde datumAanvangGeldigheid, zelfde ts-reg. Dit record verwijderen omdat dit een
                // left-over van de vorig actie is die zo meteen wordt overschreven.

                // De flush is noodzakelijk, omdat we anders geen nieuwe kunnen aanmaken. Hibernate/JPA cached deze
                // operaties en de delete wordt pas aan het eind gedaan en krijgen we alsnog duplicate key.
                entityManager.remove(y);
                entityManager.flush();
                // verwijder nu van de overlapte lijst, zodat deze niet naar D-Laag gepushed wordt.
                overlapteRecords.remove(y);
            }

            if (datumEindeGeldigheid != null && (y.getMaterieleHistorie().getDatumEindeGeldigheid() == null
                || y.getMaterieleHistorie().getDatumEindeGeldigheid().getWaarde() > datumEindeGeldigheid.getWaarde()))
            {
                // Dit is afhandelen van de staart-record.
                // nieuwe ingekorte record maken en de bestaande naar D-Laag pushen.
                MaterieleHistorieEntiteit nieuwRecord = y.kopieer();
                nieuwRecord.getMaterieleHistorie().setDatumAanvangGeldigheid(datumEindeGeldigheid);
                nieuwRecord.getMaterieleHistorie().setDatumTijdRegistratie(actie.getTijdstipRegistratie());
                nieuwRecord.getMaterieleHistorie().setActieAanpassingGeldigheid(actie);
                entityManager.persist(nieuwRecord);
            }

            if (y.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde() < datumIngangGeldigheid.getWaarde()) {
                // Dit is afhandelen van de kop-record.
                // nieuwe ingekorte record maken, de bestaande naar D-Laag pushen.
                MaterieleHistorieEntiteit nieuwRecord = y.kopieer();
                nieuwRecord.getMaterieleHistorie().setDatumEindeGeldigheid(datumIngangGeldigheid);
                nieuwRecord.getMaterieleHistorie().setDatumTijdRegistratie(actie.getTijdstipRegistratie());
                nieuwRecord.getMaterieleHistorie().setActieAanpassingGeldigheid(actie);
                entityManager.persist(nieuwRecord);
            }
        }
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
            y.getMaterieleHistorie().setDatumTijdVerval(actie.getTijdstipRegistratie());
            y.getMaterieleHistorie().setActieVerval(actie);
            entityManager.persist(y);
        }
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
