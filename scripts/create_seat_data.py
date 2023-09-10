# 홀과 무대 좌석 정보
halls = [
    {"hall_id": 2, "hall_name": "Hall A", "seat_count": 50},
    {"hall_id": 3, "hall_name": "Hall B", "seat_count": 100},
    {"hall_id": 4, "hall_name": "Hall C", "seat_count": 200}
]

# .sql 파일 생성
with open("mock_seat_data.sql", "w") as sql_file:
    # 홀 생성
    for hall in halls:
        hall_id = hall["hall_id"]
        hall_name = hall["hall_name"]
        query = f"INSERT INTO HALL (id, name) VALUES ({hall_id}, '{hall_name}');\n"
        sql_file.write(query)

    # 무대 생성 (각 홀에 대해)
    for hall in halls:
        stage_id = hall["hall_id"]
        hall_id = hall["hall_id"]
        stage_name = f"Stage {stage_id}"
        query = f"INSERT INTO STAGE (id, hall_id, name) VALUES ({stage_id}, {hall_id}, '{stage_name}');\n"
        sql_file.write(query)

    # 대량의 무대 좌석 데이터 생성 및 삽입 (각 홀에 대해)
    for hall in halls:
        stage_id = hall["hall_id"]
        seat_count = hall["seat_count"]

        for seat_number in range(1, seat_count + 1):
            # `limit` 값 (예시로 2를 사용)
            seat_limit = 2
            query = f"INSERT INTO STAGE_SEAT (id, `row`, col, `limit`, stage_id) VALUES ({seat_number}, {seat_number}, 1, {seat_limit}, {stage_id});\n"
            sql_file.write(query)
