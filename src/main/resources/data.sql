-- 외래 키 제약 조건 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- DELETE FROM cosmetic;
-- DELETE FROM brand;
-- DELETE FROM category;

-- 데이터 삭제 및 AUTO-INCREMENT 초기화
TRUNCATE TABLE cosmetic;
TRUNCATE TABLE brand;
TRUNCATE TABLE category;

-- 외래 키 제약 조건 활성화
SET FOREIGN_KEY_CHECKS = 1;

insert into brand(name, created_at, updated_at, status)
values ('TestBrand1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
       ('TestBrand2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
       ('TestBrand3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

insert into category(parent_category_id, name, created_at, updated_at, status)
values (NULL, 'TestCategory1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
       (NULL, 'TestCategory2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
       (NULL, 'TestCategory3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

insert into cosmetic(brand_id, category_id, name, price, capacity, explanation, cosmetic_image, created_at, updated_at, status)
values (1, 1, 'TestCosmetic1', 10000, 100, '테스트를 위한 화장품 데이터1', '테스트 이미지 URL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
       (2, 2, 'TestCosmetic2', 20000, 200, '테스트를 위한 화장품 데이터2', '테스트 이미지 URL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
       (3, 3, 'TestCosmetic3', 30000, 300, '테스트를 위한 화장품 데이터3', '테스트 이미지 URL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
       (1, 1, 'TestCosmetic4', 40000, 400, '테스트를 위한 화장품 데이터4', '테스트 이미지 URL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
       (2, 2, 'TestCosmetic5', 50000, 500, '테스트를 위한 화장품 데이터4', '테스트 이미지 URL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
       (3, 3, 'TestCosmetic6', 60000, 600, '테스트를 위한 화장품 데이터4', '테스트 이미지 URL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

