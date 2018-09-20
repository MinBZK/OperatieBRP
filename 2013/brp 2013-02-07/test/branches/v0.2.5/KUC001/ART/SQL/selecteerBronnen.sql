select * from kern.actie actie
                left join kern.bron bron on actie.id = bron.actie
                join kern.doc doc on bron.doc = doc.id
                left join kern.his_doc his_doc on doc.id = his_doc.doc
                where doc.oms = ${omschrijving_dbb0};