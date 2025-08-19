package project;


import java.util.*;

//Interface defining banking operations
interface BankingOperations {
 void deposit(double amount);
 void withdraw(double amount);
 void transfer(BankAccount receiver, double amount);
 void showBalance();
}

//BankAccount class implementing BankingOperations
class BankAccount implements BankingOperations {
 private String accountNo;
 private String password;
 private double balance;

 // Constructor
 public BankAccount(String accountNo, String password, double balance) {
     this.accountNo = accountNo;
     this.password = password;
     this.balance = balance;
 }

 // Getter & Setter
 public String getAccountNo() {
     return accountNo;
 }

 public void setAccountNo(String accountNo) {
     this.accountNo = accountNo;
 }

 public String getPassword() {
     return password;
 }

 public void setPassword(String password) {
     this.password = password;
 }

 public double getBalance() {
     return balance;
 }

 private void setBalance(double balance) {  // keep setter private to avoid misuse
     this.balance = balance;
 }

 // Deposit
 @Override
 public void deposit(double amount) {
     if (amount > 0) {
         setBalance(getBalance() + amount);
         System.out.println("Deposit successful. New Balance: " + getBalance());
     } else {
         System.out.println(" Invalid deposit amount!");
     }
 }

 // Withdraw
 @Override
 public void withdraw(double amount) {
     if (amount > 0 && amount <= getBalance()) {
         setBalance(getBalance() - amount);
         System.out.println("Withdrawal successful. New Balance: " + getBalance());
     } else {
         System.out.println("Insufficient balance or invalid amount!");
     }
 }

 // Transfer with OTP verification
 @Override
 public void transfer(BankAccount receiver, double amount) {
     if (amount > 0 && amount <= getBalance()) {
         int otp = generateOTP();
         Scanner sc = new Scanner(System.in);
         System.out.println("Enter OTP to confirm transfer: " + otp);
         int enteredOtp = sc.nextInt();
         
         if (enteredOtp == otp) {
             this.setBalance(this.getBalance() - amount);
             receiver.setBalance(receiver.getBalance() + amount);
             System.out.println(" Transfer successful. Remaining Balance: " + getBalance());
         } else {
             System.out.println(" OTP Verification Failed. Transfer Cancelled!");
         }
     } else {
         System.out.println("Insufficient balance or invalid amount!");
     }
 }

 // Show balance
 @Override
 public void showBalance() {
     System.out.println(" Current Balance: " + getBalance());
 }

 // OTP Generator (4 digit random number)
 private int generateOTP() {
     Random rand = new Random();
     return 1000 + rand.nextInt(9000);
 }
}

public class BankingApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

	     // Hardcoded accounts
	     BankAccount acc1 = new BankAccount("12345", "pass123", 5000);
	     BankAccount acc2 = new BankAccount("67890", "word321", 3000);

	     System.out.print("Enter Account Number: ");
	     String accNo = sc.nextLine();
	     System.out.print("Enter Password: ");
	     String pass = sc.nextLine();

	     BankAccount currentUser = null;

	     // Simple login check
	     if (acc1.getAccountNo().equals(accNo) && acc1.getPassword().equals(pass)) {
	         currentUser = acc1;
	     } else if (acc2.getAccountNo().equals(accNo) && acc2.getPassword().equals(pass)) {
	         currentUser = acc2;
	     } else {
	         System.out.println(" Invalid Credentials!");
	         System.exit(0);
	     }

	     // Banking menu
	     int choice;
	     do {
	         System.out.println("\n--- Banking Menu ---");
	         System.out.println("1. Deposit");
	         System.out.println("2. Withdraw");
	         System.out.println("3. Transfer");
	         System.out.println("4. Show Balance");
	         System.out.println("5. Logout");
	         System.out.print("Enter your choice: ");
	         choice = sc.nextInt();

	         switch (choice) {
	             case 1:
	                 System.out.print("Enter deposit amount: ");
	                 double depAmt = sc.nextDouble();
	                 currentUser.deposit(depAmt);
	                 break;
	             case 2:
	                 System.out.print("Enter withdrawal amount: ");
	                 double witAmt = sc.nextDouble();
	                 currentUser.withdraw(witAmt);
	                 break;
	             case 3:
	                 System.out.print("Enter transfer amount: ");
	                 double trAmt = sc.nextDouble();
	                 currentUser.transfer(acc1 == currentUser ? acc2 : acc1, trAmt);
	                 break;
	             case 4:
	                 currentUser.showBalance();
	                 break;
	             case 5:
	                 System.out.println("Logged out successfully!");
	                 break;
	             default:
	                 System.out.println("Invalid choice!");
	         }
	     } while (choice != 5);

	     sc.close();


	}

}

