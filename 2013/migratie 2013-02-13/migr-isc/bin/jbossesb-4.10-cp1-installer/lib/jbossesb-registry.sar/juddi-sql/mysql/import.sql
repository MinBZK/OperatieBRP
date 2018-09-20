
    create table j3_address (
        id bigint not null auto_increment,
        sort_code varchar(10),
        tmodel_key varchar(255),
        use_type varchar(255),
        address_id bigint not null,
        primary key (id)
    );

    create table j3_address_line (
        id bigint not null auto_increment,
        key_name varchar(255),
        key_value varchar(255),
        line varchar(80) not null,
        address_id bigint not null,
        primary key (id)
    );

    create table j3_auth_token (
        auth_token varchar(51) not null,
        authorized_name varchar(255) not null,
        created datetime not null,
        last_used datetime not null,
        number_of_uses integer not null,
        token_state integer not null,
        primary key (auth_token)
    );

    create table j3_binding_category_bag (
        id bigint not null,
        entity_key varchar(255) not null,
        primary key (id),
        unique (entity_key)
    );

    create table j3_binding_descr (
        id bigint not null auto_increment,
        descr longtext not null,
        lang_code varchar(5),
        entity_key varchar(255) not null,
        primary key (id)
    );

    create table j3_binding_template (
        access_point_type varchar(255),
        access_point_url longtext,
        hosting_redirector varchar(255),
        entity_key varchar(255) not null,
        service_key varchar(255) not null,
        primary key (entity_key)
    );

    create table j3_business_category_bag (
        id bigint not null,
        entity_key varchar(255) not null,
        primary key (id),
        unique (entity_key)
    );

    create table j3_business_descr (
        id bigint not null auto_increment,
        descr longtext not null,
        lang_code varchar(5),
        entity_key varchar(255) not null,
        primary key (id)
    );

    create table j3_business_entity (
        entity_key varchar(255) not null,
        primary key (entity_key)
    );

    create table j3_business_identifier (
        id bigint not null auto_increment,
        key_name varchar(255),
        key_value varchar(255) not null,
        tmodel_key_ref varchar(255),
        entity_key varchar(255) not null,
        primary key (id)
    );

    create table j3_business_name (
        id bigint not null auto_increment,
        lang_code varchar(5),
        name varchar(255) not null,
        entity_key varchar(255) not null,
        primary key (id)
    );

    create table j3_business_service (
        entity_key varchar(255) not null,
        business_key varchar(255) not null,
        primary key (entity_key)
    );

    create table j3_category_bag (
        id bigint not null auto_increment,
        primary key (id)
    );

    create table j3_clerk (
        clerk_name varchar(255) not null,
        cred varchar(255),
        publisher_id varchar(255) not null,
        node_name varchar(255),
        primary key (clerk_name)
    );

    create table j3_client_subscriptioninfo (
        subscription_key varchar(255) not null,
        last_notified datetime,
        fromClerk_clerk_name varchar(255),
        toClerk_clerk_name varchar(255),
        primary key (subscription_key)
    );

    create table j3_contact (
        id bigint not null auto_increment,
        use_type varchar(255),
        entity_key varchar(255) not null,
        primary key (id)
    );

    create table j3_contact_descr (
        id bigint not null auto_increment,
        descr longtext not null,
        lang_code varchar(5),
        contact_id bigint not null,
        primary key (id)
    );

    create table j3_discovery_url (
        id bigint not null auto_increment,
        url varchar(255) not null,
        use_type varchar(255) not null,
        entity_key varchar(255) not null,
        primary key (id)
    );

    create table j3_email (
        id bigint not null auto_increment,
        email_address varchar(255) not null,
        use_type varchar(255),
        contact_id bigint not null,
        primary key (id)
    );

    create table j3_instance_details_descr (
        id bigint not null auto_increment,
        descr longtext not null,
        lang_code varchar(5),
        tmodel_instance_info_id bigint not null,
        primary key (id)
    );

    create table j3_instance_details_doc_descr (
        id bigint not null auto_increment,
        descr longtext not null,
        lang_code varchar(5),
        tmodel_instance_info_id bigint not null,
        primary key (id)
    );

    create table j3_keyed_reference (
        id bigint not null auto_increment,
        key_name varchar(255),
        key_value varchar(255) not null,
        tmodel_key_ref varchar(255),
        category_bag_id bigint,
        keyed_reference_group_id bigint,
        primary key (id)
    );

    create table j3_keyed_reference_group (
        id bigint not null auto_increment,
        tmodel_key varchar(255),
        category_bag_id bigint not null,
        primary key (id)
    );

    create table j3_node (
        name varchar(255) not null,
        custody_transfer_url varchar(255) not null,
        factory_initial varchar(255),
        factory_naming_provider varchar(255),
        factory_url_pkgs varchar(255),
        inquiry_url varchar(255) not null,
        juddi_api_url varchar(255),
        manager_name varchar(255) not null,
        proxy_transport varchar(255) not null,
        publish_url varchar(255) not null,
        security_url varchar(255) not null,
        subscription_url varchar(255) not null,
        primary key (name)
    );

    create table j3_overview_doc (
        id bigint not null auto_increment,
        overview_url varchar(255) not null,
        overview_url_use_type varchar(255),
        entity_key varchar(255),
        tomodel_instance_info_id bigint,
        primary key (id)
    );

    create table j3_overview_doc_descr (
        id bigint not null auto_increment,
        descr longtext not null,
        lang_code varchar(5),
        overview_doc_id bigint,
        primary key (id)
    );

    create table j3_person_name (
        id bigint not null auto_increment,
        lang_code varchar(5),
        name varchar(255) not null,
        contact_id bigint not null,
        primary key (id)
    );

    create table j3_phone (
        id bigint not null auto_increment,
        phone_number varchar(50) not null,
        use_type varchar(255),
        contact_id bigint not null,
        primary key (id)
    );

    create table j3_publisher (
        authorized_name varchar(255) not null,
        email_address varchar(255),
        is_admin varchar(5),
        is_enabled varchar(5),
        max_bindings_per_service integer,
        max_businesses integer,
        max_services_per_business integer,
        max_tmodels integer,
        publisher_name varchar(255) not null,
        primary key (authorized_name)
    );

    create table j3_publisher_assertion (
        from_key varchar(255) not null,
        to_key varchar(255) not null,
        from_check varchar(5) not null,
        key_name varchar(255) not null,
        key_value varchar(255) not null,
        tmodel_key varchar(255) not null,
        to_check varchar(5) not null,
        primary key (from_key, to_key)
    );

    create table j3_service_category_bag (
        id bigint not null,
        entity_key varchar(255) not null,
        primary key (id),
        unique (entity_key)
    );

    create table j3_service_descr (
        id bigint not null auto_increment,
        descr longtext not null,
        lang_code varchar(5),
        entity_key varchar(255) not null,
        primary key (id)
    );

    create table j3_service_name (
        id bigint not null auto_increment,
        lang_code varchar(5),
        name varchar(255) not null,
        entity_key varchar(255) not null,
        primary key (id)
    );

    create table j3_service_projection (
        business_key varchar(255) not null,
        service_key varchar(255) not null,
        primary key (business_key, service_key)
    );

    create table j3_subscription (
        subscription_key varchar(255) not null,
        authorized_name varchar(255) not null,
        binding_key varchar(255),
        brief bit,
        create_date datetime not null,
        expires_after datetime,
        last_notified datetime,
        max_entities integer,
        notification_interval varchar(255),
        subscription_filter longtext not null,
        primary key (subscription_key)
    );

    create table j3_subscription_chunk_token (
        chunk_token varchar(255) not null,
        data integer not null,
        end_point datetime,
        expires_after datetime not null,
        start_point datetime,
        subscription_key varchar(255) not null,
        primary key (chunk_token)
    );

    create table j3_subscription_match (
        id bigint not null auto_increment,
        entity_key varchar(255) not null,
        subscription_key varchar(255) not null,
        primary key (id)
    );

    create table j3_tmodel (
        deleted bit,
        lang_code varchar(5),
        name varchar(255) not null,
        entity_key varchar(255) not null,
        primary key (entity_key)
    );

    create table j3_tmodel_category_bag (
        id bigint not null,
        entity_key varchar(255) not null,
        primary key (id),
        unique (entity_key)
    );

    create table j3_tmodel_descr (
        id bigint not null auto_increment,
        descr longtext not null,
        lang_code varchar(5),
        entity_key varchar(255) not null,
        primary key (id)
    );

    create table j3_tmodel_identifier (
        id bigint not null auto_increment,
        key_name varchar(255),
        key_value varchar(255) not null,
        tmodel_key_ref varchar(255),
        entity_key varchar(255) not null,
        primary key (id)
    );

    create table j3_tmodel_instance_info (
        id bigint not null auto_increment,
        instance_parms longtext,
        tmodel_key varchar(255) not null,
        entity_key varchar(255) not null,
        primary key (id)
    );

    create table j3_tmodel_instance_info_descr (
        id bigint not null auto_increment,
        descr longtext not null,
        lang_code varchar(5),
        tmodel_instance_info_id bigint not null,
        primary key (id)
    );

    create table j3_transfer_token (
        transfer_token varchar(51) not null,
        expiration_date datetime not null,
        primary key (transfer_token)
    );

    create table j3_transfer_token_keys (
        id bigint not null auto_increment,
        entity_key varchar(255),
        transfer_token varchar(51) not null,
        primary key (id)
    );

    create table j3_uddi_entity (
        entity_key varchar(255) not null,
        authorized_name varchar(255) not null,
        created datetime,
        modified datetime not null,
        modified_including_children datetime,
        node_id varchar(255),
        primary key (entity_key)
    );

    alter table j3_address 
        add index FKF83236BE75D860FB (address_id), 
        add constraint FKF83236BE75D860FB 
        foreign key (address_id) 
        references j3_contact (id);

    alter table j3_address_line 
        add index FKC665B8D5F8B8D8CF (address_id), 
        add constraint FKC665B8D5F8B8D8CF 
        foreign key (address_id) 
        references j3_address (id);

    alter table j3_binding_category_bag 
        add index FKCF34B2376A68D45A (id), 
        add constraint FKCF34B2376A68D45A 
        foreign key (id) 
        references j3_category_bag (id);

    alter table j3_binding_category_bag 
        add index FKCF34B237CFBD88B7 (entity_key), 
        add constraint FKCF34B237CFBD88B7 
        foreign key (entity_key) 
        references j3_binding_template (entity_key);

    alter table j3_binding_descr 
        add index FK5EA60911CFBD88B7 (entity_key), 
        add constraint FK5EA60911CFBD88B7 
        foreign key (entity_key) 
        references j3_binding_template (entity_key);

    alter table j3_binding_template 
        add index FKD044BD6A2E448F3F (service_key), 
        add constraint FKD044BD6A2E448F3F 
        foreign key (service_key) 
        references j3_business_service (entity_key);

    alter table j3_binding_template 
        add index FKD044BD6AD1823CA5 (entity_key), 
        add constraint FKD044BD6AD1823CA5 
        foreign key (entity_key) 
        references j3_uddi_entity (entity_key);

    alter table j3_business_category_bag 
        add index FKD6D3ECB06A68D45A (id), 
        add constraint FKD6D3ECB06A68D45A 
        foreign key (id) 
        references j3_category_bag (id);

    alter table j3_business_category_bag 
        add index FKD6D3ECB0BEB92A91 (entity_key), 
        add constraint FKD6D3ECB0BEB92A91 
        foreign key (entity_key) 
        references j3_business_entity (entity_key);

    alter table j3_business_descr 
        add index FK3A24B4B8BEB92A91 (entity_key), 
        add constraint FK3A24B4B8BEB92A91 
        foreign key (entity_key) 
        references j3_business_entity (entity_key);

    alter table j3_business_entity 
        add index FKCA61A0CD1823CA5 (entity_key), 
        add constraint FKCA61A0CD1823CA5 
        foreign key (entity_key) 
        references j3_uddi_entity (entity_key);

    alter table j3_business_identifier 
        add index FKB0C7A652BEB92A91 (entity_key), 
        add constraint FKB0C7A652BEB92A91 
        foreign key (entity_key) 
        references j3_business_entity (entity_key);

    alter table j3_business_name 
        add index FK43F526F4BEB92A91 (entity_key), 
        add constraint FK43F526F4BEB92A91 
        foreign key (entity_key) 
        references j3_business_entity (entity_key);

    alter table j3_business_service 
        add index FK5D4255ACD1823CA5 (entity_key), 
        add constraint FK5D4255ACD1823CA5 
        foreign key (entity_key) 
        references j3_uddi_entity (entity_key);

    alter table j3_business_service 
        add index FK5D4255ACEF04CFEE (business_key), 
        add constraint FK5D4255ACEF04CFEE 
        foreign key (business_key) 
        references j3_business_entity (entity_key);

    alter table j3_clerk 
        add index FK34DC7D9F6BB0F935 (node_name), 
        add constraint FK34DC7D9F6BB0F935 
        foreign key (node_name) 
        references j3_node (name);

    alter table j3_client_subscriptioninfo 
        add index FKDF04CC095BFC6733 (fromClerk_clerk_name), 
        add constraint FKDF04CC095BFC6733 
        foreign key (fromClerk_clerk_name) 
        references j3_clerk (clerk_name);

    alter table j3_client_subscriptioninfo 
        add index FKDF04CC09F3CE9C04 (toClerk_clerk_name), 
        add constraint FKDF04CC09F3CE9C04 
        foreign key (toClerk_clerk_name) 
        references j3_clerk (clerk_name);

    alter table j3_contact 
        add index FK7551BEEABEB92A91 (entity_key), 
        add constraint FK7551BEEABEB92A91 
        foreign key (entity_key) 
        references j3_business_entity (entity_key);

    alter table j3_contact_descr 
        add index FK56CA9E6C2E3FD94F (contact_id), 
        add constraint FK56CA9E6C2E3FD94F 
        foreign key (contact_id) 
        references j3_contact (id);

    alter table j3_discovery_url 
        add index FKA042FDAABEB92A91 (entity_key), 
        add constraint FKA042FDAABEB92A91 
        foreign key (entity_key) 
        references j3_business_entity (entity_key);

    alter table j3_email 
        add index FK34F910E62E3FD94F (contact_id), 
        add constraint FK34F910E62E3FD94F 
        foreign key (contact_id) 
        references j3_contact (id);

    alter table j3_instance_details_descr 
        add index FK3CC165902B115C6F (tmodel_instance_info_id), 
        add constraint FK3CC165902B115C6F 
        foreign key (tmodel_instance_info_id) 
        references j3_tmodel_instance_info (id);

    alter table j3_instance_details_doc_descr 
        add index FK447324492B115C6F (tmodel_instance_info_id), 
        add constraint FK447324492B115C6F 
        foreign key (tmodel_instance_info_id) 
        references j3_tmodel_instance_info (id);

    alter table j3_keyed_reference 
        add index FK350C8454E075C8D7 (keyed_reference_group_id), 
        add constraint FK350C8454E075C8D7 
        foreign key (keyed_reference_group_id) 
        references j3_keyed_reference_group (id);

    alter table j3_keyed_reference 
        add index FK350C84541DB72652 (category_bag_id), 
        add constraint FK350C84541DB72652 
        foreign key (category_bag_id) 
        references j3_category_bag (id);

    alter table j3_keyed_reference_group 
        add index FKF6224ED41DB72652 (category_bag_id), 
        add constraint FKF6224ED41DB72652 
        foreign key (category_bag_id) 
        references j3_category_bag (id);

    alter table j3_overview_doc 
        add index FK5CD8D0E8C5BF8903 (entity_key), 
        add constraint FK5CD8D0E8C5BF8903 
        foreign key (entity_key) 
        references j3_tmodel (entity_key);

    alter table j3_overview_doc 
        add index FK5CD8D0E8E8CE1B36 (tomodel_instance_info_id), 
        add constraint FK5CD8D0E8E8CE1B36 
        foreign key (tomodel_instance_info_id) 
        references j3_tmodel_instance_info (id);

    alter table j3_overview_doc_descr 
        add index FK36FB9EA9BDC711C (overview_doc_id), 
        add constraint FK36FB9EA9BDC711C 
        foreign key (overview_doc_id) 
        references j3_overview_doc (id);

    alter table j3_person_name 
        add index FKCB7B8AFF2E3FD94F (contact_id), 
        add constraint FKCB7B8AFF2E3FD94F 
        foreign key (contact_id) 
        references j3_contact (id);

    alter table j3_phone 
        add index FK359202B82E3FD94F (contact_id), 
        add constraint FK359202B82E3FD94F 
        foreign key (contact_id) 
        references j3_contact (id);

    alter table j3_publisher_assertion 
        add index FK8A102449E3544929 (to_key), 
        add constraint FK8A102449E3544929 
        foreign key (to_key) 
        references j3_business_entity (entity_key);

    alter table j3_publisher_assertion 
        add index FK8A102449CCEE22D8 (from_key), 
        add constraint FK8A102449CCEE22D8 
        foreign key (from_key) 
        references j3_business_entity (entity_key);

    alter table j3_service_category_bag 
        add index FK185A68076A68D45A (id), 
        add constraint FK185A68076A68D45A 
        foreign key (id) 
        references j3_category_bag (id);

    alter table j3_service_category_bag 
        add index FK185A680748D0656D (entity_key), 
        add constraint FK185A680748D0656D 
        foreign key (entity_key) 
        references j3_business_service (entity_key);

    alter table j3_service_descr 
        add index FKB6D63D4148D0656D (entity_key), 
        add constraint FKB6D63D4148D0656D 
        foreign key (entity_key) 
        references j3_business_service (entity_key);

    alter table j3_service_name 
        add index FKCC1BE94B48D0656D (entity_key), 
        add constraint FKCC1BE94B48D0656D 
        foreign key (entity_key) 
        references j3_business_service (entity_key);

    alter table j3_service_projection 
        add index FK629F290F2E448F3F (service_key), 
        add constraint FK629F290F2E448F3F 
        foreign key (service_key) 
        references j3_business_service (entity_key);

    alter table j3_service_projection 
        add index FK629F290FEF04CFEE (business_key), 
        add constraint FK629F290FEF04CFEE 
        foreign key (business_key) 
        references j3_business_entity (entity_key);

    alter table j3_subscription_match 
        add index FK5B9C2F19BEEE42E5 (subscription_key), 
        add constraint FK5B9C2F19BEEE42E5 
        foreign key (subscription_key) 
        references j3_subscription (subscription_key);

    alter table j3_tmodel 
        add index FK83C8072BD1823CA5 (entity_key), 
        add constraint FK83C8072BD1823CA5 
        foreign key (entity_key) 
        references j3_uddi_entity (entity_key);

    alter table j3_tmodel_category_bag 
        add index FK7E0859DB6A68D45A (id), 
        add constraint FK7E0859DB6A68D45A 
        foreign key (id) 
        references j3_category_bag (id);

    alter table j3_tmodel_category_bag 
        add index FK7E0859DBC5BF8903 (entity_key), 
        add constraint FK7E0859DBC5BF8903 
        foreign key (entity_key) 
        references j3_tmodel (entity_key);

    alter table j3_tmodel_descr 
        add index FK63DFF1EDC5BF8903 (entity_key), 
        add constraint FK63DFF1EDC5BF8903 
        foreign key (entity_key) 
        references j3_tmodel (entity_key);

    alter table j3_tmodel_identifier 
        add index FKD5FB623DC5BF8903 (entity_key), 
        add constraint FKD5FB623DC5BF8903 
        foreign key (entity_key) 
        references j3_tmodel (entity_key);

    alter table j3_tmodel_instance_info 
        add index FKDC6C9004CFBD88B7 (entity_key), 
        add constraint FKDC6C9004CFBD88B7 
        foreign key (entity_key) 
        references j3_binding_template (entity_key);

    alter table j3_tmodel_instance_info_descr 
        add index FKD826B4062B115C6F (tmodel_instance_info_id), 
        add constraint FKD826B4062B115C6F 
        foreign key (tmodel_instance_info_id) 
        references j3_tmodel_instance_info (id);

    alter table j3_transfer_token_keys 
        add index FK8BBF49185ED9DD48 (transfer_token), 
        add constraint FK8BBF49185ED9DD48 
        foreign key (transfer_token) 
        references j3_transfer_token (transfer_token);
