package edu.esprit.entities;

<<<<<<< HEAD
import java.util.Objects;

public class User {

    private int id;
    private String roles;
    private String nom,prenom;
    private String image_name;

    public User() {
    }

    public User(int id, String roles,String nom,String prenom, String image_name) {
        this.id = id;
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
        this.image_name = image_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

=======
import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private Integer id;
    private String email;
    private String roles;
    private String password;
    private String nom;
    private String prenom;
    private LocalDateTime createdAt;
    private boolean isVerified;
    private String googleId;
    private String phoneNumber;
    private String pays;
    private String descriptionUser;
    private String imageName;

    public User(){

    }
    public User(String email,String nom,String prenom,String password,String phoneNumber,String roles,boolean isVerified,LocalDateTime createdAt)
    {
        this.email=email;
        this.nom=nom;
        this.prenom=prenom;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.roles=roles;
        this.isVerified=isVerified;
        this.createdAt=createdAt;
    }
    public User(int id,String email,String nom,String prenom,String password,String phoneNumber,String roles,boolean isVerified,LocalDateTime createdAt,String pays,String descriptionUser,String imageName)
    {
        this.id=id;
        this.pays=pays;
        this.descriptionUser=descriptionUser;
        this.email=email;
        this.nom=nom;
        this.prenom=prenom;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.roles=roles;
        this.isVerified=isVerified;
        this.createdAt=createdAt;
        this.imageName=imageName;
    }
    public User(String email,String nom,String prenom,String password,String phoneNumber,String roles,boolean isVerified,LocalDateTime createdAt,String pays,String descriptionUser,String imageName)
    {
        this.pays=pays;
        this.descriptionUser=descriptionUser;
        this.email=email;
        this.nom=nom;
        this.prenom=prenom;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.roles=roles;
        this.isVerified=isVerified;
        this.createdAt=createdAt;
        this.imageName=imageName;
    }
    public User(String email,String nom,String prenom,String phoneNumber,String roles,boolean isVerified,LocalDateTime createdAt,String pays,String descriptionUser,String imageName)
    {
        this.pays=pays;
        this.descriptionUser=descriptionUser;
        this.email=email;
        this.nom=nom;
        this.prenom=prenom;
        this.phoneNumber=phoneNumber;
        this.roles=roles;
        this.isVerified=isVerified;
        this.createdAt=createdAt;
        this.imageName=imageName;
    }
    public User(String email,String nom,String prenom,String roles,boolean isVerified,LocalDateTime createdAt,String googleId)
    {
        this.googleId =googleId;
        this.email=email;
        this.nom=nom;
        this.prenom=prenom;
        this.roles=roles;
        this.isVerified=isVerified;
        this.createdAt=createdAt;
    }

    public User(String email, String roles, boolean isVerified, LocalDateTime createdAt, String googleId) {
        this.email = email;
        this.roles = roles;
        this.isVerified = isVerified();
        this.createdAt = createdAt;
        this.googleId = googleId;
    }


    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", createdAt=" + createdAt +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", pays='" + pays + '\'' +
                ", descriptionUser='" + descriptionUser + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return isVerified == user.isVerified && Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(roles, user.roles) && Objects.equals(password, user.password) && Objects.equals(nom, user.nom) && Objects.equals(prenom, user.prenom) && Objects.equals(createdAt, user.createdAt) && Objects.equals(googleId, user.googleId) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(pays, user.pays) && Objects.equals(descriptionUser, user.descriptionUser) && Objects.equals(imageName, user.imageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, roles, password, nom, prenom, createdAt, isVerified, googleId, phoneNumber, pays, descriptionUser, imageName);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

>>>>>>> 98c6bd46f80b2b737013558c61c66b4bec3d581b
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

<<<<<<< HEAD
=======
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

>>>>>>> 98c6bd46f80b2b737013558c61c66b4bec3d581b
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

<<<<<<< HEAD
    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", roles='" + roles + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roles);
    }
}
=======
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getDescriptionUser() {
        return descriptionUser;
    }

    public void setDescriptionUser(String descriptionUser) {
        this.descriptionUser = descriptionUser;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath()
    {
        return "C:\\Users\\User\\IdeaProjects\\ProjectHOpe\\src\\main\\resources\\assets\\"+getImageName();
    }
}

>>>>>>> 98c6bd46f80b2b737013558c61c66b4bec3d581b
