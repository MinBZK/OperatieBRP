/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//package nl.bzk.brp.poc.generiekmodel;
//
//import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
//
//import java.util.List;
//import java.util.Set;
//
///**
// */
//public class HistorieUtil {
//
//
//    public static class HistorieVis extends ModelVisitorAdapter {
//
//        final Set<BrpAttribuut> attributen;
//
//        public HistorieVis(Set<BrpAttribuut> attributen) {
//            this.attributen = attributen;
//        }
//
//        @Override
//        public void visit(FormeleGroep g) {
//
//
//        final DatumTijdAttribuut registratieDatumTijd = formeleHistorie.getTijdstipRegistratie();
//        final DatumTijdAttribuut vervalDatumTijd = formeleHistorie.getDatumTijdVerval();
//
//        return (vervalDatumTijd == null || vervalDatumTijd.na(peilDatumTijd))
//                && (registratieDatumTijd.voor(peilDatumTijd) || registratieDatumTijd.getWaarde().equals(
//                peilDatumTijd.getWaarde()));
//
//            final List<FormeelVoorkomen> voorkomens = g.getVoorkomens();
//
//            for (FormeelVoorkomen voorkomen : voorkomens) {
//            }
//        }
//
//        @Override
//        public void visit(MaterieleGroep g) {
//
//            final List<MaterieelVoorkomen> voorkomens = g.getVoorkomens();
//        }
//    };
//
////    @Override
//
//    public static void voegToe(final BrpGroep groep, final Set<BrpAttribuut> attributen) {
//
//
//        groep.visit(new HistorieVis(attributen));
//    }
////    public final boolean evaluate(final Object object) {
//
////    }
//
//
//    @Override
//    public void voegToe(final T nieuwRecord) {
//        // Valideer de precondities voor een nieuw record.
//        HistorieSetNieuwRecordValidator.valideerNieuwFormeleHistorieRecord(nieuwRecord);
//
//        // Selecteer bestaande record uit de C-laag.
//        final T actueleRecord = getActueleRecord();
//
//        // Verplaats bestaande C-laag record naar de D-laag.
//        if (actueleRecord != null) {
//            if (actueleRecord.getFormeleHistorie().getTijdstipRegistratie()
//                    .equals(nieuwRecord.getFormeleHistorie().getTijdstipRegistratie()))
//            {
//                // Dit is het geval als 2 acties een record toevoegen. Bijv. 2x een afleiding achter elkaar.
//                // We verwijderen het vorige record, want het nieuwe record is van de laatste actie!
//                interneSet.remove(actueleRecord);
//            } else {
//                verplaatsCLaagRecordNaarDLaag(actueleRecord, nieuwRecord.getVerantwoordingInhoud(), nieuwRecord
//                        .getFormeleHistorie().getTijdstipRegistratie());
//            }
//        }
//
//        // Sla nieuwe C-laag record op.
//        interneSet.add(nieuwRecord);
//    }
//
//}
