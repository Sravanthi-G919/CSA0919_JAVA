import java.util.*;
import java.io.*;
public class Main{
public static void main(String[] args){
Scanner sc=new Scanner(System.in);
double balance=5000;
try{
try(FileInputStream file=new FileInputStream("card_reader.txt")){
System.out.println("Card reader file found.");
}catch(IOException e){
System.out.println("Warning: Card reader file is missing.");
}
System.out.print("Enter account number: ");
String account=sc.nextLine();
if(account.trim().isEmpty())throw new IllegalArgumentException("Account number cannot be empty.");
System.out.print("Enter withdrawal amount: ");
double amount;
try{
amount=Double.parseDouble(sc.nextLine());
}catch(NumberFormatException e){
System.out.println("Error: Enter a valid numeric amount.");
return;
}
if(amount<0)throw new IllegalArgumentException("Amount cannot be negative.");
if(amount==0)throw new IllegalArgumentException("Amount must be greater than zero.");
if(amount>balance)throw new ArithmeticException("Insufficient balance.");
balance-=amount;
System.out.println("Withdrawal successful.");
System.out.println("Amount withdrawn: "+amount);
System.out.println("Remaining balance: "+balance);
}catch(IllegalArgumentException|ArithmeticException e){
System.out.println("Transaction failed: "+e.getMessage());
}finally{
System.out.println("ATM transaction ended.");
sc.close();
}
}
}

