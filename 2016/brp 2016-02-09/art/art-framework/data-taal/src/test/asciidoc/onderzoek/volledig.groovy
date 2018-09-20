import nl.bzk.brp.datataal.model.Persoon

def willem = Persoon.uitDatabase(bsn: 302533928)
Persoon.nieuweGebeurtenissenVoor(willem) {
    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart',
            verwachteAfhandelDatum: '2015-04-01')
    }
}

Persoon.slaOp(willem)
