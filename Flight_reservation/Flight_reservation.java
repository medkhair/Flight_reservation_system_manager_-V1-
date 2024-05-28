import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.HOURS;
class Flight{
	String LineAgency, Serie, Aircraft;
	double distance;
	int Stops;
	int MaxSeats;
	int AvailableSeats;
	int[] seats = new int[MaxSeats];
	boolean[] availability = new boolean[this.MaxSeats];
	LocalDateTime DepartingTime;
	String DepartingLocation; 
	LocalDateTime ArrivingTime;
	String ArrivingLocation; 
	long Duration;
	public Flight(String la, String s, String a, double d, int st, int ms, LocalDateTime dt, String dl, LocalDateTime at, String al){
		this.LineAgency = la;
		this.Serie = s;
		this.Aircraft = a;
		this.distance = d;
		this.Stops = st;
		this.MaxSeats = ms;
		this.DepartingTime = dt;
		this.DepartingLocation = dl;
		this.ArrivingTime = at;
		this.ArrivingLocation = al;
		this.Duration = HOURS.between(dt, at);
		this.seats = new int[this.MaxSeats];
        this.availability = new boolean[this.MaxSeats];
        this.AvailableSeats = this.MaxSeats;
        setAvailability();
	}
	void setAvailability(){
		for (int i=0;i<this.MaxSeats;i++) {
			this.availability[i] = true;
		}
	}
	void getSeats(){
		String a;
		for (int i=0;i<this.MaxSeats;i++){
			if (this.availability[i] == true) {
				a="Available";
			}else{
				a="Reserved";
			}
			System.out.println((i+1)+"- "+a);
		}
	}
	void get(){
		System.out.println("\t Line Agency -> "+this.LineAgency);
		System.out.println("\t Serie -> "+this.Serie);
		System.out.println("\t Aircraft -> "+this.Aircraft);
		System.out.println("\t Duration -> "+this.Duration+" Hours");
		System.out.println("\t Departing at  -> "+this.DepartingTime);
		System.out.println("\t Arriving at -> "+this.ArrivingTime);
		System.out.println("\t Departing in -> "+this.DepartingLocation);
		System.out.println("\t Arriving in -> "+this.ArrivingLocation);
		System.out.println("\t Dinstance -> "+this.distance+" km");
		System.out.println("\t Stops -> "+this.Stops);
	}
	void locationTime(){
		System.out.println(this.DepartingTime+" : "+this.DepartingLocation+" -> "+ this.ArrivingLocation+" : "+this.ArrivingTime);
	}
}
class Passanger{
	String Name, Phone, Adress, Email;
	public Passanger(String n, String p, String a, String e){
		this.Name = n;
		this.Phone = p;
		this.Adress = a;
		this.Email = e;
	}
}
class Reservation{
	Scanner sc = new Scanner(System.in);
	Passanger ReservationPassanger;
	String ReservationCode, TravelConsultant;
	int Seat;
	Flight ReservationFlight;
	boolean Status;
	LocalDateTime ReservationTime = LocalDateTime.now();
	boolean b;
	int a = 0;
	public Reservation(Passanger rp, String rc, String tc, int s, Flight f, boolean st){
		this.ReservationPassanger = rp;
		this.ReservationCode = rc;
		this.TravelConsultant = tc;
		this.Seat = s;
		this.ReservationFlight = f;
		this.Status = st;
	}
	void get(){
		System.out.println("\t For -> "+this.ReservationPassanger.Name);
		System.out.println("\t ReservationCode -> "+this.ReservationCode);
		System.out.println("\t TravelConsultant -> "+this.TravelConsultant);
		System.out.println("\t Seat -> "+this.Seat);
		System.out.println("\t Status -> "+this.Status);
		this.ReservationFlight.get();
		System.out.println("\t ReservationTime -> "+this.ReservationTime);
	}
	void setReservation(ArrayList<String> cities, ArrayList<Flight> flights){
		ArrayList<String> cities2 = new ArrayList<String>();
		ArrayList<Flight> avFlights = new ArrayList<Flight>();
		int f,s;
		this.b = true;
		System.out.print("\t Enter your Name : ");
		this.ReservationPassanger.Name = sc.nextLine();
		System.out.print("\t Enter your Phone : ");
		this.ReservationPassanger.Phone = sc.nextLine();
		System.out.print("\t Enter your Adress : ");
		this.ReservationPassanger.Adress = sc.nextLine();
		System.out.print("\t Enter your Email : ");
		this.ReservationPassanger.Email = sc.nextLine();
		for (int i=0;i<cities.size();i++) {
			System.out.println((i+1)+"- "+cities.get(i));
		}
		System.out.print("\t Enter the Departing city (choose the Number) : ");
		int dc = sc.nextInt();
		for (int i=0;i<cities.size();i++) {
			if (i == dc-1 ) {
				continue;
			}
			cities2.add(cities.get(i));
		}
		for (int i=0;i<cities2.size();i++){
			System.out.println((i+1)+"- "+cities2.get(i));
		}
		System.out.print("\t Enter the Arriving city (choose the Number) : ");
		int ac = sc.nextInt();
		System.out.print("\t Enter the Day : ");
		int d = sc.nextInt();
		System.out.print("\t Enter the Month : ");
		int m = sc.nextInt();
		System.out.print("\t Enter the Year : ");
		int y = sc.nextInt();
		String s1 = cities.get(dc-1);
		String s2 = cities2.get(ac-1);
		LocalDateTime check = LocalDateTime.of(y,m,d,00,00);
		LocalDateTime today = LocalDateTime.now();
		if(today.isAfter(check) == true){
			System.out.println("\t out dated");
			f=0;
		}else{
			int cc=1;
			for (int i=0;i<flights.size();i++) {
				if ((check.isBefore(flights.get(i).DepartingTime)) && ((s1.equals(flights.get(i).DepartingLocation)) && (s2.equals(flights.get(i).ArrivingLocation)))){
					System.out.print((cc)+"- ");
					flights.get(i).locationTime();
					avFlights.add(flights.get(i));
					cc++;
				}
			}
			System.out.print("\t choose your Flight : ");
			f = sc.nextInt();
			avFlights.get(f-1).getSeats();
			System.out.print("\t choose your seat : ");
			s = sc.nextInt();
			if (avFlights.get(f-1).availability[s-1] == false) {
				System.out.println("\t Not Available !");
				this.Status = false;
			}else{
				avFlights.get(f-1).availability[s-1] = false;
				System.out.println("\t Validated !");
				avFlights.get(f-1).AvailableSeats--;
				this.Status = true;
			}
			this.ReservationCode = "0756319";
			this.TravelConsultant = "Medkhair";
			this.Seat = s;
			this.ReservationFlight = avFlights.get(f-1);
		}
		for (int i=0;i<cities.size();i++) {
			cities2.add(cities.get(i));
		}
		this.a++;
	}
}
class FlightApp{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int choice;
		ArrayList<Reservation> ress = new ArrayList<Reservation>();
		ArrayList<Flight> flights = new ArrayList<Flight>();
		ArrayList<String> cities = new ArrayList<String>();
		LocalDateTime DepartMadridMarrakech = LocalDateTime.of(2024,6,21,2,30);
		LocalDateTime ArriveMadridMarrakech = LocalDateTime.of(2024,6,21,5,30);
		LocalDateTime DepartCasaBlancaMoskow = LocalDateTime.of(2024,5,30,6,00);
		LocalDateTime ArriveCasaBlancaMoskow = LocalDateTime.of(2024,5,30,10,00);
		LocalDateTime DepartDubaiNewyork = LocalDateTime.of(2024,4,30,6,00);
		LocalDateTime ArriveDubaiNewyork = LocalDateTime.of(2024,4,30,10,00);
		Flight f1 = new Flight("RyanAir","152478","Boing500",15000,0,20,DepartMadridMarrakech,"Madrid",ArriveMadridMarrakech,"Marrakech");
		Flight f2 = new Flight("Royal Air Maroc","1700","F15",400000,0,20,DepartCasaBlancaMoskow,"CasaBlanca",ArriveCasaBlancaMoskow,"Moskow");
		Flight f3 = new Flight("Fly Emirates","785214","boing16",80000,0,20,DepartDubaiNewyork,"Dubai",ArriveDubaiNewyork,"New York");
		flights.add(f1);
		flights.add(f2);
		flights.add(f3);
		f1.setAvailability();
		f2.setAvailability();
		f3.setAvailability();
		cities.add("Madrid");
		cities.add("Marrakech");
		cities.add("CasaBlanca");
		cities.add("Moskow");
		cities.add("Dubai");
		cities.add("New York");
		if (cities.get(0).equals(flights.get(0).DepartingLocation)) {
			System.out.println(cities.get(0));
		}
		do{
			System.out.println("-------------------------------------");
            System.out.println("\t \t Principal: Menu");
            System.out.println("\n\t 1. Show all Available flights");
            System.out.println("\t 2. Booking");
            System.out.println("\t 3. My reservations");
            System.out.println("\t 0. Exit");
            System.out.print("\t Enter your choice : ");
            choice = sc.nextInt();
            sc.nextLine();
            System.out.println("-------------------------------------");
            System.out.println("-------------------------------------");
            switch (choice) {
                case 1:
                  	allAvailableFlights(flights);
                    break;
                case 2:
                	bookingNow(flights,cities,ress);
                    break;
                case 3:
                    myReservations(ress);
                    break;
                case 0:
                    System.out.println("\t Thanks for using our Flight managing System ! See you Later :) ");
                    break;
                default:
                    System.out.println("\t Incorrect choice. Try again.");
            }
        }while (choice != 0);
	}
	static void allAvailableFlights(ArrayList<Flight> flights){
		Scanner sc = new Scanner(System.in);
		int choice,i;
		do{
			System.out.println("-------------------------------------");
            System.out.println("\t \t Available flights");
            System.out.println("-------------------------------------");
            for (i=0;i<flights.size();i++) {
            	System.out.print((i+1)+"- ");
            	flights.get(i).locationTime();
				System.out.println("-------------------------------------");
				System.out.println("-------------------------------------");
            }
			System.out.println("\t 0. <- Back");
            System.out.print("\t Enter your choice : ");
            choice = sc.nextInt();
            sc.nextLine();
            System.out.println("-------------------------------------");
            System.out.println("-------------------------------------");
            switch (choice) {
                case 0:
                    break;
                default:
                	if ((choice-1)<flights.size()) {
                		flights.get(choice-1).get();
                	}else {
                		System.out.println("\t Incorrect choice. Try again.");
                	}
                    
            }
        }while (choice != 0);
	}
	static void bookingNow(ArrayList<Flight> flights, ArrayList<String> cities, ArrayList<Reservation> r){
		LocalDateTime DepartMadridMarrakech = LocalDateTime.of(2024,6,21,2,30);
		LocalDateTime ArriveMadridMarrakech = LocalDateTime.of(2024,6,21,5,30);
		Flight f = new Flight("RyanAir","152478","Boing500",15000,0,20,DepartMadridMarrakech,"Madrid",ArriveMadridMarrakech,"Marrakech");
		boolean st;
		Passanger p = new Passanger(" "," "," "," ");
		Reservation rev = new Reservation(p, "rc", "tc", 3, f, false);
		Scanner sc = new Scanner(System.in);
		int choice;
		do{
			System.out.println("-------------------------------------");
            System.out.println("\t \t Booking");
            System.out.println("-------------------------------------");
			System.out.println("\t 1. <- Book Now");
			System.out.println("\t 0. <- Back");
            System.out.print("\t Enter your choice : ");
            choice = sc.nextInt();
            sc.nextLine();
            System.out.println("-------------------------------------");
            System.out.println("-------------------------------------");
            switch (choice) {
            	case 1:
            		rev.setReservation(cities, flights);
            		r.add(rev);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\t Incorrect choice. Try again.");
            }
        }while (choice != 0);
	}
	static void myReservations(ArrayList<Reservation> r){
		Scanner sc = new Scanner(System.in);
		int choice;
		do{
			System.out.println("-------------------------------------");
            System.out.println("\t \t My reservations");
            System.out.println("-------------------------------------");
			System.out.println("\t 1. <- Show all");
			System.out.println("\t 2. <- Add");
			System.out.println("\t 3. <- Remove");
			System.out.println("\t 0. <- Back");
            System.out.print("\t Enter your choice : ");
            choice = sc.nextInt();
            sc.nextLine();
            System.out.println("-------------------------------------");
            System.out.println("-------------------------------------");
            switch (choice) {
            	case 1:
            		showAll(r);
                    break;
                case 2:
                	LocalDateTime DepartMadridMarrakech = LocalDateTime.of(2024,6,21,2,30);
					LocalDateTime ArriveMadridMarrakech = LocalDateTime.of(2024,6,21,5,30);
                	Flight f = new Flight("RyanAir","152478","Boing500",15000,0,20,DepartMadridMarrakech,"Madrid",ArriveMadridMarrakech,"Marrakech");
                	Passanger p = new Passanger(" "," "," "," ");
					Reservation rev = new Reservation(p, "rc", "tc", 3, f, false);
					ArrayList<Flight> flights = new ArrayList<Flight>();
					ArrayList<String> cities = new ArrayList<String>();
            		bookingNow(flights,cities,r);
                    break;
                case 3:
            		delete(r);                    
            		break;
                case 0:
                    break;
                default:
                    System.out.println("\t Incorrect choice. Try again.");
            }
        }while (choice != 0);
	}
	static void showAll(ArrayList<Reservation> r){
		Scanner sc = new Scanner(System.in);
		int choice;
		do{
			System.out.println("-------------------------------------");
            System.out.println("\t \t All");
            System.out.println("-------------------------------------");
            for (int i=0;i<r.size();i++){
            	System.out.print((i+1)+"- ");
            	r.get(i).ReservationFlight.locationTime();
            }
			System.out.println("\t 0. <- Back");
            System.out.print("\t Enter your choice : ");
            choice = sc.nextInt();
            sc.nextLine();
            System.out.println("-------------------------------------");
            System.out.println("-------------------------------------");
            switch (choice){
                case 0:
                    break;
                default:
                	if ((choice-1)<r.size()) {
                		r.get(choice-1).get();
                	}else {
                		System.out.println("\t Incorrect choice. Try again.");
                	}
            }
        }while (choice != 0);
	}
	static void delete(ArrayList<Reservation> r){
		Scanner sc = new Scanner(System.in);
		int choice;
		do{
			System.out.println("-------------------------------------");
            System.out.println("\t \t Remove");
            System.out.println("-------------------------------------");
            for (int i=0;i<r.size();i++){
            	System.out.print((i+1)+"- ");
            	r.get(i).ReservationFlight.locationTime();
            }
			System.out.println("\t 0. <- Back");
            System.out.print("\t Enter your choice : ");
            choice = sc.nextInt();
            sc.nextLine();
            System.out.println("-------------------------------------");
            System.out.println("-------------------------------------");
            switch (choice){
                case 0:
                    break;
                default:
                	if ((choice-1)<r.size()) {
                		r.remove(choice-1);
                	}else {
                		System.out.println("\t Incorrect choice. Try again.");
                	}
            }
        }while (choice != 0);
	}
}