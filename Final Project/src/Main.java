import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.String;
import java.util.Random;
import java.lang.Integer;
class Address implements Serializable{
    @Serial
    private static final long serialVersionUID = -4601648070472058835L;
    private int house;
    private int street;
    private String area;
    private String city;
    private String province;

    public Address() {
    }

    public Address(int house, int street, String area, String city, String province) {
        this.house = house;
        this.street = street;
        this.area=area;
        this.city = city;
        this.province = province;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getStreet() {
        return street;
    }

    public void setStreet(int street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String toString() {
        return (" House " + house + ", Street " + street + ", " +area+", "+ city + ", " + province);
    }
}

class Person implements Serializable{
    @Serial
    private static final long serialVersionUID = 5685274554349851198L;
    protected Address add;
    protected String name;
    protected int age;

    public Person() {
        add=new Address();
        name=null;
        age=0;
    }

    public Person(Address add, String name, int age) {
        this.add = add;
        this.name = name;
        this.age = age;
    }

    public Address getAdd() {

        return add;
    }

    public void setAdd(Address add) {
        this.add = add;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return ("Name: " + name + "\nAge: " + age + "\nAddress:" + getAdd().toString());
    }
}

class Client extends Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 374365939867966936L;
    File f=new File("ClientInfo.txt");
    ArrayList<Client> clientList=new ArrayList<Client>();
    private String emailID;
    private String password;
    private String purpose;

    private Passport passport;
    private  IDCard idcard;

    public Client() {

        super();
    }
    public Client(Address add, String name, int age, String email, String pass) {
        super(add, name, age);
        emailID = email;
        password = pass;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
    public void setIdcard(IDCard idcard){
        this.idcard=idcard;
    }

    public Passport getPassport(){
        return passport;
    }

    public IDCard getIdcard(){
        return  idcard;
    }
    public void setPurpose(String s){
        purpose=s;
    }

    public String getPurpose(){
        return purpose;
    }

    public boolean setPassword(String pass) {
        // Set up Password
        boolean checkPass1 = false;
        boolean checkPass2 = false;
        boolean checkPass3 = false;
        int checkPass4 = 0;
        // Checks
        for (int j = 0; j < pass.length(); j++) {
            if (Character.isUpperCase(pass.charAt(j))) {
                checkPass1 = true;
            }
            if (Character.isLowerCase(pass.charAt(j))) {
                checkPass2 = true;
            }
            if (Character.isDigit(pass.charAt(j))) {
                checkPass3 = true;
            }
            checkPass4++;
        }
        if (checkPass4 >= 8) {
            if (checkPass1 && checkPass2 && checkPass3) {
                password = pass;
                return true;
            }else{
                System.out.println("Incorrect Password! \nYour password must have:\n* 8 characters\n* An Uppercase character\n* A lowerCase\n* A digit \n");
                return false;
            }
        }else{
            System.out.println("Password must be at least 8 characters long!");
            return false;
        }
    }

    public boolean setEmailID(String email) {
        // Set up email
        if (SearchEmail(email)) {
            System.out.println("Sorry! This email already exists!");
            return false;
        } else {
            boolean x=false;
            boolean check1 = false;
            boolean check2 = false;
            for (int i = 0; i < email.length(); i++) {
                if (email.contains("@")) {
                    check1 = true;
                }

                if (email.contains(".com")) {
                    check2 = true;
                }
            }

            if (check1 && check2) {
                emailID = email;
                x= true;
            } else{
                System.out.println("Incorrect email!");
                System.exit(0);
                x=false;
            }
            return x;
        }
    }

    public String getEmailID(){
        return emailID;
    }
    public String getPassword(){
        return password;
    }



    public boolean setUpAccount(int house, int street, String area, String city, String province, String name, int age){
        if(setEmailID(emailID) && setPassword(password)){

            super.getAdd().setHouse(house);
            super.getAdd().setStreet(street);
            super.getAdd().setCity(city);
            super.getAdd().setArea(area);
            super.getAdd().setProvince(province);
            super.setName(name);
            super.setAge(age);
            System.out.println("Your account has been set up!");
            return true;


        }else{
            System.out.println("Sorry! Your account could not be set up!");
            return false;
        }
    }

    public boolean login(String email, String pass){
        if(loginAccount(email,pass)){
            System.out.println("You've logged in successfully!");
            return true;
        }else{
            System.out.println("Please enter the correct email and password!");
            return false;
        }
    }

    public void insertClient(Client obj){
        try {
            boolean fileExists = fileExists();
            if (fileExists) {
                ObjectInputStream readClient = new ObjectInputStream(new FileInputStream(f));
                clientList = (ArrayList<Client>) readClient.readObject();
                readClient.close();
            }

            clientList.add(obj);

            ObjectOutputStream writeClient = new ObjectOutputStream(new FileOutputStream(f));
            writeClient.writeObject(clientList);
            writeClient.close();

            System.out.println("------------Data entered Successfully!------------");
        } catch (Exception e) {
            System.out.println("Error in data entry!");
        }
    }

    public boolean fileExists(){
        boolean check=false;
        if(f.isFile()){
            try{
                ObjectInputStream readClient=new ObjectInputStream(new FileInputStream(f));
                clientList=(ArrayList<Client>) readClient.readObject();
                readClient.close();
                check=true;
            }catch(Exception e){
                System.out.println("Error in file!");
            }
        }else{
            check=false;
        }
        return check;
    }

    public boolean Search(String email,String pass){
        boolean check=fileExists();
        boolean recordExist=false;
        if(check){
            try{
                ObjectInputStream readClient=new ObjectInputStream(new FileInputStream(f));
                clientList=(ArrayList<Client>) readClient.readObject();
                readClient.close();
                for(Client obj: clientList){
                    if(obj.emailID.equals(email) && obj.password.equals(pass)){
                        recordExist=true;
                        System.out.println(obj);

                    }
                }
                if(!recordExist){
                    System.out.println("Account does not exist!");
                }
            }catch(Exception e){
                System.out.println("Error in data reading!");
            }
        }
        return recordExist;
    }

    public boolean loginAccount(String email,String pass){
        boolean check=fileExists();
        boolean recordExist=false;
        if(check){
            try{
                ObjectInputStream readClient=new ObjectInputStream(new FileInputStream(f));
                clientList=(ArrayList<Client>) readClient.readObject();
                readClient.close();
                for(Client obj: clientList){
                    if(obj.emailID.equals(email) && obj.password.equals(pass)){
                        recordExist=true;
                        System.out.println("You've logged in successfully!");

                    }
                }
                if(!recordExist){
                    System.out.println("Account does not exist!");
                }
            }catch(Exception e){
                System.out.println("Error in data reading!");
            }
        }
        return recordExist;
    }

    public boolean SearchEmail(String email){
        boolean check=fileExists();
        boolean recordExist=false;
        if(check){
            try{
                ObjectInputStream readClient=new ObjectInputStream(new FileInputStream(f));
                clientList=(ArrayList<Client>) readClient.readObject();
                readClient.close();
                for(Client obj: clientList){
                    if(obj.emailID.equals(email)){
                        recordExist=true;

                    }else{
                        recordExist=false;
                    }
                }
            }catch(Exception e){
                System.out.println("Error in data reading!");
            }

        }
        return recordExist;
    }

    public String toString() {
        return super.toString() + "\nEmail: " + emailID +"\nPurpose: " +purpose;
    }

}

class Employee extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -3419729183416411004L;
    private String position;
    private String id;
    private String wHours;
    private String cnic;
    private String emailID;
    private String password;
    private String branch;
    private ArrayList<Client> clientList = new ArrayList<Client>();
    File vv= new File("EmployeeList.txt");
    ArrayList<Employee> employeeList = new ArrayList<Employee>();

    public Employee() {
        super();
    }


    public Employee(Address add, String name, int age, String position, String id, String wHours, String cnic, String emailID, String password, String branch) {
        super(add, name, age);
        this.position = position;
        this.id = id;
        this.wHours=wHours;
        this.cnic = cnic;
        this.emailID = emailID;
        this.password = password;
        this.branch = branch;
        clientList = new ArrayList<Client>();
        employeeList = new ArrayList<>();
    }

    public void addClient(Client e){
        if(clientList==null){
            System.out.println("Clients are empty");
        }
        clientList.add(e);
    }

    public ArrayList<Client> getClientList(){
        return clientList;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String positon) {
        this.position = positon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getBranch() {
        return branch;
    }

    public String getwHours() {
        return wHours;
    }

    public String toString() {
        return (super.toString() + "\nPosition: " + position + "\nWork ID: " +id+ "\nWork Hours: "+wHours + "\nCNIC: " + cnic + "\nEmail ID: " + emailID + "\nBranch Address: " + branch);
    }

    public void insertEmployee(Employee e) {
        Employee temp = null;
        try {
            boolean fileExists = fileExists();
            if (fileExists) {
                ObjectInputStream readEmployee = new ObjectInputStream(new FileInputStream(vv));
                employeeList = (ArrayList<Employee>) readEmployee.readObject();
                for (Employee obj : employeeList) {
                    if (obj.id.equals(e.id)) {
                        temp=obj;
                    }
                }
                readEmployee.close();
            }
            if(temp!=null){
                employeeList.remove(temp);
            }
            employeeList.add(e);

            ObjectOutputStream writeEmployee = new ObjectOutputStream(new FileOutputStream(vv));
            writeEmployee.writeObject(employeeList);
            writeEmployee.close();

            System.out.println("------------Data entered Successfully!------------");
        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Error in data entry!");
        }
    }

    public void insertEmployeePrevious(Employee obj) {
        try {
            boolean fileExists = fileExists();
            if (fileExists) {
                ObjectInputStream readEmployee = new ObjectInputStream(new FileInputStream(vv));
                employeeList = (ArrayList<Employee>) readEmployee.readObject();
                readEmployee.close();
            }

            employeeList.add(obj);

            ObjectOutputStream writeEmployee = new ObjectOutputStream(new FileOutputStream(vv));
            writeEmployee.writeObject(employeeList);
            writeEmployee.close();

            System.out.println("------------Data entered Successfully!------------");
        } catch (Exception e) {
            System.out.println("Error in data entry!");
        }
    }
    public boolean checkWorkID(String workID) {
        if (workID.contains("NAD") && (workID.length() == 7)) {
            return true;
        } else {
            return false;
        }
    }


    public boolean fileExists() {
        boolean check = false;
        if (vv.isFile()) {
            try {
                ObjectInputStream readEmployee = new ObjectInputStream(new FileInputStream(vv));
                employeeList = (ArrayList<Employee>) readEmployee.readObject();
                readEmployee.close();
                check = true;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error in file!");
            }
        } else {
            check = false;
        }
        return check;
    }


    public boolean Search(String workID, String pass) {
        boolean check = fileExists();
        boolean recordExist = false;
        if (check) {
            try {
                ObjectInputStream readEmployee = new ObjectInputStream(new FileInputStream(vv));
                employeeList = (ArrayList<Employee>) readEmployee.readObject(); // Read the entire list object from the file
                readEmployee.close();
                for (Employee obj : employeeList) {

                    if (obj.id.equals(workID) && obj.password.equals(pass)) {
                        recordExist = true;
                        System.out.println(obj);
                    }
                }

                if (!recordExist) {
                    System.out.println("Account does not exist!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error in data reading!");
            }
        }

        return recordExist;
    }
    public void setPassword(String r){
        password=r;
    }
    public String getPassword(){
        return password;
    }

    public Employee Booking(String t,String br) {
        boolean check = fileExists();
        Employee ep=new Employee();
        if (check) {
            try {
                ObjectInputStream readEmployee = new ObjectInputStream(new FileInputStream(vv));
                employeeList = (ArrayList<Employee>) readEmployee.readObject(); // Read the entire list object from the file
                readEmployee.close();
                for (Employee obj : employeeList) {
                    if (obj.wHours.contains(t) && obj.branch.contains(br) && obj.clientList.size()<=18) {
                        ep=obj;
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error in data reading!");
            }
        }
        return ep;
    }

    public Employee set(Employee e, String wid, String pass){
        boolean check = fileExists();
        boolean recordExist = false;
        if (check) {
            try {
                ObjectInputStream readEmployee = new ObjectInputStream(new FileInputStream(vv));
                employeeList = (ArrayList<Employee>) readEmployee.readObject(); // Read the entire list object from the file
                readEmployee.close();
                for (Employee obj : employeeList) {

                    if (obj.id.equals(wid) && obj.password.equals(pass)) {
                        recordExist = true;
                        return obj;
                    }
                }

                if (!recordExist) {
                    System.out.println("Account does not exist!");
                }
            } catch (Exception exc) {
                exc.printStackTrace();
                System.out.println("Error in data reading!");
            }
        }
        return e;
    }


    public void setPassword1(Employee e, String r){
        boolean check = fileExists();
        //e.password=r;
        boolean recordExist = false;
        if (check) {
            try {
                ObjectInputStream readEmployee = new ObjectInputStream(new FileInputStream(vv));
                employeeList = (ArrayList<Employee>) readEmployee.readObject(); // Read the entire list object from the file
                readEmployee.close();
                for (Employee obj : employeeList) {
                    if (obj.id.equals(e.id) && obj.password.equals(e.password)) {
                        recordExist = true;
                        employeeList.remove(obj);
                        break;
                    }
                }
                e.password=r;
                insertEmployee(e);

                if (!recordExist) {
                    System.out.println("Account does not exist!");
                }
            } catch (Exception exc) {
                exc.printStackTrace();
                System.out.println("Error in data reading!");
            }
        }
    }

    public void updateClientList(Employee e, Client c) {
        boolean check = fileExists();
        boolean recordExist = false;

        if (check) {
            try {
                ObjectInputStream readEmployee = new ObjectInputStream(new FileInputStream(vv));
                employeeList = (ArrayList<Employee>) readEmployee.readObject(); // Read the entire list object from the file
                readEmployee.close();

                for (Employee obj : employeeList) {
                    if ((obj.wHours.equals(e.wHours)) && (obj.branch.equals(e.branch)) && (obj.clientList.size() == e.clientList.size())) {
                        recordExist = true;
                        obj.getClientList().add(c); // Update the clientList of the matching Employee object
                        break;
                    }
                }

                if (recordExist) {
                    // Write the updated employeeList back to the file
                    ObjectOutputStream writeEmployee = new ObjectOutputStream(new FileOutputStream(vv));
                    writeEmployee.writeObject(employeeList);
                    writeEmployee.close();

                    System.out.println("Client added to the Employee's clientList and file updated.");
                } else {
                    System.out.println("Account does not exist!");
                }
            } catch (Exception exc) {
                exc.printStackTrace();
                System.out.println("Error in data reading!");
            }
        }
    }



    public boolean loginAccount(String workID, String pass) {
        boolean check = fileExists();
        boolean recordExist = false;
        if (check) {
            try {
                ObjectInputStream readEmployee = new ObjectInputStream(new FileInputStream(vv));
                employeeList = (ArrayList<Employee>) readEmployee.readObject(); // Read the entire list object from the file
                readEmployee.close();
                for (Employee obj : employeeList) {

                    if (obj.id.equals(workID) && obj.password.equals(pass)) {
                        recordExist = true;
                        System.out.println("You've logged in successfully!");
                    }
                }

                if (!recordExist) {
                    System.out.println("Account does not exist!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error in data reading!");
            }
        }

        return recordExist;
    }

   
}

class Passport extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 2041069139974993887L;
    private int valid;

    private String passNO;
    private String pages;
    private String request;
    ArrayList<Passport> passportList = new ArrayList<Passport>();
    File PassportDetails = new File("Passport-Data-Creation.txt");

    public Passport() {
    }

    public Passport(Address add, String name, int age, int valid,int choiceReq) {
        super(add, name, age);
        this.valid = valid;

        if (choiceReq==1){
            request="Urgent";
        }
        else {
            request="Normal";
        }
        passNO = generatePassportNumber();
        passportList = new ArrayList<Passport>();
    }

    public Passport(Address add, String name, int age, int valid, String passNO) {
        super(add, name, age);
        this.valid = valid;
        this.passNO = passNO;
        passportList = new ArrayList<Passport>();
    }
    public void setRequest(int choice){
        if (choice==1){
            request="Urgent";
        }
        else {
            request="Normal";
        }
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }



    public String getPassNO(){
        return passNO;
    }

    public void setPages(int i){
        if(i==1){
            pages="36";
        }else if(i==2){
            pages="72";
        }
    }
    public String generatePassportNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PK");

        for (int i = 0; i < 7; i++) {
            int randomNumber = random.nextInt(10);
            stringBuilder.append(randomNumber);
        }
        return stringBuilder.toString();
    }

    public void time(int i, Employee m){
        if(i==1){
            long index=(long)(m.getClientList().size());
            LocalTime slot=LocalTime.of(9,0);
            LocalTime s=slot.plusMinutes((index*10));
            System.out.println("Your appointment is booked for "+s+"!");
        }else if(i==2){
            long index=(long)(m.getClientList().size());
            LocalTime slot=LocalTime.of(12,0);
            LocalTime s=slot.plusMinutes((index*10));
            System.out.println("Your appointment is booked for "+s+"!");
        }else if(i==3){
            long index=(long)(m.getClientList().size());
            LocalTime slot=LocalTime.of(15,0);
            LocalTime s=slot.plusMinutes((index*10));
            System.out.println("Your appointment is booked for "+s+"!");
        }else{
            System.out.println("Error!");
        }
    }

    @Override
    public String toString() {
        return super.toString()  + "\nValidity of Passport: " + valid + "\nPassport Number: " + passNO;
    }

    public boolean fileExists(){
        boolean check=false;
        if(PassportDetails.isFile()){
            try{
                ObjectInputStream readPassport=new ObjectInputStream(new FileInputStream(PassportDetails));
                passportList=(ArrayList<Passport>) readPassport.readObject();
                readPassport.close();
                check=true;
            }catch(Exception e){
                System.out.println("Error in file!");
            }
        }
        return check;
    }

    public void passportCreation(Passport p) {
        try {
            boolean fileExists = fileExists();
            if (fileExists) {
                ObjectInputStream readPassport = new ObjectInputStream(new FileInputStream(PassportDetails));
                passportList = (ArrayList<Passport>) readPassport.readObject();
                readPassport.close();
            }

            passportList.add(p);

            ObjectOutputStream writePassport = new ObjectOutputStream(new FileOutputStream(PassportDetails));
            writePassport.writeObject(passportList);
            writePassport.writeBytes("\n");
            writePassport.close();

            System.out.println("------------Data entered Successfully!------------");
        } catch (Exception e) {
            System.out.println("Error in data entry!");
        }
    }


    public boolean passportDetails(String passportNo) {
        boolean recordExist = false;
        try {
            ObjectInputStream readPassport = new ObjectInputStream(new FileInputStream(PassportDetails));
            passportList = (ArrayList<Passport>) readPassport.readObject(); // Read the entire list object from the file
            readPassport.close();
            for (Passport obj : passportList) {

                if (obj.passNO.equals(passportNo)) {
                    recordExist = true;
                    System.out.println(obj);
                    break;
                }
            }

            if (!recordExist) {
                System.out.println("Account does not exist!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in data reading!");
        }
        return recordExist;
    }
}

class IDCard extends Person implements Serializable{

    @Serial
    private static final long serialVersionUID = 6375300309354010530L;
    protected int valid;
    protected String cnic;
    protected String request;
    ArrayList<IDCard> idCardList = new ArrayList<IDCard>();
    File IDCardDetails = new File("ID_CARD_DETAILS.txt");

    public IDCard() {
        super();

    }
    public IDCard(Address add, String name, int age, int valid, String cnic, int choice) {
        super(add, name, age);
        this.valid = valid;
        this.cnic = cnic;
        if (choice==1){
            request="Urgent";
        }
        else {
            request="Normal";
        }
        idCardList = new ArrayList<IDCard>();
    }

    public IDCard(Address add, String name, int age, int valid, int choice) {
        super(add, name, age);
        this.valid = valid;
        cnic = generateCNIC() ;
        if (choice==1){
            request="Urgent";
        }
        else {
            request="Normal";
        }
        idCardList = new ArrayList<IDCard>();
    }

    public void setRequest(int choice){
        if (choice==1){
            request="Urgent";
        }
        else {
            request="Normal";
        }
    }

    public String generateCNIC() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        // Generate the first part (90428)
        sb.append(random.nextInt(100000));

        // Append the hyphen
        sb.append("-");

        // Generate the second part (9012345)
        sb.append(random.nextInt(10000000));

        // Append the hyphen
        sb.append("-");

        // Generate the third part (9)
        sb.append(random.nextInt(10));

        return sb.toString();
    }

    public String toString(){
        return super.toString()+"\nValidity application: "+valid+" years\nCNIC: "+cnic+"\nRequest: "+request;
    }

    public boolean fileExists(){
        boolean check=false;
        if(IDCardDetails.isFile()){
            try{
                ObjectInputStream readIDCard=new ObjectInputStream(new FileInputStream(IDCardDetails));
                idCardList=(ArrayList<IDCard>) readIDCard.readObject();
                readIDCard.close();
                check=true;
            }catch(Exception e){
                System.out.println("Error in file!");
            }
        }
        return check;
    }

    public void idCardCreation(IDCard i) {
        try {
            i.cnic=i.generateCNIC();
            boolean fileExists = fileExists();
            if (fileExists) {
                ObjectInputStream readIDCard = new ObjectInputStream(new FileInputStream(IDCardDetails));
                idCardList = (ArrayList<IDCard>) readIDCard.readObject();
                readIDCard.close();
            }

            idCardList.add(i);

            ObjectOutputStream writeIDCard = new ObjectOutputStream(new FileOutputStream(IDCardDetails));
            writeIDCard.writeObject(idCardList);
            writeIDCard.close();

            System.out.println("------------Data entered Successfully!------------");
        } catch (Exception e) {
            System.out.println("Error in data entry!");
        }
    }


    public boolean IDCardDetails(String nic) {
        boolean recordExist = false;
        try {
            ObjectInputStream readIDCard = new ObjectInputStream(new FileInputStream(IDCardDetails));
            idCardList = (ArrayList<IDCard>) readIDCard.readObject(); // Read the entire list object from the file
            readIDCard.close();
            for (IDCard obj : idCardList) {

                if (obj.cnic.equals(nic)) {
                    recordExist = true;
                    System.out.println(obj);
                    break;
                }
            }

            if (!recordExist) {
                System.out.println("Account does not exist!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in data reading!");
        }
        return recordExist;
    }

    public void time(int i, Employee m){
        if(i==1){
            long index=(long)(m.getClientList().size());
            LocalTime slot=LocalTime.of(9,0);
            LocalTime s=slot.plusMinutes((index*10));
            System.out.println("Your appointment is booked for "+s+"!");
        }else if(i==2){
            long index=(long)(m.getClientList().size());
            LocalTime slot=LocalTime.of(12,0);
            LocalTime s=slot.plusMinutes((index*10));
            System.out.println("Your appointment is booked for "+s+"!");
        }else if(i==3){
            long index=(long)(m.getClientList().size());
            LocalTime slot=LocalTime.of(15,0);
            LocalTime s=slot.plusMinutes((index*10));
            System.out.println("Your appointment is booked for "+s+"!");
        }else{
            System.out.println("Error!");
        }
    }
    public boolean CheckIDCard(String nic) {
        boolean recordExist = false;
        try {
            ObjectInputStream readIDCard = new ObjectInputStream(new FileInputStream(IDCardDetails));
            idCardList = (ArrayList<IDCard>) readIDCard.readObject(); // Read the entire list object from the file
            readIDCard.close();
            for (IDCard obj : idCardList) {

                if (obj.cnic.equals(nic)) {
                    recordExist = true;
                    break;
                }
            }

            if (!recordExist) {
                System.out.println("Account does not exist!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in data reading!");
        }
        return recordExist;
    }
}

class ClientEmployee extends JFrame implements ActionListener{
    private int choice;
    Client v = new Client();
    Employee y=new Employee();
    Scanner sc=new Scanner(System.in);
    public ClientEmployee(){

    }
    JLabel employeeHandle, wid,pass, clientHandle,email,password,name,age,house,street,area,city,province;
    JButton logIn, signUpClient,logInClient,logClient,signClient;
    JPasswordField passField,clientPass;
    JTextField widTextField,emailTextField,nameTextField,ageTextField,houseTextField,streetTextField,areaTextField,cityTextField,provinceTextField;


    public ClientEmployee(int num){
        if(num==2){
            setLayout(null);
            getContentPane().setBackground(Color.white);
            setTitle("NADRA Online Reservation System");
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Nadra.jpg"));
            Image i2 = i1.getImage().getScaledInstance(130, 110, Image.SCALE_DEFAULT);
            ImageIcon i3 = new ImageIcon(i2);
            JLabel label1 = new JLabel(i3);
            label1.setBounds(70, 40, 110, 90);
            add(label1);

            employeeHandle = new JLabel("EMPLOYEE HANDLE");
            employeeHandle.setFont(new Font("Osward", Font.BOLD, 28));
            employeeHandle.setBounds(275, 70, 400, 30);
            add(employeeHandle);

            wid=new JLabel("Work ID: ");
            wid.setFont(new Font("Raleway", Font.BOLD, 28));
            wid.setBounds(120, 250, 150, 40);
            add(wid);

            widTextField=new JTextField();
            widTextField.setBounds(300,255,250,30);
            widTextField.setFont(new Font("Arial",Font.PLAIN,14));
            add(widTextField);

            pass=new JLabel("Password: ");
            pass.setFont(new Font("Raleway", Font.BOLD, 28));
            pass.setBounds(120, 350, 250, 30);
            add(pass);

            passField=new JPasswordField();
            passField.setBounds(300,355,250,30);
            passField.setFont(new Font("Arial",Font.BOLD,14));
            add(passField);

            logIn=new JButton("Log In");
            logIn.setBounds(350,450,100,30);
            logIn.setBackground(Color.black);
            logIn.setForeground(Color.white);
            logIn.addActionListener(this);
            add(logIn);

            setSize(800, 600);
            setVisible(true);
            setLocation(310, 110);
        } else if (num==1) {
            setLayout(null);
            getContentPane().setBackground(Color.white);
            setTitle("NADRA Online Reservation System");
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Nadra.jpg"));
            Image i2 = i1.getImage().getScaledInstance(130, 110, Image.SCALE_DEFAULT);
            ImageIcon i3 = new ImageIcon(i2);
            JLabel label1 = new JLabel(i3);
            label1.setBounds(70, 40, 110, 90);
            add(label1);

            clientHandle = new JLabel("NADRA PAKISTAN");
            clientHandle.setFont(new Font("Osward", Font.BOLD, 38));
            clientHandle.setBounds(250, 70, 400, 40);
            add(clientHandle);

            logInClient=new JButton("Log In ");
            logInClient.setFont(new Font("Raleway", Font.BOLD, 28));
            logInClient.setBounds(250, 280, 300, 40);
            logInClient.setForeground(Color.black);
            logInClient.addActionListener(this);
            add(logInClient);

            signUpClient=new JButton("Sign Up");
            signUpClient.setFont(new Font("Raleway", Font.BOLD, 28));
            signUpClient.setBounds(250, 380, 300, 40);
            signUpClient.setForeground(Color.black);
            signUpClient.addActionListener(this);
            add(signUpClient);

            setSize(800, 600);
            setVisible(true);
            setLocation(310, 110);


        } else if (num==3) {
            setLayout(null);
            getContentPane().setBackground(Color.white);
            setTitle("NADRA Online Reservation System");
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Nadra.jpg"));
            Image i2 = i1.getImage().getScaledInstance(130, 110, Image.SCALE_DEFAULT);
            ImageIcon i3 = new ImageIcon(i2);
            JLabel label1 = new JLabel(i3);
            label1.setBounds(70, 40, 110, 90);
            add(label1);

            clientHandle = new JLabel("NADRA PAKISTAN");
            clientHandle.setFont(new Font("Osward", Font.BOLD, 38));
            clientHandle.setBounds(250, 70, 400, 40);
            add(clientHandle);

            email=new JLabel("Email ID: ");
            email.setFont(new Font("Raleway", Font.BOLD, 28));
            email.setBounds(120, 250, 150, 40);
            add(email);

            emailTextField=new JTextField();
            emailTextField.setBounds(300,255,250,30);
            emailTextField.setFont(new Font("Arial",Font.PLAIN,14));
            add(emailTextField);

            password=new JLabel("Password: ");
            password.setFont(new Font("Raleway", Font.BOLD, 28));
            password.setBounds(120, 350, 250, 30);
            add(password);

            clientPass=new JPasswordField();
            clientPass.setBounds(300,355,250,30);
            clientPass.setFont(new Font("Arial",Font.BOLD,14));
            add(clientPass);

            logClient=new JButton("Log In");
            logClient.setBounds(350,450,100,30);
            logClient.setBackground(Color.black);
            logClient.setForeground(Color.white);
            logClient.addActionListener(this);
            add(logClient);

            setSize(800, 600);
            setVisible(true);
            setLocation(310, 110);
        } else if (num==4) {
            setLayout(null);
            getContentPane().setBackground(Color.white);
            setTitle("NADRA Online Reservation System");
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Nadra.jpg"));
            Image i2 = i1.getImage().getScaledInstance(130, 110, Image.SCALE_DEFAULT);
            ImageIcon i3 = new ImageIcon(i2);
            JLabel label1 = new JLabel(i3);
            label1.setBounds(70, 40, 110, 90);
            add(label1);

            clientHandle = new JLabel("NADRA PAKISTAN");
            clientHandle.setFont(new Font("Osward", Font.BOLD, 38));
            clientHandle.setBounds(250, 70, 400, 40);
            add(clientHandle);

            name=new JLabel("Name: ");
            name.setFont(new Font("Raleway", Font.BOLD, 14));
            name.setBounds(200, 160, 150, 40);
            add(name);

            nameTextField=new JTextField();
            nameTextField.setBounds(300,165,250,30);
            nameTextField.setFont(new Font("Arial",Font.PLAIN,16));
            add(nameTextField);

            age=new JLabel("Age: ");
            age.setFont(new Font("Raleway", Font.BOLD, 16));
            age.setBounds(200, 200, 150, 40);
            add(age);

            ageTextField=new JTextField();
            ageTextField.setBounds(300,205,250,30);
            ageTextField.setFont(new Font("Arial",Font.PLAIN,14));
            add(ageTextField);

            house=new JLabel("House No: ");
            house.setFont(new Font("Raleway", Font.BOLD, 16));
            house.setBounds(200, 240, 150, 40);
            add(house);

            houseTextField=new JTextField();
            houseTextField.setBounds(300,245,250,30);
            houseTextField.setFont(new Font("Arial",Font.PLAIN,14));
            add(houseTextField);

            street=new JLabel("Street No: ");
            street.setFont(new Font("Raleway", Font.BOLD, 16));
            street.setBounds(200, 280, 150, 40);
            add(street);

            streetTextField=new JTextField();
            streetTextField.setBounds(300,285,250,30);
            streetTextField.setFont(new Font("Arial",Font.PLAIN,14));
            add(streetTextField);

            area=new JLabel("Area: ");
            area.setFont(new Font("Raleway", Font.BOLD, 16));
            area.setBounds(200, 320, 150, 40);
            add(area);

            areaTextField=new JTextField();
            areaTextField.setBounds(300,325,250,30);
            areaTextField.setFont(new Font("Arial",Font.PLAIN,14));
            add(areaTextField);

            city=new JLabel("City: ");
            city.setFont(new Font("Raleway", Font.BOLD, 16));
            city.setBounds(200, 360, 150, 40);
            add(city);

            cityTextField=new JTextField();
            cityTextField.setBounds(300,365,250,30);
            cityTextField.setFont(new Font("Arial",Font.PLAIN,14));
            add(cityTextField);

            province=new JLabel("Province: ");
            province.setFont(new Font("Raleway", Font.BOLD, 16));
            province.setBounds(200, 400, 150, 40);
            add(province);

            provinceTextField=new JTextField();
            provinceTextField.setBounds(300,405,250,30);
            provinceTextField.setFont(new Font("Arial",Font.PLAIN,14));
            add(provinceTextField);

            email=new JLabel("Email ID: ");
            email.setFont(new Font("Raleway", Font.BOLD, 16));
            email.setBounds(200, 440, 150, 40);
            add(email);

            emailTextField=new JTextField();
            emailTextField.setBounds(300,445,250,30);
            emailTextField.setFont(new Font("Arial",Font.PLAIN,14));
            add(emailTextField);

            password=new JLabel("Password: ");
            password.setFont(new Font("Raleway", Font.BOLD, 16));
            password.setBounds(200, 480, 250, 30);
            add(password);

            clientPass=new JPasswordField();
            clientPass.setBounds(300,485,250,30);
            clientPass.setFont(new Font("Arial",Font.BOLD,14));
            add(clientPass);

            signClient=new JButton("Sign Up");
            signClient.setBounds(350,530,100,30);
            signClient.setBackground(Color.black);
            signClient.setForeground(Color.white);
            signClient.addActionListener(this);
            add(signClient);

            setSize(800, 600);
            setVisible(true);
            setLocation(310, 110);
        }
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==logIn){
            Employee e1=new Employee();
            char [] password=passField.getPassword();
            String pass=new String (password);
            if(e1.loginAccount(widTextField.getText(),pass)){
                e1.Search(widTextField.getText(),pass);
                setVisible(false);
                e1=e1.set(e1,widTextField.getText(),pass);
                midWindow mw=new midWindow(e1);
            }
            else{
                setVisible(false);
                System.out.println("Incorrect Details. Please try again!");
                Menu m=new Menu();
            }
            //employeeDetails emp=new employeeDetails(e1);
            //e1.loginAccount(wid.getText(),pass.getText());
        }
        else if (ae.getSource()==logInClient) {
            setVisible(false);
            new ClientEmployee(3).setVisible(true);

        } else if (ae.getSource()==signUpClient) {
            setVisible(false);
            new ClientEmployee(4).setVisible(true);
        }else if (ae.getSource()==logClient) {
            Client c1=new Client();
            if(c1.loginAccount(emailTextField.getText(), clientPass.getText())){
                setVisible(false);
                clientGUIstep1 f=new clientGUIstep1(c1,c1.getAdd(),c1.getName(),c1.getAge(),c1.getEmailID(),c1.getPassword(),c1.getPurpose());
            }else{
                System.out.println("Account does not exist!");
            }

        } else if (ae.getSource()==signClient) {
            Client c2=new Client();
            int hNo=Integer.parseInt(houseTextField.getText());
            int sNo=Integer.parseInt(streetTextField.getText());
            int ageNo=Integer.parseInt(ageTextField.getText());
            c2.setEmailID(emailTextField.getText());
            c2.setPassword(clientPass.getText());
            if(c2.setUpAccount(hNo,sNo,areaTextField.getText(),cityTextField.getText(),provinceTextField.getText(),nameTextField.getText(),ageNo)){
                setVisible(false);
                c2.insertClient(c2);
                clientGUIstep1 f=new clientGUIstep1(c2,c2.getAdd(),c2.getName(),c2.getAge(),c2.getEmailID(),c2.getPassword(),c2.getPurpose());
            }else{
                System.out.println("Incorrect details!");
            }
        }
    }

}

class Menu extends JFrame implements ActionListener {

    JButton client;
    JButton employee;

    Scanner sc = new Scanner(System.in);
    Menu() {
        setLayout(null);
        getContentPane().setBackground(Color.white);
        setTitle("NADRA Online Reservation System");
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Nadra.jpg"));
        Image i2 = i1.getImage().getScaledInstance(130, 110, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label1 = new JLabel(i3);
        label1.setBounds(70, 40, 110, 90);
        add(label1);

        JLabel nadra = new JLabel("NADRA PAKISTAN");
        nadra.setFont(new Font("Osward", Font.BOLD, 38));
        nadra.setBounds(250, 70, 400, 40);
        add(nadra);


        //JLabel option=new JLabel("Select ");
        //option.setFont(new Font("Osward",Font.BOLD,30));
        //option.setBounds(300,180,300,40);
        //add(option);

        client = new JButton("Client");
        client.setFont(new Font("Osward", Font.BOLD, 25));
        client.setBounds(250, 280, 300, 40);
        client.addActionListener(this);
        add(client);

        employee = new JButton("NADRA Employee");
        employee.setFont(new Font("Osward", Font.BOLD, 25));
        employee.setBounds(250, 380, 300, 40);
        employee.addActionListener(this);
        add(employee);

        setSize(800, 600);
        setVisible(true);
        setLocation(310, 110);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==client) {
            setVisible(false);
            new ClientEmployee(1).setVisible(true);
        }
        else {
            setVisible(false);
            new ClientEmployee(2).setVisible(true);
        }

    }

    int mainMenu(){
        int ch;
        do {
            System.out.println("*-*-*-*-*-*- NADRA PAKISTAN -*-*-*-*-*-*\n\n\n");
            System.out.println("Select the option that applies to you:\n(1) Client\n(2) Nadra Employee\n[Note: Press 0 to exit the program]");
            ch = sc.nextInt();
            if (ch == 0) {
                System.exit(0);
            }
        } while (ch != 1 && ch != 2);
        return ch;
    }
}

class clientGUIstep1 extends JFrame implements ActionListener {
    JLabel clientHandle,cn,valid,br,timeSlot;
    JTextField cnTextField;
    JButton submit, logOut, clientDetails;
    JRadioButton duration1, duration2, time1,time2,time3, valid1,valid2;
    ButtonGroup duration,slots,group;
    JComboBox<String> purp,branch;

    Client c=null;
    Address add=null;
    String name=null;
    int age=0;
    String email=null;
    String pass=null;
    String purpose=null;


    public clientGUIstep1(Client c,Address add,String name,int age, String email,String pass,String purpose) {
        this.c=c;
        this.add=add;
        this.name=name;
        this.age=age;
        this.email=email;
        this.pass=pass;
        this.purpose=purpose;

        setLayout(null);
        getContentPane().setBackground(Color.white);
        setTitle("NADRA Online Reservation System");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Nadra.jpg"));
        Image i2 = i1.getImage().getScaledInstance(130, 110, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label1 = new JLabel(i3);
        label1.setBounds(70, 40, 110, 90);
        add(label1);

        clientHandle = new JLabel("NADRA PAKISTAN");
        JLabel note=new JLabel("(Fill all fields that are applicable)");
        note.setBounds(250, 110, 400, 20);
        note.setForeground(Color.gray);
        add(note);
        clientHandle.setFont(new Font("Osward", Font.BOLD, 38));
        clientHandle.setBounds(250, 70, 400, 40);
        add(clientHandle);

        JLabel select = new JLabel("Purpose: ");
        select.setFont(new Font("Raleway", Font.BOLD, 16));
        select.setBounds(170, 170, 80, 30);
        add(select);

        String[] p = {"ID Card Renewal", "ID Card Application", "Passport Renewal", "Passport Application"};
        purp = new JComboBox<>(p);
        purp.setBounds(250, 170, 300, 30);
        add(purp);

        cn = new JLabel("CNIC/Passport No. : ");
        cn.setFont(new Font("Raleway", Font.BOLD, 16));
        cn.setBounds(170, 230, 300, 40);
        add(cn);

        cnTextField = new JTextField();
        cnTextField.setBounds(330, 235, 250, 30);
        cnTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(cnTextField);

        JLabel choose = new JLabel("Duration: ");
        choose.setFont(new Font("Raleway", Font.BOLD, 16));
        choose.setBounds(170, 290, 300, 40);
        add(choose);

        duration1 = new JRadioButton("Urgent");
        duration1.setBounds(250, 298, 100, 30);
        duration1.setBackground(Color.white);
        duration1.setActionCommand("Urgent");
        duration2 = new JRadioButton("Regular");
        duration2.setBounds(400, 298, 100, 30);
        duration2.setBackground(Color.white);
        duration2.setActionCommand("Regular");
        duration = new ButtonGroup();
        duration.add(duration1);
        duration.add(duration2);
        add(duration1);
        add(duration2);

        valid = new JLabel("Validity: ");
        valid.setFont(new Font("Raleway", Font.BOLD, 16));
        valid.setBounds(170, 350, 300, 40);
        add(valid);

        valid1 = new JRadioButton("5 years");
        valid1.setBounds(290, 358, 100, 30);
        valid1.setBackground(Color.white);
        valid1.setActionCommand("5 years");
        valid2 = new JRadioButton("10 years");
        valid2.setBounds(440, 358, 100, 30);
        valid2.setBackground(Color.white);
        valid2.setActionCommand("10 years");
        group = new ButtonGroup();
        group.add(valid1);
        group.add(valid2);
        add(valid1);
        add(valid2);

        br = new JLabel("Branch: ");
        br.setFont(new Font("Raleway", Font.BOLD, 16));
        br.setBounds(170, 410, 300, 40);
        add(br);

        String[] b = {"Blue Area", "Murree Rd", "Canteen Store Department"};
        branch = new JComboBox<>(b);
        branch.setBounds(250, 418, 300, 30);
        branch.addActionListener(this);
        add(branch);

        timeSlot = new JLabel("Choose Time: ");
        timeSlot.setFont(new Font("Raleway", Font.BOLD, 16));
        timeSlot.setBounds(170, 470, 300, 40);
        add(timeSlot);

        time1 = new JRadioButton("09:00-12:00");
        time1.setBounds(290, 478, 100, 30);
        time1.setBackground(Color.white);
        time1.setActionCommand("09:00-12:00");
        time2 = new JRadioButton("12:00-15:00");
        time2.setBounds(440, 478, 100, 30);
        time2.setBackground(Color.white);
        time2.setActionCommand("12:00-15:00");
        time3 = new JRadioButton("15:00-18:00");
        time3.setBounds(590, 478, 100, 30);
        time3.setBackground(Color.white);
        time3.setActionCommand("15:00-18:00");
        slots = new ButtonGroup();
        slots.add(time1);
        slots.add(time2);
        slots.add(time3);
        add(time1);
        add(time2);
        add(time3);

        submit = new JButton("Submit");
        submit.setBounds(250, 530, 100, 30);
        submit.setBackground(Color.black);
        submit.setForeground(Color.white);
        submit.addActionListener(this);
        add(submit);

        logOut = new JButton("Log Out");
        logOut.setBounds(450, 530, 100, 30);
        logOut.setBackground(Color.black);
        logOut.setForeground(Color.white);
        logOut.addActionListener(this);
        add(logOut);

        setSize(800, 600);
        setVisible(true);
        setLocation(310, 110);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String checkBranch=(String)branch.getSelectedItem();
            String br;
            if(checkBranch.equals("Blue Area")){
                br="109 Jinnah Ave, Block I G 7/2 Blue Area, Islamabad, Islamabad Capital Territory 44000";
            }
            else if(checkBranch.equals("Murree Rd")){
                br="Murree Rd, near Rehmanabad Metro Bus Station, A Block Block A Satellite Town, Rawalpindi, Punjab";
            }
            else{
                br="Canteen Store Department, Rawalpindi, Punjab 46000";
            }
            if (purp.getSelectedItem().equals("ID Card Renewal")) {
                setVisible(false);
                IDCard id=new IDCard();

                Employee y=new Employee();
                int t=0;
                if(id.CheckIDCard(cnTextField.getText())){
                    Employee emp=y.Booking(slots.getSelection().getActionCommand(),br);
                    if(emp==null){
                        System.out.println("Sorry!No appointments are available for tomorrow!");
                        System.exit(0);
                    }
                    c.setPurpose("ID Card Renewal");
                    emp.updateClientList(emp,c);

                    if (slots.getSelection().getActionCommand().contains("09:00-12:00")){
                        t=1;
                    }else if (slots.getSelection().getActionCommand().contains("12:00-15:00")){
                        t=2;
                    }else if (slots.getSelection().getActionCommand().contains("15:00-18:00")){
                        t=3;
                    }
                    id.time(t,emp);
                }
            }
            else if (purp.getSelectedItem().equals("ID Card Application")) {

                setVisible(false);
                int dur=0;
                int val=0;
                IDCard card=null;
                c.setPurpose("ID Card Application");
                //create id card and add to client
                if (group.getSelection().getActionCommand().contains("5 years")){
                    val=5;
                    if(duration.getSelection().getActionCommand().contains("Urgent")){
                        dur=1;
                        card=new IDCard(add,name,age,val,dur);
                    } else if (duration.getSelection().getActionCommand().contains("Regular")) {
                        dur=2;
                        card=new IDCard(add,name,age,val,dur);
                    }
                }else if (group.getSelection().getActionCommand().contains("10 years")){
                    val=10;
                    if(duration.getSelection().getActionCommand().contains("Urgent")){
                        dur=1;
                        card=new IDCard(add,name,age,val,dur);
                    } else if (duration.getSelection().getActionCommand().contains("Regular")) {
                        dur=2;
                        card=new IDCard(add,name,age,val,dur);
                    }
                }
                card.idCardCreation(card);
                c.setIdcard(card);

                //book time
                Employee y=new Employee();
                int t=0;
                Employee emp=y.Booking(slots.getSelection().getActionCommand(),br);
                if(emp==null){
                    System.out.println("Sorry!No appointments are available for tomorrow!");
                    System.exit(0);
                }
                emp.updateClientList(emp,c);

                if (slots.getSelection().getActionCommand().contains("09:00-12:00")){
                    t=1;
                }else if (slots.getSelection().getActionCommand().contains("12:00-15:00")){
                    t=2;
                }else if (slots.getSelection().getActionCommand().contains("15:00-18:00")){
                    t=3;
                }
                card.time(t,emp);

            }
            else if (purp.getSelectedItem().equals("Passport Renewal")) {
                setVisible(false);
                Passport p=new Passport();
                Employee y=new Employee();
                c.setPurpose("Passport Renewal");
                int t=0;
                if(p.passportDetails(cnTextField.getText())){
                    Employee emp=y.Booking(slots.getSelection().getActionCommand(),br);
                    if(emp==null){
                        System.out.println("Sorry!No appointments are available for tomorrow!");
                        System.exit(0);
                    }
                    emp.updateClientList(emp,c);
                    if (slots.getSelection().getActionCommand().contains("09:00-12:00")){
                        t=1;
                    }else if (slots.getSelection().getActionCommand().contains("12:00-15:00")){
                        t=2;
                    }else if (slots.getSelection().getActionCommand().contains("15:00-18:00")){
                        t=3;
                    }
                    p.time(t,emp);
                }

            }
            else if (purp.getSelectedItem().equals("Passport Application")) {
                setVisible(false);
                Passport pp=new Passport();
                int dur=0;
                int val=0;
                c.setPurpose("Passport Application");
                //create passport card and add to client
                if (group.getSelection().getActionCommand().contains("5 years")){
                    val=5;
                    if(duration.getSelection().getActionCommand().contains("Urgent")){
                        dur=1;
                        pp=new Passport(add,name,age,val,dur);
                    } else if (duration.getSelection().getActionCommand().contains("Regular")) {
                        dur=2;
                        pp=new Passport(add,name,age,val,dur);
                    }
                }else if (group.getSelection().getActionCommand().contains("10 years")){
                    val=10;
                    if(duration.getSelection().getActionCommand().contains("Urgent")){
                        dur=1;
                        pp=new Passport(add,name,age,val,dur);
                    } else if (duration.getSelection().getActionCommand().contains("Regular")) {
                        dur=2;
                        pp=new Passport(add,name,age,val,dur);
                    }
                }
                pp.passportCreation(pp);
                c.setPassport(pp);

                //book time
                Employee y=new Employee();
                int t=0;
                Employee emp=y.Booking(slots.getSelection().getActionCommand(),br);
                if(emp==null){
                    System.out.println("Sorry!No appointments are available for tomorrow!");
                    System.exit(0);
                }
                emp.updateClientList(emp,c);

                if (slots.getSelection().getActionCommand().contains("09:00-12:00")){
                    t=1;
                }else if (slots.getSelection().getActionCommand().contains("12:00-15:00")){
                    t=2;
                }else if (slots.getSelection().getActionCommand().contains("15:00-18:00")){
                    t=3;
                }
                pp.time(t,emp);
            }

            else {

                setVisible(false);
                Menu m = new Menu();
            }
        } else if (e.getSource()==logOut) {
            System.exit(0);
        }
    }
}



class employeeDetails extends JFrame implements ActionListener {
    JLabel name, name1, age, age1, address, address1, position, position1, wid, wid1, wHours, wHours1, cnic, cnic1, email, email1, branch, branch1;
    JButton logOut, main;

    public employeeDetails(Employee emp) {
        setLayout(null);
        getContentPane().setBackground(Color.white);
        setTitle("NADRA Online Reservation System");
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Nadra.jpg"));
        Image i2 = i1.getImage().getScaledInstance(130, 110, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label1 = new JLabel(i3);
        label1.setBounds(70, 10, 110, 90);
        add(label1);

        JLabel nadra = new JLabel("EMPLOYEE HANDLE");
        nadra.setFont(new Font("Osward", Font.BOLD, 38));
        nadra.setBounds(250, 40, 400, 40);
        add(nadra);


        name = new JLabel("Name: ");
        name.setFont(new Font("Raleway", Font.BOLD, 20));
        name.setBounds(70, 110, 100, 30);
        add(name);


        name1 = new JLabel(emp.getName());
        name1.setFont(new Font("Raleway", Font.PLAIN, 20));
        name1.setBounds(190, 110, 300, 30);
        add(name1);


        age = new JLabel("Age: ");
        age.setFont(new Font("Osward", Font.BOLD, 20));
        age.setBounds(70, 160, 200, 30);
        add(age);

        String x = String.valueOf(emp.getAge());
        age1 = new JLabel(x);
        age1.setFont(new Font("Osward", Font.PLAIN, 20));
        age1.setBounds(190, 160, 200, 30);
        add(age1);

        address = new JLabel("Address: ");
        address.setFont(new Font("Osward", Font.BOLD, 20));
        address.setBounds(70, 200, 200, 30);
        add(address);

        address1 = new JLabel(emp.getAdd().toString());
        address1.setFont(new Font("Osward", Font.PLAIN, 20));
        address1.setBounds(190, 200, 900, 30);
        add(address1);

        position = new JLabel("Position: ");
        position.setFont(new Font("Osward", Font.BOLD, 20));
        position.setBounds(70, 250, 200, 30);
        add(position);

        position1 = new JLabel(emp.getPosition());
        position1.setFont(new Font("Osward", Font.PLAIN, 20));
        position1.setBounds(190, 250, 200, 30);
        add(position1);

        wid = new JLabel("Work ID: ");
        wid.setFont(new Font("Osward", Font.BOLD, 20));
        wid.setBounds(70, 290, 200, 30);
        add(wid);

        wid1 = new JLabel(emp.getId());
        wid1.setFont(new Font("Osward", Font.PLAIN, 20));
        wid1.setBounds(190, 290, 200, 30);
        add(wid1);

        wHours = new JLabel("Work Hours: ");
        wHours.setFont(new Font("Osward", Font.BOLD, 20));
        wHours.setBounds(70, 340, 200, 30);
        add(wHours);

        wHours1 = new JLabel(emp.getwHours());
        wHours1.setFont(new Font("Osward", Font.PLAIN, 20));
        wHours1.setBounds(190, 340, 200, 30);
        add(wHours1);

        cnic = new JLabel("CNIC: ");
        cnic.setFont(new Font("Osward", Font.BOLD, 20));
        cnic.setBounds(70, 390, 200, 30);
        add(cnic);

        cnic1 = new JLabel(emp.getCnic());
        cnic1.setFont(new Font("Osward", Font.PLAIN, 20));
        cnic1.setBounds(190, 390, 200, 30);
        add(cnic1);

        email = new JLabel("Email ID: ");
        email.setFont(new Font("Osward", Font.BOLD, 20));
        email.setBounds(70, 440, 200, 30);
        add(email);

        email1 = new JLabel(emp.getEmailID());
        email1.setFont(new Font("Osward", Font.PLAIN, 20));
        email1.setBounds(190, 440, 500, 30);
        add(email1);

        branch = new JLabel("Branch: ");
        branch.setFont(new Font("Osward", Font.BOLD, 20));
        branch.setBounds(70, 490, 200, 30);
        add(branch);

        branch1 = new JLabel(emp.getBranch());
        branch1.setFont(new Font("Osward", Font.PLAIN, 20));
        branch1.setBounds(190, 490, 1000, 30);
        add(branch1);

        logOut = new JButton("Log Out");
        logOut.setBounds(410, 570, 100, 30);
        logOut.setBackground(Color.black);
        logOut.setForeground(Color.white);
        logOut.addActionListener(this);
        add(logOut);

        main = new JButton("Main");
        main.setBounds(290, 570, 100, 30);
        main.setBackground(Color.gray);
        main.setForeground(Color.white);
        main.addActionListener(this);
        add(main);

        setSize(800, 700);
        setVisible(true);
        setLocation(310, 10);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == main) {
            setVisible(false);
            Menu m = new Menu();
        } else {
            setVisible(false);
            new ClientEmployee(2).setVisible(true);
        }

    }
}


class midWindow extends JFrame implements ActionListener{
    JButton changePass, employeeDetails, clientDetails;
    Employee temp;

    public midWindow(Employee e) {
        temp=e;
        setLayout(null);
        getContentPane().setBackground(Color.white);
        setTitle("NADRA Online Reservation System");
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Nadra.jpg"));
        Image i2 = i1.getImage().getScaledInstance(130, 110, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label1 = new JLabel(i3);
        label1.setBounds(70, 10, 110, 90);
        add(label1);

        JLabel nadra = new JLabel("EMPLOYEE HANDLE");
        nadra.setFont(new Font("Osward", Font.BOLD, 38));
        nadra.setBounds(250, 40, 400, 40);
        add(nadra);

        employeeDetails = new JButton("Employee Details");
        employeeDetails.setFont(new Font("Osward", Font.BOLD, 25));
        employeeDetails.setBounds(250, 240, 300, 40);
        employeeDetails.addActionListener(this);
        add(employeeDetails);

        clientDetails = new JButton("Client Details");
        clientDetails.setFont(new Font("Osward", Font.BOLD, 25));
        clientDetails.setBounds(250, 340, 300, 40);
        clientDetails.addActionListener(this);
        add(clientDetails);

        changePass = new JButton("Change Password");
        changePass.setFont(new Font("Osward", Font.BOLD, 25));
        changePass.setBounds(250, 440, 300, 40);
        changePass.addActionListener(this);
        add(changePass);


        setSize(800, 600);
        setVisible(true);
        setLocation(310, 110);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==employeeDetails){
            setVisible(false);
            employeeDetails e=new employeeDetails(temp);
        }
        else if(ae.getSource()==changePass){
            setVisible(false);
            changePassword cp=new changePassword(temp);
        }
        else{
            setVisible(false);
            ArrayList<Client> list=temp.getClientList();
            for (Client obj : list) {
                if(!obj.name.equals(null)){
                    System.out.println("\n\n"+obj.toString()+"\n");
                }
                else{
                    System.out.println("--------------------------------------------");
                }

            }
        }

    }
}

class changePassword extends JFrame implements ActionListener{
    JLabel oldPass,newPass,employeeHandle;
    JTextField oldPassText;
    JPasswordField newPassText;
    JButton submit;
    Employee temp;

    public changePassword(Employee x){
        temp=x;
        setLayout(null);
        getContentPane().setBackground(Color.white);
        setTitle("NADRA Online Reservation System");
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Nadra.jpg"));
        Image i2 = i1.getImage().getScaledInstance(130, 110, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label1 = new JLabel(i3);
        label1.setBounds(70, 40, 110, 90);
        add(label1);

        employeeHandle = new JLabel("EMPLOYEE HANDLE");
        employeeHandle.setFont(new Font("Osward", Font.BOLD, 28));
        employeeHandle.setBounds(275, 70, 400, 30);
        add(employeeHandle);

        oldPass=new JLabel("Old Password: ");
        oldPass.setFont(new Font("Raleway", Font.BOLD, 28));
        oldPass.setBounds(20, 250, 400, 40);
        add(oldPass);

        oldPassText=new JTextField();
        oldPassText.setBounds(300,255,250,30);
        oldPassText.setFont(new Font("Arial",Font.PLAIN,14));
        add(oldPassText);

        newPass=new JLabel("New Password: ");
        newPass.setFont(new Font("Raleway", Font.BOLD, 28));
        newPass.setBounds(20, 350, 400, 30);
        add(newPass);

        newPassText=new JPasswordField();
        newPassText.setBounds(300,355,250,30);
        newPassText.setFont(new Font("Arial",Font.BOLD,14));
        add(newPassText);

        submit=new JButton("Submit");
        submit.setBounds(350,450,100,30);
        submit.setBackground(Color.black);
        submit.setForeground(Color.white);
        submit.addActionListener(this);
        add(submit);

        setSize(800, 600);
        setVisible(true);
        setLocation(310, 110);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==submit){
            Employee e1=new Employee();
            char [] password=newPassText.getPassword();
            String pass=new String (password);
            temp.setPassword1(temp,pass);
            setVisible(false);
            Menu m=new Menu();
            //employeeDetails emp=new employeeDetails(e1);
            //e1.loginAccount(wid.getText(),pass.getText());
        }
    }
}


public class Main {
    public static void main(String[] args) {


        Menu m=new Menu();



    }

}