# [ERD 및 이유]

- 수강신청 시, 실시간으로 변경이 되어야 하는 부분은 잔여좌석 수 이다.<br>
- 강의 기본 정보와 상세 정보에 대한 변경이 없어 최소한의 락을 걸기 위해 
Lecture 테이블을 Lecture, LectureItem, LectureInventory 로 나누었다.<br>
 

```mermaid
erDiagram
    MEMBER {
        bigint id
        varchar(10) name
        varchar(10) user_type
        timestamp created_at
        timestamp updated_at
    }

    LECTURE_HISTORY {
        bigint id
        bigint member_id
        bigint lecture_id
        bigint lecture_item_id
        varchar applyStatus
        timestamp created_at
        timestamp updated_at
    }

    LECTURE {
        bigint id
        varchar(10) name
        bigint member_id
        varchar(10) member_name
        timestamp created_at
        timestamp updated_at
    }

    LECTURE_ITEM {
        bigint id
        bigint lecture_id
        int capacity
        timestamp open_at
        boolean is_close
        timestamp created_at
        timestamp updated_at
    }

    LECTURE_INVENTORY {
        bigint id
        bigint lecture_id
        bigint lecture_item_id
        int remaining_seats
        timestamp created_at
        timestamp updated_at
    }

    MEMBER ||--o{ LECTURE_HISTORY: "member_id"
    LECTURE ||--o{ LECTURE_HISTORY: "member_id"
    LECTURE ||--|{ LECTURE_ITEM: "lecture_id"
    LECTURE_ITEM ||--|{ LECTURE_INVENTORY: "lecture_id"
