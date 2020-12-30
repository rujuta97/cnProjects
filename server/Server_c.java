import java.io.*;
import java.util.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
public class Server_c {

	public static void main(String[] args) throws IOException{
		Socket sock = null;
		DataInputStream input = null;
		DataOutputStream output = null;
		String message1;
		
		int port= 8888;
	   ServerSocket soc = new ServerSocket(port);
		while(true){
			try {
				sock=soc.accept();
				output = new DataOutputStream(sock.getOutputStream());
				input = new DataInputStream(sock.getInputStream());
                System.out.println("Conection created");
				 Thread th = new HelpClient(sock, input, output);
	                th.start(); 
			}
			catch (Exception e){ 
                sock.close();   
                e.printStackTrace(); 
			}
			
		}
		
	}

}
class HelpClient extends Thread  
{ 
	public String getDir(String dirName){
        File directory = new File(dirName);
        File[] FileList = directory.listFiles();
        String f = "";
        for (File file : FileList){
            f += file.getName() + "\n";
        }
        return f;
	}
    final DataInputStream input; 
    final DataOutputStream output; 
    final Socket sock; 
	
    public HelpClient(Socket sock, DataInputStream input, DataOutputStream output)  
    { 
        this.sock = sock; 
        this.input = input; 
        this.output = output; 
    } 
    
    public void run()  
    { 
        String strarrived; 
        String ret;
        while (true)  
        { 
            try {  
                output.writeUTF("choose the operation to be performed"); 
                strarrived = input.readUTF(); 
                  
                if(strarrived.equals("Exit")) 
                {  
                    System.out.println("Closing this connection."); 
                    sock.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
                String[] strarrivedarr = strarrived.split(" ");
                   
                    if(strarrivedarr[0].equals("dir")){
                        ret= getDir(".");
                        output.writeUTF(ret); 
                        
                    } 
                        
                          
                    else if (strarrivedarr[0].equals("get")){

                        if (strarrivedarr.length < 3) {
                            output.writeUTF("Incorrect Command.");
                        }
                        else {
                            File srcFile = new File(strarrivedarr[1]);
                            if (!srcFile.exists()) {
                                output.writeUTF("No such file exists.");
                            }
                            else {
                                File destFile = new File("../" + strarrivedarr[2] + "/" + strarrivedarr[1]);
                                String info = new String(Files.readAllBytes(Paths.get(strarrivedarr[1])));
                                Files.write(destFile.toPath(), info.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
                                ret = "File was retrieved from the server.";
                                output.writeUTF(ret); 
                            }
                        }
                         
                    } 
                    	 
                     
                    else if(strarrivedarr[0].equals("upload")){
                        if (strarrivedarr.length < 3) {
                            output.writeUTF("Incorrect Command.");
                        }
                        else {
                            File srcFile = new File("../" + strarrivedarr[2] + "/" + strarrivedarr[1]);
                            if (!srcFile.exists()) {
                                output.writeUTF("No such file exists.");
                            }
                            else {
                                File destFile = new File(strarrivedarr[1]);
                                String info = new String(Files.readAllBytes(Paths.get("../" + strarrivedarr[2] + "/" + strarrivedarr[1])));
                                Files.write(destFile.toPath(), info.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
                                ret = "File was uploaded to the server.";
                                output.writeUTF(ret); 
                            }
                        }
                    }
                    	 
                          
                    else{

                        output.writeUTF("Input is incorrect"); 
                    } 
                        
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
          
        try
        { 
            input.close(); 
            output.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 

