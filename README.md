# 🧮 Scientific Calculator Application

## 📌 Overview

This project is a fully functional **Scientific Calculator** built in Java using Swing.  
It provides accurate numerical calculations, a structured calculation engine, and a user-friendly graphical interface with customizable settings.

This is my **first GUI-based project**, focused on clean architecture, separation of concerns, and handling real-world edge cases in numeric input and formatting.

---

## 🚀 Features

### 🔢 Advanced Number Handling

- Supports **integers and decimal numbers**
- Maintains clean and consistent output format
- Prevents invalid input
- Handles edge cases
- Allows continued calculations using previous result

---

### 📜 Two-Level History System

The calculator tracks operations in **two stages** for better clarity:


5 × 3 = 15

15 × 6 = 90


- Previous operation displayed on top
- Current operation displayed below
- Final result shown on the main screen

---

### 🔍 Extended History Features

- View the **full calculation history** through a dedicated "Show History" option
- Select any previous calculation to **reload it into the calculator**
- Option to **save history** or **load history**
- Option to **export history as a text file**
- Option to **clear history** completely

---

### 🎛️ Settings

The application includes a **settings panel** where users can:

- Adjust **font size**
- Change **decimal precision**
- Switch between **Light Mode ☀️ and Dark Mode 🌙 (default)** 
- Reset settings to default or apply the changes made

---

### 🔬 Scientific Operations

The calculator includes support for basic scientific operations, such as:

- Trigonometric functions (sin, cos, tan)
- Logarithmic functions (log, ln)
- Power and exponentiation

---

## 🖥️ Technologies Used

- **Java**
- **Java Swing (AWT/Swing GUI)**
- **Eclipse IDE**
- **Git, GitHub, Visual Paradigm**

---

## 🏗️ Project Structure

The project is organized into separate components:

- **UI Layer (Swing)** – handles the graphical interface
- **Logic Layer (Calculator Engine)** – manages application state and behavior
- **Calculation Module** – performs all mathematical operations

---

## 🎯 What I Learned

- Handling **state in interactive applications**
- Implementing **theme switching and UI customization**
- Understanding how to structure a multi-class Java application
- Working with event-driven programming
- Handling edge cases in real-world scenarios
- Debugging complex application behavior
- Writing clean and readable code

---

## 🛠️ Future Improvements

- Keyboard input support
- Support for parentheses in expressions

---

## 💾 Installation

Follow these steps to clone and run the calculator locally. This guide works for Linux, macOS, and Windows (via Git Bash or WSL).

### 1. Clone the repository

Make sure you have Git installed. Open your terminal or command prompt and run:

```bash
git clone https://github.com/mitsako1926/ScientificCalculator.git

cd Calculator
```

---

### 2. Open the project

You can open the project in:

- IntelliJ IDEA (recommended)
- Eclipse
- or any Java IDE

Make sure the `src` folder is marked as the source root if required by your IDE.

---

### 3. Run the application

#### Using an IDE:

Navigate to:

```bash
src/engine/Main.java
```

and run the `Main` class.

---

#### Using the terminal:

```bash
cd src
javac engine/Main.java
java engine.Main
```
---
## 🤝 Contributing

Feel free to fork the project and submit improvements.  
Contributions such as bug fixes, optimizations, or new features are welcome.

---

## 📜 License

This project is licensed under the **MIT License**.
