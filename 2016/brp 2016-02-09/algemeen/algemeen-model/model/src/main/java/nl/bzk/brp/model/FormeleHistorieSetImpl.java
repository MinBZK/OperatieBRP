/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.VerantwoordingsEntiteit;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.UnmodifiableIterator;


/**
 * Een slimme Set implementatie voor collections die C/D laag entiteiten bevatten met formele historie.
 *
 * @param <T> type historie entiteit wat in deze Set gaat.
 */
public class FormeleHistorieSetImpl<T extends FormeelHistorisch & FormeelVerantwoordbaar> implements
    FormeleHistorieSet<T>
{

    private final Set<T> interneSet;

    /**
     * Constructor die gelijk de interne set initialiseert naar de opgegeven set van historie instanties. Merk op dat <code>null</code> niet is toegestaan
     * als initiele waarde en dat er een {@link IllegalArgumentException} wordt gegooid als de set met <code>null</code> als parameter wordt
     * geconstrueerd.
     *
     * @param interneSet de interne set met historie voor deze slimme set.
     */
    public FormeleHistorieSetImpl(final Set<T> interneSet) {
        if (interneSet == null) {
            throw new IllegalArgumentException("Null als onderliggende set waarde is niet toegestaan.");
        }
        this.interneSet = interneSet;
    }

    @Override
    public void voegToe(final T nieuwRecord) {
        // Valideer de precondities voor een nieuw record.
        HistorieSetNieuwRecordValidator.valideerNieuwFormeleHistorieRecord(nieuwRecord);

        // Selecteer bestaande record uit de C-laag.
        final T actueleRecord = getActueleRecord();

        // Verplaats bestaande C-laag record naar de D-laag.
        if (actueleRecord != null) {
            if (actueleRecord.getFormeleHistorie().getTijdstipRegistratie()
                .equals(nieuwRecord.getFormeleHistorie().getTijdstipRegistratie()))
            {
                // Dit is het geval als 2 acties een record toevoegen. Bijv. 2x een afleiding achter elkaar.
                // We verwijderen het vorige record, want het nieuwe record is van de laatste actie!
                interneSet.remove(actueleRecord);
            } else {
                verplaatsCLaagRecordNaarDLaag(actueleRecord, nieuwRecord.getVerantwoordingInhoud(), nieuwRecord
                    .getFormeleHistorie().getTijdstipRegistratie());
            }
        }

        // Sla nieuwe C-laag record op.
        interneSet.add(nieuwRecord);
    }

    /**
     * Verplaats een (C-laag) record naar de D-laag; laat een record vervallen. Hiervoor wordt het record aangepast en wordt het tijdstipvervallen
     * ingevuld.
     *
     * @param cLaagRecord          records uit de C-laag.
     * @param verantwoordingVerval verantwoordings entiteit dat heeft geleid tot het vervallen.
     * @param tijdstipVerval       het tijdstip van de verval.
     */
    private void verplaatsCLaagRecordNaarDLaag(final T cLaagRecord, final VerantwoordingsEntiteit verantwoordingVerval,
        final DatumTijdAttribuut tijdstipVerval)
    {
        cLaagRecord.setVerantwoordingVerval(verantwoordingVerval);
        cLaagRecord.getFormeleHistorie().setDatumTijdVerval(tijdstipVerval);
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
    public void verval(final VerantwoordingsEntiteit verantwoordingVerval, final DatumTijdAttribuut datumTijdVerval) {
        final T actueleRecord = getActueleRecord();
        if (actueleRecord == null) {
            throw new IllegalStateException("De formele historie is al vervallen.");
        } else {
            verplaatsCLaagRecordNaarDLaag(actueleRecord, verantwoordingVerval, datumTijdVerval);
        }
    }

    @Override
    // In dit geval wordt de materiele datum gewoon genegeerd. Het principe van deze methode
    // is echter niet fout, aangezien de historie er op alle materiele momenten hetzelfde uitziet.
    public T getHistorieRecord(final DatumAttribuut materieleDatum, final DatumTijdAttribuut formeleTijdstip)
    {
        return getHistorieRecord(formeleTijdstip);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getHistorieRecord(final DatumTijdAttribuut formeleTijdstip) {
        final Predicate predikaat = FormeleHistoriePredikaat.bekendOp(formeleTijdstip);
        return (T) CollectionUtils.find(getHistorie(), predikaat);
    }

    @Override
    public T getActueleRecord() {
        return getHistorieRecord(new DatumTijdAttribuut(new Date()));
    }

    @Override
    public boolean heeftActueelRecord() {
        return getActueleRecord() != null;
    }

    @Override
    public boolean isVervallen() {
        return !heeftActueelRecord();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return UnmodifiableIterator.decorate(interneSet.iterator());
    }
}
