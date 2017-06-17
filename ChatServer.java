// Chat.java
// to provide communication between two connected systems

   import java.awt.*;
   import java.awt.event.*;
   import java.io.*;
   import java.net.*;


 // Chat class : contains main function

   class ChatServer
   {
     public static void main(String args[])
     {
       try
       {
         // KB
           BufferedReader stdin = new BufferedReader(
             new InputStreamReader(System.in));

        // Accept a choice
         // System.out.print("Enter 1.Server  2.Client : ");
         // int ch = Integer.parseInt(stdin.readLine());



            //modified here

             int ch=1;
         /* if(ch == 1)
          {
            // start the server
              ServerSocket server = new ServerSocket(9999);
              System.out.println("Ready ... ");
              Socket s = server.accept();
              System.out.println("Connected to : "+s.getInetAddress());
              MyFrame f = new MyFrame(s);
              f.setTitle("First System ... ");
              f.setBounds(50,50,300,200);
              f.setVisible(true);
          }*/
         if(ch==1)
          {
            // start the client
               System.out.print("Enter Dest Host Name : ");
               String hostName = stdin.readLine();

               Socket s = new Socket(hostName,9999);
               System.out.println("Connected to : "+s.getInetAddress());
               MyFrame f = new MyFrame(s);
               f.setTitle("... Second System");
               f.setBounds(400,50,300,200);
               f.setVisible(true);
          }   

                   // end of if()
       
        }
        catch(Exception ex)
        {
          System.out.println("Error: "+ex);
        }
     }                           // end of main()
   }                             // end of class



  // MyFrame class : Provides GUI

  class MyFrame extends Frame implements ActionListener
  {
     Socket s; BufferedReader in; PrintWriter out;
     TextField t1;  TextArea t2;

     MyFrame(Socket s)
     {
       this.s = s;
       setFont(new Font("SansSerif",Font.BOLD,14));

       t1 = new TextField();
       t2 = new TextArea();

       add(t1,BorderLayout.SOUTH);
       add(t2,BorderLayout.CENTER);

       t1.addActionListener(this);
       try
       {
         // Get the Streams from the Socket connection

         in = new BufferedReader(new InputStreamReader
         (s.getInputStream()));
         out = new PrintWriter(s.getOutputStream());

          // Send the input stream to the Thread
          // i.e. send the in to the ReadData
            ReadData r = new ReadData(this,in);
            r.start();      // start the Thread

       }
       catch(Exception ex)
       {
         System.out.println("Error: "+ex);
       }
     }                  // end of MyFrame()

     public void actionPerformed(ActionEvent e)
     {
        if(e.getSource() == t1)
        {                              
          sendAcceptedData();
        }
     }                     // end of actionPerformed()


     void sendAcceptedData()
     {
        // Send the Accepted data to the connected system

         try
         {
           String str = t1.getText();

           t2.append("\r\nSent: "+str);
           t1.setText("");           // Remove data from t1

           out.write(str+"\r\n");   // Send to NW
           out.flush();

           t1.requestFocus();
         }
         catch(Exception ex)
         {
           System.out.println("Error: "+ex);
         }
      }                      // end of sendAcceptedData()
  }                          // end of class MyFrame



  // ReadData class monitors the network input
  class ReadData extends Thread
  {
     MyFrame f;  BufferedReader in;

    ReadData(MyFrame f, BufferedReader in)
    {
       this.f = f;
       this.in = in;

    }                     // end of constructor

     public void run()    // Overridden
     {
      while(true)
      {
        try
        {
          // Read Data from the NW
             String str = in.readLine();

             if(str == null)
             {
               break;
             }


           // Place data into the Frame's TextArea
              f.t2.append("\r\nRecd: "+str);

          }
          catch(Exception ex)
          {
            System.out.println("Error: "+ex);
            break;
          }
        }                       // end of while()

     // close the Socket
       try
       {
         f.s.close();
       }
       catch(Exception ex)
       {
         System.out.println("Error: "+ex);
       }

     }                    // end of run()
   }                      // end of class ReadData


















