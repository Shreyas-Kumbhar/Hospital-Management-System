# 🏥 Hospital Management System (HMS)

A full-stack **Hospital Management System** built with **Spring Boot 4**, **Spring Security**, **Spring Data JPA**, **Thymeleaf**, and **MySQL**. It covers the complete admin workflow — user authentication, doctor and patient records, appointment scheduling, and prescription tracking — all behind a protected, role-based login system with a modern responsive UI.

---

## 🌟 Features at a Glance

| Module | What it does |
|---|---|
| 🔐 Authentication | Register, login, logout with BCrypt-hashed passwords |
| 👨‍⚕️ Doctors | Full CRUD — add, view, edit, delete, search by name/specialization |
| 🧑 Patients | Full CRUD — add, view, edit, delete, search by name/disease |
| 📅 Appointments | Schedule consults linking a patient + doctor, manage statuses |
| 💊 Prescriptions | Track medicine, dosage, notes, linked to a specific appointment |
| 📊 Dashboard | Live counts + Chart.js bar chart across all four modules |

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 24 |
| Framework | Spring Boot 4.0.5 |
| Security | Spring Security 6 (BCrypt, form login) |
| Persistence | Spring Data JPA / Hibernate |
| Database | MySQL 8.x |
| Templates | Thymeleaf + Thymeleaf Security Extras |
| Frontend | HTML5, CSS3, Vanilla JS, Chart.js |
| Build | Maven (Maven Wrapper included) |
| Utilities | Lombok, Jakarta Validation |

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 24+
- MySQL Server 8.0+
- Maven (or use `./mvnw`)

### 1. Create the database
```sql
CREATE DATABASE hospital_db;
```

### 2. Configure `application.properties`
```properties
spring.application.name=stud-man
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build and run
```bash
# Windows
.\mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw spring-boot:run
```

### 4. Open in browser
```
http://localhost:8080/login
```
Register an account, then log in — you will be redirected to the dashboard automatically.

---

## 📂 Project Structure

```
hospitalMngtSys/
├── src/main/java/com/hospital/hospitalMngtSys/
│   ├── HospitalMngtSysApplication.java   ← Entry point
│   ├── controller/                        ← HTTP request handlers
│   │   ├── AuthController.java
│   │   ├── DashBoardController.java
│   │   ├── DoctorController.java
│   │   ├── PatientController.java
│   │   ├── AppointmentController.java
│   │   └── PrescriptionController.java
│   ├── entity/                            ← JPA domain models
│   │   ├── User.java
│   │   ├── Doctor.java
│   │   ├── Patient.java
│   │   ├── Appointment.java
│   │   └── Prescription.java
│   ├── repository/                        ← Database access layer
│   │   ├── UserRepository.java
│   │   ├── DoctorRepository.java
│   │   ├── PatientRepository.java
│   │   ├── AppointmentRepository.java
│   │   └── PrescriptionRepository.java
│   ├── service/                           ← Business logic interfaces
│   │   ├── UserService.java
│   │   ├── DoctorService.java
│   │   ├── PatientService.java
│   │   ├── AppointmentService.java
│   │   └── PrescriptionService.java
│   ├── service/impl/                      ← Business logic implementations
│   │   ├── UserServiceImpl.java
│   │   ├── DoctorServiceImpl.java
│   │   ├── PatientServiceImpl.java
│   │   ├── AppointmentServiceImpl.java
│   │   └── PrescriptionServiceImpl.java
│   └── security/                          ← Spring Security configuration
│       ├── SecurityConfig.java
│       └── CustomUserDetailsService.java
├── src/main/resources/
│   ├── templates/                         ← Thymeleaf HTML pages
│   ├── static/css/style.css               ← Global stylesheet
│   └── application.properties
└── pom.xml
```

---

## 📋 Class-by-Class Reference

---

### 🚀 Entry Point

#### `HospitalMngtSysApplication.java`
The Spring Boot bootstrap class. Contains the `main` method that launches the embedded Tomcat server and initializes the entire application context via `SpringApplication.run()`. The `@SpringBootApplication` annotation enables auto-configuration, component scanning, and JPA repository detection in one step.

---

### 🗄️ Entities (Database Tables)

#### `User.java`
Represents an application user stored in the `users` table. Fields: `id`, `username` (unique), `password` (BCrypt hashed), `role` (e.g. `ROLE_ADMIN`). Used exclusively by Spring Security for authentication. Lombok annotations (`@Getter`, `@Setter`, `@Builder`, etc.) eliminate boilerplate getters/setters/constructors.

#### `Doctor.java`
Maps to the `doctor` table. Stores `id`, `name`, `specialization`, and `phone`. Jakarta Validation annotations (`@NotEmpty`) enforce that no field can be blank when submitted through a form. The `@Entity` annotation tells Hibernate to manage this class as a database table.

#### `Patient.java`
Maps to the `patient` table. Stores `id`, `name`, `age`, `address`, `email`, and `disease`. The `@Email` constraint on the email field validates format on form submission. The `@NotNull` on age ensures the field must be provided.

#### `Appointment.java`
Maps to the `appointment` table. Stores `id`, `date` (`LocalDate`), and `status` (e.g. Scheduled, Completed, Cancelled). Has two `@ManyToOne` relationships — each appointment belongs to one `Patient` and one `Doctor`, with foreign keys `patient_id` and `doctor_id`.

#### `Prescription.java`
Maps to the `prescription` table. Stores `id`, `medicine`, `dosage`, and `notes`. Has a `@OneToOne` relationship to `Appointment` via `appointment_id` — each prescription is linked to exactly one appointment.

---

### 🗃️ Repositories (Data Access Layer)

Repositories extend `JpaRepository<Entity, ID>`, which provides built-in methods like `save()`, `findById()`, `findAll()`, `delete()`, etc. without writing any SQL.

#### `UserRepository.java`
Provides `findByUsername(String)` to look up a user by their login name, and `existsByUsername(String)` to check for duplicates during registration. Both are auto-implemented by Spring Data JPA using method name parsing.

#### `DoctorRepository.java`
Custom queries: `findByNameIgnoreCase(String)` searches doctors by name case-insensitively; `findBySpecializationIgnoreCase(String)` filters by specialty; `deleteByNameIgnoreCase(String)` removes all doctors matching a name and returns the count of deleted rows.

#### `PatientRepository.java`
Custom queries: `findByNameIgnoreCase(String)` and `findByDiseaseIgnoreCase(String)` support case-insensitive search. `deleteByName(String)` removes patients by exact name match.

#### `AppointmentRepository.java`
Custom queries: `findByDoctorId(Long)` returns all appointments for a specific doctor; `findByPatientId(Long)` returns all appointments for a specific patient. Both are derived automatically from the method names by Spring Data JPA.

#### `PrescriptionRepository.java`
Custom query: `findByAppointmentId(Long)` retrieves the prescription linked to a given appointment ID, used when viewing appointment-specific prescription details.

---

### ⚙️ Service Interfaces

Service interfaces define the contract (what operations exist) without specifying how they work. This keeps the controllers decoupled from the implementation details.

#### `UserService.java`
Declares two methods: `register(username, rawPassword)` — creates a new user account and returns `null` if the username is taken; `existsByUsername(username)` — returns a boolean used in the registration controller to give a clear duplicate error.

#### `DoctorService.java`
Declares the full set of doctor operations: create, get all, get by ID, get by name, get by specialization, update, delete by ID, delete by name, and delete all.

#### `PatientService.java`
Declares all patient operations: create, get all, get by ID, search by name, search by disease, update, delete by ID, delete by name, and delete all.

#### `AppointmentService.java`
Declares: create, get by ID, get all, update, get by doctor ID, get by patient ID, and delete by ID.

#### `PrescriptionService.java`
Declares: create, get by ID, get all, update, delete by ID, and get by appointment ID (used to find the prescription tied to a specific appointment).

---

### 🔧 Service Implementations (`service/impl/`)

Implementations are annotated with `@Service` so Spring registers them as beans. They contain the actual business logic — input validation, error handling (via `ResponseStatusException`), and calls to the repository layer.

#### `UserServiceImpl.java`
Implements `UserService`. The `register()` method first checks if the username is already taken via `existsByUsername()`. If not, it hashes the raw password with `BCryptPasswordEncoder`, assigns the `ROLE_ADMIN` role, builds the `User` entity and saves it. Injects `PasswordEncoder` from the security config as a Spring bean.

#### `DoctorServiceImpl.java`
Implements `DoctorService`. All lookup methods throw `ResponseStatusException(NOT_FOUND)` if no matching record exists, which Spring MVC turns into a 404 response. The `updateDoctor()` method uses a null-check pattern — only fields that are non-null in the incoming object are applied to the existing record, so partial updates work correctly. `deleteByNameIgnoreCase()` returns the count of deleted rows; if zero, a 404 is thrown.

#### `PatientServiceImpl.java`
Implements `PatientService`. Same null-check partial-update pattern as `DoctorServiceImpl`. The `deletePatientByName()` method first fetches matching patients and throws a 404 if none exist, before calling the repository delete. `getAllPatients()` simply delegates to `patientRepository.findAll()`.

#### `AppointmentServiceImpl.java`
Implements `AppointmentService`. `createAppointment()` saves a fully-assembled `Appointment` entity (patient and doctor objects are resolved in the controller before reaching this layer). `updateAppointment()` applies partial updates — date, status, patient, and doctor are only overwritten if the incoming value is non-null. Lookup methods for doctor ID and patient ID delegate directly to custom repository queries.

#### `PrescriptionServiceImpl.java`
Implements `PrescriptionService`. `createPrescription()` expects the `Appointment` object to already be set on the entity before saving. `updatePrescription()` applies null-safe partial updates across medicine, dosage, notes, and appointment. `getPrescriptionByAppointmentId()` delegates to the repository's `findByAppointmentId()` query.

---

### 🌐 Controllers (HTTP Request Handlers)

Controllers are annotated with `@Controller` and use `@RequestMapping` to group URL paths. They receive HTTP requests, call the appropriate service, add data to the `Model`, and return the name of a Thymeleaf template to render.

#### `AuthController.java`
Handles `/login` (GET) and `/register` (GET + POST). The login GET simply renders the page and checks for `?error` or `?logout` query params to display alert messages. The register POST performs four sequential validations — blank username, password too short, passwords don't match, username already taken — redirecting back with a flash error message on any failure. On success it calls `userService.register()` and redirects to `/login` with a success message.

#### `DashBoardController.java`
Handles `GET /dashboard`. Injects all four services and calls `.size()` on their list results to pass `doctorCount`, `patientCount`, `appointmentCount`, and `prescriptionCount` into the model. These numbers are consumed by the Thymeleaf template and the Chart.js bar chart on the dashboard page.

#### `DoctorController.java`
Handles all routes under `/doctors`. The `saveDoctor()` POST uses `@Valid` and `BindingResult` — if validation fails (e.g. blank name), it re-renders the form with error messages instead of saving. The `updateDoctor()` POST skips re-validation since the edit form pre-fills existing values. Two search routes (`/name/{name}` and `/specialization/{specialization}`) pass filtered lists to the same `doctors.html` template.

#### `PatientController.java`
Handles all routes under `/patients`. Both `savePatient()` and `updatePatient()` use `@Valid` + `BindingResult` validation. Provides search endpoints by name (`/search/name/{name}`) and by disease (`/search/disease/{disease}`). `deleteAllPatients()` is a destructive bulk operation guarded by a JavaScript `confirm()` dialog in the UI.

#### `AppointmentController.java`
Handles all routes under `/appointment`. The tricky part here is that the HTML form sends patient and doctor as plain Long IDs (from `<select>` dropdowns), not as objects. The `saveAppointment()` and `updateAppointment()` methods capture these with `@RequestParam("patient") Long patientId` and `@RequestParam("doctor") Long doctorId`, then call `patientService.getPatientById()` and `doctorService.getDoctorById()` to resolve them to full entities before saving. Includes filter endpoints by doctor ID (`/doctor/{id}`) and patient ID (`/patient/{id}`).

#### `PrescriptionController.java`
Handles all routes under `/prescription`. Unlike other controllers it does not use `@ModelAttribute` for save/update — instead it takes individual `@RequestParam` strings for medicine, dosage, notes, and appointment ID. This avoids Thymeleaf binding issues with the `@OneToOne` appointment relationship. Both the add and edit handlers filter out appointments where patient or doctor is null before passing the list to the view. An explicit null-check on the appointment ID produces a user-facing error message rather than a silent failure.

---

### 🔒 Security (`security/`)

#### `CustomUserDetailsService.java`
Implements Spring Security's `UserDetailsService` interface. The `loadUserByUsername()` method is called automatically by Spring Security during login. It queries `UserRepository` for the given username, throws `UsernameNotFoundException` if not found (which Spring Security maps to a login failure), and wraps the `User` entity into a Spring `UserDetails` object with the stored BCrypt password and granted authority (role).

#### `SecurityConfig.java`
The central security configuration class. Defines four beans:
- **`PasswordEncoder`** — returns a `BCryptPasswordEncoder` instance, used both for hashing passwords at registration and verifying them at login.
- **`DaoAuthenticationProvider`** — wires `CustomUserDetailsService` and the `PasswordEncoder` together. Spring Security uses this provider to authenticate login attempts.
- **`AuthenticationManager`** — exposes the authentication manager as a bean, required for programmatic authentication flows.
- **`SecurityFilterChain`** — the core rule set: `/login`, `/register`, and all static assets (`/css/**`, `/js/**`, `/images/**`) are public; every other URL requires an authenticated session. Configures the custom login page at `/login`, the processing URL where Spring Security reads the username/password form fields, the success redirect to `/dashboard`, and the failure redirect to `/login?error=true`. Logout invalidates the session, clears authentication, and redirects to `/login?logout=true`.

---

## 🔗 URL Route Map

### Auth
| Method | URL | Description |
|---|---|---|
| GET | `/login` | Login page |
| POST | `/login` | Spring Security processes credentials |
| GET | `/register` | Registration page |
| POST | `/register` | Create new account |
| POST | `/logout` | End session, redirect to login |

### Dashboard
| Method | URL | Description |
|---|---|---|
| GET | `/dashboard` | Main dashboard with stats and chart |

### Doctors
| Method | URL | Description |
|---|---|---|
| GET | `/doctors/list` | All doctors table |
| GET | `/doctors/add` | Add doctor form |
| POST | `/doctors` | Save new doctor |
| GET | `/doctors/{id}` | Doctor detail view |
| GET | `/doctors/edit/{id}` | Edit doctor form |
| POST | `/doctors/update/{id}` | Update doctor |
| POST | `/doctors/delete/{id}` | Delete doctor by ID |
| POST | `/doctors/deleteAll` | Delete all doctors |
| GET | `/doctors/name/{name}` | Search by name |
| GET | `/doctors/specialization/{spec}` | Search by specialization |

### Patients
| Method | URL | Description |
|---|---|---|
| GET | `/patients/list` | All patients table |
| GET | `/patients/add` | Add patient form |
| POST | `/patients` | Save new patient |
| GET | `/patients/{id}` | Patient detail view |
| GET | `/patients/edit/{id}` | Edit patient form |
| POST | `/patients/update/{id}` | Update patient |
| POST | `/patients/delete/{id}` | Delete patient by ID |
| POST | `/patients/deleteAll` | Delete all patients |
| GET | `/patients/search/name/{name}` | Search by name |
| GET | `/patients/search/disease/{disease}` | Search by disease |

### Appointments
| Method | URL | Description |
|---|---|---|
| GET | `/appointment/list` | All appointments table |
| GET | `/appointment/add` | New appointment form |
| POST | `/appointment` | Save new appointment |
| GET | `/appointment/search/{id}` | Appointment detail view |
| GET | `/appointment/edit/{id}` | Edit appointment form |
| POST | `/appointment/update/{id}` | Update appointment |
| POST | `/appointment/delete/{id}` | Delete appointment |
| GET | `/appointment/doctor/{id}` | Appointments by doctor |
| GET | `/appointment/patient/{id}` | Appointments by patient |

### Prescriptions
| Method | URL | Description |
|---|---|---|
| GET | `/prescription/list` | All prescriptions table |
| GET | `/prescription/add` | Add prescription form |
| POST | `/prescription` | Save new prescription |
| GET | `/prescription/search/{id}` | Prescription detail view |
| GET | `/prescription/edit/{id}` | Edit prescription form |
| POST | `/prescription/edit/{id}` | Update prescription |
| POST | `/prescription/delete/{id}` | Delete prescription |

---

## 🎨 Frontend & Styling

All 17 Thymeleaf templates share a single stylesheet at `static/css/style.css`. Key design decisions:

- **CSS variables** — colors, spacing, shadows, and border-radius are defined as `:root` variables for easy theming.
- **Sidebar layout** — list pages use a fixed 260px sidebar with grouped navigation sections (Overview / Manage / Scheduling / Pharmacy). Collapses to icon-only mode on screens narrower than 900px.
- **Form pages** — centered card layout (`max-width: 520px`) with labeled input groups, focus ring glow, and inline field error messages.
- **Detail pages** — card with a colored left-border accent (blue for doctors, green for patients, yellow for appointments, teal for prescriptions).
- **Tables** — dark header row, hover highlight, badge-colored status indicators.
- **Auth pages** — dark gradient background with a floating card, icon-prefixed inputs, and real-time password match feedback on the register page.
- **Animations** — `fadeUp`, `slideLeft` (sidebar), `popIn` (stat cards) using CSS `@keyframes`.

---

## 📸 Screenshots

<div align="center">
  <img src="screenshots/dashboard.png" alt="Dashboard" width="800"/>
  <br/>
  <i>Dashboard Overview</i>
  <br/><br/>

  <img src="screenshots/doctors_list.png" alt="Doctors List" width="800"/>
  <br/>
  <i>Doctors Management</i>
  <br/><br/>

  <img src="screenshots/patients_list.png" alt="Patients List" width="800"/>
  <br/>
  <i>Patients Directory</i>
  <br/><br/>

  <img src="screenshots/appointments_list.png" alt="Appointments List" width="800"/>
  <br/>
  <i>Appointments Scheduling</i>
  <br/><br/>

  <img src="screenshots/prescriptions_list.png" alt="Prescriptions List" width="800"/>
  <br/>
  <i>Prescriptions Records</i>
  <br/><br/>

  <img src="screenshots/add_appointment.png" alt="Add Appointment" width="800"/>
  <br/>
  <i>Booking a New Appointment</i>
</div>

---

## 🔮 Possible Future Enhancements

- **Search & filter** on list pages (by name, date range, status)
- **Pagination** for large data sets using Spring Data `Pageable`
- **Email notifications** when an appointment is booked (Spring Mail)
- **PDF export** for prescriptions (iText / Flying Saucer)
- **Role separation** — `ROLE_DOCTOR` can only view their own appointments; `ROLE_RECEPTIONIST` can book but not delete
- **Soft delete** — archive records instead of hard deleting
- **Dashboard improvements** — today's appointment count, monthly trend line chart

---

## 👤 Author

Built as a full-stack Spring Boot learning project covering MVC architecture, Spring Security, JPA relationships, Thymeleaf templating, and modern CSS design.

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<div align="center">
  Built with ❤️ by <strong>Shreyas Kumbhar</strong>
</div>

