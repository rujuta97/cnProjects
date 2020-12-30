import java.net.*;
import java.io.*;
import java.util.*;
public class Client {
	public static void main(String[] args) throws IOException{
		Socket socket;
		DataInputStream input = null;
		DataOutputStream output = null;
		final String un = "un";
		final String pw = "pw";
		String uni, pwi;
		String m1;
		String ftpcommand;
			try {
				Scanner sc= new Scanner(System.in);
				while(true) {
					System.out.println("Enter ftp command.");
					ftpcommand = sc.nextLine();
					String[] ftparr = ftpcommand.split(" ");
					if (ftparr.length < 3) {
						System.out.println("Incorrect command.");
					}
					else {
						if (ftparr[0].equals("ftp_client")) {
							if (ftparr[1].equals("localhost") && ftparr[2].equals("8888")) {
								break;
							}
							else {
								System.out.println("Wrong ip or port.");
							}
						}
						else {
							System.out.println("Incorrect command.");
						}
					}
				}
				do {
					System.out.println("Enter username and password:");
					uni = sc.nextLine();
					pwi = sc.nextLine();
				}while(!uni.equals(un) || !pwi.equals(pw));
				socket = new Socket(InetAddress.getByName("localhost"), 8888);
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
				while(true)
				{
					System.out.println(input.readUTF());
	                m1 = sc.nextLine(); 
	                output.writeUTF(m1); 
	                if (m1.equals("Exit")) {
	                	System.out.println("Closing this connection : " + socket); 
                    	socket.close(); 
                    	System.out.println("Connection closed"); 
                    	break; 
	                }
	                String m2 = input.readUTF();
					System.out.println(m2);
				}
	                input.close();
					output.close();
			}
			catch(UnknownHostException unknownHost){
				System.out.println("Host unknown");
			}
		}
		
	}
		