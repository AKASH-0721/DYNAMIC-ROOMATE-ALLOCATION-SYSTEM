-- Sample data for Hostel Management System
-- This file automatically populates the database with test data

-- Insert sample rooms
INSERT INTO rooms (room_number, capacity, occupancy, hostel_block, room_type, status) VALUES
('A101', 2, 0, 'A', 'Double', 'Available'),
('A102', 2, 1, 'A', 'Double', 'Available'),
('A103', 1, 0, 'A', 'Single', 'Available'),
('A104', 3, 2, 'A', 'Triple', 'Available'),
('A105', 4, 4, 'A', 'Quad', 'Full'),
('B201', 2, 0, 'B', 'Double', 'Available'),
('B202', 1, 1, 'B', 'Single', 'Full'),
('B203', 3, 0, 'B', 'Triple', 'Available'),
('B204', 2, 0, 'B', 'Double', 'Maintenance'),
('C301', 1, 0, 'C', 'Single', 'Available');

-- Insert sample students
INSERT INTO students (name, gender, branch, "year", preference_type, room_type_preference, status, room_id) VALUES
('Rahul Sharma', 'Male', 'Computer Science', 2, 'Studious', 'Single', 'Allocated', 7),
('Priya Patel', 'Female', 'Electronics', 3, 'Social', 'Double', 'Waiting', NULL),
('Amit Kumar', 'Male', 'Mechanical', 1, 'Gaming', 'Double', 'Allocated', 2),
('Sneha Reddy', 'Female', 'Civil', 4, 'Quiet', 'Single', 'Waiting', NULL),
('Vikram Singh', 'Male', 'Computer Science', 2, 'Social', 'Triple', 'Allocated', 4),
('Kavya Nair', 'Female', 'Electrical', 3, 'Studious', 'Double', 'Waiting', NULL),
('Arjun Gupta', 'Male', 'Mechanical', 1, 'Gaming', 'Quad', 'Allocated', 5),
('Divya Shah', 'Female', 'Computer Science', 2, 'Quiet', 'Single', 'Processing Allocation', NULL),
('Rohit Verma', 'Male', 'Electronics', 4, 'Social', 'Double', 'Waiting', NULL),
('Ananya Das', 'Female', 'Civil', 1, 'Studious', 'Triple', 'Allocated', 4);

-- Insert sample student preferences
INSERT INTO student_preferences (student_id, study_time, sleep_time, interests, roommate_gender_preference, noise_level_preference, cleanliness_level) VALUES
(1, 'Morning', 'Early', 'Reading, Coding', 'Same', 'Quiet', 'Very Clean'),
(2, 'Evening', 'Normal', 'Music, Dancing', 'Same', 'Social', 'Clean'),
(3, 'Night', 'Late', 'Gaming, Movies', 'Same', 'Moderate', 'Normal'),
(4, 'Morning', 'Early', 'Reading, Yoga', 'Same', 'Quiet', 'Very Clean'),
(5, 'Afternoon', 'Normal', 'Sports, Music', 'Same', 'Social', 'Clean'),
(6, 'Evening', 'Early', 'Studying, Books', 'Same', 'Quiet', 'Very Clean'),
(7, 'Night', 'Very Late', 'Gaming, Sports', 'Same', 'Very Social', 'Flexible'),
(8, 'Morning', 'Early', 'Reading, Art', 'Same', 'Quiet', 'Clean'),
(9, 'Evening', 'Normal', 'Movies, Friends', 'Same', 'Social', 'Normal'),
(10, 'Afternoon', 'Normal', 'Studies, Dance', 'Same', 'Moderate', 'Clean');

-- Insert sample waitlist entries
INSERT INTO wait_list (student_id, preferred_room_type, waiting_since, priority_score) VALUES
(2, 'Double', '2024-01-15', 85.5),
(4, 'Single', '2024-01-10', 92.0),
(6, 'Double', '2024-01-20', 78.3),
(8, 'Single', '2024-01-18', 88.7),
(9, 'Double', '2024-01-25', 72.1);

-- Insert sample allocation history
INSERT INTO allocation_history (student_id, room_id, allocation_date, reason, created_by) VALUES
(1, 7, '2024-01-05', 'Initial Allocation', 'admin'),
(3, 2, '2024-01-08', 'Initial Allocation', 'admin'),
(5, 4, '2024-01-12', 'Initial Allocation', 'admin'),
(7, 5, '2024-01-15', 'Initial Allocation', 'admin'),
(10, 4, '2024-01-20', 'Roommate Request', 'admin');

-- Insert sample roommate assignments
INSERT INTO roommates (room_id, student_id, joined_date, compatibility_score) VALUES
(2, 3, '2024-01-08', 85),
(4, 5, '2024-01-12', 90),
(4, 10, '2024-01-20', 88),
(5, 7, '2024-01-15', 75),
(7, 1, '2024-01-05', 95);

-- Insert sample room swap requests
INSERT INTO room_swap_requests (student_id, current_room_id, requested_room_type, status, request_date, reason, priority_level) VALUES
(3, 2, 'Single', 'Pending', '2024-01-25', 'Need more privacy for studies', 4),
(5, 4, 'Double', 'Under Review', '2024-01-22', 'Roommate compatibility issues', 3);

-- Insert sample admin (using plain text passwords for testing)
INSERT INTO system_admins (username, password_hash, role, full_name, email, is_active, created_at) VALUES
('admin', 'admin123', 'SUPER_ADMIN', 'System Administrator', 'admin@hostel.edu', true, '2024-01-01 00:00:00'),
('manager', 'manager123', 'HOSTEL_MANAGER', 'Hostel Manager', 'manager@hostel.edu', true, '2024-01-01 00:00:00'),
('coordinator', 'coord123', 'ROOM_COORDINATOR', 'Room Coordinator', 'coordinator@hostel.edu', true, '2024-01-01 00:00:00');
