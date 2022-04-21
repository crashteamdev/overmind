--liquibase formatted sql
CREATE TABLE ke_category
(
    category_id  BIGINT            NOT NULL,
    title        CHARACTER VARYING NOT NULL,
    icon_link    CHARACTER VARYING NOT NULL,
    adult        BOOLEAN,
    eco          BOOLEAN,
    seo_meta_tag CHARACTER VARYING,
    seo_header   CHARACTER VARYING,
    PRIMARY KEY (category_id)
);