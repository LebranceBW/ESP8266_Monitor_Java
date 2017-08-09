package ESPWIFI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class mainPanel extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int Port = 2333;
	
	private JButton newSocket_btn = new JButton("Open The Server");
	private JLabel status_Lab = new JLabel("Please Open The Server");
	private JPanel NorPanel = new JPanel();
	private WavePanel FramePanel = new WavePanel();
	private boolean isServerRunning = false;
	private Socket mySocket = new Socket();
	private ServerSocket serverSocket;
	private JLabel CharReceived = new JLabel();
	public static void main(String[] args)
	{
		mainPanel ms = new mainPanel();
		ms.setVisible(true);
		ms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	Thread serverThread = new Thread()
	{
		
		@Override
		public void run()
		{
			boolean f = beginSocket();// TODO Auto-generated method stub
			newSocket_btn.setEnabled(true);
			if(f == false)	
			{
				status_Lab.setText("Server Fault");
				newSocket_btn.setText(new String("Open The Server"));
			}
			else
			{		
				newSocket_btn.setText("Close The Socket");
				status_Lab.setText("Connect Successed！" + mySocket.getRemoteSocketAddress());
			}
			BufferedReader is;
			
			try
			{
				is = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
				while(true)
				{
					int temp = is.read();
					FramePanel.enterQ(temp);
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	};
	
	public mainPanel()
	{
		super();
		setUIFont (new javax.swing.plaf.FontUIResource("Serif",Font.ITALIC,12));
		setSize(new Dimension(800, 500));		
		setTitle("幅频特性曲线显示");
		getContentPane().setLayout(new BorderLayout(10,10));
		getContentPane().add(NorPanel,BorderLayout.NORTH);
		NorPanel.setLayout(new FlowLayout(FlowLayout.LEFT,60,30));
		NorPanel.add(newSocket_btn);
		NorPanel.add(status_Lab);
		NorPanel.add(CharReceived);
		getContentPane().add(FramePanel, BorderLayout.CENTER);
		
		newSocket_btn.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(false == isServerRunning)
				{
					newSocket_btn.setText("Waitting for connect");
					newSocket_btn.setEnabled(false);
					status_Lab.setText("Waitting for connect,Port 2333");
					isServerRunning = !isServerRunning;
					serverThread.start();
				}
				else
				{
					try
					{
						mySocket.close();
						serverSocket.close();
					} catch (IOException e1)
					{
						e1.printStackTrace();
					}
					newSocket_btn.setText(new String("Open The Server"));
					isServerRunning = !isServerRunning;
					status_Lab.setText("Server Closed");
					
				}
			}
		});
	}
	
	public boolean beginSocket()
	{
		try
		{
			serverSocket = new ServerSocket(Port);
			
			mySocket=serverSocket.accept();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    //
	    // sets the default font for all Swing components.
	    // ex. 
	    //  setUIFont (new javax.swing.plaf.FontUIResource("Serif",Font.ITALIC,12));
	    //
	    @SuppressWarnings("rawtypes")
		java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	    }
}
