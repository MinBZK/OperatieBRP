/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.VerantwoordingsEntiteit;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.NotPredicate;
import org.apache.commons.collections.iterators.UnmodifiableIterator;


/**
 * Een slimme Set implementatie voor collections die C/D-laag entiteiten bevatten met materiele historie.
 *
 * @param <T> type historie entiteit wat in deze Set gaat.
 */
public class MaterieleHistorieSetImpl<T extends MaterieelHistorisch & MaterieelVerantwoordbaar> implements
    MaterieleHistorieSet<T>
{

    private final Predicate vervallenRecordPredikaat = new Predicate() {

        @Override
        @SuppressWarnings("unchecked")
        public boolean evaluate(final Object object) {
            // Een record is vervallen als zijn datum tijd verval niet
            // leeg is.
            return ((T) object).getMaterieleHistorie()
                .getDatumTijdVerval() != null;
        }
    };

    /**
     * De backing-set. *
     */
    private final Set<T> interneSet;

    /**
     * Constructor die gelijk de interne set initialiseert naar de opgegeven set van historie instanties. Merk op dat <code>null</code> niet is toegestaan
     * als initiele waarde en dat er een {@link IllegalArgumentException} wordt gegooid als de set met <code>null</code> als parameter wordt
     * geconstrueerd.
     *
     * @param interneSet de interne set die deze HistorieSet moet bijhouden.
     */
    public MaterieleHistorieSetImpl(final Set<T> interneSet) {
        if (interneSet == null) {
            throw new IllegalArgumentException("Null als onderliggende set waarde is niet toegestaan.");
        }
        this.interneSet = interneSet;
    }

    @Override
    public void voegToe(final T nieuwRecord) {
        // Valideer de precondities voor een nieuw record.
        HistorieSetNieuwRecordValidator.valideerNieuwMaterieleHistorieRecord(nieuwRecord);

        // Verwerk het vervallen van het stuk historie waarvoor het nieuwe record geldig is.
        werkHistorieVervalBijVoorPeriode(nieuwRecord.getMaterieleHistorie(), nieuwRecord.getVerantwoordingInhoud());

        // Voeg het nieuwe record zelf toe.
        interneSet.add(nieuwRecord);
    }

    @Override
    public void beeindig(final MaterieleHistorie periodeRecord, final VerantwoordingsEntiteit verantwoording) {
        werkHistorieVervalBijVoorPeriode(periodeRecord, verantwoording);
    }

    @Override
    public void vervalActueleRecords(final VerantwoordingsEntiteit verantwoordingVerval, final DatumTijdAttribuut datumTijdVerval) {
        final T actueleRecord = getActueleRecord();
        if (actueleRecord == null) {
            throw new IllegalStateException("De materiele historie heeft geen actueel record.");
        } else {
            verplaatsCLaagRecordNaarDLaag(actueleRecord, verantwoordingVerval, datumTijdVerval);
        }

    }

    /**
     * Verplaats een (C-laag) record naar de D-laag; laat een record vervallen. Hiervoor wordt het record aangepast en wordt het tijdstipvervallen
     * ingevuld.
     *
     * @param cLaagRecord          record uit de C-laag.
     * @param verantwoordingVerval verantwoordings entiteit dat heeft geleid tot het vervallen.
     * @param tijdstipVerval       het tijdstip van de verval.
     */
    private void verplaatsCLaagRecordNaarDLaag(final T cLaagRecord, final VerantwoordingsEntiteit verantwoordingVerval,
        final DatumTijdAttribuut tijdstipVerval)
    {
        cLaagRecord.setVerantwoordingVerval(verantwoordingVerval);
        cLaagRecord.getMaterieleHistorie().setDatumTijdVerval(tijdstipVerval);
    }


    @Override
    public void vervalGeheleHistorie(final VerantwoordingsEntiteit verantwoordingVerval, final DatumTijdAttribuut datumTijdVerval) {
        final Set<T> nietVervallenRecords = getNietVervallenHistorie();

        for (T record : nietVervallenRecords) {
            verplaatsCLaagRecordNaarDLaag(record, verantwoordingVerval, datumTijdVerval);
        }
    }

    /**
     * Laat de bestaande historie vervallen voor de opgegeven periode. Dit kan zijn naar aanleiding van een nieuw record met deze geldigheid of
     * bijvoorbeeld een beëindiging voor deze periode.
     * <p/>
     * Het werkt als volgt: 1. Selecteer de bestaande C-laag records die overlapt worden. 2. Maak hiervoor eventueel benodigde kop en staart records aan
     * . 3.
     * Laat de overlapte records vervallen (verplaatsen van C-laag naar D-laag).
     *
     * @param historiePeriode materiele periode waarvoor historie moet vervallen
     * @param verantwoording  de verantwoording voor deze historie aanpassing
     */
    private void werkHistorieVervalBijVoorPeriode(final MaterieleHistorie historiePeriode,
        final VerantwoordingsEntiteit verantwoording)
    {
        // Selecteer records uit C-laag met overlap (in de tijd)
        // voor de opgegeven periode.
        final List<T> overlapteRecords = selecteerOverlapteActueleRecords(historiePeriode);

        // De niet geheel overlapte records zullen moeten worden aangepast (ingekort).
        // Let op: Het kan zijn dat de originele lijst van overlapteRecords aangepast wordt in deze methode.
        kopieerGrensRecordsEnPasDezeAan(historiePeriode, verantwoording, overlapteRecords);

        // Alle overlapte en deels overlapte records zullen moeten worden verplaatst naar de D-laag
        verplaatsOverlapteRecordsNaarDLaag(historiePeriode, verantwoording, overlapteRecords);
    }

    @Override
    public int getAantal() {
        return interneSet.size();
    }

    @Override
    public boolean isLeeg() {
        return interneSet.isEmpty();
    }

    @Override
    public Set<T> getHistorie() {
        return Collections.unmodifiableSet(interneSet);
    }

    @Override
    public Set<T> getNietVervallenHistorie() {
        final Set<T> nietVervallenHistorie = new HashSet<>();
        CollectionUtils.select(interneSet, new NotPredicate(vervallenRecordPredikaat), nietVervallenHistorie);
        return Collections.unmodifiableSet(nietVervallenHistorie);
    }

    @Override
    public List<T> selecteerOverlapteActueleRecords(final MaterieleHistorie overlappendeHistorie) {
        return getOverlapteRecords(overlappendeHistorie, getNietVervallenHistorie());
    }

    @Override
    public Set<T> getVervallenHistorie() {
        final Set<T> vervallenHistorie = new HashSet<>();
        CollectionUtils.select(interneSet, vervallenRecordPredikaat, vervallenHistorie);
        return Collections.unmodifiableSet(vervallenHistorie);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getHistorieRecord(final DatumAttribuut materieleDatum, final DatumTijdAttribuut formeleTijdstip) {
        final Predicate predikaat = MaterieleHistoriePredikaat.geldigOp(formeleTijdstip, materieleDatum);
        return (T) CollectionUtils.find(getHistorie(), predikaat);
    }

    @Override
    public T getActueleRecord() {
        return getHistorieRecord(DatumAttribuut.vandaag(), new DatumTijdAttribuut(new Date()));
    }

    /**
     * Alle (deels) overlapte records dienen uit de C-laag te worden gehaald en naar de D-laag te worden verplaatst, daar ze niet meer geldig zijn.
     * Hiervoor wordt het record aangepast en worden de actie verval en tijdstip verval ingevuld.
     *
     * @param nieuwRecordHistorie      nieuwe periode dat leidt tot het verplaaten van records naar D-Laag.
     * @param verantwoordingAanpassing de verantwoording voor de aanpassing
     * @param overlapteRecords         alle overlapte records
     */
    @SuppressWarnings("unchecked")
    private void verplaatsOverlapteRecordsNaarDLaag(final MaterieleHistorie nieuwRecordHistorie,
        final VerantwoordingsEntiteit verantwoordingAanpassing, final List<T> overlapteRecords)
    {
        for (final T t : overlapteRecords) {
            t.getMaterieleHistorie().setDatumTijdVerval(nieuwRecordHistorie.getTijdstipRegistratie());
            t.setVerantwoordingVerval(verantwoordingAanpassing);
        }
    }

    /**
     * Records die slechts deels worden overlapt, dus die aanvangen reeds voor de aanvang uit de context of nog doorlopen tot na het einde uit de context,
     * zullen moeten worden ingekort. Hiervoor worden nieuwe records aan de C-laag toegevoegd, welke een kopie zijn van de deels overlapte records, maar
     * waarvan de aanvangdatum of einddatum is aangepast, conform de context.
     *
     * @param nieuwRecordHistorie      materiele periode van het nieuwe record dat er toe leidt dat grens records worden aangepast.
     * @param verantwoordingAanpassing de verantwoording voor de aanpassing
     * @param overlapteRecords         alle overlappende records uit de C-laag
     */
    private void kopieerGrensRecordsEnPasDezeAan(final MaterieleHistorie nieuwRecordHistorie,
        final VerantwoordingsEntiteit verantwoordingAanpassing, final List<T> overlapteRecords)
    {
        final List<T> kopieVanOverlapteRecords = new ArrayList<>(overlapteRecords);
        for (final T kopieVanOverlapteRecord : kopieVanOverlapteRecords) {
            // Van alle overlapte records gaan we deze type gevallen afhandelen.
            // 1) Alle records met zelfde tijdstip registratie, dwz. records die zijn aangemaakt in een
            // eerdere actie, en nu opnieuw voorkomt in de overlapte records lijst, worden opnieuw aangepast
            // (kop, of staart of in zijn geheel overschaduwd). worden normaal gesproken gepushed naar de D-Laag.
            // Dit veroorzaakt 0-periode records (ts registratie == ts verval) en duplicate key violation.
            // Daarom verwijderen we deze records in zijn geheel.
            // Let op dat dit moet gebeuren voordat een nieuwe kop-record wordt aangemaakt (optie 2 en 3)
            // Gelukkig, nadat we het record hebben verwijderd, deze nog in het geheugen beschikbaar is, om
            // de kop/staart-records alsnog te kunnen aanmaken.
            // 2) aan het eind, het begin deel inkorten, wat we overhouden is staart record. Deze wordt weggeschreven
            // de originele record wordt naar de D-Laag gepushed.
            // 3) aan het begin, het einde korten we in, wat we overhouden is de kop record. Deze wordt weggeschreven.
            // de originele record wordt naar de D-laag gepushed.
            // 4) periode die volledig is overschaduwed. Deze gaat in zijn geheel naar de D-Laag. Hoeft niets te doen.

            if (hebbenZelfdeTijdstipRegistratie(kopieVanOverlapteRecord.getMaterieleHistorie(), nieuwRecordHistorie)) {
                // Overlappende geldigheid, zelfde ts-reg. Dit record verwijderen omdat dit een
                // left-over van de vorig actie is die zo meteen wordt overschreven.
                // Let op: de kop/staart-records worden nog wel doorgevoerd!
                interneSet.remove(kopieVanOverlapteRecord);
                overlapteRecords.remove(kopieVanOverlapteRecord);
            }

            if (historieOverlaptNietGeheelAanEind(nieuwRecordHistorie,
                kopieVanOverlapteRecord.getMaterieleHistorie()))
            {
                // Dit is afhandelen van de staart-record.
                // nieuwe ingekorte record maken en de bestaande naar D-Laag pushen.
                final T toeTeVoegenRecord =
                    kopieerRecord(kopieVanOverlapteRecord, verantwoordingAanpassing,
                        nieuwRecordHistorie.getTijdstipRegistratie());

                toeTeVoegenRecord.getMaterieleHistorie().setDatumAanvangGeldigheid(
                    nieuwRecordHistorie.getDatumEindeGeldigheid());
                interneSet.add(toeTeVoegenRecord);
            }

            if (historieOverlaptNietGeheelAanBegin(nieuwRecordHistorie,
                kopieVanOverlapteRecord.getMaterieleHistorie()))
            {
                // Dit is afhandelen van de kop-record.
                // nieuwe ingekorte record maken, de bestaande naar D-Laag pushen.
                final T toeTeVoegenRecord =
                    kopieerRecord(kopieVanOverlapteRecord, verantwoordingAanpassing,
                        nieuwRecordHistorie.getTijdstipRegistratie());

                toeTeVoegenRecord.getMaterieleHistorie().setDatumEindeGeldigheid(
                    nieuwRecordHistorie.getDatumAanvangGeldigheid());
                interneSet.add(toeTeVoegenRecord);
            }
        }
    }

    /**
     * Kopieert het opgegeven record, maar zet de aanpassing geldigheid actie en het tijdstip registratie van de kopie naar de opgegeven waardes (en
     * overschrijft dus de gekopieerde waardes).
     *
     * @param record                   het record dat gekopieerd dient te worden.
     * @param verantwoordingAanpassing de verantwoording die heeft geleidt tot de kopie (en dus de aanpassing).
     * @param tsReg                    het tijdstip van executie (en dus aanpassing).
     * @return een kopie van het opgegeven record.
     */
    @SuppressWarnings("unchecked")
    private T kopieerRecord(final T record, final VerantwoordingsEntiteit verantwoordingAanpassing,
        final DatumTijdAttribuut tsReg)
    {
        final T kopie = (T) record.kopieer();
        kopie.getMaterieleHistorie().setDatumTijdRegistratie(tsReg);
        kopie.setVerantwoordingAanpassingGeldigheid(verantwoordingAanpassing);
        return kopie;
    }

    /**
     * Controleert of de opgegeven histories hetzelfde tijdstip registratie hebben of niet.
     *
     * @param historieA historie A van de vergelijking.
     * @param historieB historie B van de vergelijking.
     * @return of de opgegeven histories hetzelfde tijdstip registratie hebben of niet.
     */
    private boolean
    hebbenZelfdeTijdstipRegistratie(final MaterieleHistorie historieA, final MaterieleHistorie historieB) {
        return historieA.getTijdstipRegistratie().equals(historieB.getTijdstipRegistratie());
    }

    /**
     * Geeft aan of de overlappende historie niet geheel het begin van de overlapte historie overlapt.
     *
     * @param overlappendeHistorie de overlappende historie
     * @param overlapteHistorie    de overlapte historie
     * @return of de overlapping aan het begin geheel is of niet
     */
    public boolean historieOverlaptNietGeheelAanBegin(final MaterieleHistorie overlappendeHistorie,
        final MaterieleHistorie overlapteHistorie)
    {
        return overlapteHistorie.getDatumAanvangGeldigheid().getWaarde() < overlappendeHistorie
            .getDatumAanvangGeldigheid().getWaarde();
    }

    /**
     * Geeft aan of de overlappende historie niet geheel het eind van de overlapte historie overlapt.
     *
     * @param overlappendeHistorie de overlappende historie
     * @param overlapteHistorie    de overlapte historie
     * @return of de overlapping aan het eind geheel is of niet
     */
    private boolean historieOverlaptNietGeheelAanEind(final MaterieleHistorie overlappendeHistorie,
        final MaterieleHistorie overlapteHistorie)
    {
        return overlappendeHistorie.getDatumEindeGeldigheid() != null
            && (overlapteHistorie.getDatumEindeGeldigheid() == null || overlapteHistorie.getDatumEindeGeldigheid()
            .getWaarde() > overlappendeHistorie.getDatumEindeGeldigheid().getWaarde());
    }

    /**
     * Selecteert alle records uit meegegeven lijst die deels of geheel worden overlapt. Indien er geen dergelijke records aanwezig zijn, zal een lege
     * lijst worden geretourneerd.
     * <p/>
     * Let op: alle records in de lijst worden meegenomen, onafhankelijk van C of D laag.
     *
     * @param overlappendeHistorie de materiele periode waar de overlapping voor gecheckt wordt
     * @param lijst                de lijst van records om te checken
     * @return lijst van records die overlapt worden.
     */
    private List<T> getOverlapteRecords(final MaterieleHistorie overlappendeHistorie, final Collection<T> lijst) {
        final List<T> overlapteRecords = new ArrayList<>();
        for (final T record : lijst) {
            if (isOverlappendeHistorie(overlappendeHistorie, record.getMaterieleHistorie())) {
                overlapteRecords.add(record);
            }
        }
        return overlapteRecords;
    }

    /**
     * Bekijkt of twee stukjes historie een overlap hebben of niet.
     *
     * @param overlappendeHistorie de overlappende historie
     * @param overlapteHistorie    de overlapte historie
     * @return of er sprake is van overlap of niet
     */
    private boolean isOverlappendeHistorie(final MaterieleHistorie overlappendeHistorie,
        final MaterieleHistorie overlapteHistorie)
    {
        boolean isOverlappend = false;
        final DatumEvtDeelsOnbekendAttribuut overlappendeDatumAanvang =
            overlappendeHistorie.getDatumAanvangGeldigheid();
        final DatumEvtDeelsOnbekendAttribuut overlappendeDatumEinde = overlappendeHistorie.getDatumEindeGeldigheid();
        final DatumEvtDeelsOnbekendAttribuut overlapteDatumAanvang = overlapteHistorie.getDatumAanvangGeldigheid();
        final DatumEvtDeelsOnbekendAttribuut overlapteDatumEinde = overlapteHistorie.getDatumEindeGeldigheid();

        if (overlappendeDatumEinde == null) {
            if (overlapteDatumAanvang.na(overlappendeDatumAanvang) || overlapteDatumEinde == null
                || overlapteDatumEinde.na(overlappendeDatumAanvang))
            {
                isOverlappend = true;
            }
        } else {
            if (overlapteDatumAanvang.voor(overlappendeDatumEinde)
                && ((overlapteDatumAanvang.naOfOp(overlappendeDatumAanvang) && overlapteDatumAanvang
                .voor(overlappendeDatumEinde))
                || (overlapteDatumEinde == null && overlapteDatumAanvang.voor(overlappendeDatumEinde))
                || (overlapteDatumEinde != null && overlapteDatumEinde.voorOfOp(overlappendeDatumEinde)
                    && overlapteDatumEinde.na(overlappendeDatumAanvang))
                || (overlapteDatumEinde != null && overlapteDatumAanvang.voorOfOp(overlappendeDatumAanvang)
                    && overlapteDatumEinde.naOfOp(overlappendeDatumEinde))))
            {
                isOverlappend = true;
            }
        }
        return isOverlappend;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return UnmodifiableIterator.decorate(interneSet.iterator());
    }

    @Override
    public String toString() {
        final String tabTab = "\t\t";
        final StringBuilder string = new StringBuilder();
        string.append("DatumAanvGel").append(tabTab).append("DatumEindGel").append(tabTab).append("DatumTijdReg")
            .append(tabTab).append("DatumTijdVerv").append("\n");
        for (final T t : interneSet) {
            string.append(t.getMaterieleHistorie().getDatumAanvangGeldigheid()).append(tabTab)
                .append(t.getMaterieleHistorie().getDatumEindeGeldigheid()).append(tabTab)
                .append(t.getMaterieleHistorie().getTijdstipRegistratie()).append(tabTab)
                .append(t.getMaterieleHistorie().getDatumTijdVerval()).append(tabTab).append("\n");
        }
        return string.toString();
    }
}
