--liquibase formatted sql
--changeset vitaxa:create-overmind-db
CREATE DATABASE IF NOT EXISTS overmind;

--changeset vitaxa:ke-tables
DROP TABLE IF EXISTS overmind.ke_product;
CREATE TABLE overmind.ke_product
(
    timestamp              DateTime,
    fetchTime              UInt64,
    productId              String,
    skuId                  String,
    title                  String,
    rating                 Decimal32(2),
    categoryPath           Array(UInt64),
    reviewsAmount          UInt32,
    totalOrdersAmount      UInt64,
    totalAvailableAmount   UInt64,
    availableAmount        UInt64,
    charityCommission      UInt32,
    attributes             Array(String),
    tags                   Array(String),
    photoKey               String,
    characteristics        Map(String, String),
    sellerId               UInt64,
    sellerAccountId        UInt64,
    sellerTitle            String,
    sellerLink             String,
    sellerRegistrationDate DateTime,
    sellerRating           Decimal32(2),
    sellerReviewsCount     UInt64,
    sellerOrders           UInt64,
    sellerOfficial         UInt8,
    sellerContacts         Map(String, String),
    isEco                  UInt8,
    isPerishable           UInt8,
    hasVerticalPhoto       UInt8,
    showKitty              UInt8,
    bonusProduct           UInt8,
    adultCategory          UInt8,
    colorPhotoPreview      UInt8,
    fullPrice              UInt64,
    purchasePrice          UInt64,
    charityProfit          UInt64,
    barcode                String,
    vatType                String,
    vatAmount              UInt64,
    vatPrice               UInt64
) ENGINE = MergeTree()
      PARTITION BY toYYYYMM(timestamp)
      ORDER BY (timestamp, productId, skuId);

CREATE TABLE overmind.ke_seller
(
    timestamp          Date,
    sellerId           UInt64,
    accountId          UInt64,
    categoryIds        Array(String),
    title              String,
    link               String,
    hasCharityProducts UInt8,
    registrationDate   Date,
    rating             Decimal32(2),
    reviews            UInt64,
    orders             UInt64,
    official           UInt8,
    ogrnip             String,
    accountName        String
) ENGINE = MergeTree()
      PARTITION BY toYYYYMM(timestamp)
      ORDER BY (timestamp, sellerId, accountId);

CREATE TABLE overmind.ke_brand
(
    timestamp   Date,
    brandId     UInt64,
    name        String,
    description String,
    image       String,
    categoryId  UInt64,
    productIds  Array(String)
) ENGINE = MergeTree()
      PARTITION BY toYYYYMM(timestamp)
      ORDER BY (timestamp, brandId, categoryId);
