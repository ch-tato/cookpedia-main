
# 🧩 Cookpedia JSP Website — Full To-Do List

---

## 🏁 **Phase 0 — Project Setup**

**Goal:** Prepare the development environment and base structure.

### ✅ To-Do

| Task | Description                                                                                                  | Responsible  |
| ---- | ------------------------------------------------------------------------------------------------------------ | ------------ |
| 0.1  | Install **IntelliJ IDEA Ultimate**, **Tomcat**, **MySQL Server**, and **MySQL Workbench**                    | All          |
| 0.2  | Create new **JSP + Servlet** project in IntelliJ                                                             | Backend Lead |
| 0.3  | Configure **Tomcat Server** (add local server configuration)                                                 | Backend Lead |
| 0.4  | Add **JDBC driver** (`mysql-connector-j.jar`) to project libraries                                           | Backend Lead |
| 0.5  | Setup project structure: <br> `/webapp` → JSP files<br>`/src` → servlets, DAO, model<br>`/WEB-INF` → web.xml | Backend Lead |
| 0.6  | Create **database “cookpedia_db”** in MySQL                                                                  | Database Dev |
| 0.7  | Implement all tables (PDM) using SQL                                                                         | Database Dev |
| 0.8  | Test DB connection using JDBC test servlet                                                                   | Backend Lead |

---

## 🔐 **Phase 1 — Authentication (Login & Register)**

**Goal:** Enable users to create accounts and log in.

### ✅ To-Do

| Task | Description                                                                                         | Responsible |
| ---- | --------------------------------------------------------------------------------------------------- | ----------- |
| 1.1  | Create `User` model class                                                                           | Backend     |
| 1.2  | Create `UserDAO` with methods:<br>• `registerUser()`<br>• `validateLogin()`<br>• `getUserByEmail()` | Backend     |
| 1.3  | Design `register.jsp` (form: username, email, password, confirm password)                           | Frontend    |
| 1.4  | Design `login.jsp` (form: email, password)                                                          | Frontend    |
| 1.5  | Create `RegisterServlet` and `LoginServlet`                                                         | Backend     |
| 1.6  | Add session handling (store `user_id` and `username` after login)                                   | Backend     |
| 1.7  | Add validation and error messages in JSP                                                            | Frontend    |
| 1.8  | Redirect to Home after successful login                                                             | Backend     |
| 1.9  | Create `logout.jsp` or `LogoutServlet` to invalidate session                                        | Backend     |
| 1.10 | Test full authentication flow                                                                       | QA Tester   |

---

## 🍳 **Phase 2 — Recipe Management (CRUD)**

**Goal:** Add, view, edit, and delete recipes.

### ✅ To-Do

| Task | Description                                                                                                                                         | Responsible |
| ---- | --------------------------------------------------------------------------------------------------------------------------------------------------- | ----------- |
| 2.1  | Create `Recipe` model                                                                                                                               | Backend     |
| 2.2  | Create `RecipeDAO` with:<br>• `insertRecipe()`<br>• `getAllRecipes()`<br>• `getRecipeById()`<br>• `updateRecipe()`<br>• `deleteRecipe()`            | Backend     |
| 2.3  | Create JSP pages:<br>• `add_recipe.jsp`<br>• `edit_recipe.jsp`<br>• `recipe_detail.jsp`<br>• `recipe_list.jsp`                                      | Frontend    |
| 2.4  | Implement Servlets:<br>• `AddRecipeServlet`<br>• `EditRecipeServlet`<br>• `DeleteRecipeServlet`<br>• `RecipeListServlet`<br>• `RecipeDetailServlet` | Backend     |
| 2.5  | Add image upload functionality (store file in `/uploads` and save path in DB)                                                                       | Backend     |
| 2.6  | Add validation and error handling (empty fields, wrong formats)                                                                                     | Frontend    |
| 2.7  | Test CRUD operations end-to-end                                                                                                                     | QA Tester   |

---

## 🥗 **Phase 3 — Category Management**

**Goal:** Allow admin/users to manage recipe categories.

### ✅ To-Do

| Task | Description                                                                                                                   | Responsible |
| ---- | ----------------------------------------------------------------------------------------------------------------------------- | ----------- |
| 3.1  | Create `Category` model                                                                                                       | Backend     |
| 3.2  | Create `CategoryDAO` with CRUD methods                                                                                        | Backend     |
| 3.3  | Create JSP pages:<br>• `category_list.jsp`<br>• `add_category.jsp`<br>• `edit_category.jsp`                                   | Frontend    |
| 3.4  | Create Servlets:<br>• `CategoryListServlet`<br>• `AddCategoryServlet`<br>• `EditCategoryServlet`<br>• `DeleteCategoryServlet` | Backend     |
| 3.5  | Add dropdown for selecting category when adding a recipe                                                                      | Frontend    |
| 3.6  | Test category CRUD and integration with recipes                                                                               | QA Tester   |

---

## 🧂 **Phase 4 — Ingredients and Steps**

**Goal:** Add detailed info for each recipe.

### ✅ To-Do

| Task | Description                                                                              | Responsible |
| ---- | ---------------------------------------------------------------------------------------- | ----------- |
| 4.1  | Create models: `Ingredient`, `RecipeIngredient`, `Step`                                  | Backend     |
| 4.2  | Create DAOs for each model                                                               | Backend     |
| 4.3  | In `add_recipe.jsp`, allow multiple ingredient and step inputs using JS (dynamic fields) | Frontend    |
| 4.4  | Modify `AddRecipeServlet` to handle multiple ingredients & steps                         | Backend     |
| 4.5  | Update `recipe_detail.jsp` to show ingredients and steps in order                        | Frontend    |
| 4.6  | Test recipe with multiple steps and ingredients                                          | QA Tester   |

---

## 👍 **Phase 5 — Voting System (Upvote / Downvote)**

**Goal:** Let users rate recipes and compute popularity.

### ✅ To-Do

| Task | Description                                         | Responsible |
| ---- | --------------------------------------------------- | ----------- |
| 5.1  | Create `RecipeVote` model and DAO                   | Backend     |
| 5.2  | Add `VoteServlet` (handle vote toggle logic)        | Backend     |
| 5.3  | Add upvote/downvote buttons on `recipe_detail.jsp`  | Frontend    |
| 5.4  | Use AJAX for instant voting feedback (optional)     | Frontend    |
| 5.5  | Display vote count (popularity = upvote - downvote) | Frontend    |
| 5.6  | Test voting logic (update/undo votes)               | QA Tester   |

---

## ⭐ **Phase 6 — Saved Recipes (Favorites)**

**Goal:** Allow users to bookmark their favorite recipes.

### ✅ To-Do

| Task | Description                                                           | Responsible |
| ---- | --------------------------------------------------------------------- | ----------- |
| 6.1  | Create `SavedRecipe` model and DAO                                    | Backend     |
| 6.2  | Create `SaveRecipeServlet` and `UnsaveRecipeServlet`                  | Backend     |
| 6.3  | Add “Save Recipe” / “Remove from Saved” button in `recipe_detail.jsp` | Frontend    |
| 6.4  | Create `saved_recipes.jsp` to list user’s saved recipes               | Frontend    |
| 6.5  | Test bookmark feature end-to-end                                      | QA Tester   |

---

## 🔎 **Phase 7 — Filter & Search**

**Goal:** Filter by category/popularity, and search recipes by name/ingredient.

### ✅ To-Do

| Task | Description                                                           | Responsible |
| ---- | --------------------------------------------------------------------- | ----------- |
| 7.1  | Add filter dropdown in `recipe_list.jsp` (by category or popularity)  | Frontend    |
| 7.2  | Modify `RecipeDAO.getAllRecipes()` to support SQL filtering           | Backend     |
| 7.3  | Add search bar for recipe name or ingredient                          | Frontend    |
| 7.4  | Create `SearchServlet` that handles keyword and returns filtered list | Backend     |
| 7.5  | Implement live search (AJAX, optional)                                | Frontend    |
| 7.6  | Test all filters and searches                                         | QA Tester   |

---

## 🎨 **Phase 8 — UI/UX & Styling**

**Goal:** Make the site minimalist and elegant (like Google’s design).

### ✅ To-Do

| Task | Description                                                  | Responsible |
| ---- | ------------------------------------------------------------ | ----------- |
| 8.1  | Create base layout `layout.jsp` (header, footer, navigation) | Frontend    |
| 8.2  | Use **CSS Flex/Grid** for clean layouts                      | Frontend    |
| 8.3  | Add **Google Fonts** (e.g. Poppins, Roboto)                  | Frontend    |
| 8.4  | Use a **white background + soft shadows**                    | Frontend    |
| 8.5  | Make UI responsive (mobile friendly)                         | Frontend    |
| 8.6  | Add favicon, site title “Cookpedia”                          | Frontend    |

---

## 🧰 **Phase 9 — Utilities & Security**

**Goal:** Improve robustness and data security.

### ✅ To-Do

| Task | Description                                     | Responsible        |
| ---- | ----------------------------------------------- | ------------------ |
| 9.1  | Hash passwords with SHA-256 or bcrypt           | Backend            |
| 9.2  | Use prepared statements (prevent SQL Injection) | Backend            |
| 9.3  | Validate inputs on both client & server side    | Backend & Frontend |
| 9.4  | Protect routes with login check filter          | Backend            |
| 9.5  | Handle image upload safely (size/type check)    | Backend            |
| 9.6  | Use HTTPS in production                         | DevOps             |

---

## 🚀 **Phase 10 — Deployment**

**Goal:** Host and share the website.

### ✅ To-Do

| Task | Description                                         | Responsible  |
| ---- | --------------------------------------------------- | ------------ |
| 10.1 | Export `.war` file from IntelliJ                    | Backend Lead |
| 10.2 | Deploy on **Apache Tomcat server** (local or cloud) | DevOps       |
| 10.3 | Set up MySQL on the server (import schema)          | Database     |
| 10.4 | Configure `context.xml` for DB connection           | Backend      |
| 10.5 | Final testing & bug fixing                          | QA Tester    |
| 10.6 | Document the project (README + setup guide)         | All          |

---

## 🗂️ Folder Structure (Suggested)

```
Cookpedia/
│
├── src/
│   ├── model/           ← JavaBeans (User, Recipe, etc.)
│   ├── dao/             ← Data Access Objects
│   ├── servlet/         ← All Servlets
│   └── utils/           ← DB connection helper, security utils
│
├── webapp/
│   ├── WEB-INF/
│   │   └── web.xml
│   ├── assets/
│   │   ├── css/
│   │   ├── js/
│   │   └── images/
│   ├── layout.jsp
│   ├── login.jsp
│   ├── register.jsp
│   ├── add_recipe.jsp
│   ├── edit_recipe.jsp
│   ├── recipe_list.jsp
│   ├── recipe_detail.jsp
│   ├── category_list.jsp
│   ├── saved_recipes.jsp
│   └── search.jsp
│
└── cookpedia_db.sql
```

---

# 🗂️ Cookpedia JSP Website — Project To-Do List (Divided by Member)

---

## To-Do

| #      | Task                                     | Description                                                                                              | Assigned To     | Est. Deadline |
| ------ | ---------------------------------------- | -------------------------------------------------------------------------------------------------------- | --------------- | ------------- |
| **1**  | **Set up project structure**             | Create the JSP project folder structure (`/src`, `/web`, `/WEB-INF`, etc.), connect to Tomcat and MySQL. | **Danish**      | 2 days        |
| **2**  | **Database design & setup**              | Design database schema (tables: `users`, `recipes`, `categories`, `votes`), implement SQL script.        | **Sienna**        | 2 days        |
| **3**  | **Front-end layout (base JSP template)** | Design main layout (navbar, footer, consistent style using JSP includes).                                | **Zaky**      | 2 days        |
| **4**  | **User authentication: Register**        | Create `register.jsp`, `RegisterServlet.java`, and validation logic.                                     | **Danish**      | 3 days        |
| **5**  | **User authentication: Login & Session** | Create `login.jsp`, `LoginServlet.java`, and session control for login/logout.                           | **Danish**      | 3 days        |
| **6**  | **Recipe CRUD: Create & Read**           | Implement recipe upload form, image handling, and recipe list page.                                      | **Sienna**        | 4 days        |
| **7**  | **Recipe CRUD: Update & Delete**         | Implement edit and delete features for user’s own recipes.                                               | **Sienna**        | 3 days        |
| **8**  | **Category System**                      | Create category table, dropdown in add-recipe form, and filter recipes by category.                      | **Zaky**      | 3 days        |
| **9**  | **Upvote/Downvote System**               | Add voting buttons on recipe list/detail pages, manage vote count in database.                           | **Danish**      | 3 days        |
| **10** | **Search System (Live Search)**          | Create AJAX-based search on recipes by title.                                                            | **Sienna**        | 3 days        |
| **11** | **Filter System**                        | Implement client-side and/or server-side filter (by vote, category, etc.).                               | **Zaky**      | 2 days        |
| **12** | **UI Styling & Responsiveness**          | Apply CSS/Bootstrap for consistent look; test on multiple devices.                                       | **Zaky**      | 3 days        |
| **13** | **Integration Testing**                  | Combine modules, test all CRUD + Auth + Voting + Filter/Search.                                          | **All Members** | 2 days        |
| **14** | **Bug Fixing & Polishing**               | Fix errors, optimize code, finalize presentation/demo version.                                           | **All Members** | 2 days        |

---

### 🧩 Summary of Division

| Member     | Responsibility Focus                          | Total Duration  |
| ---------- | --------------------------------------------- | --------------- |
| **Danish** | Backend logic (auth, session, voting) + setup | ~11 days        |
| **Sienna**   | Database & CRUD + search                      | ~12 days        |
| **Zaky** | Front-end UI + category + filter              | ~10 days        |
| **All**    | Testing & polish                              | 4 days (shared) |
