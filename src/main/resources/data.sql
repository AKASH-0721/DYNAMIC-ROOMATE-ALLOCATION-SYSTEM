-- --- Rooms ---
INSERT INTO rooms (room_number, room_type, capacity, occupancy, hostel_block, status)
VALUES ('A101', 'Single', 1, 0, 'Block A', 'Available');

INSERT INTO rooms (room_number, room_type, capacity, occupancy, hostel_block, status)
VALUES ('A102', 'Double', 2, 1, 'Block A', 'Available');

-- --- Students ---
INSERT INTO students (name, gender, branch, year, preference_type, room_type_preference, status)
VALUES ('Akash BC', 'Male', 'CSE', 3, 'Quiet', 'Single', 'Waiting');

INSERT INTO students (name, gender, branch, year, preference_type, room_type_preference, status)
VALUES ('Rahul R', 'Male', 'ECE', 2, 'Gaming', 'Double', 'Waiting');

-- --- Admin ---
INSERT INTO admins (username, password, role)
VALUES ('admin', 'admin123', 'SUPER_ADMIN');

-- --- Waitlist ---
INSERT INTO wait_list (student_id, preferred_room_type, priority_score, waiting_since)
VALUES (1, 'Single', 85.0, CURRENT_DATE);

INSERT INTO wait_list (student_id, preferred_room_type, priority_score, waiting_since)
VALUES (2, 'Double', 70.0, CURRENT_DATE);
