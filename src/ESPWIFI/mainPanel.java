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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class mainPanel extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JButton newSocket_btn = new JButton("Connect");
	public JLabel ipLabel = new JLabel("IP:");
	public JTextArea ipText = new JTextArea("127.0.0.1");
	public JLabel portLabel = new JLabel("Port:");
	public JTextArea portText = new JTextArea("233");
	
	public JLabel status_Lab = new JLabel("Please Connect To The Server");
	public JPanel NorPanel = new JPanel();
	private WavePanel FramePanel = new WavePanel();
	private boolean isServerRunning = false;
	private Socket mySocket;

	private JLabel CharReceived = new JLabel();
	public static void main(String[] args)
	{
		mainPanel ms = new mainPanel();
		ms.setVisible(true);
	}
	
	Thread serverThread = new Thread()
	{
		
		
	};
	
	public mainPanel()
	{
		super();
		setUIFont (new javax.swing.plaf.FontUIResource("Serif",Font.ITALIC,12));
		setSize(new Dimension(800, 500));		
		setTitle("幅频特性曲线显示");
		getContentPane().setLayout(new BorderLayout(10,10));
		getContentPane().add(NorPanel,BorderLayout.NORTH);
		NorPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,30));
		NorPanel.add(ipLabel);
		NorPanel.add(ipText);
		NorPanel.add(portLabel);
		NorPanel.add(portText);
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
			
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(false == isServerRunning)
				{
					newSocket_btn.setEnabled(false);
					status_Lab.setText("On Connecting,addr:" + ipText.getText()+":"+portText.getText());
					isServerRunning = !isServerRunning;
					serverThread = new socketThread();
					serverThread.start();
				}
				else
				{
					serverThread.stop();
					try
					{
						mySocket.close();
					} catch (IOException e1)
					{
						e1.printStackTrace();
					}
					newSocket_btn.setText(new String("Connect"));
					isServerRunning = !isServerRunning;
					status_Lab.setText("Connection closed");
					
				}
			}
		});
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
	
	
	
	
	public class socketThread extends Thread
	{

		@Override
		public void run()
		{
			boolean f = beginSocket();// TODO Auto-generated method stub
			newSocket_btn.setEnabled(true);
			if(f == false)	
			{
				status_Lab.setText("Connect Failed");
				newSocket_btn.setText(new String("Reconnect"));
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
		public boolean beginSocket()
		{
			try
			{
				SocketAddress addr = new InetSocketAddress(ipText.getText(),Integer.valueOf(portText.getText()));
				mySocket = new Socket();
				mySocket.connect(addr,10);
				return true;
			}
			catch (Exception e) {
				return false;
			}
		}
	}

}
