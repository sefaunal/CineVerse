# CineVerse
CineVerse is a web application that allows users to explore and manage a movie database. It offers a user-friendly interface for browsing, adding, editing, and deleting movies. The application utilizes technologies such as Java, Thymeleaf, Spring Boot, and MongoDB.

<br>

## Features
- **User Registration**: Users can create an account to access the application.

- **Movie Listing**: View a comprehensive list of movies stored in the database.

- **Add New Movie**: Admins can add new movies by providing details like title, genre, release date, and description.

- **Edit Movie Details**: Existing movie entries can be updated to correct or enhance information.

- **Search Functionality**: Quickly find movies by title or genre using the search feature.

- **Movie Comments**: Users can leave comments on movie pages.

- **Admin Panel**: Administrators have access to an admin panel with additional privileges.
  
    - **Grant/Revoke Admin Privileges**: Administrators can grant or revoke admin privileges for other users.
      
    - **Ban/Unban Users**: Administrators can ban or unban users, restricting or restoring their access to the application.

<br>

## Technologies Used
- Java 21

- MongoDB

- Thymeleaf

- Spring Boot 3

- Spring Security 6

- Firebase Storage

<br>

## Configuration
MongoDB & Firebase Storage needs to be configured before you can start using CineVerse Application.

### MongoDB
CineVerse uses MongoDB as its database. The `application.yaml` file is already pre-configured. If you don't have it installed you can [download it from here.](https://www.mongodb.com/try/download/community)

### MongoDB Compass
You may need this for editing data on the database directly so I strongly suggest having this installed on your local environment as well. You can [download the application from here.](https://www.mongodb.com/try/download/compass)

### Firebase Storage
CineVerse Application uses Firebase for storing images like profile pictures & project photos.

To configure Firebase Storage:

- Create a new project from [Firebase Console.](https://console.firebase.google.com/)

- Go to Project Settings -> Service Accounts -> Firebase Admin SDK -> Generate new private key.

- Replace `src/main/resources/cloud/firebase.json` with your downloaded JSON file. Keep in mind that it should be named as `firebase.json`.

- Enable Firebase Storage.

- Update the `BUCKET_NAME` constant in `src/main/java/com/sefaunal/cineverse/Utils/ImageUtils.java` to match your Firebase project's bucket name.

```java
private static final String BUCKET_NAME = "resumebuilder-f10a4.appspot.com";
```

<br>

## Accessing Admin Panel
If you would like to access admin panel you'll need to:

- Run the project.

- Access the application via browser from `http://localhost:8080/`.

- Create an account.

- Change user's role to `ADMIN` from MongoDB Compass.

![Screenshot 2025-04-11 at 14 08 35](https://github.com/user-attachments/assets/bdc61cf1-9eee-4037-8113-84811850d24b)

<br>

## Screenshots

### <p align="center">Home Page</p>
![Screenshot 2025-04-11 at 14 14 22](https://github.com/user-attachments/assets/fbee3d4f-72cd-46e2-8638-040ef1920fda)

### <p align="center">Login Page</p>
![Screenshot 2025-04-11 at 14 07 56](https://github.com/user-attachments/assets/af401422-4331-4ae9-ba13-7015654a18b9)

### <p align="center">404 Page</p>
<img width="1840" alt="7" src="https://github.com/user-attachments/assets/2d8e20e0-2b8b-4b9a-936d-27954d46011b" />

### <p align="center">Profile Details Page</p>
![Screenshot 2025-04-11 at 14 14 07](https://github.com/user-attachments/assets/23aa80fd-0631-4362-b45a-e05afbb74e38)

### <p align="center">Content Panel Page</p>
![Screenshot 2025-04-11 at 14 11 49](https://github.com/user-attachments/assets/96bc5be4-9efb-4009-a073-83f0302ecda2)

### <p align="center">Movie Details Page</p>
![Screenshot 2025-04-11 at 14 14 31](https://github.com/user-attachments/assets/aac07198-75ed-45f4-93a4-c225ddc52718)

![Screenshot 2025-04-11 at 14 29 10](https://github.com/user-attachments/assets/cae41427-3612-498f-aae1-90b5750fb6cf)
