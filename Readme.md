# Treasure Hunt Game - Backend

## 📌 Overview
The **Treasure Hunt Game Backend** is a **Spring Boot** application that manages the game logic, user authentication, and interactions with the frontend. It provides a **REST API** for creating users, starting game sessions, retrieving and solving riddles, and managing game history.

The backend maintains game state and riddles in a **database** and processes user interactions through **secure API endpoints**.

## 🚀 Features

### 🏷 User Management
- **Register users** with a unique username.
- Fetch **user details** by username.

### 🎮 Game Session Management
- **Start a new game** by selecting a difficulty level.
- Store **game progress** and track the number of **attempts left**.
- **Abandon a game** if the player decides to quit.
- **Delete a game** from the history.
- Fetch **game history** for a user.

### ❓ Riddle Interaction
- Retrieve the **current riddle** for a given game session.
- Submit an answer and validate it against stored riddle coordinates.
- **Update game status** and attempts left.

## 🛠️ Tech Stack
- **Backend:** Spring Boot
- **Database:** JPA with Hibernate (Supports PostgreSQL, MySQL, H2)
- **Security:** Token-based authentication (game session tokens)
- **API Communication:** RESTful Endpoints

## 📥 Installation & Setup
### 1️⃣ Clone the Repository
```sh
git clone https://github.com/B2Yassine00/treasure-hunting-back.git
cd treasure-hunting-back
```

### 2️⃣ Configure the Application
Create an `.env` file or set environment variables in `application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/treasure_hunt
spring.datasource.username=your_username
spring.datasource.password=your_password

# Server Configuration
server.port=8087
```

### 3️⃣ Run the Application
```sh
mvn spring-boot:run
```
The API will be available at **http://localhost:8087**.

## 🔥 API Endpoints

### 🏷 User API
| Method | Endpoint | Description |
|--------|------------|-------------|
| `POST` | `/users/register` | Registers a new user |

### 🎮 Game API
| Method | Endpoint | Description |
|--------|------------|-------------|
| `POST` | `/game/start` | Starts a new game session |
| `GET` | `/game/{gameId}/riddle` | Retrieves the current riddle for a game |
| `POST` | `/game/{gameId}/answer` | Submits an answer and checks correctness |
| `GET` | `/game/history` | Retrieves a user's game history |
| `DELETE` | `/game/{gameId}` | Deletes a game session |
| `POST` | `/game/{gameId}/abandon` | Abandons the current game session |

### 🔹 Request & Response Examples
#### **1️⃣ Start a New Game**
**Request:**
```json
{
  "username": "player1",
  "difficulty": "EASY"
}
```
**Response:**
```json
{
  "game_id": 10,
  "access_token": "10",
  "username": "player1",
  "attempts_left": 5,
  "difficulty": "EASY"
}
```

#### **2️⃣ Get Current Riddle**
**Request:**
```http
GET /game/10/riddle
Authorization: 10
```
**Response:**
```json
{
  "question": "Where is the Eiffel Tower?"
}
```

#### **3️⃣ Submit Answer**
**Request:**
```json
{
  "latitude": 48.8584,
  "longitude": 2.2945
}
```
**Response:**
```json
{
  "status": "IN_PROGRESS",
  "solved": true,
  "attempts_left": 4
}
```

#### **4️⃣ Abandon Game**
**Request:**
```http
POST /game/10/abandon
```
**Response:**
```json
{
  "message": "Game: 10 Abandoned"
}
```

#### **5️⃣ Delete Game**
**Request:**
```http
DELETE /game/10
```
**Response:**
```json
{
  "message": "Game: 10 Deleted"
}
```

## 📌 Future Enhancements
- **User Authentication System (JWT)**
- **Leaderboard & Achievements**
- **Real-time Multiplayer Mode**
- **WebSockets for Live Updates**

## 📜 Contributing
1. Fork the repo
2. Create a feature branch (`git checkout -b feature-name`)
3. Commit changes (`git commit -m 'Add feature'`)
4. Push to branch (`git push origin feature-name`)
5. Create a Pull Request

## 🏆 Credits
Developed by [@B2Yassine00](https://github.com/B2Yassine00)

---
🚀 Enjoy the Treasure Hunt Game and happy hunting! 🎉

