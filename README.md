# Campus Navigation and Reminder App
**Author:** Han Wang  
**Course:** CIS 3296 – Software Design  
**Project Type:** Proof of Concept (Java Swing Application)  
**Repository:** [https://github.com/cis3296f25/project-tun66365.github](https://github.com/cis3296f25/project-tun66365.github)

---

## 📘 Project Overview
The **Campus Navigation and Reminder App** is a lightweight desktop application designed to help students find routes between major campus buildings and receive quick reminders before classes or events.  

This version demonstrates the **core navigation visualization** using Java Swing, including:
- Route input and drawing  
- Compass and scale display  
- Direction arrows  
- 5-second reminder popup  

It serves as a **Proof of Concept** for how a more advanced version could integrate real campus maps and live navigation data (e.g., from [Temple Maps](https://www.temple.edu/maps-directions)).

---

## 🎯 Main Features
| Feature | Description |
|----------|-------------|
| 🗺️ **Map Visualization** | Displays main buildings as labeled points on a simulated campus grid. |
| 📍 **Route Finder** | Draws a red route from the Library (default start) to the chosen destination. |
| 🧭 **Compass** | Always-visible compass (top-right) with a red north indicator and N/E/S/W labels. |
| ➡️ **Direction Arrow** | A red triangular arrow appears along the route, pointing toward the destination. |
| 📏 **Scale Bar** | Shows approximate scale (≈100m) for visual reference. |
| ⏰ **Reminder Popup** | A simple “Set 5s Reminder” button displays a message box after 5 seconds. |
| 💬 **Status Bar Details** | Displays full route summary: distance, direction, and text instruction. |

---

## 🧩 Technologies Used
| Category | Details |
|-----------|----------|
| **Language** | Java (JDK 17) |
| **GUI Framework** | Swing |
| **Architecture** | MVC-style separation (`model/`, `ui/`, `util/`) |
| **IDE** | IntelliJ IDEA |
| **Platform** | macOS / Windows / Linux |
| **Version** | v2.1 — Direction Edition |

---

## ⚙️ Installation & Running the Demo

### 🔧 Requirements
- **Java Development Kit (JDK) 17+**
- **IntelliJ IDEA** or any Java IDE

### ▶️ Steps
1. **Clone or download** this repository:  
   ```bash
   git clone https://github.com/cis3296f25/project-tun66365.github
