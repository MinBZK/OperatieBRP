#!/bin/bash

psql -d soa -U migratie -f export_meting2.sql;
