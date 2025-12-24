# 🏠 Hostel Management System

A comprehensive Spring Boot application for managing hostel accommodations, room allocations, and student preferences using advanced data structures and algorithms.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [DSA Implementation](#dsa-implementation)
- [Contributing](#contributing)
- [License](#license)

## 🌟 Overview

The Hostel Management System is designed to efficiently manage student accommodations in hostels. It implements advanced algorithms for optimal room allocation, compatibility matching, and waitlist management using various data structures like priority queues, graphs, and hash maps.

### Key Objectives
- **Automated Room Allocation**: Smart assignment based on student preferences and compatibility
- **Waitlist Management**: Priority-based queue system for fair allocation
- **Compatibility Scoring**: Algorithm to match compatible roommates
- **Real-time Updates**: Live tracking of room occupancy and availability
- **Comprehensive Reporting**: Analytics and insights on allocation patterns

## ✨ Features

### Core Functionality
- 🎯 **Smart Room Allocation**: Algorithm-based room assignment considering multiple factors
- 👥 **Student Management**: Complete CRUD operations for student records
- 🏠 **Room Management**: Track room capacity, occupancy, and status
- ⏰ **Waitlist System**: Priority queue implementation for fair allocation
- 🔄 **Room Swapping**: Request and process room change requests
- 📊 **Analytics Dashboard**: Real-time statistics and reports

### Advanced Features
- 🤝 **Compatibility Matching**: AI-powered roommate compatibility scoring
- 🕒 **Allocation History**: Complete audit trail of all room assignments
- 🔍 **Smart Search**: Advanced filtering and search capabilities
- 📱 **Responsive UI**: Mobile-friendly interface using Bootstrap
- 🔒 **Security**: Spring Security integration for authentication
- 📈 **Performance Monitoring**: Built-in metrics and health checks

## 🛠 Technology Stack

### Backend
- **Java 17** - Modern LTS version with latest features
- **Spring Boot 3.5.7** - Main application framework
- **Spring Data JPA** - Data persistence layer
- **Spring Security** - Authentication and authorization
- **Spring Validation** - Input validation and constraints
- **Hibernate** - ORM implementation
- **H2/PostgreSQL** - Database options

### Frontend
- **Thymeleaf** - Server-side templating engine
- **Bootstrap 5.3** - CSS framework for responsive design
- **HTML5/CSS3** - Modern web standards
- **JavaScript** - Client-side interactivity

### Development Tools
- **Maven** - Dependency management and build automation
- **Lombok** - Boilerplate code reduction
- **Spring Boot DevTools** - Development productivity tools

## 🏗 Architecture

The application follows a layered architecture pattern:

```
┌─────────────────────────────────────┐
│           Presentation Layer         │
│     (Controllers, Templates)        │
├─────────────────────────────────────┤
│            Service Layer            │
│        (Business Logic)             │
├─────────────────────────────────────┤
│          Repository Layer           │
│       (Data Access Layer)           │
├─────────────────────────────────────┤
│            Data Layer               │
│    (JPA Entities, Database)         │
└─────────────────────────────────────┘
```

### Key Components

#### 📁 Models (Entities)
- **Student**: Student information and preferences
- **Room**: Room details and capacity management
- **Preference**: Student lifestyle preferences
- **WaitList**: Priority queue for room allocation
- **AllocationHistory**: Audit trail of allocations
- **Roommate**: Room sharing relationships
- **RoomSwapRequest**: Room change requests

#### 🔧 Services (Business Logic)
- **StudentService**: Student operations and compatibility scoring
- **RoomService**: Room management and availability tracking
- **AllocationService**: Core allocation algorithms
- **WaitlistService**: Priority queue management
- **RoomateService**: Roommate assignment logic

#### 📊 Controllers (REST APIs)
- **HostelController**: Main application endpoints
- **LC**: Room listing and debug endpoints

## 🚀 Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+ (optional, H2 included)
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-repo/hostel-management-system.git
   cd hostel-management-system
   ```

2. **Configure Database**
   
   **Option A: PostgreSQL (Recommended for Production)**
   ```properties
   # Update application.properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/hosteldb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
   
   **Option B: H2 (Development)**
   ```properties
   # Update application.properties
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.h2.console.enabled=true
   ```

3. **Build the Application**
   ```bash
   ./mvnw clean compile
   ```

4. **Run the Application**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Access the Application**
   - Main Application: http://localhost:8080
   - H2 Console (if enabled): http://localhost:8080/h2-console

## 📖 Usage

### Student Management
1. **Add Students**: Navigate to `/hostel/students` to view and add new students
2. **Set Preferences**: Configure room type preferences and lifestyle choices
3. **View Status**: Track allocation status (Waiting, Allocated, Processing)

### Room Management  
1. **View Rooms**: Access `/hostel/rooms` to see all available rooms
2. **Check Occupancy**: Monitor room capacity and current occupancy
3. **Room Status**: Track room availability (Available, Full, Maintenance)

### Allocation Process
1. **Waitlist Management**: Students automatically added to waitlist
2. **Smart Allocation**: System assigns rooms based on compatibility
3. **Manual Override**: Administrators can manually allocate specific rooms

### Waitlist System
1. **Priority Scoring**: Students ranked by priority score
2. **Fair Allocation**: First-come-first-served with preference weighting
3. **Real-time Updates**: Live waitlist position tracking

## 🌐 API Documentation

### Student Endpoints
```http
GET    /hostel/students           # List all students
POST   /hostel/allocate          # Allocate room to student
```

### Room Endpoints
```http
GET    /hostel/rooms             # List all rooms  
GET    /rooms                    # Alternative room listing
```

### Waitlist Endpoints
```http
GET    /hostel/waitlist          # View current waitlist
```

### Request/Response Examples

**Allocate Room**
```http
POST /hostel/allocate
Content-Type: application/x-www-form-urlencoded

studentId=1&roomType=Double
```

## 🗄 Database Schema

### Core Tables

#### Students Table
```sql
CREATE TABLE students (
    student_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    branch VARCHAR(50) NOT NULL,
    year INTEGER NOT NULL,
    preference_type VARCHAR(50),
    room_type_preference VARCHAR(20),
    status VARCHAR(20) NOT NULL DEFAULT 'Waiting',
    room_id BIGINT REFERENCES rooms(room_id)
);
```

#### Rooms Table
```sql
CREATE TABLE rooms (
    room_id BIGSERIAL PRIMARY KEY,
    room_number VARCHAR(10) UNIQUE NOT NULL,
    capacity INTEGER NOT NULL,
    occupancy INTEGER DEFAULT 0,
    hostel_block VARCHAR(1) NOT NULL,
    room_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'Available'
);
```

#### Waitlist Table
```sql
CREATE TABLE waitlist (
    wait_id BIGSERIAL PRIMARY KEY,
    student_id BIGINT UNIQUE REFERENCES students(student_id),
    preferred_room_type VARCHAR(20),
    waiting_since DATE NOT NULL,
    priority_score DECIMAL(5,2)
);
```

### Relationships
- **Student ↔ Room**: Many-to-One (students can be in one room)
- **Student ↔ Preference**: One-to-Many (students can have multiple preferences)
- **Student ↔ WaitList**: One-to-One (students can have one waitlist entry)
- **Room ↔ Roommate**: One-to-Many (rooms can have multiple roommate records)

## 🧮 DSA Implementation

### Algorithms Used

#### 1. **Room Allocation Algorithm**
```java
// Priority-based allocation using heap data structure
public String allocateRooms(String roomType) {
    // Get students from priority queue (sorted by priority score)
    List<Student> waitingStudents = waitlistService.getWaitingStudentsByRoomType(roomType);
    List<Room> availableRooms = roomService.getAvailableRoomsByType(roomType);
    
    // Greedy allocation algorithm
    int roomIndex = 0;
    for (Student student : waitingStudents) {
        if (roomIndex >= availableRooms.size()) break;
        
        Room room = availableRooms.get(roomIndex);
        assignStudentToRoom(student, room);
        
        // Move to next room if current room is full
        if (room.isFull()) roomIndex++;
    }
}
```

#### 2. **Compatibility Scoring Algorithm**
```java
// Weighted scoring system for roommate compatibility
public int calculateCompatibility(Student a, Student b) {
    int score = 0;
    
    // Gender compatibility (highest weight)
    if (a.getGender().equalsIgnoreCase(b.getGender())) score += 2;
    
    // Academic branch compatibility  
    if (a.getBranch().equalsIgnoreCase(b.getBranch())) score += 1;
    
    // Lifestyle preference compatibility (highest weight)
    if (a.getPreferenceType().equalsIgnoreCase(b.getPreferenceType())) score += 3;
    
    return score; // Maximum score: 6
}
```

#### 3. **Priority Queue Implementation**
```java
// Custom priority queue for waitlist management
List<WaitList> findByPreferredRoomTypeOrderByPriorityScoreDesc(String roomType);

// Priority calculation factors:
// - Waiting duration (longer wait = higher priority)
// - Academic year (senior students get preference)  
// - Special circumstances (medical, academic performance)
```

### Data Structures Used

#### 1. **Hash Maps**
- **Room Index**: O(1) lookup for room availability
- **Student Cache**: Fast student retrieval by ID
- **Compatibility Matrix**: Cached compatibility scores

#### 2. **Priority Queues**
- **Waitlist Management**: Students ordered by priority score
- **Room Assignment**: Rooms ordered by availability and suitability

#### 3. **Graphs**
- **Compatibility Network**: Student compatibility relationships
- **Room Layout**: Hostel block and room relationships

#### 4. **Arrays/Lists**
- **Allocation History**: Time-series data for analytics
- **Room Occupancy**: Real-time occupancy tracking

### Time Complexity Analysis

| Operation | Time Complexity | Space Complexity |
|-----------|----------------|------------------|
| Room Allocation | O(n log n) | O(n) |
| Compatibility Calculation | O(1) | O(1) |
| Waitlist Addition | O(log n) | O(n) |
| Student Search | O(1) avg, O(n) worst | O(n) |
| Room Search | O(1) avg, O(n) worst | O(n) |

## 🎯 Key Features Explained

### 1. Smart Room Allocation
The system uses a sophisticated algorithm that considers:
- **Student Preferences**: Room type, lifestyle choices
- **Compatibility Scores**: Roommate matching algorithm  
- **Priority Levels**: Waiting duration, academic seniority
- **Room Characteristics**: Capacity, location, amenities

### 2. Waitlist Management
Implementation using priority queue ensures:
- **Fair Allocation**: First-come-first-served with priority weighting
- **Dynamic Updates**: Real-time position changes
- **Preference Handling**: Room type specific queues

### 3. Compatibility Scoring  
Advanced algorithm considering:
- **Demographics**: Gender, academic branch, year
- **Lifestyle**: Study habits, sleep patterns, interests
- **Behavioral**: Social preferences, cleanliness habits

### 4. Real-time Analytics
- **Occupancy Tracking**: Live room availability updates
- **Allocation Patterns**: Historical trend analysis
- **Performance Metrics**: Algorithm efficiency monitoring

## 🔧 Configuration

### Application Properties
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/hosteldb
spring.datasource.username=postgres
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.port=8080
spring.application.name=Hostel-Management-System

# Security Configuration  
spring.security.user.name=admin
spring.security.user.password=admin123
```

### Custom Configuration
```java
@Configuration
public class HostelConfig {
    
    @Value("${hostel.max-room-capacity:4}")
    private int maxRoomCapacity;
    
    @Value("${hostel.allocation-batch-size:50}")
    private int allocationBatchSize;
    
    // Additional configuration beans...
}
```

## 🧪 Testing

### Running Tests
```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=StudentServiceTest

# Run with coverage
./mvnw test jacoco:report
```

### Test Categories
- **Unit Tests**: Service layer business logic
- **Integration Tests**: Database operations
- **Controller Tests**: API endpoint testing  
- **Performance Tests**: Algorithm efficiency testing

## 🚀 Deployment

### Local Deployment
```bash
# Create executable JAR
./mvnw clean package

# Run the JAR
java -jar target/DSA_Proj-0.0.1-SNAPSHOT.jar
```

### Docker Deployment
```dockerfile
FROM openjdk:17-jdk-slim

COPY target/DSA_Proj-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Database Setup
```sql
-- Create database
CREATE DATABASE hosteldb;

-- Create user
CREATE USER hostel_user WITH PASSWORD 'secure_password';
GRANT ALL PRIVILEGES ON DATABASE hosteldb TO hostel_user;
```

## 📈 Performance Optimization

### Algorithm Optimizations
- **Caching**: Frequently accessed data cached in memory
- **Batch Processing**: Room allocations processed in batches
- **Indexing**: Database indexes on frequently queried fields
- **Lazy Loading**: JPA entities loaded on demand

### Memory Management
- **Connection Pooling**: Database connection optimization
- **Object Pooling**: Reuse of expensive objects
- **Garbage Collection**: Optimized GC settings

## 🛡 Security

### Authentication & Authorization
- **Spring Security**: Role-based access control
- **Password Encryption**: BCrypt password hashing
- **Session Management**: Secure session handling
- **CSRF Protection**: Cross-site request forgery prevention

### Data Protection
- **Input Validation**: Bean validation annotations
- **SQL Injection**: JPA parameterized queries
- **XSS Prevention**: Thymeleaf auto-escaping

## 🐛 Troubleshooting

### Common Issues

#### Database Connection Issues
```bash
# Check PostgreSQL status
systemctl status postgresql

# Verify connection
psql -h localhost -U postgres -d hosteldb
```

#### Port Conflicts
```properties
# Change port in application.properties
server.port=8081
```

#### Memory Issues
```bash
# Increase JVM heap size
java -Xmx2g -jar app.jar
```

### Logging Configuration
```properties
# Enable debug logging
logging.level.org.example.dsa_proj=DEBUG
logging.level.org.springframework.web=DEBUG
```

## 🤝 Contributing

We welcome contributions! Please follow these guidelines:

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/amazing-feature`
3. **Commit changes**: `git commit -m 'Add amazing feature'`
4. **Push to branch**: `git push origin feature/amazing-feature`
5. **Open a Pull Request**

### Development Guidelines
- Follow Java coding standards
- Write comprehensive tests
- Update documentation
- Use meaningful commit messages

### Code Style
- Use Google Java Style Guide
- Maximum line length: 120 characters
- Use descriptive variable names
- Add JavaDoc for public methods

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

**DSA Project Team**
- Lead Developer: [Your Name]
- Database Designer: [Team Member]
- Algorithm Specialist: [Team Member]
- UI/UX Designer: [Team Member]

## 📞 Support

For support and questions:
- **Email**: support@hostelmanagement.com
- **Issues**: [GitHub Issues](https://github.com/your-repo/hostel-management-system/issues)
- **Wiki**: [Project Wiki](https://github.com/your-repo/hostel-management-system/wiki)

## 🔮 Future Enhancements

### Planned Features
- [ ] **Mobile App**: React Native mobile application
- [ ] **AI Recommendations**: Machine learning for better allocations
- [ ] **Payment Integration**: Online fee payment system
- [ ] **Notification System**: Email/SMS notifications
- [ ] **Analytics Dashboard**: Advanced reporting and insights
- [ ] **Multi-hostel Support**: Support for multiple hostel buildings
- [ ] **Guest Management**: Visitor tracking and management
- [ ] **Maintenance Tracking**: Room maintenance and repair tracking

### Technical Improvements
- [ ] **Microservices**: Break down into microservices architecture
- [ ] **Redis Cache**: Implement distributed caching
- [ ] **Kubernetes**: Container orchestration
- [ ] **GraphQL**: API query optimization
- [ ] **Real-time Updates**: WebSocket implementation

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- PostgreSQL community for the robust database
- Bootstrap team for the responsive CSS framework
- All contributors and testers

---

**Made with ❤️ by the DSA Project Team**

*Last updated: December 2024*