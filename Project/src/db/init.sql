CREATE DATABASE todo_list_db;
USE todo_list_db;

CREATE TABLE users (
  id int NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username (username)
)
CREATE TABLE tasks ( 
  id int NOT NULL AUTO_INCREMENT,
  user_id int NOT NULL,
  category_id int DEFAULT NULL,
  task varchar(255) NOT NULL,
  description text,
  due_date datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY user_id (user_id),
  KEY category_id (category_id),
  CONSTRAINT tasks_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  CONSTRAINT tasks_ibfk_2 FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL
) 
CREATE TABLE categories (
  id int NOT NULL AUTO_INCREMENT,
  title varchar(255) NOT NULL,
  due_date date NOT NULL,
  reminder int DEFAULT NULL,
  completed tinyint(1) DEFAULT '0',
  user_id int NOT NULL,
  PRIMARY KEY (id),
  KEY categories_ibfk_1 (user_id),
  CONSTRAINT categories_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
)

/*if you want to use CRUD method here's example code*/
1. Users Table
Create (Insert a new user):
INSERT INTO users (username, password)
VALUES ('example_user', 'secure_password');

Read (Retrieve all users or a specific user):
SELECT * FROM users;
SELECT * FROM users WHERE id = 1;

Update (Modify user information):
UPDATE users
SET password = 'new_secure_password'
WHERE id = 1;

Delete (Remove a user):
DELETE FROM users WHERE id = 1;

2. Tasks Table
Create (Insert a new task):
INSERT INTO tasks (user_id, category_id, task, description, due_date)
VALUES (1, NULL, 'Finish project', 'Complete the project by the end of the month', '2024-12-31 23:59:59');

Read (Retrieve tasks for a specific user or all tasks):
SELECT * FROM tasks;
SELECT * FROM tasks WHERE user_id = 1;

Update (Modify a task):
UPDATE tasks
SET task = 'Update documentation', description = 'Revise and update the user manual'
WHERE id = 1;

Delete (Remove a task):
DELETE FROM tasks WHERE id = 1;

3. Categories Table
Create (Insert a new category):
INSERT INTO categories (title, due_date, reminder, completed, user_id)
VALUES ('Work', '2024-12-31', 2, 0, 1);

Read (Retrieve all categories or categories for a specific user):
SELECT * FROM categories;
SELECT * FROM categories WHERE user_id = 1;

Update (Modify a category):
UPDATE categories
SET completed = 1
WHERE id = 1;

Delete (Remove a category):
DELETE FROM categories WHERE id = 1;
