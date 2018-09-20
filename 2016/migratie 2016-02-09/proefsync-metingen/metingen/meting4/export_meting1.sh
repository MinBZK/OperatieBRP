#!/bin/bash

psql -d GBAV -U migratie -f export_meting1.sql;
