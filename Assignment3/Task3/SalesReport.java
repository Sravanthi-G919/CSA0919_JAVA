import java.io.*;
public class Main{
static String data="Store 1: 1000\nStore 2: 1500\nStore 3: 2000\n";
static void manualReport(){
StringReader reader=null;
StringWriter writer=null;
try{
reader=new StringReader(data);
writer=new StringWriter();
BufferedReader br=new BufferedReader(reader);
BufferedWriter bw=new BufferedWriter(writer);
String line;
while((line=br.readLine())!=null){
bw.write(line);
bw.newLine();
}
bw.flush();
System.out.println("Manual report created successfully.");
System.out.println("Manual report contents:");
System.out.print(writer.toString());
}catch(Exception e){
System.out.println("Manual report failed: "+e.getMessage());
}finally{
if(reader!=null)reader.close();
if(writer!=null)try{writer.close();}catch(Exception e){}
}
}
static void safeReport(){
try(BufferedReader reader=new BufferedReader(new StringReader(data));
StringWriter writer=new StringWriter()){
String line;
while((line=reader.readLine())!=null){
writer.write(line);
writer.write(System.lineSeparator());
}
System.out.println("Try-with-resources report created successfully.");
}catch(Exception e){
System.out.println("Try-with-resources failed: "+e.getMessage());
}
}
public static void main(String[] args){
manualReport();
safeReport();
System.out.println("Report comparison completed.");
}
}
